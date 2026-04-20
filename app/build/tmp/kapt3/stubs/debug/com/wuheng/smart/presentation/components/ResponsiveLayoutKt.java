package com.wuheng.smart.presentation.components;

import androidx.compose.foundation.layout.Arrangement;
import androidx.compose.runtime.Composable;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.unit.Dp;

@kotlin.Metadata(mv = {1, 7, 1}, k = 2, d1 = {"\u0000T\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010 \n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\b\n\u0002\b\b\u001a<\u0010\u0000\u001a\u00020\u00012\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\u0011\u0010\u0006\u001a\r\u0012\u0004\u0012\u00020\u00010\u0007\u00a2\u0006\u0002\b\bH\u0007\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b\t\u0010\n\u001aR\u0010\u000b\u001a\u00020\u00012\b\b\u0002\u0010\u0004\u001a\u00020\u00052;\u0010\u0006\u001a7\u0012\u0013\u0012\u00110\u0003\u00a2\u0006\f\b\r\u0012\b\b\u000e\u0012\u0004\b\b(\u000f\u0012\u0013\u0012\u00110\u0010\u00a2\u0006\f\b\r\u0012\b\b\u000e\u0012\u0004\b\b(\u0011\u0012\u0004\u0012\u00020\u00010\f\u00a2\u0006\u0002\b\bH\u0007\u00f8\u0001\u0001\u001az\u0010\u0012\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u00132\f\u0010\u0014\u001a\b\u0012\u0004\u0012\u0002H\u00130\u00152\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0016\u001a\u00020\u00032;\u0010\u0017\u001a7\u0012\u0013\u0012\u0011H\u0013\u00a2\u0006\f\b\r\u0012\b\b\u000e\u0012\u0004\b\b(\u0018\u0012\u0013\u0012\u00110\u0010\u00a2\u0006\f\b\r\u0012\b\b\u000e\u0012\u0004\b\b(\u0011\u0012\u0004\u0012\u00020\u00010\f\u00a2\u0006\u0002\b\bH\u0007\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b\u0019\u0010\u001a\u001aR\u0010\u001b\u001a\u00020\u00012\b\b\u0002\u0010\u0004\u001a\u00020\u00052;\u0010\u0006\u001a7\u0012\u0013\u0012\u00110\u0003\u00a2\u0006\f\b\r\u0012\b\b\u000e\u0012\u0004\b\b(\u000f\u0012\u0013\u0012\u00110\u0010\u00a2\u0006\f\b\r\u0012\b\b\u000e\u0012\u0004\b\b(\u0011\u0012\u0004\u0012\u00020\u00010\f\u00a2\u0006\u0002\b\bH\u0007\u00f8\u0001\u0001\u001ay\u0010\u001c\u001a\u00020\u00012\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0016\u001a\u00020\u00032&\u0010\u001d\u001a\"\u0012\u0013\u0012\u00110\u0010\u00a2\u0006\f\b\r\u0012\b\b\u000e\u0012\u0004\b\b(\u0011\u0012\u0004\u0012\u00020\u00010\u001e\u00a2\u0006\u0002\b\b2&\u0010\u001f\u001a\"\u0012\u0013\u0012\u00110\u0010\u00a2\u0006\f\b\r\u0012\b\b\u000e\u0012\u0004\b\b(\u0011\u0012\u0004\u0012\u00020\u00010\u001e\u00a2\u0006\u0002\b\bH\u0007\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b \u0010!\u001a\u001d\u0010\"\u001a\u00020#2\u0006\u0010\u000f\u001a\u00020\u0003H\u0007\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b$\u0010%\u001a\u001d\u0010&\u001a\u00020\u00102\u0006\u0010\u000f\u001a\u00020\u0003H\u0007\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b\'\u0010(\u001a\u001d\u0010)\u001a\u00020*2\u0006\u0010\u000f\u001a\u00020\u0003H\u0007\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b+\u0010,\u001a\u001d\u0010-\u001a\u00020\u00032\u0006\u0010\u000f\u001a\u00020\u0003H\u0007\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b.\u0010/\u001a\u001d\u00100\u001a\u00020\u00032\u0006\u0010\u000f\u001a\u00020\u0003H\u0007\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b1\u0010/\u0082\u0002\u000b\n\u0005\b\u00a1\u001e0\u0001\n\u0002\b\u0019\u00a8\u00062"}, d2 = {"MaxWidthContainer", "", "maxContentWidth", "Landroidx/compose/ui/unit/Dp;", "modifier", "Landroidx/compose/ui/Modifier;", "content", "Lkotlin/Function0;", "Landroidx/compose/runtime/Composable;", "MaxWidthContainer-8Feqmps", "(FLandroidx/compose/ui/Modifier;Lkotlin/jvm/functions/Function0;)V", "ResponsiveContainer", "Lkotlin/Function2;", "Lkotlin/ParameterName;", "name", "maxWidth", "", "isWide", "ResponsiveGridLayout", "T", "items", "", "gap", "itemContent", "item", "ResponsiveGridLayout-d8LSEHM", "(Ljava/util/List;Landroidx/compose/ui/Modifier;FLkotlin/jvm/functions/Function2;)V", "ResponsivePageLayout", "ResponsiveTwoColumnLayout", "leftContent", "Lkotlin/Function1;", "rightContent", "ResponsiveTwoColumnLayout-ziNgDLE", "(Landroidx/compose/ui/Modifier;FLkotlin/jvm/functions/Function1;Lkotlin/jvm/functions/Function1;)V", "getWindowSizeClass", "Lcom/wuheng/smart/presentation/components/WindowSizeClass;", "getWindowSizeClass-0680j_4", "(F)Lcom/wuheng/smart/presentation/components/WindowSizeClass;", "isWideLayout", "isWideLayout-0680j_4", "(F)Z", "responsiveColumnCount", "", "responsiveColumnCount-0680j_4", "(F)I", "responsiveHorizontalPadding", "responsiveHorizontalPadding-0680j_4", "(F)F", "responsiveSpacing", "responsiveSpacing-0680j_4", "app_debug"})
public final class ResponsiveLayoutKt {
    
    /**
     * 响应式容器
     * 根据屏幕宽度自动调整布局
     *
     * @param content 内容 composable，接收 maxWidth 和 isWide 参数
     */
    @androidx.compose.runtime.Composable()
    public static final void ResponsiveContainer(@org.jetbrains.annotations.NotNull()
    androidx.compose.ui.Modifier modifier, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function2<? super androidx.compose.ui.unit.Dp, ? super java.lang.Boolean, kotlin.Unit> content) {
    }
    
    /**
     * 响应式页面布局
     * 自动处理页面边距和最大宽度限制
     */
    @androidx.compose.runtime.Composable()
    public static final void ResponsivePageLayout(@org.jetbrains.annotations.NotNull()
    androidx.compose.ui.Modifier modifier, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function2<? super androidx.compose.ui.unit.Dp, ? super java.lang.Boolean, kotlin.Unit> content) {
    }
}