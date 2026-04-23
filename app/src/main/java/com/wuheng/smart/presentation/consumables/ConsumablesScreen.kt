package com.wuheng.smart.presentation.consumables

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.wuheng.smart.presentation.base.UiDataState
import com.wuheng.smart.presentation.theme.*

/**
 * 耗材进度页面 - 像素级还原设计图（耗材进度.png）
 *
 * 布局结构：
 * 1. 顶部导航栏：返回按钮 + "耗材使用进度"标题
 * 2. 耗材列表：白色卡片列表，每项显示滤芯名称、百分比、状态
 *
 * 设计规范：
 * - 页面背景：BackgroundLight (#F1F5F9)
 * - 列表项背景：SurfaceLight (白色)
 * - 正常状态：SuccessGreen (#22C55E 绿色)
 * - 需更换状态：ErrorRed (#EF4444 红色)
 * - 卡片圆角：corner_md (16.dp)
 * - 列表项间距：card_spacing_vertical (12.dp)
 */
@Composable
fun ConsumablesScreen(
    viewModel: ConsumablesViewModel = hiltViewModel(),
    onNavigateBack: () -> Unit = {}
) {
    val consumablesState by viewModel.consumablesState.collectAsStateWithLifecycle()
    val bookingState by viewModel.bookingState.collectAsStateWithLifecycle()

    ConsumablesContent(
        consumablesState = consumablesState,
        bookingState = bookingState,
        onNavigateBack = onNavigateBack,
        onRefresh = { viewModel.refresh() },
        onBookReplacement = { item -> viewModel.bookFilterReplacement(item.id.toString()) },
        onResetBookingState = { viewModel.resetBookingState() }
    )
}

/**
 * 耗材进度页面内容
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConsumablesContent(
    consumablesState: UiDataState<List<ConsumableItem>>,
    bookingState: UiDataState<Unit> = UiDataState.Idle,
    onNavigateBack: () -> Unit = {},
    onRefresh: () -> Unit = {},
    onBookReplacement: (ConsumableItem) -> Unit = {},
    onResetBookingState: () -> Unit = {}
) {
    var selectedItem by remember { mutableStateOf<ConsumableItem?>(null) }
    var showDetailDialog by remember { mutableStateOf(false) }
    var showBookingConfirmDialog by remember { mutableStateOf(false) }

    // 处理预约结果
    LaunchedEffect(bookingState) {
        when (bookingState) {
            is UiDataState.Success -> {
                showBookingConfirmDialog = false
                selectedItem = null
                onResetBookingState()
            }
            is UiDataState.Error -> {
                onResetBookingState()
            }
            else -> {}
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "耗材使用进度",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.SemiBold,
                        color = TextPrimaryLight
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "返回",
                            tint = TextPrimaryLight
                        )
                    }
                },
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = BackgroundLight,
                    titleContentColor = TextPrimaryLight
                )
            )
        },
        containerColor = BackgroundLight
    ) { paddingValues ->
        when (consumablesState) {
            is UiDataState.Idle, is UiDataState.Loading, is UiDataState.LoadingWithData -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(color = PrimaryBlue)
                }
            }
            is UiDataState.Error, is UiDataState.ErrorWithData -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(spacing_md)
                    ) {
                        Text(
                            text = "加载失败",
                            color = ErrorRed,
                            style = MaterialTheme.typography.bodyLarge
                        )
                        Button(onClick = onRefresh) {
                            Text("重试")
                        }
                    }
                }
            }
            is UiDataState.Success -> {
                val consumables = consumablesState.data
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                        .padding(horizontal = page_margin_horizontal),
                    verticalArrangement = Arrangement.spacedBy(card_spacing_vertical)
                ) {
                    item { Spacer(modifier = Modifier.height(spacing_sm)) }

                    // 统计卡片
                    item {
                        ConsumablesSummaryCard(consumables = consumables)
                    }

                    item { Spacer(modifier = Modifier.height(spacing_md)) }

                    // 耗材列表
                    items(consumables.size) { index ->
                        val item = consumables[index]
                        ConsumableItemCard(
                            item = item,
                            onClick = {
                                selectedItem = item
                                showDetailDialog = true
                            }
                        )
                    }

                    item { Spacer(modifier = Modifier.height(spacing_lg)) }
                }
            }
        }
    }

    // 详情弹窗
    if (showDetailDialog && selectedItem != null) {
        ConsumableDetailDialog(
            item = selectedItem!!,
            onDismiss = { showDetailDialog = false },
            onBookReplacement = {
                showDetailDialog = false
                showBookingConfirmDialog = true
            }
        )
    }

    // 预约确认弹窗
    if (showBookingConfirmDialog && selectedItem != null) {
        BookingConfirmDialog(
            item = selectedItem!!,
            bookingState = bookingState,
            onDismiss = { showBookingConfirmDialog = false },
            onConfirm = { onBookReplacement(selectedItem!!) }
        )
    }
}

// ConsumableItem 和 ConsumableStatus 定义
enum class ConsumableStatus {
    NORMAL,     // 正常 (>30%)
    WARNING,    // 警告 (10%-30%)
    CRITICAL    // 急需更换 (<10%)
}

data class ConsumableItem(
    val id: Int,
    val name: String,
    val percentage: Int,
    val status: ConsumableStatus,
    val installDate: String = "2024-01-15",  // 安装日期
    val estimatedLife: Int = 365,            // 预计寿命（天）
    val description: String = ""             // 滤芯描述
)

/**
 * 耗材统计卡片
 */
