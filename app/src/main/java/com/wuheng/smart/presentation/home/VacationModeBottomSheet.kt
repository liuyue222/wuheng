package com.wuheng.smart.presentation.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.wuheng.smart.R
import com.wuheng.smart.presentation.theme.*
import java.util.*

/**
 * 度假模式对话框 - 手势滚动选择时间
 */
@Composable
fun VacationModeBottomSheet(
    onDismiss: () -> Unit,
    onConfirm: (returnDateTime: String) -> Unit
) {
    Dialog(onDismissRequest = onDismiss) {
        VacationModeContent(
            onDismiss = onDismiss,
            onConfirm = onConfirm
        )
    }
}

@Composable
private fun VacationModeContent(
    onDismiss: () -> Unit,
    onConfirm: (returnDateTime: String) -> Unit
) {
    val calendar = remember { Calendar.getInstance() }

    var selectedYear by remember { mutableStateOf(calendar.get(Calendar.YEAR)) }
    var selectedMonth by remember { mutableStateOf(calendar.get(Calendar.MONTH) + 1) }
    var selectedDay by remember { mutableStateOf(calendar.get(Calendar.DAY_OF_MONTH)) }
    var selectedHour by remember { mutableStateOf(calendar.get(Calendar.HOUR_OF_DAY)) }
    var selectedMinute by remember { mutableStateOf(calendar.get(Calendar.MINUTE)) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = page_margin_horizontal),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = SurfaceLight)
    ) {
        Column(
            modifier = Modifier.padding(horizontal = page_margin_horizontal, vertical = spacing_lg)
        ) {
            // 标题
            Text(
                text = "设置返程时间",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = TextPrimaryLight
            )

            Spacer(modifier = Modifier.height(spacing_sm))

            Text(
                text = "系统将在返程前自动启动预冷/预热，确保您到家即恒温",
                style = MaterialTheme.typography.bodyMedium,
                color = TextSecondaryLight
            )

            Spacer(modifier = Modifier.height(spacing_lg))

            // 五个滚轮横向排列
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                // 年
                WheelPicker(
                    value = selectedYear,
                    onValueChange = { 
                        selectedYear = it
                        // 年份变化时检查日期有效性
                        val maxDay = getDaysInMonth(selectedYear, selectedMonth)
                        if (selectedDay > maxDay) {
                            selectedDay = maxDay
                        }
                    },
                    range = (calendar.get(Calendar.YEAR)..(calendar.get(Calendar.YEAR) + 2)).toList(),
                    suffix = "年",
                    modifier = Modifier.weight(1.5f)
                )

                // 月
                WheelPicker(
                    value = selectedMonth,
                    onValueChange = { 
                        selectedMonth = it
                        // 月份变化时检查日期有效性（如2月只有28/29天）
                        val maxDay = getDaysInMonth(selectedYear, selectedMonth)
                        if (selectedDay > maxDay) {
                            selectedDay = maxDay
                        }
                    },
                    range = (1..12).toList(),
                    suffix = "月",
                    modifier = Modifier.weight(1f)
                )

                // 日 - 动态根据年月计算最大天数
                val maxDay = getDaysInMonth(selectedYear, selectedMonth)
                WheelPicker(
                    value = selectedDay.coerceIn(1, maxDay),
                    onValueChange = { selectedDay = it },
                    range = (1..maxDay).toList(),
                    suffix = "日",
                    modifier = Modifier.weight(1f)
                )

                // 时
                WheelPicker(
                    value = selectedHour,
                    onValueChange = { selectedHour = it },
                    range = (0..23).toList(),
                    suffix = "时",
                    modifier = Modifier.weight(1f)
                )

                // 分 - 按5分钟间隔
                WheelPicker(
                    value = selectedMinute,
                    onValueChange = { selectedMinute = it },
                    range = (0..11).map { it * 5 },
                    suffix = "分",
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(spacing_lg))

            // 返程时间和预计启动时间
            val returnDateTime = String.format(
                "%04d-%02d-%02d %02d:%02d",
                selectedYear, selectedMonth, selectedDay, selectedHour, selectedMinute
            )
            
            // 计算预计启动时间（返程前2小时）
            val startCal = Calendar.getInstance().apply {
                set(selectedYear, selectedMonth - 1, selectedDay, selectedHour, selectedMinute)
                add(Calendar.HOUR_OF_DAY, -2)
            }
            val startTimeStr = String.format(
                "%04d-%02d-%02d %02d:%02d",
                startCal.get(Calendar.YEAR),
                startCal.get(Calendar.MONTH) + 1,
                startCal.get(Calendar.DAY_OF_MONTH),
                startCal.get(Calendar.HOUR_OF_DAY),
                startCal.get(Calendar.MINUTE)
            )

            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = PrimaryBlue.copy(alpha = 0.1f)),
                shape = RoundedCornerShape(12.dp)
            ) {
                Column(modifier = Modifier.padding(spacing_md)) {
                    Text(
                        text = "返程时间: $returnDateTime",
                        style = MaterialTheme.typography.bodyMedium,
                        color = TextPrimaryLight,
                        fontWeight = FontWeight.Medium
                    )
                    Spacer(modifier = Modifier.height(spacing_xs))
                    Text(
                        text = "预计启动: $startTimeStr (提前2小时预冷/预热)",
                        style = MaterialTheme.typography.bodySmall,
                        color = PrimaryBlue,
                        fontWeight = FontWeight.Medium
                    )
                }
            }

            Spacer(modifier = Modifier.height(spacing_xl))

            // 按钮行
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(spacing_md)
            ) {
                OutlinedButton(
                    onClick = onDismiss,
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text("取消")
                }

                Button(
                    onClick = { 
                        // 计算预计启动时间（返程前2小时）
                        val returnCal = Calendar.getInstance().apply {
                            set(selectedYear, selectedMonth - 1, selectedDay, selectedHour, selectedMinute)
                        }
                        val startCal = returnCal.clone() as Calendar
                        startCal.add(Calendar.HOUR_OF_DAY, -2)
                        
                        val startTimeStr = String.format(
                            "%04d-%02d-%02d %02d:%02d",
                            startCal.get(Calendar.YEAR),
                            startCal.get(Calendar.MONTH) + 1,
                            startCal.get(Calendar.DAY_OF_MONTH),
                            startCal.get(Calendar.HOUR_OF_DAY),
                            startCal.get(Calendar.MINUTE)
                        )
                        
                        onConfirm(returnDateTime)
                    },
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = PrimaryBlue)
                ) {
                    Text("确认")
                }
            }

            Spacer(modifier = Modifier.height(spacing_lg))
        }
    }
}

