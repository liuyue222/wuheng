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
 * ClimateRepository 单元测试
 *
 * 测试覆盖:
 * - 获取系统状态: 温度、湿度、模式等
 * - 设置系统模式: cooling/heating/ventilation/auto
 * - 设置全局温度: 16-30度范围
 * - 设置全局湿度: 30-70%范围
 * - 重试机制: 网络错误自动重试
 * - Mock模式: 模拟数据返回
 * - 边界条件: 温度边界、湿度边界、极值
 */
@OptIn(ExperimentalCoroutinesApi::class)
class ClimateRepositoryTest {

    @RegisterExtension
    val mainDispatcherRule = MainDispatcherRule()

    @MockK
    private lateinit var apiService: ApiService

    private lateinit var repository: ClimateRepositoryImpl

    @BeforeEach
    fun setup() {
        MockKAnnotations.init(this)
        repository = ClimateRepositoryImpl(apiService, useMock = false)
    }

    @AfterEach
    fun tearDown() {
        unmockkAll()
    }

    // ==================== 获取系统状态测试 ====================

    @Nested
    @DisplayName("获取系统状态测试")
    inner class GetSystemStatusTests {

        @Test
        fun `getSystemStatus - 正常获取 - 返回完整系统状态`() = runTest {
            // Given
            val systemStatus = SystemStatus(
                systemStatus = SystemStatusInfo(
                    systemMode = "cooling",
                    globalTempSet = "24.00",
                    globalHumiditySet = "45.00",
                    avgIndoorTemp = "23.50",
                    avgIndoorHumidity = "45.20",
                    avgCo2 = "420",
                    outdoorTemp = "28.00",
                    outdoorHumidity = "60",
                    outdoorAqi = "良",
                    outdoorPm25 = "35",
                    systemRunStatus = "running"
                ),
                houseInfo = null,
                deviceCount = 6,
                onlineCount = 5
            )
            coEvery { apiService.getSystemStatus(any()) } returns BaseResponse(200, "success", systemStatus)

            // When & Then
            repository.getSystemStatus(1).test {
                assertEquals(ApiResult.Loading, awaitItem())
                val success = awaitItem() as ApiResult.Success
                assertEquals("cooling", success.data.systemStatus.systemMode)
                assertEquals("24.00", success.data.systemStatus.globalTempSet)
                assertEquals("45.00", success.data.systemStatus.globalHumiditySet)
                assertEquals("23.50", success.data.systemStatus.avgIndoorTemp)
                assertEquals("45.20", success.data.systemStatus.avgIndoorHumidity)
                assertEquals(6, success.data.deviceCount)
                assertEquals(5, success.data.onlineCount)
                awaitComplete()
            }
        }

        @Test
        fun `getSystemStatus - 制冷模式 - 返回制冷状态`() = runTest {
            // Given
            val systemStatus = SystemStatus(
                systemStatus = SystemStatusInfo(
                    systemMode = "cooling",
                    globalTempSet = "22.00",
                    globalHumiditySet = "50.00",
                    avgIndoorTemp = "21.50",
                    avgIndoorHumidity = "49.00",
                    systemRunStatus = "running"
                ),
                houseInfo = null,
                deviceCount = 6,
                onlineCount = 6
            )
            coEvery { apiService.getSystemStatus(any()) } returns BaseResponse(200, "success", systemStatus)

            // When & Then
            repository.getSystemStatus(1).test {
                assertEquals(ApiResult.Loading, awaitItem())
                val success = awaitItem() as ApiResult.Success
                assertEquals("cooling", success.data.systemStatus.systemMode)
                awaitComplete()
            }
        }

        @Test
        fun `getSystemStatus - 制热模式 - 返回制热状态`() = runTest {
            // Given
            val systemStatus = SystemStatus(
                systemStatus = SystemStatusInfo(
                    systemMode = "heating",
                    globalTempSet = "26.00",
                    globalHumiditySet = "40.00",
                    avgIndoorTemp = "25.50",
                    avgIndoorHumidity = "39.00",
                    systemRunStatus = "running"
                ),
                houseInfo = null,
                deviceCount = 6,
                onlineCount = 6
            )
            coEvery { apiService.getSystemStatus(any()) } returns BaseResponse(200, "success", systemStatus)

            // When & Then
            repository.getSystemStatus(1).test {
                assertEquals(ApiResult.Loading, awaitItem())
                val success = awaitItem() as ApiResult.Success
                assertEquals("heating", success.data.systemStatus.systemMode)
                awaitComplete()
            }
        }

        @Test
        fun `getSystemStatus - 通风模式 - 返回通风状态`() = runTest {
            // Given
            val systemStatus = SystemStatus(
                systemStatus = SystemStatusInfo(
                    systemMode = "ventilation",
                    globalTempSet = "24.00",
                    globalHumiditySet = "45.00",
                    avgIndoorTemp = "24.00",
                    avgIndoorHumidity = "45.00",
                    systemRunStatus = "running"
                ),
                houseInfo = null,
                deviceCount = 6,
                onlineCount = 6
            )
            coEvery { apiService.getSystemStatus(any()) } returns BaseResponse(200, "success", systemStatus)

            // When & Then
            repository.getSystemStatus(1).test {
                assertEquals(ApiResult.Loading, awaitItem())
                val success = awaitItem() as ApiResult.Success
                assertEquals("ventilation", success.data.systemStatus.systemMode)
                awaitComplete()
            }
        }

        @Test
        fun `getSystemStatus - 自动模式 - 返回自动状态`() = runTest {
            // Given
            val systemStatus = SystemStatus(
                systemStatus = SystemStatusInfo(
                    systemMode = "auto",
                    globalTempSet = "24.00",
                    globalHumiditySet = "45.00",
                    avgIndoorTemp = "23.80",
                    avgIndoorHumidity = "45.50",
                    systemRunStatus = "running"
                ),
                houseInfo = null,
                deviceCount = 6,
                onlineCount = 6
            )
            coEvery { apiService.getSystemStatus(any()) } returns BaseResponse(200, "success", systemStatus)

            // When & Then
            repository.getSystemStatus(1).test {
                assertEquals(ApiResult.Loading, awaitItem())
                val success = awaitItem() as ApiResult.Success
                assertEquals("auto", success.data.systemStatus.systemMode)
                awaitComplete()
            }
        }

        @Test
        fun `getSystemStatus - 系统待机 - 返回待机状态`() = runTest {
            // Given
            val systemStatus = SystemStatus(
                systemStatus = SystemStatusInfo(
                    systemMode = "cooling",
                    globalTempSet = "24.00",
                    globalHumiditySet = "45.00",
                    avgIndoorTemp = "25.00",
                    avgIndoorHumidity = "50.00",
                    systemRunStatus = "standby"
                ),
                houseInfo = null,
                deviceCount = 6,
                onlineCount = 6
            )
            coEvery { apiService.getSystemStatus(any()) } returns BaseResponse(200, "success", systemStatus)

            // When & Then
            repository.getSystemStatus(1).test {
                assertEquals(ApiResult.Loading, awaitItem())
                val success = awaitItem() as ApiResult.Success
                assertEquals("standby", success.data.systemStatus.systemRunStatus)
                awaitComplete()
            }
        }

        @Test
        fun `getSystemStatus - 系统停止 - 返回停止状态`() = runTest {
            // Given
            val systemStatus = SystemStatus(
                systemStatus = SystemStatusInfo(
                    systemMode = "off",
                    globalTempSet = "--",
                    globalHumiditySet = "--",
                    avgIndoorTemp = "25.00",
                    avgIndoorHumidity = "50.00",
                    systemRunStatus = "stopped"
                ),
                houseInfo = null,
                deviceCount = 6,
                onlineCount = 0
            )
            coEvery { apiService.getSystemStatus(any()) } returns BaseResponse(200, "success", systemStatus)

            // When & Then
            repository.getSystemStatus(1).test {
                assertEquals(ApiResult.Loading, awaitItem())
                val success = awaitItem() as ApiResult.Success
                assertEquals("stopped", success.data.systemStatus.systemRunStatus)
                assertEquals(0, success.data.onlineCount)
                awaitComplete()
            }
        }

        @Test
        fun `getSystemStatus - 网络错误 - 返回网络错误`() = runTest {
            // Given
            coEvery { apiService.getSystemStatus(any()) } throws java.net.UnknownHostException()

            // When & Then
            repository.getSystemStatus(1).test {
                assertEquals(ApiResult.Loading, awaitItem())
                val error = awaitItem() as ApiResult.Error
                assertTrue(error.exception is AppException.NetworkError)
                awaitComplete()
            }
        }

        @Test
        fun `getSystemStatus - 超时错误 - 返回超时错误`() = runTest {
            // Given
            coEvery { apiService.getSystemStatus(any()) } throws java.net.SocketTimeoutException()

            // When & Then
            repository.getSystemStatus(1).test {
                assertEquals(ApiResult.Loading, awaitItem())
                val error = awaitItem() as ApiResult.Error
                assertTrue(error.exception is AppException.TimeoutError)
                awaitComplete()
            }
        }

        @Test
        fun `getSystemStatus - Mock模式 - 返回模拟状态`() = runTest {
            // Given
            val mockRepo = ClimateRepositoryImpl(apiService, useMock = true)

            // When & Then
            mockRepo.getSystemStatus(1).test {
                assertEquals(ApiResult.Loading, awaitItem())
                val success = awaitItem() as ApiResult.Success
                assertNotNull(success.data.systemStatus.systemMode)
                assertNotNull(success.data.systemStatus.globalTempSet)
                awaitComplete()
            }
        }

        @Test
        fun `getSystemStatus - 房屋不存在 - 返回404错误`() = runTest {
            // Given
            coEvery { apiService.getSystemStatus(any()) } returns BaseResponse(404, "房屋不存在", null)

            // When & Then
            repository.getSystemStatus(999).test {
                assertEquals(ApiResult.Loading, awaitItem())
                val error = awaitItem() as ApiResult.Error
                assertTrue(error.exception is AppException.BusinessError)
                awaitComplete()
            }
        }
    }

