package com.wuheng.smart.presentation.climate;

import androidx.compose.foundation.layout.*;
import androidx.compose.material3.*;
import androidx.compose.runtime.Composable;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.tooling.preview.Preview;

@kotlin.Metadata(mv = {1, 7, 1}, k = 2, d1 = {"\u0000B\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\u001a(\u0010\u0000\u001a\u00020\u00012\b\b\u0002\u0010\u0002\u001a\u00020\u00032\u0014\b\u0002\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00010\u0005H\u0007\u001a\u0088\u0001\u0010\u0007\u001a\u00020\u00012\u0006\u0010\b\u001a\u00020\t2\u0012\u0010\n\u001a\u000e\u0012\u0004\u0012\u00020\u000b\u0012\u0004\u0012\u00020\u00010\u00052\u0012\u0010\f\u001a\u000e\u0012\u0004\u0012\u00020\r\u0012\u0004\u0012\u00020\u00010\u00052\u0012\u0010\u000e\u001a\u000e\u0012\u0004\u0012\u00020\r\u0012\u0004\u0012\u00020\u00010\u00052\u0018\u0010\u000f\u001a\u0014\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u0011\u0012\u0004\u0012\u00020\u00010\u00102\u0012\u0010\u0012\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00010\u00052\f\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00010\u0014H\u0003\u001a\b\u0010\u0015\u001a\u00020\u0001H\u0007\u001a\b\u0010\u0016\u001a\u00020\u0001H\u0007\u001a\b\u0010\u0017\u001a\u00020\u0001H\u0007\u00a8\u0006\u0018"}, d2 = {"ClimateScreen", "", "viewModel", "Lcom/wuheng/smart/presentation/climate/ClimateViewModel;", "onNavigateToFloorDetail", "Lkotlin/Function1;", "", "ClimateScreenContent", "uiState", "Lcom/wuheng/smart/presentation/climate/ClimateUiState;", "onTabSelected", "Lcom/wuheng/smart/presentation/climate/ClimateTab;", "onTemperatureChange", "", "onHumidityChange", "onFloorToggle", "Lkotlin/Function2;", "", "onFloorClick", "onRefresh", "Lkotlin/Function0;", "ClimateScreenFloorPreview", "ClimateScreenWholeHousePreview", "ClimateScreenWidePreview", "app_debug"})
public final class ClimateScreenKt {
    
    /**
     * 冷暖舒适页面 Screen - 处理ViewModel和状态管理
     * 逻辑和UI分离：Screen负责状态管理，Layout负责纯UI渲染
     *
     * 性能优化：
     * 1. 使用 remember 缓存回调函数，避免每次重组时创建新的lambda
     * 2. 使用 derivedStateOf 优化状态计算（在Layout中实现）
     * 3. 滑块拖动时使用 rememberUpdatedState 确保回调始终引用最新值
     */
    @androidx.compose.runtime.Composable()
    public static final void ClimateScreen(@org.jetbrains.annotations.NotNull()
    com.wuheng.smart.presentation.climate.ClimateViewModel viewModel, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onNavigateToFloorDetail) {
    }
    
    /**
     * 冷暖舒适页面内容 - 纯UI，接收状态和回调
     */
    @androidx.compose.runtime.Composable()
    private static final void ClimateScreenContent(com.wuheng.smart.presentation.climate.ClimateUiState uiState, kotlin.jvm.functions.Function1<? super com.wuheng.smart.presentation.climate.ClimateTab, kotlin.Unit> onTabSelected, kotlin.jvm.functions.Function1<? super java.lang.Float, kotlin.Unit> onTemperatureChange, kotlin.jvm.functions.Function1<? super java.lang.Float, kotlin.Unit> onHumidityChange, kotlin.jvm.functions.Function2<? super java.lang.String, ? super java.lang.Boolean, kotlin.Unit> onFloorToggle, kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onFloorClick, kotlin.jvm.functions.Function0<kotlin.Unit> onRefresh) {
    }
    
    @androidx.compose.runtime.Composable()
    @androidx.compose.ui.tooling.preview.Preview(showBackground = true, name = "\u51b7\u6696\u8212\u9002-\u5168\u5c4b\u6a21\u5f0f", backgroundColor = 4293981432L)
    public static final void ClimateScreenWholeHousePreview() {
    }
    
    @androidx.compose.runtime.Composable()
    @androidx.compose.ui.tooling.preview.Preview(showBackground = true, name = "\u51b7\u6696\u8212\u9002-\u697c\u5c42\u6a21\u5f0f", backgroundColor = 4293981432L)
    public static final void ClimateScreenFloorPreview() {
    }
    
    @androidx.compose.runtime.Composable()
    @androidx.compose.ui.tooling.preview.Preview(showBackground = true, name = "\u51b7\u6696\u8212\u9002-\u5bbd\u5c4f720dp", widthDp = 720, backgroundColor = 4293981432L)
    public static final void ClimateScreenWidePreview() {
    }
}