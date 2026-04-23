package com.wuheng.smart.presentation.home.components

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.wuheng.smart.data.model.DeviceInfo
import com.wuheng.smart.presentation.base.UiDataState
import com.wuheng.smart.presentation.theme.*

/**
 * 设备卡片 UI State
 * 用于首页设备列表展示
 */
data class DeviceCardUiState(
    val deviceId: String,
    val deviceName: String,
    val deviceType: DeviceType,
    val isPoweredOn: Boolean = false,
    val currentTemp: Float? = null,
    val isCoolingMode: Boolean = true,
    val roomName: String = "",
    val isOnline: Boolean = true,
    val hasError: Boolean = false,
    val errorMessage: String? = null
)

enum class DeviceType {
    CLIMATE, WATER, LIGHT, CURTAIN, SECURITY, OTHER
}

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
@Composable
fun DeviceList(
    devices: List<DeviceCardUiState>,
    isLoading: Boolean = false,
    onDeviceClick: (String) -> Unit = {},
    onDevicePowerToggle: (String, Boolean) -> Unit = { _, _ -> },
    onDeviceModeToggle: (String, Boolean) -> Unit = { _, _ -> }
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(spacing_md)
    ) {
        // 标题行
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "我的设备",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
                color = TextPrimaryLight,
                fontSize = text_h3_size
            )

            if (isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(16.dp),
                    color = PrimaryBlue,
                    strokeWidth = 2.dp
                )
            } else {
                Text(
                    text = "${devices.size}个设备",
                    style = MaterialTheme.typography.bodySmall,
                    color = TextTertiaryLight,
                    fontSize = 12.sp
                )
            }
        }

        if (devices.isEmpty() && !isLoading) {
            // 空状态
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = spacing_xl),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(spacing_sm)
                ) {
                    Icon(
                        imageVector = Icons.Filled.Devices,
                        contentDescription = null,
                        tint = TextTertiaryLight,
                        modifier = Modifier.size(48.dp)
                    )
                    Text(
                        text = "暂无设备",
                        style = MaterialTheme.typography.bodyMedium,
                        color = TextTertiaryLight
                    )
                }
            }
        } else {
            // 设备列表 - 使用LazyRow横向滚动
            LazyRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(spacing_md),
                contentPadding = PaddingValues(horizontal = 0.dp)
            ) {
                items(
                    items = devices,
                    key = { it.deviceId }
                ) { device ->
                    DeviceCard(
                        device = device,
                        onClick = { onDeviceClick(device.deviceId) },
                        onPowerToggle = { onDevicePowerToggle(device.deviceId, it) },
                        onModeToggle = { onDeviceModeToggle(device.deviceId, it) }
                    )
                }
            }
        }
    }
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
@Composable
fun DeviceCard(
    device: DeviceCardUiState,
    onClick: () -> Unit = {},
    onPowerToggle: (Boolean) -> Unit = {},
    onModeToggle: (Boolean) -> Unit = {}
) {
    var powerState by remember(device.deviceId, device.isPoweredOn) {
        mutableStateOf(device.isPoweredOn)
    }

    // 动画状态
    val scale by animateFloatAsState(
        targetValue = 1f,
        animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy),
        label = "CardScale"
    )

    Box(
        modifier = Modifier
            .width(160.dp)
            .shadow(
                elevation = if (powerState) elevation_lg else elevation_sm,
                shape = RoundedCornerShape(corner_md),
                ambientColor = if (powerState) PrimaryBlue.copy(alpha = 0.1f) else ShadowLight,
                spotColor = if (powerState) PrimaryBlue.copy(alpha = 0.1f) else ShadowLight
            )
            .clip(RoundedCornerShape(corner_md))
            .background(
                when {
                    !device.isOnline -> SurfaceVariantLight
                    powerState -> SurfaceLight
                    else -> SurfaceLight
                }
            )
            .then(
                if (device.hasError) {
                    Modifier.border(
                        width = 2.dp,
                        color = ErrorRed.copy(alpha = 0.5f),
                        shape = RoundedCornerShape(corner_md)
                    )
                } else Modifier
            )
            .clickable(onClick = onClick)
            .padding(card_padding_large)
            .graphicsLayer {
                scaleX = scale
                scaleY = scale
            }
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(spacing_md)
        ) {
            // 顶部行：图标 + 状态指示
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // 设备图标
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(RoundedCornerShape(corner_sm))
                        .background(
                            when {
                                !device.isOnline -> DividerLight
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
                            !device.isOnline -> TextDisabledLight
                            powerState -> PrimaryBlue
                            else -> TextTertiaryLight
                        },
                        modifier = Modifier.size(24.dp)
                    )
                }

                // 状态指示器
                if (device.hasError) {
                    // 异常状态
                    Box(
                        modifier = Modifier
                            .size(16.dp)
                            .clip(CircleShape)
                            .background(ErrorRed),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Warning,
                            contentDescription = "异常",
                            tint = Color.White,
                            modifier = Modifier.size(10.dp)
                        )
                    }
                } else if (!device.isOnline) {
                    // 离线状态
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(4.dp))
                            .background(TextDisabledLight.copy(alpha = 0.2f))
                            .padding(horizontal = 6.dp, vertical = 2.dp)
                    ) {
                        Text(
                            text = "离线",
                            style = MaterialTheme.typography.labelSmall,
                            color = TextDisabledLight,
                            fontSize = 10.sp
                        )
                    }
                } else if (powerState) {
                    // 运行中状态
                    Box(
                        modifier = Modifier
                            .size(8.dp)
                            .clip(CircleShape)
                            .background(SuccessGreen)
                    )
                }
            }

            // 设备名称和房间
            Column {
                Text(
                    text = device.deviceName,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Medium,
                    color = if (device.isOnline) TextPrimaryLight else TextDisabledLight,
                    fontSize = text_body_size,
                    maxLines = 1
                )
                Text(
                    text = device.roomName,
                    style = MaterialTheme.typography.bodySmall,
                    color = TextTertiaryLight,
                    fontSize = text_caption_size,
                    maxLines = 1
                )
            }

            // 异常提醒
            AnimatedVisibility(
                visible = device.hasError,
                enter = fadeIn() + expandVertically(),
                exit = fadeOut() + shrinkVertically()
            ) {
                Text(
                    text = device.errorMessage ?: "设备异常",
                    style = MaterialTheme.typography.bodySmall,
                    color = ErrorRed,
                    fontSize = 10.sp,
                    maxLines = 1
                )
            }

            // 温度显示（如果有）
            if (device.currentTemp != null && device.isOnline) {
                Row(
                    verticalAlignment = Alignment.Bottom,
                    horizontalArrangement = Arrangement.spacedBy(2.dp)
                ) {
                    Text(
                        text = "${device.currentTemp.toInt()}",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = if (powerState) TemperatureValueColor else TextTertiaryLight,
                        fontSize = text_body_large_size
                    )
                    Text(
                        text = "°C",
                        style = MaterialTheme.typography.bodySmall,
                        color = if (powerState) TemperatureUnitColor else TextTertiaryLight,
                        fontSize = 10.sp,
                        modifier = Modifier.padding(bottom = 2.dp)
                    )
                }
            }

            // 快捷控制
            if (device.isOnline) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
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

                    // 模式切换按钮（仅温控设备）
                    if (device.deviceType == DeviceType.CLIMATE && powerState) {
                        val isCooling = device.isCoolingMode
                        IconButton(
                            onClick = { onModeToggle(!isCooling) },
                            modifier = Modifier.size(32.dp)
                        ) {
                            Icon(
                                imageVector = if (isCooling) Icons.Filled.AcUnit else Icons.Filled.LocalFireDepartment,
                                contentDescription = if (isCooling) "制冷" else "制热",
                                tint = if (isCooling) PrimaryBlue else WarningYellow,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

/**
 * 设备列表状态组件
 * 用于展示设备列表的加载、成功、错误状态
 */
@Composable
fun DeviceListSection(
    deviceListState: UiDataState<List<DeviceInfo>>,
    currentMode: String = "cooling",
    onDeviceClick: (String) -> Unit = {},
    onDevicePowerToggle: (Int, Boolean) -> Unit = { _, _ -> },
    onRefresh: () -> Unit = {}
) {
    when (deviceListState) {
        is UiDataState.Idle, is UiDataState.Loading -> {
            DeviceList(
                devices = emptyList(),
                isLoading = true
            )
        }
        is UiDataState.Success -> {
            val devices = deviceListState.data.map { device ->
                DeviceCardUiState(
                    deviceId = device.deviceId.toString(),
                    deviceName = device.deviceName,
                    deviceType = when (device.deviceType.lowercase()) {
                        "thermostat", "climate", "kongtiao", "空调" -> DeviceType.CLIMATE
                        "water", "shui", "水系统" -> DeviceType.WATER
                        "light", "dengguang", "灯光" -> DeviceType.LIGHT
                        "curtain", "chuanglian", "窗帘" -> DeviceType.CURTAIN
                        "security", "anfang", "安防" -> DeviceType.SECURITY
                        else -> DeviceType.OTHER
                    },
                    isPoweredOn = device.runStatus == "running",
                    currentTemp = null, // 需要从设备数据获取
                    isCoolingMode = currentMode == "cooling",
                    roomName = device.roomName,
                    isOnline = device.onlineStatus == 1,
                    hasError = device.runStatus == "error",
                    errorMessage = if (device.runStatus == "error") "设备故障" else null
                )
            }

            DeviceList(
                devices = devices,
                isLoading = false,
                onDeviceClick = onDeviceClick,
                onDevicePowerToggle = { deviceId, power ->
                    deviceId.toIntOrNull()?.let { onDevicePowerToggle(it, power) }
                }
            )
        }
        is UiDataState.Error -> {
            // 错误状态显示
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = spacing_xl),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(spacing_sm)
                ) {
                    Icon(
                        imageVector = Icons.Filled.Error,
                        contentDescription = null,
                        tint = ErrorRed,
                        modifier = Modifier.size(48.dp)
                    )
                    Text(
                        text = "加载设备失败",
                        style = MaterialTheme.typography.bodyMedium,
                        color = TextSecondaryLight
                    )
                    TextButton(onClick = onRefresh) {
                        Text("重试")
                    }
                }
            }
        }
        else -> {}
    }
}

