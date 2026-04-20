package com.wuheng.smart.presentation.home;

import androidx.compose.animation.core.*;
import androidx.compose.foundation.layout.*;
import androidx.compose.runtime.*;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.graphics.Brush;
import androidx.compose.ui.graphics.drawscope.DrawScope;
import androidx.compose.ui.tooling.preview.Preview;

@kotlin.Metadata(mv = {1, 7, 1}, k = 2, d1 = {"\u0000.\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0000\u001a\b\u0010\u0000\u001a\u00020\u0001H\u0003\u001a#\u0010\u0002\u001a\u00020\u00012\u0006\u0010\u0003\u001a\u00020\u00042\u0011\u0010\u0005\u001a\r\u0012\u0004\u0012\u00020\u00010\u0006\u00a2\u0006\u0002\b\u0007H\u0007\u001a\b\u0010\b\u001a\u00020\u0001H\u0007\u001a\u001c\u0010\t\u001a\u00020\u0001*\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH\u0002\u00a8\u0006\u000f"}, d2 = {"RainEffect", "", "WeatherBackground", "weather", "", "content", "Lkotlin/Function0;", "Landroidx/compose/runtime/Composable;", "WeatherBackgroundRainPreview", "drawRaindrop", "Landroidx/compose/ui/graphics/drawscope/DrawScope;", "raindrop", "Lcom/wuheng/smart/presentation/home/Raindrop;", "progress", "", "app_debug"})
public final class WeatherBackgroundKt {
    
    /**
     * 天气背景效果
     * 根据天气类型显示不同的背景效果
     */
    @androidx.compose.runtime.Composable()
    public static final void WeatherBackground(@org.jetbrains.annotations.NotNull()
    java.lang.String weather, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> content) {
    }
    
    /**
     * 雨滴效果
     */
    @androidx.compose.runtime.Composable()
    private static final void RainEffect() {
    }
    
    /**
     * 绘制单个雨滴
     */
    private static final void drawRaindrop(androidx.compose.ui.graphics.drawscope.DrawScope $this$drawRaindrop, com.wuheng.smart.presentation.home.Raindrop raindrop, float progress) {
    }
    
    @androidx.compose.runtime.Composable()
    @androidx.compose.ui.tooling.preview.Preview(showBackground = true)
    public static final void WeatherBackgroundRainPreview() {
    }
}