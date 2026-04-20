@file:OptIn(ExperimentalMaterial3Api::class)

package com.wuheng.smart.presentation.login

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
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
@Composable
fun LoginScreen(
    viewModel: LoginViewModel = hiltViewModel(),
    onNavigateToHome: () -> Unit = {},
    onNavigateToRegister: () -> Unit = {},
    onNavigateToForgotPassword: () -> Unit = {}
) {
    val loginState by viewModel.loginState.collectAsStateWithLifecycle()
    val validationError by viewModel.validationError.collectAsStateWithLifecycle()
    val savedPhone by viewModel.savedPhone.collectAsStateWithLifecycle()
    val savedPassword by viewModel.savedPassword.collectAsStateWithLifecycle()
    val isRememberPasswordEnabled by viewModel.isRememberPassword.collectAsStateWithLifecycle()

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
        LoginContent(
            modifier = Modifier.padding(paddingValues),
            loginState = loginState,
            savedPhone = savedPhone,
            savedPassword = savedPassword,
            isRememberPasswordEnabled = isRememberPasswordEnabled,
            onLogin = { phone, password, rememberPassword ->
                viewModel.login(phone, password, rememberPassword)
            },
            onNavigateToHome = onNavigateToHome,
            onNavigateToRegister = onNavigateToRegister,
            onNavigateToForgotPassword = onNavigateToForgotPassword
        )
    }
}

/**
 * 登录页面内容
 */
@Composable
fun LoginContent(
    modifier: Modifier = Modifier,
    loginState: UiDataState<Unit>,
    savedPhone: String = "",
    savedPassword: String = "",
    isRememberPasswordEnabled: Boolean = false,
    onLogin: (String, String, Boolean) -> Unit,
    onNavigateToHome: () -> Unit = {},
    onNavigateToRegister: () -> Unit = {},
    onNavigateToForgotPassword: () -> Unit = {}
) {
    var phone by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var rememberPassword by remember { mutableStateOf(false) }
    var isLoading by remember { mutableStateOf(false) }
    var showSuccessAnimation by remember { mutableStateOf(false) }

    // 初始化时填充保存的登录信息
    LaunchedEffect(savedPhone, savedPassword, isRememberPasswordEnabled) {
        if (phone.isEmpty() && savedPhone.isNotEmpty()) {
            phone = savedPhone
        }
        if (password.isEmpty() && savedPassword.isNotEmpty() && isRememberPasswordEnabled) {
            password = savedPassword
            rememberPassword = true
        }
    }

    // 登录成功动画状态
    val successScale = remember { Animatable(0f) }
    val scope = rememberCoroutineScope()

    // 监听登录状态
    LaunchedEffect(loginState) {
        isLoading = loginState is UiDataState.Loading

        if (loginState is UiDataState.Success) {
            // 触发成功动画
            showSuccessAnimation = true
            successScale.animateTo(
                targetValue = 1f,
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessLow
                )
            )
            delay(800)
            onNavigateToHome()
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
            Spacer(modifier = Modifier.height(80.dp))

            // ==================== Logo区域 ====================
            LoginLogoSection()

            Spacer(modifier = Modifier.height(48.dp))

            // ==================== 登录表单卡片 ====================
            LoginFormCard(
                phone = phone,
                onPhoneChange = { phone = it },
                password = password,
                onPasswordChange = { password = it },
                rememberPassword = rememberPassword,
                onRememberPasswordChange = { rememberPassword = it },
                isLoading = isLoading,
                onLogin = { onLogin(phone, password, rememberPassword) },
                isPhoneValid = phone.isValidPhoneNumber()
            )

            Spacer(modifier = Modifier.height(24.dp))

            // ==================== 底部操作区域 ====================
            LoginFooterSection(
                onForgotPasswordClick = onNavigateToForgotPassword,
                onRegisterClick = onNavigateToRegister
            )

            Spacer(modifier = Modifier.weight(1f))
        }

        // 登录成功动画覆盖层
        AnimatedVisibility(
            visible = showSuccessAnimation,
            enter = fadeIn(animationSpec = tween(300)),
            exit = fadeOut(animationSpec = tween(300))
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(PrimaryBlue.copy(alpha = 0.9f)),
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
                            contentDescription = "登录成功",
                            tint = SuccessGreen,
                            modifier = Modifier.size(60.dp)
                        )
                    }

                    Text(
                        text = "登录成功",
                        style = MaterialTheme.typography.titleLarge,
                        color = Color.White,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }
    }
}

/**
 * 登录页面Logo区域
 */
@Composable
private fun LoginLogoSection() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(spacing_md)
    ) {
        // Logo容器
        Box(
            modifier = Modifier
                .size(100.dp)
                .shadow(
                    elevation = elevation_lg,
                    shape = RoundedCornerShape(corner_xl),
                    ambientColor = PrimaryBlue.copy(alpha = 0.1f),
                    spotColor = PrimaryBlue.copy(alpha = 0.2f)
                )
                .clip(RoundedCornerShape(corner_xl))
                .background(SurfaceLight),
            contentAlignment = Alignment.Center
        ) {
            // Logo占位符
            Text(
                text = "五恒",
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold,
                color = PrimaryBlue
            )
        }

        // 应用名称
        Text(
            text = "新宜能五恒系统",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.SemiBold,
            color = TextPrimaryLight,
            fontSize = text_h1_size
        )

        // 副标题
        Text(
            text = "智能舒适家居控制平台",
            style = MaterialTheme.typography.bodyMedium,
            color = TextSecondaryLight,
            fontSize = text_body_size
        )
    }
}

