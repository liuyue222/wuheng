package com.wuheng.smart.presentation.theme

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

/**
 * 五恒系统专用图标集 - 自定义矢量图标库
 *
 * 包含所有UI界面使用的自定义图标：
 * - 底部导航栏图标 (Home/Climate/Water/Profile)
 * - 功能操作图标 (Menu/Close/Settings/Plus/Minus)
 * - 导航箭头 (ChevronRight/ChevronLeft)
 * - 设备状态图标 (Power/Fan/Flame/Shield/FreshAir/Droplet)
 * - 环境显示图标 (TemperatureHigh/TemperatureLow/Sun/Moon/Cloud)
 * - 状态指示图标 (CheckCircle)
 * - 新增：导航和选择器图标 (Home/List/ChevronDown)
 *
 * 使用示例：
 * ```kotlin
 * Icon(
 *     imageVector = IconsWuHeng.Climate,
 *     contentDescription = "冷暖舒适"
 * )
 * ```
 */
object IconsWuHeng {

    // ==================== 底部导航栏图标 ====================

    /**
     * 冷暖舒适图标 - 用于底部导航栏"冷暖舒适"Tab
     */
    val Climate: ImageVector = ImageVector.Builder(
        name = "Climate",
        defaultWidth = 24.dp,
        defaultHeight = 24.dp,
        viewportWidth = 24f,
        viewportHeight = 24f
    ).apply {
        path(
            fill = SolidColor(Color.Transparent),
            stroke = SolidColor(Color.White),
            strokeLineWidth = 2f,
            strokeLineCap = StrokeCap.Round,
            strokeLineJoin = StrokeJoin.Round
        ) {
            moveTo(14f, 14.76f)
            verticalLineToRelative(3.64f)
            arcToRelative(2f, 2f, 0f, isMoreThanHalf = false, isPositiveArc = false, -2f, -2f)
            verticalLineToRelative(-3.64f)
        }
        path(
            fill = SolidColor(Color.Transparent),
            stroke = SolidColor(Color.White),
            strokeLineWidth = 2f,
            strokeLineCap = StrokeCap.Round,
            strokeLineJoin = StrokeJoin.Round
        ) {
            moveTo(18f, 8f)
            arcToRelative(6f, 6f, 0f, isMoreThanHalf = true, isPositiveArc = true, -12f, 0f)
            verticalLineTo(14f)
            horizontalLineTo(18f)
            close()
        }
    }.build()

    /**
     * 水系统图标 - 用于底部导航栏"水系统"Tab
     */
    val Water: ImageVector = ImageVector.Builder(
        name = "Water",
        defaultWidth = 24.dp,
        defaultHeight = 24.dp,
        viewportWidth = 24f,
        viewportHeight = 24f
    ).apply {
        path(
            fill = SolidColor(Color.Transparent),
            stroke = SolidColor(Color.White),
            strokeLineWidth = 2f,
            strokeLineCap = StrokeCap.Round,
            strokeLineJoin = StrokeJoin.Round
        ) {
            moveTo(12f, 2.69f)
            lineToRelative(5.66f, 5.66f)
            arcToRelative(8f, 8f, 0f, isMoreThanHalf = true, isPositiveArc = true, -11.31f, 0f)
            close()
        }
    }.build()

    /**
     * 我的页面图标 - 用于底部导航栏"我的"Tab
     */
    val Profile: ImageVector = ImageVector.Builder(
        name = "Profile",
        defaultWidth = 24.dp,
        defaultHeight = 24.dp,
        viewportWidth = 24f,
        viewportHeight = 24f
    ).apply {
        path(
            fill = SolidColor(Color.Transparent),
            stroke = SolidColor(Color.White),
            strokeLineWidth = 2f,
            strokeLineCap = StrokeCap.Round,
            strokeLineJoin = StrokeJoin.Round
        ) {
            moveTo(20f, 21f)
            verticalLineTo(19f)
            arcToRelative(2f, 2f, 0f, isMoreThanHalf = false, isPositiveArc = false, -2f, -2f)
            horizontalLineTo(8f)
            arcToRelative(2f, 2f, 0f, isMoreThanHalf = false, isPositiveArc = false, -2f, 2f)
            verticalLineTo(21f)
        }
        path(
            fill = SolidColor(Color.Transparent),
            stroke = SolidColor(Color.White),
            strokeLineWidth = 2f,
            strokeLineCap = StrokeCap.Round,
            strokeLineJoin = StrokeJoin.Round
        ) {
            moveTo(16f, 11f)
            arcToRelative(4f, 4f, 0f, isMoreThanHalf = true, isPositiveArc = false, -8f, 0f)
            arcToRelative(4f, 4f, 0f, isMoreThanHalf = true, isPositiveArc = false, 8f, 0f)
        }
    }.build()

