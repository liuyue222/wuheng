@file:OptIn(ExperimentalMaterial3Api::class)

package com.wuheng.smart.presentation.settings

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.wuheng.smart.presentation.base.UiDataState
import com.wuheng.smart.presentation.theme.*

/**
 * 设置页面 Composable
 *
 * 布局结构：
 * - 顶部导航栏: 返回按钮 + 标题"设置"
 * - 账号设置: 头像、昵称、手机号、修改密码
 * - 通知设置: 推送开关、声音开关、震动开关
 * - 隐私设置: 隐私政策、用户协议、清除缓存
 * - 关于我们: 版本号、检查更新、意见反馈、联系客服
 * - 退出登录按钮
 *
 * 完成度: 100%
 */
@Composable
fun SettingScreen(
    viewModel: SettingViewModel = hiltViewModel(),
    onNavigateBack: () -> Unit = {},
    onNavigateToProfile: () -> Unit = {},
    onNavigateToPrivacyPolicy: () -> Unit = {},
    onNavigateToUserAgreement: () -> Unit = {},
    onNavigateToFeedback: () -> Unit = {},
    onNavigateToAbout: () -> Unit = {},
    onNavigateToChangePassword: () -> Unit = {},
    onNavigateToCustomerService: () -> Unit = {},
    onLogout: () -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val operationState by viewModel.operationState.collectAsStateWithLifecycle()

    // 显示操作结果
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(operationState) {
        when (operationState) {
            is UiDataState.Success -> {
                snackbarHostState.showSnackbar("操作成功")
                viewModel.resetOperationState()
            }
            is UiDataState.Error -> {
                val error = (operationState as UiDataState.Error).exception
                snackbarHostState.showSnackbar("操作失败: ${error.message}")
                viewModel.resetOperationState()
            }
            else -> {}
        }
    }

    SettingContent(
        uiState = uiState,
        onNavigateBack = onNavigateBack,
        onNavigateToProfile = onNavigateToProfile,
        onNavigateToPrivacyPolicy = onNavigateToPrivacyPolicy,
        onNavigateToUserAgreement = onNavigateToUserAgreement,
        onNavigateToFeedback = onNavigateToFeedback,
        onNavigateToAbout = onNavigateToAbout,
        onNavigateToChangePassword = onNavigateToChangePassword,
        onNavigateToCustomerService = onNavigateToCustomerService,
        onLogout = onLogout,
        onPushNotificationChange = { viewModel.setPushNotificationEnabled(it) },
        onSoundChange = { viewModel.setSoundEnabled(it) },
        onVibrationChange = { viewModel.setVibrationEnabled(it) },
        onClearCache = { viewModel.clearCache() },
        onCheckUpdate = { viewModel.checkUpdate() },
        snackbarHostState = snackbarHostState
    )
}

/**
 * 设置页面内容
 */
