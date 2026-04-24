package com.wuheng.smart.presentation.floorzone

import app.cash.turbine.test
import com.wuheng.smart.MainDispatcherRule
import com.wuheng.smart.data.model.*
import com.wuheng.smart.data.network.ApiResult
import com.wuheng.smart.data.network.AppException
import com.wuheng.smart.data.repository.HomeRepository
import com.wuheng.smart.presentation.base.UiDataState
import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

/**
 * FloorZoneViewModel 单元测试
 */
@ExperimentalCoroutinesApi
@ExtendWith(MainDispatcherRule::class)
@DisplayName("FloorZoneViewModel Tests")
class FloorZoneViewModelTest {

    private lateinit var viewModel: FloorZoneViewModel
    private lateinit var mockHomeRepository: HomeRepository

    @BeforeEach
    fun setup() {
        mockHomeRepository = mockk(relaxed = true)
        
        // 设置默认的楼层数据
        val mockFloors = listOf(
            FloorInfo(1, "FLOOR1", "地下一层", -1, "80.00", 0),
            FloorInfo(2, "FLOOR2", "一层", 1, "100.00", 3),
            FloorInfo(3, "FLOOR3", "二层", 2, "100.00", 2)
        )
        coEvery { mockHomeRepository.getFloorInfo(1) } returns flowOf(ApiResult.Success(mockFloors))
        coEvery { mockHomeRepository.getRoomInfo(1, any()) } returns flowOf(ApiResult.Success(emptyList()))
        coEvery { mockHomeRepository.getDeviceList(1, any()) } returns flowOf(ApiResult.Success(emptyList()))
        coEvery { mockHomeRepository.controlDevice(any(), any(), any()) } returns flowOf(ApiResult.Success(mockk(relaxed = true)))

        viewModel = FloorZoneViewModel(mockHomeRepository)
    }

    @AfterEach
    fun tearDown() {
        unmockkAll()
    }

    @Test
    @DisplayName("初始状态 - 楼层数据应已加载，房间和设备状态应为Loading或Success")
    fun `initial state - floors loaded, rooms and devices should be Loading or Success`() = runTest {
        advanceUntilIdle()
        
        // 初始化时自动加载楼层数据
        assertTrue(viewModel.floorsState.value is UiDataState.Success)
        // 自动选择第一个楼层后会加载房间，所以roomsState可能是Loading或Success
        assertTrue(
            viewModel.roomsState.value is UiDataState.Loading || 
            viewModel.roomsState.value is UiDataState.Success
        )
        // 设备数据需要手动选择房间后才加载
        assertTrue(viewModel.roomDevicesState.value is UiDataState.Idle)
    }

    @Test
    @DisplayName("初始化 - 应自动加载楼层列表并选择第一个")
    fun `initialization - should auto load floors and select first`() = runTest {
        advanceUntilIdle()

        coVerify { mockHomeRepository.getFloorInfo(1) }
        assertEquals("1", viewModel.selectedFloorId.value)
    }

    @Test
    @DisplayName("加载楼层列表 - 成功时应更新状态并自动选择第一个")
    fun `load floors - success should update state and auto select first`() = runTest {
        val mockFloors = listOf(
            FloorInfo(1, "FLOOR1", "地下一层", -1, "80.00", 0),
            FloorInfo(2, "FLOOR2", "一层", 1, "100.00", 3)
        )
        coEvery { mockHomeRepository.getFloorInfo(1) } returns flowOf(ApiResult.Success(mockFloors))
        coEvery { mockHomeRepository.getRoomInfo(1, 1) } returns flowOf(ApiResult.Success(emptyList()))

        val newViewModel = FloorZoneViewModel(mockHomeRepository)
        advanceUntilIdle()

        assertTrue(newViewModel.floorsState.value is UiDataState.Success)
        assertEquals("1", newViewModel.selectedFloorId.value)
    }

    @Test
    @DisplayName("加载楼层列表 - 失败时应返回Error状态")
    fun `load floors - failure should return Error`() = runTest {
        coEvery { mockHomeRepository.getFloorInfo(1) } returns flowOf(ApiResult.Error(AppException.NetworkError()))

        val newViewModel = FloorZoneViewModel(mockHomeRepository)
        advanceUntilIdle()

        assertTrue(newViewModel.floorsState.value is UiDataState.Error)
    }

    @Test
    @DisplayName("加载房间列表 - 成功时应更新状态并自动选择第一个")
    fun `load rooms - success should update state and auto select first`() = runTest {
        val mockRooms = listOf(
            RoomInfo(1, "ROOM1", "客厅", "living", "45.00", 2),
            RoomInfo(2, "ROOM2", "主卧", "bedroom", "25.00", 1)
        )
        coEvery { mockHomeRepository.getRoomInfo(1, 1) } returns flowOf(ApiResult.Success(mockRooms))

        viewModel.selectFloor("1")
        advanceUntilIdle()

        assertTrue(viewModel.roomsState.value is UiDataState.Success)
        assertEquals("1", viewModel.selectedRoomId.value)
    }

