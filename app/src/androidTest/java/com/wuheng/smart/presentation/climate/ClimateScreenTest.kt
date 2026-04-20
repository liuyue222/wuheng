package com.wuheng.smart.presentation.climate

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.wuheng.smart.data.model.*
import com.wuheng.smart.data.repository.ClimateRepository
import com.wuheng.smart.presentation.base.UiDataState
import io.mockk.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * 冷暖系统页面 UI 测试
 *
 * 测试范围:
 * 1. 页面元素显示验证
 * 2. 温度显示与控制
 * 3. 模式切换
 * 4. 楼层/区域列表
 * 5. 区域控制
 * 6. 加载和错误状态
 */
@RunWith(AndroidJUnit4::class)
class ClimateScreenTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    private lateinit var mockViewModel: ClimateViewModel
    private val uiState = MutableStateFlow(ClimateUiState())

    @Before
    fun setUp() {
        mockViewModel = mockk(relaxed = true)
        every { mockViewModel.uiState } returns uiState.asStateFlow()
    }

    // ==================== 页面元素显示测试 ====================

    @Test
    fun climateScreen_shouldDisplayHeaderElements() {
        // Given
        uiState.value = ClimateUiState(
            overview = UiDataState.Success(
                ClimateOverview(
                    currentTemperature = 24.5,
                    targetTemperature = 24.0,
                    currentMode = ClimateMode.COOLING,
                    isRunning = true,
                    floorCount = 3,
                    zoneCount = 8,
                    runningZoneCount = 6,
                    averageHumidity = 50
                )
            )
        )

        // When
        composeTestRule.setContent {
            ClimateScreen(
                viewModel = mockViewModel,
                onNavigateBack = {}
            )
        }

        // Then
        composeTestRule.onNodeWithText("冷暖系统").assertIsDisplayed()
        composeTestRule.onNodeWithText("24.5°C").assertIsDisplayed()
    }

    @Test
    fun climateScreen_shouldDisplayCurrentMode() {
        // Given
        uiState.value = ClimateUiState(
            overview = UiDataState.Success(
                ClimateOverview(
                    currentTemperature = 24.5,
                    targetTemperature = 24.0,
                    currentMode = ClimateMode.COOLING,
                    isRunning = true,
                    floorCount = 3,
                    zoneCount = 8,
                    runningZoneCount = 6
                )
            )
        )

        // When
        composeTestRule.setContent {
            ClimateScreen(
                viewModel = mockViewModel,
                onNavigateBack = {}
            )
        }

        // Then
        composeTestRule.onNodeWithText("制冷模式").assertIsDisplayed()
    }

    @Test
    fun climateScreen_shouldDisplayHeatingMode() {
        // Given
        uiState.value = ClimateUiState(
            overview = UiDataState.Success(
                ClimateOverview(
                    currentTemperature = 26.0,
                    targetTemperature = 26.0,
                    currentMode = ClimateMode.HEATING,
                    isRunning = true,
                    floorCount = 3,
                    zoneCount = 8,
                    runningZoneCount = 6
                )
            )
        )

        // When
        composeTestRule.setContent {
            ClimateScreen(
                viewModel = mockViewModel,
                onNavigateBack = {}
            )
        }

        // Then
        composeTestRule.onNodeWithText("制热模式").assertIsDisplayed()
    }

    // ==================== 温度控制测试 ====================

    @Test
    fun climateScreen_shouldDisplayTargetTemperature() {
        // Given
        uiState.value = ClimateUiState(
            overview = UiDataState.Success(
                ClimateOverview(
                    currentTemperature = 24.5,
                    targetTemperature = 24.0,
                    currentMode = ClimateMode.COOLING,
                    isRunning = true,
                    floorCount = 3,
                    zoneCount = 8,
                    runningZoneCount = 6
                )
            )
        )

        // When
        composeTestRule.setContent {
            ClimateScreen(
                viewModel = mockViewModel,
                onNavigateBack = {}
            )
        }

        // Then
        composeTestRule.onNodeWithText("目标温度").assertIsDisplayed()
        composeTestRule.onNodeWithText("24.0°C").assertIsDisplayed()
    }

    @Test
    fun climateScreen_shouldIncreaseTemperatureWhenPlusClicked() {
        // Given
        uiState.value = ClimateUiState(
            overview = UiDataState.Success(
                ClimateOverview(
                    currentTemperature = 24.5,
                    targetTemperature = 24.0,
                    currentMode = ClimateMode.COOLING,
                    isRunning = true,
                    floorCount = 3,
                    zoneCount = 8,
                    runningZoneCount = 6
                )
            )
        )

        composeTestRule.setContent {
            ClimateScreen(
                viewModel = mockViewModel,
                onNavigateBack = {}
            )
        }

        // When
        composeTestRule.onNodeWithContentDescription("增加温度")
            .performClick()

        // Then
        verify { mockViewModel.increaseTargetTemperature() }
    }

    @Test
    fun climateScreen_shouldDecreaseTemperatureWhenMinusClicked() {
        // Given
        uiState.value = ClimateUiState(
            overview = UiDataState.Success(
                ClimateOverview(
                    currentTemperature = 24.5,
                    targetTemperature = 24.0,
                    currentMode = ClimateMode.COOLING,
                    isRunning = true,
                    floorCount = 3,
                    zoneCount = 8,
                    runningZoneCount = 6
                )
            )
        )

        composeTestRule.setContent {
            ClimateScreen(
                viewModel = mockViewModel,
                onNavigateBack = {}
            )
        }

        // When
        composeTestRule.onNodeWithContentDescription("降低温度")
            .performClick()

        // Then
        verify { mockViewModel.decreaseTargetTemperature() }
    }

    // ==================== 模式切换测试 ====================

    @Test
    fun climateScreen_shouldDisplayModeSelector() {
        // Given
        uiState.value = ClimateUiState(
            overview = UiDataState.Success(
                ClimateOverview(
                    currentTemperature = 24.5,
                    targetTemperature = 24.0,
                    currentMode = ClimateMode.COOLING,
                    isRunning = true,
                    floorCount = 3,
                    zoneCount = 8,
                    runningZoneCount = 6
                )
            )
        )

        // When
        composeTestRule.setContent {
            ClimateScreen(
                viewModel = mockViewModel,
                onNavigateBack = {}
            )
        }

        // Then
        composeTestRule.onNodeWithText("制冷").assertIsDisplayed()
        composeTestRule.onNodeWithText("制热").assertIsDisplayed()
        composeTestRule.onNodeWithText("通风").assertIsDisplayed()
        composeTestRule.onNodeWithText("自动").assertIsDisplayed()
    }

    @Test
    fun climateScreen_shouldSwitchModeWhenModeClicked() {
        // Given
        uiState.value = ClimateUiState(
            overview = UiDataState.Success(
                ClimateOverview(
                    currentTemperature = 24.5,
                    targetTemperature = 24.0,
                    currentMode = ClimateMode.COOLING,
                    isRunning = true,
                    floorCount = 3,
                    zoneCount = 8,
                    runningZoneCount = 6
                )
            )
        )

        composeTestRule.setContent {
            ClimateScreen(
                viewModel = mockViewModel,
                onNavigateBack = {}
            )
        }

        // When
        composeTestRule.onNodeWithText("制热")
            .performClick()

        // Then
        verify { mockViewModel.setMode(ClimateMode.HEATING) }
    }

    @Test
    fun climateScreen_shouldSwitchToVentilationMode() {
        // Given
        uiState.value = ClimateUiState(
            overview = UiDataState.Success(
                ClimateOverview(
                    currentTemperature = 24.5,
                    targetTemperature = 24.0,
                    currentMode = ClimateMode.COOLING,
                    isRunning = true,
                    floorCount = 3,
                    zoneCount = 8,
                    runningZoneCount = 6
                )
            )
        )

        composeTestRule.setContent {
            ClimateScreen(
                viewModel = mockViewModel,
                onNavigateBack = {}
            )
        }

        // When
        composeTestRule.onNodeWithText("通风")
            .performClick()

        // Then
        verify { mockViewModel.setMode(ClimateMode.VENTILATION) }
    }

    // ==================== 电源控制测试 ====================

    @Test
    fun climateScreen_shouldDisplayPowerButton() {
        // Given
        uiState.value = ClimateUiState(
            overview = UiDataState.Success(
                ClimateOverview(
                    currentTemperature = 24.5,
                    targetTemperature = 24.0,
                    currentMode = ClimateMode.COOLING,
                    isRunning = true,
                    floorCount = 3,
                    zoneCount = 8,
                    runningZoneCount = 6
                )
            )
        )

        // When
        composeTestRule.setContent {
            ClimateScreen(
                viewModel = mockViewModel,
                onNavigateBack = {}
            )
        }

        // Then
        composeTestRule.onNodeWithContentDescription("电源开关")
            .assertIsDisplayed()
    }

    @Test
    fun climateScreen_shouldTogglePowerWhenPowerButtonClicked() {
        // Given
        uiState.value = ClimateUiState(
            overview = UiDataState.Success(
                ClimateOverview(
                    currentTemperature = 24.5,
                    targetTemperature = 24.0,
                    currentMode = ClimateMode.COOLING,
                    isRunning = true,
                    floorCount = 3,
                    zoneCount = 8,
                    runningZoneCount = 6
                )
            )
        )

        composeTestRule.setContent {
            ClimateScreen(
                viewModel = mockViewModel,
                onNavigateBack = {}
            )
        }

        // When
        composeTestRule.onNodeWithContentDescription("电源开关")
            .performClick()

        // Then
        verify { mockViewModel.togglePower() }
    }

    @Test
    fun climateScreen_shouldShowOffStateWhenSystemOff() {
        // Given
        uiState.value = ClimateUiState(
            overview = UiDataState.Success(
                ClimateOverview(
                    currentTemperature = 22.0,
                    targetTemperature = 24.0,
                    currentMode = ClimateMode.OFF,
                    isRunning = false,
                    floorCount = 3,
                    zoneCount = 8,
                    runningZoneCount = 0
                )
            )
        )

        // When
        composeTestRule.setContent {
            ClimateScreen(
                viewModel = mockViewModel,
                onNavigateBack = {}
            )
        }

        // Then
        composeTestRule.onNodeWithText("已关闭").assertIsDisplayed()
    }

    // ==================== 楼层列表测试 ====================

    @Test
    fun climateScreen_shouldDisplayFloorList() {
        // Given
        uiState.value = ClimateUiState(
            floors = UiDataState.Success(
                listOf(
                    Floor(id = "1", name = "一楼", order = 1, zoneCount = 3, runningZoneCount = 2, averageTemperature = 24.0),
                    Floor(id = "2", name = "二楼", order = 2, zoneCount = 3, runningZoneCount = 2, averageTemperature = 24.5),
                    Floor(id = "b1", name = "地下室", order = 0, zoneCount = 2, runningZoneCount = 1, averageTemperature = 22.0)
                )
            )
        )

        // When
        composeTestRule.setContent {
            ClimateScreen(
                viewModel = mockViewModel,
                onNavigateBack = {}
            )
        }

        // Then
        composeTestRule.onNodeWithText("一楼").assertIsDisplayed()
        composeTestRule.onNodeWithText("二楼").assertIsDisplayed()
        composeTestRule.onNodeWithText("地下室").assertIsDisplayed()
    }

    @Test
    fun climateScreen_shouldSelectFloorWhenClicked() {
        // Given
        uiState.value = ClimateUiState(
            floors = UiDataState.Success(
                listOf(
                    Floor(id = "1", name = "一楼", order = 1, zoneCount = 3, runningZoneCount = 2, averageTemperature = 24.0),
                    Floor(id = "2", name = "二楼", order = 2, zoneCount = 3, runningZoneCount = 2, averageTemperature = 24.5)
                )
            ),
            selectedFloorId = "1"
        )

        composeTestRule.setContent {
            ClimateScreen(
                viewModel = mockViewModel,
                onNavigateBack = {}
            )
        }

        // When
        composeTestRule.onNodeWithText("二楼")
            .performClick()

        // Then
        verify { mockViewModel.selectFloor("2") }
    }

    // ==================== 区域列表测试 ====================

    @Test
    fun climateScreen_shouldDisplayZoneList() {
        // Given
        uiState.value = ClimateUiState(
            selectedFloorId = "1",
            zones = UiDataState.Success(
                listOf(
                    Zone(id = "z1", name = "客厅", floorId = "1", currentTemperature = 24.0, targetTemperature = 24.0, mode = ClimateMode.COOLING, isRunning = true, isOnline = true),
                    Zone(id = "z2", name = "主卧", floorId = "1", currentTemperature = 24.5, targetTemperature = 24.0, mode = ClimateMode.COOLING, isRunning = true, isOnline = true),
                    Zone(id = "z3", name = "厨房", floorId = "1", currentTemperature = 25.0, targetTemperature = 24.0, mode = ClimateMode.COOLING, isRunning = false, isOnline = true)
                )
            )
        )

        // When
        composeTestRule.setContent {
            ClimateScreen(
                viewModel = mockViewModel,
                onNavigateBack = {}
            )
        }

        // Then
        composeTestRule.onNodeWithText("客厅").assertIsDisplayed()
        composeTestRule.onNodeWithText("主卧").assertIsDisplayed()
        composeTestRule.onNodeWithText("厨房").assertIsDisplayed()
    }

    @Test
    fun climateScreen_shouldDisplayZoneTemperature() {
        // Given
        uiState.value = ClimateUiState(
            selectedFloorId = "1",
            zones = UiDataState.Success(
                listOf(
                    Zone(id = "z1", name = "客厅", floorId = "1", currentTemperature = 24.0, targetTemperature = 24.0, mode = ClimateMode.COOLING, isRunning = true, isOnline = true)
                )
            )
        )

        // When
        composeTestRule.setContent {
            ClimateScreen(
                viewModel = mockViewModel,
                onNavigateBack = {}
            )
        }

        // Then
        composeTestRule.onNodeWithText("24.0°C").assertIsDisplayed()
    }

    @Test
    fun climateScreen_shouldToggleZonePower() {
        // Given
        uiState.value = ClimateUiState(
            selectedFloorId = "1",
            zones = UiDataState.Success(
                listOf(
                    Zone(id = "z1", name = "客厅", floorId = "1", currentTemperature = 24.0, targetTemperature = 24.0, mode = ClimateMode.COOLING, isRunning = false, isOnline = true)
                )
            )
        )

        composeTestRule.setContent {
            ClimateScreen(
                viewModel = mockViewModel,
                onNavigateBack = {}
            )
        }

        // When
        composeTestRule.onNodeWithContentDescription("区域开关")
            .performClick()

        // Then
        verify { mockViewModel.toggleZonePower("z1", true) }
    }

    @Test
    fun climateScreen_shouldShowOfflineZone() {
        // Given
        uiState.value = ClimateUiState(
            selectedFloorId = "1",
            zones = UiDataState.Success(
                listOf(
                    Zone(id = "z1", name = "客厅", floorId = "1", currentTemperature = 24.0, targetTemperature = 24.0, mode = ClimateMode.COOLING, isRunning = false, isOnline = false)
                )
            )
        )

        // When
        composeTestRule.setContent {
            ClimateScreen(
                viewModel = mockViewModel,
                onNavigateBack = {}
            )
        }

        // Then
        composeTestRule.onNodeWithText("离线").assertIsDisplayed()
    }

    // ==================== 加载状态测试 ====================

    @Test
    fun climateScreen_shouldShowLoadingIndicator() {
        // Given
        uiState.value = ClimateUiState(
            overview = UiDataState.Loading
        )

        // When
        composeTestRule.setContent {
            ClimateScreen(
                viewModel = mockViewModel,
                onNavigateBack = {}
            )
        }

        // Then
        composeTestRule.onNode(hasProgressBar())
            .assertIsDisplayed()
    }

    @Test
    fun climateScreen_shouldShowShimmerWhileLoading() {
        // Given
        uiState.value = ClimateUiState(
            overview = UiDataState.Loading
        )

        // When
        composeTestRule.setContent {
            ClimateScreen(
                viewModel = mockViewModel,
                onNavigateBack = {}
            )
        }

        // Then
        composeTestRule.onAllNodes(hasTestTag("shimmer"))
            .fetchSemanticsNodes()
            .isNotEmpty()
    }

    // ==================== 错误状态测试 ====================

    @Test
    fun climateScreen_shouldShowErrorMessage() {
        // Given
        uiState.value = ClimateUiState(
            overview = UiDataState.Error(Exception("加载失败"))
        )

        // When
        composeTestRule.setContent {
            ClimateScreen(
                viewModel = mockViewModel,
                onNavigateBack = {}
            )
        }

        // Then
        composeTestRule.onNodeWithText("加载失败，点击重试")
            .assertIsDisplayed()
    }

    @Test
    fun climateScreen_shouldRetryWhenErrorClicked() {
        // Given
        uiState.value = ClimateUiState(
            overview = UiDataState.Error(Exception("加载失败"))
        )

        composeTestRule.setContent {
            ClimateScreen(
                viewModel = mockViewModel,
                onNavigateBack = {}
            )
        }

        // When
        composeTestRule.onNodeWithText("加载失败，点击重试")
            .performClick()

        // Then
        verify { mockViewModel.loadClimateData() }
    }

    // ==================== 刷新测试 ====================

    @Test
    fun climateScreen_shouldSupportPullToRefresh() {
        // Given
        uiState.value = ClimateUiState(
            overview = UiDataState.Success(
                ClimateOverview(
                    currentTemperature = 24.5,
                    targetTemperature = 24.0,
                    currentMode = ClimateMode.COOLING,
                    isRunning = true,
                    floorCount = 3,
                    zoneCount = 8,
                    runningZoneCount = 6
                )
            ),
            isRefreshing = false
        )

        composeTestRule.setContent {
            ClimateScreen(
                viewModel = mockViewModel,
                onNavigateBack = {}
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
    fun climateScreen_shouldNavigateBackWhenBackClicked() {
        // Given
        var navigatedBack = false
        uiState.value = ClimateUiState(
            overview = UiDataState.Success(
                ClimateOverview(
                    currentTemperature = 24.5,
                    targetTemperature = 24.0,
                    currentMode = ClimateMode.COOLING,
                    isRunning = true,
                    floorCount = 3,
                    zoneCount = 8,
                    runningZoneCount = 6
                )
            )
        )

        composeTestRule.setContent {
            ClimateScreen(
                viewModel = mockViewModel,
                onNavigateBack = { navigatedBack = true }
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
    fun climateScreen_shouldHaveProperSemantics() {
        // Given
        uiState.value = ClimateUiState(
            overview = UiDataState.Success(
                ClimateOverview(
                    currentTemperature = 24.5,
                    targetTemperature = 24.0,
                    currentMode = ClimateMode.COOLING,
                    isRunning = true,
                    floorCount = 3,
                    zoneCount = 8,
                    runningZoneCount = 6
                )
            )
        )

        // When
        composeTestRule.setContent {
            ClimateScreen(
                viewModel = mockViewModel,
                onNavigateBack = {}
            )
        }

        // Then
        composeTestRule.onNodeWithContentDescription("返回")
            .assertHasClickAction()

        composeTestRule.onNodeWithContentDescription("增加温度")
            .assertHasClickAction()

        composeTestRule.onNodeWithContentDescription("降低温度")
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
