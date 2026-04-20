package com.wuheng.smart.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.wuheng.smart.presentation.about.AboutScreen
import com.wuheng.smart.presentation.climate.ClimateScreen
import com.wuheng.smart.presentation.consumables.ConsumablesScreen
import com.wuheng.smart.presentation.device.DeviceDetailScreen
import com.wuheng.smart.presentation.floorzone.FloorZoneScreen
import com.wuheng.smart.presentation.forgotpassword.ForgotPasswordScreen
import com.wuheng.smart.presentation.home.HomeScreen
import com.wuheng.smart.presentation.login.LoginScreen
import com.wuheng.smart.presentation.profile.ProfileScreen
import com.wuheng.smart.presentation.register.RegisterScreen
import com.wuheng.smart.presentation.water.WaterScreen

/**
 * 应用导航图
 *
 * 定义所有页面路由和导航逻辑
 * 使用 Jetpack Navigation Compose 实现
 */
@Composable
fun WuHengNavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    startDestination: String = NavigationRoutes.HOME
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        // ==================== 主Tab页面 ====================

        // 首页
        composable(NavigationRoutes.HOME) {
            HomeScreen(
                onNavigateToResidence = {
                    // TODO: 导航到住宅详情页面
                }
            )
        }

        // 冷暖系统页面
        composable(NavigationRoutes.CLIMATE) {
            ClimateScreen(
                onNavigateToFloorDetail = { floorId ->
                    navController.navigate(NavigationRoutes.floorZone(floorId.toIntOrNull()))
                }
            )
        }

        // 水系统页面
        composable(NavigationRoutes.WATER) {
            WaterScreen()
        }

        // 个人中心页面
        composable(NavigationRoutes.PROFILE) {
            ProfileScreen(
                onNavigateToNotifications = {
                    // TODO: 导航到通知页面
                },
                onNavigateToServiceSelect = {
                    // TODO: 导航到服务选择页面
                },
                onNavigateToConsumables = {
                    navController.navigate(NavigationRoutes.CONSUMABLES)
                },
                onNavigateToAbout = {
                    navController.navigate(NavigationRoutes.ABOUT)
                },
                onNavigateToPrivacy = {
                    // TODO: 导航到隐私政策页面
                }
            )
        }

        // ==================== 首页子页面 ====================

        // 设备详情页
        composable(
            route = NavigationRoutes.DEVICE_DETAIL,
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
                    // TODO: 导航到设备编辑页面
                }
            )
        }

        // 服务详情页
        composable(
            route = NavigationRoutes.SERVICE_DETAIL,
            arguments = listOf(
                navArgument("serviceType") {
                    type = NavType.StringType
                }
            )
        ) { backStackEntry ->
            val serviceType = backStackEntry.arguments?.getString("serviceType") ?: ""
            // TODO: 创建 ServiceDetailScreen
            // ServiceDetailScreen(
            //     serviceType = serviceType,
            //     onNavigateBack = { navController.popBackStack() }
            // )
        }

        // 场景编辑页
        composable(
            route = NavigationRoutes.SCENE_EDIT,
            arguments = listOf(
                navArgument("sceneId") {
                    type = NavType.StringType
                }
            )
        ) { backStackEntry ->
            val sceneId = backStackEntry.arguments?.getString("sceneId") ?: ""
            // TODO: 创建 SceneEditScreen
            // SceneEditScreen(
            //     sceneId = sceneId,
            //     onNavigateBack = { navController.popBackStack() }
            // )
        }

        // 环境数据详情页
        composable(NavigationRoutes.ENVIRONMENT_DETAIL) {
            // TODO: 创建 EnvironmentDetailScreen
            // EnvironmentDetailScreen(
            //     onNavigateBack = { navController.popBackStack() }
            // )
        }

        // ==================== 冷暖系统子页面 ====================

        // 楼层区域页面
        composable(
            route = NavigationRoutes.FLOOR_ZONE,
            arguments = listOf(
                navArgument("floorId") {
                    type = NavType.IntType
                    defaultValue = -1
                }
            )
        ) { backStackEntry ->
            val floorId = backStackEntry.arguments?.getInt("floorId")
            FloorZoneScreen(
                floorId = if (floorId != null && floorId >= 0) floorId else null,
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }

        // 区域详情页
        composable(
            route = NavigationRoutes.CLIMATE_ZONE_DETAIL,
            arguments = listOf(
                navArgument("zoneId") {
                    type = NavType.StringType
                }
            )
        ) { backStackEntry ->
            val zoneId = backStackEntry.arguments?.getString("zoneId") ?: ""
            // TODO: 创建 ZoneDetailScreen
            // ZoneDetailScreen(
            //     zoneId = zoneId,
            //     onNavigateBack = { navController.popBackStack() }
            // )
        }

        // ==================== 水系统子页面 ====================

        // 水系统设备详情页
        composable(
            route = NavigationRoutes.WATER_DEVICE_DETAIL,
            arguments = listOf(
                navArgument("deviceId") {
                    type = NavType.StringType
                }
            )
        ) { backStackEntry ->
            val deviceId = backStackEntry.arguments?.getString("deviceId") ?: ""
            // TODO: 创建 WaterDeviceDetailScreen
            // WaterDeviceDetailScreen(
            //     deviceId = deviceId,
            //     onNavigateBack = { navController.popBackStack() }
            // )
        }

        // ==================== 个人中心子页面 ====================

        // 耗材使用进度页面
        composable(NavigationRoutes.CONSUMABLES) {
            ConsumablesScreen(
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }

        // 关于新宜能页面
        composable(NavigationRoutes.ABOUT) {
            AboutScreen(
                onNavigateBack = {
                    navController.popBackStack()
                },
                onFunctionIntroClick = {
                    // TODO: 导航到功能介绍页面
                },
                onUserAgreementClick = {
                    // TODO: 导航到用户协议页面
                },
                onPrivacyPolicyClick = {
                    // TODO: 导航到隐私政策页面
                },
                onContactUsClick = {
                    // TODO: 导航到联系我们页面
                }
            )
        }

        // 个人设置页面
        composable(NavigationRoutes.PROFILE_SETTINGS) {
            // TODO: 创建 SettingsScreen
            // SettingsScreen(
            //     onNavigateBack = { navController.popBackStack() }
            // )
        }

        // 编辑个人资料页面
        composable(NavigationRoutes.PROFILE_EDIT) {
            // TODO: 创建 EditProfileScreen
            // EditProfileScreen(
            //     onNavigateBack = { navController.popBackStack() }
            // )
        }

        // ==================== 认证页面 ====================

        // 登录页面
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

        // 注册页面
        composable(NavigationRoutes.REGISTER) {
            RegisterScreen(
                onNavigateToLogin = { navController.popBackStack() },
                onNavigateToHome = {
                    navController.navigate(NavigationRoutes.HOME) {
                        popUpTo(NavigationRoutes.LOGIN) { inclusive = true }
                    }
                }
            )
        }

        // 忘记密码页面
        composable(NavigationRoutes.FORGOT_PASSWORD) {
            ForgotPasswordScreen(
                onNavigateToLogin = { navController.popBackStack() }
            )
        }
    }
}

