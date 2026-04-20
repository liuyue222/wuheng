package com.wuheng.smart.presentation

import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.wuheng.smart.data.model.UserProfile
import com.wuheng.smart.presentation.base.UiDataState
import com.wuheng.smart.presentation.profile.ProfileContent
import org.junit.Rule
import org.junit.Test

/**
 * ProfileScreen UI测试
 * 测试个人中心页面显示和用户操作
 */
class ProfileScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

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

    @Test
    fun given_userProfileSuccess_when_screenDisplayed_then_showUserInfo() {
        // Given - 成功状态
        composeTestRule.setContent {
            ProfileContent(
                userProfileState = UiDataState.Success(mockUserProfile),
                onRefresh = {},
                onLogout = {}
            )
        }

        // Then - 验证用户信息正确显示
        composeTestRule.onNodeWithText("我的").assertIsDisplayed()
        composeTestRule.onNodeWithText("张先生").assertIsDisplayed() // 昵称
        composeTestRule.onNodeWithText("138****8888").assertIsDisplayed() // 手机号
        composeTestRule.onNodeWithText("绿城桃花源别墅").assertIsDisplayed() // 家庭名称
        composeTestRule.onNodeWithText("zhang@example.com").assertIsDisplayed() // 邮箱
    }

    @Test
    fun given_userProfileSuccess_when_screenDisplayed_then_showSettingsOptions() {
        // Given - 成功状态
        composeTestRule.setContent {
            ProfileContent(
                userProfileState = UiDataState.Success(mockUserProfile),
                onRefresh = {},
                onLogout = {}
            )
        }

        // Then - 验证设置选项显示
        composeTestRule.onNodeWithText("账号设置").assertIsDisplayed()
        composeTestRule.onNodeWithText("家庭管理").assertIsDisplayed()
        composeTestRule.onNodeWithText("关于").assertIsDisplayed()
    }

    @Test
    fun given_userProfileSuccess_when_screenDisplayed_then_showLogoutButton() {
        // Given - 成功状态
        composeTestRule.setContent {
            ProfileContent(
                userProfileState = UiDataState.Success(mockUserProfile),
                onRefresh = {},
                onLogout = {}
            )
        }

        // Then - 验证退出登录按钮显示
        composeTestRule.onNodeWithText("退出登录").assertIsDisplayed()
    }

    @Test
    fun given_logoutButton_when_clicked_then_triggerLogout() {
        // Given
        var logoutCalled = false
        composeTestRule.setContent {
            ProfileContent(
                userProfileState = UiDataState.Success(mockUserProfile),
                onRefresh = {},
                onLogout = { logoutCalled = true }
            )
        }

        // When - 点击退出登录按钮
        composeTestRule.onNodeWithText("退出登录").performClick()

        // Then - 验证登出回调被调用
        assert(logoutCalled)
    }

    @Test
    fun given_refreshButton_when_clicked_then_triggerRefresh() {
        // Given
        var refreshCalled = false
        composeTestRule.setContent {
            ProfileContent(
                userProfileState = UiDataState.Success(mockUserProfile),
                onRefresh = { refreshCalled = true },
                onLogout = {}
            )
        }

        // When - 点击刷新按钮
        composeTestRule.onNodeWithText("刷新").performClick()

        // Then - 验证刷新回调被调用
        assert(refreshCalled)
    }

    @Test
    fun given_loadingState_when_screenDisplayed_then_showLoading() {
        // Given - 加载状态
        composeTestRule.setContent {
            ProfileContent(
                userProfileState = UiDataState.Loading,
                onRefresh = {},
                onLogout = {}
            )
        }

        // Then - 验证加载状态
        composeTestRule.onNodeWithText("我的").assertIsDisplayed()
    }

    @Test
    fun given_errorState_when_screenDisplayed_then_showError() {
        // Given - 错误状态
        val errorMessage = "加载失败，请重试"
        composeTestRule.setContent {
            ProfileContent(
                userProfileState = UiDataState.Error(
                    com.wuheng.smart.data.network.AppException.NetworkError(errorMessage)
                ),
                onRefresh = {},
                onLogout = {}
            )
        }

        // Then - 验证错误信息显示
        composeTestRule.onNodeWithText(errorMessage).assertIsDisplayed()
        composeTestRule.onNodeWithText("重试").assertIsDisplayed()
    }

    @Test
    fun given_errorState_when_retryClicked_then_triggerRefresh() {
        // Given
        var retryCalled = false
        composeTestRule.setContent {
            ProfileContent(
                userProfileState = UiDataState.Error(
                    com.wuheng.smart.data.network.AppException.NetworkError("网络错误")
                ),
                onRefresh = { retryCalled = true },
                onLogout = {}
            )
        }

        // When - 点击重试按钮
        composeTestRule.onNodeWithText("重试").performClick()

        // Then - 验证重试回调被调用
        assert(retryCalled)
    }

    @Test
    fun given_userWithoutEmail_when_screenDisplayed_then_showNotSet() {
        // Given - 无邮箱用户
        val profileWithoutEmail = mockUserProfile.copy(email = null)
        composeTestRule.setContent {
            ProfileContent(
                userProfileState = UiDataState.Success(profileWithoutEmail),
                onRefresh = {},
                onLogout = {}
            )
        }

        // Then - 验证用户信息显示（邮箱为null时显示"未设置"）
        composeTestRule.onNodeWithText("张先生").assertIsDisplayed()
    }

    @Test
    fun given_userWithoutAvatar_when_screenDisplayed_then_showDefaultAvatar() {
        // Given - 无头像用户
        val profileWithoutAvatar = mockUserProfile.copy(avatar = null)
        composeTestRule.setContent {
            ProfileContent(
                userProfileState = UiDataState.Success(profileWithoutAvatar),
                onRefresh = {},
                onLogout = {}
            )
        }

        // Then - 验证用户信息正确显示
        composeTestRule.onNodeWithText("张先生").assertIsDisplayed()
        composeTestRule.onNodeWithText("138****8888").assertIsDisplayed()
    }

    @Test
    fun given_idleState_when_screenDisplayed_then_showIdleMessage() {
        // Given - 空闲状态
        composeTestRule.setContent {
            ProfileContent(
                userProfileState = UiDataState.Idle,
                onRefresh = {},
                onLogout = {}
            )
        }

        // Then - 验证空闲状态显示
        composeTestRule.onNodeWithText("等待加载...").assertIsDisplayed()
    }

    @Test
    fun given_longNickname_when_screenDisplayed_then_showCorrectly() {
        // Given - 超长昵称
        val longNicknameProfile = mockUserProfile.copy(
            nickname = "这是一个非常长的用户昵称用于测试"
        )
        composeTestRule.setContent {
            ProfileContent(
                userProfileState = UiDataState.Success(longNicknameProfile),
                onRefresh = {},
                onLogout = {}
            )
        }

        // Then - 验证长昵称正确显示
        composeTestRule.onNodeWithText("这是一个非常长的用户昵称用于测试").assertIsDisplayed()
    }

    @Test
    fun given_specialCharacters_when_screenDisplayed_then_showCorrectly() {
        // Given - 特殊字符
        val specialProfile = mockUserProfile.copy(
            nickname = "User@123!",
            homeName = "Home #1"
        )
        composeTestRule.setContent {
            ProfileContent(
                userProfileState = UiDataState.Success(specialProfile),
                onRefresh = {},
                onLogout = {}
            )
        }

        // Then - 验证特殊字符正确显示
        composeTestRule.onNodeWithText("User@123!").assertIsDisplayed()
        composeTestRule.onNodeWithText("Home #1").assertIsDisplayed()
    }

    @Test
    fun given_unauthorizedError_when_screenDisplayed_then_showLoginExpired() {
        // Given - 未授权错误
        composeTestRule.setContent {
            ProfileContent(
                userProfileState = UiDataState.Error(
                    com.wuheng.smart.data.network.AppException.BusinessError(
                        401,
                        "登录已过期，请重新登录"
                    )
                ),
                onRefresh = {},
                onLogout = {}
            )
        }

        // Then - 验证错误信息显示
        composeTestRule.onNodeWithText("登录已过期，请重新登录").assertIsDisplayed()
    }

    @Test
    fun given_serverError_when_screenDisplayed_then_showServerError() {
        // Given - 服务器错误
        composeTestRule.setContent {
            ProfileContent(
                userProfileState = UiDataState.Error(
                    com.wuheng.smart.data.network.AppException.ServerError(
                        500,
                        "服务器内部错误"
                    )
                ),
                onRefresh = {},
                onLogout = {}
            )
        }

        // Then - 验证错误信息显示
        composeTestRule.onNodeWithText("服务器内部错误").assertIsDisplayed()
    }

    @Test
    fun given_timeoutError_when_screenDisplayed_then_showTimeoutMessage() {
        // Given - 超时错误
        composeTestRule.setContent {
            ProfileContent(
                userProfileState = UiDataState.Error(
                    com.wuheng.smart.data.network.AppException.TimeoutError()
                ),
                onRefresh = {},
                onLogout = {}
            )
        }

        // Then - 验证超时错误信息显示
        composeTestRule.onNodeWithText("请求超时").assertIsDisplayed()
    }
}
