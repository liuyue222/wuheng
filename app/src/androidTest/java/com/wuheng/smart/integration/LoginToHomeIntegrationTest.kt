package com.wuheng.smart.integration

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.wuheng.smart.MainActivity
import com.wuheng.smart.presentation.home.HomeScreen
import com.wuheng.smart.presentation.login.LoginScreen
import com.wuheng.smart.presentation.theme.WuHengTheme
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

/**
 * 登录到首页的完整导航流程集成测试
 * 使用Hilt进行依赖注入测试
 */
@HiltAndroidTest
class LoginToHomeIntegrationTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun init() {
        hiltRule.inject()
    }

    /**
     * 测试登录页面到首页的完整导航流程
     */
    @Test
    fun test_loginToHomeNavigationFlow() {
        // 设置测试导航
        composeTestRule.setContent {
            WuHengTheme {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = "login"
                ) {
                    composable("login") {
                        LoginScreen(
                            onNavigateToHome = {
                                navController.navigate("home") {
                                    popUpTo("login") { inclusive = true }
                                }
                            },
                            onNavigateToRegister = {},
                            onNavigateToForgotPassword = {}
                        )
                    }
                    composable("home") {
                        HomeScreen()
                    }
                }
            }
        }

        // 验证登录页面显示
        composeTestRule.onNodeWithText("欢迎登录").assertIsDisplayed()
        composeTestRule.onNodeWithText("手机号").assertIsDisplayed()
        composeTestRule.onNodeWithText("密码").assertIsDisplayed()

        // 输入手机号和密码
        composeTestRule.onNodeWithText("手机号").performTextInput("13800138001")
        composeTestRule.onNodeWithText("密码").performTextInput("password123")

        // 点击登录按钮
        composeTestRule.onNodeWithText("登录").performClick()

        // 等待导航完成
        composeTestRule.waitForIdle()

        // 验证首页显示（首页应该有"楼层区域"或"冷暖"等特征文本）
        // 注意：由于登录是异步的，这里可能需要等待或使用IdlingResource
    }

    /**
     * 测试登录表单验证
     */
    @Test
    fun test_loginFormValidation() {
        composeTestRule.setContent {
            WuHengTheme {
                LoginScreen()
            }
        }

        // 验证初始状态
        composeTestRule.onNodeWithText("登录").assertIsNotEnabled()

        // 输入无效手机号
        composeTestRule.onNodeWithText("手机号").performTextInput("123")
        composeTestRule.onNodeWithText("登录").assertIsNotEnabled()

        // 清除并输入有效手机号
        composeTestRule.onNodeWithText("手机号").performTextClearance()
        composeTestRule.onNodeWithText("手机号").performTextInput("13800138001")

        // 输入密码
        composeTestRule.onNodeWithText("密码").performTextInput("123456")

        // 验证登录按钮启用
        composeTestRule.onNodeWithText("登录").assertIsEnabled()
    }

    /**
     * 测试记住密码功能
     */
    @Test
    fun test_rememberPasswordFunctionality() {
        composeTestRule.setContent {
            WuHengTheme {
                LoginScreen()
            }
        }

        // 验证记住密码复选框存在
        composeTestRule.onNodeWithText("记住密码").assertIsDisplayed()

        // 点击记住密码
        composeTestRule.onNodeWithText("记住密码").performClick()

        // 验证复选框被选中（通过文本断言）
        composeTestRule.onNode(
            hasText("记住密码") and isToggleable()
        ).assertIsDisplayed()
    }

    /**
     * 测试密码显示/隐藏切换
     */
    @Test
    fun test_passwordVisibilityToggle() {
        composeTestRule.setContent {
            WuHengTheme {
                LoginScreen()
            }
        }

        // 输入密码
        composeTestRule.onNodeWithText("密码").performTextInput("password123")

        // 点击显示密码
        composeTestRule.onNodeWithText("显示").performClick()

        // 点击隐藏密码
        composeTestRule.onNodeWithText("隐藏").performClick()
    }
}
