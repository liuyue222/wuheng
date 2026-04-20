package com.wuheng.smart.data.model

import com.google.gson.annotations.SerializedName

// ==================== 水系统模块数据模型 (新版API) ====================

/**
 * 热水循环状态
 */
data class HeaterStatus(
    @SerializedName("current_temp")
    val currentTemp: String,
    @SerializedName("target_temp")
    val targetTemp: String,
    @SerializedName("circulation_mode")
    val circulationMode: String,
    @SerializedName("circulation_status")
    val circulationStatus: Int,
    @SerializedName("sterilization_enable")
    val sterilizationEnable: Int,
    @SerializedName("sterilization_time")
    val sterilizationTime: String
)

/**
 * 设置循环模式请求
 */
data class SetCirculationModeRequest(
    @SerializedName("house_id")
    val houseId: Int,
    @SerializedName("mode")
    val mode: String,
    @SerializedName("duration")
    val duration: Int? = null
)

/**
 * 设置循环模式响应
 */
data class SetCirculationModeResponse(
    @SerializedName("mode")
    val mode: String
)

/**
 * 滤芯状态信息 (新版API)
 */
data class FilterStatusInfo(
    @SerializedName("filter_id")
    val filterId: Int,
    @SerializedName("filter_name")
    val filterName: String,
    @SerializedName("filter_type")
    val filterType: String,
    @SerializedName("life_percent")
    val lifePercent: Int,
    @SerializedName("status")
    val status: Int
)

/**
 * 预约滤芯更换请求
 */
data class BookFilterReplaceRequest(
    @SerializedName("house_id")
    val houseId: Int,
    @SerializedName("filter_id")
    val filterId: Int,
    @SerializedName("contact_name")
    val contactName: String? = null,
    @SerializedName("contact_phone")
    val contactPhone: String? = null,
    @SerializedName("appointment_date")
    val appointmentDate: String? = null
)

/**
 * 循环模式枚举
 */
enum class CirculationMode(val value: String) {
    ALL_DAY("all_day"),
    TIMER("timer"),
    TEMP("temp"),
    OFF("off");

    companion object {
        fun fromValue(value: String): CirculationMode {
            return values().find { it.value == value } ?: OFF
        }
    }
}

/**
 * 滤芯类型枚举
 */
enum class FilterType(val value: String) {
    PRE("pre"),
    CENTRAL("central"),
    END("end");

    companion object {
        fun fromValue(value: String): FilterType {
            return values().find { it.value == value } ?: PRE
        }
    }
}

/**
 * 滤芯状态枚举
 */
enum class FilterLifeStatus(val code: Int) {
    NORMAL(0),
    WARNING(1),
    CRITICAL(2);

    companion object {
        fun fromCode(code: Int): FilterLifeStatus {
            return values().find { it.code == code } ?: NORMAL
        }
    }
}
