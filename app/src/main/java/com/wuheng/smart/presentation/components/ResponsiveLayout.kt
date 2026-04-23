@file:OptIn(ExperimentalMaterial3Api::class)

package com.wuheng.smart.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Badge
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.wuheng.smart.presentation.theme.breakpoint_compact
import com.wuheng.smart.presentation.theme.breakpoint_expanded
import com.wuheng.smart.presentation.theme.breakpoint_medium
import com.wuheng.smart.presentation.theme.page_margin_horizontal
import com.wuheng.smart.presentation.theme.page_margin_horizontal_wide
import com.wuheng.smart.presentation.theme.spacing_default
import com.wuheng.smart.presentation.theme.spacing_wide_default

/**
 * 窗口尺寸类别
 * 用于响应式布局
 */
enum class WindowSizeClass {
    COMPACT,    // 紧凑 - 手机竖屏 (<600dp)
    MEDIUM,     // 中等 - 手机横屏/小平板 (600-840dp)
    EXPANDED    // 展开 - 平板/大屏 (>840dp)
}

/**
 * 屏幕方向
 */
enum class ScreenOrientation {
    PORTRAIT,   // 竖屏
    LANDSCAPE   // 横屏
}

/**
 * 获取屏幕方向
 */
@Composable
fun getScreenOrientation(maxWidth: Dp, maxHeight: Dp): ScreenOrientation {
    return if (maxWidth > maxHeight) ScreenOrientation.LANDSCAPE else ScreenOrientation.PORTRAIT
}

/**
 * 判断是否为iPad/平板设备
 */
@Composable
fun isTabletDevice(maxWidth: Dp, maxHeight: Dp): Boolean {
    val minDimension = minOf(maxWidth.value, maxHeight.value)
    return minDimension >= 600f
}

/**
 * 判断是否需要双面板布局
 * 用于iPad横屏时显示主从界面
 */
@Composable
fun shouldUseTwoPaneLayout(maxWidth: Dp, maxHeight: Dp): Boolean {
    return maxWidth >= 840.dp && maxWidth > maxHeight
}

/**
 * 获取窗口尺寸类别
 */
@Composable
fun getWindowSizeClass(maxWidth: Dp): WindowSizeClass {
    return when {
        maxWidth < breakpoint_compact -> WindowSizeClass.COMPACT
        maxWidth < breakpoint_medium -> WindowSizeClass.MEDIUM
        else -> WindowSizeClass.EXPANDED
    }
}

/**
 * 判断是否为宽屏布局 (>=720dp)
 */
@Composable
fun isWideLayout(maxWidth: Dp): Boolean {
    return maxWidth >= 720.dp
}

/**
 * 响应式页面边距
 * 根据屏幕宽度自动调整
 */
@Composable
fun responsiveHorizontalPadding(maxWidth: Dp): Dp {
    return if (isWideLayout(maxWidth)) page_margin_horizontal_wide else page_margin_horizontal
}

/**
 * 响应式间距
 * 根据屏幕宽度自动调整
 */
@Composable
fun responsiveSpacing(maxWidth: Dp): Dp {
    return if (isWideLayout(maxWidth)) spacing_wide_default else spacing_default
}

/**
 * 响应式列数
 * 根据屏幕宽度返回合适的列数
 */
@Composable
fun responsiveColumnCount(maxWidth: Dp): Int {
    return when (getWindowSizeClass(maxWidth)) {
        WindowSizeClass.COMPACT -> 1
        WindowSizeClass.MEDIUM -> 2
        WindowSizeClass.EXPANDED -> 3
    }
}

/**
 * 响应式容器
 * 根据屏幕宽度自动调整布局
 *
 * @param content 内容 composable，接收 maxWidth 和 isWide 参数
 */
@Composable
fun ResponsiveContainer(
    modifier: Modifier = Modifier,
    content: @Composable (maxWidth: Dp, isWide: Boolean) -> Unit
) {
    BoxWithConstraints(
        modifier = modifier.fillMaxSize()
    ) {
        val maxWidth = maxWidth
        val isWide = isWideLayout(maxWidth)
        content(maxWidth, isWide)
    }
}