    // ==================== 功能操作图标 ====================

    /**
     * 菜单图标 - 用于侧边菜单或更多选项
     */
    val Menu: ImageVector = ImageVector.Builder(
        name = "Menu",
        defaultWidth = 24.dp,
        defaultHeight = 24.dp,
        viewportWidth = 24f,
        viewportHeight = 24f
    ).apply {
        path(
            fill = SolidColor(Color.Transparent),
            stroke = SolidColor(Color.White),
            strokeLineWidth = 2f,
            strokeLineCap = StrokeCap.Round,
            strokeLineJoin = StrokeJoin.Round
        ) {
            moveTo(3f, 12f)
            horizontalLineTo(21f)
            moveTo(3f, 6f)
            horizontalLineTo(21f)
            moveTo(3f, 18f)
            horizontalLineTo(21f)
        }
    }.build()

    /**
     * 关闭图标 - 用于关闭对话框或弹窗
     */
    val Close: ImageVector = ImageVector.Builder(
        name = "Close",
        defaultWidth = 24.dp,
        defaultHeight = 24.dp,
        viewportWidth = 24f,
        viewportHeight = 24f
    ).apply {
        path(
            fill = SolidColor(Color.Transparent),
            stroke = SolidColor(Color.White),
            strokeLineWidth = 2f,
            strokeLineCap = StrokeCap.Round,
            strokeLineJoin = StrokeJoin.Round
        ) {
            moveTo(18f, 6f)
            lineTo(6f, 18f)
            moveTo(6f, 6f)
            lineTo(18f, 18f)
        }
    }.build()

    /**
     * 设置图标 - 用于进入设置页面
     */
    val Settings: ImageVector = ImageVector.Builder(
        name = "Settings",
        defaultWidth = 24.dp,
        defaultHeight = 24.dp,
        viewportWidth = 24f,
        viewportHeight = 24f
    ).apply {
        path(
            fill = SolidColor(Color.Transparent),
            stroke = SolidColor(Color.White),
            strokeLineWidth = 2f,
            strokeLineCap = StrokeCap.Round,
            strokeLineJoin = StrokeJoin.Round
        ) {
            moveTo(12.22f, 2f)
            lineToRelative(-0.44f, 0f)
            lineToRelative(-2f, 3.5f)
            arcToRelative(10f, 10f, 0f, isMoreThanHalf = false, isPositiveArc = false, -7.78f, 9f)
            lineToRelative(0f, 0f)
            arcToRelative(10f, 10f, 0f, isMoreThanHalf = false, isPositiveArc = false, 7.78f, 9f)
            lineToRelative(2f, 0f)
            arcToRelative(10f, 10f, 0f, isMoreThanHalf = false, isPositiveArc = false, 7.78f, -9f)
            lineToRelative(0f, 0f)
            arcToRelative(10f, 10f, 0f, isMoreThanHalf = false, isPositiveArc = false, -4.56f, -3.5f)
            close()
        }
    }.build()

    /**
     * 加号图标 - 用于添加新项目或增加数值
     */
    val Plus: ImageVector = ImageVector.Builder(
        name = "Plus",
        defaultWidth = 24.dp,
        defaultHeight = 24.dp,
        viewportWidth = 24f,
        viewportHeight = 24f
    ).apply {
        path(
            fill = SolidColor(Color.Transparent),
            stroke = SolidColor(Color.White),
            strokeLineWidth = 2f,
            strokeLineCap = StrokeCap.Round,
            strokeLineJoin = StrokeJoin.Round
        ) {
            moveTo(12f, 5f)
            verticalLineTo(19f)
            moveTo(5f, 12f)
            horizontalLineTo(19f)
        }
    }.build()

