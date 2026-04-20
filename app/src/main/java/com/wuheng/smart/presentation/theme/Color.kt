package com.wuheng.smart.presentation.theme

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// ==================== 品牌主色 ====================
/**
 * 主色调 - 科技蓝
 * 用于：主按钮、选中状态、重要图标、导航激活状态
 * 设计图参考：首页当前住宅卡片背景、Tab选中状态
 */
val PrimaryBlue = Color(0xFF2B9DF0)

/**
 * 主色浅色 - 用于悬停或轻量背景
 */
val PrimaryBlueLight = Color(0xFFE8F5FD)

/**
 * 主色深色 - 用于强调或按下状态
 */
val PrimaryBlueDark = Color(0xFF1A7BC8)

/**
 * 主色渐变起始色 - 用于卡片渐变背景
 */
val PrimaryBlueGradientStart = Color(0xFF4AB8FF)

/**
 * 主色渐变结束色
 */
val PrimaryBlueGradientEnd = Color(0xFF2B9DF0)

// ==================== 辅助色 ====================
/**
 * 辅助色 - 活力橙
 * 用于：热力杀菌、热水循环、警告提示
 * 设计图参考：水系统页面热力杀菌图标
 */
val SecondaryOrange = Color(0xFFFF7A45)

/**
 * 辅助色浅色
 */
val SecondaryOrangeLight = Color(0xFFFFF2EB)

/**
 * 辅助色深色
 */
val SecondaryOrangeDark = Color(0xFFD94E1F)

// ==================== 功能色 ====================
/**
 * 成功/正常 - 绿色
 * 用于：在线状态、正常提示、低CO2/PM2.5
 */
val SuccessGreen = Color(0xFF52C41A)

/**
 * 警告 - 黄色/橙色
 * 用于：中等空气质量、耗材预警
 */
val WarningYellow = Color(0xFFFFA940)

/**
 * 错误/危险 - 红色
 * 用于：离线状态、高污染、需更换提示
 */
val ErrorRed = Color(0xFFFF4D4F)

/**
 * 信息提示 - 蓝色
 */
val InfoBlue = Color(0xFF1890FF)

// ==================== 背景色 ====================
/**
 * 页面背景 - 浅灰蓝
 * 设计图参考：所有页面的背景色
 */
val BackgroundLight = Color(0xFFF0F4F8)

/**
 * 暗色主题页面背景
 */
val BackgroundDark = Color(0xFF121212)

/**
 * 卡片背景 - 纯白
 * 用于：内容卡片、列表项
 */
val SurfaceLight = Color(0xFFFFFFFF)

/**
 * 暗色主题卡片背景
 */
val SurfaceDark = Color(0xFF1E1E1E)

/**
 * 次级表面色 - 用于区分层次
 */
val SurfaceVariantLight = Color(0xFFF5F7FA)

/**
 * 暗色主题次级表面色
 */
val SurfaceVariantDark = Color(0xFF2D2D2D)

// ==================== 文字色 ====================
/**
 * 主要文字 - 深灰黑
 * 用于：标题、重要内容
 */
val TextPrimaryLight = Color(0xFF1A1A2E)

/**
 * 次要文字 - 中灰
 * 用于：副标题、描述文字
 */
val TextSecondaryLight = Color(0xFF666666)

/**
 * 第三级文字 - 浅灰
 * 用于：提示文字、时间戳
 */
val TextTertiaryLight = Color(0xFF999999)

/**
 * 禁用文字颜色
 */
val TextDisabledLight = Color(0xFFCCCCCC)

/**
 * 暗色主题主要文字
 */
val TextPrimaryDark = Color(0xFFFFFFFF)

/**
 * 暗色主题次要文字
 */
val TextSecondaryDark = Color(0xFFB0B0B0)

/**
 * 暗色主题第三级文字
 */
val TextTertiaryDark = Color(0xFF808080)

// ==================== 边框与分隔线 ====================
/**
 * 边框颜色 - 浅灰
 */
val BorderLight = Color(0xFFE8E8E8)

/**
 * 分隔线颜色
 */
val DividerLight = Color(0xFFEEEEEE)

/**
 * 暗色主题边框
 */
val BorderDark = Color(0xFF404040)

/**
 * 暗色主题分隔线
 */
val DividerDark = Color(0xFF333333)

// ==================== 阴影色 ====================
/**
 * 浅色阴影
 */
val ShadowLight = Color(0x1A000000)

/**
 * 暗色阴影
 */
val ShadowDark = Color(0x4D000000)

// ==================== 开关与滑块 ====================
/**
 * 开关选中状态
 */
val SwitchChecked = Color(0xFF2B9DF0)

/**
 * 开关未选中状态
 */
val SwitchUnchecked = Color(0xFFCCCCCC)

/**
 * 滑块激活状态
 */
val SliderActive = Color(0xFF2B9DF0)

/**
 * 滑块未激活状态
 */
val SliderInactive = Color(0xFFE0E0E0)

