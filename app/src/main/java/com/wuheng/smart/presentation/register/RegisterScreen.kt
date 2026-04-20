@file:OptIn(ExperimentalMaterial3Api::class)

package com.wuheng.smart.presentation.register

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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
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
@Composable
fun RegisterScreen(
    viewModel: RegisterViewModel = hiltViewModel(),
    onNavigateToLogin: () -> Unit = {},
    onNavigateToHome: () -> Unit = {}
) {
    val registerState by viewModel.registerState.collectAsStateWithLifecycle()
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
            RegisterTopBar(
                onBackClick = onNavigateToLogin
            )
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
        RegisterContent(
            modifier = Modifier.padding(paddingValues),
            registerState = registerState,
            onRegister = { username, phone, password, realName, email ->
                viewModel.register(username, phone, password, realName, email)
            },
            onNavigateToLogin = onNavigateToLogin,
            onNavigateToHome = onNavigateToHome
        )
    }
}

/**
 * 注册页面顶部栏
 */
@Composable
private fun RegisterTopBar(
    onBackClick: () -> Unit
) {
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = "注册账号",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.SemiBold,
                color = TextPrimaryLight
            )
        },
        navigationIcon = {
            IconButton(onClick = onBackClick) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "返回",
                    tint = TextPrimaryLight
                )
            }
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = BackgroundLight
        )
    )
}

/**
 * 注册页面内容
 */
@Composable
private fun RegisterContent(
    modifier: Modifier = Modifier,
    registerState: UiDataState<Unit>,
    onRegister: (String, String, String, String, String) -> Unit,
    onNavigateToLogin: () -> Unit = {},
    onNavigateToHome: () -> Unit = {}
) {
    var username by remember { mutableStateOf("") }
    var mobile by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }
    var showSuccessAnimation by remember { mutableStateOf(false) }

    // 注册成功动画状态
    val successScale = remember { Animatable(0f) }
    val scope = rememberCoroutineScope()

    // 监听注册状态
    LaunchedEffect(registerState) {
        isLoading = registerState is UiDataState.Loading

        if (registerState is UiDataState.Success) {
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
                .verticalScroll(rememberScrollState())
                .padding(horizontal = page_margin_horizontal),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(24.dp))

            // ==================== Logo区域 ====================
            RegisterLogoSection()

            Spacer(modifier = Modifier.height(32.dp))

            // ==================== 注册表单卡片 ====================
            RegisterFormCard(
                username = username,
                onUsernameChange = { username = it },
                mobile = mobile,
                onMobileChange = { mobile = it },
                password = password,
                onPasswordChange = { password = it },
                confirmPassword = confirmPassword,
                onConfirmPasswordChange = { confirmPassword = it },
                email = email,
                onEmailChange = { email = it },
                isLoading = isLoading,
                onRegister = {
                    // 按钮enabled状态已验证表单，直接调用注册
                    onRegister(username, mobile, password, "", email)
                }
            )

            Spacer(modifier = Modifier.height(24.dp))

            // ==================== 底部操作区域 ====================
            RegisterFooterSection(
                onLoginClick = onNavigateToLogin
            )

            Spacer(modifier = Modifier.height(32.dp))
        }

        // 注册成功动画覆盖层
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
                            contentDescription = "注册成功",
                            tint = SuccessGreen,
                            modifier = Modifier.size(60.dp)
                        )
                    }

                    Text(
                        text = "注册成功",
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
 * 注册页面Logo区域
 */
@Composable
private fun RegisterLogoSection() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(spacing_md)
    ) {
        // Logo容器
        Box(
            modifier = Modifier
                .size(80.dp)
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
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = PrimaryBlue
            )
        }

        // 应用名称
        Text(
            text = "创建新账号",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.SemiBold,
            color = TextPrimaryLight,
            fontSize = text_h2_size
        )

        // 副标题
        Text(
            text = "填写以下信息完成注册",
            style = MaterialTheme.typography.bodyMedium,
            color = TextSecondaryLight,
            fontSize = text_body_size
        )
    }
}

/**
 * 注册表单卡片
 */
