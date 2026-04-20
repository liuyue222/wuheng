package com.wuheng.smart.presentation.home

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
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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

    val mainScenes = uiState.scenes.filter { scene ->
        scene.type in listOf(SceneType.MEETING, SceneType.AWAY, SceneType.SLEEP, SceneType.GUARD)
    }.take(4)

    // 使用天气背景
    WeatherBackground(weather = uiState.weather) {
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
        item {
            WeatherHeader(
                location = uiState.location,
                outdoorTemp = uiState.outdoorTemp,
                weather = uiState.weather,
                aqi = uiState.aqi,
                pm25 = uiState.pm25,
                humidity = uiState.outdoorHumidity
            )
        }

        item {
            Spacer(modifier = Modifier.height(spacing_lg))
            ResidenceCard(
                residenceName = uiState.residenceName,
                onClick = onResidenceClick
            )
        }

        item {
            Spacer(modifier = Modifier.height(spacing_lg))
            ModeSelector(
                selectedMode = uiState.currentMode,
                onModeSelected = onModeSelected
            )
        }

        item {
            Spacer(modifier = Modifier.height(spacing_lg))
            EnvironmentDataCard(
                indoorTemp = uiState.indoorTemp,
                indoorHumidity = uiState.indoorHumidity,
                co2 = uiState.co2,
                pm25 = uiState.pm25,
                tovc = uiState.tovc
            )
        }

        if (mainScenes.isNotEmpty()) {
            item {
                Spacer(modifier = Modifier.height(spacing_lg))
                SceneSection(
                    scenes = mainScenes,
                    onSceneSelected = onSceneSelected
                )
            }
        }

        if (vacationModeEnabled) {
            item {
                Spacer(modifier = Modifier.height(spacing_lg))
                VacationModeCard(
                    onClick = onVacationModeClick,
                    startTime = vacationStartTime
                )
            }
        }

        item {
            Spacer(modifier = Modifier.height(spacing_xl))
        }
    }
    }
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
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(spacing_xs))
                Text(
                    text = location.ifEmpty { "定位中..." },
                    style = MaterialTheme.typography.bodyMedium,
                    color = TextPrimaryLight,
                    fontSize = 14.sp
                )
            }

            Spacer(modifier = Modifier.height(spacing_sm))

            Row(verticalAlignment = Alignment.Top) {
                Text(
                    text = if (outdoorTemp > 0) "$outdoorTemp" else "--",
                    style = OutdoorTempStyle,
                    color = TextPrimaryLight
                )
                Text(
                    text = "°",
                    style = UnitLargeTextStyle,
                    modifier = Modifier.padding(top = 4.dp)
                )
                Spacer(modifier = Modifier.width(spacing_sm))
                Text(
                    text = weather,
                    style = MaterialTheme.typography.bodyLarge,
                    color = TextSecondaryLight,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
        }

        Column(horizontalAlignment = Alignment.End) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End
            ) {
                Text(
                    text = "AQI ",
                    style = MaterialTheme.typography.bodySmall,
                    color = TextTertiaryLight,
                    fontSize = 12.sp
                )
                Text(
                    text = "$aqi",
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold,
                    color = getAqiColor(aqi),
                    fontSize = 18.sp
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = getAqiLevel(aqi),
                    style = MaterialTheme.typography.bodySmall,
                    color = getAqiColor(aqi),
                    fontSize = 12.sp
                )
            }

            Spacer(modifier = Modifier.height(2.dp))

            Text(
                text = "PM2.5 $pm25",
                style = MaterialTheme.typography.bodySmall,
                color = TextTertiaryLight,
                fontSize = 12.sp
            )

            Text(
                text = "湿度 ${humidity}%",
                style = MaterialTheme.typography.bodySmall,
                color = TextTertiaryLight,
                fontSize = 12.sp
            )
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
                elevation = 12.dp,
                shape = CardLargeShape,
                spotColor = PrimaryBlue.copy(alpha = 0.15f),
                ambientColor = PrimaryBlue.copy(alpha = 0.05f)
            )
            .clip(CardLargeShape)
            .background(
                Brush.linearGradient(
                    colors = listOf(
                        Color(0xFFF5FAFF),
                        Color(0xFFE8F4FD)
                    )
                )
            )
            .border(
                width = 1.dp,
                color = Color.White.copy(alpha = 0.8f),
                shape = CardLargeShape
            )
            .clickable(onClick = onClick)
            .padding(card_padding_large)
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
                Spacer(modifier = Modifier.height(spacing_xs))
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
                    .size(44.dp)
                    .shadow(
                        elevation = 4.dp,
                        shape = CircleShape,
                        spotColor = PrimaryBlue.copy(alpha = 0.3f)
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
                elevation = 4.dp,
                shape = RoundedCornerShape(24.dp),
                spotColor = Color.Black.copy(alpha = 0.08f)
            )
            .clip(RoundedCornerShape(24.dp))
            .background(Color.White)
            .padding(6.dp)
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
    Box(
        modifier = modifier
            .shadow(
                elevation = if (selected) 6.dp else 0.dp,
                shape = RoundedCornerShape(18.dp),
                spotColor = if (selected) PrimaryBlue.copy(alpha = 0.25f) else Color.Transparent
            )
            .clip(RoundedCornerShape(18.dp))
            .background(if (selected) Color.White else Color.Transparent)
            .clickable(onClick = onClick)
            .padding(horizontal = 8.dp, vertical = 10.dp),
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
                fontSize = 12.sp
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
    WuHengCard(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(card_padding_default)
        ) {
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

            Spacer(modifier = Modifier.height(spacing_md))
            WuHengDivider()
            Spacer(modifier = Modifier.height(spacing_md))

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
private fun SceneSection(
    scenes: List<SceneItem>,
    onSceneSelected: (SceneType) -> Unit
) {
    Column {
        Text(
            text = "智能场景",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.SemiBold,
            color = TextPrimaryLight
        )
        Spacer(modifier = Modifier.height(spacing_md))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            scenes.forEach { scene ->
                SceneButton(
                    scene = scene,
                    onClick = { onSceneSelected(scene.type) },
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

@Composable
private fun SceneButton(
    scene: SceneItem,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val backgroundColor = when (scene.type) {
        SceneType.MEETING -> SceneMeeting
        SceneType.AWAY -> SceneAway
        SceneType.SLEEP -> SceneSleep
        SceneType.GUARD -> SceneGuard
        else -> PrimaryBlue
    }

    val iconRes = when (scene.type) {
        SceneType.MEETING -> R.drawable.ic_scene_meeting
        SceneType.AWAY -> R.drawable.ic_scene_away
        SceneType.SLEEP -> R.drawable.ic_scene_sleep
        SceneType.GUARD -> R.drawable.ic_scene_eco
        else -> R.drawable.ic_scene_meeting
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .shadow(
                elevation = 3.dp,
                shape = SceneButtonShape,
                spotColor = if (scene.isSelected) backgroundColor.copy(alpha = 0.4f) else Color.Black.copy(alpha = 0.1f)
            )
            .clip(SceneButtonShape)
            .background(if (scene.isSelected) backgroundColor else Color.White)
            .clickable(onClick = onClick)
            .padding(vertical = 12.dp, horizontal = 8.dp)
    ) {
        Image(
            painter = painterResource(id = iconRes),
            contentDescription = scene.name,
            modifier = Modifier.size(32.dp)
        )
        Spacer(modifier = Modifier.height(6.dp))
        Text(
            text = scene.name,
            style = MaterialTheme.typography.bodySmall,
            color = if (scene.isSelected) Color.White else TextPrimaryLight,
            fontSize = 11.sp,
            fontWeight = FontWeight.Medium
        )
    }
}

@Composable
private fun VacationModeCard(
    onClick: () -> Unit,
    startTime: String? = null
) {
    WuHengCard(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(card_padding_default),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_scene_vacation),
                contentDescription = null,
                modifier = Modifier.size(48.dp)
            )

            Spacer(modifier = Modifier.width(spacing_md))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "度假模式",
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Medium,
                    color = TextPrimaryLight
                )
                Spacer(modifier = Modifier.height(spacing_xs))
                Text(
                    text = startTime?.let { "预计启动时间：$it" } ?: "点击设置返程时间",
                    style = MaterialTheme.typography.bodySmall,
                    color = TextTertiaryLight
                )
            }

            Icon(
                imageVector = Icons.Filled.KeyboardArrowRight,
                contentDescription = null,
                modifier = Modifier.size(icon_size_default),
                tint = TextTertiaryLight
            )
        }
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
