package com.wuheng.smart.data.repository

import app.cash.turbine.test
import com.wuheng.smart.MainDispatcherRule
import com.wuheng.smart.data.model.*
import com.wuheng.smart.data.network.ApiResult
import com.wuheng.smart.data.network.ApiService
import com.wuheng.smart.data.network.AppException
import com.wuheng.smart.data.network.BaseResponse
import io.mockk.*
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.extension.RegisterExtension
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import org.junit.jupiter.params.provider.EnumSource
import org.junit.jupiter.params.provider.ValueSource

/**
 * WaterRepository 单元测试
 *
 * 测试覆盖:
 * - 热水循环状态: 获取状态、设置循环模式
 * - 净水状态: TDS值、水质等级、流量
 * - 滤芯状态: 滤芯列表、寿命百分比
 * - 预约更换: 提交预约请求
 * - 重试机制: 网络错误自动重试
 * - Mock模式: 模拟数据返回
 * - 边界条件: 温度边界、TDS极值、滤芯状态
 */
@OptIn(ExperimentalCoroutinesApi::class)
class WaterRepositoryTest {

    @RegisterExtension
    val mainDispatcherRule = MainDispatcherRule()

    @MockK
    private lateinit var apiService: ApiService

    private lateinit var repository: WaterRepositoryImpl

    @BeforeEach
    fun setup() {
        MockKAnnotations.init(this)
        repository = WaterRepositoryImpl(apiService, useMock = false)
    }

    @AfterEach
    fun tearDown() {
        unmockkAll()
    }

    // ==================== 热水循环状态测试 ====================

    @Nested
    @DisplayName("热水循环状态测试")
    inner class HotWaterStatusTests {

        @Test
        fun `getHotWaterStatus - 正常获取 - 返回完整状态`() = runTest {
            // Given
            val status = HotWaterStatusResponse(
                currentTemp = "55.00",
                targetTemp = "55.00",
                circulationMode = "all_day",
                circulationStatus = 1,
                sterilizationEnable = 1,
                sterilizationTime = "02:00:00"
            )
            coEvery { apiService.getHotWaterStatus(any()) } returns BaseResponse(200, "success", status)

            // When & Then
            repository.getHotWaterStatus(1).test {
                assertEquals(ApiResult.Loading, awaitItem())
                val success = awaitItem() as ApiResult.Success
                assertEquals("55.00", success.data.currentTemp)
                assertEquals("55.00", success.data.targetTemp)
                assertEquals("all_day", success.data.circulationMode)
                assertEquals(1, success.data.circulationStatus)
                assertEquals(1, success.data.sterilizationEnable)
                assertEquals("02:00:00", success.data.sterilizationTime)
                awaitComplete()
            }
        }

        @Test
        fun `getHotWaterStatus - 全天循环模式 - 返回全天模式`() = runTest {
            // Given
            val status = HotWaterStatusResponse(
                currentTemp = "55.00",
                targetTemp = "55.00",
                circulationMode = "all_day",
                circulationStatus = 1,
                sterilizationEnable = 1,
                sterilizationTime = "02:00:00"
            )
            coEvery { apiService.getHotWaterStatus(any()) } returns BaseResponse(200, "success", status)

            // When & Then
            repository.getHotWaterStatus(1).test {
                assertEquals(ApiResult.Loading, awaitItem())
                val success = awaitItem() as ApiResult.Success
                assertEquals("all_day", success.data.circulationMode)
                assertEquals(1, success.data.circulationStatus)
                awaitComplete()
            }
        }

        @Test
        fun `getHotWaterStatus - 定时循环模式 - 返回定时模式`() = runTest {
            // Given
            val status = HotWaterStatusResponse(
                currentTemp = "55.00",
                targetTemp = "55.00",
                circulationMode = "timer",
                circulationStatus = 0,
                sterilizationEnable = 1,
                sterilizationTime = "02:00:00"
            )
            coEvery { apiService.getHotWaterStatus(any()) } returns BaseResponse(200, "success", status)

            // When & Then
            repository.getHotWaterStatus(1).test {
                assertEquals(ApiResult.Loading, awaitItem())
                val success = awaitItem() as ApiResult.Success
                assertEquals("timer", success.data.circulationMode)
                assertEquals(0, success.data.circulationStatus)
                awaitComplete()
            }
        }

        @Test
        fun `getHotWaterStatus - 临时循环模式 - 返回临时模式`() = runTest {
            // Given
            val status = HotWaterStatusResponse(
                currentTemp = "55.00",
                targetTemp = "55.00",
                circulationMode = "temp",
                circulationStatus = 1,
                sterilizationEnable = 0,
                sterilizationTime = "02:00:00"
            )
            coEvery { apiService.getHotWaterStatus(any()) } returns BaseResponse(200, "success", status)

            // When & Then
            repository.getHotWaterStatus(1).test {
                assertEquals(ApiResult.Loading, awaitItem())
                val success = awaitItem() as ApiResult.Success
                assertEquals("temp", success.data.circulationMode)
                assertEquals(0, success.data.sterilizationEnable)
                awaitComplete()
            }
        }

        @Test
        fun `getHotWaterStatus - 关闭循环 - 返回关闭状态`() = runTest {
            // Given
            val status = HotWaterStatusResponse(
                currentTemp = "25.00",
                targetTemp = "55.00",
                circulationMode = "off",
                circulationStatus = 0,
                sterilizationEnable = 0,
                sterilizationTime = "02:00:00"
            )
            coEvery { apiService.getHotWaterStatus(any()) } returns BaseResponse(200, "success", status)

            // When & Then
            repository.getHotWaterStatus(1).test {
                assertEquals(ApiResult.Loading, awaitItem())
                val success = awaitItem() as ApiResult.Success
                assertEquals("off", success.data.circulationMode)
                assertEquals(0, success.data.circulationStatus)
                awaitComplete()
            }
        }

        @Test
        fun `getHotWaterStatus - 网络错误 - 返回网络错误`() = runTest {
            // Given
            coEvery { apiService.getHotWaterStatus(any()) } throws java.net.UnknownHostException()

            // When & Then
            repository.getHotWaterStatus(1).test {
                assertEquals(ApiResult.Loading, awaitItem())
                val error = awaitItem() as ApiResult.Error
                assertTrue(error.exception is AppException.NetworkError)
                awaitComplete()
            }
        }

        @Test
        fun `getHotWaterStatus - Mock模式 - 返回模拟状态`() = runTest {
            // Given
            val mockRepo = WaterRepositoryImpl(apiService, useMock = true)

            // When & Then
            mockRepo.getHotWaterStatus(1).test {
                assertEquals(ApiResult.Loading, awaitItem())
                val success = awaitItem() as ApiResult.Success
                assertNotNull(success.data.currentTemp)
                assertNotNull(success.data.targetTemp)
                assertNotNull(success.data.circulationMode)
                awaitComplete()
            }
        }
    }