/**
 * 响应式页面布局
 * 自动处理页面边距和最大宽度限制
 */
@Composable
fun ResponsivePageLayout(
    modifier: Modifier = Modifier,
    content: @Composable (maxWidth: Dp, isWide: Boolean) -> Unit
) {
    ResponsiveContainer(modifier = modifier) { maxWidth, isWide ->
        val horizontalPadding = responsiveHorizontalPadding(maxWidth)
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = horizontalPadding)
        ) {
            content(maxWidth, isWide)
        }
    }
}

/**
 * 响应式双列布局
 * 宽屏时显示双列，窄屏时显示单列
 */
@Composable
fun ResponsiveTwoColumnLayout(
    modifier: Modifier = Modifier,
    gap: Dp = 16.dp,
    leftContent: @Composable (isWide: Boolean) -> Unit,
    rightContent: @Composable (isWide: Boolean) -> Unit
) {
    ResponsiveContainer(modifier = modifier) { maxWidth, isWide ->
        if (isWide) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(gap)
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    leftContent(true)
                }
                Column(modifier = Modifier.weight(1f)) {
                    rightContent(true)
                }
            }
        } else {
            Column {
                leftContent(false)
                rightContent(false)
            }
        }
    }
}

/**
 * 响应式网格布局
 * 根据屏幕宽度自动调整列数
 */
@Composable
fun <T> ResponsiveGridLayout(
    items: List<T>,
    modifier: Modifier = Modifier,
    gap: Dp = 16.dp,
    itemContent: @Composable (item: T, isWide: Boolean) -> Unit
) {
    ResponsiveContainer(modifier = modifier) { maxWidth, isWide ->
        val columnCount = responsiveColumnCount(maxWidth)
        val rows = items.chunked(columnCount)

        Column(
            verticalArrangement = Arrangement.spacedBy(gap)
        ) {
            rows.forEach { rowItems ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(gap)
                ) {
                    rowItems.forEach { item ->
                        Column(modifier = Modifier.weight(1f)) {
                            itemContent(item, isWide)
                        }
                    }
                    // 填充剩余空间
                    if (rowItems.size < columnCount) {
                        repeat(columnCount - rowItems.size) {
                            Column(modifier = Modifier.weight(1f)) {}
                        }
                    }
                }
            }
        }
    }
}

/**
 * 最大宽度约束容器
 * 限制内容最大宽度，在大屏幕上居中显示
 */
@Composable
fun MaxWidthContainer(
    maxContentWidth: Dp = 840.dp,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    BoxWithConstraints(modifier = modifier.fillMaxWidth()) {
        val contentModifier = if (maxWidth > maxContentWidth) {
            Modifier
                .fillMaxWidth()
                .padding(horizontal = (maxWidth - maxContentWidth) / 2)
        } else {
            Modifier.fillMaxWidth()
        }
        Column(modifier = contentModifier) {
            content()
        }
    }
}

/**
 * iPad双面板布局
 * 横屏时显示主从双面板，竖屏时显示单面板
 *
 * @param masterContent 主面板内容（列表/导航）
 * @param detailContent 从面板内容（详情）
 * @param masterWidthFraction 主面板宽度占比（默认0.4）
 */
@Composable
fun TwoPaneLayout(
    modifier: Modifier = Modifier,
    masterWidthFraction: Float = 0.4f,
    gap: Dp = 16.dp,
    masterContent: @Composable () -> Unit,
    detailContent: @Composable () -> Unit
) {
    BoxWithConstraints(modifier = modifier.fillMaxSize()) {
        val maxWidth = maxWidth
        val maxHeight = maxHeight
        val isLandscape = maxWidth > maxHeight
        val useTwoPane = shouldUseTwoPaneLayout(maxWidth, maxHeight)

        if (useTwoPane) {
            // 双面板模式（iPad横屏）
            Row(
                modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.spacedBy(gap)
            ) {
                // 主面板
                Column(
                    modifier = Modifier
                        .fillMaxHeight()
                        .weight(masterWidthFraction)
                ) {
                    masterContent()
                }
                // 从面板
                Column(
                    modifier = Modifier
                        .fillMaxHeight()
                        .weight(1f - masterWidthFraction)
                ) {
                    detailContent()
                }
            }
        } else {
            // 单面板模式（手机或iPad竖屏）
            Column(modifier = Modifier.fillMaxSize()) {
                masterContent()
            }
        }
    }
}

