package com.wuheng.smart.presentation.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.wuheng.smart.presentation.components.*
import com.wuheng.smart.presentation.theme.*

/**
 * 个人中心页面布局 - 纯UI组件
 *
 * 布局结构分析：
 * 1. 用户信息区域：头像、姓名、地址、角色、通知图标
 * 2. 项目概述卡片
 * 3. 预约服务卡片
 * 4. 耗材使用进度入口
 * 5. 关于新宜能入口
 * 6. 退出登录按钮
 * 7. 版本信息和隐私条款
 */

// ==================== 主布局 ====================

@Composable
fun ProfileLayout(
    uiState: ProfileUiState,
    selectedServiceType: ServiceType? = null,
    onNotificationClick: () -> Unit,
    onServiceSelect: () -> Unit,
    onBookService: () -> Unit,
    onConsumablesClick: () -> Unit,
    onAboutClick: () -> Unit,
    onPrivacyClick: () -> Unit,
    onLogout: () -> Unit,
    modifier: Modifier = Modifier,
    maxWidth: Dp = 360.dp
) {
    val isWide = maxWidth >= 720.dp
    val horizontalPadding = if (isWide) page_margin_horizontal_wide else page_margin_horizontal

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(BackgroundLight),
        contentPadding = PaddingValues(
            start = horizontalPadding,
            end = horizontalPadding,
            top = page_top_safe_area,
            bottom = page_bottom_safe_area
        )
    ) {
        // 用户信息区域
        item {
            UserHeader(
                userName = uiState.userName,
                residenceName = uiState.residenceName,
                role = uiState.role,
                hasNotification = uiState.hasNotification,
                onNotificationClick = onNotificationClick
            )
        }

        // 项目概述卡片
        item {
            Spacer(modifier = Modifier.height(spacing_lg))
            ProjectOverviewCard(description = uiState.projectDescription)
        }

        // 预约服务卡片
            item {
                Spacer(modifier = Modifier.height(spacing_lg))
                ServiceBookingCard(
                    lastServiceDate = uiState.lastServiceDate,
                    selectedServiceType = selectedServiceType,
                    onServiceSelect = onServiceSelect,
                    onBookService = onBookService
                )
            }

        // 耗材使用进度入口
        item {
            Spacer(modifier = Modifier.height(spacing_lg))
            ClickableListItem(
                title = "耗材使用进度",
                onClick = onConsumablesClick
            )
        }

        // 关于新宜能入口
        item {
            Spacer(modifier = Modifier.height(spacing_md))
            ClickableListItem(
                title = "关于新宜能",
                onClick = onAboutClick
            )
        }

        // 退出登录按钮
        item {
            Spacer(modifier = Modifier.height(spacing_lg))
            LogoutButton(onLogout = onLogout)
        }

        // 版本信息和隐私条款
        item {
            Spacer(modifier = Modifier.height(spacing_2xl))
            FooterSection(
                version = uiState.version,
                onPrivacyClick = onPrivacyClick
            )
        }

        // 底部间距
        item {
            Spacer(modifier = Modifier.height(spacing_xl))
        }
    }
}

// ==================== 子组件 ====================

/**
 * 用户头部信息 - 像素级还原设计图
 */
@Composable
private fun UserHeader(
    userName: String,
    residenceName: String,
    role: String,
    hasNotification: Boolean,
    onNotificationClick: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            // 头像 - 像素级还原
            Box(
                modifier = Modifier
                    .size(56.dp)
                    .clip(CircleShape)
                    .background(SurfaceVariantLight),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = userName.take(1).ifEmpty { "用" },
                    style = MaterialTheme.typography.titleLarge,
                    color = PrimaryBlue,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Medium
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            // 用户信息
            Column {
                Text(
                    text = userName,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = TextPrimaryLight,
                    fontSize = 18.sp
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "$residenceName · $role",
                    style = MaterialTheme.typography.bodySmall,
                    color = TextSecondaryLight,
                    fontSize = 13.sp
                )
            }
        }

        // 通知图标
        Box(
            modifier = Modifier
                .size(44.dp)
                .clip(CircleShape)
                .background(Color.White)
                .clickable(onClick = onNotificationClick),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.Notifications,
                contentDescription = "通知",
                modifier = Modifier.size(24.dp),
                tint = if (hasNotification) SecondaryOrange else TextSecondaryLight
            )
            // 红点指示器
            if (hasNotification) {
                Box(
                    modifier = Modifier
                        .size(8.dp)
                        .clip(CircleShape)
                        .background(ErrorRed)
                        .align(Alignment.TopEnd)
                )
            }
        }
    }
}

