package com.wuheng.smart.data.model

import com.google.gson.annotations.SerializedName

/**
 * 用户统计数据
 */
data class UserStats(
    @SerializedName("userId")
    val userId: String = "",
    @SerializedName("totalDevices")
    val totalDevices: Int = 0,
    @SerializedName("onlineDevices")
    val onlineDevices: Int = 0,
    @SerializedName("energyUsage")
    val energyUsage: Double = 0.0,
    @SerializedName("energySaved")
    val energySaved: Double = 0.0
)

// ==================== 用户资料（增强版）====================

/**
 * 用户资料（增强版）
 *
 * UI组件映射：
 * - 用户头部(64dp头像+边框): avatar, nickname, role
 * - 通知铃铛按钮+红点: hasUnreadNotification
 * - 副标题 "西湖壹号院·业主": address, role
 */
data class UserProfile(
    @SerializedName("userId")
    val userId: String = "",
    @SerializedName("nickname")
    val nickname: String = "",
    @SerializedName("avatar")
    val avatar: String = "",                  // 头像URL
    @SerializedName("phone")
    val phone: String = "",
    @SerializedName("email")
    val email: String = "",
    // ========== 住宅信息 ==========
    @SerializedName("homeName")
    val homeName: String = "",                // 住宅名称 ("西湖壹号院")
    @SerializedName("address")
    val address: String = "",                 // 详细地址
    // ========== 新增：角色和通知 ==========
    @SerializedName("role")
    val role: String = "业主",                // 用户角色 ("业主"/"家庭成员"/"租户")
    @SerializedName("hasUnreadNotification")
    val hasUnreadNotification: Boolean = false, // 是否有未读通知
    @SerializedName("unreadCount")
    val unreadCount: Int = 0                  // 未读消息数量
)

// ==================== 功能菜单项（新增）====================

/**
 * 功能菜单项
 *
 * UI映射：功能菜单列表 (图标+箭头+红点)
 * - 耗材使用进度 [>]
 * - 关于新宜能 [>]
 * - 意见反馈 [>]
 * - 清除缓存 [>]
 */
data class MenuItem(
    @SerializedName("id")
    val id: String = "",                      // 菜单项唯一标识
    @SerializedName("title")
    val title: String = "",                   // 显示标题
    @SerializedName("iconType")
    val iconType: String = "",                // 图标类型标识符
    @SerializedName("hasNotification")
    val hasNotification: Boolean = false,     // 是否显示红点
    @SerializedName("badgeCount")
    val badgeCount: Int? = null,              // 角标数字(可选)
    @SerializedName("isEnabled")
    val isEnabled: Boolean = true             // 是否可用
)

// 预定义的菜单项ID常量
object MenuItemId {
    const val CONSUMABLES = "menu_consumables"        // 耗材使用进度
    const val ABOUT = "menu_about"                    // 关于新宜能
    const val FEEDBACK = "menu_feedback"              // 意见反馈
    const val CLEAR_CACHE = "menu_clear_cache"        // 清除缓存
    const val PRIVACY = "menu_privacy"                // 隐私政策
    const val TERMS = "menu_terms"                    // 服务条款
}

// ==================== 版本信息（新增）====================

/**
 * 应用版本信息
 *
 * UI映射：版本信息区域 (底部居中)
 * - "V1.2.3"
 * - 《隐私服务条款》链接
 */
data class VersionInfo(
    @SerializedName("versionName")
    val versionName: String = "1.0.0",          // 版本号 "V1.2.3"
    @SerializedName("versionCode")
    val versionCode: Int = 1,                   // 版本代码
    @SerializedName("buildDate")
    val buildDate: String? = null,              // 构建日期
    @SerializedName("hasUpdate")
    val hasUpdate: Boolean = false,             // 是否有新版本
    @SerializedName("updateUrl")
    val updateUrl: String? = null,              // 更新下载地址
    @SerializedName("updateLog")
    val updateLog: String? = null               // 更新说明
)

// ==================== 预约服务（新增）====================

/**
 * 预约服务记录
 *
 * UI映射：预约服务卡片
 * - 上次预约时间
 * - 服务类型选择
 * - [+ 预约服务] 按钮
 */
data class AppointmentRecord(
    @SerializedName("id")
    val id: String = "",
    @SerializedName("type")
    val type: String = "",                      // 服务类型 ("定期保养"/"故障维修"/"滤芯更换")
    @SerializedName("status")
    val status: AppointmentStatus = AppointmentStatus.SCHEDULED,
    @SerializedName("appointmentDate")
    val appointmentDate: String = "",           // 预约日期 "2026-07-15"
    @SerializedName("serviceItem")
    val serviceItem: String = "",              // 服务项目描述
    @SerializedName("technician")
    val technician: String? = null,            // 服务人员姓名
    @SerializedName("rating")
    val rating: Int? = null,                   // 评分 (1-5)
    @SerializedName("comment")
    val comment: String? = null                // 评价内容
)

/**
 * 预约状态枚举
 */
enum class AppointmentStatus {
    SCHEDULED,      // 已预约
    IN_PROGRESS,    // 服务中
    COMPLETED,      // 已完成
    CANCELLED       // 已取消
}

// ==================== 请求模型 ====================

/**
 * 更新资料请求（旧版API）
 */
data class UpdateProfileRequest(
    @SerializedName("nickname")
    val nickname: String? = null,
    @SerializedName("avatar")
    val avatar: String? = null,
    @SerializedName("email")
    val email: String? = null,
    @SerializedName("homeName")
    val homeName: String? = null,
    @SerializedName("address")
    val address: String? = null
)

// 注意：以下类已在 UserApiModels.kt 中定义，不要在此重复定义：
// - UserInfo (新版API使用 Int 类型的 userId)
// - LoginRequest
// - UpdateUserInfoRequest
// - MyHouse
