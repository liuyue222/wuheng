@file:OptIn(ExperimentalMaterial3Api::class)

package com.wuheng.smart.presentation.floorzone

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.wuheng.smart.data.model.DeviceInfo
import com.wuheng.smart.data.model.FloorInfo
import com.wuheng.smart.data.model.RoomInfo
import com.wuheng.smart.presentation.base.UiDataState
import com.wuheng.smart.presentation.theme.*

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
@Composable
fun FloorZoneScreen(
    floorId: Int? = null,
    viewModel: FloorZoneViewModel = hiltViewModel(),
    onNavigateBack: () -> Unit = {},
    onNavigateToDeviceDetail: (String) -> Unit = {}
) {
    val floorsState by viewModel.floorsState.collectAsStateWithLifecycle()
    val roomsState by viewModel.roomsState.collectAsStateWithLifecycle()
    val selectedFloorId by viewModel.selectedFloorId.collectAsStateWithLifecycle()
    val selectedRoomId by viewModel.selectedRoomId.collectAsStateWithLifecycle()
    val roomDevicesState by viewModel.roomDevicesState.collectAsStateWithLifecycle()
    val roomEnvironmentState by viewModel.roomEnvironmentState.collectAsStateWithLifecycle()
    val roomControlState by viewModel.roomControlState.collectAsStateWithLifecycle()

    FloorZoneContent(
        floorsState = floorsState,
        roomsState = roomsState,
        roomDevicesState = roomDevicesState,
        roomEnvironmentState = roomEnvironmentState,
        selectedFloorId = selectedFloorId,
        selectedRoomId = selectedRoomId,
        roomControlState = roomControlState,
        onNavigateBack = onNavigateBack,
        onFloorSelected = { viewModel.selectFloor(it) },
        onRoomSelected = { viewModel.selectRoom(it) },
        onRefresh = { viewModel.refresh() },
        onDevicePowerToggle = { deviceId, power -> viewModel.toggleDevicePower(deviceId, power) },
        onDeviceClick = { onNavigateToDeviceDetail(it.toString()) },
        onTargetTempChanged = { temp -> selectedRoomId?.let { viewModel.setTargetTemperature(it, temp) } },
        onCeilingRadiationToggle = { selectedRoomId?.let { viewModel.toggleCeilingRadiation(it) } },
        onFloorRadiationToggle = { selectedRoomId?.let { viewModel.toggleFloorRadiation(it) } },
        onFanSpeedChanged = { speed -> selectedRoomId?.let { viewModel.setFanSpeed(it, speed) } },
        onTargetHumidityChanged = { humidity -> selectedRoomId?.let { viewModel.setTargetHumidity(it, humidity) } },
        onCo2ThresholdChanged = { threshold -> selectedRoomId?.let { viewModel.setCo2Threshold(it, threshold) } }
    )
}

/**
 * 楼层区域页面内容
 */
