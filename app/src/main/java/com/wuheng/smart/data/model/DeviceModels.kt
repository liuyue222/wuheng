package com.wuheng.smart.data.model

import com.google.gson.annotations.SerializedName

// ==================== 设备模块数据模型 ====================

/**
 * 设备信息
 */
data class DeviceInfo(
    @SerializedName("device_id")
    val deviceId: Int,
    @SerializedName("device_id_no")
    val deviceIdNo: String,
    @SerializedName("device_name")
    val deviceName: String,
    @SerializedName("device_type")
    val deviceType: String,
    @SerializedName("device_model")
    val deviceModel: String,
    @SerializedName("online_status")
    val onlineStatus: Int,
    @SerializedName("run_status")
    val runStatus: String,
    @SerializedName("room_name")
    val roomName: String
)

/**
 * 设备实时数据
 */
data class DeviceData(
    @SerializedName("data_id")
    val dataId: Int,
    @SerializedName("device_id")
    val deviceId: Int,
    @SerializedName("temperature")
    val temperature: String,
    @SerializedName("humidity")
    val humidity: String,
    @SerializedName("co2")
    val co2: Int,
    @SerializedName("pm25")
    val pm25: Int,
    @SerializedName("voc")
    val voc: Int,
    @SerializedName("fan_speed")
    val fanSpeed: Int,
    @SerializedName("valve_open")
    val valveOpen: Int,
    @SerializedName("power")
    val power: Int,
    @SerializedName("report_time")
    val reportTime: Long
)

/**
 * 控制设备请求
 */
data class ControlDeviceRequest(
    @SerializedName("device_id")
    val deviceId: Int,
    @SerializedName("command")
    val command: String,
    @SerializedName("value")
    val value: String? = null
)

/**
 * 控制设备响应
 */
data class ControlDeviceResponse(
    @SerializedName("command")
    val command: String,
    @SerializedName("value")
    val value: String,
    @SerializedName("device_id")
    val deviceId: Int
)

/**
 * 设备控制命令枚举
 */
enum class DeviceCommand(val value: String) {
    ON("on"),
    OFF("off"),
    TEMP_UP("temp_up"),
    TEMP_DOWN("temp_down"),
    SET_TEMP("set_temp");

    companion object {
        fun fromValue(value: String): DeviceCommand {
            return values().find { it.value == value } ?: ON
        }
    }
}

/**
 * 设备运行状态枚举
 */
enum class DeviceRunStatus(val value: String) {
    RUNNING("running"),
    STOPPED("stopped"),
    STANDBY("standby"),
    ERROR("error"),
    OFFLINE("offline");

    companion object {
        fun fromValue(value: String): DeviceRunStatus {
            return values().find { it.value == value } ?: OFFLINE
        }
    }
}
