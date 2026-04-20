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
 * WaterRepository 单元测试
 *
 * 测试范围:
 * 1. 水系统状态获取与设置
 * 2. 水温控制
 * 3. 水系统模式设置
 * 4. 耗材进度获取
 * 5. 耗材重置
 */
@OptIn(ExperimentalCoroutinesApi::class)
class WaterRepositoryTest {

    private lateinit var apiService: ApiService
    private lateinit var waterRepository: WaterRepositoryImpl

    @BeforeEach
    fun setUp() {
        apiService = mockk(relaxed = true)
        waterRepository = WaterRepositoryImpl(apiService, useMock = false)
    }

    @AfterEach
    fun tearDown() {
        unmockkAll()
    }

    // ==================== 水系统状态测试 ====================

    @Test
    fun `getWaterSystemStatus should return water system status`() = runTest {
        // Given
        val mockStatus = WaterSystemStatus(
            systemId = 1,
            systemIdNo = "WATER001",
            systemName = "全屋净水系统",
            systemType = "全屋净水",
            status = 1,
            inletTemp = "15.5",
            outletTemp = "45.0",
            waterTempSet = "45",
            waterFlow = "2.5",
            waterPressure = "0.35",
            totalFlow = "1250.5",
            filterStatus = 1,
            runStatus = "running",
            reportTime = System.currentTimeMillis()
        )
        coEvery { apiService.getWaterSystemStatus(1) } returns BaseResponse(200, "success", mockStatus)

        // When
        val result = waterRepository.getWaterSystemStatus(1).first()

        // Then
        assertTrue(result is ApiResult.Success)
        assertEquals("45.0", (result as ApiResult.Success).data.outletTemp)
        assertEquals("running", result.data.runStatus)
    }

    @Test
    fun `getWaterSystemStatus when system off should return correct state`() = runTest {
        // Given
        val mockStatus = WaterSystemStatus(
            systemId = 1,
            systemIdNo = "WATER001",
            systemName = "全屋净水系统",
            systemType = "全屋净水",
            status = 0,
            inletTemp = "15.5",
            outletTemp = "20.0",
            waterTempSet = "45",
            waterFlow = "0.0",
            waterPressure = "0.35",
            totalFlow = "1250.5",
            filterStatus = 1,
            runStatus = "stopped",
            reportTime = System.currentTimeMillis()
        )
        coEvery { apiService.getWaterSystemStatus(1) } returns BaseResponse(200, "success", mockStatus)

        // When
        val result = waterRepository.getWaterSystemStatus(1).first()

        // Then
        assertTrue(result is ApiResult.Success)
        assertEquals("stopped", (result as ApiResult.Success).data.runStatus)
        assertEquals(0, result.data.status)
    }

    @Test
    fun `getWaterSystemStatus with invalid system should return error`() = runTest {
        // Given
        coEvery { apiService.getWaterSystemStatus(999) } returns BaseResponse(404, "Water system not found", null)

        // When
        val result = waterRepository.getWaterSystemStatus(999).first()

        // Then
        assertTrue(result is ApiResult.Error)
    }

    // ==================== 水温设置测试 ====================

    @Test
    fun `setWaterTemperature with valid temperature should succeed`() = runTest {
        // Given
        coEvery { apiService.setWaterTemperature(any()) } returns BaseResponse(200, "success", Unit)

        // When
        val result = waterRepository.setWaterTemperature(1, "45").first()

        // Then
        assertTrue(result is ApiResult.Success)
    }

    @Test
    fun `setWaterTemperature with minimum temperature should succeed`() = runTest {
        // Given
        coEvery { apiService.setWaterTemperature(any()) } returns BaseResponse(200, "success", Unit)

        // When
        val result = waterRepository.setWaterTemperature(1, "30").first()

        // Then
        assertTrue(result is ApiResult.Success)
    }

    @Test
    fun `setWaterTemperature with maximum temperature should succeed`() = runTest {
        // Given
        coEvery { apiService.setWaterTemperature(any()) } returns BaseResponse(200, "success", Unit)

        // When
        val result = waterRepository.setWaterTemperature(1, "60").first()

        // Then
        assertTrue(result is ApiResult.Success)
    }

    @Test
    fun `setWaterTemperature with out of range temperature should return error`() = runTest {
        // Given
        coEvery { apiService.setWaterTemperature(any()) } returns BaseResponse(400, "Temperature out of range", null)

        // When
        val result = waterRepository.setWaterTemperature(1, "80").first()

        // Then
        assertTrue(result is ApiResult.Error)
    }

