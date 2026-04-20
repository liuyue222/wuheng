@file:OptIn(ExperimentalMaterial3Api::class)

package com.wuheng.smart.presentation.forgotpassword

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.wuheng.smart.presentation.base.UiDataState
import com.wuheng.smart.presentation.theme.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

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
@Composable
fun ForgotPasswordScreen(
    viewModel: ForgotPasswordViewModel = hiltViewModel(),
    onNavigateBack: () -> Unit = {},
    onNavigateToLogin: () -> Unit = {}
) {
    val resetState by viewModel.resetState.collectAsStateWithLifecycle()
    val validationError by viewModel.validationError.collectAsStateWithLifecycle()

    // Snackbar 状态
    val snackbarHostState = remember { SnackbarHostState() }

    // 监听验证错误并显示 Snackbar
    LaunchedEffect(validationError) {
        validationError?.let { error ->
            snackbarHostState.showSnackbar(
                message = error,
                duration = SnackbarDuration.Short
            )
            viewModel.clearValidationError()
        }
    }

    Scaffold(
        topBar = {
            ForgotPasswordTopBar(onNavigateBack = onNavigateBack)
        },
        snackbarHost = {
            SnackbarHost(
                hostState = snackbarHostState,
                modifier = Modifier.padding(bottom = 100.dp)
            ) { data ->
                Snackbar(
                    modifier = Modifier.padding(horizontal = page_margin_horizontal),
                    shape = RoundedCornerShape(corner_sm),
                    containerColor = ErrorRed,
                    contentColor = Color.White
                ) {
                    Text(
                        text = data.visuals.message,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        },
        containerColor = BackgroundLight
    ) { paddingValues ->
        ForgotPasswordContent(
            modifier = Modifier.padding(paddingValues),
            resetState = resetState,
            onSendVerificationCode = { phone ->
                viewModel.sendVerificationCode(phone)
            },
            onResetPassword = { phone, code, newPassword ->
                viewModel.resetPassword(phone, code, newPassword)
            },
            onNavigateToLogin = onNavigateToLogin
        )
    }
}

/**
 * 忘记密码页面顶部导航栏
 */
@Composable
private fun ForgotPasswordTopBar(
    onNavigateBack: () -> Unit
) {
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = "忘记密码",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.SemiBold,
                color = TextPrimaryLight
            )
        },
        navigationIcon = {
            IconButton(onClick = onNavigateBack) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "返回",
                    tint = TextPrimaryLight
                )
            }
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = BackgroundLight,
            titleContentColor = TextPrimaryLight
        )
    )
}

/**
 * 忘记密码页面内容
 */
