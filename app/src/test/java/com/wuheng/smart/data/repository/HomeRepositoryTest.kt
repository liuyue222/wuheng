package com.wuheng.smart.data.repository

import com.wuheng.smart.data.model.*
import com.wuheng.smart.data.network.ApiResult
import com.wuheng.smart.data.network.ApiService
import com.wuheng.smart.data.network.BaseResponse
import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.*

/**
 * HomeRepository 单元测试
 *
 * 测试范围:
 * 1. 房屋信息获取
 * 2. 楼层/房间/设备列表获取
 * 3. 设备控制和数据获取
 * 4. 场景管理
 * 5. 系统状态和控制
 */
@OptIn(ExperimentalCoroutinesApi::class)
class HomeRepositoryTest {

    private lateinit var apiService: ApiService
    private lateinit var homeRepository: HomeRepositoryImpl

    @BeforeEach
    fun setUp() {
        apiService = mockk(relaxed = true)
        homeRepository = HomeRepositoryImpl(apiService, useMock = false)
    }

    @AfterEach
    fun tearDown() {
        unmockkAll()
    }

    // ==================== 房屋信息测试 ====================

    @Test
    fun `getHouseInfo should return house details`() = runTest {
        // Given
        val mockHouse = HouseInfo(
            houseId = 1,
            houseIdNo = "HOUSE001",
            houseName = "西湖壹号院",
            ownerName = "张三",
            ownerPhone = "13800138000",
            address = "杭州市西湖区文三路123号",
            floorCount = 3,
            areaTotal = "280",
            systemType = "五恒系统",
            roomCount = 8,
            deviceCount = 12,
            onlineCount = 10
        )
        coEvery { apiService.getHouseInfo(1) } returns BaseResponse(200, "success", mockHouse)

        // When
        val result = homeRepository.getHouseInfo(1).first()

        // Then
        assertTrue(result is ApiResult.Success)
        assertEquals(mockHouse, (result as ApiResult.Success).data)
    }

    @Test
    fun `getHouseInfo with invalid id should return error`() = runTest {
        // Given
        coEvery { apiService.getHouseInfo(999) } returns BaseResponse(404, "House not found", null)

        // When
        val result = homeRepository.getHouseInfo(999).first()

        // Then
        assertTrue(result is ApiResult.Error)
    }

    // ==================== 楼层列表测试 ====================

    @Test
    fun `getFloorList should return floors for house`() = runTest {
        // Given
        val mockFloors = listOf(
            FloorInfo(1, "FLOOR001", "一楼", 1, "120", 3),
            FloorInfo(2, "FLOOR002", "二楼", 2, "100", 3),
            FloorInfo(3, "FLOOR003", "地下室", -1, "60", 2)
        )
        coEvery { apiService.getFloorList(1) } returns BaseResponse(200, "success", mockFloors)

        // When
        val result = homeRepository.getFloorList(1).first()

        // Then
        assertTrue(result is ApiResult.Success)
        assertEquals(3, (result as ApiResult.Success).data.size)
    }

    @Test
    fun `getFloorList should return empty list when no floors`() = runTest {
        // Given
        coEvery { apiService.getFloorList(1) } returns BaseResponse(200, "success", emptyList<FloorInfo>())

        // When
        val result = homeRepository.getFloorList(1).first()

        // Then
        assertTrue(result is ApiResult.Success)
        assertTrue((result as ApiResult.Success).data.isEmpty())
    }

    // ==================== 房间列表测试 ====================

    @Test
    fun `getRoomList should return rooms for house`() = runTest {
        // Given
        val mockRooms = listOf(
            RoomInfo(1, "ROOM001", "客厅", "living", "45", 3),
            RoomInfo(2, "ROOM002", "主卧", "bedroom", "25", 2),
            RoomInfo(3, "ROOM003", "厨房", "kitchen", "15", 2)
        )
        coEvery { apiService.getRoomList(1, null) } returns BaseResponse(200, "success", mockRooms)

        // When
        val result = homeRepository.getRoomList(1).first()

        // Then
        assertTrue(result is ApiResult.Success)
        assertEquals(3, (result as ApiResult.Success).data.size)
    }

    @Test
    fun `getRoomList with floor filter should return filtered rooms`() = runTest {
        // Given
        val mockRooms = listOf(RoomInfo(1, "ROOM001", "客厅", "living", "45", 3))
        coEvery { apiService.getRoomList(1, 1) } returns BaseResponse(200, "success", mockRooms)

        // When
        val result = homeRepository.getRoomList(1, 1).first()

        // Then
        assertTrue(result is ApiResult.Success)
        assertEquals(1, (result as ApiResult.Success).data.size)
    }

    // ==================== 设备列表测试 ====================