/**
 * 滚轮选择器 - 手势滚动
 * 中间选中项黑色加粗，上下未选中项灰色，距离越远字体越小
 */
@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun WheelPicker(
    value: Int,
    onValueChange: (Int) -> Unit,
    range: List<Int>,
    suffix: String,
    modifier: Modifier = Modifier
) {
    val listState = rememberLazyListState()
    val currentIndex = range.indexOf(value).coerceAtLeast(0)

    // 同步滚动位置到当前值
    LaunchedEffect(Unit) {
        listState.scrollToItem(currentIndex)
    }

    // 监听滚动停止，更新选中值
    LaunchedEffect(listState.isScrollInProgress) {
        if (!listState.isScrollInProgress) {
            val index = listState.firstVisibleItemIndex
            if (index in range.indices) {
                onValueChange(range[index])
            }
        }
    }

    Box(
        modifier = modifier
            .fillMaxHeight()
            .clip(RoundedCornerShape(8.dp)),
        contentAlignment = Alignment.Center
    ) {
        LazyColumn(
            state = listState,
            flingBehavior = rememberSnapFlingBehavior(lazyListState = listState),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(vertical = 80.dp) // 上下留白，确保第一项和最后一项可以居中
        ) {
            items(range.size) { index ->
                val itemValue = range[index]
                val isSelected = itemValue == value

                // 计算与选中项的距离
                val distanceFromCenter = kotlin.math.abs(index - currentIndex)

                // 根据距离计算字体大小和颜色
                val fontSize = when {
                    isSelected -> 18.sp
                    distanceFromCenter == 1 -> 14.sp
                    distanceFromCenter >= 2 -> 12.sp
                    else -> 14.sp
                }

                val textColor = when {
                    isSelected -> TextPrimaryLight  // 选中：黑色
                    distanceFromCenter == 1 -> TextTertiaryLight.copy(alpha = 0.7f)  // 相邻：深灰
                    else -> TextTertiaryLight.copy(alpha = 0.4f)  // 更远：浅灰
                }

                val fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(40.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = String.format("%02d%s", itemValue, suffix),
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontSize = fontSize,
                            fontWeight = fontWeight
                        ),
                        color = textColor
                    )
                }
            }
        }

        // 中间选中指示框（仅边框）
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(40.dp)
                .background(Color.Transparent)
                .border(
                    width = 1.dp,
                    color = DividerLight,
                    shape = RoundedCornerShape(4.dp)
                )
        )
    }
}

private fun getDaysInMonth(year: Int, month: Int): Int {
    val calendar = Calendar.getInstance()
    calendar.set(year, month - 1, 1)
    return calendar.getActualMaximum(Calendar.DAY_OF_MONTH)
}
