package com.wuheng.smart.security

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.wuheng.smart.BuildConfig
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * 安全Token管理器
 *
 * 使用EncryptedSharedPreferences安全存储敏感数据：
 * - 用户Token
 * - 刷新Token
 * - 用户ID
 * - 其他敏感信息
 *
 * 相比普通SharedPreferences，提供以下安全增强：
 * 1. 使用AES-256加密存储的数据
 * 2. 使用Android Keystore系统保护加密密钥
 * 3. 防止Root设备上的数据提取
 * 4. 防止备份恢复攻击
 *
 * @author 五恒智能控制系统
 * @since 1.0
 */
class SecureTokenManager(context: Context) {

    companion object {
        private const val PREFS_FILE_NAME = "secure_tokens"

        // 存储Key（使用混淆后的字符串增加安全性）
        private const val KEY_TOKEN = "user_token_secure"
        private const val KEY_REFRESH_TOKEN = "refresh_token_secure"
        private const val KEY_USER_ID = "user_id_secure"
        private const val KEY_USER_NAME = "user_name_secure"
        private const val KEY_USER_PHONE = "user_phone_secure"
        private const val KEY_HOUSE_ID = "house_id_secure"
        private const val KEY_LOGIN_TIME = "login_time_secure"
        private const val KEY_TOKEN_EXPIRES = "token_expires_secure"

        // 主题设置Key
        private const val KEY_DARK_MODE = "dark_mode_setting"
        private const val KEY_SYSTEM_THEME = "system_theme_setting"
        private const val KEY_LANGUAGE = "language_setting"

        @Volatile
        private var instance: SecureTokenManager? = null

        fun getInstance(context: Context): SecureTokenManager {
            return instance ?: synchronized(this) {
                instance ?: SecureTokenManager(context.applicationContext).also {
                    instance = it
                }
            }
        }
    }

    private val masterKey: MasterKey by lazy {
        MasterKey.Builder(context)
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
            .build()
    }

    private val encryptedPrefs: EncryptedSharedPreferences by lazy {
        EncryptedSharedPreferences.create(
            context,
            PREFS_FILE_NAME,
            masterKey,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        ) as EncryptedSharedPreferences
    }

    // 普通SharedPreferences用于非敏感数据
    private val normalPrefs: SharedPreferences by lazy {
        context.getSharedPreferences("${PREFS_FILE_NAME}_normal", Context.MODE_PRIVATE)
    }

    // Token状态Flow
    private val _tokenFlow = MutableStateFlow<String?>(null)
    val tokenFlow: StateFlow<String?> = _tokenFlow.asStateFlow()

    // 登录状态Flow
    private val _isLoggedInFlow = MutableStateFlow(false)
    val isLoggedInFlow: StateFlow<Boolean> = _isLoggedInFlow.asStateFlow()

    // 主题设置Flow
    private val _darkModeFlow = MutableStateFlow(false)
    val darkModeFlow: StateFlow<Boolean> = _darkModeFlow.asStateFlow()

    private val _systemThemeFlow = MutableStateFlow(true)
    val systemThemeFlow: StateFlow<Boolean> = _systemThemeFlow.asStateFlow()

    // 语言设置Flow
    private val _languageFlow = MutableStateFlow("zh")
    val languageFlow: StateFlow<String> = _languageFlow.asStateFlow()

    init {
        // 初始化时加载Token
        _tokenFlow.value = getToken()
        _isLoggedInFlow.value = isLoggedIn()
        _darkModeFlow.value = getDarkMode()
        _systemThemeFlow.value = getSystemTheme()
        _languageFlow.value = getLanguage()
    }

    // ==================== Token管理 ====================

    /**
     * 保存用户Token（加密存储）
     *
     * @param token 用户Token
     */
    fun saveToken(token: String) {
        encryptedPrefs.edit().putString(KEY_TOKEN, token).apply()
        _tokenFlow.value = token
        _isLoggedInFlow.value = true
    }

    /**
     * 获取用户Token
     *
     * @return Token字符串，如果不存在返回null
     */
    fun getToken(): String? {
        return encryptedPrefs.getString(KEY_TOKEN, null)
    }

    /**
     * 保存刷新Token
     *
     * @param refreshToken 刷新Token
     */
    fun saveRefreshToken(refreshToken: String) {
        encryptedPrefs.edit().putString(KEY_REFRESH_TOKEN, refreshToken).apply()
    }

    /**
     * 获取刷新Token
     *
     * @return 刷新Token字符串，如果不存在返回null
     */
    fun getRefreshToken(): String? {
        return encryptedPrefs.getString(KEY_REFRESH_TOKEN, null)
    }

