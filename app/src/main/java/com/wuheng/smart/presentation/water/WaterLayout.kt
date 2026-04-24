package com.wuheng.smart.presentation.water

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
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
                currentTemp = uiState.currentTemp,
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
    currentTemp: Int,
    onModeSelected: (HotWaterMode) -> Unit,
    onDurationClick: () -> Unit
) {
    // 使用remember缓存模式列表，避免每次重组都创建新的列表
    val modes = remember {
        listOf(
            HotWaterMode.ALL_DAY to "全天循环",
            HotWaterMode.TIMED to "定时循环",
            HotWaterMode.TEMPORARY to "临时循环",
            HotWaterMode.OFF to "关闭循环"
        )
    }

    // 使用remember优化状态计算，只在currentMode变化时重新计算
    val isTemporaryMode = remember(currentMode) {
        currentMode == HotWaterMode.TEMPORARY
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
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            // 标题行
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    // 热水图标
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                            .background(SecondaryOrange.copy(alpha = 0.1f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "",
                            fontSize = 20.sp,
                            color = SecondaryOrange
                        )
                    }
                    Text(
                        text = "生活热水循环",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold,
                        color = TextPrimaryLight,
                        fontSize = 16.sp
                    )
                }

                // 当前水温
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "当前水温 ",
                        style = MaterialTheme.typography.bodySmall,
                        color = TextSecondaryLight,
                        fontSize = 12.sp
                    )
                    Text(
                        text = "$currentTemp°C",
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Bold,
                        color = SecondaryOrange,
                        fontSize = 16.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // 模式选择网格 - 2x2布局
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                modes.chunked(2).forEach { rowModes ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        rowModes.forEach { (mode, label) ->
                            // 使用key来确保每个按钮的稳定性
                            val isSelected = currentMode == mode
                            // 缓存点击回调，避免每次重组都创建新的lambda
                            val onClick by remember(onModeSelected, mode) {
                                mutableStateOf<() -> Unit>({ onModeSelected(mode) })
                            }
                            ModeButton(
                                label = label,
                                isSelected = isSelected,
                                onClick = onClick,
                                modifier = Modifier.weight(1f)
                            )
                        }
                    }
                }
            }

            // 临时运行时长（仅在临时循环模式显示）
            if (isTemporaryMode) {
                Spacer(modifier = Modifier.height(12.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable(onClick = onDurationClick)
                        .padding(vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "临时运行时长",
                        style = MaterialTheme.typography.bodyMedium,
                        color = TextSecondaryLight,
                        fontSize = 14.sp
                    )
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "30 min",
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Medium,
                            color = SecondaryOrange,
                            fontSize = 14.sp
                        )
                        Icon(
                            imageVector = Icons.Default.KeyboardArrowRight,
                            contentDescription = null,
                            modifier = Modifier.size(20.dp),
                            tint = TextTertiaryLight
                        )
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
    val backgroundColor = if (isSelected) SecondaryOrange else Color.White
    val textColor = if (isSelected) Color.White else TextPrimaryLight
    val borderColor = if (isSelected) SecondaryOrange else DividerLight

    Box(
        modifier = modifier
            .height(44.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(backgroundColor)
            .border(
                width = 1.dp,
                color = borderColor,
                shape = RoundedCornerShape(8.dp)
            )
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = label,
            color = textColor,
            fontSize = 14.sp,
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
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // 杀菌图标
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(ErrorRed.copy(alpha = 0.1f)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "",
                        fontSize = 20.sp,
                        color = ErrorRed
                    )
                }
                Column {
                    Text(
                        text = "热力杀菌",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold,
                        color = TextPrimaryLight,
                        fontSize = 16.sp
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = "预约: $scheduleTime",
                        fontSize = 13.sp,
                        color = TextSecondaryLight
                    )
                }
            }

            // 修改按钮
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(16.dp))
                    .border(1.dp, DividerLight, RoundedCornerShape(16.dp))
                    .clickable(onClick = onEditClick)
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                Text(
                    text = "修改",
                    fontSize = 13.sp,
                    color = TextPrimaryLight
                )
            }
        }
    }
}

// ==================== 滤芯状态卡片 ====================

