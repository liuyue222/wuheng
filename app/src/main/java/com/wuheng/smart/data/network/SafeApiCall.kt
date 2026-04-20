package com.wuheng.smart.data.network

import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import timber.log.Timber
import java.io.IOException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

/**
 * 安全的 API 调用包装函数
 * 自动处理异常并转换为 ApiResult
 *
 * 特性：
 * 1. 自动处理 401 Unauthorized 错误，清除 Token 并触发重新登录
 * 2. 网络异常、超时异常统一处理
 * 3. 业务错误码转换
 */
suspend fun <T> safeApiCall(
    emitLoading: suspend () -> Unit = {},
    apiCall: suspend () -> BaseResponse<T>
): ApiResult<T> {
    return withContext(Dispatchers.IO) {
        try {
            emitLoading()
            val response = apiCall()

            if (response.isSuccess()) {
                response.data?.let {
                    ApiResult.Success(it)
                } ?: ApiResult.Error(AppException.BusinessError(-1, "返回数据为空"))
            } else {
                ApiResult.Error(
                    AppException.BusinessError(
                        response.code,
                        response.message
                    )
                )
            }
        } catch (e: CancellationException) {
            // 重新抛出取消异常，让协程正常取消
            throw e
        } catch (e: UnknownHostException) {
            Timber.e(e, "Network error")
            ApiResult.Error(AppException.NetworkError())
        } catch (e: SocketTimeoutException) {
            Timber.e(e, "Timeout error")
            ApiResult.Error(AppException.TimeoutError())
        } catch (e: IOException) {
            Timber.e(e, "IO error")
            ApiResult.Error(AppException.NetworkError("网络连接异常"))
        } catch (e: HttpException) {
            Timber.e(e, "HTTP error: ${e.code()}")
            handleHttpException(e)
        } catch (e: Exception) {
            Timber.e(e, "Unknown error")
            ApiResult.Error(AppException.UnknownError(e.message ?: "未知错误"))
        }
    }
}

/**
 * 处理 HTTP 异常
 * 特别处理 401 Unauthorized 错误
 */
private fun <T> handleHttpException(e: HttpException): ApiResult<T> {
    val code = e.code()
    // 尝试获取服务器返回的错误信息
    val errorBody = e.response()?.errorBody()?.string()
    val serverMessage = parseServerErrorMessage(errorBody) ?: e.message
    val message = serverMessage ?: "服务器错误"

    Timber.e("HTTP $code error: $message, errorBody: $errorBody")

    return when (code) {
        401 -> {
            Timber.w("Token expired or invalid (401), clearing token and triggering re-login")
            // 发送 401 事件，通知应用层处理
            AuthEventManager.postAuthEvent(AuthEvent.Unauthorized)
            ApiResult.Error(
                AppException.Unauthorized(
                    message = "登录已过期，请重新登录",
                    originalCode = code
                )
            )
        }
        403 -> {
            // 403 Forbidden - 权限不足
            ApiResult.Error(AppException.BusinessError(code, "权限不足"))
        }
        404 -> {
            ApiResult.Error(AppException.NotFound())
        }
        in 500..599 -> {
            ApiResult.Error(AppException.ServerError(code, message))
        }
        else -> {
            ApiResult.Error(AppException.BusinessError(code, message))
        }
    }
}

/**
 * 解析服务器错误信息
 */
private fun parseServerErrorMessage(errorBody: String?): String? {
    return try {
        errorBody?.let {
            // 尝试解析 JSON 错误响应
            val jsonObject = org.json.JSONObject(it)
            jsonObject.optString("msg", null)
                ?: jsonObject.optString("message", null)
                ?: jsonObject.optString("error", null)
        }
    } catch (e: Exception) {
        // 如果不是 JSON 格式，返回原始内容
        errorBody?.takeIf { it.isNotBlank() }
    }
}
