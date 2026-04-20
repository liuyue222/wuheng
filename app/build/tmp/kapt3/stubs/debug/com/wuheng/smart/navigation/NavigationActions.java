package com.wuheng.smart.navigation;

import androidx.compose.runtime.Composable;
import androidx.compose.ui.Modifier;
import androidx.navigation.NavHostController;
import androidx.navigation.NavType;

/**
 * 导航扩展函数
 */
@kotlin.Metadata(mv = {1, 7, 1}, k = 1, d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0004\b\u00c7\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\n\u0010\u0003\u001a\u00020\u0004*\u00020\u0005J\n\u0010\u0006\u001a\u00020\u0004*\u00020\u0005J\n\u0010\u0007\u001a\u00020\u0004*\u00020\u0005J\u0012\u0010\b\u001a\u00020\u0004*\u00020\u00052\u0006\u0010\t\u001a\u00020\nJ\u001b\u0010\u000b\u001a\u00020\u0004*\u00020\u00052\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\r\u00a2\u0006\u0002\u0010\u000eJ\n\u0010\u000f\u001a\u00020\u0004*\u00020\u0005J\n\u0010\u0010\u001a\u00020\u0004*\u00020\u0005\u00a8\u0006\u0011"}, d2 = {"Lcom/wuheng/smart/navigation/NavigationActions;", "", "()V", "goBack", "", "Landroidx/navigation/NavHostController;", "navigateToAbout", "navigateToConsumables", "navigateToDeviceDetail", "deviceId", "", "navigateToFloorZone", "floorId", "", "(Landroidx/navigation/NavHostController;Ljava/lang/Integer;)V", "navigateToHome", "navigateToLogin", "app_debug"})
public final class NavigationActions {
    @org.jetbrains.annotations.NotNull()
    public static final com.wuheng.smart.navigation.NavigationActions INSTANCE = null;
    
    private NavigationActions() {
        super();
    }
    
    /**
     * 导航到首页
     */
    public final void navigateToHome(@org.jetbrains.annotations.NotNull()
    androidx.navigation.NavHostController $this$navigateToHome) {
    }
    
    /**
     * 导航到设备详情
     */
    public final void navigateToDeviceDetail(@org.jetbrains.annotations.NotNull()
    androidx.navigation.NavHostController $this$navigateToDeviceDetail, @org.jetbrains.annotations.NotNull()
    java.lang.String deviceId) {
    }
    
    /**
     * 导航到楼层区域
     */
    public final void navigateToFloorZone(@org.jetbrains.annotations.NotNull()
    androidx.navigation.NavHostController $this$navigateToFloorZone, @org.jetbrains.annotations.Nullable()
    java.lang.Integer floorId) {
    }
    
    /**
     * 导航到耗材进度页面
     */
    public final void navigateToConsumables(@org.jetbrains.annotations.NotNull()
    androidx.navigation.NavHostController $this$navigateToConsumables) {
    }
    
    /**
     * 导航到关于页面
     */
    public final void navigateToAbout(@org.jetbrains.annotations.NotNull()
    androidx.navigation.NavHostController $this$navigateToAbout) {
    }
    
    /**
     * 导航到登录页面（清除回退栈）
     */
    public final void navigateToLogin(@org.jetbrains.annotations.NotNull()
    androidx.navigation.NavHostController $this$navigateToLogin) {
    }
    
    /**
     * 返回上一页
     */
    public final void goBack(@org.jetbrains.annotations.NotNull()
    androidx.navigation.NavHostController $this$goBack) {
    }
}