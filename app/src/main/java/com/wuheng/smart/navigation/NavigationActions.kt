package com.wuheng.smart.navigation

import androidx.navigation.NavHostController

/**
 * 导航操作类
 *
 * 集中管理所有导航操作
 */
class NavigationActions(private val navController: NavHostController) {

    fun navigateToLogin() {
        navController.navigate(NavigationRoutes.LOGIN) {
            popUpTo(NavigationRoutes.HOME) { inclusive = true }
        }
    }

    fun navigateToHome() {
        navController.navigate(NavigationRoutes.HOME) {
            popUpTo(NavigationRoutes.LOGIN) { inclusive = true }
        }
    }

    fun navigateBack() {
        navController.popBackStack()
    }

    fun navigateToSettings() {
        navController.navigate(NavigationRoutes.SETTINGS)
    }

    fun navigateToHelp() {
        navController.navigate(NavigationRoutes.HELP)
    }

    fun navigateToFloorZone(floorId: Int? = null) {
        navController.navigate(NavigationRoutes.floorZone(floorId))
    }

    fun navigateToDeviceDetail(deviceId: String) {
        navController.navigate(NavigationRoutes.deviceDetail(deviceId))
    }

    fun navigateToNotification() {
        navController.navigate(NavigationRoutes.NOTIFICATION)
    }
}

/**
 * 导航扩展函数
 */
fun NavHostController.navigateToLogin() {
    navigate(NavigationRoutes.LOGIN) {
        popUpTo(NavigationRoutes.HOME) { inclusive = true }
    }
}

/**
 * 底部导航路由列表
 */
val bottomNavRoutes = listOf(
    NavigationRoutes.HOME,
    NavigationRoutes.CLIMATE,
    NavigationRoutes.WATER,
    NavigationRoutes.PROFILE
)
