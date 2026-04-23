package com.wuheng.smart.integration

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import app.cash.turbine.test
import com.wuheng.smart.data.model.*
import com.wuheng.smart.data.network.*
import com.wuheng.smart.data.repository.HomeRepository
import com.wuheng.smart.data.repository.HomeRepositoryImpl
import com.wuheng.smart.data.repository.UserRepository
import com.wuheng.smart.data.repository.UserRepositoryImpl
import com.wuheng.smart.presentation.home.ClimateMode
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
 * Repository到ViewModel数据流集成测试
 * 验证完整的数据流转：Repository -> ViewModel -> UI State
 */
@ExperimentalCoroutinesApi
@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class RepositoryToViewModelFlowTest {

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

        // 使用Mock Repository进行测试
        homeRepository = mockk(relaxed = true)
        userRepository = mockk(relaxed = true)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    // ==================== 1. 登录ViewModel数据流测试 ====================

    /**
     * 测试登录成功数据流
     */
    @Test
    fun `test login success flow updates UI state correctly`() = runTest {
        // 准备登录成功响应
        val loginResponse = LoginResponse(
            userId = 1,
            userIdNo = "USER001",
            userName = "张三",
            userTel = "13800138001",
            userToken = "token123",
            userType = 1,
            houseId = 1,
            status = 1
        )

        coEvery { 
            userRepository.login(any(), any(), any()) 
        } returns flowOf(ApiResult.Success(loginResponse))

        coEvery { 
            userRepository.getSavedPhone() 
        } returns flowOf("")

        coEvery { 
            userRepository.getSavedPassword() 
        } returns flowOf("")

        coEvery { 
            userRepository.isRememberPassword() 
        } returns flowOf(false)

        // 创建ViewModel
        loginViewModel = LoginViewModel(userRepository)

        // 验证初始状态
        Assert.assertTrue(
            "初始状态应该是Idle",
            loginViewModel.loginState.value is com.wuheng.smart.presentation.base.UiDataState.Idle
        )

        // 执行登录
        loginViewModel.login("13800138001", "password123", false)

        // 等待协程执行
        advanceUntilIdle()

        // 验证登录成功状态
        Assert.assertTrue(
            "登录成功后状态应该是Success",
            loginViewModel.loginState.value is com.wuheng.smart.presentation.base.UiDataState.Success
        )
    }

    /**
     * 测试登录失败数据流
     */
    @Test
    fun `test login failure flow updates UI state correctly`() = runTest {
        // 准备登录失败响应
        val error = AppException.BusinessError(401, "用户名或密码错误")

        coEvery { 
            userRepository.login(any(), any(), any()) 
        } returns flowOf(ApiResult.Error(error))

        coEvery { 
            userRepository.getSavedPhone() 
        } returns flowOf("")

        coEvery { 
            userRepository.getSavedPassword() 
        } returns flowOf("")

        coEvery { 
            userRepository.isRememberPassword() 
        } returns flowOf(false)

        // 创建ViewModel
        loginViewModel = LoginViewModel(userRepository)

        // 执行登录
        loginViewModel.login("13800138001", "wrong_password", false)

        // 等待协程执行
        advanceUntilIdle()

        // 验证登录失败状态
        val state = loginViewModel.loginState.value
        Assert.assertTrue(
            "登录失败后状态应该是Error",
            state is com.wuheng.smart.presentation.base.UiDataState.Error
        )
    }

    /**
     * 测试登录表单验证
     */
    @Test
    fun `test login form validation shows error for invalid input`() = runTest {
        coEvery { 
            userRepository.getSavedPhone() 
        } returns flowOf("")

        coEvery { 
            userRepository.getSavedPassword() 
        } returns flowOf("")

        coEvery { 
            userRepository.isRememberPassword() 
        } returns flowOf(false)

        // 创建ViewModel
        loginViewModel = LoginViewModel(userRepository)

        // 验证初始状态没有错误
        Assert.assertNull(
            "初始状态应该没有验证错误",
            loginViewModel.validationError.value
        )

        // 执行无效登录（空手机号）
        loginViewModel.login("", "password123", false)

        // 等待协程执行
        advanceUntilIdle()

        // 验证验证错误
        Assert.assertEquals(
            "应该显示手机号验证错误",
            "请输入手机号",
            loginViewModel.validationError.value
        )

        // 清除错误
        loginViewModel.clearValidationError()

        // 验证错误已清除
        Assert.assertNull(
            "清除后应该没有验证错误",
            loginViewModel.validationError.value
        )
    }

    // ==================== 2. 首页ViewModel数据流测试 ====================

    /**
     * 测试首页数据加载成功数据流
     */
    @Test
    fun `test home data loading success flow`() = runTest {
        // 准备房屋信息响应
        val houseInfo = HouseInfo(
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

        // 准备场景列表响应
        val sceneList = listOf(
            SceneInfo(1, "SCENE001", "回家模式", "guest", "24.00", "45.00", 1, 1, 1, 1),
            SceneInfo(2, "SCENE002", "离家模式", "away", "18.00", "40.00", 0, 0, 0, 0)
        )

        // 准备系统状态响应
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

        // 设置Mock响应
        coEvery { homeRepository.getHouseInfo(any()) } returns flowOf(
            ApiResult.Loading,
            ApiResult.Success(houseInfo)
        )

        coEvery { homeRepository.getSceneList(any()) } returns flowOf(
            ApiResult.Loading,
            ApiResult.Success(sceneList)
        )

        coEvery { homeRepository.getSystemStatus(any()) } returns flowOf(
            ApiResult.Loading,
            ApiResult.Success(systemStatus)
        )

        coEvery { homeRepository.getDeviceList(any(), any()) } returns flowOf(
            ApiResult.Success(emptyList())
        )

        every { tokenManager.getCurrentHouseId() } returns "1"

        // 创建ViewModel
        homeViewModel = HomeViewModel(homeRepository, tokenManager)

        // 等待初始数据加载
        advanceUntilIdle()

        // 验证UI状态更新
        val uiState = homeViewModel.uiState.value
        Assert.assertEquals("房屋名称应该匹配", "西湖一号院", uiState.residenceName)
        Assert.assertEquals("室内温度应该匹配", "23.50", uiState.indoorTemp)
        Assert.assertEquals("室内湿度应该匹配", "45.20", uiState.indoorHumidity)
        Assert.assertEquals("场景数量应该为2", 2, uiState.scenes.size)
    }

    /**
     * 测试首页数据加载失败数据流
     */
    @Test
    fun `test home data loading error flow`() = runTest {
        // 准备错误响应
        val error = AppException.NetworkError("网络连接失败")

        coEvery { homeRepository.getHouseInfo(any()) } returns flowOf(
            ApiResult.Loading,
            ApiResult.Error(error)
        )

        coEvery { homeRepository.getSceneList(any()) } returns flowOf(
            ApiResult.Success(emptyList())
        )

        coEvery { homeRepository.getSystemStatus(any()) } returns flowOf(
            ApiResult.Success(
                SystemStatus(
                    systemStatus = SystemStatusInfo(
                        systemMode = "cooling",
                        globalTempSet = "24.00",
                        globalHumiditySet = "45.00",
                        avgIndoorTemp = "--",
                        avgIndoorHumidity = "--",
                        systemRunStatus = "stopped"
                    ),
                    houseInfo = null,
                    deviceCount = 0,
                    onlineCount = 0
                )
            )
        )

        coEvery { homeRepository.getDeviceList(any(), any()) } returns flowOf(
            ApiResult.Success(emptyList())
        )

        every { tokenManager.getCurrentHouseId() } returns "1"

        // 创建ViewModel
        homeViewModel = HomeViewModel(homeRepository, tokenManager)

        // 等待数据加载
        advanceUntilIdle()

        // 验证错误状态
        val uiState = homeViewModel.uiState.value
        Assert.assertNotNull("应该有错误信息", uiState.errorMessage)
        Assert.assertTrue("错误信息应该包含'网络'", uiState.errorMessage?.contains("网络") == true)
    }

    /**
     * 测试场景选择数据流
     */
    @Test
    fun `test scene selection flow`() = runTest {
        // 准备场景列表
        val sceneList = listOf(
            SceneInfo(1, "SCENE001", "回家模式", "guest", "24.00", "45.00", 1, 1, 1, 1),
            SceneInfo(2, "SCENE002", "离家模式", "away", "18.00", "40.00", 0, 0, 0, 0),
            SceneInfo(3, "SCENE003", "睡眠模式", "sleep", "26.00", "45.00", 1, 0, 0, 1)
        )

        val applySceneResponse = ApplySceneResponse(1, "回家模式", "24.00", "45.00")

        coEvery { homeRepository.getHouseInfo(any()) } returns flowOf(
            ApiResult.Success(
                HouseInfo(
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

        coEvery { homeRepository.getSceneList(any()) } returns flowOf(
            ApiResult.Success(sceneList)
        )

        coEvery { homeRepository.getSystemStatus(any()) } returns flowOf(
            ApiResult.Success(
                SystemStatus(
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
        )

        coEvery { homeRepository.getDeviceList(any(), any()) } returns flowOf(
            ApiResult.Success(emptyList())
        )

        coEvery { homeRepository.applyScene(any(), any()) } returns flowOf(
            ApiResult.Success(applySceneResponse)
        )

        every { tokenManager.getCurrentHouseId() } returns "1"

        // 创建ViewModel
        homeViewModel = HomeViewModel(homeRepository, tokenManager)

        // 等待初始数据加载
        advanceUntilIdle()

        // 验证初始场景状态
        val initialScenes = homeViewModel.uiState.value.scenes
        Assert.assertEquals("初始应该有3个场景", 3, initialScenes.size)
        Assert.assertFalse("初始没有选中场景", initialScenes.any { it.isSelected })

        // 选择场景
        homeViewModel.onSceneSelected(SceneType.MEETING)

        // 等待处理
        advanceUntilIdle()

        // 验证场景被选中
        val updatedScenes = homeViewModel.uiState.value.scenes
        Assert.assertTrue("应该有场景被选中", updatedScenes.any { it.isSelected })
        Assert.assertTrue("会客模式应该被选中", updatedScenes.find { it.type == SceneType.MEETING }?.isSelected == true)
    }

    /**
     * 测试模式切换数据流
     */
    @Test
    fun `test mode switching flow`() = runTest {
        coEvery { homeRepository.getHouseInfo(any()) } returns flowOf(
            ApiResult.Success(
                HouseInfo(
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

        coEvery { homeRepository.getSceneList(any()) } returns flowOf(
            ApiResult.Success(emptyList())
        )

        coEvery { homeRepository.getSystemStatus(any()) } returns flowOf(
            ApiResult.Success(
                SystemStatus(
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
        )

        coEvery { homeRepository.getDeviceList(any(), any()) } returns flowOf(
            ApiResult.Success(emptyList())
        )

        coEvery { homeRepository.setSystemMode(any(), any()) } returns flowOf(
            ApiResult.Success(SetSystemModeResponse("heating"))
        )

        every { tokenManager.getCurrentHouseId() } returns "1"

        // 创建ViewModel
        homeViewModel = HomeViewModel(homeRepository, tokenManager)

        // 等待初始数据加载
        advanceUntilIdle()

        // 验证初始模式
        Assert.assertEquals("初始模式应该是制冷", ClimateMode.COOLING, homeViewModel.uiState.value.currentMode)

        // 切换模式
        homeViewModel.onModeSelected(ClimateMode.HEATING)

        // 等待处理
        advanceUntilIdle()

        // 验证模式已切换
        Assert.assertEquals("模式应该切换为制热", ClimateMode.HEATING, homeViewModel.uiState.value.currentMode)
    }

    // ==================== 3. 错误恢复数据流测试 ====================

    /**
     * 测试错误恢复数据流
     */
    @Test
    fun `test error recovery flow`() = runTest {
        var requestCount = 0

        coEvery { homeRepository.getHouseInfo(any()) } answers {
            requestCount++
            if (requestCount == 1) {
                flowOf(ApiResult.Error(AppException.NetworkError("网络错误")))
            } else {
                flowOf(
                    ApiResult.Success(
                        HouseInfo(
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

        coEvery { homeRepository.getSceneList(any()) } returns flowOf(
            ApiResult.Success(emptyList())
        )

        coEvery { homeRepository.getSystemStatus(any()) } returns flowOf(
            ApiResult.Success(
                SystemStatus(
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
        )

        coEvery { homeRepository.getDeviceList(any(), any()) } returns flowOf(
            ApiResult.Success(emptyList())
        )

        every { tokenManager.getCurrentHouseId() } returns "1"

        // 创建ViewModel
        homeViewModel = HomeViewModel(homeRepository, tokenManager)

        // 等待初始数据加载（失败）
        advanceUntilIdle()

        // 验证错误状态
        Assert.assertNotNull("应该有错误信息", homeViewModel.uiState.value.errorMessage)

        // 刷新数据
        homeViewModel.refreshData()

        // 等待重试
        advanceUntilIdle()

        // 验证数据已加载
        Assert.assertNull("错误应该被清除", homeViewModel.uiState.value.errorMessage)
        Assert.assertEquals("房屋名称应该匹配", "西湖一号院", homeViewModel.uiState.value.residenceName)
    }
}
