package com.wuheng.smart.presentation.home.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.wuheng.smart.R
import com.wuheng.smart.data.model.DeviceType
import com.wuheng.smart.presentation.theme.*

/**
 * 设备控制卡片组件
 *
 * 布局结构分析（基于设计图）:
 * - 外层: 圆角矩形玻璃拟态卡片 (16dp圆角)
 * - 顶部区域: 设备图标 + 设备名称 + 开关状态
 * - 中间区域: 温度显示 + 模式图标(太阳/雪花)
 * - 底部区域: 自定义开关控件 (参考kaiguan-guan-3.png样式)
 *
 * 切图资源引用:
 *   - kongtiao.png -> ic_air_conditioner (空调图标)
 *   - kaiguan-guan-3.png -> ic_switch_on (开关开启态)
 *   - 太阳.png -> ic_sun (制热/日间模式)
 *   - 雪花(1).png -> ic_snowflake (制冷模式)
 */

data class DeviceCardUiState(
    val deviceId: String = "",
    val deviceName: String = "客厅空调",
    val deviceType: DeviceType = DeviceType.CLIMATE,
    val isPoweredOn: Boolean = true,
    val currentTemp: Float = 24.5f,
    val targetTemp: Float = 24.0f,
    val isCoolingMode: Boolean = true, // true=制冷, false=制热
    val roomName: String = "客厅",
    val isOnline: Boolean = true
)

@Composable
fun DeviceCard(
    uiState: DeviceCardUiState,
    onPowerToggle: (Boolean) -> Unit = {},
    onTempChange: (Float) -> Unit = {},
    onModeToggle: () -> Unit = {},
    onCardClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    val cardBackgroundColor by animateColorAsState(
        targetValue = if (uiState.isPoweredOn) GlassPanelBg else Color.White.copy(alpha = 0.6f),
        animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy),
        label = "cardBackground"
    )

    val iconBackgroundColor = when {
        !uiState.isOnline -> TextDisabledLight
        uiState.isPoweredOn && uiState.isCoolingMode -> CoolingBlue.copy(alpha = 0.12f)
        uiState.isPoweredOn && !uiState.isCoolingMode -> HeatingOrange.copy(alpha = 0.12f)
        else -> DividerLight
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .shadow(
                elevation = if (uiState.isPoweredOn) 6.dp else 3.dp,
                shape = RoundedCornerShape(16.dp),
                ambientColor = ShadowLight.copy(alpha = 0.4f),
                spotColor = ShadowLight.copy(alpha = 0.4f)
            )
            .clip(RoundedCornerShape(16.dp))
            .background(cardBackgroundColor)
            .clickable { onCardClick() }
            .padding(20.dp)
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
            // 顶部：设备信息行
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    // 设备图标容器
                    Box(
                        modifier = Modifier
                            .size(44.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(iconBackgroundColor),
                        contentAlignment = Alignment.Center
                    ) {
                        val iconRes = when (uiState.deviceType) {
                            DeviceType.CLIMATE -> R.drawable.ic_snowflake
                            DeviceType.WATER -> R.drawable.ic_scene_eco
                            else -> R.drawable.ic_snowflake
                        }
                        val iconTint = when {
                            !uiState.isOnline -> TextDisabledLight
                            uiState.isPoweredOn && uiState.isCoolingMode -> CoolingBlue
                            uiState.isPoweredOn && !uiState.isCoolingMode -> HeatingOrange
                            else -> TextTertiaryLight
                        }
                        Image(
                            painter = painterResource(id = iconRes),
                            contentDescription = uiState.deviceName,
                            modifier = Modifier.size(24.dp),
                            colorFilter = ColorFilter.tint(iconTint)
                        )
                    }
                    Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                        Text(
                            text = uiState.deviceName,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = TextPrimaryLight,
                            fontSize = 16.sp
                        )
                        Text(
                            text = uiState.roomName,
                            style = MaterialTheme.typography.bodySmall,
                            color = TextTertiaryLight,
                            fontSize = 12.sp
                        )
                    }
                }
                // 自定义开关控件
                CustomSwitch(
                    isChecked = uiState.isPoweredOn,
                    onCheckedChange = onPowerToggle,
                    enabled = uiState.isOnline
                )
            }

            // 中间：温度显示区域
            if (uiState.isPoweredOn && uiState.isOnline) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(14.dp))
                        .background(if (uiState.isCoolingMode) CoolingBlue.copy(alpha = 0.06f) else HeatingOrange.copy(alpha = 0.06f))
                        .padding(horizontal = 20.dp, vertical = 18.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                            Text(
                                text = "当前温度",
                                style = MaterialTheme.typography.bodySmall,
                                color = TextSecondaryLight,
                                fontSize = 12.sp
                            )
                            Row(verticalAlignment = Alignment.Bottom, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                                Text(
                                    text = String.format("%.1f", uiState.currentTemp),
                                    style = MaterialTheme.typography.headlineMedium,
                                    fontWeight = FontWeight.Light,
                                    color = if (uiState.isCoolingMode) CoolingBlue else HeatingOrange,
                                    fontSize = 32.sp
                                )
                                Text(
                                    text = "°C",
                                    style = MaterialTheme.typography.bodyLarge,
                                    color = if (uiState.isCoolingMode) CoolingBlue else HeatingOrange,
                                    fontSize = 18.sp,
                                    modifier = Modifier.padding(bottom = 6.dp)
                                )
                            }
                        }
                        // 模式切换按钮
                        Box(
                            modifier = Modifier
                                .size(48.dp)
                                .clip(CircleShape)
                                .background(if (uiState.isCoolingMode) CoolingBlue.copy(alpha = 0.15f) else HeatingOrange.copy(alpha = 0.15f))
                                .clickable { onModeToggle() },
                            contentAlignment = Alignment.Center
                        ) {
                            Image(
                                painter = painterResource(
                                    id = if (uiState.isCoolingMode) R.drawable.ic_snowflake else R.drawable.ic_sun
                                ),
                                contentDescription = if (uiState.isCoolingMode) "制冷模式" else "制热模式",
                                modifier = Modifier.size(26.dp),
                                colorFilter = ColorFilter.tint(
                                    if (uiState.isCoolingMode) CoolingBlue else HeatingOrange
                                )
                            )
                        }
                    }
                }
            } else {
                // 关闭状态占位
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(80.dp)
                        .clip(RoundedCornerShape(14.dp))
                        .background(DividerLight.copy(alpha = 0.3f)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = if (!uiState.isOnline) "设备离线" else "已关闭",
                        style = MaterialTheme.typography.bodyLarge,
                        color = TextTertiaryLight,
                        fontSize = 15.sp
                    )
                }
            }
        }
    }
}

