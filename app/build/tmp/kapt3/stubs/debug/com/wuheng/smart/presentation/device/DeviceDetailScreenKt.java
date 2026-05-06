package com.wuheng.smart.presentation.device;

import androidx.compose.animation.*;
import androidx.compose.animation.core.*;
import androidx.compose.foundation.layout.*;
import androidx.compose.material.icons.Icons;
import androidx.compose.material.icons.filled.*;
import androidx.compose.material3.*;
import androidx.compose.runtime.*;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.graphics.StrokeCap;
import androidx.compose.ui.text.font.FontWeight;
import androidx.compose.ui.tooling.preview.Preview;
import com.wuheng.smart.data.model.DeviceData;
import com.wuheng.smart.data.model.DeviceInfo;
import com.wuheng.smart.data.model.DeviceRunStatus;
import com.wuheng.smart.data.model.DeviceStatus;
import com.wuheng.smart.presentation.base.UiDataState;
import com.wuheng.smart.presentation.theme.*;
import java.text.SimpleDateFormat;
import java.util.*;

@kotlin.OptIn(markerClass = {androidx.compose.material3.ExperimentalMaterial3Api.class})
@kotlin.Metadata(mv = {1, 7, 1}, k = 2, d1 = {"\u0000j\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0010\n\u0002\u0018\u0002\n\u0002\b\u0012\u001a5\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00032\u0006\u0010\u0005\u001a\u00020\u00032\u0006\u0010\u0006\u001a\u00020\u0007H\u0003\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b\b\u0010\t\u001aQ\u0010\n\u001a\u00020\u00012\u0006\u0010\u000b\u001a\u00020\u00032\u0006\u0010\f\u001a\u00020\u00032\u0006\u0010\r\u001a\u00020\u00032\u0006\u0010\u000e\u001a\u00020\u00072\f\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00010\u00102\f\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00010\u0010H\u0003\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b\u0012\u0010\u0013\u001a?\u0010\u0014\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00032\u0006\u0010\u0015\u001a\u00020\u00032\u0006\u0010\u0006\u001a\u00020\u00072\b\b\u0002\u0010\u0005\u001a\u00020\u0003H\u0003\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b\u0016\u0010\u0017\u001aN\u0010\u0018\u001a\u00020\u00012\b\u0010\u0019\u001a\u0004\u0018\u00010\u001a2\u0012\u0010\u001b\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00010\u001c2\u0012\u0010\u001d\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00010\u001c2\u0012\u0010\u001e\u001a\u000e\u0012\u0004\u0012\u00020\u001f\u0012\u0004\u0012\u00020\u00010\u001cH\u0003\u001a\u0012\u0010 \u001a\u00020\u00012\b\u0010\u0019\u001a\u0004\u0018\u00010\u001aH\u0003\u001a\u00fa\u0001\u0010!\u001a\u00020\u00012\f\u0010\"\u001a\b\u0012\u0004\u0012\u00020$0#2\f\u0010%\u001a\b\u0012\u0004\u0012\u00020\u001a0#2\f\u0010&\u001a\b\u0012\u0004\u0012\u00020\'0#2\u000e\b\u0002\u0010(\u001a\b\u0012\u0004\u0012\u00020\u00010\u00102\u000e\b\u0002\u0010)\u001a\b\u0012\u0004\u0012\u00020\u00010\u00102\u000e\b\u0002\u0010*\u001a\b\u0012\u0004\u0012\u00020\u00010\u00102\u0014\b\u0002\u0010+\u001a\u000e\u0012\u0004\u0012\u00020,\u0012\u0004\u0012\u00020\u00010\u001c2\u0014\b\u0002\u0010\u001e\u001a\u000e\u0012\u0004\u0012\u00020\u001f\u0012\u0004\u0012\u00020\u00010\u001c2\u0014\b\u0002\u0010\u001b\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00010\u001c2\u0014\b\u0002\u0010\u001d\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00010\u001c2\u0014\b\u0002\u0010-\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00010\u001c2\u000e\b\u0002\u0010.\u001a\b\u0012\u0004\u0012\u00020\u00010\u00102\u000e\b\u0002\u0010/\u001a\b\u0012\u0004\u0012\u00020\u00010\u00102\b\b\u0002\u00100\u001a\u000201H\u0007\u001a\b\u00102\u001a\u00020\u0001H\u0007\u001a\u0010\u00103\u001a\u00020\u00012\u0006\u00104\u001a\u00020$H\u0003\u001a\b\u00105\u001a\u00020\u0001H\u0007\u001a@\u00106\u001a\u00020\u00012\u0006\u00107\u001a\u00020\u00032\b\b\u0002\u00108\u001a\u0002092\u000e\b\u0002\u0010(\u001a\b\u0012\u0004\u0012\u00020\u00010\u00102\u0014\b\u0002\u0010)\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00010\u001cH\u0007\u001a\b\u0010:\u001a\u00020\u0001H\u0007\u001a.\u0010;\u001a\u00020\u00012\u0006\u00104\u001a\u00020$2\b\u0010\u0019\u001a\u0004\u0018\u00010\u001a2\u0012\u0010+\u001a\u000e\u0012\u0004\u0012\u00020,\u0012\u0004\u0012\u00020\u00010\u001cH\u0003\u001a\u001a\u0010<\u001a\u00020\u00012\b\u0010=\u001a\u0004\u0018\u00010\'2\u0006\u0010>\u001a\u00020,H\u0003\u001a\u0018\u0010?\u001a\u00020\u00012\u0006\u0010@\u001a\u00020,2\u0006\u0010A\u001a\u00020\u0003H\u0003\u001a/\u0010B\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010C\u001a\u00020\u0007H\u0003\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\bD\u0010E\u001a0\u0010F\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010G\u001a\u00020,2\f\u0010H\u001a\b\u0012\u0004\u0012\u00020\u00010\u00102\b\b\u0002\u0010I\u001a\u00020JH\u0003\u001a2\u0010K\u001a\u00020\u00012\u0006\u0010L\u001a\u00020\u00032\u0012\u0010\u000f\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00010\u001c2\f\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00010\u0010H\u0003\u001a-\u0010M\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00032\u0006\u0010C\u001a\u00020\u0007H\u0003\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\bN\u0010E\u001a\u0018\u0010O\u001a\u00020\u00072\u0006\u0010P\u001a\u00020\u001fH\u0002\u00f8\u0001\u0001\u00a2\u0006\u0002\u0010Q\u001a\u0010\u0010R\u001a\u00020\u00032\u0006\u0010S\u001a\u00020\u0003H\u0002\u001a\u0018\u0010T\u001a\u00020\u00072\u0006\u0010U\u001a\u00020\u001fH\u0002\u00f8\u0001\u0001\u00a2\u0006\u0002\u0010Q\u001a\u0018\u0010V\u001a\u00020\u00072\u0006\u0010W\u001a\u00020\u0003H\u0002\u00f8\u0001\u0001\u00a2\u0006\u0002\u0010X\u001a\u0010\u0010Y\u001a\u00020\u00032\u0006\u0010W\u001a\u00020\u0003H\u0002\u001a\u0018\u0010Z\u001a\u00020\u00072\u0006\u0010[\u001a\u00020\u001fH\u0002\u00f8\u0001\u0001\u00a2\u0006\u0002\u0010Q\u0082\u0002\u000b\n\u0005\b\u00a1\u001e0\u0001\n\u0002\b\u0019\u00a8\u0006\\"}, d2 = {"BigTemperatureDisplay", "", "label", "", "value", "unit", "color", "Landroidx/compose/ui/graphics/Color;", "BigTemperatureDisplay-g2O1Hgs", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;J)V", "ConfirmDialog", "title", "message", "confirmText", "confirmColor", "onConfirm", "Lkotlin/Function0;", "onDismiss", "ConfirmDialog-Bx497Mc", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;JLkotlin/jvm/functions/Function0;Lkotlin/jvm/functions/Function0;)V", "DataItem", "icon", "DataItem-42QJj7c", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;JLjava/lang/String;)V", "DeviceControlPanel", "deviceData", "Lcom/wuheng/smart/data/model/DeviceData;", "onTemperatureChange", "Lkotlin/Function1;", "onModeChange", "onFanSpeedChange", "", "DeviceDataCard", "DeviceDetailContent", "deviceInfoState", "Lcom/wuheng/smart/presentation/base/UiDataState;", "Lcom/wuheng/smart/data/model/DeviceInfo;", "deviceDataState", "historyDataState", "Lcom/wuheng/smart/data/model/DeviceStatus;", "onNavigateBack", "onNavigateToEdit", "onRefresh", "onPowerToggle", "", "onRenameDevice", "onResetDevice", "onDeleteDevice", "snackbarHostState", "Landroidx/compose/material3/SnackbarHostState;", "DeviceDetailErrorPreview", "DeviceDetailInfoCard", "deviceInfo", "DeviceDetailLoadingPreview", "DeviceDetailScreen", "deviceId", "viewModel", "Lcom/wuheng/smart/presentation/device/DeviceDetailViewModel;", "DeviceDetailSuccessPreview", "DeviceInfoCard", "DeviceStatusCard", "deviceStatus", "isLoading", "DeviceStatusIndicator", "isOnline", "runStatus", "InfoRow", "valueColor", "InfoRow-mxwnekA", "(Ljava/lang/String;Ljava/lang/String;J)V", "ModeButton", "isSelected", "onClick", "modifier", "Landroidx/compose/ui/Modifier;", "RenameDeviceDialog", "currentName", "StatusItem", "StatusItem-mxwnekA", "getCo2Color", "co2", "(I)J", "getDeviceTypeDisplay", "deviceType", "getPm25Color", "pm25", "getRunStatusColor", "status", "(Ljava/lang/String;)J", "getRunStatusDisplay", "getVocColor", "voc", "app_debug"})
public final class DeviceDetailScreenKt {
    
