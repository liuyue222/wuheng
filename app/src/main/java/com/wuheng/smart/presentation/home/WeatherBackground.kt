package com.wuheng.smart.presentation.home

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin
import kotlin.random.Random

/**
 * 天气类型枚举
 */
enum class WeatherType {
    SUNNY,      // 晴天
    CLOUDY,     // 多云
    RAINY,      // 雨天
    SNOWY,      // 雪天
    THUNDER,    // 雷雨
    FOGGY,      // 雾天
    UNKNOWN     // 未知
}

/**
 * 天气背景效果
 * 根据天气类型显示不同的背景效果
 */
@Composable
fun WeatherBackground(
    weather: String,
    content: @Composable () -> Unit
) {
    val weatherType = remember(weather) { parseWeatherType(weather) }

    Box(modifier = Modifier.fillMaxSize()) {
        // 基础背景渐变
        val backgroundBrush = when (weatherType) {
            WeatherType.SUNNY -> {
                // 晴天 - 暖色调渐变
                Brush.linearGradient(
                    colors = listOf(
                        Color(0xFFFFF8E1),  // 暖白
                        Color(0xFFFFECB3),  // 浅黄
                        Color(0xFFFFE082)   // 淡黄
                    )
                )
            }
            WeatherType.CLOUDY -> {
                // 多云 - 中性渐变
                Brush.linearGradient(
                    colors = listOf(
                        Color(0xFFF0F4F8),
                        Color(0xFFE3EAF0),
                        Color(0xFFD1DCE5)
                    )
                )
            }
            WeatherType.RAINY -> {
                // 雨天 - 深蓝色渐变
                Brush.linearGradient(
                    colors = listOf(
                        Color(0xFFE3F2FD),  // 浅蓝
                        Color(0xFFBBDEFB),  // 稍深一点的蓝
                        Color(0xFF90CAF9)   // 淡蓝
                    )
                )
            }
            WeatherType.SNOWY -> {
                // 雪天 - 冷白色渐变
                Brush.linearGradient(
                    colors = listOf(
                        Color(0xFFFFFFFF),
                        Color(0xFFF0F7FF),
                        Color(0xFFE1F0FF)
                    )
                )
            }
            WeatherType.THUNDER -> {
                // 雷雨 - 深灰蓝色渐变
                Brush.linearGradient(
                    colors = listOf(
                        Color(0xFFE8EAF6),
                        Color(0xFFC5CAE9),
                        Color(0xFF9FA8DA)
                    )
                )
            }
            WeatherType.FOGGY -> {
                // 雾天 - 灰白色渐变
                Brush.linearGradient(
                    colors = listOf(
                        Color(0xFFF5F5F5),
                        Color(0xFFE0E0E0),
                        Color(0xFFBDBDBD)
                    )
                )
            }
            WeatherType.UNKNOWN -> {
                // 默认 - 中性渐变
                Brush.linearGradient(
                    colors = listOf(
                        Color(0xFFF0F4F8),
                        Color(0xFFE3EAF0)
                    )
                )
            }
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(backgroundBrush)
        )

        // 根据天气类型显示不同的动画效果
        when (weatherType) {
            WeatherType.SUNNY -> SunnyEffect()
            WeatherType.CLOUDY -> CloudyEffect()
            WeatherType.RAINY -> RainEffect()
            WeatherType.SNOWY -> SnowEffect()
            WeatherType.THUNDER -> ThunderEffect()
            WeatherType.FOGGY -> FoggyEffect()
            else -> { /* 无特效 */ }
        }

        // 内容
        content()
    }
}

/**
 * 解析天气字符串为天气类型
 */
private fun parseWeatherType(weather: String): WeatherType {
    return when {
        weather.contains("晴") -> WeatherType.SUNNY
        weather.contains("多云") || weather.contains("阴") -> WeatherType.CLOUDY
        weather.contains("雨") && !weather.contains("雷") -> WeatherType.RAINY
        weather.contains("雪") -> WeatherType.SNOWY
        weather.contains("雷") || weather.contains("闪电") -> WeatherType.THUNDER
        weather.contains("雾") || weather.contains("霾") -> WeatherType.FOGGY
        else -> WeatherType.UNKNOWN
    }
}

/**
 * 晴天效果 - 阳光射线
 */
