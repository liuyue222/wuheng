package com.wuheng.smart.data.model

import com.google.gson.annotations.SerializedName

// ==================== 场景模块数据模型 ====================

/**
 * 场景信息
 */
data class SceneInfo(
    @SerializedName("scene_id")
    val sceneId: Int,
    @SerializedName("scene_id_no")
    val sceneIdNo: String,
    @SerializedName("scene_name")
    val sceneName: String,
    @SerializedName("scene_type")
    val sceneType: String,
    @SerializedName("temp_set")
    val tempSet: String,
    @SerializedName("humidity_set")
    val humiditySet: String,
    @SerializedName("fan_speed")
    val fanSpeed: Int,
    @SerializedName("ceiling_radiation")
    val ceilingRadiation: Int,
    @SerializedName("floor_radiation")
    val floorRadiation: Int,
    @SerializedName("fresh_air")
    val freshAir: Int
)

/**
 * 应用场景请求
 */
data class ApplySceneRequest(
    @SerializedName("scene_id")
    val sceneId: Int,
    @SerializedName("house_id")
    val houseId: Int
)

/**
 * 应用场景响应
 */
data class ApplySceneResponse(
    @SerializedName("scene_id")
    val sceneId: Int,
    @SerializedName("scene_name")
    val sceneName: String,
    @SerializedName("temp_set")
    val tempSet: String,
    @SerializedName("humidity_set")
    val humiditySet: String
)

/**
 * 保存自定义场景请求
 */
data class SaveSceneRequest(
    @SerializedName("house_id")
    val houseId: Int,
    @SerializedName("scene_name")
    val sceneName: String,
    @SerializedName("temp_set")
    val tempSet: String? = null,
    @SerializedName("humidity_set")
    val humiditySet: String? = null,
    @SerializedName("co2_threshold")
    val co2Threshold: Int? = null,
    @SerializedName("fan_speed")
    val fanSpeed: Int? = null,
    @SerializedName("ceiling_radiation")
    val ceilingRadiation: Int? = null,
    @SerializedName("floor_radiation")
    val floorRadiation: Int? = null,
    @SerializedName("fresh_air")
    val freshAir: Int? = null
)

/**
 * 场景类型枚举
 */
enum class SceneType(val value: String) {
    MEETING("meeting"),    // 会客模式
    AWAY("away"),          // 离家模式
    SLEEP("sleep"),        // 睡眠模式
    GUARD("guard"),        // 值守模式
    GUEST("guest"),
    HOME("home"),
    CUSTOM("custom");

    companion object {
        fun fromValue(value: String): SceneType {
            return values().find { it.value == value } ?: CUSTOM
        }
    }
}

// ==================== 度假模式数据模型 ====================

/**
 * 设置度假模式请求
 */
data class SetVacationModeRequest(
    @SerializedName("house_id")
    val houseId: Int,
    @SerializedName("return_time")
    val returnTime: Long,
    @SerializedName("temp_set")
    val tempSet: String? = null,
    @SerializedName("humidity_set")
    val humiditySet: String? = null
)

/**
 * 设置度假模式响应
 */
data class SetVacationModeResponse(
    @SerializedName("house_id")
    val houseId: Int,
    @SerializedName("return_time")
    val returnTime: Long,
    @SerializedName("return_time_str")
    val returnTimeStr: String,
    @SerializedName("pre_start_time")
    val preStartTime: Long,
    @SerializedName("pre_start_time_str")
    val preStartTimeStr: String,
    @SerializedName("temp_set")
    val tempSet: String,
    @SerializedName("humidity_set")
    val humiditySet: String
)

/**
 * 取消度假模式请求
 */
data class CancelVacationRequest(
    @SerializedName("house_id")
    val houseId: Int
)

/**
 * 度假模式状态响应
 */
data class VacationStatusResponse(
    @SerializedName("active")
    val active: Boolean,
    @SerializedName("status")
    val status: String? = null,
    @SerializedName("message")
    val message: String? = null,
    @SerializedName("return_time")
    val returnTime: Long? = null,
    @SerializedName("return_time_str")
    val returnTimeStr: String? = null,
    @SerializedName("pre_start_time")
    val preStartTime: Long? = null,
    @SerializedName("pre_start_time_str")
    val preStartTimeStr: String? = null,
    @SerializedName("temp_set")
    val tempSet: String? = null,
    @SerializedName("humidity_set")
    val humiditySet: String? = null,
    @SerializedName("countdown_seconds")
    val countdownSeconds: Long? = null,
    @SerializedName("countdown_text")
    val countdownText: String? = null
)