@Composable
fun ForgotPasswordContent(
    modifier: Modifier = Modifier,
    resetState: UiDataState<Unit>,
    onSendVerificationCode: (String) -> Unit,
    onResetPassword: (String, String, String) -> Unit,
    onNavigateToLogin: () -> Unit = {}
) {
    var phone by remember { mutableStateOf("") }
    var verificationCode by remember { mutableStateOf("") }
    var newPassword by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }
    var showSuccessAnimation by remember { mutableStateOf(false) }

    // 验证码倒计时
    var countdownSeconds by remember { mutableStateOf(0) }
    var isCountingDown by remember { mutableStateOf(false) }

    val scope = rememberCoroutineScope()

    // 启动倒计时
    fun startCountdown() {
        isCountingDown = true
        countdownSeconds = 60
        scope.launch {
            while (countdownSeconds > 0) {
                delay(1000)
                countdownSeconds--
            }
            isCountingDown = false
        }
    }

    // 成功动画状态
    val successScale = remember { Animatable(0f) }

    // 监听重置状态
    LaunchedEffect(resetState) {
        isLoading = resetState is UiDataState.Loading

        if (resetState is UiDataState.Success) {
            // 触发成功动画
            showSuccessAnimation = true
            successScale.animateTo(
                targetValue = 1f,
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessLow
                )
            )
            delay(1500)
            onNavigateToLogin()
        }
    }

    Box(
        modifier = modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(BackgroundLight)
                .padding(horizontal = page_margin_horizontal),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(spacing_xl))

            // ==================== 说明文字区域 ====================
            ForgotPasswordDescriptionSection()

            Spacer(modifier = Modifier.height(spacing_2xl))

            // ==================== 表单卡片 ====================
            ForgotPasswordFormCard(
                phone = phone,
                onPhoneChange = { phone = it },
                verificationCode = verificationCode,
                onVerificationCodeChange = { verificationCode = it },
                newPassword = newPassword,
                onNewPasswordChange = { newPassword = it },
                confirmPassword = confirmPassword,
                onConfirmPasswordChange = { confirmPassword = it },
                isLoading = isLoading,
                countdownSeconds = countdownSeconds,
                isCountingDown = isCountingDown,
                onSendVerificationCode = {
                    onSendVerificationCode(phone)
                    startCountdown()
                },
                onResetPassword = {
                    onResetPassword(phone, verificationCode, newPassword)
                },
                isPhoneValid = phone.isValidPhoneNumber()
            )

            Spacer(modifier = Modifier.weight(1f))

            // ==================== 底部返回登录链接 ====================
            ForgotPasswordFooterSection(onNavigateToLogin = onNavigateToLogin)

            Spacer(modifier = Modifier.height(spacing_2xl))
        }

        // 重置成功动画覆盖层
        AnimatedVisibility(
            visible = showSuccessAnimation,
            enter = fadeIn(animationSpec = tween(300)),
            exit = fadeOut(animationSpec = tween(300))
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(SuccessGreen.copy(alpha = 0.9f)),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(spacing_md)
                ) {
                    // 成功勾选动画
                    Box(
                        modifier = Modifier
                            .size(100.dp)
                            .scale(successScale.value)
                            .clip(RoundedCornerShape(corner_full))
                            .background(Color.White),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.CheckCircle,
                            contentDescription = "重置成功",
                            tint = SuccessGreen,
                            modifier = Modifier.size(60.dp)
                        )
                    }

                    Text(
                        text = "密码重置成功",
                        style = MaterialTheme.typography.titleLarge,
                        color = Color.White,
                        fontWeight = FontWeight.SemiBold
                    )

                    Text(
                        text = "请使用新密码登录",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.White.copy(alpha = 0.9f)
                    )
                }
            }
        }
    }
}

/**
 * 忘记密码页面说明文字区域
 */
@Composable
private fun ForgotPasswordDescriptionSection() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(spacing_sm)
    ) {
        Text(
            text = "重置密码",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            color = TextPrimaryLight,
            fontSize = text_h2_size
        )

        Text(
            text = "请输入手机号，我们将发送验证码帮您重置密码",
            style = MaterialTheme.typography.bodyMedium,
            color = TextSecondaryLight,
            textAlign = TextAlign.Center,
            fontSize = text_body_size
        )
    }
}

/**
 * 忘记密码表单卡片
 */
