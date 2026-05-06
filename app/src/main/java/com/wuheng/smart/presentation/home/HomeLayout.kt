package com.wuheng.smart.presentation.home

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.material.icons.filled.WbCloudy
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.material.icons.filled.AcUnit
import androidx.compose.material.icons.filled.FlashOn
import androidx.compose.material.icons.filled.Cloud
import androidx.compose.material.icons.filled.Grain
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.wuheng.smart.R
import com.wuheng.smart.data.model.SceneType
import com.wuheng.smart.presentation.components.*
import com.wuheng.smart.presentation.theme.*

@Composable
fun HomeLayout(
    uiState: HomeUiState,
    onModeSelected: (ClimateMode) -> Unit,
    onSceneSelected: (SceneType) -> Unit,
    onVacationModeClick: () -> Unit,
    onResidenceClick: () -> Unit,
    vacationModeEnabled: Boolean = false,
    vacationStartTime: String? = null,
    modifier: Modifier = Modifier,
    maxWidth: Dp = 360.dp
) {
    val isWide = maxWidth >= 720.dp
    val horizontalPadding = if (isWide) page_margin_horizontal_wide else page_margin_horizontal

    // 优化1: 使用derivedStateOf缓存场景列表，只在scenes引用变化时重新计算
    // 使用key来稳定场景列表的标识，避免不必要的重组
    val mainScenes by remember(uiState.scenes) {
        derivedStateOf {
            uiState.scenes.filter { scene ->
                scene.type in listOf(SceneType.MEETING, SceneType.AWAY, SceneType.SLEEP, SceneType.GUARD)
            }.take(4)
        }
    }

    // 优化2: 缓存场景选中状态，避免整个列表因单个场景状态变化而重组
    val selectedSceneType by remember(uiState.scenes) {
        derivedStateOf {
            uiState.scenes.find { it.isSelected }?.type
        }
    }

    // 优化3: 将WeatherBackground移到LazyColumn外部，避免天气变化导致整个列表重组
    WeatherBackground(weather = uiState.weather, weatherCode = uiState.weatherCode) {
        LazyColumn(
            modifier = modifier
                .fillMaxSize(),
            contentPadding = PaddingValues(
                start = horizontalPadding,
                end = horizontalPadding,
                top = page_top_safe_area,
                bottom = page_bottom_safe_area
            )
        ) {
            // 优化4: 为每个item添加稳定的key，帮助Compose识别哪些项需要重组
            item(key = "weather_header") {
                WeatherHeader(
                    location = uiState.location,
                    outdoorTemp = uiState.outdoorTemp,
                    weather = uiState.weather,
                    aqi = uiState.aqi,
                    pm25 = uiState.pm25,
                    humidity = uiState.outdoorHumidity
                )
            }

            item(key = "residence_card") {
                Spacer(modifier = Modifier.height(spacing_lg))
                ResidenceCard(
                    residenceName = uiState.residenceName,
                    onClick = onResidenceClick
                )
            }

            item(key = "mode_selector") {
                Spacer(modifier = Modifier.height(spacing_lg))
                ModeSelector(
                    selectedMode = uiState.currentMode,
                    onModeSelected = onModeSelected
                )
            }

            item(key = "environment_card") {
                Spacer(modifier = Modifier.height(spacing_lg))
                EnvironmentDataCard(
                    indoorTemp = uiState.indoorTemp,
                    indoorHumidity = uiState.indoorHumidity,
                    co2 = uiState.co2,
                    pm25 = uiState.pm25,
                    tovc = uiState.tovc
                )
            }

            // 优化5: 场景列表使用稳定key，并传递selectedSceneType而非整个scenes列表
            if (mainScenes.isNotEmpty()) {
                item(key = "scene_section") {
                    Spacer(modifier = Modifier.height(spacing_lg))
                    SceneSection(
                        scenes = mainScenes,
                        selectedSceneType = selectedSceneType,
                        onSceneSelected = onSceneSelected
                    )
                }
            }

            if (vacationModeEnabled) {
                item(key = "vacation_card") {
                    Spacer(modifier = Modifier.height(spacing_lg))
                    VacationModeCard(
                        onClick = onVacationModeClick,
                        startTime = vacationStartTime
                    )
                }
            }

            item(key = "bottom_spacer") {
                Spacer(modifier = Modifier.height(spacing_xl))
            }
        }
    }
}