    // ==================== 水系统模式测试 ====================

    @Test
    fun `setWaterSystemMode to comfort should succeed`() = runTest {
        // Given
        coEvery { apiService.setWaterSystemMode(any()) } returns BaseResponse(200, "success", Unit)

        // When
        val result = waterRepository.setWaterSystemMode(1, WaterSystemMode.COMFORT).first()

        // Then
        assertTrue(result is ApiResult.Success)
    }

    @Test
    fun `setWaterSystemMode to eco should succeed`() = runTest {
        // Given
        coEvery { apiService.setWaterSystemMode(any()) } returns BaseResponse(200, "success", Unit)

        // When
        val result = waterRepository.setWaterSystemMode(1, WaterSystemMode.ECO).first()

        // Then
        assertTrue(result is ApiResult.Success)
    }

    @Test
    fun `setWaterSystemMode to away should succeed`() = runTest {
        // Given
        coEvery { apiService.setWaterSystemMode(any()) } returns BaseResponse(200, "success", Unit)

        // When
        val result = waterRepository.setWaterSystemMode(1, WaterSystemMode.AWAY).first()

        // Then
        assertTrue(result is ApiResult.Success)
    }

    @Test
    fun `setWaterSystemMode with invalid mode should return error`() = runTest {
        // Given
        coEvery { apiService.setWaterSystemMode(any()) } returns BaseResponse(400, "Invalid mode", null)

        // When
        val result = waterRepository.setWaterSystemMode(1, WaterSystemMode.COMFORT).first()

        // Then
        assertTrue(result is ApiResult.Error)
    }

    // ==================== 旧版API - 水系统概览测试 ====================

    @Test
    fun `getWaterSystemOverview should return water system overview`() = runTest {
        // Given
        val mockOverview = WaterSystemOverview(
            currentTemperature = 45.0,
            targetTemperature = 45.0,
            currentMode = WaterMode.COMFORT,
            isRunning = true,
            inletTemperature = 15.5,
            waterFlow = 2.5,
            waterPressure = 0.35,
            totalWaterFlow = 1250.5,
            filterStatus = FilterStatus.GOOD
        )
        coEvery { apiService.getWaterSystemOverview() } returns BaseResponse(200, "success", mockOverview)

        // When
        val result = waterRepository.getWaterSystemOverview().first()

        // Then
        assertTrue(result is ApiResult.Success)
        assertEquals(45.0, (result as ApiResult.Success).data.currentTemperature, 0.01)
        assertEquals(WaterMode.COMFORT, result.data.currentMode)
    }

    @Test
    fun `getWaterSystemOverview when system off should return correct state`() = runTest {
        // Given
        val mockOverview = WaterSystemOverview(
            currentTemperature = 20.0,
            targetTemperature = 45.0,
            currentMode = WaterMode.OFF,
            isRunning = false,
            inletTemperature = 15.5,
            waterFlow = 0.0,
            waterPressure = 0.35,
            totalWaterFlow = 1250.5,
            filterStatus = FilterStatus.GOOD
        )
        coEvery { apiService.getWaterSystemOverview() } returns BaseResponse(200, "success", mockOverview)

        // When
        val result = waterRepository.getWaterSystemOverview().first()

        // Then
        assertTrue(result is ApiResult.Success)
        assertEquals(WaterMode.OFF, (result as ApiResult.Success).data.currentMode)
        assertFalse(result.data.isRunning)
    }

    // ==================== 旧版API - 水温控制测试 ====================

    @Test
    fun `setWaterTemperatureOld should update water temperature`() = runTest {
        // Given
        coEvery { apiService.setWaterTemperatureOld(any()) } returns BaseResponse(200, "success", Unit)

        // When
        val result = waterRepository.setWaterTemperatureOld(45.0).first()

        // Then
        assertTrue(result is ApiResult.Success)
    }

    @Test
    fun `setWaterTemperatureOld with boundary values should handle correctly`() = runTest {
        // Given
        coEvery { apiService.setWaterTemperatureOld(any()) } returns BaseResponse(200, "success", Unit)

        // When - Test minimum
        val minResult = waterRepository.setWaterTemperatureOld(30.0).first()
        assertTrue(minResult is ApiResult.Success)

        // When - Test maximum
        val maxResult = waterRepository.setWaterTemperatureOld(60.0).first()
        assertTrue(maxResult is ApiResult.Success)
    }

