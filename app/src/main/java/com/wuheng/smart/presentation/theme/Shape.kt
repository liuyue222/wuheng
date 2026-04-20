package com.wuheng.smart.presentation.theme

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Shapes
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp

/**
 * 应用形状规范
 * 基于Material 3形状系统，定义统一的圆角规范
 */

// ==================== Material 3 标准形状 ====================

/**
 * 应用形状配置
 * - extraSmall: 用于小标签、小按钮 (4dp)
 * - small: 用于输入框、小卡片 (8dp)
 * - medium: 用于标准卡片、按钮 (12dp)
 * - large: 用于大卡片、模态框 (16dp)
 * - extraLarge: 用于特殊卡片、对话框 (24dp)
 */
val AppShapes = Shapes(
    extraSmall = RoundedCornerShape(4.dp),
    small = RoundedCornerShape(8.dp),
    medium = RoundedCornerShape(12.dp),
    large = RoundedCornerShape(16.dp),
    extraLarge = RoundedCornerShape(24.dp)
)

// ==================== 卡片形状 ====================

/**
 * 标准卡片形状 - 16dp圆角
 * 用于：内容卡片、列表卡片
 */
val CardShape = RoundedCornerShape(16.dp)

/**
 * 大卡片形状 - 20dp圆角
 * 用于：首页大卡片、重要内容卡片
 */
val CardLargeShape = RoundedCornerShape(20.dp)

/**
 * 超大卡片形状 - 24dp圆角
 * 用于：特殊卡片、顶部卡片
 */
val CardExtraLargeShape = RoundedCornerShape(24.dp)

/**
 * 小卡片形状 - 12dp圆角
 * 用于：小卡片、标签卡片
 */
val CardSmallShape = RoundedCornerShape(12.dp)

/**
 * 顶部圆角卡片 - 只有顶部有圆角
 * 用于：底部弹出的卡片
 */
val CardTopRoundedShape = RoundedCornerShape(
    topStart = 20.dp,
    topEnd = 20.dp,
    bottomStart = 0.dp,
    bottomEnd = 0.dp
)

/**
 * 底部圆角卡片 - 只有底部有圆角
 * 用于：顶部固定的卡片
 */
val CardBottomRoundedShape = RoundedCornerShape(
    topStart = 0.dp,
    topEnd = 0.dp,
    bottomStart = 20.dp,
    bottomEnd = 20.dp
)

// ==================== 按钮形状 ====================

/**
 * 标准按钮形状 - 8dp圆角
 * 用于：主按钮、次按钮
 */
val ButtonShape = RoundedCornerShape(8.dp)

/**
 * 大按钮形状 - 12dp圆角
 * 用于：重要操作按钮
 */
val ButtonLargeShape = RoundedCornerShape(12.dp)

/**
 * 小按钮形状 - 6dp圆角
 * 用于：小按钮、图标按钮
 */
val ButtonSmallShape = RoundedCornerShape(6.dp)

/**
 * 全圆角按钮形状 - 50%圆角
 * 用于：悬浮按钮、圆形按钮
 */
val ButtonCircleShape = CircleShape

// ==================== 输入框形状 ====================

/**
 * 标准输入框形状 - 8dp圆角
 * 用于：文本输入框
 */
val InputShape = RoundedCornerShape(8.dp)

/**
 * 大输入框形状 - 12dp圆角
 * 用于：搜索框、大输入框
 */
val InputLargeShape = RoundedCornerShape(12.dp)

// ==================== Chip/Tag形状 ====================

/**
 * Chip形状 - 16dp圆角 (半圆)
 * 用于：筛选标签、状态标签
 */
val ChipShape = RoundedCornerShape(16.dp)

/**
 * 小Chip形状 - 12dp圆角
 * 用于：小标签
 */
val ChipSmallShape = RoundedCornerShape(12.dp)

/**
 * 楼层标签形状 - 18dp圆角 (半圆)
 * 用于：楼层选择器标签
 */
val FloorTagShape = RoundedCornerShape(18.dp)

/**
 * 区域标签形状 - 16dp圆角
 * 用于：区域选择器标签
 */
val ZoneTagShape = RoundedCornerShape(16.dp)

// ==================== 场景按钮形状 ====================

/**
 * 场景按钮形状 - 16dp圆角
 * 用于：智能场景按钮
 */
val SceneButtonShape = RoundedCornerShape(16.dp)

/**
 * 场景按钮大形状 - 20dp圆角
 * 用于：大尺寸场景按钮
 */
val SceneButtonLargeShape = RoundedCornerShape(20.dp)

// ==================== 列表项形状 ====================

/**
 * 列表项形状 - 12dp圆角
 * 用于：列表项卡片
 */
val ListItemShape = RoundedCornerShape(12.dp)

/**
 * 列表项顶部形状 - 顶部圆角
 * 用于：列表第一个项
 */
val ListItemTopShape = RoundedCornerShape(
    topStart = 12.dp,
    topEnd = 12.dp,
    bottomStart = 0.dp,
    bottomEnd = 0.dp
)

