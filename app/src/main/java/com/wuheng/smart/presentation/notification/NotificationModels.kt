package com.wuheng.smart.presentation.notification

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import com.wuheng.smart.presentation.theme.*

/**
 * 通知类型
 */
enum class NotificationType(
    val icon: ImageVector = Icons.Filled.Info,
    val iconColor: Color = PrimaryBlue,
    val backgroundColor: Color = PrimaryBlue.copy(alpha = 0.1f)
) {
    SYSTEM(
        icon = Icons.Filled.Info,
        iconColor = PrimaryBlue,
        backgroundColor = PrimaryBlue.copy(alpha = 0.1f)
    ),
    MAINTENANCE(
        icon = Icons.Filled.Build,
        iconColor = SuccessGreen,
        backgroundColor = SuccessGreen.copy(alpha = 0.1f)
    ),
    ALERT(
        icon = Icons.Filled.Warning,
        iconColor = ErrorRed,
        backgroundColor = ErrorRed.copy(alpha = 0.1f)
    ),
    SCENE(
        icon = Icons.Filled.Settings,
        iconColor = PrimaryBlue,
        backgroundColor = PrimaryBlue.copy(alpha = 0.1f)
    ),
    DEVICE(
        icon = Icons.Filled.Devices,
        iconColor = SuccessGreen,
        backgroundColor = SuccessGreen.copy(alpha = 0.1f)
    ),
    SECURITY(
        icon = Icons.Filled.Security,
        iconColor = WarningYellow,
        backgroundColor = WarningYellow.copy(alpha = 0.1f)
    )
}

/**
 * 通知数据模型
 */
data class NotificationItem(
    val id: String,
    val type: NotificationType,
    val title: String,
    val content: String,
    val timestamp: Long,
    val isRead: Boolean = false,
    val extraData: Map<String, String> = emptyMap()
)

/**
 * 通知筛选类型
 */
enum class NotificationFilter(val label: String) {
    ALL("全部"),
    SYSTEM("系统"),
    DEVICE("设备"),
    SECURITY("安全")
}
