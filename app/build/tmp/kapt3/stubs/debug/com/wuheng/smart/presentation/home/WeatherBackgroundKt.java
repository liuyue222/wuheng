package com.wuheng.smart.presentation.home;

import androidx.compose.animation.core.*;
import androidx.compose.foundation.layout.*;
import androidx.compose.runtime.*;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.graphics.Brush;
import androidx.compose.ui.graphics.StrokeCap;
import androidx.compose.ui.graphics.drawscope.DrawScope;
import androidx.compose.ui.tooling.preview.Preview;

@kotlin.Metadata(mv = {1, 7, 1}, k = 2, d1 = {"\u0000b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0007\n\u0002\b\b\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a\b\u0010\u0000\u001a\u00020\u0001H\u0003\u001a\b\u0010\u0002\u001a\u00020\u0001H\u0003\u001a<\u0010\u0003\u001a\u00020\u00012\u0006\u0010\u0004\u001a\u00020\u00052\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u00072\f\u0010\t\u001a\b\u0012\u0004\u0012\u00020\b0\u00072\u0006\u0010\n\u001a\u00020\u00052\u0006\u0010\u000b\u001a\u00020\u0005H\u0003\u001a\b\u0010\f\u001a\u00020\u0001H\u0003\u001a\b\u0010\r\u001a\u00020\u0001H\u0003\u001a\b\u0010\u000e\u001a\u00020\u0001H\u0003\u001a-\u0010\u000f\u001a\u00020\u00012\u0006\u0010\u0010\u001a\u00020\u00112\b\b\u0002\u0010\u0012\u001a\u00020\u00112\u0011\u0010\u0013\u001a\r\u0012\u0004\u0012\u00020\u00010\u0014\u00a2\u0006\u0002\b\u0015H\u0007\u001a\b\u0010\u0016\u001a\u00020\u0001H\u0007\u001a\b\u0010\u0017\u001a\u00020\u0001H\u0007\u001a\b\u0010\u0018\u001a\u00020\u0001H\u0007\u001a\b\u0010\u0019\u001a\u00020\u0001H\u0007\u001a\b\u0010\u001a\u001a\u00020\u0001H\u0007\u001a\u0019\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u001d0\u001c2\u0006\u0010\u001e\u001a\u00020\u001fH\u0002\u00f8\u0001\u0000\u001a\u0018\u0010 \u001a\u00020\u001f2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u0011H\u0002\u001a9\u0010!\u001a\u00020\u0001*\u00020\"2\u0006\u0010#\u001a\u00020\b2\u0006\u0010$\u001a\u00020\b2\u0006\u0010%\u001a\u00020\b2\u0006\u0010&\u001a\u00020\u001dH\u0002\u00f8\u0001\u0001\u00f8\u0001\u0000\u00a2\u0006\u0004\b\'\u0010(\u001a\u001c\u0010)\u001a\u00020\u0001*\u00020\"2\u0006\u0010*\u001a\u00020+2\u0006\u0010,\u001a\u00020\bH\u0002\u001a9\u0010-\u001a\u00020\u0001*\u00020\"2\u0006\u0010#\u001a\u00020\b2\u0006\u0010$\u001a\u00020\b2\u0006\u0010%\u001a\u00020\b2\u0006\u0010&\u001a\u00020\u001dH\u0002\u00f8\u0001\u0001\u00f8\u0001\u0000\u00a2\u0006\u0004\b.\u0010(\u001a\u001c\u0010/\u001a\u00020\u0001*\u00020\"2\u0006\u00100\u001a\u0002012\u0006\u0010,\u001a\u00020\bH\u0002\u001a\u001c\u00102\u001a\u00020\u0001*\u00020\"2\u0006\u00103\u001a\u0002042\u0006\u0010,\u001a\u00020\bH\u0002\u001a\f\u00105\u001a\u00020\b*\u000201H\u0002\u0082\u0002\u000b\n\u0002\b\u0019\n\u0005\b\u00a1\u001e0\u0001\u00a8\u00066"}, d2 = {"CloudyEffect", "", "FoggyEffect", "RainEffect", "density", "", "speedRange", "Lkotlin/ranges/ClosedFloatingPointRange;", "", "lineWidthRange", "splashCount", "animPeriodMs", "SnowEffect", "SunnyEffect", "ThunderFlashEffect", "WeatherBackground", "weather", "", "weatherCode", "content", "Lkotlin/Function0;", "Landroidx/compose/runtime/Composable;", "WeatherBackgroundFogPreview", "WeatherBackgroundHeavyRainPreview", "WeatherBackgroundLightRainPreview", "WeatherBackgroundSnowPreview", "WeatherBackgroundSunnyPreview", "backgroundColors", "", "Landroidx/compose/ui/graphics/Color;", "type", "Lcom/wuheng/smart/presentation/home/WeatherType;", "parseWeatherType", "drawCloud", "Landroidx/compose/ui/graphics/drawscope/DrawScope;", "x", "y", "size", "color", "drawCloud-xwkQ0AY", "(Landroidx/compose/ui/graphics/drawscope/DrawScope;FFFJ)V", "drawRaindrop", "raindrop", "Lcom/wuheng/smart/presentation/home/Raindrop;", "progress", "drawSmallCloud", "drawSmallCloud-xwkQ0AY", "drawSnowflake", "snowflake", "Lcom/wuheng/smart/presentation/home/Snowflake;", "drawSplash", "splash", "Lcom/wuheng/smart/presentation/home/Splash;", "phase", "app_debug"})
public final class WeatherBackgroundKt {
    
