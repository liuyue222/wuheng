@file:OptIn(ExperimentalMaterial3Api::class)

package com.wuheng.smart.presentation.device

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.wuheng.smart.data.model.DeviceData
import com.wuheng.smart.data.model.DeviceInfo
import com.wuheng.smart.data.model.DeviceRunStatus
import com.wuheng.smart.presentation.base.UiDataState
import com.wuheng.smart.presentation.theme.*
import java.text.SimpleDateFormat
import java.util.*

/**
 * 设备详情页面 Composable (完善版)
 *
 * 布局结构：
 * - 顶部导航栏: 返回按钮 + 标题"设备详情" + 更多按钮
 * - 设备基本信息卡片: 设备名称、型号、房间、在线状态
 * - 设备实时数据卡片: 温度、湿度、CO2、PM2.5、VOC
 * - 设备控制面板: 开关、温度调节、模式切换、风速调节
 * - 24小时趋势图表: 温度/湿度历史数据
 * - 设备设置: 重命名、删除、恢复出厂
 *
 * 完成度: 100%
 */
@Composable
fun DeviceDetailScreen(
    deviceId: String,
    viewModel: DeviceDetailViewModel = hiltViewModel(),
    onNavigateBack: () -> Unit = {},
    onNavigateToEdit: (String) -> Unit = {}
) {
    val deviceInfoState by viewModel.deviceInfoState.collectAsStateWithLifecycle()
    val deviceDataState by viewModel.deviceDataState.collectAsStateWithLifecycle()
    val historyDataState by viewModel.historyDataState.collectAsStateWithLifecycle()
    val operationState by viewModel.operationState.collectAsStateWithLifecycle()

    // 将String类型的deviceId转换为Int
    val deviceIdInt = remember(deviceId) { deviceId.toIntOrNull() ?: 0 }

    // 加载设备数据
    LaunchedEffect(deviceId) {
        viewModel.refreshDeviceData(deviceIdInt)
    }

    // 显示操作结果
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(operationState) {
        when (operationState) {
            is UiDataState.Success -> {
                snackbarHostState.showSnackbar("操作成功")
                viewModel.resetOperationState()
            }
            is UiDataState.Error -> {
                val error = (operationState as UiDataState.Error).exception
                snackbarHostState.showSnackbar("操作失败: ${error.message}")
                viewModel.resetOperationState()
            }
            else -> {}
        }
    }

    DeviceDetailContent(
        deviceInfoState = deviceInfoState,
        deviceDataState = deviceDataState,
        historyDataState = historyDataState,
        onNavigateBack = onNavigateBack,
        onNavigateToEdit = { onNavigateToEdit(deviceId) },
        onRefresh = { viewModel.refreshDeviceData(deviceIdInt) },
        onPowerToggle = { viewModel.togglePower(deviceIdInt, it) },
        onFanSpeedChange = { viewModel.setFanSpeed(deviceIdInt, it) },
        onTemperatureChange = { viewModel.setTemperature(deviceIdInt, it) },
        onModeChange = { viewModel.setMode(deviceIdInt, it) },
        onRenameDevice = { viewModel.renameDevice(deviceIdInt, it) },
        onResetDevice = { viewModel.resetDevice(deviceIdInt) },
        onDeleteDevice = { viewModel.deleteDevice(deviceIdInt) },
        snackbarHostState = snackbarHostState
    )
}

/**
 * 设备详情页面内容
 */
