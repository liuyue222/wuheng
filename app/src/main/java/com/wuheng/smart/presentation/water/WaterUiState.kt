package com.wuheng.smart.presentation.water

/**
 * 水系统页面 UI State
 *
 * 包含生活热水循环、热力杀菌、滤芯状态等所有UI需要的数据
 */
data class WaterUiState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,

    // 生活热水循环
    val hotWaterMode: HotWaterMode = HotWaterMode.OFF,
    val currentTemp: Int = 0,
    val temporaryDuration: Int = 30,

    // 热力杀菌
    val sterilizationSchedule: String = "",

    // 滤芯列表
    val filters: List<FilterItem> = emptyList()
)

/**
 * 热水循环模式枚举
 */
enum class HotWaterMode {
    ALL_DAY,    // 全天循环
    TIMED,      // 定时循环
    TEMPORARY,  // 临时循环
    OFF         // 关闭循环
}

/**
 * 滤芯项目数据类
 */
data class FilterItem(
    val name: String,
    val progress: Float,  // 0.0 - 1.0
    val status: FilterUiStatus
)

/**
 * 滤芯UI状态枚举（用于UI展示）
 */
enum class FilterUiStatus {
    NORMAL,   // 正常 (>30%)
    WARNING,  // 警告 (10%-30%)
    EXPIRED   // 已过期 (<10%)
}
