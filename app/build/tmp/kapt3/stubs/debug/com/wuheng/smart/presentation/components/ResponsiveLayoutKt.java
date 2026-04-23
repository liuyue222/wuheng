package com.wuheng.smart.presentation.components;

import androidx.compose.foundation.layout.Arrangement;
import androidx.compose.material3.ExperimentalMaterial3Api;
import androidx.compose.runtime.Composable;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.unit.Dp;

@kotlin.OptIn(markerClass = {androidx.compose.material3.ExperimentalMaterial3Api.class})
@kotlin.Metadata(mv = {1, 7, 1}, k = 2, d1 = {"\u0000p\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\r\n\u0002\u0010\u0007\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0013\u001ao\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u00022\f\u0010\u0003\u001a\b\u0012\u0004\u0012\u0002H\u00020\u00042\b\b\u0002\u0010\u0005\u001a\u00020\u00062\b\b\u0002\u0010\u0007\u001a\u00020\b2\b\b\u0002\u0010\t\u001a\u00020\b2&\u0010\n\u001a\"\u0012\u0013\u0012\u0011H\u0002\u00a2\u0006\f\b\f\u0012\b\b\r\u0012\u0004\b\b(\u000e\u0012\u0004\u0012\u00020\u00010\u000b\u00a2\u0006\u0002\b\u000fH\u0007\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b\u0010\u0010\u0011\u001aO\u0010\u0012\u001a\u00020\u00012\b\b\u0002\u0010\u0005\u001a\u00020\u00062\f\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00140\u00042\u0006\u0010\u0015\u001a\u00020\u00162\u0012\u0010\u0017\u001a\u000e\u0012\u0004\u0012\u00020\u0016\u0012\u0004\u0012\u00020\u00010\u000b2\u0011\u0010\u0018\u001a\r\u0012\u0004\u0012\u00020\u00010\u0019\u00a2\u0006\u0002\b\u000fH\u0007\u001a<\u0010\u001a\u001a\u00020\u00012\b\b\u0002\u0010\u001b\u001a\u00020\b2\b\b\u0002\u0010\u0005\u001a\u00020\u00062\u0011\u0010\u0018\u001a\r\u0012\u0004\u0012\u00020\u00010\u0019\u00a2\u0006\u0002\b\u000fH\u0007\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b\u001c\u0010\u001d\u001a&\u0010\u001e\u001a\u00020\u00012\u0006\u0010\u000e\u001a\u00020\u00142\u0006\u0010\u001f\u001a\u00020 2\f\u0010!\u001a\b\u0012\u0004\u0012\u00020\u00010\u0019H\u0003\u001aR\u0010\"\u001a\u00020\u00012\b\b\u0002\u0010\u0005\u001a\u00020\u00062;\u0010\u0018\u001a7\u0012\u0013\u0012\u00110\b\u00a2\u0006\f\b\f\u0012\b\b\r\u0012\u0004\b\b($\u0012\u0013\u0012\u00110 \u00a2\u0006\f\b\f\u0012\b\b\r\u0012\u0004\b\b(%\u0012\u0004\u0012\u00020\u00010#\u00a2\u0006\u0002\b\u000fH\u0007\u00f8\u0001\u0001\u001az\u0010&\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u00022\f\u0010\u0003\u001a\b\u0012\u0004\u0012\u0002H\u00020\u00042\b\b\u0002\u0010\u0005\u001a\u00020\u00062\b\b\u0002\u0010\t\u001a\u00020\b2;\u0010\n\u001a7\u0012\u0013\u0012\u0011H\u0002\u00a2\u0006\f\b\f\u0012\b\b\r\u0012\u0004\b\b(\u000e\u0012\u0013\u0012\u00110 \u00a2\u0006\f\b\f\u0012\b\b\r\u0012\u0004\b\b(%\u0012\u0004\u0012\u00020\u00010#\u00a2\u0006\u0002\b\u000fH\u0007\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b\'\u0010(\u001aR\u0010)\u001a\u00020\u00012\b\b\u0002\u0010\u0005\u001a\u00020\u00062;\u0010\u0018\u001a7\u0012\u0013\u0012\u00110\b\u00a2\u0006\f\b\f\u0012\b\b\r\u0012\u0004\b\b($\u0012\u0013\u0012\u00110 \u00a2\u0006\f\b\f\u0012\b\b\r\u0012\u0004\b\b(%\u0012\u0004\u0012\u00020\u00010#\u00a2\u0006\u0002\b\u000fH\u0007\u00f8\u0001\u0001\u001ay\u0010*\u001a\u00020\u00012\b\b\u0002\u0010\u0005\u001a\u00020\u00062\b\b\u0002\u0010\t\u001a\u00020\b2&\u0010+\u001a\"\u0012\u0013\u0012\u00110 \u00a2\u0006\f\b\f\u0012\b\b\r\u0012\u0004\b\b(%\u0012\u0004\u0012\u00020\u00010\u000b\u00a2\u0006\u0002\b\u000f2&\u0010,\u001a\"\u0012\u0013\u0012\u00110 \u00a2\u0006\f\b\f\u0012\b\b\r\u0012\u0004\b\b(%\u0012\u0004\u0012\u00020\u00010\u000b\u00a2\u0006\u0002\b\u000fH\u0007\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b-\u0010.\u001aY\u0010/\u001a\u00020\u00012\b\b\u0002\u0010\u0005\u001a\u00020\u00062\b\b\u0002\u00100\u001a\u0002012\b\b\u0002\u0010\t\u001a\u00020\b2\u0011\u00102\u001a\r\u0012\u0004\u0012\u00020\u00010\u0019\u00a2\u0006\u0002\b\u000f2\u0011\u00103\u001a\r\u0012\u0004\u0012\u00020\u00010\u0019\u00a2\u0006\u0002\b\u000fH\u0007\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b4\u00105\u001a%\u00106\u001a\u0002072\u0006\u0010$\u001a\u00020\b2\u0006\u00108\u001a\u00020\bH\u0007\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b9\u0010:\u001a\u001d\u0010;\u001a\u00020<2\u0006\u0010$\u001a\u00020\bH\u0007\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b=\u0010>\u001a%\u0010?\u001a\u00020 2\u0006\u0010$\u001a\u00020\b2\u0006\u00108\u001a\u00020\bH\u0007\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b@\u0010A\u001a\u001d\u0010B\u001a\u00020 2\u0006\u0010$\u001a\u00020\bH\u0007\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\bC\u0010D\u001a\u001d\u0010E\u001a\u00020\u00162\u0006\u0010$\u001a\u00020\bH\u0007\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\bF\u0010G\u001a\u001d\u0010H\u001a\u00020\b2\u0006\u0010$\u001a\u00020\bH\u0007\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\bI\u0010J\u001a\u001d\u0010K\u001a\u00020\b2\u0006\u0010$\u001a\u00020\bH\u0007\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\bL\u0010J\u001a%\u0010M\u001a\u00020 2\u0006\u0010$\u001a\u00020\b2\u0006\u00108\u001a\u00020\bH\u0007\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\bN\u0010A\u0082\u0002\u000b\n\u0005\b\u00a1\u001e0\u0001\n\u0002\b\u0019\u00a8\u0006O"}, d2 = {"AdaptiveCardGrid", "", "T", "items", "", "modifier", "Landroidx/compose/ui/Modifier;", "minCardWidth", "Landroidx/compose/ui/unit/Dp;", "gap", "itemContent", "Lkotlin/Function1;", "Lkotlin/ParameterName;", "name", "item", "Landroidx/compose/runtime/Composable;", "AdaptiveCardGrid--JS8el8", "(Ljava/util/List;Landroidx/compose/ui/Modifier;FFLkotlin/jvm/functions/Function1;)V", "AdaptiveNavigationLayout", "navigationItems", "Lcom/wuheng/smart/presentation/components/NavigationItem;", "selectedIndex", "", "onItemSelected", "content", "Lkotlin/Function0;", "MaxWidthContainer", "maxContentWidth", "MaxWidthContainer-8Feqmps", "(FLandroidx/compose/ui/Modifier;Lkotlin/jvm/functions/Function0;)V", "NavigationItemView", "isSelected", "", "onClick", "ResponsiveContainer", "Lkotlin/Function2;", "maxWidth", "isWide", "ResponsiveGridLayout", "ResponsiveGridLayout-d8LSEHM", "(Ljava/util/List;Landroidx/compose/ui/Modifier;FLkotlin/jvm/functions/Function2;)V", "ResponsivePageLayout", "ResponsiveTwoColumnLayout", "leftContent", "rightContent", "ResponsiveTwoColumnLayout-ziNgDLE", "(Landroidx/compose/ui/Modifier;FLkotlin/jvm/functions/Function1;Lkotlin/jvm/functions/Function1;)V", "TwoPaneLayout", "masterWidthFraction", "", "masterContent", "detailContent", "TwoPaneLayout-TDGSqEk", "(Landroidx/compose/ui/Modifier;FFLkotlin/jvm/functions/Function0;Lkotlin/jvm/functions/Function0;)V", "getScreenOrientation", "Lcom/wuheng/smart/presentation/components/ScreenOrientation;", "maxHeight", "getScreenOrientation-YgX7TsA", "(FF)Lcom/wuheng/smart/presentation/components/ScreenOrientation;", "getWindowSizeClass", "Lcom/wuheng/smart/presentation/components/WindowSizeClass;", "getWindowSizeClass-0680j_4", "(F)Lcom/wuheng/smart/presentation/components/WindowSizeClass;", "isTabletDevice", "isTabletDevice-YgX7TsA", "(FF)Z", "isWideLayout", "isWideLayout-0680j_4", "(F)Z", "responsiveColumnCount", "responsiveColumnCount-0680j_4", "(F)I", "responsiveHorizontalPadding", "responsiveHorizontalPadding-0680j_4", "(F)F", "responsiveSpacing", "responsiveSpacing-0680j_4", "shouldUseTwoPaneLayout", "shouldUseTwoPaneLayout-YgX7TsA", "app_debug"})
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
    
    /**
     * iPad侧边导航布局
     * 宽屏时显示侧边导航栏，窄屏时显示底部导航
     *
     * @param navigationItems 导航项列表
     * @param selectedIndex 当前选中索引
     * @param onItemSelected 导航项选中回调
     * @param content 内容区域
     */
    @androidx.compose.runtime.Composable()
    public static final void AdaptiveNavigationLayout(@org.jetbrains.annotations.NotNull()
    androidx.compose.ui.Modifier modifier, @org.jetbrains.annotations.NotNull()
    java.util.List<com.wuheng.smart.presentation.components.NavigationItem> navigationItems, int selectedIndex, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super java.lang.Integer, kotlin.Unit> onItemSelected, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> content) {
    }
    
    /**
     * 导航项视图
     */
    @androidx.compose.runtime.Composable()
    private static final void NavigationItemView(com.wuheng.smart.presentation.components.NavigationItem item, boolean isSelected, kotlin.jvm.functions.Function0<kotlin.Unit> onClick) {
    }
}