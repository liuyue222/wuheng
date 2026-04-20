package com.wuheng.smart.presentation.profile

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.wuheng.smart.data.model.*
import com.wuheng.smart.data.repository.UserRepository
import com.wuheng.smart.presentation.base.UiDataState
import io.mockk.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * 个人中心页面 UI 测试
 *
 * 测试范围:
 * 1. 页面元素显示验证
 * 2. 用户信息显示
 * 3. 房屋信息显示
 * 4. 菜单项点击
 * 5. 退出登录
 * 6. 加载和错误状态
 */
@RunWith(AndroidJUnit4::class)
class ProfileScreenTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    private lateinit var mockViewModel: ProfileViewModel
    private val uiState = MutableStateFlow(ProfileUiState())

    @Before
    fun setUp() {
        mockViewModel = mockk(relaxed = true)
        every { mockViewModel.uiState } returns uiState.asStateFlow()
    }

    // ==================== 页面元素显示测试 ====================

    @Test
    fun profileScreen_shouldDisplayHeaderElements() {
        // Given
        uiState.value = ProfileUiState(
            userInfo = UiDataState.Success(
                UserInfo(
                    userId = 1,
                    userIdNo = "USER001",
                    userName = "张三",
                    userTel = "13800138000",
                    userType = 1,
                    houseId = 1,
                    status = 1
                )
            )
        )

        // When
        composeTestRule.setContent {
            ProfileScreen(
                viewModel = mockViewModel,
                onNavigateBack = {},
                onNavigateToSettings = {},
                onNavigateToAbout = {},
                onNavigateToHelp = {},
                onLogout = {}
            )
        }

        // Then
        composeTestRule.onNodeWithText("个人中心").assertIsDisplayed()
        composeTestRule.onNodeWithText("张三").assertIsDisplayed()
    }

    @Test
    fun profileScreen_shouldDisplayUserPhone() {
        // Given
        uiState.value = ProfileUiState(
            userInfo = UiDataState.Success(
                UserInfo(
                    userId = 1,
                    userIdNo = "USER001",
                    userName = "张三",
                    userTel = "13800138000",
                    userType = 1,
                    houseId = 1,
                    status = 1
                )
            )
        )

        // When
        composeTestRule.setContent {
            ProfileScreen(
                viewModel = mockViewModel,
                onNavigateBack = {},
                onNavigateToSettings = {},
                onNavigateToAbout = {},
                onNavigateToHelp = {},
                onLogout = {}
            )
        }

        // Then
        composeTestRule.onNodeWithText("138****8000").assertIsDisplayed()
    }

    // ==================== 房屋信息测试 ====================

    @Test
    fun profileScreen_shouldDisplayHouseInfo() {
        // Given
        uiState.value = ProfileUiState(
            houseInfo = UiDataState.Success(
                HouseInfo(
                    houseId = 1,
                    houseIdNo = "HOUSE001",
                    houseName = "西湖壹号院",
                    ownerName = "张三",
                    ownerPhone = "13800138000",
                    address = "杭州市西湖区文三路123号",
                    floorCount = 3,
                    areaTotal = "280",
                    systemType = "五恒系统",
                    roomCount = 8,
                    deviceCount = 12,
                    onlineCount = 10
                )
            )
        )

        // When
        composeTestRule.setContent {
            ProfileScreen(
                viewModel = mockViewModel,
                onNavigateBack = {},
                onNavigateToSettings = {},
                onNavigateToAbout = {},
                onNavigateToHelp = {},
                onLogout = {}
            )
        }

        // Then
        composeTestRule.onNodeWithText("我的房屋").assertIsDisplayed()
        composeTestRule.onNodeWithText("西湖壹号院").assertIsDisplayed()
        composeTestRule.onNodeWithText("杭州市西湖区文三路123号").assertIsDisplayed()
    }

    @Test
    fun profileScreen_shouldDisplayHouseDetails() {
        // Given
        uiState.value = ProfileUiState(
            houseInfo = UiDataState.Success(
                HouseInfo(
                    houseId = 1,
                    houseIdNo = "HOUSE001",
                    houseName = "西湖壹号院",
                    ownerName = "张三",
                    ownerPhone = "13800138000",
                    address = "杭州市西湖区文三路123号",
                    floorCount = 3,
                    areaTotal = "280",
                    systemType = "五恒系统",
                    roomCount = 8,
                    deviceCount = 12,
                    onlineCount = 10
                )
            )
        )

        // When
        composeTestRule.setContent {
            ProfileScreen(
                viewModel = mockViewModel,
                onNavigateBack = {},
                onNavigateToSettings = {},
                onNavigateToAbout = {},
                onNavigateToHelp = {},
                onLogout = {}
            )
        }

        // Then
        composeTestRule.onNodeWithText("3层").assertIsDisplayed()
        composeTestRule.onNodeWithText("280m²").assertIsDisplayed()
        composeTestRule.onNodeWithText("8个房间").assertIsDisplayed()
        composeTestRule.onNodeWithText("12个设备").assertIsDisplayed()
    }

    // ==================== 菜单项测试 ====================

    @Test
    fun profileScreen_shouldDisplayMenuItems() {
        // Given
        uiState.value = ProfileUiState()

        // When
        composeTestRule.setContent {
            ProfileScreen(
                viewModel = mockViewModel,
                onNavigateBack = {},
                onNavigateToSettings = {},
                onNavigateToAbout = {},
                onNavigateToHelp = {},
                onLogout = {}
            )
        }

        // Then
        composeTestRule.onNodeWithText("账号设置").assertIsDisplayed()
        composeTestRule.onNodeWithText("消息通知").assertIsDisplayed()
        composeTestRule.onNodeWithText("帮助中心").assertIsDisplayed()
        composeTestRule.onNodeWithText("关于我们").assertIsDisplayed()
    }

    @Test
    fun profileScreen_shouldNavigateToSettingsWhenSettingsClicked() {
        // Given
        var navigatedToSettings = false
        uiState.value = ProfileUiState()

        composeTestRule.setContent {
            ProfileScreen(
                viewModel = mockViewModel,
                onNavigateBack = {},
                onNavigateToSettings = { navigatedToSettings = true },
                onNavigateToAbout = {},
                onNavigateToHelp = {},
                onLogout = {}
            )
        }

        // When
        composeTestRule.onNodeWithText("账号设置")
            .performClick()

        // Then
        assert(navigatedToSettings)
    }

    @Test
    fun profileScreen_shouldNavigateToAboutWhenAboutClicked() {
        // Given
        var navigatedToAbout = false
        uiState.value = ProfileUiState()

        composeTestRule.setContent {
            ProfileScreen(
                viewModel = mockViewModel,
                onNavigateBack = {},
                onNavigateToSettings = {},
                onNavigateToAbout = { navigatedToAbout = true },
                onNavigateToHelp = {},
                onLogout = {}
            )
        }

        // When
        composeTestRule.onNodeWithText("关于我们")
            .performClick()

        // Then
        assert(navigatedToAbout)
    }

    @Test
    fun profileScreen_shouldNavigateToHelpWhenHelpClicked() {
        // Given
        var navigatedToHelp = false
        uiState.value = ProfileUiState()

        composeTestRule.setContent {
            ProfileScreen(
                viewModel = mockViewModel,
                onNavigateBack = {},
                onNavigateToSettings = {},
                onNavigateToAbout = {},
                onNavigateToHelp = { navigatedToHelp = true },
                onLogout = {}
            )
        }

        // When
        composeTestRule.onNodeWithText("帮助中心")
            .performClick()

        // Then
        assert(navigatedToHelp)
    }

    // ==================== 退出登录测试 ====================

    @Test
    fun profileScreen_shouldDisplayLogoutButton() {
        // Given
        uiState.value = ProfileUiState()

        // When
        composeTestRule.setContent {
            ProfileScreen(
                viewModel = mockViewModel,
                onNavigateBack = {},
                onNavigateToSettings = {},
                onNavigateToAbout = {},
                onNavigateToHelp = {},
                onLogout = {}
            )
        }

        // Then
        composeTestRule.onNodeWithText("退出登录")
            .assertIsDisplayed()
    }

    @Test
    fun profileScreen_shouldShowLogoutConfirmationWhenLogoutClicked() {
        // Given
        uiState.value = ProfileUiState()

        composeTestRule.setContent {
            ProfileScreen(
                viewModel = mockViewModel,
                onNavigateBack = {},
                onNavigateToSettings = {},
                onNavigateToAbout = {},
                onNavigateToHelp = {},
                onLogout = {}
            )
        }

        // When
        composeTestRule.onNodeWithText("退出登录")
            .performClick()

        // Then
        composeTestRule.onNodeWithText("确认退出").assertIsDisplayed()
        composeTestRule.onNodeWithText("确定").assertIsDisplayed()
        composeTestRule.onNodeWithText("取消").assertIsDisplayed()
    }

    @Test
    fun profileScreen_shouldLogoutWhenConfirmed() {
        // Given
        var logoutCalled = false
        uiState.value = ProfileUiState()

        composeTestRule.setContent {
            ProfileScreen(
                viewModel = mockViewModel,
                onNavigateBack = {},
                onNavigateToSettings = {},
                onNavigateToAbout = {},
                onNavigateToHelp = {},
                onLogout = { logoutCalled = true }
            )
        }

        // When
        composeTestRule.onNodeWithText("退出登录")
            .performClick()
        composeTestRule.onNodeWithText("确定")
            .performClick()

        // Then
        verify { mockViewModel.logout() }
    }

    @Test
    fun profileScreen_shouldDismissDialogWhenCancelled() {
        // Given
        uiState.value = ProfileUiState()

        composeTestRule.setContent {
            ProfileScreen(
                viewModel = mockViewModel,
                onNavigateBack = {},
                onNavigateToSettings = {},
                onNavigateToAbout = {},
                onNavigateToHelp = {},
                onLogout = {}
            )
        }

        // When
        composeTestRule.onNodeWithText("退出登录")
            .performClick()
        composeTestRule.onNodeWithText("取消")
            .performClick()

        // Then
        composeTestRule.onNodeWithText("确认退出").assertDoesNotExist()
    }

    // ==================== 加载状态测试 ====================

    @Test
    fun profileScreen_shouldShowLoadingIndicator() {
        // Given
        uiState.value = ProfileUiState(
            userInfo = UiDataState.Loading,
            houseInfo = UiDataState.Loading
        )

        // When
        composeTestRule.setContent {
            ProfileScreen(
                viewModel = mockViewModel,
                onNavigateBack = {},
                onNavigateToSettings = {},
                onNavigateToAbout = {},
                onNavigateToHelp = {},
                onLogout = {}
            )
        }

        // Then
        composeTestRule.onNode(hasProgressBar())
            .assertIsDisplayed()
    }

    @Test
    fun profileScreen_shouldShowShimmerWhileLoading() {
        // Given
        uiState.value = ProfileUiState(
            userInfo = UiDataState.Loading
        )

        // When
        composeTestRule.setContent {
            ProfileScreen(
                viewModel = mockViewModel,
                onNavigateBack = {},
                onNavigateToSettings = {},
                onNavigateToAbout = {},
                onNavigateToHelp = {},
                onLogout = {}
            )
        }

        // Then
        composeTestRule.onAllNodes(hasTestTag("shimmer"))
            .fetchSemanticsNodes()
            .isNotEmpty()
    }

    // ==================== 错误状态测试 ====================

    @Test
    fun profileScreen_shouldShowErrorMessage() {
        // Given
        uiState.value = ProfileUiState(
            userInfo = UiDataState.Error(Exception("加载失败"))
        )

        // When
        composeTestRule.setContent {
            ProfileScreen(
                viewModel = mockViewModel,
                onNavigateBack = {},
                onNavigateToSettings = {},
                onNavigateToAbout = {},
                onNavigateToHelp = {},
                onLogout = {}
            )
        }

        // Then
        composeTestRule.onNodeWithText("加载失败，点击重试")
            .assertIsDisplayed()
    }

    @Test
    fun profileScreen_shouldRetryWhenErrorClicked() {
        // Given
        uiState.value = ProfileUiState(
            userInfo = UiDataState.Error(Exception("加载失败"))
        )

        composeTestRule.setContent {
            ProfileScreen(
                viewModel = mockViewModel,
                onNavigateBack = {},
                onNavigateToSettings = {},
                onNavigateToAbout = {},
                onNavigateToHelp = {},
                onLogout = {}
            )
        }

        // When
        composeTestRule.onNodeWithText("加载失败，点击重试")
            .performClick()

        // Then
        verify { mockViewModel.loadUserInfo() }
    }

    // ==================== 刷新测试 ====================

    @Test
    fun profileScreen_shouldSupportPullToRefresh() {
        // Given
        uiState.value = ProfileUiState(
            userInfo = UiDataState.Success(
                UserInfo(
                    userId = 1,
                    userIdNo = "USER001",
                    userName = "张三",
                    userTel = "13800138000",
                    userType = 1,
                    houseId = 1,
                    status = 1
                )
            ),
            isRefreshing = false
        )

        composeTestRule.setContent {
            ProfileScreen(
                viewModel = mockViewModel,
                onNavigateBack = {},
                onNavigateToSettings = {},
                onNavigateToAbout = {},
                onNavigateToHelp = {},
                onLogout = {}
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
    fun profileScreen_shouldNavigateBackWhenBackClicked() {
        // Given
        var navigatedBack = false
        uiState.value = ProfileUiState(
            userInfo = UiDataState.Success(
                UserInfo(
                    userId = 1,
                    userIdNo = "USER001",
                    userName = "张三",
                    userTel = "13800138000",
                    userType = 1,
                    houseId = 1,
                    status = 1
                )
            )
        )

        composeTestRule.setContent {
            ProfileScreen(
                viewModel = mockViewModel,
                onNavigateBack = { navigatedBack = true },
                onNavigateToSettings = {},
                onNavigateToAbout = {},
                onNavigateToHelp = {},
                onLogout = {}
            )
        }

        // When
        composeTestRule.onNodeWithContentDescription("返回")
            .performClick()

        // Then
        assert(navigatedBack)
    }

    // ==================== 用户头像测试 ====================

    @Test
    fun profileScreen_shouldDisplayUserAvatar() {
        // Given
        uiState.value = ProfileUiState(
            userInfo = UiDataState.Success(
                UserInfo(
                    userId = 1,
                    userIdNo = "USER001",
                    userName = "张三",
                    userTel = "13800138000",
                    userType = 1,
                    houseId = 1,
                    status = 1
                )
            )
        )

        // When
        composeTestRule.setContent {
            ProfileScreen(
                viewModel = mockViewModel,
                onNavigateBack = {},
                onNavigateToSettings = {},
                onNavigateToAbout = {},
                onNavigateToHelp = {},
                onLogout = {}
            )
        }

        // Then
        composeTestRule.onNodeWithContentDescription("用户头像")
            .assertIsDisplayed()
    }

    // ==================== 版本信息测试 ====================

    @Test
    fun profileScreen_shouldDisplayAppVersion() {
        // Given
        uiState.value = ProfileUiState(
            appVersion = "1.0.0"
        )

        // When
        composeTestRule.setContent {
            ProfileScreen(
                viewModel = mockViewModel,
                onNavigateBack = {},
                onNavigateToSettings = {},
                onNavigateToAbout = {},
                onNavigateToHelp = {},
                onLogout = {}
            )
        }

        // Then
        composeTestRule.onNodeWithText("版本 1.0.0").assertIsDisplayed()
    }

    // ==================== 无障碍测试 ====================

    @Test
    fun profileScreen_shouldHaveProperSemantics() {
        // Given
        uiState.value = ProfileUiState(
            userInfo = UiDataState.Success(
                UserInfo(
                    userId = 1,
                    userIdNo = "USER001",
                    userName = "张三",
                    userTel = "13800138000",
                    userType = 1,
                    houseId = 1,
                    status = 1
                )
            )
        )

        // When
        composeTestRule.setContent {
            ProfileScreen(
                viewModel = mockViewModel,
                onNavigateBack = {},
                onNavigateToSettings = {},
                onNavigateToAbout = {},
                onNavigateToHelp = {},
                onLogout = {}
            )
        }

        // Then
        composeTestRule.onNodeWithContentDescription("返回")
            .assertHasClickAction()

        composeTestRule.onNodeWithText("账号设置")
            .assertHasClickAction()

        composeTestRule.onNodeWithText("退出登录")
            .assertHasClickAction()
    }
}

// Helper functions for semantic matchers
private fun hasProgressBar(): SemanticsMatcher {
    return SemanticsMatcher.keyIsDefined(androidx.compose.ui.semantics.ProgressBarRangeInfo)
}

private fun hasTestTag(tag: String): SemanticsMatcher {
    return SemanticsMatcher.expectValue(androidx.compose.ui.semantics.SemanticsProperties.TestTag, tag)
}