/**
 * 项目概述卡片 - 像素级还原设计图
 */
@Composable
private fun ProjectOverviewCard(description: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(
                elevation = 2.dp,
                shape = RoundedCornerShape(16.dp),
                spotColor = Color.Black.copy(alpha = 0.06f)
            )
            .clip(RoundedCornerShape(16.dp))
            .background(Color.White)
            .padding(16.dp)
    ) {
        Column {
            Text(
                text = "项目概述",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
                color = TextPrimaryLight,
                fontSize = 16.sp
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = description,
                style = MaterialTheme.typography.bodyMedium,
                color = TextSecondaryLight,
                fontSize = 14.sp,
                lineHeight = 22.sp
            )
        }
    }
}

/**
 * 预约服务卡片 - 像素级还原设计图
 */
@Composable
private fun ServiceBookingCard(
    lastServiceDate: String,
    selectedServiceType: ServiceType?,
    onServiceSelect: () -> Unit,
    onBookService: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(
                elevation = 2.dp,
                shape = RoundedCornerShape(16.dp),
                spotColor = Color.Black.copy(alpha = 0.06f)
            )
            .clip(RoundedCornerShape(16.dp))
            .background(Color.White)
            .padding(16.dp)
    ) {
        Column {
            // 标题行
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "预约服务",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = TextPrimaryLight,
                    fontSize = 16.sp
                )
                if (lastServiceDate.isNotEmpty()) {
                    Text(
                        text = "上次预约:$lastServiceDate",
                        style = MaterialTheme.typography.bodySmall,
                        color = TextTertiaryLight,
                        fontSize = 12.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // 服务类型选择器
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color(0xFFF5F7FA))
                    .clickable(onClick = onServiceSelect)
                    .padding(horizontal = 16.dp, vertical = 14.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = selectedServiceType?.displayName ?: "选择服务",
                        style = MaterialTheme.typography.bodyMedium,
                        color = if (selectedServiceType != null) TextPrimaryLight else TextTertiaryLight,
                        fontSize = 14.sp
                    )
                    Icon(
                        imageVector = Icons.Filled.KeyboardArrowRight,
                        contentDescription = null,
                        modifier = Modifier.size(20.dp),
                        tint = TextTertiaryLight
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // 预约按钮
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(44.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(PrimaryBlue)
                    .clickable(onClick = onBookService),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "+预约服务",
                    color = Color.White,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}

/**
 * 可点击列表项 - 像素级还原设计图
 */
@Composable
private fun ClickableListItem(
    title: String,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(
                elevation = 2.dp,
                shape = RoundedCornerShape(12.dp),
                spotColor = Color.Black.copy(alpha = 0.06f)
            )
            .clip(RoundedCornerShape(12.dp))
            .background(Color.White)
            .clickable(onClick = onClick)
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.bodyMedium,
                color = TextPrimaryLight,
                fontSize = 15.sp,
                fontWeight = FontWeight.Medium
            )
            Icon(
                imageVector = Icons.Filled.KeyboardArrowRight,
                contentDescription = null,
                modifier = Modifier.size(20.dp),
                tint = TextTertiaryLight
            )
        }
    }
}

/**
 * 服务类型
 */
enum class ServiceType(val displayName: String, val description: String) {
    REGULAR_MAINTENANCE("常规保养", "系统检查、清洁、调试"),
    FILTER_REPLACEMENT("滤芯更换", "全屋净水滤芯更换服务"),
    SYSTEM_INSPECTION("系统检修", "故障排查、部件更换"),
    SEASONAL_SWITCH("换季切换", "制冷/制热模式切换服务");

    fun toApiValue(): String = when (this) {
        REGULAR_MAINTENANCE -> "maintenance"
        FILTER_REPLACEMENT -> "filter_replace"
        SYSTEM_INSPECTION -> "repair"
        SEASONAL_SWITCH -> "inspection"
    }
}

