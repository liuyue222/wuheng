package com.wuheng.smart.data.model

import com.google.gson.annotations.SerializedName

// 冷暖系统概览
data class ClimateOverview(
    @SerializedName("currentTemperature")
    val currentTemperature: Double = 0.0,
    @SerializedName("targetTemperature")
    val targetTemperature: Double = 0.0,
    @SerializedName("currentMode")
    val currentMode: ClimateMode = ClimateMode.OFF,
    @SerializedName("isRunning")
    val isRunning: Boolean = false,
    @SerializedName("floorCount")
    val floorCount: Int = 0,
    @SerializedName("zoneCount")
    val zoneCount: Int = 0,
    @SerializedName("runningZoneCount")
    val runningZoneCount: Int = 0,
    // ========== 新增：全屋平均湿度 ==========
    @SerializedName("averageHumidity")
    val averageHumidity: Int = 50,            // 全屋平均湿度百分比
    // ========== 新增：室外温度（用于显示对比）==========
    @SerializedName("outdoorTemperature")
    val outdoorTemperature: Double? = null     // 室外温度
)

enum class ClimateMode {
    OFF,        // 关闭
    COOLING,    // 制冷
    HEATING,    // 制热
    VENTILATION, // 通风
    DEHUMIDIFICATION, // 除湿
    AUTO        // 自动
}

// ==================== 辐射模式（新增）====================

/**
 * 辐射类型枚举
 * 用于冷暖系统的辐射末端控制（地暖/顶面辐射）
 */
enum class RadiationType {
    CEILING,    // 顶面辐射 (毛细管/风机盘管)
    FLOOR,      // 地面辐射 (地暖)
    BOTH,       // 混合模式 (顶面+地面)
    NONE        // 无辐射 (纯对流)
}

/**
 * 辐射模式配置
 */
data class RadiationConfig(
    @SerializedName("isEnabled")
    val isEnabled: Boolean = false,           // 是否启用辐射模式
    @SerializedName("radiationType")
    val radiationType: RadiationType = RadiationType.FLOOR,
    @SerializedName("ceilingTemp")
    val ceilingTemp: Float? = null,           // 顶面辐射目标温度
    @SerializedName("floorTemp")
    val floorTemp: Float? = null              // 地面辐射目标温度
)

// 楼层
data class Floor(
    @SerializedName("id")
    val id: String = "",
    @SerializedName("name")
    val name: String = "",
    @SerializedName("order")
    val order: Int = 0,
    @SerializedName("zoneCount")
    val zoneCount: Int = 0,
    @SerializedName("runningZoneCount")
    val runningZoneCount: Int = 0,
    @SerializedName("averageTemperature")
    val averageTemperature: Double = 0.0
)

// 区域
data class Zone(
    @SerializedName("id")
    val id: String = "",
    @SerializedName("name")
    val name: String = "",
    @SerializedName("floorId")
    val floorId: String = "",
    @SerializedName("currentTemperature")
    val currentTemperature: Double = 0.0,
    @SerializedName("targetTemperature")
    val targetTemperature: Double = 0.0,
    @SerializedName("mode")
    val mode: ClimateMode = ClimateMode.OFF,
    @SerializedName("isRunning")
    val isRunning: Boolean = false,
    @SerializedName("isOnline")
    val isOnline: Boolean = false
)

// 区域详情
/**
 * 区域详情（增强版）
 *
 * UI组件映射：
 * - 房间Chip选择器: name, floorName, isOnline
 * - 温度/湿度设定卡片: currentTemperature, targetTemperature, humidity, targetHumidity
 * - 风速选择器: fanSpeed
 * - 辐射模式开关: radiationConfig
 */
data class ZoneDetail(
    @SerializedName("id")
    val id: String = "",
    @SerializedName("name")
    val name: String = "",
    @SerializedName("floorId")
    val floorId: String = "",
    @SerializedName("floorName")
    val floorName: String = "",
    // ========== 温度控制 ==========
    @SerializedName("currentTemperature")
    val currentTemperature: Double = 0.0,
    @SerializedName("targetTemperature")
    val targetTemperature: Double = 0.0,
    // ========== 湿度控制（增强）==========
    @SerializedName("humidity")
    val humidity: Int = 0,                     // 当前湿度
    @SerializedName("targetHumidity")
    val targetHumidity: Int = 50,              // 目标湿度 (新增)
    // ========== 运行状态 ==========
    @SerializedName("mode")
    val mode: ClimateMode = ClimateMode.OFF,
    @SerializedName("isRunning")
    val isRunning: Boolean = false,
    @SerializedName("isOnline")
    val isOnline: Boolean = false,
    // ========== 风速控制 ==========
    @SerializedName("fanSpeed")
    val fanSpeed: FanSpeed = FanSpeed.AUTO,
    // ========== 辐射模式（新增）==========
    @SerializedName("radiationConfig")
    val radiationConfig: RadiationConfig? = null,
    // ========== 定时任务 ==========
    @SerializedName("scheduleEnabled")
    val scheduleEnabled: Boolean = false,
    @SerializedName("scheduleInfo")
    val scheduleInfo: String? = null
)

enum class FanSpeed {
    LOW,
    MEDIUM,
    HIGH,
    AUTO
}

// 请求体
data class TemperatureRequest(
    @SerializedName("temperature")
    val temperature: Double = 0.0
)

data class ModeRequest(
    @SerializedName("mode")
    val mode: ClimateMode = ClimateMode.OFF
)

data class PowerRequest(
    @SerializedName("powerOn")
    val powerOn: Boolean = false
)