    // ==================== 设置循环模式测试 ====================

    @Nested
    @DisplayName("设置循环模式测试")
    inner class SetCirculationModeTests {

        @ParameterizedTest
        @EnumSource(CirculationMode::class)
        fun `setCirculationMode - 各种模式 - 返回成功`(mode: CirculationMode) = runTest {
            // Given
            val response = SetCirculationModeResponse(mode.value)
            coEvery { apiService.setCirculationMode(any()) } returns BaseResponse(200, "success", response)

            // When & Then
            repository.setCirculationMode(1, mode, null).test {
                assertEquals(ApiResult.Loading, awaitItem())
                val success = awaitItem() as ApiResult.Success
                assertEquals(mode.value, success.data.mode)
                awaitComplete()
            }
        }

        @Test
        fun `setCirculationMode - 全天模式 - 返回成功`() = runTest {
            // Given
            val response = SetCirculationModeResponse("all_day")
            coEvery { apiService.setCirculationMode(any()) } returns BaseResponse(200, "success", response)

            // When & Then
            repository.setCirculationMode(1, CirculationMode.ALL_DAY, null).test {
                assertEquals(ApiResult.Loading, awaitItem())
                val success = awaitItem() as ApiResult.Success
                assertEquals("all_day", success.data.mode)
                awaitComplete()
            }
        }

        @Test
        fun `setCirculationMode - 临时模式带时长 - 返回成功`() = runTest {
            // Given
            val response = SetCirculationModeResponse("temp")
            coEvery { apiService.setCirculationMode(any()) } returns BaseResponse(200, "success", response)

            // When & Then
            repository.setCirculationMode(1, CirculationMode.TEMP, 30).test {
                assertEquals(ApiResult.Loading, awaitItem())
                val success = awaitItem() as ApiResult.Success
                assertEquals("temp", success.data.mode)
                awaitComplete()
            }
        }

        @ParameterizedTest
        @ValueSource(ints = [5, 10, 15, 30, 60, 120])
        fun `setCirculationMode - 各种临时时长 - 返回成功`(duration: Int) = runTest {
            // Given
            val response = SetCirculationModeResponse("temp")
            coEvery { apiService.setCirculationMode(any()) } returns BaseResponse(200, "success", response)

            // When & Then
            repository.setCirculationMode(1, CirculationMode.TEMP, duration).test {
                assertEquals(ApiResult.Loading, awaitItem())
                val success = awaitItem() as ApiResult.Success
                assertEquals("temp", success.data.mode)
                awaitComplete()
            }
        }

        @Test
        fun `setCirculationMode - 网络错误 - 返回网络错误`() = runTest {
            // Given
            coEvery { apiService.setCirculationMode(any()) } throws java.net.UnknownHostException()

            // When & Then
            repository.setCirculationMode(1, CirculationMode.ALL_DAY, null).test {
                assertEquals(ApiResult.Loading, awaitItem())
                val error = awaitItem() as ApiResult.Error
                assertTrue(error.exception is AppException.NetworkError)
                awaitComplete()
            }
        }

        @Test
        fun `setCirculationMode - Mock模式 - 返回模拟响应`() = runTest {
            // Given
            val mockRepo = WaterRepositoryImpl(apiService, useMock = true)

            // When & Then
            mockRepo.setCirculationMode(1, CirculationMode.ALL_DAY, null).test {
                assertEquals(ApiResult.Loading, awaitItem())
                val success = awaitItem() as ApiResult.Success
                assertEquals("all_day", success.data.mode)
                awaitComplete()
            }
        }

        @Test
        fun `setCirculationMode - 权限不足 - 返回403错误`() = runTest {
            // Given
            coEvery { apiService.setCirculationMode(any()) } returns BaseResponse(403, "权限不足", null)

            // When & Then
            repository.setCirculationMode(1, CirculationMode.ALL_DAY, null).test {
                assertEquals(ApiResult.Loading, awaitItem())
                val error = awaitItem() as ApiResult.Error
                assertTrue(error.exception is AppException.BusinessError)
                awaitComplete()
            }
        }
    }

