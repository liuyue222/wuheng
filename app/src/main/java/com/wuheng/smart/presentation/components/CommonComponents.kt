package com.wuheng.smart.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.wuheng.smart.presentation.theme.*

// ==================== 卡片组件 ====================

/**
 * 标准卡片容器
 * 用于：内容卡片、列表项
 */
@Composable
fun WuHengCard(
    modifier: Modifier = Modifier,
    backgroundColor: Color = SurfaceLight,
    cornerRadius: Dp = corner_default,
    elevation: Dp = elevation_sm,
    shadowColor: Color = ShadowLight,
    content: @Composable ColumnScope.() -> Unit
) {
    Box(
        modifier = modifier
            .shadow(
                elevation = elevation,
                shape = RoundedCornerShape(cornerRadius),
                ambientColor = shadowColor,
                spotColor = shadowColor
            )
            .clip(RoundedCornerShape(cornerRadius))
            .background(backgroundColor)
    ) {
        Column(content = content)
    }
}

/**
 * 渐变背景卡片
 * 用于：首页当前住宅卡片
 */
@Composable
fun GradientCard(
    modifier: Modifier = Modifier,
    gradient: Brush = Brush.linearGradient(
        colors = listOf(PrimaryBlueGradientStart, PrimaryBlueGradientEnd)
    ),
    cornerRadius: Dp = corner_lg,
    content: @Composable ColumnScope.() -> Unit
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(cornerRadius))
            .background(gradient)
    ) {
        Column(content = content)
    }
}

// ==================== 按钮组件 ====================

/**
 * 主按钮
 * 用于：主要操作
 */
@Composable
fun PrimaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    icon: ImageVector? = null
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .height(button_height_lg),
        enabled = enabled,
        shape = ButtonShape,
        colors = ButtonDefaults.buttonColors(
            containerColor = ButtonPrimaryBg,
            disabledContainerColor = ButtonDisabledBg
        )
    ) {
        if (icon != null) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier.size(icon_size_default)
            )
            Spacer(modifier = Modifier.width(spacing_sm))
        }
        Text(
            text = text,
            style = ButtonTextStyle
        )
    }
}

/**
 * 次按钮
 * 用于：次要操作
 */
@Composable
fun SecondaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true
) {
    OutlinedButton(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .height(button_height_default),
        enabled = enabled,
        shape = ButtonShape,
        colors = ButtonDefaults.outlinedButtonColors(
            contentColor = TextPrimaryLight
        ),
        border = androidx.compose.foundation.BorderStroke(1.dp, BorderLight)
    ) {
        Text(
            text = text,
            style = ButtonSmallTextStyle,
            color = if (enabled) TextPrimaryLight else ButtonDisabledText
        )
    }
}

/**
 * 小按钮
 * 用于：卡片内操作
 */
@Composable
fun SmallButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    isPrimary: Boolean = true
) {
    Box(
        modifier = modifier
            .clip(ButtonSmallShape)
            .background(if (isPrimary) ButtonPrimaryBg else ButtonSecondaryBg)
            .clickable(onClick = onClick)
            .padding(horizontal = spacing_md, vertical = spacing_xs),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            style = TagTextStyle,
            color = if (isPrimary) Color.White else ButtonSecondaryText
        )
    }
}

// ==================== Chip/Tag组件 ====================

/**
 * 选择标签
 * 用于：楼层选择、区域选择
 */
@Composable
fun SelectableChip(
    text: String,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .clip(ChipShape)
            .background(if (selected) ChipSelectedBg else Color.Transparent)
            .then(
                if (!selected) {
                    Modifier.border(1.dp, ChipUnselectedBorder, ChipShape)
                } else Modifier
            )
            .clickable(onClick = onClick)
            .padding(horizontal = chip_padding_horizontal, vertical = spacing_sm),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            style = TagTextStyle,
            color = if (selected) ChipSelectedText else ChipUnselectedText
        )
    }
}

