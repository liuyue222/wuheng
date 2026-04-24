package com.wuheng.smart.presentation.home

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.Settings
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.wuheng.smart.data.location.LocationManager
import com.wuheng.smart.data.location.WeatherManager
import com.wuheng.smart.data.model.SceneType
import com.wuheng.smart.presentation.components.ErrorRetryView
import com.wuheng.smart.presentation.components.LoadingIndicator
import com.wuheng.smart.presentation.components.ResponsiveContainer
import com.wuheng.smart.presentation.theme.WuHengTheme
import kotlinx.coroutines.launch
import timber.log.Timber

/**
 * 首页 Screen - 处理ViewModel和状态管理
 */
@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    onNavigateToResidence: () -> Unit = {},
    onNavigateToHouseList: () -> Unit = {}
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val locationManager = remember { LocationManager(context) }
    val weatherManager = remember { WeatherManager() }

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val sceneListState by viewModel.sceneListState.collectAsStateWithLifecycle()

    // 控制弹窗显示状态
    var showVacationDialog by remember { mutableStateOf(false) }
    var showModeConfirmDialog by remember { mutableStateOf<ClimateMode?>(null) }
    var showPermissionDeniedDialog by remember { mutableStateOf(false) }
    var showHouseSelectorDialog by remember { mutableStateOf(false) }

    // 模拟房产列表（实际应从API获取）
    val houseList = remember {
        listOf(
            MyHouseInfo("5", "未来科技城公寓", "浙江省杭州市余杭区"),
            MyHouseInfo("6", "西湖一号院", "浙江省杭州市西湖区")
        )
    }

    // 检查是否有度假模式场景
    val hasVacationScene = (sceneListState as? com.wuheng.smart.presentation.base.UiDataState.Success)?.data?.any {
        it.sceneType == "vacation"
    } ?: false

    // 定位权限请求
    val locationPermissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        when {
            permissions[Manifest.permission.ACCESS_FINE_LOCATION] == true ||
                    permissions[Manifest.permission.ACCESS_COARSE_LOCATION] == true -> {
                // 权限已获取，开始定位
                scope.launch {
                    updateLocationAndWeather(context, locationManager, weatherManager, viewModel)
                }
            }
            else -> {
                // 权限被拒绝
                showPermissionDeniedDialog = true
                viewModel.updateLocation("请开启定位权限")
            }
        }
    }

    // 首次进入时检查并申请定位权限
    LaunchedEffect(Unit) {
        when {
            locationManager.hasLocationPermission() -> {
                // 已有权限，直接获取位置
                updateLocationAndWeather(context, locationManager, weatherManager, viewModel)
            }
            else -> {
                // 申请权限
                locationPermissionLauncher.launch(
                    arrayOf(
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    )
                )
            }
        }
    }

    HomeScreenContent(
        uiState = uiState,
        hasVacationScene = hasVacationScene,
        onModeSelected = { mode ->
            // 检查是否需要二次确认
            if (needsModeConfirmation(uiState.currentMode, mode)) {
                showModeConfirmDialog = mode
            } else {
                // 直接切换
                viewModel.updateMode(mode)
            }
        },
        onSceneSelected = { sceneType ->
            // 只更新场景，不刷新整个页面
            viewModel.onSceneSelected(sceneType)
        },
        onVacationModeClick = { showVacationDialog = true },
        onResidenceClick = { showHouseSelectorDialog = true },
        onRefresh = {
            viewModel.refreshData()
            // 刷新时也更新位置
            scope.launch {
                if (locationManager.hasLocationPermission()) {
                    updateLocationAndWeather(context, locationManager, weatherManager, viewModel)
                }
            }
        }
    )

    // 模式切换确认对话框
    showModeConfirmDialog?.let { targetMode ->
        ModeSwitchConfirmDialog(
            fromMode = uiState.currentMode,
            toMode = targetMode,
            onConfirm = {
                viewModel.updateMode(targetMode)
                showModeConfirmDialog = null
            },
            onDismiss = { showModeConfirmDialog = null }
        )
    }

    // 度假模式底部浮层
    if (showVacationDialog) {
        VacationModeBottomSheet(
            onDismiss = { showVacationDialog = false },
            onConfirm = { returnDateTime ->
                // TODO: 调用API设置度假模式
                showVacationDialog = false
            }
        )
    }

    // 权限被拒绝对话框
    if (showPermissionDeniedDialog) {
        AlertDialog(
            onDismissRequest = { showPermissionDeniedDialog = false },
            title = { Text("需要定位权限") },
            text = { Text("为了获取您所在位置的天气信息，需要开启定位权限。") },
            confirmButton = {
                TextButton(
                    onClick = {
                        showPermissionDeniedDialog = false
                        // 跳转到设置页
                        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                            data = Uri.fromParts("package", context.packageName, null)
                        }
                        context.startActivity(intent)
                    }
                ) {
                    Text("去设置")
                }
            },
            dismissButton = {
                TextButton(onClick = { showPermissionDeniedDialog = false }) {
                    Text("取消")
                }
            }
        )
    }

    // 房产选择对话框
    if (showHouseSelectorDialog) {
        HouseSelectorDialog(
            houses = houseList,
            currentHouseId = "5", // TODO: 从TokenManager获取当前房屋ID
            onHouseSelected = { house ->
                // TODO: 切换房屋
                showHouseSelectorDialog = false
            },
            onDismiss = { showHouseSelectorDialog = false }
        )
    }
}