/**
 * 登录表单卡片
 */
@Composable
private fun LoginFormCard(
    phone: String,
    onPhoneChange: (String) -> Unit,
    password: String,
    onPasswordChange: (String) -> Unit,
    rememberPassword: Boolean,
    onRememberPasswordChange: (Boolean) -> Unit,
    isLoading: Boolean,
    onLogin: () -> Unit,
    isPhoneValid: Boolean
) {
    val focusManager = LocalFocusManager.current
    var passwordVisible by remember { mutableStateOf(false) }

    // 手机号输入过滤：只允许数字，最多11位
    val filteredPhoneChange: (String) -> Unit = { newValue ->
        val filtered = newValue.filter { it.isDigit() }.take(11)
        onPhoneChange(filtered)
    }

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
            // 标题
            Text(
                text = "欢迎登录",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.SemiBold,
                color = TextPrimaryLight,
                fontSize = text_h2_size
            )

            // 手机号输入框
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
                modifier = Modifier.fillMaxWidth(),
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
                },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.CheckCircle,
                        contentDescription = null,
                        tint = if (isPhoneValid) SuccessGreen else TextTertiaryLight
                    )
                }
            )

            // 密码输入框
            OutlinedTextField(
                value = password,
                onValueChange = onPasswordChange,
                label = { Text("密码") },
                placeholder = { Text("请输入密码") },
                singleLine = true,
                visualTransformation = if (passwordVisible) {
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
                        if (!isLoading && phone.isValidPhoneNumber() && password.isNotBlank()) {
                            onLogin()
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
                        onClick = { passwordVisible = !passwordVisible }
                    ) {
                        Text(
                            text = if (passwordVisible) "隐藏" else "显示",
                            style = MaterialTheme.typography.bodySmall,
                            color = PrimaryBlue
                        )
                    }
                }
            )

            // 记住密码选项
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = rememberPassword,
                    onCheckedChange = onRememberPasswordChange,
                    colors = CheckboxDefaults.colors(
                        checkedColor = PrimaryBlue,
                        uncheckedColor = TextTertiaryLight
                    )
                )
                Text(
                    text = "记住密码",
                    style = MaterialTheme.typography.bodyMedium,
                    color = TextSecondaryLight,
                    modifier = Modifier.clickable { onRememberPasswordChange(!rememberPassword) }
                )
            }

            Spacer(modifier = Modifier.height(spacing_sm))

            // 登录按钮
            Button(
                onClick = {
                    focusManager.clearFocus()
                    onLogin()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(corner_sm),
                colors = ButtonDefaults.buttonColors(
                    containerColor = PrimaryBlue,
                    disabledContainerColor = PrimaryBlue.copy(alpha = 0.5f)
                ),
                enabled = !isLoading && phone.isValidPhoneNumber() && password.isNotBlank()
            ) {
                if (isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(24.dp),
                        color = Color.White,
                        strokeWidth = 2.dp
                    )
                } else {
                    Text(
                        text = "登录",
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
 * 登录页面底部操作区域
 */
@Composable
private fun LoginFooterSection(
    onForgotPasswordClick: () -> Unit,
    onRegisterClick: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // 忘记密码
        TextButton(
            onClick = onForgotPasswordClick
        ) {
            Text(
                text = "忘记密码？",
                style = MaterialTheme.typography.bodyMedium,
                color = PrimaryBlue,
                fontSize = text_body_size
            )
        }

        // 注册账号
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(spacing_xs)
        ) {
            Text(
                text = "还没有账号？",
                style = MaterialTheme.typography.bodyMedium,
                color = TextSecondaryLight,
                fontSize = text_body_size
            )
            TextButton(
                onClick = onRegisterClick,
                contentPadding = PaddingValues(horizontal = 4.dp)
            ) {
                Text(
                    text = "立即注册",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = PrimaryBlue,
                    fontSize = text_body_size
                )
            }
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

@Preview(showBackground = true, name = "登录页面-亮色主题", backgroundColor = 0xFFF0F4F8)
@Composable
fun LoginScreenPreview() {
    WuHengTheme {
        LoginContent(
            loginState = UiDataState.Idle,
            onLogin = { _, _, _ -> }
        )
    }
}

@Preview(showBackground = true, name = "登录页面-加载中", backgroundColor = 0xFFF0F4F8)
@Composable
fun LoginScreenLoadingPreview() {
    WuHengTheme {
        LoginContent(
            loginState = UiDataState.Loading,
            onLogin = { _, _, _ -> }
        )
    }
}

@Preview(showBackground = true, name = "登录页面-错误状态", backgroundColor = 0xFFF0F4F8)
@Composable
fun LoginScreenErrorPreview() {
    WuHengTheme {
        LoginContent(
            loginState = UiDataState.Error(
                com.wuheng.smart.data.network.AppException.BusinessError(-1, "手机号或密码错误")
            ),
            onLogin = { _, _, _ -> }
        )
    }
}

@Preview(showBackground = true, name = "登录页面-成功状态", backgroundColor = 0xFFF0F4F8)
@Composable
fun LoginScreenSuccessPreview() {
    WuHengTheme {
        LoginContent(
            loginState = UiDataState.Success(Unit),
            onLogin = { _, _, _ -> }
        )
    }
}
