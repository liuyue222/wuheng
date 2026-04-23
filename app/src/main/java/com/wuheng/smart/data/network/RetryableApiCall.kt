package com.wuheng.smart.data.network

import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.delay
import timber.log.Timber
import java.io.IOException
import java.net.SocketTimeoutException

/**
 * 重试配置
 *
 * @param maxRetries 最大重试次数
 * @param initialDelayMillis 初始重试延迟（毫秒）
 * @param maxDelayMillis 最大重试延迟（毫秒）
 * @param exponentialBackoff 是否使用指数退避
 * @param retryOnNetworkError 是否在网络错误时重试
 * @param retryOnTimeout 是否在超时时重试
 * @param retryOnServerError 是否在服务器错误(5xx)时重试
 */
data class RetryConfig(
    val maxRetries: Int = 3,
    val initialDelayMillis: Long = 1000L,
    val maxDelayMillis: Long = 10000L,
    val exponentialBackoff: Boolean = true,
    val retryOnNetworkError: Boolean = true,
    val retryOnTimeout: Boolean = true,
    val retryOnServerError: Boolean = true
) {
    companion object {
        /**
         * 默认重试配置
         */
        val DEFAULT = RetryConfig()

        /**
         * 无重试配置
         */
        val NO_RETRY = RetryConfig(maxRetries = 0)

        /**
         * 积极重试配置（更多重试次数）
         */
        val AGGRESSIVE = RetryConfig(
            maxRetries = 5,
            initialDelayMillis = 500L,
            maxDelayMillis = 5000L
        )

        /**
         * 保守重试配置（较少重试次数）
         */
        val CONSERVATIVE = RetryConfig(
            maxRetries = 2,
            initialDelayMillis = 2000L,
            maxDelayMillis = 15000L
        )

        /**
         * 快速重试配置（用于登录等需要快速响应的场景）
         */
        val FAST = RetryConfig(
            maxRetries = 2,
            initialDelayMillis = 500L,
            maxDelayMillis = 2000L
        )
    }
}

/**
 * 带重试机制的API调用
 *
 * @param retryConfig 重试配置
 * @param operationName 操作名称（用于日志）
 * @param apiCall 实际的API调用
 * @return ApiResult
 */
suspend fun <T> retryableApiCall(
    retryConfig: RetryConfig = RetryConfig.DEFAULT,
    operationName: String = "API",
    apiCall: suspend () -> BaseResponse<T>
): ApiResult<T> {
    var lastException: AppException? = null
    var currentDelay = retryConfig.initialDelayMillis

    for (attempt in 0..retryConfig.maxRetries) {
        try {
            if (attempt > 0) {
                Timber.d("[$operationName] 第${attempt}次重试，延迟${currentDelay}ms")
                delay(currentDelay)

                // 计算下一次延迟（指数退避）
                if (retryConfig.exponentialBackoff) {
                    currentDelay = (currentDelay * 2).coerceAtMost(retryConfig.maxDelayMillis)
                }
            }

            val response = apiCall()

            return if (response.isSuccess()) {
                response.data?.let {
                    if (attempt > 0) {
                        Timber.d("[$operationName] 重试成功")
                    }
                    ApiResult.Success(it)
                } ?: ApiResult.Error(AppException.BusinessError(-1, "返回数据为空"))
            } else {
                // 业务错误，不重试
                ApiResult.Error(
                    AppException.BusinessError(
                        response.code,
                        response.message
                    )
                )
            }
        } catch (e: CancellationException) {
            // 重新抛出取消异常
            throw e
        } catch (e: SocketTimeoutException) {
            Timber.e(e, "[$operationName] 请求超时 (attempt $attempt)")
            lastException = AppException.TimeoutError()

            if (!retryConfig.retryOnTimeout || attempt >= retryConfig.maxRetries) {
                return ApiResult.Error(lastException)
            }
        } catch (e: IOException) {
            Timber.e(e, "[$operationName] 网络错误 (attempt $attempt)")
            lastException = AppException.NetworkError("网络连接异常")

            if (!retryConfig.retryOnNetworkError || attempt >= retryConfig.maxRetries) {
                return ApiResult.Error(lastException)
            }
        } catch (e: retrofit2.HttpException) {
            Timber.e(e, "[$operationName] HTTP错误: ${e.code()} (attempt $attempt)")
            val result = handleHttpExceptionWithRetry<T>(e, retryConfig, attempt)

            // 如果需要重试，继续循环
            if (result == null) {
                lastException = when (e.code()) {
                    401 -> AppException.Unauthorized(originalCode = e.code())
                    403 -> AppException.BusinessError(e.code(), "权限不足")
                    404 -> AppException.NotFound()
                    in 500..599 -> AppException.ServerError(e.code(), e.message ?: "服务器错误")
                    else -> AppException.BusinessError(e.code(), e.message ?: "请求失败")
                }
                continue
            }

            // 不需要重试，直接返回
            return result
        } catch (e: Exception) {
            Timber.e(e, "[$operationName] 未知错误 (attempt $attempt)")
            return ApiResult.Error(AppException.UnknownError(e.message ?: "未知错误"))
        }
    }

    // 所有重试都失败了
    return ApiResult.Error(lastException ?: AppException.UnknownError("请求失败，已重试${retryConfig.maxRetries}次"))
}

