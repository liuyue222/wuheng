package com.wuheng.smart.presentation.home;

import androidx.compose.animation.core.Spring;
import androidx.compose.foundation.layout.*;
import androidx.compose.material.icons.Icons;
import androidx.compose.material3.*;
import androidx.compose.runtime.Composable;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.graphics.Brush;
import androidx.compose.ui.text.font.FontWeight;
import androidx.compose.ui.unit.Dp;
import com.wuheng.smart.R;
import com.wuheng.smart.data.model.SceneType;
import com.wuheng.smart.presentation.components.*;
import com.wuheng.smart.presentation.theme.*;

@kotlin.Metadata(mv = {1, 7, 1}, k = 2, d1 = {"\u0000l\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\t\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\u0006\u001a\u0010\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003H\u0003\u001a0\u0010\u0004\u001a\u00020\u00012\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00062\u0006\u0010\b\u001a\u00020\u00032\u0006\u0010\t\u001a\u00020\u00032\u0006\u0010\n\u001a\u00020\u0006H\u0003\u001aA\u0010\u000b\u001a\u00020\u00012\u0006\u0010\f\u001a\u00020\u00062\u0006\u0010\r\u001a\u00020\u00062\u0006\u0010\u000e\u001a\u00020\u00062\b\b\u0002\u0010\u000f\u001a\u00020\u00102\b\b\u0002\u0010\u0011\u001a\u00020\u0012H\u0003\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b\u0013\u0010\u0014\u001a\u008b\u0001\u0010\u0015\u001a\u00020\u00012\u0006\u0010\u0016\u001a\u00020\u00172\u0012\u0010\u0018\u001a\u000e\u0012\u0004\u0012\u00020\u001a\u0012\u0004\u0012\u00020\u00010\u00192\u0012\u0010\u001b\u001a\u000e\u0012\u0004\u0012\u00020\u001c\u0012\u0004\u0012\u00020\u00010\u00192\f\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\u00010\u001e2\f\u0010\u001f\u001a\b\u0012\u0004\u0012\u00020\u00010\u001e2\b\b\u0002\u0010 \u001a\u00020!2\n\b\u0002\u0010\"\u001a\u0004\u0018\u00010\u00062\b\b\u0002\u0010\u000f\u001a\u00020\u00102\b\b\u0002\u0010#\u001a\u00020$H\u0007\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b%\u0010&\u001a$\u0010\'\u001a\u00020\u00012\u0006\u0010(\u001a\u00020\u001a2\u0012\u0010\u0018\u001a\u000e\u0012\u0004\u0012\u00020\u001a\u0012\u0004\u0012\u00020\u00010\u0019H\u0003\u001a8\u0010)\u001a\u00020\u00012\u0006\u0010*\u001a\u00020\u00032\u0006\u0010\f\u001a\u00020\u00062\u0006\u0010+\u001a\u00020!2\f\u0010,\u001a\b\u0012\u0004\u0012\u00020\u00010\u001e2\b\b\u0002\u0010\u000f\u001a\u00020\u0010H\u0003\u001a\u001e\u0010-\u001a\u00020\u00012\u0006\u0010.\u001a\u00020\u00062\f\u0010,\u001a\b\u0012\u0004\u0012\u00020\u00010\u001eH\u0003\u001a0\u0010/\u001a\u00020\u00012\u0006\u00100\u001a\u0002012\u0006\u00102\u001a\u00020!2\f\u0010,\u001a\b\u0012\u0004\u0012\u00020\u00010\u001e2\b\b\u0002\u0010\u000f\u001a\u00020\u0010H\u0003\u001a4\u00103\u001a\u00020\u00012\f\u00104\u001a\b\u0012\u0004\u0012\u000201052\b\u00106\u001a\u0004\u0018\u00010\u001c2\u0012\u0010\u001b\u001a\u000e\u0012\u0004\u0012\u00020\u001c\u0012\u0004\u0012\u00020\u00010\u0019H\u0003\u001a\"\u00107\u001a\u00020\u00012\f\u0010,\u001a\b\u0012\u0004\u0012\u00020\u00010\u001e2\n\b\u0002\u00108\u001a\u0004\u0018\u00010\u0006H\u0003\u001a8\u00109\u001a\u00020\u00012\u0006\u0010:\u001a\u00020\u00062\u0006\u0010;\u001a\u00020\u00032\u0006\u0010<\u001a\u00020\u00062\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\t\u001a\u00020\u00032\u0006\u0010=\u001a\u00020\u0003H\u0003\u001a\u001a\u0010>\u001a\u00020\u00012\u0006\u0010<\u001a\u00020\u00062\b\b\u0002\u0010\u000f\u001a\u00020\u0010H\u0003\u001a\u001f\u0010?\u001a\u000e\u0012\u0004\u0012\u00020\u0012\u0012\u0004\u0012\u00020\u00120@2\u0006\u0010\u0002\u001a\u00020\u0003H\u0002\u00f8\u0001\u0001\u001a\u0018\u0010A\u001a\u00020\u00122\u0006\u0010\u0002\u001a\u00020\u0003H\u0002\u00f8\u0001\u0001\u00a2\u0006\u0002\u0010B\u001a\u0010\u0010C\u001a\u00020\u00062\u0006\u0010\u0002\u001a\u00020\u0003H\u0002\u001a\u0018\u0010D\u001a\u00020\u00122\u0006\u0010\b\u001a\u00020\u0003H\u0002\u00f8\u0001\u0001\u00a2\u0006\u0002\u0010B\u001a\u0018\u0010E\u001a\u00020\u00122\u0006\u0010\t\u001a\u00020\u0003H\u0002\u00f8\u0001\u0001\u00a2\u0006\u0002\u0010B\u0082\u0002\u000b\n\u0005\b\u00a1\u001e0\u0001\n\u0002\b\u0019\u00a8\u0006F"}, d2 = {"AqiLevelBadge", "", "aqi", "", "EnvironmentDataCard", "indoorTemp", "", "indoorHumidity", "co2", "pm25", "tovc", "EnvironmentDataItem", "label", "value", "unit", "modifier", "Landroidx/compose/ui/Modifier;", "valueColor", "Landroidx/compose/ui/graphics/Color;", "EnvironmentDataItem-xwkQ0AY", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Landroidx/compose/ui/Modifier;J)V", "HomeLayout", "uiState", "Lcom/wuheng/smart/presentation/home/HomeUiState;", "onModeSelected", "Lkotlin/Function1;", "Lcom/wuheng/smart/presentation/home/ClimateMode;", "onSceneSelected", "Lcom/wuheng/smart/data/model/SceneType;", "onVacationModeClick", "Lkotlin/Function0;", "onResidenceClick", "vacationModeEnabled", "", "vacationStartTime", "maxWidth", "Landroidx/compose/ui/unit/Dp;", "HomeLayout-2jamyfo", "(Lcom/wuheng/smart/presentation/home/HomeUiState;Lkotlin/jvm/functions/Function1;Lkotlin/jvm/functions/Function1;Lkotlin/jvm/functions/Function0;Lkotlin/jvm/functions/Function0;ZLjava/lang/String;Landroidx/compose/ui/Modifier;F)V", "ModeSelector", "selectedMode", "ModeTab", "iconRes", "selected", "onClick", "ResidenceCard", "residenceName", "SceneButton", "scene", "Lcom/wuheng/smart/presentation/home/SceneItem;", "isSelected", "SceneSection", "scenes", "", "selectedSceneType", "VacationModeCard", "startTime", "WeatherHeader", "location", "outdoorTemp", "weather", "humidity", "WeatherIcon", "getAqiBadgeColors", "Lkotlin/Pair;", "getAqiColor", "(I)J", "getAqiLevel", "getCo2Color", "getPm25Color", "app_debug"})
public final class HomeLayoutKt {
    
    /**
     * 获取天气图标
     */
    @androidx.compose.runtime.Composable()
    private static final void WeatherIcon(java.lang.String weather, androidx.compose.ui.Modifier modifier) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void WeatherHeader(java.lang.String location, int outdoorTemp, java.lang.String weather, int aqi, int pm25, int humidity) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void ResidenceCard(java.lang.String residenceName, kotlin.jvm.functions.Function0<kotlin.Unit> onClick) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void ModeSelector(com.wuheng.smart.presentation.home.ClimateMode selectedMode, kotlin.jvm.functions.Function1<? super com.wuheng.smart.presentation.home.ClimateMode, kotlin.Unit> onModeSelected) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void ModeTab(int iconRes, java.lang.String label, boolean selected, kotlin.jvm.functions.Function0<kotlin.Unit> onClick, androidx.compose.ui.Modifier modifier) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void EnvironmentDataCard(java.lang.String indoorTemp, java.lang.String indoorHumidity, int co2, int pm25, java.lang.String tovc) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void SceneSection(java.util.List<com.wuheng.smart.presentation.home.SceneItem> scenes, com.wuheng.smart.data.model.SceneType selectedSceneType, kotlin.jvm.functions.Function1<? super com.wuheng.smart.data.model.SceneType, kotlin.Unit> onSceneSelected) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void SceneButton(com.wuheng.smart.presentation.home.SceneItem scene, boolean isSelected, kotlin.jvm.functions.Function0<kotlin.Unit> onClick, androidx.compose.ui.Modifier modifier) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void VacationModeCard(kotlin.jvm.functions.Function0<kotlin.Unit> onClick, java.lang.String startTime) {
    }
    
    /**
     * AQI 等级标签组件 - 带圆角背景和对应颜色
     * 参考 Apple Weather 和小米天气的设计风格
     */
    @androidx.compose.runtime.Composable()
    private static final void AqiLevelBadge(int aqi) {
    }
    
    /**
     * 获取 AQI 标签的背景色和文字色
     * 背景使用对应颜色的浅色版本，文字使用深色版本以确保可读性
     */
    private static final kotlin.Pair<androidx.compose.ui.graphics.Color, androidx.compose.ui.graphics.Color> getAqiBadgeColors(int aqi) {
        return null;
    }
    
    private static final long getAqiColor(int aqi) {
        return 0L;
    }
    
    private static final java.lang.String getAqiLevel(int aqi) {
        return null;
    }
    
    private static final long getCo2Color(int co2) {
        return 0L;
    }
    
    private static final long getPm25Color(int pm25) {
        return 0L;
    }
}