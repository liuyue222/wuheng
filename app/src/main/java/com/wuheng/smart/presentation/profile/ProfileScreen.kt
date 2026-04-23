package com.wuheng.smart.presentation.profile

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.wuheng.smart.data.network.AuthEventManager
import com.wuheng.smart.data.network.TokenManager
import com.wuheng.smart.presentation.components.ErrorRetryView
import com.wuheng.smart.presentation.components.LoadingIndicator
import com.wuheng.smart.presentation.components.ResponsiveContainer
import com.wuheng.smart.presentation.theme.WuHengTheme
import timber.log.Timber

/**
 * 个人中心页面 Screen - 处理ViewModel和状态管理
 * 逻辑和UI分离：Screen负责状态管理，Layout负责纯UI渲染
 */
@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel = hiltViewModel(),
    onNavigateToNotifications: () -> Unit = {},
    onNavigateToConsumables: () -> Unit = {},
    onNavigateToAbout: () -> Unit = {},
    onNavigateToPrivacy: () -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val bookingState by viewModel.bookingState.collectAsStateWithLifecycle()

    // 弹窗状态
    var showServiceTypeDialog by remember { mutableStateOf(false) }
    var showBookingConfirmDialog by remember { mutableStateOf(false) }

    // 处理预约结果
    LaunchedEffect(bookingState) {
        when (bookingState) {
            is com.wuheng.smart.presentation.base.UiDataState.Success -> {
                showBookingConfirmDialog = false
                viewModel.resetBookingState()
            }
            else -> {}
        }
    }

    ProfileScreenContent(
        uiState = uiState,
        bookingState = bookingState,
        selectedServiceType = uiState.selectedServiceType,
        onNotificationClick = onNavigateToNotifications,
        onServiceSelect = { showServiceTypeDialog = true },
        onBookService = { showBookingConfirmDialog = true },
        onConsumablesClick = onNavigateToConsumables,
        onAboutClick = onNavigateToAbout,
        onPrivacyClick = onNavigateToPrivacy,
        onLogout = { performLogout() },
        onRefresh = { viewModel.refreshData() }
    )

    // 服务类型选择弹窗
    if (showServiceTypeDialog) {
        ServiceTypeSelectorDialog(
            selectedType = uiState.selectedServiceType,
            onTypeSelected = { type ->
                viewModel.selectServiceType(type)
                showServiceTypeDialog = false
            },
            onDismiss = { showServiceTypeDialog = false }
        )
    }

    // 预约确认弹窗
    val selectedServiceType = uiState.selectedServiceType
    if (showBookingConfirmDialog && selectedServiceType != null) {
        ServiceBookingConfirmDialog(
            serviceType = selectedServiceType,
            bookingState = bookingState,
            onConfirm = {
                viewModel.confirmBooking()
            },
            onDismiss = {
                showBookingConfirmDialog = false
                viewModel.resetBookingState()
            }
        )
    }
}

/**
 * 执行登出操作
 * 1. 清除 TokenManager 中的用户信息
 * 2. 发送 AuthEvent.LogoutSuccess 事件
 */
private fun performLogout() {
    Timber.d("Performing logout")
    // 发送登出成功事件，MainActivity 会监听并跳转到登录页
    AuthEventManager.postLogoutSuccessEvent()
}

/**
 * 个人中心页面内容 - 纯UI，接收状态和回调
 */
@Composable
private fun ProfileScreenContent(
    uiState: ProfileUiState,
    bookingState: com.wuheng.smart.presentation.base.UiDataState<Unit> = com.wuheng.smart.presentation.base.UiDataState.Idle,
    selectedServiceType: ServiceType? = null,
    onNotificationClick: () -> Unit,
    onServiceSelect: () -> Unit,
    onBookService: () -> Unit,
    onConsumablesClick: () -> Unit,
    onAboutClick: () -> Unit,
    onPrivacyClick: () -> Unit,
    onLogout: () -> Unit,
    onRefresh: () -> Unit
) {
    ResponsiveContainer { maxWidth, _ ->
        when {
            uiState.isLoading -> {
                LoadingIndicator()
            }
            uiState.errorMessage != null -> {
                ErrorRetryView(
                    message = uiState.errorMessage,
                    onRetry = onRefresh
                )
            }
            else -> {
                ProfileLayout(
                    uiState = uiState,
                    selectedServiceType = selectedServiceType,
                    onNotificationClick = onNotificationClick,
                    onServiceSelect = onServiceSelect,
                    onBookService = onBookService,
                    onConsumablesClick = onConsumablesClick,
                    onAboutClick = onAboutClick,
                    onPrivacyClick = onPrivacyClick,
                    onLogout = onLogout,
                    maxWidth = maxWidth
                )
            }
        }
    }
}

// ==================== Preview ====================

@Preview(showBackground = true, name = "个人中心-正常状态", backgroundColor = 0xFFF0F4F8)
@Composable
fun ProfileScreenPreview() {
    WuHengTheme {
        ProfileScreenContent(
            uiState = ProfileUiState(
                userName = "张先生",
                residenceName = "西湖壹号院",
                role = "业主",
                hasNotification = true,
                projectDescription = "西湖一号院五恒系统V20采用最先进的辐射空调技术，集成了地源热泵与毛细管网系统，为您提供恒温、恒湿、恒氧、恒洁、恒静的极致居住体验。",
                lastServiceDate = "2026.02.15",
                version = "V1.2.3"
            ),
            onNotificationClick = {},
            onServiceSelect = {},
            onBookService = {},
            onConsumablesClick = {},
            onAboutClick = {},
            onPrivacyClick = {},
            onLogout = {},
            onRefresh = {}
        )
    }
}

@Preview(showBackground = true, name = "个人中心-宽屏720dp", widthDp = 720, backgroundColor = 0xFFF0F4F8)
@Composable
fun ProfileScreenWidePreview() {
    WuHengTheme {
        ProfileScreenContent(
            uiState = ProfileUiState(
                userName = "张先生",
                residenceName = "西湖壹号院",
                role = "业主",
                hasNotification = true,
                projectDescription = "西湖一号院五恒系统V20采用最先进的辐射空调技术，集成了地源热泵与毛细管网系统，为您提供恒温、恒湿、恒氧、恒洁、恒静的极致居住体验。",
                lastServiceDate = "2026.02.15",
                version = "V1.2.3"
            ),
            onNotificationClick = {},
            onServiceSelect = {},
            onBookService = {},
            onConsumablesClick = {},
            onAboutClick = {},
            onPrivacyClick = {},
            onLogout = {},
            onRefresh = {}
        )
    }
}
