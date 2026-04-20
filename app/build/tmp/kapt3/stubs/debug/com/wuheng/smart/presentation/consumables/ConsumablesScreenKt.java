package com.wuheng.smart.presentation.consumables;

import androidx.compose.foundation.layout.*;
import androidx.compose.material.icons.Icons;
import androidx.compose.material3.*;
import androidx.compose.runtime.*;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.text.font.FontWeight;
import androidx.compose.ui.tooling.preview.Preview;
import com.wuheng.smart.presentation.base.UiDataState;
import com.wuheng.smart.presentation.theme.*;

@kotlin.Metadata(mv = {1, 7, 1}, k = 2, d1 = {"\u0000*\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\u001a \u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u000e\b\u0002\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00010\u0005H\u0003\u001a<\u0010\u0006\u001a\u00020\u00012\u0012\u0010\u0007\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00030\t0\b2\u000e\b\u0002\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00010\u00052\u000e\b\u0002\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u00010\u0005H\u0007\u001a\"\u0010\f\u001a\u00020\u00012\b\b\u0002\u0010\r\u001a\u00020\u000e2\u000e\b\u0002\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00010\u0005H\u0007\u001a\b\u0010\u000f\u001a\u00020\u0001H\u0007\u001a\b\u0010\u0010\u001a\u00020\u0001H\u0007\u001a\b\u0010\u0011\u001a\u00020\u0001H\u0007\u00a8\u0006\u0012"}, d2 = {"ConsumableItemCard", "", "item", "Lcom/wuheng/smart/presentation/consumables/ConsumableItem;", "onClick", "Lkotlin/Function0;", "ConsumablesContent", "consumablesState", "Lcom/wuheng/smart/presentation/base/UiDataState;", "", "onNavigateBack", "onRefresh", "ConsumablesScreen", "viewModel", "Lcom/wuheng/smart/presentation/consumables/ConsumablesViewModel;", "ConsumablesScreenDarkPreview", "ConsumablesScreenLoadingPreview", "ConsumablesScreenPreview", "app_debug"})
public final class ConsumablesScreenKt {
    
    /**
     * 耗材进度页面 - 像素级还原设计图（耗材进度.png）
     *
     * 布局结构：
     * 1. 顶部导航栏：返回按钮 + "耗材使用进度"标题
     * 2. 耗材列表：白色卡片列表，每项显示滤芯名称、百分比、状态
     *
     * 设计规范：
     * - 页面背景：BackgroundLight (#F1F5F9)
     * - 列表项背景：SurfaceLight (白色)
     * - 正常状态：SuccessGreen (#22C55E 绿色)
     * - 需更换状态：ErrorRed (#EF4444 红色)
     * - 卡片圆角：corner_md (16.dp)
     * - 列表项间距：card_spacing_vertical (12.dp)
     */
    @androidx.compose.runtime.Composable()
    public static final void ConsumablesScreen(@org.jetbrains.annotations.NotNull()
    com.wuheng.smart.presentation.consumables.ConsumablesViewModel viewModel, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onNavigateBack) {
    }
    
    /**
     * 耗材进度页面内容
     */
    @androidx.compose.runtime.Composable()
    @kotlin.OptIn(markerClass = {androidx.compose.material3.ExperimentalMaterial3Api.class})
    public static final void ConsumablesContent(@org.jetbrains.annotations.NotNull()
    com.wuheng.smart.presentation.base.UiDataState<? extends java.util.List<com.wuheng.smart.presentation.consumables.ConsumableItem>> consumablesState, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onNavigateBack, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onRefresh) {
    }
    
    /**
     * 耗材列表项卡片 - 像素级还原设计图
     *
     * 布局结构：
     * ┌─────────────────────────────────────────┐
     * │ [滤芯名称]              [百分比] [状态] │
     * └─────────────────────────────────────────┘
     *
     * 设计规范：
     * - 卡片背景：SurfaceLight (白色)
     * - 圆角：corner_md (16.dp)
     * - 内边距：card_padding_large (20.dp)
     * - 名称字号：filter_item_name_size (15.sp)
     * - 百分比字号：filter_percentage_size (15.sp)
     * - 状态标签字号：filter_status_size (13.sp)
     * - 正常状态颜色：SuccessGreen (#22C55E)
     * - 需更换状态颜色：ErrorRed (#EF4444)
     */
    @androidx.compose.runtime.Composable()
    private static final void ConsumableItemCard(com.wuheng.smart.presentation.consumables.ConsumableItem item, kotlin.jvm.functions.Function0<kotlin.Unit> onClick) {
    }
    
    @androidx.compose.runtime.Composable()
    @androidx.compose.ui.tooling.preview.Preview(showBackground = true, name = "\u8017\u6750\u8fdb\u5ea6-\u4eae\u8272\u4e3b\u9898", backgroundColor = 4294047225L)
    public static final void ConsumablesScreenPreview() {
    }
    
    @androidx.compose.runtime.Composable()
    @androidx.compose.ui.tooling.preview.Preview(showBackground = true, name = "\u8017\u6750\u8fdb\u5ea6-\u52a0\u8f7d\u72b6\u6001", backgroundColor = 4294047225L)
    public static final void ConsumablesScreenLoadingPreview() {
    }
    
    @androidx.compose.runtime.Composable()
    @androidx.compose.ui.tooling.preview.Preview(showBackground = true, name = "\u8017\u6750\u8fdb\u5ea6-\u6697\u8272\u4e3b\u9898", backgroundColor = 4279179050L)
    public static final void ConsumablesScreenDarkPreview() {
    }
}