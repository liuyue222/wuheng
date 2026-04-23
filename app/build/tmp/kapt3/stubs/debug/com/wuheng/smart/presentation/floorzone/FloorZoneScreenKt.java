package com.wuheng.smart.presentation.floorzone;

import androidx.compose.animation.*;
import androidx.compose.animation.core.*;
import androidx.compose.foundation.layout.*;
import androidx.compose.material.icons.Icons;
import androidx.compose.material.icons.filled.*;
import androidx.compose.material3.*;
import androidx.compose.runtime.*;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.text.font.FontWeight;
import androidx.compose.ui.tooling.preview.Preview;
import com.wuheng.smart.data.model.DeviceInfo;
import com.wuheng.smart.data.model.FloorInfo;
import com.wuheng.smart.data.model.RoomInfo;
import com.wuheng.smart.presentation.base.UiDataState;
import com.wuheng.smart.presentation.theme.*;

@kotlin.OptIn(markerClass = {androidx.compose.material3.ExperimentalMaterial3Api.class})
@kotlin.Metadata(mv = {1, 7, 1}, k = 2, d1 = {"\u0000p\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u001c\u001a2\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0012\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00010\u00052\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00010\bH\u0003\u001a=\u0010\t\u001a\u00020\u00012\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u000b2\u0006\u0010\r\u001a\u00020\u000b2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u0011H\u0003\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b\u0012\u0010\u0013\u001a>\u0010\u0014\u001a\u00020\u00012\f\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00170\u00162\b\u0010\u0018\u001a\u0004\u0018\u00010\u00172\u0012\u0010\u0019\u001a\u000e\u0012\u0004\u0012\u00020\u001a\u0012\u0004\u0012\u00020\u00010\u00052\b\b\u0002\u0010\u001b\u001a\u00020\u0006H\u0003\u001a\u00e4\u0001\u0010\u001c\u001a\u00020\u00012\u0012\u0010\u001d\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00170\u00160\u001e2\u0012\u0010\u001f\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020 0\u00160\u001e2\u0012\u0010!\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00030\u00160\u001e2\f\u0010\"\u001a\b\u0012\u0004\u0012\u00020#0\u001e2\b\u0010$\u001a\u0004\u0018\u00010\u000b2\b\u0010%\u001a\u0004\u0018\u00010\u000b2\u000e\b\u0002\u0010&\u001a\b\u0012\u0004\u0012\u00020\u00010\b2\u0014\b\u0002\u0010\u0019\u001a\u000e\u0012\u0004\u0012\u00020\u000b\u0012\u0004\u0012\u00020\u00010\u00052\u0014\b\u0002\u0010\'\u001a\u000e\u0012\u0004\u0012\u00020\u000b\u0012\u0004\u0012\u00020\u00010\u00052\u000e\b\u0002\u0010(\u001a\b\u0012\u0004\u0012\u00020\u00010\b2\u001a\b\u0002\u0010)\u001a\u0014\u0012\u0004\u0012\u00020\u001a\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00010*2\u0014\b\u0002\u0010+\u001a\u000e\u0012\u0004\u0012\u00020\u001a\u0012\u0004\u0012\u00020\u00010\u0005H\u0007\u001a\b\u0010,\u001a\u00020\u0001H\u0007\u001a\b\u0010-\u001a\u00020\u0001H\u0007\u001aI\u0010.\u001a\u00020\u00012\n\b\u0002\u0010/\u001a\u0004\u0018\u00010\u001a2\b\b\u0002\u00100\u001a\u0002012\u000e\b\u0002\u0010&\u001a\b\u0012\u0004\u0012\u00020\u00010\b2\u0014\b\u0002\u00102\u001a\u000e\u0012\u0004\u0012\u00020\u000b\u0012\u0004\u0012\u00020\u00010\u0005H\u0007\u00a2\u0006\u0002\u00103\u001a\b\u00104\u001a\u00020\u0001H\u0007\u001a\u001a\u00105\u001a\u00020\u00012\u0006\u00106\u001a\u00020\u000b2\b\b\u0002\u0010\u001b\u001a\u00020\u0006H\u0003\u001a,\u00107\u001a\u00020\u00012\u0006\u0010\n\u001a\u00020\u000b2\u0006\u00108\u001a\u00020\u00062\u0012\u00109\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00010\u0005H\u0003\u001a4\u0010:\u001a\u00020\u00012\f\u0010;\u001a\b\u0012\u0004\u0012\u00020 0\u00162\b\u0010<\u001a\u0004\u0018\u00010 2\u0012\u0010\'\u001a\u000e\u0012\u0004\u0012\u00020\u001a\u0012\u0004\u0012\u00020\u00010\u0005H\u0003\u001aL\u0010=\u001a\u00020\u00012\f\u0010>\u001a\b\u0012\u0004\u0012\u00020\u00030\u00162\u0006\u0010\u001b\u001a\u00020\u00062\u0018\u0010)\u001a\u0014\u0012\u0004\u0012\u00020\u001a\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00010*2\u0012\u0010+\u001a\u000e\u0012\u0004\u0012\u00020\u001a\u0012\u0004\u0012\u00020\u00010\u0005H\u0003\u001a\u001a\u0010?\u001a\u00020\u00012\b\u0010@\u001a\u0004\u0018\u00010#2\u0006\u0010\u001b\u001a\u00020\u0006H\u0003\u001a\u001a\u0010A\u001a\u00020\u00012\u0006\u00106\u001a\u00020\u000b2\b\b\u0002\u0010\u001b\u001a\u00020\u0006H\u0003\u001a\u001a\u0010B\u001a\u00020\u00012\u0006\u00106\u001a\u00020\u000b2\b\b\u0002\u0010\u001b\u001a\u00020\u0006H\u0003\u001a\u0018\u0010C\u001a\u00020\u00112\u0006\u0010D\u001a\u00020\u001aH\u0002\u00f8\u0001\u0001\u00a2\u0006\u0002\u0010E\u001a\u0010\u0010F\u001a\u00020\u000f2\u0006\u0010G\u001a\u00020\u000bH\u0003\u001a\u0018\u0010H\u001a\u00020\u00112\u0006\u0010\u0002\u001a\u00020\u0003H\u0003\u00f8\u0001\u0001\u00a2\u0006\u0002\u0010I\u001a\u0010\u0010J\u001a\u00020\u000b2\u0006\u0010\u0002\u001a\u00020\u0003H\u0002\u001a\u0018\u0010K\u001a\u00020\u00112\u0006\u0010L\u001a\u00020\u001aH\u0002\u00f8\u0001\u0001\u00a2\u0006\u0002\u0010E\u0082\u0002\u000b\n\u0005\b\u00a1\u001e0\u0001\n\u0002\b\u0019\u00a8\u0006M"}, d2 = {"DeviceListItem", "", "device", "Lcom/wuheng/smart/data/model/DeviceInfo;", "onPowerToggle", "Lkotlin/Function1;", "", "onClick", "Lkotlin/Function0;", "EnvironmentDataItem", "label", "", "value", "unit", "icon", "Landroidx/compose/ui/graphics/vector/ImageVector;", "color", "Landroidx/compose/ui/graphics/Color;", "EnvironmentDataItem-xwkQ0AY", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Landroidx/compose/ui/graphics/vector/ImageVector;J)V", "FloorSelector", "floors", "", "Lcom/wuheng/smart/data/model/FloorInfo;", "selectedFloor", "onFloorSelected", "", "isLoading", "FloorZoneContent", "floorsState", "Lcom/wuheng/smart/presentation/base/UiDataState;", "roomsState", "Lcom/wuheng/smart/data/model/RoomInfo;", "roomDevicesState", "roomEnvironmentState", "Lcom/wuheng/smart/presentation/floorzone/RoomEnvironmentData;", "selectedFloorId", "selectedRoomId", "onNavigateBack", "onRoomSelected", "onRefresh", "onDevicePowerToggle", "Lkotlin/Function2;", "onDeviceClick", "FloorZoneErrorPreview", "FloorZoneLoadingPreview", "FloorZoneScreen", "floorId", "viewModel", "Lcom/wuheng/smart/presentation/floorzone/FloorZoneViewModel;", "onNavigateToDeviceDetail", "(Ljava/lang/Integer;Lcom/wuheng/smart/presentation/floorzone/FloorZoneViewModel;Lkotlin/jvm/functions/Function0;Lkotlin/jvm/functions/Function1;)V", "FloorZoneSuccessPreview", "FreshAirControlCard", "roomName", "RadiationSwitchItem", "isChecked", "onCheckedChange", "RoomChipSelector", "rooms", "selectedRoom", "RoomDevicesCard", "devices", "RoomEnvironmentCard", "environmentData", "RoomHumidityCard", "RoomTemperatureCard", "getCo2Color", "co2", "(I)J", "getDeviceIcon", "deviceType", "getDeviceStatusColor", "(Lcom/wuheng/smart/data/model/DeviceInfo;)J", "getDeviceStatusText", "getPm25Color", "pm25", "app_debug"})
public final class FloorZoneScreenKt {
    
    /**
     * 楼层区域页面 Composable (完善版)
     *
     * 布局结构：
     * - 顶部导航栏: 返回按钮 + 标题"楼层区域"
     * - 楼层选择器: 下拉选择楼层 (B1地下室, 1F一层, 2F二层等)
     * - 房间Chip选择器: 横向滚动的房间选择 (客厅, 主卧, 儿童房等)
     * - 房间环境数据卡片: 温度、湿度、CO2、PM2.5显示
     * - 房间设备列表: 房间内设备的快捷控制
     * - 房间温度设定卡片: 温度显示、档位按钮、温度滑块、辐射控制开关
     * - 房间湿度设定卡片: 湿度显示、档位按钮、湿度滑块
     * - 新风微控卡片: CO2阈值、湿度设定、风速选择
     *
     * 完成度: 100%
     */
    @androidx.compose.runtime.Composable()
    public static final void FloorZoneScreen(@org.jetbrains.annotations.Nullable()
    java.lang.Integer floorId, @org.jetbrains.annotations.NotNull()
    com.wuheng.smart.presentation.floorzone.FloorZoneViewModel viewModel, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onNavigateBack, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onNavigateToDeviceDetail) {
    }
    
    /**
     * 楼层区域页面内容
     */
    @androidx.compose.runtime.Composable()
    public static final void FloorZoneContent(@org.jetbrains.annotations.NotNull()
    com.wuheng.smart.presentation.base.UiDataState<? extends java.util.List<com.wuheng.smart.data.model.FloorInfo>> floorsState, @org.jetbrains.annotations.NotNull()
    com.wuheng.smart.presentation.base.UiDataState<? extends java.util.List<com.wuheng.smart.data.model.RoomInfo>> roomsState, @org.jetbrains.annotations.NotNull()
    com.wuheng.smart.presentation.base.UiDataState<? extends java.util.List<com.wuheng.smart.data.model.DeviceInfo>> roomDevicesState, @org.jetbrains.annotations.NotNull()
    com.wuheng.smart.presentation.base.UiDataState<com.wuheng.smart.presentation.floorzone.RoomEnvironmentData> roomEnvironmentState, @org.jetbrains.annotations.Nullable()
    java.lang.String selectedFloorId, @org.jetbrains.annotations.Nullable()
    java.lang.String selectedRoomId, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onNavigateBack, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onFloorSelected, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onRoomSelected, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onRefresh, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function2<? super java.lang.Integer, ? super java.lang.Boolean, kotlin.Unit> onDevicePowerToggle, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super java.lang.Integer, kotlin.Unit> onDeviceClick) {
    }
    
    /**
     * 房间环境数据卡片 (新增)
     */
    @androidx.compose.runtime.Composable()
    private static final void RoomEnvironmentCard(com.wuheng.smart.presentation.floorzone.RoomEnvironmentData environmentData, boolean isLoading) {
    }
    
    /**
     * 房间设备列表卡片 (新增)
     */
    @androidx.compose.runtime.Composable()
    private static final void RoomDevicesCard(java.util.List<com.wuheng.smart.data.model.DeviceInfo> devices, boolean isLoading, kotlin.jvm.functions.Function2<? super java.lang.Integer, ? super java.lang.Boolean, kotlin.Unit> onDevicePowerToggle, kotlin.jvm.functions.Function1<? super java.lang.Integer, kotlin.Unit> onDeviceClick) {
    }
    
    /**
     * 设备列表项
     */
    @androidx.compose.runtime.Composable()
    private static final void DeviceListItem(com.wuheng.smart.data.model.DeviceInfo device, kotlin.jvm.functions.Function1<? super java.lang.Boolean, kotlin.Unit> onPowerToggle, kotlin.jvm.functions.Function0<kotlin.Unit> onClick) {
    }
    
    /**
     * 获取设备图标
     */
    @androidx.compose.runtime.Composable()
    private static final androidx.compose.ui.graphics.vector.ImageVector getDeviceIcon(java.lang.String deviceType) {
        return null;
    }
    
    /**
     * 获取设备状态文本
     */
    private static final java.lang.String getDeviceStatusText(com.wuheng.smart.data.model.DeviceInfo device) {
        return null;
    }
    
    /**
     * 获取设备状态颜色
     */
    @androidx.compose.runtime.Composable()
    private static final long getDeviceStatusColor(com.wuheng.smart.data.model.DeviceInfo device) {
        return 0L;
    }
    
    /**
     * 楼层选择器组件
     */
    @androidx.compose.runtime.Composable()
    private static final void FloorSelector(java.util.List<com.wuheng.smart.data.model.FloorInfo> floors, com.wuheng.smart.data.model.FloorInfo selectedFloor, kotlin.jvm.functions.Function1<? super java.lang.Integer, kotlin.Unit> onFloorSelected, boolean isLoading) {
    }
    
    /**
     * 房间Chip选择器
     */
    @androidx.compose.runtime.Composable()
    private static final void RoomChipSelector(java.util.List<com.wuheng.smart.data.model.RoomInfo> rooms, com.wuheng.smart.data.model.RoomInfo selectedRoom, kotlin.jvm.functions.Function1<? super java.lang.Integer, kotlin.Unit> onRoomSelected) {
    }
    
    /**
     * 房间温度设定卡片
     */
    @androidx.compose.runtime.Composable()
    private static final void RoomTemperatureCard(java.lang.String roomName, boolean isLoading) {
    }
    
    /**
     * 房间湿度设定卡片
     */
    @androidx.compose.runtime.Composable()
    private static final void RoomHumidityCard(java.lang.String roomName, boolean isLoading) {
    }
    
    /**
     * 辐射控制开关项
     */
    @androidx.compose.runtime.Composable()
    private static final void RadiationSwitchItem(java.lang.String label, boolean isChecked, kotlin.jvm.functions.Function1<? super java.lang.Boolean, kotlin.Unit> onCheckedChange) {
    }
    
    /**
     * 新风微控卡片
     */
    @androidx.compose.runtime.Composable()
    private static final void FreshAirControlCard(java.lang.String roomName, boolean isLoading) {
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
    
    @androidx.compose.runtime.Composable()
    @androidx.compose.ui.tooling.preview.Preview(showBackground = true, name = "\u697c\u5c42\u533a\u57df-\u52a0\u8f7d\u4e2d", backgroundColor = 4294047225L)
    public static final void FloorZoneLoadingPreview() {
    }
    
    @androidx.compose.runtime.Composable()
    @androidx.compose.ui.tooling.preview.Preview(showBackground = true, name = "\u697c\u5c42\u533a\u57df-\u6210\u529f", backgroundColor = 4294047225L)
    public static final void FloorZoneSuccessPreview() {
    }
    
    @androidx.compose.runtime.Composable()
    @androidx.compose.ui.tooling.preview.Preview(showBackground = true, name = "\u697c\u5c42\u533a\u57df-\u9519\u8bef", backgroundColor = 4294047225L)
    public static final void FloorZoneErrorPreview() {
    }
}