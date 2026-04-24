package com.wuheng.smart.navigation;

import java.lang.System;

/**
 * 导航路由定义
 *
 * 集中管理所有页面路由，避免硬编码字符串
 */
@kotlin.Metadata(mv = {1, 7, 1}, k = 1, d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0019\n\u0002\u0010 \n\u0002\b\u0007\n\u0002\u0010\b\n\u0002\b\u0004\b\u00c7\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u000e\u0010!\u001a\u00020\u00042\u0006\u0010\"\u001a\u00020\u0004J\u000e\u0010#\u001a\u00020\u00042\u0006\u0010\"\u001a\u00020\u0004J\u0017\u0010$\u001a\u00020\u00042\n\b\u0002\u0010%\u001a\u0004\u0018\u00010&\u00a2\u0006\u0002\u0010\'J\u000e\u0010(\u001a\u00020\u00042\u0006\u0010)\u001a\u00020\u0004R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0014\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0015\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0016\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0017\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0018\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0019\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001a\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001b\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001c\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u0017\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\u00040\u001e\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001f\u0010 \u00a8\u0006*"}, d2 = {"Lcom/wuheng/smart/navigation/NavigationRoutes;", "", "()V", "ABOUT", "", "CLIMATE", "CONSUMABLES", "DEVICE_DETAIL", "DEVICE_DETAIL_WITH_ARG", "DEVICE_EDIT", "DEVICE_EDIT_WITH_ARG", "FAQ", "FEEDBACK", "FLOOR_ZONE", "FLOOR_ZONE_WITH_ARG", "FORGOT_PASSWORD", "HELP", "HOME", "LOGIN", "NOTIFICATION", "NOTIFICATION_DETAIL", "NOTIFICATION_DETAIL_WITH_ARG", "PRIVACY_POLICY", "PROFILE", "REGISTER", "SETTINGS", "SPLASH", "USER_AGREEMENT", "WATER", "bottomNavRoutes", "", "getBottomNavRoutes", "()Ljava/util/List;", "deviceDetail", "deviceId", "deviceEdit", "floorZone", "floorId", "", "(Ljava/lang/Integer;)Ljava/lang/String;", "notificationDetail", "notificationId", "app_debug"})
public final class NavigationRoutes {
    @org.jetbrains.annotations.NotNull()
    public static final com.wuheng.smart.navigation.NavigationRoutes INSTANCE = null;
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String LOGIN = "login";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String REGISTER = "register";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String FORGOT_PASSWORD = "forgot_password";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String HOME = "home";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String FLOOR_ZONE = "floor_zone";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String FLOOR_ZONE_WITH_ARG = "floor_zone/{floorId}";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String DEVICE_DETAIL = "device_detail";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String DEVICE_DETAIL_WITH_ARG = "device_detail/{deviceId}";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String DEVICE_EDIT = "device_edit";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String DEVICE_EDIT_WITH_ARG = "device_edit/{deviceId}";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String NOTIFICATION = "notification";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String NOTIFICATION_DETAIL = "notification_detail";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String NOTIFICATION_DETAIL_WITH_ARG = "notification_detail/{notificationId}";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String PROFILE = "profile";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String SETTINGS = "settings";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String PRIVACY_POLICY = "privacy_policy";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String USER_AGREEMENT = "user_agreement";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String FEEDBACK = "feedback";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String ABOUT = "about";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String CLIMATE = "climate";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String WATER = "water";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String CONSUMABLES = "consumables";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String HELP = "help";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String FAQ = "faq";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String SPLASH = "splash";
    @org.jetbrains.annotations.NotNull()
    private static final java.util.List<java.lang.String> bottomNavRoutes = null;
    
    private NavigationRoutes() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<java.lang.String> getBottomNavRoutes() {
        return null;
    }
    
    /**
     * 构建楼层区域路由
     */
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String floorZone(@org.jetbrains.annotations.Nullable()
    java.lang.Integer floorId) {
        return null;
    }
    
    /**
     * 构建设备详情路由
     */
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String deviceDetail(@org.jetbrains.annotations.NotNull()
    java.lang.String deviceId) {
        return null;
    }
    
    /**
     * 构建设备编辑路由
     */
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String deviceEdit(@org.jetbrains.annotations.NotNull()
    java.lang.String deviceId) {
        return null;
    }
    
    /**
     * 构建通知详情路由
     */
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String notificationDetail(@org.jetbrains.annotations.NotNull()
    java.lang.String notificationId) {
        return null;
    }
}