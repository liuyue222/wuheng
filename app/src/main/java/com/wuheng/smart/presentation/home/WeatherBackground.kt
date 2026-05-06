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
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlin.math.PI
import kotlin.math.abs
import kotlin.math.cos
import kotlin.math.sin
import kotlin.random.Random

enum class WeatherType {
    SUNNY,
    CLOUDY,
    OVERCAST,
    LIGHT_RAIN,
    MODERATE_RAIN,
    HEAVY_RAIN,
    SNOWY,
    THUNDER,
    FOGGY,
    UNKNOWN
}

@Composable
fun WeatherBackground(
    weather: String,
    weatherCode: String = "",
    content: @Composable () -> Unit
) {
    val weatherType = remember(weather, weatherCode) { parseWeatherType(weather, weatherCode) }

    Box(modifier = Modifier.fillMaxSize()) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Brush.linearGradient(backgroundColors(weatherType)))
        )

        when (weatherType) {
            WeatherType.SUNNY -> SunnyEffect()
            WeatherType.CLOUDY, WeatherType.OVERCAST -> CloudyEffect()
            WeatherType.LIGHT_RAIN -> RainEffect(density = 50, speedRange = 0.6f..1.3f, lineWidthRange = 0.5f..1.2f, splashCount = 3, animPeriodMs = 1400)
            WeatherType.MODERATE_RAIN -> RainEffect(density = 90, speedRange = 1.3f..2.8f, lineWidthRange = 0.8f..2.0f, splashCount = 6, animPeriodMs = 900)
            WeatherType.HEAVY_RAIN -> RainEffect(density = 140, speedRange = 2.5f..5.5f, lineWidthRange = 1.5f..3.5f, splashCount = 12, animPeriodMs = 550)
            WeatherType.SNOWY -> SnowEffect()
            WeatherType.THUNDER -> {
                RainEffect(density = 110, speedRange = 2f..4.5f, lineWidthRange = 1.5f..3.0f, splashCount = 8, animPeriodMs = 650)
                ThunderFlashEffect()
            }
            WeatherType.FOGGY -> FoggyEffect()
            WeatherType.UNKNOWN -> { }
        }

        content()
    }
}

private fun backgroundColors(type: WeatherType): List<Color> = when (type) {
    WeatherType.SUNNY ->
        listOf(Color(0xFFFFF8E1), Color(0xFFFFECB3), Color(0xFFFFE082))
    WeatherType.CLOUDY ->
        listOf(Color(0xFFF0F4F8), Color(0xFFE3EAF0), Color(0xFFD1DCE5))
    WeatherType.OVERCAST ->
        listOf(Color(0xFFCFD8DC), Color(0xFFB0BEC5), Color(0xFF90A4AE))
    WeatherType.LIGHT_RAIN ->
        listOf(Color(0xFFE8EAF6), Color(0xFFC5CAE9), Color(0xFFE3F2FD))
    WeatherType.MODERATE_RAIN ->
        listOf(Color(0xFFB0BEC5), Color(0xFF90A4AE), Color(0xFF78909C))
    WeatherType.HEAVY_RAIN ->
        listOf(Color(0xFF455A64), Color(0xFF37474F), Color(0xFF263238))
    WeatherType.SNOWY ->
        listOf(Color(0xFFFFFFFF), Color(0xFFF0F7FF), Color(0xFFE1F0FF))
    WeatherType.THUNDER ->
        listOf(Color(0xFF37474F), Color(0xFF263238), Color(0xFF1A237E))
    WeatherType.FOGGY ->
        listOf(Color(0xFFF5F5F5), Color(0xFFE0E0E0), Color(0xFFBDBDBD))
    WeatherType.UNKNOWN ->
        listOf(Color(0xFFF0F4F8), Color(0xFFE3EAF0))
}