@Composable
private fun SunnyEffect() {
    val infiniteTransition = rememberInfiniteTransition()

    // 太阳光芒旋转动画
    val rotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(20000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        )
    )

    // 光晕缩放动画
    val scale by infiniteTransition.animateFloat(
        initialValue = 0.95f,
        targetValue = 1.05f,
        animationSpec = infiniteRepeatable(
            animation = tween(3000, easing = EaseInOutSine),
            repeatMode = RepeatMode.Reverse
        )
    )

    Canvas(modifier = Modifier.fillMaxSize()) {
        // 绘制太阳光芒
        val centerX = size.width * 0.8f
        val centerY = size.height * 0.15f
        val sunRadius = 60f * scale

        // 绘制光芒
        for (i in 0 until 12) {
            val angle = (i * 30 + rotation) * PI / 180
            val startRadius = sunRadius * 1.2f
            val endRadius = sunRadius * (1.8f + 0.3f * sin(angle.toFloat() * 2))

            val startX = centerX + cos(angle).toFloat() * startRadius
            val startY = centerY + sin(angle).toFloat() * startRadius
            val endX = centerX + cos(angle).toFloat() * endRadius
            val endY = centerY + sin(angle).toFloat() * endRadius

            drawLine(
                color = Color(0xFFFFD54F).copy(alpha = 0.3f),
                start = Offset(startX, startY),
                end = Offset(endX, endY),
                strokeWidth = 3f
            )
        }

        // 绘制太阳
        drawCircle(
            color = Color(0xFFFFD54F).copy(alpha = 0.2f),
            radius = sunRadius * 1.5f,
            center = Offset(centerX, centerY)
        )
        drawCircle(
            color = Color(0xFFFFC107).copy(alpha = 0.3f),
            radius = sunRadius,
            center = Offset(centerX, centerY)
        )
    }
}

/**
 * 多云效果 - 飘动的云朵
 */
@Composable
private fun CloudyEffect() {
    val infiniteTransition = rememberInfiniteTransition()

    // 云朵飘动动画
    val cloudOffset1 by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(25000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        )
    )

    val cloudOffset2 by infiniteTransition.animateFloat(
        initialValue = 0.3f,
        targetValue = 1.3f,
        animationSpec = infiniteRepeatable(
            animation = tween(30000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        )
    )

    Canvas(modifier = Modifier.fillMaxSize()) {
        // 云朵1
        val cloud1X = (cloudOffset1 * size.width * 1.2f) % (size.width * 1.2f) - size.width * 0.1f
        val cloud1Y = size.height * 0.1f
        drawCloud(cloud1X, cloud1Y, 80f, Color(0xFFFFFFFF).copy(alpha = 0.6f))

        // 云朵2
        val cloud2X = (cloudOffset2 * size.width * 1.2f) % (size.width * 1.2f) - size.width * 0.1f
        val cloud2Y = size.height * 0.2f
        drawCloud(cloud2X, cloud2Y, 60f, Color(0xFFFFFFFF).copy(alpha = 0.4f))
    }
}

/**
 * 绘制云朵
 */
private fun DrawScope.drawCloud(x: Float, y: Float, size: Float, color: Color) {
    // 云朵由多个圆组成
    val circles = listOf(
        Triple(0f, 0f, size),
        Triple(size * 0.6f, -size * 0.2f, size * 0.8f),
        Triple(-size * 0.6f, -size * 0.1f, size * 0.7f),
        Triple(size * 0.3f, size * 0.2f, size * 0.6f),
        Triple(-size * 0.3f, size * 0.15f, size * 0.65f)
    )

    circles.forEach { (dx, dy, radius) ->
        drawCircle(
            color = color,
            radius = radius,
            center = Offset(x + dx, y + dy)
        )
    }
}

/**
 * 雨滴效果
 */
@Composable
private fun RainEffect() {
    val raindrops = remember { List(60) { Raindrop(Random.nextFloat(), Random.nextFloat(), Random.nextFloat() * 2 + 1) } }
    val infiniteTransition = rememberInfiniteTransition()

    // 动画进度
    val progress by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        )
    )

    Canvas(modifier = Modifier.fillMaxSize()) {
        raindrops.forEach { raindrop ->
            drawRaindrop(raindrop, progress)
        }
    }
}

