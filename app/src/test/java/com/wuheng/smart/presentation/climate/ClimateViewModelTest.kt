package com.wuheng.smart.presentation.climate

import com.wuheng.smart.data.model.ClimateMode
import com.wuheng.smart.data.model.ClimateOverview
import com.wuheng.smart.data.model.Floor
import com.wuheng.smart.data.model.Zone
import com.wuheng.smart.data.model.ZoneDetail
import com.wuheng.smart.data.network.ApiResult
import com.wuheng.smart.data.network.AppException
import com.wuheng.smart.data.repository.ClimateRepository
import com.wuheng.smart.presentation.base.UiDataState
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

/**
 * ClimateViewModel 单元测试
 * 测试温度调节、模式切换、楼层选择等功能
 */
@OptIn(ExperimentalCoroutinesApi::class)
class ClimateViewModelTest {

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
        viewModel = ClimateViewModel(climateRepository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `Given 初始化状态 When ViewModel创建时 Then 应该自动加载概览和楼层数据`() = runTest {
        // Given
        coEvery { climateRepository.getClimateOverview() } returns ApiResult.Success(mockClimateOverview)
        coEvery { climateRepository.getFloors() } returns ApiResult.Success(mockFloors)

        // When
        advanceUntilIdle()

        // Then
        val overviewState = viewModel.climateOverviewState.first()
        assertTrue(overviewState is UiDataState.Success)
        assertEquals(mockClimateOverview, (overviewState as UiDataState.Success).data)

        val floorsState = viewModel.floorsState.first()
        assertTrue(floorsState is UiDataState.Success)
        assertEquals(mockFloors, (floorsState as UiDataState.Success).data)
    }

    @Test
    fun `Given 概览数据 When 调用loadClimateOverview Then 应该返回正确的冷暖系统状态`() = runTest {
        // Given
        coEvery { climateRepository.getClimateOverview() } returns ApiResult.Success(mockClimateOverview)

        // When
        viewModel.loadClimateOverview()
        advanceUntilIdle()

        // Then
        val state = viewModel.climateOverviewState.first()
        assertTrue(state is UiDataState.Success)
        val data = (state as UiDataState.Success).data
        assertEquals(24.5, data.currentTemperature, 0.01)
        assertEquals(26.0, data.targetTemperature, 0.01)
        assertEquals(ClimateMode.COOLING, data.currentMode)
        assertTrue(data.isRunning)
    }

    @Test
    fun `Given 楼层列表 When 调用loadFloors Then 应该返回正确的楼层数据`() = runTest {
        // Given
        coEvery { climateRepository.getFloors() } returns ApiResult.Success(mockFloors)

        // When
        viewModel.loadFloors()
        advanceUntilIdle()

        // Then
        val state = viewModel.floorsState.first()
        assertTrue(state is UiDataState.Success)
        assertEquals(2, (state as UiDataState.Success).data.size)
    }

    @Test
    fun `Given 楼层ID When 调用selectFloor Then 应该更新选中楼层并加载对应区域`() = runTest {
        // Given
        val floorId = "floor_1f"
        coEvery { climateRepository.getZonesByFloor(floorId) } returns ApiResult.Success(mockZones)

        // When
        viewModel.selectFloor(floorId)
        advanceUntilIdle()

        // Then
        assertEquals(floorId, viewModel.selectedFloorId.first())
        val zonesState = viewModel.zonesState.first()
        assertTrue(zonesState is UiDataState.Success)
        assertEquals(mockZones, (zonesState as UiDataState.Success).data)
    }

    @Test
    fun `Given 无效楼层ID When 调用selectFloor Then 应该返回空区域列表`() = runTest {
        // Given
        val invalidFloorId = "floor_invalid"
        coEvery { climateRepository.getZonesByFloor(invalidFloorId) } returns ApiResult.Success(emptyList())

        // When
        viewModel.selectFloor(invalidFloorId)
        advanceUntilIdle()

        // Then
        val zonesState = viewModel.zonesState.first()
        assertTrue(zonesState is UiDataState.Success)
        assertTrue((zonesState as UiDataState.Success).data.isEmpty())
    }

    @Test
    fun `Given 区域ID When 调用loadZoneDetail Then 应该返回区域详情`() = runTest {
        // Given
        val zoneId = "zone_1f_01"
        coEvery { climateRepository.getZoneDetail(zoneId) } returns ApiResult.Success(mockZoneDetail)

        // When
        viewModel.loadZoneDetail(zoneId)
        advanceUntilIdle()

        // Then
        val state = viewModel.zoneDetailState.first()
        assertTrue(state is UiDataState.Success)
        assertEquals(mockZoneDetail, (state as UiDataState.Success).data)
    }

    @Test
    fun `Given 区域ID和温度 When 调用setZoneTemperature Then 应该更新温度并刷新详情`() = runTest {
        // Given
        val zoneId = "zone_1f_01"
        val newTemperature = 25.0
        coEvery { climateRepository.setZoneTemperature(zoneId, newTemperature) } returns ApiResult.Success(Unit)
        coEvery { climateRepository.getZoneDetail(zoneId) } returns ApiResult.Success(mockZoneDetail)

        // When
        viewModel.setZoneTemperature(zoneId, newTemperature)
        advanceUntilIdle()

        // Then
        coVerify { climateRepository.setZoneTemperature(zoneId, newTemperature) }
        coVerify { climateRepository.getZoneDetail(zoneId) }
        val operationState = viewModel.operationState.first()
        assertTrue(operationState is UiDataState.Success)
    }

    @Test
    fun `Given 区域ID和制冷模式 When 调用setZoneMode Then 应该更新模式并刷新详情`() = runTest {
        // Given
        val zoneId = "zone_1f_01"
        val newMode = ClimateMode.HEATING
        coEvery { climateRepository.setZoneMode(zoneId, newMode) } returns ApiResult.Success(Unit)
        coEvery { climateRepository.getZoneDetail(zoneId) } returns ApiResult.Success(mockZoneDetail)

        // When
        viewModel.setZoneMode(zoneId, newMode)
        advanceUntilIdle()

        // Then
        coVerify { climateRepository.setZoneMode(zoneId, newMode) }
        val operationState = viewModel.operationState.first()
        assertTrue(operationState is UiDataState.Success)
    }

    @Test
    fun `Given 区域ID和开关状态 When 调用setZonePower Then 应该更新电源状态`() = runTest {
        // Given
        val zoneId = "zone_1f_01"
        val powerOn = false
        coEvery { climateRepository.setZonePower(zoneId, powerOn) } returns ApiResult.Success(Unit)
        coEvery { climateRepository.getZoneDetail(zoneId) } returns ApiResult.Success(mockZoneDetail)

        // When
        viewModel.setZonePower(zoneId, powerOn)
        advanceUntilIdle()

        // Then
        coVerify { climateRepository.setZonePower(zoneId, powerOn) }
        val operationState = viewModel.operationState.first()
        assertTrue(operationState is UiDataState.Success)
    }

    @Test
    fun `Given 设置失败 When 调用setZoneTemperature Then 应该返回错误状态`() = runTest {
        // Given
        val zoneId = "zone_1f_01"
        val temperature = 25.0
        coEvery { climateRepository.setZoneTemperature(zoneId, temperature) } returns ApiResult.Error(
            AppException.BusinessError(400, "温度设置失败")
        )

        // When
        viewModel.setZoneTemperature(zoneId, temperature)
        advanceUntilIdle()

        // Then
        val operationState = viewModel.operationState.first()
        assertTrue(operationState is UiDataState.Error)
    }

    @Test
    fun `Given 网络异常 When 调用loadClimateOverview Then 应该返回NetworkError`() = runTest {
        // Given
        coEvery { climateRepository.getClimateOverview() } returns ApiResult.Error(
            AppException.NetworkError("网络连接失败")
        )

        // When
        viewModel.loadClimateOverview()
        advanceUntilIdle()

        // Then
        val state = viewModel.climateOverviewState.first()
        assertTrue(state is UiDataState.Error)
        assertTrue((state as UiDataState.Error).exception is AppException.NetworkError)
    }

    @Test
    fun `Given 未选择楼层 When 调用refresh Then 应该只刷新概览和楼层`() = runTest {
        // Given
        coEvery { climateRepository.getClimateOverview() } returns ApiResult.Success(mockClimateOverview)
        coEvery { climateRepository.getFloors() } returns ApiResult.Success(mockFloors)

        // When
        viewModel.refresh()
        advanceUntilIdle()

        // Then
        coVerify(atLeast = 2) { climateRepository.getClimateOverview() }
        coVerify(atLeast = 2) { climateRepository.getFloors() }
        // 不应该调用getZonesByFloor，因为没有选中楼层
    }

    @Test
    fun `Given 已选择楼层 When 调用refresh Then 应该同时刷新区域数据`() = runTest {
        // Given
        val floorId = "floor_1f"
        coEvery { climateRepository.getClimateOverview() } returns ApiResult.Success(mockClimateOverview)
        coEvery { climateRepository.getFloors() } returns ApiResult.Success(mockFloors)
        coEvery { climateRepository.getZonesByFloor(floorId) } returns ApiResult.Success(mockZones)

        // 先选择楼层
        viewModel.selectFloor(floorId)
        advanceUntilIdle()

        // When
        viewModel.refresh()
        advanceUntilIdle()

        // Then
        coVerify(atLeast = 2) { climateRepository.getZonesByFloor(floorId) }
    }

    @Test
    fun `Given 区域离线 When 调用loadZoneDetail Then 应该返回离线状态`() = runTest {
        // Given
        val zoneId = "zone_offline"
        val offlineZone = mockZoneDetail.copy(isOnline = false, isRunning = false)
        coEvery { climateRepository.getZoneDetail(zoneId) } returns ApiResult.Success(offlineZone)

        // When
        viewModel.loadZoneDetail(zoneId)
        advanceUntilIdle()

        // Then
        val state = viewModel.zoneDetailState.first()
        assertTrue(state is UiDataState.Success)
        val data = (state as UiDataState.Success).data
        assertFalse(data.isOnline)
        assertFalse(data.isRunning)
    }

    @Test
    fun `Given 边界温度值 When 调用setZoneTemperature Then 应该接受极值`() = runTest {
        // Given - 测试边界温度值
        val zoneId = "zone_1f_01"
        val minTemperature = 16.0
        val maxTemperature = 30.0

        coEvery { climateRepository.setZoneTemperature(zoneId, any()) } returns ApiResult.Success(Unit)
        coEvery { climateRepository.getZoneDetail(zoneId) } returns ApiResult.Success(mockZoneDetail)

        // When - 测试最低温度
        viewModel.setZoneTemperature(zoneId, minTemperature)
        advanceUntilIdle()

        // Then
        coVerify { climateRepository.setZoneTemperature(zoneId, minTemperature) }

        // When - 测试最高温度
        viewModel.setZoneTemperature(zoneId, maxTemperature)
        advanceUntilIdle()

        // Then
        coVerify { climateRepository.setZoneTemperature(zoneId, maxTemperature) }
    }
}
