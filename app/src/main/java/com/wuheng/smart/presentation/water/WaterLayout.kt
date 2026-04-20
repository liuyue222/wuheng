package com.wuheng.smart.presentation.water

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.wuheng.smart.presentation.components.*
import com.wuheng.smart.presentation.theme.*

/**
 * 水系统页面布局 - 纯UI组件
 *
 * 布局结构分析：
 * 1. 生活热水循环卡片：全天循环、定时循环、临时循环、关闭循环
 * 2. 热力杀菌卡片：预约时间显示
 * 3. 全屋净水滤芯卡片：前置过滤器、中央净水机、末端直饮状态
 */

// ==================== 主布局 ====================

@Composable
fun WaterLayout(
    uiState: WaterUiState,
    onHotWaterModeSelected: (HotWaterMode) -> Unit,
    onDurationClick: () -> Unit,
    onSterilizationEdit: () -> Unit,
    onFilterReplaceClick: () -> Unit,
    modifier: Modifier = Modifier,
    maxWidth: Dp = 360.dp
) {
    val isWide = maxWidth >= 720.dp
    val horizontalPadding = if (isWide) page_margin_horizontal_wide else page_margin_horizontal

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(BackgroundLight),
        contentPadding = PaddingValues(horizontal = horizontalPadding, vertical = spacing_lg)
    ) {
        item { Spacer(modifier = Modifier.height(spacing_sm)) }

        // 热水循环卡片
        item {
            HotWaterCirculationCard(
                currentMode = uiState.hotWaterMode,
                onModeSelected = onHotWaterModeSelected,
                onDurationClick = onDurationClick
            )
        }

        item { Spacer(modifier = Modifier.height(spacing_lg)) }

        // 热力杀菌卡片
        item {
            SterilizationCard(
                scheduleTime = uiState.sterilizationSchedule,
                onEditClick = onSterilizationEdit
            )
        }

        item { Spacer(modifier = Modifier.height(spacing_lg)) }

        // 滤芯状态卡片
        item {
            FilterStatusCard(
                filters = uiState.filters,
                onReplaceClick = onFilterReplaceClick
            )
        }

        item { Spacer(modifier = Modifier.height(spacing_lg)) }
    }
}

// ==================== 热水循环卡片 ====================

@Composable
private fun HotWaterCirculationCard(
    currentMode: HotWaterMode,
    onModeSelected: (HotWaterMode) -> Unit,
    onDurationClick: () -> Unit
) {
    WuHengCard {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(card_padding_large)
        ) {
            // 标题行
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "生活热水循环",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = TextPrimaryLight
                )

                // 设置按钮
                TextButton(onClick = onDurationClick) {
                    Text(
                        text = "设置",
                        color = PrimaryBlue,
                        fontSize = text_body_medium_size
                    )
                }
            }

            Spacer(modifier = Modifier.height(spacing_md))

            // 模式选择网格
            val modes = listOf(
                HotWaterMode.ALL_DAY to "全天循环",
                HotWaterMode.TIMED to "定时循环",
                HotWaterMode.TEMPORARY to "临时循环",
                HotWaterMode.OFF to "关闭循环"
            )

            Column(verticalArrangement = Arrangement.spacedBy(spacing_sm)) {
                modes.chunked(2).forEach { rowModes ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(spacing_sm)
                    ) {
                        rowModes.forEach { (mode, label) ->
                            ModeButton(
                                label = label,
                                isSelected = currentMode == mode,
                                onClick = { onModeSelected(mode) },
                                modifier = Modifier.weight(1f)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun ModeButton(
    label: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val backgroundColor = if (isSelected) PrimaryBlue else SurfaceLight
    val textColor = if (isSelected) Color.White else TextPrimaryLight

    Box(
        modifier = modifier
            .height(44.dp)
            .clip(RoundedCornerShape(corner_sm))
            .background(backgroundColor)
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = label,
            color = textColor,
            fontSize = text_body_medium_size,
            fontWeight = if (isSelected) FontWeight.Medium else FontWeight.Normal
        )
    }
}

// ==================== 热力杀菌卡片 ====================

@Composable
private fun SterilizationCard(
    scheduleTime: String,
    onEditClick: () -> Unit
) {
    WuHengCard {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(card_padding_large)
                .clickable(onClick = onEditClick),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "热力杀菌",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = TextPrimaryLight
                )

                Spacer(modifier = Modifier.height(spacing_xs))

                Text(
                    text = "预约时间: $scheduleTime",
                    fontSize = text_body_medium_size,
                    color = TextSecondaryLight
                )
            }

            Icon(
                imageVector = Icons.Filled.KeyboardArrowRight,
                contentDescription = "编辑",
                tint = TextTertiaryLight
            )
        }
    }
}

// ==================== 滤芯状态卡片 ====================

@Composable
private fun FilterStatusCard(
    filters: List<FilterItem>,
    onReplaceClick: () -> Unit
) {
    WuHengCard {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(card_padding_large)
        ) {
            // 标题行
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "全屋净水滤芯",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = TextPrimaryLight
                )

                TextButton(onClick = onReplaceClick) {
                    Text(
                        text = "预约更换",
                        color = PrimaryBlue,
                        fontSize = text_body_medium_size
                    )
                }
            }

            Spacer(modifier = Modifier.height(spacing_md))

            // 滤芯列表
            Column(verticalArrangement = Arrangement.spacedBy(spacing_md)) {
                filters.forEach { filter ->
                    FilterItemRow(filter = filter)
                }
            }
        }
    }
}

@Composable
private fun FilterItemRow(filter: FilterItem) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(
                text = filter.name,
                fontSize = text_body_medium_size,
                color = TextPrimaryLight
            )

            Spacer(modifier = Modifier.height(spacing_xs))

            LinearProgressIndicator(
                progress = filter.progress,
                modifier = Modifier.width(120.dp),
                color = when (filter.status) {
                    FilterStatus.NORMAL -> SuccessGreen
                    FilterStatus.WARNING -> WarningYellow
                    FilterStatus.EXPIRED -> ErrorRed
                },
                trackColor = ProgressTrackBg
            )
        }

        // 状态标签
        val (statusText, statusColor) = when (filter.status) {
            FilterStatus.NORMAL -> "正常" to SuccessGreen
            FilterStatus.WARNING -> "需更换" to WarningYellow
            FilterStatus.EXPIRED -> "已过期" to ErrorRed
        }

        Text(
            text = statusText,
            fontSize = text_body_small_size,
            color = statusColor,
            fontWeight = FontWeight.Medium
        )
    }
}


