package com.wuheng.smart.presentation.home.components;

import androidx.compose.animation.core.Spring;
import androidx.compose.foundation.layout.*;
import androidx.compose.material3.*;
import androidx.compose.runtime.Composable;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.graphics.ColorFilter;
import androidx.compose.ui.text.font.FontWeight;
import androidx.compose.ui.tooling.preview.Preview;
import com.wuheng.smart.R;
import com.wuheng.smart.data.model.WeatherMode;
import com.wuheng.smart.presentation.theme.*;

@kotlin.Metadata(mv = {1, 7, 1}, k = 2, d1 = {"\u0000@\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\u001a8\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00052\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00010\b2\b\b\u0002\u0010\t\u001a\u00020\nH\u0003\u001a2\u0010\u000b\u001a\u00020\u00012\b\b\u0002\u0010\f\u001a\u00020\r2\u0014\b\u0002\u0010\u000e\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00010\u000f2\b\b\u0002\u0010\t\u001a\u00020\nH\u0007\u001a\b\u0010\u0010\u001a\u00020\u0001H\u0007\u001a\b\u0010\u0011\u001a\u00020\u0001H\u0007\u001a\b\u0010\u0012\u001a\u00020\u0001H\u0007\u001a\u0012\u0010\u0013\u001a\u00020\u0014*\u00020\u0003\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0015\u001a\n\u0010\u0016\u001a\u00020\u0017*\u00020\u0003\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006\u0018"}, d2 = {"ModeButtonHorizontal", "", "mode", "Lcom/wuheng/smart/data/model/WeatherMode;", "isSelected", "", "isEnabled", "onClick", "Lkotlin/Function0;", "modifier", "Landroidx/compose/ui/Modifier;", "WeatherModeSelector", "uiState", "Lcom/wuheng/smart/presentation/home/components/WeatherModeSelectorUiState;", "onModeSelected", "Lkotlin/Function1;", "WeatherModeSelectorCoolingPreview", "WeatherModeSelectorDisabledPreview", "WeatherModeSelectorHeatingPreview", "getColor", "Landroidx/compose/ui/graphics/Color;", "(Lcom/wuheng/smart/data/model/WeatherMode;)J", "getDisplayName", "", "app_debug"})
public final class WeatherModeSelectorKt {
    
    @androidx.compose.runtime.Composable()
    public static final void WeatherModeSelector(@org.jetbrains.annotations.NotNull()
    com.wuheng.smart.presentation.home.components.WeatherModeSelectorUiState uiState, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super com.wuheng.smart.data.model.WeatherMode, kotlin.Unit> onModeSelected, @org.jetbrains.annotations.NotNull()
    androidx.compose.ui.Modifier modifier) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void ModeButtonHorizontal(com.wuheng.smart.data.model.WeatherMode mode, boolean isSelected, boolean isEnabled, kotlin.jvm.functions.Function0<kotlin.Unit> onClick, androidx.compose.ui.Modifier modifier) {
    }
    
    /**
     * 获取模式的显示名称（基于第二版设计图）
     */
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String getDisplayName(@org.jetbrains.annotations.NotNull()
    com.wuheng.smart.data.model.WeatherMode $this$getDisplayName) {
        return null;
    }
    
    /**
     * 获取模式对应的颜色
     */
    public static final long getColor(@org.jetbrains.annotations.NotNull()
    com.wuheng.smart.data.model.WeatherMode $this$getColor) {
        return 0L;
    }
    
    @androidx.compose.runtime.Composable()
    @androidx.compose.ui.tooling.preview.Preview(showBackground = true, name = "\u5929\u6c14\u6a21\u5f0f\u9009\u62e9\u5668-\u5236\u51b7", backgroundColor = 4294047225L)
    public static final void WeatherModeSelectorCoolingPreview() {
    }
    
    @androidx.compose.runtime.Composable()
    @androidx.compose.ui.tooling.preview.Preview(showBackground = true, name = "\u5929\u6c14\u6a21\u5f0f\u9009\u62e9\u5668-\u5236\u70ed", backgroundColor = 4294047225L)
    public static final void WeatherModeSelectorHeatingPreview() {
    }
    
    @androidx.compose.runtime.Composable()
    @androidx.compose.ui.tooling.preview.Preview(showBackground = true, name = "\u5929\u6c14\u6a21\u5f0f\u9009\u62e9\u5668-\u7981\u7528", backgroundColor = 4294047225L)
    public static final void WeatherModeSelectorDisabledPreview() {
    }
}