/**
 * 状态标签
 * 用于：显示状态信息
 */
@Composable
fun StatusTag(
    text: String,
    status: TagStatus,
    modifier: Modifier = Modifier
) {
    val (backgroundColor, textColor) = when (status) {
        TagStatus.NORMAL -> SuccessGreen to Color.White
        TagStatus.WARNING -> WarningYellow to Color.White
        TagStatus.ERROR -> ErrorRed to Color.White
        TagStatus.INFO -> PrimaryBlue to Color.White
        TagStatus.DEFAULT -> SurfaceVariantLight to TextPrimaryLight
    }

    Box(
        modifier = modifier
            .clip(ChipSmallShape)
            .background(backgroundColor)
            .padding(horizontal = spacing_md, vertical = spacing_xs),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            style = TagTextStyle,
            color = textColor
        )
    }
}

enum class TagStatus {
    NORMAL, WARNING, ERROR, INFO, DEFAULT
}

// ==================== 列表项组件 ====================

/**
 * 可点击列表项
 * 用于：设置项、导航项
 */
@Composable
fun ClickableListItem(
    title: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    subtitle: String? = null,
    leadingIcon: ImageVector? = null,
    trailingText: String? = null,
    showArrow: Boolean = true
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(horizontal = spacing_default, vertical = spacing_md),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (leadingIcon != null) {
            Icon(
                imageVector = leadingIcon,
                contentDescription = null,
                modifier = Modifier.size(icon_size_md),
                tint = PrimaryBlue
            )
            Spacer(modifier = Modifier.width(spacing_md))
        }

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                style = ListItemTitleStyle
            )
            if (subtitle != null) {
                Text(
                    text = subtitle,
                    style = ListItemDescStyle,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }

        if (trailingText != null) {
            Text(
                text = trailingText,
                style = StatusTextStyle,
                color = TextSecondaryLight
            )
            Spacer(modifier = Modifier.width(spacing_sm))
        }

        if (showArrow) {
            Icon(
                imageVector = Icons.Filled.KeyboardArrowRight,
                contentDescription = null,
                modifier = Modifier.size(icon_size_sm),
                tint = TextTertiaryLight
            )
        }
    }
}

/**
 * 信息列表项
 * 用于：显示键值对信息
 */
@Composable
fun InfoListItem(
    label: String,
    value: String,
    modifier: Modifier = Modifier,
    valueColor: Color = TextPrimaryLight
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = spacing_default, vertical = spacing_sm),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            color = TextSecondaryLight
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Medium,
            color = valueColor
        )
    }
}

// ==================== 进度条组件 ====================

/**
 * 带标签的进度条
 * 用于：滤芯进度、耗材进度
 */
@Composable
fun LabeledProgressBar(
    label: String,
    progress: Float,
    modifier: Modifier = Modifier,
    statusText: String? = null,
    statusColor: Color = TextSecondaryLight
) {
    Column(modifier = modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = label,
                style = MaterialTheme.typography.bodyMedium,
                color = TextPrimaryLight
            )
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "${(progress * 100).toInt()}%",
                    style = ProgressPercentStyle,
                    color = TextPrimaryLight
                )
                if (statusText != null) {
                    Spacer(modifier = Modifier.width(spacing_sm))
                    Text(
                        text = statusText,
                        style = TagTextStyle,
                        color = statusColor
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(spacing_xs))
        LinearProgressIndicator(
            progress = progress,
            modifier = Modifier
                .fillMaxWidth()
                .height(progress_bar_height)
                .clip(ProgressTrackShape),
            color = when {
                progress > 0.7f -> ProgressFillNormal
                progress > 0.3f -> ProgressFillWarning
                else -> ProgressFillDanger
            },
            trackColor = ProgressTrackBg
        )
    }
}

// ==================== 图标组件 ====================

/**
 * 圆形图标容器
 * 用于：场景图标、功能图标
 */