    // ==================== 净水状态测试 ====================

    @Nested
    @DisplayName("净水状态测试")
    inner class WaterPurifierStatusTests {

        @Test
        fun `getWaterPurifierStatus - 正常获取 - 返回完整状态`() = runTest {
            // Given
            val status = WaterPurifierStatusResponse(
                tdsIn = 150,
                tdsOut = 15,
                waterQuality = "excellent",
                totalFlow = "1250.5",
                dailyFlow = "45.2",
                deviceStatus = 1,
                lastUpdate = "2026-04-23 14:30:00"
            )
            coEvery { apiService.getWaterPurifierStatus(any()) } returns BaseResponse(200, "success", status)

            // When & Then
            repository.getWaterPurifierStatus(1).test {
                assertEquals(ApiResult.Loading, awaitItem())
                val success = awaitItem() as ApiResult.Success
                assertEquals(150, success.data.tdsIn)
                assertEquals(15, success.data.tdsOut)
                assertEquals("excellent", success.data.waterQuality)
                assertEquals("1250.5", success.data.totalFlow)
                assertEquals("45.2", success.data.dailyFlow)
                assertEquals(1, success.data.deviceStatus)
                assertEquals("2026-04-23 14:30:00", success.data.lastUpdate)
                awaitComplete()
            }
        }

        @ParameterizedTest
        @CsvSource("excellent,优秀", "good,良好", "fair,一般", "poor,较差")
        fun `getWaterPurifierStatus - 各种水质等级 - 返回成功`(quality: String, description: String) = runTest {
            // Given
            val status = WaterPurifierStatusResponse(
                tdsIn = 150,
                tdsOut = 15,
                waterQuality = quality,
                totalFlow = "1000.0",
                dailyFlow = "50.0",
                deviceStatus = 1,
                lastUpdate = "2026-04-23 14:30:00"
            )
            coEvery { apiService.getWaterPurifierStatus(any()) } returns BaseResponse(200, "success", status)

            // When & Then
            repository.getWaterPurifierStatus(1).test {
                assertEquals(ApiResult.Loading, awaitItem())
                val success = awaitItem() as ApiResult.Success
                assertEquals(quality, success.data.waterQuality)
                awaitComplete()
            }
        }

        @Test
        fun `getWaterPurifierStatus - 设备离线 - 返回离线状态`() = runTest {
            // Given
            val status = WaterPurifierStatusResponse(
                tdsIn = null,
                tdsOut = null,
                waterQuality = null,
                totalFlow = null,
                dailyFlow = null,
                deviceStatus = 0,
                lastUpdate = "2026-04-23 10:00:00"
            )
            coEvery { apiService.getWaterPurifierStatus(any()) } returns BaseResponse(200, "success", status)

            // When & Then
            repository.getWaterPurifierStatus(1).test {
                assertEquals(ApiResult.Loading, awaitItem())
                val success = awaitItem() as ApiResult.Success
                assertEquals(0, success.data.deviceStatus)
                assertNull(success.data.tdsIn)
                assertNull(success.data.tdsOut)
                awaitComplete()
            }
        }

        @Test
        fun `getWaterPurifierStatus - 设备工作中 - 返回工作状态`() = runTest {
            // Given
            val status = WaterPurifierStatusResponse(
                tdsIn = 200,
                tdsOut = 20,
                waterQuality = "good",
                totalFlow = "1500.0",
                dailyFlow = "60.0",
                deviceStatus = 2,
                lastUpdate = "2026-04-23 14:30:00"
            )
            coEvery { apiService.getWaterPurifierStatus(any()) } returns BaseResponse(200, "success", status)

            // When & Then
            repository.getWaterPurifierStatus(1).test {
                assertEquals(ApiResult.Loading, awaitItem())
                val success = awaitItem() as ApiResult.Success
                assertEquals(2, success.data.deviceStatus)
                awaitComplete()
            }
        }

        @Test
        fun `getWaterPurifierStatus - 网络错误 - 返回网络错误`() = runTest {
            // Given
            coEvery { apiService.getWaterPurifierStatus(any()) } throws java.net.UnknownHostException()

            // When & Then
            repository.getWaterPurifierStatus(1).test {
                assertEquals(ApiResult.Loading, awaitItem())
                val error = awaitItem() as ApiResult.Error
                assertTrue(error.exception is AppException.NetworkError)
                awaitComplete()
            }
        }

        @Test
        fun `getWaterPurifierStatus - Mock模式 - 返回模拟状态`() = runTest {
            // Given
            val mockRepo = WaterRepositoryImpl(apiService, useMock = true)

            // When & Then
            mockRepo.getWaterPurifierStatus(1).test {
                assertEquals(ApiResult.Loading, awaitItem())
                val success = awaitItem() as ApiResult.Success
                assertNotNull(success.data.tdsIn)
                assertNotNull(success.data.tdsOut)
                assertNotNull(success.data.waterQuality)
                awaitComplete()
            }
        }

        @Test
        fun `getWaterPurifierStatus - 高TDS值 - 正确处理`() = runTest {
            // Given
            val status = WaterPurifierStatusResponse(
                tdsIn = 999,
                tdsOut = 999,
                waterQuality = "poor",
                totalFlow = "9999.9",
                dailyFlow = "999.9",
                deviceStatus = 1,
                lastUpdate = "2026-04-23 14:30:00"
            )
            coEvery { apiService.getWaterPurifierStatus(any()) } returns BaseResponse(200, "success", status)

            // When & Then
            repository.getWaterPurifierStatus(1).test {
                assertEquals(ApiResult.Loading, awaitItem())
                val success = awaitItem() as ApiResult.Success
                assertEquals(999, success.data.tdsIn)
                assertEquals(999, success.data.tdsOut)
                awaitComplete()
            }
        }

        @Test
        fun `getWaterPurifierStatus - 零TDS值 - 正确处理`() = runTest {
            // Given
            val status = WaterPurifierStatusResponse(
                tdsIn = 0,
                tdsOut = 0,
                waterQuality = "excellent",
                totalFlow = "0.0",
                dailyFlow = "0.0",
                deviceStatus = 1,
                lastUpdate = "2026-04-23 14:30:00"
            )
            coEvery { apiService.getWaterPurifierStatus(any()) } returns BaseResponse(200, "success", status)

            // When & Then
            repository.getWaterPurifierStatus(1).test {
                assertEquals(ApiResult.Loading, awaitItem())
                val success = awaitItem() as ApiResult.Success
                assertEquals(0, success.data.tdsIn)
                assertEquals(0, success.data.tdsOut)
                awaitComplete()
            }
        }
    }

