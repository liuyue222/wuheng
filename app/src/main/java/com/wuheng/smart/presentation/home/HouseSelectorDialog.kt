package com.wuheng.smart.presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.wuheng.smart.data.model.MyHouse
import com.wuheng.smart.presentation.theme.*

/**
 * 房产选择对话框
 */
@Composable
fun HouseSelectorDialog(
    houses: List<MyHouse>,
    currentHouseId: String,
    onHouseSelected: (MyHouse) -> Unit,
    onDismiss: () -> Unit
) {
    Dialog(onDismissRequest = onDismiss) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = SurfaceLight)
        ) {
            Column(
                modifier = Modifier.padding(page_margin_horizontal, spacing_lg)
            ) {
                // 标题
                Text(
                    text = "选择住宅",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = TextPrimaryLight
                )

                Spacer(modifier = Modifier.height(spacing_sm))

                Text(
                    text = "请选择要控制的住宅",
                    style = MaterialTheme.typography.bodyMedium,
                    color = TextSecondaryLight
                )

                Spacer(modifier = Modifier.height(spacing_lg))

                // 房产列表
                houses.forEach { house ->
                    HouseItem(
                        house = house,
                        isSelected = house.houseId.toString() == currentHouseId,
                        onClick = { onHouseSelected(house) }
                    )
                    Spacer(modifier = Modifier.height(spacing_sm))
                }

                Spacer(modifier = Modifier.height(spacing_lg))

                // 取消按钮
                OutlinedButton(
                    onClick = onDismiss,
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text("取消")
                }
            }
        }
    }
}

@Composable
private fun HouseItem(
    house: MyHouse,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) PrimaryBlue.copy(alpha = 0.1f) else Color.White
        ),
        border = if (isSelected) {
            androidx.compose.foundation.BorderStroke(2.dp, PrimaryBlue)
        } else null
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(spacing_md),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // 房子图标
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(if (isSelected) PrimaryBlue else PrimaryBlue.copy(alpha = 0.1f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Home,
                    contentDescription = null,
                    modifier = Modifier.size(24.dp),
                    tint = if (isSelected) Color.White else PrimaryBlue
                )
            }

            Spacer(modifier = Modifier.width(spacing_md))

            // 房产信息
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = house.houseName,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Medium,
                    color = TextPrimaryLight
                )
                Spacer(modifier = Modifier.height(spacing_xs))
                Text(
                    text = house.address,
                    style = MaterialTheme.typography.bodySmall,
                    color = TextTertiaryLight
                )
            }

            // 选中标记
            if (isSelected) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = null,
                    modifier = Modifier.size(24.dp),
                    tint = PrimaryBlue
                )
            }
        }
    }
}
