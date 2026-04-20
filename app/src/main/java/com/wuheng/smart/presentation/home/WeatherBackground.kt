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
import kotlin.random.Random

/**
 * 天气背景效果
 * 根据天气类型显示不同的背景效果
 */
@Composable
fun WeatherBackground(
    weather: String,
    content: @Composable () -> Unit
) {
    Box(modifier = Modifier.fillMaxSize()) {
        // 基础背景渐变
        val backgroundBrush = when {
            weather.contains("雨") -> {
                // 雨天 - 深蓝色渐变
                Brush.linearGradient(
                    colors = listOf(
                        Color(0xFFE3F2FD),  // 浅蓝
                        Color(0xFFBBDEFB),  // 稍深一点的蓝
                        Color(0xFF90CAF9)   // 淡蓝
                    )
                )
            }
            weather.contains("晴") -> {
                // 晴天 - 暖色调渐变
                Brush.linearGradient(
                    colors = listOf(
                        Color(0xFFFFF8E1),  // 暖白
                        Color(0xFFFFECB3),  // 浅黄
                        Color(0xFFFFE082)   // 淡黄
                    )
                )
            }
            else -> {
                // 默认/多云 - 中性渐变
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

        // 雨天效果
        if (weather.contains("雨")) {
            RainEffect()
        }

        // 内容
        content()
    }
}

/**
 * 雨滴效果
 */
@Composable
private fun RainEffect() {
    val raindrops = remember { List(50) { Raindrop(Random.nextFloat(), Random.nextFloat(), Random.nextFloat() * 2 + 1) } }
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
    val y = (raindrop.y + progress) % 1f * size.height
    val length = raindrop.speed * 20f
    val alpha = 0.3f * (1f - (y / size.height))  // 越往下越淡

    drawLine(
        color = Color(0xFF64B5F6).copy(alpha = alpha),
        start = Offset(x, y),
        end = Offset(x, y + length),
        strokeWidth = 2f
    )
}

/**
 * 雨滴数据类
 */
private data class Raindrop(
    val x: Float,      // 水平位置 (0-1)
    val y: Float,      // 初始垂直位置 (0-1)
    val speed: Float   // 下落速度
)

@Preview(showBackground = true)
@Composable
fun WeatherBackgroundRainPreview() {
    WeatherBackground(weather = "雨") {
        Box(modifier = Modifier.fillMaxSize())
    }
}