    // ==================== 设置系统模式测试 ====================

    @Nested
    @DisplayName("设置系统模式测试")
    inner class SetSystemModeTests {

        @ParameterizedTest
        @EnumSource(SystemMode::class)
        fun `setSystemMode - 各种模式 - 返回成功`(mode: SystemMode) = runTest {
            // Given
            val response = SetSystemModeResponse(mode.value)
            coEvery { apiService.setSystemMode(any()) } returns BaseResponse(200, "success", response)

            // When & Then
            repository.setSystemMode(1, mode).test {
                assertEquals(ApiResult.Loading, awaitItem())
                val success = awaitItem() as ApiResult.Success
                assertEquals(mode.value, success.data.mode)
                awaitComplete()
            }
        }

        @Test
        fun `setSystemMode - 制冷模式 - 返回成功`() = runTest {
            // Given
            val response = SetSystemModeResponse("cooling")
            coEvery { apiService.setSystemMode(any()) } returns BaseResponse(200, "success", response)

            // When & Then
            repository.setSystemMode(1, SystemMode.COOLING).test {
                assertEquals(ApiResult.Loading, awaitItem())
                val success = awaitItem() as ApiResult.Success
                assertEquals("cooling", success.data.mode)
                awaitComplete()
            }
        }

        @Test
        fun `setSystemMode - 制热模式 - 返回成功`() = runTest {
            // Given
            val response = SetSystemModeResponse("heating")
            coEvery { apiService.setSystemMode(any()) } returns BaseResponse(200, "success", response)

            // When & Then
            repository.setSystemMode(1, SystemMode.HEATING).test {
                assertEquals(ApiResult.Loading, awaitItem())
                val success = awaitItem() as ApiResult.Success
                assertEquals("heating", success.data.mode)
                awaitComplete()
            }
        }

        @Test
        fun `setSystemMode - 网络错误 - 返回网络错误`() = runTest {
            // Given
            coEvery { apiService.setSystemMode(any()) } throws java.net.UnknownHostException()

            // When & Then
            repository.setSystemMode(1, SystemMode.COOLING).test {
                assertEquals(ApiResult.Loading, awaitItem())
                val error = awaitItem() as ApiResult.Error
                assertTrue(error.exception is AppException.NetworkError)
                awaitComplete()
            }
        }

        @Test
        fun `setSystemMode - Mock模式 - 返回模拟响应`() = runTest {
            // Given
            val mockRepo = ClimateRepositoryImpl(apiService, useMock = true)

            // When & Then
            mockRepo.setSystemMode(1, SystemMode.COOLING).test {
                assertEquals(ApiResult.Loading, awaitItem())
                val success = awaitItem() as ApiResult.Success
                assertEquals("cooling", success.data.mode)
                awaitComplete()
            }
        }

        @Test
        fun `setSystemMode - 权限不足 - 返回403错误`() = runTest {
            // Given
            coEvery { apiService.setSystemMode(any()) } returns BaseResponse(403, "权限不足", null)

            // When & Then
            repository.setSystemMode(1, SystemMode.COOLING).test {
                assertEquals(ApiResult.Loading, awaitItem())
                val error = awaitItem() as ApiResult.Error
                assertTrue(error.exception is AppException.BusinessError)
                awaitComplete()
            }
        }
    }

