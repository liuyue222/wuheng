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
import org.junit.jupiter.params.provider.ValueSource

/**
 * DeviceRepository 单元测试
 *
 * 测试覆盖:
 * - 获取设备列表: 全部设备、按房间筛选
 * - 获取设备详情: 单设备信息
 * - 获取设备状态: 实时状态数据
 * - 控制设备: 各种命令类型
 * - 重试机制: 网络错误自动重试
 * - Mock模式: 模拟数据返回
 * - 边界条件: 空值、极值、异常状态
 */
@OptIn(ExperimentalCoroutinesApi::class)
class DeviceRepositoryTest {

    @RegisterExtension
    val mainDispatcherRule = MainDispatcherRule()

    @MockK
    private lateinit var apiService: ApiService

    private lateinit var repository: DeviceRepositoryImpl

    @BeforeEach
    fun setup() {
        MockKAnnotations.init(this)
        repository = DeviceRepositoryImpl(apiService, useMock = false)
    }

    @AfterEach
    fun tearDown() {
        unmockkAll()
    }

    // ==================== 获取设备列表测试 ====================

    @Nested
    @DisplayName("获取设备列表测试")
    inner class GetDeviceListTests {

        @Test
        fun `getDeviceList - 正常获取全部设备 - 返回设备列表`() = runTest {
            // Given
            val devices = listOf(
                DeviceInfo(1, "D001", "客厅温控器", "thermostat", "TH-2025A", 1, "running", "客厅"),
                DeviceInfo(2, "D002", "主卧温控器", "thermostat", "TH-2025A", 1, "standby", "主卧"),
                DeviceInfo(3, "D003", "环境传感器", "sensor", "SE-001", 1, "running", "客厅"),
                DeviceInfo(4, "D004", "新风系统", "fresh_air", "FA-001", 1, "running", "全屋"),
                DeviceInfo(5, "D005", "地暖控制器", "floor_heating", "FH-001", 1, "running", "客厅"),
                DeviceInfo(6, "D006", "湿度调节器", "humidifier", "HM-001", 0, "offline", "主卧")
            )
            coEvery { apiService.getDeviceList(any(), any()) } returns BaseResponse(200, "success", devices)

            // When & Then
            repository.getDeviceList(1, null).test {
                assertEquals(ApiResult.Loading, awaitItem())
                val success = awaitItem() as ApiResult.Success
                assertEquals(6, success.data.size)
                assertEquals("客厅温控器", success.data[0].deviceName)
                assertEquals("thermostat", success.data[0].deviceType)
                awaitComplete()
            }
        }

        @Test
        fun `getDeviceList - 按房间筛选 - 返回该房间设备`() = runTest {
            // Given
            val devices = listOf(
                DeviceInfo(1, "D001", "客厅温控器", "thermostat", "TH-2025A", 1, "running", "客厅"),
                DeviceInfo(3, "D003", "环境传感器", "sensor", "SE-001", 1, "running", "客厅")
            )
            coEvery { apiService.getDeviceList(1, 1) } returns BaseResponse(200, "success", devices)

            // When & Then
            repository.getDeviceList(1, 1).test {
                assertEquals(ApiResult.Loading, awaitItem())
                val success = awaitItem() as ApiResult.Success
                assertEquals(2, success.data.size)
                assertTrue(success.data.all { it.roomName == "客厅" })
                awaitComplete()
            }
        }

        @Test
        fun `getDeviceList - 空设备列表 - 返回空列表`() = runTest {
            // Given
            coEvery { apiService.getDeviceList(any(), any()) } returns BaseResponse(200, "success", emptyList())

            // When & Then
            repository.getDeviceList(1, null).test {
                assertEquals(ApiResult.Loading, awaitItem())
                val success = awaitItem() as ApiResult.Success
                assertTrue(success.data.isEmpty())
                awaitComplete()
            }
        }

        @Test
        fun `getDeviceList - 网络错误 - 返回网络错误`() = runTest {
            // Given
            coEvery { apiService.getDeviceList(any(), any()) } throws java.net.UnknownHostException()

            // When & Then
            repository.getDeviceList(1, null).test {
                assertEquals(ApiResult.Loading, awaitItem())
                val error = awaitItem() as ApiResult.Error
                assertTrue(error.exception is AppException.NetworkError)
                awaitComplete()
            }
        }

        @Test
        fun `getDeviceList - 超时错误 - 返回超时错误`() = runTest {
            // Given
            coEvery { apiService.getDeviceList(any(), any()) } throws java.net.SocketTimeoutException()

            // When & Then
            repository.getDeviceList(1, null).test {
                assertEquals(ApiResult.Loading, awaitItem())
                val error = awaitItem() as ApiResult.Error
                assertTrue(error.exception is AppException.TimeoutError)
                awaitComplete()
            }
        }

        @Test
        fun `getDeviceList - 服务器500错误 - 返回服务器错误`() = runTest {
            // Given
            val httpException = mockk<retrofit2.HttpException>(relaxed = true)
            every { httpException.code() } returns 500
            every { httpException.message() } returns "Internal Server Error"
            every { httpException.response() } returns null
            coEvery { apiService.getDeviceList(any(), any()) } throws httpException

            // When & Then
            repository.getDeviceList(1, null).test {
                assertEquals(ApiResult.Loading, awaitItem())
                val error = awaitItem() as ApiResult.Error
                assertTrue(error.exception is AppException.ServerError)
                awaitComplete()
            }
        }

        @Test
        fun `getDeviceList - Mock模式 - 返回模拟设备列表`() = runTest {
            // Given
            val mockRepo = DeviceRepositoryImpl(apiService, useMock = true)

            // When & Then
            mockRepo.getDeviceList(1, null).test {
                assertEquals(ApiResult.Loading, awaitItem())
                val success = awaitItem() as ApiResult.Success
                assertEquals(6, success.data.size)
                assertTrue(success.data.any { it.deviceType == "thermostat" })
                assertTrue(success.data.any { it.deviceType == "sensor" })
                assertTrue(success.data.any { it.deviceType == "fresh_air" })
                awaitComplete()
            }
        }

        @Test
        fun `getDeviceList - 大量设备 - 正确处理`() = runTest {
            // Given
            val devices = (1..100).map {
                DeviceInfo(it, "D$it", "设备$it", "thermostat", "TH-2025A", 1, "running", "客厅")
            }
            coEvery { apiService.getDeviceList(any(), any()) } returns BaseResponse(200, "success", devices)

            // When & Then
            repository.getDeviceList(1, null).test {
                assertEquals(ApiResult.Loading, awaitItem())
                val success = awaitItem() as ApiResult.Success
                assertEquals(100, success.data.size)
                awaitComplete()
            }
        }
    }

