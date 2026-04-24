package com.wuheng.smart.presentation.home;

import androidx.compose.animation.core.*;
import androidx.compose.foundation.layout.*;
import androidx.compose.runtime.*;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.graphics.Brush;
import androidx.compose.ui.graphics.drawscope.DrawScope;
import androidx.compose.ui.tooling.preview.Preview;

@kotlin.Metadata(mv = {1, 7, 1}, k = 2, d1 = {"\u0000F\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0007\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\u001a\b\u0010\u0000\u001a\u00020\u0001H\u0003\u001a\b\u0010\u0002\u001a\u00020\u0001H\u0003\u001a\b\u0010\u0003\u001a\u00020\u0001H\u0003\u001a\b\u0010\u0004\u001a\u00020\u0001H\u0003\u001a\b\u0010\u0005\u001a\u00020\u0001H\u0003\u001a\b\u0010\u0006\u001a\u00020\u0001H\u0003\u001a#\u0010\u0007\u001a\u00020\u00012\u0006\u0010\b\u001a\u00020\t2\u0011\u0010\n\u001a\r\u0012\u0004\u0012\u00020\u00010\u000b\u00a2\u0006\u0002\b\fH\u0007\u001a\b\u0010\r\u001a\u00020\u0001H\u0007\u001a\b\u0010\u000e\u001a\u00020\u0001H\u0007\u001a\b\u0010\u000f\u001a\u00020\u0001H\u0007\u001a\u0010\u0010\u0010\u001a\u00020\u00112\u0006\u0010\b\u001a\u00020\tH\u0002\u001a9\u0010\u0012\u001a\u00020\u0001*\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u00152\u0006\u0010\u0017\u001a\u00020\u00152\u0006\u0010\u0018\u001a\u00020\u0019H\u0002\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b\u001a\u0010\u001b\u001a\u001c\u0010\u001c\u001a\u00020\u0001*\u00020\u00132\u0006\u0010\u001d\u001a\u00020\u001e2\u0006\u0010\u001f\u001a\u00020\u0015H\u0002\u001a\u001c\u0010 \u001a\u00020\u0001*\u00020\u00132\u0006\u0010!\u001a\u00020\"2\u0006\u0010\u001f\u001a\u00020\u0015H\u0002\u0082\u0002\u000b\n\u0005\b\u00a1\u001e0\u0001\n\u0002\b\u0019\u00a8\u0006#"}, d2 = {"CloudyEffect", "", "FoggyEffect", "RainEffect", "SnowEffect", "SunnyEffect", "ThunderEffect", "WeatherBackground", "weather", "", "content", "Lkotlin/Function0;", "Landroidx/compose/runtime/Composable;", "WeatherBackgroundCloudyPreview", "WeatherBackgroundRainPreview", "WeatherBackgroundSunnyPreview", "parseWeatherType", "Lcom/wuheng/smart/presentation/home/WeatherType;", "drawCloud", "Landroidx/compose/ui/graphics/drawscope/DrawScope;", "x", "", "y", "size", "color", "Landroidx/compose/ui/graphics/Color;", "drawCloud-xwkQ0AY", "(Landroidx/compose/ui/graphics/drawscope/DrawScope;FFFJ)V", "drawRaindrop", "raindrop", "Lcom/wuheng/smart/presentation/home/Raindrop;", "progress", "drawSnowflake", "snowflake", "Lcom/wuheng/smart/presentation/home/Snowflake;", "app_debug"})
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
     * 解析天气字符串为天气类型
     */
    private static final com.wuheng.smart.presentation.home.WeatherType parseWeatherType(java.lang.String weather) {
        return null;
    }
    
    /**
     * 晴天效果 - 阳光射线
     */
    @androidx.compose.runtime.Composable()
    private static final void SunnyEffect() {
    }
    
    /**
     * 多云效果 - 飘动的云朵
     */
    @androidx.compose.runtime.Composable()
    private static final void CloudyEffect() {
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
    
    /**
     * 雪花效果
     */
    @androidx.compose.runtime.Composable()
    private static final void SnowEffect() {
    }
    
    /**
     * 绘制单个雪花
     */
    private static final void drawSnowflake(androidx.compose.ui.graphics.drawscope.DrawScope $this$drawSnowflake, com.wuheng.smart.presentation.home.Snowflake snowflake, float progress) {
    }
    
    /**
     * 雷雨效果
     */
    @androidx.compose.runtime.Composable()
    private static final void ThunderEffect() {
    }
    
    /**
     * 雾天效果
     */
    @androidx.compose.runtime.Composable()
    private static final void FoggyEffect() {
    }
    
    @androidx.compose.runtime.Composable()
    @androidx.compose.ui.tooling.preview.Preview(showBackground = true)
    public static final void WeatherBackgroundRainPreview() {
    }
    
    @androidx.compose.runtime.Composable()
    @androidx.compose.ui.tooling.preview.Preview(showBackground = true)
    public static final void WeatherBackgroundSunnyPreview() {
    }
    
    @androidx.compose.runtime.Composable()
    @androidx.compose.ui.tooling.preview.Preview(showBackground = true)
    public static final void WeatherBackgroundCloudyPreview() {
    }
}