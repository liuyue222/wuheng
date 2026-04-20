package com.wuheng.smart.presentation.water

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
 * 水系统页面 Screen - 处理ViewModel和状态管理
 * 逻辑和UI分离：Screen负责状态管理，Layout负责纯UI渲染
 */
@Composable
fun WaterScreen(
    viewModel: WaterViewModel = hiltViewModel(),
    onNavigateToDurationPicker: () -> Unit = {},
    onNavigateToSterilizationEdit: () -> Unit = {},
    onNavigateToFilterReplace: () -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    WaterScreenContent(
        uiState = uiState,
        onHotWaterModeSelected = { viewModel.onHotWaterModeSelected(it) },
        onDurationClick = onNavigateToDurationPicker,
        onSterilizationEdit = onNavigateToSterilizationEdit,
        onFilterReplaceClick = onNavigateToFilterReplace,
        onRefresh = { viewModel.refreshData() }
    )
}

/**
 * 水系统页面内容 - 纯UI，接收状态和回调
 */
@Composable
private fun WaterScreenContent(
    uiState: WaterUiState,
    onHotWaterModeSelected: (HotWaterMode) -> Unit,
    onDurationClick: () -> Unit,
    onSterilizationEdit: () -> Unit,
    onFilterReplaceClick: () -> Unit,
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
                WaterLayout(
                    uiState = uiState,
                    onHotWaterModeSelected = onHotWaterModeSelected,
                    onDurationClick = onDurationClick,
                    onSterilizationEdit = onSterilizationEdit,
                    onFilterReplaceClick = onFilterReplaceClick,
                    maxWidth = maxWidth
                )
            }
        }
    }
}

// ==================== Preview ====================

@Preview(showBackground = true, name = "水系统-正常状态", backgroundColor = 0xFFF0F4F8)
@Composable
fun WaterScreenPreview() {
    WuHengTheme {
        WaterScreenContent(
            uiState = WaterUiState(
                hotWaterMode = HotWaterMode.TEMPORARY,
                currentTemp = 55,
                temporaryDuration = 30,
                sterilizationSchedule = "每周五 02:00",
                filters = listOf(
                    FilterItem("前置过滤器", 0.98f, FilterStatus.NORMAL),
                    FilterItem("中央净水机", 0.65f, FilterStatus.NORMAL),
                    FilterItem("末端直饮", 0.15f, FilterStatus.WARNING)
                )
            ),
            onHotWaterModeSelected = {},
            onDurationClick = {},
            onSterilizationEdit = {},
            onFilterReplaceClick = {},
            onRefresh = {}
        )
    }
}

@Preview(showBackground = true, name = "水系统-宽屏720dp", widthDp = 720, backgroundColor = 0xFFF0F4F8)
@Composable
fun WaterScreenWidePreview() {
    WuHengTheme {
        WaterScreenContent(
            uiState = WaterUiState(
                hotWaterMode = HotWaterMode.TEMPORARY,
                currentTemp = 55,
                temporaryDuration = 30,
                sterilizationSchedule = "每周五 02:00",
                filters = listOf(
                    FilterItem("前置过滤器", 0.98f, FilterStatus.NORMAL),
                    FilterItem("中央净水机", 0.65f, FilterStatus.NORMAL),
                    FilterItem("末端直饮", 0.15f, FilterStatus.WARNING)
                )
            ),
            onHotWaterModeSelected = {},
            onDurationClick = {},
            onSterilizationEdit = {},
            onFilterReplaceClick = {},
            onRefresh = {}
        )
    }
}
