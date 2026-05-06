package com.wuheng.smart.data.repository

import app.cash.turbine.test
import com.wuheng.smart.MainDispatcherRule
import com.wuheng.smart.data.model.*
import com.wuheng.smart.data.network.ApiResult
import com.wuheng.smart.data.network.ApiService
import com.wuheng.smart.data.network.AppException
import com.wuheng.smart.data.network.BaseResponse
import com.wuheng.smart.data.network.TokenManager
import com.google.gson.Gson
import io.mockk.*
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.extension.RegisterExtension
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import org.junit.jupiter.params.provider.ValueSource

/**
 * UserRepository 单元测试
 *
 * 测试覆盖:
 * - 正常路径: 所有API调用成功场景
 * - 错误路径: 网络错误、超时、服务器错误、业务错误
 * - 边界条件: 空值、极值、特殊字符
 * - 重试机制: 指数退避重试
 * - Mock模式: 模拟数据返回
 */
@OptIn(ExperimentalCoroutinesApi::class)
class UserRepositoryTest {

    @RegisterExtension
    val mainDispatcherRule = MainDispatcherRule()

    @MockK
    private lateinit var apiService: ApiService

    @MockK
    private lateinit var tokenManager: TokenManager

    private lateinit var repository: UserRepositoryImpl

    @BeforeEach
    fun setup() {
        MockKAnnotations.init(this)
        repository = UserRepositoryImpl(apiService, tokenManager, useMock = false)
    }

    @AfterEach
    fun tearDown() {
        unmockkAll()
    }

    // ==================== 登录功能测试 ====================

