package com.wuheng.smart.presentation.floorzone;

import androidx.compose.foundation.layout.*;
import androidx.compose.material.icons.Icons;
import androidx.compose.material3.*;
import androidx.compose.runtime.*;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.text.font.FontWeight;
import androidx.compose.ui.tooling.preview.Preview;
import com.wuheng.smart.data.model.FloorInfo;
import com.wuheng.smart.data.model.RoomInfo;
import com.wuheng.smart.presentation.base.UiDataState;
import com.wuheng.smart.presentation.theme.*;

@kotlin.OptIn(markerClass = {androidx.compose.material3.ExperimentalMaterial3Api.class})
@kotlin.Metadata(mv = {1, 7, 1}, k = 2, d1 = {"\u0000L\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\t\u001a4\u0010\u0000\u001a\u00020\u00012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\b\u0010\u0005\u001a\u0004\u0018\u00010\u00042\u0012\u0010\u0006\u001a\u000e\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\u00010\u0007H\u0003\u001a\u0090\u0001\u0010\t\u001a\u00020\u00012\u0012\u0010\n\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00040\u00030\u000b2\u0012\u0010\f\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\r0\u00030\u000b2\b\u0010\u000e\u001a\u0004\u0018\u00010\u000f2\b\u0010\u0010\u001a\u0004\u0018\u00010\u000f2\u000e\b\u0002\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00010\u00122\u0014\b\u0002\u0010\u0006\u001a\u000e\u0012\u0004\u0012\u00020\u000f\u0012\u0004\u0012\u00020\u00010\u00072\u0014\b\u0002\u0010\u0013\u001a\u000e\u0012\u0004\u0012\u00020\u000f\u0012\u0004\u0012\u00020\u00010\u00072\u000e\b\u0002\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00010\u0012H\u0007\u001a\b\u0010\u0015\u001a\u00020\u0001H\u0007\u001a\b\u0010\u0016\u001a\u00020\u0001H\u0007\u001a3\u0010\u0017\u001a\u00020\u00012\n\b\u0002\u0010\u0018\u001a\u0004\u0018\u00010\b2\b\b\u0002\u0010\u0019\u001a\u00020\u001a2\u000e\b\u0002\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00010\u0012H\u0007\u00a2\u0006\u0002\u0010\u001b\u001a\b\u0010\u001c\u001a\u00020\u0001H\u0007\u001a,\u0010\u001d\u001a\u00020\u00012\u0006\u0010\u001e\u001a\u00020\u000f2\u0006\u0010\u001f\u001a\u00020 2\u0012\u0010!\u001a\u000e\u0012\u0004\u0012\u00020 \u0012\u0004\u0012\u00020\u00010\u0007H\u0003\u001a4\u0010\"\u001a\u00020\u00012\f\u0010#\u001a\b\u0012\u0004\u0012\u00020\r0\u00032\b\u0010$\u001a\u0004\u0018\u00010\r2\u0012\u0010\u0013\u001a\u000e\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\u00010\u0007H\u0003\u001a\b\u0010%\u001a\u00020\u0001H\u0003\u001a\u0010\u0010&\u001a\u00020\u00012\u0006\u0010\'\u001a\u00020\u000fH\u0003\u001a\u0010\u0010(\u001a\u00020\u00012\u0006\u0010\'\u001a\u00020\u000fH\u0003\u00a8\u0006)"}, d2 = {"FloorSelector", "", "floors", "", "Lcom/wuheng/smart/data/model/FloorInfo;", "selectedFloor", "onFloorSelected", "Lkotlin/Function1;", "", "FloorZoneContent", "floorsState", "Lcom/wuheng/smart/presentation/base/UiDataState;", "roomsState", "Lcom/wuheng/smart/data/model/RoomInfo;", "selectedFloorId", "", "selectedRoomId", "onNavigateBack", "Lkotlin/Function0;", "onRoomSelected", "onRefresh", "FloorZoneErrorPreview", "FloorZoneLoadingPreview", "FloorZoneScreen", "floorId", "viewModel", "Lcom/wuheng/smart/presentation/floorzone/FloorZoneViewModel;", "(Ljava/lang/Integer;Lcom/wuheng/smart/presentation/floorzone/FloorZoneViewModel;Lkotlin/jvm/functions/Function0;)V", "FloorZoneSuccessPreview", "RadiationSwitchItem", "label", "isChecked", "", "onCheckedChange", "RoomChipSelector", "rooms", "selectedRoom", "RoomFanSpeedSelector", "RoomHumidityCard", "roomName", "RoomTemperatureCard", "app_debug"})
public final class FloorZoneScreenKt {
    
