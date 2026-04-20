@file:OptIn(ExperimentalMaterial3Api::class)

package com.wuheng.smart.presentation.device

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
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
import com.wuheng.smart.data.model.DeviceData
import com.wuheng.smart.data.model.DeviceInfo
import com.wuheng.smart.data.model.DeviceRunStatus
import com.wuheng.smart.presentation.base.UiDataState
import com.wuheng.smart.presentation.theme.*

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
 *   - 设备详情设计图 -> 展示设备详细信息和控制功能
 */
@Composable
fun DeviceDetailScreen(
    deviceId: String,
    viewModel: DeviceDetailViewModel = hiltViewModel(),
    onNavigateBack: () -> Unit = {},
    onNavigateToEdit: (String) -> Unit = {}
) {
    val deviceInfoState by viewModel.deviceInfoState.collectAsStateWithLifecycle()
    val deviceDataState by viewModel.deviceDataState.collectAsStateWithLifecycle()
    
    // 将String类型的deviceId转换为Int
    val deviceIdInt = remember(deviceId) { deviceId.toIntOrNull() ?: 0 }

    DeviceDetailContent(
        deviceInfoState = deviceInfoState,
        deviceDataState = deviceDataState,
        onNavigateBack = onNavigateBack,
        onNavigateToEdit = { onNavigateToEdit(deviceId) },
        onRefresh = { viewModel.refreshDeviceData(deviceIdInt) },
        onPowerToggle = { viewModel.togglePower(deviceIdInt, it) },
        onFanSpeedChange = { viewModel.setFanSpeed(deviceIdInt, it) }
    )
}

/**
 * 设备详情页面内容
 */