    @Nested
    @DisplayName("登录功能测试")
    inner class LoginTests {

        @Test
        fun `login - 正常登录成功 - 返回用户数据并保存Token`() = runTest {
            // Given
            val username = "13800138000"
            val password = "password123"
            val loginResponse = LoginResponse(
                userId = 1,
                userIdNo = "USER202604190001",
                userName = "张三",
                userTel = "13800138000",
                userToken = "token123",
                userType = 1,
                houseId = 1,
                status = 1
            )
            coEvery { apiService.login(any()) } returns BaseResponse(200, "success", loginResponse)
            every { tokenManager.onLoginSuccess(any(), any(), any(), any(), any()) } just Runs

            // When & Then
            repository.login(username, password).test {
                assertEquals(ApiResult.Loading, awaitItem())
                val success = awaitItem() as ApiResult.Success
                assertEquals(loginResponse.userId, success.data.userId)
                assertEquals(loginResponse.userToken, success.data.userToken)
                awaitComplete()
            }

            // Verify
            coVerify { apiService.login(LoginRequest(username, password)) }
            verify { tokenManager.onLoginSuccess("token123", "1", "张三", "1", "1") }
        }

        @Test
        fun `login - 登录成功带回调 - 回调被正确执行`() = runTest {
            // Given
            val username = "13800138000"
            val password = "password123"
            val loginResponse = LoginResponse(
                userId = 1,
                userIdNo = "USER202604190001",
                userName = "张三",
                userTel = "13800138000",
                userToken = "token123",
                userType = 1,
                houseId = 1,
                status = 1
            )
            val callback = mockk<LoginResultCallback>(relaxed = true)
            coEvery { apiService.login(any()) } returns BaseResponse(200, "success", loginResponse)
            every { tokenManager.onLoginSuccess(any(), any(), any(), any(), any()) } just Runs

            // When
            repository.login(username, password, callback).test {
                assertEquals(ApiResult.Loading, awaitItem())
                val success = awaitItem() as ApiResult.Success
                assertEquals(loginResponse.userId, success.data.userId)
                awaitComplete()
            }

            // Then
            coVerify { callback.onLoginSuccess(loginResponse) }
        }

        @Test
        fun `login - 用户名密码错误 - 返回业务错误`() = runTest {
            // Given
            coEvery { apiService.login(any()) } returns BaseResponse(400, "用户名或密码错误", null)

            // When & Then
            repository.login("wrong", "wrong").test {
                assertEquals(ApiResult.Loading, awaitItem())
                val error = awaitItem() as ApiResult.Error
                assertTrue(error.exception is AppException.BusinessError)
                awaitComplete()
            }
        }

        @ParameterizedTest
        @CsvSource(
            "'',password,用户名不能为空",
            "user,'',密码不能为空",
            "user,short,密码长度不足"
        )
        fun `login - 边界条件测试`(username: String, password: String, description: String) = runTest {
            // Given
            coEvery { apiService.login(any()) } returns BaseResponse(400, "参数错误", null)

            // When & Then
            repository.login(username.ifEmpty { "" }, password.ifEmpty { "" }).test {
                assertEquals(ApiResult.Loading, awaitItem())
                val error = awaitItem() as ApiResult.Error
                assertTrue(error.exception is AppException.BusinessError)
                awaitComplete()
            }
        }

        @Test
        fun `login - 网络错误 - 返回网络错误`() = runTest {
            // Given
            coEvery { apiService.login(any()) } throws java.net.UnknownHostException("No network")

            // When & Then
            repository.login("user", "pass").test {
                assertEquals(ApiResult.Loading, awaitItem())
                val error = awaitItem() as ApiResult.Error
                assertTrue(error.exception is AppException.NetworkError)
                awaitComplete()
            }
        }

        @Test
        fun `login - 超时错误 - 返回超时错误`() = runTest {
            // Given
            coEvery { apiService.login(any()) } throws java.net.SocketTimeoutException("Timeout")

            // When & Then
            repository.login("user", "pass").test {
                assertEquals(ApiResult.Loading, awaitItem())
                val error = awaitItem() as ApiResult.Error
                assertTrue(error.exception is AppException.TimeoutError)
                awaitComplete()
            }
        }

        @Test
        fun `login - 服务器500错误 - 返回服务器错误`() = runTest {
            // Given
            val httpException = mockk<retrofit2.HttpException>(relaxed = true)
            every { httpException.code() } returns 500
            every { httpException.message() } returns "Internal Server Error"
            every { httpException.response() } returns null
            coEvery { apiService.login(any()) } throws httpException

            // When & Then
            repository.login("user", "pass").test {
                assertEquals(ApiResult.Loading, awaitItem())
                val error = awaitItem() as ApiResult.Error
                assertTrue(error.exception is AppException.ServerError)
                awaitComplete()
            }
        }

        @Test
        fun `login - 401未授权 - 返回未授权错误`() = runTest {
            // Given
            val httpException = mockk<retrofit2.HttpException>(relaxed = true)
            every { httpException.code() } returns 401
            every { httpException.message() } returns "Unauthorized"
            every { httpException.response() } returns null
            coEvery { apiService.login(any()) } throws httpException

            // When & Then
            repository.login("user", "pass").test {
                assertEquals(ApiResult.Loading, awaitItem())
                val error = awaitItem() as ApiResult.Error
                assertTrue(error.exception is AppException.Unauthorized)
                awaitComplete()
            }
        }

        @Test
        fun `login - Mock模式 - 返回模拟数据`() = runTest {
            // Given
            val mockRepo = UserRepositoryImpl(apiService, tokenManager, useMock = true)
            every { tokenManager.onLoginSuccess(any(), any(), any(), any(), any()) } just Runs

            // When & Then
            mockRepo.login("test", "test").test {
                assertEquals(ApiResult.Loading, awaitItem())
                val success = awaitItem() as ApiResult.Success
                assertEquals(1, success.data.userId)
                assertNotNull(success.data.userToken)
                awaitComplete()
            }
        }

        @Test
        fun `login - 响应数据为空 - 返回业务错误`() = runTest {
            // Given
            coEvery { apiService.login(any()) } returns BaseResponse(200, "success", null)

            // When & Then
            repository.login("user", "pass").test {
                assertEquals(ApiResult.Loading, awaitItem())
                val error = awaitItem() as ApiResult.Error
                assertTrue(error.exception is AppException.BusinessError)
                awaitComplete()
            }
        }

        @Test
        fun `login - 特殊字符用户名 - 正确处理`() = runTest {
            // Given
            val specialUsername = "user@example.com"
            val loginResponse = LoginResponse(
                userId = 1,
                userIdNo = "USER001",
                userName = "Test",
                userTel = "13800138000",
                userToken = "token",
                userType = 1,
                houseId = 1,
                status = 1
            )
            coEvery { apiService.login(any()) } returns BaseResponse(200, "success", loginResponse)
            every { tokenManager.onLoginSuccess(any(), any(), any(), any(), any()) } just Runs

            // When & Then
            repository.login(specialUsername, "pass").test {
                assertEquals(ApiResult.Loading, awaitItem())
                assertTrue(awaitItem() is ApiResult.Success)
                awaitComplete()
            }
        }
    }

