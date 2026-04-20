package com.wuheng.smart.presentation.device;

import androidx.compose.foundation.layout.*;
import androidx.compose.material.icons.Icons;
import androidx.compose.material3.*;
import androidx.compose.runtime.*;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.text.font.FontWeight;
import androidx.compose.ui.tooling.preview.Preview;
import com.wuheng.smart.data.model.DeviceData;
import com.wuheng.smart.data.model.DeviceInfo;
import com.wuheng.smart.data.model.DeviceRunStatus;
import com.wuheng.smart.presentation.base.UiDataState;
import com.wuheng.smart.presentation.theme.*;

@kotlin.OptIn(markerClass = {androidx.compose.material3.ExperimentalMaterial3Api.class})
@kotlin.Metadata(mv = {1, 7, 1}, k = 2, d1 = {"\u0000V\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\r\u001a?\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00032\u0006\u0010\u0005\u001a\u00020\u00032\u0006\u0010\u0006\u001a\u00020\u00072\b\b\u0002\u0010\b\u001a\u00020\u0003H\u0003\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b\t\u0010\n\u001a&\u0010\u000b\u001a\u00020\u00012\b\u0010\f\u001a\u0004\u0018\u00010\r2\u0012\u0010\u000e\u001a\u000e\u0012\u0004\u0012\u00020\u0010\u0012\u0004\u0012\u00020\u00010\u000fH\u0003\u001a\u0012\u0010\u0011\u001a\u00020\u00012\b\u0010\f\u001a\u0004\u0018\u00010\rH\u0003\u001a\u0080\u0001\u0010\u0012\u001a\u00020\u00012\f\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00150\u00142\f\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\r0\u00142\u000e\b\u0002\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00010\u00182\u000e\b\u0002\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u00010\u00182\u000e\b\u0002\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u00010\u00182\u0014\b\u0002\u0010\u001b\u001a\u000e\u0012\u0004\u0012\u00020\u001c\u0012\u0004\u0012\u00020\u00010\u000f2\u0014\b\u0002\u0010\u000e\u001a\u000e\u0012\u0004\u0012\u00020\u0010\u0012\u0004\u0012\u00020\u00010\u000fH\u0007\u001a\b\u0010\u001d\u001a\u00020\u0001H\u0007\u001a\b\u0010\u001e\u001a\u00020\u0001H\u0007\u001a@\u0010\u001f\u001a\u00020\u00012\u0006\u0010 \u001a\u00020\u00032\b\b\u0002\u0010!\u001a\u00020\"2\u000e\b\u0002\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00010\u00182\u0014\b\u0002\u0010\u0019\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00010\u000fH\u0007\u001a\b\u0010#\u001a\u00020\u0001H\u0007\u001a$\u0010$\u001a\u00020\u00012\u0006\u0010%\u001a\u00020\u00152\u0012\u0010\u001b\u001a\u000e\u0012\u0004\u0012\u00020\u001c\u0012\u0004\u0012\u00020\u00010\u000fH\u0003\u001a9\u0010&\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\'\u001a\u00020(2\b\b\u0002\u0010)\u001a\u00020\u0007H\u0003\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b*\u0010+\u001a\u0018\u0010,\u001a\u00020\u00072\u0006\u0010-\u001a\u00020\u0010H\u0002\u00f8\u0001\u0001\u00a2\u0006\u0002\u0010.\u001a\u0010\u0010/\u001a\u00020\u00032\u0006\u00100\u001a\u00020\u0003H\u0002\u001a\u0018\u00101\u001a\u00020\u00072\u0006\u00102\u001a\u00020\u0010H\u0002\u00f8\u0001\u0001\u00a2\u0006\u0002\u0010.\u001a\u0018\u00103\u001a\u00020\u00072\u0006\u00104\u001a\u00020\u0010H\u0002\u00f8\u0001\u0001\u00a2\u0006\u0002\u0010.\u0082\u0002\u000b\n\u0005\b\u00a1\u001e0\u0001\n\u0002\b\u0019\u00a8\u00065"}, d2 = {"DataItem", "", "label", "", "value", "icon", "color", "Landroidx/compose/ui/graphics/Color;", "unit", "DataItem-42QJj7c", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;JLjava/lang/String;)V", "DeviceControlCard", "deviceData", "Lcom/wuheng/smart/data/model/DeviceData;", "onFanSpeedChange", "Lkotlin/Function1;", "", "DeviceDataCard", "DeviceDetailContent", "deviceInfoState", "Lcom/wuheng/smart/presentation/base/UiDataState;", "Lcom/wuheng/smart/data/model/DeviceInfo;", "deviceDataState", "onNavigateBack", "Lkotlin/Function0;", "onNavigateToEdit", "onRefresh", "onPowerToggle", "", "DeviceDetailErrorPreview", "DeviceDetailLoadingPreview", "DeviceDetailScreen", "deviceId", "viewModel", "Lcom/wuheng/smart/presentation/device/DeviceDetailViewModel;", "DeviceDetailSuccessPreview", "DeviceInfoCard", "deviceInfo", "DeviceInfoItem", "modifier", "Landroidx/compose/ui/Modifier;", "valueColor", "DeviceInfoItem-g2O1Hgs", "(Ljava/lang/String;Ljava/lang/String;Landroidx/compose/ui/Modifier;J)V", "getCo2Color", "co2", "(I)J", "getDeviceTypeDisplay", "deviceType", "getPm25Color", "pm25", "getVocColor", "voc", "app_debug"})
public final class DeviceDetailScreenKt {
    
    /**
     * 设备详情页面 Composable
     *
     * 布局结构（基于设计图分析）:
     * - 顶部导航栏: 返回按钮 + 标题"设备详情" + 编辑按钮
     * - 设备基本信息卡片: 设备名称、型号、房间、在线状态
     * - 设备实时数据卡片: 温度、湿度、CO2、PM2.5、VOC
     * - 设备控制卡片: 风速调节、阀门开度、电源开关
     *
     * 设计图参考:
     *  - 设备详情设计图 -> 展示设备详细信息和控制功能
     */
    @androidx.compose.runtime.Composable()
    public static final void DeviceDetailScreen(@org.jetbrains.annotations.NotNull()
    java.lang.String deviceId, @org.jetbrains.annotations.NotNull()
    com.wuheng.smart.presentation.device.DeviceDetailViewModel viewModel, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onNavigateBack, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onNavigateToEdit) {
    }
    
    /**
     * 设备详情页面内容
     */
    @androidx.compose.runtime.Composable()
    public static final void DeviceDetailContent(@org.jetbrains.annotations.NotNull()
    com.wuheng.smart.presentation.base.UiDataState<com.wuheng.smart.data.model.DeviceInfo> deviceInfoState, @org.jetbrains.annotations.NotNull()
    com.wuheng.smart.presentation.base.UiDataState<com.wuheng.smart.data.model.DeviceData> deviceDataState, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onNavigateBack, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onNavigateToEdit, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onRefresh, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super java.lang.Boolean, kotlin.Unit> onPowerToggle, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super java.lang.Integer, kotlin.Unit> onFanSpeedChange) {
    }
    
    /**
     * 设备基本信息卡片
     *
     * 设计规范：
     * - 卡片背景: SurfaceLight (白色)
     * - 圆角: corner_md = 16dp
     * - 阴影: elevation_md = 4dp
     * - 内边距: card_padding_large = 20dp
     */
    @androidx.compose.runtime.Composable()
    private static final void DeviceInfoCard(com.wuheng.smart.data.model.DeviceInfo deviceInfo, kotlin.jvm.functions.Function1<? super java.lang.Boolean, kotlin.Unit> onPowerToggle) {
    }
    
    /**
     * 设备实时数据卡片
     *
     * 设计规范：
     * - 2x3 网格布局展示环境数据
     * - 温度、湿度、CO2、PM2.5、VOC
     */
    @androidx.compose.runtime.Composable()
    private static final void DeviceDataCard(com.wuheng.smart.data.model.DeviceData deviceData) {
    }
    
    /**
     * 设备控制卡片
     */
    @androidx.compose.runtime.Composable()
    private static final void DeviceControlCard(com.wuheng.smart.data.model.DeviceData deviceData, kotlin.jvm.functions.Function1<? super java.lang.Integer, kotlin.Unit> onFanSpeedChange) {
    }
    
    /**
     * 获取设备类型显示名称
     */
    private static final java.lang.String getDeviceTypeDisplay(java.lang.String deviceType) {
        return null;
    }
    
    /**
     * 获取CO2颜色
     */
    private static final long getCo2Color(int co2) {
        return 0L;
    }
    
    /**
     * 获取PM2.5颜色
     */
    private static final long getPm25Color(int pm25) {
        return 0L;
    }
    
    /**
     * 获取VOC颜色
     */
    private static final long getVocColor(int voc) {
        return 0L;
    }
    
    @androidx.compose.runtime.Composable()
    @androidx.compose.ui.tooling.preview.Preview(showBackground = true, name = "\u8bbe\u5907\u8be6\u60c5-\u52a0\u8f7d\u4e2d", backgroundColor = 4294047225L)
    public static final void DeviceDetailLoadingPreview() {
    }
    
    @androidx.compose.runtime.Composable()
    @androidx.compose.ui.tooling.preview.Preview(showBackground = true, name = "\u8bbe\u5907\u8be6\u60c5-\u6210\u529f", backgroundColor = 4294047225L)
    public static final void DeviceDetailSuccessPreview() {
    }
    
    @androidx.compose.runtime.Composable()
    @androidx.compose.ui.tooling.preview.Preview(showBackground = true, name = "\u8bbe\u5907\u8be6\u60c5-\u9519\u8bef", backgroundColor = 4294047225L)
    public static final void DeviceDetailErrorPreview() {
    }
}