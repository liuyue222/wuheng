package com.wuheng.smart.presentation.login

import app.cash.turbine.test
import com.wuheng.smart.MainDispatcherRule
import com.wuheng.smart.data.model.LoginResponse
import com.wuheng.smart.data.network.ApiResult
import com.wuheng.smart.data.network.AppException
import com.wuheng.smart.data.repository.UserRepository
import com.wuheng.smart.presentation.base.UiDataState
import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import org.junit.jupiter.params.provider.ValueSource

/**
 * LoginViewModel 单元测试
 */
@ExperimentalCoroutinesApi
@ExtendWith(MainDispatcherRule::class)
@DisplayName("LoginViewModel Tests")
class LoginViewModelTest {

    private lateinit var viewModel: LoginViewModel
    private lateinit var mockUserRepository: UserRepository

    @BeforeEach
    fun setup() {
        mockUserRepository = mockk(relaxed = true)
        
        // 设置默认的登录响应
        val mockResponse = LoginResponse(
            userId = 1,
            userIdNo = "USER001",
            userName = "Test User",
            userTel = "13800138000",
            userToken = "test_token",
            userType = 1,
            houseId = 1,
            status = 1
        )
        coEvery { mockUserRepository.login(any(), any(), any()) } returns flowOf(ApiResult.Success(mockResponse))

        viewModel = LoginViewModel(mockUserRepository)
    }

    @AfterEach
    fun tearDown() {
        unmockkAll()
    }

    @Test
    @DisplayName("初始状态 - 登录状态应为Idle")
    fun `initial state - login state should be Idle`() = runTest {
        assertTrue(viewModel.loginState.value is UiDataState.Idle)
    }

    @Test
    @DisplayName("初始状态 - 验证错误应为null")
    fun `initial state - validation error should be null`() = runTest {
        assertNull(viewModel.validationError.value)
    }

    @Test
    @DisplayName("表单验证 - 空手机号应返回错误")
    fun `validation - empty phone should return error`() = runTest {
        viewModel.login("", "123456", false)
        advanceUntilIdle()

        assertEquals("请输入手机号", viewModel.validationError.value)
        assertTrue(viewModel.loginState.value is UiDataState.Idle)
    }

    @Test
    @DisplayName("表单验证 - 空密码应返回错误")
    fun `validation - empty password should return error`() = runTest {
        viewModel.login("13800138000", "", false)
        advanceUntilIdle()

        assertEquals("请输入密码", viewModel.validationError.value)
        assertTrue(viewModel.loginState.value is UiDataState.Idle)
    }

    @ParameterizedTest
    @ValueSource(strings = ["123", "12345678901", "abcdefghijk", "1380013800", "23800138000"])
    @DisplayName("表单验证 - 无效手机号格式应返回错误")
    fun `validation - invalid phone format should return error`(phone: String) = runTest {
        viewModel.login(phone, "123456", false)
        advanceUntilIdle()

        assertEquals("请输入正确的11位手机号", viewModel.validationError.value)
    }

    @ParameterizedTest
    @ValueSource(strings = ["12345", "1234", "1"])
    @DisplayName("表单验证 - 密码长度小于6位应返回错误")
    fun `validation - password less than 6 chars should return error`(password: String) = runTest {
        viewModel.login("13800138000", password, false)
        advanceUntilIdle()

        assertEquals("密码长度至少为6位", viewModel.validationError.value)
    }

    @Test
    @DisplayName("表单验证 - 空密码应返回请输入密码错误")
    fun `validation - empty password should return enter password error`() = runTest {
        viewModel.login("13800138000", "", false)
        advanceUntilIdle()

        assertEquals("请输入密码", viewModel.validationError.value)
    }

    @Test
    @DisplayName("表单验证 - 有效手机号和密码应通过验证")
    fun `validation - valid phone and password should pass`() = runTest {
        viewModel.login("13800138000", "123456", false)
        advanceUntilIdle()

        assertNull(viewModel.validationError.value)
        coVerify { mockUserRepository.login("13800138000", "123456", any()) }
    }