    @Test
    fun `getDeviceList should return devices for house`() = runTest {
        // Given
        val mockDevices = listOf(
            DeviceInfo(1, "DEV001", "客厅温控器", "thermostat", "TH-001", 1, "running", "客厅"),
            DeviceInfo(2, "DEV002", "主卧温控器", "thermostat", "TH-002", 1, "standby", "主卧"),
            DeviceInfo(3, "DEV003", "环境传感器", "sensor", "SE-001", 1, "running", "客厅")
        )
        coEvery { apiService.getDeviceList(1, null) } returns BaseResponse(200, "success", mockDevices)

        // When
        val result = homeRepository.getDeviceList(1).first()

        // Then
        assertTrue(result is ApiResult.Success)
        assertEquals(3, (result as ApiResult.Success).data.size)
    }

    @Test
    fun `getDeviceInfo should return single device details`() = runTest {
        // Given
        val mockDevice = DeviceInfo(
            deviceId = 1,
            deviceIdNo = "DEV001",
            deviceName = "客厅温控器",
            deviceType = "thermostat",
            deviceModel = "TH-001",
            onlineStatus = 1,
            runStatus = "running",
            roomName = "客厅"
        )
        coEvery { apiService.getDeviceInfo(1) } returns BaseResponse(200, "success", mockDevice)

        // When
        val result = homeRepository.getDeviceInfo(1).first()

        // Then
        assertTrue(result is ApiResult.Success)
        assertEquals(mockDevice, (result as ApiResult.Success).data)
    }

    @Test
    fun `getDeviceData should return device sensor data`() = runTest {
        // Given
        val mockData = DeviceData(
            dataId = 1,
            deviceId = 1,
            temperature = "24.5",
            humidity = "55",
            co2 = 450,
            pm25 = 35,
            voc = 200,
            fanSpeed = 2,
            valveOpen = 80,
            power = 1,
            reportTime = System.currentTimeMillis()
        )
        coEvery { apiService.getDeviceData(1) } returns BaseResponse(200, "success", mockData)

        // When
        val result = homeRepository.getDeviceData(1).first()

        // Then
        assertTrue(result is ApiResult.Success)
        assertEquals(24.5f, (result as ApiResult.Success).data.temperature.toFloat(), 0.1f)
    }

    // ==================== 设备控制测试 ====================

    @Test
    fun `controlDevice should send command successfully`() = runTest {
        // Given
        val mockResponse = ControlDeviceResponse("on", "", 1)
        coEvery { apiService.controlDevice(any()) } returns BaseResponse(200, "success", mockResponse)

        // When
        val result = homeRepository.controlDevice(1, "on", null).first()

        // Then
        assertTrue(result is ApiResult.Success)
    }

    @Test
    fun `toggleDevicePower should control device power`() = runTest {
        // Given
        coEvery { apiService.controlDeviceOld(any()) } returns BaseResponse(200, "success", Unit)

        // When
        val result = homeRepository.toggleDevicePower("device1", true).first()

        // Then
        assertTrue(result is ApiResult.Success)
    }

    @Test
    fun `setDeviceTemperature should update temperature`() = runTest {
        // Given
        coEvery { apiService.controlDeviceOld(any()) } returns BaseResponse(200, "success", Unit)

        // When
        val result = homeRepository.setDeviceTemperature("device1", 25.0).first()

        // Then
        assertTrue(result is ApiResult.Success)
    }

    // ==================== 场景管理测试 ====================

    @Test
    fun `getSceneList should return available scenes`() = runTest {
        // Given
        val mockScenes = listOf(
            SceneInfo(1, "SCENE001", "会客模式", "guest", "24", "50", 0, 1, 0, 1),
            SceneInfo(2, "SCENE002", "离家模式", "away", "18", "40", 0, 0, 0, 0),
            SceneInfo(3, "SCENE003", "睡眠模式", "sleep", "26", "45", 1, 0, 0, 1),
            SceneInfo(4, "SCENE004", "值守模式", "home", "22", "50", 0, 1, 1, 1)
        )
        coEvery { apiService.getSceneList(1) } returns BaseResponse(200, "success", mockScenes)

        // When
        val result = homeRepository.getSceneList(1).first()

        // Then
        assertTrue(result is ApiResult.Success)
        assertEquals(4, (result as ApiResult.Success).data.size)
    }

    @Test
    fun `applyScene should activate scene`() = runTest {
        // Given
        val mockResponse = ApplySceneResponse(1, "会客模式", "24", "50")
        coEvery { apiService.applyScene(any()) } returns BaseResponse(200, "success", mockResponse)

        // When
        val result = homeRepository.applyScene(1, 1).first()

        // Then
        assertTrue(result is ApiResult.Success)
        assertEquals("会客模式", (result as ApiResult.Success).data.sceneName)
    }