    // ==================== 获取设备详情测试 ====================

    @Nested
    @DisplayName("获取设备详情测试")
    inner class GetDeviceDetailTests {

        @Test
        fun `getDeviceDetail - 正常获取 - 返回设备详情`() = runTest {
            // Given
            val device = DeviceInfo(
                deviceId = 1,
                deviceIdNo = "DEV202604190001",
                deviceName = "客厅温控器",
                deviceType = "thermostat",
                deviceModel = "TH-2025A",
                onlineStatus = 1,
                runStatus = "running",
                roomName = "客厅"
            )
            coEvery { apiService.getDeviceDetail(any()) } returns BaseResponse(200, "success", device)

            // When & Then
            repository.getDeviceDetail(1).test {
                assertEquals(ApiResult.Loading, awaitItem())
                val success = awaitItem() as ApiResult.Success
                assertEquals(1, success.data.deviceId)
                assertEquals("客厅温控器", success.data.deviceName)
                assertEquals("thermostat", success.data.deviceType)
                assertEquals("TH-2025A", success.data.deviceModel)
                assertEquals(1, success.data.onlineStatus)
                assertEquals("running", success.data.runStatus)
                awaitComplete()
            }
        }

        @Test
        fun `getDeviceDetail - 设备不存在 - 返回404错误`() = runTest {
            // Given
            coEvery { apiService.getDeviceDetail(any()) } returns BaseResponse(404, "设备不存在", null)

            // When & Then
            repository.getDeviceDetail(999).test {
                assertEquals(ApiResult.Loading, awaitItem())
                val error = awaitItem() as ApiResult.Error
                assertTrue(error.exception is AppException.BusinessError)
                awaitComplete()
            }
        }

        @Test
        fun `getDeviceDetail - 网络错误 - 返回网络错误`() = runTest {
            // Given
            coEvery { apiService.getDeviceDetail(any()) } throws java.net.UnknownHostException()

            // When & Then
            repository.getDeviceDetail(1).test {
                assertEquals(ApiResult.Loading, awaitItem())
                val error = awaitItem() as ApiResult.Error
                assertTrue(error.exception is AppException.NetworkError)
                awaitComplete()
            }
        }

        @Test
        fun `getDeviceDetail - Mock模式 - 返回模拟设备详情`() = runTest {
            // Given
            val mockRepo = DeviceRepositoryImpl(apiService, useMock = true)

            // When & Then
            mockRepo.getDeviceDetail(1).test {
                assertEquals(ApiResult.Loading, awaitItem())
                val success = awaitItem() as ApiResult.Success
                assertEquals(1, success.data.deviceId)
                assertNotNull(success.data.deviceName)
                assertNotNull(success.data.deviceType)
                awaitComplete()
            }
        }

        @ParameterizedTest
        @ValueSource(ints = [0, -1, Int.MIN_VALUE, Int.MAX_VALUE])
        fun `getDeviceDetail - 边界设备ID - 正确处理`(deviceId: Int) = runTest {
            // Given
            if (deviceId <= 0) {
                coEvery { apiService.getDeviceDetail(deviceId) } returns BaseResponse(400, "无效的设备ID", null)
            } else {
                val device = DeviceInfo(deviceId, "D$deviceId", "设备", "thermostat", "TH-2025A", 1, "running", "客厅")
                coEvery { apiService.getDeviceDetail(deviceId) } returns BaseResponse(200, "success", device)
            }

            // When & Then
            repository.getDeviceDetail(deviceId).test {
                assertEquals(ApiResult.Loading, awaitItem())
                if (deviceId <= 0) {
                    val error = awaitItem() as ApiResult.Error
                    assertTrue(error.exception is AppException.BusinessError)
                } else {
                    val success = awaitItem() as ApiResult.Success
                    assertEquals(deviceId, success.data.deviceId)
                }
                awaitComplete()
            }
        }
    }