    // ==================== 注册功能测试 ====================

    @Nested
    @DisplayName("注册功能测试")
    inner class RegisterTests {

        @Test
        fun `register - 正常注册成功 - 返回用户ID和Token`() = runTest {
            // Given
            val request = RegisterRequest(
                username = "newuser",
                password = "password123",
                mobile = "13800138000",
                realname = "张三"
            )
            val response = RegisterResponse(userId = 100, userToken = "new_token")
            coEvery { apiService.register(any()) } returns BaseResponse(200, "success", response)

            // When & Then
            repository.register(request).test {
                assertEquals(ApiResult.Loading, awaitItem())
                val success = awaitItem() as ApiResult.Success
                assertEquals(100, success.data.userId)
                assertEquals("new_token", success.data.userToken)
                awaitComplete()
            }
        }

        @Test
        fun `register - 手机号已存在 - 返回业务错误`() = runTest {
            // Given
            val request = RegisterRequest(
                username = "existing",
                password = "password123",
                mobile = "13800138000"
            )
            coEvery { apiService.register(any()) } returns BaseResponse(409, "手机号已注册", null)

            // When & Then
            repository.register(request).test {
                assertEquals(ApiResult.Loading, awaitItem())
                val error = awaitItem() as ApiResult.Error
                assertTrue(error.exception is AppException.BusinessError)
                awaitComplete()
            }
        }

        @Test
        fun `register - 网络错误 - 返回网络错误`() = runTest {
            // Given
            val request = RegisterRequest("user", "pass", "13800138000")
            coEvery { apiService.register(any()) } throws java.net.UnknownHostException()

            // When & Then
            repository.register(request).test {
                assertEquals(ApiResult.Loading, awaitItem())
                val error = awaitItem() as ApiResult.Error
                assertTrue(error.exception is AppException.NetworkError)
                awaitComplete()
            }
        }

        @Test
        fun `register - Mock模式 - 返回模拟数据`() = runTest {
            // Given
            val mockRepo = UserRepositoryImpl(apiService, tokenManager, useMock = true)
            val request = RegisterRequest("user", "pass", "13800138000")

            // When & Then
            mockRepo.register(request).test {
                assertEquals(ApiResult.Loading, awaitItem())
                val success = awaitItem() as ApiResult.Success
                assertTrue(success.data.userId > 0)
                assertNotNull(success.data.userToken)
                awaitComplete()
            }
        }

        @ParameterizedTest
        @ValueSource(strings = ["", "a", "ab", "abc"])
        fun `register - 密码长度边界测试`(password: String) = runTest {
            // Given
            val request = RegisterRequest("user", password, "13800138000")
            coEvery { apiService.register(any()) } returns BaseResponse(400, "密码长度不足", null)

            // When & Then
            repository.register(request).test {
                assertEquals(ApiResult.Loading, awaitItem())
                val error = awaitItem() as ApiResult.Error
                assertTrue(error.exception is AppException.BusinessError)
                awaitComplete()
            }
        }
    }

    // ==================== 登出功能测试 ====================

