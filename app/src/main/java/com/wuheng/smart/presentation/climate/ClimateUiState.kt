package com.wuheng.smart.presentation.climate

/**
 * 冷暖舒适页面 UI State
 *
 * 包含全屋/楼层模式、温度湿度设定、楼层列表等所有UI需要的数据
 */
data class ClimateUiState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,

    // 当前选中的Tab
    val selectedTab: ClimateTab = ClimateTab.WHOLE_HOUSE,

    // 全屋设定
    val temperature: Float = 0f,
    val humidity: Float = 0f,

    // 楼层列表
    val floors: List<FloorItem> = emptyList()
)

/**
 * Tab枚举
 */
enum class ClimateTab {
    WHOLE_HOUSE,  // 全屋
    FLOOR         // 楼层
}

/**
 * 楼层项数据类
 */
data class FloorItem(
    val id: String,
    val name: String,
    val isEnabled: Boolean = true,
    val isMainControl: Boolean = false,
    val devices: List<FloorDevice> = emptyList()
)

/**
 * 楼层设备数据类
 */
data class FloorDevice(
    val name: String,
    val value: String? = null,
    val status: String = "关闭"
)
