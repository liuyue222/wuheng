package com.wuheng.smart.presentation.climate;

import androidx.compose.foundation.layout.*;
import androidx.compose.material.icons.Icons;
import androidx.compose.material3.*;
import androidx.compose.runtime.*;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.text.font.FontWeight;
import androidx.compose.ui.unit.Dp;
import com.wuheng.smart.presentation.components.*;
import com.wuheng.smart.presentation.theme.*;

@kotlin.OptIn(markerClass = {androidx.compose.material3.ExperimentalMaterial3Api.class})
@kotlin.Metadata(mv = {1, 7, 1}, k = 2, d1 = {"\u0000^\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\u001a\u009b\u0001\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0012\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00010\u00052\u0012\u0010\u0007\u001a\u000e\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\u00010\u00052\u0012\u0010\t\u001a\u000e\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\u00010\u00052\u0018\u0010\n\u001a\u0014\u0012\u0004\u0012\u00020\f\u0012\u0004\u0012\u00020\r\u0012\u0004\u0012\u00020\u00010\u000b2\u0012\u0010\u000e\u001a\u000e\u0012\u0004\u0012\u00020\f\u0012\u0004\u0012\u00020\u00010\u00052\b\b\u0002\u0010\u000f\u001a\u00020\u00102\b\b\u0002\u0010\u0011\u001a\u00020\u0012H\u0007\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b\u0013\u0010\u0014\u001a.\u0010\u0015\u001a\u00020\u00012\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\f2\u0006\u0010\u0019\u001a\u00020\r2\f\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u00010\u001bH\u0003\u001a$\u0010\u001c\u001a\u00020\u00012\u0006\u0010\u001d\u001a\u00020\u00062\u0012\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00010\u0005H\u0003\u001a8\u0010\u001e\u001a\u00020\u00012\u0006\u0010\u001f\u001a\u00020 2\u0012\u0010!\u001a\u000e\u0012\u0004\u0012\u00020\f\u0012\u0004\u0012\u00020\u00010\u00052\u0012\u0010\u001a\u001a\u000e\u0012\u0004\u0012\u00020\f\u0012\u0004\u0012\u00020\u00010\u0005H\u0003\u001a\u0010\u0010\"\u001a\u00020\u00012\u0006\u0010#\u001a\u00020$H\u0003\u001a$\u0010%\u001a\u00020\u00012\u0006\u0010&\u001a\u00020\b2\u0012\u0010\t\u001a\u000e\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\u00010\u0005H\u0003\u001a$\u0010\'\u001a\u00020\u00012\u0006\u0010(\u001a\u00020\b2\u0012\u0010\u0007\u001a\u000e\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\u00010\u0005H\u0003\u0082\u0002\u000b\n\u0005\b\u00a1\u001e0\u0001\n\u0002\b\u0019\u00a8\u0006)"}, d2 = {"ClimateLayout", "", "uiState", "Lcom/wuheng/smart/presentation/climate/ClimateUiState;", "onTabSelected", "Lkotlin/Function1;", "Lcom/wuheng/smart/presentation/climate/ClimateTab;", "onTemperatureChange", "", "onHumidityChange", "onFloorToggle", "Lkotlin/Function2;", "", "", "onFloorClick", "modifier", "Landroidx/compose/ui/Modifier;", "maxWidth", "Landroidx/compose/ui/unit/Dp;", "ClimateLayout-QMuTD5c", "(Lcom/wuheng/smart/presentation/climate/ClimateUiState;Lkotlin/jvm/functions/Function1;Lkotlin/jvm/functions/Function1;Lkotlin/jvm/functions/Function1;Lkotlin/jvm/functions/Function2;Lkotlin/jvm/functions/Function1;Landroidx/compose/ui/Modifier;F)V", "ClimateTabItem", "icon", "Landroidx/compose/ui/graphics/vector/ImageVector;", "label", "selected", "onClick", "Lkotlin/Function0;", "ClimateTabSelector", "selectedTab", "FloorCard", "floor", "Lcom/wuheng/smart/presentation/climate/FloorItem;", "onToggle", "FloorDeviceItem", "device", "Lcom/wuheng/smart/presentation/climate/FloorDevice;", "HumiditySettingCard", "humidity", "TemperatureSettingCard", "temperature", "app_debug"})
public final class ClimateLayoutKt {
    
    /**
     * Tab选择器
     */
    @androidx.compose.runtime.Composable()
    private static final void ClimateTabSelector(com.wuheng.smart.presentation.climate.ClimateTab selectedTab, kotlin.jvm.functions.Function1<? super com.wuheng.smart.presentation.climate.ClimateTab, kotlin.Unit> onTabSelected) {
    }
    
    /**
     * Tab项
     */
    @androidx.compose.runtime.Composable()
    private static final void ClimateTabItem(androidx.compose.ui.graphics.vector.ImageVector icon, java.lang.String label, boolean selected, kotlin.jvm.functions.Function0<kotlin.Unit> onClick) {
    }
    
    /**
     * 温度设定卡片
     */
    @androidx.compose.runtime.Composable()
    private static final void TemperatureSettingCard(float temperature, kotlin.jvm.functions.Function1<? super java.lang.Float, kotlin.Unit> onTemperatureChange) {
    }
    
    /**
     * 湿度设定卡片
     */
    @androidx.compose.runtime.Composable()
    private static final void HumiditySettingCard(float humidity, kotlin.jvm.functions.Function1<? super java.lang.Float, kotlin.Unit> onHumidityChange) {
    }
    
    /**
     * 楼层卡片
     */
    @androidx.compose.runtime.Composable()
    private static final void FloorCard(com.wuheng.smart.presentation.climate.FloorItem floor, kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onToggle, kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onClick) {
    }
    
    /**
     * 楼层设备项
     */
    @androidx.compose.runtime.Composable()
    private static final void FloorDeviceItem(com.wuheng.smart.presentation.climate.FloorDevice device) {
    }
}