/**
 * 获取天气图标
 */
@Composable
private fun WeatherIcon(weather: String, modifier: Modifier = Modifier) {
    val (icon, tint) = when {
        weather.contains("晴") -> Icons.Filled.WbSunny to Color(0xFFFFA726) // 橙色
        weather.contains("多云") -> Icons.Filled.WbCloudy to Color(0xFF90A4AE) // 蓝灰色
        weather.contains("阴") -> Icons.Filled.Cloud to Color(0xFFB0BEC5) // 灰色
        weather.contains("雨") && weather.contains("雷") -> Icons.Filled.FlashOn to Color(0xFF7E57C2) // 紫色
        weather.contains("雨") -> Icons.Filled.WaterDrop to Color(0xFF42A5F5) // 蓝色
        weather.contains("雪") -> Icons.Filled.AcUnit to Color(0xFF81D4FA) // 浅蓝色
        weather.contains("雾") || weather.contains("霾") -> Icons.Filled.Grain to Color(0xFFBDBDBD) // 灰色
        else -> Icons.Filled.WbCloudy to Color(0xFF90A4AE) // 默认多云
    }

    Icon(
        imageVector = icon,
        contentDescription = weather,
        modifier = modifier,
        tint = tint
    )
}

@Composable
private fun WeatherHeader(
    location: String,
    outdoorTemp: Int,
    weather: String,
    aqi: Int,
    pm25: Int,
    humidity: Int
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Top
    ) {
        Column {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(id = R.drawable.ic_location),
                    contentDescription = null,
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(spacing_xs))
                Text(
                    text = location.ifEmpty { "定位中..." },
                    style = MaterialTheme.typography.bodyMedium,
                    color = TextSecondaryLight,
                    fontSize = 14.sp
                )
            }

            Spacer(modifier = Modifier.height(spacing_sm))

            Row(verticalAlignment = Alignment.Top) {
                Text(
                    text = if (outdoorTemp > 0) "$outdoorTemp" else "--",
                    style = OutdoorTempStyle,
                    color = TextPrimaryLight,
                    fontSize = 48.sp,
                    fontWeight = FontWeight.Normal
                )
                Text(
                    text = "°",
                    style = UnitLargeTextStyle,
                    fontSize = 24.sp,
                    modifier = Modifier.padding(top = 4.dp)
                )
                Spacer(modifier = Modifier.width(spacing_sm))
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    // 天气图标
                    if (weather.isNotEmpty()) {
                        WeatherIcon(
                            weather = weather,
                            modifier = Modifier.size(32.dp)
                        )
                    }
                    Text(
                        text = weather.ifEmpty { "--" },
                        style = MaterialTheme.typography.bodyLarge,
                        color = TextSecondaryLight,
                        fontSize = 16.sp
                    )
                }
            }
        }

        Column(
            horizontalAlignment = Alignment.End,
            verticalArrangement = Arrangement.Top
        ) {
            // AQI 主显示区域 - 大数字 + 等级标签
            Row(
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.End
            ) {
                // AQI 数值 - 大号字体，醒目显示
                Text(
                    text = "$aqi",
                    style = MaterialTheme.typography.headlineLarge,
                    fontWeight = FontWeight.Bold,
                    color = getAqiColor(aqi),
                    fontSize = 36.sp,
                    lineHeight = 36.sp
                )
                Spacer(modifier = Modifier.width(6.dp))
                // AQI 等级标签 - 圆角背景
                AqiLevelBadge(aqi = aqi)
            }

            Spacer(modifier = Modifier.height(8.dp))

            // PM2.5 和湿度 - 水平排列，更紧凑
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End
            ) {
                // PM2.5
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "PM2.5",
                        style = MaterialTheme.typography.bodySmall,
                        color = TextTertiaryLight,
                        fontSize = 11.sp
                    )
                    Spacer(modifier = Modifier.width(2.dp))
                    Text(
                        text = "$pm25",
                        style = MaterialTheme.typography.bodySmall,
                        fontWeight = FontWeight.Medium,
                        color = TextSecondaryLight,
                        fontSize = 13.sp
                    )
                }

                Spacer(modifier = Modifier.width(10.dp))

                // 分隔点
                Box(
                    modifier = Modifier
                        .size(3.dp)
                        .background(TextTertiaryLight.copy(alpha = 0.5f), CircleShape)
                )

                Spacer(modifier = Modifier.width(10.dp))

                // 湿度
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "湿度",
                        style = MaterialTheme.typography.bodySmall,
                        color = TextTertiaryLight,
                        fontSize = 11.sp
                    )
                    Spacer(modifier = Modifier.width(2.dp))
                    Text(
                        text = "$humidity%",
                        style = MaterialTheme.typography.bodySmall,
                        fontWeight = FontWeight.Medium,
                        color = TextSecondaryLight,
                        fontSize = 13.sp
                    )
                }
            }
        }
    }
}

