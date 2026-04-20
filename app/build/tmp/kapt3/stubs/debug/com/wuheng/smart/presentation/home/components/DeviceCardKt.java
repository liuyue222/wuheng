package com.wuheng.smart.presentation.home.components;

import androidx.compose.animation.core.Spring;
import androidx.compose.foundation.layout.*;
import androidx.compose.material3.*;
import androidx.compose.runtime.*;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.graphics.ColorFilter;
import androidx.compose.ui.text.font.FontWeight;
import androidx.compose.ui.tooling.preview.Preview;
import com.wuheng.smart.R;
import com.wuheng.smart.data.model.DeviceType;
import com.wuheng.smart.presentation.theme.*;

@kotlin.Metadata(mv = {1, 7, 1}, k = 2, d1 = {"\u0000>\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0000\u001a8\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0012\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00010\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00032\b\b\u0002\u0010\u0007\u001a\u00020\bH\u0003\u001af\u0010\t\u001a\u00020\u00012\u0006\u0010\n\u001a\u00020\u000b2\u0014\b\u0002\u0010\f\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00010\u00052\u0014\b\u0002\u0010\r\u001a\u000e\u0012\u0004\u0012\u00020\u000e\u0012\u0004\u0012\u00020\u00010\u00052\u000e\b\u0002\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00010\u00102\u000e\b\u0002\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00010\u00102\b\b\u0002\u0010\u0007\u001a\u00020\bH\u0007\u001a\b\u0010\u0012\u001a\u00020\u0001H\u0007\u001a\b\u0010\u0013\u001a\u00020\u0001H\u0007\u001a\b\u0010\u0014\u001a\u00020\u0001H\u0007\u001aL\u0010\u0015\u001a\u00020\u00012\u0006\u0010\n\u001a\u00020\u000b2\u001a\b\u0002\u0010\f\u001a\u0014\u0012\u0004\u0012\u00020\u0017\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00010\u00162\u0014\b\u0002\u0010\u0011\u001a\u000e\u0012\u0004\u0012\u00020\u0017\u0012\u0004\u0012\u00020\u00010\u00052\b\b\u0002\u0010\u0007\u001a\u00020\bH\u0007\u00a8\u0006\u0018"}, d2 = {"CustomSwitch", "", "isChecked", "", "onCheckedChange", "Lkotlin/Function1;", "enabled", "modifier", "Landroidx/compose/ui/Modifier;", "DeviceCard", "uiState", "Lcom/wuheng/smart/presentation/home/components/DeviceCardUiState;", "onPowerToggle", "onTempChange", "", "onModeToggle", "Lkotlin/Function0;", "onCardClick", "DeviceCardCoolingPreview", "DeviceCardHeatingPreview", "DeviceCardOffPreview", "DeviceListItem", "Lkotlin/Function2;", "", "app_debug"})
public final class DeviceCardKt {
    
    @androidx.compose.runtime.Composable()
    public static final void DeviceCard(@org.jetbrains.annotations.NotNull()
    com.wuheng.smart.presentation.home.components.DeviceCardUiState uiState, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super java.lang.Boolean, kotlin.Unit> onPowerToggle, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super java.lang.Float, kotlin.Unit> onTempChange, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onModeToggle, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onCardClick, @org.jetbrains.annotations.NotNull()
    androidx.compose.ui.Modifier modifier) {
    }
    
    /**
     * 自定义开关组件
     * 样式参考：kaiguan-guan-3.png (开启状态为蓝色圆形滑块)
     */
    @androidx.compose.runtime.Composable()
    private static final void CustomSwitch(boolean isChecked, kotlin.jvm.functions.Function1<? super java.lang.Boolean, kotlin.Unit> onCheckedChange, boolean enabled, androidx.compose.ui.Modifier modifier) {
    }
    
    /**
     * 设备卡片列表项（用于LazyColumn）
     */
    @androidx.compose.runtime.Composable()
    public static final void DeviceListItem(@org.jetbrains.annotations.NotNull()
    com.wuheng.smart.presentation.home.components.DeviceCardUiState uiState, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function2<? super java.lang.String, ? super java.lang.Boolean, kotlin.Unit> onPowerToggle, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onCardClick, @org.jetbrains.annotations.NotNull()
    androidx.compose.ui.Modifier modifier) {
    }
    
    @androidx.compose.runtime.Composable()
    @androidx.compose.ui.tooling.preview.Preview(showBackground = true, name = "\u8bbe\u5907\u5361\u7247-\u5236\u51b7\u5f00\u542f", backgroundColor = 4294047225L)
    public static final void DeviceCardCoolingPreview() {
    }
    
    @androidx.compose.runtime.Composable()
    @androidx.compose.ui.tooling.preview.Preview(showBackground = true, name = "\u8bbe\u5907\u5361\u7247-\u5236\u70ed\u5f00\u542f", backgroundColor = 4294047225L)
    public static final void DeviceCardHeatingPreview() {
    }
    
    @androidx.compose.runtime.Composable()
    @androidx.compose.ui.tooling.preview.Preview(showBackground = true, name = "\u8bbe\u5907\u5361\u7247-\u5173\u95ed", backgroundColor = 4294047225L)
    public static final void DeviceCardOffPreview() {
    }
}