    // ==================== 旧版API - 水系统模式测试 ====================

    @Test
    fun `setWaterMode to comfort should succeed`() = runTest {
        // Given
        coEvery { apiService.setWaterMode(any()) } returns BaseResponse(200, "success", Unit)

        // When
        val result = waterRepository.setWaterMode(WaterMode.COMFORT).first()

        // Then
        assertTrue(result is ApiResult.Success)
    }

    @Test
    fun `setWaterMode to eco should succeed`() = runTest {
        // Given
        coEvery { apiService.setWaterMode(any()) } returns BaseResponse(200, "success", Unit)

        // When
        val result = waterRepository.setWaterMode(WaterMode.ECO).first()

        // Then
        assertTrue(result is ApiResult.Success)
    }

    @Test
    fun `setWaterMode to away should succeed`() = runTest {
        // Given
        coEvery { apiService.setWaterMode(any()) } returns BaseResponse(200, "success", Unit)

        // When
        val result = waterRepository.setWaterMode(WaterMode.AWAY).first()

        // Then
        assertTrue(result is ApiResult.Success)
    }

    @Test
    fun `setWaterMode to off should succeed`() = runTest {
        // Given
        coEvery { apiService.setWaterMode(any()) } returns BaseResponse(200, "success", Unit)

        // When
        val result = waterRepository.setWaterMode(WaterMode.OFF).first()

        // Then
        assertTrue(result is ApiResult.Success)
    }

    @Test
    fun `setWaterMode with all modes should succeed`() = runTest {
        // Given
        coEvery { apiService.setWaterMode(any()) } returns BaseResponse(200, "success", Unit)

        // When & Then - Test all modes
        WaterMode.values().forEach { mode ->
            val result = waterRepository.setWaterMode(mode).first()
            assertTrue(result is ApiResult.Success, "Mode $mode should succeed")
        }
    }

    // ==================== 耗材进度测试 ====================

    @Test
    fun `getConsumablesProgress should return consumables status`() = runTest {
        // Given
        val mockConsumables = ConsumablesProgress(
            filters = listOf(
                FilterInfo(
                    id = "filter1",
                    name = "PP棉滤芯",
                    type = "PP",
                    lifePercent = 85,
                    lifeDays = 180,
                    totalDays = 180,
                    status = FilterStatus.GOOD,
                    installDate = System.currentTimeMillis() - 30L * 24 * 60 * 60 * 1000, // 30 days ago
                    replaceDate = System.currentTimeMillis() + 150L * 24 * 60 * 60 * 1000 // 150 days later
                ),
                FilterInfo(
                    id = "filter2",
                    name = "活性炭滤芯",
                    type = "CTO",
                    lifePercent = 60,
                    lifeDays = 180,
                    totalDays = 180,
                    status = FilterStatus.NORMAL,
                    installDate = System.currentTimeMillis() - 72L * 24 * 60 * 60 * 1000,
                    replaceDate = System.currentTimeMillis() + 108L * 24 * 60 * 60 * 1000
                ),
                FilterInfo(
                    id = "filter3",
                    name = "RO反渗透膜",
                    type = "RO",
                    lifePercent = 15,
                    lifeDays = 365,
                    totalDays = 365,
                    status = FilterStatus.WARNING,
                    installDate = System.currentTimeMillis() - 310L * 24 * 60 * 60 * 1000,
                    replaceDate = System.currentTimeMillis() + 55L * 24 * 60 * 60 * 1000
                )
            )
        )
        coEvery { apiService.getConsumablesProgress() } returns BaseResponse(200, "success", mockConsumables)

        // When
        val result = waterRepository.getConsumablesProgress().first()

        // Then
        assertTrue(result is ApiResult.Success)
        assertEquals(3, (result as ApiResult.Success).data.filters.size)
        assertEquals(FilterStatus.WARNING, result.data.filters[2].status)
    }

