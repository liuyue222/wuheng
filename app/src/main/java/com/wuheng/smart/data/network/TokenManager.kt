package com.wuheng.smart.data.network

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Token管理器 - 使用StateFlow作为单一数据源，DataStore进行持久化存储
 *
 * 功能：
 * 1. 内存级Token存储（同步读写，避免拦截器中使用runBlocking导致的线程阻塞）
 * 2. Token状态监听（通过StateFlow观察Token变化）
 * 3. 用户信息缓存（登录后保存用户基本信息）
 * 4. 当前选中房屋ID管理
 * 5. 持久化存储（应用重启后Token不丢失）
 * 6. 记住密码功能（安全存储登录凭证）
 */
@Singleton
open class TokenManager @Inject constructor(
    @ApplicationContext private val context: Context
) {

    companion object {
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "token_prefs")

        // DataStore Keys
        private val KEY_TOKEN = stringPreferencesKey("token")
        private val KEY_USER_ID = stringPreferencesKey("user_id")
        private val KEY_USER_NAME = stringPreferencesKey("user_name")
        private val KEY_USER_TYPE = stringPreferencesKey("user_type")
        private val KEY_CURRENT_HOUSE_ID = stringPreferencesKey("current_house_id")
        
        // 记住密码相关 Keys
        private val KEY_SAVED_PHONE = stringPreferencesKey("saved_phone")
        private val KEY_SAVED_PASSWORD = stringPreferencesKey("saved_password")
        private val KEY_REMEMBER_PASSWORD = booleanPreferencesKey("remember_password")

        // 主题设置 Keys
        private val KEY_DARK_MODE = booleanPreferencesKey("dark_mode")
        private val KEY_SYSTEM_THEME = booleanPreferencesKey("system_theme")

        // 语言设置 Key
        private val KEY_LANGUAGE = stringPreferencesKey("language")
    }

    private val dataStore = context.dataStore
    private val coroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    // ==================== Token管理 ====================

    private val _tokenFlow = MutableStateFlow("")
    val tokenFlow: StateFlow<String> = _tokenFlow.asStateFlow()

    init {
        // 初始化时从DataStore读取保存的数据
        coroutineScope.launch {
            try {
                // 使用first()只读取一次，避免collect导致的持续监听
                val preferences = dataStore.data.first()
                _tokenFlow.value = preferences[KEY_TOKEN] ?: ""
                _userId.value = preferences[KEY_USER_ID] ?: ""
                _userName.value = preferences[KEY_USER_NAME] ?: ""
                _userType.value = preferences[KEY_USER_TYPE] ?: ""
                _currentHouseId.value = preferences[KEY_CURRENT_HOUSE_ID] ?: ""
            } catch (e: ClassCastException) {
                // 类型转换错误：可能是之前存储了Int类型，现在用String类型读取
                // 清除所有数据，让用户重新登录
                clearAllData()
            } catch (e: Exception) {
                // 其他错误也清除数据
                clearAllData()
            }
        }
    }

    /**
     * 清除所有数据（包括内存和持久化存储）
     */
    private fun clearAllData() {
        _tokenFlow.value = ""
        _userId.value = ""
        _userName.value = ""
        _userType.value = ""
        _currentHouseId.value = ""
        coroutineScope.launch {
            dataStore.edit { it.clear() }
        }
    }

    /**
     * 同步获取当前Token（用于拦截器）
     * 从内存直接读取，避免线程阻塞
     */
    fun getToken(): String = _tokenFlow.value

    /**
     * 同步设置Token，同时持久化到DataStore
     */
    fun setToken(newToken: String) {
        _tokenFlow.value = newToken
        // 异步保存到DataStore
        coroutineScope.launch {
            dataStore.edit { preferences ->
                preferences[KEY_TOKEN] = newToken
            }
        }
    }

    /**
     * 清除Token，同时清除持久化存储
     */
    fun clearToken() {
        _tokenFlow.value = ""
        // 清除Token时同时清除用户信息和持久化存储
        clearUserInfo()
        coroutineScope.launch {
            dataStore.edit { preferences ->
                preferences.remove(KEY_TOKEN)
            }
        }
    }

    /**
     * 检查是否有有效的Token
     */
    fun hasToken(): Boolean = _tokenFlow.value.isNotEmpty()

    // ==================== 用户信息管理 ====================

    private val _userId = MutableStateFlow("")
    val userId: StateFlow<String> = _userId.asStateFlow()

    private val _userName = MutableStateFlow("")
    val userName: StateFlow<String> = _userName.asStateFlow()

    private val _userType = MutableStateFlow("")
    val userType: StateFlow<String> = _userType.asStateFlow()

    /**
     * 设置用户信息，同时持久化到DataStore
     */
    fun setUserInfo(userId: String, userName: String, userType: String) {
        _userId.value = userId
        _userName.value = userName
        _userType.value = userType
        // 异步保存到DataStore
        coroutineScope.launch {
            dataStore.edit { preferences ->
                preferences[KEY_USER_ID] = userId
                preferences[KEY_USER_NAME] = userName
                preferences[KEY_USER_TYPE] = userType
            }
        }
    }

    /**
     * 清除用户信息，同时清除持久化存储
     */
    fun clearUserInfo() {
        _userId.value = ""
        _userName.value = ""
        _userType.value = ""
        _currentHouseId.value = ""
        coroutineScope.launch {
            dataStore.edit { preferences ->
                preferences.remove(KEY_USER_ID)
                preferences.remove(KEY_USER_NAME)
                preferences.remove(KEY_USER_TYPE)
                preferences.remove(KEY_CURRENT_HOUSE_ID)
            }
        }
    }

    // ==================== 当前房屋管理 ====================

    private val _currentHouseId = MutableStateFlow("")
    val currentHouseId: StateFlow<String> = _currentHouseId.asStateFlow()

    /**
     * 设置当前选中的房屋ID，同时持久化到DataStore
     */
    fun setCurrentHouseId(houseId: String) {
        _currentHouseId.value = houseId
        // 异步保存到DataStore
        coroutineScope.launch {
            dataStore.edit { preferences ->
                preferences[KEY_CURRENT_HOUSE_ID] = houseId
            }
        }
    }

    /**
     * 获取当前房屋ID（同步）
     */
    open fun getCurrentHouseId(): String = _currentHouseId.value

    /**
     * 检查是否已选择房屋
     */
    fun hasSelectedHouse(): Boolean = _currentHouseId.value.isNotEmpty()

    // ==================== 记住密码功能 ====================

    /**
     * 保存登录凭证（记住密码）
     * 注意：实际项目中应该对密码进行加密存储
     *
     * @param phone 手机号
     * @param password 密码
     */
    suspend fun saveLoginCredentials(phone: String, password: String) {
        dataStore.edit { preferences ->
            preferences[KEY_SAVED_PHONE] = phone
            // 注意：这里仅作演示，实际应该加密存储
            preferences[KEY_SAVED_PASSWORD] = password
            preferences[KEY_REMEMBER_PASSWORD] = true
        }
    }

    /**
     * 清除保存的登录凭证
     */
    suspend fun clearLoginCredentials() {
        dataStore.edit { preferences ->
            preferences.remove(KEY_SAVED_PHONE)
            preferences.remove(KEY_SAVED_PASSWORD)
            preferences[KEY_REMEMBER_PASSWORD] = false
        }
    }

    /**
     * 获取保存的手机号
     */
    fun getSavedPhone(): Flow<String> {
        return dataStore.data.map { preferences ->
            preferences[KEY_SAVED_PHONE] ?: ""
        }
    }

    /**
     * 获取保存的密码
     */
    fun getSavedPassword(): Flow<String> {
        return dataStore.data.map { preferences ->
            preferences[KEY_SAVED_PASSWORD] ?: ""
        }
    }

    /**
     * 检查是否启用了记住密码
     */
    fun isRememberPassword(): Flow<Boolean> {
        return dataStore.data.map { preferences ->
            preferences[KEY_REMEMBER_PASSWORD] ?: false
        }
    }

    /**
     * 检查是否有保存的登录凭证
     */
    suspend fun hasSavedCredentials(): Boolean {
        val preferences = dataStore.data.first()
        return preferences[KEY_REMEMBER_PASSWORD] == true &&
                !preferences[KEY_SAVED_PHONE].isNullOrEmpty()
    }

    // ==================== 便捷方法 ====================

    /**
     * 登录成功后保存所有用户信息
     */
    fun onLoginSuccess(token: String, userId: String, userName: String, userType: String, houseId: String) {
        setToken(token)
        setUserInfo(userId, userName, userType)
        setCurrentHouseId(houseId)
    }

    /**
     * 登出时清除所有信息
     */
    fun onLogout() {
        clearToken()
    }

    // ==================== 主题设置 ====================

    private val _darkMode = MutableStateFlow(false)
    val darkMode: StateFlow<Boolean> = _darkMode.asStateFlow()

    private val _systemTheme = MutableStateFlow(true)
    val systemTheme: StateFlow<Boolean> = _systemTheme.asStateFlow()

    init {
        // 初始化时从DataStore读取主题设置
        coroutineScope.launch {
            try {
                val preferences = dataStore.data.first()
                _darkMode.value = preferences[KEY_DARK_MODE] ?: false
                _systemTheme.value = preferences[KEY_SYSTEM_THEME] ?: true
            } catch (e: Exception) {
                // 使用默认值
                _darkMode.value = false
                _systemTheme.value = true
            }
        }
    }

    /**
     * 设置深色模式
     */
    fun setDarkMode(enabled: Boolean) {
        _darkMode.value = enabled
        coroutineScope.launch {
            dataStore.edit { preferences ->
                preferences[KEY_DARK_MODE] = enabled
            }
        }
    }

    /**
     * 设置是否跟随系统主题
     */
    fun setSystemTheme(enabled: Boolean) {
        _systemTheme.value = enabled
        coroutineScope.launch {
            dataStore.edit { preferences ->
                preferences[KEY_SYSTEM_THEME] = enabled
            }
        }
    }

    /**
     * 获取深色模式设置Flow
     */
    fun getDarkModeFlow(): Flow<Boolean> {
        return dataStore.data.map { preferences ->
            preferences[KEY_DARK_MODE] ?: false
        }
    }

    /**
     * 获取系统主题设置Flow
     */
    fun getSystemThemeFlow(): Flow<Boolean> {
        return dataStore.data.map { preferences ->
            preferences[KEY_SYSTEM_THEME] ?: true
        }
    }

    // ==================== 语言设置 ====================

    private val _language = MutableStateFlow("zh")
    val language: StateFlow<String> = _language.asStateFlow()

    init {
        // 初始化时从DataStore读取语言设置
        coroutineScope.launch {
            try {
                val preferences = dataStore.data.first()
                _language.value = preferences[KEY_LANGUAGE] ?: "zh"
            } catch (e: Exception) {
                _language.value = "zh"
            }
        }
    }

    /**
     * 设置语言
     * @param languageCode 语言代码: "zh" - 中文, "en" - 英文
     */
    fun setLanguage(languageCode: String) {
        _language.value = languageCode
        coroutineScope.launch {
            dataStore.edit { preferences ->
                preferences[KEY_LANGUAGE] = languageCode
            }
        }
    }

    /**
     * 获取语言设置Flow
     */
    fun getLanguageFlow(): Flow<String> {
        return dataStore.data.map { preferences ->
            preferences[KEY_LANGUAGE] ?: "zh"
        }
    }
}
