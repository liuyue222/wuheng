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
 * ClimateRepository 单元测试
 *
 * 测试范围:
 * 1. 系统状态获取与设置
 * 2. 全局温度和湿度控制
 * 3. 楼层和区域管理
 * 4. 区域温度/模式/电源控制
 */
@OptIn(ExperimentalCoroutinesApi::class)
class ClimateRepositoryTest {

    private lateinit var apiService: ApiService
    private lateinit var climateRepository: ClimateRepositoryImpl

    @BeforeEach
    fun setUp() {
        apiService = mockk(relaxed = true)
        climateRepository = ClimateRepositoryImpl(apiService, useMock = false)
    }

    @AfterEach
    fun tearDown() {
        unmockkAll()
    }

    // ==================== 系统状态测试 ====================

    @Test
    fun `getSystemStatus should return current climate system status`() = runTest {
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
        val result = climateRepository.getSystemStatus(1).first()

        // Then
        assertTrue(result is ApiResult.Success)
        assertEquals("cooling", (result as ApiResult.Success).data.systemStatus.systemMode)
        assertEquals("24", result.data.systemStatus.globalTempSet)
    }

    @Test
    fun `getSystemStatus with different modes should return correct mode`() = runTest {
        // Given - Heating mode
        val mockStatus = SystemStatus(
            systemStatus = SystemStatusInfo(
                systemMode = "heating",
                globalTempSet = "26",
                globalHumiditySet = "45",
                avgIndoorTemp = "25.5",
                avgIndoorHumidity = "48",
                systemRunStatus = "running"
            ),
            houseInfo = null,
            deviceCount = 12,
            onlineCount = 10
        )
        coEvery { apiService.getSystemStatus(1) } returns BaseResponse(200, "success", mockStatus)

        // When
        val result = climateRepository.getSystemStatus(1).first()

        // Then
        assertTrue(result is ApiResult.Success)
        assertEquals("heating", (result as ApiResult.Success).data.systemStatus.systemMode)
    }

    // ==================== 系统模式设置测试 ====================

    @Test
    fun `setSystemMode to cooling should succeed`() = runTest {
        // Given
        val mockResponse = SetSystemModeResponse("cooling")
        coEvery { apiService.setSystemMode(any()) } returns BaseResponse(200, "success", mockResponse)

        // When
        val result = climateRepository.setSystemMode(1, SystemMode.COOLING).first()

        // Then
        assertTrue(result is ApiResult.Success)
        assertEquals("cooling", (result as ApiResult.Success).data.mode)
    }

    @Test
    fun `setSystemMode to heating should succeed`() = runTest {
        // Given
        val mockResponse = SetSystemModeResponse("heating")
        coEvery { apiService.setSystemMode(any()) } returns BaseResponse(200, "success", mockResponse)

        // When
        val result = climateRepository.setSystemMode(1, SystemMode.HEATING).first()

        // Then
        assertTrue(result is ApiResult.Success)
        assertEquals("heating", (result as ApiResult.Success).data.mode)
    }

    @Test
    fun `setSystemMode to ventilation should succeed`() = runTest {
        // Given
        val mockResponse = SetSystemModeResponse("ventilation")
        coEvery { apiService.setSystemMode(any()) } returns BaseResponse(200, "success", mockResponse)

        // When
        val result = climateRepository.setSystemMode(1, SystemMode.VENTILATION).first()

        // Then
        assertTrue(result is ApiResult.Success)
        assertEquals("ventilation", (result as ApiResult.Success).data.mode)
    }

    @Test
    fun `setSystemMode to auto should succeed`() = runTest {
        // Given
        val mockResponse = SetSystemModeResponse("auto")
        coEvery { apiService.setSystemMode(any()) } returns BaseResponse(200, "success", mockResponse)

        // When
        val result = climateRepository.setSystemMode(1, SystemMode.AUTO).first()

        // Then
        assertTrue(result is ApiResult.Success)
        assertEquals("auto", (result as ApiResult.Success).data.mode)
    }

    @Test
    fun `setSystemMode with invalid house should return error`() = runTest {
        // Given
        coEvery { apiService.setSystemMode(any()) } returns BaseResponse(404, "House not found", null)

        // When
        val result = climateRepository.setSystemMode(999, SystemMode.COOLING).first()

        // Then
        assertTrue(result is ApiResult.Error)
    }

    // ==================== 全局温度设置测试 ====================

    @Test
    fun `setGlobalTemp with valid temperature should succeed`() = runTest {
        // Given
        coEvery { apiService.setGlobalTemp(any()) } returns BaseResponse(200, "success", Unit)

        // When
        val result = climateRepository.setGlobalTemp(1, "24").first()

        // Then
        assertTrue(result is ApiResult.Success)
    }

    @Test
    fun `setGlobalTemp with minimum temperature should succeed`() = runTest {
        // Given
        coEvery { apiService.setGlobalTemp(any()) } returns BaseResponse(200, "success", Unit)

        // When
        val result = climateRepository.setGlobalTemp(1, "16").first()

        // Then
        assertTrue(result is ApiResult.Success)
    }