    @Test
    @DisplayName("加载房间设备 - 成功时应更新状态")
    fun `load room devices - success should update state`() = runTest {
        val mockDevices = listOf(
            DeviceInfo(1, "DEV1", "客厅温控器", "thermostat", "TH-001", 1, "running", "客厅"),
            DeviceInfo(2, "DEV2", "环境传感器", "sensor", "SE-001", 1, "running", "客厅")
        )
        coEvery { mockHomeRepository.getDeviceList(1, 1) } returns flowOf(ApiResult.Success(mockDevices))

        viewModel.selectRoom("1")
        advanceUntilIdle()

        assertTrue(viewModel.roomDevicesState.value is UiDataState.Success)
        val devices = (viewModel.roomDevicesState.value as UiDataState.Success).data
        assertEquals(2, devices.size)
    }

    @Test
    @DisplayName("选择楼层 - 应更新选中楼层并加载房间")
    fun `select floor - should update selected floor and load rooms`() = runTest {
        coEvery { mockHomeRepository.getRoomInfo(1, 2) } returns flowOf(ApiResult.Success(emptyList()))

        viewModel.selectFloor("2")
        advanceUntilIdle()

        assertEquals("2", viewModel.selectedFloorId.value)
        assertNull(viewModel.selectedRoomId.value)
        coVerify { mockHomeRepository.getRoomInfo(1, 2) }
    }

    @Test
    @DisplayName("选择相同楼层 - 不应重复加载")
    fun `select same floor - should not reload`() = runTest {
        advanceUntilIdle()
        clearMocks(mockHomeRepository)
        coEvery { mockHomeRepository.getRoomInfo(any(), any()) } returns flowOf(ApiResult.Success(emptyList()))

        viewModel.selectFloor("1")
        advanceUntilIdle()

        coVerify(exactly = 0) { mockHomeRepository.getRoomInfo(any(), any()) }
    }

    @Test
    @DisplayName("选择房间 - 应更新选中房间并加载设备")
    fun `select room - should update selected room and load devices`() = runTest {
        // 先加载房间列表
        val mockRooms = listOf(
            RoomInfo(1, "ROOM1", "客厅", "living", "45.00", 2),
            RoomInfo(2, "ROOM2", "主卧", "bedroom", "25.00", 1)
        )
        coEvery { mockHomeRepository.getRoomInfo(1, 1) } returns flowOf(ApiResult.Success(mockRooms))
        viewModel.selectFloor("1")
        advanceUntilIdle()
        
        // 然后选择房间
        coEvery { mockHomeRepository.getDeviceList(1, 2) } returns flowOf(ApiResult.Success(emptyList()))
        viewModel.selectRoom("2")
        advanceUntilIdle()

        assertEquals("2", viewModel.selectedRoomId.value)
        coVerify { mockHomeRepository.getDeviceList(1, 2) }
    }

    @Test
    @DisplayName("切换设备电源 - 开启时应发送on命令")
    fun `toggle device power - on should send on command`() = runTest {
        coEvery { mockHomeRepository.controlDevice(1, "on", null) } returns flowOf(
            ApiResult.Success(ControlDeviceResponse("on", "", 1))
        )

        viewModel.toggleDevicePower(1, true)
        advanceUntilIdle()

        coVerify { mockHomeRepository.controlDevice(1, "on", null) }
    }

    @Test
    @DisplayName("切换设备电源 - 关闭时应发送off命令")
    fun `toggle device power - off should send off command`() = runTest {
        coEvery { mockHomeRepository.controlDevice(1, "off", null) } returns flowOf(
            ApiResult.Success(ControlDeviceResponse("off", "", 1))
        )

        viewModel.toggleDevicePower(1, false)
        advanceUntilIdle()

        coVerify { mockHomeRepository.controlDevice(1, "off", null) }
    }

    @Test
    @DisplayName("设置房间温度 - 有温控器时应发送命令")
    fun `set room temperature - with thermostat should send command`() = runTest {
        val mockDevices = listOf(
            DeviceInfo(1, "DEV1", "温控器", "thermostat", "TH-001", 1, "running", "客厅")
        )
        coEvery { mockHomeRepository.getDeviceList(1, 1) } returns flowOf(ApiResult.Success(mockDevices))
        coEvery { mockHomeRepository.controlDevice(1, "set_temp", "25.0") } returns flowOf(
            ApiResult.Success(ControlDeviceResponse("set_temp", "25.0", 1))
        )

        viewModel.selectRoom("1")
        advanceUntilIdle()

        viewModel.setRoomTemperature(1, 25f)
        advanceUntilIdle()

        coVerify { mockHomeRepository.controlDevice(1, "set_temp", "25.0") }
    }