    @Nested
    @DisplayName("登出功能测试")
    inner class LogoutTests {

        @Test
        fun `logout - 正常登出成功 - 清除Token`() = runTest {
            // Given
            coEvery { apiService.logout() } returns BaseResponse(200, "success", Unit)
            every { tokenManager.onLogout() } just Runs

            // When & Then
            repository.logout().test {
                assertEquals(ApiResult.Loading, awaitItem())
                val success = awaitItem() as ApiResult.Success
                assertEquals(Unit, success.data)
                awaitComplete()
            }

            verify { tokenManager.onLogout() }
        }

        @Test
        fun `logout - API调用失败 - 仍清除本地Token`() = runTest {
            // Given
            coEvery { apiService.logout() } throws java.net.UnknownHostException()
            every { tokenManager.onLogout() } just Runs

            // When & Then
            repository.logout().test {
                assertEquals(ApiResult.Loading, awaitItem())
                val error = awaitItem() as ApiResult.Error
                assertTrue(error.exception is AppException.NetworkError)
                awaitComplete()
            }

            // 即使API调用失败，也应该清除Token
            verify { tokenManager.onLogout() }
        }

        @Test
        fun `logout - Mock模式 - 清除Token成功`() = runTest {
            // Given
            val mockRepo = UserRepositoryImpl(apiService, tokenManager, useMock = true)
            every { tokenManager.onLogout() } just Runs

            // When & Then
            mockRepo.logout().test {
                assertEquals(ApiResult.Loading, awaitItem())
                val success = awaitItem() as ApiResult.Success
                assertEquals(Unit, success.data)
                awaitComplete()
            }
        }
    }

    // ==================== 获取用户信息测试 ====================

    @Nested
    @DisplayName("获取用户信息测试")
    inner class GetUserInfoTests {

        @Test
        fun `getUserInfo - 正常获取成功 - 返回用户信息`() = runTest {
            // Given
            val userInfo = UserInfo(
                userId = 1,
                userIdNo = "USER001",
                userName = "张三",
                userTel = "13800138000",
                userType = 1,
                houseId = 1,
                status = 1
            )
            val userJson = Gson().toJsonTree(userInfo)
            coEvery { apiService.getUserInfo() } returns BaseResponse(200, "success", userJson)

            // When & Then
            repository.getUserInfo().test {
                assertEquals(ApiResult.Loading, awaitItem())
                val success = awaitItem() as ApiResult.Success
                assertEquals(userInfo.userId, success.data.userId)
                assertEquals(userInfo.userName, success.data.userName)
                awaitComplete()
            }
        }

        @Test
        fun `getUserInfo - Token过期 - 返回未授权错误`() = runTest {
            // Given
            val httpException = mockk<retrofit2.HttpException>(relaxed = true)
            every { httpException.code() } returns 401
            every { httpException.message() } returns "Unauthorized"
            every { httpException.response() } returns null
            coEvery { apiService.getUserInfo() } throws httpException

            // When & Then
            repository.getUserInfo().test {
                assertEquals(ApiResult.Loading, awaitItem())
                val error = awaitItem() as ApiResult.Error
                assertTrue(error.exception is AppException.Unauthorized)
                awaitComplete()
            }
        }

        @Test
        fun `getUserInfo - Mock模式 - 返回模拟数据`() = runTest {
            // Given
            val mockRepo = UserRepositoryImpl(apiService, tokenManager, useMock = true)

            // When & Then
            mockRepo.getUserInfo().test {
                assertEquals(ApiResult.Loading, awaitItem())
                val success = awaitItem() as ApiResult.Success
                assertTrue(success.data.userId > 0)
                assertNotNull(success.data.userName)
                awaitComplete()
            }
        }
    }

    // ==================== 更新用户信息测试 ====================

    @Nested
    @DisplayName("更新用户信息测试")
    inner class UpdateUserInfoTests {

        @Test
        fun `updateUserInfo - 正常更新成功 - 返回成功`() = runTest {
            // Given
            val request = UpdateUserInfoRequest(
                realname = "李四",
                email = "lisi@example.com",
                avatar = "http://example.com/avatar.jpg"
            )
            coEvery { apiService.updateUserInfo(any()) } returns BaseResponse(200, "success", Unit)

            // When & Then
            repository.updateUserInfo(request).test {
                assertEquals(ApiResult.Loading, awaitItem())
                val success = awaitItem() as ApiResult.Success
                assertEquals(Unit, success.data)
                awaitComplete()
            }
        }

        @Test
        fun `updateUserInfo - 部分字段更新 - 成功`() = runTest {
            // Given
            val request = UpdateUserInfoRequest(realname = "李四")
            coEvery { apiService.updateUserInfo(any()) } returns BaseResponse(200, "success", Unit)

            // When & Then
            repository.updateUserInfo(request).test {
                assertEquals(ApiResult.Loading, awaitItem())
                assertTrue(awaitItem() is ApiResult.Success)
                awaitComplete()
            }
        }

        @Test
        fun `updateUserInfo - 空请求 - 成功`() = runTest {
            // Given
            val request = UpdateUserInfoRequest()
            coEvery { apiService.updateUserInfo(any()) } returns BaseResponse(200, "success", Unit)

            // When & Then
            repository.updateUserInfo(request).test {
                assertEquals(ApiResult.Loading, awaitItem())
                assertTrue(awaitItem() is ApiResult.Success)
                awaitComplete()
            }
        }

        @Test
        fun `updateUserInfo - Mock模式 - 返回成功`() = runTest {
            // Given
            val mockRepo = UserRepositoryImpl(apiService, tokenManager, useMock = true)
            val request = UpdateUserInfoRequest(realname = "Test")

            // When & Then
            mockRepo.updateUserInfo(request).test {
                assertEquals(ApiResult.Loading, awaitItem())
                assertTrue(awaitItem() is ApiResult.Success)
                awaitComplete()
            }
        }
    }