@Composable
fun DeviceDetailContent(
    deviceInfoState: UiDataState<DeviceInfo>,
    deviceDataState: UiDataState<DeviceData>,
    historyDataState: UiDataState<List<HistoryDataPoint>>,
    onNavigateBack: () -> Unit = {},
    onNavigateToEdit: () -> Unit = {},
    onRefresh: () -> Unit = {},
    onPowerToggle: (Boolean) -> Unit = {},
    onFanSpeedChange: (Int) -> Unit = {},
    onTemperatureChange: (String) -> Unit = {},
    onModeChange: (String) -> Unit = {},
    onRenameDevice: (String) -> Unit = {},
    onResetDevice: () -> Unit = {},
    onDeleteDevice: () -> Unit = {},
    snackbarHostState: SnackbarHostState = remember { SnackbarHostState() }
) {
    var showSettingsMenu by remember { mutableStateOf(false) }
    var showRenameDialog by remember { mutableStateOf(false) }
    var showResetConfirmDialog by remember { mutableStateOf(false) }
    var showDeleteConfirmDialog by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "设备详情",
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
                actions = {
                    // 更多菜单按钮
                    Box {
                        IconButton(onClick = { showSettingsMenu = true }) {
                            Icon(
                                imageVector = Icons.Filled.MoreVert,
                                contentDescription = "更多",
                                tint = TextPrimaryLight
                            )
                        }

                        DropdownMenu(
                            expanded = showSettingsMenu,
                            onDismissRequest = { showSettingsMenu = false },
                            modifier = Modifier.background(SurfaceLight)
                        ) {
                            DropdownMenuItem(
                                text = { Text("重命名") },
                                leadingIcon = {
                                    Icon(Icons.Filled.Edit, null, modifier = Modifier.size(20.dp))
                                },
                                onClick = {
                                    showSettingsMenu = false
                                    showRenameDialog = true
                                }
                            )
                            DropdownMenuItem(
                                text = { Text("恢复出厂设置") },
                                leadingIcon = {
                                    Icon(Icons.Filled.Refresh, null, modifier = Modifier.size(20.dp))
                                },
                                onClick = {
                                    showSettingsMenu = false
                                    showResetConfirmDialog = true
                                }
                            )
                            Divider()
                            DropdownMenuItem(
                                text = { Text("删除设备", color = ErrorRed) },
                                leadingIcon = {
                                    Icon(
                                        Icons.Filled.Delete,
                                        null,
                                        modifier = Modifier.size(20.dp),
                                        tint = ErrorRed
                                    )
                                },
                                onClick = {
                                    showSettingsMenu = false
                                    showDeleteConfirmDialog = true
                                }
                            )
                        }
                    }
                },
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = BackgroundLight
                )
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { paddingValues ->
        when (deviceInfoState) {
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
                            style = MaterialTheme.typography.bodyLarge,
                            color = TextSecondaryLight
                        )
                        Button(
                            onClick = onRefresh,
                            colors = ButtonDefaults.buttonColors(containerColor = PrimaryBlue)
                        ) {
                            Text("重试")
                        }
                    }
                }
            }
            is UiDataState.Success -> {
                val deviceInfo = (deviceInfoState as UiDataState.Success<DeviceInfo>).data
                val deviceData = (deviceDataState as? UiDataState.Success<DeviceData>)?.data
                val historyData = (historyDataState as? UiDataState.Success<List<HistoryDataPoint>>)?.data

                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                        .background(BackgroundLight)
                        .padding(horizontal = page_margin_horizontal),
                    verticalArrangement = Arrangement.spacedBy(spacing_lg)
                ) {
                    item { Spacer(modifier = Modifier.height(spacing_sm)) }

                    // 设备基本信息卡片
                    item {
                        DeviceInfoCard(
                            deviceInfo = deviceInfo,
                            deviceData = deviceData,
                            onPowerToggle = onPowerToggle
                        )
                    }

                    // 设备控制面板
                    item {
                        DeviceControlPanel(
                            deviceData = deviceData,
                            onTemperatureChange = onTemperatureChange,
                            onModeChange = onModeChange,
                            onFanSpeedChange = onFanSpeedChange
                        )
                    }

                    // 设备实时数据卡片
                    item {
                        DeviceDataCard(deviceData = deviceData)
                    }

                    // 24小时趋势图表
                    item {
                        HistoryDataChart(
                            historyData = historyData,
                            isLoading = historyDataState is UiDataState.Loading
                        )
                    }

                    // 设备信息详情
                    item {
                        DeviceDetailInfoCard(deviceInfo = deviceInfo)
                    }

                    item { Spacer(modifier = Modifier.height(spacing_lg)) }
                }
            }
        }
    }

    // 重命名对话框
    if (showRenameDialog) {
        val deviceInfo = (deviceInfoState as? UiDataState.Success<DeviceInfo>)?.data
        RenameDeviceDialog(
            currentName = deviceInfo?.deviceName ?: "",
            onConfirm = { newName ->
                onRenameDevice(newName)
                showRenameDialog = false
            },
            onDismiss = { showRenameDialog = false }
        )
    }

    // 恢复出厂确认对话框
    if (showResetConfirmDialog) {
        ConfirmDialog(
            title = "恢复出厂设置",
            message = "确定要恢复出厂设置吗？此操作将清除所有自定义配置。",
            confirmText = "恢复",
            confirmColor = WarningYellow,
            onConfirm = {
                onResetDevice()
                showResetConfirmDialog = false
            },
            onDismiss = { showResetConfirmDialog = false }
        )
    }

    // 删除设备确认对话框
    if (showDeleteConfirmDialog) {
        ConfirmDialog(
            title = "删除设备",
            message = "确定要删除此设备吗？删除后需要重新添加。",
            confirmText = "删除",
            confirmColor = ErrorRed,
            onConfirm = {
                onDeleteDevice()
                showDeleteConfirmDialog = false
            },
            onDismiss = { showDeleteConfirmDialog = false }
        )
    }
}

