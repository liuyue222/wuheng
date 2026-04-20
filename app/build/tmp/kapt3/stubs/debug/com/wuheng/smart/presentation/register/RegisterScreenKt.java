package com.wuheng.smart.presentation.register;

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
@kotlin.Metadata(mv = {1, 7, 1}, k = 2, d1 = {"\u0000>\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0007\u001al\u0010\u0000\u001a\u00020\u00012\b\b\u0002\u0010\u0002\u001a\u00020\u00032\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00010\u00052*\u0010\u0006\u001a&\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\u00010\u00072\u000e\b\u0002\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00010\n2\u000e\b\u0002\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u00010\nH\u0003\u001a\u0016\u0010\f\u001a\u00020\u00012\f\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u00010\nH\u0003\u001a\u00aa\u0001\u0010\u000e\u001a\u00020\u00012\u0006\u0010\u000f\u001a\u00020\b2\u0012\u0010\u0010\u001a\u000e\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\u00010\u00112\u0006\u0010\u0012\u001a\u00020\b2\u0012\u0010\u0013\u001a\u000e\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\u00010\u00112\u0006\u0010\u0014\u001a\u00020\b2\u0012\u0010\u0015\u001a\u000e\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\u00010\u00112\u0006\u0010\u0016\u001a\u00020\b2\u0012\u0010\u0017\u001a\u000e\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\u00010\u00112\u0006\u0010\u0018\u001a\u00020\b2\u0012\u0010\u0019\u001a\u000e\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\u00010\u00112\u0006\u0010\u001a\u001a\u00020\u001b2\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00010\nH\u0003\u001a\b\u0010\u001c\u001a\u00020\u0001H\u0003\u001a2\u0010\u001d\u001a\u00020\u00012\b\b\u0002\u0010\u001e\u001a\u00020\u001f2\u000e\b\u0002\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00010\n2\u000e\b\u0002\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u00010\nH\u0007\u001a\b\u0010 \u001a\u00020\u0001H\u0007\u001a\b\u0010!\u001a\u00020\u0001H\u0007\u001a\b\u0010\"\u001a\u00020\u0001H\u0007\u001a\u0016\u0010#\u001a\u00020\u00012\f\u0010$\u001a\b\u0012\u0004\u0012\u00020\u00010\nH\u0003\u001a(\u0010%\u001a\u00020\u001b2\u0006\u0010\u000f\u001a\u00020\b2\u0006\u0010\u0012\u001a\u00020\b2\u0006\u0010\u0014\u001a\u00020\b2\u0006\u0010\u0016\u001a\u00020\bH\u0002\u00a8\u0006&"}, d2 = {"RegisterContent", "", "modifier", "Landroidx/compose/ui/Modifier;", "registerState", "Lcom/wuheng/smart/presentation/base/UiDataState;", "onRegister", "Lkotlin/Function5;", "", "onNavigateToLogin", "Lkotlin/Function0;", "onNavigateToHome", "RegisterFooterSection", "onLoginClick", "RegisterFormCard", "username", "onUsernameChange", "Lkotlin/Function1;", "mobile", "onMobileChange", "password", "onPasswordChange", "confirmPassword", "onConfirmPasswordChange", "email", "onEmailChange", "isLoading", "", "RegisterLogoSection", "RegisterScreen", "viewModel", "Lcom/wuheng/smart/presentation/register/RegisterViewModel;", "RegisterScreenLoadingPreview", "RegisterScreenPreview", "RegisterScreenSuccessPreview", "RegisterTopBar", "onBackClick", "isFormValid", "app_debug"})
public final class RegisterScreenKt {
    
    /**
     * 注册页面 Composable
     *
     * 布局结构：
     * 1. 顶部标题栏：返回按钮 + 页面标题
     * 2. Logo区域：应用图标 + 应用名称
     * 3. 注册表单卡片：用户名 + 手机号 + 密码 + 确认密码 + 邮箱（可选）
     * 4. 底部操作区域：已有账号？立即登录
     *
     * 设计规范：
     * - 页面背景：BackgroundLight (#F0F4F8)
     * - 卡片背景：SurfaceLight (白色)
     * - 卡片圆角：corner_md (12.dp)
     * - 主按钮：PrimaryBlue (#2B9DF0)
     *
     * @param viewModel 注册ViewModel
     * @param onNavigateToLogin 导航到登录页回调
     * @param onNavigateToHome 导航到首页回调（注册成功）
     */
    @androidx.compose.runtime.Composable()
    public static final void RegisterScreen(@org.jetbrains.annotations.NotNull()
    com.wuheng.smart.presentation.register.RegisterViewModel viewModel, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onNavigateToLogin, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onNavigateToHome) {
    }
    
    /**
     * 注册页面顶部栏
     */
    @androidx.compose.runtime.Composable()
    private static final void RegisterTopBar(kotlin.jvm.functions.Function0<kotlin.Unit> onBackClick) {
    }
    
    /**
     * 注册页面内容
     */
    @androidx.compose.runtime.Composable()
    private static final void RegisterContent(androidx.compose.ui.Modifier modifier, com.wuheng.smart.presentation.base.UiDataState<kotlin.Unit> registerState, kotlin.jvm.functions.Function5<? super java.lang.String, ? super java.lang.String, ? super java.lang.String, ? super java.lang.String, ? super java.lang.String, kotlin.Unit> onRegister, kotlin.jvm.functions.Function0<kotlin.Unit> onNavigateToLogin, kotlin.jvm.functions.Function0<kotlin.Unit> onNavigateToHome) {
    }
    
    /**
     * 注册页面Logo区域
     */
    @androidx.compose.runtime.Composable()
    private static final void RegisterLogoSection() {
    }
    
    /**
     * 注册表单卡片
     */
    @androidx.compose.runtime.Composable()
    private static final void RegisterFormCard(java.lang.String username, kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onUsernameChange, java.lang.String mobile, kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onMobileChange, java.lang.String password, kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onPasswordChange, java.lang.String confirmPassword, kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onConfirmPasswordChange, java.lang.String email, kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onEmailChange, boolean isLoading, kotlin.jvm.functions.Function0<kotlin.Unit> onRegister) {
    }
    
    /**
     * 检查表单是否有效
     */
    private static final boolean isFormValid(java.lang.String username, java.lang.String mobile, java.lang.String password, java.lang.String confirmPassword) {
        return false;
    }
    
    /**
     * 注册页面底部操作区域
     */
    @androidx.compose.runtime.Composable()
    private static final void RegisterFooterSection(kotlin.jvm.functions.Function0<kotlin.Unit> onLoginClick) {
    }
    
    @androidx.compose.runtime.Composable()
    @androidx.compose.ui.tooling.preview.Preview(showBackground = true, name = "\u6ce8\u518c\u9875\u9762-\u4eae\u8272\u4e3b\u9898", backgroundColor = 4293981432L)
    public static final void RegisterScreenPreview() {
    }
    
    @androidx.compose.runtime.Composable()
    @androidx.compose.ui.tooling.preview.Preview(showBackground = true, name = "\u6ce8\u518c\u9875\u9762-\u52a0\u8f7d\u4e2d", backgroundColor = 4293981432L)
    public static final void RegisterScreenLoadingPreview() {
    }
    
    @androidx.compose.runtime.Composable()
    @androidx.compose.ui.tooling.preview.Preview(showBackground = true, name = "\u6ce8\u518c\u9875\u9762-\u6210\u529f\u72b6\u6001", backgroundColor = 4293981432L)
    public static final void RegisterScreenSuccessPreview() {
    }
}