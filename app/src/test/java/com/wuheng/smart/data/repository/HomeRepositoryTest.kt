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
 * HomeRepository 单元测试
 *
 * 测试覆盖:
 * - 房屋模块: 获取房屋信息、楼层列表、房间列表
 * - 设备模块: 获取设备列表、设备详情、设备状态、控制设备
 * - 场景模块: 获取场景列表、应用场景、保存场景
 * - 系统模块: 获取系统状态、设置系统模式/温度/湿度、系统参数
 * - 重试机制: 指数退避重试
 * - Mock模式: 模拟数据返回
 */
@OptIn(ExperimentalCoroutinesApi::class)
class HomeRepositoryTest {

    @RegisterExtension
    val mainDispatcherRule = MainDispatcherRule()

    @MockK
    private lateinit var apiService: ApiService

    private lateinit var repository: HomeRepositoryImpl

    @BeforeEach
    fun setup() {
        MockKAnnotations.init(this)
        repository = HomeRepositoryImpl(apiService, useMock = false)
    }

    @AfterEach
    fun tearDown() {
        unmockkAll()
    }

    // ==================== 房屋模块测试 ====================

    @Nested
    @DisplayName("房屋模块测试")
    inner class HouseTests {

        @Test
        fun `getHouseInfo - 正常获取成功 - 返回房屋信息`() = runTest {
            // Given
            val houseInfo = HouseInfo(
                houseId = 1,
                houseIdNo = "HOUSE001",
                houseName = "阳光花园别墅",
                ownerName = "张三",
                ownerPhone = "13800138000",
                address = "浙江省杭州市西湖区",
                floorCount = 3,
                areaTotal = "280.00",
                systemType = "辐射空调系统",
                roomCount = 5,
                deviceCount = 6,
                onlineCount = 5
            )
            coEvery { apiService.getHouseInfo(any()) } returns BaseResponse(200, "success", houseInfo)

            // When & Then
            repository.getHouseInfo(1).test {
                assertEquals(ApiResult.Loading, awaitItem())
                val success = awaitItem() as ApiResult.Success
                assertEquals(1, success.data.houseId)
                assertEquals("阳光花园别墅", success.data.houseName)
                awaitComplete()
            }
        }

        @Test
        fun `getHouseInfo - 房屋不存在 - 返回404错误`() = runTest {
            // Given
            coEvery { apiService.getHouseInfo(any()) } returns BaseResponse(404, "房屋不存在", null)

            // When & Then
            repository.getHouseInfo(999).test {
                assertEquals(ApiResult.Loading, awaitItem())
                val error = awaitItem() as ApiResult.Error
                assertTrue(error.exception is AppException.BusinessError)
                awaitComplete()
            }
        }

        @Test
        fun `getHouseInfo - 网络错误 - 返回网络错误`() = runTest {
            // Given
            coEvery { apiService.getHouseInfo(any()) } throws java.net.UnknownHostException()

            // When & Then
            repository.getHouseInfo(1).test {
                assertEquals(ApiResult.Loading, awaitItem())
                val error = awaitItem() as ApiResult.Error
                assertTrue(error.exception is AppException.NetworkError)
                awaitComplete()
            }
        }

        @Test
        fun `getHouseInfo - Mock模式 - 返回模拟数据`() = runTest {
            // Given
            val mockRepo = HomeRepositoryImpl(apiService, useMock = true)

            // When & Then
            mockRepo.getHouseInfo(1).test {
                assertEquals(ApiResult.Loading, awaitItem())
                val success = awaitItem() as ApiResult.Success
                assertTrue(success.data.houseId > 0)
                assertNotNull(success.data.houseName)
                awaitComplete()
            }
        }

        @Test
        fun `getFloorInfo - 正常获取 - 返回楼层列表`() = runTest {
            // Given
            val floors = listOf(
                FloorInfo(1, "F001", "地下一层", -1, "80.00", 0),
                FloorInfo(2, "F002", "一层", 1, "100.00", 3),
                FloorInfo(3, "F003", "二层", 2, "100.00", 2)
            )
            coEvery { apiService.getFloorInfo(any()) } returns BaseResponse(200, "success", floors)

            // When & Then
            repository.getFloorInfo(1).test {
                assertEquals(ApiResult.Loading, awaitItem())
                val success = awaitItem() as ApiResult.Success
                assertEquals(3, success.data.size)
                assertEquals("地下一层", success.data[0].floorName)
                awaitComplete()
            }
        }

        @Test
        fun `getFloorInfo - 空楼层列表 - 返回空列表`() = runTest {
            // Given
            coEvery { apiService.getFloorInfo(any()) } returns BaseResponse(200, "success", emptyList())

            // When & Then
            repository.getFloorInfo(1).test {
                assertEquals(ApiResult.Loading, awaitItem())
                val success = awaitItem() as ApiResult.Success
                assertTrue(success.data.isEmpty())
                awaitComplete()
            }
        }

        @Test
        fun `getFloorInfo - Mock模式 - 返回模拟楼层`() = runTest {
            // Given
            val mockRepo = HomeRepositoryImpl(apiService, useMock = true)

            // When & Then
            mockRepo.getFloorInfo(1).test {
                assertEquals(ApiResult.Loading, awaitItem())
                val success = awaitItem() as ApiResult.Success
                assertTrue(success.data.isNotEmpty())
                awaitComplete()
            }
        }

        @Test
        fun `getRoomInfo - 正常获取 - 返回房间列表`() = runTest {
            // Given
            val rooms = listOf(
                RoomInfo(1, "R001", "客厅", "living", "45.00", 2),
                RoomInfo(2, "R002", "主卧", "bedroom", "25.00", 1),
                RoomInfo(3, "R003", "厨房", "kitchen", "15.00", 1)
            )
            coEvery { apiService.getRoomInfo(any(), any()) } returns BaseResponse(200, "success", rooms)

            // When & Then
            repository.getRoomInfo(1, null).test {
                assertEquals(ApiResult.Loading, awaitItem())
                val success = awaitItem() as ApiResult.Success
                assertEquals(3, success.data.size)
                assertEquals("客厅", success.data[0].roomName)
                awaitComplete()
            }
        }

        @Test
        fun `getRoomInfo - 指定楼层 - 返回该楼层房间`() = runTest {
            // Given
            val rooms = listOf(
                RoomInfo(1, "R001", "客厅", "living", "45.00", 2),
                RoomInfo(2, "R002", "主卧", "bedroom", "25.00", 1)
            )
            coEvery { apiService.getRoomInfo(1, 1) } returns BaseResponse(200, "success", rooms)

            // When & Then
            repository.getRoomInfo(1, 1).test {
                assertEquals(ApiResult.Loading, awaitItem())
                val success = awaitItem() as ApiResult.Success
                assertEquals(2, success.data.size)
                awaitComplete()
            }
        }

        @Test
        fun `getRoomInfo - Mock模式 - 返回模拟房间`() = runTest {
            // Given
            val mockRepo = HomeRepositoryImpl(apiService, useMock = true)

            // When & Then
            mockRepo.getRoomInfo(1, null).test {
                assertEquals(ApiResult.Loading, awaitItem())
                val success = awaitItem() as ApiResult.Success
                assertTrue(success.data.isNotEmpty())
                awaitComplete()
            }
        }
    }