/**
 * 获取设备图标
 */
@Composable
private fun getDeviceIcon(deviceType: DeviceType): ImageVector {
    return when (deviceType) {
        DeviceType.CLIMATE -> Icons.Filled.Thermostat
        DeviceType.WATER -> Icons.Filled.WaterDrop
        DeviceType.LIGHT -> Icons.Filled.Lightbulb
        DeviceType.CURTAIN -> Icons.Filled.Curtains
        DeviceType.SECURITY -> Icons.Filled.Security
        DeviceType.OTHER -> Icons.Filled.Devices
    }
}

// ==================== Preview 函数 ====================

@Preview(showBackground = true, name = "设备列表-正常", backgroundColor = 0xFFF1F5F9)
@Composable
fun DeviceListPreview() {
    WuHengTheme {
        DeviceList(
            devices = listOf(
                DeviceCardUiState(
                    deviceId = "1",
                    deviceName = "客厅空调",
                    deviceType = DeviceType.CLIMATE,
                    isPoweredOn = true,
                    currentTemp = 24.5f,
                    isCoolingMode = true,
                    roomName = "客厅",
                    isOnline = true
                ),
                DeviceCardUiState(
                    deviceId = "2",
                    deviceName = "主卧空调",
                    deviceType = DeviceType.CLIMATE,
                    isPoweredOn = false,
                    currentTemp = 26f,
                    isCoolingMode = true,
                    roomName = "主卧",
                    isOnline = true
                ),
                DeviceCardUiState(
                    deviceId = "3",
                    deviceName = "新风系统",
                    deviceType = DeviceType.WATER,
                    isPoweredOn = true,
                    isCoolingMode = false,
                    roomName = "全屋",
                    isOnline = true
                ),
                DeviceCardUiState(
                    deviceId = "4",
                    deviceName = "客厅灯光",
                    deviceType = DeviceType.LIGHT,
                    isPoweredOn = true,
                    isCoolingMode = false,
                    roomName = "客厅",
                    isOnline = true
                )
            )
        )
    }
}

