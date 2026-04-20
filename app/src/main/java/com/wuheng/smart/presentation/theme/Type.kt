package com.wuheng.smart.presentation.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

// ==================== 字体家族定义 ====================

/**
 * 主字体家族 - 使用系统默认字体
 * 用于：正文、标题、UI元素
 */
val NotoSansSC = FontFamily.Default

/**
 * 数字字体家族 - 使用系统等宽字体
 * 用于：温度、湿度等数值显示
 */
val RobotoMono = FontFamily.Monospace

// ==================== Material 3 Typography ====================

/**
 * 应用字体排版规范
 * 基于Material 3 Typography规范，适配五恒系统设计
 */
val AppTypography = Typography(
    // 显示大字体 - 用于首页大数字
    displayLarge = TextStyle(
        fontFamily = NotoSansSC,
        fontWeight = FontWeight.Bold,
        fontSize = 48.sp,
        lineHeight = 56.sp,
        letterSpacing = (-0.5).sp
    ),
    displayMedium = TextStyle(
        fontFamily = NotoSansSC,
        fontWeight = FontWeight.Bold,
        fontSize = 40.sp,
        lineHeight = 48.sp,
        letterSpacing = (-0.5).sp
    ),
    displaySmall = TextStyle(
        fontFamily = NotoSansSC,
        fontWeight = FontWeight.Bold,
        fontSize = 32.sp,
        lineHeight = 40.sp,
        letterSpacing = 0.sp
    ),

    // 大标题
    headlineLarge = TextStyle(
        fontFamily = NotoSansSC,
        fontWeight = FontWeight.Bold,
        fontSize = 28.sp,
        lineHeight = 36.sp,
        letterSpacing = 0.sp
    ),
    headlineMedium = TextStyle(
        fontFamily = NotoSansSC,
        fontWeight = FontWeight.SemiBold,
        fontSize = 24.sp,
        lineHeight = 32.sp,
        letterSpacing = 0.sp
    ),
    headlineSmall = TextStyle(
        fontFamily = NotoSansSC,
        fontWeight = FontWeight.SemiBold,
        fontSize = 20.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),

    // 标题
    titleLarge = TextStyle(
        fontFamily = NotoSansSC,
        fontWeight = FontWeight.SemiBold,
        fontSize = 20.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    titleMedium = TextStyle(
        fontFamily = NotoSansSC,
        fontWeight = FontWeight.Medium,
        fontSize = 18.sp,
        lineHeight = 26.sp,
        letterSpacing = 0.sp
    ),
    titleSmall = TextStyle(
        fontFamily = NotoSansSC,
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.sp
    ),

    // 正文
    bodyLarge = TextStyle(
        fontFamily = NotoSansSC,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.sp
    ),
    bodyMedium = TextStyle(
        fontFamily = NotoSansSC,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.sp
    ),
    bodySmall = TextStyle(
        fontFamily = NotoSansSC,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.sp
    ),

    // 标签/说明文字
    labelLarge = TextStyle(
        fontFamily = NotoSansSC,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.sp
    ),
    labelMedium = TextStyle(
        fontFamily = NotoSansSC,
        fontWeight = FontWeight.Medium,
        fontSize = 12.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.sp
    ),
    labelSmall = TextStyle(
        fontFamily = NotoSansSC,
        fontWeight = FontWeight.Medium,
        fontSize = 10.sp,
        lineHeight = 14.sp,
        letterSpacing = 0.sp
    )
)

// ==================== 扩展文字样式 ====================

/**
 * 温度显示样式 - 大数字
 */
val TemperatureDisplayStyle = TextStyle(
    fontFamily = RobotoMono,
    fontWeight = FontWeight.Medium,
    fontSize = 48.sp,
    lineHeight = 56.sp,
    letterSpacing = (-1).sp
)

/**
 * 温度小数部分样式
 */
val TemperatureDecimalStyle = TextStyle(
    fontFamily = RobotoMono,
    fontWeight = FontWeight.Medium,
    fontSize = 32.sp,
    lineHeight = 40.sp,
    letterSpacing = 0.sp
)

/**
 * 室外温度样式
 */
val OutdoorTempStyle = TextStyle(
    fontFamily = RobotoMono,
    fontWeight = FontWeight.Medium,
    fontSize = 40.sp,
    lineHeight = 48.sp,
    letterSpacing = (-0.5).sp
)

/**
 * 环境数值样式 - 用于CO2、PM2.5等
 */
val EnvironmentValueStyle = TextStyle(
    fontFamily = RobotoMono,
    fontWeight = FontWeight.Medium,
    fontSize = 24.sp,
    lineHeight = 32.sp,
    letterSpacing = 0.sp
)

/**
 * 卡片标题样式
 */
val CardTitleStyle = TextStyle(
    fontFamily = NotoSansSC,
    fontWeight = FontWeight.SemiBold,
    fontSize = 18.sp,
    lineHeight = 26.sp,
    letterSpacing = 0.sp
)

/**
 * 卡片副标题样式
 */
val CardSubtitleStyle = TextStyle(
    fontFamily = NotoSansSC,
    fontWeight = FontWeight.Normal,
    fontSize = 14.sp,
    lineHeight = 20.sp,
    letterSpacing = 0.sp,
    color = TextSecondaryLight
)

/**
 * 按钮文字样式
 */
val ButtonTextStyle = TextStyle(
    fontFamily = NotoSansSC,
    fontWeight = FontWeight.Medium,
    fontSize = 16.sp,
    lineHeight = 24.sp,
    letterSpacing = 0.sp
)

/**
 * 小按钮文字样式
 */
val ButtonSmallTextStyle = TextStyle(
    fontFamily = NotoSansSC,
    fontWeight = FontWeight.Medium,
    fontSize = 14.sp,
    lineHeight = 20.sp,
    letterSpacing = 0.sp
)

/**
 * 标签文字样式
 */
val TagTextStyle = TextStyle(
    fontFamily = NotoSansSC,
    fontWeight = FontWeight.Medium,
    fontSize = 13.sp,
    lineHeight = 18.sp,
    letterSpacing = 0.sp
)

/**
 * 导航标签样式
 */
val NavLabelStyle = TextStyle(
    fontFamily = NotoSansSC,
    fontWeight = FontWeight.Medium,
    fontSize = 12.sp,
    lineHeight = 16.sp,
    letterSpacing = 0.sp
)

/**
 * 场景名称样式
 */
val SceneNameStyle = TextStyle(
    fontFamily = NotoSansSC,
    fontWeight = FontWeight.Medium,
    fontSize = 13.sp,
    lineHeight = 18.sp,
    letterSpacing = 0.sp
)

/**
 * 状态文字样式
 */
val StatusTextStyle = TextStyle(
    fontFamily = NotoSansSC,
    fontWeight = FontWeight.Medium,
    fontSize = 14.sp,
    lineHeight = 20.sp,
    letterSpacing = 0.sp
)

/**
 * 小标签样式 - 用于"CURRENT RESIDENCE"等
 */
val SmallLabelStyle = TextStyle(
    fontFamily = NotoSansSC,
    fontWeight = FontWeight.Medium,
    fontSize = 11.sp,
    lineHeight = 16.sp,
    letterSpacing = 0.5.sp
)

/**
 * 单位文字样式 - 用于°C、%等单位
 */
val UnitTextStyle = TextStyle(
    fontFamily = NotoSansSC,
    fontWeight = FontWeight.Normal,
    fontSize = 16.sp,
    lineHeight = 24.sp,
    letterSpacing = 0.sp,
    color = TextSecondaryLight
)

/**
 * 大单位文字样式
 */
val UnitLargeTextStyle = TextStyle(
    fontFamily = NotoSansSC,
    fontWeight = FontWeight.Normal,
    fontSize = 20.sp,
    lineHeight = 28.sp,
    letterSpacing = 0.sp,
    color = TextSecondaryLight
)

/**
 * 列表项标题样式
 */
val ListItemTitleStyle = TextStyle(
    fontFamily = NotoSansSC,
    fontWeight = FontWeight.Medium,
    fontSize = 16.sp,
    lineHeight = 24.sp,
    letterSpacing = 0.sp
)

/**
 * 列表项描述样式
 */
val ListItemDescStyle = TextStyle(
    fontFamily = NotoSansSC,
    fontWeight = FontWeight.Normal,
    fontSize = 13.sp,
    lineHeight = 18.sp,
    letterSpacing = 0.sp,
    color = TextTertiaryLight
)

/**
 * 提示文字样式
 */
val HintTextStyle = TextStyle(
    fontFamily = NotoSansSC,
    fontWeight = FontWeight.Normal,
    fontSize = 12.sp,
    lineHeight = 16.sp,
    letterSpacing = 0.sp,
    color = TextTertiaryLight
)

/**
 * 输入框文字样式
 */
val InputTextStyle = TextStyle(
    fontFamily = NotoSansSC,
    fontWeight = FontWeight.Normal,
    fontSize = 16.sp,
    lineHeight = 24.sp,
    letterSpacing = 0.sp
)

/**
 * 输入框提示样式
 */
val InputHintStyle = TextStyle(
    fontFamily = NotoSansSC,
    fontWeight = FontWeight.Normal,
    fontSize = 16.sp,
    lineHeight = 24.sp,
    letterSpacing = 0.sp,
    color = TextTertiaryLight
)

/**
 * 进度百分比样式
 */
val ProgressPercentStyle = TextStyle(
    fontFamily = RobotoMono,
    fontWeight = FontWeight.Medium,
    fontSize = 16.sp,
    lineHeight = 24.sp,
    letterSpacing = 0.sp
)

/**
 * 页脚文字样式
 */
val FooterTextStyle = TextStyle(
    fontFamily = NotoSansSC,
    fontWeight = FontWeight.Normal,
    fontSize = 12.sp,
    lineHeight = 16.sp,
    letterSpacing = 0.sp,
    color = TextTertiaryLight
)

/**
 * 版本号样式
 */
val VersionTextStyle = TextStyle(
    fontFamily = RobotoMono,
    fontWeight = FontWeight.Normal,
    fontSize = 12.sp,
    lineHeight = 16.sp,
    letterSpacing = 0.sp,
    color = TextTertiaryLight
)