    @Test
    fun `getConsumablesProgress with all good filters should return correct status`() = runTest {
        // Given
        val mockConsumables = ConsumablesProgress(
            filters = listOf(
                FilterInfo(
                    id = "filter1",
                    name = "PP棉滤芯",
                    type = "PP",
                    lifePercent = 95,
                    lifeDays = 180,
                    totalDays = 180,
                    status = FilterStatus.GOOD
                ),
                FilterInfo(
                    id = "filter2",
                    name = "活性炭滤芯",
                    type = "CTO",
                    lifePercent = 90,
                    lifeDays = 180,
                    totalDays = 180,
                    status = FilterStatus.GOOD
                )
            )
        )
        coEvery { apiService.getConsumablesProgress() } returns BaseResponse(200, "success", mockConsumables)

        // When
        val result = waterRepository.getConsumablesProgress().first()

        // Then
        assertTrue(result is ApiResult.Success)
        assertTrue((result as ApiResult.Success).data.filters.all { it.status == FilterStatus.GOOD })
    }

    @Test
    fun `getConsumablesProgress with expired filter should return expired status`() = runTest {
        // Given
        val mockConsumables = ConsumablesProgress(
            filters = listOf(
                FilterInfo(
                    id = "filter1",
                    name = "PP棉滤芯",
                    type = "PP",
                    lifePercent = 0,
                    lifeDays = 0,
                    totalDays = 180,
                    status = FilterStatus.EXPIRED
                )
            )
        )
        coEvery { apiService.getConsumablesProgress() } returns BaseResponse(200, "success", mockConsumables)

        // When
        val result = waterRepository.getConsumablesProgress().first()

        // Then
        assertTrue(result is ApiResult.Success)
        assertEquals(FilterStatus.EXPIRED, (result as ApiResult.Success).data.filters[0].status)
        assertEquals(0, result.data.filters[0].lifePercent)
    }

    @Test
    fun `getConsumablesProgress with empty list should return empty`() = runTest {
        // Given
        val mockConsumables = ConsumablesProgress(filters = emptyList())
        coEvery { apiService.getConsumablesProgress() } returns BaseResponse(200, "success", mockConsumables)

        // When
        val result = waterRepository.getConsumablesProgress().first()

        // Then
        assertTrue(result is ApiResult.Success)
        assertTrue((result as ApiResult.Success).data.filters.isEmpty())
    }

    // ==================== 耗材重置测试 ====================

    @Test
    fun `resetConsumable should reset filter life`() = runTest {
        // Given
        coEvery { apiService.resetConsumable(any()) } returns BaseResponse(200, "success", Unit)

        // When
        val result = waterRepository.resetConsumable("filter1").first()

        // Then
        assertTrue(result is ApiResult.Success)
    }

    @Test
    fun `resetConsumable with invalid filter should return error`() = runTest {
        // Given
        coEvery { apiService.resetConsumable(any()) } returns BaseResponse(404, "Filter not found", null)

        // When
        val result = waterRepository.resetConsumable("invalid_filter").first()

        // Then
        assertTrue(result is ApiResult.Error)
    }

    @Test
    fun `resetConsumable when not authorized should return error`() = runTest {
        // Given
        coEvery { apiService.resetConsumable(any()) } returns BaseResponse(403, "Not authorized", null)

        // When
        val result = waterRepository.resetConsumable("filter1").first()

        // Then
        assertTrue(result is ApiResult.Error)
    }

    // ==================== 错误处理测试 ====================

    @Test
    fun `getWaterSystemStatus when system error should return error`() = runTest {
        // Given
        coEvery { apiService.getWaterSystemStatus(any()) } returns BaseResponse(500, "System error", null)

        // When
        val result = waterRepository.getWaterSystemStatus(1).first()

        // Then
        assertTrue(result is ApiResult.Error)
    }

    @Test
    fun `setWaterTemperature when system offline should return error`() = runTest {
        // Given
        coEvery { apiService.setWaterTemperature(any()) } returns BaseResponse(503, "System offline", null)

        // When
        val result = waterRepository.setWaterTemperature(1, "45").first()

        // Then
        assertTrue(result is ApiResult.Error)
    }

    @Test
    fun `getConsumablesProgress when network error should return error`() = runTest {
        // Given
        coEvery { apiService.getConsumablesProgress() } returns BaseResponse(0, "Network error", null)

        // When
        val result = waterRepository.getConsumablesProgress().first()

        // Then
        assertTrue(result is ApiResult.Error)
    }

    @Test
    fun `resetConsumable when system busy should return error`() = runTest {
        // Given
        coEvery { apiService.resetConsumable(any()) } returns BaseResponse(423, "System is busy", null)

        // When
        val result = waterRepository.resetConsumable("filter1").first()

        // Then
        assertTrue(result is ApiResult.Error)
    }
}