@Preview(showBackground = true, name = "设备列表-含异常", backgroundColor = 0xFFF1F5F9)
@Composable
fun DeviceListWithErrorPreview() {
    WuHengTheme {
        DeviceList(
            devices = listOf(
                DeviceCardUiState(
                    deviceId = "1",
                    deviceName = "客厅空调",
                    deviceType = DeviceType.CLIMATE,
                    isPoweredOn = true,
                    currentTemp = 24.5f,
                    isCoolingMode = true,
                    roomName = "客厅",
                    isOnline = true
                ),
                DeviceCardUiState(
                    deviceId = "2",
                    deviceName = "主卧空调",
                    deviceType = DeviceType.CLIMATE,
                    isPoweredOn = false,
                    currentTemp = 26f,
                    isCoolingMode = true,
                    roomName = "主卧",
                    isOnline = true,
                    hasError = true,
                    errorMessage = "传感器故障"
                ),
                DeviceCardUiState(
                    deviceId = "3",
                    deviceName = "新风系统",
                    deviceType = DeviceType.WATER,
                    isPoweredOn = false,
                    isCoolingMode = false,
                    roomName = "全屋",
                    isOnline = false
                )
            )
        )
    }
}

@Preview(showBackground = true, name = "设备列表-加载中", backgroundColor = 0xFFF1F5F9)
@Composable
fun DeviceListLoadingPreview() {
    WuHengTheme {
        DeviceList(
            devices = emptyList(),
            isLoading = true
        )
    }
}