/**
 * 检查模式切换是否需要二次确认
 * 任何模式切换都需要确认，因为涉及全屋水系统
 */
private fun needsModeConfirmation(fromMode: ClimateMode, toMode: ClimateMode): Boolean {
    // 相同模式不需要确认
    if (fromMode == toMode) return false

    // 任何不同模式之间的切换都需要确认
    return true
}

/**
 * 模式切换确认对话框
 */
@Composable
private fun ModeSwitchConfirmDialog(
    fromMode: ClimateMode,
    toMode: ClimateMode,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    val modeName = when (toMode) {
        ClimateMode.COOLING -> "制冷"
        ClimateMode.HEATING -> "制热"
        ClimateMode.VENTILATION -> "通风"
    }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("切换系统模式") },
        text = {
            Text(
                "确定要切换到【$modeName】模式吗？\n\n" +
                        "此操作涉及全屋水系统切换，可能需要几分钟时间完成。"
            )
        },
        confirmButton = {
            Button(
                onClick = onConfirm,
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
            ) {
                Text("确认切换")
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
 * 更新位置和天气
 */
private suspend fun updateLocationAndWeather(
    context: android.content.Context,
    locationManager: LocationManager,
    weatherManager: WeatherManager,
    viewModel: HomeViewModel
) {
    try {
        // 先显示定位中
        viewModel.updateLocation("定位中...")

        // 获取位置（现在总是返回有效地址，不会失败）
        val (address, location) = locationManager.getFormattedLocation()

        // 更新位置
        viewModel.updateLocation(address)

        // 强制模拟雨天
        viewModel.updateWeather(
            temperature = 22,
            weather = "雨",
            aqi = 45,
            pm25 = 18,
            humidity = 85
        )
    } catch (e: Exception) {
        // 异常时使用默认地址（理论上不会走到这里，因为LocationManager已处理）
        Timber.e(e, "更新位置和天气异常")
        viewModel.updateLocation("杭州市 · 余杭区")
        viewModel.updateWeather(
            temperature = 22,
            weather = "雨",
            aqi = 45,
            pm25 = 18,
            humidity = 85
        )
    }
}

/**
 * 首页内容 - 纯UI，接收状态和回调
 */
@Composable
private fun HomeScreenContent(
    uiState: HomeUiState,
    hasVacationScene: Boolean = false,
    onModeSelected: (ClimateMode) -> Unit,
    onSceneSelected: (SceneType) -> Unit,
    onVacationModeClick: () -> Unit,
    onResidenceClick: () -> Unit,
    onRefresh: () -> Unit
) {
    ResponsiveContainer { maxWidth, _ ->
        when {
            uiState.isLoading && uiState.residenceName.isEmpty() -> {
                LoadingIndicator()
            }
            uiState.errorMessage != null && uiState.residenceName.isEmpty() -> {
                ErrorRetryView(
                    message = uiState.errorMessage,
                    onRetry = onRefresh
                )
            }
            else -> {
                HomeLayout(
                    uiState = uiState,
                    onModeSelected = onModeSelected,
                    onSceneSelected = onSceneSelected,
                    onVacationModeClick = onVacationModeClick,
                    onResidenceClick = onResidenceClick,
                    vacationModeEnabled = hasVacationScene,
                    vacationStartTime = null,
                    maxWidth = maxWidth
                )
            }
        }
    }
}

// ==================== Preview ====================

@Preview(showBackground = true, name = "首页-正常状态", backgroundColor = 0xFFF0F4F8)
@Composable
fun HomeScreenPreview() {
    WuHengTheme {
        HomeScreenContent(
            uiState = HomeUiState(
                location = "杭州市 西湖区",
                outdoorTemp = 26,
                weather = "多云",
                aqi = 35,
                pm25 = 12,
                outdoorHumidity = 65,
                residenceName = "西湖一号院",
                currentMode = ClimateMode.COOLING,
                indoorTemp = "24.5",
                indoorHumidity = "48",
                co2 = 420,
                tovc = "0.6",
                scenes = listOf(
                    SceneItem(SceneType.MEETING, "会客模式", false),
                    SceneItem(SceneType.AWAY, "离家模式", false),
                    SceneItem(SceneType.SLEEP, "睡眠模式", false),
                    SceneItem(SceneType.GUARD, "ECO节能", false)
                )
            ),
            hasVacationScene = true,
            onModeSelected = {},
            onSceneSelected = {},
            onVacationModeClick = {},
            onResidenceClick = {},
            onRefresh = {}
        )
    }
}

@Preview(showBackground = true, name = "首页-加载中", backgroundColor = 0xFFF0F4F8)
@Composable
fun HomeScreenLoadingPreview() {
    WuHengTheme {
        HomeScreenContent(
            uiState = HomeUiState(isLoading = true),
            onModeSelected = {},
            onSceneSelected = {},
            onVacationModeClick = {},
            onResidenceClick = {},
            onRefresh = {}
        )
    }
}

@Preview(showBackground = true, name = "首页-错误", backgroundColor = 0xFFF0F4F8)
@Composable
fun HomeScreenErrorPreview() {
    WuHengTheme {
        HomeScreenContent(
            uiState = HomeUiState(errorMessage = "网络连接失败，请重试"),
            onModeSelected = {},
            onSceneSelected = {},
            onVacationModeClick = {},
            onResidenceClick = {},
            onRefresh = {}
        )
    }
}