@Composable
fun FloorZoneContent(
    floorsState: UiDataState<List<FloorInfo>>,
    roomsState: UiDataState<List<RoomInfo>>,
    roomDevicesState: UiDataState<List<DeviceInfo>>,
    roomEnvironmentState: UiDataState<RoomEnvironmentData>,
    selectedFloorId: String?,
    selectedRoomId: String?,
    roomControlState: RoomControlState = RoomControlState(),
    onNavigateBack: () -> Unit = {},
    onFloorSelected: (String) -> Unit = {},
    onRoomSelected: (String) -> Unit = {},
    onRefresh: () -> Unit = {},
    onDevicePowerToggle: (Int, Boolean) -> Unit = { _, _ -> },
    onDeviceClick: (Int) -> Unit = {},
    onTargetTempChanged: (Float) -> Unit = {},
    onCeilingRadiationToggle: () -> Unit = {},
    onFloorRadiationToggle: () -> Unit = {},
    onFanSpeedChanged: (Int) -> Unit = {},
    onTargetHumidityChanged: (Float) -> Unit = {},
    onCo2ThresholdChanged: (Int) -> Unit = {}
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
            is UiDataState.Success,
            is UiDataState.LoadingWithData,
            is UiDataState.ErrorWithData -> {
                val floors = floorsState.getDataOrNull() ?: emptyList()
                val rooms = roomsState.getDataOrNull() ?: emptyList()
                val roomDevices = roomDevicesState.getDataOrNull() ?: emptyList()
                val roomEnvironment = roomEnvironmentState.getDataOrNull()
                val selectedFloor = floors.find { it.floorId.toString() == selectedFloorId }
                val selectedRoom = rooms.find { it.roomId.toString() == selectedRoomId }

                val isLoadingRooms = roomsState.isLoading()
                val isLoadingDevices = roomDevicesState.isLoading()

                Box(modifier = Modifier.fillMaxSize()) {
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
                                onFloorSelected = { onFloorSelected(it.toString()) },
                                isLoading = isLoadingRooms
                            )
                        }

                        // 房间Chip选择器
                        item {
                            Crossfade(
                                targetState = rooms to selectedRoom,
                                animationSpec = tween(300),
                                label = "RoomChips"
                            ) { (currentRooms, currentSelectedRoom) ->
                                if (currentRooms.isNotEmpty()) {
                                    RoomChipSelector(
                                        rooms = currentRooms,
                                        selectedRoom = currentSelectedRoom,
                                        onRoomSelected = { onRoomSelected(it.toString()) }
                                    )
                                }
                            }
                        }

                        // 房间环境数据卡片 (新增)
                        item {
                            Crossfade(
                                targetState = selectedRoomId,
                                animationSpec = tween(400),
                                label = "RoomEnvironment"
                            ) { roomId ->
                                if (roomId != null) {
                                    RoomEnvironmentCard(
                                        environmentData = roomEnvironment,
                                        isLoading = isLoadingDevices
                                    )
                                }
                            }
                        }

                        // 房间设备列表 (新增)
                        item {
                            Crossfade(
                                targetState = selectedRoomId to roomDevices,
                                animationSpec = tween(400),
                                label = "RoomDevices"
                            ) { (_, devices) ->
                                if (selectedRoomId != null) {
                                    RoomDevicesCard(
                                        devices = devices,
                                        isLoading = isLoadingDevices,
                                        onDevicePowerToggle = onDevicePowerToggle,
                                        onDeviceClick = onDeviceClick
                                    )
                                }
                            }
                        }

                        // 房间控制卡片区域
                        item {
                            Crossfade(
                                targetState = selectedFloorId to selectedRoom?.roomId,
                                animationSpec = tween(400),
                                label = "RoomContent"
                            ) { (_, roomId) ->
                                val roomName = selectedRoom?.roomName ?: "客厅"
                                Column(verticalArrangement = Arrangement.spacedBy(spacing_lg)) {
                                    // 房间温度设定卡片
                                    RoomTemperatureCard(
                                        roomName = roomName,
                                        controlState = roomControlState,
                                        onTempChanged = onTargetTempChanged,
                                        onCeilingToggle = onCeilingRadiationToggle,
                                        onFloorToggle = onFloorRadiationToggle,
                                        isLoading = isLoadingRooms
                                    )

                                    // 房间湿度设定卡片
                                    RoomHumidityCard(
                                        roomName = roomName,
                                        controlState = roomControlState,
                                        onHumidityChanged = onTargetHumidityChanged,
                                        isLoading = isLoadingRooms
                                    )

                                    // 新风微控卡片
                                    FreshAirControlCard(
                                        roomName = roomName,
                                        controlState = roomControlState,
                                        onFanSpeedChanged = onFanSpeedChanged,
                                        onCo2ThresholdChanged = onCo2ThresholdChanged,
                                        onTargetHumidityChanged = onTargetHumidityChanged,
                                        isLoading = isLoadingRooms
                                    )
                                }
                            }
                        }

                        item { Spacer(modifier = Modifier.height(spacing_lg)) }
                    }

                    // 加载指示器
                    AnimatedVisibility(
                        visible = isLoadingRooms || isLoadingDevices,
                        enter = fadeIn(animationSpec = tween(200)),
                        exit = fadeOut(animationSpec = tween(200)),
                        modifier = Modifier.align(Alignment.TopCenter)
                    ) {
                        Box(
                            modifier = Modifier
                                .padding(top = paddingValues.calculateTopPadding() + spacing_lg)
                                .clip(RoundedCornerShape(corner_md))
                                .background(SurfaceLight.copy(alpha = 0.9f))
                                .padding(horizontal = spacing_lg, vertical = spacing_md),
                            contentAlignment = Alignment.Center
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(spacing_sm)
                            ) {
                                CircularProgressIndicator(
                                    modifier = Modifier.size(16.dp),
                                    color = PrimaryBlue,
                                    strokeWidth = 2.dp
                                )
                                Text(
                                    text = "加载中...",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = TextSecondaryLight
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

/**
 * 房间环境数据卡片 (新增)
 */
@Composable
private fun RoomEnvironmentCard(
    environmentData: RoomEnvironmentData?,
    isLoading: Boolean
) {
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
            .alpha(if (isLoading) 0.7f else 1f)
            .padding(card_padding_large)
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(spacing_lg)) {
            // 标题
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "环境数据",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = TextPrimaryLight,
                    fontSize = text_h3_size
                )

                // 更新时间
                Text(
                    text = "刚刚更新",
                    style = MaterialTheme.typography.bodySmall,
                    color = TextTertiaryLight,
                    fontSize = 12.sp
                )
            }

            if (environmentData == null) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = spacing_xl),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "暂无环境数据",
                        style = MaterialTheme.typography.bodyMedium,
                        color = TextTertiaryLight
                    )
                }
            } else {
                // 环境数据网格
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    EnvironmentDataItem(
                        label = "温度",
                        value = "${environmentData.temperature}",
                        unit = "°C",
                        icon = Icons.Filled.Thermostat,
                        color = TemperatureValueColor
                    )
                    EnvironmentDataItem(
                        label = "湿度",
                        value = "${environmentData.humidity}",
                        unit = "%",
                        icon = Icons.Filled.WaterDrop,
                        color = HumidityValueColor
                    )
                    EnvironmentDataItem(
                        label = "CO2",
                        value = "${environmentData.co2}",
                        unit = "ppm",
                        icon = Icons.Filled.Cloud,
                        color = getCo2Color(environmentData.co2)
                    )
                    EnvironmentDataItem(
                        label = "PM2.5",
                        value = "${environmentData.pm25}",
                        unit = "μg/m³",
                        icon = Icons.Filled.Air,
                        color = getPm25Color(environmentData.pm25)
                    )
                }
            }
        }
    }
}