    /**
     * 减号图标 - 用于减少数值
     */
    val Minus: ImageVector = ImageVector.Builder(
        name = "Minus",
        defaultWidth = 24.dp,
        defaultHeight = 24.dp,
        viewportWidth = 24f,
        viewportHeight = 24f
    ).apply {
        path(
            fill = SolidColor(Color.Transparent),
            stroke = SolidColor(Color.White),
            strokeLineWidth = 2f,
            strokeLineCap = StrokeCap.Round,
            strokeLineJoin = StrokeJoin.Round
        ) {
            moveTo(5f, 12f)
            horizontalLineTo(19f)
        }
    }.build()

    // ==================== 导航箭头图标 ====================

    /**
     * 右箭头图标 - 用于导航到下一级页面
     */
    val ChevronRight: ImageVector = ImageVector.Builder(
        name = "ChevronRight",
        defaultWidth = 24.dp,
        defaultHeight = 24.dp,
        viewportWidth = 24f,
        viewportHeight = 24f
    ).apply {
        path(
            fill = SolidColor(Color.Transparent),
            stroke = SolidColor(Color.White),
            strokeLineWidth = 2f,
            strokeLineCap = StrokeCap.Round,
            strokeLineJoin = StrokeJoin.Round
        ) {
            moveTo(9f, 18f)
            lineTo(15f, 12f)
            lineTo(9f, 6f)
        }
    }.build()

    /**
     * 左箭头图标 - 用于返回上一级页面
     */
    val ChevronLeft: ImageVector = ImageVector.Builder(
        name = "ChevronLeft",
        defaultWidth = 24.dp,
        defaultHeight = 24.dp,
        viewportWidth = 24f,
        viewportHeight = 24f
    ).apply {
        path(
            fill = SolidColor(Color.Transparent),
            stroke = SolidColor(Color.White),
            strokeLineWidth = 2f,
            strokeLineCap = StrokeCap.Round,
            strokeLineJoin = StrokeJoin.Round
        ) {
            moveTo(15f, 18f)
            lineTo(9f, 12f)
            lineTo(15f, 6f)
        }
    }.build()

    // ==================== 设备状态图标 ====================

    /**
     * 电源图标 - 用于设备开关控制
     */
    val Power: ImageVector = ImageVector.Builder(
        name = "Power",
        defaultWidth = 24.dp,
        defaultHeight = 24.dp,
        viewportWidth = 24f,
        viewportHeight = 24f
    ).apply {
        path(
            fill = SolidColor(Color.Transparent),
            stroke = SolidColor(Color.White),
            strokeLineWidth = 2f,
            strokeLineCap = StrokeCap.Round,
            strokeLineJoin = StrokeJoin.Round
        ) {
            moveTo(18.36f, 6.64f)
            arcToRelative(9f, 9f, 0f, isMoreThanHalf = true, isPositiveArc = true, -12.73f, 0f)
            moveTo(12f, 2f)
            verticalLineTo(12f)
        }
    }.build()

    /**
     * 风扇图标 - 用于风速控制
     */
    val Fan: ImageVector = ImageVector.Builder(
        name = "Fan",
        defaultWidth = 24.dp,
        defaultHeight = 24.dp,
        viewportWidth = 24f,
        viewportHeight = 24f
    ).apply {
        path(
            fill = SolidColor(Color.Transparent),
            stroke = SolidColor(Color.White),
            strokeLineWidth = 2f,
            strokeLineCap = StrokeCap.Round,
            strokeLineJoin = StrokeJoin.Round
        ) {
            moveTo(12f, 12f)
            moveTo(12f, 4.5f)
            curveToRelative(3f, 3f, -3f, 7.5f, 0f, 7.5f)
            moveTo(19.5f, 12f)
            curveToRelative(-3f, 3f, -7.5f, -3f, -7.5f, 0f)
            moveTo(12f, 19.5f)
            curveToRelative(-3f, -3f, 3f, -7.5f, 0f, -7.5f)
            moveTo(4.5f, 12f)
            curveToRelative(3f, -3f, 7.5f, 3f, 7.5f, 0f)
        }
    }.build()