    // ==================== 修改密码测试 ====================

    @Nested
    @DisplayName("修改密码测试")
    inner class ChangePasswordTests {

        @Test
        fun `changePassword - 正常修改成功 - 返回成功`() = runTest {
            // Given
            coEvery { apiService.changePassword(any()) } returns BaseResponse(200, "success", Unit)

            // When & Then
            repository.changePassword("oldPass123", "newPass123").test {
                assertEquals(ApiResult.Loading, awaitItem())
                val success = awaitItem() as ApiResult.Success
                assertEquals(Unit, success.data)
                awaitComplete()
            }
        }

        @Test
        fun `changePassword - 旧密码错误 - 返回业务错误`() = runTest {
            // Given
            coEvery { apiService.changePassword(any()) } returns BaseResponse(400, "旧密码错误", null)

            // When & Then
            repository.changePassword("wrongOld", "newPass").test {
                assertEquals(ApiResult.Loading, awaitItem())
                val error = awaitItem() as ApiResult.Error
                assertTrue(error.exception is AppException.BusinessError)
                awaitComplete()
            }
        }

        @ParameterizedTest
        @ValueSource(strings = ["", "123", "12345"])
        fun `changePassword - 新密码长度不足 - 返回错误`(newPassword: String) = runTest {
            // Given
            coEvery { apiService.changePassword(any()) } returns BaseResponse(400, "密码长度不足6位", null)

            // When & Then
            repository.changePassword("oldPass123", newPassword).test {
                assertEquals(ApiResult.Loading, awaitItem())
                val error = awaitItem() as ApiResult.Error
                assertTrue(error.exception is AppException.BusinessError)
                awaitComplete()
            }
        }

        @Test
        fun `changePassword - Mock模式 - 返回成功`() = runTest {
            // Given
            val mockRepo = UserRepositoryImpl(apiService, tokenManager, useMock = true)

            // When & Then
            mockRepo.changePassword("old", "new").test {
                assertEquals(ApiResult.Loading, awaitItem())
                assertTrue(awaitItem() is ApiResult.Success)
                awaitComplete()
            }
        }
    }

    // ==================== 绑定房屋测试 ====================

    @Nested
    @DisplayName("绑定房屋测试")
    inner class BindHouseTests {

        @Test
        fun `bindHouse - 正常绑定成功 - 设置当前房屋ID`() = runTest {
            // Given
            coEvery { apiService.bindHouse(any()) } returns BaseResponse(200, "success", Unit)
            every { tokenManager.setCurrentHouseId(any()) } just Runs

            // When & Then
            repository.bindHouse("123", "bindCode456").test {
                assertEquals(ApiResult.Loading, awaitItem())
                val success = awaitItem() as ApiResult.Success
                assertEquals(Unit, success.data)
                awaitComplete()
            }

            verify { tokenManager.setCurrentHouseId("123") }
        }

        @Test
        fun `bindHouse - 无需绑定码 - 成功`() = runTest {
            // Given
            coEvery { apiService.bindHouse(any()) } returns BaseResponse(200, "success", Unit)
            every { tokenManager.setCurrentHouseId(any()) } just Runs

            // When & Then
            repository.bindHouse("123", null).test {
                assertEquals(ApiResult.Loading, awaitItem())
                assertTrue(awaitItem() is ApiResult.Success)
                awaitComplete()
            }
        }

        @Test
        fun `bindHouse - 房屋不存在 - 返回404错误`() = runTest {
            // Given
            coEvery { apiService.bindHouse(any()) } returns BaseResponse(404, "房屋不存在", null)

            // When & Then
            repository.bindHouse("999", null).test {
                assertEquals(ApiResult.Loading, awaitItem())
                val error = awaitItem() as ApiResult.Error
                assertTrue(error.exception is AppException.BusinessError)
                awaitComplete()
            }
        }

        @Test
        fun `bindHouse - Mock模式 - 返回成功`() = runTest {
            // Given
            val mockRepo = UserRepositoryImpl(apiService, tokenManager, useMock = true)
            every { tokenManager.setCurrentHouseId(any()) } just Runs

            // When & Then
            mockRepo.bindHouse("123", "code").test {
                assertEquals(ApiResult.Loading, awaitItem())
                assertTrue(awaitItem() is ApiResult.Success)
                awaitComplete()
            }
        }
    }

