package com.wuheng.smart.presentation.forgotpassword;

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
@kotlin.Metadata(mv = {1, 7, 1}, k = 2, d1 = {"\u0000B\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\b\u001ad\u0010\u0000\u001a\u00020\u00012\b\b\u0002\u0010\u0002\u001a\u00020\u00032\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00010\u00052\u0012\u0010\u0006\u001a\u000e\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\u00010\u00072\u001e\u0010\t\u001a\u001a\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\u00010\n2\u000e\b\u0002\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u00010\fH\u0007\u001a\b\u0010\r\u001a\u00020\u0001H\u0003\u001a\u0016\u0010\u000e\u001a\u00020\u00012\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u00010\fH\u0003\u001a\u00b4\u0001\u0010\u000f\u001a\u00020\u00012\u0006\u0010\u0010\u001a\u00020\b2\u0012\u0010\u0011\u001a\u000e\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\u00010\u00072\u0006\u0010\u0012\u001a\u00020\b2\u0012\u0010\u0013\u001a\u000e\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\u00010\u00072\u0006\u0010\u0014\u001a\u00020\b2\u0012\u0010\u0015\u001a\u000e\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\u00010\u00072\u0006\u0010\u0016\u001a\u00020\b2\u0012\u0010\u0017\u001a\u000e\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\u00010\u00072\u0006\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u001c\u001a\u00020\u00192\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00010\f2\f\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00010\f2\u0006\u0010\u001d\u001a\u00020\u0019H\u0003\u001a2\u0010\u001e\u001a\u00020\u00012\b\b\u0002\u0010\u001f\u001a\u00020 2\u000e\b\u0002\u0010!\u001a\b\u0012\u0004\u0012\u00020\u00010\f2\u000e\b\u0002\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u00010\fH\u0007\u001a\b\u0010\"\u001a\u00020\u0001H\u0007\u001a\b\u0010#\u001a\u00020\u0001H\u0007\u001a\b\u0010$\u001a\u00020\u0001H\u0007\u001a\b\u0010%\u001a\u00020\u0001H\u0007\u001a\u0016\u0010&\u001a\u00020\u00012\f\u0010!\u001a\b\u0012\u0004\u0012\u00020\u00010\fH\u0003\u001a\f\u0010\'\u001a\u00020\u0019*\u00020\bH\u0002\u00a8\u0006("}, d2 = {"ForgotPasswordContent", "", "modifier", "Landroidx/compose/ui/Modifier;", "resetState", "Lcom/wuheng/smart/presentation/base/UiDataState;", "onSendVerificationCode", "Lkotlin/Function1;", "", "onResetPassword", "Lkotlin/Function3;", "onNavigateToLogin", "Lkotlin/Function0;", "ForgotPasswordDescriptionSection", "ForgotPasswordFooterSection", "ForgotPasswordFormCard", "phone", "onPhoneChange", "verificationCode", "onVerificationCodeChange", "newPassword", "onNewPasswordChange", "confirmPassword", "onConfirmPasswordChange", "isLoading", "", "countdownSeconds", "", "isCountingDown", "isPhoneValid", "ForgotPasswordScreen", "viewModel", "Lcom/wuheng/smart/presentation/forgotpassword/ForgotPasswordViewModel;", "onNavigateBack", "ForgotPasswordScreenErrorPreview", "ForgotPasswordScreenLoadingPreview", "ForgotPasswordScreenPreview", "ForgotPasswordScreenSuccessPreview", "ForgotPasswordTopBar", "isValidPhoneNumber", "app_debug"})
public final class ForgotPasswordScreenKt {
    
    /**
     * 忘记密码页面 Composable
     *
     * 布局结构：
     * 1. 顶部导航栏：返回按钮 + 标题"忘记密码"
     * 2. 说明文字区域：提示用户输入手机号
     * 3. 表单卡片：手机号输入框 + 验证码输入框 + 新密码输入框 + 确认新密码输入框
     * 4. 重置密码按钮
     * 5. 底部返回登录链接
     *
     * 功能特性：
     * - 手机号格式验证（11位数字）
     * - 验证码倒计时（60秒）
     * - 密码显示/隐藏切换
     * - 新密码与确认密码匹配验证
     * - 加载状态显示
     * - 错误提示（Snackbar）
     * - 重置成功动画
     *
     * 设计规范：
     * - 页面背景：BackgroundLight (#F0F4F8)
     * - 卡片背景：SurfaceLight (白色)
     * - 卡片圆角：corner_md (12.dp)
     * - 主按钮：PrimaryBlue (#2B9DF0)
     *
     * @param viewModel 忘记密码ViewModel
     * @param onNavigateBack 返回上一页回调
     * @param onNavigateToLogin 导航到登录页面回调（重置成功后）
     */
    @androidx.compose.runtime.Composable()
    public static final void ForgotPasswordScreen(@org.jetbrains.annotations.NotNull()
    com.wuheng.smart.presentation.forgotpassword.ForgotPasswordViewModel viewModel, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onNavigateBack, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onNavigateToLogin) {
    }
    
