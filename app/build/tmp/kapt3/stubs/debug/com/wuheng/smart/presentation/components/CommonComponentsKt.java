package com.wuheng.smart.presentation.components;

import androidx.compose.foundation.layout.*;
import androidx.compose.material.icons.Icons;
import androidx.compose.material3.*;
import androidx.compose.runtime.Composable;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.graphics.Brush;
import androidx.compose.ui.graphics.vector.ImageVector;
import androidx.compose.ui.text.TextStyle;
import androidx.compose.ui.text.font.FontWeight;
import androidx.compose.ui.text.style.TextOverflow;
import androidx.compose.ui.unit.Dp;
import com.wuheng.smart.presentation.theme.*;

@kotlin.Metadata(mv = {1, 7, 1}, k = 2, d1 = {"\u0000l\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\r\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u0007\n\u0002\b\u0015\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\r\u001aC\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00072\b\b\u0002\u0010\b\u001a\u00020\t2\b\b\u0002\u0010\n\u001a\u00020\u0005H\u0007\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b\u000b\u0010\f\u001aV\u0010\r\u001a\u00020\u00012\u0006\u0010\u000e\u001a\u00020\u000f2\f\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00010\u00112\b\b\u0002\u0010\u0006\u001a\u00020\u00072\n\b\u0002\u0010\u0012\u001a\u0004\u0018\u00010\u000f2\n\b\u0002\u0010\u0013\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0014\u001a\u0004\u0018\u00010\u000f2\b\b\u0002\u0010\u0015\u001a\u00020\u0016H\u0007\u001a\u001a\u0010\u0017\u001a\u00020\u00012\u0006\u0010\u0018\u001a\u00020\u000f2\b\b\u0002\u0010\u0006\u001a\u00020\u0007H\u0007\u001aA\u0010\u0019\u001a\u00020\u00012\u0006\u0010\u001a\u001a\u00020\u000f2\u0006\u0010\u001b\u001a\u00020\u000f2\u0006\u0010\u001c\u001a\u00020\u000f2\b\b\u0002\u0010\u0006\u001a\u00020\u00072\b\b\u0002\u0010\u001d\u001a\u00020\u0005H\u0007\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b\u001e\u0010\u001f\u001a(\u0010 \u001a\u00020\u00012\u0006\u0010\u0018\u001a\u00020\u000f2\f\u0010!\u001a\b\u0012\u0004\u0012\u00020\u00010\u00112\b\b\u0002\u0010\u0006\u001a\u00020\u0007H\u0007\u001aQ\u0010\"\u001a\u00020\u00012\b\b\u0002\u0010\u0006\u001a\u00020\u00072\b\b\u0002\u0010#\u001a\u00020$2\b\b\u0002\u0010%\u001a\u00020\t2\u001c\u0010&\u001a\u0018\u0012\u0004\u0012\u00020(\u0012\u0004\u0012\u00020\u00010\'\u00a2\u0006\u0002\b)\u00a2\u0006\u0002\b*H\u0007\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b+\u0010,\u001a9\u0010-\u001a\u00020\u00012\u0006\u0010\u001a\u001a\u00020\u000f2\u0006\u0010\u001b\u001a\u00020\u000f2\b\b\u0002\u0010\u0006\u001a\u00020\u00072\b\b\u0002\u0010\u001d\u001a\u00020\u0005H\u0007\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b.\u0010/\u001aE\u00100\u001a\u00020\u00012\u0006\u0010\u001a\u001a\u00020\u000f2\u0006\u00101\u001a\u0002022\b\b\u0002\u0010\u0006\u001a\u00020\u00072\n\b\u0002\u00103\u001a\u0004\u0018\u00010\u000f2\b\b\u0002\u00104\u001a\u00020\u0005H\u0007\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b5\u00106\u001a\u001c\u00107\u001a\u00020\u00012\b\b\u0002\u0010\u0006\u001a\u00020\u00072\b\b\u0002\u00108\u001a\u00020\u000fH\u0007\u001a)\u00109\u001a\u00020\u00012\b\b\u0002\u0010\u0006\u001a\u00020\u00072\b\b\u0002\u0010:\u001a\u00020\tH\u0007\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b;\u0010<\u001a>\u0010=\u001a\u00020\u00012\u0006\u00108\u001a\u00020\u000f2\f\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00010\u00112\b\b\u0002\u0010\u0006\u001a\u00020\u00072\b\b\u0002\u0010>\u001a\u00020\u00162\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u0003H\u0007\u001aC\u0010?\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00072\b\b\u0002\u0010\b\u001a\u00020\t2\b\b\u0002\u0010\n\u001a\u00020\u0005H\u0007\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b@\u0010\f\u001a2\u0010A\u001a\u00020\u00012\u0006\u00108\u001a\u00020\u000f2\f\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00010\u00112\b\b\u0002\u0010\u0006\u001a\u00020\u00072\b\b\u0002\u0010>\u001a\u00020\u0016H\u0007\u001a0\u0010B\u001a\u00020\u00012\u0006\u00108\u001a\u00020\u000f2\u0006\u0010C\u001a\u00020\u00162\f\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00010\u00112\b\b\u0002\u0010\u0006\u001a\u00020\u0007H\u0007\u001a2\u0010D\u001a\u00020\u00012\u0006\u00108\u001a\u00020\u000f2\f\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00010\u00112\b\b\u0002\u0010\u0006\u001a\u00020\u00072\b\b\u0002\u0010E\u001a\u00020\u0016H\u0007\u001a\"\u0010F\u001a\u00020\u00012\u0006\u00108\u001a\u00020\u000f2\u0006\u0010G\u001a\u00020H2\b\b\u0002\u0010\u0006\u001a\u00020\u0007H\u0007\u001aM\u0010I\u001a\u00020\u00012\u0006\u0010\u001b\u001a\u00020\u000f2\u0006\u0010\u001c\u001a\u00020\u000f2\b\b\u0002\u0010\u0006\u001a\u00020\u00072\b\b\u0002\u0010J\u001a\u00020K2\b\b\u0002\u0010L\u001a\u00020K2\b\b\u0002\u0010\u001d\u001a\u00020\u0005H\u0007\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\bM\u0010N\u001ae\u0010O\u001a\u00020\u00012\b\b\u0002\u0010\u0006\u001a\u00020\u00072\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010%\u001a\u00020\t2\b\b\u0002\u0010P\u001a\u00020\t2\b\b\u0002\u0010Q\u001a\u00020\u00052\u001c\u0010&\u001a\u0018\u0012\u0004\u0012\u00020(\u0012\u0004\u0012\u00020\u00010\'\u00a2\u0006\u0002\b)\u00a2\u0006\u0002\b*H\u0007\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\bR\u0010S\u001a)\u0010T\u001a\u00020\u00012\b\b\u0002\u0010\u0006\u001a\u00020\u00072\b\b\u0002\u0010U\u001a\u00020\u0005H\u0007\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\bV\u0010W\u0082\u0002\u000b\n\u0005\b\u00a1\u001e0\u0001\n\u0002\b\u0019\u00a8\u0006X"}, d2 = {"CircleIconContainer", "", "icon", "Landroidx/compose/ui/graphics/vector/ImageVector;", "backgroundColor", "Landroidx/compose/ui/graphics/Color;", "modifier", "Landroidx/compose/ui/Modifier;", "iconSize", "Landroidx/compose/ui/unit/Dp;", "iconColor", "CircleIconContainer-SO87i5Q", "(Landroidx/compose/ui/graphics/vector/ImageVector;JLandroidx/compose/ui/Modifier;FJ)V", "ClickableListItem", "title", "", "onClick", "Lkotlin/Function0;", "subtitle", "leadingIcon", "trailingText", "showArrow", "", "EmptyView", "message", "EnvironmentDataItem", "label", "value", "unit", "valueColor", "EnvironmentDataItem-xwkQ0AY", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Landroidx/compose/ui/Modifier;J)V", "ErrorRetryView", "onRetry", "GradientCard", "gradient", "Landroidx/compose/ui/graphics/Brush;", "cornerRadius", "content", "Lkotlin/Function1;", "Landroidx/compose/foundation/layout/ColumnScope;", "Landroidx/compose/runtime/Composable;", "Lkotlin/ExtensionFunctionType;", "GradientCard-d8LSEHM", "(Landroidx/compose/ui/Modifier;Landroidx/compose/ui/graphics/Brush;FLkotlin/jvm/functions/Function1;)V", "InfoListItem", "InfoListItem-g2O1Hgs", "(Ljava/lang/String;Ljava/lang/String;Landroidx/compose/ui/Modifier;J)V", "LabeledProgressBar", "progress", "", "statusText", "statusColor", "LabeledProgressBar-xwkQ0AY", "(Ljava/lang/String;FLandroidx/compose/ui/Modifier;Ljava/lang/String;J)V", "LoadingIndicator", "text", "PaddedDivider", "horizontalPadding", "PaddedDivider-3ABfNKs", "(Landroidx/compose/ui/Modifier;F)V", "PrimaryButton", "enabled", "RoundedIconContainer", "RoundedIconContainer-SO87i5Q", "SecondaryButton", "SelectableChip", "selected", "SmallButton", "isPrimary", "StatusTag", "status", "Lcom/wuheng/smart/presentation/components/TagStatus;", "ValueWithUnit", "valueStyle", "Landroidx/compose/ui/text/TextStyle;", "unitStyle", "ValueWithUnit-kKL39v8", "(Ljava/lang/String;Ljava/lang/String;Landroidx/compose/ui/Modifier;Landroidx/compose/ui/text/TextStyle;Landroidx/compose/ui/text/TextStyle;J)V", "WuHengCard", "elevation", "shadowColor", "WuHengCard-74i2voM", "(Landroidx/compose/ui/Modifier;JFFJLkotlin/jvm/functions/Function1;)V", "WuHengDivider", "color", "WuHengDivider-4WTKRHQ", "(Landroidx/compose/ui/Modifier;J)V", "app_debug"})
public final class CommonComponentsKt {
    
    /**
     * 主按钮
     * 用于：主要操作
     */
    @androidx.compose.runtime.Composable()
    public static final void PrimaryButton(@org.jetbrains.annotations.NotNull()
    java.lang.String text, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onClick, @org.jetbrains.annotations.NotNull()
    androidx.compose.ui.Modifier modifier, boolean enabled, @org.jetbrains.annotations.Nullable()
    androidx.compose.ui.graphics.vector.ImageVector icon) {
    }
    
    /**
     * 次按钮
     * 用于：次要操作
     */
    @androidx.compose.runtime.Composable()
    public static final void SecondaryButton(@org.jetbrains.annotations.NotNull()
    java.lang.String text, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onClick, @org.jetbrains.annotations.NotNull()
    androidx.compose.ui.Modifier modifier, boolean enabled) {
    }
    
    /**
     * 小按钮
     * 用于：卡片内操作
     */
    @androidx.compose.runtime.Composable()
    public static final void SmallButton(@org.jetbrains.annotations.NotNull()
    java.lang.String text, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onClick, @org.jetbrains.annotations.NotNull()
    androidx.compose.ui.Modifier modifier, boolean isPrimary) {
    }
    
    /**
     * 选择标签
     * 用于：楼层选择、区域选择
     */
    @androidx.compose.runtime.Composable()
    public static final void SelectableChip(@org.jetbrains.annotations.NotNull()
    java.lang.String text, boolean selected, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onClick, @org.jetbrains.annotations.NotNull()
    androidx.compose.ui.Modifier modifier) {
    }
    
    /**
     * 状态标签
     * 用于：显示状态信息
     */
    @androidx.compose.runtime.Composable()
    public static final void StatusTag(@org.jetbrains.annotations.NotNull()
    java.lang.String text, @org.jetbrains.annotations.NotNull()
    com.wuheng.smart.presentation.components.TagStatus status, @org.jetbrains.annotations.NotNull()
    androidx.compose.ui.Modifier modifier) {
    }
    
    /**
     * 可点击列表项
     * 用于：设置项、导航项
     */
    @androidx.compose.runtime.Composable()
    public static final void ClickableListItem(@org.jetbrains.annotations.NotNull()
    java.lang.String title, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onClick, @org.jetbrains.annotations.NotNull()
    androidx.compose.ui.Modifier modifier, @org.jetbrains.annotations.Nullable()
    java.lang.String subtitle, @org.jetbrains.annotations.Nullable()
    androidx.compose.ui.graphics.vector.ImageVector leadingIcon, @org.jetbrains.annotations.Nullable()
    java.lang.String trailingText, boolean showArrow) {
    }
    
    /**
     * 加载指示器
     */
    @androidx.compose.runtime.Composable()
    public static final void LoadingIndicator(@org.jetbrains.annotations.NotNull()
    androidx.compose.ui.Modifier modifier, @org.jetbrains.annotations.NotNull()
    java.lang.String text) {
    }
    
    /**
     * 错误重试视图
     */
    @androidx.compose.runtime.Composable()
    public static final void ErrorRetryView(@org.jetbrains.annotations.NotNull()
    java.lang.String message, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onRetry, @org.jetbrains.annotations.NotNull()
    androidx.compose.ui.Modifier modifier) {
    }
    
    /**
     * 空数据视图
     */
    @androidx.compose.runtime.Composable()
    public static final void EmptyView(@org.jetbrains.annotations.NotNull()
    java.lang.String message, @org.jetbrains.annotations.NotNull()
    androidx.compose.ui.Modifier modifier) {
    }
}