@Composable
private fun ConsumablesSummaryCard(consumables: List<ConsumableItem>) {
    val normalCount = consumables.count { it.status == ConsumableStatus.NORMAL }
    val warningCount = consumables.count { it.status == ConsumableStatus.WARNING }
    val criticalCount = consumables.count { it.status == ConsumableStatus.CRITICAL }

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = PrimaryBlue.copy(alpha = 0.1f)),
        shape = RoundedCornerShape(corner_md)
    ) {
        Column(
            modifier = Modifier.padding(card_padding_large)
        ) {
            Text(
                text = "耗材状态概览",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
                color = TextPrimaryLight
            )

            Spacer(modifier = Modifier.height(spacing_md))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                SummaryItem(count = normalCount, label = "正常", color = SuccessGreen)
                SummaryItem(count = warningCount, label = "需更换", color = WarningYellow)
                SummaryItem(count = criticalCount, label = "急需更换", color = ErrorRed)
            }
        }
    }
}

@Composable
private fun SummaryItem(count: Int, label: String, color: Color) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = count.toString(),
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = color
        )
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall,
            color = TextSecondaryLight
        )
    }
}

/**
 * 耗材详情弹窗
 */
@Composable
private fun ConsumableDetailDialog(
    item: ConsumableItem,
    onDismiss: () -> Unit,
    onBookReplacement: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = item.name,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
        },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(spacing_md)) {
                // 进度条
                LinearProgressIndicator(
                    progress = item.percentage / 100f,
                    modifier = Modifier.fillMaxWidth(),
                    color = when (item.status) {
                        ConsumableStatus.NORMAL -> SuccessGreen
                        ConsumableStatus.WARNING -> WarningYellow
                        ConsumableStatus.CRITICAL -> ErrorRed
                    }
                )

                // 信息行
                DetailInfoRow(label = "剩余寿命", value = "${item.percentage}%")
                DetailInfoRow(label = "安装日期", value = item.installDate)
                DetailInfoRow(label = "预计寿命", value = "${item.estimatedLife}天")

                // 状态提示
                val tipText = when (item.status) {
                    ConsumableStatus.NORMAL -> "滤芯状态良好，请继续保持"
                    ConsumableStatus.WARNING -> "滤芯寿命即将到期，建议预约更换"
                    ConsumableStatus.CRITICAL -> "滤芯急需更换，请立即预约"
                }
                Text(
                    text = tipText,
                    style = MaterialTheme.typography.bodyMedium,
                    color = when (item.status) {
                        ConsumableStatus.NORMAL -> SuccessGreen
                        ConsumableStatus.WARNING -> WarningYellow
                        ConsumableStatus.CRITICAL -> ErrorRed
                    },
                    modifier = Modifier.padding(top = spacing_sm)
                )
            }
        },
        confirmButton = {
            if (item.status != ConsumableStatus.NORMAL) {
                Button(
                    onClick = onBookReplacement,
                    colors = ButtonDefaults.buttonColors(containerColor = PrimaryBlue)
                ) {
                    Text("预约更换")
                }
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("关闭")
            }
        }
    )
}

@Composable
private fun DetailInfoRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = label, color = TextSecondaryLight)
        Text(text = value, fontWeight = FontWeight.Medium)
    }
}

/**
 * 预约确认弹窗
 */
