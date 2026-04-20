package com.wuheng.smart.presentation.login;

import androidx.compose.animation.core.Spring;
import androidx.compose.foundation.layout.*;
import androidx.compose.foundation.text.KeyboardOptions;
import androidx.compose.material.icons.Icons;
import androidx.compose.material3.*;
import androidx.compose.runtime.*;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.focus.FocusDirection;
import androidx.compose.ui.text.font.FontWeight;
import androidx.compose.ui.text.input.ImeAction;
import androidx.compose.ui.text.input.KeyboardType;
import androidx.compose.ui.text.input.PasswordVisualTransformation;
import androidx.compose.ui.text.input.VisualTransformation;
import androidx.compose.ui.text.style.TextAlign;
import androidx.compose.ui.tooling.preview.Preview;
import com.wuheng.smart.presentation.base.UiDataState;
import com.wuheng.smart.presentation.theme.*;

@kotlin.OptIn(markerClass = {androidx.compose.material3.ExperimentalMaterial3Api.class})
@kotlin.Metadata(mv = {1, 7, 1}, k = 2, d1 = {"\u0000@\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0006\u001a\u008e\u0001\u0010\u0000\u001a\u00020\u00012\b\b\u0002\u0010\u0002\u001a\u00020\u00032\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00010\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00072\b\b\u0002\u0010\b\u001a\u00020\u00072\b\b\u0002\u0010\t\u001a\u00020\n2\u001e\u0010\u000b\u001a\u001a\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\u00010\f2\u000e\b\u0002\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u00010\u000e2\u000e\b\u0002\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00010\u000e2\u000e\b\u0002\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00010\u000eH\u0007\u001a$\u0010\u0011\u001a\u00020\u00012\f\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00010\u000e2\f\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00010\u000eH\u0003\u001az\u0010\u0014\u001a\u00020\u00012\u0006\u0010\u0015\u001a\u00020\u00072\u0012\u0010\u0016\u001a\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\u00010\u00172\u0006\u0010\u0018\u001a\u00020\u00072\u0012\u0010\u0019\u001a\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\u00010\u00172\u0006\u0010\u001a\u001a\u00020\n2\u0012\u0010\u001b\u001a\u000e\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\u00010\u00172\u0006\u0010\u001c\u001a\u00020\n2\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u00010\u000e2\u0006\u0010\u001d\u001a\u00020\nH\u0003\u001a\b\u0010\u001e\u001a\u00020\u0001H\u0003\u001aB\u0010\u001f\u001a\u00020\u00012\b\b\u0002\u0010 \u001a\u00020!2\u000e\b\u0002\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u00010\u000e2\u000e\b\u0002\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00010\u000e2\u000e\b\u0002\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00010\u000eH\u0007\u001a\b\u0010\"\u001a\u00020\u0001H\u0007\u001a\b\u0010#\u001a\u00020\u0001H\u0007\u001a\b\u0010$\u001a\u00020\u0001H\u0007\u001a\b\u0010%\u001a\u00020\u0001H\u0007\u001a\f\u0010&\u001a\u00020\n*\u00020\u0007H\u0002\u00a8\u0006\'"}, d2 = {"LoginContent", "", "modifier", "Landroidx/compose/ui/Modifier;", "loginState", "Lcom/wuheng/smart/presentation/base/UiDataState;", "savedPhone", "", "savedPassword", "isRememberPasswordEnabled", "", "onLogin", "Lkotlin/Function3;", "onNavigateToHome", "Lkotlin/Function0;", "onNavigateToRegister", "onNavigateToForgotPassword", "LoginFooterSection", "onForgotPasswordClick", "onRegisterClick", "LoginFormCard", "phone", "onPhoneChange", "Lkotlin/Function1;", "password", "onPasswordChange", "rememberPassword", "onRememberPasswordChange", "isLoading", "isPhoneValid", "LoginLogoSection", "LoginScreen", "viewModel", "Lcom/wuheng/smart/presentation/login/LoginViewModel;", "LoginScreenErrorPreview", "LoginScreenLoadingPreview", "LoginScreenPreview", "LoginScreenSuccessPreview", "isValidPhoneNumber", "app_debug"})
public final class LoginScreenKt {
    
