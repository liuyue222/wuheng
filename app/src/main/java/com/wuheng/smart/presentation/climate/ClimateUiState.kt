package com.wuheng.smart.presentation.climate

/**
 * 冷暖舒适页面 UI State
 *
 * 包含全屋/楼层模式、温度湿度设定、楼层列表等所有UI需要的数据
 */
data class ClimateUiState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,

    val selectedTab: ClimateTab = ClimateTab.WHOLE_HOUSE,

    val temperature: Float = 0f,
    val humidity: Float = 0f,

    val floors: List<FloorItem> = emptyList(),
    val selectedFloorId: String? = null,
    val rooms: List<RoomUiItem> = emptyList(),
    val roomsLoading: Boolean = false
)

enum class ClimateTab {
    WHOLE_HOUSE,
    FLOOR
}

data class FloorItem(
    val id: String,
    val name: String,
    val isEnabled: Boolean = true,
    val isMainControl: Boolean = false,
    val devices: List<FloorDevice> = emptyList()
)

data class FloorDevice(
    val name: String,
    val value: String? = null,
    val status: String = "关闭"
)

data class RoomUiItem(
    val id: String,
    val name: String,
    val floorId: String = "",
    val roomType: String = "",
    val area: String = "",
    val currentTemp: Float = 0f,
    val targetTemp: Float = 0f,
    val humidity: Float = 0f,
    val deviceCount: Int = 0,
    val isRunning: Boolean = false,
    val isOnline: Boolean = true
)
