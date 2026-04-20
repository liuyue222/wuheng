package com.wuheng.smart.navigation

/**
 * 应用导航路由定义
 *
 * 统一管理所有页面的路由路径
 * 使用字符串常量避免硬编码路由路径
 */
object NavigationRoutes {
    // ==================== 主Tab路由 ====================

    const val HOME = "home"
    const val CLIMATE = "climate"
    const val WATER = "water"
    const val PROFILE = "profile"

    // ==================== 首页子路由 ====================

    /**
     * 设备详情页
     * @param deviceId 设备ID
     */
    const val DEVICE_DETAIL = "home/device/{deviceId}"

    fun deviceDetail(deviceId: String): String = "home/device/$deviceId"

    /**
     * 服务详情页
     * @param serviceType 服务类型
     */
    const val SERVICE_DETAIL = "home/service/{serviceType}"

    fun serviceDetail(serviceType: String): String = "home/service/$serviceType"

    /**
     * 场景编辑页
     * @param sceneId 场景ID
     */
    const val SCENE_EDIT = "home/scene/{sceneId}"

    fun sceneEdit(sceneId: String): String = "home/scene/$sceneId"

    /**
     * 环境数据详情页
     */
    const val ENVIRONMENT_DETAIL = "home/environment"

    // ==================== 冷暖系统子路由 ====================

    const val CLIMATE_OVERVIEW = "climate/overview"
    const val CLIMATE_FLOORS = "climate/floors"
    const val CLIMATE_ZONES = "climate/zones/{floorId}"
    const val CLIMATE_ZONE_DETAIL = "climate/zone/{zoneId}"

    fun climateZones(floorId: String) = "climate/zones/$floorId"
    fun climateZoneDetail(zoneId: String) = "climate/zone/$zoneId"

    /**
     * 楼层区域页面
     * @param floorId 可选的楼层ID
     */
    const val FLOOR_ZONE = "floor_zone?floorId={floorId}"

    fun floorZone(floorId: Int? = null): String =
        if (floorId != null) "floor_zone?floorId=$floorId" else "floor_zone"

    // ==================== 水系统子路由 ====================

    const val WATER_OVERVIEW = "water/overview"
    const val WATER_DEVICE_DETAIL = "water/device/{deviceId}"

    fun waterDeviceDetail(deviceId: String) = "water/device/$deviceId"

    // ==================== 个人中心子路由 ====================

    const val PROFILE_SETTINGS = "profile/settings"
    const val PROFILE_ABOUT = "profile/about"
    const val PROFILE_EDIT = "profile/edit"

    /**
     * 耗材使用进度页面
     */
    const val CONSUMABLES = "consumables"

    /**
     * 关于新宜能页面
     */
    const val ABOUT = "about"

    // ==================== 认证路由 ====================

    /**
     * 登录页面
     */
    const val LOGIN = "login"

    /**
     * 注册页面
     */
    const val REGISTER = "register"

    /**
     * 忘记密码页面
     */
    const val FORGOT_PASSWORD = "forgot_password"

    // ==================== 深层链接（可选） ====================

    /**
     * 直接打开设备控制（支持外部App唤起）
     * @param deviceId 设备ID
     */
    const val DEEPLINK_DEVICE_CONTROL = "wuheng://device/{deviceId}"

    fun deepLinkDeviceControl(deviceId: String) = "wuheng://device/$deviceId"

    /**
     * 直接激活场景（支持外部App唤起）
     * @param sceneId 场景ID
     */
    const val DEEPLINK_ACTIVATE_SCENE = "wuheng://scene/activate/{sceneId}"

    fun deepLinkActivateScene(sceneId: String) = "wuheng://scene/activate/$sceneId"

    // ==================== 路由分组 ====================

    /**
     * 底部导航路由列表
     */
    val bottomNavRoutes = listOf(HOME, CLIMATE, WATER, PROFILE)

    /**
     * 需要登录才能访问的路由列表
     */
    val authRequiredRoutes = listOf(
        HOME, CLIMATE, WATER, PROFILE,
        CONSUMABLES, ABOUT, FLOOR_ZONE,
        DEVICE_DETAIL, PROFILE_SETTINGS, PROFILE_EDIT
    )
}