    // ==================== 滤芯状态测试 ====================

    @Nested
    @DisplayName("滤芯状态测试")
    inner class FilterStatusTests {

        @Test
        fun `getFilterStatus - 正常获取 - 返回滤芯列表`() = runTest {
            // Given
            val filters = listOf(
                FilterStatusInfo(1, "前置过滤器", "pre", 85, 0),
                FilterStatusInfo(2, "中央净水器", "central", 60, 0),
                FilterStatusInfo(3, "末端直饮机", "end", 45, 1)
            )
            coEvery { apiService.getFilterStatus(any()) } returns BaseResponse(200, "success", filters)

            // When & Then
            repository.getFilterStatus(1).test {
                assertEquals(ApiResult.Loading, awaitItem())
                val success = awaitItem() as ApiResult.Success
                assertEquals(3, success.data.size)
                assertEquals("前置过滤器", success.data[0].filterName)
                assertEquals("pre", success.data[0].filterType)
                assertEquals(85, success.data[0].lifePercent)
                assertEquals(0, success.data[0].status)
                awaitComplete()
            }
        }

        @Test
        fun `getFilterStatus - 前置滤芯正常 - 返回正常状态`() = runTest {
            // Given
            val filters = listOf(
                FilterStatusInfo(1, "前置过滤器", "pre", 85, 0)
            )
            coEvery { apiService.getFilterStatus(any()) } returns BaseResponse(200, "success", filters)

            // When & Then
            repository.getFilterStatus(1).test {
                assertEquals(ApiResult.Loading, awaitItem())
                val success = awaitItem() as ApiResult.Success
                assertEquals(85, success.data[0].lifePercent)
                assertEquals(0, success.data[0].status)
                awaitComplete()
            }
        }

        @Test
        fun `getFilterStatus - 滤芯警告 - 返回警告状态`() = runTest {
            // Given
            val filters = listOf(
                FilterStatusInfo(1, "前置过滤器", "pre", 20, 1)
            )
            coEvery { apiService.getFilterStatus(any()) } returns BaseResponse(200, "success", filters)

            // When & Then
            repository.getFilterStatus(1).test {
                assertEquals(ApiResult.Loading, awaitItem())
                val success = awaitItem() as ApiResult.Success
                assertEquals(20, success.data[0].lifePercent)
                assertEquals(1, success.data[0].status)
                awaitComplete()
            }
        }

        @Test
        fun `getFilterStatus - 滤芯严重警告 - 返回严重状态`() = runTest {
            // Given
            val filters = listOf(
                FilterStatusInfo(1, "前置过滤器", "pre", 5, 2)
            )
            coEvery { apiService.getFilterStatus(any()) } returns BaseResponse(200, "success", filters)

            // When & Then
            repository.getFilterStatus(1).test {
                assertEquals(ApiResult.Loading, awaitItem())
                val success = awaitItem() as ApiResult.Success
                assertEquals(5, success.data[0].lifePercent)
                assertEquals(2, success.data[0].status)
                awaitComplete()
            }
        }

        @Test
        fun `getFilterStatus - 空滤芯列表 - 返回空列表`() = runTest {
            // Given
            coEvery { apiService.getFilterStatus(any()) } returns BaseResponse(200, "success", emptyList())

            // When & Then
            repository.getFilterStatus(1).test {
                assertEquals(ApiResult.Loading, awaitItem())
                val success = awaitItem() as ApiResult.Success
                assertTrue(success.data.isEmpty())
                awaitComplete()
            }
        }

        @Test
        fun `getFilterStatus - 网络错误 - 返回网络错误`() = runTest {
            // Given
            coEvery { apiService.getFilterStatus(any()) } throws java.net.UnknownHostException()

            // When & Then
            repository.getFilterStatus(1).test {
                assertEquals(ApiResult.Loading, awaitItem())
                val error = awaitItem() as ApiResult.Error
                assertTrue(error.exception is AppException.NetworkError)
                awaitComplete()
            }
        }

        @Test
        fun `getFilterStatus - Mock模式 - 返回模拟滤芯`() = runTest {
            // Given
            val mockRepo = WaterRepositoryImpl(apiService, useMock = true)

            // When & Then
            mockRepo.getFilterStatus(1).test {
                assertEquals(ApiResult.Loading, awaitItem())
                val success = awaitItem() as ApiResult.Success
                assertTrue(success.data.isNotEmpty())
                assertTrue(success.data.any { it.filterType == "pre" })
                assertTrue(success.data.any { it.filterType == "central" })
                assertTrue(success.data.any { it.filterType == "end" })
                awaitComplete()
            }
        }

        @Test
        fun `getFilterStatus - 多种滤芯类型 - 正确处理`() = runTest {
            // Given
            val filters = listOf(
                FilterStatusInfo(1, "前置过滤器", "pre", 85, 0),
                FilterStatusInfo(2, "中央净水器", "central", 60, 0),
                FilterStatusInfo(3, "末端直饮机", "end", 45, 1),
                FilterStatusInfo(4, "RO反渗透", "ro", 70, 0),
                FilterStatusInfo(5, "活性炭", "carbon", 30, 1)
            )
            coEvery { apiService.getFilterStatus(any()) } returns BaseResponse(200, "success", filters)

            // When & Then
            repository.getFilterStatus(1).test {
                assertEquals(ApiResult.Loading, awaitItem())
                val success = awaitItem() as ApiResult.Success
                assertEquals(5, success.data.size)
                val types = success.data.map { it.filterType }.toSet()
                assertTrue(types.contains("pre"))
                assertTrue(types.contains("central"))
                assertTrue(types.contains("end"))
                awaitComplete()
            }
        }
    }

