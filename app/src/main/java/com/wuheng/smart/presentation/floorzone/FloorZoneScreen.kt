@file:OptIn(ExperimentalMaterial3Api::class)

package com.wuheng.smart.presentation.floorzone

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.wuheng.smart.data.model.FloorInfo
import com.wuheng.smart.data.model.RoomInfo
import com.wuheng.smart.presentation.base.UiDataState
import com.wuheng.smart.presentation.theme.*

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
 *   - 冷暖舒适-楼层-区域.png -> 楼层区域控制页面
 */
@Composable
fun FloorZoneScreen(
    floorId: Int? = null,
    viewModel: FloorZoneViewModel = hiltViewModel(),
    onNavigateBack: () -> Unit = {}
) {
    val floorsState by viewModel.floorsState.collectAsStateWithLifecycle()
    val roomsState by viewModel.roomsState.collectAsStateWithLifecycle()
    val selectedFloorId by viewModel.selectedFloorId.collectAsStateWithLifecycle()
    val selectedRoomId by viewModel.selectedRoomId.collectAsStateWithLifecycle()

    FloorZoneContent(
        floorsState = floorsState,
        roomsState = roomsState,
        selectedFloorId = selectedFloorId,
        selectedRoomId = selectedRoomId,
        onNavigateBack = onNavigateBack,
        onFloorSelected = { viewModel.selectFloor(it) },
        onRoomSelected = { viewModel.selectRoom(it) },
        onRefresh = { viewModel.refresh() }
    )
}

/**
 * 楼层区域页面内容
 */