    /**
     * 清除所有Token（登出时使用）
     */
    fun clearTokens() {
        encryptedPrefs.edit().apply {
            remove(KEY_TOKEN)
            remove(KEY_REFRESH_TOKEN)
            remove(KEY_USER_ID)
            remove(KEY_USER_NAME)
            remove(KEY_USER_PHONE)
            remove(KEY_HOUSE_ID)
            remove(KEY_LOGIN_TIME)
            remove(KEY_TOKEN_EXPIRES)
            apply()
        }
        _tokenFlow.value = null
        _isLoggedInFlow.value = false
    }

    /**
     * 检查是否已登录
     *
     * @return true 如果存在有效Token
     */
    fun isLoggedIn(): Boolean {
        return !getToken().isNullOrEmpty()
    }

    // ==================== 用户信息管理 ====================

    /**
     * 保存用户ID
     *
     * @param userId 用户ID
     */
    fun saveUserId(userId: Int) {
        encryptedPrefs.edit().putInt(KEY_USER_ID, userId).apply()
    }

    /**
     * 获取用户ID
     *
     * @return 用户ID，如果不存在返回-1
     */
    fun getUserId(): Int {
        return encryptedPrefs.getInt(KEY_USER_ID, -1)
    }

    /**
     * 保存用户名
     *
     * @param userName 用户名
     */
    fun saveUserName(userName: String) {
        encryptedPrefs.edit().putString(KEY_USER_NAME, userName).apply()
    }

    /**
     * 获取用户名
     *
     * @return 用户名，如果不存在返回空字符串
     */
    fun getUserName(): String {
        return encryptedPrefs.getString(KEY_USER_NAME, "") ?: ""
    }

    /**
     * 保存用户手机号
     *
     * @param phone 手机号
     */
    fun saveUserPhone(phone: String) {
        encryptedPrefs.edit().putString(KEY_USER_PHONE, phone).apply()
    }

    /**
     * 获取用户手机号
     *
     * @return 手机号，如果不存在返回空字符串
     */
    fun getUserPhone(): String {
        return encryptedPrefs.getString(KEY_USER_PHONE, "") ?: ""
    }

    /**
     * 保存房屋ID
     *
     * @param houseId 房屋ID
     */
    fun saveHouseId(houseId: Int) {
        encryptedPrefs.edit().putInt(KEY_HOUSE_ID, houseId).apply()
    }

    /**
     * 获取房屋ID
     *
     * @return 房屋ID，如果不存在返回-1
     */
    fun getHouseId(): Int {
        return encryptedPrefs.getInt(KEY_HOUSE_ID, -1)
    }

    /**
     * 保存登录时间
     *
     * @param timestamp 登录时间戳
     */
    fun saveLoginTime(timestamp: Long) {
        encryptedPrefs.edit().putLong(KEY_LOGIN_TIME, timestamp).apply()
    }

    /**
     * 获取登录时间
     *
     * @return 登录时间戳，如果不存在返回0
     */
    fun getLoginTime(): Long {
        return encryptedPrefs.getLong(KEY_LOGIN_TIME, 0)
    }

    /**
     * 保存Token过期时间
     *
     * @param expiresIn 过期时间（秒）
     */
    fun saveTokenExpires(expiresIn: Long) {
        val expiresAt = System.currentTimeMillis() + (expiresIn * 1000)
        encryptedPrefs.edit().putLong(KEY_TOKEN_EXPIRES, expiresAt).apply()
    }

    /**
     * 获取Token过期时间
     *
     * @return 过期时间戳，如果不存在返回0
     */
    fun getTokenExpires(): Long {
        return encryptedPrefs.getLong(KEY_TOKEN_EXPIRES, 0)
    }

    /**
     * 检查Token是否过期
     *
     * @return true 如果Token已过期
     */
    fun isTokenExpired(): Boolean {
        val expiresAt = getTokenExpires()
        return expiresAt > 0 && System.currentTimeMillis() >= expiresAt
    }

    // ==================== 主题设置管理 ====================

    /**
     * 设置深色模式
     *
     * @param enabled 是否启用深色模式
     */
    fun setDarkMode(enabled: Boolean) {
        normalPrefs.edit().putBoolean(KEY_DARK_MODE, enabled).apply()
        _darkModeFlow.value = enabled
    }

    /**
     * 获取深色模式设置
     *
     * @return true 如果启用深色模式
     */
    fun getDarkMode(): Boolean {
        return normalPrefs.getBoolean(KEY_DARK_MODE, false)
    }

