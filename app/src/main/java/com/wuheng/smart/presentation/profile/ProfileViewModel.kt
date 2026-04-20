package com.wuheng.smart.presentation.profile

import androidx.lifecycle.viewModelScope
import com.wuheng.smart.data.network.ApiResult
import com.wuheng.smart.data.network.TokenManager
import com.wuheng.smart.data.repository.UserRepository
import com.wuheng.smart.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

/**
 * 个人中心 ViewModel
 *
 * 职责：
 * 1. 管理个人中心所有UI状态
 * 2. 处理用户交互事件
 * 3. 调用API获取用户数据
 */
@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val tokenManager: TokenManager,
    private val userRepository: UserRepository
) : BaseViewModel() {

    // ==================== UI State ====================

    private val _uiState = MutableStateFlow(ProfileUiState())
    val uiState: StateFlow<ProfileUiState> = _uiState.asStateFlow()

    // ==================== 初始化 ====================

    init {
        loadUserInfo()
    }

    // ==================== 数据加载方法 ====================

    /**
     * 加载用户信息
     * 从API获取用户详细信息并更新UI
     */
    private fun loadUserInfo() {
        viewModelScope.launch {
            // 设置加载状态
            _uiState.value = _uiState.value.copy(isLoading = true, errorMessage = null)

            userRepository.getUserInfo().collect { result ->
                when (result) {
                    is ApiResult.Success -> {
                        val userInfo = result.data
                        _uiState.value = _uiState.value.copy(
                            isLoading = false,
                            errorMessage = null,
                            userName = userInfo.userName,
                            // 根据userType设置角色名称
                            role = when (userInfo.userType) {
                                1 -> "业主"
                                2 -> "租户"
                                3 -> "管理员"
                                else -> "用户"
                            }
                            // residenceName、hasNotification等字段需要其他API获取
                            // 或者从UserInfo中扩展字段
                        )
                        Timber.d("加载用户信息成功: userId=${userInfo.userId}, userName=${userInfo.userName}")
                    }
                    is ApiResult.Error -> {
                        _uiState.value = _uiState.value.copy(
                            isLoading = false,
                            errorMessage = result.exception.message ?: "获取用户信息失败"
                        )
                        Timber.e("加载用户信息失败: ${result.exception.message}")
                    }
                    is ApiResult.Loading -> {
                        _uiState.value = _uiState.value.copy(isLoading = true)
                    }
                }
            }
        }
    }

    /**
     * 刷新所有数据
     * 重新加载用户信息和相关数据
     */
    fun refresh() {
        Timber.d("开始刷新个人中心数据")
        loadUserInfo()
        // TODO: 可以同时加载其他数据，如房屋信息、未读消息数等
        // loadHouseInfo()
        // loadUnreadCount()
    }

    /**
     * 刷新数据（供Layout调用）
     */
    fun refreshData() {
        refresh()
    }

    /**
     * 重试加载数据
     * 在加载失败时调用
     */
    fun retry() {
        Timber.d("重试加载用户数据")
        loadUserInfo()
    }

    /**
     * 清除错误信息
     */
    fun clearError() {
        _uiState.value = _uiState.value.copy(errorMessage = null)
    }

    // ==================== UI交互方法 ====================

    /**
     * 预约服务
     */
    fun onBookService() {
        // 实现预约服务逻辑
        Timber.d("用户点击预约服务")
    }

    /**
     * 用户登出
     */
    fun logout() {
        tokenManager.onLogout()
        Timber.d("用户登出")
    }
}

/**
 * 个人中心 UI State
 */
data class ProfileUiState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val userName: String = "",
    val residenceName: String = "",
    val role: String = "",
    val hasNotification: Boolean = false,
    val projectDescription: String = "",
    val lastServiceDate: String = "",
    val version: String = ""
)
