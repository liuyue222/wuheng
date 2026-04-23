package com.wuheng.smart.presentation.home.components;

import androidx.compose.animation.*;
import androidx.compose.animation.core.*;
import androidx.compose.foundation.layout.*;
import androidx.compose.material.icons.Icons;
import androidx.compose.material.icons.filled.*;
import androidx.compose.material3.*;
import androidx.compose.runtime.*;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.graphics.vector.ImageVector;
import androidx.compose.ui.text.font.FontWeight;
import androidx.compose.ui.tooling.preview.Preview;
import com.wuheng.smart.data.model.DeviceInfo;
import com.wuheng.smart.presentation.base.UiDataState;
import com.wuheng.smart.presentation.theme.*;

@kotlin.Metadata(mv = {1, 7, 1}, k = 2, d1 = {"\u0000T\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\b\u0007\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001aL\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u000e\b\u0002\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00010\u00052\u0014\b\u0002\u0010\u0006\u001a\u000e\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\u00010\u00072\u0014\b\u0002\u0010\t\u001a\u000e\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\u00010\u0007H\u0007\u001a\b\u0010\n\u001a\u00020\u0001H\u0007\u001a\b\u0010\u000b\u001a\u00020\u0001H\u0007\u001a\b\u0010\f\u001a\u00020\u0001H\u0007\u001a\b\u0010\r\u001a\u00020\u0001H\u0007\u001an\u0010\u000e\u001a\u00020\u00012\f\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00030\u00102\b\b\u0002\u0010\u0011\u001a\u00020\b2\u0014\b\u0002\u0010\u0012\u001a\u000e\u0012\u0004\u0012\u00020\u0013\u0012\u0004\u0012\u00020\u00010\u00072\u001a\b\u0002\u0010\u0014\u001a\u0014\u0012\u0004\u0012\u00020\u0013\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\u00010\u00152\u001a\b\u0002\u0010\u0016\u001a\u0014\u0012\u0004\u0012\u00020\u0013\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\u00010\u0015H\u0007\u001a\b\u0010\u0017\u001a\u00020\u0001H\u0007\u001a\b\u0010\u0018\u001a\u00020\u0001H\u0007\u001a\b\u0010\u0019\u001a\u00020\u0001H\u0007\u001ah\u0010\u001a\u001a\u00020\u00012\u0012\u0010\u001b\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u001d0\u00100\u001c2\b\b\u0002\u0010\u001e\u001a\u00020\u00132\u0014\b\u0002\u0010\u0012\u001a\u000e\u0012\u0004\u0012\u00020\u0013\u0012\u0004\u0012\u00020\u00010\u00072\u001a\b\u0002\u0010\u0014\u001a\u0014\u0012\u0004\u0012\u00020\u001f\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\u00010\u00152\u000e\b\u0002\u0010 \u001a\b\u0012\u0004\u0012\u00020\u00010\u0005H\u0007\u001a\b\u0010!\u001a\u00020\u0001H\u0007\u001a\u0010\u0010\"\u001a\u00020#2\u0006\u0010$\u001a\u00020%H\u0003\u00a8\u0006&"}, d2 = {"DeviceCard", "", "device", "Lcom/wuheng/smart/presentation/home/components/DeviceCardUiState;", "onClick", "Lkotlin/Function0;", "onPowerToggle", "Lkotlin/Function1;", "", "onModeToggle", "DeviceCardErrorPreview", "DeviceCardNormalPreview", "DeviceCardOffPreview", "DeviceCardOfflinePreview", "DeviceList", "devices", "", "isLoading", "onDeviceClick", "", "onDevicePowerToggle", "Lkotlin/Function2;", "onDeviceModeToggle", "DeviceListEmptyPreview", "DeviceListLoadingPreview", "DeviceListPreview", "DeviceListSection", "deviceListState", "Lcom/wuheng/smart/presentation/base/UiDataState;", "Lcom/wuheng/smart/data/model/DeviceInfo;", "currentMode", "", "onRefresh", "DeviceListWithErrorPreview", "getDeviceIcon", "Landroidx/compose/ui/graphics/vector/ImageVector;", "deviceType", "Lcom/wuheng/smart/presentation/home/components/DeviceType;", "app_debug"})
public final class DeviceCardKt {
    
