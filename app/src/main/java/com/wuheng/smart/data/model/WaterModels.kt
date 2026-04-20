package com.wuheng.smart.data.model

import com.google.gson.annotations.SerializedName

// ==================== 水系统常量配置 ====================

/**
 * 滤芯状态阈值配置
 */
object FilterConfig {
    const val WARNING_THRESHOLD = 30       // 警告阈值（剩余30%）
    const val REPLACE_THRESHOLD = 10      // 更换阈值（剩余10%）
    const val NORMAL_COLOR = "#4ADE80"    // 正常-绿色
    const val WARNING_COLOR = "#FBBF24"   // 警告-黄色
    const val CRITICAL_COLOR = "#EF4444"  // 急需更换-红色
}

// ==================== 水系统概览（增强版）====================

/**
 * 水系统概览数据（增强版）
 *
 * UI组件映射：
 * - 生活热水循环卡片: currentTemp, cycleMode, temporaryDuration
 * - 热力杀菌卡片: sterilizationSchedule, sterilizationStatus
 * - 全屋净水滤芯: filters
 */
data class WaterOverview(
    @SerializedName("systemStatus")
    val systemStatus: WaterSystemStatus = WaterSystemStatus.NORMAL,
    // ========== 基础参数 ==========
    @SerializedName("inletTemperature")
    val inletTemperature: Double = 0.0,
    @SerializedName("outletTemperature")
    val outletTemperature: Double = 0.0,
    @SerializedName("pressure")
    val pressure: Double = 0.0,
    @SerializedName("flowRate")
    val flowRate: Double = 0.0,
    // ========== 设备统计 ==========
    @SerializedName("deviceCount")
    val deviceCount: Int = 0,
    @SerializedName("runningDeviceCount")
    val runningDeviceCount: Int = 0,
    // ========== 新增：生活热水（用于热水循环卡片）==========
    @SerializedName("currentTemp")
    val currentTemp: Float = 55f,           // 当前水温 (如55°C)
    @SerializedName("cycleMode")
    val cycleMode: CycleMode = CycleMode.OFF, // 当前循环模式
    @SerializedName("temporaryDuration")
    val temporaryDuration: Int = 30,         // 临时循环时长(分钟)，默认30分钟
    // ========== 新增：热力杀菌 ==========
    @SerializedName("sterilizationSchedule")
    val sterilizationSchedule: String? = null, // "每周五 02:00"
    @SerializedName("sterilizationStatus")
    val sterilizationStatus: SterilizationStatus? = null,
    // ========== 新增：滤芯列表 ==========
    @SerializedName("filters")
    val filters: List<FilterInfo> = emptyList(),
    // ========== 新增：水质参数（可选）==========
    @SerializedName("tds")
    val tds: Int? = null,                    // TDS值 (正常<50)
    @SerializedName("ph")
    val ph: Float? = null                    // PH值 (正常6.5-8.5)
)

// ==================== 生活热水循环模式（新增）====================

/**
 * 热水循环模式枚举
 *
 * UI映射：生活热水循环卡片的2x2模式按钮
 * - 全天循环 (CycleMode.ALWAYS)
 * - 定时循环 (CycleMode.SCHEDULE)
 * - 临时循环 (CycleMode.TEMPORARY) - 高亮选中态
 * - 关闭循环 (CycleMode.OFF)
 */
enum class CycleMode {
    ALWAYS,      // 全天循环：24小时保持热水管道温热
    SCHEDULE,    // 定时循环：按设定时间段运行（如06:00-23:00）
    TEMPORARY,   // 临时循环：手动启动，运行指定时长后自动关闭
    OFF          // 关闭循环：不进行管道循环
}

/**
 * 循环模式配置请求
 */
data class CycleModeRequest(
    @SerializedName("mode")
    val mode: CycleMode,
    @SerializedName("duration")              // 仅TEMPORARY模式需要
    val duration: Int? = null                // 运行时长(分钟): 30/60/90/120
)

// ==================== 热力杀菌（新增）====================

/**
 * 热力杀菌状态
 *
 * UI映射：热力杀菌卡片
 * - 显示预约时间、上次执行时间、温度设置
 */
data class SterilizationStatus(
    @SerializedName("isActive")
    val isActive: Boolean = false,           // 是否启用
    @SerializedName("lastExecutedTime")
    val lastExecutedTime: String? = null,    // 上次执行时间 "2026-04-01 03:00"
    @SerializedName("nextScheduledTime")
    val nextScheduledTime: String? = null,   // 下次计划时间 "2026-04-08 03:00"
    @SerializedName("sterilizationTemperature")
    val sterilizationTemperature: Int = 70,  // 杀菌温度(°C)，默认70°C
    @SerializedName("duration")
    val duration: Int = 30,                  // 持续时长(分钟)，默认30分钟
    @SerializedName("scheduleDayOfWeek")
    val scheduleDayOfWeek: Int = 5,          // 每周几执行 (1=周一, 5=周五)
    @SerializedName("scheduleTime")
    val scheduleTime: String = "02:00"       // 执行时间
)

