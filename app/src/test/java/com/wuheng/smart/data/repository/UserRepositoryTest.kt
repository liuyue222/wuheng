package com.wuheng.smart.data.repository

import app.cash.turbine.test
import com.wuheng.smart.data.model.*
import com.wuheng.smart.data.network.ApiResult
import com.wuheng.smart.data.network.ApiService
import com.wuheng.smart.data.network.BaseResponse
import com.wuheng.smart.data.network.TokenManager
import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.*

/**
 * UserRepository 单元测试
 *
 * 测试范围:
 * 1. 用户登录/注册/登出
 * 2. 用户信息获取与更新
 * 3. 密码修改
 * 4. 房屋绑定
 * 5. 错误处理场景
 */
@OptIn(ExperimentalCoroutinesApi::class)
class UserRepositoryTest {

    private lateinit var apiService: ApiService
    private lateinit var tokenManager: TokenManager
    private lateinit var userRepository: UserRepositoryImpl

    @BeforeEach
    fun setUp() {
        apiService = mockk(relaxed = true)
        tokenManager = mockk(relaxed = true)
        userRepository = UserRepositoryImpl(apiService, tokenManager, useMock = false)
    }

    @AfterEach
    fun tearDown() {
        unmockkAll()
    }

    // ==================== 登录测试 ====================

    @Test
    fun `login with valid credentials should return success and save token`() = runTest {
        // Given
        val username = "testuser"
        val password = "password123"
        val mockResponse = LoginResponse(
            userId = 1,
            userIdNo = "USER001",
            userName = username,
            userTel = "13800138000",
            userToken = "valid_token_123",
            userType = 1,
            houseId = 1,
            status = 1
        )
        coEvery { apiService.login(any()) } returns BaseResponse(200, "success", mockResponse)

        // When
        val result = userRepository.login(username, password).first()

        // Then
        assertTrue(result is ApiResult.Success)
        assertEquals(mockResponse, (result as ApiResult.Success).data)
        verify { tokenManager.setToken("valid_token_123") }
    }

    @Test
    fun `login with invalid credentials should return error`() = runTest {
        // Given
        coEvery { apiService.login(any()) } returns BaseResponse(401, "Invalid credentials", null)

        // When
        val result = userRepository.login("wrong", "wrong").first()

        // Then
        assertTrue(result is ApiResult.Error)
        assertEquals(401, (result as ApiResult.Error).exception.let {
            (it as com.wuheng.smart.data.network.AppException.BusinessError).code
        })
    }

    @Test
    fun `login should emit loading first`() = runTest {
        // Given
        val mockResponse = LoginResponse(
            userId = 1, userIdNo = "USER001", userName = "test",
            userTel = "13800138000", userToken = "token", userType = 1,
            houseId = 1, status = 1
        )
        coEvery { apiService.login(any()) } returns BaseResponse(200, "success", mockResponse)

        // When & Then
        userRepository.login("test", "pass").test {
            assertTrue(awaitItem() is ApiResult.Loading)
            assertTrue(awaitItem() is ApiResult.Success)
            awaitComplete()
        }
    }

    // ==================== 注册测试 ====================

    @Test
    fun `register with valid data should return success`() = runTest {
        // Given
        val request = RegisterRequest(
            username = "newuser",
            password = "pass123",
            mobile = "13800138000",
            realname = "张三"
        )
        val mockResponse = RegisterResponse(userId = 2, userToken = "new_token")
        coEvery { apiService.register(any()) } returns BaseResponse(200, "success", mockResponse)

        // When
        val result = userRepository.register(request).first()

        // Then
        assertTrue(result is ApiResult.Success)
        assertEquals(mockResponse, (result as ApiResult.Success).data)
    }

    @Test
    fun `register with duplicate username should return error`() = runTest {
        // Given
        coEvery { apiService.register(any()) } returns BaseResponse(409, "Username already exists", null)

        // When
        val request = RegisterRequest("existing", "pass", "13800138000")
        val result = userRepository.register(request).first()

        // Then
        assertTrue(result is ApiResult.Error)
    }

    // ==================== 登出测试 ====================

    @Test
    fun `logout should clear token and return success`() = runTest {
        // Given
        coEvery { apiService.logout() } returns BaseResponse(200, "success", Unit)

        // When
        val result = userRepository.logout().first()

        // Then
        assertTrue(result is ApiResult.Success)
        verify { tokenManager.clearToken() }
    }

    @Test
    fun `logout should clear token even when api fails`() = runTest {
        // Given
        coEvery { apiService.logout() } returns BaseResponse(500, "Server error", null)

        // When
        val result = userRepository.logout().first()

        // Then
        verify { tokenManager.clearToken() }
    }