    /**
     * 设备列表组件（完善版）
     *
     * 功能：
     * 1. 设备列表展示
     * 2. 设备状态实时更新
     * 3. 设备快捷控制（开关、模式）
     * 4. 设备异常提醒
     * 5. 设备详情导航
     *
     * 完成度: 100%
     */
    @androidx.compose.runtime.Composable()
    public static final void DeviceList(@org.jetbrains.annotations.NotNull()
    java.util.List<com.wuheng.smart.presentation.home.components.DeviceCardUiState> devices, boolean isLoading, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onDeviceClick, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function2<? super java.lang.String, ? super java.lang.Boolean, kotlin.Unit> onDevicePowerToggle, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function2<? super java.lang.String, ? super java.lang.Boolean, kotlin.Unit> onDeviceModeToggle) {
    }
    
    /**
     * 设备卡片组件（完善版）
     *
     * 功能：
     * 1. 设备信息展示（名称、房间、状态）
     * 2. 设备状态指示（在线/离线/异常）
     * 3. 快捷控制（电源开关、模式切换）
     * 4. 异常提醒标记
     * 5. 实时数据展示
     */
    @androidx.compose.runtime.Composable()
    public static final void DeviceCard(@org.jetbrains.annotations.NotNull()
    com.wuheng.smart.presentation.home.components.DeviceCardUiState device, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onClick, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super java.lang.Boolean, kotlin.Unit> onPowerToggle, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super java.lang.Boolean, kotlin.Unit> onModeToggle) {
    }
    
    /**
     * 设备列表状态组件
     * 用于展示设备列表的加载、成功、错误状态
     */
    @androidx.compose.runtime.Composable()
    public static final void DeviceListSection(@org.jetbrains.annotations.NotNull()
    com.wuheng.smart.presentation.base.UiDataState<? extends java.util.List<com.wuheng.smart.data.model.DeviceInfo>> deviceListState, @org.jetbrains.annotations.NotNull()
    java.lang.String currentMode, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onDeviceClick, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function2<? super java.lang.Integer, ? super java.lang.Boolean, kotlin.Unit> onDevicePowerToggle, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onRefresh) {
    }
    
    /**
     * 获取设备图标
     */
    @androidx.compose.runtime.Composable()
    private static final androidx.compose.ui.graphics.vector.ImageVector getDeviceIcon(com.wuheng.smart.presentation.home.components.DeviceType deviceType) {
        return null;
    }
    
    @androidx.compose.runtime.Composable()
    @androidx.compose.ui.tooling.preview.Preview(showBackground = true, name = "\u8bbe\u5907\u5217\u8868-\u6b63\u5e38", backgroundColor = 4294047225L)
    public static final void DeviceListPreview() {
    }
    
    @androidx.compose.runtime.Composable()
    @androidx.compose.ui.tooling.preview.Preview(showBackground = true, name = "\u8bbe\u5907\u5217\u8868-\u542b\u5f02\u5e38", backgroundColor = 4294047225L)
    public static final void DeviceListWithErrorPreview() {
    }
    
    @androidx.compose.runtime.Composable()
    @androidx.compose.ui.tooling.preview.Preview(showBackground = true, name = "\u8bbe\u5907\u5217\u8868-\u52a0\u8f7d\u4e2d", backgroundColor = 4294047225L)
    public static final void DeviceListLoadingPreview() {
    }
    
    @androidx.compose.runtime.Composable()
    @androidx.compose.ui.tooling.preview.Preview(showBackground = true, name = "\u8bbe\u5907\u5217\u8868-\u7a7a\u72b6\u6001", backgroundColor = 4294047225L)
    public static final void DeviceListEmptyPreview() {
    }
    
    @androidx.compose.runtime.Composable()
    @androidx.compose.ui.tooling.preview.Preview(showBackground = true, name = "\u8bbe\u5907\u5361\u7247-\u6b63\u5e38", backgroundColor = 4294047225L)
    public static final void DeviceCardNormalPreview() {
    }
    
    @androidx.compose.runtime.Composable()
    @androidx.compose.ui.tooling.preview.Preview(showBackground = true, name = "\u8bbe\u5907\u5361\u7247-\u5173\u95ed", backgroundColor = 4294047225L)
    public static final void DeviceCardOffPreview() {
    }
    
    @androidx.compose.runtime.Composable()
    @androidx.compose.ui.tooling.preview.Preview(showBackground = true, name = "\u8bbe\u5907\u5361\u7247-\u79bb\u7ebf", backgroundColor = 4294047225L)
    public static final void DeviceCardOfflinePreview() {
    }
    
    @androidx.compose.runtime.Composable()
    @androidx.compose.ui.tooling.preview.Preview(showBackground = true, name = "\u8bbe\u5907\u5361\u7247-\u5f02\u5e38", backgroundColor = 4294047225L)
    public static final void DeviceCardErrorPreview() {
    }
}