@Composable
private fun RegisterFormCard(
    username: String,
    onUsernameChange: (String) -> Unit,
    mobile: String,
    onMobileChange: (String) -> Unit,
    password: String,
    onPasswordChange: (String) -> Unit,
    confirmPassword: String,
    onConfirmPasswordChange: (String) -> Unit,
    email: String,
    onEmailChange: (String) -> Unit,
    isLoading: Boolean,
    onRegister: () -> Unit
) {
    val focusManager = LocalFocusManager.current
    var passwordVisible by remember { mutableStateOf(false) }
    var confirmPasswordVisible by remember { mutableStateOf(false) }

    // 手机号输入过滤：只允许数字，最多11位
    val filteredMobileChange: (String) -> Unit = { newValue ->
        val filtered = newValue.filter { it.isDigit() }.take(11)
        onMobileChange(filtered)
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
            verticalArrangement = Arrangement.spacedBy(spacing_md)
        ) {
            // 用户名输入框
            OutlinedTextField(
                value = username,
                onValueChange = { onUsernameChange(it.take(20)) },
                label = { Text("用户名") },
                placeholder = { Text("请输入用户名（3-20字符）") },
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
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
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = null,
                        tint = TextTertiaryLight
                    )
                }
            )

            // 手机号输入框
            OutlinedTextField(
                value = mobile,
                onValueChange = filteredMobileChange,
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
                    unfocusedLabelColor = TextTertiaryLight
                ),
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Phone,
                        contentDescription = null,
                        tint = TextTertiaryLight
                    )
                }
            )

            // 密码输入框
            OutlinedTextField(
                value = password,
                onValueChange = onPasswordChange,
                label = { Text("密码") },
                placeholder = { Text("请输入密码（6-20位）") },
                singleLine = true,
                visualTransformation = if (passwordVisible) {
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
                    TextButton(
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

            // 确认密码输入框
            OutlinedTextField(
                value = confirmPassword,
                onValueChange = onConfirmPasswordChange,
                label = { Text("确认密码") },
                placeholder = { Text("请再次输入密码") },
                singleLine = true,
                visualTransformation = if (confirmPasswordVisible) {
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
                    TextButton(
                        onClick = { confirmPasswordVisible = !confirmPasswordVisible }
                    ) {
                        Text(
                            text = if (confirmPasswordVisible) "隐藏" else "显示",
                            style = MaterialTheme.typography.bodySmall,
                            color = PrimaryBlue
                        )
                    }
                }
            )

            // 邮箱输入框（可选）
            OutlinedTextField(
                value = email,
                onValueChange = onEmailChange,
                label = { Text("邮箱（可选）") },
                placeholder = { Text("请输入邮箱地址") },
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        focusManager.clearFocus()
                        if (!isLoading && isFormValid(username, mobile, password, confirmPassword)) {
                            onRegister()
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
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Email,
                        contentDescription = null,
                        tint = TextTertiaryLight
                    )
                }
            )

            Spacer(modifier = Modifier.height(spacing_sm))

            // 注册按钮
            Button(
                onClick = {
                    focusManager.clearFocus()
                    onRegister()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(corner_sm),
                colors = ButtonDefaults.buttonColors(
                    containerColor = PrimaryBlue,
                    disabledContainerColor = PrimaryBlue.copy(alpha = 0.5f)
                ),
                enabled = !isLoading && isFormValid(username, mobile, password, confirmPassword)
            ) {
                if (isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(24.dp),
                        color = Color.White,
                        strokeWidth = 2.dp
                    )
                } else {
                    Text(
                        text = "注册",
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
 * 检查表单是否有效
 */
private fun isFormValid(
    username: String,
    mobile: String,
    password: String,
    confirmPassword: String
): Boolean {
    return username.length >= 3 &&
            mobile.matches(Regex("^1[3-9]\\d{9}$")) &&
            password.length >= 6 &&
            password == confirmPassword
}

/**
 * 注册页面底部操作区域
 */
@Composable
private fun RegisterFooterSection(
    onLoginClick: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "已有账号？",
            style = MaterialTheme.typography.bodyMedium,
            color = TextSecondaryLight,
            fontSize = text_body_size
        )
        TextButton(
            onClick = onLoginClick,
            contentPadding = PaddingValues(horizontal = 4.dp)
        ) {
            Text(
                text = "立即登录",
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.SemiBold,
                color = PrimaryBlue,
                fontSize = text_body_size
            )
        }
    }
}

// ==================== Preview 函数 ====================

@Preview(showBackground = true, name = "注册页面-亮色主题", backgroundColor = 0xFFF0F4F8)
@Composable
fun RegisterScreenPreview() {
    WuHengTheme {
        RegisterContent(
            registerState = UiDataState.Idle,
            onRegister = { _, _, _, _, _ -> }
        )
    }
}

@Preview(showBackground = true, name = "注册页面-加载中", backgroundColor = 0xFFF0F4F8)
@Composable
fun RegisterScreenLoadingPreview() {
    WuHengTheme {
        RegisterContent(
            registerState = UiDataState.Loading,
            onRegister = { _, _, _, _, _ -> }
        )
    }
}

@Preview(showBackground = true, name = "注册页面-成功状态", backgroundColor = 0xFFF0F4F8)
@Composable
fun RegisterScreenSuccessPreview() {
    WuHengTheme {
        RegisterContent(
            registerState = UiDataState.Success(Unit),
            onRegister = { _, _, _, _, _ -> }
        )
    }
}
