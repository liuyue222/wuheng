package com.wuheng.smart.data.network

import okhttp3.Interceptor
import okhttp3.Response
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

/**
 * 认证拦截器
 *
 * 功能：
 * 1. 为每个请求添加 Authorization Header
 * 2. 拦截 401 响应，清除 Token 并发送认证事件
 * 3. 支持自定义 Content-Type 和 Accept Header
 */
@Singleton
class AuthInterceptor @Inject constructor(
    private val tokenManager: TokenManager
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()

        // 同步获取token（从内存缓存读取，避免runBlocking阻塞线程）
        val token = tokenManager.getToken()

        val requestBuilder = originalRequest.newBuilder()
            .header("Content-Type", "application/json")
            .header("Accept", "application/json")

        if (token.isNotEmpty()) {
            requestBuilder.header("Authorization", "Bearer $token")
        }

        val request = requestBuilder.build()
        val response = chain.proceed(request)

        // 检查 401 响应
        if (response.code == 401) {
            handleUnauthorizedResponse()
        }

        return response
    }

    /**
     * 处理 401 响应
     * 清除 Token 并发送认证事件
     */
    private fun handleUnauthorizedResponse() {
        Timber.w("AuthInterceptor: Received 401 response, clearing token and posting auth event")

        // 清除当前 Token
        tokenManager.clearToken()

        // 发送 401 事件，通知 UI 层跳转到登录页
        AuthEventManager.postAuthEvent(AuthEvent.Unauthorized)
    }
}
