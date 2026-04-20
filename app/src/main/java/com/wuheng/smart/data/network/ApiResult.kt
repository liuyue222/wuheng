package com.wuheng.smart.data.network

sealed class ApiResult<out T> {
    data class Success<T>(val data: T) : ApiResult<T>()
    data class Error(val exception: AppException) : ApiResult<Nothing>()
    object Loading : ApiResult<Nothing>()
}

sealed class AppException : Exception() {
    data class NetworkError(override val message: String = "网络连接失败") : AppException()
    data class TimeoutError(override val message: String = "请求超时") : AppException()
    data class ServerError(val code: Int, override val message: String) : AppException()
    data class BusinessError(val code: Int, override val message: String) : AppException()
    data class NotFound(override val message: String = "资源不存在") : AppException()
    data class UnknownError(override val message: String = "未知错误") : AppException()
    
    /**
     * 401 Unauthorized - Token 过期或无效
     * 需要清除当前 Token 并跳转到登录页
     */
    data class Unauthorized(
        override val message: String = "登录已过期，请重新登录",
        val originalCode: Int = 401
    ) : AppException()
}

fun <T> ApiResult<T>.onSuccess(action: (T) -> Unit): ApiResult<T> {
    if (this is ApiResult.Success) action(data)
    return this
}

fun <T> ApiResult<T>.onError(action: (AppException) -> Unit): ApiResult<T> {
    if (this is ApiResult.Error) action(exception)
    return this
}

fun <T> ApiResult<T>.onLoading(action: () -> Unit): ApiResult<T> {
    if (this is ApiResult.Loading) action()
    return this
}

/**
 * 安全的API调用包装函数
 * 自动处理异常并转换为ApiResult
 */
suspend fun <T> safeApiCall(call: suspend () -> T): ApiResult<T> {
    return try {
        ApiResult.Success(call())
    } catch (e: Exception) {
        ApiResult.Error(handleApiException(e))
    }
}

/**
 * 处理API异常，转换为AppException
 */
fun handleApiException(e: Exception): AppException {
    return when (e) {
        is java.net.UnknownHostException,
        is java.net.ConnectException -> AppException.NetworkError()
        is java.net.SocketTimeoutException -> AppException.TimeoutError()
        is retrofit2.HttpException -> {
            when (e.code()) {
                404 -> AppException.NotFound()
                in 500..599 -> AppException.ServerError(e.code(), e.message ?: "服务器错误")
                else -> AppException.BusinessError(e.code(), e.message ?: "请求失败")
            }
        }
        else -> AppException.UnknownError(e.message ?: "未知错误")
    }
}
