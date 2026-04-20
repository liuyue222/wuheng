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

    ConsumablesContent(
        consumablesState = consumablesState,
        onNavigateBack = onNavigateBack,
        onRefresh = { viewModel.refresh() }
    )
}

/**
 * 耗材进度页面内容
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConsumablesContent(
    consumablesState: UiDataState<List<ConsumableItem>>,
    onNavigateBack: () -> Unit = {},
    onRefresh: () -> Unit = {}
) {
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
            is UiDataState.Idle, is UiDataState.Loading -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(color = PrimaryBlue)
                }
            }
            is UiDataState.Error -> {
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

                    items(consumables.size) { index ->
                        val item = consumables[index]
                        ConsumableItemCard(item = item)
                    }

                    item { Spacer(modifier = Modifier.height(spacing_lg)) }
                }
            }
        }
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
    val status: ConsumableStatus
)

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