/**
 * 设备基本信息卡片 (完善版)
 */
@Composable
private fun DeviceInfoCard(
    deviceInfo: DeviceInfo,
    deviceData: DeviceData?,
    onPowerToggle: (Boolean) -> Unit
) {
    var powerState by remember { mutableStateOf(deviceInfo.runStatus == DeviceRunStatus.RUNNING.value) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(
                elevation = elevation_md,
                shape = RoundedCornerShape(corner_md),
                ambientColor = ShadowLight,
                spotColor = ShadowLight
            )
            .clip(RoundedCornerShape(corner_md))
            .background(SurfaceLight)
            .padding(card_padding_large)
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(spacing_lg)) {
            // 标题行：设备名称 + 电源开关
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = deviceInfo.deviceName,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = TextPrimaryLight,
                        fontSize = text_h2_size
                    )
                    Spacer(modifier = Modifier.height(spacing_xs))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = deviceInfo.roomName,
                            style = MaterialTheme.typography.bodyMedium,
                            color = TextSecondaryLight,
                            fontSize = text_body_size
                        )
                        Spacer(modifier = Modifier.width(spacing_sm))
                        // 在线状态指示
                        DeviceStatusIndicator(
                            isOnline = deviceInfo.onlineStatus == 1,
                            runStatus = deviceInfo.runStatus
                        )
                    }
                }

                // 电源开关
                Switch(
                    checked = powerState,
                    onCheckedChange = {
                        powerState = it
                        onPowerToggle(it)
                    },
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = Color.White,
                        checkedTrackColor = SwitchChecked,
                        uncheckedThumbColor = Color.White,
                        uncheckedTrackColor = SwitchUnchecked
                    ),
                    modifier = Modifier.width(switch_width)
                )
            }

            // 分隔线
            Divider(color = DividerLight, thickness = 1.dp)

            // 温度大显示 (如果设备有温度数据)
            if (deviceData != null && powerState) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    BigTemperatureDisplay(
                        label = "当前温度",
                        value = deviceData.temperature,
                        unit = "°C",
                        color = TemperatureValueColor
                    )
                    Divider(
                        modifier = Modifier
                            .height(40.dp)
                            .width(1.dp),
                        color = DividerLight
                    )
                    BigTemperatureDisplay(
                        label = "当前湿度",
                        value = deviceData.humidity,
                        unit = "%",
                        color = HumidityValueColor
                    )
                }
            }
        }
    }
}

/**
 * 设备状态指示器
 */
@Composable
private fun DeviceStatusIndicator(isOnline: Boolean, runStatus: String) {
    val (color, text) = when {
        !isOnline -> TextTertiaryLight to "离线"
        runStatus == DeviceRunStatus.RUNNING.value -> SuccessGreen to "运行中"
        runStatus == DeviceRunStatus.STANDBY.value -> WarningYellow to "待机"
        runStatus == DeviceRunStatus.ERROR.value -> ErrorRed to "故障"
        else -> TextTertiaryLight to "未知"
    }

    Row(verticalAlignment = Alignment.CenterVertically) {
        Box(
            modifier = Modifier
                .size(6.dp)
                .clip(CircleShape)
                .background(color)
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = text,
            style = MaterialTheme.typography.bodySmall,
            color = color,
            fontSize = 12.sp
        )
    }
}