@Composable
private fun ForgotPasswordFormCard(
    phone: String,
    onPhoneChange: (String) -> Unit,
    verificationCode: String,
    onVerificationCodeChange: (String) -> Unit,
    newPassword: String,
    onNewPasswordChange: (String) -> Unit,
    confirmPassword: String,
    onConfirmPasswordChange: (String) -> Unit,
    isLoading: Boolean,
    countdownSeconds: Int,
    isCountingDown: Boolean,
    onSendVerificationCode: () -> Unit,
    onResetPassword: () -> Unit,
    isPhoneValid: Boolean
) {
    val focusManager = LocalFocusManager.current
    var newPasswordVisible by remember { mutableStateOf(false) }
    var confirmPasswordVisible by remember { mutableStateOf(false) }

    // 手机号输入过滤：只允许数字，最多11位
    val filteredPhoneChange: (String) -> Unit = { newValue ->
        val filtered = newValue.filter { it.isDigit() }.take(11)
        onPhoneChange(filtered)
    }

    // 验证码输入过滤：只允许数字，最多6位
    val filteredCodeChange: (String) -> Unit = { newValue ->
        val filtered = newValue.filter { it.isDigit() }.take(6)
        onVerificationCodeChange(filtered)
    }

    // 检查表单是否有效
    val isFormValid = isPhoneValid &&
            verificationCode.length == 6 &&
            newPassword.length >= 6 &&
            newPassword == confirmPassword

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(
                elevation = elevation_lg,
                shape = RoundedCornerShape(corner_md),
                ambientColor = ShadowLight.copy(alpha = 0.15f),
                spotColor = ShadowLight.copy(alpha = 0.25f)
            )
            .clip(RoundedCornerShape(corner_md))
            .background(SurfaceLight)
            .padding(card_padding_large)
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(spacing_lg)
        ) {
            // 手机号输入框（带获取验证码按钮）
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(spacing_md),
                verticalAlignment = Alignment.Top
            ) {
                OutlinedTextField(
                    value = phone,
                    onValueChange = filteredPhoneChange,
                    label = { Text("手机号") },
                    placeholder = { Text("请输入11位手机号") },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Phone,
                        imeAction = ImeAction.Next
                    ),
                    keyboardActions = KeyboardActions(
                        onNext = { focusManager.moveFocus(FocusDirection.Down) }
                    ),
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(corner_sm),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = PrimaryBlue,
                        unfocusedBorderColor = DividerLight,
                        focusedLabelColor = PrimaryBlue,
                        unfocusedLabelColor = TextTertiaryLight,
                        errorBorderColor = ErrorRed
                    ),
                    isError = phone.isNotEmpty() && !isPhoneValid,
                    supportingText = {
                        if (phone.isNotEmpty() && !isPhoneValid) {
                            Text(
                                text = "请输入正确的11位手机号",
                                color = ErrorRed,
                                style = MaterialTheme.typography.bodySmall
                            )
                        }
                    }
                )

                // 获取验证码按钮
                Button(
                    onClick = onSendVerificationCode,
                    modifier = Modifier
                        .height(56.dp)
                        .width(120.dp),
                    shape = RoundedCornerShape(corner_sm),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = PrimaryBlue,
                        disabledContainerColor = ButtonDisabledBg
                    ),
                    enabled = !isCountingDown && isPhoneValid && !isLoading
                ) {
                    Text(
                        text = if (isCountingDown) "${countdownSeconds}s" else "获取验证码",
                        style = MaterialTheme.typography.bodySmall,
                        fontWeight = FontWeight.Medium,
                        fontSize = text_caption_size
                    )
                }
            }

            // 验证码输入框
            OutlinedTextField(
                value = verificationCode,
                onValueChange = filteredCodeChange,
                label = { Text("验证码") },
                placeholder = { Text("请输入6位验证码") },
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = { focusManager.moveFocus(FocusDirection.Down) }
                ),
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(corner_sm),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = PrimaryBlue,
                    unfocusedBorderColor = DividerLight,
                    focusedLabelColor = PrimaryBlue,
                    unfocusedLabelColor = TextTertiaryLight
                ),
                isError = verificationCode.isNotEmpty() && verificationCode.length != 6,
                supportingText = {
                    if (verificationCode.isNotEmpty() && verificationCode.length != 6) {
                        Text(
                            text = "验证码为6位数字",
                            color = ErrorRed,
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }
            )

            // 新密码输入框
            OutlinedTextField(
                value = newPassword,
                onValueChange = onNewPasswordChange,
                label = { Text("新密码") },
                placeholder = { Text("请设置6-20位新密码") },
                singleLine = true,
                visualTransformation = if (newPasswordVisible) {
                    VisualTransformation.None
                } else {
                    PasswordVisualTransformation()
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = { focusManager.moveFocus(FocusDirection.Down) }
                ),
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(corner_sm),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = PrimaryBlue,
                    unfocusedBorderColor = DividerLight,
                    focusedLabelColor = PrimaryBlue,
                    unfocusedLabelColor = TextTertiaryLight
                ),
                trailingIcon = {
                    IconButton(
                        onClick = { newPasswordVisible = !newPasswordVisible }
                    ) {
                        Text(
                            text = if (newPasswordVisible) "隐藏" else "显示",
                            style = MaterialTheme.typography.bodySmall,
                            color = PrimaryBlue
                        )
                    }
                },
                isError = newPassword.isNotEmpty() && newPassword.length < 6,
                supportingText = {
                    if (newPassword.isNotEmpty() && newPassword.length < 6) {
                        Text(
                            text = "密码长度不能少于6位",
                            color = ErrorRed,
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }
            )

            // 确认新密码输入框
            OutlinedTextField(
                value = confirmPassword,
                onValueChange = onConfirmPasswordChange,
                label = { Text("确认新密码") },
                placeholder = { Text("请再次输入新密码") },
                singleLine = true,
                visualTransformation = if (confirmPasswordVisible) {
                    VisualTransformation.None
                } else {
                    PasswordVisualTransformation()
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        focusManager.clearFocus()
                        if (isFormValid) {
                            onResetPassword()
                        }
                    }
                ),
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(corner_sm),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = PrimaryBlue,
                    unfocusedBorderColor = DividerLight,
                    focusedLabelColor = PrimaryBlue,
                    unfocusedLabelColor = TextTertiaryLight
                ),
                trailingIcon = {
                    IconButton(
                        onClick = { confirmPasswordVisible = !confirmPasswordVisible }
                    ) {
                        Text(
                            text = if (confirmPasswordVisible) "隐藏" else "显示",
                            style = MaterialTheme.typography.bodySmall,
                            color = PrimaryBlue
                        )
                    }
                },
                isError = confirmPassword.isNotEmpty() && confirmPassword != newPassword,
                supportingText = {
                    if (confirmPassword.isNotEmpty() && confirmPassword != newPassword) {
                        Text(
                            text = "两次输入的密码不一致",
                            color = ErrorRed,
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }
            )

            Spacer(modifier = Modifier.height(spacing_sm))

            // 重置密码按钮
            Button(
                onClick = {
                    focusManager.clearFocus()
                    onResetPassword()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(corner_sm),
                colors = ButtonDefaults.buttonColors(
                    containerColor = PrimaryBlue,
                    disabledContainerColor = PrimaryBlue.copy(alpha = 0.5f)
                ),
                enabled = !isLoading && isFormValid
            ) {
                if (isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(24.dp),
                        color = Color.White,
                        strokeWidth = 2.dp
                    )
                } else {
                    Text(
                        text = "重置密码",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = text_body_large_size
                    )
                }
            }
        }
    }
}

