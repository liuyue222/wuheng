package com.wuheng.smart.presentation.water;

import androidx.compose.foundation.layout.*;
import androidx.compose.material3.*;
import androidx.compose.runtime.Composable;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.tooling.preview.Preview;

@kotlin.Metadata(mv = {1, 7, 1}, k = 2, d1 = {"\u0000(\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\u001aB\u0010\u0000\u001a\u00020\u00012\b\b\u0002\u0010\u0002\u001a\u00020\u00032\u000e\b\u0002\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00010\u00052\u000e\b\u0002\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00010\u00052\u000e\b\u0002\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00010\u0005H\u0007\u001a\\\u0010\b\u001a\u00020\u00012\u0006\u0010\t\u001a\u00020\n2\u0012\u0010\u000b\u001a\u000e\u0012\u0004\u0012\u00020\r\u0012\u0004\u0012\u00020\u00010\f2\f\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00010\u00052\f\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00010\u00052\f\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00010\u00052\f\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00010\u0005H\u0003\u001a\b\u0010\u0012\u001a\u00020\u0001H\u0007\u001a\b\u0010\u0013\u001a\u00020\u0001H\u0007\u00a8\u0006\u0014"}, d2 = {"WaterScreen", "", "viewModel", "Lcom/wuheng/smart/presentation/water/WaterViewModel;", "onNavigateToDurationPicker", "Lkotlin/Function0;", "onNavigateToSterilizationEdit", "onNavigateToFilterReplace", "WaterScreenContent", "uiState", "Lcom/wuheng/smart/presentation/water/WaterUiState;", "onHotWaterModeSelected", "Lkotlin/Function1;", "Lcom/wuheng/smart/presentation/water/HotWaterMode;", "onDurationClick", "onSterilizationEdit", "onFilterReplaceClick", "onRefresh", "WaterScreenPreview", "WaterScreenWidePreview", "app_debug"})
public final class WaterScreenKt {
    
    /**
     * 水系统页面 Screen - 处理ViewModel和状态管理
     * 逻辑和UI分离：Screen负责状态管理，Layout负责纯UI渲染
     */
    @androidx.compose.runtime.Composable()
    public static final void WaterScreen(@org.jetbrains.annotations.NotNull()
    com.wuheng.smart.presentation.water.WaterViewModel viewModel, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onNavigateToDurationPicker, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onNavigateToSterilizationEdit, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onNavigateToFilterReplace) {
    }
    
    /**
     * 水系统页面内容 - 纯UI，接收状态和回调
     */
    @androidx.compose.runtime.Composable()
    private static final void WaterScreenContent(com.wuheng.smart.presentation.water.WaterUiState uiState, kotlin.jvm.functions.Function1<? super com.wuheng.smart.presentation.water.HotWaterMode, kotlin.Unit> onHotWaterModeSelected, kotlin.jvm.functions.Function0<kotlin.Unit> onDurationClick, kotlin.jvm.functions.Function0<kotlin.Unit> onSterilizationEdit, kotlin.jvm.functions.Function0<kotlin.Unit> onFilterReplaceClick, kotlin.jvm.functions.Function0<kotlin.Unit> onRefresh) {
    }
    
    @androidx.compose.runtime.Composable()
    @androidx.compose.ui.tooling.preview.Preview(showBackground = true, name = "\u6c34\u7cfb\u7edf-\u6b63\u5e38\u72b6\u6001", backgroundColor = 4293981432L)
    public static final void WaterScreenPreview() {
    }
    
    @androidx.compose.runtime.Composable()
    @androidx.compose.ui.tooling.preview.Preview(showBackground = true, name = "\u6c34\u7cfb\u7edf-\u5bbd\u5c4f720dp", widthDp = 720, backgroundColor = 4293981432L)
    public static final void WaterScreenWidePreview() {
    }
}