private fun parseWeatherType(weather: String, weatherCode: String): WeatherType {
    val code = weatherCode.toIntOrNull()
    if (code != null) {
        val fromCode = when (code) {
            0 -> WeatherType.SUNNY
            in 1..2 -> WeatherType.CLOUDY
            3 -> WeatherType.OVERCAST
            in 4..6 -> WeatherType.LIGHT_RAIN
            in 7..9 -> WeatherType.MODERATE_RAIN
            in 10..12 -> WeatherType.HEAVY_RAIN
            in 13..17 -> WeatherType.SNOWY
            in 45..48 -> WeatherType.FOGGY
            else -> null
        }
        if (fromCode != null) return fromCode
    }

    return when {
        weather.contains("雷") || weather.contains("闪电") -> WeatherType.THUNDER
        weather.contains("大雨") || weather.contains("暴雨") || weather.contains("强雨") -> WeatherType.HEAVY_RAIN
        weather.contains("中雨") -> WeatherType.MODERATE_RAIN
        weather.contains("小雨") || weather.contains("阵雨") -> WeatherType.LIGHT_RAIN
        weather.contains("雨") -> WeatherType.MODERATE_RAIN
        weather.contains("晴") -> WeatherType.SUNNY
        weather.contains("多云") -> WeatherType.CLOUDY
        weather.contains("阴") -> WeatherType.OVERCAST
        weather.contains("雪") -> WeatherType.SNOWY
        weather.contains("雾") || weather.contains("霾") -> WeatherType.FOGGY
        else -> WeatherType.UNKNOWN
    }
}

private data class SunParticle(
    val angle: Float,
    val distance: Float,
    val size: Float,
    val phase: Float,
    val colorIndex: Int
)

private data class LightRay(
    val angle: Float,
    val length: Float,
    val width: Float,
    val alpha: Float,
    val phase: Float
)

