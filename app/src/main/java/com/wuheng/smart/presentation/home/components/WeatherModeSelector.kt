package com.wuheng.smart.presentation.home.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
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
import com.wuheng.smart.data.model.WeatherMode
import com.wuheng.smart.presentation.theme.*

/**
 * 天气模式选择器组件 - 像素级还原第二版设计图
 *
 * 设计规范要点 (基于Dimension.kt和Color.kt):
 * ❌ 未选中时：背景色完全透明 (Color.Transparent)
 * ✅ 选中时：白色背景 + elevation_lg阴影 + 白色图标文字
 *
 * 尺寸规范:
 * - 按钮高度：mode_button_height = 44.dp
 * - 图标尺寸：mode_button_icon_size = 18.dp
 * - 按钮间距：mode_button_gap = 12.dp
 * - 选中态背景：ChipSelectedBg (#FFFFFF)
 * - 选中态阴影：elevation_lg = 8.dp
 * - 未选中态背景：Color.Transparent （完全透明！）
 * - 文字样式：mode_button_text_size = 14.sp, Medium
 *
 * 布局结构分析:
 * - 外层: 圆角矩形玻璃拟态卡片 (corner_md = 16dp圆角)
 * - 内部: 横向排列的3个按钮（除湿/制冷、通风、供暖/加湿）
 * - 布局方式: 水平排列，图标在左、文字在右 (Row布局)
 *
 * 切图资源引用:
 *   - 雪花(1).png -> ic_snowflake (制冷模式)
 *   - 太阳.png -> ic_sun (制热/供暖加湿模式)
 *   - 风.png -> ic_wind (通风模式)
 */

data class WeatherModeSelectorUiState(
    val selectedMode: WeatherMode = WeatherMode.COOLING,
    val availableModes: List<WeatherMode> = listOf(
        WeatherMode.COOLING,
        WeatherMode.HEATING,
        WeatherMode.VENTILATION
    ), // 只保留3个模式，删除AUTO
    val isEnabled: Boolean = true
)

