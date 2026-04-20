package com.wuheng.smart.data.model

import com.google.gson.annotations.SerializedName

// ==================== 房屋模块数据模型 ====================

/**
 * 房屋详细信息
 */
data class HouseInfo(
    @SerializedName("house_id")
    val houseId: Int,
    @SerializedName("house_id_no")
    val houseIdNo: String,
    @SerializedName("house_name")
    val houseName: String,
    @SerializedName("owner_name")
    val ownerName: String,
    @SerializedName("owner_phone")
    val ownerPhone: String,
    @SerializedName("address")
    val address: String,
    @SerializedName("floor_count")
    val floorCount: Int,
    @SerializedName("area_total")
    val areaTotal: String,
    @SerializedName("system_type")
    val systemType: String,
    @SerializedName("room_count")
    val roomCount: Int,
    @SerializedName("device_count")
    val deviceCount: Int,
    @SerializedName("online_count")
    val onlineCount: Int
)

/**
 * 楼层信息
 */
data class FloorInfo(
    @SerializedName("floor_id")
    val floorId: Int,
    @SerializedName("floor_id_no")
    val floorIdNo: String,
    @SerializedName("floor_name")
    val floorName: String,
    @SerializedName("floor_level")
    val floorLevel: Int,
    @SerializedName("area")
    val area: String,
    @SerializedName("room_count")
    val roomCount: Int
)

/**
 * 房间信息
 */
data class RoomInfo(
    @SerializedName("room_id")
    val roomId: Int,
    @SerializedName("room_id_no")
    val roomIdNo: String,
    @SerializedName("room_name")
    val roomName: String,
    @SerializedName("room_type")
    val roomType: String,
    @SerializedName("area")
    val area: String,
    @SerializedName("device_count")
    val deviceCount: Int
)

/**
 * 房间类型枚举
 */
enum class RoomType(val value: String) {
    LIVING("living"),
    BEDROOM("bedroom"),
    KITCHEN("kitchen"),
    BATHROOM("bathroom"),
    STUDY("study"),
    DINING("dining"),
    OTHER("other");

    companion object {
        fun fromValue(value: String): RoomType {
            return values().find { it.value == value } ?: OTHER
        }
    }
}
