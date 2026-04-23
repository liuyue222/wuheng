package com.wuheng.smart.presentation.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

// ==================== 亮色主题颜色方案 ====================
private val LightColorScheme = lightColorScheme(
    // 主色
    primary = PrimaryBlue,
    onPrimary = Color.White,
    primaryContainer = PrimaryBlueLight,
    onPrimaryContainer = PrimaryBlueDark,

    // 辅助色
    secondary = SecondaryOrange,
    onSecondary = Color.White,
    secondaryContainer = SecondaryOrangeLight,
    onSecondaryContainer = SecondaryOrangeDark,

    // 第三色
    tertiary = SuccessGreen,
    onTertiary = Color.White,
    tertiaryContainer = SuccessGreen.copy(alpha = 0.2f),
    onTertiaryContainer = SuccessGreen,

    // 错误色
    error = ErrorRed,
    onError = Color.White,
    errorContainer = ErrorRed.copy(alpha = 0.2f),
    onErrorContainer = ErrorRed,

    // 背景色
    background = BackgroundLight,
    onBackground = TextPrimaryLight,

    // 表面色
    surface = SurfaceLight,
    onSurface = TextPrimaryLight,
    surfaceVariant = SurfaceVariantLight,
    onSurfaceVariant = TextSecondaryLight,

    // 轮廓
    outline = BorderLight,
    outlineVariant = DividerLight,

    // 反色
    inverseSurface = SurfaceDark,
    inverseOnSurface = TextPrimaryDark,
    inversePrimary = PrimaryBlueLight,

    // 表面色调
    surfaceTint = PrimaryBlue
)

// ==================== 暗色主题颜色方案 ====================
private val DarkColorScheme = darkColorScheme(
    // 主色
    primary = PrimaryBlueLight,
    onPrimary = Color.Black,
    primaryContainer = PrimaryBlueDark,
    onPrimaryContainer = PrimaryBlueLight,

    // 辅助色
    secondary = SecondaryOrangeLight,
    onSecondary = Color.Black,
    secondaryContainer = SecondaryOrangeDark,
    onSecondaryContainer = SecondaryOrangeLight,

    // 第三色
    tertiary = SuccessGreen,
    onTertiary = Color.Black,
    tertiaryContainer = SuccessGreen.copy(alpha = 0.3f),
    onTertiaryContainer = SuccessGreen,

    // 错误色
    error = ErrorRed,
    onError = Color.Black,
    errorContainer = ErrorRed.copy(alpha = 0.3f),
    onErrorContainer = ErrorRed,

    // 背景色
    background = BackgroundDark,
    onBackground = TextPrimaryDark,

    // 表面色
    surface = SurfaceDark,
    onSurface = TextPrimaryDark,
    surfaceVariant = SurfaceVariantDark,
    onSurfaceVariant = TextSecondaryDark,

    // 轮廓
    outline = BorderDark,
    outlineVariant = DividerDark,

    // 反色
    inverseSurface = SurfaceLight,
    inverseOnSurface = TextPrimaryLight,
    inversePrimary = PrimaryBlueDark,

    // 表面色调
    surfaceTint = PrimaryBlueLight
)

// ==================== 主题入口 ====================
@Composable
fun WuHengTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    useSystemTheme: Boolean = true,
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = false, // 默认关闭动态颜色，使用品牌色
    content: @Composable () -> Unit
) {
    // 根据用户设置决定是否使用系统主题
    val isDarkTheme = if (useSystemTheme) {
        isSystemInDarkTheme()
    } else {
        darkTheme
    }

    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (isDarkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        isDarkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.background.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !isDarkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = AppTypography,
        shapes = AppShapes,
        content = content
    )
}

// ==================== 扩展主题属性 ====================
object WuHengTheme {
    val colors: AppColors
        @Composable
        get() = if (isSystemInDarkTheme()) DarkAppColors else LightAppColors
}

// ==================== 自定义颜色扩展 ====================
data class AppColors(
    val primaryBlue: Color,
    val primaryBlueLight: Color,
    val primaryBlueDark: Color,
    val secondaryOrange: Color,
    val secondaryOrangeLight: Color,
    val secondaryOrangeDark: Color,
    val success: Color,
    val warning: Color,
    val error: Color,
    val info: Color,
    val cooling: Color,
    val heating: Color,
    val ventilation: Color,
    val water: Color,
    val hotWater: Color,
    val filterNormal: Color,
    val filterWarning: Color,
    val airQualityExcellent: Color,
    val airQualityGood: Color,
    val airQualityModerate: Color,
    val airQualityPoor: Color,
    val airQualityBad: Color,
    val switchChecked: Color,
    val switchUnchecked: Color,
    val sliderActive: Color,
    val sliderInactive: Color,
    val shadow: Color
)

private val LightAppColors = AppColors(
    primaryBlue = PrimaryBlue,
    primaryBlueLight = PrimaryBlueLight,
    primaryBlueDark = PrimaryBlueDark,
    secondaryOrange = SecondaryOrange,
    secondaryOrangeLight = SecondaryOrangeLight,
    secondaryOrangeDark = SecondaryOrangeDark,
    success = SuccessGreen,
    warning = WarningYellow,
    error = ErrorRed,
    info = InfoBlue,
    cooling = CoolingBlue,
    heating = HeatingOrange,
    ventilation = VentilationTeal,
    water = WaterBlue,
    hotWater = HotWaterOrange,
    filterNormal = FilterNormal,
    filterWarning = FilterWarning,
    airQualityExcellent = AirQualityExcellent,
    airQualityGood = AirQualityGood,
    airQualityModerate = AirQualityModerate,
    airQualityPoor = AirQualityPoor,
    airQualityBad = AirQualityBad,
    switchChecked = SwitchChecked,
    switchUnchecked = SwitchUnchecked,
    sliderActive = SliderActive,
    sliderInactive = SliderInactive,
    shadow = ShadowLight
)

private val DarkAppColors = AppColors(
    primaryBlue = PrimaryBlueLight,
    primaryBlueLight = PrimaryBlue,
    primaryBlueDark = PrimaryBlueDark,
    secondaryOrange = SecondaryOrangeLight,
    secondaryOrangeLight = SecondaryOrange,
    secondaryOrangeDark = SecondaryOrangeDark,
    success = SuccessGreen,
    warning = WarningYellow,
    error = ErrorRed,
    info = InfoBlue,
    cooling = CoolingBlue,
    heating = HeatingOrange,
    ventilation = VentilationTeal,
    water = WaterBlue,
    hotWater = HotWaterOrange,
    filterNormal = FilterNormal,
    filterWarning = FilterWarning,
    airQualityExcellent = AirQualityExcellent,
    airQualityGood = AirQualityGood,
    airQualityModerate = AirQualityModerate,
    airQualityPoor = AirQualityPoor,
    airQualityBad = AirQualityBad,
    switchChecked = PrimaryBlueLight,
    switchUnchecked = Color(0xFF666666),
    sliderActive = PrimaryBlueLight,
    sliderInactive = Color(0xFF424242),
    shadow = ShadowDark
)
