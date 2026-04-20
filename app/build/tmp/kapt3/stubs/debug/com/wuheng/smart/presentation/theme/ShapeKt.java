package com.wuheng.smart.presentation.theme;

import androidx.compose.material3.Shapes;
import androidx.compose.ui.graphics.Shape;

@kotlin.Metadata(mv = {1, 7, 1}, k = 2, d1 = {"\u0000\u0012\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\ba\"\u0011\u0010\u0000\u001a\u00020\u0001\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0002\u0010\u0003\"\u0011\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007\"\u0011\u0010\b\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\t\u0010\u0007\"\u0011\u0010\n\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\u0007\"\u0011\u0010\f\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u0007\"\u0011\u0010\u000e\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0007\"\u0011\u0010\u0010\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0007\"\u0011\u0010\u0012\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0007\"\u0011\u0010\u0014\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0007\"\u0011\u0010\u0016\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0007\"\u0011\u0010\u0018\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u0007\"\u0011\u0010\u001a\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u0007\"\u0011\u0010\u001c\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\u0007\"\u0011\u0010\u001e\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001f\u0010\u0007\"\u0011\u0010 \u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b!\u0010\u0007\"\u0011\u0010\"\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b#\u0010\u0007\"\u0011\u0010$\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b%\u0010\u0007\"\u0011\u0010&\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\'\u0010\u0007\"\u0011\u0010(\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b)\u0010\u0007\"\u0011\u0010*\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b+\u0010\u0007\"\u0011\u0010,\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b-\u0010\u0007\"\u0011\u0010.\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b/\u0010\u0007\"\u0011\u00100\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b1\u0010\u0007\"\u0011\u00102\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b3\u0010\u0007\"\u0011\u00104\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b5\u0010\u0007\"\u0011\u00106\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b7\u0010\u0007\"\u0011\u00108\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b9\u0010\u0007\"\u0011\u0010:\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b;\u0010\u0007\"\u0011\u0010<\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b=\u0010\u0007\"\u0011\u0010>\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b?\u0010\u0007\"\u0011\u0010@\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\bA\u0010\u0007\"\u0011\u0010B\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\bC\u0010\u0007\"\u0011\u0010D\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\bE\u0010\u0007\"\u0011\u0010F\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\bG\u0010\u0007\"\u0011\u0010H\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\bI\u0010\u0007\"\u0011\u0010J\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\bK\u0010\u0007\"\u0011\u0010L\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\bM\u0010\u0007\"\u0011\u0010N\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\bO\u0010\u0007\"\u0011\u0010P\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\bQ\u0010\u0007\"\u0011\u0010R\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\bS\u0010\u0007\"\u0011\u0010T\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\bU\u0010\u0007\"\u0011\u0010V\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\bW\u0010\u0007\"\u0011\u0010X\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\bY\u0010\u0007\"\u0011\u0010Z\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b[\u0010\u0007\"\u0011\u0010\\\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b]\u0010\u0007\"\u0011\u0010^\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b_\u0010\u0007\"\u0011\u0010`\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\ba\u0010\u0007\"\u0011\u0010b\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\bc\u0010\u0007\"\u0011\u0010d\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\be\u0010\u0007\u00a8\u0006f"}, d2 = {"AppShapes", "Landroidx/compose/material3/Shapes;", "getAppShapes", "()Landroidx/compose/material3/Shapes;", "AvatarRoundedShape", "Landroidx/compose/foundation/shape/RoundedCornerShape;", "getAvatarRoundedShape", "()Landroidx/compose/foundation/shape/RoundedCornerShape;", "AvatarShape", "getAvatarShape", "BottomNavShape", "getBottomNavShape", "BottomSheetShape", "getBottomSheetShape", "BubbleShape", "getBubbleShape", "ButtonCircleShape", "getButtonCircleShape", "ButtonLargeShape", "getButtonLargeShape", "ButtonShape", "getButtonShape", "ButtonSmallShape", "getButtonSmallShape", "CardBottomRoundedShape", "getCardBottomRoundedShape", "CardExtraLargeShape", "getCardExtraLargeShape", "CardLargeShape", "getCardLargeShape", "CardShape", "getCardShape", "CardSmallShape", "getCardSmallShape", "CardTopRoundedShape", "getCardTopRoundedShape", "ChipShape", "getChipShape", "ChipSmallShape", "getChipSmallShape", "DialogShape", "getDialogShape", "DividerShape", "getDividerShape", "FanSpeedShape", "getFanSpeedShape", "FloorTagShape", "getFloorTagShape", "FullCircleShape", "getFullCircleShape", "ImageCircleShape", "getImageCircleShape", "ImageLargeShape", "getImageLargeShape", "ImageShape", "getImageShape", "IndicatorSelectedShape", "getIndicatorSelectedShape", "IndicatorShape", "getIndicatorShape", "InputLargeShape", "getInputLargeShape", "InputShape", "getInputShape", "ListItemBottomShape", "getListItemBottomShape", "ListItemMiddleShape", "getListItemMiddleShape", "ListItemShape", "getListItemShape", "ListItemTopShape", "getListItemTopShape", "MenuShape", "getMenuShape", "NoShape", "getNoShape", "ProgressFillShape", "getProgressFillShape", "ProgressLargeShape", "getProgressLargeShape", "ProgressTrackShape", "getProgressTrackShape", "SceneButtonLargeShape", "getSceneButtonLargeShape", "SceneButtonShape", "getSceneButtonShape", "SliderThumbShape", "getSliderThumbShape", "SliderTrackShape", "getSliderTrackShape", "SwitchThumbShape", "getSwitchThumbShape", "SwitchTrackShape", "getSwitchTrackShape", "TabSelectedShape", "getTabSelectedShape", "TabShape", "getTabShape", "ToastShape", "getToastShape", "ZoneTagShape", "getZoneTagShape", "app_debug"})
public final class ShapeKt {
    
