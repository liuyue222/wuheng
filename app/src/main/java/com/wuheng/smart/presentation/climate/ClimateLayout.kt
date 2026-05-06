@file:OptIn(ExperimentalMaterial3Api::class)

package com.wuheng.smart.presentation.climate

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsDraggedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.wuheng.smart.presentation.components.*
import com.wuheng.smart.presentation.theme.*

/**
 * 冷暖舒适页面布局 - 纯UI组件
 *
 * 布局结构分析：
 * 1. Tab切换：全屋 / 楼层
 * 2. 全屋模式：温度设定滑块(16-30°C)、湿度设定滑块
 * 3. 楼层模式：楼层列表，每个楼层显示设备状态
 *
 * 性能优化：
 * 1. 滑块使用 remember 缓存本地状态，避免拖动时频繁触发重组
 * 2. 使用 LaunchedEffect 和 snapshotFlow 实现拖动结束后再回调
 * 3. 使用 derivedStateOf 优化派生状态计算
 */

// ==================== 主布局 ====================

@Composable
fun ClimateLayout(
    uiState: ClimateUiState,
    onTabSelected: (ClimateTab) -> Unit,
    onTemperatureChange: (Float) -> Unit,
    onHumidityChange: (Float) -> Unit,
    onFloorToggle: (String, Boolean) -> Unit,
    onFloorSelected: (String) -> Unit,
    modifier: Modifier = Modifier,
    maxWidth: Dp = 360.dp
) {
    val isWide = maxWidth >= 720.dp
    val horizontalPadding = if (isWide) page_margin_horizontal_wide else page_margin_horizontal

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(BackgroundLight),
        contentPadding = PaddingValues(
            start = horizontalPadding,
            end = horizontalPadding,
            top = page_top_safe_area,
            bottom = page_bottom_safe_area
        )
    ) {
        // Tab切换
        item {
            ClimateTabSelector(
                selectedTab = uiState.selectedTab,
                onTabSelected = onTabSelected
            )
        }

        // 根据选中Tab显示不同内容
        when (uiState.selectedTab) {
            ClimateTab.WHOLE_HOUSE -> {
                // 全屋温度设定
                item {
                    Spacer(modifier = Modifier.height(spacing_lg))
                    TemperatureSettingCard(
                        temperature = uiState.temperature,
                        onTemperatureChange = onTemperatureChange
                    )
                }

                // 全屋湿度设定
                item {
                    Spacer(modifier = Modifier.height(spacing_lg))
                    HumiditySettingCard(
                        humidity = uiState.humidity,
                        onHumidityChange = onHumidityChange
                    )
                }
            }
            ClimateTab.FLOOR -> {
                item {
                    Spacer(modifier = Modifier.height(spacing_lg))
                    FloorChipSelector(
                        floors = uiState.floors,
                        selectedFloorId = uiState.selectedFloorId,
                        onFloorSelected = onFloorSelected
                    )
                }

                if (uiState.selectedFloorId != null) {
                    if (uiState.roomsLoading) {
                        item {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 32.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                CircularProgressIndicator(
                                    color = PrimaryBlue,
                                    modifier = Modifier.size(32.dp),
                                    strokeWidth = 3.dp
                                )
                            }
                        }
                    } else if (uiState.rooms.isNotEmpty()) {
                        items(uiState.rooms.size) { index ->
                            Spacer(modifier = Modifier.height(if (index == 0) spacing_md else spacing_md))
                            RoomCard(room = uiState.rooms[index])
                        }
                    } else {
                        item {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 24.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "该楼层暂无房间",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = TextTertiaryLight,
                                    fontSize = 13.sp
                                )
                            }
                        }
                    }
                } else {
                    item {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 24.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "请选择楼层查看房间",
                                style = MaterialTheme.typography.bodyMedium,
                                color = TextTertiaryLight,
                                fontSize = 13.sp
                            )
                        }
                    }
                }
            }
        }

        // 底部间距
        item {
            Spacer(modifier = Modifier.height(spacing_xl))
        }
    }
}

// ==================== 子组件 ====================

/**
 * Tab选择器 - 像素级还原设计图
 */