    // ==================== 设置全局温度测试 ====================

    @Nested
    @DisplayName("设置全局温度测试")
    inner class SetGlobalTempTests {

        @ParameterizedTest
        @CsvSource("16", "18", "20", "22", "24", "26", "28", "30")
        fun `setGlobalTemp - 各种温度值 - 返回成功`(temp: String) = runTest {
            // Given
            coEvery { apiService.setGlobalTemp(any()) } returns BaseResponse(200, "success", Unit)

            // When & Then
            repository.setGlobalTemp(1, temp).test {
                assertEquals(ApiResult.Loading, awaitItem())
                val success = awaitItem() as ApiResult.Success
                assertEquals(Unit, success.data)
                awaitComplete()
            }
        }

        @ParameterizedTest
        @CsvSource("16.0", "20.5", "24.0", "26.5", "30.0")
        fun `setGlobalTemp - 小数温度值 - 返回成功`(temp: String) = runTest {
            // Given
            coEvery { apiService.setGlobalTemp(any()) } returns BaseResponse(200, "success", Unit)

            // When & Then
            repository.setGlobalTemp(1, temp).test {
                assertEquals(ApiResult.Loading, awaitItem())
                val success = awaitItem() as ApiResult.Success
                assertEquals(Unit, success.data)
                awaitComplete()
            }
        }

        @Test
        fun `setGlobalTemp - 网络错误 - 返回网络错误`() = runTest {
            // Given
            coEvery { apiService.setGlobalTemp(any()) } throws java.net.UnknownHostException()

            // When & Then
            repository.setGlobalTemp(1, "24").test {
                assertEquals(ApiResult.Loading, awaitItem())
                val error = awaitItem() as ApiResult.Error
                assertTrue(error.exception is AppException.NetworkError)
                awaitComplete()
            }
        }

        @Test
        fun `setGlobalTemp - Mock模式 - 返回成功`() = runTest {
            // Given
            val mockRepo = ClimateRepositoryImpl(apiService, useMock = true)

            // When & Then
            mockRepo.setGlobalTemp(1, "24").test {
                assertEquals(ApiResult.Loading, awaitItem())
                val success = awaitItem() as ApiResult.Success
                assertEquals(Unit, success.data)
                awaitComplete()
            }
        }

        @Test
        fun `setGlobalTemp - 温度超出范围 - 返回业务错误`() = runTest {
            // Given
            coEvery { apiService.setGlobalTemp(any()) } returns BaseResponse(400, "温度超出范围(16-30)", null)

            // When & Then
            repository.setGlobalTemp(1, "35").test {
                assertEquals(ApiResult.Loading, awaitItem())
                val error = awaitItem() as ApiResult.Error
                assertTrue(error.exception is AppException.BusinessError)
                awaitComplete()
            }
        }

        @Test
        fun `setGlobalTemp - 负数温度 - 返回业务错误`() = runTest {
            // Given
            coEvery { apiService.setGlobalTemp(any()) } returns BaseResponse(400, "温度不能为负数", null)

            // When & Then
            repository.setGlobalTemp(1, "-5").test {
                assertEquals(ApiResult.Loading, awaitItem())
                val error = awaitItem() as ApiResult.Error
                assertTrue(error.exception is AppException.BusinessError)
                awaitComplete()
            }
        }
    }

