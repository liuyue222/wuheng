package com.wuheng.smart.presentation.theme

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.size
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.wuheng.smart.R

/**
 * 底部导航栏组件 - 使用切图资源 (第二版优化版)
 *
 * 设计规范要点:
 * ✅ 选中和未选中状态背景颜色都是**完全透明**的
 * ✅ 仅改变图片图标和文字颜色（不改变背景色）
 * ✅ 使用NavSelectedColor (#0EA5E9) 和 NavUnselectedColor (#94A3B8)
 * ✅ 导航指示器完全透明 (NavIndicatorColor)
 *
 * 切图资源对应表:
 * - 首页选中: R.drawable.home2
 * - 首页未选中: R.drawable.home2_unchecked
 * - 冷暖选中: R.drawable.liebiao
 * - 冷暖未选中: R.drawable.liebiao_unchecked
 * - 水系统选中: R.drawable.ic_water_selected
 * - 水系统未选中: R.drawable.ic_water
 * - 我的选中: R.drawable.ic_profile_selected
 * - 我的未选中: R.drawable.ic_profile
 */
@Composable
fun WuHengBottomNavigation(selectedItem: Int, onItemSelected: (Int) -> Unit) {
    // 定义导航项数据 (label, selectedIcon, unselectedIcon) - 使用 remember 避免重复创建
    val items = remember {
        listOf(
            Triple("首页", R.drawable.home2, R.drawable.home2_unchecked),
            Triple("冷暖", R.drawable.liebiao, R.drawable.liebiao_unchecked),
            Triple("水系统", R.drawable.ic_water_selected, R.drawable.ic_water),
            Triple("我的", R.drawable.ic_profile_selected, R.drawable.ic_profile)
        )
    }

    Surface(
        tonalElevation = 0.dp,
        shadowElevation = 8.dp,
        color = GlassNavBg
    ) {
        NavigationBar(
            containerColor = Color.Transparent,
            tonalElevation = 0.dp,
            windowInsets = WindowInsets(0.dp)
        ) {
            items.forEachIndexed { index, (label, selectedIcon, unselectedIcon) ->
                val isSelected = selectedItem == index
                NavigationBarItem(
                    icon = {
                        Image(
                            painter = painterResource(
                                id = if (isSelected) selectedIcon else unselectedIcon
                            ),
                            contentDescription = label,
                            modifier = Modifier.size(24.dp)
                        )
                    },
                    label = {
                        Text(
                            text = label,
                            fontSize = 11.sp,
                            fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal,
                            letterSpacing = if (isSelected) 0.3.sp else 0.sp,
                            // 使用设计令牌：选中态蓝色，未选中态灰色
                            color = if (isSelected) NavSelectedColor else NavUnselectedColor
                        )
                    },
                    selected = isSelected,
                    onClick = { onItemSelected(index) },
                    colors = NavigationBarItemDefaults.colors(
                        // 核心优化：所有背景色都使用完全透明
                        selectedIconColor = NavSelectedColor,
                        selectedTextColor = NavSelectedColor,
                        unselectedIconColor = NavUnselectedColor,
                        unselectedTextColor = NavUnselectedColor,
                        indicatorColor = Color.Transparent  // 强制完全透明，无背景指示器
                    ),
                    // 禁用默认选中指示器动画效果
                    alwaysShowLabel = true
                )
            }
        }
    }
}
