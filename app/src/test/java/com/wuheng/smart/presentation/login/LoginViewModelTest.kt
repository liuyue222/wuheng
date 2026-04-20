package com.wuheng.smart.presentation.login

import app.cash.turbine.test
import com.wuheng.smart.data.model.LoginResponse
import com.wuheng.smart.data.network.ApiResult
import com.wuheng.smart.data.repository.UserRepository
import com.wuheng.smart.presentation.base.UiDataState
import io.mockk.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.*
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.*

/**
 * LoginViewModel 单元测试
 *
 * 测试范围:
 * 1. 用户名/密码输入验证
 * 2. 表单验证逻辑
 * 3. 登录流程（成功/失败）
 * 4. 密码可见性切换
 * 5. 记住密码选项
 * 6. 状态重置
 */
@OptIn(ExperimentalCoroutinesApi::class)
class LoginViewModelTest {

    private lateinit var userRepository: UserRepository
    private lateinit var viewModel: LoginViewModel
    private val testDispatcher = StandardTestDispatcher()

    @BeforeEach
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        userRepository = mockk(relaxed = true)
        viewModel = LoginViewModel(userRepository)
    }

    @AfterEach
    fun tearDown() {
        Dispatchers.resetMain()
        unmockkAll()
    }

    // ==================== 初始状态测试 ====================

    @Test
    fun `initial state should have empty fields and idle state`() = runTest {
        // Given & When
        val initialState = viewModel.uiState.value

        // Then
        assertEquals("", initialState.username)
        assertEquals("", initialState.password)
        assertFalse(initialState.rememberPassword)
        assertFalse(initialState.isPasswordVisible)
        assertTrue(initialState.loginState is UiDataState.Idle)
        assertNull(initialState.usernameError)
        assertNull(initialState.passwordError)
        assertFalse(initialState.isFormValid)
    }

    // ==================== 用户名输入测试 ====================

    @Test
    fun `onUsernameChanged with valid username should update state`() = runTest {
        // Given
        val validUsername = "testuser"

        // When
        viewModel.onUsernameChanged(validUsername)

        // Then
        assertEquals(validUsername, viewModel.uiState.value.username)
        assertNull(viewModel.uiState.value.usernameError)
    }

    @Test
    fun `onUsernameChanged with blank username should show error`() = runTest {
        // Given
        val blankUsername = ""

        // When
        viewModel.onUsernameChanged(blankUsername)

        // Then
        assertEquals(blankUsername, viewModel.uiState.value.username)
        assertEquals("用户名不能为空", viewModel.uiState.value.usernameError)
    }

    @Test
    fun `onUsernameChanged with short username should show error`() = runTest {
        // Given
        val shortUsername = "ab"

        // When
        viewModel.onUsernameChanged(shortUsername)

        // Then
        assertEquals(shortUsername, viewModel.uiState.value.username)
        assertEquals("用户名至少3个字符", viewModel.uiState.value.usernameError)
    }

    @Test
    fun `onUsernameChanged with long username should show error`() = runTest {
        // Given
        val longUsername = "a".repeat(21)

        // When
        viewModel.onUsernameChanged(longUsername)

        // Then
        assertEquals(longUsername, viewModel.uiState.value.username)
        assertEquals("用户名最多20个字符", viewModel.uiState.value.usernameError)
    }

    @Test
    fun `onUsernameChanged with boundary length should be valid`() = runTest {
        // Given - Exactly 3 characters (minimum)
        val minValidUsername = "abc"

        // When
        viewModel.onUsernameChanged(minValidUsername)

        // Then
        assertNull(viewModel.uiState.value.usernameError)

        // Given - Exactly 20 characters (maximum)
        val maxValidUsername = "a".repeat(20)

        // When
        viewModel.onUsernameChanged(maxValidUsername)

        // Then
        assertNull(viewModel.uiState.value.usernameError)
    }

    // ==================== 密码输入测试 ====================

    @Test
    fun `onPasswordChanged with valid password should update state`() = runTest {
        // Given
        val validPassword = "password123"

        // When
        viewModel.onPasswordChanged(validPassword)

        // Then
        assertEquals(validPassword, viewModel.uiState.value.password)
        assertNull(viewModel.uiState.value.passwordError)
    }

    @Test
    fun `onPasswordChanged with blank password should show error`() = runTest {
        // Given
        val blankPassword = ""

        // When
        viewModel.onPasswordChanged(blankPassword)

        // Then
        assertEquals(blankPassword, viewModel.uiState.value.password)
        assertEquals("密码不能为空", viewModel.uiState.value.passwordError)
    }

    @Test
    fun `onPasswordChanged with short password should show error`() = runTest {
        // Given
        val shortPassword = "12345"

        // When
        viewModel.onPasswordChanged(shortPassword)

        // Then
        assertEquals(shortPassword, viewModel.uiState.value.password)
        assertEquals("密码至少6个字符", viewModel.uiState.value.passwordError)
    }

    @Test
    fun `onPasswordChanged with long password should show error`() = runTest {
        // Given
        val longPassword = "a".repeat(21)

        // When
        viewModel.onPasswordChanged(longPassword)

        // Then
        assertEquals(longPassword, viewModel.uiState.value.password)
        assertEquals("密码最多20个字符", viewModel.uiState.value.passwordError)
    }

    @Test
    fun `onPasswordChanged with boundary length should be valid`() = runTest {
        // Given - Exactly 6 characters (minimum)
        val minValidPassword = "123456"

        // When
        viewModel.onPasswordChanged(minValidPassword)

        // Then
        assertNull(viewModel.uiState.value.passwordError)

        // Given - Exactly 20 characters (maximum)
        val maxValidPassword = "a".repeat(20)

        // When
        viewModel.onPasswordChanged(maxValidPassword)

        // Then
        assertNull(viewModel.uiState.value.passwordError)
    }

    // ==================== 表单验证测试 ====================

    @Test
    fun `isFormValid should be true when both fields valid`() = runTest {
        // Given
        viewModel.onUsernameChanged("validuser")
        viewModel.onPasswordChanged("validpass123")

        // Then
        assertTrue(viewModel.uiState.value.isFormValid)
    }

    @Test
    fun `isFormValid should be false when username invalid`() = runTest {
        // Given
        viewModel.onUsernameChanged("ab") // Too short
        viewModel.onPasswordChanged("validpass123")

        // Then
        assertFalse(viewModel.uiState.value.isFormValid)
    }

    @Test
    fun `isFormValid should be false when password invalid`() = runTest {
        // Given
        viewModel.onUsernameChanged("validuser")
        viewModel.onPasswordChanged("12345") // Too short

        // Then
        assertFalse(viewModel.uiState.value.isFormValid)
    }

    @Test
    fun `isFormValid should be false when both fields invalid`() = runTest {
        // Given
        viewModel.onUsernameChanged("")
        viewModel.onPasswordChanged("")

        // Then
        assertFalse(viewModel.uiState.value.isFormValid)
    }

    // ==================== 密码可见性测试 ====================

    @Test
    fun `onPasswordVisibilityChanged should toggle visibility`() = runTest {
        // Given - Initial state
        assertFalse(viewModel.uiState.value.isPasswordVisible)

        // When - Toggle on
        viewModel.onPasswordVisibilityChanged()

        // Then
        assertTrue(viewModel.uiState.value.isPasswordVisible)

        // When - Toggle off
        viewModel.onPasswordVisibilityChanged()

        // Then
        assertFalse(viewModel.uiState.value.isPasswordVisible)
    }

    // ==================== 记住密码测试 ====================

    @Test
    fun `onRememberPasswordChanged should update state`() = runTest {
        // Given - Initial state
        assertFalse(viewModel.uiState.value.rememberPassword)

        // When - Set to true
        viewModel.onRememberPasswordChanged(true)

        // Then
        assertTrue(viewModel.uiState.value.rememberPassword)

        // When - Set to false
        viewModel.onRememberPasswordChanged(false)

        // Then
        assertFalse(viewModel.uiState.value.rememberPassword)
    }

    // ==================== 登录成功测试 ====================

    @Test
    fun `login with valid credentials should emit success state`() = runTest {
        // Given
        val mockResponse = LoginResponse(
            userId = 1,
            userIdNo = "USER001",
            userName = "testuser",
            userTel = "13800138000",
            userToken = "token123",
            userType = 1,
            houseId = 1,
            status = 1
        )
        coEvery { userRepository.login(any(), any()) } returns flowOf(ApiResult.Success(mockResponse))

        viewModel.onUsernameChanged("testuser")
        viewModel.onPasswordChanged("password123")

        // When & Then
        viewModel.uiState.test {
            // Initial state
            awaitItem()

            viewModel.login()

            // Loading state
            val loadingState = awaitItem()
            assertTrue(loadingState.loginState is UiDataState.Loading)

            // Success state
            val successState = awaitItem()
            assertTrue(successState.loginState is UiDataState.Success)
            assertEquals(mockResponse, (successState.loginState as UiDataState.Success).data)

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `login should call repository with correct credentials`() = runTest {
        // Given
        val username = "testuser"
        val password = "password123"
        coEvery { userRepository.login(any(), any()) } returns flowOf(
            ApiResult.Loading,
            ApiResult.Success(LoginResponse(1, "USER001", username, "13800138000", "token", 1, 1, 1))
        )

        viewModel.onUsernameChanged(username)
        viewModel.onPasswordChanged(password)

        // When
        viewModel.login()
        advanceUntilIdle()

        // Then
        coVerify { userRepository.login(username, password) }
    }

    // ==================== 登录失败测试 ====================

    @Test
    fun `login with invalid credentials should emit error state`() = runTest {
        // Given
        val exception = Exception("Invalid credentials")
        coEvery { userRepository.login(any(), any()) } returns flowOf(ApiResult.Error(exception))

        viewModel.onUsernameChanged("testuser")
        viewModel.onPasswordChanged("wrongpassword")

        // When & Then
        viewModel.uiState.test {
            // Initial state
            awaitItem()

            viewModel.login()

            // Loading state
            val loadingState = awaitItem()
            assertTrue(loadingState.loginState is UiDataState.Loading)

            // Error state
            val errorState = awaitItem()
            assertTrue(errorState.loginState is UiDataState.Error)

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `login with empty username should show validation error`() = runTest {
        // Given
        viewModel.onUsernameChanged("")
        viewModel.onPasswordChanged("password123")

        // When
        viewModel.login()

        // Then
        assertEquals("用户名不能为空", viewModel.uiState.value.usernameError)
        assertTrue(viewModel.uiState.value.loginState is UiDataState.Idle)
        coVerify(exactly = 0) { userRepository.login(any(), any()) }
    }

    @Test
    fun `login with empty password should show validation error`() = runTest {
        // Given
        viewModel.onUsernameChanged("testuser")
        viewModel.onPasswordChanged("")

        // When
        viewModel.login()

        // Then
        assertEquals("密码不能为空", viewModel.uiState.value.passwordError)
        assertTrue(viewModel.uiState.value.loginState is UiDataState.Idle)
        coVerify(exactly = 0) { userRepository.login(any(), any()) }
    }

    @Test
    fun `login with invalid form should not call repository`() = runTest {
        // Given
        viewModel.onUsernameChanged("ab") // Too short
        viewModel.onPasswordChanged("12345") // Too short

        // When
        viewModel.login()

        // Then
        coVerify(exactly = 0) { userRepository.login(any(), any()) }
    }

    // ==================== 状态清除测试 ====================

    @Test
    fun `clearLoginState should reset to idle`() = runTest {
        // Given
        coEvery { userRepository.login(any(), any()) } returns flowOf(
            ApiResult.Success(LoginResponse(1, "USER001", "test", "13800138000", "token", 1, 1, 1))
        )
        viewModel.onUsernameChanged("testuser")
        viewModel.onPasswordChanged("password123")
        viewModel.login()
        advanceUntilIdle()

        // Verify success state
        assertTrue(viewModel.uiState.value.loginState is UiDataState.Success)

        // When
        viewModel.clearLoginState()

        // Then
        assertTrue(viewModel.uiState.value.loginState is UiDataState.Idle)
    }

    @Test
    fun `clearErrors should remove all validation errors`() = runTest {
        // Given
        viewModel.onUsernameChanged("")
        viewModel.onPasswordChanged("")
        assertNotNull(viewModel.uiState.value.usernameError)
        assertNotNull(viewModel.uiState.value.passwordError)

        // When
        viewModel.clearErrors()

        // Then
        assertNull(viewModel.uiState.value.usernameError)
        assertNull(viewModel.uiState.value.passwordError)
    }

    // ==================== 边界条件测试 ====================

    @Test
    fun `login with special characters in username should handle correctly`() = runTest {
        // Given
        val specialUsername = "user_123.test"
        coEvery { userRepository.login(any(), any()) } returns flowOf(
            ApiResult.Success(LoginResponse(1, "USER001", specialUsername, "13800138000", "token", 1, 1, 1))
        )

        viewModel.onUsernameChanged(specialUsername)
        viewModel.onPasswordChanged("password123")

        // When
        viewModel.login()
        advanceUntilIdle()

        // Then
        coVerify { userRepository.login(specialUsername, "password123") }
    }

    @Test
    fun `login with unicode characters should handle correctly`() = runTest {
        // Given
        val unicodeUsername = "用户123"
        coEvery { userRepository.login(any(), any()) } returns flowOf(
            ApiResult.Success(LoginResponse(1, "USER001", unicodeUsername, "13800138000", "token", 1, 1, 1))
        )

        viewModel.onUsernameChanged(unicodeUsername)
        viewModel.onPasswordChanged("password123")

        // When
        viewModel.login()
        advanceUntilIdle()

        // Then
        coVerify { userRepository.login(unicodeUsername, "password123") }
    }

    @Test
    fun `multiple rapid login attempts should handle correctly`() = runTest {
        // Given
        coEvery { userRepository.login(any(), any()) } returns flowOf(
            ApiResult.Loading,
            ApiResult.Success(LoginResponse(1, "USER001", "test", "13800138000", "token", 1, 1, 1))
        )

        viewModel.onUsernameChanged("testuser")
        viewModel.onPasswordChanged("password123")

        // When - Multiple rapid calls
        viewModel.login()
        viewModel.login()
        viewModel.login()
        advanceUntilIdle()

        // Then - Repository should be called multiple times (no debounce in current implementation)
        coVerify(atLeast = 1) { userRepository.login(any(), any()) }
    }
}
