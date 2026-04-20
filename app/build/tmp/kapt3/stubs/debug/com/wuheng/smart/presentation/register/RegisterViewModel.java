package com.wuheng.smart.presentation.register;

import com.wuheng.smart.data.model.RegisterRequest;
import com.wuheng.smart.data.network.ApiResult;
import com.wuheng.smart.data.network.AppException;
import com.wuheng.smart.data.repository.UserRepository;
import com.wuheng.smart.presentation.base.BaseViewModel;
import com.wuheng.smart.presentation.base.UiDataState;
import dagger.hilt.android.lifecycle.HiltViewModel;
import kotlinx.coroutines.flow.StateFlow;
import timber.log.Timber;
import javax.inject.Inject;

/**
 * 注册页面 ViewModel
 *
 * 负责处理用户注册相关的业务逻辑，包括：
 * - 表单验证
 * - 注册请求
 * - 错误处理
 */
@dagger.hilt.android.lifecycle.HiltViewModel()
@kotlin.Metadata(mv = {1, 7, 1}, k = 1, d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u000e\n\u0002\u0010\u000b\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u000f\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0006\u0010\u0011\u001a\u00020\bJ2\u0010\u0012\u001a\u00020\b2\u0006\u0010\u0013\u001a\u00020\n2\u0006\u0010\u0014\u001a\u00020\n2\u0006\u0010\u0015\u001a\u00020\n2\b\b\u0002\u0010\u0016\u001a\u00020\n2\b\b\u0002\u0010\u0017\u001a\u00020\nJ\u0006\u0010\u0018\u001a\u00020\bJ2\u0010\u0019\u001a\u0004\u0018\u00010\n2\u0006\u0010\u0013\u001a\u00020\n2\u0006\u0010\u0014\u001a\u00020\n2\u0006\u0010\u0015\u001a\u00020\n2\u0006\u0010\u0016\u001a\u00020\n2\u0006\u0010\u0017\u001a\u00020\nH\u0002J\f\u0010\u001a\u001a\u00020\u001b*\u00020\nH\u0002R\u001a\u0010\u0005\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00070\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0016\u0010\t\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\n0\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001d\u0010\u000b\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00070\f\u00a2\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0019\u0010\u000f\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\n0\f\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u000e\u00a8\u0006\u001c"}, d2 = {"Lcom/wuheng/smart/presentation/register/RegisterViewModel;", "Lcom/wuheng/smart/presentation/base/BaseViewModel;", "userRepository", "Lcom/wuheng/smart/data/repository/UserRepository;", "(Lcom/wuheng/smart/data/repository/UserRepository;)V", "_registerState", "Lkotlinx/coroutines/flow/MutableStateFlow;", "Lcom/wuheng/smart/presentation/base/UiDataState;", "", "_validationError", "", "registerState", "Lkotlinx/coroutines/flow/StateFlow;", "getRegisterState", "()Lkotlinx/coroutines/flow/StateFlow;", "validationError", "getValidationError", "clearValidationError", "register", "username", "phone", "password", "realName", "email", "resetState", "validateRegisterForm", "isValidEmail", "", "app_debug"})
public final class RegisterViewModel extends com.wuheng.smart.presentation.base.BaseViewModel {
    private final com.wuheng.smart.data.repository.UserRepository userRepository = null;
    private final kotlinx.coroutines.flow.MutableStateFlow<com.wuheng.smart.presentation.base.UiDataState<kotlin.Unit>> _registerState = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.wuheng.smart.presentation.base.UiDataState<kotlin.Unit>> registerState = null;
    private final kotlinx.coroutines.flow.MutableStateFlow<java.lang.String> _validationError = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.lang.String> validationError = null;
    
    @javax.inject.Inject()
    public RegisterViewModel(@org.jetbrains.annotations.NotNull()
    com.wuheng.smart.data.repository.UserRepository userRepository) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.wuheng.smart.presentation.base.UiDataState<kotlin.Unit>> getRegisterState() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.lang.String> getValidationError() {
        return null;
    }
    
    /**
     * 执行注册
     *
     * @param username 用户名
     * @param phone 手机号
     * @param password 密码
     * @param realName 真实姓名（可选）
     * @param email 邮箱（可选）
     */
    public final void register(@org.jetbrains.annotations.NotNull()
    java.lang.String username, @org.jetbrains.annotations.NotNull()
    java.lang.String phone, @org.jetbrains.annotations.NotNull()
    java.lang.String password, @org.jetbrains.annotations.NotNull()
    java.lang.String realName, @org.jetbrains.annotations.NotNull()
    java.lang.String email) {
    }
    
    /**
     * 验证注册表单
     *
     * @return 错误信息，如果验证通过返回 null
     */
    private final java.lang.String validateRegisterForm(java.lang.String username, java.lang.String phone, java.lang.String password, java.lang.String realName, java.lang.String email) {
        return null;
    }
    
    /**
     * 清除验证错误
     */
    public final void clearValidationError() {
    }
    
    /**
     * 重置注册状态
     */
    public final void resetState() {
    }
    
    /**
     * 邮箱格式验证扩展函数
     */
    private final boolean isValidEmail(java.lang.String $this$isValidEmail) {
        return false;
    }
}