@Composable
private fun SunnyEffect() {
    val infiniteTransition = rememberInfiniteTransition()

    val rotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(30000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        )
    )

    val glowScale by infiniteTransition.animateFloat(
        initialValue = 0.88f,
        targetValue = 1.12f,
        animationSpec = infiniteRepeatable(
            animation = tween(3500, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

    val particleProgress by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(5000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        )
    )

    val rayPulse by infiniteTransition.animateFloat(
        initialValue = 0.75f,
        targetValue = 1.0f,
        animationSpec = infiniteRepeatable(
            animation = tween(2500, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

    val particles = remember {
        List(24) {
            SunParticle(
                angle = Random.nextFloat() * 360f,
                distance = Random.nextFloat() * 0.55f + 0.4f,
                size = Random.nextFloat() * 3.5f + 1.2f,
                phase = Random.nextFloat(),
                colorIndex = Random.nextInt(3)
            )
        }
    }

    val rays = remember {
        List(12) {
            LightRay(
                angle = it * 30f + Random.nextFloat() * 10f - 5f,
                length = Random.nextFloat() * 0.5f + 0.6f,
                width = Random.nextFloat() * 1.5f + 0.8f,
                alpha = Random.nextFloat() * 0.25f + 0.15f,
                phase = Random.nextFloat()
            )
        }
    }

    Canvas(modifier = Modifier.fillMaxSize()) {
        val cx = size.width * 0.78f
        val cy = size.height * 0.14f
        val sunRadius = 52f * glowScale

        for (layer in 3 downTo 0) {
            drawCircle(
                color = Color(0xFFFFD54F).copy(alpha = 0.04f * (layer + 1)),
                radius = sunRadius * (2.8f - layer * 0.6f),
                center = Offset(cx, cy)
            )
        }

        drawCircle(
            color = Color(0xFFFF9800).copy(alpha = 0.06f),
            radius = sunRadius * 2.2f,
            center = Offset(cx, cy)
        )

        rays.forEach { ray ->
            val rayAngle = (ray.angle + rotation * 0.4f) * PI.toFloat() / 180f
            val pulseFactor = rayPulse * (0.85f + 0.15f * sin((particleProgress + ray.phase) * PI.toFloat() * 2f))
            val startR = sunRadius * 1.35f
            val endR = sunRadius * (1.35f + ray.length * 2.5f * pulseFactor)

            drawLine(
                color = Color(0xFFFFF176).copy(alpha = ray.alpha * pulseFactor),
                start = Offset(cx + cos(rayAngle) * startR, cy + sin(rayAngle) * startR),
                end = Offset(cx + cos(rayAngle) * endR, cy + sin(rayAngle) * endR),
                strokeWidth = ray.width * pulseFactor,
                cap = StrokeCap.Round
            )
        }

        drawCircle(
            color = Color(0xFFFFC107).copy(alpha = 0.9f),
            radius = sunRadius,
            center = Offset(cx, cy)
        )

        drawCircle(
            color = Color(0xFFFFF9C4).copy(alpha = 0.7f),
            radius = sunRadius * 0.5f,
            center = Offset(cx - sunRadius * 0.18f, cy - sunRadius * 0.18f)
        )

        drawCircle(
            color = Color(0xFFFFFFFF).copy(alpha = 0.4f),
            radius = sunRadius * 0.2f,
            center = Offset(cx - sunRadius * 0.3f, cy - sunRadius * 0.3f)
        )

        particles.forEach { p ->
            val pAngle = (p.angle + rotation * 0.25f) * PI.toFloat() / 180f
            val pDist = sunRadius * (1.4f + p.distance * 2.5f)
            val pp = (particleProgress + p.phase) % 1f
            val px = cx + cos(pAngle) * pDist + sin(pp * PI.toFloat() * 2f) * 20f
            val py = cy + sin(pAngle) * pDist + cos(pp * PI.toFloat() * 2f) * 16f
            val alpha = (0.1f + 0.35f * sin(pp * PI.toFloat() * 2f)).coerceIn(0f, 1f)

            val particleColor = when (p.colorIndex) {
                0 -> Color(0xFFFFF176)
                1 -> Color(0xFFFFD54F)
                else -> Color(0xFFFFAB40)
            }

            drawCircle(
                color = particleColor.copy(alpha = alpha),
                radius = p.size,
                center = Offset(px, py)
            )
        }
    }
}

private data class CloudParticle(
    val x: Float,
    val y: Float,
    val size: Float,
    val speed: Float,
    val alpha: Float,
    val phase: Float
)

@Composable
private fun CloudyEffect() {
    val infiniteTransition = rememberInfiniteTransition()

    val cloudOffset1 by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(28000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        )
    )

    val cloudOffset2 by infiniteTransition.animateFloat(
        initialValue = 0.4f,
        targetValue = 1.4f,
        animationSpec = infiniteRepeatable(
            animation = tween(35000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        )
    )

    val cloudOffset3 by infiniteTransition.animateFloat(
        initialValue = 0.7f,
        targetValue = 1.7f,
        animationSpec = infiniteRepeatable(
            animation = tween(40000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        )
    )

    val particleProgress by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(22000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        )
    )

    val particles = remember {
        List(15) {
            CloudParticle(
                x = Random.nextFloat(),
                y = Random.nextFloat() * 0.5f + 0.05f,
                size = Random.nextFloat() * 18f + 8f,
                speed = Random.nextFloat() * 2f + 0.5f,
                alpha = Random.nextFloat() * 0.3f + 0.15f,
                phase = Random.nextFloat()
            )
        }
    }

    Canvas(modifier = Modifier.fillMaxSize()) {
        val cloud1X = (cloudOffset1 * size.width * 1.2f) % (size.width * 1.2f) - size.width * 0.1f
        drawCloud(cloud1X, size.height * 0.08f, 85f, Color(0xFFFFFFFF).copy(alpha = 0.55f))
        drawSmallCloud(cloud1X + size.width * 0.3f, size.height * 0.16f, 40f, Color(0xFFF5F5F5).copy(alpha = 0.35f))

        val cloud2X = (cloudOffset2 * size.width * 1.2f) % (size.width * 1.2f) - size.width * 0.1f
        drawCloud(cloud2X, size.height * 0.22f, 65f, Color(0xFFFFFFFF).copy(alpha = 0.4f))

        val cloud3X = (cloudOffset3 * size.width * 1.2f) % (size.width * 1.2f) - size.width * 0.1f
        drawCloud(cloud3X, size.height * 0.35f, 50f, Color(0xFFF0F0F0).copy(alpha = 0.3f))

        particles.forEach { p ->
            val px = ((p.x + particleProgress * p.speed * 0.3f) % 1f).let { if (it < 0) it + 1f else it } * size.width
            val py = p.y * size.height + sin((particleProgress + p.phase) * PI.toFloat() * 2f) * 10f
            drawCircle(
                color = Color(0xFFFFFFFF).copy(alpha = p.alpha),
                radius = p.size,
                center = Offset(px, py)
            )
        }
    }
}

private fun DrawScope.drawCloud(x: Float, y: Float, size: Float, color: Color) {
    val circles = listOf(
        Triple(0f, 0f, size),
        Triple(size * 0.65f, -size * 0.22f, size * 0.78f),
        Triple(-size * 0.65f, -size * 0.12f, size * 0.72f),
        Triple(size * 0.35f, size * 0.22f, size * 0.58f),
        Triple(-size * 0.35f, size * 0.16f, size * 0.62f)
    )
    circles.forEach { (dx, dy, radius) ->
        drawCircle(color = color, radius = radius, center = Offset(x + dx, y + dy))
    }
}

private fun DrawScope.drawSmallCloud(x: Float, y: Float, size: Float, color: Color) {
    val circles = listOf(
        Triple(0f, 0f, size),
        Triple(size * 0.5f, -size * 0.15f, size * 0.7f),
        Triple(-size * 0.5f, -size * 0.08f, size * 0.65f)
    )
    circles.forEach { (dx, dy, radius) ->
        drawCircle(color = color, radius = radius, center = Offset(x + dx, y + dy))
    }
}

private data class Raindrop(
    val x: Float,
    val y: Float,
    val speed: Float,
    val lineWidth: Float,
    val splashPhase: Float
)

private data class Splash(
    val x: Float,
    val y: Float,
    val radius: Float,
    val alpha: Float,
    val phase: Float
)

@Composable
private fun RainEffect(
    density: Int,
    speedRange: ClosedFloatingPointRange<Float>,
    lineWidthRange: ClosedFloatingPointRange<Float>,
    splashCount: Int,
    animPeriodMs: Int
) {
    val raindrops = remember(density) {
        List(density) {
            Raindrop(
                x = Random.nextFloat(),
                y = Random.nextFloat(),
                speed = Random.nextFloat() * (speedRange.endInclusive - speedRange.start) + speedRange.start,
                lineWidth = Random.nextFloat() * (lineWidthRange.endInclusive - lineWidthRange.start) + lineWidthRange.start,
                splashPhase = Random.nextFloat()
            )
        }
    }

    val splashes = remember(splashCount) {
        List(splashCount) {
            Splash(
                x = Random.nextFloat(),
                y = 0f,
                radius = 0f,
                alpha = 0f,
                phase = Random.nextFloat()
            )
        }
    }

    val infiniteTransition = rememberInfiniteTransition()
    val progress by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(animPeriodMs, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        )
    )

    Canvas(modifier = Modifier.fillMaxSize()) {
        raindrops.forEach { raindrop ->
            drawRaindrop(raindrop, progress)
        }

        splashes.forEach { splash ->
            drawSplash(splash, progress)
        }
    }
}

private fun DrawScope.drawRaindrop(raindrop: Raindrop, progress: Float) {
    val x = raindrop.x * size.width
    val rawY = (raindrop.y + progress * raindrop.speed) % 1.05f
    val y = rawY * size.height
    val length = raindrop.speed * 20f
    val slant = length * 0.45f
    val alpha = (0.65f * (1f - y / size.height) * (1f - abs(rawY - 1f) * 10f).coerceIn(0f, 1f))
        .coerceIn(0.03f, 0.65f)

    drawLine(
        color = Color(0xFF90CAF9).copy(alpha = alpha),
        start = Offset(x - slant, y - length * 0.6f),
        end = Offset(x + slant, y + length * 0.4f),
        strokeWidth = raindrop.lineWidth,
        cap = StrokeCap.Round
    )
}

private fun DrawScope.drawSplash(splash: Splash, progress: Float) {
    val localProgress = (progress + splash.phase * 0.7f) % 1f
    val triggerY = 0.92f + splash.phase * 0.06f

    if (localProgress < triggerY || localProgress > triggerY + 0.12f) return

    val splashProgress = (localProgress - triggerY) / 0.12f
    val sx = splash.x * size.width
    val sy = triggerY * size.height

    val dropletRadius = splashProgress * 3f + 1f
    if (splashProgress < 0.5f) {
        drawCircle(
            color = Color(0xFF90CAF9).copy(alpha = (1f - splashProgress * 2f) * 0.4f),
            radius = dropletRadius,
            center = Offset(sx + splashProgress * 12f - 6f, sy + splashProgress * 6f)
        )
    }

    val ringRadius = splashProgress * 8f
    val ringAlpha = (1f - splashProgress) * 0.45f
    drawCircle(
        color = Color(0xFFBBDEFB).copy(alpha = ringAlpha),
        radius = ringRadius,
        center = Offset(sx, sy),
        style = androidx.compose.ui.graphics.drawscope.Stroke(width = 1.2f)
    )
}

private data class Snowflake(
    val x: Float,
    val y: Float,
    val size: Float,
    val speed: Float,
    val drift: Float,
    val driftFreq: Float,
    val opacity: Float
)

@Composable
private fun SnowEffect() {
    val snowflakes = remember {
        List(80) {
            Snowflake(
                x = Random.nextFloat(),
                y = Random.nextFloat(),
                size = Random.nextFloat() * 2.5f + 1f,
                speed = Random.nextFloat() * 0.4f + 0.2f,
                drift = Random.nextFloat() * 1.5f - 0.75f,
                driftFreq = Random.nextFloat() * 2f + 1f,
                opacity = Random.nextFloat() * 0.5f + 0.5f
            )
        }
    }

    val infiniteTransition = rememberInfiniteTransition()
    val progress by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(6000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        )
    )

    Canvas(modifier = Modifier.fillMaxSize()) {
        snowflakes.forEach { snowflake ->
            drawSnowflake(snowflake, progress)
        }
    }
}

private fun DrawScope.drawSnowflake(snowflake: Snowflake, progress: Float) {
    val driftX = sin((progress + snowflake.phase()) * PI.toFloat() * snowflake.driftFreq) * 30f
    val x = ((snowflake.x + snowflake.drift * progress * 0.5f + driftX / size.width) % 1f)
        .let { if (it < 0) it + 1f else it } * size.width
    val y = ((snowflake.y + progress * snowflake.speed) % 1f) * size.height
    val alpha = (snowflake.opacity * (1f - y / size.height).coerceIn(0.05f, 1f)).coerceIn(0.05f, snowflake.opacity)

    drawCircle(
        color = Color(0xFFFFFFFF).copy(alpha = alpha),
        radius = snowflake.size,
        center = Offset(x, y)
    )

    if (snowflake.size > 1.8f && alpha > 0.2f) {
        drawCircle(
            color = Color(0xFFF0F7FF).copy(alpha = alpha * 0.4f),
            radius = snowflake.size * 1.8f,
            center = Offset(x, y)
        )
    }
}

private fun Snowflake.phase(): Float = driftFreq * size

@Composable
private fun ThunderFlashEffect() {
    var showLightning by remember { mutableStateOf(false) }
    var flashIntensity by remember { mutableStateOf(0f) }

    LaunchedEffect(Unit) {
        while (true) {
            delay(Random.nextLong(3000, 10000))
            showLightning = true
            flashIntensity = 0.25f
            delay(120)
            flashIntensity = 0.05f
            delay(80)
            showLightning = true
            flashIntensity = 0.18f
            delay(90)
            flashIntensity = 0.03f
            delay(60)
            showLightning = false
            flashIntensity = 0f
        }
    }

    if (showLightning) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF5F5FF).copy(alpha = flashIntensity))
        )
    }
}