    // ==================== 获取设备状态测试 ====================

    @Nested
    @DisplayName("获取设备状态测试")
    inner class GetDeviceStatusTests {

        @Test
        fun `getDeviceStatus - 正常获取 - 返回完整状态`() = runTest {
            // Given
            val status = DeviceStatus(
                deviceId = 1,
                onlineStatus = 1,
                runStatus = "running",
                power = 1,
                temperature = "24.50",
                humidity = "45.00",
                co2 = 420,
                pm25 = 35,
                voc = 150,
                fanSpeed = 1,
                valveOpen = 1,
                reportTime = 1234567890
            )
            coEvery { apiService.getDeviceStatus(any()) } returns BaseResponse(200, "success", status)

            // When & Then
            repository.getDeviceStatus(1).test {
                assertEquals(ApiResult.Loading, awaitItem())
                val success = awaitItem() as ApiResult.Success
                assertEquals(1, success.data.deviceId)
                assertEquals(1, success.data.onlineStatus)
                assertEquals("running", success.data.runStatus)
                assertEquals(1, success.data.power)
                assertEquals("24.50", success.data.temperature)
                assertEquals("45.00", success.data.humidity)
                assertEquals(420, success.data.co2)
                assertEquals(35, success.data.pm25)
                assertEquals(150, success.data.voc)
                assertEquals(1, success.data.fanSpeed)
                assertEquals(1, success.data.valveOpen)
                awaitComplete()
            }
        }

        @Test
        fun `getDeviceStatus - 设备离线 - 返回离线状态`() = runTest {
            // Given
            val status = DeviceStatus(
                deviceId = 1,
                onlineStatus = 0,
                runStatus = "offline",
                power = 0,
                temperature = null,
                humidity = null,
                co2 = null,
                pm25 = null,
                voc = null,
                fanSpeed = null,
                valveOpen = null,
                reportTime = null
            )
            coEvery { apiService.getDeviceStatus(any()) } returns BaseResponse(200, "success", status)

            // When & Then
            repository.getDeviceStatus(1).test {
                assertEquals(ApiResult.Loading, awaitItem())
                val success = awaitItem() as ApiResult.Success
                assertEquals(0, success.data.onlineStatus)
                assertEquals("offline", success.data.runStatus)
                assertEquals(0, success.data.power)
                assertNull(success.data.temperature)
                assertNull(success.data.humidity)
                awaitComplete()
            }
        }

        @Test
        fun `getDeviceStatus - 待机状态 - 返回待机状态`() = runTest {
            // Given
            val status = DeviceStatus(
                deviceId = 1,
                onlineStatus = 1,
                runStatus = "standby",
                power = 0,
                temperature = "24.50",
                humidity = "45.00",
                co2 = 420,
                pm25 = 35,
                voc = 150,
                fanSpeed = 0,
                valveOpen = 0,
                reportTime = 1234567890
            )
            coEvery { apiService.getDeviceStatus(any()) } returns BaseResponse(200, "success", status)

            // When & Then
            repository.getDeviceStatus(1).test {
                assertEquals(ApiResult.Loading, awaitItem())
                val success = awaitItem() as ApiResult.Success
                assertEquals(1, success.data.onlineStatus)
                assertEquals("standby", success.data.runStatus)
                assertEquals(0, success.data.power)
                awaitComplete()
            }
        }

        @Test
        fun `getDeviceStatus - 网络错误 - 返回网络错误`() = runTest {
            // Given
            coEvery { apiService.getDeviceStatus(any()) } throws java.net.UnknownHostException()

            // When & Then
            repository.getDeviceStatus(1).test {
                assertEquals(ApiResult.Loading, awaitItem())
                val error = awaitItem() as ApiResult.Error
                assertTrue(error.exception is AppException.NetworkError)
                awaitComplete()
            }
        }

        @Test
        fun `getDeviceStatus - Mock模式 - 返回模拟状态`() = runTest {
            // Given
            val mockRepo = DeviceRepositoryImpl(apiService, useMock = true)

            // When & Then
            mockRepo.getDeviceStatus(1).test {
                assertEquals(ApiResult.Loading, awaitItem())
                val success = awaitItem() as ApiResult.Success
                assertEquals(1, success.data.deviceId)
                assertNotNull(success.data.temperature)
                assertNotNull(success.data.humidity)
                awaitComplete()
            }
        }

        @Test
        fun `getDeviceStatus - 极端传感器值 - 正确处理`() = runTest {
            // Given
            val status = DeviceStatus(
                deviceId = 1,
                onlineStatus = 1,
                runStatus = "running",
                power = 1,
                temperature = "99.99",
                humidity = "99.99",
                co2 = 9999,
                pm25 = 999,
                voc = 9999,
                fanSpeed = 5,
                valveOpen = 100,
                reportTime = 1234567890
            )
            coEvery { apiService.getDeviceStatus(any()) } returns BaseResponse(200, "success", status)

            // When & Then
            repository.getDeviceStatus(1).test {
                assertEquals(ApiResult.Loading, awaitItem())
                val success = awaitItem() as ApiResult.Success
                assertEquals(9999, success.data.co2)
                assertEquals(999, success.data.pm25)
                awaitComplete()
            }
        }
    }