    @androidx.compose.runtime.Composable()
    public static final void WeatherBackground(@org.jetbrains.annotations.NotNull()
    java.lang.String weather, @org.jetbrains.annotations.NotNull()
    java.lang.String weatherCode, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> content) {
    }
    
    private static final java.util.List<androidx.compose.ui.graphics.Color> backgroundColors(com.wuheng.smart.presentation.home.WeatherType type) {
        return null;
    }
    
    private static final com.wuheng.smart.presentation.home.WeatherType parseWeatherType(java.lang.String weather, java.lang.String weatherCode) {
        return null;
    }
    
    @androidx.compose.runtime.Composable()
    private static final void SunnyEffect() {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void CloudyEffect() {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void RainEffect(int density, kotlin.ranges.ClosedFloatingPointRange<java.lang.Float> speedRange, kotlin.ranges.ClosedFloatingPointRange<java.lang.Float> lineWidthRange, int splashCount, int animPeriodMs) {
    }
    
    private static final void drawRaindrop(androidx.compose.ui.graphics.drawscope.DrawScope $this$drawRaindrop, com.wuheng.smart.presentation.home.Raindrop raindrop, float progress) {
    }
    
    private static final void drawSplash(androidx.compose.ui.graphics.drawscope.DrawScope $this$drawSplash, com.wuheng.smart.presentation.home.Splash splash, float progress) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void SnowEffect() {
    }
    
    private static final void drawSnowflake(androidx.compose.ui.graphics.drawscope.DrawScope $this$drawSnowflake, com.wuheng.smart.presentation.home.Snowflake snowflake, float progress) {
    }
    
    private static final float phase(com.wuheng.smart.presentation.home.Snowflake $this$phase) {
        return 0.0F;
    }
    
    @androidx.compose.runtime.Composable()
    private static final void ThunderFlashEffect() {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void FoggyEffect() {
    }
    
    @androidx.compose.runtime.Composable()
    @androidx.compose.ui.tooling.preview.Preview(showBackground = true)
    public static final void WeatherBackgroundHeavyRainPreview() {
    }
    
    @androidx.compose.runtime.Composable()
    @androidx.compose.ui.tooling.preview.Preview(showBackground = true)
    public static final void WeatherBackgroundLightRainPreview() {
    }
    
    @androidx.compose.runtime.Composable()
    @androidx.compose.ui.tooling.preview.Preview(showBackground = true)
    public static final void WeatherBackgroundSunnyPreview() {
    }
    
    @androidx.compose.runtime.Composable()
    @androidx.compose.ui.tooling.preview.Preview(showBackground = true)
    public static final void WeatherBackgroundSnowPreview() {
    }
    
    @androidx.compose.runtime.Composable()
    @androidx.compose.ui.tooling.preview.Preview(showBackground = true)
    public static final void WeatherBackgroundFogPreview() {
    }
}