@Composable
fun DeviceDetailContent(
    deviceInfoState: UiDataState<DeviceInfo>,
    deviceDataState: UiDataState<DeviceData>,
    onNavigateBack: () -> Unit = {},
    onNavigateToEdit: () -> Unit = {},
    onRefresh: () -> Unit = {},
    onPowerToggle: (Boolean) -> Unit = {},
    onFanSpeedChange: (Int) -> Unit = {}
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "设备详情",
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
                actions = {
                    TextButton(onClick = onNavigateToEdit) {
                        Text(
                            text = "编辑",
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Medium,
                            color = PrimaryBlue
                        )
                    }
                },
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = BackgroundLight
                )
            )
        }
    ) { paddingValues ->
        when (deviceInfoState) {
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
                val deviceInfo = (deviceInfoState as UiDataState.Success<DeviceInfo>).data
                val deviceData = (deviceDataState as? UiDataState.Success<DeviceData>)?.data

                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                        .background(BackgroundLight)
                        .padding(horizontal = page_margin_horizontal),
                    verticalArrangement = Arrangement.spacedBy(spacing_lg)
                ) {
                    item { Spacer(modifier = Modifier.height(spacing_sm)) }

                    // 设备基本信息卡片
                    item {
                        DeviceInfoCard(
                            deviceInfo = deviceInfo,
                            onPowerToggle = onPowerToggle
                        )
                    }

                    // 设备实时数据卡片
                    item {
                        DeviceDataCard(deviceData = deviceData)
                    }

                    // 设备控制卡片
                    item {
                        DeviceControlCard(
                            deviceData = deviceData,
                            onFanSpeedChange = onFanSpeedChange
                        )
                    }

                    item { Spacer(modifier = Modifier.height(spacing_lg)) }
                }
            }
        }
    }
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
@Composable
private fun DeviceInfoCard(
    deviceInfo: DeviceInfo,
    onPowerToggle: (Boolean) -> Unit
) {
    var powerState by remember { mutableStateOf(deviceInfo.runStatus == DeviceRunStatus.RUNNING.value) }

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
            // 标题行：设备名称 + 电源开关
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = deviceInfo.deviceName,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = TextPrimaryLight,
                        fontSize = text_h2_size
                    )
                    Spacer(modifier = Modifier.height(spacing_xs))
                    Text(
                        text = deviceInfo.roomName,
                        style = MaterialTheme.typography.bodyMedium,
                        color = TextSecondaryLight,
                        fontSize = text_body_size
                    )
                }

                // 电源开关
                Switch(
                    checked = powerState,
                    onCheckedChange = {
                        powerState = it
                        onPowerToggle(it)
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

            // 分隔线
            Divider(
                color = DividerLight,
                thickness = 1.dp,
                modifier = Modifier.fillMaxWidth()
            )

            // 设备详细信息网格
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                DeviceInfoItem(
                    label = "设备型号",
                    value = deviceInfo.deviceModel,
                    modifier = Modifier.weight(1f)
                )
                DeviceInfoItem(
                    label = "设备类型",
                    value = getDeviceTypeDisplay(deviceInfo.deviceType),
                    modifier = Modifier.weight(1f)
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                DeviceInfoItem(
                    label = "设备编号",
                    value = deviceInfo.deviceIdNo,
                    modifier = Modifier.weight(1f)
                )
                DeviceInfoItem(
                    label = "在线状态",
                    value = if (deviceInfo.onlineStatus == 1) "在线" else "离线",
                    valueColor = if (deviceInfo.onlineStatus == 1) SuccessGreen else TextTertiaryLight,
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

/**
 * 设备信息项
 */
@Composable
private fun DeviceInfoItem(
    label: String,
    value: String,
    modifier: Modifier = Modifier,
    valueColor: Color = TextPrimaryLight
) {
    Column(modifier = modifier) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall,
            color = TextTertiaryLight,
            fontSize = text_caption_size
        )
        Spacer(modifier = Modifier.height(spacing_xs))
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Medium,
            color = valueColor,
            fontSize = text_body_size
        )
    }
}

/**
 * 设备实时数据卡片
 *
 * 设计规范：
 * - 2x3 网格布局展示环境数据
 * - 温度、湿度、CO2、PM2.5、VOC
 */
@Composable
private fun DeviceDataCard(deviceData: DeviceData?) {
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
            // 标题
            Text(
                text = "实时数据",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
                color = TextPrimaryLight,
                fontSize = text_h3_size
            )

            if (deviceData == null) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = spacing_xl),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "暂无数据",
                        style = MaterialTheme.typography.bodyMedium,
                        color = TextTertiaryLight
                    )
                }
            } else {
                // 数据网格
                Column(verticalArrangement = Arrangement.spacedBy(spacing_md)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        DataItem(
                            label = "温度",
                            value = "${deviceData.temperature}°C",
                            icon = "��️",
                            color = TemperatureValueColor
                        )
                        DataItem(
                            label = "湿度",
                            value = "${deviceData.humidity}%",
                            icon = "��",
                            color = HumidityValueColor
                        )
                        DataItem(
                            label = "CO2",
                            value = "${deviceData.co2}",
                            unit = "ppm",
                            icon = "��️",
                            color = getCo2Color(deviceData.co2)
                        )
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        DataItem(
                            label = "PM2.5",
                            value = "${deviceData.pm25}",
                            unit = "μg/m³",
                            icon = "��",
                            color = getPm25Color(deviceData.pm25)
                        )
                        DataItem(
                            label = "VOC",
                            value = "${deviceData.voc}",
                            unit = "mg/m³",
                            icon = "��",
                            color = getVocColor(deviceData.voc)
                        )
                        DataItem(
                            label = "风速",
                            value = "${deviceData.fanSpeed}",
                            unit = "档",
                            icon = "��",
                            color = PrimaryBlue
                        )
                    }
                }
            }
        }
    }
}

/**
 * 数据项组件
 */
