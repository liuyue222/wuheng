package com.wuheng.smart.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.wuheng.smart.presentation.about.AboutScreen
import com.wuheng.smart.presentation.about.AboutViewModel
import com.wuheng.smart.presentation.consumables.ConsumablesScreen
import com.wuheng.smart.presentation.consumables.ConsumablesViewModel
import com.wuheng.smart.presentation.device.DeviceDetailScreen
import com.wuheng.smart.presentation.device.DeviceDetailViewModel
import com.wuheng.smart.presentation.floorzone.FloorZoneScreen
import com.wuheng.smart.presentation.floorzone.FloorZoneViewModel
import com.wuheng.smart.presentation.forgotpassword.ForgotPasswordScreen
import com.wuheng.smart.presentation.forgotpassword.ForgotPasswordViewModel
import com.wuheng.smart.presentation.home.HomeScreen
import com.wuheng.smart.presentation.home.HomeViewModel
import com.wuheng.smart.presentation.login.LoginScreen
import com.wuheng.smart.presentation.login.LoginViewModel
import com.wuheng.smart.presentation.notification.NotificationScreen
import com.wuheng.smart.presentation.notification.NotificationViewModel
import com.wuheng.smart.presentation.privacypolicy.PrivacyPolicyScreen
import com.wuheng.smart.presentation.profile.ProfileScreen
import com.wuheng.smart.presentation.profile.ProfileViewModel
import com.wuheng.smart.presentation.climate.ClimateScreen
import com.wuheng.smart.presentation.climate.ClimateViewModel
import com.wuheng.smart.presentation.register.RegisterScreen
import com.wuheng.smart.presentation.register.RegisterViewModel
import com.wuheng.smart.presentation.settings.SettingScreen
import com.wuheng.smart.presentation.settings.SettingViewModel
import com.wuheng.smart.presentation.splash.SplashScreen
import com.wuheng.smart.presentation.water.WaterScreen
import com.wuheng.smart.presentation.water.WaterViewModel
import com.wuheng.smart.presentation.theme.*

/**
 * 应用导航图
 *
 * 定义所有页面路由和导航逻辑
 */