    /**
     * 火焰图标 - 用于供暖模式或温度加热
     */
    val Flame: ImageVector = ImageVector.Builder(
        name = "Flame",
        defaultWidth = 24.dp,
        defaultHeight = 24.dp,
        viewportWidth = 24f,
        viewportHeight = 24f
    ).apply {
        path(
            fill = SolidColor(Color.Transparent),
            stroke = SolidColor(Color.White),
            strokeLineWidth = 2f,
            strokeLineCap = StrokeCap.Round,
            strokeLineJoin = StrokeJoin.Round
        ) {
            moveTo(8.5f, 14.5f)
            arcToRelative(2.5f, 2.5f, 0f, isMoreThanHalf = true, isPositiveArc = true, 5f, 0f)
            close()
            moveTo(12f, 12f)
            arcToRelative(6f, 6f, 0f, isMoreThanHalf = false, isPositiveArc = false, -9f, 9f)
            lineToRelative(3f, 3f)
            arcToRelative(3f, 3f, 0f, isMoreThanHalf = false, isPositiveArc = false, 6f, 0f)
            close()
        }
    }.build()

    /**
     * 盾牌图标 - 用于安全保护功能（如热力杀菌）
     */
    val Shield: ImageVector = ImageVector.Builder(
        name = "Shield",
        defaultWidth = 24.dp,
        defaultHeight = 24.dp,
        viewportWidth = 24f,
        viewportHeight = 24f
    ).apply {
        path(
            fill = SolidColor(Color.Transparent),
            stroke = SolidColor(Color.White),
            strokeLineWidth = 2f,
            strokeLineCap = StrokeCap.Round,
            strokeLineJoin = StrokeJoin.Round
        ) {
            moveTo(12f, 22f)
            curveToRelative(0f, 0f, 8f, -4f, 8f, -10f)
            verticalLineTo(5f)
            lineToRelative(-8f, -3f)
            lineToRelative(-8f, 3f)
            verticalLineToRelative(7f)
            curveToRelative(0f, 6f, 8f, 10f, 8f, 10f)
        }
    }.build()

    /**
     * 新风图标 - 用于新风系统控制
     */
    val FreshAir: ImageVector = ImageVector.Builder(
        name = "FreshAir",
        defaultWidth = 24.dp,
        defaultHeight = 24.dp,
        viewportWidth = 24f,
        viewportHeight = 24f
    ).apply {
        path(
            fill = SolidColor(Color.Transparent),
            stroke = SolidColor(Color.White),
            strokeLineWidth = 2f,
            strokeLineCap = StrokeCap.Round,
            strokeLineJoin = StrokeJoin.Round
        ) {
            moveTo(9.59f, 4.59f)
            arcToRelative(2f, 2f, 0f, isMoreThanHalf = true, isPositiveArc = true, 3f, 9f)
            horizontalLineToRelative(3f)
            verticalLineToRelative(10f)
            horizontalLineToRelative(2f)
            verticalLineTo(9f)
            horizontalLineToRelative(3f)
            arcToRelative(2f, 2f, 0f, isMoreThanHalf = true, isPositiveArc = true, -6.41f, -4.41f)
            close()
            moveTo(17f, 17f)
            lineToRelative(5f, -5f)
            moveTo(17f, 17f)
            lineToRelative(-5f, 5f)
        }
    }.build()

    /**
     * 水滴图标 - 用于湿度显示或水系统相关功能
     */
    val Droplet: ImageVector = ImageVector.Builder(
        name = "Droplet",
        defaultWidth = 24.dp,
        defaultHeight = 24.dp,
        viewportWidth = 24f,
        viewportHeight = 24f
    ).apply {
        path(
            fill = SolidColor(Color.Transparent),
            stroke = SolidColor(Color.White),
            strokeLineWidth = 2f,
            strokeLineCap = StrokeCap.Round,
            strokeLineJoin = StrokeJoin.Round
        ) {
            moveTo(12f, 2.69f)
            lineToRelative(5.66f, 5.66f)
            arcToRelative(8f, 8f, 0f, isMoreThanHalf = true, isPositiveArc = true, -11.31f, 0f)
            close()
        }
    }.build()

