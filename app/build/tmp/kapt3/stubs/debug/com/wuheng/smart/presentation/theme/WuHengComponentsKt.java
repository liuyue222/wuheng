package com.wuheng.smart.presentation.theme;

import androidx.compose.material3.*;
import androidx.compose.runtime.Composable;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.text.font.FontWeight;
import com.wuheng.smart.R;

@kotlin.Metadata(mv = {1, 7, 1}, k = 2, d1 = {"\u0000\u0014\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a$\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0012\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00010\u0005H\u0007\u00a8\u0006\u0006"}, d2 = {"WuHengBottomNavigation", "", "selectedItem", "", "onItemSelected", "Lkotlin/Function1;", "app_debug"})
public final class WuHengComponentsKt {
    
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
    @androidx.compose.runtime.Composable()
    public static final void WuHengBottomNavigation(int selectedItem, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super java.lang.Integer, kotlin.Unit> onItemSelected) {
    }
}