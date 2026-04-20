package com.wuheng.smart.presentation.water;

import androidx.compose.foundation.layout.*;
import androidx.compose.material.icons.Icons;
import androidx.compose.material3.*;
import androidx.compose.runtime.Composable;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.text.font.FontWeight;
import androidx.compose.ui.unit.Dp;
import com.wuheng.smart.presentation.components.*;
import com.wuheng.smart.presentation.theme.*;

@kotlin.Metadata(mv = {1, 7, 1}, k = 2, d1 = {"\u0000R\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\u001a\u0010\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003H\u0003\u001a$\u0010\u0004\u001a\u00020\u00012\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00030\u00062\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00010\bH\u0003\u001a2\u0010\t\u001a\u00020\u00012\u0006\u0010\n\u001a\u00020\u000b2\u0012\u0010\f\u001a\u000e\u0012\u0004\u0012\u00020\u000b\u0012\u0004\u0012\u00020\u00010\r2\f\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00010\bH\u0003\u001a0\u0010\u000f\u001a\u00020\u00012\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u00132\f\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00010\b2\b\b\u0002\u0010\u0015\u001a\u00020\u0016H\u0003\u001a\u001e\u0010\u0017\u001a\u00020\u00012\u0006\u0010\u0018\u001a\u00020\u00112\f\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u00010\bH\u0003\u001ao\u0010\u001a\u001a\u00020\u00012\u0006\u0010\u001b\u001a\u00020\u001c2\u0012\u0010\u001d\u001a\u000e\u0012\u0004\u0012\u00020\u000b\u0012\u0004\u0012\u00020\u00010\r2\f\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00010\b2\f\u0010\u001e\u001a\b\u0012\u0004\u0012\u00020\u00010\b2\f\u0010\u001f\u001a\b\u0012\u0004\u0012\u00020\u00010\b2\b\b\u0002\u0010\u0015\u001a\u00020\u00162\b\b\u0002\u0010 \u001a\u00020!H\u0007\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b\"\u0010#\u0082\u0002\u000b\n\u0005\b\u00a1\u001e0\u0001\n\u0002\b\u0019\u00a8\u0006$"}, d2 = {"FilterItemRow", "", "filter", "Lcom/wuheng/smart/presentation/water/FilterItem;", "FilterStatusCard", "filters", "", "onReplaceClick", "Lkotlin/Function0;", "HotWaterCirculationCard", "currentMode", "Lcom/wuheng/smart/presentation/water/HotWaterMode;", "onModeSelected", "Lkotlin/Function1;", "onDurationClick", "ModeButton", "label", "", "isSelected", "", "onClick", "modifier", "Landroidx/compose/ui/Modifier;", "SterilizationCard", "scheduleTime", "onEditClick", "WaterLayout", "uiState", "Lcom/wuheng/smart/presentation/water/WaterUiState;", "onHotWaterModeSelected", "onSterilizationEdit", "onFilterReplaceClick", "maxWidth", "Landroidx/compose/ui/unit/Dp;", "WaterLayout-6ZxE2Lo", "(Lcom/wuheng/smart/presentation/water/WaterUiState;Lkotlin/jvm/functions/Function1;Lkotlin/jvm/functions/Function0;Lkotlin/jvm/functions/Function0;Lkotlin/jvm/functions/Function0;Landroidx/compose/ui/Modifier;F)V", "app_debug"})
public final class WaterLayoutKt {
    
    @androidx.compose.runtime.Composable()
    private static final void HotWaterCirculationCard(com.wuheng.smart.presentation.water.HotWaterMode currentMode, kotlin.jvm.functions.Function1<? super com.wuheng.smart.presentation.water.HotWaterMode, kotlin.Unit> onModeSelected, kotlin.jvm.functions.Function0<kotlin.Unit> onDurationClick) {
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
}