    /**
     * 忘记密码页面顶部导航栏
     */
    @androidx.compose.runtime.Composable()
    private static final void ForgotPasswordTopBar(kotlin.jvm.functions.Function0<kotlin.Unit> onNavigateBack) {
    }
    
    /**
     * 忘记密码页面内容
     */
    @androidx.compose.runtime.Composable()
    public static final void ForgotPasswordContent(@org.jetbrains.annotations.NotNull()
    androidx.compose.ui.Modifier modifier, @org.jetbrains.annotations.NotNull()
    com.wuheng.smart.presentation.base.UiDataState<kotlin.Unit> resetState, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onSendVerificationCode, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function3<? super java.lang.String, ? super java.lang.String, ? super java.lang.String, kotlin.Unit> onResetPassword, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onNavigateToLogin) {
    }
    
    /**
     * 忘记密码页面说明文字区域
     */
    @androidx.compose.runtime.Composable()
    private static final void ForgotPasswordDescriptionSection() {
    }
    
    /**
     * 忘记密码表单卡片
     */
    @androidx.compose.runtime.Composable()
    private static final void ForgotPasswordFormCard(java.lang.String phone, kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onPhoneChange, java.lang.String verificationCode, kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onVerificationCodeChange, java.lang.String newPassword, kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onNewPasswordChange, java.lang.String confirmPassword, kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onConfirmPasswordChange, boolean isLoading, int countdownSeconds, boolean isCountingDown, kotlin.jvm.functions.Function0<kotlin.Unit> onSendVerificationCode, kotlin.jvm.functions.Function0<kotlin.Unit> onResetPassword, boolean isPhoneValid) {
    }
    
    /**
     * 忘记密码页面底部操作区域
     */
    @androidx.compose.runtime.Composable()
    private static final void ForgotPasswordFooterSection(kotlin.jvm.functions.Function0<kotlin.Unit> onNavigateToLogin) {
    }
    
    /**
     * 手机号格式验证扩展函数
     * 规则：11位数字，以1开头，第二位为3-9
     */
    private static final boolean isValidPhoneNumber(java.lang.String $this$isValidPhoneNumber) {
        return false;
    }
    
    @androidx.compose.runtime.Composable()
    @androidx.compose.ui.tooling.preview.Preview(showBackground = true, name = "\u5fd8\u8bb0\u5bc6\u7801\u9875\u9762-\u9ed8\u8ba4\u72b6\u6001", backgroundColor = 4293981432L)
    public static final void ForgotPasswordScreenPreview() {
    }
    
    @androidx.compose.runtime.Composable()
    @androidx.compose.ui.tooling.preview.Preview(showBackground = true, name = "\u5fd8\u8bb0\u5bc6\u7801\u9875\u9762-\u52a0\u8f7d\u4e2d", backgroundColor = 4293981432L)
    public static final void ForgotPasswordScreenLoadingPreview() {
    }
    
    @androidx.compose.runtime.Composable()
    @androidx.compose.ui.tooling.preview.Preview(showBackground = true, name = "\u5fd8\u8bb0\u5bc6\u7801\u9875\u9762-\u9519\u8bef\u72b6\u6001", backgroundColor = 4293981432L)
    public static final void ForgotPasswordScreenErrorPreview() {
    }
    
    @androidx.compose.runtime.Composable()
    @androidx.compose.ui.tooling.preview.Preview(showBackground = true, name = "\u5fd8\u8bb0\u5bc6\u7801\u9875\u9762-\u6210\u529f\u72b6\u6001", backgroundColor = 4293981432L)
    public static final void ForgotPasswordScreenSuccessPreview() {
    }
}