    /**
     * 应用形状配置
     * - extraSmall: 用于小标签、小按钮 (4dp)
     * - small: 用于输入框、小卡片 (8dp)
     * - medium: 用于标准卡片、按钮 (12dp)
     * - large: 用于大卡片、模态框 (16dp)
     * - extraLarge: 用于特殊卡片、对话框 (24dp)
     */
    @org.jetbrains.annotations.NotNull()
    private static final androidx.compose.material3.Shapes AppShapes = null;
    
    /**
     * 标准卡片形状 - 16dp圆角
     * 用于：内容卡片、列表卡片
     */
    @org.jetbrains.annotations.NotNull()
    private static final androidx.compose.foundation.shape.RoundedCornerShape CardShape = null;
    
    /**
     * 大卡片形状 - 20dp圆角
     * 用于：首页大卡片、重要内容卡片
     */
    @org.jetbrains.annotations.NotNull()
    private static final androidx.compose.foundation.shape.RoundedCornerShape CardLargeShape = null;
    
    /**
     * 超大卡片形状 - 24dp圆角
     * 用于：特殊卡片、顶部卡片
     */
    @org.jetbrains.annotations.NotNull()
    private static final androidx.compose.foundation.shape.RoundedCornerShape CardExtraLargeShape = null;
    
    /**
     * 小卡片形状 - 12dp圆角
     * 用于：小卡片、标签卡片
     */
    @org.jetbrains.annotations.NotNull()
    private static final androidx.compose.foundation.shape.RoundedCornerShape CardSmallShape = null;
    
    /**
     * 顶部圆角卡片 - 只有顶部有圆角
     * 用于：底部弹出的卡片
     */
    @org.jetbrains.annotations.NotNull()
    private static final androidx.compose.foundation.shape.RoundedCornerShape CardTopRoundedShape = null;
    
    /**
     * 底部圆角卡片 - 只有底部有圆角
     * 用于：顶部固定的卡片
     */
    @org.jetbrains.annotations.NotNull()
    private static final androidx.compose.foundation.shape.RoundedCornerShape CardBottomRoundedShape = null;
    
    /**
     * 标准按钮形状 - 8dp圆角
     * 用于：主按钮、次按钮
     */
    @org.jetbrains.annotations.NotNull()
    private static final androidx.compose.foundation.shape.RoundedCornerShape ButtonShape = null;
    
    /**
     * 大按钮形状 - 12dp圆角
     * 用于：重要操作按钮
     */
    @org.jetbrains.annotations.NotNull()
    private static final androidx.compose.foundation.shape.RoundedCornerShape ButtonLargeShape = null;
    
    /**
     * 小按钮形状 - 6dp圆角
     * 用于：小按钮、图标按钮
     */
    @org.jetbrains.annotations.NotNull()
    private static final androidx.compose.foundation.shape.RoundedCornerShape ButtonSmallShape = null;
    
    /**
     * 全圆角按钮形状 - 50%圆角
     * 用于：悬浮按钮、圆形按钮
     */
    @org.jetbrains.annotations.NotNull()
    private static final androidx.compose.foundation.shape.RoundedCornerShape ButtonCircleShape = null;
    
    /**
     * 标准输入框形状 - 8dp圆角
     * 用于：文本输入框
     */
    @org.jetbrains.annotations.NotNull()
    private static final androidx.compose.foundation.shape.RoundedCornerShape InputShape = null;
    
