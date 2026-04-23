package com.wuheng.smart.data.model

import com.google.gson.annotations.SerializedName

// ==================== 首页概览数据 ====================

/**
 * 温度控制范围配置常量
 */
object TemperatureConfig {
    const val COOLING_MIN_TEMP = 16.0  // 制冷模式最低温度
    const val COOLING_MAX_TEMP = 30.0  // 制冷模式最高温度
    const val HEATING_MIN_TEMP = 16.0  // 制热模式最低温度
    const val HEATING_MAX_TEMP = 32.0  // 制热模式最高温度
    const val TEMP_STEP = 0.5          // 温度调节步进值（摄氏度）
}

/**
 * 湿度控制范围配置常量
 */
object HumidityConfig {
    const val MIN_HUMIDITY = 30        // 最低湿度
    const val MAX_HUMIDITY = 70        // 最高湿度
    const val HUMIDITY_STEP = 5       // 湿度调节步进值
}

/**
 * 首页概览数据
 * 包含住宅整体状态、环境参数、运行场景等信息
 *
 * UI组件映射：
 * - 环境数据卡片(5项): indoorTemperature, indoorHumidity, pm25, co2, voc
 * - 天气信息栏: weatherTemp, weatherDesc, aqiStatus
 * - 西湖一号院卡片: residenceName, address
 * - 场景网格: runningScenes (需要iconResId)
 */
data class HomeOverview(
    @SerializedName("roomCount")
    val roomCount: Int = 0,
    @SerializedName("deviceCount")
    val deviceCount: Int = 0,
    @SerializedName("onlineDeviceCount")
    val onlineDeviceCount: Int = 0,
    // ========== 环境数据（环境数据卡片5项）==========
    @SerializedName("indoorTemperature")
    val indoorTemperature: Double = 0.0,
    @SerializedName("indoorHumidity")
    val indoorHumidity: Int = 0,
    @SerializedName("pm25")
    val pm25: Int = 0,
    @SerializedName("co2")
    val co2: Int = 0,
    @SerializedName("voc")
    val voc: Double = 0.0,
    // ========== 天气信息（天气信息栏）==========
    @SerializedName("weatherTemp")
    val weatherTemp: Float? = null,          // 室外天气温度
    @SerializedName("weatherDesc")
    val weatherDesc: String? = null,         // 天气描述 ("多云"/"晴"/"阴")
    @SerializedName("aqiStatus")
    val aqiStatus: String = "优",            // AQI等级文本 ("优"/"良"/"轻度污染")
    // ========== 住宅信息（西湖壹号院卡片）==========
    @SerializedName("residenceName")
    val residenceName: String = "",          // 住宅名称 ("西湖壹号院")
    @SerializedName("address")
    val address: String = "",                // 详细地址
    @SerializedName("residenceImageResId")
    val residenceImageResId: String? = null, // 住宅背景图片URL或资源标识
    // ========== 运行状态 ==========
    @SerializedName("currentWeatherMode")
    val currentWeatherMode: WeatherMode = WeatherMode.COOLING,
    @SerializedName("runningScenes")
    val runningScenes: List<Scene> = emptyList(),
    @SerializedName("recentDevices")
    val recentDevices: List<Device> = emptyList()
)

// ==================== 场景模式 ====================

/**
 * 智能场景
 *
 * UI组件映射：
 * - 2x2场景网格: name, iconResId, isRunning
 *
 * 四种核心场景定义（来自控制系统文档）:
 * - 会客模式: 温度24°C, 湿度50%, 风速自动
 * - 离家模式: 关闭所有设备, 开启安防
 * - 睡眠模式: 温度26°C, 湿度45%, 风速低
 * - 值守模式: 最低功耗运行
 */
data class Scene(
    @SerializedName("id")
    val id: String = "",
    @SerializedName("name")
    val name: String = "",
    @SerializedName("icon")
    val icon: String = "",                    // 图标名称/标识符
    @SerializedName("iconResId")
    val iconResId: Int? = null,               // 图标资源ID (用于Compose Image)
    @SerializedName("isRunning")
    val isRunning: Boolean = false,
    // ========== 场景预设参数（可选，用于高级场景控制）==========
    @SerializedName("presetTemperature")
    val presetTemperature: Float? = null,      // 预设温度
    @SerializedName("presetHumidity")
    val presetHumidity: Int? = null,           // 预设湿度
    @SerializedName("presetFanSpeed")
    val presetFanSpeed: String? = null,        // 预设风速 ("auto"/"low"/"medium"/"high")
    @SerializedName("description")
    val description: String = ""              // 场景描述
)

// ==================== 设备模型 ====================

/**
 * 基础设备信息
 */
data class Device(
    @SerializedName("id")
    val id: String = "",
    @SerializedName("name")
    val name: String = "",
    @SerializedName("type")
    val type: DeviceType = DeviceType.OTHER,
    @SerializedName("status")
    val status: DeviceRunningStatus = DeviceRunningStatus.OFF,
    @SerializedName("roomName")
    val roomName: String = "",
    @SerializedName("isOnline")
    val isOnline: Boolean = false
)

/**
 * 扩展设备信息（用于设备卡片展示）
 * 包含温度、模式等详细运行参数
 */
data class DeviceDetail(
    @SerializedName("id")
    val id: String = "",
    @SerializedName("name")
    val name: String = "",
    @SerializedName("type")
    val type: DeviceType = DeviceType.OTHER,
    @SerializedName("status")
    val status: DeviceRunningStatus = DeviceRunningStatus.OFF,
    @SerializedName("roomName")
    val roomName: String = "",
    @SerializedName("isOnline")
    val isOnline: Boolean = false,
    @SerializedName("currentTemp")
    val currentTemp: Double = 0.0,
    @SerializedName("targetTemp")
    val targetTemp: Double? = null,
    @SerializedName("isCoolingMode")
    val isCoolingMode: Boolean = true
)

/**
 * 设备控制请求
 */
data class DeviceControlRequest(
    @SerializedName("deviceId")
    val deviceId: String,
    @SerializedName("powerOn")
    val powerOn: Boolean? = null,
    @SerializedName("targetTemp")
    val targetTemp: Double? = null,
    @SerializedName("mode")
    val mode: String? = null  // "cooling" | "heating" | "ventilation"
)

// ==================== 天气模式 ====================

/**
 * 系统天气/运行模式枚举
 */
enum class WeatherMode {
    COOLING,      // 制冷
    HEATING,      // 制热
    VENTILATION,  // 通风
    AUTO          // 自动
}

/**
 * 天气模式切换请求
 */
data class WeatherModeRequest(
    @SerializedName("mode")
    val mode: WeatherMode
)

// ==================== 服务类型 ====================

/**
 * 服务类型枚举（对应ServiceGrid组件）
 */
enum class ServiceType {
    HOME_SERVICE,    // 上门服务
    SPACE_SERVICE,   // 空间管理
    ECO_SERVICE,     // 绿植养护
    MORE_SERVICE     // 更多服务
}

// ==================== 设备类型与状态 ====================

enum class DeviceType {
    CLIMATE,    // 冷暖设备
    WATER,      // 水系统设备
    LIGHT,      // 灯光
    CURTAIN,    // 窗帘
    SECURITY,   // 安防
    OTHER       // 其他
}

enum class DeviceRunningStatus {
    ON,
    OFF,
    STANDBY,
    ERROR
}
