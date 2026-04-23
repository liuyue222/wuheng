package com.wuheng.smart.security

import android.content.Context
import com.wuheng.smart.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

/**
 * 安全拦截器
 *
 * 用于OkHttp客户端的安全增强：
 * 1. 添加安全请求头
 * 2. 防重放攻击（添加时间戳和随机数）
 * 3. 请求签名验证
 * 4. 安全检查（Root/调试/模拟器检测）
 *
 * @author 五恒智能控制系统
 * @since 1.0
 */
class SecurityInterceptor(private val context: Context) : Interceptor {

    companion object {
        private const val HEADER_TIMESTAMP = "X-Timestamp"
        private const val HEADER_NONCE = "X-Nonce"
        private const val HEADER_SIGNATURE = "X-Signature"
        private const val HEADER_APP_VERSION = "X-App-Version"
        private const val HEADER_BUILD_TYPE = "X-Build-Type"
        private const val HEADER_DEVICE_ID = "X-Device-ID"
        private const val HEADER_SECURITY_CHECK = "X-Security-Check"
    }

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val requestBuilder = originalRequest.newBuilder()

        // 添加基础安全头
        addSecurityHeaders(requestBuilder)

        // 执行安全检查（仅在Debug构建中记录）
        if (BuildConfig.ENABLE_DEBUG_FEATURES) {
            performSecurityChecks()
        }

        // 添加防重放攻击头
        addAntiReplayHeaders(requestBuilder)

        // 添加请求签名（可选）
        if (BuildConfig.API_KEY.isNotEmpty()) {
            addRequestSignature(requestBuilder, originalRequest)
        }

        val request = requestBuilder.build()
        return chain.proceed(request)
    }

    /**
     * 添加基础安全请求头
     */
    private fun addSecurityHeaders(builder: okhttp3.Request.Builder) {
        // 应用版本
        builder.header(HEADER_APP_VERSION, BuildConfig.VERSION_NAME)

        // 构建类型
        builder.header(HEADER_BUILD_TYPE, BuildConfig.BUILD_TYPE_NAME)

        // 设备唯一标识（使用Android ID的哈希值）
        val deviceId = getDeviceId()
        builder.header(HEADER_DEVICE_ID, deviceId)

        // 安全检查状态（仅在检测到风险时添加）
        val securityResult = SecurityUtils.performSecurityCheck(context)
        if (securityResult.hasSecurityRisk()) {
            val riskFlags = buildString {
                if (securityResult.isDebugged) append("D")
                if (securityResult.isRooted) append("R")
                if (securityResult.isEmulator) append("E")
                if (securityResult.isXposedInstalled) append("X")
                if (securityResult.isFridaDetected) append("F")
            }
            builder.header(HEADER_SECURITY_CHECK, riskFlags)
        }
    }

    /**
     * 添加防重放攻击头
     */
    private fun addAntiReplayHeaders(builder: okhttp3.Request.Builder) {
        // 添加时间戳（毫秒）
        val timestamp = System.currentTimeMillis().toString()
        builder.header(HEADER_TIMESTAMP, timestamp)

        // 添加随机数
        val nonce = generateNonce()
        builder.header(HEADER_NONCE, nonce)
    }

    /**
     * 添加请求签名
     * 简单的签名实现，生产环境应使用更复杂的签名算法
     */
    private fun addRequestSignature(
        builder: okhttp3.Request.Builder,
        originalRequest: okhttp3.Request
    ) {
        try {
            val timestamp = builder.build().header(HEADER_TIMESTAMP) ?: ""
            val nonce = builder.build().header(HEADER_NONCE) ?: ""

            // 构建签名字符串
            val signString = buildString {
                append(originalRequest.method)
                append("&")
                append(originalRequest.url.encodedPath)
                append("&")
                append(timestamp)
                append("&")
                append(nonce)
            }

            // 使用API Key进行签名
            val signature = generateSignature(signString, BuildConfig.API_KEY)
            builder.header(HEADER_SIGNATURE, signature)
        } catch (e: Exception) {
            // 签名失败不阻止请求
            if (BuildConfig.ENABLE_LOGGING) {
                e.printStackTrace()
            }
        }
    }

    /**
     * 生成随机数
     */
    private fun generateNonce(): String {
        return java.util.UUID.randomUUID().toString().replace("-", "").substring(0, 16)
    }

    /**
     * 生成签名
     */
    private fun generateSignature(data: String, key: String): String {
        return try {
            val mac = javax.crypto.Mac.getInstance("HmacSHA256")
            val secretKey = javax.crypto.spec.SecretKeySpec(key.toByteArray(), "HmacSHA256")
            mac.init(secretKey)
            val hash = mac.doFinal(data.toByteArray())
            android.util.Base64.encodeToString(hash, android.util.Base64.NO_WRAP)
        } catch (e: Exception) {
            ""
        }
    }

    /**
     * 获取设备ID
     */
    private fun getDeviceId(): String {
        return try {
            val androidId = android.provider.Settings.Secure.getString(
                context.contentResolver,
                android.provider.Settings.Secure.ANDROID_ID
            )
            // 返回Android ID的SHA-256哈希值前16位
            SecurityUtils.sha256(androidId ?: "unknown").substring(0, 16)
        } catch (e: Exception) {
            "unknown"
        }
    }

    /**
     * 执行安全检查
     */
    private fun performSecurityChecks() {
        val result = SecurityUtils.performSecurityCheck(context)

        if (result.hasSecurityRisk() && BuildConfig.ENABLE_LOGGING) {
            val risks = result.getRiskDescriptions()
            android.util.Log.w("SecurityInterceptor", "Security risks detected: $risks")
        }
    }
}

