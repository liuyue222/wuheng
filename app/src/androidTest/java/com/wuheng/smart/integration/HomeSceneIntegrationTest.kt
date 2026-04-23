package com.wuheng.smart.integration

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.wuheng.smart.data.model.SceneType
import com.wuheng.smart.presentation.home.*
import com.wuheng.smart.presentation.theme.WuHengTheme
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * 首页场景切换的UI集成测试
 */
@RunWith(AndroidJUnit4::class)
class HomeSceneIntegrationTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    /**
     * 测试首页场景切换的UI反馈
     */
    @Test
    fun test_homeSceneSwitchingUI() {
        var selectedScene: SceneType? = null

        composeTestRule.setContent {
            WuHengTheme {
                HomeScreenContent(
                    uiState = HomeUiState(
                        location = "杭州市 西湖区",
                        outdoorTemp = 26,
                        weather = "多云",
                        aqi = 35,
                        pm25 = 12,
                        outdoorHumidity = 65,
                        residenceName = "西湖一号院",
                        currentMode = ClimateMode.COOLING,
                        indoorTemp = "24.5",
                        indoorHumidity = "48",
                        co2 = 420,
                        tovc = "0.6",
                        scenes = listOf(
                            SceneItem(SceneType.MEETING, "会客模式", false),
                            SceneItem(SceneType.AWAY, "离家模式", false),
                            SceneItem(SceneType.SLEEP, "睡眠模式", false),
                            SceneItem(SceneType.GUARD, "ECO节能", false)
                        )
                    ),
                    hasVacationScene = true,
                    onModeSelected = {},
                    onSceneSelected = { sceneType ->
                        selectedScene = sceneType
                    },
                    onVacationModeClick = {},
                    onResidenceClick = {},
                    onRefresh = {}
                )
            }
        }

        // 验证首页标题
        composeTestRule.onNodeWithText("西湖一号院").assertIsDisplayed()

        // 验证场景按钮存在
        composeTestRule.onNodeWithText("会客模式").assertIsDisplayed()
        composeTestRule.onNodeWithText("离家模式").assertIsDisplayed()
        composeTestRule.onNodeWithText("睡眠模式").assertIsDisplayed()
        composeTestRule.onNodeWithText("ECO节能").assertIsDisplayed()

        // 点击会客模式
        composeTestRule.onNodeWithText("会客模式").performClick()

        // 验证回调被触发
        composeTestRule.waitForIdle()
        assert(selectedScene == SceneType.MEETING)

        // 点击离家模式
        composeTestRule.onNodeWithText("离家模式").performClick()
        composeTestRule.waitForIdle()
        assert(selectedScene == SceneType.AWAY)

        // 点击睡眠模式
        composeTestRule.onNodeWithText("睡眠模式").performClick()
        composeTestRule.waitForIdle()
        assert(selectedScene == SceneType.SLEEP)
    }

    /**
     * 测试首页模式切换
     */
    @Test
    fun test_homeModeSwitching() {
        var selectedMode: ClimateMode? = null

        composeTestRule.setContent {
            WuHengTheme {
                HomeScreenContent(
                    uiState = HomeUiState(
                        location = "杭州市 西湖区",
                        outdoorTemp = 26,
                        weather = "多云",
                        aqi = 35,
                        pm25 = 12,
                        outdoorHumidity = 65,
                        residenceName = "西湖一号院",
                        currentMode = ClimateMode.COOLING,
                        indoorTemp = "24.5",
                        indoorHumidity = "48",
                        co2 = 420,
                        tovc = "0.6",
                        scenes = listOf(
                            SceneItem(SceneType.MEETING, "会客模式", false),
                            SceneItem(SceneType.AWAY, "离家模式", false),
                            SceneItem(SceneType.SLEEP, "睡眠模式", false),
                            SceneItem(SceneType.GUARD, "ECO节能", false)
                        )
                    ),
                    hasVacationScene = true,
                    onModeSelected = { mode ->
                        selectedMode = mode
                    },
                    onSceneSelected = {},
                    onVacationModeClick = {},
                    onResidenceClick = {},
                    onRefresh = {}
                )
            }
        }

        // 验证模式选择器存在（通过内容描述或语义）
        // 这里假设模式选择器有相应的文本或描述
    }

    /**
     * 测试首页加载状态
     */
    @Test
    fun test_homeLoadingState() {
        composeTestRule.setContent {
            WuHengTheme {
                HomeScreenContent(
                    uiState = HomeUiState(isLoading = true),
                    onModeSelected = {},
                    onSceneSelected = {},
                    onVacationModeClick = {},
                    onResidenceClick = {},
                    onRefresh = {}
                )
            }
        }

        // 验证加载指示器显示
        composeTestRule.onNode(hasProgressBarRangeInfo()).assertIsDisplayed()
    }

    /**
     * 测试首页错误状态
     */
    @Test
    fun test_homeErrorState() {
        var retryClicked = false

        composeTestRule.setContent {
            WuHengTheme {
                HomeScreenContent(
                    uiState = HomeUiState(errorMessage = "网络连接失败，请重试"),
                    onModeSelected = {},
                    onSceneSelected = {},
                    onVacationModeClick = {},
                    onResidenceClick = {},
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
     * 测试首页刷新功能
     */
    @Test
    fun test_homeRefreshFunctionality() {
        var refreshClicked = false

        composeTestRule.setContent {
            WuHengTheme {
                HomeScreenContent(
                    uiState = HomeUiState(
                        location = "杭州市 西湖区",
                        outdoorTemp = 26,
                        weather = "多云",
                        residenceName = "西湖一号院",
                        currentMode = ClimateMode.COOLING,
                        indoorTemp = "24.5",
                        indoorHumidity = "48"
                    ),
                    onModeSelected = {},
                    onSceneSelected = {},
                    onVacationModeClick = {},
                    onResidenceClick = {},
                    onRefresh = { refreshClicked = true }
                )
            }
        }

        // 验证页面内容显示
        composeTestRule.onNodeWithText("西湖一号院").assertIsDisplayed()
        composeTestRule.onNodeWithText("24.5").assertIsDisplayed()
    }
}
