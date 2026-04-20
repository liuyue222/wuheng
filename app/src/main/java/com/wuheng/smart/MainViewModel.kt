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
 *
 * @param tokenManager Token 管理器，用于检查登录状态
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
}