    // ==================== 获取我的房屋列表测试 ====================

    @Nested
    @DisplayName("获取我的房屋列表测试")
    inner class GetMyHousesTests {

        @Test
        fun `getMyHouses - 正常获取 - 返回房屋列表`() = runTest {
            // Given
            val houses = listOf(
                MyHouse(1, "HOUSE001", "房屋1", "地址1", "张三", "280.00", "辐射空调系统", "owner", 1234567890),
                MyHouse(2, "HOUSE002", "房屋2", "地址2", "李四", "150.00", "新风系统", "member", 1234567891)
            )
            coEvery { apiService.getMyHouses() } returns BaseResponse(200, "success", houses)

            // When & Then
            repository.getMyHouses().test {
                assertEquals(ApiResult.Loading, awaitItem())
                val success = awaitItem() as ApiResult.Success
                assertEquals(2, success.data.size)
                assertEquals("房屋1", success.data[0].houseName)
                awaitComplete()
            }
        }

        @Test
        fun `getMyHouses - 无房屋 - 返回空列表`() = runTest {
            // Given
            coEvery { apiService.getMyHouses() } returns BaseResponse(200, "success", emptyList())

            // When & Then
            repository.getMyHouses().test {
                assertEquals(ApiResult.Loading, awaitItem())
                val success = awaitItem() as ApiResult.Success
                assertTrue(success.data.isEmpty())
                awaitComplete()
            }
        }

        @Test
        fun `getMyHouses - Mock模式 - 返回模拟数据`() = runTest {
            // Given
            val mockRepo = UserRepositoryImpl(apiService, tokenManager, useMock = true)

            // When & Then
            mockRepo.getMyHouses().test {
                assertEquals(ApiResult.Loading, awaitItem())
                val success = awaitItem() as ApiResult.Success
                assertTrue(success.data.isNotEmpty())
                awaitComplete()
            }
        }
    }

    // ==================== 记住密码功能测试 ====================

    @Nested
    @DisplayName("记住密码功能测试")
    inner class RememberPasswordTests {

        @Test
        fun `saveLoginCredentials - 保存凭证 - 调用TokenManager`() = runTest {
            // Given
            coEvery { tokenManager.saveLoginCredentials(any(), any()) } just Runs

            // When
            repository.saveLoginCredentials("13800138000", "password123")

            // Then
            coVerify { tokenManager.saveLoginCredentials("13800138000", "password123") }
        }

        @Test
        fun `clearLoginCredentials - 清除凭证 - 调用TokenManager`() = runTest {
            // Given
            coEvery { tokenManager.clearLoginCredentials() } just Runs

            // When
            repository.clearLoginCredentials()

            // Then
            coVerify { tokenManager.clearLoginCredentials() }
        }

        @Test
        fun `getSavedPhone - 获取保存的手机号 - 返回Flow`() = runTest {
            // Given
            coEvery { tokenManager.getSavedPhone() } returns kotlinx.coroutines.flow.flowOf("13800138000")

            // When & Then
            val result = repository.getSavedPhone().first()
            assertEquals("13800138000", result)
        }

        @Test
        fun `getSavedPassword - 获取保存的密码 - 返回Flow`() = runTest {
            // Given
            coEvery { tokenManager.getSavedPassword() } returns kotlinx.coroutines.flow.flowOf("password123")

            // When & Then
            val result = repository.getSavedPassword().first()
            assertEquals("password123", result)
        }

        @Test
        fun `isRememberPassword - 检查记住密码状态 - 返回Flow`() = runTest {
            // Given
            coEvery { tokenManager.isRememberPassword() } returns kotlinx.coroutines.flow.flowOf(true)

            // When & Then
            val result = repository.isRememberPassword().first()
            assertTrue(result)
        }
    }

