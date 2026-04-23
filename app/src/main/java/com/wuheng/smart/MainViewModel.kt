package com.wuheng.smart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wuheng.smart.data.network.TokenManager
import com.wuheng.smart.navigation.NavigationRoutes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

/**
 * MainActivity 的 ViewModel
 *
 * 职责：
 * 1. 管理应用登录状态
 * 2. 根据登录状态决定起始页面
 * 3. 监听 Token 变化并更新登录状态
 * 4. 管理主题设置（深色模式/系统主题）
 *
 * @param tokenManager Token 管理器，用于检查登录状态和主题设置
 */
@HiltViewModel
class MainViewModel @Inject constructor(
    private val tokenManager: TokenManager
) : ViewModel() {

    /**
     * 登录状态
     */
    private val _isLoggedIn = MutableStateFlow(false)
    val isLoggedIn: StateFlow<Boolean> = _isLoggedIn.asStateFlow()

    /**
     * 起始页面路由
     */
    private val _startDestination = MutableStateFlow(NavigationRoutes.LOGIN)
    val startDestination: StateFlow<String> = _startDestination.asStateFlow()

    /**
     * 深色模式设置
     */
    val darkMode: StateFlow<Boolean> = tokenManager.darkMode

    /**
     * 系统主题设置
     */
    val systemTheme: StateFlow<Boolean> = tokenManager.systemTheme

    init {
        // 初始化时检查登录状态
        checkLoginStatus()

        // 监听 Token 变化
        tokenManager.tokenFlow
            .onEach { token ->
                val loggedIn = token.isNotEmpty()
                _isLoggedIn.value = loggedIn
                _startDestination.value = if (loggedIn) NavigationRoutes.HOME else NavigationRoutes.LOGIN
                Timber.d("Token changed, isLoggedIn=$loggedIn, startDestination=${_startDestination.value}")
            }
            .launchIn(viewModelScope)
    }

    /**
     * 检查当前登录状态
     */
    private fun checkLoginStatus() {
        val hasToken = tokenManager.hasToken()
        _isLoggedIn.value = hasToken
        _startDestination.value = if (hasToken) NavigationRoutes.HOME else NavigationRoutes.LOGIN
        Timber.d("Initial login status check: isLoggedIn=$hasToken")
    }

    /**
     * 设置深色模式
     */
    fun setDarkMode(enabled: Boolean) {
        tokenManager.setDarkMode(enabled)
        Timber.d("设置深色模式: $enabled")
    }

    /**
     * 设置是否跟随系统主题
     */
    fun setSystemTheme(enabled: Boolean) {
        tokenManager.setSystemTheme(enabled)
        Timber.d("设置跟随系统主题: $enabled")
    }

    /**
     * 语言设置
     */
    val language: StateFlow<String> = tokenManager.language

    /**
     * 设置语言
     * @param languageCode 语言代码: "zh" - 中文, "en" - 英文
     */
    fun setLanguage(languageCode: String) {
        tokenManager.setLanguage(languageCode)
        Timber.d("设置语言: $languageCode")
    }
}