/**
 * 环境数据项
 */
@Composable
private fun EnvironmentDataItem(
    label: String,
    value: String,
    unit: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    color: Color
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(spacing_xs)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = color,
            modifier = Modifier.size(24.dp)
        )
        Row(verticalAlignment = Alignment.Bottom) {
            Text(
                text = value,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = color,
                fontSize = text_body_large_size
            )
            Text(
                text = unit,
                style = MaterialTheme.typography.bodySmall,
                color = TextTertiaryLight,
                fontSize = 10.sp,
                modifier = Modifier.padding(bottom = 2.dp)
            )
        }
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall,
            color = TextSecondaryLight,
            fontSize = text_caption_size
        )
    }
}

/**
 * 房间设备列表卡片 (新增)
 */
@Composable
private fun RoomDevicesCard(
    devices: List<DeviceInfo>,
    isLoading: Boolean,
    onDevicePowerToggle: (Int, Boolean) -> Unit,
    onDeviceClick: (Int) -> Unit
) {
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
            .alpha(if (isLoading) 0.7f else 1f)
            .padding(card_padding_large)
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(spacing_lg)) {
            // 标题
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "房间设备",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = TextPrimaryLight,
                    fontSize = text_h3_size
                )

                Text(
                    text = "${devices.size}个设备",
                    style = MaterialTheme.typography.bodySmall,
                    color = TextTertiaryLight,
                    fontSize = 12.sp
                )
            }

            if (devices.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = spacing_xl),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "暂无设备",
                        style = MaterialTheme.typography.bodyMedium,
                        color = TextTertiaryLight
                    )
                }
            } else {
                // 设备列表
                Column(verticalArrangement = Arrangement.spacedBy(spacing_md)) {
                    devices.forEach { device ->
                        DeviceListItem(
                            device = device,
                            onPowerToggle = { onDevicePowerToggle(device.deviceId, it) },
                            onClick = { onDeviceClick(device.deviceId) }
                        )
                        if (device != devices.last()) {
                            Divider(color = DividerLight, thickness = 0.5.dp)
                        }
                    }
                }
            }
        }
    }
}