// ==================== 功能特定颜色 ====================
/**
 * 制冷/降温 - 冷蓝色
 */
val CoolingBlue = Color(0xFF40A9FF)

/**
 * 制热/升温 - 暖橙色
 */
val HeatingOrange = Color(0xFFFF7A45)

/**
 * 通风/新风 - 青绿色
 */
val VentilationTeal = Color(0xFF36CFC9)

/**
 * 水系统 - 水蓝色
 */
val WaterBlue = Color(0xFF69C0FF)

/**
 * 热水 - 暖橙色
 */
val HotWaterOrange = Color(0xFFFF9C6E)

/**
 * 滤芯正常 - 蓝色
 */
val FilterNormal = Color(0xFF2B9DF0)

/**
 * 滤芯需更换 - 橙色
 */
val FilterWarning = Color(0xFFFFA940)

/**
 * 滤芯已过期 - 红色
 */
val FilterExpired = Color(0xFFFF4D4F)

// ==================== 空气质量颜色 ====================
/**
 * 空气质量优
 */
val AirQualityExcellent = Color(0xFF52C41A)

/**
 * 空气质量良
 */
val AirQualityGood = Color(0xFF95DE64)

/**
 * 空气质量轻度污染
 */
val AirQualityModerate = Color(0xFFFFA940)

/**
 * 空气质量中度污染
 */
val AirQualityPoor = Color(0xFFFF7875)

/**
 * 空气质量重度污染
 */
val AirQualityBad = Color(0xFFFF4D4F)

// ==================== 温度值颜色 ====================
/**
 * 温度值显示 - 深色
 */
val TemperatureValueColor = Color(0xFF1A1A2E)

/**
 * CO2值颜色 - 根据数值变化
 */
val Co2ValueColor = Color(0xFF52C41A)

/**
 * PM2.5值颜色
 */
val Pm25ValueColor = Color(0xFF52C41A)

/**
 * VOC值颜色
 */
val VocValueColor = Color(0xFF52C41A)

// ==================== 按钮状态色 ====================
/**
 * 主按钮背景
 */
val ButtonPrimaryBg = Color(0xFF2B9DF0)

/**
 * 主按钮按下状态
 */
val ButtonPrimaryPressed = Color(0xFF1A7BC8)

/**
 * 次按钮背景
 */
val ButtonSecondaryBg = Color(0xFFF5F5F5)

/**
 * 次按钮文字
 */
val ButtonSecondaryText = Color(0xFF666666)

/**
 * 禁用状态背景
 */
val ButtonDisabledBg = Color(0xFFE0E0E0)

/**
 * 禁用状态文字
 */
val ButtonDisabledText = Color(0xFFBFBFBF)

// ==================== Chip/Tag颜色 ====================
/**
 * Chip选中背景
 */
val ChipSelectedBg = Color(0xFFE8F5FD)

/**
 * Chip选中文字
 */
val ChipSelectedText = Color(0xFF2B9DF0)

/**
 * Chip未选中边框
 */
val ChipUnselectedBorder = Color(0xFFE0E0E0)

/**
 * Chip未选中文字
 */
val ChipUnselectedText = Color(0xFF666666)

// ==================== 进度条颜色 ====================
/**
 * 进度条轨道背景
 */
val ProgressTrackBg = Color(0xFFE8E8E8)

/**
 * 进度条填充色 - 正常
 */
val ProgressFillNormal = Color(0xFF2B9DF0)

/**
 * 进度条填充色 - 警告
 */
val ProgressFillWarning = Color(0xFFFFA940)

/**
 * 进度条填充色 - 危险
 */
val ProgressFillDanger = Color(0xFFFF4D4F)

// ==================== 风速档位颜色 ====================
/**
 * 风速选中背景
 */
val FanSpeedSelectedBg = Color(0xFF2B9DF0)

/**
 * 风速选中文字
 */
val FanSpeedSelectedText = Color(0xFFFFFFFF)

/**
 * 风速未选中背景
 */
val FanSpeedUnselectedBg = Color(0xFFF5F5F5)

/**
 * 风速未选中文字
 */
val FanSpeedUnselectedText = Color(0xFF666666)

// ==================== 底部导航颜色 ====================
/**
 * 底部导航选中
 */
val NavSelectedColor = Color(0xFF2B9DF0)

/**
 * 底部导航未选中
 */
val NavUnselectedColor = Color(0xFF999999)

/**
 * 底部导航背景
 */
val NavBackgroundColor = Color(0xFFFFFFFF)

// ==================== 场景模式颜色 ====================
/**
 * 会客模式
 */
val SceneMeeting = Color(0xFF2B9DF0)

/**
 * 离家模式
 */
val SceneAway = Color(0xFF8C8C8C)

/**
 * 睡眠模式
 */
val SceneSleep = Color(0xFF722ED1)

/**
 * 值守模式
 */
val SceneGuard = Color(0xFF36CFC9)