    /**
     * 设置是否跟随系统主题
     *
     * @param enabled 是否跟随系统主题
     */
    fun setSystemTheme(enabled: Boolean) {
        normalPrefs.edit().putBoolean(KEY_SYSTEM_THEME, enabled).apply()
        _systemThemeFlow.value = enabled
    }

    /**
     * 获取系统主题设置
     *
     * @return true 如果跟随系统主题
     */
    fun getSystemTheme(): Boolean {
        return normalPrefs.getBoolean(KEY_SYSTEM_THEME, true)
    }

    // ==================== 语言设置管理 ====================

    /**
     * 设置语言
     *
     * @param language 语言代码（如 "zh", "en"）
     */
    fun setLanguage(language: String) {
        normalPrefs.edit().putString(KEY_LANGUAGE, language).apply()
        _languageFlow.value = language
    }

    /**
     * 获取语言设置
     *
     * @return 语言代码，默认 "zh"
     */
    fun getLanguage(): String {
        return normalPrefs.getString(KEY_LANGUAGE, "zh") ?: "zh"
    }

    // ==================== 安全存储通用方法 ====================

    /**
     * 安全保存字符串值
     *
     * @param key 键
     * @param value 值
     */
    fun putSecureString(key: String, value: String) {
        encryptedPrefs.edit().putString(key, value).apply()
    }

    /**
     * 安全获取字符串值
     *
     * @param key 键
     * @param defaultValue 默认值
     * @return 存储的值或默认值
     */
    fun getSecureString(key: String, defaultValue: String? = null): String? {
        return encryptedPrefs.getString(key, defaultValue)
    }

    /**
     * 安全保存整数值
     *
     * @param key 键
     * @param value 值
     */
    fun putSecureInt(key: String, value: Int) {
        encryptedPrefs.edit().putInt(key, value).apply()
    }

    /**
     * 安全获取整数值
     *
     * @param key 键
     * @param defaultValue 默认值
     * @return 存储的值或默认值
     */
    fun getSecureInt(key: String, defaultValue: Int = 0): Int {
        return encryptedPrefs.getInt(key, defaultValue)
    }

    /**
     * 安全保存长整数值
     *
     * @param key 键
     * @param value 值
     */
    fun putSecureLong(key: String, value: Long) {
        encryptedPrefs.edit().putLong(key, value).apply()
    }

    /**
     * 安全获取长整数值
     *
     * @param key 键
     * @param defaultValue 默认值
     * @return 存储的值或默认值
     */
    fun getSecureLong(key: String, defaultValue: Long = 0): Long {
        return encryptedPrefs.getLong(key, defaultValue)
    }

    /**
     * 安全保存布尔值
     *
     * @param key 键
     * @param value 值
     */
    fun putSecureBoolean(key: String, value: Boolean) {
        encryptedPrefs.edit().putBoolean(key, value).apply()
    }

    /**
     * 安全获取布尔值
     *
     * @param key 键
     * @param defaultValue 默认值
     * @return 存储的值或默认值
     */
    fun getSecureBoolean(key: String, defaultValue: Boolean = false): Boolean {
        return encryptedPrefs.getBoolean(key, defaultValue)
    }

    /**
     * 安全删除值
     *
     * @param key 键
     */
    fun removeSecure(key: String) {
        encryptedPrefs.edit().remove(key).apply()
    }

    /**
     * 清除所有安全存储的数据
     * 注意：谨慎使用，会清除所有用户数据
     */
    fun clearAllSecureData() {
        encryptedPrefs.edit().clear().apply()
        _tokenFlow.value = null
        _isLoggedInFlow.value = false
    }

    // ==================== 调试和测试方法 ====================

    /**
     * 获取存储摘要信息（用于调试）
     * 注意：生产环境应移除此方法
     *
     * @return 存储摘要信息
     */
    fun getStorageSummary(): String {
        return if (BuildConfig.ENABLE_DEBUG_FEATURES) {
            buildString {
                appendLine("SecureTokenManager Storage Summary:")
                appendLine("- Token exists: ${getToken() != null}")
                appendLine("- User ID: ${getUserId()}")
                appendLine("- User Name: ${getUserName()}")
                appendLine("- House ID: ${getHouseId()}")
                appendLine("- Is Logged In: ${isLoggedIn()}")
                appendLine("- Token Expired: ${isTokenExpired()}")
                appendLine("- Dark Mode: ${getDarkMode()}")
                appendLine("- System Theme: ${getSystemTheme()}")
                appendLine("- Language: ${getLanguage()}")
            }
        } else {
            "Storage summary disabled in release build"
        }
    }
}
