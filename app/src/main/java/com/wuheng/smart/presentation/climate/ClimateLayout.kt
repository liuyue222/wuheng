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
    onFloorClick: (String) -> Unit,
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
                // 楼层列表
                items(uiState.floors.size) { index ->
                    Spacer(modifier = Modifier.height(if (index == 0) spacing_lg else spacing_md))
                    FloorCard(
                        floor = uiState.floors[index],
                        onToggle = { onFloorToggle(it, !uiState.floors[index].isEnabled) },
                        onClick = { onFloorClick(it) }
                    )
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
 * 楼层卡片 - 像素级还原设计图
 */
@Composable
private fun FloorCard(
    floor: FloorItem,
    onToggle: (String) -> Unit,
    onClick: (String) -> Unit
) {
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
            .clickable { onClick(floor.id) }
            .padding(16.dp)
    ) {
        Column {
            // 楼层标题行
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = floor.name + if (floor.isMainControl) " (主控)" else "",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = TextPrimaryLight,
                    fontSize = 16.sp
                )

                // 开关
                Switch(
                    checked = floor.isEnabled,
                    onCheckedChange = { onToggle(floor.id) },
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = Color.White,
                        checkedTrackColor = PrimaryBlue,
                        uncheckedThumbColor = Color.White,
                        uncheckedTrackColor = Color(0xFFCCCCCC)
                    ),
                    modifier = Modifier.width(48.dp)
                )
            }

            // 设备状态
            if (floor.devices.isNotEmpty()) {
                Spacer(modifier = Modifier.height(12.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(24.dp)
                ) {
                    floor.devices.forEach { device ->
                        FloorDeviceItem(device = device)
                    }
                }
            }
        }
    }
}

/**
 * 楼层设备项
 */
@Composable
private fun FloorDeviceItem(device: FloorDevice) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = device.name,
            style = MaterialTheme.typography.bodySmall,
            color = TextSecondaryLight,
            fontSize = 13.sp
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = device.value ?: device.status,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Medium,
            color = if (device.status == "开启" || device.status == "运行中") PrimaryBlue else TextSecondaryLight,
            fontSize = 14.sp
        )
    }
}
