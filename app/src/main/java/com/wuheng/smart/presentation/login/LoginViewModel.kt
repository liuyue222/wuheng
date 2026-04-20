package com.wuheng.smart.presentation.login

import androidx.lifecycle.viewModelScope
import com.wuheng.smart.data.network.ApiResult
import com.wuheng.smart.data.repository.UserRepository
import com.wuheng.smart.presentation.base.BaseViewModel
import com.wuheng.smart.presentation.base.UiDataState
import com.wuheng.smart.presentation.base.createUiStateFlow
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

/**
 * 登录页面 ViewModel（生产级实现）
 *
 * 职责：
 * 1. 处理登录逻辑，调用UserRepository进行认证
 * 2. 管理登录状态（Idle/Loading/Success/Error）
 * 3. 表单验证（手机号、密码格式校验）
 * 4. 登录成功后保存用户状态
 * 5. 支持"记住密码"功能（存储登录凭证到本地DataStore）
 * 6. 自动填充已保存的登录信息
 *
 * @param userRepository 用户数据仓库，用于执行登录操作和管理登录凭证
 */
@HiltViewModel
class LoginViewModel @Inject constructor(
    private val userRepository: UserRepository
) : BaseViewModel() {

    /**
     * 登录状态
     */
    private val _loginState = createUiStateFlow<Unit>()
    val loginState: StateFlow<UiDataState<Unit>> = _loginState.asStateFlow()

    /**
     * 表单验证错误信息
     */
    private val _validationError = MutableStateFlow<String?>(null)
    val validationError: StateFlow<String?> = _validationError.asStateFlow()

    /**
     * 已保存的手机号（用于自动填充）
     */
    private val _savedPhone = MutableStateFlow("")
    val savedPhone: StateFlow<String> = _savedPhone.asStateFlow()

    /**
     * 已保存的密码（用于自动填充）
     */
    private val _savedPassword = MutableStateFlow("")
    val savedPassword: StateFlow<String> = _savedPassword.asStateFlow()

    /**
     * 是否启用了记住密码
     */
    private val _isRememberPassword = MutableStateFlow(false)
    val isRememberPassword: StateFlow<Boolean> = _isRememberPassword.asStateFlow()

    init {
        // 初始化时尝试加载保存的登录信息
        loadSavedLoginInfo()
    }

    /**
     * 加载保存的登录信息
     */
    private fun loadSavedLoginInfo() {
        viewModelScope.launch {
            try {
                // 从UserRepository获取保存的手机号
                userRepository.getSavedPhone().collectLatest { phone ->
                    _savedPhone.value = phone
                    Timber.d("Loaded saved phone: ${phone.isNotEmpty()}")
                }
            } catch (e: Exception) {
                Timber.e(e, "Failed to load saved phone")
            }
        }

        viewModelScope.launch {
            try {
                // 从UserRepository获取保存的密码
                userRepository.getSavedPassword().collectLatest { password ->
                    _savedPassword.value = password
                    Timber.d("Loaded saved password: ${password.isNotEmpty()}")
                }
            } catch (e: Exception) {
                Timber.e(e, "Failed to load saved password")
            }
        }

        viewModelScope.launch {
            try {
                // 从UserRepository获取记住密码状态
                userRepository.isRememberPassword().collectLatest { isRemember ->
                    _isRememberPassword.value = isRemember
                    Timber.d("Loaded remember password state: $isRemember")
                }
            } catch (e: Exception) {
                Timber.e(e, "Failed to load remember password state")
            }
        }
    }

    /**
     * 执行登录
     *
     * @param phone 手机号
     * @param password 密码
     * @param rememberPassword 是否记住密码
     */
    fun login(phone: String, password: String, rememberPassword: Boolean = false) {
        // 先进行表单验证
        val validationResult = validateLoginForm(phone, password)
        if (!validationResult.isValid) {
            _validationError.value = validationResult.errorMessage
            return
        }

        _validationError.value = null
        Timber.d("Starting login for user: $phone, rememberPassword: $rememberPassword")

        viewModelScope.launch {
            _loginState.value = UiDataState.Loading

            userRepository.login(phone, password).collectLatest { result ->
                when (result) {
                    is ApiResult.Loading -> {
                        _loginState.value = UiDataState.Loading
                    }
                    is ApiResult.Success -> {
                        Timber.d("Login successful")
                        
                        // 如果选择了记住密码，保存登录信息
                        if (rememberPassword) {
                            saveLoginCredentials(phone, password)
                        } else {
                            clearSavedLoginCredentials()
                        }
                        
                        _loginState.value = UiDataState.Success(Unit)
                    }
                    is ApiResult.Error -> {
                        Timber.e(result.exception, "Login failed")
                        _loginState.value = UiDataState.Error(result.exception)
                    }
                }
            }
        }
    }

    /**
     * 保存登录凭证到本地
     *
     * @param phone 手机号
     * @param password 密码
     */
    private fun saveLoginCredentials(phone: String, password: String) {
        viewModelScope.launch {
            try {
                userRepository.saveLoginCredentials(phone, password)
                Timber.d("Login credentials saved")
            } catch (e: Exception) {
                Timber.e(e, "Failed to save login credentials")
            }
        }
    }

    /**
     * 清除保存的登录凭证
     */
    private fun clearSavedLoginCredentials() {
        viewModelScope.launch {
            try {
                userRepository.clearLoginCredentials()
                Timber.d("Login credentials cleared")
            } catch (e: Exception) {
                Timber.e(e, "Failed to clear login credentials")
            }
        }
    }

    /**
     * 重置登录状态
     * 用于在导航后或重新尝试登录前清除状态
     */
    fun resetState() {
        _loginState.value = UiDataState.Idle
        _validationError.value = null
        Timber.d("Login state reset")
    }

    /**
     * 清除验证错误
     */
    fun clearValidationError() {
        _validationError.value = null
    }

    /**
     * 验证登录表单
     *
     * @param phone 手机号
     * @param password 密码
     * @return 验证结果
     */
    private fun validateLoginForm(phone: String, password: String): ValidationResult {
        return when {
            phone.isBlank() -> ValidationResult(false, "请输入手机号")
            !phone.matches(Regex("^1[3-9]\\d{9}$")) -> ValidationResult(false, "请输入正确的11位手机号")
            password.isBlank() -> ValidationResult(false, "请输入密码")
            password.length < 6 -> ValidationResult(false, "密码长度至少为6位")
            else -> ValidationResult(true)
        }
    }

    /**
     * 验证结果数据类
     */
    private data class ValidationResult(
        val isValid: Boolean,
        val errorMessage: String? = null
    )
}