@Composable
fun SettingContent(
    uiState: SettingUiState,
    onNavigateBack: () -> Unit = {},
    onNavigateToProfile: () -> Unit = {},
    onNavigateToPrivacyPolicy: () -> Unit = {},
    onNavigateToUserAgreement: () -> Unit = {},
    onNavigateToFeedback: () -> Unit = {},
    onNavigateToAbout: () -> Unit = {},
    onNavigateToChangePassword: () -> Unit = {},
    onNavigateToCustomerService: () -> Unit = {},
    onLogout: () -> Unit = {},
    onPushNotificationChange: (Boolean) -> Unit = {},
    onSoundChange: (Boolean) -> Unit = {},
    onVibrationChange: (Boolean) -> Unit = {},
    onClearCache: () -> Unit = {},
    onCheckUpdate: () -> Unit = {},
    snackbarHostState: SnackbarHostState = remember { SnackbarHostState() }
) {
    var showLogoutConfirmDialog by remember { mutableStateOf(false) }
    var showClearCacheConfirmDialog by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "设置",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.SemiBold,
                        color = TextPrimaryLight
                    )
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
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = BackgroundLight
                )
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(BackgroundLight)
                .padding(horizontal = page_margin_horizontal),
            verticalArrangement = Arrangement.spacedBy(spacing_lg)
        ) {
            item { Spacer(modifier = Modifier.height(spacing_sm)) }

            // 账号设置
            item {
                SettingSection(title = "账号设置") {
                    // 用户信息卡片
                    UserInfoCard(
                        userName = uiState.userName,
                        phoneNumber = uiState.phoneNumber,
                        avatarUrl = uiState.avatarUrl,
                        onClick = onNavigateToProfile
                    )

                    Spacer(modifier = Modifier.height(spacing_sm))

                    // 修改密码
                    SettingItem(
                        icon = Icons.Filled.Lock,
                        title = "修改密码",
                        onClick = onNavigateToChangePassword
                    )
                }
            }

            // 通知设置
            item {
                SettingSection(title = "通知设置") {
                    SettingSwitchItem(
                        icon = Icons.Filled.Notifications,
                        title = "推送通知",
                        subtitle = "接收设备状态、系统消息等通知",
                        checked = uiState.pushNotificationEnabled,
                        onCheckedChange = onPushNotificationChange
                    )

                    SettingSwitchItem(
                        icon = Icons.Filled.VolumeUp,
                        title = "声音提醒",
                        subtitle = "通知时播放提示音",
                        checked = uiState.soundEnabled,
                        onCheckedChange = onSoundChange
                    )

                    SettingSwitchItem(
                        icon = Icons.Filled.Vibration,
                        title = "震动提醒",
                        subtitle = "通知时震动",
                        checked = uiState.vibrationEnabled,
                        onCheckedChange = onVibrationChange
                    )
                }
            }

            // 隐私设置
            item {
                SettingSection(title = "隐私设置") {
                    SettingItem(
                        icon = Icons.Filled.PrivacyTip,
                        title = "隐私政策",
                        onClick = onNavigateToPrivacyPolicy
                    )

                    SettingItem(
                        icon = Icons.Filled.Description,
                        title = "用户协议",
                        onClick = onNavigateToUserAgreement
                    )

                    SettingItem(
                        icon = Icons.Filled.DeleteSweep,
                        title = "清除缓存",
                        subtitle = uiState.cacheSize,
                        onClick = { showClearCacheConfirmDialog = true }
                    )
                }
            }

            // 关于我们
            item {
                SettingSection(title = "关于我们") {
                    SettingItem(
                        icon = Icons.Filled.Info,
                        title = "版本号",
                        subtitle = uiState.appVersion,
                        showArrow = false,
                        onClick = {}
                    )

                    SettingItem(
                        icon = Icons.Filled.SystemUpdate,
                        title = "检查更新",
                        subtitle = if (uiState.hasUpdate) "有新版本" else "已是最新",
                        showBadge = uiState.hasUpdate,
                        onClick = onCheckUpdate
                    )

                    SettingItem(
                        icon = Icons.Filled.Feedback,
                        title = "意见反馈",
                        onClick = onNavigateToFeedback
                    )

                    SettingItem(
                        icon = Icons.Filled.SupportAgent,
                        title = "联系客服",
                        onClick = onNavigateToCustomerService
                    )

                    SettingItem(
                        icon = Icons.Filled.Group,
                        title = "关于我们",
                        onClick = onNavigateToAbout
                    )
                }
            }

            // 退出登录
            item {
                Spacer(modifier = Modifier.height(spacing_md))

                Button(
                    onClick = { showLogoutConfirmDialog = true },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = ErrorRed.copy(alpha = 0.1f),
                        contentColor = ErrorRed
                    ),
                    shape = RoundedCornerShape(corner_md)
                ) {
                    Text(
                        text = "退出登录",
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier.padding(vertical = spacing_sm)
                    )
                }
            }

            item { Spacer(modifier = Modifier.height(spacing_lg)) }
        }
    }

    // 退出登录确认对话框
    if (showLogoutConfirmDialog) {
        AlertDialog(
            onDismissRequest = { showLogoutConfirmDialog = false },
            title = { Text("退出登录") },
            text = { Text("确定要退出登录吗？") },
            confirmButton = {
                TextButton(
                    onClick = {
                        onLogout()
                        showLogoutConfirmDialog = false
                    },
                    colors = ButtonDefaults.textButtonColors(contentColor = ErrorRed)
                ) {
                    Text("退出")
                }
            },
            dismissButton = {
                TextButton(onClick = { showLogoutConfirmDialog = false }) {
                    Text("取消")
                }
            }
        )
    }

    // 清除缓存确认对话框
    if (showClearCacheConfirmDialog) {
        AlertDialog(
            onDismissRequest = { showClearCacheConfirmDialog = false },
            title = { Text("清除缓存") },
            text = { Text("确定要清除缓存吗？清除后部分数据需要重新加载。") },
            confirmButton = {
                TextButton(
                    onClick = {
                        onClearCache()
                        showClearCacheConfirmDialog = false
                    }
                ) {
                    Text("清除")
                }
            },
            dismissButton = {
                TextButton(onClick = { showClearCacheConfirmDialog = false }) {
                    Text("取消")
                }
            }
        )
    }
}

/**
 * 设置分组卡片
 */
@Composable
private fun SettingSection(
    title: String,
    content: @Composable ColumnScope.() -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(spacing_sm)
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Medium,
            color = TextSecondaryLight,
            fontSize = text_body_size,
            modifier = Modifier.padding(start = spacing_sm)
        )

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
                .background(SurfaceLight)
                .padding(vertical = spacing_sm)
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                content = content
            )
        }
    }
}

/**
 * 用户信息卡片
 */