    /**
     * 大输入框形状 - 12dp圆角
     * 用于：搜索框、大输入框
     */
    @org.jetbrains.annotations.NotNull()
    private static final androidx.compose.foundation.shape.RoundedCornerShape InputLargeShape = null;
    
    /**
     * Chip形状 - 16dp圆角 (半圆)
     * 用于：筛选标签、状态标签
     */
    @org.jetbrains.annotations.NotNull()
    private static final androidx.compose.foundation.shape.RoundedCornerShape ChipShape = null;
    
    /**
     * 小Chip形状 - 12dp圆角
     * 用于：小标签
     */
    @org.jetbrains.annotations.NotNull()
    private static final androidx.compose.foundation.shape.RoundedCornerShape ChipSmallShape = null;
    
    /**
     * 楼层标签形状 - 18dp圆角 (半圆)
     * 用于：楼层选择器标签
     */
    @org.jetbrains.annotations.NotNull()
    private static final androidx.compose.foundation.shape.RoundedCornerShape FloorTagShape = null;
    
    /**
     * 区域标签形状 - 16dp圆角
     * 用于：区域选择器标签
     */
    @org.jetbrains.annotations.NotNull()
    private static final androidx.compose.foundation.shape.RoundedCornerShape ZoneTagShape = null;
    
    /**
     * 场景按钮形状 - 16dp圆角
     * 用于：智能场景按钮
     */
    @org.jetbrains.annotations.NotNull()
    private static final androidx.compose.foundation.shape.RoundedCornerShape SceneButtonShape = null;
    
    /**
     * 场景按钮大形状 - 20dp圆角
     * 用于：大尺寸场景按钮
     */
    @org.jetbrains.annotations.NotNull()
    private static final androidx.compose.foundation.shape.RoundedCornerShape SceneButtonLargeShape = null;
    
    /**
     * 列表项形状 - 12dp圆角
     * 用于：列表项卡片
     */
    @org.jetbrains.annotations.NotNull()
    private static final androidx.compose.foundation.shape.RoundedCornerShape ListItemShape = null;
    
    /**
     * 列表项顶部形状 - 顶部圆角
     * 用于：列表第一个项
     */
    @org.jetbrains.annotations.NotNull()
    private static final androidx.compose.foundation.shape.RoundedCornerShape ListItemTopShape = null;
    
    /**
     * 列表项底部形状 - 底部圆角
     * 用于：列表最后一个项
     */
    @org.jetbrains.annotations.NotNull()
    private static final androidx.compose.foundation.shape.RoundedCornerShape ListItemBottomShape = null;
    
    /**
     * 列表项中间形状 - 无圆角
     * 用于：列表中间项
     */
    @org.jetbrains.annotations.NotNull()
    private static final androidx.compose.foundation.shape.RoundedCornerShape ListItemMiddleShape = null;
    
    /**
     * 底部导航形状 - 顶部圆角
     * 用于：底部导航栏
     */
    @org.jetbrains.annotations.NotNull()
    private static final androidx.compose.foundation.shape.RoundedCornerShape BottomNavShape = null;
    
    /**
     * Tab形状 - 8dp圆角
     * 用于：Tab选择器
     */
    @org.jetbrains.annotations.NotNull()
    private static final androidx.compose.foundation.shape.RoundedCornerShape TabShape = null;
    
    /**
     * Tab选中形状 - 全圆角
     * 用于：选中的Tab
     */
    @org.jetbrains.annotations.NotNull()
    private static final androidx.compose.foundation.shape.RoundedCornerShape TabSelectedShape = null;
    
    /**
     * 对话框形状 - 20dp圆角
     * 用于：对话框、弹窗
     */
    @org.jetbrains.annotations.NotNull()
    private static final androidx.compose.foundation.shape.RoundedCornerShape DialogShape = null;
    
    /**
     * 底部弹窗形状 - 顶部圆角
     * 用于：底部弹出的选择器、菜单
     */
    @org.jetbrains.annotations.NotNull()
    private static final androidx.compose.foundation.shape.RoundedCornerShape BottomSheetShape = null;
    
    /**
     * 菜单形状 - 12dp圆角
     * 用于：下拉菜单、弹出菜单
     */
    @org.jetbrains.annotations.NotNull()
    private static final androidx.compose.foundation.shape.RoundedCornerShape MenuShape = null;
    
    /**
     * Toast形状 - 8dp圆角
     * 用于：提示消息
     */
    @org.jetbrains.annotations.NotNull()
    private static final androidx.compose.foundation.shape.RoundedCornerShape ToastShape = null;
    