/**
 * 忘记密码页面底部操作区域
 */
@Composable
private fun ForgotPasswordFooterSection(
    onNavigateToLogin: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "想起密码了？",
            style = MaterialTheme.typography.bodyMedium,
            color = TextSecondaryLight,
            fontSize = text_body_size
        )
        TextButton(
            onClick = onNavigateToLogin,
            contentPadding = PaddingValues(horizontal = 4.dp)
        ) {
            Text(
                text = "返回登录",
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.SemiBold,
                color = PrimaryBlue,
                fontSize = text_body_size
            )
        }
    }
}

/**
 * 手机号格式验证扩展函数
 * 规则：11位数字，以1开头，第二位为3-9
 */
private fun String.isValidPhoneNumber(): Boolean {
    return matches(Regex("^1[3-9]\\d{9}$"))
}

// ==================== Preview 函数 ====================

@Preview(showBackground = true, name = "忘记密码页面-默认状态", backgroundColor = 0xFFF0F4F8)
@Composable
fun ForgotPasswordScreenPreview() {
    WuHengTheme {
        ForgotPasswordContent(
            resetState = UiDataState.Idle,
            onSendVerificationCode = {},
            onResetPassword = { _, _, _ -> }
        )
    }
}

@Preview(showBackground = true, name = "忘记密码页面-加载中", backgroundColor = 0xFFF0F4F8)
@Composable
fun ForgotPasswordScreenLoadingPreview() {
    WuHengTheme {
        ForgotPasswordContent(
            resetState = UiDataState.Loading,
            onSendVerificationCode = {},
            onResetPassword = { _, _, _ -> }
        )
    }
}

@Preview(showBackground = true, name = "忘记密码页面-错误状态", backgroundColor = 0xFFF0F4F8)
@Composable
fun ForgotPasswordScreenErrorPreview() {
    WuHengTheme {
        ForgotPasswordContent(
            resetState = UiDataState.Error(
                com.wuheng.smart.data.network.AppException.BusinessError(-1, "验证码错误")
            ),
            onSendVerificationCode = {},
            onResetPassword = { _, _, _ -> }
        )
    }
}

@Preview(showBackground = true, name = "忘记密码页面-成功状态", backgroundColor = 0xFFF0F4F8)
@Composable
fun ForgotPasswordScreenSuccessPreview() {
    WuHengTheme {
        ForgotPasswordContent(
            resetState = UiDataState.Success(Unit),
            onSendVerificationCode = {},
            onResetPassword = { _, _, _ -> }
        )
    }
}