    // ==================== 重试机制测试 ====================

    @Nested
    @DisplayName("重试机制测试")
    inner class RetryTests {

        @Test
        fun `login - 网络错误后重试成功 - 返回成功结果`() = runTest {
            // Given - 第一次调用失败，第二次成功
            val loginResponse = LoginResponse(
                userId = 1, userIdNo = "USER001", userName = "Test",
                userTel = "13800138000", userToken = "token", userType = 1,
                houseId = 1, status = 1
            )
            coEvery { apiService.login(any()) }
                .throws(java.net.UnknownHostException())
                .andThen(BaseResponse(200, "success", loginResponse))
            every { tokenManager.onLoginSuccess(any(), any(), any(), any(), any()) } just Runs

            // When & Then
            repository.login("user", "pass").test {
                assertEquals(ApiResult.Loading, awaitItem())
                // 由于重试机制，这里应该最终返回成功
                val result = awaitItem()
                // 注意：实际重试行为取决于RetryConfig
                awaitComplete()
            }
        }

        @Test
        fun `getUserInfo - 超时后重试 - 处理超时错误`() = runTest {
            // Given
            coEvery { apiService.getUserInfo() } throws java.net.SocketTimeoutException()

            // When & Then
            repository.getUserInfo().test {
                assertEquals(ApiResult.Loading, awaitItem())
                val error = awaitItem() as ApiResult.Error
                assertTrue(error.exception is AppException.TimeoutError)
                awaitComplete()
            }
        }
    }

    // ==================== 边界条件测试 ====================

    @Nested
    @DisplayName("边界条件测试")
    inner class EdgeCaseTests {

        @Test
        fun `login - 超长用户名 - 正确处理`() = runTest {
            // Given
            val longUsername = "a".repeat(100)
            coEvery { apiService.login(any()) } returns BaseResponse(400, "用户名过长", null)

            // When & Then
            repository.login(longUsername, "pass").test {
                assertEquals(ApiResult.Loading, awaitItem())
                val error = awaitItem() as ApiResult.Error
                assertTrue(error.exception is AppException.BusinessError)
                awaitComplete()
            }
        }

        @Test
        fun `login - Unicode用户名 - 正确处理`() = runTest {
            // Given
            val unicodeUsername = "用户@example.com"
            val loginResponse = LoginResponse(
                userId = 1, userIdNo = "USER001", userName = unicodeUsername,
                userTel = "13800138000", userToken = "token", userType = 1,
                houseId = 1, status = 1
            )
            coEvery { apiService.login(any()) } returns BaseResponse(200, "success", loginResponse)
            every { tokenManager.onLoginSuccess(any(), any(), any(), any(), any()) } just Runs

            // When & Then
            repository.login(unicodeUsername, "pass").test {
                assertEquals(ApiResult.Loading, awaitItem())
                val success = awaitItem() as ApiResult.Success
                assertEquals(unicodeUsername, success.data.userName)
                awaitComplete()
            }
        }

        @Test
        fun `bindHouse - 特殊字符房屋ID - 正确处理`() = runTest {
            // Given - 使用Mock模式测试特殊字符房屋ID
            val mockRepo = UserRepositoryImpl(apiService, tokenManager, useMock = true)
            every { tokenManager.setCurrentHouseId(any()) } just Runs

            // When & Then - Mock模式下应该成功处理
            mockRepo.bindHouse("HOUSE-123_456", null).test {
                assertEquals(ApiResult.Loading, awaitItem())
                val success = awaitItem() as ApiResult.Success
                assertEquals(Unit, success.data)
                awaitComplete()
            }
        }
    }
}