    // ==================== 用户信息测试 ====================

    @Test
    fun `getUserInfo should return user details`() = runTest {
        // Given
        val mockUserInfo = UserInfo(
            userId = 1,
            userIdNo = "USER001",
            userName = "张三",
            userTel = "13800138000",
            userType = 1,
            houseId = 1,
            status = 1
        )
        coEvery { apiService.getUserInfo() } returns BaseResponse(200, "success", mockUserInfo)

        // When
        val result = userRepository.getUserInfo().first()

        // Then
        assertTrue(result is ApiResult.Success)
        assertEquals(mockUserInfo, (result as ApiResult.Success).data)
    }

    @Test
    fun `updateUserInfo should return success`() = runTest {
        // Given
        val request = UpdateUserInfoRequest(realname = "李四", email = "li@example.com")
        coEvery { apiService.updateUserInfo(any()) } returns BaseResponse(200, "success", Unit)

        // When
        val result = userRepository.updateUserInfo(request).first()

        // Then
        assertTrue(result is ApiResult.Success)
    }

    // ==================== 密码修改测试 ====================

    @Test
    fun `changePassword with correct old password should succeed`() = runTest {
        // Given
        coEvery { apiService.changePassword(any()) } returns BaseResponse(200, "success", Unit)

        // When
        val result = userRepository.changePassword("oldPass", "newPass").first()

        // Then
        assertTrue(result is ApiResult.Success)
    }

    @Test
    fun `changePassword with wrong old password should fail`() = runTest {
        // Given
        coEvery { apiService.changePassword(any()) } returns BaseResponse(400, "Old password incorrect", null)

        // When
        val result = userRepository.changePassword("wrongOld", "newPass").first()

        // Then
        assertTrue(result is ApiResult.Error)
    }

    // ==================== 房屋绑定测试 ====================

    @Test
    fun `bindHouse with valid code should succeed`() = runTest {
        // Given
        coEvery { apiService.bindHouse(any()) } returns BaseResponse(200, "success", Unit)

        // When
        val result = userRepository.bindHouse(1, "BIND123").first()

        // Then
        assertTrue(result is ApiResult.Success)
    }

    @Test
    fun `bindHouse with invalid code should fail`() = runTest {
        // Given
        coEvery { apiService.bindHouse(any()) } returns BaseResponse(400, "Invalid bind code", null)

        // When
        val result = userRepository.bindHouse(1, "WRONG").first()

        // Then
        assertTrue(result is ApiResult.Error)
    }

    // ==================== 我的房屋测试 ====================

    @Test
    fun `getMyHouses should return list of houses`() = runTest {
        // Given
        val mockHouses = listOf(
            MyHouse(1, "西湖壹号院", "杭州市西湖区", "owner", System.currentTimeMillis()),
            MyHouse(2, "滨江公寓", "杭州市滨江区", "family", System.currentTimeMillis())
        )
        coEvery { apiService.getMyHouses() } returns BaseResponse(200, "success", mockHouses)

        // When
        val result = userRepository.getMyHouses().first()

        // Then
        assertTrue(result is ApiResult.Success)
        assertEquals(2, (result as ApiResult.Success).data.size)
    }

    @Test
    fun `getMyHouses with no houses should return empty list`() = runTest {
        // Given
        coEvery { apiService.getMyHouses() } returns BaseResponse(200, "success", emptyList<MyHouse>())

        // When
        val result = userRepository.getMyHouses().first()

        // Then
        assertTrue(result is ApiResult.Success)
        assertTrue((result as ApiResult.Success).data.isEmpty())
    }

    // ==================== 边界条件测试 ====================

    @Test
    fun `login with empty username should handle gracefully`() = runTest {
        // Given
        coEvery { apiService.login(any()) } returns BaseResponse(400, "Username cannot be empty", null)

        // When
        val result = userRepository.login("", "password").first()

        // Then
        assertTrue(result is ApiResult.Error)
    }

    @Test
    fun `login with empty password should handle gracefully`() = runTest {
        // Given
        coEvery { apiService.login(any()) } returns BaseResponse(400, "Password cannot be empty", null)

        // When
        val result = userRepository.login("user", "").first()

        // Then
        assertTrue(result is ApiResult.Error)
    }

    @Test
    fun `getUserInfo when not logged in should return error`() = runTest {
        // Given
        coEvery { apiService.getUserInfo() } returns BaseResponse(401, "Unauthorized", null)

        // When
        val result = userRepository.getUserInfo().first()

        // Then
        assertTrue(result is ApiResult.Error)
    }
}