/**
 * 导航扩展函数
 */
object NavigationActions {

    /**
     * 导航到首页
     */
    fun NavHostController.navigateToHome() {
        navigate(NavigationRoutes.HOME) {
            popUpTo(graph.startDestinationId) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }

    /**
     * 导航到设备详情
     */
    fun NavHostController.navigateToDeviceDetail(deviceId: String) {
        navigate(NavigationRoutes.deviceDetail(deviceId))
    }

    /**
     * 导航到楼层区域
     */
    fun NavHostController.navigateToFloorZone(floorId: Int? = null) {
        navigate(NavigationRoutes.floorZone(floorId))
    }

    /**
     * 导航到耗材进度页面
     */
    fun NavHostController.navigateToConsumables() {
        navigate(NavigationRoutes.CONSUMABLES)
    }

    /**
     * 导航到关于页面
     */
    fun NavHostController.navigateToAbout() {
        navigate(NavigationRoutes.ABOUT)
    }

    /**
     * 导航到登录页面（清除回退栈）
     */
    fun NavHostController.navigateToLogin() {
        navigate(NavigationRoutes.LOGIN) {
            popUpTo(0) { inclusive = true }
        }
    }

    /**
     * 返回上一页
     */
    fun NavHostController.goBack() {
        popBackStack()
    }
}
