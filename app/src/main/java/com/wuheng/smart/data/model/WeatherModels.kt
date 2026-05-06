package com.wuheng.smart.data.model

import com.google.gson.annotations.SerializedName

// ==================== 天气模块数据模型 ====================

/**
 * 天气数据（完整版）
 * 对应接口: GET /home/weather/getWeather
 */
data class WeatherData(
    @SerializedName("location")
    val location: String,
    @SerializedName("latitude")
    val latitude: String,
    @SerializedName("longitude")
    val longitude: String,
    @SerializedName("temperature")
    val temperature: String,
    @SerializedName("weather_code")
    val weatherCode: String,
    @SerializedName("weather_desc")
    val weatherDesc: String,
    @SerializedName("humidity")
    val humidity: String,
    @SerializedName("wind_speed")
    val windSpeed: String,
    @SerializedName("wind_direction")
    val windDirection: String,
    @SerializedName("visibility")
    val visibility: String,
    @SerializedName("uv_index")
    val uvIndex: String,
    @SerializedName("aqi")
    val aqi: Int,
    @SerializedName("aqi_level")
    val aqiLevel: String,
    @SerializedName("pm25")
    val pm25: Int,
    @SerializedName("pm10")
    val pm10: Int,
    @SerializedName("forecast")
    val forecast: List<WeatherForecast>? = null
)

/**
 * 天气预报
 */
data class WeatherForecast(
    @SerializedName("date")
    val date: String,
    @SerializedName("max_temp")
    val maxTemp: String,
    @SerializedName("min_temp")
    val minTemp: String,
    @SerializedName("weather_desc")
    val weatherDesc: String
)

/**
 * 室外环境数据
 * 对应接口: GET /home/weather/getOutdoorEnv
 */
data class OutdoorEnv(
    @SerializedName("outdoor_temp")
    val outdoorTemp: String,
    @SerializedName("outdoor_humidity")
    val outdoorHumidity: String,
    @SerializedName("outdoor_aqi")
    val outdoorAqi: Int,
    @SerializedName("outdoor_pm25")
    val outdoorPm25: Int
)