@Composable
fun FloorZoneContent(
    floorsState: UiDataState<List<FloorInfo>>,
    roomsState: UiDataState<List<RoomInfo>>,
    selectedFloorId: String?,
    selectedRoomId: String?,
    onNavigateBack: () -> Unit = {},
    onFloorSelected: (String) -> Unit = {},
    onRoomSelected: (String) -> Unit = {},
    onRefresh: () -> Unit = {}
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "楼层区域",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.SemiBold,
                        color = TextPrimaryLight
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "返回",
                            tint = TextPrimaryLight
                        )
                    }
                },
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = BackgroundLight
                )
            )
        }
    ) { paddingValues ->
        when (floorsState) {
            is UiDataState.Idle, is UiDataState.Loading -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(color = PrimaryBlue)
                }
            }
            is UiDataState.Error -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(spacing_md)
                    ) {
                        Text(
                            text = "加载失败",
                            style = MaterialTheme.typography.bodyLarge,
                            color = TextSecondaryLight
                        )
                        Button(
                            onClick = onRefresh,
                            colors = ButtonDefaults.buttonColors(containerColor = PrimaryBlue)
                        ) {
                            Text("重试")
                        }
                    }
                }
            }
            is UiDataState.Success -> {
                val floors = (floorsState as UiDataState.Success<List<FloorInfo>>).data
                val rooms = (roomsState as? UiDataState.Success<List<RoomInfo>>)?.data ?: emptyList()
                val selectedFloor = floors.find { it.floorId.toString() == selectedFloorId }
                val selectedRoom = rooms.find { it.roomId.toString() == selectedRoomId }

                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                        .background(BackgroundLight)
                        .padding(horizontal = page_margin_horizontal),
                    verticalArrangement = Arrangement.spacedBy(spacing_lg)
                ) {
                    item { Spacer(modifier = Modifier.height(spacing_sm)) }

                    // 楼层选择器
                    item {
                        FloorSelector(
                            floors = floors,
                            selectedFloor = selectedFloor,
                            onFloorSelected = { onFloorSelected(it.toString()) }
                        )
                    }

                    // 房间Chip选择器
                    item {
                        RoomChipSelector(
                            rooms = rooms,
                            selectedRoom = selectedRoom,
                            onRoomSelected = { onRoomSelected(it.toString()) }
                        )
                    }

                    // 房间温度设定卡片
                    item {
                        RoomTemperatureCard(
                            roomName = selectedRoom?.roomName ?: "客厅"
                        )
                    }

                    // 房间湿度设定卡片
                    item {
                        RoomHumidityCard(
                            roomName = selectedRoom?.roomName ?: "客厅"
                        )
                    }

                    // 风速选择器
                    item {
                        RoomFanSpeedSelector()
                    }

                    item { Spacer(modifier = Modifier.height(spacing_lg)) }
                }
            }
        }
    }
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
@Composable
private fun FloorSelector(
    floors: List<FloorInfo>,
    selectedFloor: FloorInfo?,
    onFloorSelected: (Int) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxWidth()) {
        // 选中的楼层显示
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(corner_md))
                .clickable { expanded = true }
                .padding(vertical = spacing_sm),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = selectedFloor?.floorName ?: "选择楼层",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
                color = TextPrimaryLight,
                fontSize = floor_button_text_size
            )
            Icon(
                imageVector = Icons.Default.KeyboardArrowDown,
                contentDescription = "展开楼层",
                tint = FloorDropdownArrowColor,
                modifier = Modifier.size(floor_dropdown_arrow_size)
            )
        }

        // 下拉菜单
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .width(200.dp)
                .background(SurfaceLight)
        ) {
            floors.forEach { floor ->
                DropdownMenuItem(
                    text = {
                        Text(
                            text = floor.floorName,
                            style = MaterialTheme.typography.bodyMedium,
                            color = TextPrimaryLight
                        )
                    },
                    onClick = {
                        onFloorSelected(floor.floorId)
                        expanded = false
                    }
                )
            }
        }
    }
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
@Composable
private fun RoomChipSelector(
    rooms: List<RoomInfo>,
    selectedRoom: RoomInfo?,
    onRoomSelected: (Int) -> Unit
) {
    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(room_chip_gap)
    ) {
        items(rooms.size) { index ->
            val room = rooms[index]
            val isSelected = selectedRoom?.roomId == room.roomId

            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(room_chip_corner))
                    .background(
                        if (isSelected) TabSelectedBackground else ChipUnselectedBg
                    )
                    .then(
                        if (!isSelected) {
                            Modifier.border(
                                width = 1.dp,
                                color = ChipUnselectedBorder,
                                shape = RoundedCornerShape(room_chip_corner)
                            )
                        } else Modifier
                    )
                    .clickable { onRoomSelected(room.roomId) }
                    .padding(
                        horizontal = room_chip_padding_h,
                        vertical = (room_chip_height - 14.dp) / 2
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = room.roomName,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal,
                    color = if (isSelected) TabSelectedText else ChipUnselectedText,
                    fontSize = room_chip_text_size
                )
            }
        }
    }
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
@Composable
private fun RoomTemperatureCard(roomName: String) {
    var mainSwitch by remember { mutableStateOf(true) }
    var topRadiation by remember { mutableStateOf(true) }
    var bottomRadiation by remember { mutableStateOf(false) }
    var tempValue by remember { mutableStateOf(23f) }
    var selectedPreset by remember { mutableStateOf(1) }

    val presets = listOf("偏低-", "适中", "偏高+")

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(
                elevation = elevation_md,
                shape = RoundedCornerShape(corner_md),
                ambientColor = ShadowLight,
                spotColor = ShadowLight
            )
            .clip(RoundedCornerShape(corner_md))
            .background(SurfaceLight)
            .padding(card_padding_large)
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(spacing_lg)) {
            // 标题行：房间温度设定 + 总开关
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "$roomName 温度设定",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = TextPrimaryLight,
                    fontSize = text_h2_size
                )
                Switch(
                    checked = mainSwitch,
                    onCheckedChange = { mainSwitch = it },
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = Color.White,
                        checkedTrackColor = SwitchChecked,
                        uncheckedThumbColor = Color.White,
                        uncheckedTrackColor = SwitchUnchecked
                    ),
                    modifier = Modifier.width(switch_width)
                )
            }

            // 温度显示 + 档位按钮
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // 左侧：大号温度显示
                Row(verticalAlignment = Alignment.Bottom) {
                    Text(
                        text = "${tempValue.toInt()}",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold,
                        color = TemperatureValueColor,
                        fontSize = text_body_large_size
                    )
                    Text(
                        text = "°",
                        style = MaterialTheme.typography.bodyLarge,
                        color = TemperatureUnitColor,
                        fontSize = 16.sp,
                        modifier = Modifier.padding(bottom = 4.dp)
                    )
                }

                // 右侧：档位按钮组
                Row(horizontalArrangement = Arrangement.spacedBy(spacing_sm)) {
                    presets.forEachIndexed { index, preset ->
                        val isSelected = selectedPreset == index
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(temp_preset_corner))
                                .background(
                                    if (isSelected) SurfaceVariantLight else Color.Transparent
                                )
                                .border(
                                    width = 1.dp,
                                    color = if (isSelected) PrimaryBlue else DividerLight,
                                    shape = RoundedCornerShape(temp_preset_corner)
                                )
                                .clickable { selectedPreset = index }
                                .padding(
                                    horizontal = spacing_md,
                                    vertical = (temp_preset_button_height - 13.dp) / 2
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = preset,
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal,
                                color = if (isSelected) TempPresetLabelSelected else TempPresetLabelNormal,
                                fontSize = temp_preset_text_size
                            )
                        }
                    }
                }
            }

            // 温度滑块
            Box(contentAlignment = Alignment.Center) {
                Slider(
                    value = tempValue,
                    onValueChange = { tempValue = it },
                    valueRange = 16f..30f,
                    steps = ((30f - 16f - 1).toInt()),
                    colors = SliderDefaults.colors(
                        thumbColor = SliderThumb,
                        activeTrackColor = SliderActive,
                        inactiveTrackColor = SliderInactive,
                        activeTickColor = Color.Transparent,
                        inactiveTickColor = Color.Transparent
                    ),
                    modifier = Modifier.fillMaxWidth()
                )
                Box(
                    modifier = Modifier
                        .size(slider_thumb_size)
                        .clip(CircleShape)
                        .shadow(elevation_sm, CircleShape)
                        .border(slider_thumb_border_width, SliderThumbBorderActive, CircleShape)
                        .background(SliderThumb)
                )
            }

            // 辐射控制（两个开关并排）
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                RadiationSwitchItem(label = "顶面辐射", isChecked = topRadiation) { topRadiation = it }
                RadiationSwitchItem(label = "地面辐射", isChecked = bottomRadiation) { bottomRadiation = it }
            }
        }
    }
}

