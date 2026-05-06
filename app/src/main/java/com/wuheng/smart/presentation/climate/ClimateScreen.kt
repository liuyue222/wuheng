package com.wuheng.smart.presentation.climate

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.wuheng.smart.presentation.components.ErrorRetryView
import com.wuheng.smart.presentation.components.LoadingIndicator
import com.wuheng.smart.presentation.components.ResponsiveContainer
import com.wuheng.smart.presentation.theme.WuHengTheme

/**
 * 冷暖舒适页面 Screen - 处理ViewModel和状态管理
 * 逻辑和UI分离：Screen负责状态管理，Layout负责纯UI渲染
 *
 * 性能优化：
 * 1. 使用 remember 缓存回调函数，避免每次重组时创建新的lambda
 * 2. 使用 derivedStateOf 优化状态计算（在Layout中实现）
 * 3. 滑块拖动时使用 rememberUpdatedState 确保回调始终引用最新值
 */
@Composable
fun ClimateScreen(
    viewModel: ClimateViewModel = hiltViewModel(),
    onNavigateToFloorDetail: (String) -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val onTabSelected = remember(viewModel) { { tab: ClimateTab -> viewModel.onTabSelected(tab) } }
    val onTemperatureChange = remember(viewModel) { { temp: Float -> viewModel.onTemperatureChange(temp) } }
    val onHumidityChange = remember(viewModel) { { humidity: Float -> viewModel.onHumidityChange(humidity) } }
    val onFloorToggle = remember(viewModel) { { id: String, enabled: Boolean -> viewModel.onFloorToggle(id, enabled) } }
    val onFloorSelected = remember(viewModel) { { id: String -> viewModel.onFloorSelected(id) } }
    val onRefresh = remember(viewModel) { { viewModel.refreshData() } }

    ClimateScreenContent(
        uiState = uiState,
        onTabSelected = onTabSelected,
        onTemperatureChange = onTemperatureChange,
        onHumidityChange = onHumidityChange,
        onFloorToggle = onFloorToggle,
        onFloorSelected = onFloorSelected,
        onRefresh = onRefresh
    )
}

/**
 * 冷暖舒适页面内容 - 纯UI，接收状态和回调
 */
@Composable
private fun ClimateScreenContent(
    uiState: ClimateUiState,
    onTabSelected: (ClimateTab) -> Unit,
    onTemperatureChange: (Float) -> Unit,
    onHumidityChange: (Float) -> Unit,
    onFloorToggle: (String, Boolean) -> Unit,
    onFloorSelected: (String) -> Unit,
    onRefresh: () -> Unit
) {
    ResponsiveContainer { maxWidth, _ ->
        when {
            uiState.isLoading -> {
                LoadingIndicator()
            }
            uiState.errorMessage != null -> {
                ErrorRetryView(
                    message = uiState.errorMessage,
                    onRetry = onRefresh
                )
            }
            else -> {
                ClimateLayout(
                    uiState = uiState,
                    onTabSelected = onTabSelected,
                    onTemperatureChange = onTemperatureChange,
                    onHumidityChange = onHumidityChange,
                    onFloorToggle = onFloorToggle,
                    onFloorSelected = onFloorSelected,
                    maxWidth = maxWidth
                )
            }
        }
    }
}

// ==================== Preview ====================

@Preview(showBackground = true, name = "冷暖舒适-全屋模式", backgroundColor = 0xFFF0F4F8)
@Composable
fun ClimateScreenWholeHousePreview() {
    WuHengTheme {
        ClimateScreenContent(
            uiState = ClimateUiState(
                selectedTab = ClimateTab.WHOLE_HOUSE,
                temperature = 24.5f,
                humidity = 45f
            ),
            onTabSelected = {},
            onTemperatureChange = {},
            onHumidityChange = {},
            onFloorToggle = { _, _ -> },
            onFloorSelected = {},
            onRefresh = {}
        )
    }
}

@Preview(showBackground = true, name = "冷暖舒适-楼层模式", backgroundColor = 0xFFF0F4F8)
@Composable
fun ClimateScreenFloorPreview() {
    WuHengTheme {
        ClimateScreenContent(
            uiState = ClimateUiState(
                selectedTab = ClimateTab.FLOOR,
                floors = listOf(
                    FloorItem(
                        id = "b1",
                        name = "B1地下室",
                        isEnabled = false,
                        devices = listOf(
                            FloorDevice("温控器", "开启"),
                            FloorDevice("新风面板", "关闭")
                        )
                    ),
                    FloorItem(
                        id = "1f",
                        name = "1F 一层",
                        isMainControl = true,
                        isEnabled = true,
                        devices = listOf(
                            FloorDevice("温控器", "运行中", "24°C"),
                            FloorDevice("新风面板", "运行中")
                        )
                    )
                ),
                rooms = listOf(
                    RoomUiItem(
                        id = "1", name = "客厅", roomType = "living",
                        area = "45.00", deviceCount = 2, currentTemp = 23.5f,
                        targetTemp = 24f, humidity = 45f, isOnline = true
                    ),
                    RoomUiItem(
                        id = "2", name = "主卧", roomType = "bedroom",
                        area = "25.00", deviceCount = 1, currentTemp = 22f,
                        targetTemp = 26f, humidity = 50f, isOnline = true
                    )
                )
            ),
            onTabSelected = {},
            onTemperatureChange = {},
            onHumidityChange = {},
            onFloorToggle = { _, _ -> },
            onFloorSelected = {},
            onRefresh = {}
        )
    }
}

@Preview(showBackground = true, name = "冷暖舒适-宽屏720dp", widthDp = 720, backgroundColor = 0xFFF0F4F8)
@Composable
fun ClimateScreenWidePreview() {
    WuHengTheme {
        ClimateScreenContent(
            uiState = ClimateUiState(
                selectedTab = ClimateTab.WHOLE_HOUSE,
                temperature = 24.5f,
                humidity = 45f
            ),
            onTabSelected = {},
            onTemperatureChange = {},
            onHumidityChange = {},
            onFloorToggle = { _, _ -> },
            onFloorSelected = {},
            onRefresh = {}
        )
    }
}