    @ParameterizedTest
    @CsvSource(
        "13800138000, 123456",
        "13900139000, password123",
        "15000150000, 12345678"
    )
    @DisplayName("表单验证 - 多种有效输入组合应通过验证")
    fun `validation - various valid inputs should pass`(phone: String, password: String) = runTest {
        viewModel.login(phone, password, false)
        advanceUntilIdle()

        assertNull(viewModel.validationError.value)
    }

    @Test
    @DisplayName("登录成功 - 状态应变为Success")
    fun `login success - state should become Success`() = runTest {
        val mockResponse = LoginResponse(
            userId = 1,
            userIdNo = "USER001",
            userName = "Test User",
            userTel = "13800138000",
            userToken = "test_token_123",
            userType = 1,
            houseId = 1,
            status = 1
        )
        coEvery { mockUserRepository.login("13800138000", "123456", any()) } returns flowOf(ApiResult.Success(mockResponse))

        viewModel.login("13800138000", "123456", false)
        advanceUntilIdle()

        assertTrue(viewModel.loginState.value is UiDataState.Success)
    }

    @Test
    @DisplayName("登录成功 - 不记住密码时不应保存凭证")
    fun `login success without remember - should not save credentials`() = runTest {
        viewModel.login("13800138000", "123456", false)
        advanceUntilIdle()

        coVerify { mockUserRepository.clearLoginCredentials() }
        coVerify(exactly = 0) { mockUserRepository.saveLoginCredentials(any(), any()) }
    }

    @Test
    @DisplayName("登录成功 - 记住密码时应保存凭证")
    fun `login success with remember - should save credentials`() = runTest {
        viewModel.login("13800138000", "123456", true)
        advanceUntilIdle()

        coVerify { mockUserRepository.saveLoginCredentials("13800138000", "123456") }
        coVerify(exactly = 0) { mockUserRepository.clearLoginCredentials() }
    }

    @Test
    @DisplayName("登录失败 - 网络错误应返回Error状态")
    fun `login failure - network error should return Error`() = runTest {
        coEvery { mockUserRepository.login(any(), any(), any()) } returns flowOf(ApiResult.Error(AppException.NetworkError()))

        viewModel.login("13800138000", "123456", false)
        advanceUntilIdle()

        val state = viewModel.loginState.value
        assertTrue(state is UiDataState.Error)
        assertEquals("网络连接失败", (state as UiDataState.Error).exception.message)
    }

    @Test
    @DisplayName("登录失败 - 服务器错误应返回Error状态")
    fun `login failure - server error should return Error`() = runTest {
        coEvery { mockUserRepository.login(any(), any(), any()) } returns flowOf(ApiResult.Error(AppException.ServerError(500, "服务器内部错误")))

        viewModel.login("13800138000", "123456", false)
        advanceUntilIdle()

        assertTrue(viewModel.loginState.value is UiDataState.Error)
    }

    @Test
    @DisplayName("登录失败 - 业务错误应返回Error状态")
    fun `login failure - business error should return Error`() = runTest {
        coEvery { mockUserRepository.login(any(), any(), any()) } returns flowOf(ApiResult.Error(AppException.BusinessError(1001, "用户名或密码错误")))

        viewModel.login("13800138000", "wrong_password", false)
        advanceUntilIdle()

        assertTrue(viewModel.loginState.value is UiDataState.Error)
    }

    @Test
    @DisplayName("登录失败 - 超时错误应返回Error状态")
    fun `login failure - timeout error should return Error`() = runTest {
        coEvery { mockUserRepository.login(any(), any(), any()) } returns flowOf(ApiResult.Error(AppException.TimeoutError()))

        viewModel.login("13800138000", "123456", false)
        advanceUntilIdle()

        val state = viewModel.loginState.value
        assertTrue(state is UiDataState.Error)
        assertEquals("请求超时", (state as UiDataState.Error).exception.message)
    }

