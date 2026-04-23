@file:OptIn(ExperimentalMaterial3Api::class)

package com.wuheng.smart.presentation.notification

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.wuheng.smart.presentation.base.UiDataState
import com.wuheng.smart.presentation.theme.*
import java.text.SimpleDateFormat
import java.util.*

// 导入tabIndicatorOffset
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset

/**
 * 通知中心页面 Composable (完善版)
 *
 * 布局结构：
 * - 顶部导航栏: 返回按钮 + 标题"通知中心" + 清空按钮
 * - 分类筛选Tab: 全部/系统/设备/安全
 * - 通知列表: 通知项展示（图标、标题、内容、时间、已读状态）
 * - 空状态: 无通知时的提示
 *
 * 完成度: 100%
 */
@Composable
fun NotificationScreen(
    viewModel: NotificationViewModel = hiltViewModel(),
    onNavigateBack: () -> Unit = {},
    onNavigateToNotificationDetail: (String) -> Unit = {}
) {
    val notificationsState by viewModel.notificationsState.collectAsStateWithLifecycle()
    val selectedFilter by viewModel.selectedFilter.collectAsStateWithLifecycle()
    val unreadCount by viewModel.unreadCount.collectAsStateWithLifecycle()

    NotificationContent(
        notificationsState = notificationsState,
        selectedFilter = selectedFilter,
        unreadCount = unreadCount,
        onNavigateBack = onNavigateBack,
        onFilterSelected = { viewModel.setFilter(it) },
        onNotificationClick = { notification ->
            viewModel.markAsRead(notification.id)
            onNavigateToNotificationDetail(notification.id)
        },
        onMarkAllAsRead = { viewModel.markAllAsRead() },
        onClearAll = { viewModel.clearAllNotifications() },
        onRefresh = { viewModel.refreshNotifications() }
    )
}

/**
 * 通知中心页面内容
 */
@Composable
fun NotificationContent(
    notificationsState: UiDataState<List<NotificationItem>>,
    selectedFilter: NotificationFilter,
    unreadCount: Int,
    onNavigateBack: () -> Unit = {},
    onFilterSelected: (NotificationFilter) -> Unit = {},
    onNotificationClick: (NotificationItem) -> Unit = {},
    onMarkAllAsRead: () -> Unit = {},
    onClearAll: () -> Unit = {},
    onRefresh: () -> Unit = {}
) {
    var showClearConfirmDialog by remember { mutableStateOf(false) }
    var showMenu by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(spacing_sm)
                    ) {
                        Text(
                            text = "通知中心",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.SemiBold,
                            color = TextPrimaryLight
                        )
                        if (unreadCount > 0) {
                            Badge(
                                containerColor = ErrorRed,
                                contentColor = Color.White
                            ) {
                                Text(
                                    text = unreadCount.toString(),
                                    fontSize = 12.sp
                                )
                            }
                        }
                    }
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "返回",
                            tint = TextPrimaryLight
                        )
                    }
                },
                actions = {
                    // 更多菜单
                    Box {
                        IconButton(onClick = { showMenu = true }) {
                            Icon(
                                imageVector = Icons.Filled.MoreVert,
                                contentDescription = "更多",
                                tint = TextPrimaryLight
                            )
                        }

                        DropdownMenu(
                            expanded = showMenu,
                            onDismissRequest = { showMenu = false },
                            modifier = Modifier.background(SurfaceLight)
                        ) {
                            DropdownMenuItem(
                                text = { Text("全部标为已读") },
                                leadingIcon = {
                                    Icon(Icons.Filled.DoneAll, null, modifier = Modifier.size(20.dp))
                                },
                                onClick = {
                                    showMenu = false
                                    onMarkAllAsRead()
                                }
                            )
                            DropdownMenuItem(
                                text = { Text("清空所有通知", color = ErrorRed) },
                                leadingIcon = {
                                    Icon(
                                        Icons.Filled.DeleteSweep,
                                        null,
                                        modifier = Modifier.size(20.dp),
                                        tint = ErrorRed
                                    )
                                },
                                onClick = {
                                    showMenu = false
                                    showClearConfirmDialog = true
                                }
                            )
                        }
                    }
                },
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = BackgroundLight
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(BackgroundLight)
        ) {
            // 分类筛选Tab
            NotificationFilterTabs(
                selectedFilter = selectedFilter,
                onFilterSelected = onFilterSelected,
                unreadCount = unreadCount
            )

            // 通知列表内容
            when (notificationsState) {
                is UiDataState.Idle, is UiDataState.Loading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(color = PrimaryBlue)
                    }
                }
                is UiDataState.Error -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(spacing_md)
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Error,
                                contentDescription = null,
                                tint = ErrorRed,
                                modifier = Modifier.size(48.dp)
                            )
                            Text(
                                text = "加载失败",
                                style = MaterialTheme.typography.bodyLarge,
                                color = TextSecondaryLight
                            )
                            Button(
                                onClick = onRefresh,
                                colors = ButtonDefaults.buttonColors(containerColor = PrimaryBlue)
                            ) {
                                Text("重试")
                            }
                        }
                    }
                }
                is UiDataState.Success -> {
                    val notifications = notificationsState.data

                    if (notifications.isEmpty()) {
                        // 空状态
                        EmptyNotificationState(
                            filter = selectedFilter,
                            onRefresh = onRefresh
                        )
                    } else {
                        // 通知列表
                        LazyColumn(
                            modifier = Modifier.fillMaxSize(),
                            contentPadding = PaddingValues(
                                horizontal = page_margin_horizontal,
                                vertical = spacing_md
                            ),
                            verticalArrangement = Arrangement.spacedBy(spacing_sm)
                        ) {
                            items(
                                items = notifications,
                                key = { it.id }
                            ) { notification ->
                                NotificationItemCard(
                                    notification = notification,
                                    onClick = { onNotificationClick(notification) }
                                )
                            }

                            // 底部留白
                            item { Spacer(modifier = Modifier.height(spacing_lg)) }
                        }
                    }
                }
                else -> {}
            }
        }
    }

    // 清空确认对话框
    if (showClearConfirmDialog) {
        AlertDialog(
            onDismissRequest = { showClearConfirmDialog = false },
            title = { Text("清空通知") },
            text = { Text("确定要清空所有通知吗？此操作不可撤销。") },
            confirmButton = {
                TextButton(
                    onClick = {
                        onClearAll()
                        showClearConfirmDialog = false
                    },
                    colors = ButtonDefaults.textButtonColors(contentColor = ErrorRed)
                ) {
                    Text("清空")
                }
            },
            dismissButton = {
                TextButton(onClick = { showClearConfirmDialog = false }) {
                    Text("取消")
                }
            }
        )
    }
}

