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
                    .height(180.dp),
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                // 年
                WheelPicker(
                    value = selectedYear,
                    onValueChange = { selectedYear = it },
                    range = (calendar.get(Calendar.YEAR)..(calendar.get(Calendar.YEAR) + 2)).toList(),
                    suffix = "年",
                    modifier = Modifier.weight(1.5f)
                )

                // 月
                WheelPicker(
                    value = selectedMonth,
                    onValueChange = { selectedMonth = it },
                    range = (1..12).toList(),
                    suffix = "月",
                    modifier = Modifier.weight(1f)
                )

                // 日
                val maxDay = getDaysInMonth(selectedYear, selectedMonth)
                WheelPicker(
                    value = selectedDay,
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

                // 分
                WheelPicker(
                    value = selectedMinute,
                    onValueChange = { selectedMinute = it },
                    range = (0..59).toList(),
                    suffix = "分",
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(spacing_lg))

            // 预计启动时间提示
            val returnDateTime = String.format(
                "%04d-%02d-%02d %02d:%02d",
                selectedYear, selectedMonth, selectedDay, selectedHour, selectedMinute
            )

            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = PrimaryBlue.copy(alpha = 0.1f)),
                shape = RoundedCornerShape(12.dp)
            ) {
                Column(modifier = Modifier.padding(spacing_md)) {
                    Text(
                        text = "预计启动时间",
                        style = MaterialTheme.typography.bodySmall,
                        color = TextTertiaryLight
                    )
                    Spacer(modifier = Modifier.height(spacing_xs))
                    Text(
                        text = "系统将根据热惰性计算，提前启动",
                        style = MaterialTheme.typography.bodyMedium,
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
                    onClick = { onConfirm(returnDateTime) },
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
    // 在列表前后添加空白项，确保可以滚动到第一项和最后一项
    val items = listOf(-1) + range + listOf(-1)
    val listState = rememberLazyListState()
    val currentIndex = range.indexOf(value).coerceAtLeast(0) + 1 // +1 因为前面加了一个空白项

    // 同步滚动位置
    LaunchedEffect(Unit) {
        listState.scrollToItem(currentIndex)
    }

    // 监听滚动停止，更新选中值
    LaunchedEffect(listState.isScrollInProgress) {
        if (!listState.isScrollInProgress) {
            val index = listState.firstVisibleItemIndex
            // 跳过空白项
            val actualIndex = index - 1
            if (actualIndex in range.indices) {
                onValueChange(range[actualIndex])
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
            modifier = Modifier.fillMaxWidth()
        ) {
            items(items.size) { index ->
                val itemValue = items[index]
                // 空白项
                if (itemValue == -1) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(40.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        // 空白占位
                    }
                    return@items
                }

                val isSelected = itemValue == value

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(40.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = String.format("%02d%s", itemValue, suffix),
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                        color = if (isSelected) TextPrimaryLight else TextTertiaryLight.copy(alpha = 0.5f),
                        fontSize = if (isSelected) 16.sp else 14.sp
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