/**
 * 大温度显示
 */
@Composable
private fun BigTemperatureDisplay(
    label: String,
    value: String,
    unit: String,
    color: Color
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall,
            color = TextSecondaryLight,
            fontSize = 12.sp
        )
        Spacer(modifier = Modifier.height(spacing_xs))
        Row(verticalAlignment = Alignment.Bottom) {
            Text(
                text = value,
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = color,
                fontSize = 32.sp
            )
            Text(
                text = unit,
                style = MaterialTheme.typography.bodyMedium,
                color = color,
                fontSize = 14.sp,
                modifier = Modifier.padding(bottom = 4.dp)
            )
        }
    }
}

/**
 * 设备控制面板 (新增)
 */
@Composable
private fun DeviceControlPanel(
    deviceData: DeviceData?,
    onTemperatureChange: (String) -> Unit,
    onModeChange: (String) -> Unit,
    onFanSpeedChange: (Int) -> Unit
) {
    var selectedMode by remember { mutableStateOf("cooling") }
    var tempValue by remember { mutableStateOf(24f) }
    var fanSpeed by remember { mutableStateOf(deviceData?.fanSpeed ?: 1) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(
                elevation = elevation_md,
                shape = RoundedCornerShape(corner_md),
                ambientColor = ShadowLight,
                spotColor = ShadowLight
            )
            .clip(RoundedCornerShape(corner_md))
            .background(SurfaceLight)
            .padding(card_padding_large)
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(spacing_lg)) {
            // 标题
            Text(
                text = "设备控制",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
                color = TextPrimaryLight,
                fontSize = text_h3_size
            )

            // 模式选择
            Text(
                text = "模式",
                style = MaterialTheme.typography.bodyMedium,
                color = TextSecondaryLight,
                fontSize = text_body_size
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(spacing_sm)
            ) {
                val modes = listOf(
                    "cooling" to "制冷",
                    "heating" to "制热",
                    "ventilation" to "通风",
                    "auto" to "自动"
                )

                modes.forEach { (mode, label) ->
                    val isSelected = selectedMode == mode
                    ModeButton(
                        label = label,
                        isSelected = isSelected,
                        onClick = {
                            selectedMode = mode
                            onModeChange(mode)
                        },
                        modifier = Modifier.weight(1f)
                    )
                }
            }

            Divider(color = DividerLight)

            // 温度调节
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "温度设定",
                    style = MaterialTheme.typography.bodyMedium,
                    color = TextSecondaryLight,
                    fontSize = text_body_size
                )
                Text(
                    text = "${tempValue.toInt()}°C",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold,
                    color = PrimaryBlue,
                    fontSize = text_body_size
                )
            }

            // 温度调节按钮
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = {
                        if (tempValue > 16) {
                            tempValue -= 0.5f
                            onTemperatureChange(tempValue.toString())
                        }
                    },
                    modifier = Modifier
                        .size(48.dp)
                        .background(PrimaryBlue.copy(alpha = 0.1f), CircleShape)
                ) {
                    Icon(
                        imageVector = Icons.Filled.Remove,
                        contentDescription = "降低温度",
                        tint = PrimaryBlue
                    )
                }

                // 温度滑块
                Slider(
                    value = tempValue,
                    onValueChange = { tempValue = it },
                    onValueChangeFinished = { onTemperatureChange(tempValue.toString()) },
                    valueRange = 16f..30f,
                    steps = 27,
                    colors = SliderDefaults.colors(
                        thumbColor = SliderThumb,
                        activeTrackColor = SliderActive,
                        inactiveTrackColor = SliderInactive
                    ),
                    modifier = Modifier.weight(1f)
                )

                IconButton(
                    onClick = {
                        if (tempValue < 30) {
                            tempValue += 0.5f
                            onTemperatureChange(tempValue.toString())
                        }
                    },
                    modifier = Modifier
                        .size(48.dp)
                        .background(PrimaryBlue.copy(alpha = 0.1f), CircleShape)
                ) {
                    Icon(
                        imageVector = Icons.Filled.Add,
                        contentDescription = "升高温度",
                        tint = PrimaryBlue
                    )
                }
            }

            Divider(color = DividerLight)

            // 风速控制
            Column(verticalArrangement = Arrangement.spacedBy(spacing_md)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "风速调节",
                        style = MaterialTheme.typography.bodyMedium,
                        color = TextSecondaryLight,
                        fontSize = text_body_size
                    )
                    Text(
                        text = "$fanSpeed 档",
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Medium,
                        color = PrimaryBlue,
                        fontSize = text_body_size
                    )
                }

                // 风速档位选择
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(spacing_sm)
                ) {
                    val speeds = listOf(1, 2, 3, 4, 5)
                    speeds.forEach { speed ->
                        val isSelected = fanSpeed == speed
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .clip(RoundedCornerShape(fan_speed_corner))
                                .background(
                                    if (isSelected) FanSpeedSelectedBg else FanSpeedUnselectedBg
                                )
                                .then(
                                    if (!isSelected) {
                                        Modifier.border(
                                            width = 1.dp,
                                            color = ChipUnselectedBorder,
                                            shape = RoundedCornerShape(fan_speed_corner)
                                        )
                                    } else Modifier
                                )
                                .clickable {
                                    fanSpeed = speed
                                    onFanSpeedChange(speed)
                                }
                                .padding(vertical = spacing_sm),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "$speed",
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal,
                                color = if (isSelected) FanSpeedSelectedText else FanSpeedUnselectedText,
                                fontSize = fan_speed_text_size
                            )
                        }
                    }
                }
            }
        }
    }
}

