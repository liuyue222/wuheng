package com.wuheng.smart.presentation.home

import app.cash.turbine.test
import com.wuheng.smart.data.model.*
import com.wuheng.smart.data.network.ApiResult
import com.wuheng.smart.data.network.TokenManager
import com.wuheng.smart.data.repository.HomeRepository
import com.wuheng.smart.presentation.base.UiDataState
import io.mockk.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.*
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.*

/**
 * HomeViewModel 单元测试
 *
 * 测试范围:
 * 1. 首页数据加载（成功/失败）
 * 2. 设备列表获取
 * 3. 设备控制（开关、温度调节）
 * 4. 场景应用
 * 5. 下拉刷新
 * 6. 设备状态更新
 */
@OptIn(ExperimentalCoroutinesApi::class)
class HomeViewModelTest {

    private lateinit var homeRepository: HomeRepository
    private lateinit var tokenManager: TokenManager
    private lateinit var viewModel: HomeViewModel
    private val testDispatcher = StandardTestDispatcher()

    @BeforeEach
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        homeRepository = mockk(relaxed = true)
        tokenManager = mockk(relaxed = true)
        every { tokenManager.getCurrentHouseId() } returns 1
        viewModel = HomeViewModel(homeRepository, tokenManager)
    }

    @AfterEach
    fun tearDown() {
        Dispatchers.resetMain()
        unmockkAll()
    }

    // ==================== 初始状态测试 ====================

    @Test
    fun `initial state should have default values`() = runTest {
        // Given & When
        val initialState = viewModel.uiState.value

        // Then
        assertEquals("杭州市 西湖区", initialState.location)
        assertEquals(26, initialState.outdoorTemp)
        assertEquals("多云", initialState.weather)
        assertEquals("西湖一号院", initialState.residenceName)
        assertEquals(ClimateMode.COOLING, initialState.currentMode)
        assertEquals(4, initialState.scenes.size)
        assertFalse(initialState.preheatPreheatEnabled)
    }

    @Test
    fun `initial homeOverviewState should be idle`() = runTest {
        // Given & When
        val initialState = viewModel.homeOverviewState.value

        // Then
        assertTrue(initialState is UiDataState.Idle)
    }

    @Test
    fun `initial devicesState should be idle`() = runTest {
        // Given & When
        val initialState = viewModel.devicesState.value

        // Then
        assertTrue(initialState is UiDataState.Idle)
    }

    // ==================== 首页数据加载测试 ====================

    @Test
    fun `loadHomeOverview should emit success state with data`() = runTest {
        // Given
        val mockOverview = HomeOverview(
            roomCount = 8,
            deviceCount = 12,
            onlineDeviceCount = 10,
            indoorTemperature = 24.5,
            indoorHumidity = 48,
            pm25 = 12,
            co2 = 420,
            voc = 0.3,
            residenceName = "西湖壹号院",
            address = "杭州市西湖区"
        )
        coEvery { homeRepository.getHomeOverview() } returns flowOf(ApiResult.Success(mockOverview))

        // When & Then
        viewModel.homeOverviewState.test {
            // Initial state
            awaitItem()

            viewModel.loadHomeOverview()

            // Loading state
            val loadingState = awaitItem()
            assertTrue(loadingState is UiDataState.Loading)

            // Success state
            val successState = awaitItem()
            assertTrue(successState is UiDataState.Success)
            assertEquals(mockOverview, (successState as UiDataState.Success).data)

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `loadHomeOverview should emit error state on failure`() = runTest {
        // Given
        val exception = Exception("Network error")
        coEvery { homeRepository.getHomeOverview() } returns flowOf(ApiResult.Error(exception))

        // When & Then
        viewModel.homeOverviewState.test {
            // Initial state
            awaitItem()

            viewModel.loadHomeOverview()

            // Loading state
            val loadingState = awaitItem()
            assertTrue(loadingState is UiDataState.Loading)

            // Error state
            val errorState = awaitItem()
            assertTrue(errorState is UiDataState.Error)

            cancelAndIgnoreRemainingEvents()
        }
    }

    // ==================== 设备列表测试 ====================

    @Test
    fun `loadAllDevices should emit success state with devices`() = runTest {
        // Given
        val mockDevices = listOf(
            Device(id = "1", name = "客厅空调", type = DeviceType.CLIMATE, status = DeviceStatus.ON, roomName = "客厅", isOnline = true),
            Device(id = "2", name = "主卧空调", type = DeviceType.CLIMATE, status = DeviceStatus.OFF, roomName = "主卧", isOnline = true),
            Device(id = "3", name = "净水系统", type = DeviceType.WATER, status = DeviceStatus.ON, roomName = "厨房", isOnline = true)
        )
        coEvery { homeRepository.getAllDevices() } returns flowOf(ApiResult.Success(mockDevices))

        // When & Then
        viewModel.devicesState.test {
            // Initial state
            awaitItem()

            viewModel.loadAllDevices()

            // Loading state
            val loadingState = awaitItem()
            assertTrue(loadingState is UiDataState.Loading)

            // Success state
            val successState = awaitItem()
            assertTrue(successState is UiDataState.Success)
            assertEquals(3, (successState as UiDataState.Success).data.size)

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `loadAllDevices with empty list should emit success with empty data`() = runTest {
        // Given
        coEvery { homeRepository.getAllDevices() } returns flowOf(ApiResult.Success(emptyList<Device>()))

        // When
        viewModel.loadAllDevices()
        advanceUntilIdle()

        // Then
        assertTrue(viewModel.devicesState.value is UiDataState.Success)
        assertTrue((viewModel.devicesState.value as UiDataState.Success).data.isEmpty())
    }

    // ==================== 设备控制测试 ====================

    @Test
    fun `toggleDevicePower should call repository and refresh devices`() = runTest {
        // Given
        coEvery { homeRepository.toggleDevicePower(any(), any()) } returns flowOf(ApiResult.Success(Unit))
        coEvery { homeRepository.getAllDevices() } returns flowOf(ApiResult.Success(emptyList()))

        // When
        viewModel.toggleDevicePower("device1", true)
        advanceUntilIdle()

        // Then
        coVerify { homeRepository.toggleDevicePower("device1", true) }
        coVerify { homeRepository.getAllDevices() }
    }

    @Test
    fun `toggleDevicePower on should enable device`() = runTest {
        // Given
        coEvery { homeRepository.toggleDevicePower("device1", true) } returns flowOf(ApiResult.Success(Unit))
        coEvery { homeRepository.getAllDevices() } returns flowOf(ApiResult.Success(emptyList()))

        // When
        viewModel.toggleDevicePower("device1", true)
        advanceUntilIdle()

        // Then
        coVerify { homeRepository.toggleDevicePower("device1", true) }
    }

    @Test
    fun `toggleDevicePower off should disable device`() = runTest {
        // Given
        coEvery { homeRepository.toggleDevicePower("device1", false) } returns flowOf(ApiResult.Success(Unit))
        coEvery { homeRepository.getAllDevices() } returns flowOf(ApiResult.Success(emptyList()))

        // When
        viewModel.toggleDevicePower("device1", false)
        advanceUntilIdle()

        // Then
        coVerify { homeRepository.toggleDevicePower("device1", false) }
    }

    @Test
    fun `toggleDevicePower when error should not crash`() = runTest {
        // Given
        val exception = Exception("Device offline")
        coEvery { homeRepository.toggleDevicePower(any(), any()) } returns flowOf(ApiResult.Error(exception))

        // When & Then - Should not throw
        viewModel.toggleDevicePower("device1", true)
        advanceUntilIdle()
    }

    // ==================== 场景应用测试 ====================

    @Test
    fun `applyScene with houseId should call repository and refresh data`() = runTest {
        // Given
        coEvery { homeRepository.applyScene(any(), any()) } returns flowOf(ApiResult.Success(Unit))
        coEvery { homeRepository.getSceneList(any()) } returns flowOf(ApiResult.Success(emptyList()))
        coEvery { homeRepository.getSystemStatus(any()) } returns flowOf(ApiResult.Success(createMockSystemStatus()))

        // When
        viewModel.applyScene(1, 1)
        advanceUntilIdle()

        // Then
        coVerify { homeRepository.applyScene(1, 1) }
        coVerify { homeRepository.getSceneList(1) }
        coVerify { homeRepository.getSystemStatus(1) }
    }

    @Test
    fun `activateScene should call repository and refresh data`() = runTest {
        // Given
        coEvery { homeRepository.activateScene(any()) } returns flowOf(ApiResult.Success(Unit))
        coEvery { homeRepository.getHomeOverview() } returns flowOf(ApiResult.Success(createMockHomeOverview()))

        // When
        viewModel.activateScene("scene1")
        advanceUntilIdle()

        // Then
        coVerify { homeRepository.activateScene("scene1") }
        coVerify { homeRepository.getHomeOverview() }
    }

    @Test
    fun `activateScene when error should handle gracefully`() = runTest {
        // Given
        val exception = Exception("Scene not found")
        coEvery { homeRepository.activateScene(any()) } returns flowOf(ApiResult.Error(exception))

        // When & Then - Should not throw
        viewModel.activateScene("invalid_scene")
        advanceUntilIdle()
    }

    @Test
    fun `deactivateScene should call repository and refresh data`() = runTest {
        // Given
        coEvery { homeRepository.deactivateScene(any()) } returns flowOf(ApiResult.Success(Unit))
        coEvery { homeRepository.getHomeOverview() } returns flowOf(ApiResult.Success(createMockHomeOverview()))

        // When
        viewModel.deactivateScene("scene1")
        advanceUntilIdle()

        // Then
        coVerify { homeRepository.deactivateScene("scene1") }
        coVerify { homeRepository.getHomeOverview() }
    }

    // ==================== 刷新测试 ====================

    @Test
    fun `refresh should reload all data`() = runTest {
        // Given
        coEvery { homeRepository.getHomeOverview() } returns flowOf(ApiResult.Success(createMockHomeOverview()))
        coEvery { homeRepository.getAllDevices() } returns flowOf(ApiResult.Success(emptyList()))
        coEvery { homeRepository.getHouseInfo(any()) } returns flowOf(ApiResult.Success(createMockHouseInfo()))
        coEvery { homeRepository.getFloorList(any()) } returns flowOf(ApiResult.Success(emptyList()))
        coEvery { homeRepository.getSceneList(any()) } returns flowOf(ApiResult.Success(emptyList()))
        coEvery { homeRepository.getSystemStatus(any()) } returns flowOf(ApiResult.Success(createMockSystemStatus()))

        // When
        viewModel.refresh()
        advanceUntilIdle()

        // Then
        coVerify { homeRepository.getHomeOverview() }
        coVerify { homeRepository.getAllDevices() }
        coVerify { homeRepository.getHouseInfo(1) }
        coVerify { homeRepository.getFloorList(1) }
        coVerify { homeRepository.getSceneList(1) }
        coVerify { homeRepository.getSystemStatus(1) }
    }

    @Test
    fun `refreshData should call refresh`() = runTest {
        // Given
        coEvery { homeRepository.getHomeOverview() } returns flowOf(ApiResult.Success(createMockHomeOverview()))
        coEvery { homeRepository.getAllDevices() } returns flowOf(ApiResult.Success(emptyList()))
        coEvery { homeRepository.getHouseInfo(any()) } returns flowOf(ApiResult.Success(createMockHouseInfo()))
        coEvery { homeRepository.getFloorList(any()) } returns flowOf(ApiResult.Success(emptyList()))
        coEvery { homeRepository.getSceneList(any()) } returns flowOf(ApiResult.Success(emptyList()))
        coEvery { homeRepository.getSystemStatus(any()) } returns flowOf(ApiResult.Success(createMockSystemStatus()))

        // When
        viewModel.refreshData()
        advanceUntilIdle()

        // Then
        coVerify { homeRepository.getHomeOverview() }
        coVerify { homeRepository.getAllDevices() }
    }

    // ==================== 场景列表测试 ====================

    @Test
    fun `loadSceneList should emit success state with scenes`() = runTest {
        // Given
        val mockScenes = listOf(
            SceneInfo(id = 1, name = "会客模式", icon = "guest", isActive = false),
            SceneInfo(id = 2, name = "离家模式", icon = "away", isActive = true),
            SceneInfo(id = 3, name = "睡眠模式", icon = "sleep", isActive = false)
        )
        coEvery { homeRepository.getSceneList(any()) } returns flowOf(ApiResult.Success(mockScenes))

        // When & Then
        viewModel.sceneListState.test {
            // Initial state
            awaitItem()

            viewModel.loadSceneList(1)

            // Loading state
            val loadingState = awaitItem()
            assertTrue(loadingState is UiDataState.Loading)

            // Success state
            val successState = awaitItem()
            assertTrue(successState is UiDataState.Success)
            assertEquals(3, (successState as UiDataState.Success).data.size)

            cancelAndIgnoreRemainingEvents()
        }
    }

    // ==================== 设备状态设置测试 ====================

    @Test
    fun `setDeviceTemperature should call repository and refresh devices`() = runTest {
        // Given
        coEvery { homeRepository.setDeviceTemperature(any(), any()) } returns flowOf(ApiResult.Success(Unit))
        coEvery { homeRepository.getAllDevices() } returns flowOf(ApiResult.Success(emptyList()))

        // When
        viewModel.setDeviceTemperature("device1", 25.0)
        advanceUntilIdle()

        // Then
        coVerify { homeRepository.setDeviceTemperature("device1", 25.0) }
        coVerify { homeRepository.getAllDevices() }
    }

    @Test
    fun `toggleDeviceMode should call repository and refresh devices`() = runTest {
        // Given
        coEvery { homeRepository.setDeviceMode(any(), any()) } returns flowOf(ApiResult.Success(Unit))
        coEvery { homeRepository.getAllDevices() } returns flowOf(ApiResult.Success(emptyList()))

        // When
        viewModel.toggleDeviceMode("device1", true)
        advanceUntilIdle()

        // Then
        coVerify { homeRepository.setDeviceMode("device1", true) }
        coVerify { homeRepository.getAllDevices() }
    }

    // ==================== 天气模式测试 ====================

    @Test
    fun `onWeatherModeSelected should update mode and call repository`() = runTest {
        // Given
        coEvery { homeRepository.setWeatherMode(any()) } returns flowOf(ApiResult.Success(Unit))

        // When
        viewModel.onWeatherModeSelected(WeatherMode.HEATING)
        advanceUntilIdle()

        // Then
        coVerify { homeRepository.setWeatherMode(WeatherMode.HEATING) }
        assertEquals(WeatherMode.HEATING, viewModel.weatherModeState.value.selectedMode)
    }

    @Test
    fun `onModeSelected should update climate mode and call API`() = runTest {
        // Given
        coEvery { homeRepository.setWeatherMode(any()) } returns flowOf(ApiResult.Success(Unit))

        // When
        viewModel.onModeSelected(ClimateMode.HEATING)
        advanceUntilIdle()

        // Then
        assertEquals(ClimateMode.HEATING, viewModel.uiState.value.currentMode)
        coVerify { homeRepository.setWeatherMode(WeatherMode.HEATING) }
    }

    // ==================== 错误处理测试 ====================

    @Test
    fun `loadHomeOverview with network error should emit error state`() = runTest {
        // Given
        val exception = Exception("Network unavailable")
        coEvery { homeRepository.getHomeOverview() } returns flowOf(ApiResult.Error(exception))

        // When
        viewModel.loadHomeOverview()
        advanceUntilIdle()

        // Then
        assertTrue(viewModel.homeOverviewState.value is UiDataState.Error)
    }

    @Test
    fun `loadAllDevices with timeout should emit error state`() = runTest {
        // Given
        val exception = Exception("Request timeout")
        coEvery { homeRepository.getAllDevices() } returns flowOf(ApiResult.Error(exception))

        // When
        viewModel.loadAllDevices()
        advanceUntilIdle()

        // Then
        assertTrue(viewModel.devicesState.value is UiDataState.Error)
    }

    // ==================== 边界条件测试 ====================

    @Test
    fun `loadAllDevices with very large list should handle correctly`() = runTest {
        // Given
        val largeDeviceList = (1..100).map {
            Device(
                id = "$it",
                name = "设备$it",
                type = if (it % 2 == 0) DeviceType.CLIMATE else DeviceType.WATER,
                status = if (it % 3 == 0) DeviceStatus.ON else DeviceStatus.OFF,
                roomName = "房间${it % 10}",
                isOnline = it % 5 != 0
            )
        }
        coEvery { homeRepository.getAllDevices() } returns flowOf(ApiResult.Success(largeDeviceList))

        // When
        viewModel.loadAllDevices()
        advanceUntilIdle()

        // Then
        val devices = (viewModel.devicesState.value as? UiDataState.Success)?.data
        assertEquals(100, devices?.size)
    }

    @Test
    fun `toggleDevicePower with offline device should handle error`() = runTest {
        // Given
        val exception = Exception("Device is offline")
        coEvery { homeRepository.toggleDevicePower(any(), any()) } returns flowOf(ApiResult.Error(exception))

        // When & Then - Should not crash
        viewModel.toggleDevicePower("offline_device", true)
        advanceUntilIdle()
    }

    @Test
    fun `consecutive refreshes should handle correctly`() = runTest {
        // Given
        coEvery { homeRepository.getHomeOverview() } returns flowOf(ApiResult.Success(createMockHomeOverview()))
        coEvery { homeRepository.getAllDevices() } returns flowOf(ApiResult.Success(emptyList()))
        coEvery { homeRepository.getHouseInfo(any()) } returns flowOf(ApiResult.Success(createMockHouseInfo()))
        coEvery { homeRepository.getFloorList(any()) } returns flowOf(ApiResult.Success(emptyList()))
        coEvery { homeRepository.getSceneList(any()) } returns flowOf(ApiResult.Success(emptyList()))
        coEvery { homeRepository.getSystemStatus(any()) } returns flowOf(ApiResult.Success(createMockSystemStatus()))

        // When - Multiple consecutive refreshes
        viewModel.refresh()
        viewModel.refresh()
        viewModel.refresh()
        advanceUntilIdle()

        // Then - Should complete without issues
        // Verify methods were called multiple times
        coVerify(atLeast = 3) { homeRepository.getHomeOverview() }
    }

    // ==================== 事件测试 ====================

    @Test
    fun `onDeviceCardClicked should emit device click event`() = runTest {
        // Given & When & Then
        viewModel.deviceClickEvent.test {
            viewModel.onDeviceCardClicked("device1")
            assertEquals("device1", awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `onServiceClicked should emit service click event`() = runTest {
        // Given & When & Then
        viewModel.serviceClickEvent.test {
            viewModel.onServiceClicked(ServiceType.AIR_CONDITIONER)
            assertEquals(ServiceType.AIR_CONDITIONER, awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }

    // ==================== 系统模式测试 ====================

    @Test
    fun `setSystemMode should call repository and refresh system status`() = runTest {
        // Given
        coEvery { homeRepository.setSystemMode(any(), any()) } returns flowOf(ApiResult.Success(Unit))
        coEvery { homeRepository.getSystemStatus(any()) } returns flowOf(ApiResult.Success(createMockSystemStatus()))

        // When
        viewModel.setSystemMode(1, SystemMode.COMFORT)
        advanceUntilIdle()

        // Then
        coVerify { homeRepository.setSystemMode(1, SystemMode.COMFORT.value) }
        coVerify { homeRepository.getSystemStatus(1) }
    }

    @Test
    fun `setGlobalTemp should call repository and refresh system status`() = runTest {
        // Given
        coEvery { homeRepository.setGlobalTemp(any(), any()) } returns flowOf(ApiResult.Success(Unit))
        coEvery { homeRepository.getSystemStatus(any()) } returns flowOf(ApiResult.Success(createMockSystemStatus()))

        // When
        viewModel.setGlobalTemp(1, "25")
        advanceUntilIdle()

        // Then
        coVerify { homeRepository.setGlobalTemp(1, "25") }
        coVerify { homeRepository.getSystemStatus(1) }
    }

    @Test
    fun `setGlobalHumidity should call repository and refresh system status`() = runTest {
        // Given
        coEvery { homeRepository.setGlobalHumidity(any(), any()) } returns flowOf(ApiResult.Success(Unit))
        coEvery { homeRepository.getSystemStatus(any()) } returns flowOf(ApiResult.Success(createMockSystemStatus()))

        // When
        viewModel.setGlobalHumidity(1, "50")
        advanceUntilIdle()

        // Then
        coVerify { homeRepository.setGlobalHumidity(1, "50") }
        coVerify { homeRepository.getSystemStatus(1) }
    }

    // ==================== Helper functions ====================

    private fun createMockHomeOverview(): HomeOverview {
        return HomeOverview(
            roomCount = 8,
            deviceCount = 12,
            onlineDeviceCount = 10,
            indoorTemperature = 24.5,
            indoorHumidity = 48,
            pm25 = 12,
            co2 = 420,
            voc = 0.3,
            residenceName = "西湖壹号院",
            address = "杭州市西湖区"
        )
    }

    private fun createMockHouseInfo(): HouseInfo {
        return HouseInfo(
            id = 1,
            name = "西湖一号院",
            address = "杭州市西湖区"
        )
    }

    private fun createMockSystemStatus(): SystemStatus {
        return SystemStatus(
            mode = SystemMode.COMFORT,
            temperature = "24",
            humidity = "48",
            isRunning = true
        )
    }
}