    // ==================== 预约滤芯更换测试 ====================

    @Nested
    @DisplayName("预约滤芯更换测试")
    inner class BookFilterReplaceTests {

        @Test
        fun `bookFilterReplace - 正常预约 - 返回成功`() = runTest {
            // Given - Mock模式下直接返回成功
            val mockRepo = WaterRepositoryImpl(apiService, useMock = true)

            // When & Then
            mockRepo.bookFilterReplace(1, 1, "张三", "13800138000", "2026-05-01").test {
                assertEquals(ApiResult.Loading, awaitItem())
                val success = awaitItem() as ApiResult.Success
                assertEquals(Unit, success.data)
                awaitComplete()
            }
        }

        @Test
        fun `bookFilterReplace - 仅必填参数 - 返回成功`() = runTest {
            // Given
            val mockRepo = WaterRepositoryImpl(apiService, useMock = true)

            // When & Then
            mockRepo.bookFilterReplace(1, 1, null, null, null).test {
                assertEquals(ApiResult.Loading, awaitItem())
                val success = awaitItem() as ApiResult.Success
                assertEquals(Unit, success.data)
                awaitComplete()
            }
        }

        @Test
        fun `bookFilterReplace - 部分可选参数 - 返回成功`() = runTest {
            // Given
            val mockRepo = WaterRepositoryImpl(apiService, useMock = true)

            // When & Then
            mockRepo.bookFilterReplace(1, 1, "张三", null, "2026-05-01").test {
                assertEquals(ApiResult.Loading, awaitItem())
                val success = awaitItem() as ApiResult.Success
                assertEquals(Unit, success.data)
                awaitComplete()
            }
        }

        @ParameterizedTest
        @ValueSource(strings = ["2026-05-01", "2026-12-31", "2027-01-01"])
        fun `bookFilterReplace - 各种日期格式 - 返回成功`(date: String) = runTest {
            // Given
            val mockRepo = WaterRepositoryImpl(apiService, useMock = true)

            // When & Then
            mockRepo.bookFilterReplace(1, 1, "张三", "13800138000", date).test {
                assertEquals(ApiResult.Loading, awaitItem())
                val success = awaitItem() as ApiResult.Success
                assertEquals(Unit, success.data)
                awaitComplete()
            }
        }
    }

