package com.wuheng.smart.presentation

import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.wuheng.smart.data.model.ClimateMode
import com.wuheng.smart.data.model.ClimateOverview
import com.wuheng.smart.data.model.Floor
import com.wuheng.smart.data.model.Zone
import com.wuheng.smart.presentation.base.UiDataState
import com.wuheng.smart.presentation.climate.ClimateContent
import org.junit.Rule
import org.junit.Test

/**
 * ClimateScreen UI测试
 * 测试冷暖页面交互、楼层选择、区域控制
 */
class ClimateScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    // 测试数据
    private val mockClimateOverview = ClimateOverview(
        currentTemperature = 24.5,
        targetTemperature = 26.0,
        currentMode = ClimateMode.COOLING,
        isRunning = true,
        floorCount = 4,
        zoneCount = 8,
        runningZoneCount = 5
    )

    private val mockFloors = listOf(
        Floor(
            id = "floor_b1",
            name = "B1 地下室",
            order = 0,
            zoneCount = 2,
            runningZoneCount = 1,
            averageTemperature = 22.0
        ),
        Floor(
            id = "floor_1f",
            name = "1F 一层",
            order = 1,
            zoneCount = 3,
            runningZoneCount = 2,
            averageTemperature = 24.5
        ),
        Floor(
            id = "floor_2f",
            name = "2F 二层",
            order = 2,
            zoneCount = 2,
            runningZoneCount = 1,
            averageTemperature = 25.0
        )
    )

    private val mockZones = listOf(
        Zone(
            id = "zone_1f_01",
            name = "客厅",
            floorId = "floor_1f",
            currentTemperature = 24.5,
            targetTemperature = 26.0,
            mode = ClimateMode.COOLING,
            isRunning = true,
            isOnline = true
        ),
        Zone(
            id = "zone_1f_02",
            name = "餐厅",
            floorId = "floor_1f",
            currentTemperature = 24.0,
            targetTemperature = 26.0,
            mode = ClimateMode.COOLING,
            isRunning = true,
            isOnline = true
        ),
        Zone(
            id = "zone_1f_03",
            name = "厨房",
            floorId = "floor_1f",
            currentTemperature = 25.0,
            targetTemperature = 26.0,
            mode = ClimateMode.VENTILATION,
            isRunning = false,
            isOnline = true
        )
    )

    @Test
    fun given_climateOverviewSuccess_when_screenDisplayed_then_showOverviewData() {
        // Given - 成功状态
        composeTestRule.setContent {
            ClimateContent(
                climateOverviewState = UiDataState.Success(mockClimateOverview),
                floorsState = UiDataState.Loading,
                zonesState = UiDataState.Idle,
                selectedFloorId = null,
                onFloorSelected = {},
                onRefresh = {}
            )
        }

        // Then - 验证概览数据正确显示
        composeTestRule.onNodeWithText("冷暖系统").assertIsDisplayed()
        composeTestRule.onNodeWithText("24.5°C").assertIsDisplayed() // 当前温度
        composeTestRule.onNodeWithText("26.0°C").assertIsDisplayed() // 目标温度
        composeTestRule.onNodeWithText("COOLING").assertIsDisplayed() // 模式
        composeTestRule.onNodeWithText("4层").assertIsDisplayed() // 楼层数
        composeTestRule.onNodeWithText("8").assertIsDisplayed() // 区域数
        composeTestRule.onNodeWithText("5").assertIsDisplayed() // 运行中
    }

    @Test
    fun given_floorsSuccess_when_screenDisplayed_then_showFloorSelector() {
        // Given - 楼层列表成功状态
        composeTestRule.setContent {
            ClimateContent(
                climateOverviewState = UiDataState.Success(mockClimateOverview),
                floorsState = UiDataState.Success(mockFloors),
                zonesState = UiDataState.Idle,
                selectedFloorId = null,
                onFloorSelected = {},
                onRefresh = {}
            )
        }

        // Then - 验证楼层选择器显示
        composeTestRule.onNodeWithText("选择楼层").assertIsDisplayed()
        composeTestRule.onNodeWithText("B1 地下室").assertIsDisplayed()
        composeTestRule.onNodeWithText("1F 一层").assertIsDisplayed()
        composeTestRule.onNodeWithText("2F 二层").assertIsDisplayed()
    }

    @Test
    fun given_zonesSuccess_when_screenDisplayed_then_showZoneList() {
        // Given - 区域列表成功状态
        composeTestRule.setContent {
            ClimateContent(
                climateOverviewState = UiDataState.Success(mockClimateOverview),
                floorsState = UiDataState.Success(mockFloors),
                zonesState = UiDataState.Success(mockZones),
                selectedFloorId = "floor_1f",
                onFloorSelected = {},
                onRefresh = {}
            )
        }

        // Then - 验证区域列表显示
        composeTestRule.onNodeWithText("区域控制").assertIsDisplayed()
        composeTestRule.onNodeWithText("客厅").assertIsDisplayed()
        composeTestRule.onNodeWithText("餐厅").assertIsDisplayed()
        composeTestRule.onNodeWithText("厨房").assertIsDisplayed()
        composeTestRule.onNodeWithText("24.5°C / 目标 26.0°C").assertIsDisplayed()
    }

    @Test
    fun given_floorSelected_when_clicked_then_triggerCallback() {
        // Given
        var selectedFloorId: String? = null
        composeTestRule.setContent {
            ClimateContent(
                climateOverviewState = UiDataState.Success(mockClimateOverview),
                floorsState = UiDataState.Success(mockFloors),
                zonesState = UiDataState.Idle,
                selectedFloorId = null,
                onFloorSelected = { selectedFloorId = it },
                onRefresh = {}
            )
        }

        // When - 点击楼层
        composeTestRule.onNodeWithText("1F 一层").performClick()

        // Then - 验证回调被调用
        assert(selectedFloorId == "floor_1f")
    }

    @Test
    fun given_loadingState_when_screenDisplayed_then_showLoading() {
        // Given - 加载状态
        composeTestRule.setContent {
            ClimateContent(
                climateOverviewState = UiDataState.Loading,
                floorsState = UiDataState.Loading,
                zonesState = UiDataState.Loading,
                selectedFloorId = null,
                onFloorSelected = {},
                onRefresh = {}
            )
        }

        // Then - 验证加载状态
        composeTestRule.onNodeWithText("冷暖").assertIsDisplayed()
    }

    @Test
    fun given_errorState_when_screenDisplayed_then_showError() {
        // Given - 错误状态
        composeTestRule.setContent {
            ClimateContent(
                climateOverviewState = UiDataState.Error(
                    com.wuheng.smart.data.network.AppException.NetworkError("网络错误")
                ),
                floorsState = UiDataState.Idle,
                zonesState = UiDataState.Idle,
                selectedFloorId = null,
                onFloorSelected = {},
                onRefresh = {}
            )
        }

        // Then - 验证错误信息显示
        composeTestRule.onNodeWithText("网络错误").assertIsDisplayed()
        composeTestRule.onNodeWithText("重试").assertIsDisplayed()
    }

    @Test
    fun given_offlineZone_when_screenDisplayed_then_showOfflineStatus() {
        // Given - 离线区域
        val offlineZones = listOf(
            Zone(
                id = "zone_offline",
                name = "书房",
                floorId = "floor_2f",
                currentTemperature = 26.0,
                targetTemperature = 25.0,
                mode = ClimateMode.COOLING,
                isRunning = false,
                isOnline = false
            )
        )
        composeTestRule.setContent {
            ClimateContent(
                climateOverviewState = UiDataState.Success(mockClimateOverview),
                floorsState = UiDataState.Success(mockFloors),
                zonesState = UiDataState.Success(offlineZones),
                selectedFloorId = "floor_2f",
                onFloorSelected = {},
                onRefresh = {}
            )
        }

        // Then - 验证离线区域显示
        composeTestRule.onNodeWithText("书房").assertIsDisplayed()
        composeTestRule.onNodeWithText("COOLING").assertIsDisplayed()
    }

    @Test
    fun given_emptyZones_when_screenDisplayed_then_showEmptyState() {
        // Given - 空区域列表
        composeTestRule.setContent {
            ClimateContent(
                climateOverviewState = UiDataState.Success(mockClimateOverview),
                floorsState = UiDataState.Success(mockFloors),
                zonesState = UiDataState.Success(emptyList()),
                selectedFloorId = "floor_empty",
                onFloorSelected = {},
                onRefresh = {}
            )
        }

        // Then - 验证区域控制标题显示但无区域项
        composeTestRule.onNodeWithText("区域控制").assertIsDisplayed()
        composeTestRule.onAllNodesWithText("客厅").assertCountEquals(0)
    }

    @Test
    fun given_allClimateModes_when_screenDisplayed_then_showCorrectMode() {
        // Given - 不同模式的区域
        val modes = ClimateMode.values()
        val zonesWithModes = modes.mapIndexed { index, mode ->
            Zone(
                id = "zone_$index",
                name = "区域$index",
                floorId = "floor_1f",
                currentTemperature = 24.0 + index,
                targetTemperature = 26.0,
                mode = mode,
                isRunning = index % 2 == 0,
                isOnline = true
            )
        }
        composeTestRule.setContent {
            ClimateContent(
                climateOverviewState = UiDataState.Success(mockClimateOverview),
                floorsState = UiDataState.Success(mockFloors),
                zonesState = UiDataState.Success(zonesWithModes),
                selectedFloorId = "floor_1f",
                onFloorSelected = {},
                onRefresh = {}
            )
        }

        // Then - 验证所有模式名称都显示
        modes.forEach { mode ->
            composeTestRule.onNodeWithText(mode.name).assertIsDisplayed()
        }
    }

    @Test
    fun given_refreshButton_when_clicked_then_triggerRefresh() {
        // Given
        var refreshCalled = false
        composeTestRule.setContent {
            ClimateContent(
                climateOverviewState = UiDataState.Success(mockClimateOverview),
                floorsState = UiDataState.Success(mockFloors),
                zonesState = UiDataState.Success(mockZones),
                selectedFloorId = "floor_1f",
                onFloorSelected = {},
                onRefresh = { refreshCalled = true }
            )
        }

        // When - 点击刷新按钮
        composeTestRule.onNodeWithText("刷新").performClick()

        // Then - 验证刷新回调被调用
        assert(refreshCalled)
    }

    @Test
    fun given_heatingMode_when_screenDisplayed_then_showHeatingColor() {
        // Given - 制热模式
        val heatingOverview = mockClimateOverview.copy(
            currentMode = ClimateMode.HEATING,
            isRunning = true
        )
        composeTestRule.setContent {
            ClimateContent(
                climateOverviewState = UiDataState.Success(heatingOverview),
                floorsState = UiDataState.Success(mockFloors),
                zonesState = UiDataState.Idle,
                selectedFloorId = null,
                onFloorSelected = {},
                onRefresh = {}
            )
        }

        // Then - 验证制热模式显示
        composeTestRule.onNodeWithText("HEATING").assertIsDisplayed()
    }

    @Test
    fun given_systemStopped_when_screenDisplayed_then_showStoppedState() {
        // Given - 系统停止
        val stoppedOverview = mockClimateOverview.copy(
            isRunning = false,
            currentMode = ClimateMode.OFF,
            runningZoneCount = 0
        )
        composeTestRule.setContent {
            ClimateContent(
                climateOverviewState = UiDataState.Success(stoppedOverview),
                floorsState = UiDataState.Success(mockFloors),
                zonesState = UiDataState.Idle,
                selectedFloorId = null,
                onFloorSelected = {},
                onRefresh = {}
            )
        }

        // Then - 验证停止状态显示
        composeTestRule.onNodeWithText("OFF").assertIsDisplayed()
        composeTestRule.onNodeWithText("0").assertIsDisplayed() // 运行中区域数为0
    }
}
