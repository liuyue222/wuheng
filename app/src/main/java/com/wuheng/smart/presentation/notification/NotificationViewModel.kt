package com.wuheng.smart.presentation.notification

import androidx.lifecycle.viewModelScope
import com.wuheng.smart.data.model.NotificationApiItem
import com.wuheng.smart.data.network.ApiResult
import com.wuheng.smart.data.network.TokenManager
import com.wuheng.smart.data.repository.HomeRepository
import com.wuheng.smart.presentation.base.BaseViewModel
import com.wuheng.smart.presentation.base.UiDataState
import com.wuheng.smart.presentation.base.createUiStateFlow
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class NotificationViewModel @Inject constructor(
    private val homeRepository: HomeRepository,
    private val tokenManager: TokenManager
) : BaseViewModel() {

    private val _notificationsState = createUiStateFlow<List<NotificationItem>>()
    val notificationsState: StateFlow<UiDataState<List<NotificationItem>>> = _notificationsState.asStateFlow()

    private val _selectedFilter = MutableStateFlow(NotificationFilter.ALL)
    val selectedFilter: StateFlow<NotificationFilter> = _selectedFilter.asStateFlow()

    private val _unreadCount = MutableStateFlow(0)
    val unreadCount: StateFlow<Int> = _unreadCount.asStateFlow()

    private var allNotifications: List<NotificationItem> = emptyList()

    init {
        loadNotifications()
    }

    fun loadNotifications() {
        viewModelScope.launch {
            _notificationsState.value = UiDataState.Loading

            homeRepository.getNotificationList().collectLatest { result ->
                when (result) {
                    is ApiResult.Loading -> {
                        _notificationsState.value = UiDataState.Loading
                    }
                    is ApiResult.Success -> {
                        allNotifications = result.data.map { it.toNotificationItem() }
                        applyFilter()
                        updateUnreadCount()
                        Timber.d("Loaded ${allNotifications.size} notifications")
                    }
                    is ApiResult.Error -> {
                        _notificationsState.value = UiDataState.Error(result.exception)
                        Timber.e(result.exception, "Failed to load notifications")
                    }
                }
            }
        }
    }

    fun refreshNotifications() {
        loadNotifications()
    }

    fun setFilter(filter: NotificationFilter) {
        if (_selectedFilter.value == filter) return
        Timber.d("Setting filter: $filter")
        _selectedFilter.value = filter
        applyFilter()
    }

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

    fun markAsRead(notificationId: String) {
        val idInt = notificationId.toIntOrNull() ?: return
        viewModelScope.launch {
            homeRepository.markNotificationRead(idInt).collectLatest { result ->
                when (result) {
                    is ApiResult.Success -> {
                        allNotifications = allNotifications.map { notification ->
                            if (notification.id == notificationId) {
                                notification.copy(isRead = true)
                            } else {
                                notification
                            }
                        }
                        applyFilter()
                        updateUnreadCount()
                    }
                    is ApiResult.Error -> {
                        Timber.e(result.exception, "Failed to mark notification as read")
                    }
                    is ApiResult.Loading -> {}
                }
            }
        }
    }

    fun markAllAsRead() {
        viewModelScope.launch {
            homeRepository.markAllNotificationsRead().collectLatest { result ->
                when (result) {
                    is ApiResult.Success -> {
                        allNotifications = allNotifications.map { it.copy(isRead = true) }
                        applyFilter()
                        _unreadCount.value = 0
                    }
                    is ApiResult.Error -> {
                        Timber.e(result.exception, "Failed to mark all as read")
                    }
                    is ApiResult.Loading -> {}
                }
            }
        }
    }

    fun clearAllNotifications() {
        viewModelScope.launch {
            homeRepository.clearAllNotifications().collectLatest { result ->
                when (result) {
                    is ApiResult.Success -> {
                        allNotifications = emptyList()
                        applyFilter()
                        _unreadCount.value = 0
                    }
                    is ApiResult.Error -> {
                        Timber.e(result.exception, "Failed to clear notifications")
                    }
                    is ApiResult.Loading -> {}
                }
            }
        }
    }

    private fun updateUnreadCount() {
        val count = allNotifications.count { !it.isRead }
        _unreadCount.value = count
    }

    private fun NotificationApiItem.toNotificationItem(): NotificationItem {
        return NotificationItem(
            id = notificationId.toString(),
            title = title,
            content = content,
            type = mapType(type),
            timestamp = createTime * 1000L,
            isRead = isRead == 1
        )
    }

    private fun mapType(type: String): NotificationType {
        return when (type.lowercase()) {
            "device" -> NotificationType.DEVICE
            "system" -> NotificationType.SYSTEM
            "alert" -> NotificationType.ALERT
            "security" -> NotificationType.SECURITY
            else -> NotificationType.SYSTEM
        }
    }
}