    /**
     * 设备详情页面 Composable (完善版)
     *
     * 布局结构：
     * - 顶部导航栏: 返回按钮 + 标题"设备详情" + 更多按钮
     * - 设备基本信息卡片: 设备名称、型号、房间、在线状态
     * - 设备实时数据卡片: 温度、湿度、CO2、PM2.5、VOC
     * - 设备控制面板: 开关、温度调节、模式切换、风速调节
     * - 24小时趋势图表: 温度/湿度历史数据
     * - 设备设置: 重命名、删除、恢复出厂
     *
     * 完成度: 100%
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
    com.wuheng.smart.presentation.base.UiDataState<com.wuheng.smart.data.model.DeviceStatus> historyDataState, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onNavigateBack, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onNavigateToEdit, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onRefresh, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super java.lang.Boolean, kotlin.Unit> onPowerToggle, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super java.lang.Integer, kotlin.Unit> onFanSpeedChange, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onTemperatureChange, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onModeChange, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onRenameDevice, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onResetDevice, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onDeleteDevice, @org.jetbrains.annotations.NotNull()
    androidx.compose.material3.SnackbarHostState snackbarHostState) {
    }
    
    /**
     * 设备基本信息卡片 (完善版)
     */
    @androidx.compose.runtime.Composable()
    private static final void DeviceInfoCard(com.wuheng.smart.data.model.DeviceInfo deviceInfo, com.wuheng.smart.data.model.DeviceData deviceData, kotlin.jvm.functions.Function1<? super java.lang.Boolean, kotlin.Unit> onPowerToggle) {
    }
    