    // ==================== 设置全局湿度测试 ====================

    @Nested
    @DisplayName("设置全局湿度测试")
    inner class SetGlobalHumidityTests {

        @ParameterizedTest
        @CsvSource("30", "35", "40", "45", "50", "55", "60", "65", "70")
        fun `setGlobalHumidity - 各种湿度值 - 返回成功`(humidity: String) = runTest {
            // Given
            coEvery { apiService.setGlobalHumidity(any()) } returns BaseResponse(200, "success", Unit)

            // When & Then
            repository.setGlobalHumidity(1, humidity).test {
                assertEquals(ApiResult.Loading, awaitItem())
                val success = awaitItem() as ApiResult.Success
                assertEquals(Unit, success.data)
                awaitComplete()
            }
        }

        @Test
        fun `setGlobalHumidity - 网络错误 - 返回网络错误`() = runTest {
            // Given
            coEvery { apiService.setGlobalHumidity(any()) } throws java.net.UnknownHostException()

            // When & Then
            repository.setGlobalHumidity(1, "45").test {
                assertEquals(ApiResult.Loading, awaitItem())
                val error = awaitItem() as ApiResult.Error
                assertTrue(error.exception is AppException.NetworkError)
                awaitComplete()
            }
        }

        @Test
        fun `setGlobalHumidity - Mock模式 - 返回成功`() = runTest {
            // Given
            val mockRepo = ClimateRepositoryImpl(apiService, useMock = true)

            // When & Then
            mockRepo.setGlobalHumidity(1, "45").test {
                assertEquals(ApiResult.Loading, awaitItem())
                val success = awaitItem() as ApiResult.Success
                assertEquals(Unit, success.data)
                awaitComplete()
            }
        }

        @Test
        fun `setGlobalHumidity - 湿度超出范围 - 返回业务错误`() = runTest {
            // Given
            coEvery { apiService.setGlobalHumidity(any()) } returns BaseResponse(400, "湿度超出范围(30-70)", null)

            // When & Then
            repository.setGlobalHumidity(1, "80").test {
                assertEquals(ApiResult.Loading, awaitItem())
                val error = awaitItem() as ApiResult.Error
                assertTrue(error.exception is AppException.BusinessError)
                awaitComplete()
            }
        }

        @Test
        fun `setGlobalHumidity - 湿度过低 - 返回业务错误`() = runTest {
            // Given
            coEvery { apiService.setGlobalHumidity(any()) } returns BaseResponse(400, "湿度不能低于30%", null)

            // When & Then
            repository.setGlobalHumidity(1, "20").test {
                assertEquals(ApiResult.Loading, awaitItem())
                val error = awaitItem() as ApiResult.Error
                assertTrue(error.exception is AppException.BusinessError)
                awaitComplete()
            }
        }
    }

