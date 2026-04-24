package com.wuheng.smart.navigation

/**
 * 导航路由定义
 *
 * 集中管理所有页面路由，避免硬编码字符串
 */
object NavigationRoutes {
    // ==================== 认证模块 ====================
    const val LOGIN = "login"
    const val REGISTER = "register"
    const val FORGOT_PASSWORD = "forgot_password"

    // ==================== 首页模块 ====================
    const val HOME = "home"

    // ==================== 楼层区域模块 ====================
    const val FLOOR_ZONE = "floor_zone"
    const val FLOOR_ZONE_WITH_ARG = "floor_zone/{floorId}"

    // ==================== 设备模块 ====================
    const val DEVICE_DETAIL = "device_detail"
    const val DEVICE_DETAIL_WITH_ARG = "device_detail/{deviceId}"
    const val DEVICE_EDIT = "device_edit"
    const val DEVICE_EDIT_WITH_ARG = "device_edit/{deviceId}"

    // ==================== 通知模块 ====================
    const val NOTIFICATION = "notification"
    const val NOTIFICATION_DETAIL = "notification_detail"
    const val NOTIFICATION_DETAIL_WITH_ARG = "notification_detail/{notificationId}"

    // ==================== 个人中心模块 ====================
    const val PROFILE = "profile"

    // ==================== 设置模块 ====================
    const val SETTINGS = "settings"
    const val PRIVACY_POLICY = "privacy_policy"
    const val USER_AGREEMENT = "user_agreement"
    const val FEEDBACK = "feedback"
    const val ABOUT = "about"

    // ==================== 主Tab模块 ====================
    const val CLIMATE = "climate"
    const val WATER = "water"

    // ==================== 耗材管理模块 ====================
    const val CONSUMABLES = "consumables"

    // ==================== 帮助模块 ====================
    const val HELP = "help"
    const val FAQ = "faq"
    const val SPLASH = "splash"

    // ==================== 底部导航路由列表 ====================
    val bottomNavRoutes = listOf(HOME, CLIMATE, WATER, PROFILE)

    // ==================== 参数构建方法 ====================

    /**
     * 构建楼层区域路由
     */
    fun floorZone(floorId: Int? = null): String {
        return if (floorId != null) {
            "floor_zone/$floorId"
        } else {
            FLOOR_ZONE
        }
    }

    /**
     * 构建设备详情路由
     */
    fun deviceDetail(deviceId: String): String {
        return "device_detail/$deviceId"
    }

    /**
     * 构建设备编辑路由
     */
    fun deviceEdit(deviceId: String): String {
        return "device_edit/$deviceId"
    }

    /**
     * 构建通知详情路由
     */
    fun notificationDetail(notificationId: String): String {
        return "notification_detail/$notificationId"
    }
}