    @Test
    fun `saveScene should save custom scene`() = runTest {
        // Given
        val request = SaveSceneRequest(1, "自定义场景", "25", "55", 800, 2, 1, 0, 1)
        coEvery { apiService.saveScene(any()) } returns BaseResponse(200, "success", Unit)

        // When
        val result = homeRepository.saveScene(request).first()

        // Then
        assertTrue(result is ApiResult.Success)
    }

    // ==================== 系统状态测试 ====================

    @Test
    fun `getSystemStatus should return current system status`() = runTest {
        // Given
        val mockStatus = SystemStatus(
            systemStatus = SystemStatusInfo(
                systemMode = "cooling",
                globalTempSet = "24",
                globalHumiditySet = "50",
                avgIndoorTemp = "23.5",
                avgIndoorHumidity = "52",
                systemRunStatus = "running"
            ),
            houseInfo = null,
            deviceCount = 12,
            onlineCount = 10
        )
        coEvery { apiService.getSystemStatus(1) } returns BaseResponse(200, "success", mockStatus)

        // When
        val result = homeRepository.getSystemStatus(1).first()

        // Then
        assertTrue(result is ApiResult.Success)
        assertEquals("cooling", (result as ApiResult.Success).data.systemStatus.systemMode)
    }

    @Test
    fun `setSystemMode should change system mode`() = runTest {
        // Given
        val mockResponse = SetSystemModeResponse("heating")
        coEvery { apiService.setSystemMode(any()) } returns BaseResponse(200, "success", mockResponse)

        // When
        val result = homeRepository.setSystemMode(1, "heating").first()

        // Then
        assertTrue(result is ApiResult.Success)
    }

    @Test
    fun `setGlobalTemp should update global temperature`() = runTest {
        // Given
        coEvery { apiService.setGlobalTemp(any()) } returns BaseResponse(200, "success", Unit)

        // When
        val result = homeRepository.setGlobalTemp(1, "25").first()

        // Then
        assertTrue(result is ApiResult.Success)
    }

    @Test
    fun `setGlobalHumidity should update global humidity`() = runTest {
        // Given
        coEvery { apiService.setGlobalHumidity(any()) } returns BaseResponse(200, "success", Unit)

        // When
        val result = homeRepository.setGlobalHumidity(1, "55").first()

        // Then
        assertTrue(result is ApiResult.Success)
    }

    // ==================== 天气模式测试 ====================

    @Test
    fun `setWeatherMode should change weather mode`() = runTest {
        // Given
        coEvery { apiService.setWeatherMode(any()) } returns BaseResponse(200, "success", Unit)

        // When
        val result = homeRepository.setWeatherMode(WeatherMode.COOLING).first()

        // Then
        assertTrue(result is ApiResult.Success)
    }

    // ==================== 旧版API兼容测试 ====================

    @Test
    fun `getHomeOverview should return overview data`() = runTest {
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
        coEvery { apiService.getHomeOverview() } returns BaseResponse(200, "success", mockOverview)

        // When
        val result = homeRepository.getHomeOverview().first()

        // Then
        assertTrue(result is ApiResult.Success)
        assertEquals("西湖壹号院", (result as ApiResult.Success).data.residenceName)
    }

    @Test
    fun `getAllDevices should return device list`() = runTest {
        // Given
        val mockDevices = listOf(
            Device(id = "1", name = "客厅空调", type = DeviceType.CLIMATE, status = DeviceStatus.ON, roomName = "客厅", isOnline = true),
            Device(id = "2", name = "主卧空调", type = DeviceType.CLIMATE, status = DeviceStatus.OFF, roomName = "主卧", isOnline = true)
        )
        coEvery { apiService.getAllDevices() } returns BaseResponse(200, "success", mockDevices)

        // When
        val result = homeRepository.getAllDevices().first()

        // Then
        assertTrue(result is ApiResult.Success)
        assertEquals(2, (result as ApiResult.Success).data.size)
    }

    // ==================== 错误处理测试 ====================

    @Test
    fun `getDeviceInfo with offline device should handle gracefully`() = runTest {
        // Given
        coEvery { apiService.getDeviceInfo(1) } returns BaseResponse(503, "Device offline", null)

        // When
        val result = homeRepository.getDeviceInfo(1).first()

        // Then
        assertTrue(result is ApiResult.Error)
    }

    @Test
    fun `controlDevice when device busy should return error`() = runTest {
        // Given
        coEvery { apiService.controlDevice(any()) } returns BaseResponse(423, "Device is busy", null)

        // When
        val result = homeRepository.controlDevice(1, "on", null).first()

        // Then
        assertTrue(result is ApiResult.Error)
    }

    @Test
    fun `applyScene with invalid scene should return error`() = runTest {
        // Given
        coEvery { apiService.applyScene(any()) } returns BaseResponse(404, "Scene not found", null)

        // When
        val result = homeRepository.applyScene(999, 1).first()

        // Then
        assertTrue(result is ApiResult.Error)
    }
}
