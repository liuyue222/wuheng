package com.wuheng.smart.presentation.forgotpassword

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wuheng.smart.data.network.ApiResult
import com.wuheng.smart.data.network.AppException
import com.wuheng.smart.data.repository.UserRepository
import com.wuheng.smart.presentation.base.UiDataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

/**
 * 忘记密码页面 ViewModel
 *
 * 负责处理忘记密码相关的业务逻辑：
 * - 发送验证码
 * - 验证验证码
 * - 重置密码
 *
 * @property userRepository 用户数据仓库
 */
@HiltViewModel
class ForgotPasswordViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    /**
     * 重置密码状态
     */
    private val _resetState = MutableStateFlow<UiDataState<Unit>>(UiDataState.Idle)
    val resetState: StateFlow<UiDataState<Unit>> = _resetState.asStateFlow()

    /**
     * 验证错误信息
     */
    private val _validationError = MutableStateFlow<String?>(null)
    val validationError: StateFlow<String?> = _validationError.asStateFlow()

    /**
     * 发送验证码
     *
     * @param phone 手机号
     */
    fun sendVerificationCode(phone: String) {
        viewModelScope.launch {
            // 验证手机号格式
            if (!phone.isValidPhoneNumber()) {
                _validationError.value = "请输入正确的11位手机号"
                return@launch
            }

            // TODO: 调用发送验证码的 API（如果后端有提供）
            // 目前接口文档中忘记密码只需要 mobile 和 new_password
            // 如有需要可在此处调用发送验证码接口

            _validationError.value = null
        }
    }

    /**
     * 重置密码
     *
     * @param phone 手机号
     * @param code 验证码（当前接口未使用，预留）
     * @param newPassword 新密码
     */
    fun resetPassword(phone: String, code: String, newPassword: String) {
        viewModelScope.launch {
            // 表单验证
            val validationResult = validateForm(phone, code, newPassword)
            if (validationResult != null) {
                _validationError.value = validationResult
                return@launch
            }

            _resetState.value = UiDataState.Loading
            _validationError.value = null

            // 调用忘记密码 API
            userRepository.forgotPassword(phone, newPassword)
                .collect { result ->
                    when (result) {
                        is ApiResult.Success -> {
                            Timber.d("Forgot password success: phone=$phone")
                            _resetState.value = UiDataState.Success(Unit)
                        }
                        is ApiResult.Error -> {
                            Timber.e("Forgot password failed: ${result.exception.message}")
                            _resetState.value = UiDataState.Error(result.exception)
                        }
                        is ApiResult.Loading -> {
                            // 已在前面设置为 Loading 状态
                        }
                    }
                }
        }
    }

    /**
     * 清除验证错误
     */
    fun clearValidationError() {
        _validationError.value = null
    }

    /**
     * 重置状态
     */
    fun resetState() {
        _resetState.value = UiDataState.Idle
    }

    /**
     * 验证表单
     *
     * @param phone 手机号
     * @param code 验证码
     * @param newPassword 新密码
     * @return 错误信息，验证通过返回 null
     */
    private fun validateForm(phone: String, code: String, newPassword: String): String? {
        return when {
            !phone.isValidPhoneNumber() -> "请输入正确的11位手机号"
            code.length != 6 -> "请输入6位验证码"
            newPassword.length < 6 -> "密码长度不能少于6位"
            else -> null
        }
    }

    /**
     * 手机号格式验证
     * 规则：11位数字，以1开头，第二位为3-9
     */
    private fun String.isValidPhoneNumber(): Boolean {
        return matches(Regex("^1[3-9]\\d{9}$"))
    }
}
