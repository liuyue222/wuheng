package com.wuheng.smart.integration

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.wuheng.smart.data.model.ClimateMode
import com.wuheng.smart.data.model.ClimateOverview
import com.wuheng.smart.data.model.Floor
import com.wuheng.smart.data.model.Zone
import com.wuheng.smart.data.model.ZoneDetail
import com.wuheng.smart.data.network.ApiResult
import com.wuheng.smart.data.repository.ClimateRepository
import com.wuheng.smart.presentation.base.UiDataState
import com.wuheng.smart.presentation.climate.ClimateContent
import com.wuheng.smart.presentation.climate.ClimateViewModel
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

/**
 * ClimateIntegrationTest 集成测试
 * 测试从ViewModel到Repository的完整流程
 *
 * 注意：此测试验证ViewModel和Repository的集成，使用Mock的Repository
 * 实际项目中可以使用真实Repository配合MockWebServer进行端到端测试
 */
@OptIn(ExperimentalCoroutinesApi::class)
class ClimateIntegrationTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private lateinit var viewModel: ClimateViewModel
    private lateinit var climateRepository: ClimateRepository
    private val testDispatcher = StandardTestDispatcher()

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
        ),
        Floor(
            id = "floor_3f",
            name = "3F 三层",
            order = 3,
            zoneCount = 1,
            runningZoneCount = 1,
            averageTemperature = 25.5
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

    private val mockZoneDetail = ZoneDetail(
        id = "zone_1f_01",
        name = "客厅",
        floorId = "floor_1f",
        floorName = "1F 一层",
        currentTemperature = 24.5,
        targetTemperature = 26.0,
        mode = ClimateMode.COOLING,
        isRunning = true,
        isOnline = true,
        humidity = 55,
        fanSpeed = com.wuheng.smart.data.model.FanSpeed.AUTO,
        scheduleEnabled = false,
        scheduleInfo = null
    )

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        climateRepository = mockk()

        // 设置默认的Mock响应
        coEvery { climateRepository.getClimateOverview() } returns ApiResult.Success(mockClimateOverview)
        coEvery { climateRepository.getFloors() } returns ApiResult.Success(mockFloors)
        coEvery { climateRepository.getZonesByFloor(any()) } returns ApiResult.Success(mockZones)
        coEvery { climateRepository.getZoneDetail(any()) } returns ApiResult.Success(mockZoneDetail)
        coEvery { climateRepository.setZoneTemperature(any(), any()) } returns ApiResult.Success(Unit)
        coEvery { climateRepository.setZoneMode(any(), any()) } returns ApiResult.Success(Unit)
        coEvery { climateRepository.setZonePower(any(), any()) } returns ApiResult.Success(Unit)

        viewModel = ClimateViewModel(climateRepository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun given_initialLoad_when_screenDisplayed_then_showCompleteClimateData() = runTest {
        // Given - 初始化ViewModel会自动加载数据

        // When - 设置Compose内容
        composeTestRule.setContent {
            ClimateContent(
                climateOverviewState = viewModel.climateOverviewState.value,
                floorsState = viewModel.floorsState.value,
                zonesState = viewModel.zonesState.value,
                selectedFloorId = viewModel.selectedFloorId.value,
                onFloorSelected = { viewModel.selectFloor(it) },
                onRefresh = { viewModel.refresh() }
            )
        }

        // 等待协程完成
        advanceUntilIdle()
        composeTestRule.waitForIdle()

        // Then - 验证数据正确显示
        composeTestRule.onNodeWithText("冷暖系统").assertIsDisplayed()
        composeTestRule.onNodeWithText("24.5°C").assertIsDisplayed()
        composeTestRule.onNodeWithText("26.0°C").assertIsDisplayed()
        composeTestRule.onNodeWithText("COOLING").assertIsDisplayed()
        composeTestRule.onNodeWithText("4层").assertIsDisplayed()

        // 验证楼层选择器显示
        composeTestRule.onNodeWithText("B1 地下室").assertIsDisplayed()
        composeTestRule.onNodeWithText("1F 一层").assertIsDisplayed()
        composeTestRule.onNodeWithText("2F 二层").assertIsDisplayed()
        composeTestRule.onNodeWithText("3F 三层").assertIsDisplayed()
    }

    @Test
    fun given_floorSelected_when_clicked_then_loadAndDisplayZones() = runTest {
        // Given - 设置初始状态
        coEvery { climateRepository.getZonesByFloor("floor_1f") } returns ApiResult.Success(mockZones)

        composeTestRule.setContent {
            ClimateContent(
                climateOverviewState = viewModel.climateOverviewState.value,
                floorsState = viewModel.floorsState.value,
                zonesState = viewModel.zonesState.value,
                selectedFloorId = viewModel.selectedFloorId.value,
                onFloorSelected = { viewModel.selectFloor(it) },
                onRefresh = { viewModel.refresh() }
            )
        }

        advanceUntilIdle()
        composeTestRule.waitForIdle()

        // When - 点击楼层
        composeTestRule.onNodeWithText("1F 一层").performClick()
        advanceUntilIdle()
        composeTestRule.waitForIdle()

        // Then - 验证区域数据加载并显示
        coVerify { climateRepository.getZonesByFloor("floor_1f") }
        composeTestRule.onNodeWithText("区域控制").assertIsDisplayed()
        composeTestRule.onNodeWithText("客厅").assertIsDisplayed()
        composeTestRule.onNodeWithText("餐厅").assertIsDisplayed()
        composeTestRule.onNodeWithText("厨房").assertIsDisplayed()
    }

    @Test
    fun given_temperatureAdjustment_when_setTemperature_then_updateAndRefresh() = runTest {
        // Given
        val zoneId = "zone_1f_01"
        val newTemperature = 25.0
        coEvery { climateRepository.setZoneTemperature(zoneId, newTemperature) } returns ApiResult.Success(Unit)
        coEvery { climateRepository.getZoneDetail(zoneId) } returns ApiResult.Success(
            mockZoneDetail.copy(targetTemperature = newTemperature)
        )

        // 先加载区域详情
        viewModel.loadZoneDetail(zoneId)
        advanceUntilIdle()

        // When - 设置温度
        viewModel.setZoneTemperature(zoneId, newTemperature)
        advanceUntilIdle()

        // Then - 验证Repository方法被调用
        coVerify { climateRepository.setZoneTemperature(zoneId, newTemperature) }
        coVerify { climateRepository.getZoneDetail(zoneId) }

        // 验证操作状态为成功
        val operationState = viewModel.operationState.value
        assert(operationState is UiDataState.Success)
    }

    @Test
    fun given_modeSwitch_when_changeMode_then_updateAndRefresh() = runTest {
        // Given
        val zoneId = "zone_1f_01"
        val newMode = ClimateMode.HEATING
        coEvery { climateRepository.setZoneMode(zoneId, newMode) } returns ApiResult.Success(Unit)
        coEvery { climateRepository.getZoneDetail(zoneId) } returns ApiResult.Success(
            mockZoneDetail.copy(mode = newMode)
        )

        // 先加载区域详情
        viewModel.loadZoneDetail(zoneId)
        advanceUntilIdle()

        // When - 切换模式
        viewModel.setZoneMode(zoneId, newMode)
        advanceUntilIdle()

        // Then - 验证Repository方法被调用
        coVerify { climateRepository.setZoneMode(zoneId, newMode) }
        coVerify { climateRepository.getZoneDetail(zoneId) }

        // 验证操作状态为成功
        val operationState = viewModel.operationState.value
        assert(operationState is UiDataState.Success)
    }

    @Test
    fun given_powerControl_when_togglePower_then_updateAndRefresh() = runTest {
        // Given
        val zoneId = "zone_1f_01"
        val powerOn = false
        coEvery { climateRepository.setZonePower(zoneId, powerOn) } returns ApiResult.Success(Unit)
        coEvery { climateRepository.getZoneDetail(zoneId) } returns ApiResult.Success(
            mockZoneDetail.copy(isRunning = powerOn)
        )

        // 先加载区域详情
        viewModel.loadZoneDetail(zoneId)
        advanceUntilIdle()

        // When - 切换电源
        viewModel.setZonePower(zoneId, powerOn)
        advanceUntilIdle()

        // Then - 验证Repository方法被调用
        coVerify { climateRepository.setZonePower(zoneId, powerOn) }
        coVerify { climateRepository.getZoneDetail(zoneId) }

        // 验证操作状态为成功
        val operationState = viewModel.operationState.value
        assert(operationState is UiDataState.Success)
    }

    @Test
    fun given_refreshAction_when_triggered_then_reloadAllData() = runTest {
        // Given
        coEvery { climateRepository.getClimateOverview() } returns ApiResult.Success(mockClimateOverview)
        coEvery { climateRepository.getFloors() } returns ApiResult.Success(mockFloors)

        composeTestRule.setContent {
            ClimateContent(
                climateOverviewState = viewModel.climateOverviewState.value,
                floorsState = viewModel.floorsState.value,
                zonesState = viewModel.zonesState.value,
                selectedFloorId = viewModel.selectedFloorId.value,
                onFloorSelected = { viewModel.selectFloor(it) },
                onRefresh = { viewModel.refresh() }
            )
        }

        advanceUntilIdle()
        composeTestRule.waitForIdle()

        // When - 触发刷新
        composeTestRule.onNodeWithText("刷新").performClick()
        advanceUntilIdle()

        // Then - 验证所有数据重新加载
        coVerify(atLeast = 2) { climateRepository.getClimateOverview() }
        coVerify(atLeast = 2) { climateRepository.getFloors() }
    }

    @Test
    fun given_networkError_when_loadData_then_showErrorState() = runTest {
        // Given - 模拟网络错误
        coEvery { climateRepository.getClimateOverview() } returns ApiResult.Error(
            com.wuheng.smart.data.network.AppException.NetworkError("网络连接失败")
        )

        // 创建新的ViewModel触发重新加载
        viewModel = ClimateViewModel(climateRepository)

        composeTestRule.setContent {
            ClimateContent(
                climateOverviewState = viewModel.climateOverviewState.value,
                floorsState = viewModel.floorsState.value,
                zonesState = viewModel.zonesState.value,
                selectedFloorId = viewModel.selectedFloorId.value,
                onFloorSelected = { viewModel.selectFloor(it) },
                onRefresh = { viewModel.refresh() }
            )
        }

        advanceUntilIdle()
        composeTestRule.waitForIdle()

        // Then - 验证错误状态显示
        composeTestRule.onNodeWithText("网络连接失败").assertIsDisplayed()
        composeTestRule.onNodeWithText("重试").assertIsDisplayed()
    }

    @Test
    fun given_operationError_when_setTemperature_then_showError() = runTest {
        // Given
        val zoneId = "zone_1f_01"
        val temperature = 25.0
        coEvery { climateRepository.setZoneTemperature(zoneId, temperature) } returns ApiResult.Error(
            com.wuheng.smart.data.network.AppException.BusinessError(400, "设备离线，无法设置温度")
        )

        // 先加载区域详情
        viewModel.loadZoneDetail(zoneId)
        advanceUntilIdle()

        // When - 设置温度（会失败）
        viewModel.setZoneTemperature(zoneId, temperature)
        advanceUntilIdle()

        // Then - 验证操作状态为错误
        val operationState = viewModel.operationState.value
        assert(operationState is UiDataState.Error)
    }

    @Test
    fun given_completeWorkflow_when_userInteracts_then_allOperationsSucceed() = runTest {
        // Given - 完整的用户交互流程
        coEvery { climateRepository.getZonesByFloor("floor_2f") } returns ApiResult.Success(
            listOf(
                Zone(
                    id = "zone_2f_01",
                    name = "主卧",
                    floorId = "floor_2f",
                    currentTemperature = 25.0,
                    targetTemperature = 26.0,
                    mode = ClimateMode.COOLING,
                    isRunning = true,
                    isOnline = true
                )
            )
        )

        composeTestRule.setContent {
            ClimateContent(
                climateOverviewState = viewModel.climateOverviewState.value,
                floorsState = viewModel.floorsState.value,
                zonesState = viewModel.zonesState.value,
                selectedFloorId = viewModel.selectedFloorId.value,
                onFloorSelected = { viewModel.selectFloor(it) },
                onRefresh = { viewModel.refresh() }
            )
        }

        advanceUntilIdle()
        composeTestRule.waitForIdle()

        // When - 1. 选择二层
        composeTestRule.onNodeWithText("2F 二层").performClick()
        advanceUntilIdle()
        composeTestRule.waitForIdle()

        // Then - 验证二层区域加载
        coVerify { climateRepository.getZonesByFloor("floor_2f") }
        composeTestRule.onNodeWithText("主卧").assertIsDisplayed()

        // When - 2. 刷新数据
        composeTestRule.onNodeWithText("刷新").performClick()
        advanceUntilIdle()

        // Then - 验证刷新成功
        coVerify(atLeast = 2) { climateRepository.getClimateOverview() }
    }
}
