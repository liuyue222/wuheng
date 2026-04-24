package com.wuheng.smart.navigation;

import androidx.compose.foundation.layout.*;
import androidx.compose.material.icons.Icons;
import androidx.compose.material3.*;
import androidx.compose.runtime.Composable;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.text.font.FontWeight;
import androidx.navigation.NavHostController;
import androidx.navigation.NavType;
import com.wuheng.smart.presentation.about.AboutViewModel;
import com.wuheng.smart.presentation.consumables.ConsumablesViewModel;
import com.wuheng.smart.presentation.device.DeviceDetailViewModel;
import com.wuheng.smart.presentation.floorzone.FloorZoneViewModel;
import com.wuheng.smart.presentation.forgotpassword.ForgotPasswordViewModel;
import com.wuheng.smart.presentation.home.HomeViewModel;
import com.wuheng.smart.presentation.login.LoginViewModel;
import com.wuheng.smart.presentation.notification.NotificationViewModel;
import com.wuheng.smart.presentation.profile.ProfileViewModel;
import com.wuheng.smart.presentation.climate.ClimateViewModel;
import com.wuheng.smart.presentation.register.RegisterViewModel;
import com.wuheng.smart.presentation.settings.SettingViewModel;
import com.wuheng.smart.presentation.water.WaterViewModel;
import com.wuheng.smart.presentation.theme.*;

@kotlin.Metadata(mv = {1, 7, 1}, k = 2, d1 = {"\u0000.\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\b\n\u0002\b\u0004\u001a\u001e\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00010\u0005H\u0003\u001a\u0016\u0010\u0006\u001a\u00020\u00012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00010\u0005H\u0003\u001a\u0016\u0010\u0007\u001a\u00020\u00012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00010\u0005H\u0003\u001a\u0016\u0010\b\u001a\u00020\u00012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00010\u0005H\u0003\u001a$\u0010\t\u001a\u00020\u00012\u0006\u0010\n\u001a\u00020\u000b2\b\b\u0002\u0010\f\u001a\u00020\u00032\b\b\u0002\u0010\r\u001a\u00020\u000eH\u0007\u001a\u001e\u0010\u000f\u001a\u00020\u00012\u0006\u0010\u0010\u001a\u00020\u00032\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00010\u0005H\u0003\u001a\u0016\u0010\u0011\u001a\u00020\u00012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00010\u0005H\u0003\u001a\u0012\u0010\u0012\u001a\u00020\u0001*\u00020\u000b2\u0006\u0010\u0002\u001a\u00020\u0003\u001a\u001b\u0010\u0013\u001a\u00020\u0001*\u00020\u000b2\n\b\u0002\u0010\u0014\u001a\u0004\u0018\u00010\u0015\u00a2\u0006\u0002\u0010\u0016\u001a\n\u0010\u0017\u001a\u00020\u0001*\u00020\u000b\u001a\n\u0010\u0018\u001a\u00020\u0001*\u00020\u000b\u00a8\u0006\u0019"}, d2 = {"DeviceEditPlaceholderScreen", "", "deviceId", "", "onNavigateBack", "Lkotlin/Function0;", "FaqPlaceholderScreen", "FeedbackPlaceholderScreen", "HelpPlaceholderScreen", "NavGraph", "navController", "Landroidx/navigation/NavHostController;", "startDestination", "modifier", "Landroidx/compose/ui/Modifier;", "NotificationDetailPlaceholderScreen", "notificationId", "UserAgreementPlaceholderScreen", "navigateToDeviceDetail", "navigateToFloorZone", "floorId", "", "(Landroidx/navigation/NavHostController;Ljava/lang/Integer;)V", "navigateToNotification", "navigateToSettings", "app_debug"})
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
    
    /**
     * 设备编辑占位页面
     */
    @androidx.compose.runtime.Composable()
    @kotlin.OptIn(markerClass = {androidx.compose.material3.ExperimentalMaterial3Api.class})
    private static final void DeviceEditPlaceholderScreen(java.lang.String deviceId, kotlin.jvm.functions.Function0<kotlin.Unit> onNavigateBack) {
    }
    
    /**
     * 通知详情占位页面
     */
    @androidx.compose.runtime.Composable()
    @kotlin.OptIn(markerClass = {androidx.compose.material3.ExperimentalMaterial3Api.class})
    private static final void NotificationDetailPlaceholderScreen(java.lang.String notificationId, kotlin.jvm.functions.Function0<kotlin.Unit> onNavigateBack) {
    }
    
    /**
     * 用户协议占位页面
     */
    @androidx.compose.runtime.Composable()
    @kotlin.OptIn(markerClass = {androidx.compose.material3.ExperimentalMaterial3Api.class})
    private static final void UserAgreementPlaceholderScreen(kotlin.jvm.functions.Function0<kotlin.Unit> onNavigateBack) {
    }
    
    /**
     * 意见反馈占位页面
     */
    @androidx.compose.runtime.Composable()
    @kotlin.OptIn(markerClass = {androidx.compose.material3.ExperimentalMaterial3Api.class})
    private static final void FeedbackPlaceholderScreen(kotlin.jvm.functions.Function0<kotlin.Unit> onNavigateBack) {
    }
    
    /**
     * 帮助页面占位
     */
    @androidx.compose.runtime.Composable()
    @kotlin.OptIn(markerClass = {androidx.compose.material3.ExperimentalMaterial3Api.class})
    private static final void HelpPlaceholderScreen(kotlin.jvm.functions.Function0<kotlin.Unit> onNavigateBack) {
    }
    
    /**
     * FAQ页面占位
     */
    @androidx.compose.runtime.Composable()
    @kotlin.OptIn(markerClass = {androidx.compose.material3.ExperimentalMaterial3Api.class})
    private static final void FaqPlaceholderScreen(kotlin.jvm.functions.Function0<kotlin.Unit> onNavigateBack) {
    }
}