package com.wuheng.smart.presentation.notification

import app.cash.turbine.test
import com.wuheng.smart.MainDispatcherRule
import com.wuheng.smart.data.model.NotificationApiItem
import com.wuheng.smart.data.network.ApiResult
import com.wuheng.smart.data.network.TokenManager
import com.wuheng.smart.data.repository.HomeRepository
import com.wuheng.smart.presentation.base.UiDataState
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExperimentalCoroutinesApi
@ExtendWith(MainDispatcherRule::class)
@DisplayName("NotificationViewModel Tests")
class NotificationViewModelTest {

    private lateinit var viewModel: NotificationViewModel
    private lateinit var homeRepository: HomeRepository
    private lateinit var tokenManager: TokenManager

    @BeforeEach
    fun setup() {
        homeRepository = mockk(relaxed = true)
        tokenManager = mockk(relaxed = true)

        every { tokenManager.getCurrentHouseId() } returns "2"

        val mockNotifications = listOf(
            NotificationApiItem(1, "system", "系统通知", "您的房屋系统已完成定期巡检", 0, System.currentTimeMillis() / 1000 - 3600),
            NotificationApiItem(2, "alert", "滤芯到期提醒", "末端直饮机滤芯将于7天后到期", 0, System.currentTimeMillis() / 1000 - 7200),
            NotificationApiItem(3, "device", "设备离线提醒", "客厅温控器已离线", 1, System.currentTimeMillis() / 1000 - 86400),
            NotificationApiItem(4, "security", "安全提醒", "检测到异常登录行为", 1, System.currentTimeMillis() / 1000 - 172800)
        )
        val successFlow = flowOf(ApiResult.Success(mockNotifications))
        coEvery { homeRepository.getNotificationList() } returns successFlow

        val unitFlow = flowOf(ApiResult.Success(Unit))
        coEvery { homeRepository.markNotificationRead(any()) } returns unitFlow
        coEvery { homeRepository.markAllNotificationsRead() } returns unitFlow
        coEvery { homeRepository.clearAllNotifications() } returns unitFlow

        viewModel = NotificationViewModel(homeRepository, tokenManager)
    }

    @Test
    @DisplayName("初始状态 - 通知列表应为Success状态")
    fun `initial state - notifications should be Success`() = runTest {
        advanceUntilIdle()

        assertTrue(viewModel.notificationsState.value is UiDataState.Success)
    }

    @Test
    @DisplayName("初始状态 - 默认筛选应为ALL")
    fun `initial state - default filter should be ALL`() = runTest {
        assertEquals(NotificationFilter.ALL, viewModel.selectedFilter.value)
    }

    @Test
    @DisplayName("加载通知 - 应从Repository加载数据")
    fun `load notifications - should load from repository`() = runTest {
        advanceUntilIdle()

        val notifications = (viewModel.notificationsState.value as UiDataState.Success).data
        assertTrue(notifications.isNotEmpty())
    }

    @Test
    @DisplayName("初始状态 - 未读计数应为正确值")
    fun `initial state - unread count should be correct`() = runTest {
        advanceUntilIdle()

        val notifications = (viewModel.notificationsState.value as UiDataState.Success).data
        val expectedUnread = notifications.count { !it.isRead }
        assertEquals(expectedUnread, viewModel.unreadCount.value)
    }

    @Test
    @DisplayName("标记已读 - 应更新通知状态")
    fun `mark as read - should update notification status`() = runTest {
        advanceUntilIdle()

        viewModel.markAsRead("1")
        advanceUntilIdle()

        val notifications = (viewModel.notificationsState.value as UiDataState.Success).data
        val markedNotification = notifications.find { it.id == "1" }
        assertNotNull(markedNotification)
        assertTrue(markedNotification!!.isRead)
    }

    @Test
    @DisplayName("标记全部已读 - 应将所有通知标记为已读")
    fun `mark all as read - should mark all notifications as read`() = runTest {
        advanceUntilIdle()

        viewModel.markAllAsRead()
        advanceUntilIdle()

        val notifications = (viewModel.notificationsState.value as UiDataState.Success).data
        notifications.forEach { assertTrue(it.isRead) }
        assertEquals(0, viewModel.unreadCount.value)
    }

    @Test
    @DisplayName("清空通知 - 应清空所有通知")
    fun `clear all notifications - should clear all`() = runTest {
        advanceUntilIdle()

        viewModel.clearAllNotifications()
        advanceUntilIdle()

        val notifications = (viewModel.notificationsState.value as UiDataState.Success).data
        assertTrue(notifications.isEmpty())
        assertEquals(0, viewModel.unreadCount.value)
    }

    @Test
    @DisplayName("设置筛选 - 应正确更新筛选条件")
    fun `set filter - should update filter state`() = runTest {
        advanceUntilIdle()

        viewModel.setFilter(NotificationFilter.SYSTEM)
        advanceUntilIdle()

        assertEquals(NotificationFilter.SYSTEM, viewModel.selectedFilter.value)
    }
}