    /**
     * 楼层区域页面 Composable
     *
     * 布局结构（基于设计图 冷暖舒适-楼层-区域.png 分析）:
     * - 顶部导航栏: 返回按钮 + 标题"楼层区域"
     * - 楼层选择器: 下拉选择楼层 (B1地下室, 1F一层, 2F二层等)
     * - 房间Chip选择器: 横向滚动的房间选择 (客厅, 主卧, 儿童房等)
     * - 房间温度设定卡片: 温度显示、档位按钮、温度滑块、辐射控制开关
     * - 房间湿度设定卡片: 湿度显示、档位按钮、湿度滑块
     * - 风速选择器: 自动/低速/中速/高速
     *
     * 设计图参考:
     *  - 冷暖舒适-楼层-区域.png -> 楼层区域控制页面
     */
    @androidx.compose.runtime.Composable()
    public static final void FloorZoneScreen(@org.jetbrains.annotations.Nullable()
    java.lang.Integer floorId, @org.jetbrains.annotations.NotNull()
    com.wuheng.smart.presentation.floorzone.FloorZoneViewModel viewModel, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onNavigateBack) {
    }
    
    /**
     * 楼层区域页面内容
     */
    @androidx.compose.runtime.Composable()
    public static final void FloorZoneContent(@org.jetbrains.annotations.NotNull()
    com.wuheng.smart.presentation.base.UiDataState<? extends java.util.List<com.wuheng.smart.data.model.FloorInfo>> floorsState, @org.jetbrains.annotations.NotNull()
    com.wuheng.smart.presentation.base.UiDataState<? extends java.util.List<com.wuheng.smart.data.model.RoomInfo>> roomsState, @org.jetbrains.annotations.Nullable()
    java.lang.String selectedFloorId, @org.jetbrains.annotations.Nullable()
    java.lang.String selectedRoomId, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onNavigateBack, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onFloorSelected, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onRoomSelected, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onRefresh) {
    }
    
    /**
     * 楼层选择器组件
     *
     * 设计规范：
     * - 显示选中的楼层名称 + 下拉箭头
     * - 点击展开楼层列表
     * - 文字大小：floor_button_text_size = 16sp
     * - 箭头颜色：FloorDropdownArrowColor (#64748B)
     */
    @androidx.compose.runtime.Composable()
    private static final void FloorSelector(java.util.List<com.wuheng.smart.data.model.FloorInfo> floors, com.wuheng.smart.data.model.FloorInfo selectedFloor, kotlin.jvm.functions.Function1<? super java.lang.Integer, kotlin.Unit> onFloorSelected) {
    }
    
    /**
     * 房间Chip选择器
     *
     * 设计规范（基于 冷暖舒适-楼层-区域.png 分析）：
     * - Chip样式：胶囊形圆角 room_chip_corner = 18dp
     * - Chip高度：room_chip_height = 36dp
     * - Chip内边距：room_chip_padding_h = 16dp
     * - 字号：room_chip_text_size = 14sp
     *
     * 选中态：
     * - 背景：TabSelectedBackground (#0EA5E9 蓝色)
     * - 文字：TabSelectedText (白色)
     *
     * 未选中态：
     * - 背景：ChipUnselectedBg (白色)
     * - 文字：ChipUnselectedText (#64748B 中灰)
     * - 边框：ChipUnselectedBorder (#E2E8F0 浅灰)
     */
    @androidx.compose.runtime.Composable()
    private static final void RoomChipSelector(java.util.List<com.wuheng.smart.data.model.RoomInfo> rooms, com.wuheng.smart.data.model.RoomInfo selectedRoom, kotlin.jvm.functions.Function1<? super java.lang.Integer, kotlin.Unit> onRoomSelected) {
    }
    
    /**
     * 房间温度设定卡片
     *
     * 设计规范：
     * - 标题: "{房间名}温度设定" (text_h2_size=20sp SemiBold)
     * - 温度显示: "23°" (text_body_large_size=24sp Bold)
     * - 档位按钮: [偏低-] [适中] [偏高+] (temp_preset_button_height=32dp)
     * - 温度滑块: 蓝色激活轨道 + 白色手柄
     * - 辐射控制: 顶面辐射开关 + 地面辐射开关
     */
    @androidx.compose.runtime.Composable()
    private static final void RoomTemperatureCard(java.lang.String roomName) {
    }
    
    /**
     * 房间湿度设定卡片
     */
    @androidx.compose.runtime.Composable()
    private static final void RoomHumidityCard(java.lang.String roomName) {
    }
    
    /**
     * 辐射控制开关项
     */
    @androidx.compose.runtime.Composable()
    private static final void RadiationSwitchItem(java.lang.String label, boolean isChecked, kotlin.jvm.functions.Function1<? super java.lang.Boolean, kotlin.Unit> onCheckedChange) {
    }
    
    /**
     * 风速选择器
     *
     * 设计规范：
     * - 标题: "风速" (TextSecondaryLight #64748B)
     * - 按钮组: [自动](选中) | 低速 | 中速 | 高速
     * - 按钮高度: fan_speed_button_height = 36dp
     * - 按钮圆角: fan_speed_corner = 18dp (胶囊形)
     * - 按钮间距: fan_speed_gap = 8dp
     * - 字号: fan_speed_text_size = 14sp
     */
    @androidx.compose.runtime.Composable()
    private static final void RoomFanSpeedSelector() {
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