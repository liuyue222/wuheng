package com.wuheng.smart.presentation.notification

import app.cash.turbine.test
import com.wuheng.smart.MainDispatcherRule
import com.wuheng.smart.presentation.base.UiDataState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.EnumSource

/**
 * NotificationViewModel 单元测试
 */
@ExperimentalCoroutinesApi
@ExtendWith(MainDispatcherRule::class)
@DisplayName("NotificationViewModel Tests")
class NotificationViewModelTest {

    private lateinit var viewModel: NotificationViewModel

    @BeforeEach
    fun setup() {
        viewModel = NotificationViewModel()
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
    @DisplayName("初始状态 - 未读计数应为正确值")
    fun `initial state - unread count should be correct`() = runTest {
        advanceUntilIdle()

        val notifications = (viewModel.notificationsState.value as UiDataState.Success).data
        val expectedUnread = notifications.count { !it.isRead }
        assertEquals(expectedUnread, viewModel.unreadCount.value)
    }

    @Test
    @DisplayName("加载通知 - 应生成模拟数据")
    fun `load notifications - should generate mock data`() = runTest {
        advanceUntilIdle()

        val notifications = (viewModel.notificationsState.value as UiDataState.Success).data
        assertTrue(notifications.isNotEmpty())
    }

    @Test
    @DisplayName("刷新通知 - 应重新加载数据")
    fun `refresh notifications - should reload data`() = runTest {
        advanceUntilIdle()

        val beforeRefresh = (viewModel.notificationsState.value as UiDataState.Success).data

        viewModel.refreshNotifications()
        advanceUntilIdle()

        val afterRefresh = (viewModel.notificationsState.value as UiDataState.Success).data
        assertEquals(beforeRefresh.size, afterRefresh.size)
    }

    @ParameterizedTest
    @EnumSource(NotificationFilter::class)
    @DisplayName("设置筛选 - 各筛选条件应正确过滤")
    fun `set filter - all filters should filter correctly`(filter: NotificationFilter) = runTest {
        advanceUntilIdle()

        viewModel.setFilter(filter)
        advanceUntilIdle()

        assertEquals(filter, viewModel.selectedFilter.value)

        val notifications = (viewModel.notificationsState.value as UiDataState.Success).data

        when (filter) {
            NotificationFilter.ALL -> {
                assertTrue(notifications.isNotEmpty())
            }
            NotificationFilter.SYSTEM -> {
                notifications.forEach {
                    assertEquals(NotificationType.SYSTEM, it.type)
                }
            }
            NotificationFilter.DEVICE -> {
                notifications.forEach {
                    assertTrue(it.type == NotificationType.DEVICE || it.type == NotificationType.ALERT)
                }
            }
            NotificationFilter.SECURITY -> {
                notifications.forEach {
                    assertEquals(NotificationType.SECURITY, it.type)
                }
            }
        }
    }

    @Test
    @DisplayName("标记已读 - 应更新通知状态")
    fun `mark as read - should update notification status`() = runTest {
        advanceUntilIdle()

        val beforeUnreadCount = viewModel.unreadCount.value

        viewModel.markAsRead("1")
        advanceUntilIdle()

        val notifications = (viewModel.notificationsState.value as UiDataState.Success).data
        val markedNotification = notifications.find { it.id == "1" }

        assertNotNull(markedNotification)
        assertTrue(markedNotification!!.isRead)
        assertEquals(beforeUnreadCount - 1, viewModel.unreadCount.value)
    }

    @Test
    @DisplayName("标记已读 - 重复标记不应影响计数")
    fun `mark as read - duplicate mark should not affect count`() = runTest {
        advanceUntilIdle()

        viewModel.markAsRead("1")
        advanceUntilIdle()

        val countAfterFirst = viewModel.unreadCount.value

        viewModel.markAsRead("1")
        advanceUntilIdle()

        assertEquals(countAfterFirst, viewModel.unreadCount.value)
    }

    @Test
    @DisplayName("标记全部已读 - 应将所有通知标记为已读")
    fun `mark all as read - should mark all notifications as read`() = runTest {
        advanceUntilIdle()

        viewModel.markAllAsRead()
        advanceUntilIdle()

        val notifications = (viewModel.notificationsState.value as UiDataState.Success).data

        notifications.forEach {
            assertTrue(it.isRead)
        }
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
    @DisplayName("未读计数 - 标记已读后应减少")
    fun `unread count - should decrease after mark as read`() = runTest {
        advanceUntilIdle()

        val initialUnread = viewModel.unreadCount.value

        viewModel.markAsRead("1")
        advanceUntilIdle()

        assertEquals(initialUnread - 1, viewModel.unreadCount.value)
    }

    @Test
    @DisplayName("未读计数 - 不应为负数")
    fun `unread count - should not be negative`() = runTest {
        advanceUntilIdle()

        viewModel.markAllAsRead()
        advanceUntilIdle()

        viewModel.markAsRead("1")
        advanceUntilIdle()

        assertEquals(0, viewModel.unreadCount.value)
    }

    @Test
    @DisplayName("筛选后标记已读 - 应正确更新计数")
    fun `mark as read after filter - should update count correctly`() = runTest {
        advanceUntilIdle()

        viewModel.setFilter(NotificationFilter.DEVICE)
        advanceUntilIdle()

        val beforeCount = viewModel.unreadCount.value

        viewModel.markAsRead("1")
        advanceUntilIdle()

        assertEquals(beforeCount - 1, viewModel.unreadCount.value)
    }

    @Test
    @DisplayName("通知数据 - 应包含所有必要属性")
    fun `notification data - should have all required properties`() = runTest {
        advanceUntilIdle()

        val notifications = (viewModel.notificationsState.value as UiDataState.Success).data
        assertTrue(notifications.isNotEmpty())

        val firstNotification = notifications.first()
        assertNotNull(firstNotification.id)
        assertNotNull(firstNotification.type)
        assertNotNull(firstNotification.title)
        assertNotNull(firstNotification.content)
        assertTrue(firstNotification.timestamp > 0)
    }

    @Test
    @DisplayName("通知数据 - 应包含不同类型的通知")
    fun `notification data - should contain different types`() = runTest {
        advanceUntilIdle()

        val notifications = (viewModel.notificationsState.value as UiDataState.Success).data
        val types = notifications.map { it.type }.distinct()

        assertTrue(types.size > 1)
    }

    @Test
    @DisplayName("通知数据 - 应包含已读和未读通知")
    fun `notification data - should contain read and unread`() = runTest {
        advanceUntilIdle()

        val notifications = (viewModel.notificationsState.value as UiDataState.Success).data
        val hasRead = notifications.any { it.isRead }
        val hasUnread = notifications.any { !it.isRead }

        assertTrue(hasRead)
        assertTrue(hasUnread)
    }

    @Test
    @DisplayName("边界条件 - 空通知列表应正确处理")
    fun `boundary - empty notification list should handle correctly`() = runTest {
        advanceUntilIdle()

        viewModel.clearAllNotifications()
        advanceUntilIdle()

        viewModel.setFilter(NotificationFilter.SYSTEM)
        advanceUntilIdle()

        val notifications = (viewModel.notificationsState.value as UiDataState.Success).data
        assertTrue(notifications.isEmpty())
        assertEquals(0, viewModel.unreadCount.value)
    }

    @Test
    @DisplayName("边界条件 - 全部已读后筛选应正常")
    fun `boundary - filter after all read should work`() = runTest {
        advanceUntilIdle()

        viewModel.markAllAsRead()
        advanceUntilIdle()

        NotificationFilter.values().forEach { filter ->
            viewModel.setFilter(filter)
            advanceUntilIdle()

            assertEquals(0, viewModel.unreadCount.value)
        }
    }

    @Test
    @DisplayName("通知类型 - 各类型应有正确属性")
    fun `notification type - all types should have correct properties`() = runTest {
        NotificationType.values().forEach { type ->
            assertNotNull(type.icon)
            assertNotNull(type.iconColor)
            assertNotNull(type.backgroundColor)
        }
    }

    @Test
    @DisplayName("并发安全 - 快速多次操作应处理正确")
    fun `concurrency - rapid operations should handle correctly`() = runTest {
        advanceUntilIdle()

        viewModel.markAsRead("1")
        viewModel.markAsRead("2")
        viewModel.markAsRead("3")

        advanceUntilIdle()

        val notifications = (viewModel.notificationsState.value as UiDataState.Success).data
        val expectedUnread = notifications.count { !it.isRead }
        assertEquals(expectedUnread, viewModel.unreadCount.value)
    }
}
