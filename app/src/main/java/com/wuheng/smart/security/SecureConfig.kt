package com.wuheng.smart.security

import com.wuheng.smart.BuildConfig

/**
 * 安全配置常量
 *
 * 集中管理应用的安全相关配置：
 * - API密钥（从BuildConfig获取，避免硬编码）
 * - 加密密钥
 * - 安全阈值
 * - 安全策略开关
 *
 * @author 五恒智能控制系统
 * @since 1.0
 */
object SecureConfig {

    // ==================== API配置 ====================

    /**
     * API基础URL
     */
    val BASE_URL: String
        get() = BuildConfig.BASE_URL

    /**
     * API密钥
     * 从BuildConfig获取，实际值存储在环境变量或local.properties
     */
    val API_KEY: String
        get() = BuildConfig.API_KEY

    // ==================== 加密配置 ====================

    /**
     * 加密密钥
     * 用于AES-256加密敏感数据
     */
    val ENCRYPTION_KEY: String
        get() = BuildConfig.ENCRYPTION_KEY

    /**
     * Token过期时间（毫秒）
     * 默认7天
     */
    const val TOKEN_EXPIRATION_MS = 7 * 24 * 60 * 60 * 1000L

    /**
     * Token刷新阈值（毫秒）
     * 在过期前1小时尝试刷新
     */
    const val TOKEN_REFRESH_THRESHOLD_MS = 60 * 60 * 1000L

    // ==================== 安全策略开关 ====================

    /**
     * 是否启用Root检测
     */
    const val ENABLE_ROOT_DETECTION = true

    /**
     * 是否启用模拟器检测
     */
    const val ENABLE_EMULATOR_DETECTION = true

    /**
     * 是否启用Xposed检测
     */
    const val ENABLE_XPOSED_DETECTION = true

    /**
     * 是否启用Frida检测
     */
    const val ENABLE_FRIDA_DETECTION = true

    /**
     * 是否启用调试检测
     */
    const val ENABLE_DEBUG_DETECTION = true

    /**
     * 检测到安全风险时的行为
     * true: 阻止应用运行
     * false: 仅记录日志
     */
    const val BLOCK_ON_SECURITY_RISK = false

    // ==================== 网络安全配置 ====================

    /**
     * 连接超时时间（秒）
     */
    const val CONNECT_TIMEOUT_SECONDS = 30

    /**
     * 读取超时时间（秒）
     */
    const val READ_TIMEOUT_SECONDS = 30

    /**
     * 写入超时时间（秒）
     */
    const val WRITE_TIMEOUT_SECONDS = 30

    /**
     * 最大重试次数
     */
    const val MAX_RETRY_COUNT = 3

    /**
     * 是否启用证书固定
     */
    const val ENABLE_CERTIFICATE_PINNING = false

    // ==================== 日志配置 ====================

    /**
     * 是否启用安全日志
     * Release构建应禁用
     */
    val ENABLE_SECURITY_LOGGING: Boolean
        get() = BuildConfig.ENABLE_LOGGING && BuildConfig.ENABLE_DEBUG_FEATURES

    // ==================== 签名验证配置 ====================

    /**
     * 预期的应用签名SHA-256哈希值
     * 用于验证应用完整性，防止重新打包
     *
     * 注意：需要替换为实际的应用签名哈希
     * 可以使用以下命令获取：
     * keytool -list -v -keystore your-keystore.jks -alias your-alias
     */
    const val EXPECTED_SIGNATURE_HASH = "YOUR_APP_SIGNATURE_SHA256_HASH_HERE"

    // ==================== 安全字符串（混淆） ====================

    /**
     * 获取混淆后的API密钥
     * 使用简单的XOR混淆，增加静态分析难度
     */
    fun getObfuscatedApiKey(): String {
        // 实际API密钥应在构建时注入
        return SecurityUtils.obfuscate(API_KEY)
    }

    /**
     * 获取解混淆后的API密钥
     */
    fun getDeobfuscatedApiKey(): String {
        return SecurityUtils.deobfuscate(getObfuscatedApiKey())
    }

    // ==================== 安全检查配置 ====================

    /**
     * 最大登录失败次数
     */
    const val MAX_LOGIN_ATTEMPTS = 5

    /**
     * 登录失败锁定时间（毫秒）
     */
    const val LOGIN_LOCKOUT_DURATION_MS = 15 * 60 * 1000L

    /**
     * 最大密码长度
     */
    const val MAX_PASSWORD_LENGTH = 128

    /**
     * 最小密码长度
     */
    const val MIN_PASSWORD_LENGTH = 6

    // ==================== 安全配置验证 ====================

    /**
     * 验证安全配置
     * 在应用启动时调用，确保配置正确
     *
     * @return 验证结果
     */
    fun validateConfig(): ConfigValidationResult {
        val issues = mutableListOf<String>()

        // 检查API密钥
        if (API_KEY.isEmpty()) {
            issues.add("API密钥未配置")
        }

        // 检查加密密钥
        if (ENCRYPTION_KEY.isEmpty() || ENCRYPTION_KEY == "YOUR_ENCRYPTION_KEY_HERE") {
            issues.add("加密密钥未配置或使用默认值")
        }

        // 检查签名哈希
        if (EXPECTED_SIGNATURE_HASH == "YOUR_APP_SIGNATURE_SHA256_HASH_HERE") {
            issues.add("应用签名哈希未配置")
        }

        // 检查是否为Debug构建
        if (BuildConfig.ENABLE_DEBUG_FEATURES) {
            issues.add("当前为Debug构建，部分安全功能已禁用")
        }

        return ConfigValidationResult(
            isValid = issues.isEmpty(),
            issues = issues
        )
    }

    /**
     * 配置验证结果
     */
    data class ConfigValidationResult(
        val isValid: Boolean,
        val issues: List<String>
    ) {
        fun getIssuesDescription(): String {
            return if (issues.isEmpty()) {
                "配置验证通过"
            } else {
                "配置问题:\n${issues.joinToString("\n") { "- $it" }}"
            }
        }
    }
}
