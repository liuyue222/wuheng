package com.wuheng.smart.presentation.components

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map

/**
 * Compose内存优化工具类
 * 提供重组优化、状态管理和资源清理的工具函数
 */

/**
 * 优化重组范围 - 使用key包裹列表项
 * 确保只有数据变化时才触发重组
 */
@Composable
inline fun <T> OptimizedListItem(
    item: T,
    key: Any,
    crossinline content: @Composable (T) -> Unit
) {
    key(key) {
        content(item)
    }
}

/**
 * 使用derivedStateOf优化计算密集型状态
 * 只在依赖变化时重新计算
 */
@Composable
fun <T> rememberDerivedState(
    vararg inputs: Any?,
    calculation: () -> T
): T {
    val calculationState = rememberUpdatedState(calculation)
    return remember(*inputs) {
        derivedStateOf { calculationState.value() }
    }.value
}

/**
 * 防抖状态 - 用于减少高频更新的状态重组
 * 适用于搜索输入、滑动条等场景
 */
@Composable
fun <T> rememberDebouncedState(
    value: T,
    debounceTimeMillis: Long = 300
): T {
    var debouncedValue by remember { mutableStateOf(value) }

    LaunchedEffect(value) {
        kotlinx.coroutines.delay(debounceTimeMillis)
        debouncedValue = value
    }

    return debouncedValue
}

/**
 * 节流状态 - 用于限制状态更新频率
 * 适用于滚动监听、动画等场景
 */
@Composable
fun <T> rememberThrottledState(
    value: T,
    throttleTimeMillis: Long = 100
): T {
    var throttledValue by remember { mutableStateOf(value) }
    var lastUpdateTime by remember { mutableStateOf(0L) }

    LaunchedEffect(value) {
        val currentTime = System.currentTimeMillis()
        val timeSinceLastUpdate = currentTime - lastUpdateTime

        if (timeSinceLastUpdate >= throttleTimeMillis) {
            throttledValue = value
            lastUpdateTime = currentTime
        } else {
            kotlinx.coroutines.delay(throttleTimeMillis - timeSinceLastUpdate)
            throttledValue = value
            lastUpdateTime = System.currentTimeMillis()
        }
    }

    return throttledValue
}

/**
 * 可见性追踪 - 追踪哪些列表项当前可见
 * 可用于暂停/恢复视频播放、图片加载等
 */
@Composable
fun <T> rememberVisibleItemsTracker(
    items: List<T>,
    listState: LazyListState
): Set<T> {
    val visibleItems by remember {
        derivedStateOf {
            listState.layoutInfo.visibleItemsInfo.mapNotNull { info ->
                items.getOrNull(info.index)
            }.toSet()
        }
    }
    return visibleItems
}

/**
 * 列表滚动状态监听
 * 检测列表是否正在快速滚动
 */
@Composable
fun LazyListState.isScrollingFast(threshold: Float = 2.5f): Boolean {
    var previousIndex by remember { mutableStateOf(firstVisibleItemIndex) }
    var previousScrollOffset by remember { mutableStateOf(firstVisibleItemScrollOffset) }

    val isFastScrolling by remember {
        derivedStateOf {
            val indexDelta = firstVisibleItemIndex - previousIndex
            val offsetDelta = firstVisibleItemScrollOffset - previousScrollOffset
            val velocity = kotlin.math.abs(indexDelta * 1000f + offsetDelta)

            previousIndex = firstVisibleItemIndex
            previousScrollOffset = firstVisibleItemScrollOffset

            velocity > threshold * 1000f
        }
    }

    return isFastScrolling
}

/**
 * 监听第一个可见项的变化
 */
fun LazyListState.firstVisibleItemIndexFlow(): Flow<Int> {
    return snapshotFlow { firstVisibleItemIndex }.distinctUntilChanged()
}

/**
 * 监听可见项范围的变化
 */