/**
 * 设备列表项
 */
@Composable
private fun DeviceListItem(
    device: DeviceInfo,
    onPowerToggle: (Boolean) -> Unit,
    onClick: () -> Unit
) {
    var powerState by remember { mutableStateOf(device.runStatus == "running") }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(vertical = spacing_sm),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(spacing_md)
        ) {
            // 设备图标
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(RoundedCornerShape(corner_sm))
                    .background(
                        when {
                            device.onlineStatus != 1 -> DividerLight
                            powerState -> PrimaryBlue.copy(alpha = 0.1f)
                            else -> SurfaceVariantLight
                        }
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = getDeviceIcon(device.deviceType),
                    contentDescription = null,
                    tint = when {
                        device.onlineStatus != 1 -> TextDisabledLight
                        powerState -> PrimaryBlue
                        else -> TextTertiaryLight
                    },
                    modifier = Modifier.size(24.dp)
                )
            }

            Column {
                Text(
                    text = device.deviceName,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Medium,
                    color = if (device.onlineStatus == 1) TextPrimaryLight else TextDisabledLight,
                    fontSize = text_body_size
                )
                Text(
                    text = getDeviceStatusText(device),
                    style = MaterialTheme.typography.bodySmall,
                    color = getDeviceStatusColor(device),
                    fontSize = 12.sp
                )
            }
        }

        // 电源开关
        Switch(
            checked = powerState,
            onCheckedChange = {
                powerState = it
                onPowerToggle(it)
            },
            enabled = device.onlineStatus == 1,
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
 * 获取设备图标
 */
@Composable
private fun getDeviceIcon(deviceType: String): androidx.compose.ui.graphics.vector.ImageVector {
    return when (deviceType.lowercase()) {
        "thermostat" -> Icons.Filled.Thermostat
        "sensor" -> Icons.Filled.Sensors
        "fresh_air" -> Icons.Filled.Air
        "floor_heating" -> Icons.Filled.LocalFireDepartment
        "humidifier" -> Icons.Filled.WaterDrop
        else -> Icons.Filled.Devices
    }
}

/**
 * 获取设备状态文本
 */
private fun getDeviceStatusText(device: DeviceInfo): String {
    return when {
        device.onlineStatus != 1 -> "离线"
        device.runStatus == "running" -> "运行中"
        device.runStatus == "standby" -> "待机"
        device.runStatus == "error" -> "故障"
        else -> "已关闭"
    }
}

/**
 * 获取设备状态颜色
 */
@Composable
private fun getDeviceStatusColor(device: DeviceInfo): Color {
    return when {
        device.onlineStatus != 1 -> TextDisabledLight
        device.runStatus == "running" -> SuccessGreen
        device.runStatus == "standby" -> WarningYellow
        device.runStatus == "error" -> ErrorRed
        else -> TextTertiaryLight
    }
}

/**
 * 房间环境数据
 */
data class RoomEnvironmentData(
    val temperature: Float = 0f,
    val humidity: Float = 0f,
    val co2: Int = 0,
    val pm25: Int = 0,
    val voc: Int = 0,
    val updateTime: Long = System.currentTimeMillis()
)

/**
 * 楼层选择器组件
 */
@Composable
private fun FloorSelector(
    floors: List<FloorInfo>,
    selectedFloor: FloorInfo?,
    onFloorSelected: (Int) -> Unit,
    isLoading: Boolean = false
) {
    var expanded by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxWidth()) {
        val floorName = selectedFloor?.floorName
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(corner_md))
                .clickable(enabled = !isLoading) { expanded = true }
                .padding(vertical = spacing_sm),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(spacing_sm)
            ) {
                Text(
                    text = floorName ?: "选择楼层",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = TextPrimaryLight,
                    fontSize = floor_button_text_size
                )
                if (isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(16.dp),
                        color = PrimaryBlue,
                        strokeWidth = 2.dp
                    )
                }
            }
            val rotation by animateFloatAsState(
                targetValue = if (expanded) 180f else 0f,
                animationSpec = tween(300),
                label = "ArrowRotation"
            )
            Icon(
                imageVector = Icons.Default.KeyboardArrowDown,
                contentDescription = "展开楼层",
                tint = if (isLoading) TextTertiaryLight else FloorDropdownArrowColor,
                modifier = Modifier
                    .size(floor_dropdown_arrow_size)
                    .graphicsLayer { rotationZ = rotation }
                    .alpha(if (isLoading) 0.5f else 1f)
            )
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .width(200.dp)
                .background(SurfaceLight)
        ) {
            floors.forEachIndexed { index, floor ->
                AnimatedVisibility(
                    visible = expanded,
                    enter = fadeIn(animationSpec = tween(300, delayMillis = index * 50)) +
                            slideInVertically(animationSpec = tween(300, delayMillis = index * 50)) { it / 2 }
                ) {
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
}

/**
 * 房间Chip选择器
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
 */
@Composable
private fun RoomTemperatureCard(
    roomName: String,
    controlState: RoomControlState = RoomControlState(),
    onTempChanged: (Float) -> Unit = {},
    onCeilingToggle: () -> Unit = {},
    onFloorToggle: () -> Unit = {},
    isLoading: Boolean = false
) {
    var mainSwitch by remember(controlState.roomId) { mutableStateOf(true) }
    val tempValue = controlState.targetTemperature
    val topRadiation = controlState.ceilingRadiation
    val bottomRadiation = controlState.floorRadiation

    val presets = listOf("偏低-", "适中", "偏高+")
    val selectedPreset = when {
        tempValue <= 20f -> 0
        tempValue in 21f..25f -> 1
        else -> 2
    }

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
            .alpha(if (isLoading) 0.7f else 1f)
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
                                .clickable {
                                    val newTemp = when (index) {
                                        0 -> (controlState.temperature - 2f).coerceAtLeast(16f)
                                        1 -> controlState.temperature
                                        2 -> (controlState.temperature + 2f).coerceAtMost(30f)
                                        else -> controlState.temperature
                                    }
                                    onTempChanged(newTemp)
                                }
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
                    onValueChange = onTempChanged,
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
            }

            // 辐射控制
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                RadiationSwitchItem(label = "顶面辐射", isChecked = topRadiation) { onCeilingToggle() }
                RadiationSwitchItem(label = "地面辐射", isChecked = bottomRadiation) { onFloorToggle() }
            }
        }
    }
}