/**
 * iPad自适应卡片网格
 * 根据屏幕尺寸自动调整卡片大小和列数
 *
 * @param items 数据列表
 * @param minCardWidth 卡片最小宽度（默认160dp）
 * @param gap 卡片间距
 * @param itemContent 卡片内容
 */
@Composable
fun <T> AdaptiveCardGrid(
    items: List<T>,
    modifier: Modifier = Modifier,
    minCardWidth: Dp = 160.dp,
    gap: Dp = 16.dp,
    itemContent: @Composable (item: T) -> Unit
) {
    BoxWithConstraints(modifier = modifier.fillMaxWidth()) {
        val maxWidth = maxWidth
        val columnCount = (maxWidth / (minCardWidth + gap)).toInt().coerceAtLeast(1)
        val itemWidth = (maxWidth - gap * (columnCount - 1)) / columnCount

        Column(
            verticalArrangement = Arrangement.spacedBy(gap)
        ) {
            items.chunked(columnCount).forEach { rowItems ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(gap)
                ) {
                    rowItems.forEach { item ->
                        Box(modifier = Modifier.width(itemWidth)) {
                            itemContent(item)
                        }
                    }
                    // 填充剩余空间
                    if (rowItems.size < columnCount) {
                        repeat(columnCount - rowItems.size) {
                            Box(modifier = Modifier.width(itemWidth)) {}
                        }
                    }
                }
            }
        }
    }
}

/**
 * iPad侧边导航布局
 * 宽屏时显示侧边导航栏，窄屏时显示底部导航
 *
 * @param navigationItems 导航项列表
 * @param selectedIndex 当前选中索引
 * @param onItemSelected 导航项选中回调
 * @param content 内容区域
 */
@Composable
fun AdaptiveNavigationLayout(
    modifier: Modifier = Modifier,
    navigationItems: List<NavigationItem>,
    selectedIndex: Int,
    onItemSelected: (Int) -> Unit,
    content: @Composable () -> Unit
) {
    BoxWithConstraints(modifier = modifier.fillMaxSize()) {
        val maxWidth = maxWidth
        val useSideNavigation = maxWidth >= 840.dp

        if (useSideNavigation) {
            // 侧边导航（iPad横屏）
            Row(modifier = Modifier.fillMaxSize()) {
                // 侧边导航栏
                Column(
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(240.dp)
                ) {
                    navigationItems.forEachIndexed { index, item ->
                        NavigationItemView(
                            item = item,
                            isSelected = index == selectedIndex,
                            onClick = { onItemSelected(index) }
                        )
                    }
                }
                // 内容区域
                Box(modifier = Modifier.weight(1f)) {
                    content()
                }
            }
        } else {
            // 底部导航（手机）
            Column(modifier = Modifier.fillMaxSize()) {
                Box(modifier = Modifier.weight(1f)) {
                    content()
                }
                // 底部导航栏
                // 由调用方处理
            }
        }
    }
}

/**
 * 导航项数据类
 */
data class NavigationItem(
    val title: String,
    val icon: @Composable () -> Unit,
    val badgeCount: Int = 0
)

/**
 * 导航项视图
 */
@Composable
private fun NavigationItemView(
    item: NavigationItem,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val backgroundColor = if (isSelected) {
        androidx.compose.material3.MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)
    } else {
        androidx.compose.ui.graphics.Color.Transparent
    }

    Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 4.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(backgroundColor)
                .clickable(onClick = onClick)
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            item.icon()
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                text = item.title,
                style = MaterialTheme.typography.bodyMedium,
                color = if (isSelected) {
                    MaterialTheme.colorScheme.primary
                } else {
                    MaterialTheme.colorScheme.onSurface
                }
            )
            if (item.badgeCount > 0) {
                Spacer(modifier = Modifier.weight(1f))
                Badge {
                    Text(text = item.badgeCount.toString())
                }
            }
        }
}