@Composable
fun NavGraph(
    navController: NavHostController,
    startDestination: String = NavigationRoutes.LOGIN,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        // ==================== 启动页 ====================
        composable("splash") {
            SplashScreen(
                onNavigateToLogin = {
                    navController.navigate(NavigationRoutes.LOGIN) {
                        popUpTo("splash") { inclusive = true }
                    }
                },
                onNavigateToHome = {
                    navController.navigate(NavigationRoutes.HOME) {
                        popUpTo("splash") { inclusive = true }
                    }
                }
            )
        }

        // ==================== 认证模块 ====================
        composable(NavigationRoutes.LOGIN) {
            val viewModel: LoginViewModel = hiltViewModel()
            LoginScreen(
                viewModel = viewModel,
                onNavigateToHome = {
                    navController.navigate(NavigationRoutes.HOME) {
                        popUpTo(NavigationRoutes.LOGIN) { inclusive = true }
                    }
                },
                onNavigateToRegister = {
                    navController.navigate(NavigationRoutes.REGISTER)
                },
                onNavigateToForgotPassword = {
                    navController.navigate(NavigationRoutes.FORGOT_PASSWORD)
                }
            )
        }

        composable(NavigationRoutes.REGISTER) {
            val viewModel: RegisterViewModel = hiltViewModel()
            RegisterScreen(
                viewModel = viewModel,
                onNavigateToLogin = {
                    navController.popBackStack()
                },
                onNavigateToHome = {
                    navController.navigate(NavigationRoutes.HOME) {
                        popUpTo(NavigationRoutes.LOGIN) { inclusive = true }
                    }
                }
            )
        }

        composable(NavigationRoutes.FORGOT_PASSWORD) {
            val viewModel: ForgotPasswordViewModel = hiltViewModel()
            ForgotPasswordScreen(
                viewModel = viewModel,
                onNavigateBack = {
                    navController.popBackStack()
                },
                onNavigateToLogin = {
                    navController.navigate(NavigationRoutes.LOGIN) {
                        popUpTo(NavigationRoutes.FORGOT_PASSWORD) { inclusive = true }
                    }
                }
            )
        }

        // ==================== 首页模块 ====================
        composable(NavigationRoutes.HOME) {
            val viewModel: HomeViewModel = hiltViewModel()
            HomeScreen(
                viewModel = viewModel,
                onNavigateToResidence = {
                    // 导航到房产选择页面
                },
                onNavigateToHouseList = {
                    // 导航到房屋列表
                }
            )
        }

        // ==================== 冷暖系统模块 ====================
        composable(NavigationRoutes.CLIMATE) {
            val viewModel: ClimateViewModel = hiltViewModel()
            ClimateScreen(
                viewModel = viewModel,
                onNavigateToFloorDetail = { floorId ->
                    navController.navigate(NavigationRoutes.floorZone(floorId.toIntOrNull()))
                }
            )
        }

        // ==================== 水系统模块 ====================
        composable(NavigationRoutes.WATER) {
            val viewModel: WaterViewModel = hiltViewModel()
            WaterScreen(
                viewModel = viewModel,
                onNavigateToDurationPicker = {
                    // TODO: 导航到时长选择页面
                },
                onNavigateToFilterReplace = {
                    // TODO: 导航到滤芯更换页面
                }
            )
        }

        // ==================== 楼层区域模块 ====================
        composable(
            route = NavigationRoutes.FLOOR_ZONE_WITH_ARG,
            arguments = listOf(
                navArgument("floorId") {
                    type = NavType.IntType
                    defaultValue = -1
                }
            )
        ) { backStackEntry ->
            val viewModel: FloorZoneViewModel = hiltViewModel()
            val floorId = backStackEntry.arguments?.getInt("floorId")?.takeIf { it != -1 }
            FloorZoneScreen(
                viewModel = viewModel,
                floorId = floorId,
                onNavigateBack = {
                    navController.popBackStack()
                },
                onNavigateToDeviceDetail = { deviceId ->
                    navController.navigate(NavigationRoutes.deviceDetail(deviceId))
                }
            )
        }

        // 无参数的楼层区域页面
        composable(NavigationRoutes.FLOOR_ZONE) {
            val viewModel: FloorZoneViewModel = hiltViewModel()
            FloorZoneScreen(
                viewModel = viewModel,
                onNavigateBack = {
                    navController.popBackStack()
                },
                onNavigateToDeviceDetail = { deviceId ->
                    navController.navigate(NavigationRoutes.deviceDetail(deviceId))
                }
            )
        }

        // ==================== 设备模块 ====================
        composable(
            route = NavigationRoutes.DEVICE_DETAIL_WITH_ARG,
            arguments = listOf(
                navArgument("deviceId") {
                    type = NavType.StringType
                }
            )
        ) { backStackEntry ->
            val viewModel: DeviceDetailViewModel = hiltViewModel()
            val deviceId = backStackEntry.arguments?.getString("deviceId") ?: ""
            DeviceDetailScreen(
                viewModel = viewModel,
                deviceId = deviceId,
                onNavigateBack = {
                    navController.popBackStack()
                },
                onNavigateToEdit = { id ->
                    navController.navigate(NavigationRoutes.deviceEdit(id))
                }
            )
        }

        composable(
            route = NavigationRoutes.DEVICE_EDIT_WITH_ARG,
            arguments = listOf(
                navArgument("deviceId") {
                    type = NavType.StringType
                }
            )
        ) { backStackEntry ->
            val deviceId = backStackEntry.arguments?.getString("deviceId") ?: ""
            // 设备编辑页面 - 使用简单的占位页面
            DeviceEditPlaceholderScreen(
                deviceId = deviceId,
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }

        // ==================== 通知模块 ====================
        composable(NavigationRoutes.NOTIFICATION) {
            val viewModel: NotificationViewModel = hiltViewModel()
            NotificationScreen(
                viewModel = viewModel,
                onNavigateBack = {
                    navController.popBackStack()
                },
                onNavigateToNotificationDetail = { notificationId ->
                    navController.navigate(NavigationRoutes.notificationDetail(notificationId))
                }
            )
        }

        composable(
            route = NavigationRoutes.NOTIFICATION_DETAIL_WITH_ARG,
            arguments = listOf(
                navArgument("notificationId") {
                    type = NavType.StringType
                }
            )
        ) { backStackEntry ->
            val notificationId = backStackEntry.arguments?.getString("notificationId") ?: ""
            // 通知详情页面 - 使用简单的占位页面
            NotificationDetailPlaceholderScreen(
                notificationId = notificationId,
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }

        // ==================== 个人中心模块 ====================
        composable(NavigationRoutes.PROFILE) {
            val viewModel: ProfileViewModel = hiltViewModel()
            ProfileScreen(
                viewModel = viewModel,
                onNavigateToNotifications = {
                    navController.navigate(NavigationRoutes.NOTIFICATION)
                },
                onNavigateToConsumables = {
                    navController.navigate(NavigationRoutes.CONSUMABLES)
                },
                onNavigateToAbout = {
                    navController.navigate(NavigationRoutes.ABOUT)
                },
                onNavigateToPrivacy = {
                    navController.navigate(NavigationRoutes.PRIVACY_POLICY)
                }
            )
        }

        // ==================== 耗材管理模块 ====================
        composable(NavigationRoutes.CONSUMABLES) {
            val viewModel: ConsumablesViewModel = hiltViewModel()
            ConsumablesScreen(
                viewModel = viewModel,
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }

        // ==================== 设置模块 ====================
        composable(NavigationRoutes.SETTINGS) {
            val viewModel: SettingViewModel = hiltViewModel()
            SettingScreen(
                viewModel = viewModel,
                onNavigateBack = {
                    navController.popBackStack()
                },
                onNavigateToProfile = {
                    navController.navigate(NavigationRoutes.PROFILE)
                },
                onNavigateToPrivacyPolicy = {
                    navController.navigate(NavigationRoutes.PRIVACY_POLICY)
                },
                onNavigateToUserAgreement = {
                    navController.navigate(NavigationRoutes.USER_AGREEMENT)
                },
                onNavigateToFeedback = {
                    navController.navigate(NavigationRoutes.FEEDBACK)
                },
                onNavigateToAbout = {
                    navController.navigate(NavigationRoutes.ABOUT)
                },
                onNavigateToChangePassword = {
                    navController.navigate(NavigationRoutes.FORGOT_PASSWORD)
                },
                onNavigateToCustomerService = {
                    // 打开客服页面或拨打客服电话
                    // TODO: 实现客服功能
                },
                onLogout = {
                    navController.navigate(NavigationRoutes.LOGIN) {
                        popUpTo(NavigationRoutes.HOME) { inclusive = true }
                    }
                }
            )
        }

        composable(NavigationRoutes.PRIVACY_POLICY) {
            PrivacyPolicyScreen(
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }

        composable(NavigationRoutes.USER_AGREEMENT) {
            // 用户协议页面 - 复用PrivacyPolicyScreen的样式
            UserAgreementPlaceholderScreen(
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }

        composable(NavigationRoutes.FEEDBACK) {
            // 意见反馈页面
            FeedbackPlaceholderScreen(
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }

        composable(NavigationRoutes.ABOUT) {
            val viewModel: AboutViewModel = hiltViewModel()
            AboutScreen(
                viewModel = viewModel,
                onNavigateBack = {
                    navController.popBackStack()
                },
                onFunctionIntroClick = {
                    // TODO: 导航到功能介绍页面
                },
                onUserAgreementClick = {
                    navController.navigate(NavigationRoutes.USER_AGREEMENT)
                },
                onPrivacyPolicyClick = {
                    navController.navigate(NavigationRoutes.PRIVACY_POLICY)
                },
                onContactUsClick = {
                    // TODO: 导航到联系我们页面
                }
            )
        }

        // ==================== 帮助模块 ====================
        composable(NavigationRoutes.HELP) {
            // 帮助页面
            HelpPlaceholderScreen(
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }

        composable(NavigationRoutes.FAQ) {
            // FAQ页面
            FaqPlaceholderScreen(
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}

/**
 * 导航扩展函数
 */
fun NavHostController.navigateToDeviceDetail(deviceId: String) {
    navigate(NavigationRoutes.deviceDetail(deviceId))
}

fun NavHostController.navigateToFloorZone(floorId: Int? = null) {
    navigate(NavigationRoutes.floorZone(floorId))
}

fun NavHostController.navigateToNotification() {
    navigate(NavigationRoutes.NOTIFICATION)
}

fun NavHostController.navigateToSettings() {
    navigate(NavigationRoutes.SETTINGS)
}

// ==================== 占位页面组件 ====================

/**
 * 设备编辑占位页面
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DeviceEditPlaceholderScreen(
    deviceId: String,
    onNavigateBack: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("编辑设备", fontWeight = FontWeight.SemiBold) },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "返回")
                    }
                },
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = BackgroundLight
                )
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(BackgroundLight),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = "设备编辑",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = TextPrimaryLight
                )
                Text(
                    text = "设备ID: $deviceId",
                    style = MaterialTheme.typography.bodyMedium,
                    color = TextSecondaryLight
                )
                Text(
                    text = "功能开发中...",
                    style = MaterialTheme.typography.bodySmall,
                    color = TextTertiaryLight
                )
            }
        }
    }
}

/**
 * 通知详情占位页面
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun NotificationDetailPlaceholderScreen(
    notificationId: String,
    onNavigateBack: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("通知详情", fontWeight = FontWeight.SemiBold) },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "返回")
                    }
                },
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = BackgroundLight
                )
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(BackgroundLight),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = "通知详情",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = TextPrimaryLight
                )
                Text(
                    text = "通知ID: $notificationId",
                    style = MaterialTheme.typography.bodyMedium,
                    color = TextSecondaryLight
                )
                Text(
                    text = "功能开发中...",
                    style = MaterialTheme.typography.bodySmall,
                    color = TextTertiaryLight
                )
            }
        }
    }
}

/**
 * 用户协议占位页面
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun UserAgreementPlaceholderScreen(
    onNavigateBack: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("用户协议", fontWeight = FontWeight.SemiBold) },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "返回")
                    }
                },
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = BackgroundLight
                )
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
                .background(BackgroundLight)
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = "用户协议",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = TextPrimaryLight
                )
                Text(
                    text = "欢迎使用五恒智能控制系统！\n\n" +
                           "1. 服务条款\n" +
                           "用户在使用本应用时，应遵守国家相关法律法规。\n\n" +
                           "2. 隐私保护\n" +
                           "我们重视用户隐私保护，具体请查看隐私政策。\n\n" +
                           "3. 免责声明\n" +
                           "本应用提供的智能控制服务仅供参考，用户应自行判断使用。\n\n" +
                           "4. 协议修改\n" +
                           "我们保留随时修改本协议的权利，修改后的协议将在应用内公布。",
                    style = MaterialTheme.typography.bodyMedium,
                    color = TextSecondaryLight,
                    lineHeight = 24.sp
                )
            }
        }
    }
}

/**
 * 意见反馈占位页面
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun FeedbackPlaceholderScreen(
    onNavigateBack: () -> Unit
) {
    var feedbackText by remember { mutableStateOf("") }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("意见反馈", fontWeight = FontWeight.SemiBold) },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "返回")
                    }
                },
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = BackgroundLight
                )
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
                .background(BackgroundLight),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "请输入您的宝贵意见：",
                style = MaterialTheme.typography.bodyLarge,
                color = TextPrimaryLight
            )

            OutlinedTextField(
                value = feedbackText,
                onValueChange = { feedbackText = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                placeholder = { Text("请描述您遇到的问题或建议...") },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = PrimaryBlue,
                    unfocusedBorderColor = DividerLight
                )
            )
            
            Button(
                onClick = { 
                    // TODO: 提交反馈
                    onNavigateBack()
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = PrimaryBlue
                )
            ) {
                Text("提交反馈")
            }
        }
    }
}

/**
 * 帮助页面占位
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun HelpPlaceholderScreen(
    onNavigateBack: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("帮助中心", fontWeight = FontWeight.SemiBold) },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "返回")
                    }
                },
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = BackgroundLight
                )
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(BackgroundLight),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = "帮助中心",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = TextPrimaryLight
                )
                Text(
                    text = "功能开发中...",
                    style = MaterialTheme.typography.bodyMedium,
                    color = TextSecondaryLight
                )
            }
        }
    }
}

/**
 * FAQ页面占位
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun FaqPlaceholderScreen(
    onNavigateBack: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("常见问题", fontWeight = FontWeight.SemiBold) },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "返回")
                    }
                },
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = BackgroundLight
                )
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(BackgroundLight),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = "常见问题",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = TextPrimaryLight
                )
                Text(
                    text = "功能开发中...",
                    style = MaterialTheme.typography.bodyMedium,
                    color = TextSecondaryLight
                )
            }
        }
    }
}
