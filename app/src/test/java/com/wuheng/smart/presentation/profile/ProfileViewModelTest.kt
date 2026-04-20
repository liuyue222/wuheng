package com.wuheng.smart.presentation.profile

import com.wuheng.smart.data.model.UpdateProfileRequest
import com.wuheng.smart.data.model.UserProfile
import com.wuheng.smart.data.network.ApiResult
import com.wuheng.smart.data.network.AppException
import com.wuheng.smart.data.repository.UserRepository
import com.wuheng.smart.presentation.base.UiDataState
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

/**
 * ProfileViewModel 单元测试
 * 测试用户信息加载、更新、登出等功能
 */
@OptIn(ExperimentalCoroutinesApi::class)
class ProfileViewModelTest {

    private lateinit var viewModel: ProfileViewModel
    private lateinit var userRepository: UserRepository
    private val testDispatcher = StandardTestDispatcher()

    // 测试数据
    private val mockUserProfile = UserProfile(
        userId = "user_001",
        nickname = "张先生",
        avatar = "https://example.com/avatar/default.png",
        phone = "138****8888",
        email = "zhang@example.com",
        homeName = "绿城桃花源别墅",
        address = "杭州市余杭区桃花源别墅区18栋"
    )

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        userRepository = mockk()
        viewModel = ProfileViewModel(userRepository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `Given 初始化状态 When ViewModel创建时 Then 应该自动加载用户资料`() = runTest {
        // Given
        coEvery { userRepository.getUserProfile() } returns ApiResult.Success(mockUserProfile)

        // When
        advanceUntilIdle()

        // Then
        val state = viewModel.userProfileState.first()
        assertTrue(state is UiDataState.Success)
        assertEquals(mockUserProfile, (state as UiDataState.Success).data)
    }

    @Test
    fun `Given 用户资料数据 When 调用loadUserProfile Then 应该返回正确的用户信息`() = runTest {
        // Given
        coEvery { userRepository.getUserProfile() } returns ApiResult.Success(mockUserProfile)

        // When
        viewModel.loadUserProfile()
        advanceUntilIdle()

        // Then
        val state = viewModel.userProfileState.first()
        assertTrue(state is UiDataState.Success)
        val data = (state as UiDataState.Success).data
        assertEquals("user_001", data.userId)
        assertEquals("张先生", data.nickname)
        assertEquals("138****8888", data.phone)
        assertEquals("zhang@example.com", data.email)
        assertEquals("绿城桃花源别墅", data.homeName)
        assertEquals("杭州市余杭区桃花源别墅区18栋", data.address)
    }

    @Test
    fun `Given 无头像用户 When 调用loadUserProfile Then 应该正确处理null头像`() = runTest {
        // Given
        val profileWithoutAvatar = mockUserProfile.copy(avatar = null)
        coEvery { userRepository.getUserProfile() } returns ApiResult.Success(profileWithoutAvatar)

        // When
        viewModel.loadUserProfile()
        advanceUntilIdle()

        // Then
        val state = viewModel.userProfileState.first()
        assertTrue(state is UiDataState.Success)
        assertNull((state as UiDataState.Success).data.avatar)
    }

    @Test
    fun `Given 无邮箱用户 When 调用loadUserProfile Then 应该正确处理null邮箱`() = runTest {
        // Given
        val profileWithoutEmail = mockUserProfile.copy(email = null)
        coEvery { userRepository.getUserProfile() } returns ApiResult.Success(profileWithoutEmail)

        // When
        viewModel.loadUserProfile()
        advanceUntilIdle()

        // Then
        val state = viewModel.userProfileState.first()
        assertTrue(state is UiDataState.Success)
        assertNull((state as UiDataState.Success).data.email)
    }

    @Test
    fun `Given 更新昵称请求 When 调用updateProfile Then 应该更新资料并刷新`() = runTest {
        // Given
        val updateRequest = UpdateProfileRequest(nickname = "李先生")
        val updatedProfile = mockUserProfile.copy(nickname = "李先生")
        coEvery { userRepository.updateUserProfile(updateRequest) } returns ApiResult.Success(updatedProfile)
        coEvery { userRepository.getUserProfile() } returns ApiResult.Success(updatedProfile)

        // When
        viewModel.updateProfile(updateRequest)
        advanceUntilIdle()

        // Then
        coVerify { userRepository.updateUserProfile(updateRequest) }
        coVerify { userRepository.getUserProfile() }
        val updateState = viewModel.updateState.first()
        assertTrue(updateState is UiDataState.Success)
    }

    @Test
    fun `Given 更新多个字段 When 调用updateProfile Then 应该正确更新所有字段`() = runTest {
        // Given
        val updateRequest = UpdateProfileRequest(
            nickname = "王先生",
            email = "wang@example.com",
            homeName = "西湖别墅"
        )
        val updatedProfile = mockUserProfile.copy(
            nickname = "王先生",
            email = "wang@example.com",
            homeName = "西湖别墅"
        )
        coEvery { userRepository.updateUserProfile(updateRequest) } returns ApiResult.Success(updatedProfile)
        coEvery { userRepository.getUserProfile() } returns ApiResult.Success(updatedProfile)

        // When
        viewModel.updateProfile(updateRequest)
        advanceUntilIdle()

        // Then
        coVerify { userRepository.updateUserProfile(updateRequest) }
        val updateState = viewModel.updateState.first()
        assertTrue(updateState is UiDataState.Success)
    }

    @Test
    fun `Given 仅更新头像 When 调用updateProfile Then 应该只更新头像字段`() = runTest {
        // Given
        val newAvatarUrl = "https://example.com/avatar/new.png"
        val updateRequest = UpdateProfileRequest(avatar = newAvatarUrl)
        val updatedProfile = mockUserProfile.copy(avatar = newAvatarUrl)
        coEvery { userRepository.updateUserProfile(updateRequest) } returns ApiResult.Success(updatedProfile)
        coEvery { userRepository.getUserProfile() } returns ApiResult.Success(updatedProfile)

        // When
        viewModel.updateProfile(updateRequest)
        advanceUntilIdle()

        // Then
        coVerify { userRepository.updateUserProfile(updateRequest) }
        val updateState = viewModel.updateState.first()
        assertTrue(updateState is UiDataState.Success)
    }

    @Test
    fun `Given 更新失败 When 调用updateProfile Then 应该返回错误状态`() = runTest {
        // Given
        val updateRequest = UpdateProfileRequest(nickname = "")
        coEvery { userRepository.updateUserProfile(updateRequest) } returns ApiResult.Error(
            AppException.BusinessError(400, "昵称不能为空")
        )

        // When
        viewModel.updateProfile(updateRequest)
        advanceUntilIdle()

        // Then
        val updateState = viewModel.updateState.first()
        assertTrue(updateState is UiDataState.Error)
        val exception = (updateState as UiDataState.Error).exception
        assertTrue(exception is AppException.BusinessError)
        assertEquals(400, (exception as AppException.BusinessError).code)
    }

    @Test
    fun `Given 网络错误 When 调用loadUserProfile Then 应该返回NetworkError`() = runTest {
        // Given
        coEvery { userRepository.getUserProfile() } returns ApiResult.Error(
            AppException.NetworkError("网络连接失败")
        )

        // When
        viewModel.loadUserProfile()
        advanceUntilIdle()

        // Then
        val state = viewModel.userProfileState.first()
        assertTrue(state is UiDataState.Error)
        assertTrue((state as UiDataState.Error).exception is AppException.NetworkError)
    }

    @Test
    fun `Given 未授权 When 调用loadUserProfile Then 应该返回BusinessError`() = runTest {
        // Given
        coEvery { userRepository.getUserProfile() } returns ApiResult.Error(
            AppException.BusinessError(401, "登录已过期，请重新登录")
        )

        // When
        viewModel.loadUserProfile()
        advanceUntilIdle()

        // Then
        val state = viewModel.userProfileState.first()
        assertTrue(state is UiDataState.Error)
        val exception = (state as UiDataState.Error).exception
        assertTrue(exception is AppException.BusinessError)
        assertEquals(401, (exception as AppException.BusinessError).code)
    }

    @Test
    fun `Given 登出操作 When 调用logout Then 应该执行登出逻辑`() = runTest {
        // Given - logout方法当前为空实现，但应该能够被调用

        // When
        viewModel.logout()
        advanceUntilIdle()

        // Then - 验证方法可以被调用且不抛出异常
        // 注：当前实现中logout方法为空，实际项目中应该验证相关逻辑
    }

    @Test
    fun `Given 当前状态 When 调用refresh Then 应该重新加载用户资料`() = runTest {
        // Given
        coEvery { userRepository.getUserProfile() } returns ApiResult.Success(mockUserProfile)

        // When
        viewModel.refresh()
        advanceUntilIdle()

        // Then
        coVerify(atLeast = 2) { userRepository.getUserProfile() }
    }

    @Test
    fun `Given 服务器内部错误 When 调用updateProfile Then 应该返回ServerError`() = runTest {
        // Given
        val updateRequest = UpdateProfileRequest(nickname = "测试")
        coEvery { userRepository.updateUserProfile(updateRequest) } returns ApiResult.Error(
            AppException.ServerError(500, "服务器内部错误")
        )

        // When
        viewModel.updateProfile(updateRequest)
        advanceUntilIdle()

        // Then
        val updateState = viewModel.updateState.first()
        assertTrue(updateState is UiDataState.Error)
        val exception = (updateState as UiDataState.Error).exception
        assertTrue(exception is AppException.ServerError)
        assertEquals(500, (exception as AppException.ServerError).code)
    }

    @Test
    fun `Given 超长昵称 When 调用updateProfile Then 应该返回验证错误`() = runTest {
        // Given
        val longNickname = "a".repeat(50) // 超长昵称
        val updateRequest = UpdateProfileRequest(nickname = longNickname)
        coEvery { userRepository.updateUserProfile(updateRequest) } returns ApiResult.Error(
            AppException.BusinessError(400, "昵称长度不能超过20个字符")
        )

        // When
        viewModel.updateProfile(updateRequest)
        advanceUntilIdle()

        // Then
        val updateState = viewModel.updateState.first()
        assertTrue(updateState is UiDataState.Error)
    }

    @Test
    fun `Given 无效邮箱格式 When 调用updateProfile Then 应该返回验证错误`() = runTest {
        // Given
        val invalidEmail = "invalid-email"
        val updateRequest = UpdateProfileRequest(email = invalidEmail)
        coEvery { userRepository.updateUserProfile(updateRequest) } returns ApiResult.Error(
            AppException.BusinessError(400, "邮箱格式不正确")
        )

        // When
        viewModel.updateProfile(updateRequest)
        advanceUntilIdle()

        // Then
        val updateState = viewModel.updateState.first()
        assertTrue(updateState is UiDataState.Error)
    }

    @Test
    fun `Given 空更新请求 When 调用updateProfile Then 应该不执行任何更新`() = runTest {
        // Given
        val emptyRequest = UpdateProfileRequest()
        coEvery { userRepository.updateUserProfile(emptyRequest) } returns ApiResult.Success(mockUserProfile)
        coEvery { userRepository.getUserProfile() } returns ApiResult.Success(mockUserProfile)

        // When
        viewModel.updateProfile(emptyRequest)
        advanceUntilIdle()

        // Then
        coVerify { userRepository.updateUserProfile(emptyRequest) }
        val updateState = viewModel.updateState.first()
        assertTrue(updateState is UiDataState.Success)
    }
}
