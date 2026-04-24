package com.wuheng.smart.presentation.forgotpassword;

import androidx.lifecycle.ViewModel;
import com.wuheng.smart.data.network.ApiResult;
import com.wuheng.smart.data.network.AppException;
import com.wuheng.smart.data.repository.UserRepository;
import com.wuheng.smart.presentation.base.UiDataState;
import dagger.hilt.android.lifecycle.HiltViewModel;
import kotlinx.coroutines.flow.StateFlow;
import timber.log.Timber;
import javax.inject.Inject;

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
@dagger.hilt.android.lifecycle.HiltViewModel()
@kotlin.Metadata(mv = {1, 7, 1}, k = 1, d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0010\u000b\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u000f\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0006\u0010\u0011\u001a\u00020\bJ\u001e\u0010\u0012\u001a\u00020\b2\u0006\u0010\u0013\u001a\u00020\n2\u0006\u0010\u0014\u001a\u00020\n2\u0006\u0010\u0015\u001a\u00020\nJ\u0006\u0010\u000b\u001a\u00020\bJ\u000e\u0010\u0016\u001a\u00020\b2\u0006\u0010\u0013\u001a\u00020\nJ\"\u0010\u0017\u001a\u0004\u0018\u00010\n2\u0006\u0010\u0013\u001a\u00020\n2\u0006\u0010\u0014\u001a\u00020\n2\u0006\u0010\u0015\u001a\u00020\nH\u0002J\f\u0010\u0018\u001a\u00020\u0019*\u00020\nH\u0002R\u001a\u0010\u0005\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00070\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0016\u0010\t\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\n0\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001d\u0010\u000b\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00070\f\u00a2\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0019\u0010\u000f\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\n0\f\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u000e\u00a8\u0006\u001a"}, d2 = {"Lcom/wuheng/smart/presentation/forgotpassword/ForgotPasswordViewModel;", "Landroidx/lifecycle/ViewModel;", "userRepository", "Lcom/wuheng/smart/data/repository/UserRepository;", "(Lcom/wuheng/smart/data/repository/UserRepository;)V", "_resetState", "Lkotlinx/coroutines/flow/MutableStateFlow;", "Lcom/wuheng/smart/presentation/base/UiDataState;", "", "_validationError", "", "resetState", "Lkotlinx/coroutines/flow/StateFlow;", "getResetState", "()Lkotlinx/coroutines/flow/StateFlow;", "validationError", "getValidationError", "clearValidationError", "resetPassword", "phone", "code", "newPassword", "sendVerificationCode", "validateForm", "isValidPhoneNumber", "", "app_debug"})
public final class ForgotPasswordViewModel extends androidx.lifecycle.ViewModel {
    private final com.wuheng.smart.data.repository.UserRepository userRepository = null;
    
    /**
     * 重置密码状态
     */
    private final kotlinx.coroutines.flow.MutableStateFlow<com.wuheng.smart.presentation.base.UiDataState<kotlin.Unit>> _resetState = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.wuheng.smart.presentation.base.UiDataState<kotlin.Unit>> resetState = null;
    
    /**
     * 验证错误信息
     */
    private final kotlinx.coroutines.flow.MutableStateFlow<java.lang.String> _validationError = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.lang.String> validationError = null;
    
    @javax.inject.Inject()
    public ForgotPasswordViewModel(@org.jetbrains.annotations.NotNull()
    com.wuheng.smart.data.repository.UserRepository userRepository) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.wuheng.smart.presentation.base.UiDataState<kotlin.Unit>> getResetState() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.lang.String> getValidationError() {
        return null;
    }
    
    /**
     * 发送验证码
     *
     * @param phone 手机号
     */
    public final void sendVerificationCode(@org.jetbrains.annotations.NotNull()
    java.lang.String phone) {
    }
    
    /**
     * 重置密码
     *
     * @param phone 手机号
     * @param code 验证码（当前接口未使用，预留）
     * @param newPassword 新密码
     */
    public final void resetPassword(@org.jetbrains.annotations.NotNull()
    java.lang.String phone, @org.jetbrains.annotations.NotNull()
    java.lang.String code, @org.jetbrains.annotations.NotNull()
    java.lang.String newPassword) {
    }
    
    /**
     * 清除验证错误
     */
    public final void clearValidationError() {
    }
    
    /**
     * 重置状态
     */
    public final void resetState() {
    }
    
    /**
     * 验证表单
     *
     * @param phone 手机号
     * @param code 验证码
     * @param newPassword 新密码
     * @return 错误信息，验证通过返回 null
     */
    private final java.lang.String validateForm(java.lang.String phone, java.lang.String code, java.lang.String newPassword) {
        return null;
    }
    
    /**
     * 手机号格式验证
     * 规则：11位数字，以1开头，第二位为3-9
     */
    private final boolean isValidPhoneNumber(java.lang.String $this$isValidPhoneNumber) {
        return false;
    }
}