/**
 * 热力杀菌配置请求
 */
data class SterilizationRequest(
    @SerializedName("isEnabled")
    val isEnabled: Boolean,
    @SerializedName("dayOfWeek")
    val dayOfWeek: Int?,                     // 每周几 (1-7)
    @SerializedName("time")
    val time: String?,                       // 时间 "HH:mm"
    @SerializedName("temperature")
    val temperature: Int?,                   // 温度 (°C)
    @SerializedName("duration")
    val duration: Int?                       // 时长(分钟)
)

// ==================== 滤芯信息（从MockData迁移过来）====================

/**
 * 滤芯信息
 *
 * UI映射：全屋净水滤芯卡片 - 进度条动画
 * - 显示滤芯名称、型号、剩余百分比、剩余天数、状态
 *
 * 寿命计算算法说明：
 * - 主要按使用天数计算（totalLifeDays - usedDays）
 * - 可选结合流量计数据（如果设备支持）
 * - 剩余百分比 = (1 - usedDays/totalLifeDays) * 100
 */
data class FilterInfo(
    @SerializedName("id")
    val id: String = "",
    @SerializedName("name")
    val name: String = "",                   // "前置过滤器"
    @SerializedName("filterModel")
    val filterModel: String = "",            // "PP-001"
    @SerializedName("type")
    val type: String = "",                   // "前置过滤" | "中央净水" | "末端直饮"
    @SerializedName("remainingPercentage")
    val remainingPercentage: Float = 100f,   // 剩余寿命百分比 (0-100)
    @SerializedName("remainingDays")
    val remainingDays: Int = 365,            // 剩余天数
    @SerializedName("totalLifeDays")
    val totalLifeDays: Int = 365,            // 总寿命(天)
    @SerializedName("usedDays")
    val usedDays: Int = 0,                   // 已使用天数
    @SerializedName("status")
    val status: FilterStatus = FilterStatus.NORMAL, // 状态枚举
    @SerializedName("lastReplacedDate")
    val lastReplacedDate: String? = null     // 上次更换日期
)

/**
 * 滤芯状态枚举
 */
enum class FilterStatus {
    NORMAL,        // 正常 (>30%)
    WARNING,       // 警告 (10%-30%)
    REPLACE_NOW    // 急需更换 (<10%)
}

enum class WaterSystemStatus {
    NORMAL,     // 正常
    WARNING,    // 警告
    ERROR,      // 错误
    MAINTENANCE // 维护中
}

// 水系统设备
data class WaterDevice(
    @SerializedName("id")
    val id: String = "",
    @SerializedName("name")
    val name: String = "",
    @SerializedName("type")
    val type: WaterDeviceType = WaterDeviceType.BOILER,
    @SerializedName("status")
    val status: WaterDeviceStatus = WaterDeviceStatus.OFFLINE,
    @SerializedName("isRunning")
    val isRunning: Boolean = false,
    @SerializedName("currentTemperature")
    val currentTemperature: Double? = null,
    @SerializedName("targetTemperature")
    val targetTemperature: Double? = null,
    @SerializedName("settings")
    val settings: WaterDeviceSettings? = null
)

enum class WaterDeviceType {
    BOILER,         // 锅炉
    WATER_HEATER,   // 热水器
    CIRCULATION_PUMP, // 循环泵
    WATER_TANK,     // 水箱
    PURIFIER,       // 净水器
    SOFTENER        // 软水机
}

enum class WaterDeviceStatus {
    NORMAL,
    WARNING,
    ERROR,
    OFFLINE
}

// 水设备设置
data class WaterDeviceSettings(
    @SerializedName("targetTemperature")
    val targetTemperature: Double? = null,
    @SerializedName("timerEnabled")
    val timerEnabled: Boolean = false,
    @SerializedName("timerStartTime")
    val timerStartTime: String? = null,
    @SerializedName("timerEndTime")
    val timerEndTime: String? = null,
    @SerializedName("ecoMode")
    val ecoMode: Boolean = false
)

// 更新设置请求
data class WaterSettingsRequest(
    @SerializedName("targetTemperature")
    val targetTemperature: Double? = null,
    @SerializedName("timerEnabled")
    val timerEnabled: Boolean? = null,
    @SerializedName("timerStartTime")
    val timerStartTime: String? = null,
    @SerializedName("timerEndTime")
    val timerEndTime: String? = null,
    @SerializedName("ecoMode")
    val ecoMode: Boolean? = null
)
