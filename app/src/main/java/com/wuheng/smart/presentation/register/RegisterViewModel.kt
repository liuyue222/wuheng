package com.wuheng.smart.presentation.register

import androidx.lifecycle.viewModelScope
import com.wuheng.smart.data.model.RegisterRequest
import com.wuheng.smart.data.network.ApiResult
import com.wuheng.smart.data.network.AppException
import com.wuheng.smart.data.repository.UserRepository
import com.wuheng.smart.presentation.base.BaseViewModel
import com.wuheng.smart.presentation.base.UiDataState
import com.wuheng.smart.presentation.base.createUiStateFlow
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

/**
 * 注册页面 ViewModel
 *
 * 负责处理用户注册相关的业务逻辑，包括：
 * - 表单验证
 * - 注册请求
 * - 错误处理
 */
@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val userRepository: UserRepository
) : BaseViewModel() {

    // 注册状态
    private val _registerState = createUiStateFlow<Unit>()
    val registerState: StateFlow<UiDataState<Unit>> = _registerState.asStateFlow()

    // 验证错误信息（用于显示 Snackbar）
    private val _validationError = MutableStateFlow<String?>(null)
    val validationError: StateFlow<String?> = _validationError.asStateFlow()

    /**
     * 执行注册
     *
     * @param username 用户名
     * @param phone 手机号
     * @param password 密码
     * @param realName 真实姓名（可选）
     * @param email 邮箱（可选）
     */
    fun register(
        username: String,
        phone: String,
        password: String,
        realName: String = "",
        email: String = ""
    ) {
        // 前置验证
        val validationResult = validateRegisterForm(
            username = username,
            phone = phone,
            password = password,
            realName = realName,
            email = email
        )

        if (validationResult != null) {
            _validationError.value = validationResult
            return
        }

        // 执行注册请求
        viewModelScope.launch {
            _registerState.value = UiDataState.Loading

            val request = RegisterRequest(
                username = username,
                password = password,
                mobile = phone,
                realname = realName.takeIf { it.isNotBlank() },
                email = email.takeIf { it.isNotBlank() }
            )
            
            userRepository.register(request).collect { result ->
                when (result) {
                    is ApiResult.Success -> {
                        Timber.d("Registration successful for user: $username")
                        _registerState.value = UiDataState.Success(Unit)
                    }
                    is ApiResult.Error -> {
                        Timber.e("Registration failed: ${result.exception.message}")
                        _registerState.value = UiDataState.Error(result.exception)
                        _validationError.value = result.exception.message
                    }
                    is ApiResult.Loading -> {
                        _registerState.value = UiDataState.Loading
                    }
                }
            }
        }
    }

    /**
     * 验证注册表单
     *
     * @return 错误信息，如果验证通过返回 null
     */
    private fun validateRegisterForm(
        username: String,
        phone: String,
        password: String,
        realName: String,
        email: String
    ): String? {
        // 验证用户名
        if (username.isBlank()) {
            return "请输入用户名"
        }
        if (username.length < 3) {
            return "用户名至少3个字符"
        }

        // 验证手机号
        if (phone.isBlank()) {
            return "请输入手机号"
        }
        if (!phone.matches(Regex("^1[3-9]\\d{9}$"))) {
            return "请输入正确的11位手机号"
        }

        // 验证密码
        if (password.isBlank()) {
            return "请输入密码"
        }
        if (password.length < 6) {
            return "密码至少6位字符"
        }

        // 验证邮箱格式（如果填写了）
        if (email.isNotBlank() && !email.isValidEmail()) {
            return "请输入正确的邮箱格式"
        }

        return null
    }

    /**
     * 清除验证错误
     */
    fun clearValidationError() {
        _validationError.value = null
    }

    /**
     * 重置注册状态
     */
    fun resetState() {
        _registerState.value = UiDataState.Idle
        _validationError.value = null
    }

    /**
     * 邮箱格式验证扩展函数
     */
    private fun String.isValidEmail(): Boolean {
        return matches(Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$"))
    }
}
