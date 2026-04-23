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
    @SerializedName("house_name")
    val houseName: String,
    @SerializedName("address")
    val address: String,
    @SerializedName("bind_type")
    val bindType: String,
    @SerializedName("bind_time")
    val bindTime: Long
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