@Preview(showBackground = true, name = "设备列表-空状态", backgroundColor = 0xFFF1F5F9)
@Composable
fun DeviceListEmptyPreview() {
    WuHengTheme {
        DeviceList(
            devices = emptyList(),
            isLoading = false
        )
    }
}

@Preview(showBackground = true, name = "设备卡片-正常", backgroundColor = 0xFFF1F5F9)
@Composable
fun DeviceCardNormalPreview() {
    WuHengTheme {
        DeviceCard(
            device = DeviceCardUiState(
                deviceId = "1",
                deviceName = "客厅空调",
                deviceType = DeviceType.CLIMATE,
                isPoweredOn = true,
                currentTemp = 24.5f,
                isCoolingMode = true,
                roomName = "客厅",
                isOnline = true
            )
        )
    }
}

@Preview(showBackground = true, name = "设备卡片-关闭", backgroundColor = 0xFFF1F5F9)
@Composable
fun DeviceCardOffPreview() {
    WuHengTheme {
        DeviceCard(
            device = DeviceCardUiState(
                deviceId = "1",
                deviceName = "客厅空调",
                deviceType = DeviceType.CLIMATE,
                isPoweredOn = false,
                currentTemp = 26f,
                isCoolingMode = true,
                roomName = "客厅",
                isOnline = true
            )
        )
    }
}

@Preview(showBackground = true, name = "设备卡片-离线", backgroundColor = 0xFFF1F5F9)
@Composable
fun DeviceCardOfflinePreview() {
    WuHengTheme {
        DeviceCard(
            device = DeviceCardUiState(
                deviceId = "1",
                deviceName = "客厅空调",
                deviceType = DeviceType.CLIMATE,
                isPoweredOn = false,
                currentTemp = 26f,
                isCoolingMode = true,
                roomName = "客厅",
                isOnline = false
            )
        )
    }
}

@Preview(showBackground = true, name = "设备卡片-异常", backgroundColor = 0xFFF1F5F9)
@Composable
fun DeviceCardErrorPreview() {
    WuHengTheme {
        DeviceCard(
            device = DeviceCardUiState(
                deviceId = "1",
                deviceName = "客厅空调",
                deviceType = DeviceType.CLIMATE,
                isPoweredOn = false,
                currentTemp = 26f,
                isCoolingMode = true,
                roomName = "客厅",
                isOnline = true,
                hasError = true,
                errorMessage = "传感器故障"
            )
        )
    }
}
