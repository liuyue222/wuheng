package com.wuheng.smart.presentation.home.components

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.wuheng.smart.R
import com.wuheng.smart.data.model.ServiceType
import com.wuheng.smart.presentation.theme.*

/**
 * 服务入口网格组件
 *
 * 布局结构分析（基于设计图）:
 * - 外层: 垂直布局，包含标题 + 2x2网格
 * - 网格项: 圆角矩形卡片 (14dp圆角)
 *   - 上半部分: 图标居中显示（带圆形背景）
 *   - 下半部分: 服务名称文字
 * - 交互: 点击反馈（缩放+阴影变化）
 *
 * 切图资源引用:
 *   - 上门服务-面.png -> ic_service (上门服务)
 *   - 沙发，空位.png -> ic_couch (家居/空间服务)
 *   - _叶子.png -> ic_leaf (环保/绿植服务)
 *   - liebiao.png -> 列表/更多服务
 */

data class ServiceItem(
    val type: ServiceType,
    val name: String,
    val iconRes: Int,
    val description: String = ""
)

data class ServiceGridUiState(
    val services: List<ServiceItem> = defaultServices(),
    val title: String = "智能服务"
)

private fun defaultServices(): List<ServiceItem> = listOf(
    ServiceItem(
        type = ServiceType.HOME_SERVICE,
        name = "上门服务",
        iconRes = R.drawable.ic_scene_meeting,
        description = "专业技师上门"
    ),
    ServiceItem(
        type = ServiceType.SPACE_SERVICE,
        name = "空间管理",
        iconRes = R.drawable.ic_scene_meeting,
        description = "智能家居配置"
    ),
    ServiceItem(
        type = ServiceType.ECO_SERVICE,
        name = "绿植养护",
        iconRes = R.drawable.ic_scene_eco,
        description = "智能园艺系统"
    ),
    ServiceItem(
        type = ServiceType.MORE_SERVICE,
        name = "更多",
        iconRes = R.drawable.ic_scene_meeting,
        description = "查看全部服务"
    )
)

@Composable
fun ServiceGrid(
    uiState: ServiceGridUiState = ServiceGridUiState(),
    onServiceClick: (ServiceType) -> Unit = {},
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // 标题行
        Text(
            text = uiState.title,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = TextPrimaryLight,
            fontSize = 17.sp
        )

        // 2x2 网格
        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            uiState.services.chunked(2).forEach { rowItems ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    rowItems.forEach { service ->
                        ServiceGridItem(
                            item = service,
                            onClick = { onServiceClick(service.type) },
                            modifier = Modifier.weight(1f)
                        )
                    }
                    // 如果该行不足2个，填充空白占位
                    if (rowItems.size == 1) {
                        Spacer(modifier = Modifier.weight(1f))
                    }
                }
            }
        }
    }
}

@Composable
private fun ServiceGridItem(
    item: ServiceItem,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    var isPressed by remember { mutableStateOf(false) }

    Box(
        modifier = modifier
            .shadow(
                elevation = if (isPressed) 1.dp else 3.dp,
                shape = RoundedCornerShape(14.dp),
                ambientColor = ShadowLight.copy(alpha = 0.35f),
                spotColor = ShadowLight.copy(alpha = 0.35f)
            )
            .clip(RoundedCornerShape(14.dp))
            .background(GlassPanelBg)
            .clickable {
                isPressed = true
                onClick()
            }
            .padding(vertical = 18.dp, horizontal = 12.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            // 图标容器
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(RoundedCornerShape(14.dp))
                    .background(SceneIconBg),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = item.iconRes),
                    contentDescription = item.name,
                    modifier = Modifier.size(26.dp),
                    colorFilter = ColorFilter.tint(PrimaryBlue)
                )
            }

            // 服务名称
            Text(
                text = item.name,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Medium,
                color = TextPrimaryLight,
                fontSize = 13.sp,
                textAlign = TextAlign.Center
            )

            // 描述文本（可选）
            if (item.description.isNotEmpty()) {
                Text(
                    text = item.description,
                    style = MaterialTheme.typography.bodySmall,
                    color = TextTertiaryLight,
                    fontSize = 11.sp,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

/**
 * 横向滚动服务列表（用于更多场景）
 */
@Composable
fun ServiceHorizontalList(
    services: List<ServiceItem> = defaultServices().take(4),
    onServiceClick: (ServiceType) -> Unit = {},
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .horizontalScroll(rememberScrollState()),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        services.forEach { service ->
            ServiceGridItem(
                item = service,
                onClick = { onServiceClick(service.type) },
                modifier = Modifier.width(140.dp)
            )
        }
    }
}

/**
 * 单个服务入口卡片（大尺寸版本，用于首页推荐）
 */
@Composable
fun ServiceFeaturedCard(
    item: ServiceItem,
    onClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .shadow(
                elevation = 4.dp,
                shape = RoundedCornerShape(16.dp),
                ambientColor = PrimaryBlue.copy(alpha = 0.15f),
                spotColor = PrimaryBlue.copy(alpha = 0.15f)
            )
            .clip(RoundedCornerShape(16.dp))
            .background(GlassPanelBg)
            .clickable { onClick() }
            .padding(20.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(52.dp)
                        .clip(RoundedCornerShape(14.dp))
                        .background(PrimaryBlue.copy(alpha = 0.1f)),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = item.iconRes),
                        contentDescription = item.name,
                        modifier = Modifier.size(28.dp),
                        colorFilter = ColorFilter.tint(PrimaryBlue)
                    )
                }
                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    Text(
                        text = item.name,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = TextPrimaryLight,
                        fontSize = 16.sp
                    )
                    Text(
                        text = item.description,
                        style = MaterialTheme.typography.bodySmall,
                        color = TextSecondaryLight,
                        fontSize = 13.sp
                    )
                }
            }

            // 箭头图标
            Icon(
                IconsWuHeng.ChevronRight,
                contentDescription = "查看详情",
                tint = TextTertiaryLight,
                modifier = Modifier.size(20.dp)
            )
        }
    }
}

@Preview(showBackground = true, name = "服务网格", backgroundColor = 0xFFF1F5F9)
@Composable
fun ServiceGridPreview() {
    WuHengTheme {
        ServiceGrid()
    }
}

@Preview(showBackground = true, name = "特色服务卡片", backgroundColor = 0xFFF1F5F9)
@Composable
fun ServiceFeaturedCardPreview() {
    WuHengTheme {
        ServiceFeaturedCard(
            item = defaultServices()[0]
        )
    }
}