@Composable
private fun ClimateTabSelector(
    selectedTab: ClimateTab,
    onTabSelected: (ClimateTab) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(
                elevation = 2.dp,
                shape = RoundedCornerShape(28.dp),
                spotColor = Color.Black.copy(alpha = 0.06f)
            )
            .clip(RoundedCornerShape(28.dp))
            .background(Color.White)
            .padding(4.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            ClimateTabItem(
                icon = Icons.Default.Home,
                label = "全屋",
                selected = selectedTab == ClimateTab.WHOLE_HOUSE,
                onClick = { onTabSelected(ClimateTab.WHOLE_HOUSE) },
                modifier = Modifier.weight(1f)
            )
            ClimateTabItem(
                icon = Icons.Default.List,
                label = "楼层",
                selected = selectedTab == ClimateTab.FLOOR,
                onClick = { onTabSelected(ClimateTab.FLOOR) },
                modifier = Modifier.weight(1f)
            )
        }
    }
}

/**
 * Tab项 - 像素级还原设计图
 */
@Composable
private fun ClimateTabItem(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    label: String,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(24.dp))
            .background(if (selected) PrimaryBlue else Color.Transparent)
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 10.dp),
        contentAlignment = Alignment.Center
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier.size(18.dp),
                tint = if (selected) Color.White else TextSecondaryLight
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
                text = label,
                style = MaterialTheme.typography.bodyMedium,
                color = if (selected) Color.White else TextSecondaryLight,
                fontWeight = if (selected) FontWeight.Medium else FontWeight.Normal,
                fontSize = 14.sp
            )
        }
    }
}

/**
 * 温度设定卡片 - 像素级还原设计图
 *
 * 性能优化：
 * 1. 使用 remember 缓存本地滑块状态，避免拖动时触发父组件重组
 * 2. 使用 interactionSource 监听滑块交互状态
 * 3. 只有在滑块拖动结束时才触发 onTemperatureChange 回调
 */
@Composable
private fun TemperatureSettingCard(
    temperature: Float,
    onTemperatureChange: (Float) -> Unit
) {
    // 使用 remember 缓存本地滑块值，避免拖动时触发父组件重组
    var localTemperature by remember(temperature) { mutableStateOf(temperature) }
    // 使用 rememberUpdatedState 确保回调始终引用最新值
    val currentOnChange by rememberUpdatedState(onTemperatureChange)
    // 监听滑块交互状态
    val interactionSource = remember { MutableInteractionSource() }
    val isDragging by interactionSource.collectIsDraggedAsState()

    // 当拖动结束时，触发回调
    LaunchedEffect(isDragging) {
        if (!isDragging) {
            // 拖动结束，如果值有变化则触发回调
            if (localTemperature != temperature) {
                currentOnChange(localTemperature)
            }
        }
    }

    // 当外部 temperature 变化时（非拖动状态），更新本地值
    LaunchedEffect(temperature) {
        if (!isDragging) {
            localTemperature = temperature
        }
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(
                elevation = 2.dp,
                shape = RoundedCornerShape(20.dp),
                spotColor = Color.Black.copy(alpha = 0.06f)
            )
            .clip(RoundedCornerShape(20.dp))
            .background(Color.White)
            .padding(24.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "全屋温度设定",
                style = MaterialTheme.typography.bodyMedium,
                color = TextSecondaryLight,
                fontSize = 14.sp
            )

            Spacer(modifier = Modifier.height(16.dp))

            // 温度显示 - 像素级还原
            Row(
                verticalAlignment = Alignment.Top
            ) {
                Text(
                    text = String.format("%.1f", localTemperature),
                    style = MaterialTheme.typography.displayLarge,
                    color = TextPrimaryLight,
                    fontSize = 56.sp,
                    fontWeight = FontWeight.Light
                )
                Text(
                    text = "°",
                    style = MaterialTheme.typography.headlineLarge,
                    color = TextSecondaryLight,
                    fontSize = 32.sp,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // 滑块 - 像素级还原设计图样式
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Slider(
                    value = localTemperature,
                    onValueChange = { localTemperature = it },
                    valueRange = 16f..30f,
                    modifier = Modifier.fillMaxWidth(),
                    interactionSource = interactionSource,
                    colors = SliderDefaults.colors(
                        thumbColor = Color.White,
                        activeTrackColor = PrimaryBlue,
                        inactiveTrackColor = Color(0xFFE8E8E8)
                    ),
                    thumb = {
                        Box(
                            modifier = Modifier
                                .size(24.dp)
                                .shadow(
                                    elevation = 4.dp,
                                    shape = CircleShape,
                                    spotColor = Color.Black.copy(alpha = 0.15f)
                                )
                                .clip(CircleShape)
                                .background(Color.White)
                                .border(2.dp, PrimaryBlue, CircleShape)
                        )
                    }
                )
            }

            // 温度范围标签
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "16°C",
                    style = MaterialTheme.typography.bodySmall,
                    color = TextTertiaryLight,
                    fontSize = 12.sp
                )
                Text(
                    text = "30°C",
                    style = MaterialTheme.typography.bodySmall,
                    color = TextTertiaryLight,
                    fontSize = 12.sp
                )
            }
        }
    }
}

