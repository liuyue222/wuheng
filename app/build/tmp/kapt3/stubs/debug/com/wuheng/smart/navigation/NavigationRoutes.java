package com.wuheng.smart.navigation;

import java.lang.System;

/**
 * 应用导航路由定义
 *
 * 统一管理所有页面的路由路径
 * 使用字符串常量避免硬编码路由路径
 */
@kotlin.Metadata(mv = {1, 7, 1}, k = 1, d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0019\n\u0002\u0010 \n\u0002\b\u000e\n\u0002\u0010\b\n\u0002\b\u0006\b\u00c7\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u000e\u0010#\u001a\u00020\u00042\u0006\u0010$\u001a\u00020\u0004J\u000e\u0010%\u001a\u00020\u00042\u0006\u0010&\u001a\u00020\u0004J\u000e\u0010\'\u001a\u00020\u00042\u0006\u0010(\u001a\u00020\u0004J\u000e\u0010)\u001a\u00020\u00042\u0006\u0010*\u001a\u00020\u0004J\u000e\u0010+\u001a\u00020\u00042\u0006\u0010*\u001a\u00020\u0004J\u0017\u0010,\u001a\u00020\u00042\n\b\u0002\u0010&\u001a\u0004\u0018\u00010-\u00a2\u0006\u0002\u0010.J\u000e\u0010/\u001a\u00020\u00042\u0006\u0010(\u001a\u00020\u0004J\u000e\u00100\u001a\u00020\u00042\u0006\u00101\u001a\u00020\u0004J\u000e\u00102\u001a\u00020\u00042\u0006\u0010*\u001a\u00020\u0004R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0014\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0015\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0016\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0017\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0018\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0019\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001a\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001b\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001c\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u0017\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\u00040\u001e\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001f\u0010 R\u0017\u0010!\u001a\b\u0012\u0004\u0012\u00020\u00040\u001e\u00a2\u0006\b\n\u0000\u001a\u0004\b\"\u0010 \u00a8\u00063"}, d2 = {"Lcom/wuheng/smart/navigation/NavigationRoutes;", "", "()V", "ABOUT", "", "CLIMATE", "CLIMATE_FLOORS", "CLIMATE_OVERVIEW", "CLIMATE_ZONES", "CLIMATE_ZONE_DETAIL", "CONSUMABLES", "DEEPLINK_ACTIVATE_SCENE", "DEEPLINK_DEVICE_CONTROL", "DEVICE_DETAIL", "ENVIRONMENT_DETAIL", "FLOOR_ZONE", "FORGOT_PASSWORD", "HOME", "LOGIN", "PROFILE", "PROFILE_ABOUT", "PROFILE_EDIT", "PROFILE_SETTINGS", "REGISTER", "SCENE_EDIT", "SERVICE_DETAIL", "WATER", "WATER_DEVICE_DETAIL", "WATER_OVERVIEW", "authRequiredRoutes", "", "getAuthRequiredRoutes", "()Ljava/util/List;", "bottomNavRoutes", "getBottomNavRoutes", "climateZoneDetail", "zoneId", "climateZones", "floorId", "deepLinkActivateScene", "sceneId", "deepLinkDeviceControl", "deviceId", "deviceDetail", "floorZone", "", "(Ljava/lang/Integer;)Ljava/lang/String;", "sceneEdit", "serviceDetail", "serviceType", "waterDeviceDetail", "app_debug"})
public final class NavigationRoutes {
    @org.jetbrains.annotations.NotNull()
    public static final com.wuheng.smart.navigation.NavigationRoutes INSTANCE = null;
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String HOME = "home";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String CLIMATE = "climate";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String WATER = "water";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String PROFILE = "profile";
    
    /**
     * 设备详情页
     * @param deviceId 设备ID
     */
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String DEVICE_DETAIL = "home/device/{deviceId}";
    
    /**
     * 服务详情页
     * @param serviceType 服务类型
     */
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String SERVICE_DETAIL = "home/service/{serviceType}";
    
    /**
     * 场景编辑页
     * @param sceneId 场景ID
     */
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String SCENE_EDIT = "home/scene/{sceneId}";
    
    /**
     * 环境数据详情页
     */
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String ENVIRONMENT_DETAIL = "home/environment";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String CLIMATE_OVERVIEW = "climate/overview";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String CLIMATE_FLOORS = "climate/floors";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String CLIMATE_ZONES = "climate/zones/{floorId}";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String CLIMATE_ZONE_DETAIL = "climate/zone/{zoneId}";
    
    /**
     * 楼层区域页面
     * @param floorId 可选的楼层ID
     */
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String FLOOR_ZONE = "floor_zone?floorId={floorId}";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String WATER_OVERVIEW = "water/overview";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String WATER_DEVICE_DETAIL = "water/device/{deviceId}";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String PROFILE_SETTINGS = "profile/settings";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String PROFILE_ABOUT = "profile/about";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String PROFILE_EDIT = "profile/edit";
    
    /**
     * 耗材使用进度页面
     */
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String CONSUMABLES = "consumables";
    
    /**
     * 关于新宜能页面
     */
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String ABOUT = "about";
    
    /**
     * 登录页面
     */
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String LOGIN = "login";
    
    /**
     * 注册页面
     */
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String REGISTER = "register";
    
    /**
     * 忘记密码页面
     */
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String FORGOT_PASSWORD = "forgot_password";
    
    /**
     * 直接打开设备控制（支持外部App唤起）
     * @param deviceId 设备ID
     */
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String DEEPLINK_DEVICE_CONTROL = "wuheng://device/{deviceId}";
    
    /**
     * 直接激活场景（支持外部App唤起）
     * @param sceneId 场景ID
     */
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String DEEPLINK_ACTIVATE_SCENE = "wuheng://scene/activate/{sceneId}";
    
    /**
     * 底部导航路由列表
     */
    @org.jetbrains.annotations.NotNull()
    private static final java.util.List<java.lang.String> bottomNavRoutes = null;
    
    /**
     * 需要登录才能访问的路由列表
     */
    @org.jetbrains.annotations.NotNull()
    private static final java.util.List<java.lang.String> authRequiredRoutes = null;
    
    private NavigationRoutes() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String deviceDetail(@org.jetbrains.annotations.NotNull()
    java.lang.String deviceId) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String serviceDetail(@org.jetbrains.annotations.NotNull()
    java.lang.String serviceType) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String sceneEdit(@org.jetbrains.annotations.NotNull()
    java.lang.String sceneId) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String climateZones(@org.jetbrains.annotations.NotNull()
    java.lang.String floorId) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String climateZoneDetail(@org.jetbrains.annotations.NotNull()
    java.lang.String zoneId) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String floorZone(@org.jetbrains.annotations.Nullable()
    java.lang.Integer floorId) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String waterDeviceDetail(@org.jetbrains.annotations.NotNull()
    java.lang.String deviceId) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String deepLinkDeviceControl(@org.jetbrains.annotations.NotNull()
    java.lang.String deviceId) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String deepLinkActivateScene(@org.jetbrains.annotations.NotNull()
    java.lang.String sceneId) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<java.lang.String> getBottomNavRoutes() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<java.lang.String> getAuthRequiredRoutes() {
        return null;
    }
}