@Composable
private fun ResidenceCard(
    residenceName: String,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(
                elevation = 8.dp,
                shape = RoundedCornerShape(20.dp),
                spotColor = PrimaryBlue.copy(alpha = 0.12f),
                ambientColor = PrimaryBlue.copy(alpha = 0.05f)
            )
            .clip(RoundedCornerShape(20.dp))
            .background(
                Brush.linearGradient(
                    colors = listOf(
                        Color(0xFFF8FBFF),
                        Color(0xFFEEF6FC)
                    )
                )
            )
            .border(
                width = 1.dp,
                color = Color.White.copy(alpha = 0.8f),
                shape = RoundedCornerShape(20.dp)
            )
            .clickable(onClick = onClick)
            .padding(horizontal = 24.dp, vertical = 28.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "CURRENT RESIDENCE",
                    style = MaterialTheme.typography.bodySmall,
                    color = PrimaryBlue,
                    fontSize = 11.sp,
                    letterSpacing = 1.sp
                )
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = residenceName,
                    style = MaterialTheme.typography.titleLarge,
                    color = TextPrimaryLight,
                    fontWeight = FontWeight.Bold,
                    fontSize = 22.sp
                )
            }

            Box(
                modifier = Modifier
                    .size(48.dp)
                    .shadow(
                        elevation = 4.dp,
                        shape = CircleShape,
                        spotColor = PrimaryBlue.copy(alpha = 0.25f)
                    )
                    .clip(CircleShape)
                    .background(PrimaryBlue),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Home,
                    contentDescription = null,
                    modifier = Modifier.size(24.dp),
                    tint = Color.White
                )
            }
        }
    }
}