/**
 * 湿度设定卡片 - 像素级还原设计图
 *
 * 性能优化：
 * 1. 使用 remember 缓存本地滑块状态，避免拖动时触发父组件重组
 * 2. 使用 interactionSource 监听滑块交互状态
 * 3. 只有在滑块拖动结束时才触发 onHumidityChange 回调
 */
@Composable
private fun HumiditySettingCard(
    humidity: Float,
    onHumidityChange: (Float) -> Unit
) {
    // 使用 remember 缓存本地滑块值，避免拖动时触发父组件重组
    var localHumidity by remember(humidity) { mutableStateOf(humidity) }
    // 使用 rememberUpdatedState 确保回调始终引用最新值
    val currentOnChange by rememberUpdatedState(onHumidityChange)
    // 监听滑块交互状态
    val interactionSource = remember { MutableInteractionSource() }
    val isDragging by interactionSource.collectIsDraggedAsState()

    // 当拖动结束时，触发回调
    LaunchedEffect(isDragging) {
        if (!isDragging) {
            // 拖动结束，如果值有变化则触发回调
            if (localHumidity != humidity) {
                currentOnChange(localHumidity)
            }
        }
    }

    // 当外部 humidity 变化时（非拖动状态），更新本地值
    LaunchedEffect(humidity) {
        if (!isDragging) {
            localHumidity = humidity
        }
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(
                elevation = 2.dp,
                shape = RoundedCornerShape(16.dp),
                spotColor = Color.Black.copy(alpha = 0.06f)
            )
            .clip(RoundedCornerShape(16.dp))
            .background(Color.White)
            .padding(16.dp)
    ) {
        Column {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "全屋湿度设定",
                    style = MaterialTheme.typography.bodyMedium,
                    color = TextPrimaryLight,
                    fontSize = 14.sp
                )
                Text(
                    text = "${localHumidity.toInt()}%",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = PrimaryBlue,
                    fontSize = 16.sp
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // 滑块 - 像素级还原
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Slider(
                    value = localHumidity,
                    onValueChange = { localHumidity = it },
                    valueRange = 30f..80f,
                    modifier = Modifier.fillMaxWidth(),
                    interactionSource = interactionSource,
                    colors = SliderDefaults.colors(
                        thumbColor = Color.White,
                        activeTrackColor = PrimaryBlue,
                        inactiveTrackColor = Color(0xFFE8E8E8)
                    ),
                    thumb = {
                        Box(
                            modifier = Modifier
                                .size(20.dp)
                                .shadow(
                                    elevation = 3.dp,
                                    shape = CircleShape,
                                    spotColor = Color.Black.copy(alpha = 0.15f)
                                )
                                .clip(CircleShape)
                                .background(Color.White)
                                .border(2.dp, PrimaryBlue, CircleShape)
                        )
                    }
                )
            }
        }
    }
}

/**
 * 楼层Chip选择器 - 横向滚动的芯片选择器
 */
