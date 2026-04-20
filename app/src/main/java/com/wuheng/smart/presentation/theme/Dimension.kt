package com.wuheng.smart.presentation.theme

import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// ==================== 间距系统 (基于8dp网格) ====================

/**
 * 极小号间距 - 4dp
 * 用于：图标与文字间距、紧凑元素间距
 */
val spacing_xs = 4.dp

/**
 * 小号间距 - 8dp
 * 用于：卡片内部元素间距、小图标间距
 */
val spacing_sm = 8.dp

/**
 * 中等间距 - 12dp
 * 用于：列表项内部间距、按钮内部间距
 */
val spacing_md = 12.dp

/**
 * 默认间距 - 16dp
 * 用于：卡片内边距、段落间距
 */
val spacing_default = 16.dp

/**
 * 大号间距 - 20dp
 * 用于：大卡片内边距、重要区块间距
 */
val spacing_lg = 20.dp

/**
 * 超大号间距 - 24dp
 * 用于：区块之间的大间距
 */
val spacing_xl = 24.dp

/**
 * 特大号间距 - 32dp
 * 用于：大区块分隔、页面顶部间距
 */
val spacing_2xl = 32.dp

/**
 * 超大间距 - 40dp
 * 用于：页面大标题与内容的间距
 */
val spacing_3xl = 40.dp

/**
 * 巨大间距 - 48dp
 * 用于：重要区块的分隔
 */
val spacing_4xl = 48.dp

/**
 * 卡片垂直间距 - 12dp
 */
val card_spacing_vertical = 12.dp

// ==================== 页面边距 ====================

/**
 * 页面水平边距 - 16dp
 * 设计图标准：左右边距16dp
 */
val page_margin_horizontal = 16.dp

/**
 * 页面垂直边距 - 12dp
 */
val page_margin_vertical = 12.dp

/**
 * 页面顶部安全区域 - 8dp
 */
val page_top_safe_area = 8.dp

/**
 * 页面底部安全区域 (预留底部导航) - 80dp
 */
val page_bottom_safe_area = 80.dp

// ==================== 圆角系统 ====================

/**
 * 极小圆角 - 4dp
 * 用于：小标签、小按钮
 */
val corner_xs = 4.dp

/**
 * 小号圆角 - 8dp
 * 用于：小卡片、输入框
 */
val corner_sm = 8.dp

/**
 * 中等圆角 - 12dp
 * 用于：中等卡片、按钮
 */
val corner_md = 12.dp

/**
 * 默认圆角 - 16dp
 * 用于：标准卡片
 */
val corner_default = 16.dp

/**
 * 大号圆角 - 20dp
 * 用于：大卡片、模态框
 */
val corner_lg = 20.dp

/**
 * 超大圆角 - 24dp
 * 用于：特殊卡片、顶部卡片
 */
val corner_xl = 24.dp

/**
 * 特大圆角 - 28dp
 * 用于：首页大卡片
 */
val corner_2xl = 28.dp

/**
 * 全圆角 - 50% (用于圆形元素)
 */
val corner_full = 100.dp

// ==================== 阴影系统 ====================

/**
 * 无阴影
 */
val elevation_none = 0.dp

/**
 * 极小阴影 - 2dp
 * 用于：轻微浮起效果
 */
val elevation_xs = 2.dp

/**
 * 小阴影 - 4dp
 * 用于：小卡片、按钮
 */
val elevation_sm = 4.dp

/**
 * 中等阴影 - 8dp
 * 用于：标准卡片
 */
val elevation_md = 8.dp

/**
 * 大阴影 - 12dp
 * 用于：大卡片、悬浮按钮
 */
val elevation_lg = 12.dp

/**
 * 超大阴影 - 16dp
 * 用于：模态框、对话框
 */
val elevation_xl = 16.dp

// ==================== 文字大小系统 ====================

/**
 * 极小文字 - 10sp
 * 用于：标签、时间戳
 */
val text_xs_size = 10.sp

/**
 * 小号文字 - 12sp
 * 用于：辅助说明、提示文字
 */
val text_sm_size = 12.sp

/**
 * 说明文字 - 13sp
 * 用于：次要说明
 */
val text_caption_size = 13.sp

/**
 * 常规文字 - 14sp
 * 用于：正文内容
 */
val text_body_size = 14.sp

/**
 * 中大文字 - 15sp
 * 用于：稍重要的正文
 */
val text_body_medium_size = 15.sp

