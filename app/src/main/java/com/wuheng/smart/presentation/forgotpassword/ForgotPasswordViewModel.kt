package com.wuheng.smart.presentation.forgotpassword

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wuheng.smart.data.network.AppException
import com.wuheng.smart.presentation.base.UiDataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * 忘记密码页面 ViewModel
 *
 * 负责处理忘记密码相关的业务逻辑：
 * - 发送验证码
 * - 验证验证码
 * - 重置密码
 *
 * @property resetState 重置密码状态流
 * @property validationError 验证错误信息流
 */
@HiltViewModel
class ForgotPasswordViewModel @Inject constructor(
    // TODO: 注入忘记密码相关的 UseCase 或 Repository
    // private val forgotPasswordUseCase: ForgotPasswordUseCase,
    // private val sendVerificationCodeUseCase: SendVerificationCodeUseCase
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

            // TODO: 调用发送验证码的 UseCase
            // sendVerificationCodeUseCase(phone)
            //     .onSuccess { }
            //     .onFailure { error ->
            //         _validationError.value = error.message
            //     }

            // 模拟发送成功
            _validationError.value = null
        }
    }

    /**
     * 重置密码
     *
     * @param phone 手机号
     * @param code 验证码
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

            // TODO: 调用重置密码的 UseCase
            // forgotPasswordUseCase(phone, code, newPassword)
            //     .onSuccess {
            //         _resetState.value = UiDataState.Success(Unit)
            //     }
            //     .onFailure { error ->
            //         _resetState.value = UiDataState.Error(error as AppException)
            //     }

            // 模拟网络请求
            kotlinx.coroutines.delay(1500)

            // 模拟成功（实际项目中应该调用真实的API）
            _resetState.value = UiDataState.Success(Unit)

            // 模拟错误情况（用于测试）
            // _resetState.value = UiDataState.Error(
            //     AppException.BusinessError(-1, "验证码错误或已过期")
            // )
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