    /**
     * 登录页面 Composable
     *
     * 布局结构：
     * 1. 顶部Logo区域：应用图标 + 应用名称
     * 2. 登录表单卡片：手机号输入框 + 密码输入框 + 记住密码 + 登录按钮
     * 3. 底部操作区域：忘记密码 + 注册账号
     *
     * 设计规范：
     * - 页面背景：BackgroundLight (#F0F4F8)
     * - 卡片背景：SurfaceLight (白色)
     * - 卡片圆角：corner_md (12.dp)
     * - 主按钮：PrimaryBlue (#2B9DF0)
     *
     * @param viewModel 登录ViewModel
     * @param onNavigateToHome 导航到首页回调（登录成功）
     * @param onNavigateToRegister 导航到注册页面回调
     * @param onNavigateToForgotPassword 导航到忘记密码页面回调
     */
    @androidx.compose.runtime.Composable()
    public static final void LoginScreen(@org.jetbrains.annotations.NotNull()
    com.wuheng.smart.presentation.login.LoginViewModel viewModel, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onNavigateToHome, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onNavigateToRegister, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onNavigateToForgotPassword) {
    }
    
    /**
     * 登录页面内容
     */
    @androidx.compose.runtime.Composable()
    public static final void LoginContent(@org.jetbrains.annotations.NotNull()
    androidx.compose.ui.Modifier modifier, @org.jetbrains.annotations.NotNull()
    com.wuheng.smart.presentation.base.UiDataState<kotlin.Unit> loginState, @org.jetbrains.annotations.NotNull()
    java.lang.String savedPhone, @org.jetbrains.annotations.NotNull()
    java.lang.String savedPassword, boolean isRememberPasswordEnabled, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function3<? super java.lang.String, ? super java.lang.String, ? super java.lang.Boolean, kotlin.Unit> onLogin, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onNavigateToHome, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onNavigateToRegister, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onNavigateToForgotPassword) {
    }
    
    /**
     * 登录页面Logo区域
     */
    @androidx.compose.runtime.Composable()
    private static final void LoginLogoSection() {
    }
    
    /**
     * 登录表单卡片
     */
    @androidx.compose.runtime.Composable()
    private static final void LoginFormCard(java.lang.String phone, kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onPhoneChange, java.lang.String password, kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onPasswordChange, boolean rememberPassword, kotlin.jvm.functions.Function1<? super java.lang.Boolean, kotlin.Unit> onRememberPasswordChange, boolean isLoading, kotlin.jvm.functions.Function0<kotlin.Unit> onLogin, boolean isPhoneValid) {
    }
    
    /**
     * 登录页面底部操作区域
     */
    @androidx.compose.runtime.Composable()
    private static final void LoginFooterSection(kotlin.jvm.functions.Function0<kotlin.Unit> onForgotPasswordClick, kotlin.jvm.functions.Function0<kotlin.Unit> onRegisterClick) {
    }
    
    /**
     * 手机号格式验证扩展函数
     * 规则：11位数字，以1开头，第二位为3-9
     */
    private static final boolean isValidPhoneNumber(java.lang.String $this$isValidPhoneNumber) {
        return false;
    }
    
    @androidx.compose.runtime.Composable()
    @androidx.compose.ui.tooling.preview.Preview(showBackground = true, name = "\u767b\u5f55\u9875\u9762-\u4eae\u8272\u4e3b\u9898", backgroundColor = 4293981432L)
    public static final void LoginScreenPreview() {
    }
    
    @androidx.compose.runtime.Composable()
    @androidx.compose.ui.tooling.preview.Preview(showBackground = true, name = "\u767b\u5f55\u9875\u9762-\u52a0\u8f7d\u4e2d", backgroundColor = 4293981432L)
    public static final void LoginScreenLoadingPreview() {
    }
    
    @androidx.compose.runtime.Composable()
    @androidx.compose.ui.tooling.preview.Preview(showBackground = true, name = "\u767b\u5f55\u9875\u9762-\u9519\u8bef\u72b6\u6001", backgroundColor = 4293981432L)
    public static final void LoginScreenErrorPreview() {
    }
    
    @androidx.compose.runtime.Composable()
    @androidx.compose.ui.tooling.preview.Preview(showBackground = true, name = "\u767b\u5f55\u9875\u9762-\u6210\u529f\u72b6\u6001", backgroundColor = 4293981432L)
    public static final void LoginScreenSuccessPreview() {
    }
}