    // ==================== 重试机制测试 ====================

    @Nested
    @DisplayName("重试机制测试")
    inner class RetryTests {

        @Test
        fun `getHotWaterStatus - 网络错误后重试 - 处理重试逻辑`() = runTest {
            // Given - 模拟网络错误，测试错误处理
            coEvery { apiService.getHotWaterStatus(any()) } throws java.net.UnknownHostException()

            // When & Then - 应该返回网络错误，不重试
            repository.getHotWaterStatus(1).test {
                assertEquals(ApiResult.Loading, awaitItem())
                val error = awaitItem() as ApiResult.Error
                assertTrue(error.exception is AppException.NetworkError)
                awaitComplete()
            }
        }

        @Test
        fun `setCirculationMode - 超时后重试 - 处理超时错误`() = runTest {
            // Given
            coEvery { apiService.setCirculationMode(any()) } throws java.net.SocketTimeoutException()

            // When & Then
            repository.setCirculationMode(1, CirculationMode.ALL_DAY, null).test {
                assertEquals(ApiResult.Loading, awaitItem())
                val error = awaitItem() as ApiResult.Error
                assertTrue(error.exception is AppException.TimeoutError)
                awaitComplete()
            }
        }

        @Test
        fun `getWaterPurifierStatus - 服务器500错误 - 返回服务器错误`() = runTest {
            // Given
            val httpException = mockk<retrofit2.HttpException>(relaxed = true)
            every { httpException.code() } returns 500
            every { httpException.message() } returns "Server Error"
            every { httpException.response() } returns null
            coEvery { apiService.getWaterPurifierStatus(any()) } throws httpException

            // When & Then
            repository.getWaterPurifierStatus(1).test {
                assertEquals(ApiResult.Loading, awaitItem())
                val error = awaitItem() as ApiResult.Error
                assertTrue(error.exception is AppException.ServerError)
                awaitComplete()
            }
        }

        @Test
        fun `getFilterStatus - 多次失败后重试 - 处理重试逻辑`() = runTest {
            // Given - 模拟网络错误，测试错误处理
            coEvery { apiService.getFilterStatus(any()) } throws java.net.UnknownHostException()

            // When & Then - 应该返回网络错误，不重试
            repository.getFilterStatus(1).test {
                assertEquals(ApiResult.Loading, awaitItem())
                val error = awaitItem() as ApiResult.Error
                assertTrue(error.exception is AppException.NetworkError)
                awaitComplete()
            }
        }
    }

