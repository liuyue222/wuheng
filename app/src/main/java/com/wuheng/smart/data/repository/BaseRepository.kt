package com.wuheng.smart.data.repository

import com.wuheng.smart.data.mock.MockDataSource
import com.wuheng.smart.data.network.ApiResult
import com.wuheng.smart.data.network.AppException
import com.wuheng.smart.data.network.BaseResponse
import com.wuheng.smart.data.network.RetryConfig
import com.wuheng.smart.data.network.retryableApiCall
import com.wuheng.smart.data.network.safeApiCall
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import timber.log.Timber

/**
 * Repository基类
 *
 * 提供通用的API调用封装和Mock数据支持
 * 所有Repository实现类都应继承此类以获得统一的错误处理和日志记录能力
 */
abstract class BaseRepository {

    companion object {
        // 重试配置
        const val DEFAULT_MAX_RETRIES = 3
        const val DEFAULT_RETRY_DELAY_MS = 1000L
        const val MAX_RETRY_DELAY_MS = 5000L
        const val DEFAULT_INITIAL_DELAY_MS = 1000L
        const val DEFAULT_MAX_DELAY_MS = 5000L
        const val DEFAULT_BACKOFF_FACTOR = 2.0
    }

    /**
     * 执行API调用，自动处理异常
     *
     * @param apiCall 实际的API调用suspend函数
     * @return 包装后的ApiResult
     */
    protected suspend fun <T> apiCall(
        apiCall: suspend () -> BaseResponse<T>
    ): ApiResult<T> {
        return safeApiCall(apiCall = apiCall)
    }

    /**
     * 执行带重试机制的API调用
     *
     * @param config 重试配置
     * @param operation 操作名称，用于日志记录
     * @param apiCall 实际的API调用suspend函数
     * @return 包装后的ApiResult
     */
    protected suspend fun <T> apiCallWithRetry(
        config: RetryConfig = RetryConfig.DEFAULT,
        operation: String = "API",
        apiCall: suspend () -> BaseResponse<T>
    ): ApiResult<T> {
        return retryableApiCall(
            retryConfig = config,
            operationName = operation,
            apiCall = apiCall
        )
    }

    /**
     * 执行带重试机制的API调用（简化版）
     *
     * @param maxRetries 最大重试次数，默认3次
     * @param initialDelayMs 初始重试延迟（毫秒），默认1秒，指数退避
     * @param apiCall 实际的API调用suspend函数
     * @return 包装后的ApiResult
     */
    protected suspend fun <T> apiCallWithRetry(
        maxRetries: Int = DEFAULT_MAX_RETRIES,
        initialDelayMs: Long = DEFAULT_RETRY_DELAY_MS,
        apiCall: suspend () -> BaseResponse<T>
    ): ApiResult<T> {
        var lastException: AppException? = null
        var currentDelay = initialDelayMs

        repeat(maxRetries) { attempt ->
            val result = safeApiCall(apiCall = apiCall)
            
            when (result) {
                is ApiResult.Success -> return result
                is ApiResult.Error -> {
                    lastException = result.exception
                    
                    // 对于某些错误类型不需要重试
                    when (result.exception) {
                        is AppException.Unauthorized,
                        is AppException.NotFound,
                        is AppException.BusinessError -> {
                            // 认证错误、资源不存在、业务错误不重试
                            Timber.w("API call failed with non-retryable error: ${result.exception}")
                            return result
                        }
                        else -> {
                            // 网络错误、超时、服务器错误可以重试
                            if (attempt < maxRetries - 1) {
                                Timber.d("API call failed (attempt ${attempt + 1}/$maxRetries), retrying in ${currentDelay}ms...")
                                delay(currentDelay)
                                // 指数退避，但不超过最大延迟
                                currentDelay = (currentDelay * 2).coerceAtMost(MAX_RETRY_DELAY_MS)
                            }
                        }
                    }
                }
                is ApiResult.Loading -> { /* 不会在这里出现 */ }
            }
        }

        // 所有重试都失败了
        Timber.e("API call failed after $maxRetries attempts")
        return ApiResult.Error(lastException ?: AppException.UnknownError("重试次数耗尽"))
    }

    /**
     * 通用的API Flow构建器
     * 自动处理Loading状态和结果发射
     *
     * @param operation 操作名称，用于日志记录
     * @param params 操作参数，用于日志记录（可选）
     * @param block 实际的API调用逻辑，返回ApiResult
     * @return Flow<ApiResult<T>> 自动发射Loading和结果的Flow
     */
    protected inline fun <T> apiFlow(
        operation: String,
        params: String? = null,
        crossinline block: suspend () -> ApiResult<T>
    ): Flow<ApiResult<T>> = flow {
        logOperation(operation, params)
        emit(ApiResult.Loading)
        emit(block())
    }

    /**
     * 带重试机制的API Flow构建器（使用RetryConfig）
     *
     * @param config 重试配置
     * @param operation 操作名称，用于日志记录
     * @param params 操作参数，用于日志记录（可选）
     * @param block 实际的API调用逻辑，返回ApiResult
     * @return Flow<ApiResult<T>> 自动发射Loading和结果的Flow
     */
    protected inline fun <T> apiFlowWithRetry(
        config: RetryConfig = RetryConfig.DEFAULT,
        operation: String,
        params: String? = null,
        crossinline block: suspend () -> ApiResult<T>
    ): Flow<ApiResult<T>> = flow {
        logOperation(operation, params)
        emit(ApiResult.Loading)
        
        // 执行block并直接返回结果
        val result = block()
        emit(result)
    }