/**
 * 模式按钮
 */
@Composable
private fun ModeButton(
    label: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(corner_sm))
            .background(if (isSelected) PrimaryBlue else SurfaceVariantLight)
            .clickable(onClick = onClick)
            .padding(vertical = spacing_sm),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal,
            color = if (isSelected) Color.White else TextPrimaryLight,
            fontSize = text_body_size
        )
    }
}

/**
 * 设备实时数据卡片
 */
@Composable
private fun DeviceDataCard(deviceData: DeviceData?) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(
                elevation = elevation_md,
                shape = RoundedCornerShape(corner_md),
                ambientColor = ShadowLight,
                spotColor = ShadowLight
            )
            .clip(RoundedCornerShape(corner_md))
            .background(SurfaceLight)
            .padding(card_padding_large)
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(spacing_lg)) {
            // 标题
            Text(
                text = "实时数据",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
                color = TextPrimaryLight,
                fontSize = text_h3_size
            )

            if (deviceData == null) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = spacing_xl),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "暂无数据",
                        style = MaterialTheme.typography.bodyMedium,
                        color = TextTertiaryLight
                    )
                }
            } else {
                // 数据网格
                Column(verticalArrangement = Arrangement.spacedBy(spacing_md)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        DataItem(
                            label = "温度",
                            value = "${deviceData.temperature}°C",
                            icon = "\uD83C\uDF21",
                            color = TemperatureValueColor
                        )
                        DataItem(
                            label = "湿度",
                            value = "${deviceData.humidity}%",
                            icon = "\uD83D\uDCA7",
                            color = HumidityValueColor
                        )
                        DataItem(
                            label = "CO2",
                            value = "${deviceData.co2}",
                            unit = "ppm",
                            icon = "\uD83C\uDF3F",
                            color = getCo2Color(deviceData.co2)
                        )
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        DataItem(
                            label = "PM2.5",
                            value = "${deviceData.pm25}",
                            unit = "μg/m³",
                            icon = "\uD83D\uDCA8",
                            color = getPm25Color(deviceData.pm25)
                        )
                        DataItem(
                            label = "VOC",
                            value = "${deviceData.voc}",
                            unit = "mg/m³",
                            icon = "\uD83E\uDEA8",
                            color = getVocColor(deviceData.voc)
                        )
                        DataItem(
                            label = "风速",
                            value = "${deviceData.fanSpeed}",
                            unit = "档",
                            icon = "\uD83D\uDCA8",
                            color = PrimaryBlue
                        )
                    }
                }
            }
        }
    }
}