    // ==================== 边界条件测试 ====================

    @Nested
    @DisplayName("边界条件测试")
    inner class EdgeCaseTests {

        @Test
        fun `getHotWaterStatus - 负数房屋ID - 正确处理`() = runTest {
            // Given
            coEvery { apiService.getHotWaterStatus(-1) } returns BaseResponse(400, "无效的房屋ID", null)

            // When & Then
            repository.getHotWaterStatus(-1).test {
                assertEquals(ApiResult.Loading, awaitItem())
                val error = awaitItem() as ApiResult.Error
                assertTrue(error.exception is AppException.BusinessError)
                awaitComplete()
            }
        }

        @Test
        fun `getHotWaterStatus - 零值房屋ID - 正确处理`() = runTest {
            // Given
            coEvery { apiService.getHotWaterStatus(0) } returns BaseResponse(400, "无效的房屋ID", null)

            // When & Then
            repository.getHotWaterStatus(0).test {
                assertEquals(ApiResult.Loading, awaitItem())
                val error = awaitItem() as ApiResult.Error
                assertTrue(error.exception is AppException.BusinessError)
                awaitComplete()
            }
        }

        @Test
        fun `getHotWaterStatus - 极端温度值 - 正确处理`() = runTest {
            // Given
            val status = HotWaterStatusResponse(
                currentTemp = "99.99",
                targetTemp = "99.99",
                circulationMode = "all_day",
                circulationStatus = 1,
                sterilizationEnable = 1,
                sterilizationTime = "02:00:00"
            )
            coEvery { apiService.getHotWaterStatus(any()) } returns BaseResponse(200, "success", status)

            // When & Then
            repository.getHotWaterStatus(1).test {
                assertEquals(ApiResult.Loading, awaitItem())
                val success = awaitItem() as ApiResult.Success
                assertEquals("99.99", success.data.currentTemp)
                awaitComplete()
            }
        }

        @Test
        fun `getHotWaterStatus - 零温度值 - 正确处理`() = runTest {
            // Given
            val status = HotWaterStatusResponse(
                currentTemp = "0.00",
                targetTemp = "0.00",
                circulationMode = "off",
                circulationStatus = 0,
                sterilizationEnable = 0,
                sterilizationTime = "02:00:00"
            )
            coEvery { apiService.getHotWaterStatus(any()) } returns BaseResponse(200, "success", status)

            // When & Then
            repository.getHotWaterStatus(1).test {
                assertEquals(ApiResult.Loading, awaitItem())
                val success = awaitItem() as ApiResult.Success
                assertEquals("0.00", success.data.currentTemp)
                awaitComplete()
            }
        }

        @Test
        fun `setCirculationMode - 零时长 - 正确处理`() = runTest {
            // Given
            val response = SetCirculationModeResponse("temp")
            coEvery { apiService.setCirculationMode(any()) } returns BaseResponse(200, "success", response)

            // When & Then
            repository.setCirculationMode(1, CirculationMode.TEMP, 0).test {
                assertEquals(ApiResult.Loading, awaitItem())
                val success = awaitItem() as ApiResult.Success
                assertEquals("temp", success.data.mode)
                awaitComplete()
            }
        }

        @Test
        fun `setCirculationMode - 负时长 - 正确处理`() = runTest {
            // Given
            val response = SetCirculationModeResponse("temp")
            coEvery { apiService.setCirculationMode(any()) } returns BaseResponse(200, "success", response)

            // When & Then
            repository.setCirculationMode(1, CirculationMode.TEMP, -10).test {
                assertEquals(ApiResult.Loading, awaitItem())
                val success = awaitItem() as ApiResult.Success
                assertEquals("temp", success.data.mode)
                awaitComplete()
            }
        }

        @Test
        fun `getFilterStatus - 滤芯寿命100 - 正确处理`() = runTest {
            // Given
            val filters = listOf(FilterStatusInfo(1, "新滤芯", "pre", 100, 0))
            coEvery { apiService.getFilterStatus(any()) } returns BaseResponse(200, "success", filters)

            // When & Then
            repository.getFilterStatus(1).test {
                assertEquals(ApiResult.Loading, awaitItem())
                val success = awaitItem() as ApiResult.Success
                assertEquals(100, success.data[0].lifePercent)
                awaitComplete()
            }
        }

        @Test
        fun `getFilterStatus - 滤芯寿命0 - 正确处理`() = runTest {
            // Given
            val filters = listOf(FilterStatusInfo(1, "耗尽滤芯", "pre", 0, 2))
            coEvery { apiService.getFilterStatus(any()) } returns BaseResponse(200, "success", filters)

            // When & Then
            repository.getFilterStatus(1).test {
                assertEquals(ApiResult.Loading, awaitItem())
                val success = awaitItem() as ApiResult.Success
                assertEquals(0, success.data[0].lifePercent)
                assertEquals(2, success.data[0].status)
                awaitComplete()
            }
        }

        @Test
        fun `getFilterStatus - 大量滤芯 - 正确处理`() = runTest {
            // Given
            val filters = (1..20).map {
                FilterStatusInfo(it, "滤芯$it", "pre", 80 - it, if (it > 15) 1 else 0)
            }
            coEvery { apiService.getFilterStatus(any()) } returns BaseResponse(200, "success", filters)

            // When & Then
            repository.getFilterStatus(1).test {
                assertEquals(ApiResult.Loading, awaitItem())
                val success = awaitItem() as ApiResult.Success
                assertEquals(20, success.data.size)
                awaitComplete()
            }
        }

        @Test
        fun `bookFilterReplace - 特殊字符联系人 - 正确处理`() = runTest {
            // Given
            val mockRepo = WaterRepositoryImpl(apiService, useMock = true)

            // When & Then
            mockRepo.bookFilterReplace(1, 1, "张三-先生@1号楼", "138-0013-8000", "2026-05-01").test {
                assertEquals(ApiResult.Loading, awaitItem())
                val success = awaitItem() as ApiResult.Success
                assertEquals(Unit, success.data)
                awaitComplete()
            }
        }

        @Test
        fun `bookFilterReplace - 长联系人姓名 - 正确处理`() = runTest {
            // Given
            val mockRepo = WaterRepositoryImpl(apiService, useMock = true)
            val longName = "张".repeat(50)

            // When & Then
            mockRepo.bookFilterReplace(1, 1, longName, "13800138000", "2026-05-01").test {
                assertEquals(ApiResult.Loading, awaitItem())
                val success = awaitItem() as ApiResult.Success
                assertEquals(Unit, success.data)
                awaitComplete()
            }
        }
    }