/**
 * 处理HTTP异常并决定是否重试
 *
 * @return 如果返回null表示需要重试，否则返回ApiResult
 */
private fun <T> handleHttpExceptionWithRetry(
    e: retrofit2.HttpException,
    retryConfig: RetryConfig,
    currentAttempt: Int
): ApiResult<T>? {
    val code = e.code()

    return when (code) {
        401 -> {
            // 401需要重新登录，不重试
            AuthEventManager.postAuthEvent(AuthEvent.Unauthorized)
            ApiResult.Error(
                AppException.Unauthorized(
                    message = "登录已过期，请重新登录",
                    originalCode = code
                )
            )
        }
        403 -> {
            // 403权限不足，不重试
            ApiResult.Error(AppException.BusinessError(code, "权限不足"))
        }
        404 -> {
            // 404资源不存在，不重试
            ApiResult.Error(AppException.NotFound())
        }
        in 500..599 -> {
            // 服务器错误，根据配置决定是否重试
            if (retryConfig.retryOnServerError && currentAttempt < retryConfig.maxRetries) {
                null // 返回null表示需要重试
            } else {
                ApiResult.Error(AppException.ServerError(code, e.message ?: "服务器错误"))
            }
        }
        else -> {
            // 其他错误，不重试
            ApiResult.Error(AppException.BusinessError(code, e.message ?: "请求失败"))
        }
    }
}

/**
 * 带重试机制的API Flow构建器
 *
 * @param operation 操作名称
 * @param params 操作参数（用于日志）
 * @param retryConfig 重试配置
 * @param block 实际的API调用逻辑
 * @return Flow<ApiResult<T>>
 */
inline fun <T> retryableApiFlow(
    operation: String,
    params: String? = null,
    retryConfig: RetryConfig = RetryConfig.DEFAULT,
    crossinline block: suspend () -> ApiResult<T>
): kotlinx.coroutines.flow.Flow<ApiResult<T>> = kotlinx.coroutines.flow.flow {
    if (params != null) {
        Timber.d("Repository Operation: $operation, Params: $params")
    } else {
        Timber.d("Repository Operation: $operation")
    }

    emit(ApiResult.Loading)
    emit(block())
}

/**
 * Repository扩展函数：带重试的API调用
 * 注意：此扩展函数在BaseRepository内部使用，避免循环依赖
 */
/*
suspend fun <T> BaseRepository.apiCallWithRetry(
    operation: String,
    retryConfig: RetryConfig = RetryConfig.DEFAULT,
    apiCall: suspend () -> BaseResponse<T>
): ApiResult<T> {
    return retryableApiCall(
        retryConfig = retryConfig,
        operationName = operation,
        apiCall = apiCall
    )
}
*/