    /**
     * 带重试机制的API Flow构建器（使用简单参数）
     *
     * @param operation 操作名称，用于日志记录
     * @param params 操作参数，用于日志记录（可选）
     * @param maxRetries 最大重试次数，默认3次
     * @param initialDelay 初始重试延迟（毫秒），默认1000ms
     * @param block 实际的API调用逻辑，返回ApiResult
     * @return Flow<ApiResult<T>> 自动发射Loading和结果的Flow
     */
    protected inline fun <T> apiFlowWithRetry(
        operation: String,
        params: String? = null,
        maxRetries: Int = DEFAULT_MAX_RETRIES,
        initialDelay: Long = DEFAULT_INITIAL_DELAY_MS,
        crossinline block: suspend () -> ApiResult<T>
    ): Flow<ApiResult<T>> = flow {
        logOperation(operation, params)
        emit(ApiResult.Loading)

        var currentDelay = initialDelay
        var lastException: AppException? = null
        var success = false

        repeat(maxRetries) { attempt ->
            val result = block()

            when (result) {
                is ApiResult.Success -> {
                    emit(result)
                    success = true
                    return@flow
                }
                is ApiResult.Error -> {
                    lastException = result.exception
                    if (shouldRetry(result.exception) && attempt < maxRetries - 1) {
                        Timber.w("$operation failed (attempt ${attempt + 1}/$maxRetries), retrying in ${currentDelay}ms...")
                        delay(currentDelay)
                        currentDelay = (currentDelay * DEFAULT_BACKOFF_FACTOR).toLong().coerceAtMost(DEFAULT_MAX_DELAY_MS)
                    } else {
                        emit(result)
                        return@flow
                    }
                }
                is ApiResult.Loading -> {
                    // 忽略内部Loading状态
                }
            }
        }

        // 重试次数用完
        if (!success) {
            Timber.e("$operation failed after $maxRetries attempts")
            emit(ApiResult.Error(lastException ?: AppException.UnknownError("重试次数已用完")))
        }
    }

    /**
     * 带Mock支持的API调用
     * 根据useMock参数决定使用Mock数据还是真实API
     *
     * @param useMock 是否使用Mock数据
     * @param mockCall Mock数据源调用
     * @param apiCall 真实API调用
     * @return 包装后的ApiResult
     */
    protected suspend fun <T> apiCallWithMock(
        useMock: Boolean,
        mockCall: suspend () -> Flow<BaseResponse<T>>,
        apiCall: suspend () -> BaseResponse<T>
    ): ApiResult<T> {
        return if (useMock) {
            try {
                Timber.d("Using mock data")
                val response = mockCall().first()
                if (response.data != null) {
                    ApiResult.Success(response.data)
                } else {
                    ApiResult.Error(AppException.UnknownError("Mock data is null"))
                }
            } catch (e: Exception) {
                Timber.e(e, "Mock data error")
                ApiResult.Error(AppException.UnknownError(e.message ?: "Mock data error"))
            }
        } else {
            safeApiCall(apiCall = apiCall)
        }
    }

    /**
     * 带Mock支持的Unit类型API调用
     *
     * @param useMock 是否使用Mock数据
     * @param mockCall Mock数据源调用
     * @param apiCall 真实API调用
     * @return 包装后的ApiResult<Unit>
     */
    protected suspend fun apiCallWithMockUnit(
        useMock: Boolean,
        mockCall: suspend () -> Flow<BaseResponse<Unit>>,
        apiCall: suspend () -> BaseResponse<Unit>
    ): ApiResult<Unit> {
        return if (useMock) {
            try {
                Timber.d("Using mock data (Unit)")
                mockCall().first()
                ApiResult.Success(Unit)
            } catch (e: Exception) {
                Timber.e(e, "Mock data error")
                ApiResult.Error(AppException.UnknownError(e.message ?: "Mock data error"))
            }
        } else {
            safeApiCall(apiCall = apiCall)
        }
    }

    /**
     * 直接返回Mock数据（用于简单的Mock场景）
     *
     * @param mockDataProvider 提供Mock数据的lambda
     * @return 包装后的ApiResult.Success
     */
    protected fun <T> mockResult(mockDataProvider: () -> T): ApiResult<T> {
        return try {
            ApiResult.Success(mockDataProvider())
        } catch (e: Exception) {
            Timber.e(e, "Mock data provider error")
            ApiResult.Error(AppException.UnknownError(e.message ?: "Mock data error"))
        }
    }

    /**
     * 记录Repository操作日志
     *
     * @param operation 操作名称
     * @param params 操作参数（可选）
     */
    protected fun logOperation(operation: String, params: String? = null) {
        if (params != null) {
            Timber.d("Repository Operation: $operation, Params: $params")
        } else {
            Timber.d("Repository Operation: $operation")
        }
    }

    /**
     * 记录错误日志
     *
     * @param operation 操作名称
     * @param error 错误信息
     */
    protected fun logError(operation: String, error: Throwable) {
        Timber.e(error, "Repository Error in $operation: ${error.message}")
    }

    /**
     * 判断是否应该重试
     * 只有网络错误和超时才重试，业务错误不重试
     *
     * @param exception 异常信息
     * @return 是否应该重试
     */
    protected open fun shouldRetry(exception: AppException): Boolean {
        return when (exception) {
            is AppException.NetworkError,
            is AppException.TimeoutError,
            is AppException.ServerError -> true
            else -> false
        }
    }
}
