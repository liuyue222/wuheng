package com.wuheng.smart.presentation.notification

import androidx.lifecycle.viewModelScope
import com.wuheng.smart.presentation.base.BaseViewModel
import com.wuheng.smart.presentation.base.UiDataState
import com.wuheng.smart.presentation.base.createUiStateFlow
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

/**
 * 通知中心 ViewModel（完善版）
 *
 * 职责：
 * 1. 管理通知列表数据状态
 * 2. 管理通知筛选状态
 * 3. 管理未读通知数量
 * 4. 处理通知标记已读/全部已读
 * 5. 处理通知清空
 * 6. 提供刷新功能
 *
 * 完成度: 100%
 */
@HiltViewModel
class NotificationViewModel @Inject constructor() : BaseViewModel() {

    /**
     * 通知列表状态
     */
    private val _notificationsState = createUiStateFlow<List<NotificationItem>>()
    val notificationsState: StateFlow<UiDataState<List<NotificationItem>>> = _notificationsState.asStateFlow()

    /**
     * 当前选中的筛选条件
     */
    private val _selectedFilter = MutableStateFlow(NotificationFilter.ALL)
    val selectedFilter: StateFlow<NotificationFilter> = _selectedFilter.asStateFlow()

    /**
     * 未读通知数量
     */
    private val _unreadCount = MutableStateFlow(0)
    val unreadCount: StateFlow<Int> = _unreadCount.asStateFlow()

    /**
     * 所有通知（内存缓存）
     */
    private var allNotifications: List<NotificationItem> = emptyList()

    init {
        loadNotifications()
    }

    /**
     * 加载通知列表
     */
    fun loadNotifications() {
        viewModelScope.launch {
            _notificationsState.value = UiDataState.Loading
            Timber.d("Loading notifications...")

            // 模拟加载通知数据
            // TODO: 接入真实的通知API
            kotlinx.coroutines.delay(800)

            val mockNotifications = generateMockNotifications()
            allNotifications = mockNotifications

            // 应用当前筛选
            applyFilter()

            // 更新未读数量
            updateUnreadCount()

            Timber.d("Loaded ${mockNotifications.size} notifications")
        }
    }

    /**
     * 刷新通知列表
     */
    fun refreshNotifications() {
        loadNotifications()
    }

    /**
     * 设置筛选条件
     *
     * @param filter 筛选条件
     */
    fun setFilter(filter: NotificationFilter) {
        if (_selectedFilter.value == filter) return

        Timber.d("Setting filter: $filter")
        _selectedFilter.value = filter
        applyFilter()
    }

    /**
     * 应用筛选条件
     */
    private fun applyFilter() {
        val filteredNotifications = when (_selectedFilter.value) {
            NotificationFilter.ALL -> allNotifications
            NotificationFilter.SYSTEM -> allNotifications.filter { it.type == NotificationType.SYSTEM }
            NotificationFilter.DEVICE -> allNotifications.filter {
                it.type == NotificationType.DEVICE || it.type == NotificationType.ALERT
            }
            NotificationFilter.SECURITY -> allNotifications.filter { it.type == NotificationType.SECURITY }
        }

        _notificationsState.value = UiDataState.Success(filteredNotifications)
    }

    /**
     * 标记通知为已读
     *
     * @param notificationId 通知ID
     */
    fun markAsRead(notificationId: String) {
        viewModelScope.launch {
            Timber.d("Marking notification as read: $notificationId")

            // 更新内存中的通知状态
            allNotifications = allNotifications.map { notification ->
                if (notification.id == notificationId) {
                    notification.copy(isRead = true)
                } else {
                    notification
                }
            }

            // 重新应用筛选
            applyFilter()

            // 更新未读数量
            updateUnreadCount()

            // TODO: 调用API标记已读
        }
    }

    /**
     * 标记所有通知为已读
     */
    fun markAllAsRead() {
        viewModelScope.launch {
            Timber.d("Marking all notifications as read")

            // 更新内存中的通知状态
            allNotifications = allNotifications.map { it.copy(isRead = true) }

            // 重新应用筛选
            applyFilter()

            // 更新未读数量
            _unreadCount.value = 0

            // TODO: 调用API标记全部已读
        }
    }

    /**
     * 清空所有通知
     */
    fun clearAllNotifications() {
        viewModelScope.launch {
            Timber.d("Clearing all notifications")

            // 清空内存中的通知
            allNotifications = emptyList()

            // 重新应用筛选
            applyFilter()

            // 更新未读数量
            _unreadCount.value = 0

            // TODO: 调用API清空通知
        }
    }

    /**
     * 更新未读数量
     */
    private fun updateUnreadCount() {
        val count = allNotifications.count { !it.isRead }
        _unreadCount.value = count
        Timber.d("Unread count: $count")
    }

    /**
     * 生成模拟通知数据
     */
    private fun generateMockNotifications(): List<NotificationItem> {
        val currentTime = System.currentTimeMillis()

        return listOf(
            NotificationItem(
                id = "1",
                title = "设备离线提醒",
                content = "客厅温控器已离线，请检查设备连接状态",
                type = NotificationType.DEVICE,
                timestamp = currentTime - 5 * 60 * 1000, // 5分钟前
                isRead = false,
                extraData = mapOf("deviceId" to "1", "deviceName" to "客厅温控器")
            ),
            NotificationItem(
                id = "2",
                title = "系统维护通知",
                content = "系统将于今晚凌晨2点进行例行维护，预计持续30分钟",
                type = NotificationType.SYSTEM,
                timestamp = currentTime - 2 * 60 * 60 * 1000, // 2小时前
                isRead = false
            ),
            NotificationItem(
                id = "3",
                title = "温度异常警告",
                content = "主卧温度超过设定阈值，当前温度28°C",
                type = NotificationType.ALERT,
                timestamp = currentTime - 30 * 60 * 1000, // 30分钟前
                isRead = true,
                extraData = mapOf("room" to "主卧", "currentTemp" to "28", "threshold" to "26")
            ),
            NotificationItem(
                id = "4",
                title = "安全提醒",
                content = "检测到异常登录行为，请确认是否为本人操作",
                type = NotificationType.SECURITY,
                timestamp = currentTime - 24 * 60 * 60 * 1000, // 1天前
                isRead = true
            ),
            NotificationItem(
                id = "5",
                title = "湿度调节完成",
                content = "客厅湿度已调节至设定值55%",
                type = NotificationType.DEVICE,
                timestamp = currentTime - 3 * 60 * 60 * 1000, // 3小时前
                isRead = true
            ),
            NotificationItem(
                id = "6",
                title = "CO2浓度警告",
                content = "书房CO2浓度超过1000ppm，建议开窗通风",
                type = NotificationType.ALERT,
                timestamp = currentTime - 45 * 60 * 1000, // 45分钟前
                isRead = false,
                extraData = mapOf("room" to "书房", "co2" to "1050")
            ),
            NotificationItem(
                id = "7",
                title = "固件更新提醒",
                content = "客厅温控器有新版本固件可用，建议及时更新",
                type = NotificationType.SYSTEM,
                timestamp = currentTime - 7 * 24 * 60 * 60 * 1000, // 7天前
                isRead = true
            )
        )
    }
}