/**
 * 数据项组件
 */
@Composable
private fun DataItem(
    label: String,
    value: String,
    icon: String,
    color: Color,
    unit: String = ""
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(spacing_xs),
        modifier = Modifier.padding(horizontal = spacing_sm)
    ) {
        Text(
            text = icon,
            fontSize = 24.sp
        )
        Row(verticalAlignment = Alignment.Bottom) {
            Text(
                text = value,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = color,
                fontSize = text_body_large_size
            )
            if (unit.isNotEmpty()) {
                Text(
                    text = unit,
                    style = MaterialTheme.typography.bodySmall,
                    color = TextTertiaryLight,
                    fontSize = text_caption_size,
                    modifier = Modifier.padding(bottom = 2.dp)
                )
            }
        }
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall,
            color = TextSecondaryLight,
            fontSize = text_caption_size
        )
    }
}

/**
 * 24小时历史数据图表 (新增)
 */
@Composable
private fun HistoryDataChart(
    historyData: List<HistoryDataPoint>?,
    isLoading: Boolean
) {
    var selectedMetric by remember { mutableStateOf(MetricType.TEMPERATURE) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(
                elevation = elevation_md,
                shape = RoundedCornerShape(corner_md),
                ambientColor = ShadowLight,
                spotColor = ShadowLight
            )
            .clip(RoundedCornerShape(corner_md))
            .background(SurfaceLight)
            .padding(card_padding_large)
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(spacing_lg)) {
            // 标题行 + 指标切换
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "24小时趋势",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = TextPrimaryLight,
                    fontSize = text_h3_size
                )

                // 指标切换按钮
                Row(horizontalArrangement = Arrangement.spacedBy(spacing_xs)) {
                    MetricType.values().forEach { metric ->
                        val isSelected = selectedMetric == metric
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(corner_xs))
                                .background(if (isSelected) PrimaryBlue else SurfaceVariantLight)
                                .clickable { selectedMetric = metric }
                                .padding(horizontal = spacing_md, vertical = spacing_xs),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = metric.label,
                                style = MaterialTheme.typography.bodySmall,
                                fontWeight = if (isSelected) FontWeight.Medium else FontWeight.Normal,
                                color = if (isSelected) Color.White else TextSecondaryLight,
                                fontSize = 12.sp
                            )
                        }
                    }
                }
            }

            if (isLoading) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(color = PrimaryBlue, modifier = Modifier.size(32.dp))
                }
            } else if (historyData.isNullOrEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "暂无历史数据",
                        style = MaterialTheme.typography.bodyMedium,
                        color = TextTertiaryLight
                    )
                }
            } else {
                // 简化的趋势图
                SimpleTrendChart(
                    data = historyData,
                    metricType = selectedMetric,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp)
                )
            }
        }
    }
}

/**
 * 指标类型枚举
 */
enum class MetricType(val label: String) {
    TEMPERATURE("温度"),
    HUMIDITY("湿度"),
    CO2("CO2")
}

/**
 * 历史数据点
 */
data class HistoryDataPoint(
    val timestamp: Long,
    val temperature: Float,
    val humidity: Float,
    val co2: Int
)

/**
 * 简化趋势图
 */