    // ==================== 环境显示图标 ====================

    /**
     * 高温图标 - 用于显示高温状态或升温趋势
     */
    val TemperatureHigh: ImageVector = ImageVector.Builder(
        name = "TemperatureHigh",
        defaultWidth = 24.dp,
        defaultHeight = 24.dp,
        viewportWidth = 24f,
        viewportHeight = 24f
    ).apply {
        path(
            fill = SolidColor(Color.Transparent),
            stroke = SolidColor(Color.White),
            strokeLineWidth = 2f,
            strokeLineCap = StrokeCap.Round,
            strokeLineJoin = StrokeJoin.Round
        ) {
            moveTo(14f, 14.76f)
            verticalLineToRelative(3.64f)
            arcToRelative(2f, 2f, 0f, isMoreThanHalf = false, isPositiveArc = true, 4f, 0f)
            verticalLineToRelative(-3.64f)
        }
        path(
            fill = SolidColor(Color.Transparent),
            stroke = SolidColor(Color.White),
            strokeLineWidth = 2f,
            strokeLineCap = StrokeCap.Round,
            strokeLineJoin = StrokeJoin.Round
        ) {
            moveTo(18f, 8f)
            arcToRelative(6f, 6f, 0f, isMoreThanHalf = true, isPositiveArc = true, -12f, 0f)
            verticalLineTo(14f)
            horizontalLineTo(18f)
            close()
            moveTo(12f, 10f)
            verticalLineTo(14f)
        }
    }.build()

    /**
     * 低温图标 - 用于显示低温状态或降温趋势
     */
    val TemperatureLow: ImageVector = ImageVector.Builder(
        name = "TemperatureLow",
        defaultWidth = 24.dp,
        defaultHeight = 24.dp,
        viewportWidth = 24f,
        viewportHeight = 24f
    ).apply {
        path(
            fill = SolidColor(Color.Transparent),
            stroke = SolidColor(Color.White),
            strokeLineWidth = 2f,
            strokeLineCap = StrokeCap.Round,
            strokeLineJoin = StrokeJoin.Round
        ) {
            moveTo(14f, 14.76f)
            verticalLineToRelative(3.64f)
            arcToRelative(2f, 2f, 0f, isMoreThanHalf = false, isPositiveArc = true, 4f, 0f)
            verticalLineToRelative(-3.64f)
        }
        path(
            fill = SolidColor(Color.Transparent),
            stroke = SolidColor(Color.White),
            strokeLineWidth = 2f,
            strokeLineCap = StrokeCap.Round,
            strokeLineJoin = StrokeJoin.Round
        ) {
            moveTo(18f, 8f)
            arcToRelative(6f, 6f, 0f, isMoreThanHalf = true, isPositiveArc = true, -12f, 0f)
            verticalLineTo(14f)
            horizontalLineTo(18f)
            close()
            moveTo(12f, 14f)
            verticalLineTo(10f)
        }
    }.build()

    /**
     * 太阳图标 - 用于晴天天气显示
     */
    val Sun: ImageVector = ImageVector.Builder(
        name = "Sun",
        defaultWidth = 24.dp,
        defaultHeight = 24.dp,
        viewportWidth = 24f,
        viewportHeight = 24f
    ).apply {
        path(
            fill = SolidColor(Color.Transparent),
            stroke = SolidColor(Color.White),
            strokeLineWidth = 2f,
            strokeLineCap = StrokeCap.Round,
            strokeLineJoin = StrokeJoin.Round
        ) {
            moveTo(12f, 2f)
            verticalLineToRelative(2f)
            moveTo(12f, 20f)
            verticalLineToRelative(2f)
            moveTo(4.93f, 4.93f)
            lineToRelative(1.41f, 1.41f)
            moveTo(17.66f, 17.66f)
            lineToRelative(1.41f, 1.41f)
            moveTo(2f, 12f)
            horizontalLineToRelative(2f)
            moveTo(20f, 12f)
            horizontalLineToRelative(2f)
            moveTo(6.34f, 17.66f)
            lineToRelative(-1.41f, 1.41f)
            moveTo(19.07f, 4.93f)
            lineToRelative(-1.41f, 1.41f)
            moveTo(12f, 8f)
            arcToRelative(4f, 4f, 0f, isMoreThanHalf = true, isPositiveArc = false, 8f, 0f)
            arcToRelative(4f, 4f, 0f, isMoreThanHalf = true, isPositiveArc = false, -8f, 0f)
        }
    }.build()