@Composable
private fun DataItem(
    label: String,
    value: String,
    icon: String,
    color: Color,
    unit: String = ""
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(spacing_xs),
        modifier = Modifier.padding(horizontal = spacing_sm)
    ) {
        Text(
            text = icon,
            fontSize = 24.sp
        )
        Row(verticalAlignment = Alignment.Bottom) {
            Text(
                text = value,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = color,
                fontSize = text_body_large_size
            )
            if (unit.isNotEmpty()) {
                Text(
                    text = unit,
                    style = MaterialTheme.typography.bodySmall,
                    color = TextTertiaryLight,
                    fontSize = text_caption_size,
                    modifier = Modifier.padding(bottom = 2.dp)
                )
            }
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
 * 设备控制卡片
 */
@Composable
private fun DeviceControlCard(
    deviceData: DeviceData?,
    onFanSpeedChange: (Int) -> Unit
) {
    var fanSpeed by remember { mutableStateOf(deviceData?.fanSpeed ?: 1) }

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
            // 标题
            Text(
                text = "设备控制",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
                color = TextPrimaryLight,
                fontSize = text_h3_size
            )

            // 风速控制
            Column(verticalArrangement = Arrangement.spacedBy(spacing_md)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "风速调节",
                        style = MaterialTheme.typography.bodyMedium,
                        color = TextSecondaryLight,
                        fontSize = text_body_size
                    )
                    Text(
                        text = "${fanSpeed}档",
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Medium,
                        color = PrimaryBlue,
                        fontSize = text_body_size
                    )
                }

                // 风速档位选择
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(spacing_sm)
                ) {
                    val speeds = listOf(1, 2, 3, 4, 5)
                    speeds.forEach { speed ->
                        val isSelected = fanSpeed == speed
                        Box(
                            modifier = Modifier
                                .weight(1f)
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
                                    fanSpeed = speed
                                    onFanSpeedChange(speed)
                                }
                                .padding(vertical = spacing_sm),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "$speed",
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal,
                                color = if (isSelected) FanSpeedSelectedText else FanSpeedUnselectedText,
                                fontSize = fan_speed_text_size
                            )
                        }
                    }
                }
            }

            // 阀门开度显示
            if (deviceData != null) {
                Divider(
                    color = DividerLight,
                    thickness = 1.dp,
                    modifier = Modifier.fillMaxWidth()
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "阀门开度",
                        style = MaterialTheme.typography.bodyMedium,
                        color = TextSecondaryLight,
                        fontSize = text_body_size
                    )
                    Text(
                        text = "${deviceData.valveOpen}%",
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Medium,
                        color = PrimaryBlue,
                        fontSize = text_body_size
                    )
                }

                // 阀门开度进度条
                LinearProgressIndicator(
                    progress = deviceData.valveOpen / 100f,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(8.dp)
                        .clip(RoundedCornerShape(4.dp)),
                    color = PrimaryBlue,
                    trackColor = ProgressTrackBg
                )
            }
        }
    }
}

/**
 * 获取设备类型显示名称
 */
private fun getDeviceTypeDisplay(deviceType: String): String {
    return when (deviceType.lowercase()) {
        "thermostat" -> "温控器"
        "sensor" -> "传感器"
        "valve" -> "阀门"
        "fan" -> "风机"
        "pump" -> "水泵"
        else -> "其他"
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

/**
 * 获取VOC颜色
 */
private fun getVocColor(voc: Int): Color {
    return when {
        voc <= 200 -> SuccessGreen
        voc <= 500 -> WarningYellow
        else -> ErrorRed
    }
}

// ==================== Preview 函数 ====================

@Preview(showBackground = true, name = "设备详情-加载中", backgroundColor = 0xFFF1F5F9)
@Composable
fun DeviceDetailLoadingPreview() {
    WuHengTheme {
        DeviceDetailContent(
            deviceInfoState = UiDataState.Loading,
            deviceDataState = UiDataState.Loading
        )
    }
}

@Preview(showBackground = true, name = "设备详情-成功", backgroundColor = 0xFFF1F5F9)
@Composable
fun DeviceDetailSuccessPreview() {
    WuHengTheme {
        DeviceDetailContent(
            deviceInfoState = UiDataState.Success(
                DeviceInfo(
                    deviceId = 1,
                    deviceIdNo = "DEV001",
                    deviceName = "客厅温控器",
                    deviceType = "thermostat",
                    deviceModel = "XYN-TC200",
                    onlineStatus = 1,
                    runStatus = "running",
                    roomName = "客厅"
                )
            ),
            deviceDataState = UiDataState.Success(
                DeviceData(
                    dataId = 1,
                    deviceId = 1,
                    temperature = "24.5",
                    humidity = "55",
                    co2 = 650,
                    pm25 = 25,
                    voc = 150,
                    fanSpeed = 2,
                    valveOpen = 60,
                    power = 1,
                    reportTime = System.currentTimeMillis()
                )
            )
        )
    }
}

@Preview(showBackground = true, name = "设备详情-错误", backgroundColor = 0xFFF1F5F9)
@Composable
fun DeviceDetailErrorPreview() {
    WuHengTheme {
        DeviceDetailContent(
            deviceInfoState = UiDataState.Error(
                com.wuheng.smart.data.network.AppException.NetworkError("网络连接失败")
            ),
            deviceDataState = UiDataState.Error(
                com.wuheng.smart.data.network.AppException.UnknownError("数据加载失败")
            )
        )
    }
}