/**
 * 房间湿度设定卡片
 */
@Composable
private fun RoomHumidityCard(
    roomName: String,
    controlState: RoomControlState = RoomControlState(),
    onHumidityChanged: (Float) -> Unit = {},
    isLoading: Boolean = false
) {
    val humidityValue = controlState.targetHumidity
    var selectedPreset by remember(controlState.roomId) { mutableStateOf(1) }
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
            .alpha(if (isLoading) 0.7f else 1f)
            .padding(card_padding_large)
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(spacing_lg)) {
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
                                .clickable {
                                    val newHumidity = when (index) {
                                        0 -> (controlState.humidity - 10f).coerceAtLeast(30f)
                                        1 -> controlState.humidity
                                        2 -> (controlState.humidity + 10f).coerceAtMost(70f)
                                        else -> controlState.humidity
                                    }
                                    selectedPreset = index
                                    onHumidityChanged(newHumidity)
                                }
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

            Box(contentAlignment = Alignment.Center) {
                Slider(
                    value = humidityValue,
                    onValueChange = onHumidityChanged,
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
    var switchState by remember(isChecked) { mutableStateOf(isChecked) }
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
 * 新风微控卡片
 */
@Composable
private fun FreshAirControlCard(
    roomName: String,
    controlState: RoomControlState = RoomControlState(),
    onFanSpeedChanged: (Int) -> Unit = {},
    onCo2ThresholdChanged: (Int) -> Unit = {},
    onTargetHumidityChanged: (Float) -> Unit = {},
    isLoading: Boolean = false
) {
    val co2Threshold = controlState.co2Threshold.toFloat()
    val humiditySet = controlState.targetHumidity
    val selectedSpeed = controlState.fanSpeed
    val speeds = listOf("自动", "低速", "中速", "高速")

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
            .alpha(if (isLoading) 0.7f else 1f)
            .padding(card_padding_large)
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(spacing_lg)) {
            Text(
                text = "$roomName 新风微控",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
                color = TextPrimaryLight,
                fontSize = text_h2_size
            )

            Column(verticalArrangement = Arrangement.spacedBy(spacing_sm)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "CO2阈值",
                        style = MaterialTheme.typography.bodyMedium,
                        color = TextSecondaryLight,
                        fontSize = text_body_size
                    )
                    Text(
                        text = "${co2Threshold.toInt()} ppm",
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Bold,
                        color = PrimaryBlue
                    )
                }

                Slider(
                    value = co2Threshold,
                    onValueChange = { onCo2ThresholdChanged(it.toInt()) },
                    valueRange = 400f..1500f,
                    steps = 10,
                    colors = SliderDefaults.colors(
                        thumbColor = SliderThumb,
                        activeTrackColor = SliderActive,
                        inactiveTrackColor = SliderInactive
                    )
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("400", fontSize = 12.sp, color = TextTertiaryLight)
                    Text("1500", fontSize = 12.sp, color = TextTertiaryLight)
                }
            }

            Divider(color = DividerLight)

            Column(verticalArrangement = Arrangement.spacedBy(spacing_sm)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "湿度设定",
                        style = MaterialTheme.typography.bodyMedium,
                        color = TextSecondaryLight,
                        fontSize = text_body_size
                    )
                    Text(
                        text = "${humiditySet.toInt()}%",
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Bold,
                        color = PrimaryBlue
                    )
                }

                Slider(
                    value = humiditySet,
                    onValueChange = onTargetHumidityChanged,
                    valueRange = 30f..70f,
                    steps = 7,
                    colors = SliderDefaults.colors(
                        thumbColor = SliderThumb,
                        activeTrackColor = SliderActive,
                        inactiveTrackColor = SliderInactive
                    )
                )
            }

            Divider(color = DividerLight)

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "风速",
                    style = MaterialTheme.typography.bodyMedium,
                    color = TextSecondaryLight,
                    fontSize = text_body_size
                )

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
                                .clickable {
                                    onFanSpeedChanged(index)
                                }
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
    }
}