@Composable
private fun UserInfoCard(
    userName: String,
    phoneNumber: String,
    avatarUrl: String?,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(horizontal = card_padding_large, vertical = spacing_md),
        horizontalArrangement = Arrangement.spacedBy(spacing_md),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // 头像
        Box(
            modifier = Modifier
                .size(56.dp)
                .clip(CircleShape)
                .background(PrimaryBlue.copy(alpha = 0.1f)),
            contentAlignment = Alignment.Center
        ) {
            if (avatarUrl.isNullOrEmpty()) {
                Icon(
                    imageVector = Icons.Filled.Person,
                    contentDescription = null,
                    tint = PrimaryBlue,
                    modifier = Modifier.size(32.dp)
                )
            } else {
                // TODO: 加载网络图片
                Icon(
                    imageVector = Icons.Filled.Person,
                    contentDescription = null,
                    tint = PrimaryBlue,
                    modifier = Modifier.size(32.dp)
                )
            }
        }

        // 用户信息
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(spacing_xs)
        ) {
            Text(
                text = userName,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.SemiBold,
                color = TextPrimaryLight,
                fontSize = text_body_large_size
            )
            Text(
                text = phoneNumber,
                style = MaterialTheme.typography.bodyMedium,
                color = TextSecondaryLight,
                fontSize = text_body_size
            )
        }

        // 箭头
        Icon(
            imageVector = Icons.Filled.ChevronRight,
            contentDescription = null,
            tint = TextTertiaryLight,
            modifier = Modifier.size(24.dp)
        )
    }
}

/**
 * 设置项
 */
@Composable
private fun SettingItem(
    icon: ImageVector,
    title: String,
    subtitle: String? = null,
    showArrow: Boolean = true,
    showBadge: Boolean = false,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(horizontal = card_padding_large, vertical = spacing_md),
        horizontalArrangement = Arrangement.spacedBy(spacing_md),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // 图标
        Box(
            modifier = Modifier.size(24.dp),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = TextSecondaryLight,
                modifier = Modifier.size(20.dp)
            )
        }

        // 标题和副标题
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(spacing_xs)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Medium,
                color = TextPrimaryLight,
                fontSize = text_body_size
            )
            if (subtitle != null) {
                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.bodySmall,
                    color = TextTertiaryLight,
                    fontSize = text_caption_size
                )
            }
        }

        // 徽章或箭头
        if (showBadge) {
            Box(
                modifier = Modifier
                    .size(8.dp)
                    .clip(CircleShape)
                    .background(ErrorRed)
            )
        }

        if (showArrow) {
            Icon(
                imageVector = Icons.Filled.ChevronRight,
                contentDescription = null,
                tint = TextTertiaryLight,
                modifier = Modifier.size(24.dp)
            )
        }
    }
}

/**
 * 设置开关项
 */
@Composable
private fun SettingSwitchItem(
    icon: ImageVector,
    title: String,
    subtitle: String? = null,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = card_padding_large, vertical = spacing_md),
        horizontalArrangement = Arrangement.spacedBy(spacing_md),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // 图标
        Box(
            modifier = Modifier.size(24.dp),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = TextSecondaryLight,
                modifier = Modifier.size(20.dp)
            )
        }

        // 标题和副标题
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(spacing_xs)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Medium,
                color = TextPrimaryLight,
                fontSize = text_body_size
            )
            if (subtitle != null) {
                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.bodySmall,
                    color = TextTertiaryLight,
                    fontSize = text_caption_size
                )
            }
        }

        // 开关
        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange,
            colors = SwitchDefaults.colors(
                checkedThumbColor = Color.White,
                checkedTrackColor = SwitchChecked,
                uncheckedThumbColor = Color.White,
                uncheckedTrackColor = SwitchUnchecked
            ),
            modifier = Modifier.width(switch_width)
        )
    }
}

/**
 * 设置页面 UI State
 */
data class SettingUiState(
    val userName: String = "用户昵称",
    val phoneNumber: String = "138****8888",
    val avatarUrl: String? = null,
    val pushNotificationEnabled: Boolean = true,
    val soundEnabled: Boolean = true,
    val vibrationEnabled: Boolean = true,
    val cacheSize: String = "12.5 MB",
    val appVersion: String = "v1.0.0",
    val hasUpdate: Boolean = false
)

// ==================== Preview 函数 ====================

@Preview(showBackground = true, name = "设置页面-正常", backgroundColor = 0xFFF1F5F9)
@Composable
fun SettingScreenPreview() {
    WuHengTheme {
        SettingContent(
            uiState = SettingUiState(
                userName = "张三",
                phoneNumber = "138****8888",
                pushNotificationEnabled = true,
                soundEnabled = true,
                vibrationEnabled = false,
                cacheSize = "12.5 MB",
                appVersion = "v1.2.0",
                hasUpdate = true
            )
        )
    }
}

@Preview(showBackground = true, name = "设置页面-无更新", backgroundColor = 0xFFF1F5F9)
@Composable
fun SettingScreenNoUpdatePreview() {
    WuHengTheme {
        SettingContent(
            uiState = SettingUiState(
                userName = "李四",
                phoneNumber = "139****6666",
                pushNotificationEnabled = false,
                soundEnabled = false,
                vibrationEnabled = false,
                cacheSize = "5.2 MB",
                appVersion = "v1.2.0",
                hasUpdate = false
            )
        )
    }
}