/**
 * 通知分类筛选Tab
 */
@Composable
private fun NotificationFilterTabs(
    selectedFilter: NotificationFilter,
    onFilterSelected: (NotificationFilter) -> Unit,
    unreadCount: Int
) {
    val filters = NotificationFilter.values()

    ScrollableTabRow(
        selectedTabIndex = filters.indexOf(selectedFilter),
        containerColor = BackgroundLight,
        contentColor = PrimaryBlue,
        edgePadding = page_margin_horizontal,
        indicator = { tabPositions ->
            TabRowDefaults.Indicator(
                modifier = Modifier.tabIndicatorOffset(tabPositions[filters.indexOf(selectedFilter)]),
                color = PrimaryBlue,
                height = 3.dp
            )
        }
    ) {
        filters.forEach { filter ->
            Tab(
                selected = selectedFilter == filter,
                onClick = { onFilterSelected(filter) },
                text = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Text(
                            text = filter.label,
                            fontSize = text_body_size,
                            fontWeight = if (selectedFilter == filter) FontWeight.SemiBold else FontWeight.Normal
                        )
                        // 显示未读数量（仅在全部标签）
                        if (filter == NotificationFilter.ALL && unreadCount > 0) {
                            Badge(
                                containerColor = ErrorRed,
                                contentColor = Color.White
                            ) {
                                Text(
                                    text = if (unreadCount > 99) "99+" else unreadCount.toString(),
                                    fontSize = 10.sp
                                )
                            }
                        }
                    }
                },
                selectedContentColor = PrimaryBlue,
                unselectedContentColor = TextSecondaryLight
            )
        }
    }
}

/**
 * 通知项卡片
 */
@Composable
private fun NotificationItemCard(
    notification: NotificationItem,
    onClick: () -> Unit
) {
    val backgroundColor = if (notification.isRead) SurfaceLight else SurfaceLight.copy(alpha = 0.95f)
    val borderColor = if (notification.isRead) Color.Transparent else PrimaryBlue.copy(alpha = 0.3f)

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(
                elevation = elevation_sm,
                shape = RoundedCornerShape(corner_md),
                ambientColor = ShadowLight,
                spotColor = ShadowLight
            )
            .clip(RoundedCornerShape(corner_md))
            .background(backgroundColor)
            .then(
                if (!notification.isRead) {
                    Modifier.border(
                        width = 1.dp,
                        color = borderColor,
                        shape = RoundedCornerShape(corner_md)
                    )
                } else Modifier
            )
            .clickable(onClick = onClick)
            .padding(card_padding_large)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(spacing_md),
            verticalAlignment = Alignment.Top
        ) {
            // 图标
            Box(
                modifier = Modifier
                    .size(44.dp)
                    .clip(RoundedCornerShape(corner_sm))
                    .background(notification.type.backgroundColor),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = notification.type.icon,
                    contentDescription = null,
                    tint = notification.type.iconColor,
                    modifier = Modifier.size(24.dp)
                )
            }

            // 内容
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(spacing_xs)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = notification.title,
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = if (notification.isRead) FontWeight.Normal else FontWeight.SemiBold,
                        color = TextPrimaryLight,
                        fontSize = text_body_size,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )

                    // 未读指示点
                    if (!notification.isRead) {
                        Box(
                            modifier = Modifier
                                .size(8.dp)
                                .clip(CircleShape)
                                .background(PrimaryBlue)
                        )
                    }
                }

                Text(
                    text = notification.content,
                    style = MaterialTheme.typography.bodySmall,
                    color = TextSecondaryLight,
                    fontSize = text_caption_size,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

                Text(
                    text = formatNotificationTime(notification.timestamp),
                    style = MaterialTheme.typography.labelSmall,
                    color = TextTertiaryLight,
                    fontSize = 11.sp
                )
            }
        }
    }
}

