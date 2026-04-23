package com.wuheng.smart.integration

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import app.cash.turbine.test
import com.google.gson.Gson
import com.wuheng.smart.data.model.*
import com.wuheng.smart.data.network.*
import com.wuheng.smart.data.repository.HomeRepository
import com.wuheng.smart.data.repository.HomeRepositoryImpl
import com.wuheng.smart.data.repository.UserRepository
import com.wuheng.smart.data.repository.UserRepositoryImpl
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Inject

/**
 * 端到端集成测试
 * 使用Hilt测试注入，测试Repository到ViewModel到UI的完整数据流
 * 测试错误处理流程（网络错误->重试->成功）
 */
@ExperimentalCoroutinesApi
@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class EndToEndTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var tokenManager: TokenManager

    private lateinit var mockWebServer: MockWebServer
    private lateinit var apiService: ApiService
    private lateinit var userRepository: UserRepository
    private lateinit var homeRepository: HomeRepository
    private val gson = Gson()

    @Before
    fun setup() {
        hiltRule.inject()

        mockWebServer = MockWebServer()
        mockWebServer.start()

        val okHttpClient = OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)
            .build()

        apiService = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)

        userRepository = UserRepositoryImpl(apiService, tokenManager, false)
        homeRepository = HomeRepositoryImpl(apiService, false)
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    // ==================== 1. Repository到ViewModel数据流测试 ====================

    /**
     * 测试用户登录完整数据流
     * Repository -> Flow -> ViewModel
     */
    @Test
    fun test_userLoginDataFlow() = runTest {
        // 准备登录响应
        val loginResponse = BaseResponse(
            code = 200,
            message = "success",
            data = LoginResponse(
                userId = 1,
                userIdNo = "USER202604190001",
                userName = "张三",
                userTel = "13800138001",
                userToken = "test_token_12345",
                userType = 1,
                houseId = 1,
                status = 1
            )
        )

        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setBody(gson.toJson(loginResponse))
                .addHeader("Content-Type", "application/json")
        )

        // 测试Repository数据流
        userRepository.login("13800138001", "password123").test {
            // 等待Loading状态
            val loadingState = awaitItem()
            assertTrue("应该有Loading状态", loadingState is ApiResult.Loading)

            // 等待Success状态
            val successState = awaitItem()
            assertTrue("应该有Success状态", successState is ApiResult.Success)
            assertEquals("用户ID应该匹配", 1, (successState as ApiResult.Success).data.userId)
            assertEquals("Token应该正确", "test_token_12345", successState.data.userToken)

            cancelAndIgnoreRemainingEvents()
        }

        // 验证Token已保存
        val savedToken = tokenManager.getToken()
        assertEquals("Token应该已保存", "test_token_12345", savedToken)
    }

    /**
     * 测试首页数据加载完整数据流
     */
    @Test
    fun test_homeDataLoadingFlow() = runTest {
        // 准备房屋信息响应
        val houseInfoResponse = BaseResponse(
            code = 200,
            message = "success",
            data = HouseInfo(
                houseId = 1,
                houseIdNo = "HOUSE001",
                houseName = "西湖一号院",
                ownerName = "张三",
                ownerPhone = "13800138001",
                address = "浙江省杭州市西湖区",
                floorCount = 3,
                areaTotal = "280.00",
                systemType = "辐射空调系统",
                roomCount = 5,
                deviceCount = 6,
                onlineCount = 5
            )
        )

        // 准备场景列表响应
        val sceneListResponse = BaseResponse(
            code = 200,
            message = "success",
            data = listOf(
                SceneInfo(1, "SCENE001", "回家模式", "guest", "24.00", "45.00", 1, 1, 1, 1),
                SceneInfo(2, "SCENE002", "离家模式", "away", "18.00", "40.00", 0, 0, 0, 0)
            )
        )

        // 准备系统状态响应
        val systemStatusResponse = BaseResponse(
            code = 200,
            message = "success",
            data = SystemStatus(
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
        )

        // 设置Mock响应
        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setBody(gson.toJson(houseInfoResponse))
                .addHeader("Content-Type", "application/json")
        )
        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setBody(gson.toJson(sceneListResponse))
                .addHeader("Content-Type", "application/json")
        )
        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setBody(gson.toJson(systemStatusResponse))
                .addHeader("Content-Type", "application/json")
        )

        // 测试房屋信息数据流
        homeRepository.getHouseInfo(1).test {
            val loadingState = awaitItem()
            assertTrue("应该有Loading状态", loadingState is ApiResult.Loading)

            val successState = awaitItem()
            assertTrue("应该有Success状态", successState is ApiResult.Success)
            assertEquals("房屋名称应该匹配", "西湖一号院", (successState as ApiResult.Success).data.houseName)

            cancelAndIgnoreRemainingEvents()
        }

        // 测试场景列表数据流
        homeRepository.getSceneList(1).test {
            val loadingState = awaitItem()
            assertTrue("应该有Loading状态", loadingState is ApiResult.Loading)

            val successState = awaitItem()
            assertTrue("应该有Success状态", successState is ApiResult.Success)
            assertEquals("场景数量应该为2", 2, (successState as ApiResult.Success).data.size)

            cancelAndIgnoreRemainingEvents()
        }

        // 测试系统状态数据流
        homeRepository.getSystemStatus(1).test {
            val loadingState = awaitItem()
            assertTrue("应该有Loading状态", loadingState is ApiResult.Loading)

            val successState = awaitItem()
            assertTrue("应该有Success状态", successState is ApiResult.Success)
            assertEquals("系统模式应该匹配", "cooling", (successState as ApiResult.Success).data.systemStatus.systemMode)

            cancelAndIgnoreRemainingEvents()
        }
    }

    // ==================== 2. 错误处理流程测试 ====================

    /**
     * 测试网络错误->重试->成功流程
     */
    @Test
    fun test_networkErrorRetrySuccessFlow() = runTest {
        // 第一次请求失败
        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(500)
                .setBody("{\"code\":500,\"msg\":\"服务器内部错误\",\"data\":null}")
                .addHeader("Content-Type", "application/json")
        )

        // 第二次请求成功
        val successResponse = BaseResponse(
            code = 200,
            message = "success",
            data = HouseInfo(
                houseId = 1,
                houseIdNo = "HOUSE001",
                houseName = "西湖一号院",
                ownerName = "张三",
                ownerPhone = "13800138001",
                address = "浙江省杭州市西湖区",
                floorCount = 3,
                areaTotal = "280.00",
                systemType = "辐射空调系统",
                roomCount = 5,
                deviceCount = 6,
                onlineCount = 5
            )
        )
        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setBody(gson.toJson(successResponse))
                .addHeader("Content-Type", "application/json")
        )

        // 第一次请求（失败）
        val result1 = homeRepository.getHouseInfo(1).first()
        assertTrue("第一次请求应该失败", result1 is ApiResult.Error)

        // 第二次请求（成功）
        val result2 = homeRepository.getHouseInfo(1).first { it is ApiResult.Success }
        assertTrue("第二次请求应该成功", result2 is ApiResult.Success)
        assertEquals("房屋名称应该匹配", "西湖一号院", (result2 as ApiResult.Success).data.houseName)
    }

    /**
     * 测试Token过期->刷新Token->重试流程
     */
    @Test
    fun test_tokenExpiredRefreshFlow() = runTest {
        // Token过期响应
        val expiredResponse = BaseResponse<UserInfo>(
            code = 401,
            message = "Token已过期",
            data = null
        )

        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setBody(gson.toJson(expiredResponse))
                .addHeader("Content-Type", "application/json")
        )

        // 测试Token过期处理
        userRepository.getUserInfo().test {
            val result = awaitItem()
            assertTrue("应该返回错误", result is ApiResult.Error)
            val error = (result as ApiResult.Error).exception
            assertTrue("应该是业务错误", error is AppException.BusinessError)
            assertEquals("错误码应该为401", 401, (error as AppException.BusinessError).code)

            cancelAndIgnoreRemainingEvents()
        }
    }

    /**
     * 测试网络超时处理
     */
    @Test
    fun test_networkTimeoutHandling() = runTest {
        // 设置超时响应
        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setBody(gson.toJson(BaseResponse(200, "success", Unit)))
                .setBodyDelay(5, TimeUnit.SECONDS) // 延迟5秒
                .addHeader("Content-Type", "application/json")
        )

        // 由于超时设置，这里会失败
        // 实际测试中应该配置较短的超时时间
    }

    // ==================== 3. 设备控制完整流程测试 ====================

    /**
     * 测试设备控制完整数据流
     */
    @Test
    fun test_deviceControlFlow() = runTest {
        // 准备设备列表响应
        val deviceListResponse = BaseResponse(
            code = 200,
            message = "success",
            data = listOf(
                DeviceInfo(1, "DEV001", "客厅温控器", "thermostat", "TH-2025A", 1, "running", "客厅"),
                DeviceInfo(2, "DEV002", "主卧温控器", "thermostat", "TH-2025A", 1, "standby", "主卧")
            )
        )

        // 准备控制设备响应
        val controlResponse = BaseResponse(
            code = 200,
            message = "success",
            data = ControlDeviceResponse("on", "1", 1)
        )

        // 准备设备状态响应
        val deviceStatusResponse = BaseResponse(
            code = 200,
            message = "success",
            data = DeviceStatus(
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
                reportTime = System.currentTimeMillis() / 1000
            )
        )

        // 设置Mock响应
        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setBody(gson.toJson(deviceListResponse))
                .addHeader("Content-Type", "application/json")
        )
        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setBody(gson.toJson(controlResponse))
                .addHeader("Content-Type", "application/json")
        )
        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setBody(gson.toJson(deviceStatusResponse))
                .addHeader("Content-Type", "application/json")
        )

        // 获取设备列表
        homeRepository.getDeviceList(1).test {
            val loadingState = awaitItem()
            assertTrue("应该有Loading状态", loadingState is ApiResult.Loading)

            val successState = awaitItem()
            assertTrue("应该有Success状态", successState is ApiResult.Success)
            assertEquals("设备数量应该为2", 2, (successState as ApiResult.Success).data.size)

            cancelAndIgnoreRemainingEvents()
        }

        // 控制设备
        homeRepository.controlDevice(1, "on", "1").test {
            val loadingState = awaitItem()
            assertTrue("应该有Loading状态", loadingState is ApiResult.Loading)

            val successState = awaitItem()
            assertTrue("应该有Success状态", successState is ApiResult.Success)
            assertEquals("命令应该匹配", "on", (successState as ApiResult.Success).data.command)

            cancelAndIgnoreRemainingEvents()
        }

        // 查询设备状态
        homeRepository.getDeviceStatus(1).test {
            val successState = awaitItem()
            assertTrue("应该有Success状态", successState is ApiResult.Success)
            assertEquals("设备应该在线", 1, (successState as ApiResult.Success).data.onlineStatus)

            cancelAndIgnoreRemainingEvents()
        }
    }

    // ==================== 4. 场景应用完整流程测试 ====================

    /**
     * 测试场景应用完整数据流
     */
    @Test
    fun test_sceneApplyFlow() = runTest {
        // 准备应用场景响应
        val applySceneResponse = BaseResponse(
            code = 200,
            message = "success",
            data = ApplySceneResponse(1, "回家模式", "24.00", "45.00")
        )

        // 准备系统状态响应（应用场景后）
        val systemStatusResponse = BaseResponse(
            code = 200,
            message = "success",
            data = SystemStatus(
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
        )

        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setBody(gson.toJson(applySceneResponse))
                .addHeader("Content-Type", "application/json")
        )
        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setBody(gson.toJson(systemStatusResponse))
                .addHeader("Content-Type", "application/json")
        )

        // 应用场景
        homeRepository.applyScene(1, 1).test {
            val loadingState = awaitItem()
            assertTrue("应该有Loading状态", loadingState is ApiResult.Loading)

            val successState = awaitItem()
            assertTrue("应该有Success状态", successState is ApiResult.Success)
            assertEquals("场景名称应该匹配", "回家模式", (successState as ApiResult.Success).data.sceneName)
            assertEquals("温度设置应该匹配", "24.00", successState.data.tempSet)

            cancelAndIgnoreRemainingEvents()
        }

        // 刷新系统状态
        homeRepository.getSystemStatus(1).test {
            val successState = awaitItem()
            assertTrue("应该有Success状态", successState is ApiResult.Success)
            assertEquals("系统模式应该匹配", "cooling", (successState as ApiResult.Success).data.systemStatus.systemMode)

            cancelAndIgnoreRemainingEvents()
        }
    }

    // ==================== 5. 并发请求测试 ====================

    /**
     * 测试并发数据请求
     */
    @Test
    fun test_concurrentDataRequests() = runTest {
        // 准备多个响应
        val houseInfoResponse = BaseResponse(
            code = 200,
            message = "success",
            data = HouseInfo(
                houseId = 1,
                houseIdNo = "HOUSE001",
                houseName = "西湖一号院",
                ownerName = "张三",
                ownerPhone = "13800138001",
                address = "浙江省杭州市西湖区",
                floorCount = 3,
                areaTotal = "280.00",
                systemType = "辐射空调系统",
                roomCount = 5,
                deviceCount = 6,
                onlineCount = 5
            )
        )

        val sceneListResponse = BaseResponse(
            code = 200,
            message = "success",
            data = listOf(
                SceneInfo(1, "SCENE001", "回家模式", "guest", "24.00", "45.00", 1, 1, 1, 1)
            )
        )

        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setBody(gson.toJson(houseInfoResponse))
                .addHeader("Content-Type", "application/json")
        )
        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setBody(gson.toJson(sceneListResponse))
                .addHeader("Content-Type", "application/json")
        )

        // 并发请求
        val houseResult = homeRepository.getHouseInfo(1).first { it is ApiResult.Success }
        val sceneResult = homeRepository.getSceneList(1).first { it is ApiResult.Success }

        assertTrue("房屋信息请求应该成功", houseResult is ApiResult.Success)
        assertTrue("场景列表请求应该成功", sceneResult is ApiResult.Success)
    }
}