    @Test
    fun `setGlobalTemp with maximum temperature should succeed`() = runTest {
        // Given
        coEvery { apiService.setGlobalTemp(any()) } returns BaseResponse(200, "success", Unit)

        // When
        val result = climateRepository.setGlobalTemp(1, "30").first()

        // Then
        assertTrue(result is ApiResult.Success)
    }

    @Test
    fun `setGlobalTemp with out of range temperature should return error`() = runTest {
        // Given
        coEvery { apiService.setGlobalTemp(any()) } returns BaseResponse(400, "Temperature out of range", null)

        // When
        val result = climateRepository.setGlobalTemp(1, "35").first()

        // Then
        assertTrue(result is ApiResult.Error)
    }

    // ==================== 全局湿度设置测试 ====================

    @Test
    fun `setGlobalHumidity with valid humidity should succeed`() = runTest {
        // Given
        coEvery { apiService.setGlobalHumidity(any()) } returns BaseResponse(200, "success", Unit)

        // When
        val result = climateRepository.setGlobalHumidity(1, "50").first()

        // Then
        assertTrue(result is ApiResult.Success)
    }

    @Test
    fun `setGlobalHumidity with minimum humidity should succeed`() = runTest {
        // Given
        coEvery { apiService.setGlobalHumidity(any()) } returns BaseResponse(200, "success", Unit)

        // When
        val result = climateRepository.setGlobalHumidity(1, "30").first()

        // Then
        assertTrue(result is ApiResult.Success)
    }

    @Test
    fun `setGlobalHumidity with maximum humidity should succeed`() = runTest {
        // Given
        coEvery { apiService.setGlobalHumidity(any()) } returns BaseResponse(200, "success", Unit)

        // When
        val result = climateRepository.setGlobalHumidity(1, "70").first()

        // Then
        assertTrue(result is ApiResult.Success)
    }

    // ==================== 旧版API - 概览测试 ====================

    @Test
    fun `getClimateOverview should return climate system overview`() = runTest {
        // Given
        val mockOverview = ClimateOverview(
            currentTemperature = 24.5,
            targetTemperature = 24.0,
            currentMode = ClimateMode.COOLING,
            isRunning = true,
            floorCount = 3,
            zoneCount = 8,
            runningZoneCount = 6,
            averageHumidity = 50,
            outdoorTemperature = 28.0
        )
        coEvery { apiService.getClimateOverview() } returns BaseResponse(200, "success", mockOverview)

        // When
        val result = climateRepository.getClimateOverview().first()

        // Then
        assertTrue(result is ApiResult.Success)
        assertEquals(24.5, (result as ApiResult.Success).data.currentTemperature, 0.01)
        assertEquals(ClimateMode.COOLING, result.data.currentMode)
    }

    @Test
    fun `getClimateOverview when system off should return correct state`() = runTest {
        // Given
        val mockOverview = ClimateOverview(
            currentTemperature = 22.0,
            targetTemperature = 24.0,
            currentMode = ClimateMode.OFF,
            isRunning = false,
            floorCount = 3,
            zoneCount = 8,
            runningZoneCount = 0,
            averageHumidity = 55
        )
        coEvery { apiService.getClimateOverview() } returns BaseResponse(200, "success", mockOverview)

        // When
        val result = climateRepository.getClimateOverview().first()

        // Then
        assertTrue(result is ApiResult.Success)
        assertEquals(ClimateMode.OFF, (result as ApiResult.Success).data.currentMode)
        assertFalse(result.data.isRunning)
    }

    // ==================== 旧版API - 楼层测试 ====================

    @Test
    fun `getFloors should return list of floors`() = runTest {
        // Given
        val mockFloors = listOf(
            Floor(id = "1", name = "一楼", order = 1, zoneCount = 3, runningZoneCount = 2, averageTemperature = 24.0),
            Floor(id = "2", name = "二楼", order = 2, zoneCount = 3, runningZoneCount = 2, averageTemperature = 24.5),
            Floor(id = "b1", name = "地下室", order = 0, zoneCount = 2, runningZoneCount = 1, averageTemperature = 22.0)
        )
        coEvery { apiService.getFloors() } returns BaseResponse(200, "success", mockFloors)

        // When
        val result = climateRepository.getFloors().first()

        // Then
        assertTrue(result is ApiResult.Success)
        assertEquals(3, (result as ApiResult.Success).data.size)
    }

    // ==================== 旧版API - 区域测试 ====================

    @Test
    fun `getZonesByFloor should return zones for specific floor`() = runTest {
        // Given
        val mockZones = listOf(
            Zone(id = "z1", name = "客厅", floorId = "1", currentTemperature = 24.0, targetTemperature = 24.0, mode = ClimateMode.COOLING, isRunning = true, isOnline = true),
            Zone(id = "z2", name = "主卧", floorId = "1", currentTemperature = 24.5, targetTemperature = 24.0, mode = ClimateMode.COOLING, isRunning = true, isOnline = true),
            Zone(id = "z3", name = "厨房", floorId = "1", currentTemperature = 25.0, targetTemperature = 24.0, mode = ClimateMode.COOLING, isRunning = false, isOnline = true)
        )
        coEvery { apiService.getZonesByFloor("1") } returns BaseResponse(200, "success", mockZones)

        // When
        val result = climateRepository.getZonesByFloor("1").first()

        // Then
        assertTrue(result is ApiResult.Success)
        assertEquals(3, (result as ApiResult.Success).data.size)
    }