@Composable
private fun FilterStatusCard(
    filters: List<FilterItem>,
    onReplaceClick: () -> Unit
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
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            // 标题行
            Text(
                text = "全屋净水滤芯",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
                color = TextPrimaryLight,
                fontSize = 16.sp
            )

            Spacer(modifier = Modifier.height(16.dp))

            // 滤芯列表
            Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                filters.forEach { filter ->
                    FilterItemRow(filter = filter)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // 预约更换按钮
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(44.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(TextPrimaryLight)
                    .clickable(onClick = onReplaceClick),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "预约更换",
                    color = Color.White,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Medium
                )
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
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // 滤芯名称
            Text(
                text = filter.name,
                fontSize = 14.sp,
                color = TextPrimaryLight,
                fontWeight = FontWeight.Medium
            )
        }

        // 状态标签
        val (statusText, statusColor) = when (filter.status) {
            FilterUiStatus.NORMAL -> Pair("正常", SuccessGreen)
            FilterUiStatus.WARNING -> Pair("需更换", ErrorRed)
            FilterUiStatus.EXPIRED -> Pair("已过期", ErrorRed)
        }

        Text(
            text = statusText,
            fontSize = 14.sp,
            color = statusColor,
            fontWeight = FontWeight.Medium
        )
    }

    Spacer(modifier = Modifier.height(8.dp))

    // 进度条
    val progressColor = when (filter.status) {
        FilterUiStatus.NORMAL -> PrimaryBlue
        FilterUiStatus.WARNING -> WarningYellow
        FilterUiStatus.EXPIRED -> ErrorRed
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(6.dp)
            .clip(RoundedCornerShape(3.dp))
            .background(Color(0xFFE8E8E8))
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth(filter.progress)
                .height(6.dp)
                .clip(RoundedCornerShape(3.dp))
                .background(progressColor)
        )
    }
}

/**
 * 热力杀菌时间选择弹窗
 */
@Composable
fun SterilizationTimePickerDialog(
    currentSchedule: String,
    sterilizationState: com.wuheng.smart.presentation.base.UiDataState<Unit>,
    onConfirm: (dayOfWeek: Int, hour: Int, minute: Int) -> Unit,
    onDismiss: () -> Unit
) {
    var selectedDay by remember { mutableStateOf(5) } // 默认周五
    var selectedHour by remember { mutableStateOf(2) } // 默认2点
    var selectedMinute by remember { mutableStateOf(0) } // 默认0分

    val daysOfWeek = listOf("周一", "周二", "周三", "周四", "周五", "周六", "周日")

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = "设置热力杀菌时间",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
        },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(spacing_lg)) {
                // 星期选择
                Text(
                    text = "选择星期",
                    style = MaterialTheme.typography.bodyMedium,
                    color = TextSecondaryLight
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(spacing_xs)
                ) {
                    daysOfWeek.forEachIndexed { index, day ->
                        val isSelected = selectedDay == index + 1
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .clip(RoundedCornerShape(corner_sm))
                                .background(
                                    if (isSelected) PrimaryBlue else SurfaceLight
                                )
                                .clickable { selectedDay = index + 1 }
                                .padding(vertical = spacing_sm),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = day,
                                fontSize = 12.sp,
                                color = if (isSelected) Color.White else TextPrimaryLight,
                                fontWeight = if (isSelected) FontWeight.Medium else FontWeight.Normal
                            )
                        }
                    }
                }

                // 时间选择
                Text(
                    text = "选择时间",
                    style = MaterialTheme.typography.bodyMedium,
                    color = TextSecondaryLight
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(spacing_md),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // 小时
                    NumberPicker(
                        value = selectedHour,
                        onValueChange = { selectedHour = it },
                        range = 0..23,
                        suffix = "时",
                        modifier = Modifier.weight(1f)
                    )

                    Text(":", style = MaterialTheme.typography.headlineSmall)

                    // 分钟
                    NumberPicker(
                        value = selectedMinute,
                        onValueChange = { selectedMinute = it },
                        range = 0..59,
                        suffix = "分",
                        modifier = Modifier.weight(1f)
                    )
                }

                // 预览
                Text(
                    text = "设置后将在 ${daysOfWeek[selectedDay - 1]} ${String.format("%02d:%02d", selectedHour, selectedMinute)} 自动执行热力杀菌",
                    style = MaterialTheme.typography.bodySmall,
                    color = TextSecondaryLight
                )

                // 加载和错误状态
                if (sterilizationState is com.wuheng.smart.presentation.base.UiDataState.Loading) {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        color = PrimaryBlue
                    )
                }

                if (sterilizationState is com.wuheng.smart.presentation.base.UiDataState.Error) {
                    Text(
                        text = "设置失败，请稍后重试",
                        color = ErrorRed,
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }
        },
        confirmButton = {
            Button(
                onClick = { onConfirm(selectedDay, selectedHour, selectedMinute) },
                enabled = sterilizationState !is com.wuheng.smart.presentation.base.UiDataState.Loading,
                colors = ButtonDefaults.buttonColors(containerColor = PrimaryBlue)
            ) {
                Text("确认")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("取消")
            }
        }
    )
}