// ==================== 楼层/区域标签颜色 ====================

/**
 * 楼层标签选中背景
 */
val FloorTagSelectedBg = Color(0xFF2B9DF0)

/**
 * 楼层标签选中文字
 */
val FloorTagSelectedText = Color(0xFFFFFFFF)

/**
 * 楼层标签未选中背景
 */
val FloorTagUnselectedBg = Color(0xFFF5F5F5)

/**
 * 楼层标签未选中文字
 */
val FloorTagUnselectedText = Color(0xFF666666)

/**
 * Tab选中背景
 */
val TabSelectedBackground = Color(0xFF0EA5E9)

/**
 * Tab选中文字
 */
val TabSelectedText = Color(0xFFFFFFFF)

/**
 * 楼层下拉箭头颜色
 */
val FloorDropdownArrowColor = Color(0xFF64748B)

/**
 * 温度预设标签选中颜色
 */
val TempPresetLabelSelected = Color(0xFF2B9DF0)

/**
 * 温度预设标签正常颜色
 */
val TempPresetLabelNormal = Color(0xFF666666)

/**
 * 温度单位颜色
 */
val TemperatureUnitColor = Color(0xFF666666)

/**
 * 湿度标题颜色
 */
val HumidityTitleColor = Color(0xFF333333)

/**
 * 湿度值颜色
 */
val HumidityValueColor = Color(0xFF2B9DF0)

/**
 * 辐射模式开启标签颜色
 */
val RadiationModeLabelOn = Color(0xFF2B9DF0)

/**
 * 辐射模式关闭标签颜色
 */
val RadiationModeLabelOff = Color(0xFF999999)

/**
 * 滑块拇指边框激活颜色
 */
val SliderThumbBorderActive = Color(0xFF2B9DF0)

/**
 * 滑块拇指颜色
 */
val SliderThumb = Color(0xFFFFFFFF)

/**
 * Chip未选中背景
 */
val ChipUnselectedBg = Color(0xFFFFFFFF)

/**
 * 卡片内边距
 */
val card_padding = 16.dp

// ==================== 输入框颜色 ====================
/**
 * 输入框背景
 */
val InputBackground = Color(0xFFF5F7FA)

/**
 * 输入框边框
 */
val InputBorder = Color(0xFFE8E8E8)

/**
 * 输入框聚焦边框
 */
val InputBorderFocused = Color(0xFF2B9DF0)

/**
 * 输入框提示文字
 */
val InputHint = Color(0xFFBBBBBB)

// ==================== 特殊效果颜色 ====================
/**
 * 渐变背景起始色 - 首页顶部
 */
val HomeGradientStart = Color(0xFFE8F5FD)

/**
 * 渐变背景结束色
 */
val HomeGradientEnd = Color(0xFFF0F4F8)

/**
 * 卡片光晕效果
 */
val CardGlow = Color(0x1A2B9DF0)

/**
 * 玻璃态效果背景
 */
val GlassBackground = Color(0x80FFFFFF)

// ==================== 玻璃面板颜色 ====================
/**
 * 玻璃面板背景
 */
val GlassPanelBg = Color(0x80FFFFFF)

/**
 * 玻璃导航背景
 */
val GlassNavBg = Color(0xFFFFFFFF)

/**
 * 导航指示器颜色（透明）
 */
val NavIndicatorColor = Color.Transparent

/**
 * 场景图标背景
 */
val SceneIconBg = Color(0x1A2B9DF0)

// ==================== 模式按钮尺寸常量 ====================
/**
 * 模式组水平内边距
 */
val mode_group_padding_h = 16.dp

/**
 * 模式按钮间距
 */
val mode_button_gap = 8.dp

/**
 * 模式按钮水平内边距
 */
val mode_button_padding_h = 12.dp

/**
 * 模式按钮高度
 */
val mode_button_height = 44.dp

/**
 * 模式按钮图标与文字间距
 */
val mode_button_icon_text_spacing = 4.dp

/**
 * 模式按钮图标尺寸
 */
val mode_button_icon_size = 20.dp

/**
 * 模式按钮文字大小
 */
val mode_button_text_size = 14.sp

// ==================== About页面颜色常量 ====================
/**
 * 返回箭头颜色
 */
val BackArrowColor = Color(0xFF666666)

/**
 * 菜单项标题颜色
 */
val MenuItemTitleColor = Color(0xFF333333)

/**
 * 右箭头颜色
 */
val ChevronRightColor = Color(0xFF999999)

/**
 * 滤芯项目名称颜色
 */
val FilterItemNameColor = Color(0xFF333333)

/**
 * 滤芯百分比颜色
 */
val FilterPercentageColor = Color(0xFF2B9DF0)

/**
 * 滤芯状态标签-正常
 */
val FilterStatusLabelNormal = Color(0xFF52C41A)

/**
 * 滤芯状态标签-警告
 */
val FilterStatusLabelWarning = Color(0xFFFFA940)