    @Test
    fun `getZoneDetail should return detailed zone information`() = runTest {
        // Given
        val mockDetail = ZoneDetail(
            id = "z1",
            name = "客厅",
            floorId = "1",
            floorName = "一楼",
            currentTemperature = 24.0,
            targetTemperature = 24.0,
            humidity = 50,
            targetHumidity = 55,
            mode = ClimateMode.COOLING,
            isRunning = true,
            isOnline = true,
            fanSpeed = FanSpeed.AUTO
        )
        coEvery { apiService.getZoneDetail("z1") } returns BaseResponse(200, "success", mockDetail)

        // When
        val result = climateRepository.getZoneDetail("z1").first()

        // Then
        assertTrue(result is ApiResult.Success)
        assertEquals("客厅", (result as ApiResult.Success).data.name)
        assertEquals(24.0, result.data.currentTemperature, 0.01)
    }

    // ==================== 区域控制测试 ====================

    @Test
    fun `setZoneTemperature should update zone temperature`() = runTest {
        // Given
        coEvery { apiService.setZoneTemperature(any(), any()) } returns BaseResponse(200, "success", Unit)

        // When
        val result = climateRepository.setZoneTemperature("z1", 25.0).first()

        // Then
        assertTrue(result is ApiResult.Success)
    }

    @Test
    fun `setZoneTemperature with extreme values should handle correctly`() = runTest {
        // Given - Very low temperature
        coEvery { apiService.setZoneTemperature(any(), any()) } returns BaseResponse(200, "success", Unit)

        // When
        val result = climateRepository.setZoneTemperature("z1", 16.0).first()

        // Then
        assertTrue(result is ApiResult.Success)
    }

    @Test
    fun `setZoneMode should change zone mode`() = runTest {
        // Given
        coEvery { apiService.setZoneMode(any(), any()) } returns BaseResponse(200, "success", Unit)

        // When
        val result = climateRepository.setZoneMode("z1", ClimateMode.HEATING).first()

        // Then
        assertTrue(result is ApiResult.Success)
    }

    @Test
    fun `setZoneMode to different modes should all succeed`() = runTest {
        // Given
        coEvery { apiService.setZoneMode(any(), any()) } returns BaseResponse(200, "success", Unit)

        // When & Then - Test all modes
        ClimateMode.values().forEach { mode ->
            val result = climateRepository.setZoneMode("z1", mode).first()
            assertTrue(result is ApiResult.Success, "Mode $mode should succeed")
        }
    }

    @Test
    fun `setZonePower on should enable zone`() = runTest {
        // Given
        coEvery { apiService.setZonePower(any(), any()) } returns BaseResponse(200, "success", Unit)

        // When
        val result = climateRepository.setZonePower("z1", true).first()

        // Then
        assertTrue(result is ApiResult.Success)
    }

    @Test
    fun `setZonePower off should disable zone`() = runTest {
        // Given
        coEvery { apiService.setZonePower(any(), any()) } returns BaseResponse(200, "success", Unit)

        // When
        val result = climateRepository.setZonePower("z1", false).first()

        // Then
        assertTrue(result is ApiResult.Success)
    }

    // ==================== 错误处理测试 ====================

    @Test
    fun `getSystemStatus when system error should return error`() = runTest {
        // Given
        coEvery { apiService.getSystemStatus(any()) } returns BaseResponse(500, "System error", null)

        // When
        val result = climateRepository.getSystemStatus(1).first()

        // Then
        assertTrue(result is ApiResult.Error)
    }

    @Test
    fun `setZoneTemperature when zone offline should return error`() = runTest {
        // Given
        coEvery { apiService.setZoneTemperature(any(), any()) } returns BaseResponse(503, "Zone offline", null)

        // When
        val result = climateRepository.setZoneTemperature("z1", 25.0).first()

        // Then
        assertTrue(result is ApiResult.Error)
    }

    @Test
    fun `getZoneDetail with invalid zone should return error`() = runTest {
        // Given
        coEvery { apiService.getZoneDetail(any()) } returns BaseResponse(404, "Zone not found", null)

        // When
        val result = climateRepository.getZoneDetail("invalid").first()

        // Then
        assertTrue(result is ApiResult.Error)
    }

    @Test
    fun `setSystemMode when system locked should return error`() = runTest {
        // Given
        coEvery { apiService.setSystemMode(any()) } returns BaseResponse(423, "System is locked", null)

        // When
        val result = climateRepository.setSystemMode(1, SystemMode.COOLING).first()

        // Then
        assertTrue(result is ApiResult.Error)
    }
}