/**
 * 正文小字 - 13sp
 * 用于：次要正文
 */
val text_body_small_size = 13.sp

/**
 * 大号正文 - 16sp
 * 用于：列表项标题
 */
val text_body_large_size = 16.sp

/**
 * 小标题 - 18sp
 * 用于：小标题、卡片标题
 */
val text_h4_size = 18.sp

/**
 * 中标题 - 20sp
 * 用于：区块标题
 */
val text_h3_size = 20.sp

/**
 * 大标题 - 24sp
 * 用于：页面小标题
 */
val text_h2_size = 24.sp

/**
 * 特大标题 - 28sp
 * 用于：页面大标题
 */
val text_h1_size = 28.sp

/**
 * 超大标题 - 32sp
 * 用于：重要数字显示
 */
val text_display_size = 32.sp

/**
 * 巨大数字 - 48sp
 * 用于：温度显示等大数字
 */
val text_temperature_size = 48.sp

/**
 * 温度小数部分 - 32sp
 */
val text_temperature_decimal_size = 32.sp

/**
 * 室外温度 - 40sp
 * 用于：首页室外温度
 */
val text_outdoor_temp_size = 40.sp

// ==================== 组件尺寸 ====================

/**
 * 小按钮高度 - 32dp
 */
val button_height_sm = 32.dp

/**
 * 默认按钮高度 - 44dp
 */
val button_height_default = 44.dp

/**
 * 大按钮高度 - 48dp
 */
val button_height_lg = 48.dp

/**
 * 小图标尺寸 - 16dp
 */
val icon_size_sm = 16.dp

/**
 * 默认图标尺寸 - 20dp
 */
val icon_size_default = 20.dp

/**
 * 中等图标尺寸 - 24dp
 */
val icon_size_md = 24.dp

/**
 * 大图标尺寸 - 28dp
 */
val icon_size_lg = 28.dp

/**
 * 特大图标尺寸 - 32dp
 */
val icon_size_xl = 32.dp

/**
 * 超大图标尺寸 - 40dp
 */
val icon_size_2xl = 40.dp

/**
 * 场景图标尺寸 - 48dp
 */
val icon_size_scene = 48.dp

/**
 * 头像小尺寸 - 40dp
 */
val avatar_size_sm = 40.dp

/**
 * 头像默认尺寸 - 56dp
 */
val avatar_size_default = 56.dp

/**
 * 头像大尺寸 - 64dp
 */
val avatar_size_lg = 64.dp

/**
 * 底部导航高度 - 64dp
 */
val bottom_nav_height = 64.dp

/**
 * 底部导航图标尺寸 - 24dp
 */
val bottom_nav_icon_size = 24.dp

/**
 * 顶部导航栏高度 - 56dp
 */
val top_bar_height = 56.dp

/**
 * 开关宽度 - 48dp
 */
val switch_width = 48.dp

/**
 * 开关高度 - 28dp
 */
val switch_height = 28.dp

/**
 * 滑块轨道高度 - 8dp
 */
val slider_track_height = 8.dp

/**
 * 滑块拇指大小 - 24dp
 */
val slider_thumb_size = 24.dp

/**
 * 进度条高度 - 6dp
 */
val progress_bar_height = 6.dp

/**
 * 进度条高度(大) - 8dp
 */
val progress_bar_height_lg = 8.dp

/**
 * 分隔线高度 - 1dp
 */
val divider_height = 1.dp

/**
 * 边框宽度 - 1dp
 */
val border_width = 1.dp

/**
 * 边框宽度(粗) - 2dp
 */
val border_width_thick = 2.dp

// ==================== 卡片尺寸 ====================

/**
 * 卡片内边距 - 小
 */
val card_padding_small = 12.dp

/**
 * 卡片内边距 - 默认
 */
val card_padding_default = 16.dp

/**
 * 卡片内边距 - 大
 */
val card_padding_large = 20.dp

/**
 * 卡片最小高度 - 80dp
 */
val card_min_height = 80.dp

/**
 * 卡片列表项高度 - 72dp
 */
val card_list_item_height = 72.dp

// ==================== 输入框尺寸 ====================

/**
 * 输入框高度 - 48dp
 */
val input_height = 48.dp

/**
 * 输入框内边距 - 水平
 */
val input_padding_horizontal = 16.dp

/**
 * 输入框内边距 - 垂直
 */
val input_padding_vertical = 12.dp

// ==================== Chip/Tag尺寸 ====================

