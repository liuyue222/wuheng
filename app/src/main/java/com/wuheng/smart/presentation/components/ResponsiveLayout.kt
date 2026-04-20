package com.wuheng.smart.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
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
