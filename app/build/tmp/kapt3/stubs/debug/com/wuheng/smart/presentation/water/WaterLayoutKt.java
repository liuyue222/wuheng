package com.wuheng.smart.presentation.water;

import androidx.compose.foundation.layout.*;
import androidx.compose.foundation.text.KeyboardOptions;
import androidx.compose.material.icons.Icons;
import androidx.compose.material3.*;
import androidx.compose.runtime.Composable;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.graphics.StrokeCap;
import androidx.compose.ui.graphics.drawscope.Stroke;
import androidx.compose.ui.text.font.FontWeight;
import androidx.compose.ui.text.input.KeyboardType;
import androidx.compose.ui.unit.Dp;
import com.wuheng.smart.presentation.components.*;
import com.wuheng.smart.presentation.theme.*;

@kotlin.Metadata(mv = {1, 7, 1}, k = 2, d1 = {"\u0000x\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\u001a\u0010\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003H\u0003\u001a\u0094\u0001\u0010\u0004\u001a\u00020\u00012\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00030\u00062\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00010\b2`\u0010\t\u001a\\\u0012\u0013\u0012\u00110\u000b\u00a2\u0006\f\b\f\u0012\b\b\r\u0012\u0004\b\b(\u000e\u0012\u0013\u0012\u00110\u000b\u00a2\u0006\f\b\f\u0012\b\b\r\u0012\u0004\b\b(\u000f\u0012\u0013\u0012\u00110\u000b\u00a2\u0006\f\b\f\u0012\b\b\r\u0012\u0004\b\b(\u0010\u0012\u0013\u0012\u00110\u000b\u00a2\u0006\f\b\f\u0012\b\b\r\u0012\u0004\b\b(\u0011\u0012\u0004\u0012\u00020\u00010\n2\f\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00010\u0013H\u0007\u001a$\u0010\u0014\u001a\u00020\u00012\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00030\u00062\f\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00010\u0013H\u0003\u001a:\u0010\u0016\u001a\u00020\u00012\u0006\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\u001a2\u0012\u0010\u001b\u001a\u000e\u0012\u0004\u0012\u00020\u0018\u0012\u0004\u0012\u00020\u00010\u001c2\f\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\u00010\u0013H\u0003\u001a0\u0010\u001e\u001a\u00020\u00012\u0006\u0010\u001f\u001a\u00020\u000b2\u0006\u0010 \u001a\u00020!2\f\u0010\"\u001a\b\u0012\u0004\u0012\u00020\u00010\u00132\b\b\u0002\u0010#\u001a\u00020$H\u0003\u001a>\u0010%\u001a\u00020\u00012\u0006\u0010&\u001a\u00020\u001a2\u0012\u0010\'\u001a\u000e\u0012\u0004\u0012\u00020\u001a\u0012\u0004\u0012\u00020\u00010\u001c2\u0006\u0010(\u001a\u00020)2\u0006\u0010*\u001a\u00020\u000b2\b\b\u0002\u0010#\u001a\u00020$H\u0003\u001a\u001e\u0010+\u001a\u00020\u00012\u0006\u0010,\u001a\u00020\u000b2\f\u0010-\u001a\b\u0012\u0004\u0012\u00020\u00010\u0013H\u0003\u001ay\u0010.\u001a\u00020\u00012\u0006\u0010/\u001a\u00020\u000b2\f\u00100\u001a\b\u0012\u0004\u0012\u00020\u00010\b2K\u0010\t\u001aG\u0012\u0013\u0012\u00110\u001a\u00a2\u0006\f\b\f\u0012\b\b\r\u0012\u0004\b\b(2\u0012\u0013\u0012\u00110\u001a\u00a2\u0006\f\b\f\u0012\b\b\r\u0012\u0004\b\b(3\u0012\u0013\u0012\u00110\u001a\u00a2\u0006\f\b\f\u0012\b\b\r\u0012\u0004\b\b(4\u0012\u0004\u0012\u00020\u0001012\f\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00010\u0013H\u0007\u001ao\u00105\u001a\u00020\u00012\u0006\u00106\u001a\u0002072\u0012\u00108\u001a\u000e\u0012\u0004\u0012\u00020\u0018\u0012\u0004\u0012\u00020\u00010\u001c2\f\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\u00010\u00132\f\u00109\u001a\b\u0012\u0004\u0012\u00020\u00010\u00132\f\u0010:\u001a\b\u0012\u0004\u0012\u00020\u00010\u00132\b\b\u0002\u0010#\u001a\u00020$2\b\b\u0002\u0010;\u001a\u00020<H\u0007\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b=\u0010>\u0082\u0002\u000b\n\u0005\b\u00a1\u001e0\u0001\n\u0002\b\u0019\u00a8\u0006?"}, d2 = {"FilterItemRow", "", "filter", "Lcom/wuheng/smart/presentation/water/FilterItem;", "FilterReplaceDialog", "filters", "", "filterReplaceState", "Lcom/wuheng/smart/presentation/base/UiDataState;", "onConfirm", "Lkotlin/Function4;", "", "Lkotlin/ParameterName;", "name", "filterId", "contactName", "contactPhone", "appointmentDate", "onDismiss", "Lkotlin/Function0;", "FilterStatusCard", "onReplaceClick", "HotWaterCirculationCard", "currentMode", "Lcom/wuheng/smart/presentation/water/HotWaterMode;", "currentTemp", "", "onModeSelected", "Lkotlin/Function1;", "onDurationClick", "ModeButton", "label", "isSelected", "", "onClick", "modifier", "Landroidx/compose/ui/Modifier;", "NumberPicker", "value", "onValueChange", "range", "Lkotlin/ranges/IntRange;", "suffix", "SterilizationCard", "scheduleTime", "onEditClick", "SterilizationTimePickerDialog", "currentSchedule", "sterilizationState", "Lkotlin/Function3;", "dayOfWeek", "hour", "minute", "WaterLayout", "uiState", "Lcom/wuheng/smart/presentation/water/WaterUiState;", "onHotWaterModeSelected", "onSterilizationEdit", "onFilterReplaceClick", "maxWidth", "Landroidx/compose/ui/unit/Dp;", "WaterLayout-6ZxE2Lo", "(Lcom/wuheng/smart/presentation/water/WaterUiState;Lkotlin/jvm/functions/Function1;Lkotlin/jvm/functions/Function0;Lkotlin/jvm/functions/Function0;Lkotlin/jvm/functions/Function0;Landroidx/compose/ui/Modifier;F)V", "app_debug"})
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
    
    /**
     * 滤芯预约更换弹窗
     *
     * @param filters 滤芯列表
     * @param filterReplaceState 预约状态
     * @param onConfirm 确认回调 (filterId, contactName, contactPhone, appointmentDate)
     * @param onDismiss 取消/关闭回调
     */
    @androidx.compose.runtime.Composable()
    @kotlin.OptIn(markerClass = {androidx.compose.material3.ExperimentalMaterial3Api.class})
    public static final void FilterReplaceDialog(@org.jetbrains.annotations.NotNull()
    java.util.List<com.wuheng.smart.presentation.water.FilterItem> filters, @org.jetbrains.annotations.NotNull()
    com.wuheng.smart.presentation.base.UiDataState<kotlin.Unit> filterReplaceState, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function4<? super java.lang.String, ? super java.lang.String, ? super java.lang.String, ? super java.lang.String, kotlin.Unit> onConfirm, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onDismiss) {
    }
}