/**
 * 房间湿度设定卡片
 */
@Composable
private fun RoomHumidityCard(roomName: String) {
    var humidityValue by remember { mutableStateOf(60f) }
    var selectedPreset by remember { mutableStateOf(1) }
    val presets = listOf("偏低-", "适中", "偏高+")

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(
                elevation = elevation_md,
                shape = RoundedCornerShape(corner_md),
                ambientColor = ShadowLight,
                spotColor = ShadowLight
            )
            .clip(RoundedCornerShape(corner_md))
            .background(SurfaceLight)
            .padding(card_padding_large)
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(spacing_lg)) {
            // 标题行：湿度设定 + 数值
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "$roomName 湿度设定",
                    style = MaterialTheme.typography.bodyMedium,
                    color = HumidityTitleColor,
                    fontSize = text_body_size
                )
                Text(
                    text = "${humidityValue.toInt()}%",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = HumidityValueColor,
                    fontSize = 19.sp
                )
            }

            // 档位按钮
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(horizontalArrangement = Arrangement.spacedBy(spacing_sm)) {
                    presets.forEachIndexed { index, preset ->
                        val isSelected = selectedPreset == index
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(temp_preset_corner))
                                .background(
                                    if (isSelected) SurfaceVariantLight else Color.Transparent
                                )
                                .border(
                                    width = 1.dp,
                                    color = if (isSelected) PrimaryBlue else DividerLight,
                                    shape = RoundedCornerShape(temp_preset_corner)
                                )
                                .clickable { selectedPreset = index }
                                .padding(
                                    horizontal = spacing_md,
                                    vertical = (temp_preset_button_height - 13.dp) / 2
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = preset,
                                fontSize = temp_preset_text_size,
                                fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal,
                                color = if (isSelected) TempPresetLabelSelected else TempPresetLabelNormal
                            )
                        }
                    }
                }
            }

            // 湿度滑块
            Box(contentAlignment = Alignment.Center) {
                Slider(
                    value = humidityValue,
                    onValueChange = { humidityValue = it },
                    valueRange = 30f..70f,
                    steps = (70f - 30f - 1).toInt(),
                    colors = SliderDefaults.colors(
                        thumbColor = SliderThumb,
                        activeTrackColor = SliderActive,
                        inactiveTrackColor = SliderInactive,
                        activeTickColor = Color.Transparent,
                        inactiveTickColor = Color.Transparent
                    ),
                    modifier = Modifier.fillMaxWidth()
                )
                Box(
                    modifier = Modifier
                        .size(slider_thumb_size)
                        .clip(CircleShape)
                        .shadow(elevation_sm, CircleShape)
                        .border(slider_thumb_border_width, SliderThumbBorderActive, CircleShape)
                        .background(SliderThumb)
                )
            }
        }
    }
}

/**
 * 辐射控制开关项
 */
