@file:OptIn(ExperimentalMaterial3Api::class)

package com.wuheng.smart.presentation.climate

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.wuheng.smart.presentation.components.*
import com.wuheng.smart.presentation.theme.*

/**
 * 冷暖舒适页面布局 - 纯UI组件
 *
 * 布局结构分析：
 * 1. Tab切换：全屋 / 楼层
 * 2. 全屋模式：温度设定滑块(16-30°C)、湿度设定滑块
 * 3. 楼层模式：楼层列表，每个楼层显示设备状态
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
 * Tab选择器
 */
@Composable
private fun ClimateTabSelector(
    selectedTab: ClimateTab,
    onTabSelected: (ClimateTab) -> Unit
) {
    WuHengCard {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(spacing_sm),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            ClimateTabItem(
                icon = Icons.Default.Home,
                label = "全屋",
                selected = selectedTab == ClimateTab.WHOLE_HOUSE,
                onClick = { onTabSelected(ClimateTab.WHOLE_HOUSE) }
            )
            ClimateTabItem(
                icon = Icons.Default.List,
                label = "楼层",
                selected = selectedTab == ClimateTab.FLOOR,
                onClick = { onTabSelected(ClimateTab.FLOOR) }
            )
        }
    }
}

/**
 * Tab项
 */
@Composable
private fun ClimateTabItem(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    label: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .clip(RoundedCornerShape(corner_sm))
            .clickable(onClick = onClick)
            .padding(horizontal = spacing_lg, vertical = spacing_sm)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            modifier = Modifier.size(icon_size_default),
            tint = if (selected) PrimaryBlue else TextSecondaryLight
        )
        Spacer(modifier = Modifier.width(spacing_xs))
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            color = if (selected) PrimaryBlue else TextSecondaryLight,
            fontWeight = if (selected) FontWeight.Medium else FontWeight.Normal
        )
    }
}

/**
 * 温度设定卡片
 */
@Composable
private fun TemperatureSettingCard(
    temperature: Float,
    onTemperatureChange: (Float) -> Unit
) {
    WuHengCard(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(card_padding_large),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "全屋温度设定",
                style = MaterialTheme.typography.bodyMedium,
                color = TextSecondaryLight
            )

            Spacer(modifier = Modifier.height(spacing_md))

            // 温度显示
            Row(
                verticalAlignment = Alignment.Top
            ) {
                Text(
                    text = String.format("%.1f", temperature),
                    style = TemperatureDisplayStyle,
                    color = TextPrimaryLight
                )
                Text(
                    text = "°",
                    style = UnitLargeTextStyle,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }

            Spacer(modifier = Modifier.height(spacing_lg))

            // 滑块
            Slider(
                value = temperature,
                onValueChange = onTemperatureChange,
                valueRange = 16f..30f,
                modifier = Modifier.fillMaxWidth(),
                colors = SliderDefaults.colors(
                    thumbColor = Color.White,
                    activeTrackColor = PrimaryBlue,
                    inactiveTrackColor = SliderInactive
                ),
                thumb = {
                    Box(
                        modifier = Modifier
                            .size(24.dp)
                            .clip(CircleShape)
                            .background(Color.White)
                            .border(2.dp, PrimaryBlue, CircleShape)
                    )
                }
            )

            // 温度范围标签
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "16°C",
                    style = MaterialTheme.typography.bodySmall,
                    color = TextTertiaryLight
                )
                Text(
                    text = "30°C",
                    style = MaterialTheme.typography.bodySmall,
                    color = TextTertiaryLight
                )
            }
        }
    }
}

/**
 * 湿度设定卡片
 */
@Composable
private fun HumiditySettingCard(
    humidity: Float,
    onHumidityChange: (Float) -> Unit
) {
    WuHengCard(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(card_padding_default)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "全屋湿度设定",
                    style = MaterialTheme.typography.bodyMedium,
                    color = TextPrimaryLight
                )
                Text(
                    text = "${humidity.toInt()}%",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Medium,
                    color = PrimaryBlue
                )
            }

            Spacer(modifier = Modifier.height(spacing_md))

            // 滑块
            Slider(
                value = humidity,
                onValueChange = onHumidityChange,
                valueRange = 30f..80f,
                modifier = Modifier.fillMaxWidth(),
                colors = SliderDefaults.colors(
                    thumbColor = Color.White,
                    activeTrackColor = PrimaryBlue,
                    inactiveTrackColor = SliderInactive
                ),
                thumb = {
                    Box(
                        modifier = Modifier
                            .size(20.dp)
                            .clip(CircleShape)
                            .background(Color.White)
                            .border(2.dp, PrimaryBlue, CircleShape)
                    )
                }
            )
        }
    }
}

/**
 * 楼层卡片
 */
@Composable
private fun FloorCard(
    floor: FloorItem,
    onToggle: (String) -> Unit,
    onClick: (String) -> Unit
) {
    WuHengCard(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick(floor.id) }
    ) {
        Column(
            modifier = Modifier.padding(card_padding_default)
        ) {
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
                    color = TextPrimaryLight
                )

                // 开关
                Switch(
                    checked = floor.isEnabled,
                    onCheckedChange = { onToggle(floor.id) },
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = Color.White,
                        checkedTrackColor = SwitchChecked,
                        uncheckedThumbColor = Color.White,
                        uncheckedTrackColor = SwitchUnchecked
                    )
                )
            }

            // 设备状态
            if (floor.devices.isNotEmpty()) {
                Spacer(modifier = Modifier.height(spacing_md))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(spacing_lg)
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
            color = TextSecondaryLight
        )
        Spacer(modifier = Modifier.width(spacing_sm))
        Text(
            text = device.value ?: device.status,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Medium,
            color = if (device.status == "开启" || device.status == "运行中") PrimaryBlue else TextSecondaryLight
        )
    }
}