/**
 * 绘制单个雨滴
 */
private fun DrawScope.drawRaindrop(raindrop: Raindrop, progress: Float) {
    val x = raindrop.x * size.width
    val y = ((raindrop.y + progress * raindrop.speed) % 1f) * size.height
    val length = raindrop.speed * 25f
    val alpha = 0.4f * (1f - (y / size.height))  // 越往下越淡

    drawLine(
        color = Color(0xFF64B5F6).copy(alpha = alpha),
        start = Offset(x, y),
        end = Offset(x, y + length),
        strokeWidth = 2.5f
    )
}

/**
 * 雪花效果
 */
@Composable
private fun SnowEffect() {
    val snowflakes = remember { List(40) { Snowflake(Random.nextFloat(), Random.nextFloat(), Random.nextFloat() * 0.5f + 0.5f, Random.nextFloat() * 2 - 1) } }
    val infiniteTransition = rememberInfiniteTransition()

    val progress by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(5000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        )
    )

    Canvas(modifier = Modifier.fillMaxSize()) {
        snowflakes.forEach { snowflake ->
            drawSnowflake(snowflake, progress)
        }
    }
}

/**
 * 绘制单个雪花
 */
private fun DrawScope.drawSnowflake(snowflake: Snowflake, progress: Float) {
    val x = (snowflake.x + snowflake.drift * progress) % 1f * size.width
    val y = ((snowflake.y + progress * snowflake.speed) % 1f) * size.height
    val alpha = 0.6f * (1f - (y / size.height))

    drawCircle(
        color = Color(0xFFFFFFFF).copy(alpha = alpha),
        radius = snowflake.speed * 3f,
        center = Offset(x, y)
    )
}

/**
 * 雷雨效果
 */
@Composable
private fun ThunderEffect() {
    // 复用雨滴效果
    RainEffect()

    // 闪电效果
    var showLightning by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        while (true) {
            delay(Random.nextLong(3000, 8000))
            showLightning = true
            delay(150)
            showLightning = false
            delay(100)
            showLightning = true
            delay(100)
            showLightning = false
        }
    }

    if (showLightning) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFFFFFFF).copy(alpha = 0.15f))
        )
    }
}

/**
 * 雾天效果
 */
@Composable
private fun FoggyEffect() {
    val infiniteTransition = rememberInfiniteTransition()

    val offset by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(15000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        )
    )

    Canvas(modifier = Modifier.fillMaxSize()) {
        // 绘制多层雾气
        for (i in 0 until 3) {
            val fogY = size.height * (0.3f + i * 0.25f)
            val fogOffset = ((offset + i * 0.33f) % 1f) * size.width

            drawCircle(
                color = Color(0xFFFFFFFF).copy(alpha = 0.15f - i * 0.03f),
                radius = size.width * 0.8f,
                center = Offset(fogOffset - size.width * 0.3f, fogY)
            )
            drawCircle(
                color = Color(0xFFFFFFFF).copy(alpha = 0.15f - i * 0.03f),
                radius = size.width * 0.8f,
                center = Offset(fogOffset + size.width * 0.7f, fogY)
            )
        }
    }
}

/**
 * 雨滴数据类
 */
private data class Raindrop(
    val x: Float,      // 水平位置 (0-1)
    val y: Float,      // 初始垂直位置 (0-1)
    val speed: Float   // 下落速度
)

/**
 * 雪花数据类
 */
private data class Snowflake(
    val x: Float,      // 水平位置 (0-1)
    val y: Float,      // 初始垂直位置 (0-1)
    val speed: Float,  // 下落速度
    val drift: Float   // 水平漂移
)

@Preview(showBackground = true)
@Composable
fun WeatherBackgroundRainPreview() {
    WeatherBackground(weather = "雨") {
        Box(modifier = Modifier.fillMaxSize())
    }
}

@Preview(showBackground = true)
@Composable
fun WeatherBackgroundSunnyPreview() {
    WeatherBackground(weather = "晴") {
        Box(modifier = Modifier.fillMaxSize())
    }
}

@Preview(showBackground = true)
@Composable
fun WeatherBackgroundCloudyPreview() {
    WeatherBackground(weather = "多云") {
        Box(modifier = Modifier.fillMaxSize())
    }
}