fun LazyListState.visibleItemsRangeFlow(): Flow<IntRange> {
    return snapshotFlow {
        layoutInfo.visibleItemsInfo.let { visibleItems ->
            if (visibleItems.isEmpty()) {
                0..0
            } else {
                visibleItems.first().index..visibleItems.last().index
            }
        }
    }.distinctUntilChanged()
}

/**
 * 智能加载更多 - 当用户滚动到距离底部一定距离时触发
 */
fun LazyListState.shouldLoadMore(buffer: Int = 3): Boolean {
    val lastVisibleItem = layoutInfo.visibleItemsInfo.lastOrNull()
    return lastVisibleItem != null && lastVisibleItem.index >= layoutInfo.totalItemsCount - 1 - buffer
}

/**
 * 智能加载更多Flow
 */
fun LazyListState.shouldLoadMoreFlow(buffer: Int = 3): Flow<Boolean> {
    return snapshotFlow { shouldLoadMore(buffer) }.distinctUntilChanged()
}

/**
 * 图片加载优化 - 根据列表滚动状态决定是否加载图片
 */
@Composable
fun shouldLoadImages(listState: LazyListState): Boolean {
    val isScrollingFast by remember {
        derivedStateOf {
            listState.isScrollInProgress &&
                    kotlin.math.abs(listState.firstVisibleItemScrollOffset) > 50
        }
    }
    return !isScrollingFast
}

/**
 * 保存滚动状态 - 使用rememberSaveable保存LazyListState
 * 在配置变更后恢复滚动位置
 */
@Composable
fun rememberOptimizedLazyListState(
    initialFirstVisibleItemIndex: Int = 0,
    initialFirstVisibleItemScrollOffset: Int = 0
): LazyListState {
    return rememberSaveable(
        saver = LazyListState.Saver
    ) {
        LazyListState(
            firstVisibleItemIndex = initialFirstVisibleItemIndex,
            firstVisibleItemScrollOffset = initialFirstVisibleItemScrollOffset
        )
    }
}

/**
 * 资源清理Effect - 在Composable离开组合时清理资源
 * 适用于清理监听器、取消协程等
 */
@Composable
fun <T> DisposableResourceEffect(
    resource: T,
    onDispose: (T) -> Unit
) {
    DisposableEffect(resource) {
        onDispose {
            onDispose(resource)
        }
    }
}

/**
 * 分页加载状态管理
 */
class PaginationState<T>(
    val items: List<T>,
    val isLoading: Boolean,
    val hasMore: Boolean,
    val error: String? = null
)

/**
 * 列表项复用优化 - 使用稳定的key
 * 适用于LazyColumn/LazyRow
 */
fun <T> List<T>.withStableKeys(keySelector: (T) -> Any): List<Pair<Any, T>> {
    return this.map { keySelector(it) to it }
}

/**
 * 避免不必要的状态提升 - 局部状态管理
 * 适用于只在组件内部使用的状态
 */
@Composable
fun <T> rememberLocalState(
    initialValue: T
): androidx.compose.runtime.MutableState<T> {
    return remember { mutableStateOf(initialValue) }
}

/**
 * 条件重组优化 - 只在条件满足时重组
 */
@Composable
inline fun ConditionalRecompose(
    condition: Boolean,
    crossinline content: @Composable () -> Unit
) {
    if (condition) {
        content()
    }
}

/**
 * 批量状态更新 - 减少多次状态更新导致的重组
 */
inline fun <T> androidx.compose.runtime.MutableState<T>.batchUpdate(
    update: T.() -> T
) {
    this.value = this.value.update()
}

/**
 * 稳定引用包装器 - 用于保持对象引用稳定
 * 避免Compose认为对象发生变化而触发重组
 */
class StableRef<T>(var value: T)

@Composable
fun <T> rememberStableRef(value: T): StableRef<T> {
    return remember { StableRef(value) }
}

/**
 * 列表预加载配置
 * 控制列表预加载的范围，减少内存占用
 */
object ListPreloadConfig {
    // 预加载的item数量
    const val PRELOAD_ITEM_COUNT = 5

    // 是否启用预加载
    const val ENABLE_PRELOAD = true
}