/**
 * 获取CO2颜色
 */
private fun getCo2Color(co2: Int): Color {
    return when {
        co2 <= 600 -> SuccessGreen
        co2 <= 1000 -> WarningYellow
        else -> ErrorRed
    }
}

/**
 * 获取PM2.5颜色
 */
private fun getPm25Color(pm25: Int): Color {
    return when {
        pm25 <= 35 -> SuccessGreen
        pm25 <= 75 -> WarningYellow
        else -> ErrorRed
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
            roomDevicesState = UiDataState.Loading,
            roomEnvironmentState = UiDataState.Loading,
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
            roomDevicesState = UiDataState.Success(
                listOf(
                    DeviceInfo(
                        deviceId = 1,
                        deviceIdNo = "DEV001",
                        deviceName = "客厅温控器",
                        deviceType = "thermostat",
                        deviceModel = "TH-2025A",
                        onlineStatus = 1,
                        runStatus = "running",
                        roomName = "客厅"
                    ),
                    DeviceInfo(
                        deviceId = 2,
                        deviceIdNo = "DEV002",
                        deviceName = "环境传感器",
                        deviceType = "sensor",
                        deviceModel = "SE-001",
                        onlineStatus = 1,
                        runStatus = "running",
                        roomName = "客厅"
                    )
                )
            ),
            roomEnvironmentState = UiDataState.Success(
                RoomEnvironmentData(
                    temperature = 24.5f,
                    humidity = 55f,
                    co2 = 650,
                    pm25 = 25,
                    voc = 150
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
            roomDevicesState = UiDataState.Error(
                com.wuheng.smart.data.network.AppException.UnknownError("设备数据加载失败")
            ),
            roomEnvironmentState = UiDataState.Error(
                com.wuheng.smart.data.network.AppException.UnknownError("环境数据加载失败")
            ),
            selectedFloorId = null,
            selectedRoomId = null
        )
    }
}