@Composable
fun WeatherModeSelector(
    uiState: WeatherModeSelectorUiState = WeatherModeSelectorUiState(),
    onModeSelected: (WeatherMode) -> Unit = {},
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .shadow(
                elevation = elevation_lg,  // 8.dp - 使用设计令牌
                shape = RoundedCornerShape(corner_md),  // 16.dp - 使用设计令牌
                ambientColor = ShadowLight.copy(alpha = 0.15f),
                spotColor = ShadowLight.copy(alpha = 0.25f)
            )
            .clip(RoundedCornerShape(corner_md))  // 16.dp - 使用设计令牌
            .background(GlassPanelBg)
            .padding(horizontal = mode_group_padding_h, vertical = spacing_sm)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(mode_button_gap),  // 12.dp - 使用设计令牌
            verticalAlignment = Alignment.CenterVertically
        ) {
            uiState.availableModes.forEach { mode ->
                ModeButtonHorizontal(
                    mode = mode,
                    isSelected = uiState.selectedMode == mode,
                    isEnabled = uiState.isEnabled,
                    onClick = { if (uiState.isEnabled) onModeSelected(mode) },
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

@Composable
private fun ModeButtonHorizontal(
    mode: WeatherMode,
    isSelected: Boolean,
    isEnabled: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    // 核心优化：使用设计令牌控制背景色（完全符合第二版设计图规范）
    val backgroundColor by animateColorAsState(
        targetValue = when {
            !isEnabled -> Color.LightGray.copy(alpha = 0.5f)
            isSelected -> ChipSelectedBg  // ✅ 选中状态：白色背景 #FFFFFF (来自设计令牌)
            else -> Color.Transparent  // ❌ 未选中状态：完全透明背景（关键修改！）
        },
        animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy),
        label = "backgroundColor"
    )

    val contentColor by animateColorAsState(
        targetValue = when {
            !isEnabled -> TextDisabledLight
            isSelected -> ChipSelectedText  // ✅ 选中状态：白色文字/图标（对比度保证）
            else -> mode.getColor()  // 未选中态：使用模式对应的颜色（蓝/橙/灰蓝）
        },
        animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy),
        label = "contentColor"
    )

    Box(modifier = modifier) {
        Row(
            modifier = Modifier
                .then(
                    if (isSelected) {
                        // ✅ 选中时添加阴影效果（elevation_lg + 自定义阴影颜色）
                        Modifier.shadow(
                            elevation = elevation_lg,  // 8.dp - 使用设计令牌
                            shape = RoundedCornerShape(corner_sm),  // 8.dp - 使用设计令牌
                            ambientColor = ShadowLight.copy(alpha = 0.2f),
                            spotColor = ShadowLight.copy(alpha = 0.35f)
                        )
                    } else {
                        // ❌ 未选中时无阴影，保持扁平化透明效果
                        Modifier
                    }
                )
                .clip(RoundedCornerShape(corner_sm))  // 8.dp - 使用设计令牌
                .background(backgroundColor)
                .clickable(enabled = isEnabled, onClick = onClick)
                .padding(horizontal = mode_button_padding_h, vertical = spacing_md)
                .fillMaxWidth()
                .heightIn(min = mode_button_height),  // 44.dp - 确保最小触摸区域
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(mode_button_icon_text_spacing)  // 6.dp - 使用设计令牌
        ) {
            // 左侧图标 - 使用切图资源
            val iconRes = when (mode) {
                WeatherMode.COOLING -> R.drawable.ic_snowflake
                WeatherMode.HEATING -> R.drawable.ic_sun
                WeatherMode.VENTILATION -> R.drawable.ic_wind
                else -> R.drawable.ic_snowflake
            }
            Image(
                painter = painterResource(id = iconRes),
                contentDescription = mode.getDisplayName(),
                modifier = Modifier.size(mode_button_icon_size),  // 18.dp - 使用设计令牌
                colorFilter = ColorFilter.tint(contentColor)
            )

            // 右侧文字 - 使用设计令牌中的模式名称和字号
            Text(
                text = mode.getDisplayName(),
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = if (isSelected) FontWeight.Medium else FontWeight.Normal,  // Medium字重
                color = contentColor,
                fontSize = mode_button_text_size  // 14.sp - 使用设计令牌
            )
        }
    }
}

/**
 * 获取模式的显示名称（基于第二版设计图）
 */
fun WeatherMode.getDisplayName(): String = when (this) {
    WeatherMode.COOLING -> "除湿/制冷"  // 设计图：❄ 除湿/制冷
    WeatherMode.HEATING -> "供暖/加湿"  // 设计图：☀ 供暖/加湿
    WeatherMode.VENTILATION -> "通风"   // 设计图：≡ 通风
    WeatherMode.AUTO -> "自动"
}

/**
 * 获取模式对应的颜色
 */
fun WeatherMode.getColor(): Color = when (this) {
    WeatherMode.COOLING -> CoolingBlue
    WeatherMode.HEATING -> HeatingOrange
    WeatherMode.VENTILATION -> VentilationTeal
    WeatherMode.AUTO -> TextSecondaryLight
}

@Preview(showBackground = true, name = "天气模式选择器-制冷", backgroundColor = 0xFFF1F5F9)
@Composable
fun WeatherModeSelectorCoolingPreview() {
    WuHengTheme {
        WeatherModeSelector(
            uiState = WeatherModeSelectorUiState(selectedMode = WeatherMode.COOLING)
        )
    }
}

@Preview(showBackground = true, name = "天气模式选择器-制热", backgroundColor = 0xFFF1F5F9)
@Composable
fun WeatherModeSelectorHeatingPreview() {
    WuHengTheme {
        WeatherModeSelector(
            uiState = WeatherModeSelectorUiState(selectedMode = WeatherMode.HEATING)
        )
    }
}

@Preview(showBackground = true, name = "天气模式选择器-禁用", backgroundColor = 0xFFF1F5F9)
@Composable
fun WeatherModeSelectorDisabledPreview() {
    WuHengTheme {
        WeatherModeSelector(
            uiState = WeatherModeSelectorUiState(isEnabled = false)
        )
    }
}
