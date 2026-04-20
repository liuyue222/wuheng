package com.wuheng.smart.presentation.home;

import androidx.compose.foundation.ExperimentalFoundationApi;
import androidx.compose.foundation.layout.*;
import androidx.compose.material3.*;
import androidx.compose.runtime.*;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.text.font.FontWeight;
import com.wuheng.smart.R;
import com.wuheng.smart.presentation.theme.*;
import java.util.*;

@kotlin.Metadata(mv = {1, 7, 1}, k = 2, d1 = {"\u00006\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\u001a9\u0010\u0000\u001a\u00020\u00012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00010\u00032!\u0010\u0004\u001a\u001d\u0012\u0013\u0012\u00110\u0006\u00a2\u0006\f\b\u0007\u0012\b\b\b\u0012\u0004\b\b(\t\u0012\u0004\u0012\u00020\u00010\u0005H\u0007\u001a9\u0010\n\u001a\u00020\u00012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00010\u00032!\u0010\u0004\u001a\u001d\u0012\u0013\u0012\u00110\u0006\u00a2\u0006\f\b\u0007\u0012\b\b\b\u0012\u0004\b\b(\t\u0012\u0004\u0012\u00020\u00010\u0005H\u0003\u001aD\u0010\u000b\u001a\u00020\u00012\u0006\u0010\f\u001a\u00020\r2\u0012\u0010\u000e\u001a\u000e\u0012\u0004\u0012\u00020\r\u0012\u0004\u0012\u00020\u00010\u00052\f\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\r0\u00102\u0006\u0010\u0011\u001a\u00020\u00062\b\b\u0002\u0010\u0012\u001a\u00020\u0013H\u0003\u001a\u0018\u0010\u0014\u001a\u00020\r2\u0006\u0010\u0015\u001a\u00020\r2\u0006\u0010\u0016\u001a\u00020\rH\u0002\u00a8\u0006\u0017"}, d2 = {"VacationModeBottomSheet", "", "onDismiss", "Lkotlin/Function0;", "onConfirm", "Lkotlin/Function1;", "", "Lkotlin/ParameterName;", "name", "returnDateTime", "VacationModeContent", "WheelPicker", "value", "", "onValueChange", "range", "", "suffix", "modifier", "Landroidx/compose/ui/Modifier;", "getDaysInMonth", "year", "month", "app_debug"})
public final class VacationModeBottomSheetKt {
    
    /**
     * 度假模式对话框 - 手势滚动选择时间
     */
    @androidx.compose.runtime.Composable()
    public static final void VacationModeBottomSheet(@org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onDismiss, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onConfirm) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void VacationModeContent(kotlin.jvm.functions.Function0<kotlin.Unit> onDismiss, kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onConfirm) {
    }
    
    /**
     * 滚轮选择器 - 手势滚动
     */
    @androidx.compose.runtime.Composable()
    @kotlin.OptIn(markerClass = {androidx.compose.foundation.ExperimentalFoundationApi.class})
    private static final void WheelPicker(int value, kotlin.jvm.functions.Function1<? super java.lang.Integer, kotlin.Unit> onValueChange, java.util.List<java.lang.Integer> range, java.lang.String suffix, androidx.compose.ui.Modifier modifier) {
    }
    
    private static final int getDaysInMonth(int year, int month) {
        return 0;
    }
}