@Composable
private fun RadiationSwitchItem(
    label: String,
    isChecked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    var switchState by remember { mutableStateOf(isChecked) }
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(spacing_sm)
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            color = if (switchState) RadiationModeLabelOn else RadiationModeLabelOff,
            fontSize = text_body_size
        )
        Switch(
            checked = switchState,
            onCheckedChange = {
                switchState = it
                onCheckedChange(it)
            },
            colors = SwitchDefaults.colors(
                checkedThumbColor = Color.White,
                checkedTrackColor = SwitchChecked,
                uncheckedThumbColor = Color.White,
                uncheckedTrackColor = SwitchUnchecked
            ),
            modifier = Modifier.width(switch_width)
        )
    }
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
@Composable
private fun RoomFanSpeedSelector() {
    var selectedSpeed by remember { mutableStateOf(0) }
    val speeds = listOf("自动", "低速", "中速", "高速")

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(SurfaceLight)
            .clip(RoundedCornerShape(corner_md))
            .padding(card_padding),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(spacing_md)
    ) {
        // 左侧标题
        Text(
            text = "风速",
            style = MaterialTheme.typography.bodyMedium,
            color = TextSecondaryLight,
            fontSize = text_body_size
        )

        Spacer(modifier = Modifier.weight(1f))

        // 右侧速度按钮组
        Row(horizontalArrangement = Arrangement.spacedBy(fan_speed_gap)) {
            speeds.forEachIndexed { index, speed ->
                val isSelected = selectedSpeed == index
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(fan_speed_corner))
                        .background(
                            if (isSelected) FanSpeedSelectedBg else FanSpeedUnselectedBg
                        )
                        .then(
                            if (!isSelected) {
                                Modifier.border(
                                    width = 1.dp,
                                    color = ChipUnselectedBorder,
                                    shape = RoundedCornerShape(fan_speed_corner)
                                )
                            } else Modifier
                        )
                        .clickable { selectedSpeed = index }
                        .padding(
                            horizontal = fan_speed_button_padding_h,
                            vertical = (fan_speed_button_height - 14.dp) / 2
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = speed,
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal,
                        color = if (isSelected) FanSpeedSelectedText else FanSpeedUnselectedText,
                        fontSize = fan_speed_text_size
                    )
                }
            }
        }
    }
}

// ==================== Preview 函数 ====================

@Preview(showBackground = true, name = "楼层区域-加载中", backgroundColor = 0xFFF1F5F9)
@Composable
fun FloorZoneLoadingPreview() {
    WuHengTheme {
        FloorZoneContent(
            floorsState = UiDataState.Loading,
            roomsState = UiDataState.Loading,
            selectedFloorId = null,
            selectedRoomId = null
        )
    }
}

@Preview(showBackground = true, name = "楼层区域-成功", backgroundColor = 0xFFF1F5F9)
@Composable
fun FloorZoneSuccessPreview() {
    WuHengTheme {
        FloorZoneContent(
            floorsState = UiDataState.Success(
                listOf(
                    FloorInfo(
                        floorId = 1,
                        floorIdNo = "FL001",
                        floorName = "B1地下室",
                        floorLevel = -1,
                        area = "100",
                        roomCount = 3
                    ),
                    FloorInfo(
                        floorId = 2,
                        floorIdNo = "FL002",
                        floorName = "1F一层",
                        floorLevel = 1,
                        area = "120",
                        roomCount = 4
                    ),
                    FloorInfo(
                        floorId = 3,
                        floorIdNo = "FL003",
                        floorName = "2F二层",
                        floorLevel = 2,
                        area = "100",
                        roomCount = 3
                    )
                )
            ),
            roomsState = UiDataState.Success(
                listOf(
                    RoomInfo(
                        roomId = 1,
                        roomIdNo = "RM001",
                        roomName = "客厅",
                        roomType = "living",
                        area = "40",
                        deviceCount = 2
                    ),
                    RoomInfo(
                        roomId = 2,
                        roomIdNo = "RM002",
                        roomName = "主卧",
                        roomType = "bedroom",
                        area = "25",
                        deviceCount = 1
                    ),
                    RoomInfo(
                        roomId = 3,
                        roomIdNo = "RM003",
                        roomName = "儿童房",
                        roomType = "bedroom",
                        area = "20",
                        deviceCount = 1
                    ),
                    RoomInfo(
                        roomId = 4,
                        roomIdNo = "RM004",
                        roomName = "卫生间",
                        roomType = "bathroom",
                        area = "8",
                        deviceCount = 1
                    )
                )
            ),
            selectedFloorId = "2",
            selectedRoomId = "1"
        )
    }
}

@Preview(showBackground = true, name = "楼层区域-错误", backgroundColor = 0xFFF1F5F9)
@Composable
fun FloorZoneErrorPreview() {
    WuHengTheme {
        FloorZoneContent(
            floorsState = UiDataState.Error(
                com.wuheng.smart.data.network.AppException.NetworkError("网络连接失败")
            ),
            roomsState = UiDataState.Error(
                com.wuheng.smart.data.network.AppException.UnknownError("数据加载失败")
            ),
            selectedFloorId = null,
            selectedRoomId = null
        )
    }
}