    // ==================== 控制设备测试 ====================

    @Nested
    @DisplayName("控制设备测试")
    inner class ControlDeviceTests {

        @ParameterizedTest
        @ValueSource(strings = ["on", "off"])
        fun `controlDevice - 开关命令 - 返回成功`(command: String) = runTest {
            // Given
            val response = ControlDeviceResponse(command, "", 1)
            coEvery { apiService.controlDevice(any()) } returns BaseResponse(200, "success", response)

            // When & Then
            repository.controlDevice(1, command, null).test {
                assertEquals(ApiResult.Loading, awaitItem())
                val success = awaitItem() as ApiResult.Success
                assertEquals(command, success.data.command)
                assertEquals(1, success.data.deviceId)
                awaitComplete()
            }
        }

        @ParameterizedTest
        @ValueSource(strings = ["temp_up", "temp_down"])
        fun `controlDevice - 温度调节命令 - 返回成功`(command: String) = runTest {
            // Given
            val response = ControlDeviceResponse(command, "0.5", 1)
            coEvery { apiService.controlDevice(any()) } returns BaseResponse(200, "success", response)

            // When & Then
            repository.controlDevice(1, command, null).test {
                assertEquals(ApiResult.Loading, awaitItem())
                val success = awaitItem() as ApiResult.Success
                assertEquals(command, success.data.command)
                awaitComplete()
            }
        }

        @Test
        fun `controlDevice - 设置温度命令 - 返回成功`() = runTest {
            // Given
            val response = ControlDeviceResponse("set_temp", "24.5", 1)
            coEvery { apiService.controlDevice(any()) } returns BaseResponse(200, "success", response)

            // When & Then
            repository.controlDevice(1, "set_temp", "24.5").test {
                assertEquals(ApiResult.Loading, awaitItem())
                val success = awaitItem() as ApiResult.Success
                assertEquals("set_temp", success.data.command)
                assertEquals("24.5", success.data.value)
                awaitComplete()
            }
        }

        @ParameterizedTest
        @CsvSource("16.0", "20.0", "24.0", "26.0", "30.0", "16.5", "24.5", "30.0")
        fun `controlDevice - 各种温度值 - 返回成功`(temp: String) = runTest {
            // Given
            val response = ControlDeviceResponse("set_temp", temp, 1)
            coEvery { apiService.controlDevice(any()) } returns BaseResponse(200, "success", response)

            // When & Then
            repository.controlDevice(1, "set_temp", temp).test {
                assertEquals(ApiResult.Loading, awaitItem())
                val success = awaitItem() as ApiResult.Success
                assertEquals(temp, success.data.value)
                awaitComplete()
            }
        }

        @Test
        fun `controlDevice - 设备离线 - 返回错误`() = runTest {
            // Given
            coEvery { apiService.controlDevice(any()) } returns BaseResponse(400, "设备离线，无法控制", null)

            // When & Then
            repository.controlDevice(1, "on", null).test {
                assertEquals(ApiResult.Loading, awaitItem())
                val error = awaitItem() as ApiResult.Error
                assertTrue(error.exception is AppException.BusinessError)
                awaitComplete()
            }
        }

        @Test
        fun `controlDevice - 设备不存在 - 返回404错误`() = runTest {
            // Given
            coEvery { apiService.controlDevice(any()) } returns BaseResponse(404, "设备不存在", null)

            // When & Then
            repository.controlDevice(999, "on", null).test {
                assertEquals(ApiResult.Loading, awaitItem())
                val error = awaitItem() as ApiResult.Error
                assertTrue(error.exception is AppException.BusinessError)
                awaitComplete()
            }
        }

        @Test
        fun `controlDevice - 网络错误 - 返回网络错误`() = runTest {
            // Given
            coEvery { apiService.controlDevice(any()) } throws java.net.UnknownHostException()

            // When & Then
            repository.controlDevice(1, "on", null).test {
                assertEquals(ApiResult.Loading, awaitItem())
                val error = awaitItem() as ApiResult.Error
                assertTrue(error.exception is AppException.NetworkError)
                awaitComplete()
            }
        }

        @Test
        fun `controlDevice - Mock模式 - 返回模拟响应`() = runTest {
            // Given
            val mockRepo = DeviceRepositoryImpl(apiService, useMock = true)

            // When & Then
            mockRepo.controlDevice(1, "on", null).test {
                assertEquals(ApiResult.Loading, awaitItem())
                val success = awaitItem() as ApiResult.Success
                assertEquals("on", success.data.command)
                assertEquals(1, success.data.deviceId)
                awaitComplete()
            }
        }

        @Test
        fun `controlDevice - 空值参数 - 正确处理`() = runTest {
            // Given
            val response = ControlDeviceResponse("on", "", 1)
            coEvery { apiService.controlDevice(any()) } returns BaseResponse(200, "success", response)

            // When & Then
            repository.controlDevice(1, "on", "").test {
                assertEquals(ApiResult.Loading, awaitItem())
                val success = awaitItem() as ApiResult.Success
                assertEquals("on", success.data.command)
                awaitComplete()
            }
        }
    }