@Composable
private fun FoggyEffect() {
    val infiniteTransition = rememberInfiniteTransition()

    val fogOffset1 by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(18000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        )
    )

    val fogOffset2 by infiniteTransition.animateFloat(
        initialValue = 0.5f,
        targetValue = 1.5f,
        animationSpec = infiniteRepeatable(
            animation = tween(22000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        )
    )

    val mistPulse by infiniteTransition.animateFloat(
        initialValue = 0.8f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(6000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

    val mistParticles = remember {
        List(12) {
            Triple(
                Random.nextFloat(),
                Random.nextFloat() * 0.8f + 0.1f,
                Random.nextFloat() * 0.15f + 0.05f
            )
        }
    }

    Canvas(modifier = Modifier.fillMaxSize()) {
        for (i in 0 until 4) {
            val fogY = size.height * (0.15f + i * 0.22f)
            val fogX1 = ((fogOffset1 + i * 0.25f) % 1f) * size.width
            val fogX2 = ((fogOffset2 + i * 0.3f) % 1f) * size.width

            drawCircle(
                color = Color(0xFFF5F5F5).copy(alpha = (0.12f - i * 0.02f) * mistPulse),
                radius = size.width * 0.9f,
                center = Offset(fogX1 - size.width * 0.25f, fogY)
            )
            drawCircle(
                color = Color(0xFFEEEEEE).copy(alpha = (0.1f - i * 0.015f) * mistPulse),
                radius = size.width * 0.75f,
                center = Offset(fogX2 + size.width * 0.3f, fogY + size.height * 0.05f)
            )
        }

        mistParticles.forEach { (xRatio, yRatio, alpha) ->
            val px = ((xRatio + fogOffset1 * 0.3f) % 1f) * size.width
            val py = ((yRatio + fogOffset2 * 0.15f) % 1f) * size.height
            drawCircle(
                color = Color(0xFFFFFFFF).copy(alpha = alpha * mistPulse),
                radius = size.width * 0.4f,
                center = Offset(px, py)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun WeatherBackgroundHeavyRainPreview() {
    WeatherBackground(weather = "大雨", weatherCode = "10") {
        Box(modifier = Modifier.fillMaxSize())
    }
}

@Preview(showBackground = true)
@Composable
fun WeatherBackgroundLightRainPreview() {
    WeatherBackground(weather = "小雨", weatherCode = "4") {
        Box(modifier = Modifier.fillMaxSize())
    }
}

@Preview(showBackground = true)
@Composable
fun WeatherBackgroundSunnyPreview() {
    WeatherBackground(weather = "晴", weatherCode = "0") {
        Box(modifier = Modifier.fillMaxSize())
    }
}

@Preview(showBackground = true)
@Composable
fun WeatherBackgroundSnowPreview() {
    WeatherBackground(weather = "大雪", weatherCode = "14") {
        Box(modifier = Modifier.fillMaxSize())
    }
}

@Preview(showBackground = true)
@Composable
fun WeatherBackgroundFogPreview() {
    WeatherBackground(weather = "雾", weatherCode = "45") {
        Box(modifier = Modifier.fillMaxSize())
    }
}