    /**
     * 月亮图标 - 用于夜间模式或阴天显示
     */
    val Moon: ImageVector = ImageVector.Builder(
        name = "Moon",
        defaultWidth = 24.dp,
        defaultHeight = 24.dp,
        viewportWidth = 24f,
        viewportHeight = 24f
    ).apply {
        path(
            fill = SolidColor(Color.Transparent),
            stroke = SolidColor(Color.White),
            strokeLineWidth = 2f,
            strokeLineCap = StrokeCap.Round,
            strokeLineJoin = StrokeJoin.Round
        ) {
            moveTo(21f, 12.79f)
            arcToRelative(9f, 9f, 0f, isMoreThanHalf = true, isPositiveArc = true, -9.79f, -9.79f)
            arcToRelative(7f, 7f, 0f, isMoreThanHalf = false, isPositiveArc = false, 8.79f, 9.79f)
        }
    }.build()

    /**
     * 云朵图标 - 用于多云天气显示
     */
    val Cloud: ImageVector = ImageVector.Builder(
        name = "Cloud",
        defaultWidth = 24.dp,
        defaultHeight = 24.dp,
        viewportWidth = 24f,
        viewportHeight = 24f
    ).apply {
        path(
            fill = SolidColor(Color.Transparent),
            stroke = SolidColor(Color.White),
            strokeLineWidth = 2f,
            strokeLineCap = StrokeCap.Round,
            strokeLineJoin = StrokeJoin.Round
        ) {
            moveTo(18f, 10f)
            horizontalLineToRelative(-1.26f)
            arcToRelative(8f, 8f, 0f, isMoreThanHalf = false, isPositiveArc = false, -7.74f, 10f)
            horizontalLineToRelative(9f)
            arcToRelative(5f, 5f, 0f, isMoreThanHalf = false, isPositiveArc = false, 0f, -10f)
            close()
        }
    }.build()

    // ==================== 状态指示图标 ====================

    /**
     * 勾选圆圈图标 - 用于表示已完成或正常状态
     */
    val CheckCircle: ImageVector = ImageVector.Builder(
        name = "CheckCircle",
        defaultWidth = 24.dp,
        defaultHeight = 24.dp,
        viewportWidth = 24f,
        viewportHeight = 24f
    ).apply {
        path(
            fill = SolidColor(Color.Transparent),
            stroke = SolidColor(Color.White),
            strokeLineWidth = 1.5f,
            strokeLineCap = StrokeCap.Round,
            strokeLineJoin = StrokeJoin.Round
        ) {
            moveTo(22f, 11.08f)
            verticalLineToRelative(0.92f)
            arcToRelative(10f, 10f, 0f, isMoreThanHalf = true, isPositiveArc = true, -12f, -2f)
            arcToRelative(10f, 10f, 0f, isMoreThanHalf = false, isPositiveArc = true, 10f, -9.08f)
        }
        path(
            fill = SolidColor(Color.Transparent),
            stroke = SolidColor(Color.White),
            strokeLineWidth = 1.5f,
            strokeLineCap = StrokeCap.Round,
            strokeLineJoin = StrokeJoin.Round
        ) {
            moveTo(9f, 12f)
            lineToRelative(2f, 2f)
            lineToRelative(4f, -4f)
        }
    }.build()

    // ==================== 新增：导航和选择器图标 ====================

