package com.wuheng.smart.presentation.profile

import androidx.compose.foundation.background
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
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
 * 用户头部信息
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
            // 头像
            Box(
                modifier = Modifier
                    .size(avatar_size_default)
                    .clip(CircleShape)
                    .background(SurfaceVariantLight),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = userName.take(1).ifEmpty { "用" },
                    style = MaterialTheme.typography.titleLarge,
                    color = PrimaryBlue
                )
            }

            Spacer(modifier = Modifier.width(spacing_md))

            // 用户信息
            Column {
                Text(
                    text = userName,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = TextPrimaryLight
                )
                Spacer(modifier = Modifier.height(spacing_xs))
                Text(
                    text = "$residenceName · $role",
                    style = MaterialTheme.typography.bodySmall,
                    color = TextSecondaryLight
                )
            }
        }

        // 通知图标
        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .background(SurfaceLight)
                .clickable(onClick = onNotificationClick),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.Notifications,
                contentDescription = "通知",
                modifier = Modifier.size(icon_size_md),
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
 * 项目概述卡片
 */
@Composable
private fun ProjectOverviewCard(description: String) {
    WuHengCard(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(card_padding_default)
        ) {
            Text(
                text = "项目概述",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(modifier = Modifier.height(spacing_sm))
            Text(
                text = description,
                style = MaterialTheme.typography.bodyMedium,
                color = TextSecondaryLight
            )
        }
    }
}

/**
 * 预约服务卡片
 */
@Composable
private fun ServiceBookingCard(
    lastServiceDate: String,
    onServiceSelect: () -> Unit,
    onBookService: () -> Unit
) {
    WuHengCard(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(card_padding_default)
        ) {
            // 标题行
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "预约服务",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold
                )
                if (lastServiceDate.isNotEmpty()) {
                    Text(
                        text = "上次预约:$lastServiceDate",
                        style = MaterialTheme.typography.bodySmall,
                        color = TextTertiaryLight
                    )
                }
            }

            Spacer(modifier = Modifier.height(spacing_md))

            // 服务选择器
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(corner_sm))
                    .background(InputBackground)
                    .clickable(onClick = onServiceSelect)
                    .padding(horizontal = spacing_default, vertical = spacing_md)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "选择服务",
                        style = MaterialTheme.typography.bodyMedium,
                        color = TextTertiaryLight
                    )
                    Icon(
                        imageVector = Icons.Filled.KeyboardArrowRight,
                        contentDescription = null,
                        modifier = Modifier.size(icon_size_sm),
                        tint = TextTertiaryLight
                    )
                }
            }

            Spacer(modifier = Modifier.height(spacing_md))

            // 预约按钮
            PrimaryButton(
                text = "+预约服务",
                onClick = onBookService
            )
        }
    }
}

/**
 * 退出登录按钮
 */
@Composable
private fun LogoutButton(
    onLogout: () -> Unit
) {
    WuHengCard(
        modifier = Modifier.fillMaxWidth()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clickable(onClick = onLogout)
                .padding(card_padding_default),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "退出登录",
                style = MaterialTheme.typography.bodyMedium,
                color = ErrorRed,
                fontWeight = FontWeight.Medium
            )
        }
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
            style = VersionTextStyle
        )
        Spacer(modifier = Modifier.height(spacing_xs))
        Text(
            text = "《隐私服务条款》",
            style = FooterTextStyle,
            modifier = Modifier.clickable(onClick = onPrivacyClick)
        )
    }
}