@Composable
private fun SimpleTrendChart(
    data: List<HistoryDataPoint>,
    metricType: MetricType,
    modifier: Modifier = Modifier
) {
    val values = when (metricType) {
        MetricType.TEMPERATURE -> data.map { it.temperature }
        MetricType.HUMIDITY -> data.map { it.humidity }
        MetricType.CO2 -> data.map { it.co2.toFloat() }
    }

    val color = when (metricType) {
        MetricType.TEMPERATURE -> TemperatureValueColor
        MetricType.HUMIDITY -> HumidityValueColor
        MetricType.CO2 -> Co2ValueColor
    }

    val minValue = values.minOrNull() ?: 0f
    val maxValue = values.maxOrNull() ?: 100f
    val range = (maxValue - minValue).coerceAtLeast(1f)

    Box(modifier = modifier) {
        // 这里使用简化的柱状图表示
        val chunkedValues = values.chunked(values.size / 8 + 1)
        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.Bottom
        ) {
            chunkedValues.forEach { chunk ->
                val avgValue = chunk.average().toFloat()
                val normalizedHeight = ((avgValue - minValue) / range).coerceIn(0f, 1f)

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Bottom
                ) {
                    Box(
                        modifier = Modifier
                            .width(8.dp)
                            .fillMaxHeight(normalizedHeight * 0.8f + 0.1f)
                            .clip(RoundedCornerShape(4.dp))
                            .background(color.copy(alpha = 0.7f))
                    )
                }
            }
        }

        // 数值标签
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = String.format("%.1f", minValue),
                style = MaterialTheme.typography.labelSmall,
                color = TextTertiaryLight,
                fontSize = 10.sp
            )
            Text(
                text = String.format("%.1f", maxValue),
                style = MaterialTheme.typography.labelSmall,
                color = TextTertiaryLight,
                fontSize = 10.sp
            )
        }
    }
}

/**
 * 设备详细信息卡片 (新增)
 */
@Composable
private fun DeviceDetailInfoCard(deviceInfo: DeviceInfo) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(
                elevation = elevation_md,
                shape = RoundedCornerShape(corner_md),
                ambientColor = ShadowLight,
                spotColor = ShadowLight
            )
            .clip(RoundedCornerShape(corner_md))
            .background(SurfaceLight)
            .padding(card_padding_large)
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(spacing_md)) {
            Text(
                text = "设备信息",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
                color = TextPrimaryLight,
                fontSize = text_h3_size
            )

            Divider(color = DividerLight)

            InfoRow(label = "设备型号", value = deviceInfo.deviceModel)
            InfoRow(label = "设备编号", value = deviceInfo.deviceIdNo)
            InfoRow(label = "设备类型", value = getDeviceTypeDisplay(deviceInfo.deviceType))
            InfoRow(
                label = "在线状态",
                value = if (deviceInfo.onlineStatus == 1) "在线" else "离线",
                valueColor = if (deviceInfo.onlineStatus == 1) SuccessGreen else TextTertiaryLight
            )
            InfoRow(
                label = "运行状态",
                value = getRunStatusDisplay(deviceInfo.runStatus),
                valueColor = getRunStatusColor(deviceInfo.runStatus)
            )
        }
    }
}

/**
 * 信息行
 */
@Composable
private fun InfoRow(
    label: String,
    value: String,
    valueColor: Color = TextPrimaryLight
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            color = TextSecondaryLight,
            fontSize = text_body_size
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Medium,
            color = valueColor,
            fontSize = text_body_size
        )
    }
}

/**
 * 重命名设备对话框
 */