    /**
     * 头像形状 - 圆形
     * 用于：用户头像
     */
    @org.jetbrains.annotations.NotNull()
    private static final androidx.compose.foundation.shape.RoundedCornerShape AvatarShape = null;
    
    /**
     * 圆角头像形状 - 12dp圆角
     * 用于：方形头像
     */
    @org.jetbrains.annotations.NotNull()
    private static final androidx.compose.foundation.shape.RoundedCornerShape AvatarRoundedShape = null;
    
    /**
     * 进度条轨道形状 - 4dp圆角
     * 用于：进度条背景
     */
    @org.jetbrains.annotations.NotNull()
    private static final androidx.compose.foundation.shape.RoundedCornerShape ProgressTrackShape = null;
    
    /**
     * 进度条填充形状 - 4dp圆角
     * 用于：进度条填充
     */
    @org.jetbrains.annotations.NotNull()
    private static final androidx.compose.foundation.shape.RoundedCornerShape ProgressFillShape = null;
    
    /**
     * 大进度条形状 - 8dp圆角
     * 用于：大进度条
     */
    @org.jetbrains.annotations.NotNull()
    private static final androidx.compose.foundation.shape.RoundedCornerShape ProgressLargeShape = null;
    
    /**
     * 滑块轨道形状 - 4dp圆角
     * 用于：滑块轨道
     */
    @org.jetbrains.annotations.NotNull()
    private static final androidx.compose.foundation.shape.RoundedCornerShape SliderTrackShape = null;
    
    /**
     * 滑块拇指形状 - 圆形
     * 用于：滑块拖动按钮
     */
    @org.jetbrains.annotations.NotNull()
    private static final androidx.compose.foundation.shape.RoundedCornerShape SliderThumbShape = null;
    
    /**
     * 开关轨道形状 - 14dp圆角 (半圆)
     * 用于：开关背景
     */
    @org.jetbrains.annotations.NotNull()
    private static final androidx.compose.foundation.shape.RoundedCornerShape SwitchTrackShape = null;
    
    /**
     * 开关拇指形状 - 圆形
     * 用于：开关按钮
     */
    @org.jetbrains.annotations.NotNull()
    private static final androidx.compose.foundation.shape.RoundedCornerShape SwitchThumbShape = null;
    
    /**
     * 风速档位形状 - 8dp圆角
     * 用于：风速选择按钮
     */
    @org.jetbrains.annotations.NotNull()
    private static final androidx.compose.foundation.shape.RoundedCornerShape FanSpeedShape = null;
    
    /**
     * 图片形状 - 12dp圆角
     * 用于：普通图片
     */
    @org.jetbrains.annotations.NotNull()
    private static final androidx.compose.foundation.shape.RoundedCornerShape ImageShape = null;
    
    /**
     * 图片大形状 - 16dp圆角
     * 用于：大图片
     */
    @org.jetbrains.annotations.NotNull()
    private static final androidx.compose.foundation.shape.RoundedCornerShape ImageLargeShape = null;
    
    /**
     * 图片圆形形状
     * 用于：圆形图片
     */
    @org.jetbrains.annotations.NotNull()
    private static final androidx.compose.foundation.shape.RoundedCornerShape ImageCircleShape = null;
    
    /**
     * 气泡形状 - 12dp圆角
     * 用于：消息气泡、提示气泡
     */
    @org.jetbrains.annotations.NotNull()
    private static final androidx.compose.foundation.shape.RoundedCornerShape BubbleShape = null;
    
    /**
     * 指示器形状 - 4dp圆角
     * 用于：页面指示器、步骤指示器
     */
    @org.jetbrains.annotations.NotNull()
    private static final androidx.compose.foundation.shape.RoundedCornerShape IndicatorShape = null;
    
    /**
     * 指示器选中形状 - 8dp圆角
     * 用于：选中的指示器
     */
    @org.jetbrains.annotations.NotNull()
    private static final androidx.compose.foundation.shape.RoundedCornerShape IndicatorSelectedShape = null;
    
    /**
     * 分割线形状 - 0.5dp高度
     * 用于：分隔线
     */
    @org.jetbrains.annotations.NotNull()
    private static final androidx.compose.foundation.shape.RoundedCornerShape DividerShape = null;
    
    /**
     * 无边框形状
     * 用于：不需要圆角的元素
     */
    @org.jetbrains.annotations.NotNull()
    private static final androidx.compose.foundation.shape.RoundedCornerShape NoShape = null;
    