/**
 * Chip高度 - 32dp
 */
val chip_height = 32.dp

/**
 * Chip内边距 - 水平
 */
val chip_padding_horizontal = 16.dp

/**
 * Chip圆角
 */
val chip_corner = 16.dp

/**
 * 楼层标签高度 - 36dp
 */
val floor_tag_height = 36.dp

/**
 * 楼层标签内边距 - 水平
 */
val floor_tag_padding_horizontal = 20.dp

/**
 * 楼层标签圆角
 */
val floor_tag_corner = 18.dp

// ==================== 场景按钮尺寸 ====================

/**
 * 场景按钮宽度 - 72dp
 */
val scene_button_width = 72.dp

/**
 * 场景按钮高度 - 80dp
 */
val scene_button_height = 80.dp

/**
 * 场景按钮圆角 - 16dp
 */
val scene_button_corner = 16.dp

// ==================== 风速档位尺寸 ====================

/**
 * 风速档位圆角
 */
val fan_speed_corner = 8.dp

/**
 * 风速档位文字大小
 */
val fan_speed_text_size = 16.sp

/**
 * 风速按钮高度
 */
val fan_speed_button_height = 36.dp

/**
 * 风速按钮水平内边距
 */
val fan_speed_button_padding_h = 16.dp

/**
 * 风速按钮间距
 */
val fan_speed_gap = 8.dp

// ==================== 房间Chip尺寸 ====================

/**
 * 房间Chip圆角
 */
val room_chip_corner = 18.dp

/**
 * 房间Chip高度
 */
val room_chip_height = 36.dp

/**
 * 房间Chip水平内边距
 */
val room_chip_padding_h = 16.dp

/**
 * 房间Chip文字大小
 */
val room_chip_text_size = 14.sp

/**
 * 房间Chip间距
 */
val room_chip_gap = 8.dp

// ==================== 温度预设按钮尺寸 ====================

/**
 * 温度预设按钮圆角
 */
val temp_preset_corner = 8.dp

/**
 * 温度预设按钮高度
 */
val temp_preset_button_height = 32.dp

/**
 * 温度预设文字大小
 */
val temp_preset_text_size = 14.sp

// ==================== 楼层选择器尺寸 ====================

/**
 * 楼层按钮文字大小
 */
val floor_button_text_size = 16.sp

/**
 * 楼层下拉箭头大小
 */
val floor_dropdown_arrow_size = 24.dp

// ==================== 滑块尺寸 ====================

/**
 * 滑块拇指边框宽度
 */
val slider_thumb_border_width = 2.dp

// ==================== 额外尺寸常量 ====================

/**
 * 超大间距
 */
val spacing_xxl = 32.dp

/**
 * 版本文字大小
 */
val version_text_size = 12.sp

/**
 * 菜单项高度
 */
val menu_item_height = 56.dp

/**
 * 菜单项水平内边距
 */
val menu_item_padding_h = 16.dp

/**
 * 菜单标题大小
 */
val menu_title_size = 16.sp

/**
 * 菜单箭头大小
 */
val menu_arrow_size = 20.dp

/**
 * 菜单分隔线缩进
 */
val menu_divider_indent = 16.dp

/**
 * 滤芯项目名称大小
 */
val filter_item_name_size = 14.sp

/**
 * 滤芯百分比大小
 */
val filter_percentage_size = 14.sp

/**
 * 滤芯状态大小
 */
val filter_status_size = 12.sp

// ==================== 720dp布局适配尺寸 ====================

/**
 * 720dp布局阈值
 */
val layout_720dp_threshold = 720.dp

/**
 * 大屏幕页面边距
 */
val page_margin_horizontal_wide = 24.dp

/**
 * 大屏幕卡片内边距
 */
val card_padding_wide = 24.dp

/**
 * 大屏幕间距
 */
val spacing_wide_default = 20.dp

/**
 * 大屏幕超大间距
 */
val spacing_wide_xl = 32.dp

/**
 * 双列布局间距
 */
val two_column_gap = 16.dp

/**
 * 三列布局间距
 */
val three_column_gap = 12.dp

// ==================== 响应式断点 ====================

/**
 * 紧凑屏幕最大宽度
 */
val breakpoint_compact = 600.dp

/**
 * 中等屏幕最大宽度
 */
val breakpoint_medium = 840.dp

/**
 * 大屏幕最小宽度
 */
val breakpoint_expanded = 1200.dp
