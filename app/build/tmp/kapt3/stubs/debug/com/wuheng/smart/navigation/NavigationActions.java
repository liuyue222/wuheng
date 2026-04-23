package com.wuheng.smart.navigation;

import androidx.navigation.NavHostController;

/**
 * 导航操作类
 *
 * 集中管理所有导航操作
 */
@kotlin.Metadata(mv = {1, 7, 1}, k = 1, d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0007\b\u0007\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0006\u0010\u0005\u001a\u00020\u0006J\u000e\u0010\u0007\u001a\u00020\u00062\u0006\u0010\b\u001a\u00020\tJ\u0017\u0010\n\u001a\u00020\u00062\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\f\u00a2\u0006\u0002\u0010\rJ\u0006\u0010\u000e\u001a\u00020\u0006J\u0006\u0010\u000f\u001a\u00020\u0006J\u0006\u0010\u0010\u001a\u00020\u0006J\u0006\u0010\u0011\u001a\u00020\u0006J\u0006\u0010\u0012\u001a\u00020\u0006R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0013"}, d2 = {"Lcom/wuheng/smart/navigation/NavigationActions;", "", "navController", "Landroidx/navigation/NavHostController;", "(Landroidx/navigation/NavHostController;)V", "navigateBack", "", "navigateToDeviceDetail", "deviceId", "", "navigateToFloorZone", "floorId", "", "(Ljava/lang/Integer;)V", "navigateToHelp", "navigateToHome", "navigateToLogin", "navigateToNotification", "navigateToSettings", "app_debug"})
public final class NavigationActions {
    private final androidx.navigation.NavHostController navController = null;
    
    public NavigationActions(@org.jetbrains.annotations.NotNull()
    androidx.navigation.NavHostController navController) {
        super();
    }
    
    public final void navigateToLogin() {
    }
    
    public final void navigateToHome() {
    }
    
    public final void navigateBack() {
    }
    
    public final void navigateToSettings() {
    }
    
    public final void navigateToHelp() {
    }
    
    public final void navigateToFloorZone(@org.jetbrains.annotations.Nullable()
    java.lang.Integer floorId) {
    }
    
    public final void navigateToDeviceDetail(@org.jetbrains.annotations.NotNull()
    java.lang.String deviceId) {
    }
    
    public final void navigateToNotification() {
    }
}