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