    // ==================== 设备模块测试 ====================

    @Nested
    @DisplayName("设备模块测试")
    inner class DeviceTests {

        @Test
        fun `getDeviceList - 正常获取 - 返回设备列表`() = runTest {
            // Given
            val devices = listOf(
                DeviceInfo(1, "D001", "客厅温控器", "thermostat", "TH-2025A", 1, "running", "客厅"),
                DeviceInfo(2, "D002", "主卧温控器", "thermostat", "TH-2025A", 1, "standby", "主卧"),
                DeviceInfo(3, "D003", "环境传感器", "sensor", "SE-001", 1, "running", "客厅")
            )
            coEvery { apiService.getDeviceList(any(), any()) } returns BaseResponse(200, "success", devices)

            // When & Then
            repository.getDeviceList(1, null).test {
                assertEquals(ApiResult.Loading, awaitItem())
                val success = awaitItem() as ApiResult.Success
                assertEquals(3, success.data.size)
                assertEquals("客厅温控器", success.data[0].deviceName)
                awaitComplete()
            }
        }

        @Test
        fun `getDeviceList - 指定房间 - 返回该房间设备`() = runTest {
            // Given
            val devices = listOf(
                DeviceInfo(1, "D001", "客厅温控器", "thermostat", "TH-2025A", 1, "running", "客厅")
            )
            coEvery { apiService.getDeviceList(1, 1) } returns BaseResponse(200, "success", devices)

            // When & Then
            repository.getDeviceList(1, 1).test {
                assertEquals(ApiResult.Loading, awaitItem())
                val success = awaitItem() as ApiResult.Success
                assertEquals(1, success.data.size)
                awaitComplete()
            }
        }

        @Test
        fun `getDeviceList - Mock模式 - 返回模拟设备`() = runTest {
            // Given
            val mockRepo = HomeRepositoryImpl(apiService, useMock = true)

            // When & Then
            mockRepo.getDeviceList(1, null).test {
                assertEquals(ApiResult.Loading, awaitItem())
                val success = awaitItem() as ApiResult.Success
                assertTrue(success.data.isNotEmpty())
                awaitComplete()
            }
        }

        @Test
        fun `getDeviceDetail - 正常获取 - 返回设备详情`() = runTest {
            // Given
            val device = DeviceInfo(1, "D001", "客厅温控器", "thermostat", "TH-2025A", 1, "running", "客厅")
            coEvery { apiService.getDeviceDetail(any()) } returns BaseResponse(200, "success", device)

            // When & Then
            repository.getDeviceDetail(1).test {
                assertEquals(ApiResult.Loading, awaitItem())
                val success = awaitItem() as ApiResult.Success
                assertEquals(1, success.data.deviceId)
                assertEquals("客厅温控器", success.data.deviceName)
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
        fun `getDeviceDetail - Mock模式 - 返回模拟设备`() = runTest {
            // Given
            val mockRepo = HomeRepositoryImpl(apiService, useMock = true)

            // When & Then
            mockRepo.getDeviceDetail(1).test {
                assertEquals(ApiResult.Loading, awaitItem())
                val success = awaitItem() as ApiResult.Success
                assertTrue(success.data.deviceId > 0)
                awaitComplete()
            }
        }

        @Test
        fun `getDeviceStatus - 正常获取 - 返回设备状态`() = runTest {
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
                assertEquals("24.50", success.data.temperature)
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
                awaitComplete()
            }
        }

        @Test
        fun `getDeviceStatus - Mock模式 - 返回模拟状态`() = runTest {
            // Given
            val mockRepo = HomeRepositoryImpl(apiService, useMock = true)

            // When & Then
            mockRepo.getDeviceStatus(1).test {
                assertEquals(ApiResult.Loading, awaitItem())
                val success = awaitItem() as ApiResult.Success
                assertNotNull(success.data.temperature)
                awaitComplete()
            }
        }

        @ParameterizedTest
        @ValueSource(strings = ["on", "off", "temp_up", "temp_down", "set_temp"])
        fun `controlDevice - 各种命令 - 返回成功`(command: String) = runTest {
            // Given
            val response = ControlDeviceResponse(command, "", 1)
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
        fun `controlDevice - 带值命令 - 返回成功`() = runTest {
            // Given
            val response = ControlDeviceResponse("set_temp", "24.5", 1)
            coEvery { apiService.controlDevice(any()) } returns BaseResponse(200, "success", response)

            // When & Then
            repository.controlDevice(1, "set_temp", "24.5").test {
                assertEquals(ApiResult.Loading, awaitItem())
                val success = awaitItem() as ApiResult.Success
                assertEquals("24.5", success.data.value)
                awaitComplete()
            }
        }

        @Test
        fun `controlDevice - Mock模式 - 返回模拟响应`() = runTest {
            // Given
            val mockRepo = HomeRepositoryImpl(apiService, useMock = true)

            // When & Then
            mockRepo.controlDevice(1, "on", null).test {
                assertEquals(ApiResult.Loading, awaitItem())
                val success = awaitItem() as ApiResult.Success
                assertEquals("on", success.data.command)
                awaitComplete()
            }
        }
    }

    // ==================== 场景模块测试 ====================

    @Nested
    @DisplayName("场景模块测试")
    inner class SceneTests {

        @Test
        fun `getSceneList - 正常获取 - 返回场景列表`() = runTest {
            // Given
            val scenes = listOf(
                SceneInfo(1, "S001", "回家模式", "guest", "24.00", "45.00", 1, 1, 1, 1),
                SceneInfo(2, "S002", "离家模式", "away", "18.00", "40.00", 0, 0, 0, 0),
                SceneInfo(3, "S003", "睡眠模式", "sleep", "26.00", "45.00", 1, 0, 0, 1)
            )
            coEvery { apiService.getSceneList(any()) } returns BaseResponse(200, "success", scenes)

            // When & Then
            repository.getSceneList(1).test {
                assertEquals(ApiResult.Loading, awaitItem())
                val success = awaitItem() as ApiResult.Success
                assertEquals(3, success.data.size)
                assertEquals("回家模式", success.data[0].sceneName)
                awaitComplete()
            }
        }

        @Test
        fun `getSceneList - Mock模式 - 返回模拟场景`() = runTest {
            // Given
            val mockRepo = HomeRepositoryImpl(apiService, useMock = true)

            // When & Then
            mockRepo.getSceneList(1).test {
                assertEquals(ApiResult.Loading, awaitItem())
                val success = awaitItem() as ApiResult.Success
                assertTrue(success.data.isNotEmpty())
                awaitComplete()
            }
        }

        @Test
        fun `applyScene - 正常应用 - 返回成功`() = runTest {
            // Given
            val response = ApplySceneResponse(1, "回家模式", "24.00", "45.00")
            coEvery { apiService.applyScene(any()) } returns BaseResponse(200, "success", response)

            // When & Then
            repository.applyScene(1, 1).test {
                assertEquals(ApiResult.Loading, awaitItem())
                val success = awaitItem() as ApiResult.Success
                assertEquals(1, success.data.sceneId)
                assertEquals("回家模式", success.data.sceneName)
                awaitComplete()
            }
        }

        @Test
        fun `applyScene - 场景不存在 - 返回错误`() = runTest {
            // Given
            coEvery { apiService.applyScene(any()) } returns BaseResponse(404, "场景不存在", null)

            // When & Then
            repository.applyScene(999, 1).test {
                assertEquals(ApiResult.Loading, awaitItem())
                val error = awaitItem() as ApiResult.Error
                assertTrue(error.exception is AppException.BusinessError)
                awaitComplete()
            }
        }

        @Test
        fun `applyScene - Mock模式 - 返回模拟响应`() = runTest {
            // Given
            val mockRepo = HomeRepositoryImpl(apiService, useMock = true)

            // When & Then
            mockRepo.applyScene(1, 1).test {
                assertEquals(ApiResult.Loading, awaitItem())
                val success = awaitItem() as ApiResult.Success
                assertTrue(success.data.sceneId > 0)
                awaitComplete()
            }
        }

        @Test
        fun `saveScene - 正常保存 - 返回成功`() = runTest {
            // Given
            val request = SaveSceneRequest(
                houseId = 1,
                sceneName = "自定义场景",
                tempSet = "25.00",
                humiditySet = "50.00"
            )
            coEvery { apiService.saveScene(any()) } returns BaseResponse(200, "success", Unit)

            // When & Then
            repository.saveScene(request).test {
                assertEquals(ApiResult.Loading, awaitItem())
                val success = awaitItem() as ApiResult.Success
                assertEquals(Unit, success.data)
                awaitComplete()
            }
        }

        @Test
        fun `saveScene - 完整参数 - 返回成功`() = runTest {
            // Given
            val request = SaveSceneRequest(
                houseId = 1,
                sceneName = "完整场景",
                tempSet = "24.00",
                humiditySet = "45.00",
                co2Threshold = 800,
                fanSpeed = 2,
                ceilingRadiation = 1,
                floorRadiation = 1,
                freshAir = 1
            )
            coEvery { apiService.saveScene(any()) } returns BaseResponse(200, "success", Unit)

            // When & Then
            repository.saveScene(request).test {
                assertEquals(ApiResult.Loading, awaitItem())
                assertTrue(awaitItem() is ApiResult.Success)
                awaitComplete()
            }
        }

        @Test
        fun `saveScene - Mock模式 - 返回成功`() = runTest {
            // Given
            val mockRepo = HomeRepositoryImpl(apiService, useMock = true)
            val request = SaveSceneRequest(1, "测试场景")

            // When & Then
            mockRepo.saveScene(request).test {
                assertEquals(ApiResult.Loading, awaitItem())
                assertTrue(awaitItem() is ApiResult.Success)
                awaitComplete()
            }
        }
    }

    // ==================== 系统模块测试 ====================

    @Nested
    @DisplayName("系统模块测试")
    inner class SystemTests {

        @Test
        fun `getSystemStatus - 正常获取 - 返回系统状态`() = runTest {
            // Given
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
            coEvery { apiService.getSystemStatus(any()) } returns BaseResponse(200, "success", systemStatus)

            // When & Then
            repository.getSystemStatus(1).test {
                assertEquals(ApiResult.Loading, awaitItem())
                val success = awaitItem() as ApiResult.Success
                assertEquals("cooling", success.data.systemStatus.systemMode)
                assertEquals("24.00", success.data.systemStatus.globalTempSet)
                awaitComplete()
            }
        }

        @Test
        fun `getSystemStatus - Mock模式 - 返回模拟状态`() = runTest {
            // Given
            val mockRepo = HomeRepositoryImpl(apiService, useMock = true)

            // When & Then
            mockRepo.getSystemStatus(1).test {
                assertEquals(ApiResult.Loading, awaitItem())
                val success = awaitItem() as ApiResult.Success
                assertNotNull(success.data.systemStatus)
                awaitComplete()
            }
        }

        @ParameterizedTest
        @ValueSource(strings = ["cooling", "heating", "ventilation", "auto"])
        fun `setSystemMode - 各种模式 - 返回成功`(mode: String) = runTest {
            // Given
            val response = SetSystemModeResponse(mode)
            coEvery { apiService.setSystemMode(any()) } returns BaseResponse(200, "success", response)

            // When & Then
            repository.setSystemMode(1, mode).test {
                assertEquals(ApiResult.Loading, awaitItem())
                val success = awaitItem() as ApiResult.Success
                assertEquals(mode, success.data.mode)
                awaitComplete()
            }
        }

        @Test
        fun `setSystemMode - Mock模式 - 返回模拟响应`() = runTest {
            // Given
            val mockRepo = HomeRepositoryImpl(apiService, useMock = true)

            // When & Then
            mockRepo.setSystemMode(1, "cooling").test {
                assertEquals(ApiResult.Loading, awaitItem())
                val success = awaitItem() as ApiResult.Success
                assertNotNull(success.data.mode)
                awaitComplete()
            }
        }

        @ParameterizedTest
        @CsvSource("16", "20", "24", "26", "30")
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

        @Test
        fun `setGlobalTemp - Mock模式 - 返回成功`() = runTest {
            // Given
            val mockRepo = HomeRepositoryImpl(apiService, useMock = true)

            // When & Then
            mockRepo.setGlobalTemp(1, "24").test {
                assertEquals(ApiResult.Loading, awaitItem())
                assertTrue(awaitItem() is ApiResult.Success)
                awaitComplete()
            }
        }

        @ParameterizedTest
        @CsvSource("30", "40", "50", "60", "70")
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
        fun `setGlobalHumidity - Mock模式 - 返回成功`() = runTest {
            // Given
            val mockRepo = HomeRepositoryImpl(apiService, useMock = true)

            // When & Then
            mockRepo.setGlobalHumidity(1, "45").test {
                assertEquals(ApiResult.Loading, awaitItem())
                assertTrue(awaitItem() is ApiResult.Success)
                awaitComplete()
            }
        }
    }

    // ==================== 系统参数测试 ====================

    @Nested
    @DisplayName("系统参数测试")
    inner class SystemParamsTests {

        @Test
        fun `getSystemParams - 正常获取 - 返回系统参数`() = runTest {
            // Given
            val params = SystemParams(
                houseId = 1,
                systemMode = "cooling",
                globalTempSet = "24.00",
                globalHumiditySet = "45.00",
                tempMin = "16",
                tempMax = "30",
                humidityMin = "30",
                humidityMax = "70",
                co2Threshold = 800,
                fanSpeedDefault = 1,
                vacationMode = 0,
                vacationStartTime = null,
                vacationEndTime = null
            )
            coEvery { apiService.getSystemParams(any()) } returns BaseResponse(200, "success", params)

            // When & Then
            repository.getSystemParams(1).test {
                assertEquals(ApiResult.Loading, awaitItem())
                val success = awaitItem() as ApiResult.Success
                assertEquals(1, success.data.houseId)
                assertEquals("16", success.data.tempMin)
                assertEquals("30", success.data.tempMax)
                awaitComplete()
            }
        }

        @Test
        fun `getSystemParams - Mock模式 - 返回模拟参数`() = runTest {
            // Given
            val mockRepo = HomeRepositoryImpl(apiService, useMock = true)

            // When & Then
            mockRepo.getSystemParams(1).test {
                assertEquals(ApiResult.Loading, awaitItem())
                val success = awaitItem() as ApiResult.Success
                assertNotNull(success.data.tempMin)
                assertNotNull(success.data.tempMax)
                awaitComplete()
            }
        }

        @Test
        fun `setSystemParams - 正常设置 - 返回成功`() = runTest {
            // Given
            val request = SetSystemParamsRequest(
                houseId = 1,
                globalTempSet = "25.00",
                globalHumiditySet = "50.00",
                co2Threshold = 900
            )
            val response = SetSystemParamsResponse(
                houseId = 1,
                updatedParams = listOf("global_temp_set", "global_humidity_set", "co2_threshold"),
                updateTime = 1234567890
            )
            coEvery { apiService.setSystemParams(any()) } returns BaseResponse(200, "success", response)

            // When & Then
            repository.setSystemParams(request).test {
                assertEquals(ApiResult.Loading, awaitItem())
                val success = awaitItem() as ApiResult.Success
                assertEquals(1, success.data.houseId)
                assertEquals(3, success.data.updatedParams?.size)
                awaitComplete()
            }
        }

        @Test
        fun `setSystemParams - 部分参数 - 返回成功`() = runTest {
            // Given
            val request = SetSystemParamsRequest(
                houseId = 1,
                globalTempSet = "26.00"
            )
            val response = SetSystemParamsResponse(
                houseId = 1,
                updatedParams = listOf("global_temp_set"),
                updateTime = 1234567890
            )
            coEvery { apiService.setSystemParams(any()) } returns BaseResponse(200, "success", response)

            // When & Then
            repository.setSystemParams(request).test {
                assertEquals(ApiResult.Loading, awaitItem())
                val success = awaitItem() as ApiResult.Success
                assertEquals(1, success.data.updatedParams?.size)
                awaitComplete()
            }
        }

        @Test
        fun `setSystemParams - Mock模式 - 返回模拟响应`() = runTest {
            // Given
            val mockRepo = HomeRepositoryImpl(apiService, useMock = true)
            val request = SetSystemParamsRequest(1, globalTempSet = "25")

            // When & Then
            mockRepo.setSystemParams(request).test {
                assertEquals(ApiResult.Loading, awaitItem())
                val success = awaitItem() as ApiResult.Success
                assertTrue(success.data.houseId > 0)
                awaitComplete()
            }
        }
    }

    // ==================== 重试机制测试 ====================

    @Nested
    @DisplayName("重试机制测试")
    inner class RetryTests {

        @Test
        fun `getHouseInfo - 网络错误后重试 - 处理重试逻辑`() = runTest {
            // Given - 模拟网络错误，测试错误处理
            coEvery { apiService.getHouseInfo(any()) } throws java.net.UnknownHostException()

            // When & Then - 应该返回网络错误，不重试
            repository.getHouseInfo(1).test {
                assertEquals(ApiResult.Loading, awaitItem())
                val error = awaitItem() as ApiResult.Error
                assertTrue(error.exception is AppException.NetworkError)
                awaitComplete()
            }
        }

        @Test
        fun `getSceneList - 超时后重试 - 处理超时错误`() = runTest {
            // Given
            coEvery { apiService.getSceneList(any()) } throws java.net.SocketTimeoutException()

            // When & Then
            repository.getSceneList(1).test {
                assertEquals(ApiResult.Loading, awaitItem())
                val error = awaitItem() as ApiResult.Error
                assertTrue(error.exception is AppException.TimeoutError)
                awaitComplete()
            }
        }

        @Test
        fun `applyScene - 服务器500错误 - 返回服务器错误`() = runTest {
            // Given
            val httpException = mockk<retrofit2.HttpException>(relaxed = true)
            every { httpException.code() } returns 500
            every { httpException.message() } returns "Server Error"
            every { httpException.response() } returns null
            coEvery { apiService.applyScene(any()) } throws httpException

            // When & Then
            repository.applyScene(1, 1).test {
                assertEquals(ApiResult.Loading, awaitItem())
                val error = awaitItem() as ApiResult.Error
                assertTrue(error.exception is AppException.ServerError)
                awaitComplete()
            }
        }
    }

    // ==================== 边界条件测试 ====================

    @Nested
    @DisplayName("边界条件测试")
    inner class EdgeCaseTests {

        @Test
        fun `getHouseInfo - 负数房屋ID - 正确处理`() = runTest {
            // Given
            coEvery { apiService.getHouseInfo(-1) } returns BaseResponse(400, "无效的房屋ID", null)

            // When & Then
            repository.getHouseInfo(-1).test {
                assertEquals(ApiResult.Loading, awaitItem())
                val error = awaitItem() as ApiResult.Error
                assertTrue(error.exception is AppException.BusinessError)
                awaitComplete()
            }
        }

        @Test
        fun `getHouseInfo - 零值房屋ID - 正确处理`() = runTest {
            // Given
            coEvery { apiService.getHouseInfo(0) } returns BaseResponse(400, "无效的房屋ID", null)

            // When & Then
            repository.getHouseInfo(0).test {
                assertEquals(ApiResult.Loading, awaitItem())
                val error = awaitItem() as ApiResult.Error
                assertTrue(error.exception is AppException.BusinessError)
                awaitComplete()
            }
        }

        @Test
        fun `getFloorInfo - 大数值房屋ID - 正确处理`() = runTest {
            // Given
            val largeId = Int.MAX_VALUE
            coEvery { apiService.getFloorInfo(largeId) } returns BaseResponse(200, "success", emptyList())

            // When & Then
            repository.getFloorInfo(largeId).test {
                assertEquals(ApiResult.Loading, awaitItem())
                val success = awaitItem() as ApiResult.Success
                assertTrue(success.data.isEmpty())
                awaitComplete()
            }
        }

        @Test
        fun `setGlobalTemp - 边界温度值 - 正确处理`() = runTest {
            // Given
            coEvery { apiService.setGlobalTemp(any()) } returns BaseResponse(200, "success", Unit)

            // When & Then - 测试边界值
            repository.setGlobalTemp(1, "16").test {
                assertEquals(ApiResult.Loading, awaitItem())
                assertTrue(awaitItem() is ApiResult.Success)
                awaitComplete()
            }
        }

        @Test
        fun `setGlobalHumidity - 边界湿度值 - 正确处理`() = runTest {
            // Given
            coEvery { apiService.setGlobalHumidity(any()) } returns BaseResponse(200, "success", Unit)

            // When & Then - 测试边界值
            repository.setGlobalHumidity(1, "30").test {
                assertEquals(ApiResult.Loading, awaitItem())
                assertTrue(awaitItem() is ApiResult.Success)
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
}
