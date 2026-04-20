package com.wuheng.smart.presentation.water

import com.wuheng.smart.data.model.WaterDevice
import com.wuheng.smart.data.model.WaterDeviceSettings
import com.wuheng.smart.data.model.WaterDeviceStatus
import com.wuheng.smart.data.model.WaterDeviceType
import com.wuheng.smart.data.model.WaterOverview
import com.wuheng.smart.data.model.WaterSettingsRequest
import com.wuheng.smart.data.model.WaterSystemStatus
import com.wuheng.smart.data.network.ApiResult
import com.wuheng.smart.data.network.AppException
import com.wuheng.smart.data.repository.WaterRepository
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
 * WaterViewModel 单元测试
 * 测试热水循环模式切换、水系统数据加载等功能
 */
@OptIn(ExperimentalCoroutinesApi::class)
class WaterViewModelTest {

    private lateinit var viewModel: WaterViewModel
    private lateinit var waterRepository: WaterRepository
    private val testDispatcher = StandardTestDispatcher()

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

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        waterRepository = mockk()
        viewModel = WaterViewModel(waterRepository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `Given 初始化状态 When ViewModel创建时 Then 应该自动加载水系统概览和设备列表`() = runTest {
        // Given
        coEvery { waterRepository.getWaterOverview() } returns ApiResult.Success(mockWaterOverview)
        coEvery { waterRepository.getWaterDevices() } returns ApiResult.Success(mockWaterDevices)

        // When
        advanceUntilIdle()

        // Then
        val overviewState = viewModel.waterOverviewState.first()
        assertTrue(overviewState is UiDataState.Success)
        assertEquals(mockWaterOverview, (overviewState as UiDataState.Success).data)

        val devicesState = viewModel.waterDevicesState.first()
        assertTrue(devicesState is UiDataState.Success)
        assertEquals(mockWaterDevices, (devicesState as UiDataState.Success).data)
    }

    @Test
    fun `Given 水系统概览数据 When 调用loadWaterOverview Then 应该返回正确的系统状态`() = runTest {
        // Given
        coEvery { waterRepository.getWaterOverview() } returns ApiResult.Success(mockWaterOverview)

        // When
        viewModel.loadWaterOverview()
        advanceUntilIdle()

        // Then
        val state = viewModel.waterOverviewState.first()
        assertTrue(state is UiDataState.Success)
        val data = (state as UiDataState.Success).data
        assertEquals(WaterSystemStatus.NORMAL, data.systemStatus)
        assertEquals(15.5, data.inletTemperature, 0.01)
        assertEquals(45.0, data.outletTemperature, 0.01)
        assertEquals(0.35, data.pressure, 0.01)
        assertEquals(2.8, data.flowRate, 0.01)
        assertEquals(6, data.deviceCount)
        assertEquals(4, data.runningDeviceCount)
    }

    @Test
    fun `Given 警告状态 When 调用loadWaterOverview Then 应该返回WARNING状态`() = runTest {
        // Given
        val warningOverview = mockWaterOverview.copy(systemStatus = WaterSystemStatus.WARNING)
        coEvery { waterRepository.getWaterOverview() } returns ApiResult.Success(warningOverview)

        // When
        viewModel.loadWaterOverview()
        advanceUntilIdle()

        // Then
        val state = viewModel.waterOverviewState.first()
        assertTrue(state is UiDataState.Success)
        assertEquals(WaterSystemStatus.WARNING, (state as UiDataState.Success).data.systemStatus)
    }

    @Test
    fun `Given 错误状态 When 调用loadWaterOverview Then 应该返回ERROR状态`() = runTest {
        // Given
        val errorOverview = mockWaterOverview.copy(systemStatus = WaterSystemStatus.ERROR)
        coEvery { waterRepository.getWaterOverview() } returns ApiResult.Success(errorOverview)

        // When
        viewModel.loadWaterOverview()
        advanceUntilIdle()

        // Then
        val state = viewModel.waterOverviewState.first()
        assertTrue(state is UiDataState.Success)
        assertEquals(WaterSystemStatus.ERROR, (state as UiDataState.Success).data.systemStatus)
    }

    @Test
    fun `Given 设备列表 When 调用loadWaterDevices Then 应该返回所有水系统设备`() = runTest {
        // Given
        coEvery { waterRepository.getWaterDevices() } returns ApiResult.Success(mockWaterDevices)

        // When
        viewModel.loadWaterDevices()
        advanceUntilIdle()

        // Then
        val state = viewModel.waterDevicesState.first()
        assertTrue(state is UiDataState.Success)
        val devices = (state as UiDataState.Success).data
        assertEquals(3, devices.size)

        // 验证锅炉设备
        val boiler = devices.find { it.type == WaterDeviceType.BOILER }
        assertTrue(boiler != null)
        assertEquals(65.0, boiler?.currentTemperature)
        assertTrue(boiler?.isRunning == true)

        // 验证热水器设备
        val heater = devices.find { it.type == WaterDeviceType.WATER_HEATER }
        assertTrue(heater != null)
        assertTrue(heater?.settings?.timerEnabled == true)
        assertTrue(heater?.settings?.ecoMode == true)
    }

    @Test
    fun `Given 空设备列表 When 调用loadWaterDevices Then 应该返回空列表`() = runTest {
        // Given
        coEvery { waterRepository.getWaterDevices() } returns ApiResult.Success(emptyList())

        // When
        viewModel.loadWaterDevices()
        advanceUntilIdle()

        // Then
        val state = viewModel.waterDevicesState.first()
        assertTrue(state is UiDataState.Success)
        assertTrue((state as UiDataState.Success).data.isEmpty())
    }

    @Test
    fun `Given 设备ID和设置 When 调用updateDeviceSettings Then 应该更新设备设置并刷新列表`() = runTest {
        // Given
        val deviceId = "water_002"
        val request = WaterSettingsRequest(
            targetTemperature = 60.0,
            timerEnabled = true,
            timerStartTime = "07:00",
            timerEndTime = "22:00",
            ecoMode = false
        )
        coEvery { waterRepository.updateWaterDeviceSettings(deviceId, request) } returns ApiResult.Success(Unit)
        coEvery { waterRepository.getWaterDevices() } returns ApiResult.Success(mockWaterDevices)

        // When
        viewModel.updateDeviceSettings(deviceId, request)
        advanceUntilIdle()

        // Then
        coVerify { waterRepository.updateWaterDeviceSettings(deviceId, request) }
        coVerify { waterRepository.getWaterDevices() }
        val operationState = viewModel.operationState.first()
        assertTrue(operationState is UiDataState.Success)
    }

    @Test
    fun `Given 仅更新温度 When 调用updateDeviceSettings Then 应该只更新温度字段`() = runTest {
        // Given
        val deviceId = "water_001"
        val request = WaterSettingsRequest(targetTemperature = 70.0)
        coEvery { waterRepository.updateWaterDeviceSettings(deviceId, request) } returns ApiResult.Success(Unit)
        coEvery { waterRepository.getWaterDevices() } returns ApiResult.Success(mockWaterDevices)

        // When
        viewModel.updateDeviceSettings(deviceId, request)
        advanceUntilIdle()

        // Then
        coVerify { waterRepository.updateWaterDeviceSettings(deviceId, request) }
        val operationState = viewModel.operationState.first()
        assertTrue(operationState is UiDataState.Success)
    }

    @Test
    fun `Given 更新失败 When 调用updateDeviceSettings Then 应该返回错误状态`() = runTest {
        // Given
        val deviceId = "water_001"
        val request = WaterSettingsRequest(targetTemperature = 70.0)
        coEvery { waterRepository.updateWaterDeviceSettings(deviceId, request) } returns ApiResult.Error(
            AppException.BusinessError(400, "设备离线，无法更新设置")
        )

        // When
        viewModel.updateDeviceSettings(deviceId, request)
        advanceUntilIdle()

        // Then
        val operationState = viewModel.operationState.first()
        assertTrue(operationState is UiDataState.Error)
        val exception = (operationState as UiDataState.Error).exception
        assertTrue(exception is AppException.BusinessError)
        assertEquals(400, (exception as AppException.BusinessError).code)
    }

    @Test
    fun `Given 设备离线状态 When 加载设备列表 Then 应该正确显示离线设备`() = runTest {
        // Given
        val offlineDevices = listOf(
            mockWaterDevices[0].copy(
                status = WaterDeviceStatus.OFFLINE,
                isRunning = false
            )
        )
        coEvery { waterRepository.getWaterDevices() } returns ApiResult.Success(offlineDevices)

        // When
        viewModel.loadWaterDevices()
        advanceUntilIdle()

        // Then
        val state = viewModel.waterDevicesState.first()
        assertTrue(state is UiDataState.Success)
        val device = (state as UiDataState.Success).data[0]
        assertEquals(WaterDeviceStatus.OFFLINE, device.status)
        assertFalse(device.isRunning)
    }

    @Test
    fun `Given 循环泵设备 When 加载设备列表 Then 应该正确处理无温度属性的设备`() = runTest {
        // Given
        val pumpDevice = mockWaterDevices.find { it.type == WaterDeviceType.CIRCULATION_PUMP }
        coEvery { waterRepository.getWaterDevices() } returns ApiResult.Success(listOfNotNull(pumpDevice))

        // When
        viewModel.loadWaterDevices()
        advanceUntilIdle()

        // Then
        val state = viewModel.waterDevicesState.first()
        assertTrue(state is UiDataState.Success)
        val device = (state as UiDataState.Success).data[0]
        assertEquals(WaterDeviceType.CIRCULATION_PUMP, device.type)
        assertNull(device.currentTemperature)
        assertNull(device.targetTemperature)
        assertTrue(device.isRunning)
    }

    @Test
    fun `Given 网络超时 When 调用loadWaterOverview Then 应该返回TimeoutError`() = runTest {
        // Given
        coEvery { waterRepository.getWaterOverview() } returns ApiResult.Error(
            AppException.TimeoutError()
        )

        // When
        viewModel.loadWaterOverview()
        advanceUntilIdle()

        // Then
        val state = viewModel.waterOverviewState.first()
        assertTrue(state is UiDataState.Error)
        assertTrue((state as UiDataState.Error).exception is AppException.TimeoutError)
    }

    @Test
    fun `Given 服务器错误 When 调用loadWaterDevices Then 应该返回ServerError`() = runTest {
        // Given
        coEvery { waterRepository.getWaterDevices() } returns ApiResult.Error(
            AppException.ServerError(503, "服务暂时不可用")
        )

        // When
        viewModel.loadWaterDevices()
        advanceUntilIdle()

        // Then
        val state = viewModel.waterDevicesState.first()
        assertTrue(state is UiDataState.Error)
        val exception = (state as UiDataState.Error).exception
        assertTrue(exception is AppException.ServerError)
        assertEquals(503, (exception as AppException.ServerError).code)
    }

    @Test
    fun `Given 当前状态 When 调用refresh Then 应该重新加载所有数据`() = runTest {
        // Given
        coEvery { waterRepository.getWaterOverview() } returns ApiResult.Success(mockWaterOverview)
        coEvery { waterRepository.getWaterDevices() } returns ApiResult.Success(mockWaterDevices)

        // When
        viewModel.refresh()
        advanceUntilIdle()

        // Then
        coVerify(atLeast = 2) { waterRepository.getWaterOverview() }
        coVerify(atLeast = 2) { waterRepository.getWaterDevices() }
    }

    @Test
    fun `Given 维护中状态 When 调用loadWaterOverview Then 应该返回MAINTENANCE状态`() = runTest {
        // Given
        val maintenanceOverview = mockWaterOverview.copy(systemStatus = WaterSystemStatus.MAINTENANCE)
        coEvery { waterRepository.getWaterOverview() } returns ApiResult.Success(maintenanceOverview)

        // When
        viewModel.loadWaterOverview()
        advanceUntilIdle()

        // Then
        val state = viewModel.waterOverviewState.first()
        assertTrue(state is UiDataState.Success)
        assertEquals(WaterSystemStatus.MAINTENANCE, (state as UiDataState.Success).data.systemStatus)
    }

    @Test
    fun `Given 警告设备 When 加载设备列表 Then 应该正确显示警告状态`() = runTest {
        // Given
        val warningDevice = mockWaterDevices[0].copy(status = WaterDeviceStatus.WARNING)
        coEvery { waterRepository.getWaterDevices() } returns ApiResult.Success(listOf(warningDevice))

        // When
        viewModel.loadWaterDevices()
        advanceUntilIdle()

        // Then
        val state = viewModel.waterDevicesState.first()
        assertTrue(state is UiDataState.Success)
        assertEquals(WaterDeviceStatus.WARNING, (state as UiDataState.Success).data[0].status)
    }
}
