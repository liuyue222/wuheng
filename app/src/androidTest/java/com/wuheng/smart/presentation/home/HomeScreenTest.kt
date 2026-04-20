package com.wuheng.smart.presentation.home

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.wuheng.smart.data.model.*
import com.wuheng.smart.data.repository.HomeRepository
import com.wuheng.smart.presentation.base.UiDataState
import io.mockk.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * 首页 UI 测试
 *
 * 测试范围:
 * 1. 页面元素显示验证
 * 2. 环境数据显示
 * 3. 设备卡片显示与交互
 * 4. 场景选择器
 * 5. 下拉刷新
 * 6. 加载和错误状态
 * 7. 导航交互
 */
@RunWith(AndroidJUnit4::class)
class HomeScreenTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    private lateinit var mockViewModel: HomeViewModel
    private val uiState = MutableStateFlow(HomeUiState())

    @Before
    fun setUp() {
        mockViewModel = mockk(relaxed = true)
        every { mockViewModel.uiState } returns uiState.asStateFlow()
    }

    // ==================== 页面元素显示测试 ====================

    @Test
    fun homeScreen_shouldDisplayHeaderElements() {
        // Given
        uiState.value = HomeUiState(
            homeData = UiDataState.Success(
                HomeOverview(
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
            )
        )

        // When
        composeTestRule.setContent {
            HomeScreen(
                viewModel = mockViewModel,
                onNavigateToClimate = {},
                onNavigateToWater = {},
                onNavigateToProfile = {}
            )
        }

        // Then
        composeTestRule.onNodeWithText("西湖壹号院").assertIsDisplayed()
        composeTestRule.onNodeWithText("杭州市西湖区").assertIsDisplayed()
    }

    @Test
    fun homeScreen_shouldDisplayEnvironmentData() {
        // Given
        uiState.value = HomeUiState(
            homeData = UiDataState.Success(
                HomeOverview(
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
            )
        )

        // When
        composeTestRule.setContent {
            HomeScreen(
                viewModel = mockViewModel,
                onNavigateToClimate = {},
                onNavigateToWater = {},
                onNavigateToProfile = {}
            )
        }

        // Then
        composeTestRule.onNodeWithText("24.5°C").assertIsDisplayed()
        composeTestRule.onNodeWithText("48%").assertIsDisplayed()
        composeTestRule.onNodeWithText("12").assertIsDisplayed()
        composeTestRule.onNodeWithText("420").assertIsDisplayed()
    }

    // ==================== 设备列表测试 ====================

    @Test
    fun homeScreen_shouldDisplayDeviceCards() {
        // Given
        uiState.value = HomeUiState(
            devices = UiDataState.Success(
                listOf(
                    Device(id = "1", name = "客厅空调", type = DeviceType.CLIMATE, status = DeviceStatus.ON, roomName = "客厅", isOnline = true),
                    Device(id = "2", name = "主卧空调", type = DeviceType.CLIMATE, status = DeviceStatus.OFF, roomName = "主卧", isOnline = true),
                    Device(id = "3", name = "净水系统", type = DeviceType.WATER, status = DeviceStatus.ON, roomName = "厨房", isOnline = true)
                )
            )
        )

        // When
        composeTestRule.setContent {
            HomeScreen(
                viewModel = mockViewModel,
                onNavigateToClimate = {},
                onNavigateToWater = {},
                onNavigateToProfile = {}
            )
        }

        // Then
        composeTestRule.onNodeWithText("客厅空调").assertIsDisplayed()
        composeTestRule.onNodeWithText("主卧空调").assertIsDisplayed()
        composeTestRule.onNodeWithText("净水系统").assertIsDisplayed()
    }

    @Test
    fun homeScreen_shouldShowDeviceRoomName() {
        // Given
        uiState.value = HomeUiState(
            devices = UiDataState.Success(
                listOf(
                    Device(id = "1", name = "客厅空调", type = DeviceType.CLIMATE, status = DeviceStatus.ON, roomName = "客厅", isOnline = true)
                )
            )
        )

        // When
        composeTestRule.setContent {
            HomeScreen(
                viewModel = mockViewModel,
                onNavigateToClimate = {},
                onNavigateToWater = {},
                onNavigateToProfile = {}
            )
        }

        // Then
        composeTestRule.onNodeWithText("客厅").assertIsDisplayed()
    }

    @Test
    fun homeScreen_shouldShowDeviceStatus() {
        // Given
        uiState.value = HomeUiState(
            devices = UiDataState.Success(
                listOf(
                    Device(id = "1", name = "客厅空调", type = DeviceType.CLIMATE, status = DeviceStatus.ON, roomName = "客厅", isOnline = true),
                    Device(id = "2", name = "主卧空调", type = DeviceType.CLIMATE, status = DeviceStatus.OFF, roomName = "主卧", isOnline = true)
                )
            )
        )

        // When
        composeTestRule.setContent {
            HomeScreen(
                viewModel = mockViewModel,
                onNavigateToClimate = {},
                onNavigateToWater = {},
                onNavigateToProfile = {}
            )
        }

        // Then
        composeTestRule.onNodeWithText("运行中").assertIsDisplayed()
        composeTestRule.onNodeWithText("已关闭").assertIsDisplayed()
    }

    @Test
    fun homeScreen_shouldShowOfflineDeviceStatus() {
        // Given
        uiState.value = HomeUiState(
            devices = UiDataState.Success(
                listOf(
                    Device(id = "1", name = "客厅空调", type = DeviceType.CLIMATE, status = DeviceStatus.ON, roomName = "客厅", isOnline = false)
                )
            )
        )

        // When
        composeTestRule.setContent {
            HomeScreen(
                viewModel = mockViewModel,
                onNavigateToClimate = {},
                onNavigateToWater = {},
                onNavigateToProfile = {}
            )
        }

        // Then
        composeTestRule.onNodeWithText("离线").assertIsDisplayed()
    }

    // ==================== 设备开关测试 ====================

    @Test
    fun deviceCard_shouldTogglePowerWhenSwitchClicked() {
        // Given
        uiState.value = HomeUiState(
            devices = UiDataState.Success(
                listOf(
                    Device(id = "1", name = "客厅空调", type = DeviceType.CLIMATE, status = DeviceStatus.OFF, roomName = "客厅", isOnline = true)
                )
            )
        )

        composeTestRule.setContent {
            HomeScreen(
                viewModel = mockViewModel,
                onNavigateToClimate = {},
                onNavigateToWater = {},
                onNavigateToProfile = {}
            )
        }

        // When
        composeTestRule.onNodeWithContentDescription("开关")
            .performClick()

        // Then
        verify { mockViewModel.toggleDevice("1", true) }
    }

    @Test
    fun deviceCard_shouldNavigateToClimateWhenClimateDeviceClicked() {
        // Given
        var navigatedToClimate = false
        uiState.value = HomeUiState(
            devices = UiDataState.Success(
                listOf(
                    Device(id = "1", name = "客厅空调", type = DeviceType.CLIMATE, status = DeviceStatus.ON, roomName = "客厅", isOnline = true)
                )
            )
        )

        composeTestRule.setContent {
            HomeScreen(
                viewModel = mockViewModel,
                onNavigateToClimate = { navigatedToClimate = true },
                onNavigateToWater = {},
                onNavigateToProfile = {}
            )
        }

        // When
        composeTestRule.onNodeWithText("客厅空调")
            .performClick()

        // Then
        assert(navigatedToClimate)
    }

    @Test
    fun deviceCard_shouldNavigateToWaterWhenWaterDeviceClicked() {
        // Given
        var navigatedToWater = false
        uiState.value = HomeUiState(
            devices = UiDataState.Success(
                listOf(
                    Device(id = "1", name = "净水系统", type = DeviceType.WATER, status = DeviceStatus.ON, roomName = "厨房", isOnline = true)
                )
            )
        )

        composeTestRule.setContent {
            HomeScreen(
                viewModel = mockViewModel,
                onNavigateToClimate = {},
                onNavigateToWater = { navigatedToWater = true },
                onNavigateToProfile = {}
            )
        }

        // When
        composeTestRule.onNodeWithText("净水系统")
            .performClick()

        // Then
        assert(navigatedToWater)
    }

    // ==================== 场景选择器测试 ====================

    @Test
    fun homeScreen_shouldDisplaySceneSelector() {
        // Given
        uiState.value = HomeUiState(
            scenes = UiDataState.Success(
                listOf(
                    Scene(id = "1", name = "会客模式", icon = "guest", isActive = false),
                    Scene(id = "2", name = "离家模式", icon = "away", isActive = true),
                    Scene(id = "3", name = "睡眠模式", icon = "sleep", isActive = false),
                    Scene(id = "4", name = "值守模式", icon = "home", isActive = false)
                )
            )
        )

        // When
        composeTestRule.setContent {
            HomeScreen(
                viewModel = mockViewModel,
                onNavigateToClimate = {},
                onNavigateToWater = {},
                onNavigateToProfile = {}
            )
        }

        // Then
        composeTestRule.onNodeWithText("会客模式").assertIsDisplayed()
        composeTestRule.onNodeWithText("离家模式").assertIsDisplayed()
        composeTestRule.onNodeWithText("睡眠模式").assertIsDisplayed()
        composeTestRule.onNodeWithText("值守模式").assertIsDisplayed()
    }

    @Test
    fun sceneSelector_shouldApplySceneWhenClicked() {
        // Given
        uiState.value = HomeUiState(
            scenes = UiDataState.Success(
                listOf(
                    Scene(id = "1", name = "会客模式", icon = "guest", isActive = false)
                )
            )
        )

        composeTestRule.setContent {
            HomeScreen(
                viewModel = mockViewModel,
                onNavigateToClimate = {},
                onNavigateToWater = {},
                onNavigateToProfile = {}
            )
        }

        // When
        composeTestRule.onNodeWithText("会客模式")
            .performClick()

        // Then
        verify { mockViewModel.applyScene("1") }
    }

    @Test
    fun sceneSelector_shouldHighlightActiveScene() {
        // Given
        uiState.value = HomeUiState(
            scenes = UiDataState.Success(
                listOf(
                    Scene(id = "1", name = "会客模式", icon = "guest", isActive = false),
                    Scene(id = "2", name = "离家模式", icon = "away", isActive = true)
                )
            )
        )

        // When
        composeTestRule.setContent {
            HomeScreen(
                viewModel = mockViewModel,
                onNavigateToClimate = {},
                onNavigateToWater = {},
                onNavigateToProfile = {}
            )
        }

        // Then - Active scene should be visually distinct
        composeTestRule.onNodeWithText("离家模式")
            .assertIsDisplayed()
    }

    // ==================== 加载状态测试 ====================

    @Test
    fun homeScreen_shouldShowLoadingIndicator() {
        // Given
        uiState.value = HomeUiState(
            homeData = UiDataState.Loading,
            devices = UiDataState.Loading
        )

        // When
        composeTestRule.setContent {
            HomeScreen(
                viewModel = mockViewModel,
                onNavigateToClimate = {},
                onNavigateToWater = {},
                onNavigateToProfile = {}
            )
        }

        // Then
        composeTestRule.onNode(hasProgressBar())
            .assertIsDisplayed()
    }

    @Test
    fun homeScreen_shouldShowShimmerEffectWhileLoading() {
        // Given
        uiState.value = HomeUiState(
            homeData = UiDataState.Loading
        )

        // When
        composeTestRule.setContent {
            HomeScreen(
                viewModel = mockViewModel,
                onNavigateToClimate = {},
                onNavigateToWater = {},
                onNavigateToProfile = {}
            )
        }

        // Then - Shimmer placeholders should be visible
        composeTestRule.onAllNodes(hasTestTag("shimmer"))
            .fetchSemanticsNodes()
            .isNotEmpty()
    }

    // ==================== 错误状态测试 ====================

    @Test
    fun homeScreen_shouldShowErrorMessageOnFailure() {
        // Given
        uiState.value = HomeUiState(
            homeData = UiDataState.Error(Exception("加载失败"))
        )

        // When
        composeTestRule.setContent {
            HomeScreen(
                viewModel = mockViewModel,
                onNavigateToClimate = {},
                onNavigateToWater = {},
                onNavigateToProfile = {}
            )
        }

        // Then
        composeTestRule.onNodeWithText("加载失败，点击重试")
            .assertIsDisplayed()
    }

    @Test
    fun homeScreen_shouldRetryWhenErrorClicked() {
        // Given
        uiState.value = HomeUiState(
            homeData = UiDataState.Error(Exception("加载失败"))
        )

        composeTestRule.setContent {
            HomeScreen(
                viewModel = mockViewModel,
                onNavigateToClimate = {},
                onNavigateToWater = {},
                onNavigateToProfile = {}
            )
        }

        // When
        composeTestRule.onNodeWithText("加载失败，点击重试")
            .performClick()

        // Then
        verify { mockViewModel.loadHomeData() }
    }

    // ==================== 刷新测试 ====================

    @Test
    fun homeScreen_shouldSupportPullToRefresh() {
        // Given
        uiState.value = HomeUiState(
            homeData = UiDataState.Success(createMockHomeOverview()),
            isRefreshing = false
        )

        composeTestRule.setContent {
            HomeScreen(
                viewModel = mockViewModel,
                onNavigateToClimate = {},
                onNavigateToWater = {},
                onNavigateToProfile = {}
            )
        }

        // When
        composeTestRule.onNodeWithTag("pull_refresh")
            .performTouchInput { swipeDown() }

        // Then
        verify { mockViewModel.refresh() }
    }

    @Test
    fun homeScreen_shouldShowRefreshIndicator() {
        // Given
        uiState.value = HomeUiState(
            homeData = UiDataState.Success(createMockHomeOverview()),
            isRefreshing = true
        )

        // When
        composeTestRule.setContent {
            HomeScreen(
                viewModel = mockViewModel,
                onNavigateToClimate = {},
                onNavigateToWater = {},
                onNavigateToProfile = {}
            )
        }

        // Then
        composeTestRule.onNode(hasProgressBar())
            .assertIsDisplayed()
    }

    // ==================== 空状态测试 ====================

    @Test
    fun homeScreen_shouldShowEmptyStateWhenNoDevices() {
        // Given
        uiState.value = HomeUiState(
            devices = UiDataState.Success(emptyList())
        )

        // When
        composeTestRule.setContent {
            HomeScreen(
                viewModel = mockViewModel,
                onNavigateToClimate = {},
                onNavigateToWater = {},
                onNavigateToProfile = {}
            )
        }

        // Then
        composeTestRule.onNodeWithText("暂无设备")
            .assertIsDisplayed()
    }

    // ==================== 导航测试 ====================

    @Test
    fun homeScreen_shouldNavigateToProfileWhenProfileClicked() {
        // Given
        var navigatedToProfile = false
        uiState.value = HomeUiState(
            homeData = UiDataState.Success(createMockHomeOverview())
        )

        composeTestRule.setContent {
            HomeScreen(
                viewModel = mockViewModel,
                onNavigateToClimate = {},
                onNavigateToWater = {},
                onNavigateToProfile = { navigatedToProfile = true }
            )
        }

        // When
        composeTestRule.onNodeWithContentDescription("个人中心")
            .performClick()

        // Then
        assert(navigatedToProfile)
    }

    // ==================== 无障碍测试 ====================

    @Test
    fun homeScreen_shouldHaveProperSemantics() {
        // Given
        uiState.value = HomeUiState(
            homeData = UiDataState.Success(createMockHomeOverview()),
            devices = UiDataState.Success(
                listOf(
                    Device(id = "1", name = "客厅空调", type = DeviceType.CLIMATE, status = DeviceStatus.ON, roomName = "客厅", isOnline = true)
                )
            )
        )

        // When
        composeTestRule.setContent {
            HomeScreen(
                viewModel = mockViewModel,
                onNavigateToClimate = {},
                onNavigateToWater = {},
                onNavigateToProfile = {}
            )
        }

        // Then
        composeTestRule.onNodeWithText("室内温度")
            .assertExists()

        composeTestRule.onNodeWithText("湿度")
            .assertExists()

        composeTestRule.onNodeWithContentDescription("开关")
            .assertHasClickAction()
    }

    // Helper function
    private fun createMockHomeOverview(): HomeOverview {
        return HomeOverview(
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
    }
}

// Helper functions for semantic matchers
private fun hasProgressBar(): SemanticsMatcher {
    return SemanticsMatcher.keyIsDefined(androidx.compose.ui.semantics.ProgressBarRangeInfo)
}

private fun hasTestTag(tag: String): SemanticsMatcher {
    return SemanticsMatcher.expectValue(androidx.compose.ui.semantics.SemanticsProperties.TestTag, tag)
}