    @Test
    @DisplayName("登录失败 - 未授权错误应返回Error状态")
    fun `login failure - unauthorized error should return Error`() = runTest {
        coEvery { mockUserRepository.login(any(), any(), any()) } returns flowOf(ApiResult.Error(AppException.Unauthorized()))

        viewModel.login("13800138000", "123456", false)
        advanceUntilIdle()

        assertTrue(viewModel.loginState.value is UiDataState.Error)
    }

    @Test
    @DisplayName("状态重置 - resetState应将状态重置为Idle")
    fun `reset state - should reset to Idle`() = runTest {
        coEvery { mockUserRepository.login(any(), any(), any()) } returns flowOf(ApiResult.Error(AppException.NetworkError()))
        viewModel.login("13800138000", "123456", false)
        advanceUntilIdle()

        assertTrue(viewModel.loginState.value is UiDataState.Error)

        viewModel.resetState()

        assertTrue(viewModel.loginState.value is UiDataState.Idle)
        assertNull(viewModel.validationError.value)
    }

    @Test
    @DisplayName("状态重置 - clearValidationError应清除验证错误")
    fun `clear validation error - should clear error`() = runTest {
        viewModel.login("", "123456", false)
        advanceUntilIdle()

        assertNotNull(viewModel.validationError.value)

        viewModel.clearValidationError()

        assertNull(viewModel.validationError.value)
    }

    @Test
    @DisplayName("加载状态 - 登录时应先变为Loading")
    fun `loading state - should become Loading during login`() = runTest {
        viewModel.loginState.test {
            assertTrue(awaitItem() is UiDataState.Idle)

            viewModel.login("13800138000", "123456", false)

            assertTrue(awaitItem() is UiDataState.Loading)

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    @DisplayName("边界条件 - 手机号边界值13800000000应通过")
    fun `boundary - phone 13800000000 should pass`() = runTest {
        viewModel.login("13800000000", "123456", false)
        advanceUntilIdle()

        assertNull(viewModel.validationError.value)
    }

    @Test
    @DisplayName("边界条件 - 手机号边界值19999999999应通过")
    fun `boundary - phone 19999999999 should pass`() = runTest {
        viewModel.login("19999999999", "123456", false)
        advanceUntilIdle()

        assertNull(viewModel.validationError.value)
    }

    @Test
    @DisplayName("边界条件 - 密码恰好6位应通过")
    fun `boundary - password exactly 6 chars should pass`() = runTest {
        viewModel.login("13800138000", "123456", false)
        advanceUntilIdle()

        assertNull(viewModel.validationError.value)
    }

    @Test
    @DisplayName("边界条件 - 超长密码应通过")
    fun `boundary - very long password should pass`() = runTest {
        val longPassword = "a".repeat(100)
        viewModel.login("13800138000", longPassword, false)
        advanceUntilIdle()

        assertNull(viewModel.validationError.value)
    }

    @Test
    @DisplayName("并发安全 - 快速多次调用登录应处理正确")
    fun `concurrency - rapid login calls should handle correctly`() = runTest {
        val mockResponse = LoginResponse(
            userId = 1,
            userIdNo = "USER001",
            userName = "Test User",
            userTel = "13800138000",
            userToken = "test_token",
            userType = 1,
            houseId = 1,
            status = 1
        )
        coEvery { mockUserRepository.login(any(), any(), any()) } returns flowOf(ApiResult.Success(mockResponse))

        viewModel.login("13800138000", "123456", false)
        viewModel.login("13800138000", "123456", false)
        viewModel.login("13800138000", "123456", false)

        advanceUntilIdle()

        assertTrue(viewModel.loginState.value is UiDataState.Success)
    }
}
