package com.wuheng.smart.presentation.water

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.wuheng.smart.presentation.base.UiDataState
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
    onNavigateToFilterReplace: () -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val sterilizationState by viewModel.sterilizationState.collectAsStateWithLifecycle()
    val filterReplaceState by viewModel.filterReplaceState.collectAsStateWithLifecycle()

    // 弹窗状态
    var showSterilizationDialog by remember { mutableStateOf(false) }
    var showFilterReplaceDialog by remember { mutableStateOf(false) }
    var showSuccessSnackbar by remember { mutableStateOf(false) }

    // 处理杀菌预约结果
    LaunchedEffect(sterilizationState) {
        when (sterilizationState) {
            is UiDataState.Success -> {
                showSterilizationDialog = false
                viewModel.resetSterilizationState()
            }
            else -> {}
        }
    }

    // 处理滤芯预约结果
    LaunchedEffect(filterReplaceState) {
        when (filterReplaceState) {
            is UiDataState.Success -> {
                showFilterReplaceDialog = false
                showSuccessSnackbar = true
                viewModel.resetFilterReplaceState()
            }
            else -> {}
        }
    }

    // 成功提示 Snackbar
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(showSuccessSnackbar) {
        if (showSuccessSnackbar) {
            snackbarHostState.showSnackbar(
                message = "滤芯更换预约成功",
                duration = SnackbarDuration.Short
            )
            showSuccessSnackbar = false
        }
    }

    // 使用remember缓存回调函数，避免每次重组都创建新的lambda导致重组闪烁
    val onHotWaterModeSelected by remember(viewModel) {
        mutableStateOf<(HotWaterMode) -> Unit>({ mode ->
            viewModel.onHotWaterModeSelected(mode)
        })
    }

    val onSterilizationEdit by remember { mutableStateOf<() -> Unit>({ showSterilizationDialog = true }) }
    val onFilterReplaceClick by remember { mutableStateOf<() -> Unit>({ showFilterReplaceDialog = true }) }
    val onRefresh by remember(viewModel) {
        mutableStateOf<() -> Unit>({ viewModel.refreshData() })
    }

    val onConfirmSterilization by remember(viewModel) {
        mutableStateOf<(Int, Int, Int) -> Unit>({ dayOfWeek, hour, minute ->
            viewModel.updateSterilizationSchedule(dayOfWeek, hour, minute)
        })
    }

    val onDismissSterilization by remember(viewModel) {
        mutableStateOf<() -> Unit>({
            showSterilizationDialog = false
            viewModel.resetSterilizationState()
        })
    }

    val onConfirmFilterReplace by remember(viewModel) {
        mutableStateOf<(String, String, String, String) -> Unit>({ filterId, contactName, contactPhone, appointmentDate ->
            viewModel.bookFilterReplaceWithState(
                filterId = filterId,
                contactName = contactName,
                contactPhone = contactPhone,
                appointmentDate = appointmentDate
            )
        })
    }

    val onDismissFilterReplace by remember(viewModel) {
        mutableStateOf<() -> Unit>({
            showFilterReplaceDialog = false
            viewModel.resetFilterReplaceState()
        })
    }

    @OptIn(ExperimentalMaterial3Api::class)
    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {
            WaterScreenContent(
                uiState = uiState,
                onHotWaterModeSelected = onHotWaterModeSelected,
                onDurationClick = onNavigateToDurationPicker,
                onSterilizationEdit = onSterilizationEdit,
                onFilterReplaceClick = onFilterReplaceClick,
                onRefresh = onRefresh
            )
        }
    }

    // 热力杀菌时间选择弹窗
    if (showSterilizationDialog) {
        SterilizationTimePickerDialog(
            currentSchedule = uiState.sterilizationSchedule,
            sterilizationState = sterilizationState,
            onConfirm = onConfirmSterilization,
            onDismiss = onDismissSterilization
        )
    }

    // 滤芯预约更换弹窗
    if (showFilterReplaceDialog) {
        FilterReplaceDialog(
            filters = uiState.filters,
            filterReplaceState = filterReplaceState,
            onConfirm = onConfirmFilterReplace,
            onDismiss = onDismissFilterReplace
        )
    }
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
                    FilterItem("1", "前置过滤器", 0.98f, FilterUiStatus.NORMAL),
                    FilterItem("2", "中央净水机", 0.65f, FilterUiStatus.NORMAL),
                    FilterItem("3", "末端直饮", 0.15f, FilterUiStatus.WARNING)
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
                    FilterItem("1", "前置过滤器", 0.98f, FilterUiStatus.NORMAL),
                    FilterItem("2", "中央净水机", 0.65f, FilterUiStatus.NORMAL),
                    FilterItem("3", "末端直饮", 0.15f, FilterUiStatus.WARNING)
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