    /**
     * 全圆角形状
     * 用于：圆形元素
     */
    @org.jetbrains.annotations.NotNull()
    private static final androidx.compose.foundation.shape.RoundedCornerShape FullCircleShape = null;
    
    @org.jetbrains.annotations.NotNull()
    public static final androidx.compose.material3.Shapes getAppShapes() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public static final androidx.compose.foundation.shape.RoundedCornerShape getCardShape() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public static final androidx.compose.foundation.shape.RoundedCornerShape getCardLargeShape() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public static final androidx.compose.foundation.shape.RoundedCornerShape getCardExtraLargeShape() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public static final androidx.compose.foundation.shape.RoundedCornerShape getCardSmallShape() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public static final androidx.compose.foundation.shape.RoundedCornerShape getCardTopRoundedShape() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public static final androidx.compose.foundation.shape.RoundedCornerShape getCardBottomRoundedShape() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public static final androidx.compose.foundation.shape.RoundedCornerShape getButtonShape() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public static final androidx.compose.foundation.shape.RoundedCornerShape getButtonLargeShape() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public static final androidx.compose.foundation.shape.RoundedCornerShape getButtonSmallShape() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public static final androidx.compose.foundation.shape.RoundedCornerShape getButtonCircleShape() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public static final androidx.compose.foundation.shape.RoundedCornerShape getInputShape() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public static final androidx.compose.foundation.shape.RoundedCornerShape getInputLargeShape() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public static final androidx.compose.foundation.shape.RoundedCornerShape getChipShape() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public static final androidx.compose.foundation.shape.RoundedCornerShape getChipSmallShape() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public static final androidx.compose.foundation.shape.RoundedCornerShape getFloorTagShape() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public static final androidx.compose.foundation.shape.RoundedCornerShape getZoneTagShape() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public static final androidx.compose.foundation.shape.RoundedCornerShape getSceneButtonShape() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public static final androidx.compose.foundation.shape.RoundedCornerShape getSceneButtonLargeShape() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public static final androidx.compose.foundation.shape.RoundedCornerShape getListItemShape() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public static final androidx.compose.foundation.shape.RoundedCornerShape getListItemTopShape() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public static final androidx.compose.foundation.shape.RoundedCornerShape getListItemBottomShape() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public static final androidx.compose.foundation.shape.RoundedCornerShape getListItemMiddleShape() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public static final androidx.compose.foundation.shape.RoundedCornerShape getBottomNavShape() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public static final androidx.compose.foundation.shape.RoundedCornerShape getTabShape() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public static final androidx.compose.foundation.shape.RoundedCornerShape getTabSelectedShape() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public static final androidx.compose.foundation.shape.RoundedCornerShape getDialogShape() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public static final androidx.compose.foundation.shape.RoundedCornerShape getBottomSheetShape() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public static final androidx.compose.foundation.shape.RoundedCornerShape getMenuShape() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public static final androidx.compose.foundation.shape.RoundedCornerShape getToastShape() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public static final androidx.compose.foundation.shape.RoundedCornerShape getAvatarShape() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public static final androidx.compose.foundation.shape.RoundedCornerShape getAvatarRoundedShape() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public static final androidx.compose.foundation.shape.RoundedCornerShape getProgressTrackShape() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public static final androidx.compose.foundation.shape.RoundedCornerShape getProgressFillShape() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public static final androidx.compose.foundation.shape.RoundedCornerShape getProgressLargeShape() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public static final androidx.compose.foundation.shape.RoundedCornerShape getSliderTrackShape() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public static final androidx.compose.foundation.shape.RoundedCornerShape getSliderThumbShape() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public static final androidx.compose.foundation.shape.RoundedCornerShape getSwitchTrackShape() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public static final androidx.compose.foundation.shape.RoundedCornerShape getSwitchThumbShape() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public static final androidx.compose.foundation.shape.RoundedCornerShape getFanSpeedShape() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public static final androidx.compose.foundation.shape.RoundedCornerShape getImageShape() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public static final androidx.compose.foundation.shape.RoundedCornerShape getImageLargeShape() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public static final androidx.compose.foundation.shape.RoundedCornerShape getImageCircleShape() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public static final androidx.compose.foundation.shape.RoundedCornerShape getBubbleShape() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public static final androidx.compose.foundation.shape.RoundedCornerShape getIndicatorShape() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public static final androidx.compose.foundation.shape.RoundedCornerShape getIndicatorSelectedShape() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public static final androidx.compose.foundation.shape.RoundedCornerShape getDividerShape() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public static final androidx.compose.foundation.shape.RoundedCornerShape getNoShape() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public static final androidx.compose.foundation.shape.RoundedCornerShape getFullCircleShape() {
        return null;
    }
}