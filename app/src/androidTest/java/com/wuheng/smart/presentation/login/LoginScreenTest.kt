package com.wuheng.smart.presentation.login

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.wuheng.smart.data.model.LoginResponse
import com.wuheng.smart.data.network.ApiResult
import com.wuheng.smart.data.repository.UserRepository
import com.wuheng.smart.presentation.base.UiDataState
import io.mockk.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * 登录页面 UI 测试
 *
 * 测试范围:
 * 1. 页面元素显示验证
 * 2. 用户名/密码输入
 * 3. 表单验证错误显示
 * 4. 登录按钮状态
 * 5. 密码可见性切换
 * 6. 记住密码选项
 * 7. 加载状态显示
 */
@RunWith(AndroidJUnit4::class)
class LoginScreenTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    private lateinit var mockViewModel: LoginViewModel
    private val uiState = MutableStateFlow(LoginUiState())

    @Before
    fun setUp() {
        mockViewModel = mockk(relaxed = true)
        every { mockViewModel.uiState } returns uiState.asStateFlow()
    }

    // ==================== 页面元素显示测试 ====================

    @Test
    fun loginScreen_shouldDisplayAllElements() {
        // Given & When
        composeTestRule.setContent {
            LoginScreen(
                viewModel = mockViewModel,
                onLoginSuccess = {}
            )
        }

        // Then
        composeTestRule.onNodeWithText("五恒智能控制").assertIsDisplayed()
        composeTestRule.onNodeWithText("用户名").assertIsDisplayed()
        composeTestRule.onNodeWithText("密码").assertIsDisplayed()
        composeTestRule.onNodeWithText("登录").assertIsDisplayed()
        composeTestRule.onNodeWithText("记住密码").assertIsDisplayed()
    }

    @Test
    fun loginScreen_shouldDisplayLogo() {
        // Given & When
        composeTestRule.setContent {
            LoginScreen(
                viewModel = mockViewModel,
                onLoginSuccess = {}
            )
        }

        // Then
        composeTestRule.onNodeWithContentDescription("App Logo").assertExists()
    }

    // ==================== 用户名输入测试 ====================

    @Test
    fun usernameInput_shouldAcceptTextInput() {
        // Given
        composeTestRule.setContent {
            LoginScreen(
                viewModel = mockViewModel,
                onLoginSuccess = {}
            )
        }

        // When
        composeTestRule.onNodeWithText("用户名")
            .performTextInput("testuser")

        // Then
        verify { mockViewModel.onUsernameChanged("testuser") }
    }

    @Test
    fun usernameInput_shouldDisplayErrorWhenInvalid() {
        // Given
        uiState.value = LoginUiState(
            username = "ab",
            usernameError = "用户名至少3个字符"
        )

        composeTestRule.setContent {
            LoginScreen(
                viewModel = mockViewModel,
                onLoginSuccess = {}
            )
        }

        // Then
        composeTestRule.onNodeWithText("用户名至少3个字符").assertIsDisplayed()
    }

    @Test
    fun usernameInput_shouldShowErrorForEmptyUsername() {
        // Given
        uiState.value = LoginUiState(
            username = "",
            usernameError = "用户名不能为空"
        )

        composeTestRule.setContent {
            LoginScreen(
                viewModel = mockViewModel,
                onLoginSuccess = {}
            )
        }

        // Then
        composeTestRule.onNodeWithText("用户名不能为空").assertIsDisplayed()
    }

    // ==================== 密码输入测试 ====================

    @Test
    fun passwordInput_shouldAcceptTextInput() {
        // Given
        composeTestRule.setContent {
            LoginScreen(
                viewModel = mockViewModel,
                onLoginSuccess = {}
            )
        }

        // When
        composeTestRule.onNodeWithText("密码")
            .performTextInput("password123")

        // Then
        verify { mockViewModel.onPasswordChanged("password123") }
    }

    @Test
    fun passwordInput_shouldDisplayErrorWhenInvalid() {
        // Given
        uiState.value = LoginUiState(
            password = "12345",
            passwordError = "密码至少6个字符"
        )

        composeTestRule.setContent {
            LoginScreen(
                viewModel = mockViewModel,
                onLoginSuccess = {}
            )
        }

        // Then
        composeTestRule.onNodeWithText("密码至少6个字符").assertIsDisplayed()
    }

    @Test
    fun passwordInput_shouldMaskPasswordByDefault() {
        // Given
        uiState.value = LoginUiState(
            password = "secret123",
            isPasswordVisible = false
        )

        composeTestRule.setContent {
            LoginScreen(
                viewModel = mockViewModel,
                onLoginSuccess = {}
            )
        }

        // Then - Password should be masked (visual transformation applied)
        composeTestRule.onNode(hasSetTextAction())
            .assertTextContains("•", substring = true)
    }

    // ==================== 密码可见性测试 ====================

    @Test
    fun passwordVisibilityToggle_shouldToggleVisibility() {
        // Given
        uiState.value = LoginUiState(isPasswordVisible = false)

        composeTestRule.setContent {
            LoginScreen(
                viewModel = mockViewModel,
                onLoginSuccess = {}
            )
        }

        // When
        composeTestRule.onNodeWithContentDescription("显示密码")
            .performClick()

        // Then
        verify { mockViewModel.onPasswordVisibilityChanged() }
    }

    @Test
    fun passwordVisibilityToggle_shouldShowHideIconWhenVisible() {
        // Given
        uiState.value = LoginUiState(isPasswordVisible = true)

        composeTestRule.setContent {
            LoginScreen(
                viewModel = mockViewModel,
                onLoginSuccess = {}
            )
        }

        // Then
        composeTestRule.onNodeWithContentDescription("隐藏密码")
            .assertIsDisplayed()
    }

    // ==================== 登录按钮测试 ====================

    @Test
    fun loginButton_shouldBeDisabledWhenFormInvalid() {
        // Given
        uiState.value = LoginUiState(
            username = "",
            password = "",
            isFormValid = false
        )

        composeTestRule.setContent {
            LoginScreen(
                viewModel = mockViewModel,
                onLoginSuccess = {}
            )
        }

        // Then
        composeTestRule.onNodeWithText("登录")
            .assertIsNotEnabled()
    }

    @Test
    fun loginButton_shouldBeEnabledWhenFormValid() {
        // Given
        uiState.value = LoginUiState(
            username = "validuser",
            password = "validpass",
            isFormValid = true
        )

        composeTestRule.setContent {
            LoginScreen(
                viewModel = mockViewModel,
                onLoginSuccess = {}
            )
        }

        // Then
        composeTestRule.onNodeWithText("登录")
            .assertIsEnabled()
    }

    @Test
    fun loginButton_shouldTriggerLoginWhenClicked() {
        // Given
        uiState.value = LoginUiState(
            username = "testuser",
            password = "password123",
            isFormValid = true
        )

        composeTestRule.setContent {
            LoginScreen(
                viewModel = mockViewModel,
                onLoginSuccess = {}
            )
        }

        // When
        composeTestRule.onNodeWithText("登录")
            .performClick()

        // Then
        verify { mockViewModel.login() }
    }

    // ==================== 记住密码测试 ====================

    @Test
    fun rememberPasswordCheckbox_shouldBeUncheckedByDefault() {
        // Given
        uiState.value = LoginUiState(rememberPassword = false)

        composeTestRule.setContent {
            LoginScreen(
                viewModel = mockViewModel,
                onLoginSuccess = {}
            )
        }

        // Then
        composeTestRule.onNode(hasToggleableState())
            .assertIsOff()
    }

    @Test
    fun rememberPasswordCheckbox_shouldToggleWhenClicked() {
        // Given
        uiState.value = LoginUiState(rememberPassword = false)

        composeTestRule.setContent {
            LoginScreen(
                viewModel = mockViewModel,
                onLoginSuccess = {}
            )
        }

        // When
        composeTestRule.onNodeWithText("记住密码")
            .performClick()

        // Then
        verify { mockViewModel.onRememberPasswordChanged(true) }
    }

    // ==================== 加载状态测试 ====================

    @Test
    fun loginScreen_shouldShowLoadingIndicatorWhenLoggingIn() {
        // Given
        uiState.value = LoginUiState(
            loginState = UiDataState.Loading
        )

        composeTestRule.setContent {
            LoginScreen(
                viewModel = mockViewModel,
                onLoginSuccess = {}
            )
        }

        // Then
        composeTestRule.onNode(hasProgressBar())
            .assertIsDisplayed()
    }

    @Test
    fun loginScreen_shouldShowLoadingOverlayWhenLoggingIn() {
        // Given
        uiState.value = LoginUiState(
            username = "testuser",
            password = "password123",
            loginState = UiDataState.Loading
        )

        composeTestRule.setContent {
            LoginScreen(
                viewModel = mockViewModel,
                onLoginSuccess = {}
            )
        }

        // Then
        composeTestRule.onNodeWithText("登录中...")
            .assertIsDisplayed()
    }

    // ==================== 错误提示测试 ====================

    @Test
    fun loginScreen_shouldShowErrorMessageOnLoginFailure() {
        // Given
        val errorMessage = "用户名或密码错误"
        uiState.value = LoginUiState(
            loginState = UiDataState.Error(Exception(errorMessage))
        )

        composeTestRule.setContent {
            LoginScreen(
                viewModel = mockViewModel,
                onLoginSuccess = {}
            )
        }

        // Then
        composeTestRule.onNodeWithText(errorMessage)
            .assertIsDisplayed()
    }

    @Test
    fun loginScreen_shouldShowNetworkErrorMessage() {
        // Given
        uiState.value = LoginUiState(
            loginState = UiDataState.Error(Exception("网络连接失败"))
        )

        composeTestRule.setContent {
            LoginScreen(
                viewModel = mockViewModel,
                onLoginSuccess = {}
            )
        }

        // Then
        composeTestRule.onNodeWithText("网络连接失败")
            .assertIsDisplayed()
    }

    // ==================== 登录成功测试 ====================

    @Test
    fun loginScreen_shouldCallOnLoginSuccessWhenLoginSucceeds() {
        // Given
        var loginSuccessCalled = false
        uiState.value = LoginUiState(
            loginState = UiDataState.Success(
                LoginResponse(1, "USER001", "test", "13800138000", "token", 1, 1, 1)
            )
        )

        composeTestRule.setContent {
            LoginScreen(
                viewModel = mockViewModel,
                onLoginSuccess = { loginSuccessCalled = true }
            )
        }

        // Then
        assert(loginSuccessCalled)
    }

    // ==================== 输入清除测试 ====================

    @Test
    fun usernameInput_shouldHaveClearButton() {
        // Given
        uiState.value = LoginUiState(username = "testuser")

        composeTestRule.setContent {
            LoginScreen(
                viewModel = mockViewModel,
                onLoginSuccess = {}
            )
        }

        // Then
        composeTestRule.onNodeWithContentDescription("清除用户名")
            .assertIsDisplayed()
    }

    @Test
    fun clearButton_shouldClearUsernameWhenClicked() {
        // Given
        uiState.value = LoginUiState(username = "testuser")

        composeTestRule.setContent {
            LoginScreen(
                viewModel = mockViewModel,
                onLoginSuccess = {}
            )
        }

        // When
        composeTestRule.onNodeWithContentDescription("清除用户名")
            .performClick()

        // Then
        verify { mockViewModel.onUsernameChanged("") }
    }

    // ==================== 键盘操作测试 ====================

    @Test
    fun passwordInput_shouldTriggerLoginOnImeAction() {
        // Given
        uiState.value = LoginUiState(
            username = "testuser",
            password = "password123",
            isFormValid = true
        )

        composeTestRule.setContent {
            LoginScreen(
                viewModel = mockViewModel,
                onLoginSuccess = {}
            )
        }

        // When
        composeTestRule.onNodeWithText("密码")
            .performImeAction()

        // Then
        verify { mockViewModel.login() }
    }

    // ==================== 无障碍测试 ====================

    @Test
    fun loginScreen_shouldHaveProperSemantics() {
        // Given & When
        composeTestRule.setContent {
            LoginScreen(
                viewModel = mockViewModel,
                onLoginSuccess = {}
            )
        }

        // Then
        composeTestRule.onNodeWithText("用户名")
            .assert(hasSetTextAction())

        composeTestRule.onNodeWithText("密码")
            .assert(hasSetTextAction())

        composeTestRule.onNodeWithText("登录")
            .assert(hasClickAction())
    }
}

// Helper functions for semantic matchers
private fun hasProgressBar(): SemanticsMatcher {
    return SemanticsMatcher.keyIsDefined(androidx.compose.ui.semantics.ProgressBarRangeInfo)
}

private fun hasToggleableState(): SemanticsMatcher {
    return SemanticsMatcher.keyIsDefined(androidx.compose.ui.semantics.ToggleableState)
}
