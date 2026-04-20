package com.wuheng.smart.presentation

import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.wuheng.smart.data.model.WaterDevice
import com.wuheng.smart.data.model.WaterDeviceSettings
import com.wuheng.smart.data.model.WaterDeviceStatus
import com.wuheng.smart.data.model.WaterDeviceType
import com.wuheng.smart.data.model.WaterOverview
import com.wuheng.smart.data.model.WaterSystemStatus
import com.wuheng.smart.presentation.base.UiDataState
import com.wuheng.smart.presentation.water.WaterContent
import org.junit.Rule
import org.junit.Test

/**
 * WaterScreen UI测试
 * 测试水系统页面显示和设备状态
 */
class WaterScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    // 测试数据
    private val mockWaterOverview = WaterOverview(
        systemStatus = WaterSystemStatus.NORMAL,
        inletTemperature = 15.5,
        outletTemperature = 45.0,
        pressure = 0.35,
        flowRate = 2.8,
        deviceCount = 6,
        runningDeviceCount = 4
    )

    private val mockWaterDevices = listOf(
        WaterDevice(
            id = "water_001",
            name = "燃气锅炉",
            type = WaterDeviceType.BOILER,
            status = WaterDeviceStatus.NORMAL,
            isRunning = true,
            currentTemperature = 65.0,
            targetTemperature = 65.0,
            settings = WaterDeviceSettings(
                targetTemperature = 65.0,
                timerEnabled = false,
                timerStartTime = null,
                timerEndTime = null,
                ecoMode = false
            )
        ),
        WaterDevice(
            id = "water_002",
            name = "储水式热水器",
            type = WaterDeviceType.WATER_HEATER,
            status = WaterDeviceStatus.NORMAL,
            isRunning = true,
            currentTemperature = 55.0,
            targetTemperature = 55.0,
            settings = WaterDeviceSettings(
                targetTemperature = 55.0,
                timerEnabled = true,
                timerStartTime = "06:00",
                timerEndTime = "23:00",
                ecoMode = true
            )
        ),
        WaterDevice(
            id = "water_003",
            name = "热水循环泵",
            type = WaterDeviceType.CIRCULATION_PUMP,
            status = WaterDeviceStatus.NORMAL,
            isRunning = true,
            currentTemperature = null,
            targetTemperature = null,
            settings = WaterDeviceSettings(
                targetTemperature = null,
                timerEnabled = true,
                timerStartTime = "06:00",
                timerEndTime = "23:00",
                ecoMode = false
            )
        )
    )

    @Test
    fun given_waterOverviewSuccess_when_screenDisplayed_then_showOverviewData() {
        // Given - 成功状态
        composeTestRule.setContent {
            WaterContent(
                waterOverviewState = UiDataState.Success(mockWaterOverview),
                waterDevicesState = UiDataState.Loading,
                onRefresh = {}
            )
        }

        // Then - 验证概览数据正确显示
        composeTestRule.onNodeWithText("水系统").assertIsDisplayed()
        composeTestRule.onNodeWithText("NORMAL").assertIsDisplayed() // 系统状态
        composeTestRule.onNodeWithText("15.5°C").assertIsDisplayed() // 进水温度
        composeTestRule.onNodeWithText("45.0°C").assertIsDisplayed() // 出水温度
        composeTestRule.onNodeWithText("0.35bar").assertIsDisplayed() // 压力
        composeTestRule.onNodeWithText("2.8L/min").assertIsDisplayed() // 流量
        composeTestRule.onNodeWithText("6").assertIsDisplayed() // 设备数
        composeTestRule.onNodeWithText("4").assertIsDisplayed() // 运行中
    }

    @Test
    fun given_devicesSuccess_when_screenDisplayed_then_showDeviceList() {
        // Given - 设备列表成功状态
        composeTestRule.setContent {
            WaterContent(
                waterOverviewState = UiDataState.Success(mockWaterOverview),
                waterDevicesState = UiDataState.Success(mockWaterDevices),
                onRefresh = {}
            )
        }

        // Then - 验证设备列表显示
        composeTestRule.onNodeWithText("水系统设备").assertIsDisplayed()
        composeTestRule.onNodeWithText("燃气锅炉").assertIsDisplayed()
        composeTestRule.onNodeWithText("储水式热水器").assertIsDisplayed()
        composeTestRule.onNodeWithText("热水循环泵").assertIsDisplayed()
        composeTestRule.onNodeWithText("BOILER").assertIsDisplayed()
        composeTestRule.onNodeWithText("WATER_HEATER").assertIsDisplayed()
        composeTestRule.onNodeWithText("CIRCULATION_PUMP").assertIsDisplayed()
    }

    @Test
    fun given_warningStatus_when_screenDisplayed_then_showWarningState() {
        // Given - 警告状态
        val warningOverview = mockWaterOverview.copy(systemStatus = WaterSystemStatus.WARNING)
        composeTestRule.setContent {
            WaterContent(
                waterOverviewState = UiDataState.Success(warningOverview),
                waterDevicesState = UiDataState.Success(mockWaterDevices),
                onRefresh = {}
            )
        }

        // Then - 验证警告状态显示
        composeTestRule.onNodeWithText("WARNING").assertIsDisplayed()
    }

    @Test
    fun given_errorStatus_when_screenDisplayed_then_showErrorState() {
        // Given - 错误状态
        val errorOverview = mockWaterOverview.copy(systemStatus = WaterSystemStatus.ERROR)
        composeTestRule.setContent {
            WaterContent(
                waterOverviewState = UiDataState.Success(errorOverview),
                waterDevicesState = UiDataState.Success(mockWaterDevices),
                onRefresh = {}
            )
        }

        // Then - 验证错误状态显示
        composeTestRule.onNodeWithText("ERROR").assertIsDisplayed()
    }

    @Test
    fun given_maintenanceStatus_when_screenDisplayed_then_showMaintenanceState() {
        // Given - 维护中状态
        val maintenanceOverview = mockWaterOverview.copy(systemStatus = WaterSystemStatus.MAINTENANCE)
        composeTestRule.setContent {
            WaterContent(
                waterOverviewState = UiDataState.Success(maintenanceOverview),
                waterDevicesState = UiDataState.Success(mockWaterDevices),
                onRefresh = {}
            )
        }

        // Then - 验证维护状态显示
        composeTestRule.onNodeWithText("MAINTENANCE").assertIsDisplayed()
    }

    @Test
    fun given_offlineDevice_when_screenDisplayed_then_showOfflineStatus() {
        // Given - 离线设备
        val offlineDevices = listOf(
            WaterDevice(
                id = "water_001",
                name = "燃气锅炉",
                type = WaterDeviceType.BOILER,
                status = WaterDeviceStatus.OFFLINE,
                isRunning = false,
                currentTemperature = null,
                targetTemperature = null,
                settings = WaterDeviceSettings(
                    targetTemperature = null,
                    timerEnabled = false,
                    timerStartTime = null,
                    timerEndTime = null,
                    ecoMode = false
                )
            )
        )
        composeTestRule.setContent {
            WaterContent(
                waterOverviewState = UiDataState.Success(mockWaterOverview),
                waterDevicesState = UiDataState.Success(offlineDevices),
                onRefresh = {}
            )
        }

        // Then - 验证离线状态显示
        composeTestRule.onNodeWithText("燃气锅炉").assertIsDisplayed()
        composeTestRule.onNodeWithText("OFFLINE").assertIsDisplayed()
    }

    @Test
    fun given_warningDevice_when_screenDisplayed_then_showWarningStatus() {
        // Given - 警告设备
        val warningDevices = listOf(
            mockWaterDevices[0].copy(status = WaterDeviceStatus.WARNING)
        )
        composeTestRule.setContent {
            WaterContent(
                waterOverviewState = UiDataState.Success(mockWaterOverview),
                waterDevicesState = UiDataState.Success(warningDevices),
                onRefresh = {}
            )
        }

        // Then - 验证警告状态显示
        composeTestRule.onNodeWithText("燃气锅炉").assertIsDisplayed()
        composeTestRule.onNodeWithText("WARNING").assertIsDisplayed()
    }

    @Test
    fun given_pumpDevice_when_screenDisplayed_then_showWithoutTemperature() {
        // Given - 循环泵设备（无温度）
        val pumpDevice = mockWaterDevices.find { it.type == WaterDeviceType.CIRCULATION_PUMP }
        composeTestRule.setContent {
            WaterContent(
                waterOverviewState = UiDataState.Success(mockWaterOverview),
                waterDevicesState = UiDataState.Success(listOfNotNull(pumpDevice)),
                onRefresh = {}
            )
        }

        // Then - 验证循环泵显示（无温度值）
        composeTestRule.onNodeWithText("热水循环泵").assertIsDisplayed()
        composeTestRule.onNodeWithText("CIRCULATION_PUMP").assertIsDisplayed()
    }

    @Test
    fun given_loadingState_when_screenDisplayed_then_showLoading() {
        // Given - 加载状态
        composeTestRule.setContent {
            WaterContent(
                waterOverviewState = UiDataState.Loading,
                waterDevicesState = UiDataState.Loading,
                onRefresh = {}
            )
        }

        // Then - 验证加载状态
        composeTestRule.onNodeWithText("水").assertIsDisplayed()
    }

    @Test
    fun given_errorState_when_screenDisplayed_then_showError() {
        // Given - 错误状态
        composeTestRule.setContent {
            WaterContent(
                waterOverviewState = UiDataState.Error(
                    com.wuheng.smart.data.network.AppException.NetworkError("网络连接失败")
                ),
                waterDevicesState = UiDataState.Idle,
                onRefresh = {}
            )
        }

        // Then - 验证错误信息显示
        composeTestRule.onNodeWithText("网络连接失败").assertIsDisplayed()
        composeTestRule.onNodeWithText("重试").assertIsDisplayed()
    }

    @Test
    fun given_emptyDeviceList_when_screenDisplayed_then_showEmptyState() {
        // Given - 空设备列表
        composeTestRule.setContent {
            WaterContent(
                waterOverviewState = UiDataState.Success(mockWaterOverview),
                waterDevicesState = UiDataState.Success(emptyList()),
                onRefresh = {}
            )
        }

        // Then - 验证标题显示但无设备项
        composeTestRule.onNodeWithText("水系统设备").assertIsDisplayed()
        composeTestRule.onAllNodesWithText("燃气锅炉").assertCountEquals(0)
    }

    @Test
    fun given_refreshButton_when_clicked_then_triggerRefresh() {
        // Given
        var refreshCalled = false
        composeTestRule.setContent {
            WaterContent(
                waterOverviewState = UiDataState.Success(mockWaterOverview),
                waterDevicesState = UiDataState.Success(mockWaterDevices),
                onRefresh = { refreshCalled = true }
            )
        }

        // When - 点击刷新按钮
        composeTestRule.onNodeWithText("刷新").performClick()

        // Then - 验证刷新回调被调用
        assert(refreshCalled)
    }

    @Test
    fun given_allDeviceTypes_when_screenDisplayed_then_showAllTypes() {
        // Given - 所有设备类型
        val allTypes = WaterDeviceType.values().mapIndexed { index, type ->
            WaterDevice(
                id = "water_${type.name}",
                name = type.name.lowercase().replaceFirstChar { it.uppercase() },
                type = type,
                status = WaterDeviceStatus.NORMAL,
                isRunning = index % 2 == 0,
                currentTemperature = if (type == WaterDeviceType.CIRCULATION_PUMP || 
                                        type == WaterDeviceType.PURIFIER || 
                                        type == WaterDeviceType.SOFTENER) null else 60.0,
                targetTemperature = if (type == WaterDeviceType.CIRCULATION_PUMP || 
                                        type == WaterDeviceType.PURIFIER || 
                                        type == WaterDeviceType.SOFTENER ||
                                        type == WaterDeviceType.WATER_TANK) null else 60.0,
                settings = WaterDeviceSettings(
                    targetTemperature = null,
                    timerEnabled = false,
                    timerStartTime = null,
                    timerEndTime = null,
                    ecoMode = false
                )
            )
        }
        composeTestRule.setContent {
            WaterContent(
                waterOverviewState = UiDataState.Success(mockWaterOverview),
                waterDevicesState = UiDataState.Success(allTypes),
                onRefresh = {}
            )
        }

        // Then - 验证所有设备类型名称都显示
        WaterDeviceType.values().forEach { type ->
            composeTestRule.onNodeWithText(type.name).assertIsDisplayed()
        }
    }

    @Test
    fun given_extremeValues_when_screenDisplayed_then_displayCorrectly() {
        // Given - 极端值
        val extremeOverview = WaterOverview(
            systemStatus = WaterSystemStatus.NORMAL,
            inletTemperature = 5.0,
            outletTemperature = 75.0,
            pressure = 0.0,
            flowRate = 0.0,
            deviceCount = 0,
            runningDeviceCount = 0
        )
        composeTestRule.setContent {
            WaterContent(
                waterOverviewState = UiDataState.Success(extremeOverview),
                waterDevicesState = UiDataState.Success(emptyList()),
                onRefresh = {}
            )
        }

        // Then - 验证极端值正确显示
        composeTestRule.onNodeWithText("5.0°C").assertIsDisplayed()
        composeTestRule.onNodeWithText("75.0°C").assertIsDisplayed()
        composeTestRule.onNodeWithText("0.0bar").assertIsDisplayed()
        composeTestRule.onNodeWithText("0.0L/min").assertIsDisplayed()
        composeTestRule.onNodeWithText("0").assertIsDisplayed()
    }

    @Test
    fun given_errorDevice_when_screenDisplayed_then_showErrorStatus() {
        // Given - 错误设备
        val errorDevices = listOf(
            mockWaterDevices[0].copy(status = WaterDeviceStatus.ERROR, isRunning = false)
        )
        composeTestRule.setContent {
            WaterContent(
                waterOverviewState = UiDataState.Success(mockWaterOverview),
                waterDevicesState = UiDataState.Success(errorDevices),
                onRefresh = {}
            )
        }

        // Then - 验证错误状态显示
        composeTestRule.onNodeWithText("燃气锅炉").assertIsDisplayed()
        composeTestRule.onNodeWithText("ERROR").assertIsDisplayed()
    }
}