    @Test
    @DisplayName("设置房间温度 - 无温控器时应返回成功")
    fun `set room temperature - without thermostat should return success`() = runTest {
        val mockDevices = listOf(
            DeviceInfo(1, "DEV1", "传感器", "sensor", "SE-001", 1, "running", "客厅")
        )
        coEvery { mockHomeRepository.getDeviceList(1, 1) } returns flowOf(ApiResult.Success(mockDevices))

        viewModel.selectRoom("1")
        advanceUntilIdle()

        viewModel.setRoomTemperature(1, 25f)
        advanceUntilIdle()

        assertTrue(viewModel.operationState.value is UiDataState.Success)
    }

    @Test
    @DisplayName("设置房间湿度 - 有加湿器时应发送命令")
    fun `set room humidity - with humidifier should send command`() = runTest {
        val mockDevices = listOf(
            DeviceInfo(1, "DEV1", "加湿器", "humidifier", "HM-001", 1, "running", "客厅")
        )
        coEvery { mockHomeRepository.getDeviceList(1, 1) } returns flowOf(ApiResult.Success(mockDevices))
        coEvery { mockHomeRepository.controlDevice(1, "set_humidity", "55.0") } returns flowOf(
            ApiResult.Success(ControlDeviceResponse("set_humidity", "55.0", 1))
        )

        viewModel.selectRoom("1")
        advanceUntilIdle()

        viewModel.setRoomHumidity(1, 55f)
        advanceUntilIdle()

        coVerify { mockHomeRepository.controlDevice(1, "set_humidity", "55.0") }
    }

    @Test
    @DisplayName("刷新 - 应重新加载所有数据")
    fun `refresh - should reload all data`() = runTest {
        advanceUntilIdle()
        clearMocks(mockHomeRepository)
        
        coEvery { mockHomeRepository.getFloorInfo(1) } returns flowOf(ApiResult.Success(emptyList()))

        viewModel.refresh()
        advanceUntilIdle()

        coVerify { mockHomeRepository.getFloorInfo(1) }
    }

    @Test
    @DisplayName("重置操作状态 - 应将状态重置为Idle")
    fun `reset operation state - should reset to Idle`() = runTest {
        coEvery { mockHomeRepository.controlDevice(1, "on", null) } returns flowOf(
            ApiResult.Error(AppException.NetworkError())
        )

        viewModel.toggleDevicePower(1, true)
        advanceUntilIdle()

        assertTrue(viewModel.operationState.value is UiDataState.Error)

        viewModel.resetOperationState()

        assertTrue(viewModel.operationState.value is UiDataState.Idle)
    }

    @Test
    @DisplayName("边界条件 - 单个楼层应正确处理")
    fun `boundary - single floor should handle correctly`() = runTest {
        coEvery { mockHomeRepository.getFloorInfo(1) } returns flowOf(
            ApiResult.Success(listOf(FloorInfo(1, "FLOOR1", "一层", 1, "100.00", 3)))
        )
        coEvery { mockHomeRepository.getRoomInfo(1, 1) } returns flowOf(ApiResult.Success(emptyList()))

        val newViewModel = FloorZoneViewModel(mockHomeRepository)
        advanceUntilIdle()

        assertEquals("1", newViewModel.selectedFloorId.value)
    }

    @Test
    @DisplayName("边界条件 - 空楼层列表应正确处理")
    fun `boundary - empty floor list should handle correctly`() = runTest {
        coEvery { mockHomeRepository.getFloorInfo(1) } returns flowOf(ApiResult.Success(emptyList()))

        val newViewModel = FloorZoneViewModel(mockHomeRepository)
        advanceUntilIdle()

        assertNull(newViewModel.selectedFloorId.value)
    }

    @ParameterizedTest
    @CsvSource("16.0", "20.0", "25.0", "30.0")
    @DisplayName("边界条件 - 各温度值应正确处理")
    fun `boundary - various temperatures should handle correctly`(temp: Float) = runTest {
        val mockDevices = listOf(
            DeviceInfo(1, "DEV1", "温控器", "thermostat", "TH-001", 1, "running", "客厅")
        )
        coEvery { mockHomeRepository.getDeviceList(1, 1) } returns flowOf(ApiResult.Success(mockDevices))
        coEvery { mockHomeRepository.controlDevice(1, "set_temp", temp.toString()) } returns flowOf(
            ApiResult.Success(ControlDeviceResponse("set_temp", temp.toString(), 1))
        )

        viewModel.selectRoom("1")
        advanceUntilIdle()

        viewModel.setRoomTemperature(1, temp)
        advanceUntilIdle()

        assertTrue(viewModel.operationState.value is UiDataState.Success)
    }
}