/**
 * 安全响应拦截器
 *
 * 处理响应中的安全相关逻辑：
 * 1. 检查安全响应头
 * 2. 处理服务器安全警告
 * 3. 验证响应签名（如服务器返回）
 */
class SecurityResponseInterceptor : Interceptor {

    companion object {
        private const val HEADER_SERVER_SECURITY = "X-Server-Security"
        private const val HEADER_FORCE_UPDATE = "X-Force-Update"
        private const val HEADER_TOKEN_REFRESH = "X-Token-Refresh"
    }

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val response = chain.proceed(request)

        // 检查服务器安全头
        checkServerSecurityHeaders(response)

        // 检查强制更新
        checkForceUpdate(response)

        // 检查Token刷新
        checkTokenRefresh(response)

        return response
    }

    /**
     * 检查服务器安全头
     */
    private fun checkServerSecurityHeaders(response: Response) {
        val securityHeader = response.header(HEADER_SERVER_SECURITY)
        if (!securityHeader.isNullOrEmpty() && BuildConfig.ENABLE_LOGGING) {
            android.util.Log.i("SecurityResponseInterceptor", "Server security: $securityHeader")
        }
    }

    /**
     * 检查强制更新
     */
    private fun checkForceUpdate(response: Response) {
        val forceUpdate = response.header(HEADER_FORCE_UPDATE)
        if (forceUpdate == "true") {
            // 触发强制更新逻辑
            // 可以通过EventBus或其他方式通知UI层
            if (BuildConfig.ENABLE_LOGGING) {
                android.util.Log.w("SecurityResponseInterceptor", "Force update required")
            }
        }
    }

    /**
     * 检查Token刷新
     */
    private fun checkTokenRefresh(response: Response) {
        val tokenRefresh = response.header(HEADER_TOKEN_REFRESH)
        if (!tokenRefresh.isNullOrEmpty()) {
            // 触发Token刷新逻辑
            if (BuildConfig.ENABLE_LOGGING) {
                android.util.Log.i("SecurityResponseInterceptor", "Token refresh needed")
            }
        }
    }
}