@Composable
private fun RenameDeviceDialog(
    currentName: String,
    onConfirm: (String) -> Unit,
    onDismiss: () -> Unit
) {
    var newName by remember { mutableStateOf(currentName) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("重命名设备") },
        text = {
            OutlinedTextField(
                value = newName,
                onValueChange = { newName = it },
                label = { Text("设备名称") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )
        },
        confirmButton = {
            TextButton(
                onClick = { onConfirm(newName) },
                enabled = newName.isNotBlank() && newName != currentName
            ) {
                Text("确定")
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
 * 确认对话框
 */
@Composable
private fun ConfirmDialog(
    title: String,
    message: String,
    confirmText: String,
    confirmColor: Color,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(title) },
        text = { Text(message) },
        confirmButton = {
            TextButton(
                onClick = onConfirm,
                colors = ButtonDefaults.textButtonColors(contentColor = confirmColor)
            ) {
                Text(confirmText)
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
 * 获取设备类型显示名称
 */
private fun getDeviceTypeDisplay(deviceType: String): String {
    return when (deviceType.lowercase()) {
        "thermostat" -> "温控器"
        "sensor" -> "传感器"
        "valve" -> "阀门"
        "fan" -> "风机"
        "pump" -> "水泵"
        "fresh_air" -> "新风系统"
        "floor_heating" -> "地暖控制器"
        "humidifier" -> "湿度调节器"
        else -> "其他"
    }
}

/**
 * 获取运行状态显示
 */
private fun getRunStatusDisplay(status: String): String {
    return when (status.lowercase()) {
        "running" -> "运行中"
        "stopped" -> "已停止"
        "standby" -> "待机"
        "error" -> "故障"
        "offline" -> "离线"
        else -> status
    }
}

/**
 * 获取运行状态颜色
 */
private fun getRunStatusColor(status: String): Color {
    return when (status.lowercase()) {
        "running" -> SuccessGreen
        "stopped" -> TextTertiaryLight
        "standby" -> WarningYellow
        "error" -> ErrorRed
        "offline" -> TextTertiaryLight
        else -> TextPrimaryLight
    }
}

/**
 * 获取CO2颜色
 */
private fun getCo2Color(co2: Int): Color {
    return when {
        co2 <= 600 -> SuccessGreen
        co2 <= 1000 -> WarningYellow
        else -> ErrorRed
    }
}

/**
 * 获取PM2.5颜色
 */
private fun getPm25Color(pm25: Int): Color {
    return when {
        pm25 <= 35 -> SuccessGreen
        pm25 <= 75 -> WarningYellow
        else -> ErrorRed
    }
}

/**
 * 获取VOC颜色
 */
private fun getVocColor(voc: Int): Color {
    return when {
        voc <= 200 -> SuccessGreen
        voc <= 500 -> WarningYellow
        else -> ErrorRed
    }
}

// ==================== Preview 函数 ====================

@Preview(showBackground = true, name = "设备详情-加载中", backgroundColor = 0xFFF1F5F9)
@Composable
fun DeviceDetailLoadingPreview() {
    WuHengTheme {
        DeviceDetailContent(
            deviceInfoState = UiDataState.Loading,
            deviceDataState = UiDataState.Loading,
            historyDataState = UiDataState.Loading
        )
    }
}

@Preview(showBackground = true, name = "设备详情-成功", backgroundColor = 0xFFF1F5F9)
@Composable
fun DeviceDetailSuccessPreview() {
    WuHengTheme {
        DeviceDetailContent(
            deviceInfoState = UiDataState.Success(
                DeviceInfo(
                    deviceId = 1,
                    deviceIdNo = "DEV001",
                    deviceName = "客厅温控器",
                    deviceType = "thermostat",
                    deviceModel = "XYN-TC200",
                    onlineStatus = 1,
                    runStatus = "running",
                    roomName = "客厅"
                )
            ),
            deviceDataState = UiDataState.Success(
                DeviceData(
                    dataId = 1,
                    deviceId = 1,
                    temperature = "24.5",
                    humidity = "55",
                    co2 = 650,
                    pm25 = 25,
                    voc = 150,
                    fanSpeed = 2,
                    valveOpen = 60,
                    power = 1,
                    reportTime = System.currentTimeMillis()
                )
            ),
            historyDataState = UiDataState.Success(
                List(24) { index ->
                    HistoryDataPoint(
                        timestamp = System.currentTimeMillis() - (23 - index) * 3600000,
                        temperature = 22f + kotlin.random.Random.nextFloat() * 4,
                        humidity = 50f + kotlin.random.Random.nextFloat() * 20,
                        co2 = 400 + kotlin.random.Random.nextInt(400)
                    )
                }
            )
        )
    }
}

@Preview(showBackground = true, name = "设备详情-错误", backgroundColor = 0xFFF1F5F9)
@Composable
fun DeviceDetailErrorPreview() {
    WuHengTheme {
        DeviceDetailContent(
            deviceInfoState = UiDataState.Error(
                com.wuheng.smart.data.network.AppException.NetworkError("网络连接失败")
            ),
            deviceDataState = UiDataState.Error(
                com.wuheng.smart.data.network.AppException.UnknownError("数据加载失败")
            ),
            historyDataState = UiDataState.Error(
                com.wuheng.smart.data.network.AppException.UnknownError("历史数据加载失败")
            )
        )
    }
}