@Composable
private fun FloorChipSelector(
    floors: List<FloorItem>,
    selectedFloorId: String?,
    onFloorSelected: (String) -> Unit
) {
    androidx.compose.foundation.lazy.LazyRow(
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        items(floors.size) { index ->
            val floor = floors[index]
            val isSelected = floor.id == selectedFloorId
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(20.dp))
                    .background(if (isSelected) PrimaryBlue else Color.White)
                    .border(
                        width = 1.dp,
                        color = if (isSelected) PrimaryBlue else Color(0xFFE0E0E0),
                        shape = RoundedCornerShape(20.dp)
                    )
                    .clickable { onFloorSelected(floor.id) }
                    .padding(horizontal = 18.dp, vertical = 8.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = floor.name,
                        style = MaterialTheme.typography.bodyMedium,
                        color = if (isSelected) Color.White else TextPrimaryLight,
                        fontWeight = if (isSelected) FontWeight.Medium else FontWeight.Normal,
                        fontSize = 14.sp
                    )
                    if (floor.isMainControl) {
                        Spacer(modifier = Modifier.width(4.dp))
                        Box(
                            modifier = Modifier
                                .clip(CircleShape)
                                .background(
                                    if (isSelected) Color.White.copy(alpha = 0.3f)
                                    else PrimaryBlue.copy(alpha = 0.12f)
                                )
                                .padding(horizontal = 6.dp, vertical = 2.dp)
                        ) {
                            Text(
                                text = "主控",
                                style = MaterialTheme.typography.bodySmall,
                                color = if (isSelected) Color.White else PrimaryBlue,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }
                }
            }
        }
    }
}

/**
 * 房间卡片 - 显示房间温度/湿度/设备控制
 */
@Composable
private fun RoomCard(room: RoomUiItem) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(
                elevation = 2.dp,
                shape = RoundedCornerShape(16.dp),
                spotColor = Color.Black.copy(alpha = 0.06f)
            )
            .clip(RoundedCornerShape(16.dp))
            .background(Color.White)
            .padding(16.dp)
    ) {
        Column {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(8.dp)
                            .clip(CircleShape)
                            .background(if (room.isOnline) SuccessGreen else Color(0xFFCCCCCC))
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = room.name,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold,
                        color = TextPrimaryLight,
                        fontSize = 16.sp
                    )
                }
                if (room.roomType.isNotEmpty()) {
                    Text(
                        text = when (room.roomType) {
                            "living" -> "客厅"
                            "bedroom" -> "卧室"
                            "kitchen" -> "厨房"
                            "study" -> "书房"
                            "dining" -> "餐厅"
                            "bathroom" -> "浴室"
                            else -> room.roomType
                        },
                        style = MaterialTheme.typography.bodySmall,
                        color = TextTertiaryLight,
                        fontSize = 12.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                RoomStatItem(
                    label = "面积",
                    value = "${room.area}m²",
                    color = TextSecondaryLight
                )
                RoomStatItem(
                    label = "设备",
                    value = "${room.deviceCount}台",
                    color = PrimaryBlue
                )
                RoomStatItem(
                    label = "状态",
                    value = if (room.isOnline) "在线" else "离线",
                    color = if (room.isOnline) SuccessGreen else TextTertiaryLight
                )
            }

            if (room.area.isNotEmpty() && room.area.toFloatOrNull() != null) {
                Spacer(modifier = Modifier.height(12.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp)
                        .background(DividerLight)
                )
                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "温控预设",
                        style = MaterialTheme.typography.bodySmall,
                        color = TextSecondaryLight,
                        fontSize = 13.sp
                    )
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        listOf("16°", "20°", "24°", "28°").forEach { preset ->
                            val isPresetSelected = preset == "${room.targetTemp.toInt()}°"
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(12.dp))
                                    .background(
                                        if (isPresetSelected) PrimaryBlue.copy(alpha = 0.1f)
                                        else Color(0xFFF5F5F5)
                                    )
                                    .border(
                                        width = 1.dp,
                                        color = if (isPresetSelected) PrimaryBlue else Color.Transparent,
                                        shape = RoundedCornerShape(12.dp)
                                    )
                                    .clickable { }
                                    .padding(horizontal = 10.dp, vertical = 4.dp)
                            ) {
                                Text(
                                    text = preset,
                                    style = MaterialTheme.typography.bodySmall,
                                    color = if (isPresetSelected) PrimaryBlue else TextSecondaryLight,
                                    fontSize = 12.sp,
                                    fontWeight = if (isPresetSelected) FontWeight.Medium else FontWeight.Normal
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun RoomStatItem(
    label: String,
    value: String,
    color: Color
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = value,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Medium,
            color = color,
            fontSize = 15.sp
        )
        Spacer(modifier = Modifier.height(2.dp))
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall,
            color = TextTertiaryLight,
            fontSize = 11.sp
        )
    }
}