/**
 * 服务类型选择弹窗
 */
@Composable
fun ServiceTypeSelectorDialog(
    selectedType: ServiceType?,
    onTypeSelected: (ServiceType) -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = "选择保养类型",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
        },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(spacing_sm)) {
                ServiceType.values().forEach { type ->
                    val isSelected = selectedType == type
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(corner_sm))
                            .background(
                                if (isSelected) PrimaryBlue.copy(alpha = 0.1f) else SurfaceLight
                            )
                            .border(
                                width = 1.dp,
                                color = if (isSelected) PrimaryBlue else DividerLight,
                                shape = RoundedCornerShape(corner_sm)
                            )
                            .clickable { onTypeSelected(type) }
                            .padding(spacing_default)
                    ) {
                        Column {
                            Text(
                                text = type.displayName,
                                style = MaterialTheme.typography.bodyLarge,
                                fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal,
                                color = if (isSelected) PrimaryBlue else TextPrimaryLight
                            )
                            Spacer(modifier = Modifier.height(spacing_xs))
                            Text(
                                text = type.description,
                                style = MaterialTheme.typography.bodySmall,
                                color = TextSecondaryLight
                            )
                        }
                    }
                }
            }
        },
        confirmButton = {},
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("取消")
            }
        }
    )
}

/**
 * 预约确认弹窗
 */
@Composable
fun ServiceBookingConfirmDialog(
    serviceType: ServiceType,
    bookingState: com.wuheng.smart.presentation.base.UiDataState<Unit>,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = "确认预约",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
        },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(spacing_sm)) {
                Text("您即将预约以下保养服务：")
                Text(
                    text = serviceType.displayName,
                    fontWeight = FontWeight.SemiBold,
                    color = PrimaryBlue
                )
                Text(
                    text = serviceType.description,
                    style = MaterialTheme.typography.bodySmall,
                    color = TextSecondaryLight
                )
                Spacer(modifier = Modifier.height(spacing_sm))
                Text(
                    text = "我们的服务人员将在24小时内与您联系确认上门时间。",
                    style = MaterialTheme.typography.bodySmall,
                    color = TextSecondaryLight
                )

                // 加载状态
                if (bookingState is com.wuheng.smart.presentation.base.UiDataState.Loading) {
                    Spacer(modifier = Modifier.height(spacing_md))
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        color = PrimaryBlue
                    )
                }

                // 错误提示
                if (bookingState is com.wuheng.smart.presentation.base.UiDataState.Error) {
                    Spacer(modifier = Modifier.height(spacing_md))
                    Text(
                        text = "预约失败，请稍后重试",
                        color = ErrorRed,
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }
        },
        confirmButton = {
            Button(
                onClick = onConfirm,
                enabled = bookingState !is com.wuheng.smart.presentation.base.UiDataState.Loading,
                colors = ButtonDefaults.buttonColors(containerColor = PrimaryBlue)
            ) {
                Text("确认预约")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("取消")
            }
        }
    )
}

/**
 * 退出登录按钮
 */
@Composable
private fun LogoutButton(
    onLogout: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(
                elevation = 2.dp,
                shape = RoundedCornerShape(12.dp),
                spotColor = Color.Black.copy(alpha = 0.06f)
            )
            .clip(RoundedCornerShape(12.dp))
            .background(Color.White)
            .clickable(onClick = onLogout)
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "退出登录",
            style = MaterialTheme.typography.bodyMedium,
            color = ErrorRed,
            fontWeight = FontWeight.Medium,
            fontSize = 15.sp
        )
    }
}

/**
 * 页脚区域
 */
@Composable
private fun FooterSection(
    version: String,
    onPrivacyClick: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = version,
            style = MaterialTheme.typography.bodySmall,
            color = TextTertiaryLight,
            fontSize = 12.sp
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = "《隐私服务条款》",
            style = MaterialTheme.typography.bodySmall,
            color = TextTertiaryLight,
            fontSize = 12.sp,
            modifier = Modifier.clickable(onClick = onPrivacyClick)
        )
    }
}