@Composable
private fun ModeSelector(
    selectedMode: ClimateMode,
    onModeSelected: (ClimateMode) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(
                elevation = 2.dp,
                shape = RoundedCornerShape(28.dp),
                spotColor = Color.Black.copy(alpha = 0.06f)
            )
            .clip(RoundedCornerShape(28.dp))
            .background(Color.White)
            .padding(4.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            ModeTab(
                iconRes = R.drawable.ic_snowflake,
                label = "除湿/制冷",
                selected = selectedMode == ClimateMode.COOLING,
                onClick = { onModeSelected(ClimateMode.COOLING) },
                modifier = Modifier.weight(1f)
            )
            ModeTab(
                iconRes = R.drawable.ic_wind,
                label = "通风",
                selected = selectedMode == ClimateMode.VENTILATION,
                onClick = { onModeSelected(ClimateMode.VENTILATION) },
                modifier = Modifier.weight(1f)
            )
            ModeTab(
                iconRes = R.drawable.ic_sun,
                label = "供暖/加湿",
                selected = selectedMode == ClimateMode.HEATING,
                onClick = { onModeSelected(ClimateMode.HEATING) },
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
private fun ModeTab(
    iconRes: Int,
    label: String,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val scale by animateFloatAsState(
        targetValue = if (selected) 0.95f else 1f,
        animationSpec = tween(durationMillis = 200),
        label = "scale"
    )

    Box(
        modifier = modifier
            .scale(scale)
            .shadow(
                elevation = if (selected) 8.dp else 0.dp,
                shape = RoundedCornerShape(24.dp),
                spotColor = if (selected) PrimaryBlue.copy(alpha = 0.35f) else Color.Transparent,
                ambientColor = if (selected) PrimaryBlue.copy(alpha = 0.15f) else Color.Transparent
            )
            .clip(RoundedCornerShape(24.dp))
            .background(if (selected) Color.White else Color.Transparent)
            .clickable(onClick = onClick)
            .padding(horizontal = 8.dp, vertical = 12.dp),
        contentAlignment = Alignment.Center
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(
                painter = painterResource(id = iconRes),
                contentDescription = null,
                modifier = Modifier.size(18.dp),
                tint = if (selected) PrimaryBlue else TextSecondaryLight
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = label,
                style = MaterialTheme.typography.bodySmall,
                color = if (selected) PrimaryBlue else TextSecondaryLight,
                fontWeight = if (selected) FontWeight.Medium else FontWeight.Normal,
                fontSize = 13.sp
            )
        }
    }
}

@Composable
private fun EnvironmentDataCard(
    indoorTemp: String,
    indoorHumidity: String,
    co2: Int,
    pm25: Int,
    tovc: String
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(
                elevation = 2.dp,
                shape = RoundedCornerShape(16.dp),
                spotColor = Color.Black.copy(alpha = 0.06f)
            )
            .clip(RoundedCornerShape(16.dp))
            .background(Color.White)
            .padding(16.dp)
    ) {
        Column {
            // 第一行：室内温度、室内湿度、CO2
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                EnvironmentDataItem(
                    label = "室内温度",
                    value = indoorTemp,
                    unit = "°",
                    modifier = Modifier.weight(1f)
                )
                Box(
                    modifier = Modifier
                        .width(1.dp)
                        .height(50.dp)
                        .background(DividerLight)
                        .align(Alignment.CenterVertically)
                )
                EnvironmentDataItem(
                    label = "室内湿度",
                    value = indoorHumidity,
                    unit = "%",
                    modifier = Modifier.weight(1f)
                )
                Box(
                    modifier = Modifier
                        .width(1.dp)
                        .height(50.dp)
                        .background(DividerLight)
                        .align(Alignment.CenterVertically)
                )
                EnvironmentDataItem(
                    label = "CO₂",
                    value = "$co2",
                    unit = "",
                    valueColor = getCo2Color(co2),
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))
            
            // 分隔线
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(DividerLight)
            )
            
            Spacer(modifier = Modifier.height(16.dp))

            // 第二行：PM2.5、TOVC（平分宽度，各自居中）
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                EnvironmentDataItem(
                    label = "PM2.5",
                    value = "$pm25",
                    unit = "",
                    valueColor = getPm25Color(pm25),
                    modifier = Modifier.weight(1f)
                )
                Box(
                    modifier = Modifier
                        .width(1.dp)
                        .height(50.dp)
                        .background(DividerLight)
                        .align(Alignment.CenterVertically)
                )
                EnvironmentDataItem(
                    label = "TOVC",
                    value = tovc,
                    unit = "mg/m³",
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

@Composable
private fun EnvironmentDataItem(
    label: String,
    value: String,
    unit: String,
    modifier: Modifier = Modifier,
    valueColor: Color = TextPrimaryLight
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall,
            color = TextSecondaryLight,
            fontSize = 13.sp
        )
        Spacer(modifier = Modifier.height(4.dp))
        Row(
            verticalAlignment = Alignment.Bottom
        ) {
            Text(
                text = value,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Normal,
                color = valueColor,
                fontSize = 28.sp
            )
            if (unit.isNotEmpty()) {
                Text(
                    text = unit,
                    style = MaterialTheme.typography.bodyMedium,
                    color = TextSecondaryLight,
                    fontSize = 14.sp,
                    modifier = Modifier.padding(bottom = 4.dp)
                )
            }
        }
    }
}

@Composable
private fun SceneSection(
    scenes: List<SceneItem>,
    selectedSceneType: SceneType?,
    onSceneSelected: (SceneType) -> Unit
) {
    Column {
        Text(
            text = "智能场景",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.SemiBold,
            color = PrimaryBlue,
            fontSize = 16.sp
        )
        Spacer(modifier = Modifier.height(12.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            scenes.forEach { scene ->
                // 使用key为每个场景按钮提供稳定标识
                key(scene.type) {
                    SceneButton(
                        scene = scene,
                        isSelected = scene.type == selectedSceneType,
                        onClick = { onSceneSelected(scene.type) },
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }
    }
}

@Composable
private fun SceneButton(
    scene: SceneItem,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    // 优化: 使用remember缓存颜色和图标资源，避免每次重组时重新计算
    val backgroundColor by remember(scene.type) {
        derivedStateOf {
            when (scene.type) {
                SceneType.MEETING -> SceneMeeting
                SceneType.AWAY -> SceneAway
                SceneType.SLEEP -> SceneSleep
                SceneType.GUARD -> SceneGuard
                else -> PrimaryBlue
            }
        }
    }

    val iconRes by remember(scene.type) {
        derivedStateOf {
            when (scene.type) {
                SceneType.MEETING -> R.drawable.ic_scene_meeting
                SceneType.AWAY -> R.drawable.ic_scene_away
                SceneType.SLEEP -> R.drawable.ic_scene_sleep
                SceneType.GUARD -> R.drawable.ic_scene_eco
                else -> R.drawable.ic_scene_meeting
            }
        }
    }

    // 优化: 使用animateColorAsState平滑过渡选中状态变化
    val animatedBackgroundColor by animateColorAsState(
        targetValue = if (isSelected) backgroundColor else Color.White,
        animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy),
        label = "sceneBackground"
    )

    val animatedContentColor by animateColorAsState(
        targetValue = if (isSelected) Color.White else TextPrimaryLight,
        animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy),
        label = "sceneContent"
    )

    val shadowColor by animateColorAsState(
        targetValue = if (isSelected) backgroundColor.copy(alpha = 0.3f) else Color.Black.copy(alpha = 0.08f),
        animationSpec = spring(dampingRatio = Spring.DampingRatioNoBouncy),
        label = "sceneShadow"
    )

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .shadow(
                elevation = if (isSelected) 4.dp else 2.dp,
                shape = RoundedCornerShape(16.dp),
                spotColor = shadowColor
            )
            .clip(RoundedCornerShape(16.dp))
            .background(animatedBackgroundColor)
            .clickable(onClick = onClick)
            .padding(vertical = 16.dp, horizontal = 8.dp)
    ) {
        Image(
            painter = painterResource(id = iconRes),
            contentDescription = scene.name,
            modifier = Modifier.size(32.dp),
            // 优化: 使用colorFilter来动态改变图标颜色
            colorFilter = if (isSelected) {
                androidx.compose.ui.graphics.ColorFilter.tint(Color.White)
            } else null
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = scene.name,
            style = MaterialTheme.typography.bodySmall,
            color = animatedContentColor,
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium
        )
    }
}

@Composable
private fun VacationModeCard(
    onClick: () -> Unit,
    startTime: String? = null
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(
                elevation = 2.dp,
                shape = RoundedCornerShape(16.dp),
                spotColor = Color.Black.copy(alpha = 0.06f)
            )
            .clip(RoundedCornerShape(16.dp))
            .background(Color.White)
            .clickable(onClick = onClick)
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_scene_vacation),
                contentDescription = null,
                modifier = Modifier.size(48.dp)
            )

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "预冷\\预热",
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Medium,
                    color = TextPrimaryLight,
                    fontSize = 16.sp
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = startTime?.let { "预计启动时间：$it" } ?: "点击设置预冷\\预热",
                    style = MaterialTheme.typography.bodySmall,
                    color = TextTertiaryLight,
                    fontSize = 12.sp
                )
            }

            Icon(
                imageVector = Icons.Filled.KeyboardArrowRight,
                contentDescription = null,
                modifier = Modifier.size(20.dp),
                tint = TextTertiaryLight
            )
        }
    }
}

