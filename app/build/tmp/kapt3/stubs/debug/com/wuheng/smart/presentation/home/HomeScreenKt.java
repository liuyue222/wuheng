package com.wuheng.smart.presentation.home;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.Settings;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.compose.foundation.layout.*;
import androidx.compose.material3.*;
import androidx.compose.runtime.*;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.tooling.preview.Preview;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import com.wuheng.smart.data.location.LocationManager;
import com.wuheng.smart.data.location.WeatherManager;
import com.wuheng.smart.data.model.SceneType;
import timber.log.Timber;

@kotlin.Metadata(mv = {1, 7, 1}, k = 2, d1 = {"\u0000H\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u000e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a2\u0010\u0000\u001a\u00020\u00012\b\b\u0002\u0010\u0002\u001a\u00020\u00032\u000e\b\u0002\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00010\u00052\u000e\b\u0002\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00010\u0005H\u0007\u001al\u0010\u0007\u001a\u00020\u00012\u0006\u0010\b\u001a\u00020\t2\b\b\u0002\u0010\n\u001a\u00020\u000b2\u0012\u0010\f\u001a\u000e\u0012\u0004\u0012\u00020\u000e\u0012\u0004\u0012\u00020\u00010\r2\u0012\u0010\u000f\u001a\u000e\u0012\u0004\u0012\u00020\u0010\u0012\u0004\u0012\u00020\u00010\r2\f\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00010\u00052\f\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00010\u00052\f\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00010\u0005H\u0003\u001a\b\u0010\u0014\u001a\u00020\u0001H\u0007\u001a\b\u0010\u0015\u001a\u00020\u0001H\u0007\u001a\b\u0010\u0016\u001a\u00020\u0001H\u0007\u001a4\u0010\u0017\u001a\u00020\u00012\u0006\u0010\u0018\u001a\u00020\u000e2\u0006\u0010\u0019\u001a\u00020\u000e2\f\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u00010\u00052\f\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u00010\u0005H\u0003\u001a\u0018\u0010\u001c\u001a\u00020\u000b2\u0006\u0010\u0018\u001a\u00020\u000e2\u0006\u0010\u0019\u001a\u00020\u000eH\u0002\u001a1\u0010\u001d\u001a\u00020\u00012\u0006\u0010\u001e\u001a\u00020\u001f2\u0006\u0010 \u001a\u00020!2\u0006\u0010\"\u001a\u00020#2\u0006\u0010\u0002\u001a\u00020\u0003H\u0082@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010$\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006%"}, d2 = {"HomeScreen", "", "viewModel", "Lcom/wuheng/smart/presentation/home/HomeViewModel;", "onNavigateToResidence", "Lkotlin/Function0;", "onNavigateToHouseList", "HomeScreenContent", "uiState", "Lcom/wuheng/smart/presentation/home/HomeUiState;", "hasVacationScene", "", "onModeSelected", "Lkotlin/Function1;", "Lcom/wuheng/smart/presentation/home/ClimateMode;", "onSceneSelected", "Lcom/wuheng/smart/data/model/SceneType;", "onVacationModeClick", "onResidenceClick", "onRefresh", "HomeScreenErrorPreview", "HomeScreenLoadingPreview", "HomeScreenPreview", "ModeSwitchConfirmDialog", "fromMode", "toMode", "onConfirm", "onDismiss", "needsModeConfirmation", "updateLocationAndWeather", "context", "Landroid/content/Context;", "locationManager", "Lcom/wuheng/smart/data/location/LocationManager;", "weatherManager", "Lcom/wuheng/smart/data/location/WeatherManager;", "(Landroid/content/Context;Lcom/wuheng/smart/data/location/LocationManager;Lcom/wuheng/smart/data/location/WeatherManager;Lcom/wuheng/smart/presentation/home/HomeViewModel;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_debug"})
public final class HomeScreenKt {
    
    /**
     * 首页 Screen - 处理ViewModel和状态管理
     */
    @androidx.compose.runtime.Composable()
    public static final void HomeScreen(@org.jetbrains.annotations.NotNull()
    com.wuheng.smart.presentation.home.HomeViewModel viewModel, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onNavigateToResidence, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onNavigateToHouseList) {
    }
    
    /**
     * 检查模式切换是否需要二次确认
     * 任何模式切换都需要确认，因为涉及全屋水系统
     */
    private static final boolean needsModeConfirmation(com.wuheng.smart.presentation.home.ClimateMode fromMode, com.wuheng.smart.presentation.home.ClimateMode toMode) {
        return false;
    }
    
    /**
     * 模式切换确认对话框
     */
    @androidx.compose.runtime.Composable()
    private static final void ModeSwitchConfirmDialog(com.wuheng.smart.presentation.home.ClimateMode fromMode, com.wuheng.smart.presentation.home.ClimateMode toMode, kotlin.jvm.functions.Function0<kotlin.Unit> onConfirm, kotlin.jvm.functions.Function0<kotlin.Unit> onDismiss) {
    }
    
    /**
     * 更新位置和天气
     */
    private static final java.lang.Object updateLocationAndWeather(android.content.Context context, com.wuheng.smart.data.location.LocationManager locationManager, com.wuheng.smart.data.location.WeatherManager weatherManager, com.wuheng.smart.presentation.home.HomeViewModel viewModel, kotlin.coroutines.Continuation<? super kotlin.Unit> continuation) {
        return null;
    }
    
    /**
     * 首页内容 - 纯UI，接收状态和回调
     */
    @androidx.compose.runtime.Composable()
    private static final void HomeScreenContent(com.wuheng.smart.presentation.home.HomeUiState uiState, boolean hasVacationScene, kotlin.jvm.functions.Function1<? super com.wuheng.smart.presentation.home.ClimateMode, kotlin.Unit> onModeSelected, kotlin.jvm.functions.Function1<? super com.wuheng.smart.data.model.SceneType, kotlin.Unit> onSceneSelected, kotlin.jvm.functions.Function0<kotlin.Unit> onVacationModeClick, kotlin.jvm.functions.Function0<kotlin.Unit> onResidenceClick, kotlin.jvm.functions.Function0<kotlin.Unit> onRefresh) {
    }
    
    @androidx.compose.runtime.Composable()
    @androidx.compose.ui.tooling.preview.Preview(showBackground = true, name = "\u9996\u9875-\u6b63\u5e38\u72b6\u6001", backgroundColor = 4293981432L)
    public static final void HomeScreenPreview() {
    }
    
    @androidx.compose.runtime.Composable()
    @androidx.compose.ui.tooling.preview.Preview(showBackground = true, name = "\u9996\u9875-\u52a0\u8f7d\u4e2d", backgroundColor = 4293981432L)
    public static final void HomeScreenLoadingPreview() {
    }
    
    @androidx.compose.runtime.Composable()
    @androidx.compose.ui.tooling.preview.Preview(showBackground = true, name = "\u9996\u9875-\u9519\u8bef", backgroundColor = 4293981432L)
    public static final void HomeScreenErrorPreview() {
    }
}