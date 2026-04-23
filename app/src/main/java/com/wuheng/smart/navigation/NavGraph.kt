package com.wuheng.smart.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.wuheng.smart.presentation.device.DeviceDetailScreen
import com.wuheng.smart.presentation.floorzone.FloorZoneScreen
import com.wuheng.smart.presentation.home.HomeScreen
import com.wuheng.smart.presentation.login.LoginScreen
import com.wuheng.smart.presentation.notification.NotificationScreen
import com.wuheng.smart.presentation.profile.ProfileScreen
import com.wuheng.smart.presentation.settings.SettingScreen
import com.wuheng.smart.presentation.splash.SplashScreen

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
            LoginScreen(
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
            // TODO: 注册页面
        }

        composable(NavigationRoutes.FORGOT_PASSWORD) {
            // TODO: 忘记密码页面
        }

        // ==================== 首页模块 ====================
        composable(NavigationRoutes.HOME) {
            HomeScreen(
                onNavigateToResidence = {
                    // 导航到房产选择页面
                },
                onNavigateToHouseList = {
                    // 导航到房屋列表
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
            val floorId = backStackEntry.arguments?.getInt("floorId")?.takeIf { it != -1 }
            FloorZoneScreen(
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
            FloorZoneScreen(
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
            val deviceId = backStackEntry.arguments?.getString("deviceId") ?: ""
            DeviceDetailScreen(
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
            // TODO: 设备编辑页面
        }

        // ==================== 通知模块 ====================
        composable(NavigationRoutes.NOTIFICATION) {
            NotificationScreen(
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
            // TODO: 通知详情页面
        }

        // ==================== 个人中心模块 ====================
        composable(NavigationRoutes.PROFILE) {
            ProfileScreen(
                onNavigateToNotifications = {
                    navController.navigate(NavigationRoutes.NOTIFICATION)
                },
                onNavigateToConsumables = {
                    // TODO: 导航到耗材管理
                },
                onNavigateToAbout = {
                    navController.navigate(NavigationRoutes.ABOUT)
                },
                onNavigateToPrivacy = {
                    navController.navigate(NavigationRoutes.PRIVACY_POLICY)
                }
            )
        }

        // ==================== 设置模块 ====================
        composable(NavigationRoutes.SETTINGS) {
            SettingScreen(
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
                onLogout = {
                    navController.navigate(NavigationRoutes.LOGIN) {
                        popUpTo(NavigationRoutes.HOME) { inclusive = true }
                    }
                }
            )
        }

        composable(NavigationRoutes.PRIVACY_POLICY) {
            // TODO: 隐私政策页面
        }

        composable(NavigationRoutes.USER_AGREEMENT) {
            // TODO: 用户协议页面
        }

        composable(NavigationRoutes.FEEDBACK) {
            // TODO: 意见反馈页面
        }

        composable(NavigationRoutes.ABOUT) {
            // TODO: 关于我们页面
        }

        // ==================== 帮助模块 ====================
        composable(NavigationRoutes.HELP) {
            // TODO: 帮助页面
        }

        composable(NavigationRoutes.FAQ) {
            // TODO: FAQ页面
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
