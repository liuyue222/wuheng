package com.wuheng.smart.integration

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.wuheng.smart.data.network.*
import com.wuheng.smart.data.repository.HomeRepository
import com.wuheng.smart.data.repository.UserRepository
import com.wuheng.smart.presentation.home.HomeViewModel
import com.wuheng.smart.presentation.login.LoginViewModel
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import io.mockk.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.*
import org.junit.*
import org.junit.runner.RunWith
import javax.inject.Inject

/**
 * 错误处理流程集成测试
 * 测试网络错误->重试->成功的完整流程
 */
@ExperimentalCoroutinesApi
@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class ErrorHandlingIntegrationTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val instantExecutorRule = InstantTaskExecutorRule()

    @Inject
    lateinit var tokenManager: TokenManager

    private val testDispatcher = StandardTestDispatcher()
    private lateinit var homeRepository: HomeRepository
    private lateinit var userRepository: UserRepository
    private lateinit var homeViewModel: HomeViewModel
    private lateinit var loginViewModel: LoginViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        hiltRule.inject()

        homeRepository = mockk(relaxed = true)
        userRepository = mockk(relaxed = true)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    // ==================== 1. 网络错误重试流程测试 ====================

    /**
     * 测试网络错误后自动重试流程
     */
    @Test
    fun `test network error auto retry flow`() = runTest {
        var attemptCount = 0

        // 模拟前两次失败，第三次成功
        coEvery { userRepository.login(any(), any(), any()) } answers {
            attemptCount++
            when (attemptCount) {
                1, 2 -> flowOf(ApiResult.Error(AppException.NetworkError("网络连接超时")))
                else -> flowOf(
                    ApiResult.Success(
                        com.wuheng.smart.data.model.LoginResponse(
                            userId = 1,
                            userIdNo = "USER001",
                            userName = "张三",
                            userTel = "13800138001",
                            userToken = "token123",
                            userType = 1,
                            houseId = 1,
                            status = 1
                        )
                    )
                )
            }
        }

        coEvery { userRepository.getSavedPhone() } returns flowOf("")
        coEvery { userRepository.getSavedPassword() } returns flowOf("")
        coEvery { userRepository.isRememberPassword() } returns flowOf(false)

        loginViewModel = LoginViewModel(userRepository)

        // 第一次登录尝试
        loginViewModel.login("13800138001", "password123", false)
        advanceUntilIdle()

        // 验证失败状态
        Assert.assertTrue(
            "第一次尝试应该失败",
            loginViewModel.loginState.value is com.wuheng.smart.presentation.base.UiDataState.Error
        )

        // 重置状态
        loginViewModel.resetState()

        // 第二次登录尝试
        loginViewModel.login("13800138001", "password123", false)
        advanceUntilIdle()

        // 验证仍然失败
        Assert.assertTrue(
            "第二次尝试应该失败",
            loginViewModel.loginState.value is com.wuheng.smart.presentation.base.UiDataState.Error
        )

        // 重置状态
        loginViewModel.resetState()

        // 第三次登录尝试（应该成功）
        loginViewModel.login("13800138001", "password123", false)
        advanceUntilIdle()

        // 验证成功
        Assert.assertTrue(
            "第三次尝试应该成功",
            loginViewModel.loginState.value is com.wuheng.smart.presentation.base.UiDataState.Success
        )

        Assert.assertEquals("应该尝试了3次", 3, attemptCount)
    }

    /**
     * 测试服务器错误后重试流程
     */
    @Test
    fun `test server error retry flow`() = runTest {
        var attemptCount = 0

        coEvery { homeRepository.getHouseInfo(any()) } answers {
            attemptCount++
            when (attemptCount) {
                1 -> flowOf(ApiResult.Error(AppException.ServerError("服务器内部错误", 500)))
                else -> flowOf(
                    ApiResult.Success(
                        com.wuheng.smart.data.model.HouseInfo(
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
                )
            }
        }

        coEvery { homeRepository.getSceneList(any()) } returns flowOf(ApiResult.Success(emptyList()))
        coEvery { homeRepository.getSystemStatus(any()) } returns flowOf(
            ApiResult.Success(
                com.wuheng.smart.data.model.SystemStatus(
                    systemStatus = com.wuheng.smart.data.model.SystemStatusInfo(
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
        )
        coEvery { homeRepository.getDeviceList(any(), any()) } returns flowOf(ApiResult.Success(emptyList()))
        every { tokenManager.getCurrentHouseId() } returns "1"

        homeViewModel = HomeViewModel(homeRepository, tokenManager)
        advanceUntilIdle()

        // 验证初始错误状态
        Assert.assertNotNull("应该有错误信息", homeViewModel.uiState.value.errorMessage)

        // 手动刷新（重试）
        homeViewModel.refreshData()
        advanceUntilIdle()

        // 验证成功
        Assert.assertNull("错误应该被清除", homeViewModel.uiState.value.errorMessage)
        Assert.assertEquals("房屋名称应该匹配", "西湖一号院", homeViewModel.uiState.value.residenceName)
        Assert.assertEquals("应该尝试了2次", 2, attemptCount)
    }

    // ==================== 2. Token过期处理流程测试 ====================

    /**
     * 测试Token过期后刷新流程
     */
    @Test
    fun `test token expired refresh flow`() = runTest {
        // 模拟Token过期
        coEvery { userRepository.getUserInfo() } returns flowOf(
            ApiResult.Error(AppException.BusinessError(401, "Token已过期"))
        )

        // 模拟Token刷新
        coEvery { userRepository.login(any(), any(), any()) } returns flowOf(
            ApiResult.Success(
                com.wuheng.smart.data.model.LoginResponse(
                    userId = 1,
                    userIdNo = "USER001",
                    userName = "张三",
                    userTel = "13800138001",
                    userToken = "new_token_123",
                    userType = 1,
                    houseId = 1,
                    status = 1
                )
            )
        )

        coEvery { userRepository.getSavedPhone() } returns flowOf("13800138001")
        coEvery { userRepository.getSavedPassword() } returns flowOf("password123")
        coEvery { userRepository.isRememberPassword() } returns flowOf(true)

        loginViewModel = LoginViewModel(userRepository)
        advanceUntilIdle()

        // 这里可以添加Token过期后的处理逻辑测试
    }

    // ==================== 3. 连续错误处理测试 ====================

    /**
     * 测试连续错误后最终成功的流程
     */
    @Test
    fun `test consecutive errors then success flow`() = runTest {
        val errors = listOf(
            AppException.NetworkError("网络连接超时"),
            AppException.ServerError("服务器繁忙", 503),
            AppException.NetworkError("DNS解析失败"),
            AppException.BusinessError(429, "请求过于频繁")
        )

        var attemptCount = 0

        coEvery { homeRepository.getHouseInfo(any()) } answers {
            val currentAttempt = attemptCount++
            if (currentAttempt < errors.size) {
                flowOf(ApiResult.Error(errors[currentAttempt]))
            } else {
                flowOf(
                    ApiResult.Success(
                        com.wuheng.smart.data.model.HouseInfo(
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
                )
            }
        }

        coEvery { homeRepository.getSceneList(any()) } returns flowOf(ApiResult.Success(emptyList()))
        coEvery { homeRepository.getSystemStatus(any()) } returns flowOf(
            ApiResult.Success(
                com.wuheng.smart.data.model.SystemStatus(
                    systemStatus = com.wuheng.smart.data.model.SystemStatusInfo(
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
        )
        coEvery { homeRepository.getDeviceList(any(), any()) } returns flowOf(ApiResult.Success(emptyList()))
        every { tokenManager.getCurrentHouseId() } returns "1"

        homeViewModel = HomeViewModel(homeRepository, tokenManager)

        // 连续重试直到成功
        for (i in errors.indices) {
            advanceUntilIdle()
            Assert.assertNotNull("第${i + 1}次应该有错误", homeViewModel.uiState.value.errorMessage)
            homeViewModel.refreshData()
        }

        // 最后一次应该成功
        advanceUntilIdle()
        Assert.assertNull("最终应该没有错误", homeViewModel.uiState.value.errorMessage)
        Assert.assertEquals("房屋名称应该匹配", "西湖一号院", homeViewModel.uiState.value.residenceName)
        Assert.assertEquals("应该尝试了${errors.size + 1}次", errors.size + 1, attemptCount)
    }

    // ==================== 4. 部分成功部分失败测试 ====================

    /**
     * 测试部分API成功部分失败的场景
     */
    @Test
    fun `test partial success partial failure scenario`() = runTest {
        // 房屋信息成功
        coEvery { homeRepository.getHouseInfo(any()) } returns flowOf(
            ApiResult.Success(
                com.wuheng.smart.data.model.HouseInfo(
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
        )

        // 场景列表失败
        coEvery { homeRepository.getSceneList(any()) } returns flowOf(
            ApiResult.Error(AppException.NetworkError("场景列表加载失败"))
        )

        // 系统状态成功
        coEvery { homeRepository.getSystemStatus(any()) } returns flowOf(
            ApiResult.Success(
                com.wuheng.smart.data.model.SystemStatus(
                    systemStatus = com.wuheng.smart.data.model.SystemStatusInfo(
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
        )

        coEvery { homeRepository.getDeviceList(any(), any()) } returns flowOf(ApiResult.Success(emptyList()))
        every { tokenManager.getCurrentHouseId() } returns "1"

        homeViewModel = HomeViewModel(homeRepository, tokenManager)
        advanceUntilIdle()

        // 验证房屋信息已加载
        Assert.assertEquals("房屋名称应该匹配", "西湖一号院", homeViewModel.uiState.value.residenceName)

        // 验证系统状态已加载
        Assert.assertEquals("室内温度应该匹配", "23.50", homeViewModel.uiState.value.indoorTemp)

        // 验证场景列表为空（因为加载失败）
        Assert.assertTrue("场景列表应该为空", homeViewModel.uiState.value.scenes.isEmpty())
    }
}
