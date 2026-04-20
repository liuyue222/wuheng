package com.wuheng.smart.presentation.climate

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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
 */
@Composable
fun ClimateScreen(
    viewModel: ClimateViewModel = hiltViewModel(),
    onNavigateToFloorDetail: (String) -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    ClimateScreenContent(
        uiState = uiState,
        onTabSelected = { viewModel.onTabSelected(it) },
        onTemperatureChange = { viewModel.onTemperatureChange(it) },
        onHumidityChange = { viewModel.onHumidityChange(it) },
        onFloorToggle = { id, enabled -> viewModel.onFloorToggle(id, enabled) },
        onFloorClick = { onNavigateToFloorDetail(it) },
        onRefresh = { viewModel.refreshData() }
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
    onFloorClick: (String) -> Unit,
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
                    onFloorClick = onFloorClick,
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
            onFloorClick = {},
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
                )
            ),
            onTabSelected = {},
            onTemperatureChange = {},
            onHumidityChange = {},
            onFloorToggle = { _, _ -> },
            onFloorClick = {},
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
            onFloorClick = {},
            onRefresh = {}
        )
    }
}