@Composable
fun CircleIconContainer(
    icon: ImageVector,
    backgroundColor: Color,
    modifier: Modifier = Modifier,
    iconSize: Dp = icon_size_md,
    iconColor: Color = Color.White
) {
    Box(
        modifier = modifier
            .size(icon_size_2xl)
            .clip(CircleShape)
            .background(backgroundColor),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            modifier = Modifier.size(iconSize),
            tint = iconColor
        )
    }
}

/**
 * 圆角图标容器
 * 用于：小图标按钮
 */
@Composable
fun RoundedIconContainer(
    icon: ImageVector,
    backgroundColor: Color,
    modifier: Modifier = Modifier,
    iconSize: Dp = icon_size_default,
    iconColor: Color = Color.White
) {
    Box(
        modifier = modifier
            .size(40.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(backgroundColor),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            modifier = Modifier.size(iconSize),
            tint = iconColor
        )
    }
}

// ==================== 分隔线组件 ====================

/**
 * 标准分隔线
 */
@Composable
fun WuHengDivider(
    modifier: Modifier = Modifier,
    color: Color = DividerLight
) {
    androidx.compose.material3.Divider(
        modifier = modifier.fillMaxWidth(),
        thickness = divider_height,
        color = color
    )
}

/**
 * 内边距分隔线
 */
@Composable
fun PaddedDivider(
    modifier: Modifier = Modifier,
    horizontalPadding: Dp = spacing_default
) {
    WuHengDivider(
        modifier = modifier.padding(horizontal = horizontalPadding)
    )
}

// ==================== 加载状态组件 ====================

/**
 * 加载指示器
 */
@Composable
fun LoadingIndicator(
    modifier: Modifier = Modifier,
    text: String = "加载中..."
) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        CircularProgressIndicator(
            color = PrimaryBlue,
            modifier = Modifier.size(48.dp)
        )
        Spacer(modifier = Modifier.height(spacing_md))
        Text(
            text = text,
            style = MaterialTheme.typography.bodyMedium,
            color = TextSecondaryLight
        )
    }
}

/**
 * 错误重试视图
 */
@Composable
fun ErrorRetryView(
    message: String,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = message,
            style = MaterialTheme.typography.bodyLarge,
            color = TextSecondaryLight
        )
        Spacer(modifier = Modifier.height(spacing_lg))
        Button(
            onClick = onRetry,
            colors = ButtonDefaults.buttonColors(containerColor = PrimaryBlue)
        ) {
            Text("重试")
        }
    }
}

/**
 * 空数据视图
 */
@Composable
fun EmptyView(
    message: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = message,
            style = MaterialTheme.typography.bodyMedium,
            color = TextTertiaryLight
        )
    }
}

// ==================== 数值显示组件 ====================

/**
 * 带单位的数值显示
 * 用于：温度、湿度显示
 */
@Composable
fun ValueWithUnit(
    value: String,
    unit: String,
    modifier: Modifier = Modifier,
    valueStyle: TextStyle = EnvironmentValueStyle,
    unitStyle: TextStyle = UnitTextStyle,
    valueColor: Color = TextPrimaryLight
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.Bottom
    ) {
        Text(
            text = value,
            style = valueStyle,
            color = valueColor
        )
        Text(
            text = unit,
            style = unitStyle,
            modifier = Modifier.padding(bottom = 2.dp)
        )
    }
}

/**
 * 环境数据项
 * 用于：温度、湿度、CO2等环境数据显示
 */
@Composable
fun EnvironmentDataItem(
    label: String,
    value: String,
    unit: String,
    modifier: Modifier = Modifier,
    valueColor: Color = TextPrimaryLight
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall,
            color = TextTertiaryLight
        )
        Spacer(modifier = Modifier.height(spacing_xs))
        ValueWithUnit(
            value = value,
            unit = unit,
            valueColor = valueColor
        )
    }
}
