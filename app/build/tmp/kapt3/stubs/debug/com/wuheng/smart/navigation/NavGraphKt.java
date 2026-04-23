package com.wuheng.smart.navigation;

import androidx.compose.runtime.Composable;
import androidx.compose.ui.Modifier;
import androidx.navigation.NavHostController;
import androidx.navigation.NavType;

@kotlin.Metadata(mv = {1, 7, 1}, k = 2, d1 = {"\u0000$\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\u0004\u001a$\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u0007H\u0007\u001a\u0012\u0010\b\u001a\u00020\u0001*\u00020\u00032\u0006\u0010\t\u001a\u00020\u0005\u001a\u001b\u0010\n\u001a\u00020\u0001*\u00020\u00032\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\f\u00a2\u0006\u0002\u0010\r\u001a\n\u0010\u000e\u001a\u00020\u0001*\u00020\u0003\u001a\n\u0010\u000f\u001a\u00020\u0001*\u00020\u0003\u00a8\u0006\u0010"}, d2 = {"NavGraph", "", "navController", "Landroidx/navigation/NavHostController;", "startDestination", "", "modifier", "Landroidx/compose/ui/Modifier;", "navigateToDeviceDetail", "deviceId", "navigateToFloorZone", "floorId", "", "(Landroidx/navigation/NavHostController;Ljava/lang/Integer;)V", "navigateToNotification", "navigateToSettings", "app_debug"})
public final class NavGraphKt {
    
    /**
     * 应用导航图
     *
     * 定义所有页面路由和导航逻辑
     */
    @androidx.compose.runtime.Composable()
    public static final void NavGraph(@org.jetbrains.annotations.NotNull()
    androidx.navigation.NavHostController navController, @org.jetbrains.annotations.NotNull()
    java.lang.String startDestination, @org.jetbrains.annotations.NotNull()
    androidx.compose.ui.Modifier modifier) {
    }
    
    /**
     * 导航扩展函数
     */
    public static final void navigateToDeviceDetail(@org.jetbrains.annotations.NotNull()
    androidx.navigation.NavHostController $this$navigateToDeviceDetail, @org.jetbrains.annotations.NotNull()
    java.lang.String deviceId) {
    }
    
    public static final void navigateToFloorZone(@org.jetbrains.annotations.NotNull()
    androidx.navigation.NavHostController $this$navigateToFloorZone, @org.jetbrains.annotations.Nullable()
    java.lang.Integer floorId) {
    }
    
    public static final void navigateToNotification(@org.jetbrains.annotations.NotNull()
    androidx.navigation.NavHostController $this$navigateToNotification) {
    }
    
    public static final void navigateToSettings(@org.jetbrains.annotations.NotNull()
    androidx.navigation.NavHostController $this$navigateToSettings) {
    }
}