/**
 * 自定义开关组件
 * 样式参考：kaiguan-guan-3.png (开启状态为蓝色圆形滑块)
 */
@Composable
private fun CustomSwitch(
    isChecked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    enabled: Boolean = true,
    modifier: Modifier = Modifier
) {
    val trackColor by animateColorAsState(
        targetValue = when {
            isChecked -> SwitchChecked
            else -> SwitchUnchecked
        },
        animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy),
        label = "trackColor"
    )

    val thumbOffset by animateDpAsState(
        targetValue = if (isChecked) 24.dp else 0.dp,
        animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy),
        label = "thumbOffset"
    )

    Box(
        modifier = modifier
            .width(52.dp)
            .height(30.dp)
            .clip(RoundedCornerShape(15.dp))
            .background(trackColor.copy(alpha = if (enabled) 1f else 0.5f))
            .clickable(enabled = enabled) { onCheckedChange(!isChecked) },
        contentAlignment = Alignment.CenterStart
    ) {
        Box(
            modifier = Modifier
                .size(26.dp)
                .offset(x = thumbOffset)
                .shadow(
                    elevation = 2.dp,
                    shape = CircleShape,
                    ambientColor = ShadowLight,
                    spotColor = ShadowLight
                )
                .clip(CircleShape)
                .background(Color.White)
        )
    }
}

/**
 * 设备卡片列表项（用于LazyColumn）
 */
@Composable
fun DeviceListItem(
    uiState: DeviceCardUiState,
    onPowerToggle: (String, Boolean) -> Unit = { _, _ -> },
    onCardClick: (String) -> Unit = {},
    modifier: Modifier = Modifier
) {
    DeviceCard(
        uiState = uiState,
        onPowerToggle = { onPowerToggle(uiState.deviceId, it) },
        onCardClick = { onCardClick(uiState.deviceId) },
        modifier = modifier
    )
}

@Preview(showBackground = true, name = "设备卡片-制冷开启", backgroundColor = 0xFFF1F5F9)
@Composable
fun DeviceCardCoolingPreview() {
    WuHengTheme {
        DeviceCard(
            uiState = DeviceCardUiState(
                deviceName = "客厅空调",
                deviceType = DeviceType.CLIMATE,
                isPoweredOn = true,
                currentTemp = 24.5f,
                isCoolingMode = true,
                roomName = "客厅"
            )
        )
    }
}

@Preview(showBackground = true, name = "设备卡片-制热开启", backgroundColor = 0xFFF1F5F9)
@Composable
fun DeviceCardHeatingPreview() {
    WuHengTheme {
        DeviceCard(
            uiState = DeviceCardUiState(
                deviceName = "地暖系统",
                deviceType = DeviceType.CLIMATE,
                isPoweredOn = true,
                currentTemp = 28.0f,
                isCoolingMode = false,
                roomName = "主卧"
            )
        )
    }
}

@Preview(showBackground = true, name = "设备卡片-关闭", backgroundColor = 0xFFF1F5F9)
@Composable
fun DeviceCardOffPreview() {
    WuHengTheme {
        DeviceCard(
            uiState = DeviceCardUiState(
                deviceName = "新风系统",
                deviceType = DeviceType.CLIMATE,
                isPoweredOn = false,
                currentTemp = 22.0f,
                isCoolingMode = true,
                roomName = "书房"
            )
        )
    }
}