/**
 * AQI 等级标签组件 - 带圆角背景和对应颜色
 * 参考 Apple Weather 和小米天气的设计风格
 */
@Composable
private fun AqiLevelBadge(aqi: Int) {
    val (backgroundColor, textColor) = getAqiBadgeColors(aqi)
    val levelText = getAqiLevel(aqi)

    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(10.dp))
            .background(backgroundColor)
            .padding(horizontal = 8.dp, vertical = 3.dp)
    ) {
        Text(
            text = levelText,
            style = MaterialTheme.typography.bodySmall,
            fontWeight = FontWeight.SemiBold,
            color = textColor,
            fontSize = 12.sp
        )
    }
}

/**
 * 获取 AQI 标签的背景色和文字色
 * 背景使用对应颜色的浅色版本，文字使用深色版本以确保可读性
 */
private fun getAqiBadgeColors(aqi: Int): Pair<Color, Color> {
    return when {
        aqi <= 50 -> Pair(
            AirQualityExcellent.copy(alpha = 0.15f),
            AirQualityExcellent.copy(alpha = 0.9f)
        )
        aqi <= 100 -> Pair(
            AirQualityGood.copy(alpha = 0.15f),
            AirQualityGood.copy(alpha = 0.9f)
        )
        aqi <= 150 -> Pair(
            AirQualityModerate.copy(alpha = 0.15f),
            AirQualityModerate.copy(alpha = 0.9f)
        )
        aqi <= 200 -> Pair(
            AirQualityPoor.copy(alpha = 0.15f),
            AirQualityPoor.copy(alpha = 0.9f)
        )
        else -> Pair(
            AirQualityBad.copy(alpha = 0.15f),
            AirQualityBad.copy(alpha = 0.9f)
        )
    }
}

private fun getAqiColor(aqi: Int): Color {
    return when {
        aqi <= 50 -> AirQualityExcellent
        aqi <= 100 -> AirQualityGood
        aqi <= 150 -> AirQualityModerate
        aqi <= 200 -> AirQualityPoor
        else -> AirQualityBad
    }
}

private fun getAqiLevel(aqi: Int): String {
    return when {
        aqi <= 50 -> "优"
        aqi <= 100 -> "良"
        aqi <= 150 -> "轻度"
        aqi <= 200 -> "中度"
        else -> "重度"
    }
}

private fun getCo2Color(co2: Int): Color {
    return when {
        co2 < 800 -> SuccessGreen      // <800 绿色
        co2 <= 1000 -> WarningYellow   // 800-1000 黄色
        else -> ErrorRed               // >1000 红色
    }
}

private fun getPm25Color(pm25: Int): Color {
    return when {
        pm25 <= 35 -> SuccessGreen
        pm25 <= 75 -> WarningYellow
        else -> ErrorRed
    }
}