    /**
     * 设备状态指示器
     */
    @androidx.compose.runtime.Composable()
    private static final void DeviceStatusIndicator(boolean isOnline, java.lang.String runStatus) {
    }
    
    /**
     * 设备控制面板 (新增)
     */
    @androidx.compose.runtime.Composable()
    private static final void DeviceControlPanel(com.wuheng.smart.data.model.DeviceData deviceData, kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onTemperatureChange, kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onModeChange, kotlin.jvm.functions.Function1<? super java.lang.Integer, kotlin.Unit> onFanSpeedChange) {
    }
    
    /**
     * 模式按钮
     */
    @androidx.compose.runtime.Composable()
    private static final void ModeButton(java.lang.String label, boolean isSelected, kotlin.jvm.functions.Function0<kotlin.Unit> onClick, androidx.compose.ui.Modifier modifier) {
    }
    
    /**
     * 设备实时数据卡片
     */
    @androidx.compose.runtime.Composable()
    private static final void DeviceDataCard(com.wuheng.smart.data.model.DeviceData deviceData) {
    }
    
    /**
     * 设备运行状态卡片（替代假24小时趋势图，显示当前实时数据）
     */
    @androidx.compose.runtime.Composable()
    private static final void DeviceStatusCard(com.wuheng.smart.data.model.DeviceStatus deviceStatus, boolean isLoading) {
    }
    
    /**
     * 设备详细信息卡片 (新增)
     */
    @androidx.compose.runtime.Composable()
    private static final void DeviceDetailInfoCard(com.wuheng.smart.data.model.DeviceInfo deviceInfo) {
    }
    
    /**
     * 重命名设备对话框
     */
    @androidx.compose.runtime.Composable()
    private static final void RenameDeviceDialog(java.lang.String currentName, kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onConfirm, kotlin.jvm.functions.Function0<kotlin.Unit> onDismiss) {
    }
    
    /**
     * 获取设备类型显示名称
     */
    private static final java.lang.String getDeviceTypeDisplay(java.lang.String deviceType) {
        return null;
    }
    
    /**
     * 获取运行状态显示
     */
    private static final java.lang.String getRunStatusDisplay(java.lang.String status) {
        return null;
    }
    
    /**
     * 获取运行状态颜色
     */
    private static final long getRunStatusColor(java.lang.String status) {
        return 0L;
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