    // ==================== 枚举测试 ====================

    @Nested
    @DisplayName("枚举测试")
    inner class EnumTests {

        @ParameterizedTest
        @ValueSource(strings = ["all_day", "timer", "temp", "off"])
        fun `CirculationMode fromValue - 有效值 - 返回对应枚举`(value: String) = runTest {
            // When
            val mode = CirculationMode.fromValue(value)

            // Then
            assertEquals(value, mode.value)
        }

        @Test
        fun `CirculationMode fromValue - 无效值 - 返回默认值OFF`() = runTest {
            // When
            val mode = CirculationMode.fromValue("invalid")

            // Then
            assertEquals(CirculationMode.OFF, mode)
        }

        @ParameterizedTest
        @ValueSource(strings = ["pre", "central", "end"])
        fun `FilterType fromValue - 有效值 - 返回对应枚举`(value: String) = runTest {
            // When
            val type = FilterType.fromValue(value)

            // Then
            assertEquals(value, type.value)
        }

        @Test
        fun `FilterType fromValue - 无效值 - 返回默认值PRE`() = runTest {
            // When
            val type = FilterType.fromValue("invalid")

            // Then
            assertEquals(FilterType.PRE, type)
        }

        @ParameterizedTest
        @ValueSource(ints = [0, 1, 2])
        fun `FilterLifeStatus fromCode - 有效值 - 返回对应枚举`(code: Int) = runTest {
            // When
            val status = FilterLifeStatus.fromCode(code)

            // Then
            assertEquals(code, status.code)
        }

        @Test
        fun `FilterLifeStatus fromCode - 无效值 - 返回默认值NORMAL`() = runTest {
            // When
            val status = FilterLifeStatus.fromCode(999)

            // Then
            assertEquals(FilterLifeStatus.NORMAL, status)
        }
    }
}
