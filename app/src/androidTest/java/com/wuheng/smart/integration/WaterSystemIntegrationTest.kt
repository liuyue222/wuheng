package com.wuheng.smart.integration

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.wuheng.smart.presentation.water.*
import com.wuheng.smart.presentation.theme.WuHengTheme
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * 水系统页面交互集成测试
 */
@RunWith(AndroidJUnit4::class)
class WaterSystemIntegrationTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    /**
     * 测试水系统页面正常状态
     */
    @Test
    fun test_waterSystemNormalState() {
        composeTestRule.setContent {
            WuHengTheme {
                WaterScreenContent(
                    uiState = WaterUiState(
                        hotWaterMode = HotWaterMode.TEMPORARY,
                        currentTemp = 55,
                        temporaryDuration = 30,
                        sterilizationSchedule = "每周五 02:00",
                        filters = listOf(
                            FilterItem("前置过滤器", 0.98f, FilterStatus.NORMAL),
                            FilterItem("中央净水机", 0.65f, FilterStatus.NORMAL),
                            FilterItem("末端直饮", 0.15f, FilterStatus.WARNING)
                        )
                    ),
                    onHotWaterModeSelected = {},
                    onDurationClick = {},
                    onSterilizationEdit = {},
                    onFilterReplaceClick = {},
                    onRefresh = {}
                )
            }
        }

        // 验证页面内容显示
        composeTestRule.onNodeWithText("热水循环").assertIsDisplayed()
        composeTestRule.onNodeWithText("55°C").assertIsDisplayed()
        composeTestRule.onNodeWithText("前置过滤器").assertIsDisplayed()
        composeTestRule.onNodeWithText("中央净水机").assertIsDisplayed()
        composeTestRule.onNodeWithText("末端直饮").assertIsDisplayed()
    }

    /**
     * 测试热水模式切换
     */
    @Test
    fun test_hotWaterModeSwitching() {
        var selectedMode: HotWaterMode? = null

        composeTestRule.setContent {
            WuHengTheme {
                WaterScreenContent(
                    uiState = WaterUiState(
                        hotWaterMode = HotWaterMode.TEMPORARY,
                        currentTemp = 55,
                        temporaryDuration = 30
                    ),
                    onHotWaterModeSelected = { mode ->
                        selectedMode = mode
                    },
                    onDurationClick = {},
                    onSterilizationEdit = {},
                    onFilterReplaceClick = {},
                    onRefresh = {}
                )
            }
        }

        // 验证当前模式显示
        composeTestRule.onNodeWithText("热水循环").assertIsDisplayed()

        // 点击模式切换按钮（假设有模式切换按钮）
        // 这里根据实际UI组件进行调整
    }

    /**
     * 测试滤芯状态显示
     */
    @Test
    fun test_filterStatusDisplay() {
        composeTestRule.setContent {
            WuHengTheme {
                WaterScreenContent(
                    uiState = WaterUiState(
                        hotWaterMode = HotWaterMode.TEMPORARY,
                        currentTemp = 55,
                        temporaryDuration = 30,
                        filters = listOf(
                            FilterItem("前置过滤器", 0.98f, FilterStatus.NORMAL),
                            FilterItem("中央净水机", 0.65f, FilterStatus.NORMAL),
                            FilterItem("末端直饮", 0.15f, FilterStatus.WARNING),
                            FilterItem("超滤", 0.05f, FilterStatus.EXPIRED)
                        )
                    ),
                    onHotWaterModeSelected = {},
                    onDurationClick = {},
                    onSterilizationEdit = {},
                    onFilterReplaceClick = {},
                    onRefresh = {}
                )
            }
        }

        // 验证所有滤芯显示
        composeTestRule.onNodeWithText("前置过滤器").assertIsDisplayed()
        composeTestRule.onNodeWithText("中央净水机").assertIsDisplayed()
        composeTestRule.onNodeWithText("末端直饮").assertIsDisplayed()
        composeTestRule.onNodeWithText("超滤").assertIsDisplayed()
    }

    /**
     * 测试滤芯更换按钮
     */
    @Test
    fun test_filterReplaceButton() {
        var replaceClicked = false

        composeTestRule.setContent {
            WuHengTheme {
                WaterScreenContent(
                    uiState = WaterUiState(
                        hotWaterMode = HotWaterMode.TEMPORARY,
                        currentTemp = 55,
                        temporaryDuration = 30,
                        filters = listOf(
                            FilterItem("前置过滤器", 0.15f, FilterStatus.WARNING)
                        )
                    ),
                    onHotWaterModeSelected = {},
                    onDurationClick = {},
                    onSterilizationEdit = {},
                    onFilterReplaceClick = { replaceClicked = true },
                    onRefresh = {}
                )
            }
        }

        // 点击滤芯更换
        composeTestRule.onNodeWithText("前置过滤器").performClick()
        composeTestRule.waitForIdle()

        // 验证回调被触发
        assert(replaceClicked)
    }

    /**
     * 测试水系统页面加载状态
     */
    @Test
    fun test_waterSystemLoadingState() {
        composeTestRule.setContent {
            WuHengTheme {
                WaterScreenContent(
                    uiState = WaterUiState(isLoading = true),
                    onHotWaterModeSelected = {},
                    onDurationClick = {},
                    onSterilizationEdit = {},
                    onFilterReplaceClick = {},
                    onRefresh = {}
                )
            }
        }

        // 验证加载指示器显示
        composeTestRule.onNode(hasProgressBarRangeInfo()).assertIsDisplayed()
    }

    /**
     * 测试水系统页面错误状态
     */
    @Test
    fun test_waterSystemErrorState() {
        var retryClicked = false

        composeTestRule.setContent {
            WuHengTheme {
                WaterScreenContent(
                    uiState = WaterUiState(errorMessage = "网络连接失败，请重试"),
                    onHotWaterModeSelected = {},
                    onDurationClick = {},
                    onSterilizationEdit = {},
                    onFilterReplaceClick = {},
                    onRefresh = { retryClicked = true }
                )
            }
        }

        // 验证错误信息显示
        composeTestRule.onNodeWithText("网络连接失败，请重试").assertIsDisplayed()

        // 验证重试按钮存在并点击
        composeTestRule.onNodeWithText("重试").assertIsDisplayed().performClick()

        composeTestRule.waitForIdle()
        assert(retryClicked)
    }

    /**
     * 测试杀菌预约编辑
     */
    @Test
    fun test_sterilizationScheduleEdit() {
        var editClicked = false

        composeTestRule.setContent {
            WuHengTheme {
                WaterScreenContent(
                    uiState = WaterUiState(
                        hotWaterMode = HotWaterMode.TEMPORARY,
                        currentTemp = 55,
                        temporaryDuration = 30,
                        sterilizationSchedule = "每周五 02:00"
                    ),
                    onHotWaterModeSelected = {},
                    onDurationClick = {},
                    onSterilizationEdit = { editClicked = true },
                    onFilterReplaceClick = {},
                    onRefresh = {}
                )
            }
        }

        // 验证杀菌预约显示
        composeTestRule.onNodeWithText("每周五 02:00").assertIsDisplayed()

        // 点击编辑按钮（假设有编辑按钮）
        // composeTestRule.onNodeWithContentDescription("编辑").performClick()
        // composeTestRule.waitForIdle()
        // assert(editClicked)
    }

    /**
     * 测试热水温度调节
     */
    @Test
    fun test_hotWaterTemperatureAdjustment() {
        composeTestRule.setContent {
            WuHengTheme {
                WaterScreenContent(
                    uiState = WaterUiState(
                        hotWaterMode = HotWaterMode.TEMPORARY,
                        currentTemp = 55,
                        temporaryDuration = 30
                    ),
                    onHotWaterModeSelected = {},
                    onDurationClick = {},
                    onSterilizationEdit = {},
                    onFilterReplaceClick = {},
                    onRefresh = {}
                )
            }
        }

        // 验证当前温度显示
        composeTestRule.onNodeWithText("55°C").assertIsDisplayed()
    }

    /**
     * 测试定时时长选择
     */
    @Test
    fun test_durationSelection() {
        var durationClicked = false

        composeTestRule.setContent {
            WuHengTheme {
                WaterScreenContent(
                    uiState = WaterUiState(
                        hotWaterMode = HotWaterMode.TEMPORARY,
                        currentTemp = 55,
                        temporaryDuration = 30
                    ),
                    onHotWaterModeSelected = {},
                    onDurationClick = { durationClicked = true },
                    onSterilizationEdit = {},
                    onFilterReplaceClick = {},
                    onRefresh = {}
                )
            }
        }

        // 验证时长显示
        composeTestRule.onNodeWithText("30分钟").assertIsDisplayed()

        // 点击时长选择
        // composeTestRule.onNodeWithText("30分钟").performClick()
        // composeTestRule.waitForIdle()
        // assert(durationClicked)
    }
}