@Composable
private fun BookingConfirmDialog(
    item: ConsumableItem,
    bookingState: UiDataState<Unit>,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = "确认预约",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
        },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(spacing_sm)) {
                Text("您即将预约更换以下滤芯：")
                Text(
                    text = item.name,
                    fontWeight = FontWeight.SemiBold,
                    color = PrimaryBlue
                )
                Text(
                    text = "我们的服务人员将在24小时内与您联系确认上门时间。",
                    style = MaterialTheme.typography.bodySmall,
                    color = TextSecondaryLight
                )

                if (bookingState is UiDataState.Loading) {
                    Spacer(modifier = Modifier.height(spacing_md))
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
                }

                if (bookingState is UiDataState.Error) {
                    Spacer(modifier = Modifier.height(spacing_md))
                    Text(
                        text = "预约失败，请稍后重试",
                        color = ErrorRed,
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }
        },
        confirmButton = {
            Button(
                onClick = onConfirm,
                enabled = bookingState !is UiDataState.Loading,
                colors = ButtonDefaults.buttonColors(containerColor = PrimaryBlue)
            ) {
                Text("确认预约")
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
 * 耗材列表项卡片 - 像素级还原设计图
 *
 * 布局结构：
 * ┌─────────────────────────────────────────┐
 * │ [滤芯名称]              [百分比] [状态] │
 * └─────────────────────────────────────────┘
 *
 * 设计规范：
 * - 卡片背景：SurfaceLight (白色)
 * - 圆角：corner_md (16.dp)
 * - 内边距：card_padding_large (20.dp)
 * - 名称字号：filter_item_name_size (15.sp)
 * - 百分比字号：filter_percentage_size (15.sp)
 * - 状态标签字号：filter_status_size (13.sp)
 * - 正常状态颜色：SuccessGreen (#22C55E)
 * - 需更换状态颜色：ErrorRed (#EF4444)
 */
@Composable
private fun ConsumableItemCard(
    item: ConsumableItem,
    onClick: () -> Unit = {}
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(
                elevation = elevation_md,
                shape = RoundedCornerShape(corner_md),
                ambientColor = ShadowLight.copy(alpha = 0.15f),
                spotColor = ShadowLight.copy(alpha = 0.25f)
            )
            .clip(RoundedCornerShape(corner_md))
            .background(SurfaceLight)
            .clickable(onClick = onClick)
            .padding(card_padding_large)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // 左侧：滤芯名称
            Text(
                text = item.name,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Medium,
                color = FilterItemNameColor,
                fontSize = filter_item_name_size
            )

            // 右侧：百分比 + 状态
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(spacing_md)
            ) {
                // 百分比数值
                Text(
                    text = "${item.percentage}%",
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.SemiBold,
                    color = FilterPercentageColor,
                    fontSize = filter_percentage_size
                )

                // 状态标签
                val (statusText, statusColor) = when (item.status) {
                    ConsumableStatus.NORMAL -> "正常" to FilterStatusLabelNormal
                    ConsumableStatus.WARNING -> "更换" to FilterStatusLabelWarning
                    ConsumableStatus.CRITICAL -> "急需更换" to ErrorRed
                }
                Text(
                    text = statusText,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Medium,
                    color = statusColor,
                    fontSize = filter_status_size
                )
            }
        }
    }
}

// ==================== Preview 函数 ====================

@Preview(showBackground = true, name = "耗材进度-亮色主题", backgroundColor = 0xFFF1F5F9)
@Composable
fun ConsumablesScreenPreview() {
    WuHengTheme {
        ConsumablesContent(
            consumablesState = UiDataState.Success(
                listOf(
                    ConsumableItem(
                        id = 1,
                        name = "前置过滤器",
                        percentage = 98,
                        status = ConsumableStatus.NORMAL
                    ),
                    ConsumableItem(
                        id = 2,
                        name = "中央净水机",
                        percentage = 20,
                        status = ConsumableStatus.WARNING
                    ),
                    ConsumableItem(
                        id = 3,
                        name = "空气净化滤芯",
                        percentage = 20,
                        status = ConsumableStatus.WARNING
                    )
                )
            ),
            onNavigateBack = {},
            onRefresh = {}
        )
    }
}

@Preview(showBackground = true, name = "耗材进度-加载状态", backgroundColor = 0xFFF1F5F9)
@Composable
fun ConsumablesScreenLoadingPreview() {
    WuHengTheme {
        ConsumablesContent(
            consumablesState = UiDataState.Loading,
            onNavigateBack = {},
            onRefresh = {}
        )
    }
}

@Preview(showBackground = true, name = "耗材进度-暗色主题", backgroundColor = 0xFF0F172A)
@Composable
fun ConsumablesScreenDarkPreview() {
    WuHengTheme(darkTheme = true) {
        ConsumablesContent(
            consumablesState = UiDataState.Success(
                listOf(
                    ConsumableItem(
                        id = 1,
                        name = "前置过滤器",
                        percentage = 98,
                        status = ConsumableStatus.NORMAL
                    ),
                    ConsumableItem(
                        id = 2,
                        name = "中央净水机",
                        percentage = 20,
                        status = ConsumableStatus.WARNING
                    )
                )
            ),
            onNavigateBack = {},
            onRefresh = {}
        )
    }
}
