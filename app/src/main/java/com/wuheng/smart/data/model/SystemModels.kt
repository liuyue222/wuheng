package com.wuheng.smart.data.model

import com.google.gson.annotations.SerializedName

// ==================== 系统模块数据模型 ====================

/**
 * 系统状态
 */
data class SystemStatus(
    @SerializedName("system_status")
    val systemStatus: SystemStatusInfo,
    @SerializedName("house_info")
    val houseInfo: HouseInfo? = null,
    @SerializedName("device_count")
    val deviceCount: Int,
    @SerializedName("online_count")
    val onlineCount: Int
)

/**
 * 系统状态信息
 */
data class SystemStatusInfo(
    @SerializedName("system_mode")
    val systemMode: String,
    @SerializedName("global_temp_set")
    val globalTempSet: String,
    @SerializedName("global_humidity_set")
    val globalHumiditySet: String,
    @SerializedName("avg_indoor_temp")
    val avgIndoorTemp: String,
    @SerializedName("avg_indoor_humidity")
    val avgIndoorHumidity: String,
    @SerializedName("avg_co2")
    val avgCo2: String? = null,
    @SerializedName("outdoor_temp")
    val outdoorTemp: String? = null,
    @SerializedName("outdoor_humidity")
    val outdoorHumidity: String? = null,
    @SerializedName("outdoor_aqi")
    val outdoorAqi: String? = null,
    @SerializedName("outdoor_pm25")
    val outdoorPm25: String? = null,
    @SerializedName("system_run_status")
    val systemRunStatus: String
)

/**
 * 设置系统模式请求
 */
data class SetSystemModeRequest(
    @SerializedName("house_id")
    val houseId: Int,
    @SerializedName("mode")
    val mode: String
)

/**
 * 设置系统模式响应
 */
data class SetSystemModeResponse(
    @SerializedName("mode")
    val mode: String
)

/**
 * 设置全局温度请求
 */
data class SetGlobalTempRequest(
    @SerializedName("house_id")
    val houseId: Int,
    @SerializedName("temp")
    val temp: String
)

/**
 * 设置全局湿度请求
 */
data class SetGlobalHumidityRequest(
    @SerializedName("house_id")
    val houseId: Int,
    @SerializedName("humidity")
    val humidity: String
)

/**
 * 系统模式枚举
 */
enum class SystemMode(val value: String) {
    COOLING("cooling"),
    HEATING("heating"),
    VENTILATION("ventilation"),
    AUTO("auto"),
    OFF("off");

    companion object {
        fun fromValue(value: String): SystemMode {
            return values().find { it.value == value } ?: AUTO
        }
    }
}

/**
 * 系统运行状态枚举
 */
enum class SystemRunStatus(val value: String) {
    RUNNING("running"),
    STOPPED("stopped"),
    STANDBY("standby"),
    ERROR("error");

    companion object {
        fun fromValue(value: String): SystemRunStatus {
            return values().find { it.value == value } ?: STOPPED
        }
    }
}