/**
 * 数字选择器
 */
@Composable
private fun NumberPicker(
    value: Int,
    onValueChange: (Int) -> Unit,
    range: IntRange,
    suffix: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // 减号按钮
        IconButton(
            onClick = {
                if (value > range.first) onValueChange(value - 1)
            },
            enabled = value > range.first
        ) {
            Text("-", style = MaterialTheme.typography.headlineSmall)
        }

        // 数值显示
        Text(
            text = String.format("%02d%s", value, suffix),
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.SemiBold
        )

        // 加号按钮
        IconButton(
            onClick = {
                if (value < range.last) onValueChange(value + 1)
            },
            enabled = value < range.last
        ) {
            Text("+", style = MaterialTheme.typography.headlineSmall)
        }
    }
}

// ==================== 滤芯预约更换弹窗 ====================

/**
 * 滤芯预约更换弹窗
 *
 * @param filters 滤芯列表
 * @param filterReplaceState 预约状态
 * @param onConfirm 确认回调 (filterId, contactName, contactPhone, appointmentDate)
 * @param onDismiss 取消/关闭回调
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterReplaceDialog(
    filters: List<FilterItem>,
    filterReplaceState: com.wuheng.smart.presentation.base.UiDataState<Unit>,
    onConfirm: (filterId: String, contactName: String, contactPhone: String, appointmentDate: String) -> Unit,
    onDismiss: () -> Unit
) {
    var selectedFilterIndex by remember { mutableStateOf(0) }
    var contactName by remember { mutableStateOf("") }
    var contactPhone by remember { mutableStateOf("") }
    var appointmentDate by remember { mutableStateOf("") }

    // 表单验证
    val isFormValid by remember(contactName, contactPhone, appointmentDate) {
        mutableStateOf(
            contactName.isNotBlank() &&
            contactPhone.isNotBlank() &&
            appointmentDate.isNotBlank()
        )
    }

    AlertDialog(
        onDismissRequest = {
            if (filterReplaceState !is com.wuheng.smart.presentation.base.UiDataState.Loading) {
                onDismiss()
            }
        },
        title = {
            Text(
                text = "预约滤芯更换",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
        },
        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // 选择滤芯
                Text(
                    text = "选择滤芯",
                    style = MaterialTheme.typography.bodyMedium,
                    color = TextSecondaryLight,
                    fontWeight = FontWeight.Medium
                )

                if (filters.isNotEmpty()) {
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        filters.forEachIndexed { index, filter ->
                            val isSelected = selectedFilterIndex == index
                            val statusText = when (filter.status) {
                                FilterUiStatus.NORMAL -> "正常"
                                FilterUiStatus.WARNING -> "需更换"
                                FilterUiStatus.EXPIRED -> "已过期"
                            }
                            val statusColor = when (filter.status) {
                                FilterUiStatus.NORMAL -> SuccessGreen
                                FilterUiStatus.WARNING -> WarningYellow
                                FilterUiStatus.EXPIRED -> ErrorRed
                            }

                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(
                                        if (isSelected) PrimaryBlue.copy(alpha = 0.1f) else SurfaceLight
                                    )
                                    .border(
                                        width = if (isSelected) 2.dp else 1.dp,
                                        color = if (isSelected) PrimaryBlue else DividerLight,
                                        shape = RoundedCornerShape(8.dp)
                                    )
                                    .clickable { selectedFilterIndex = index }
                                    .padding(12.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    RadioButton(
                                        selected = isSelected,
                                        onClick = { selectedFilterIndex = index },
                                        colors = RadioButtonDefaults.colors(
                                            selectedColor = PrimaryBlue,
                                            unselectedColor = TextTertiaryLight
                                        )
                                    )
                                    Text(
                                        text = filter.name,
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = TextPrimaryLight,
                                        fontWeight = if (isSelected) FontWeight.Medium else FontWeight.Normal
                                    )
                                }
                                Text(
                                    text = statusText,
                                    style = MaterialTheme.typography.bodySmall,
                                    color = statusColor,
                                    fontWeight = FontWeight.Medium
                                )
                            }
                        }
                    }
                } else {
                    Text(
                        text = "暂无滤芯信息",
                        style = MaterialTheme.typography.bodyMedium,
                        color = TextSecondaryLight
                    )
                }

                Divider(color = DividerLight)

                // 联系人姓名
                TextField(
                    value = contactName,
                    onValueChange = { contactName = it },
                    label = { Text("联系人姓名") },
                    placeholder = { Text("请输入联系人姓名") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = SurfaceLight,
                        focusedIndicatorColor = PrimaryBlue,
                        focusedLabelColor = PrimaryBlue
                    ),
                    enabled = filterReplaceState !is com.wuheng.smart.presentation.base.UiDataState.Loading
                )

                // 联系人电话
                TextField(
                    value = contactPhone,
                    onValueChange = { contactPhone = it },
                    label = { Text("联系人电话") },
                    placeholder = { Text("请输入联系人电话") },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = SurfaceLight,
                        focusedIndicatorColor = PrimaryBlue,
                        focusedLabelColor = PrimaryBlue
                    ),
                    enabled = filterReplaceState !is com.wuheng.smart.presentation.base.UiDataState.Loading
                )

                // 预约日期
                Box(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    TextField(
                        value = appointmentDate,
                        onValueChange = { },
                        label = { Text("预约日期") },
                        placeholder = { Text("请选择预约日期 (yyyy-MM-dd)") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth(),
                        colors = TextFieldDefaults.textFieldColors(
                            containerColor = SurfaceLight,
                            focusedIndicatorColor = PrimaryBlue,
                            focusedLabelColor = PrimaryBlue
                        ),
                        enabled = filterReplaceState !is com.wuheng.smart.presentation.base.UiDataState.Loading
                    )
                }

                // 加载状态
                if (filterReplaceState is com.wuheng.smart.presentation.base.UiDataState.Loading) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(24.dp),
                            color = PrimaryBlue,
                            strokeWidth = 2.dp
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "提交中...",
                            style = MaterialTheme.typography.bodyMedium,
                            color = TextSecondaryLight
                        )
                    }
                }

                // 错误状态
                if (filterReplaceState is com.wuheng.smart.presentation.base.UiDataState.Error) {
                    Text(
                        text = "预约失败，请检查网络后重试",
                        color = ErrorRed,
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    val selectedFilter = filters.getOrNull(selectedFilterIndex)
                    if (selectedFilter != null && isFormValid) {
                        // 使用索引+1作为filterId（因为FilterItem中没有id字段，需要从原始数据映射）
                        onConfirm(
                            (selectedFilterIndex + 1).toString(),
                            contactName.trim(),
                            contactPhone.trim(),
                            appointmentDate
                        )
                    }
                },
                enabled = isFormValid &&
                    filterReplaceState !is com.wuheng.smart.presentation.base.UiDataState.Loading,
                colors = ButtonDefaults.buttonColors(containerColor = PrimaryBlue)
            ) {
                Text("确认预约")
            }
        },
        dismissButton = {
            TextButton(
                onClick = onDismiss,
                enabled = filterReplaceState !is com.wuheng.smart.presentation.base.UiDataState.Loading
            ) {
                Text("取消")
            }
        }
    )
}