    // ==================== 重试机制测试 ====================

    @Nested
    @DisplayName("重试机制测试")
    inner class RetryTests {

        @Test
        fun `getSystemStatus - 网络错误后重试 - 处理重试逻辑`() = runTest {
            // Given - 模拟网络错误，测试错误处理
            coEvery { apiService.getSystemStatus(any()) } throws java.net.UnknownHostException()

            // When & Then - 应该返回网络错误，不重试
            repository.getSystemStatus(1).test {
                assertEquals(ApiResult.Loading, awaitItem())
                val error = awaitItem() as ApiResult.Error
                assertTrue(error.exception is AppException.NetworkError)
                awaitComplete()
            }
        }

        @Test
        fun `setSystemMode - 超时后重试 - 处理超时错误`() = runTest {
            // Given
            coEvery { apiService.setSystemMode(any()) } throws java.net.SocketTimeoutException()

            // When & Then
            repository.setSystemMode(1, SystemMode.COOLING).test {
                assertEquals(ApiResult.Loading, awaitItem())
                val error = awaitItem() as ApiResult.Error
                assertTrue(error.exception is AppException.TimeoutError)
                awaitComplete()
            }
        }

        @Test
        fun `setGlobalTemp - 服务器500错误 - 返回服务器错误`() = runTest {
            // Given
            val httpException = mockk<retrofit2.HttpException>(relaxed = true)
            every { httpException.code() } returns 500
            every { httpException.message() } returns "Server Error"
            every { httpException.response() } returns null
            coEvery { apiService.setGlobalTemp(any()) } throws httpException

            // When & Then
            repository.setGlobalTemp(1, "24").test {
                assertEquals(ApiResult.Loading, awaitItem())
                val error = awaitItem() as ApiResult.Error
                assertTrue(error.exception is AppException.ServerError)
                awaitComplete()
            }
        }

        @Test
        fun `setGlobalHumidity - 多次失败后重试 - 处理重试逻辑`() = runTest {
            // Given - 模拟网络错误，测试错误处理
            coEvery { apiService.setGlobalHumidity(any()) } throws java.net.UnknownHostException()

            // When & Then - 应该返回网络错误，不重试
            repository.setGlobalHumidity(1, "45").test {
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
        fun `getSystemStatus - 负数房屋ID - 正确处理`() = runTest {
            // Given
            coEvery { apiService.getSystemStatus(-1) } returns BaseResponse(400, "无效的房屋ID", null)

            // When & Then
            repository.getSystemStatus(-1).test {
                assertEquals(ApiResult.Loading, awaitItem())
                val error = awaitItem() as ApiResult.Error
                assertTrue(error.exception is AppException.BusinessError)
                awaitComplete()
            }
        }

        @Test
        fun `getSystemStatus - 零值房屋ID - 正确处理`() = runTest {
            // Given
            coEvery { apiService.getSystemStatus(0) } returns BaseResponse(400, "无效的房屋ID", null)

            // When & Then
            repository.getSystemStatus(0).test {
                assertEquals(ApiResult.Loading, awaitItem())
                val error = awaitItem() as ApiResult.Error
                assertTrue(error.exception is AppException.BusinessError)
                awaitComplete()
            }
        }

        @Test
        fun `getSystemStatus - 大数值房屋ID - 正确处理`() = runTest {
            // Given
            val largeId = Int.MAX_VALUE
            val systemStatus = SystemStatus(
                systemStatus = SystemStatusInfo(
                    systemMode = "cooling",
                    globalTempSet = "24.00",
                    globalHumiditySet = "45.00",
                    avgIndoorTemp = "23.50",
                    avgIndoorHumidity = "45.20",
                    systemRunStatus = "running"
                ),
                houseInfo = null,
                deviceCount = 6,
                onlineCount = 5
            )
            coEvery { apiService.getSystemStatus(largeId) } returns BaseResponse(200, "success", systemStatus)

            // When & Then
            repository.getSystemStatus(largeId).test {
                assertEquals(ApiResult.Loading, awaitItem())
                val success = awaitItem() as ApiResult.Success
                assertNotNull(success.data)
                awaitComplete()
            }
        }

        @Test
        fun `setGlobalTemp - 边界温度值16度 - 正确处理`() = runTest {
            // Given
            coEvery { apiService.setGlobalTemp(any()) } returns BaseResponse(200, "success", Unit)

            // When & Then
            repository.setGlobalTemp(1, "16").test {
                assertEquals(ApiResult.Loading, awaitItem())
                assertTrue(awaitItem() is ApiResult.Success)
                awaitComplete()
            }
        }

        @Test
        fun `setGlobalTemp - 边界温度值30度 - 正确处理`() = runTest {
            // Given
            coEvery { apiService.setGlobalTemp(any()) } returns BaseResponse(200, "success", Unit)

            // When & Then
            repository.setGlobalTemp(1, "30").test {
                assertEquals(ApiResult.Loading, awaitItem())
                assertTrue(awaitItem() is ApiResult.Success)
                awaitComplete()
            }
        }

        @Test
        fun `setGlobalHumidity - 边界湿度值30 - 正确处理`() = runTest {
            // Given
            coEvery { apiService.setGlobalHumidity(any()) } returns BaseResponse(200, "success", Unit)

            // When & Then
            repository.setGlobalHumidity(1, "30").test {
                assertEquals(ApiResult.Loading, awaitItem())
                assertTrue(awaitItem() is ApiResult.Success)
                awaitComplete()
            }
        }

        @Test
        fun `setGlobalHumidity - 边界湿度值70 - 正确处理`() = runTest {
            // Given
            coEvery { apiService.setGlobalHumidity(any()) } returns BaseResponse(200, "success", Unit)

            // When & Then
            repository.setGlobalHumidity(1, "70").test {
                assertEquals(ApiResult.Loading, awaitItem())
                assertTrue(awaitItem() is ApiResult.Success)
                awaitComplete()
            }
        }

        @Test
        fun `setGlobalTemp - 极端高温 - 返回业务错误`() = runTest {
            // Given
            coEvery { apiService.setGlobalTemp(any()) } returns BaseResponse(400, "温度过高", null)

            // When & Then
            repository.setGlobalTemp(1, "99").test {
                assertEquals(ApiResult.Loading, awaitItem())
                val error = awaitItem() as ApiResult.Error
                assertTrue(error.exception is AppException.BusinessError)
                awaitComplete()
            }
        }

        @Test
        fun `setGlobalHumidity - 极端高湿 - 返回业务错误`() = runTest {
            // Given
            coEvery { apiService.setGlobalHumidity(any()) } returns BaseResponse(400, "湿度过高", null)

            // When & Then
            repository.setGlobalHumidity(1, "99").test {
                assertEquals(ApiResult.Loading, awaitItem())
                val error = awaitItem() as ApiResult.Error
                assertTrue(error.exception is AppException.BusinessError)
                awaitComplete()
            }
        }

        @Test
        fun `getSystemStatus - 极端传感器值 - 正确处理`() = runTest {
            // Given
            val systemStatus = SystemStatus(
                systemStatus = SystemStatusInfo(
                    systemMode = "cooling",
                    globalTempSet = "16.00",
                    globalHumiditySet = "30.00",
                    avgIndoorTemp = "99.99",
                    avgIndoorHumidity = "99.99",
                    avgCo2 = "9999",
                    outdoorTemp = "99.99",
                    outdoorHumidity = "99",
                    outdoorAqi = "严重污染",
                    outdoorPm25 = "999",
                    systemRunStatus = "running"
                ),
                houseInfo = null,
                deviceCount = 999,
                onlineCount = 999
            )
            coEvery { apiService.getSystemStatus(any()) } returns BaseResponse(200, "success", systemStatus)

            // When & Then
            repository.getSystemStatus(1).test {
                assertEquals(ApiResult.Loading, awaitItem())
                val success = awaitItem() as ApiResult.Success
                assertEquals("99.99", success.data.systemStatus.avgIndoorTemp)
                assertEquals(999, success.data.deviceCount)
                awaitComplete()
            }
        }

        @Test
        fun `getSystemStatus - 零值传感器 - 正确处理`() = runTest {
            // Given
            val systemStatus = SystemStatus(
                systemStatus = SystemStatusInfo(
                    systemMode = "cooling",
                    globalTempSet = "24.00",
                    globalHumiditySet = "45.00",
                    avgIndoorTemp = "0.00",
                    avgIndoorHumidity = "0.00",
                    avgCo2 = "0",
                    outdoorTemp = "0.00",
                    outdoorHumidity = "0",
                    outdoorAqi = "优",
                    outdoorPm25 = "0",
                    systemRunStatus = "running"
                ),
                houseInfo = null,
                deviceCount = 0,
                onlineCount = 0
            )
            coEvery { apiService.getSystemStatus(any()) } returns BaseResponse(200, "success", systemStatus)

            // When & Then
            repository.getSystemStatus(1).test {
                assertEquals(ApiResult.Loading, awaitItem())
                val success = awaitItem() as ApiResult.Success
                assertEquals("0.00", success.data.systemStatus.avgIndoorTemp)
                assertEquals(0, success.data.deviceCount)
                assertEquals(0, success.data.onlineCount)
                awaitComplete()
            }
        }
    }

    // ==================== 系统模式枚举测试 ====================

    @Nested
    @DisplayName("系统模式枚举测试")
    inner class SystemModeEnumTests {

        @ParameterizedTest
        @ValueSource(strings = ["cooling", "heating", "ventilation", "auto", "off"])
        fun `SystemMode fromValue - 有效值 - 返回对应枚举`(value: String) = runTest {
            // When
            val mode = SystemMode.fromValue(value)

            // Then
            assertEquals(value, mode.value)
        }

        @Test
        fun `SystemMode fromValue - 无效值 - 返回默认值AUTO`() = runTest {
            // When
            val mode = SystemMode.fromValue("invalid")

            // Then
            assertEquals(SystemMode.AUTO, mode)
        }

        @ParameterizedTest
        @ValueSource(strings = ["running", "stopped", "standby", "error"])
        fun `SystemRunStatus fromValue - 有效值 - 返回对应枚举`(value: String) = runTest {
            // When
            val status = SystemRunStatus.fromValue(value)

            // Then
            assertEquals(value, status.value)
        }

        @Test
        fun `SystemRunStatus fromValue - 无效值 - 返回默认值STOPPED`() = runTest {
            // When
            val status = SystemRunStatus.fromValue("invalid")

            // Then
            assertEquals(SystemRunStatus.STOPPED, status)
        }
    }
}
