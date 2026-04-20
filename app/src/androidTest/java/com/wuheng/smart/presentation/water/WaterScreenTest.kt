package com.wuheng.smart.presentation.water

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.wuheng.smart.data.model.*
import com.wuheng.smart.data.repository.WaterRepository
import com.wuheng.smart.presentation.base.UiDataState
import io.mockk.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * 水系统页面 UI 测试
 *
 * 测试范围:
 * 1. 页面元素显示验证
 * 2. 水温显示与控制
 * 3. 水流量/压力显示
 * 4. 模式切换
 * 5. 耗材进度
 * 6. 加载和错误状态
 */
@RunWith(AndroidJUnit4::class)
class WaterScreenTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    private lateinit var mockViewModel: WaterViewModel
    private val uiState = MutableStateFlow(WaterUiState())

    @Before
    fun setUp() {
        mockViewModel = mockk(relaxed = true)
        every { mockViewModel.uiState } returns uiState.asStateFlow()
    }

    // ==================== 页面元素显示测试 ====================

    @Test
    fun waterScreen_shouldDisplayHeaderElements() {
        // Given
        uiState.value = WaterUiState(
            overview = UiDataState.Success(
                WaterSystemOverview(
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
            )
        )

        // When
        composeTestRule.setContent {
            WaterScreen(
                viewModel = mockViewModel,
                onNavigateBack = {},
                onNavigateToConsumables = {}
            )
        }

        // Then
        composeTestRule.onNodeWithText("水系统").assertIsDisplayed()
        composeTestRule.onNodeWithText("45.0°C").assertIsDisplayed()
    }

    @Test
    fun waterScreen_shouldDisplayCurrentMode() {
        // Given
        uiState.value = WaterUiState(
            overview = UiDataState.Success(
                WaterSystemOverview(
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
            )
        )

        // When
        composeTestRule.setContent {
            WaterScreen(
                viewModel = mockViewModel,
                onNavigateBack = {},
                onNavigateToConsumables = {}
            )
        }

        // Then
        composeTestRule.onNodeWithText("舒适模式").assertIsDisplayed()
    }

    @Test
    fun waterScreen_shouldDisplayEcoMode() {
        // Given
        uiState.value = WaterUiState(
            overview = UiDataState.Success(
                WaterSystemOverview(
                    currentTemperature = 42.0,
                    targetTemperature = 42.0,
                    currentMode = WaterMode.ECO,
                    isRunning = true,
                    inletTemperature = 15.5,
                    waterFlow = 2.5,
                    waterPressure = 0.35,
                    totalWaterFlow = 1250.5,
                    filterStatus = FilterStatus.GOOD
                )
            )
        )

        // When
        composeTestRule.setContent {
            WaterScreen(
                viewModel = mockViewModel,
                onNavigateBack = {},
                onNavigateToConsumables = {}
            )
        }

        // Then
        composeTestRule.onNodeWithText("节能模式").assertIsDisplayed()
    }

    // ==================== 水温控制测试 ====================

    @Test
    fun waterScreen_shouldDisplayTargetTemperature() {
        // Given
        uiState.value = WaterUiState(
            overview = UiDataState.Success(
                WaterSystemOverview(
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
            )
        )

        // When
        composeTestRule.setContent {
            WaterScreen(
                viewModel = mockViewModel,
                onNavigateBack = {},
                onNavigateToConsumables = {}
            )
        }

        // Then
        composeTestRule.onNodeWithText("目标水温").assertIsDisplayed()
        composeTestRule.onNodeWithText("45°C").assertIsDisplayed()
    }

    @Test
    fun waterScreen_shouldIncreaseTemperatureWhenPlusClicked() {
        // Given
        uiState.value = WaterUiState(
            overview = UiDataState.Success(
                WaterSystemOverview(
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
            )
        )

        composeTestRule.setContent {
            WaterScreen(
                viewModel = mockViewModel,
                onNavigateBack = {},
                onNavigateToConsumables = {}
            )
        }

        // When
        composeTestRule.onNodeWithContentDescription("增加水温")
            .performClick()

        // Then
        verify { mockViewModel.increaseTargetTemperature() }
    }

    @Test
    fun waterScreen_shouldDecreaseTemperatureWhenMinusClicked() {
        // Given
        uiState.value = WaterUiState(
            overview = UiDataState.Success(
                WaterSystemOverview(
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
            )
        )

        composeTestRule.setContent {
            WaterScreen(
                viewModel = mockViewModel,
                onNavigateBack = {},
                onNavigateToConsumables = {}
            )
        }

        // When
        composeTestRule.onNodeWithContentDescription("降低水温")
            .performClick()

        // Then
        verify { mockViewModel.decreaseTargetTemperature() }
    }

    // ==================== 水流量/压力显示测试 ====================

    @Test
    fun waterScreen_shouldDisplayWaterFlow() {
        // Given
        uiState.value = WaterUiState(
            overview = UiDataState.Success(
                WaterSystemOverview(
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
            )
        )

        // When
        composeTestRule.setContent {
            WaterScreen(
                viewModel = mockViewModel,
                onNavigateBack = {},
                onNavigateToConsumables = {}
            )
        }

        // Then
        composeTestRule.onNodeWithText("水流量").assertIsDisplayed()
        composeTestRule.onNodeWithText("2.5 L/min").assertIsDisplayed()
    }

    @Test
    fun waterScreen_shouldDisplayWaterPressure() {
        // Given
        uiState.value = WaterUiState(
            overview = UiDataState.Success(
                WaterSystemOverview(
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
            )
        )

        // When
        composeTestRule.setContent {
            WaterScreen(
                viewModel = mockViewModel,
                onNavigateBack = {},
                onNavigateToConsumables = {}
            )
        }

        // Then
        composeTestRule.onNodeWithText("水压").assertIsDisplayed()
        composeTestRule.onNodeWithText("0.35 MPa").assertIsDisplayed()
    }

    @Test
    fun waterScreen_shouldDisplayTotalWaterFlow() {
        // Given
        uiState.value = WaterUiState(
            overview = UiDataState.Success(
                WaterSystemOverview(
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
            )
        )

        // When
        composeTestRule.setContent {
            WaterScreen(
                viewModel = mockViewModel,
                onNavigateBack = {},
                onNavigateToConsumables = {}
            )
        }

        // Then
        composeTestRule.onNodeWithText("累计用水").assertIsDisplayed()
        composeTestRule.onNodeWithText("1250.5 L").assertIsDisplayed()
    }

    @Test
    fun waterScreen_shouldDisplayInletTemperature() {
        // Given
        uiState.value = WaterUiState(
            overview = UiDataState.Success(
                WaterSystemOverview(
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
            )
        )

        // When
        composeTestRule.setContent {
            WaterScreen(
                viewModel = mockViewModel,
                onNavigateBack = {},
                onNavigateToConsumables = {}
            )
        }

        // Then
        composeTestRule.onNodeWithText("进水温度").assertIsDisplayed()
        composeTestRule.onNodeWithText("15.5°C").assertIsDisplayed()
    }

    // ==================== 模式切换测试 ====================

    @Test
    fun waterScreen_shouldDisplayModeSelector() {
        // Given
        uiState.value = WaterUiState(
            overview = UiDataState.Success(
                WaterSystemOverview(
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
            )
        )

        // When
        composeTestRule.setContent {
            WaterScreen(
                viewModel = mockViewModel,
                onNavigateBack = {},
                onNavigateToConsumables = {}
            )
        }

        // Then
        composeTestRule.onNodeWithText("舒适").assertIsDisplayed()
        composeTestRule.onNodeWithText("节能").assertIsDisplayed()
        composeTestRule.onNodeWithText("离家").assertIsDisplayed()
    }

    @Test
    fun waterScreen_shouldSwitchModeWhenModeClicked() {
        // Given
        uiState.value = WaterUiState(
            overview = UiDataState.Success(
                WaterSystemOverview(
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
            )
        )

        composeTestRule.setContent {
            WaterScreen(
                viewModel = mockViewModel,
                onNavigateBack = {},
                onNavigateToConsumables = {}
            )
        }

        // When
        composeTestRule.onNodeWithText("节能")
            .performClick()

        // Then
        verify { mockViewModel.setMode(WaterMode.ECO) }
    }

    @Test
    fun waterScreen_shouldSwitchToAwayMode() {
        // Given
        uiState.value = WaterUiState(
            overview = UiDataState.Success(
                WaterSystemOverview(
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
            )
        )

        composeTestRule.setContent {
            WaterScreen(
                viewModel = mockViewModel,
                onNavigateBack = {},
                onNavigateToConsumables = {}
            )
        }

        // When
        composeTestRule.onNodeWithText("离家")
            .performClick()

        // Then
        verify { mockViewModel.setMode(WaterMode.AWAY) }
    }

    // ==================== 电源控制测试 ====================

    @Test
    fun waterScreen_shouldDisplayPowerButton() {
        // Given
        uiState.value = WaterUiState(
            overview = UiDataState.Success(
                WaterSystemOverview(
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
            )
        )

        // When
        composeTestRule.setContent {
            WaterScreen(
                viewModel = mockViewModel,
                onNavigateBack = {},
                onNavigateToConsumables = {}
            )
        }

        // Then
        composeTestRule.onNodeWithContentDescription("电源开关")
            .assertIsDisplayed()
    }

    @Test
    fun waterScreen_shouldTogglePowerWhenPowerButtonClicked() {
        // Given
        uiState.value = WaterUiState(
            overview = UiDataState.Success(
                WaterSystemOverview(
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
            )
        )

        composeTestRule.setContent {
            WaterScreen(
                viewModel = mockViewModel,
                onNavigateBack = {},
                onNavigateToConsumables = {}
            )
        }

        // When
        composeTestRule.onNodeWithContentDescription("电源开关")
            .performClick()

        // Then
        verify { mockViewModel.togglePower() }
    }

    @Test
    fun waterScreen_shouldShowOffStateWhenSystemOff() {
        // Given
        uiState.value = WaterUiState(
            overview = UiDataState.Success(
                WaterSystemOverview(
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
            )
        )

        // When
        composeTestRule.setContent {
            WaterScreen(
                viewModel = mockViewModel,
                onNavigateBack = {},
                onNavigateToConsumables = {}
            )
        }

        // Then
        composeTestRule.onNodeWithText("已关闭").assertIsDisplayed()
    }

    // ==================== 耗材状态测试 ====================

    @Test
    fun waterScreen_shouldDisplayFilterStatus() {
        // Given
        uiState.value = WaterUiState(
            overview = UiDataState.Success(
                WaterSystemOverview(
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
            )
        )

        // When
        composeTestRule.setContent {
            WaterScreen(
                viewModel = mockViewModel,
                onNavigateBack = {},
                onNavigateToConsumables = {}
            )
        }

        // Then
        composeTestRule.onNodeWithText("滤芯状态").assertIsDisplayed()
        composeTestRule.onNodeWithText("良好").assertIsDisplayed()
    }

    @Test
    fun waterScreen_shouldShowWarningFilterStatus() {
        // Given
        uiState.value = WaterUiState(
            overview = UiDataState.Success(
                WaterSystemOverview(
                    currentTemperature = 45.0,
                    targetTemperature = 45.0,
                    currentMode = WaterMode.COMFORT,
                    isRunning = true,
                    inletTemperature = 15.5,
                    waterFlow = 2.5,
                    waterPressure = 0.35,
                    totalWaterFlow = 1250.5,
                    filterStatus = FilterStatus.WARNING
                )
            )
        )

        // When
        composeTestRule.setContent {
            WaterScreen(
                viewModel = mockViewModel,
                onNavigateBack = {},
                onNavigateToConsumables = {}
            )
        }

        // Then
        composeTestRule.onNodeWithText("需更换").assertIsDisplayed()
    }

    @Test
    fun waterScreen_shouldNavigateToConsumablesWhenFilterClicked() {
        // Given
        var navigatedToConsumables = false
        uiState.value = WaterUiState(
            overview = UiDataState.Success(
                WaterSystemOverview(
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
            )
        )

        composeTestRule.setContent {
            WaterScreen(
                viewModel = mockViewModel,
                onNavigateBack = {},
                onNavigateToConsumables = { navigatedToConsumables = true }
            )
        }

        // When
        composeTestRule.onNodeWithText("滤芯状态")
            .performClick()

        // Then
        assert(navigatedToConsumables)
    }

    // ==================== 加载状态测试 ====================

    @Test
    fun waterScreen_shouldShowLoadingIndicator() {
        // Given
        uiState.value = WaterUiState(
            overview = UiDataState.Loading
        )

        // When
        composeTestRule.setContent {
            WaterScreen(
                viewModel = mockViewModel,
                onNavigateBack = {},
                onNavigateToConsumables = {}
            )
        }

        // Then
        composeTestRule.onNode(hasProgressBar())
            .assertIsDisplayed()
    }

    @Test
    fun waterScreen_shouldShowShimmerWhileLoading() {
        // Given
        uiState.value = WaterUiState(
            overview = UiDataState.Loading
        )

        // When
        composeTestRule.setContent {
            WaterScreen(
                viewModel = mockViewModel,
                onNavigateBack = {},
                onNavigateToConsumables = {}
            )
        }

        // Then
        composeTestRule.onAllNodes(hasTestTag("shimmer"))
            .fetchSemanticsNodes()
            .isNotEmpty()
    }

    // ==================== 错误状态测试 ====================

    @Test
    fun waterScreen_shouldShowErrorMessage() {
        // Given
        uiState.value = WaterUiState(
            overview = UiDataState.Error(Exception("加载失败"))
        )

        // When
        composeTestRule.setContent {
            WaterScreen(
                viewModel = mockViewModel,
                onNavigateBack = {},
                onNavigateToConsumables = {}
            )
        }

        // Then
        composeTestRule.onNodeWithText("加载失败，点击重试")
            .assertIsDisplayed()
    }

    @Test
    fun waterScreen_shouldRetryWhenErrorClicked() {
        // Given
        uiState.value = WaterUiState(
            overview = UiDataState.Error(Exception("加载失败"))
        )

        composeTestRule.setContent {
            WaterScreen(
                viewModel = mockViewModel,
                onNavigateBack = {},
                onNavigateToConsumables = {}
            )
        }

        // When
        composeTestRule.onNodeWithText("加载失败，点击重试")
            .performClick()

        // Then
        verify { mockViewModel.loadWaterData() }
    }

    // ==================== 刷新测试 ====================

    @Test
    fun waterScreen_shouldSupportPullToRefresh() {
        // Given
        uiState.value = WaterUiState(
            overview = UiDataState.Success(
                WaterSystemOverview(
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
            ),
            isRefreshing = false
        )

        composeTestRule.setContent {
            WaterScreen(
                viewModel = mockViewModel,
                onNavigateBack = {},
                onNavigateToConsumables = {}
            )
        }

        // When
        composeTestRule.onNodeWithTag("pull_refresh")
            .performTouchInput { swipeDown() }

        // Then
        verify { mockViewModel.refresh() }
    }

    // ==================== 导航测试 ====================

    @Test
    fun waterScreen_shouldNavigateBackWhenBackClicked() {
        // Given
        var navigatedBack = false
        uiState.value = WaterUiState(
            overview = UiDataState.Success(
                WaterSystemOverview(
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
            )
        )

        composeTestRule.setContent {
            WaterScreen(
                viewModel = mockViewModel,
                onNavigateBack = { navigatedBack = true },
                onNavigateToConsumables = {}
            )
        }

        // When
        composeTestRule.onNodeWithContentDescription("返回")
            .performClick()

        // Then
        assert(navigatedBack)
    }

    // ==================== 无障碍测试 ====================

    @Test
    fun waterScreen_shouldHaveProperSemantics() {
        // Given
        uiState.value = WaterUiState(
            overview = UiDataState.Success(
                WaterSystemOverview(
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
            )
        )

        // When
        composeTestRule.setContent {
            WaterScreen(
                viewModel = mockViewModel,
                onNavigateBack = {},
                onNavigateToConsumables = {}
            )
        }

        // Then
        composeTestRule.onNodeWithContentDescription("返回")
            .assertHasClickAction()

        composeTestRule.onNodeWithContentDescription("增加水温")
            .assertHasClickAction()

        composeTestRule.onNodeWithContentDescription("降低水温")
            .assertHasClickAction()

        composeTestRule.onNodeWithContentDescription("电源开关")
            .assertHasClickAction()
    }
}

// Helper functions for semantic matchers
private fun hasProgressBar(): SemanticsMatcher {
    return SemanticsMatcher.keyIsDefined(androidx.compose.ui.semantics.ProgressBarRangeInfo)
}

private fun hasTestTag(tag: String): SemanticsMatcher {
    return SemanticsMatcher.expectValue(androidx.compose.ui.semantics.SemanticsProperties.TestTag, tag)
}