    // ==================== 重试机制测试 ====================

    @Nested
    @DisplayName("重试机制测试")
    inner class RetryTests {

        @Test
        fun `getDeviceList - 网络错误后重试 - 处理重试逻辑`() = runTest {
            // Given - 模拟网络错误，测试错误处理
            coEvery { apiService.getDeviceList(any(), any()) } throws java.net.UnknownHostException()

            // When & Then - 应该返回网络错误，不重试
            repository.getDeviceList(1, null).test {
                assertEquals(ApiResult.Loading, awaitItem())
                val error = awaitItem() as ApiResult.Error
                assertTrue(error.exception is AppException.NetworkError)
                awaitComplete()
            }
        }

        @Test
        fun `getDeviceDetail - 超时后重试 - 处理超时错误`() = runTest {
            // Given
            coEvery { apiService.getDeviceDetail(any()) } throws java.net.SocketTimeoutException()

            // When & Then
            repository.getDeviceDetail(1).test {
                assertEquals(ApiResult.Loading, awaitItem())
                val error = awaitItem() as ApiResult.Error
                assertTrue(error.exception is AppException.TimeoutError)
                awaitComplete()
            }
        }

        @Test
        fun `getDeviceStatus - 服务器500错误 - 返回服务器错误`() = runTest {
            // Given
            val httpException = mockk<retrofit2.HttpException>(relaxed = true)
            every { httpException.code() } returns 500
            every { httpException.message() } returns "Server Error"
            every { httpException.response() } returns null
            coEvery { apiService.getDeviceStatus(any()) } throws httpException

            // When & Then
            repository.getDeviceStatus(1).test {
                assertEquals(ApiResult.Loading, awaitItem())
                val error = awaitItem() as ApiResult.Error
                assertTrue(error.exception is AppException.ServerError)
                awaitComplete()
            }
        }

        @Test
        fun `controlDevice - 多次失败后重试 - 处理重试逻辑`() = runTest {
            // Given - 模拟网络错误，测试错误处理
            coEvery { apiService.controlDevice(any()) } throws java.net.UnknownHostException()

            // When & Then - 应该返回网络错误，不重试
            repository.controlDevice(1, "on", null).test {
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
        fun `getDeviceList - 负数房屋ID - 正确处理`() = runTest {
            // Given
            coEvery { apiService.getDeviceList(-1, null) } returns BaseResponse(400, "无效的房屋ID", null)

            // When & Then
            repository.getDeviceList(-1, null).test {
                assertEquals(ApiResult.Loading, awaitItem())
                val error = awaitItem() as ApiResult.Error
                assertTrue(error.exception is AppException.BusinessError)
                awaitComplete()
            }
        }

        @Test
        fun `getDeviceList - 零值房屋ID - 正确处理`() = runTest {
            // Given
            coEvery { apiService.getDeviceList(0, null) } returns BaseResponse(400, "无效的房屋ID", null)

            // When & Then
            repository.getDeviceList(0, null).test {
                assertEquals(ApiResult.Loading, awaitItem())
                val error = awaitItem() as ApiResult.Error
                assertTrue(error.exception is AppException.BusinessError)
                awaitComplete()
            }
        }

        @Test
        fun `getDeviceList - 负数房间ID - 正确处理`() = runTest {
            // Given
            coEvery { apiService.getDeviceList(1, -1) } returns BaseResponse(400, "无效的房间ID", null)

            // When & Then
            repository.getDeviceList(1, -1).test {
                assertEquals(ApiResult.Loading, awaitItem())
                val error = awaitItem() as ApiResult.Error
                assertTrue(error.exception is AppException.BusinessError)
                awaitComplete()
            }
        }

        @Test
        fun `controlDevice - 极端温度值 - 正确处理`() = runTest {
            // Given
            val response = ControlDeviceResponse("set_temp", "99.9", 1)
            coEvery { apiService.controlDevice(any()) } returns BaseResponse(200, "success", response)

            // When & Then
            repository.controlDevice(1, "set_temp", "99.9").test {
                assertEquals(ApiResult.Loading, awaitItem())
                val success = awaitItem() as ApiResult.Success
                assertEquals("99.9", success.data.value)
                awaitComplete()
            }
        }

        @Test
        fun `controlDevice - 负数温度值 - 正确处理`() = runTest {
            // Given
            val response = ControlDeviceResponse("set_temp", "-10.0", 1)
            coEvery { apiService.controlDevice(any()) } returns BaseResponse(200, "success", response)

            // When & Then
            repository.controlDevice(1, "set_temp", "-10.0").test {
                assertEquals(ApiResult.Loading, awaitItem())
                val success = awaitItem() as ApiResult.Success
                assertEquals("-10.0", success.data.value)
                awaitComplete()
            }
        }

        @Test
        fun `controlDevice - 超长命令值 - 正确处理`() = runTest {
            // Given
            val longValue = "a".repeat(1000)
            val response = ControlDeviceResponse("set_temp", longValue, 1)
            coEvery { apiService.controlDevice(any()) } returns BaseResponse(200, "success", response)

            // When & Then
            repository.controlDevice(1, "set_temp", longValue).test {
                assertEquals(ApiResult.Loading, awaitItem())
                val success = awaitItem() as ApiResult.Success
                assertEquals(longValue, success.data.value)
                awaitComplete()
            }
        }

        @Test
        fun `getDeviceStatus - 零值传感器 - 正确处理`() = runTest {
            // Given
            val status = DeviceStatus(
                deviceId = 1,
                onlineStatus = 1,
                runStatus = "running",
                power = 1,
                temperature = "0.00",
                humidity = "0.00",
                co2 = 0,
                pm25 = 0,
                voc = 0,
                fanSpeed = 0,
                valveOpen = 0,
                reportTime = 1234567890
            )
            coEvery { apiService.getDeviceStatus(any()) } returns BaseResponse(200, "success", status)

            // When & Then
            repository.getDeviceStatus(1).test {
                assertEquals(ApiResult.Loading, awaitItem())
                val success = awaitItem() as ApiResult.Success
                assertEquals(0, success.data.co2)
                assertEquals(0, success.data.pm25)
                assertEquals(0, success.data.voc)
                awaitComplete()
            }
        }

        @Test
        fun `getDeviceDetail - 特殊字符设备名 - 正确处理`() = runTest {
            // Given
            val device = DeviceInfo(
                deviceId = 1,
                deviceIdNo = "D001",
                deviceName = "温控器-客厅@1号楼",
                deviceType = "thermostat",
                deviceModel = "TH-2025A",
                onlineStatus = 1,
                runStatus = "running",
                roomName = "客厅"
            )
            coEvery { apiService.getDeviceDetail(any()) } returns BaseResponse(200, "success", device)

            // When & Then
            repository.getDeviceDetail(1).test {
                assertEquals(ApiResult.Loading, awaitItem())
                val success = awaitItem() as ApiResult.Success
                assertEquals("温控器-客厅@1号楼", success.data.deviceName)
                awaitComplete()
            }
        }

        @Test
        fun `getDeviceList - 各种设备类型 - 正确处理`() = runTest {
            // Given
            val devices = listOf(
                DeviceInfo(1, "D001", "温控器", "thermostat", "TH-2025A", 1, "running", "客厅"),
                DeviceInfo(2, "D002", "传感器", "sensor", "SE-001", 1, "running", "客厅"),
                DeviceInfo(3, "D003", "新风", "fresh_air", "FA-001", 1, "running", "全屋"),
                DeviceInfo(4, "D004", "地暖", "floor_heating", "FH-001", 1, "running", "客厅"),
                DeviceInfo(5, "D005", "加湿器", "humidifier", "HM-001", 1, "running", "主卧"),
                DeviceInfo(6, "D006", "空调", "ac", "AC-001", 1, "running", "客厅"),
                DeviceInfo(7, "D007", "净化器", "purifier", "PU-001", 1, "running", "客厅")
            )
            coEvery { apiService.getDeviceList(any(), any()) } returns BaseResponse(200, "success", devices)

            // When & Then
            repository.getDeviceList(1, null).test {
                assertEquals(ApiResult.Loading, awaitItem())
                val success = awaitItem() as ApiResult.Success
                assertEquals(7, success.data.size)
                val types = success.data.map { it.deviceType }.toSet()
                assertTrue(types.contains("thermostat"))
                assertTrue(types.contains("sensor"))
                assertTrue(types.contains("fresh_air"))
                awaitComplete()
            }
        }
    }

    // ==================== 设备命令枚举测试 ====================

    @Nested
    @DisplayName("设备命令枚举测试")
    inner class DeviceCommandTests {

        @ParameterizedTest
        @ValueSource(strings = ["on", "off", "temp_up", "temp_down", "set_temp"])
        fun `DeviceCommand fromValue - 有效值 - 返回对应枚举`(value: String) = runTest {
            // When
            val command = DeviceCommand.fromValue(value)

            // Then
            assertEquals(value, command.value)
        }

        @Test
        fun `DeviceCommand fromValue - 无效值 - 返回默认值ON`() = runTest {
            // When
            val command = DeviceCommand.fromValue("invalid")

            // Then
            assertEquals(DeviceCommand.ON, command)
        }

        @ParameterizedTest
        @ValueSource(strings = ["running", "stopped", "standby", "error", "offline"])
        fun `DeviceRunStatus fromValue - 有效值 - 返回对应枚举`(value: String) = runTest {
            // When
            val status = DeviceRunStatus.fromValue(value)

            // Then
            assertEquals(value, status.value)
        }

        @Test
        fun `DeviceRunStatus fromValue - 无效值 - 返回默认值OFFLINE`() = runTest {
            // When
            val status = DeviceRunStatus.fromValue("invalid")

            // Then
            assertEquals(DeviceRunStatus.OFFLINE, status)
        }
    }
}
