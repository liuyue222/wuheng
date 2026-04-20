package com.wuheng.smart.presentation.consumables

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
 * 耗材进度页面 UI 测试
 *
 * 测试范围:
 * 1. 页面元素显示验证
 * 2. 滤芯列表显示
 * 3. 滤芯状态显示（良好/正常/需更换/已过期）
 * 4. 滤芯重置功能
 * 5. 加载和错误状态
 */
@RunWith(AndroidJUnit4::class)
class ConsumablesScreenTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    private lateinit var mockViewModel: ConsumablesViewModel
    private val uiState = MutableStateFlow(ConsumablesUiState())

    @Before
    fun setUp() {
        mockViewModel = mockk(relaxed = true)
        every { mockViewModel.uiState } returns uiState.asStateFlow()
    }

    // ==================== 页面元素显示测试 ====================

    @Test
    fun consumablesScreen_shouldDisplayHeaderElements() {
        // Given
        uiState.value = ConsumablesUiState(
            consumables = UiDataState.Success(
                ConsumablesProgress(
                    filters = listOf(
                        FilterInfo(
                            id = "filter1",
                            name = "PP棉滤芯",
                            type = "PP",
                            lifePercent = 85,
                            lifeDays = 180,
                            totalDays = 180,
                            status = FilterStatus.GOOD
                        )
                    )
                )
            )
        )

        // When
        composeTestRule.setContent {
            ConsumablesScreen(
                viewModel = mockViewModel,
                onNavigateBack = {}
            )
        }

        // Then
        composeTestRule.onNodeWithText("耗材进度").assertIsDisplayed()
    }

    @Test
    fun consumablesScreen_shouldDisplayFilterList() {
        // Given
        uiState.value = ConsumablesUiState(
            consumables = UiDataState.Success(
                ConsumablesProgress(
                    filters = listOf(
                        FilterInfo(
                            id = "filter1",
                            name = "PP棉滤芯",
                            type = "PP",
                            lifePercent = 85,
                            lifeDays = 180,
                            totalDays = 180,
                            status = FilterStatus.GOOD
                        ),
                        FilterInfo(
                            id = "filter2",
                            name = "活性炭滤芯",
                            type = "CTO",
                            lifePercent = 60,
                            lifeDays = 180,
                            totalDays = 180,
                            status = FilterStatus.NORMAL
                        ),
                        FilterInfo(
                            id = "filter3",
                            name = "RO反渗透膜",
                            type = "RO",
                            lifePercent = 15,
                            lifeDays = 365,
                            totalDays = 365,
                            status = FilterStatus.WARNING
                        )
                    )
                )
            )
        )

        // When
        composeTestRule.setContent {
            ConsumablesScreen(
                viewModel = mockViewModel,
                onNavigateBack = {}
            )
        }

        // Then
        composeTestRule.onNodeWithText("PP棉滤芯").assertIsDisplayed()
        composeTestRule.onNodeWithText("活性炭滤芯").assertIsDisplayed()
        composeTestRule.onNodeWithText("RO反渗透膜").assertIsDisplayed()
    }

    // ==================== 滤芯状态显示测试 ====================

    @Test
    fun consumablesScreen_shouldDisplayGoodStatus() {
        // Given
        uiState.value = ConsumablesUiState(
            consumables = UiDataState.Success(
                ConsumablesProgress(
                    filters = listOf(
                        FilterInfo(
                            id = "filter1",
                            name = "PP棉滤芯",
                            type = "PP",
                            lifePercent = 85,
                            lifeDays = 180,
                            totalDays = 180,
                            status = FilterStatus.GOOD
                        )
                    )
                )
            )
        )

        // When
        composeTestRule.setContent {
            ConsumablesScreen(
                viewModel = mockViewModel,
                onNavigateBack = {}
            )
        }

        // Then
        composeTestRule.onNodeWithText("良好").assertIsDisplayed()
        composeTestRule.onNodeWithText("85%").assertIsDisplayed()
    }

    @Test
    fun consumablesScreen_shouldDisplayNormalStatus() {
        // Given
        uiState.value = ConsumablesUiState(
            consumables = UiDataState.Success(
                ConsumablesProgress(
                    filters = listOf(
                        FilterInfo(
                            id = "filter1",
                            name = "活性炭滤芯",
                            type = "CTO",
                            lifePercent = 60,
                            lifeDays = 180,
                            totalDays = 180,
                            status = FilterStatus.NORMAL
                        )
                    )
                )
            )
        )

        // When
        composeTestRule.setContent {
            ConsumablesScreen(
                viewModel = mockViewModel,
                onNavigateBack = {}
            )
        }

        // Then
        composeTestRule.onNodeWithText("正常").assertIsDisplayed()
        composeTestRule.onNodeWithText("60%").assertIsDisplayed()
    }

    @Test
    fun consumablesScreen_shouldDisplayWarningStatus() {
        // Given
        uiState.value = ConsumablesUiState(
            consumables = UiDataState.Success(
                ConsumablesProgress(
                    filters = listOf(
                        FilterInfo(
                            id = "filter1",
                            name = "RO反渗透膜",
                            type = "RO",
                            lifePercent = 15,
                            lifeDays = 365,
                            totalDays = 365,
                            status = FilterStatus.WARNING
                        )
                    )
                )
            )
        )

        // When
        composeTestRule.setContent {
            ConsumablesScreen(
                viewModel = mockViewModel,
                onNavigateBack = {}
            )
        }

        // Then
        composeTestRule.onNodeWithText("需更换").assertIsDisplayed()
        composeTestRule.onNodeWithText("15%").assertIsDisplayed()
    }

    @Test
    fun consumablesScreen_shouldDisplayExpiredStatus() {
        // Given
        uiState.value = ConsumablesUiState(
            consumables = UiDataState.Success(
                ConsumablesProgress(
                    filters = listOf(
                        FilterInfo(
                            id = "filter1",
                            name = "PP棉滤芯",
                            type = "PP",
                            lifePercent = 0,
                            lifeDays = 0,
                            totalDays = 180,
                            status = FilterStatus.EXPIRED
                        )
                    )
                )
            )
        )

        // When
        composeTestRule.setContent {
            ConsumablesScreen(
                viewModel = mockViewModel,
                onNavigateBack = {}
            )
        }

        // Then
        composeTestRule.onNodeWithText("已过期").assertIsDisplayed()
        composeTestRule.onNodeWithText("0%").assertIsDisplayed()
    }

    // ==================== 滤芯详情测试 ====================

    @Test
    fun consumablesScreen_shouldDisplayFilterDetails() {
        // Given
        val currentTime = System.currentTimeMillis()
        uiState.value = ConsumablesUiState(
            consumables = UiDataState.Success(
                ConsumablesProgress(
                    filters = listOf(
                        FilterInfo(
                            id = "filter1",
                            name = "PP棉滤芯",
                            type = "PP",
                            lifePercent = 85,
                            lifeDays = 180,
                            totalDays = 180,
                            status = FilterStatus.GOOD,
                            installDate = currentTime - 30L * 24 * 60 * 60 * 1000,
                            replaceDate = currentTime + 150L * 24 * 60 * 60 * 1000
                        )
                    )
                )
            )
        )

        // When
        composeTestRule.setContent {
            ConsumablesScreen(
                viewModel = mockViewModel,
                onNavigateBack = {}
            )
        }

        // Then
        composeTestRule.onNodeWithText("剩余天数").assertIsDisplayed()
        composeTestRule.onNodeWithText("180天").assertIsDisplayed()
    }

    @Test
    fun consumablesScreen_shouldDisplayInstallDate() {
        // Given
        val currentTime = System.currentTimeMillis()
        uiState.value = ConsumablesUiState(
            consumables = UiDataState.Success(
                ConsumablesProgress(
                    filters = listOf(
                        FilterInfo(
                            id = "filter1",
                            name = "PP棉滤芯",
                            type = "PP",
                            lifePercent = 85,
                            lifeDays = 180,
                            totalDays = 180,
                            status = FilterStatus.GOOD,
                            installDate = currentTime - 30L * 24 * 60 * 60 * 1000
                        )
                    )
                )
            )
        )

        // When
        composeTestRule.setContent {
            ConsumablesScreen(
                viewModel = mockViewModel,
                onNavigateBack = {}
            )
        }

        // Then
        composeTestRule.onNodeWithText("安装日期").assertIsDisplayed()
    }

    // ==================== 滤芯重置测试 ====================

    @Test
    fun consumablesScreen_shouldDisplayResetButton() {
        // Given
        uiState.value = ConsumablesUiState(
            consumables = UiDataState.Success(
                ConsumablesProgress(
                    filters = listOf(
                        FilterInfo(
                            id = "filter1",
                            name = "PP棉滤芯",
                            type = "PP",
                            lifePercent = 15,
                            lifeDays = 180,
                            totalDays = 180,
                            status = FilterStatus.WARNING
                        )
                    )
                )
            )
        )

        // When
        composeTestRule.setContent {
            ConsumablesScreen(
                viewModel = mockViewModel,
                onNavigateBack = {}
            )
        }

        // Then
        composeTestRule.onNodeWithContentDescription("重置滤芯")
            .assertIsDisplayed()
    }

    @Test
    fun consumablesScreen_shouldShowResetConfirmationWhenResetClicked() {
        // Given
        uiState.value = ConsumablesUiState(
            consumables = UiDataState.Success(
                ConsumablesProgress(
                    filters = listOf(
                        FilterInfo(
                            id = "filter1",
                            name = "PP棉滤芯",
                            type = "PP",
                            lifePercent = 15,
                            lifeDays = 180,
                            totalDays = 180,
                            status = FilterStatus.WARNING
                        )
                    )
                )
            )
        )

        composeTestRule.setContent {
            ConsumablesScreen(
                viewModel = mockViewModel,
                onNavigateBack = {}
            )
        }

        // When
        composeTestRule.onNodeWithContentDescription("重置滤芯")
            .performClick()

        // Then
        composeTestRule.onNodeWithText("确认重置").assertIsDisplayed()
        composeTestRule.onNodeWithText("确定要重置 PP棉滤芯 吗？重置后将重新计算滤芯寿命。").assertIsDisplayed()
        composeTestRule.onNodeWithText("确定").assertIsDisplayed()
        composeTestRule.onNodeWithText("取消").assertIsDisplayed()
    }

    @Test
    fun consumablesScreen_shouldResetFilterWhenConfirmed() {
        // Given
        uiState.value = ConsumablesUiState(
            consumables = UiDataState.Success(
                ConsumablesProgress(
                    filters = listOf(
                        FilterInfo(
                            id = "filter1",
                            name = "PP棉滤芯",
                            type = "PP",
                            lifePercent = 15,
                            lifeDays = 180,
                            totalDays = 180,
                            status = FilterStatus.WARNING
                        )
                    )
                )
            )
        )

        composeTestRule.setContent {
            ConsumablesScreen(
                viewModel = mockViewModel,
                onNavigateBack = {}
            )
        }

        // When
        composeTestRule.onNodeWithContentDescription("重置滤芯")
            .performClick()
        composeTestRule.onNodeWithText("确定")
            .performClick()

        // Then
        verify { mockViewModel.resetFilter("filter1") }
    }

    @Test
    fun consumablesScreen_shouldDismissDialogWhenCancelled() {
        // Given
        uiState.value = ConsumablesUiState(
            consumables = UiDataState.Success(
                ConsumablesProgress(
                    filters = listOf(
                        FilterInfo(
                            id = "filter1",
                            name = "PP棉滤芯",
                            type = "PP",
                            lifePercent = 15,
                            lifeDays = 180,
                            totalDays = 180,
                            status = FilterStatus.WARNING
                        )
                    )
                )
            )
        )

        composeTestRule.setContent {
            ConsumablesScreen(
                viewModel = mockViewModel,
                onNavigateBack = {}
            )
        }

        // When
        composeTestRule.onNodeWithContentDescription("重置滤芯")
            .performClick()
        composeTestRule.onNodeWithText("取消")
            .performClick()

        // Then
        composeTestRule.onNodeWithText("确认重置").assertDoesNotExist()
    }

    // ==================== 空状态测试 ====================

    @Test
    fun consumablesScreen_shouldShowEmptyStateWhenNoFilters() {
        // Given
        uiState.value = ConsumablesUiState(
            consumables = UiDataState.Success(
                ConsumablesProgress(filters = emptyList())
            )
        )

        // When
        composeTestRule.setContent {
            ConsumablesScreen(
                viewModel = mockViewModel,
                onNavigateBack = {}
            )
        }

        // Then
        composeTestRule.onNodeWithText("暂无耗材信息").assertIsDisplayed()
    }

    // ==================== 加载状态测试 ====================

    @Test
    fun consumablesScreen_shouldShowLoadingIndicator() {
        // Given
        uiState.value = ConsumablesUiState(
            consumables = UiDataState.Loading
        )

        // When
        composeTestRule.setContent {
            ConsumablesScreen(
                viewModel = mockViewModel,
                onNavigateBack = {}
            )
        }

        // Then
        composeTestRule.onNode(hasProgressBar())
            .assertIsDisplayed()
    }

    @Test
    fun consumablesScreen_shouldShowShimmerWhileLoading() {
        // Given
        uiState.value = ConsumablesUiState(
            consumables = UiDataState.Loading
        )

        // When
        composeTestRule.setContent {
            ConsumablesScreen(
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
    fun consumablesScreen_shouldShowErrorMessage() {
        // Given
        uiState.value = ConsumablesUiState(
            consumables = UiDataState.Error(Exception("加载失败"))
        )

        // When
        composeTestRule.setContent {
            ConsumablesScreen(
                viewModel = mockViewModel,
                onNavigateBack = {}
            )
        }

        // Then
        composeTestRule.onNodeWithText("加载失败，点击重试")
            .assertIsDisplayed()
    }

    @Test
    fun consumablesScreen_shouldRetryWhenErrorClicked() {
        // Given
        uiState.value = ConsumablesUiState(
            consumables = UiDataState.Error(Exception("加载失败"))
        )

        composeTestRule.setContent {
            ConsumablesScreen(
                viewModel = mockViewModel,
                onNavigateBack = {}
            )
        }

        // When
        composeTestRule.onNodeWithText("加载失败，点击重试")
            .performClick()

        // Then
        verify { mockViewModel.loadConsumables() }
    }

    // ==================== 刷新测试 ====================

    @Test
    fun consumablesScreen_shouldSupportPullToRefresh() {
        // Given
        uiState.value = ConsumablesUiState(
            consumables = UiDataState.Success(
                ConsumablesProgress(
                    filters = listOf(
                        FilterInfo(
                            id = "filter1",
                            name = "PP棉滤芯",
                            type = "PP",
                            lifePercent = 85,
                            lifeDays = 180,
                            totalDays = 180,
                            status = FilterStatus.GOOD
                        )
                    )
                )
            ),
            isRefreshing = false
        )

        composeTestRule.setContent {
            ConsumablesScreen(
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
    fun consumablesScreen_shouldNavigateBackWhenBackClicked() {
        // Given
        var navigatedBack = false
        uiState.value = ConsumablesUiState(
            consumables = UiDataState.Success(
                ConsumablesProgress(
                    filters = listOf(
                        FilterInfo(
                            id = "filter1",
                            name = "PP棉滤芯",
                            type = "PP",
                            lifePercent = 85,
                            lifeDays = 180,
                            totalDays = 180,
                            status = FilterStatus.GOOD
                        )
                    )
                )
            )
        )

        composeTestRule.setContent {
            ConsumablesScreen(
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

    // ==================== 提示信息测试 ====================

    @Test
    fun consumablesScreen_shouldDisplayFilterTypeDescription() {
        // Given
        uiState.value = ConsumablesUiState(
            consumables = UiDataState.Success(
                ConsumablesProgress(
                    filters = listOf(
                        FilterInfo(
                            id = "filter1",
                            name = "PP棉滤芯",
                            type = "PP",
                            lifePercent = 85,
                            lifeDays = 180,
                            totalDays = 180,
                            status = FilterStatus.GOOD
                        ),
                        FilterInfo(
                            id = "filter2",
                            name = "活性炭滤芯",
                            type = "CTO",
                            lifePercent = 60,
                            lifeDays = 180,
                            totalDays = 180,
                            status = FilterStatus.NORMAL
                        ),
                        FilterInfo(
                            id = "filter3",
                            name = "RO反渗透膜",
                            type = "RO",
                            lifePercent = 15,
                            lifeDays = 365,
                            totalDays = 365,
                            status = FilterStatus.WARNING
                        )
                    )
                )
            )
        )

        // When
        composeTestRule.setContent {
            ConsumablesScreen(
                viewModel = mockViewModel,
                onNavigateBack = {}
            )
        }

        // Then
        composeTestRule.onNodeWithText("PP").assertIsDisplayed()
        composeTestRule.onNodeWithText("CTO").assertIsDisplayed()
        composeTestRule.onNodeWithText("RO").assertIsDisplayed()
    }

    @Test
    fun consumablesScreen_shouldDisplayHelpText() {
        // Given
        uiState.value = ConsumablesUiState(
            consumables = UiDataState.Success(
                ConsumablesProgress(
                    filters = listOf(
                        FilterInfo(
                            id = "filter1",
                            name = "PP棉滤芯",
                            type = "PP",
                            lifePercent = 85,
                            lifeDays = 180,
                            totalDays = 180,
                            status = FilterStatus.GOOD
                        )
                    )
                )
            )
        )

        // When
        composeTestRule.setContent {
            ConsumablesScreen(
                viewModel = mockViewModel,
                onNavigateBack = {}
            )
        }

        // Then
        composeTestRule.onNodeWithText("提示：更换滤芯后请点击重置按钮更新寿命计算")
            .assertIsDisplayed()
    }

    // ==================== 无障碍测试 ====================

    @Test
    fun consumablesScreen_shouldHaveProperSemantics() {
        // Given
        uiState.value = ConsumablesUiState(
            consumables = UiDataState.Success(
                ConsumablesProgress(
                    filters = listOf(
                        FilterInfo(
                            id = "filter1",
                            name = "PP棉滤芯",
                            type = "PP",
                            lifePercent = 15,
                            lifeDays = 180,
                            totalDays = 180,
                            status = FilterStatus.WARNING
                        )
                    )
                )
            )
        )

        // When
        composeTestRule.setContent {
            ConsumablesScreen(
                viewModel = mockViewModel,
                onNavigateBack = {}
            )
        }

        // Then
        composeTestRule.onNodeWithContentDescription("返回")
            .assertHasClickAction()

        composeTestRule.onNodeWithContentDescription("重置滤芯")
            .assertHasClickAction()
    }

    // ==================== 重置状态测试 ====================

    @Test
    fun consumablesScreen_shouldShowResettingState() {
        // Given
        uiState.value = ConsumablesUiState(
            consumables = UiDataState.Success(
                ConsumablesProgress(
                    filters = listOf(
                        FilterInfo(
                            id = "filter1",
                            name = "PP棉滤芯",
                            type = "PP",
                            lifePercent = 15,
                            lifeDays = 180,
                            totalDays = 180,
                            status = FilterStatus.WARNING
                        )
                    )
                )
            ),
            isResetting = true
        )

        // When
        composeTestRule.setContent {
            ConsumablesScreen(
                viewModel = mockViewModel,
                onNavigateBack = {}
            )
        }

        // Then
        composeTestRule.onNode(hasProgressBar())
            .assertIsDisplayed()
    }

    @Test
    fun consumablesScreen_shouldShowResetSuccessMessage() {
        // Given
        uiState.value = ConsumablesUiState(
            consumables = UiDataState.Success(
                ConsumablesProgress(
                    filters = listOf(
                        FilterInfo(
                            id = "filter1",
                            name = "PP棉滤芯",
                            type = "PP",
                            lifePercent = 100,
                            lifeDays = 180,
                            totalDays = 180,
                            status = FilterStatus.GOOD
                        )
                    )
                )
            ),
            resetSuccess = true
        )

        // When
        composeTestRule.setContent {
            ConsumablesScreen(
                viewModel = mockViewModel,
                onNavigateBack = {}
            )
        }

        // Then
        composeTestRule.onNodeWithText("重置成功")
            .assertIsDisplayed()
    }
}

// Helper functions for semantic matchers
private fun hasProgressBar(): SemanticsMatcher {
    return SemanticsMatcher.keyIsDefined(androidx.compose.ui.semantics.ProgressBarRangeInfo)
}

private fun hasTestTag(tag: String): SemanticsMatcher {
    return SemanticsMatcher.expectValue(androidx.compose.ui.semantics.SemanticsProperties.TestTag, tag)
}
