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

@kotlin.Metadata(mv = {1, 7, 1}, k = 2, d1 = {"\u0000d\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\b\u000f\u001a0\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00032\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00062\u0006\u0010\b\u001a\u00020\u0003H\u0003\u001aA\u0010\t\u001a\u00020\u00012\u0006\u0010\n\u001a\u00020\u00032\u0006\u0010\u000b\u001a\u00020\u00032\u0006\u0010\f\u001a\u00020\u00032\b\b\u0002\u0010\r\u001a\u00020\u000e2\b\b\u0002\u0010\u000f\u001a\u00020\u0010H\u0003\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b\u0011\u0010\u0012\u001a\u008b\u0001\u0010\u0013\u001a\u00020\u00012\u0006\u0010\u0014\u001a\u00020\u00152\u0012\u0010\u0016\u001a\u000e\u0012\u0004\u0012\u00020\u0018\u0012\u0004\u0012\u00020\u00010\u00172\u0012\u0010\u0019\u001a\u000e\u0012\u0004\u0012\u00020\u001a\u0012\u0004\u0012\u00020\u00010\u00172\f\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u00010\u001c2\f\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\u00010\u001c2\b\b\u0002\u0010\u001e\u001a\u00020\u001f2\n\b\u0002\u0010 \u001a\u0004\u0018\u00010\u00032\b\b\u0002\u0010\r\u001a\u00020\u000e2\b\b\u0002\u0010!\u001a\u00020\"H\u0007\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b#\u0010$\u001a$\u0010%\u001a\u00020\u00012\u0006\u0010&\u001a\u00020\u00182\u0012\u0010\u0016\u001a\u000e\u0012\u0004\u0012\u00020\u0018\u0012\u0004\u0012\u00020\u00010\u0017H\u0003\u001a8\u0010\'\u001a\u00020\u00012\u0006\u0010(\u001a\u00020\u00062\u0006\u0010\n\u001a\u00020\u00032\u0006\u0010)\u001a\u00020\u001f2\f\u0010*\u001a\b\u0012\u0004\u0012\u00020\u00010\u001c2\b\b\u0002\u0010\r\u001a\u00020\u000eH\u0003\u001a\u001e\u0010+\u001a\u00020\u00012\u0006\u0010,\u001a\u00020\u00032\f\u0010*\u001a\b\u0012\u0004\u0012\u00020\u00010\u001cH\u0003\u001a0\u0010-\u001a\u00020\u00012\u0006\u0010.\u001a\u00020/2\u0006\u00100\u001a\u00020\u001f2\f\u0010*\u001a\b\u0012\u0004\u0012\u00020\u00010\u001c2\b\b\u0002\u0010\r\u001a\u00020\u000eH\u0003\u001a4\u00101\u001a\u00020\u00012\f\u00102\u001a\b\u0012\u0004\u0012\u00020/032\b\u00104\u001a\u0004\u0018\u00010\u001a2\u0012\u0010\u0019\u001a\u000e\u0012\u0004\u0012\u00020\u001a\u0012\u0004\u0012\u00020\u00010\u0017H\u0003\u001a\"\u00105\u001a\u00020\u00012\f\u0010*\u001a\b\u0012\u0004\u0012\u00020\u00010\u001c2\n\b\u0002\u00106\u001a\u0004\u0018\u00010\u0003H\u0003\u001a8\u00107\u001a\u00020\u00012\u0006\u00108\u001a\u00020\u00032\u0006\u00109\u001a\u00020\u00062\u0006\u0010:\u001a\u00020\u00032\u0006\u0010;\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00062\u0006\u0010<\u001a\u00020\u0006H\u0003\u001a\u0018\u0010=\u001a\u00020\u00102\u0006\u0010;\u001a\u00020\u0006H\u0002\u00f8\u0001\u0001\u00a2\u0006\u0002\u0010>\u001a\u0010\u0010?\u001a\u00020\u00032\u0006\u0010;\u001a\u00020\u0006H\u0002\u001a\u0018\u0010@\u001a\u00020\u00102\u0006\u0010\u0005\u001a\u00020\u0006H\u0002\u00f8\u0001\u0001\u00a2\u0006\u0002\u0010>\u001a\u0018\u0010A\u001a\u00020\u00102\u0006\u0010\u0007\u001a\u00020\u0006H\u0002\u00f8\u0001\u0001\u00a2\u0006\u0002\u0010>\u0082\u0002\u000b\n\u0005\b\u00a1\u001e0\u0001\n\u0002\b\u0019\u00a8\u0006B"}, d2 = {"EnvironmentDataCard", "", "indoorTemp", "", "indoorHumidity", "co2", "", "pm25", "tovc", "EnvironmentDataItem", "label", "value", "unit", "modifier", "Landroidx/compose/ui/Modifier;", "valueColor", "Landroidx/compose/ui/graphics/Color;", "EnvironmentDataItem-xwkQ0AY", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Landroidx/compose/ui/Modifier;J)V", "HomeLayout", "uiState", "Lcom/wuheng/smart/presentation/home/HomeUiState;", "onModeSelected", "Lkotlin/Function1;", "Lcom/wuheng/smart/presentation/home/ClimateMode;", "onSceneSelected", "Lcom/wuheng/smart/data/model/SceneType;", "onVacationModeClick", "Lkotlin/Function0;", "onResidenceClick", "vacationModeEnabled", "", "vacationStartTime", "maxWidth", "Landroidx/compose/ui/unit/Dp;", "HomeLayout-2jamyfo", "(Lcom/wuheng/smart/presentation/home/HomeUiState;Lkotlin/jvm/functions/Function1;Lkotlin/jvm/functions/Function1;Lkotlin/jvm/functions/Function0;Lkotlin/jvm/functions/Function0;ZLjava/lang/String;Landroidx/compose/ui/Modifier;F)V", "ModeSelector", "selectedMode", "ModeTab", "iconRes", "selected", "onClick", "ResidenceCard", "residenceName", "SceneButton", "scene", "Lcom/wuheng/smart/presentation/home/SceneItem;", "isSelected", "SceneSection", "scenes", "", "selectedSceneType", "VacationModeCard", "startTime", "WeatherHeader", "location", "outdoorTemp", "weather", "aqi", "humidity", "getAqiColor", "(I)J", "getAqiLevel", "getCo2Color", "getPm25Color", "app_debug"})
public final class HomeLayoutKt {
    
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