/**
 * 空状态
 */
@Composable
private fun EmptyNotificationState(
    filter: NotificationFilter,
    onRefresh: () -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(spacing_md)
        ) {
            Icon(
                imageVector = Icons.Filled.NotificationsNone,
                contentDescription = null,
                tint = TextTertiaryLight,
                modifier = Modifier.size(64.dp)
            )
            Text(
                text = if (filter == NotificationFilter.ALL) "暂无通知" else "暂无${filter.label}通知",
                style = MaterialTheme.typography.bodyLarge,
                color = TextSecondaryLight
            )
            TextButton(onClick = onRefresh) {
                Text("刷新")
            }
        }
    }
}

/**
 * 格式化通知时间
 */
private fun formatNotificationTime(timestamp: Long): String {
    val now = System.currentTimeMillis()
    val diff = now - timestamp

    return when {
        diff < 60 * 1000 -> "刚刚"
        diff < 60 * 60 * 1000 -> "${diff / (60 * 1000)}分钟前"
        diff < 24 * 60 * 60 * 1000 -> "${diff / (60 * 60 * 1000)}小时前"
        diff < 7 * 24 * 60 * 60 * 1000 -> "${diff / (24 * 60 * 60 * 1000)}天前"
        else -> {
            val sdf = SimpleDateFormat("MM-dd HH:mm", Locale.getDefault())
            sdf.format(Date(timestamp))
        }
    }
}

// ==================== Preview 函数 ====================

@Preview(showBackground = true, name = "通知中心-正常", backgroundColor = 0xFFF1F5F9)
@Composable
fun NotificationScreenPreview() {
    WuHengTheme {
        NotificationContent(
            notificationsState = UiDataState.Success(
                listOf(
                    NotificationItem(
                        id = "1",
                        type = NotificationType.DEVICE,
                        title = "设备离线提醒",
                        content = "客厅温控器已离线，请检查设备连接状态",
                        timestamp = System.currentTimeMillis() - 5 * 60 * 1000,
                        isRead = false
                    ),
                    NotificationItem(
                        id = "2",
                        type = NotificationType.SYSTEM,
                        title = "系统维护通知",
                        content = "系统将于今晚凌晨2点进行例行维护，预计持续30分钟",
                        timestamp = System.currentTimeMillis() - 2 * 60 * 60 * 1000,
                        isRead = false
                    ),
                    NotificationItem(
                        id = "3",
                        type = NotificationType.ALERT,
                        title = "温度异常警告",
                        content = "主卧温度超过设定阈值，当前温度28°C",
                        timestamp = System.currentTimeMillis() - 30 * 60 * 1000,
                        isRead = true
                    ),
                    NotificationItem(
                        id = "4",
                        type = NotificationType.SECURITY,
                        title = "安全提醒",
                        content = "检测到异常登录行为，请确认是否为本人操作",
                        timestamp = System.currentTimeMillis() - 24 * 60 * 60 * 1000,
                        isRead = true
                    )
                )
            ),
            selectedFilter = NotificationFilter.ALL,
            unreadCount = 2
        )
    }
}

@Preview(showBackground = true, name = "通知中心-空状态", backgroundColor = 0xFFF1F5F9)
@Composable
fun NotificationScreenEmptyPreview() {
    WuHengTheme {
        NotificationContent(
            notificationsState = UiDataState.Success(emptyList()),
            selectedFilter = NotificationFilter.ALL,
            unreadCount = 0
        )
    }
}

@Preview(showBackground = true, name = "通知中心-加载中", backgroundColor = 0xFFF1F5F9)
@Composable
fun NotificationScreenLoadingPreview() {
    WuHengTheme {
        NotificationContent(
            notificationsState = UiDataState.Loading,
            selectedFilter = NotificationFilter.ALL,
            unreadCount = 0
        )
    }
}