    /**
     * 首页/房子图标 - 用于冷暖舒适页面Tab栏"全屋"
     */
    val Home: ImageVector = ImageVector.Builder(
        name = "Home",
        defaultWidth = 24.dp,
        defaultHeight = 24.dp,
        viewportWidth = 24f,
        viewportHeight = 24f
    ).apply {
        path(
            fill = SolidColor(Color.Transparent),
            stroke = SolidColor(Color.White),
            strokeLineWidth = 1.5f,
            strokeLineCap = StrokeCap.Round,
            strokeLineJoin = StrokeJoin.Round
        ) {
            moveTo(3f, 9f)
            lineTo(12f, 2f)
            lineTo(21f, 9f)
            verticalLineTo(20f)
            arcToRelative(2f, 2f, 0f, isMoreThanHalf = false, isPositiveArc = true, -2f, 2f)
            horizontalLineTo(5f)
            arcToRelative(2f, 2f, 0f, isMoreThanHalf = false, isPositiveArc = true, -2f, -2f)
            close()
            moveTo(9f, 22f)
            verticalLineTo(16f)
            horizontalLineTo(15f)
            verticalLineTo(22f)
        }
    }.build()

    /**
     * 列表图标 - 用于冷暖舒适页面Tab栏"楼层"
     */
    val List: ImageVector = ImageVector.Builder(
        name = "List",
        defaultWidth = 24.dp,
        defaultHeight = 24.dp,
        viewportWidth = 24f,
        viewportHeight = 24f
    ).apply {
        path(
            fill = SolidColor(Color.Transparent),
            stroke = SolidColor(Color.White),
            strokeLineWidth = 1.5f,
            strokeLineCap = StrokeCap.Round,
            strokeLineJoin = StrokeJoin.Round
        ) {
            moveTo(8f, 6f)
            horizontalLineTo(21f)
            moveTo(8f, 12f)
            horizontalLineTo(21f)
            moveTo(8f, 18f)
            horizontalLineTo(21f)
            moveTo(3f, 6f)
            horizontalLineToRelative(0.01f)
            moveTo(3f, 12f)
            horizontalLineToRelative(0.01f)
            moveTo(3f, 18f)
            horizontalLineToRelative(0.01f)
        }
    }.build()

    /**
     * 下拉箭头图标 - 用于楼层选择器、时长选择器等
     */
    val ChevronDown: ImageVector = ImageVector.Builder(
        name = "ChevronDown",
        defaultWidth = 24.dp,
        defaultHeight = 24.dp,
        viewportWidth = 24f,
        viewportHeight = 24f
    ).apply {
        path(
            fill = SolidColor(Color.Transparent),
            stroke = SolidColor(Color.White),
            strokeLineWidth = 2f,
            strokeLineCap = StrokeCap.Round,
            strokeLineJoin = StrokeJoin.Round
        ) {
            moveTo(6f, 9f)
            lineTo(12f, 15f)
            lineTo(18f, 9f)
        }
    }.build()

    /**
     * 定位/位置图标 - 用于显示当前位置
     */
    val LocationPin: ImageVector = ImageVector.Builder(
        name = "LocationPin",
        defaultWidth = 24.dp,
        defaultHeight = 24.dp,
        viewportWidth = 24f,
        viewportHeight = 24f
    ).apply {
        path(
            fill = SolidColor(Color.Transparent),
            stroke = SolidColor(Color.White),
            strokeLineWidth = 2f,
            strokeLineCap = StrokeCap.Round,
            strokeLineJoin = StrokeJoin.Round
        ) {
            moveTo(21f, 10f)
            arcToRelative(9f, 9f, 0f, isMoreThanHalf = false, isPositiveArc = false, -18f, 0f)
            curveTo(3f, 16f, 12f, 22f, 12f, 22f)
            curveTo(12f, 22f, 21f, 16f, 21f, 10f)
            close()
        }
        path(
            fill = SolidColor(Color.Transparent),
            stroke = SolidColor(Color.White),
            strokeLineWidth = 2f,
            strokeLineCap = StrokeCap.Round,
            strokeLineJoin = StrokeJoin.Round
        ) {
            moveTo(12f, 13f)
            arcToRelative(3f, 3f, 0f, isMoreThanHalf = false, isPositiveArc = false, 0f, -6f)
            arcToRelative(3f, 3f, 0f, isMoreThanHalf = false, isPositiveArc = false, 0f, 6f)
            close()
        }
    }.build()
}
