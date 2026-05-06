package com.wuheng.smart.data.model

import com.google.gson.annotations.SerializedName

// ==================== 用户模块数据模型 ====================

/**
 * 用户登录请求
 */
data class LoginRequest(
    @SerializedName("username")
    val username: String,
    @SerializedName("password")
    val password: String
)

/**
 * 用户登录响应
 * 注意：后端返回的user_id、user_type、house_id、status是Int类型
 */
data class LoginResponse(
    @SerializedName("user_id")
    val userId: Int,
    @SerializedName("user_id_no")
    val userIdNo: String,
    @SerializedName("user_name")
    val userName: String,
    @SerializedName("user_tel")
    val userTel: String,
    @SerializedName("user_token")
    val userToken: String,
    @SerializedName("user_type")
    val userType: Int,
    @SerializedName("house_id")
    val houseId: Int,
    @SerializedName("status")
    val status: Int
)

/**
 * 用户注册请求
 */
data class RegisterRequest(
    @SerializedName("username")
    val username: String,
    @SerializedName("password")
    val password: String,
    @SerializedName("mobile")
    val mobile: String,
    @SerializedName("realname")
    val realname: String? = null,
    @SerializedName("email")
    val email: String? = null
)

/**
 * 用户注册响应
 */
data class RegisterResponse(
    @SerializedName("user_id")
    val userId: Int,
    @SerializedName("user_token")
    val userToken: String
)

/**
 * 用户信息（新版API）
 */
data class UserInfo(
    @SerializedName("user_id")
    val userId: Int,
    @SerializedName("user_id_no")
    val userIdNo: String,
    @SerializedName("user_name")
    val userName: String,
    @SerializedName("user_tel")
    val userTel: String,
    @SerializedName("user_type")
    val userType: Int,
    @SerializedName("house_id")
    val houseId: Int,
    @SerializedName("status")
    val status: Int
)

/**
 * 更新用户信息请求
 */
data class UpdateUserInfoRequest(
    @SerializedName("realname")
    val realname: String? = null,
    @SerializedName("email")
    val email: String? = null,
    @SerializedName("avatar")
    val avatar: String? = null
)

/**
 * 修改密码请求
 */
data class ChangePasswordRequest(
    @SerializedName("old_password")
    val oldPassword: String,
    @SerializedName("new_password")
    val newPassword: String
)

/**
 * 绑定房屋请求
 */
data class BindHouseRequest(
    @SerializedName("house_id")
    val houseId: Int,
    @SerializedName("bind_code")
    val bindCode: String? = null
)

/**
 * 我的房屋信息
 */
data class MyHouse(
    @SerializedName("house_id")
    val houseId: Int,
    @SerializedName("house_id_no")
    val houseIdNo: String,
    @SerializedName("house_name")
    val houseName: String,
    @SerializedName("address")
    val address: String,
    @SerializedName("owner_name")
    val ownerName: String,
    @SerializedName("area_total")
    val areaTotal: String,
    @SerializedName("system_type")
    val systemType: String,
    @SerializedName("bind_type")
    val bindType: String,
    @SerializedName("bind_time")
    val bindTime: Long,
    @SerializedName("system_mode")
    val systemMode: String? = null,
    @SerializedName("system_run_status")
    val systemRunStatus: String? = null,
    @SerializedName("indoor_temp")
    val indoorTemp: String? = null,
    @SerializedName("indoor_humidity")
    val indoorHumidity: String? = null,
    @SerializedName("outdoor_temp")
    val outdoorTemp: String? = null,
    @SerializedName("device_count")
    val deviceCount: Int = 0,
    @SerializedName("online_count")
    val onlineCount: Int = 0,
    @SerializedName("alarm_count")
    val alarmCount: Int = 0
)

// ==================== 旧版登录兼容 ====================

data class LoginRequestOld(
    @SerializedName("phone")
    val phone: String,
    @SerializedName("code")
    val code: String? = null,
    @SerializedName("password")
    val password: String? = null
)

data class UserInfoOld(
    @SerializedName("userId")
    val userId: String = "",
    @SerializedName("token")
    val token: String = "",
    @SerializedName("refreshToken")
    val refreshToken: String = "",
    @SerializedName("expiresIn")
    val expiresIn: Long = 0,
    @SerializedName("nickname")
    val nickname: String = "",
    @SerializedName("avatar")
    val avatar: String = ""
)

// ==================== 忘记密码 ====================

/**
 * 忘记密码请求
 */
data class ForgotPasswordRequest(
    @SerializedName("mobile")
    val mobile: String,
    @SerializedName("new_password")
    val newPassword: String
)

data class BookServiceRequest(
    @SerializedName("house_id") val houseId: Int,
    @SerializedName("service_type") val serviceType: String,
    @SerializedName("contact_name") val contactName: String,
    @SerializedName("contact_phone") val contactPhone: String,
    @SerializedName("appointment_date") val appointmentDate: String,
    @SerializedName("remark") val remark: String? = null
)

data class MarkNotificationReadRequest(
    @SerializedName("notification_id") val notificationId: Int
)

data class NotificationApiItem(
    @SerializedName("notification_id") val notificationId: Int,
    @SerializedName("type") val type: String,
    @SerializedName("title") val title: String,
    @SerializedName("content") val content: String,
    @SerializedName("is_read") val isRead: Int,
    @SerializedName("createtime") val createTime: Long
)
