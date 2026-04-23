package com.wuheng.smart.presentation.water;

import androidx.compose.foundation.layout.*;
import androidx.compose.material.icons.Icons;
import androidx.compose.material3.*;
import androidx.compose.runtime.Composable;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.graphics.StrokeCap;
import androidx.compose.ui.graphics.drawscope.Stroke;
import androidx.compose.ui.text.font.FontWeight;
import androidx.compose.ui.unit.Dp;
import com.wuheng.smart.presentation.components.*;
import com.wuheng.smart.presentation.theme.*;

@kotlin.Metadata(mv = {1, 7, 1}, k = 2, d1 = {"\u0000r\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\u001a\u0010\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003H\u0003\u001a$\u0010\u0004\u001a\u00020\u00012\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00030\u00062\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00010\bH\u0003\u001a:\u0010\t\u001a\u00020\u00012\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\r2\u0012\u0010\u000e\u001a\u000e\u0012\u0004\u0012\u00020\u000b\u0012\u0004\u0012\u00020\u00010\u000f2\f\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00010\bH\u0003\u001a0\u0010\u0011\u001a\u00020\u00012\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u00152\f\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00010\b2\b\b\u0002\u0010\u0017\u001a\u00020\u0018H\u0003\u001a>\u0010\u0019\u001a\u00020\u00012\u0006\u0010\u001a\u001a\u00020\r2\u0012\u0010\u001b\u001a\u000e\u0012\u0004\u0012\u00020\r\u0012\u0004\u0012\u00020\u00010\u000f2\u0006\u0010\u001c\u001a\u00020\u001d2\u0006\u0010\u001e\u001a\u00020\u00132\b\b\u0002\u0010\u0017\u001a\u00020\u0018H\u0003\u001a\u001e\u0010\u001f\u001a\u00020\u00012\u0006\u0010 \u001a\u00020\u00132\f\u0010!\u001a\b\u0012\u0004\u0012\u00020\u00010\bH\u0003\u001ay\u0010\"\u001a\u00020\u00012\u0006\u0010#\u001a\u00020\u00132\f\u0010$\u001a\b\u0012\u0004\u0012\u00020\u00010%2K\u0010&\u001aG\u0012\u0013\u0012\u00110\r\u00a2\u0006\f\b(\u0012\b\b)\u0012\u0004\b\b(*\u0012\u0013\u0012\u00110\r\u00a2\u0006\f\b(\u0012\b\b)\u0012\u0004\b\b(+\u0012\u0013\u0012\u00110\r\u00a2\u0006\f\b(\u0012\b\b)\u0012\u0004\b\b(,\u0012\u0004\u0012\u00020\u00010\'2\f\u0010-\u001a\b\u0012\u0004\u0012\u00020\u00010\bH\u0007\u001ao\u0010.\u001a\u00020\u00012\u0006\u0010/\u001a\u0002002\u0012\u00101\u001a\u000e\u0012\u0004\u0012\u00020\u000b\u0012\u0004\u0012\u00020\u00010\u000f2\f\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00010\b2\f\u00102\u001a\b\u0012\u0004\u0012\u00020\u00010\b2\f\u00103\u001a\b\u0012\u0004\u0012\u00020\u00010\b2\b\b\u0002\u0010\u0017\u001a\u00020\u00182\b\b\u0002\u00104\u001a\u000205H\u0007\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b6\u00107\u0082\u0002\u000b\n\u0005\b\u00a1\u001e0\u0001\n\u0002\b\u0019\u00a8\u00068"}, d2 = {"FilterItemRow", "", "filter", "Lcom/wuheng/smart/presentation/water/FilterItem;", "FilterStatusCard", "filters", "", "onReplaceClick", "Lkotlin/Function0;", "HotWaterCirculationCard", "currentMode", "Lcom/wuheng/smart/presentation/water/HotWaterMode;", "currentTemp", "", "onModeSelected", "Lkotlin/Function1;", "onDurationClick", "ModeButton", "label", "", "isSelected", "", "onClick", "modifier", "Landroidx/compose/ui/Modifier;", "NumberPicker", "value", "onValueChange", "range", "Lkotlin/ranges/IntRange;", "suffix", "SterilizationCard", "scheduleTime", "onEditClick", "SterilizationTimePickerDialog", "currentSchedule", "sterilizationState", "Lcom/wuheng/smart/presentation/base/UiDataState;", "onConfirm", "Lkotlin/Function3;", "Lkotlin/ParameterName;", "name", "dayOfWeek", "hour", "minute", "onDismiss", "WaterLayout", "uiState", "Lcom/wuheng/smart/presentation/water/WaterUiState;", "onHotWaterModeSelected", "onSterilizationEdit", "onFilterReplaceClick", "maxWidth", "Landroidx/compose/ui/unit/Dp;", "WaterLayout-6ZxE2Lo", "(Lcom/wuheng/smart/presentation/water/WaterUiState;Lkotlin/jvm/functions/Function1;Lkotlin/jvm/functions/Function0;Lkotlin/jvm/functions/Function0;Lkotlin/jvm/functions/Function0;Landroidx/compose/ui/Modifier;F)V", "app_debug"})
public final class WaterLayoutKt {
    
    @androidx.compose.runtime.Composable()
    private static final void HotWaterCirculationCard(com.wuheng.smart.presentation.water.HotWaterMode currentMode, int currentTemp, kotlin.jvm.functions.Function1<? super com.wuheng.smart.presentation.water.HotWaterMode, kotlin.Unit> onModeSelected, kotlin.jvm.functions.Function0<kotlin.Unit> onDurationClick) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void ModeButton(java.lang.String label, boolean isSelected, kotlin.jvm.functions.Function0<kotlin.Unit> onClick, androidx.compose.ui.Modifier modifier) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void SterilizationCard(java.lang.String scheduleTime, kotlin.jvm.functions.Function0<kotlin.Unit> onEditClick) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void FilterStatusCard(java.util.List<com.wuheng.smart.presentation.water.FilterItem> filters, kotlin.jvm.functions.Function0<kotlin.Unit> onReplaceClick) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void FilterItemRow(com.wuheng.smart.presentation.water.FilterItem filter) {
    }
    
    /**
     * 热力杀菌时间选择弹窗
     */
    @androidx.compose.runtime.Composable()
    public static final void SterilizationTimePickerDialog(@org.jetbrains.annotations.NotNull()
    java.lang.String currentSchedule, @org.jetbrains.annotations.NotNull()
    com.wuheng.smart.presentation.base.UiDataState<kotlin.Unit> sterilizationState, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function3<? super java.lang.Integer, ? super java.lang.Integer, ? super java.lang.Integer, kotlin.Unit> onConfirm, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onDismiss) {
    }
    
    /**
     * 数字选择器
     */
    @androidx.compose.runtime.Composable()
    private static final void NumberPicker(int value, kotlin.jvm.functions.Function1<? super java.lang.Integer, kotlin.Unit> onValueChange, kotlin.ranges.IntRange range, java.lang.String suffix, androidx.compose.ui.Modifier modifier) {
    }
}