/**
 * 列表项底部形状 - 底部圆角
 * 用于：列表最后一个项
 */
val ListItemBottomShape = RoundedCornerShape(
    topStart = 0.dp,
    topEnd = 0.dp,
    bottomStart = 12.dp,
    bottomEnd = 12.dp
)

/**
 * 列表项中间形状 - 无圆角
 * 用于：列表中间项
 */
val ListItemMiddleShape = RoundedCornerShape(0.dp)

// ==================== 导航形状 ====================

/**
 * 底部导航形状 - 顶部圆角
 * 用于：底部导航栏
 */
val BottomNavShape = RoundedCornerShape(
    topStart = 16.dp,
    topEnd = 16.dp,
    bottomStart = 0.dp,
    bottomEnd = 0.dp
)

/**
 * Tab形状 - 8dp圆角
 * 用于：Tab选择器
 */
val TabShape = RoundedCornerShape(8.dp)

/**
 * Tab选中形状 - 全圆角
 * 用于：选中的Tab
 */
val TabSelectedShape = RoundedCornerShape(8.dp)

// ==================== 弹窗/对话框形状 ====================

/**
 * 对话框形状 - 20dp圆角
 * 用于：对话框、弹窗
 */
val DialogShape = RoundedCornerShape(20.dp)

/**
 * 底部弹窗形状 - 顶部圆角
 * 用于：底部弹出的选择器、菜单
 */
val BottomSheetShape = RoundedCornerShape(
    topStart = 20.dp,
    topEnd = 20.dp,
    bottomStart = 0.dp,
    bottomEnd = 0.dp
)

/**
 * 菜单形状 - 12dp圆角
 * 用于：下拉菜单、弹出菜单
 */
val MenuShape = RoundedCornerShape(12.dp)

/**
 * Toast形状 - 8dp圆角
 * 用于：提示消息
 */
val ToastShape = RoundedCornerShape(8.dp)

// ==================== 头像形状 ====================

/**
 * 头像形状 - 圆形
 * 用于：用户头像
 */
val AvatarShape = CircleShape

/**
 * 圆角头像形状 - 12dp圆角
 * 用于：方形头像
 */
val AvatarRoundedShape = RoundedCornerShape(12.dp)

// ==================== 进度条形状 ====================

/**
 * 进度条轨道形状 - 4dp圆角
 * 用于：进度条背景
 */
val ProgressTrackShape = RoundedCornerShape(4.dp)

/**
 * 进度条填充形状 - 4dp圆角
 * 用于：进度条填充
 */
val ProgressFillShape = RoundedCornerShape(4.dp)

/**
 * 大进度条形状 - 8dp圆角
 * 用于：大进度条
 */
val ProgressLargeShape = RoundedCornerShape(8.dp)

// ==================== 滑块形状 ====================

/**
 * 滑块轨道形状 - 4dp圆角
 * 用于：滑块轨道
 */
val SliderTrackShape = RoundedCornerShape(4.dp)

/**
 * 滑块拇指形状 - 圆形
 * 用于：滑块拖动按钮
 */
val SliderThumbShape = CircleShape

// ==================== 开关形状 ====================

/**
 * 开关轨道形状 - 14dp圆角 (半圆)
 * 用于：开关背景
 */
val SwitchTrackShape = RoundedCornerShape(14.dp)

/**
 * 开关拇指形状 - 圆形
 * 用于：开关按钮
 */
val SwitchThumbShape = CircleShape

// ==================== 风速档位形状 ====================

/**
 * 风速档位形状 - 8dp圆角
 * 用于：风速选择按钮
 */
val FanSpeedShape = RoundedCornerShape(8.dp)

// ==================== 图片形状 ====================

/**
 * 图片形状 - 12dp圆角
 * 用于：普通图片
 */
val ImageShape = RoundedCornerShape(12.dp)

/**
 * 图片大形状 - 16dp圆角
 * 用于：大图片
 */
val ImageLargeShape = RoundedCornerShape(16.dp)

/**
 * 图片圆形形状
 * 用于：圆形图片
 */
val ImageCircleShape = CircleShape

// ==================== 特殊形状 ====================

/**
 * 气泡形状 - 12dp圆角
 * 用于：消息气泡、提示气泡
 */
val BubbleShape = RoundedCornerShape(12.dp)

/**
 * 指示器形状 - 4dp圆角
 * 用于：页面指示器、步骤指示器
 */
val IndicatorShape = RoundedCornerShape(4.dp)

/**
 * 指示器选中形状 - 8dp圆角
 * 用于：选中的指示器
 */
val IndicatorSelectedShape = RoundedCornerShape(8.dp)

/**
 * 分割线形状 - 0.5dp高度
 * 用于：分隔线
 */
val DividerShape = RoundedCornerShape(0.5.dp)

/**
 * 无边框形状
 * 用于：不需要圆角的元素
 */
val NoShape = RoundedCornerShape(0.dp)

/**
 * 全圆角形状
 * 用于：圆形元素
 */
val FullCircleShape = CircleShape
