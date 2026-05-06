package com.wuheng.smart.data.network

import com.wuheng.smart.data.model.*
import retrofit2.http.*

/**
 * 五恒智能控制系统 API 接口服务
 * Base URL: http://116.62.51.112/wuheng_iot/index.php
 */
interface ApiService {

    // ==================== 一、用户模块 (8个接口) ====================

    /**
     * 1. 用户登录
     * URL: /home/user/login
     * Method: POST
     * 认证: 否
     */
    @POST("home/user/login")
    suspend fun login(@Body request: LoginRequest): BaseResponse<LoginResponse>

    /**
     * 2. 用户注册
     * URL: /home/user/register
     * Method: POST
     * 认证: 否
     */
    @POST("home/user/register")
    suspend fun register(@Body request: RegisterRequest): BaseResponse<RegisterResponse>

    /**
     * 3. 用户登出
     * URL: /home/user/logout
     * Method: POST
     * 认证: 是
     */
    @POST("home/user/logout")
    suspend fun logout(): BaseResponse<Unit>

    /**
     * 4. 获取用户信息
     * URL: /home/user/getUserInfo
     * Method: GET
     * 认证: 是
     * data字段可能返回对象{}或空数组[]，用JsonElement兼容
     */
    @GET("home/user/getUserInfo")
    suspend fun getUserInfo(): BaseResponse<com.google.gson.JsonElement>

    /**
     * 5. 更新用户信息
     * URL: /home/user/updateUserInfo
     * Method: POST
     * 认证: 是
     */
    @POST("home/user/updateUserInfo")
    suspend fun updateUserInfo(@Body request: UpdateUserInfoRequest): BaseResponse<Unit>

    /**
     * 6. 修改密码
     * URL: /home/user/changePassword
     * Method: POST
     * 认证: 是
     */
    @POST("home/user/changePassword")
    suspend fun changePassword(@Body request: ChangePasswordRequest): BaseResponse<Unit>

    /**
     * 7. 绑定房屋
     * URL: /home/user/bindHouse
     * Method: POST
     * 认证: 是
     */
    @POST("home/user/bindHouse")
    suspend fun bindHouse(@Body request: BindHouseRequest): BaseResponse<Unit>

    /**
     * 8. 获取我的房屋列表
     * URL: /home/user/getMyHouses
     * Method: GET
     * 认证: 是
     */
    @GET("home/user/getMyHouses")
    suspend fun getMyHouses(): BaseResponse<List<MyHouse>>

    /**
     * 9. 忘记密码
     * URL: /home/user/forgotPassword
     * Method: POST
     * 认证: 否
     */
    @POST("home/user/forgotPassword")
    suspend fun forgotPassword(@Body request: ForgotPasswordRequest): BaseResponse<Unit>

    // ==================== 二、房屋模块 (3个接口) ====================

    /**
     * 1. 获取房屋信息
     * URL: /home/house/getHouseInfo
     * Method: GET
     * 认证: 是
     */
    @GET("home/house/getHouseInfo")
    suspend fun getHouseInfo(@Query("house_id") houseId: Int): BaseResponse<HouseInfo>

    /**
     * 2. 获取楼层信息
     * URL: /home/house/getFloorList
     * Method: GET
     * 认证: 是
     * 注意: 后端接口名为getFloorList，前端统一使用getFloorInfo
     */
    @GET("home/house/getFloorList")
    suspend fun getFloorInfo(@Query("house_id") houseId: Int): BaseResponse<List<FloorInfo>>

    /**
     * 3. 获取房间信息
     * URL: /home/house/getRoomList
     * Method: GET
     * 认证: 是
     * 注意: 后端接口名为getRoomList，前端统一使用getRoomInfo
     */
    @GET("home/house/getRoomList")
    suspend fun getRoomInfo(
        @Query("house_id") houseId: Int,
        @Query("floor_id") floorId: Int? = null
    ): BaseResponse<List<RoomInfo>>

    @GET("home/house/getMaintenanceLog")
    suspend fun getMaintenanceLog(@Query("house_id") houseId: Int): BaseResponse<List<MaintenanceLogItem>>

    // ==================== 三、设备模块 (4个接口) ====================

    /**
     * 1. 获取设备列表
     * URL: /home/device/getDeviceList
     * Method: GET
     * 认证: 是
     */
    @GET("home/device/getDeviceList")
    suspend fun getDeviceList(
        @Query("house_id") houseId: Int,
        @Query("room_id") roomId: Int? = null
    ): BaseResponse<List<DeviceInfo>>

    /**
     * 2. 获取设备详情
     * URL: /home/device/getDeviceInfo
     * Method: GET
     * 认证: 是
     */
    @GET("home/device/getDeviceInfo")
    suspend fun getDeviceDetail(@Query("device_id") deviceId: Int): BaseResponse<DeviceInfo>

    /**
     * 3. 获取设备实时数据
     * URL: /home/device/getDeviceData
     * Method: GET
     * 认证: 是
     */
    @GET("home/device/getDeviceData")
    suspend fun getDeviceStatus(@Query("device_id") deviceId: Int): BaseResponse<DeviceStatus>

    /**
     * 4. 控制设备
     * URL: /home/device/controlDevice
     * Method: POST
     * 认证: 是
     */
    @POST("home/device/controlDevice")
    suspend fun controlDevice(@Body request: ControlDeviceRequest): BaseResponse<ControlDeviceResponse>

    @GET("home/device/getHistoryData")
    suspend fun getDeviceHistoryData(
        @Query("device_id") deviceId: Int,
        @Query("data_type") dataType: String? = null,
        @Query("start_time") startTime: Long? = null,
        @Query("end_time") endTime: Long? = null
    ): BaseResponse<List<HistoryDataPoint>>

    @POST("home/device/renameDevice")
    suspend fun renameDevice(@Body request: RenameDeviceRequest): BaseResponse<Unit>

    @POST("home/device/deleteDevice")
    suspend fun deleteDevice(@Body request: DeleteDeviceRequest): BaseResponse<Unit>

    // ==================== 四、场景模块 (3个接口) ====================

    /**
     * 1. 获取场景列表
     * URL: /home/scene/getSceneList
     * Method: GET
     * 认证: 是
     */
    @GET("home/scene/getSceneList")
    suspend fun getSceneList(@Query("house_id") houseId: Int): BaseResponse<List<SceneInfo>>

    /**
     * 2. 应用场景
     * URL: /home/scene/applyScene
     * Method: POST
     * 认证: 是
     */
    @POST("home/scene/applyScene")
    suspend fun applyScene(@Body request: ApplySceneRequest): BaseResponse<ApplySceneResponse>

    /**
     * 3. 保存自定义场景
     * URL: /home/scene/saveScene
     * Method: POST
     * 认证: 是
     */
    @POST("home/scene/saveScene")
    suspend fun saveScene(@Body request: SaveSceneRequest): BaseResponse<Unit>

    /**
     * 4. 设置度假模式
     * URL: /home/scene/setVacationMode
     * Method: POST
     * 认证: 是
     */
    @POST("home/scene/setVacationMode")
    suspend fun setVacationMode(@Body request: SetVacationModeRequest): BaseResponse<SetVacationModeResponse>

    /**
     * 5. 获取度假模式状态
     * URL: /home/scene/getVacationStatus
     * Method: GET
     * 认证: 是
     */
    @GET("home/scene/getVacationStatus")
    suspend fun getVacationStatus(@Query("house_id") houseId: Int): BaseResponse<VacationStatusResponse>

    /**
     * 6. 取消度假模式
     * URL: /home/scene/cancelVacationMode
     * Method: POST
     * 认证: 是
     */
    @POST("home/scene/cancelVacationMode")
    suspend fun cancelVacationMode(@Body request: CancelVacationRequest): BaseResponse<Unit>

    // ==================== 五、系统模块 (4个接口) ====================

    /**
     * 1. 获取系统状态
     * URL: /home/system/getSystemStatus
     * Method: GET
     * 认证: 是
     */
    @GET("home/system/getSystemStatus")
    suspend fun getSystemStatus(@Query("house_id") houseId: Int): BaseResponse<SystemStatus>

    /**
     * 2. 设置系统模式
     * URL: /home/system/setSystemMode
     * Method: POST
     * 认证: 是
     */
    @POST("home/system/setSystemMode")
    suspend fun setSystemMode(@Body request: SetSystemModeRequest): BaseResponse<SetSystemModeResponse>

    /**
     * 3. 设置全局温度
     * URL: /home/system/setGlobalTemp
     * Method: POST
     * 认证: 是
     */
    @POST("home/system/setGlobalTemp")
    suspend fun setGlobalTemp(@Body request: SetGlobalTempRequest): BaseResponse<Unit>

    /**
     * 4. 设置全局湿度
     * URL: /home/system/setGlobalHumidity
     * Method: POST
     * 认证: 是
     */
    @POST("home/system/setGlobalHumidity")
    suspend fun setGlobalHumidity(@Body request: SetGlobalHumidityRequest): BaseResponse<Unit>

    // getSystemParams / setSystemParams -- 接口文档中不存在，暂不可用

    // ==================== 七、天气模块 (2个接口) ====================

    /**
     * 1. 获取天气数据
     * URL: /home/weather/getWeather
     * Method: GET
     * 认证: 否
     */
    @GET("home/weather/getWeather")
    suspend fun getWeather(
        @Query("lat") lat: String,
        @Query("lng") lng: String
    ): BaseResponse<WeatherData>

    /**
     * 2. 获取室外环境
     * URL: /home/weather/getOutdoorEnv
     * Method: GET
     * 认证: 是
     */
    @GET("home/weather/getOutdoorEnv")
    suspend fun getOutdoorEnv(@Query("house_id") houseId: Int): BaseResponse<OutdoorEnv>

    // ==================== 六、水系统模块 (4个接口) ====================

    /**
     * 1. 获取热水循环状态
     * URL: /home/water/getHeaterStatus
     * Method: GET
     * 认证: 是
     */
    @GET("home/water/getHeaterStatus")
    suspend fun getHotWaterStatus(@Query("house_id") houseId: Int): BaseResponse<HotWaterStatusResponse>

    /**
     * 2. 设置循环模式
     * URL: /home/water/setCirculationMode
     * Method: POST
     * 认证: 是
     */
    @POST("home/water/setCirculationMode")
    suspend fun setCirculationMode(@Body request: SetCirculationModeRequest): BaseResponse<SetCirculationModeResponse>

    // getWaterPurifierStatus -- 接口文档中不存在，暂不可用

    /**
     * 4. 获取滤芯状态
     * URL: /home/water/getFilterStatus
     * Method: GET
     * 认证: 是
     */
    @GET("home/water/getFilterStatus")
    suspend fun getFilterStatus(@Query("house_id") houseId: Int): BaseResponse<List<FilterStatusInfo>>

    /**
     * 5. 预约滤芯更换
     * URL: /home/water/bookFilterReplace
     * Method: POST
     * 认证: 是
     */
    @POST("home/water/bookFilterReplace")
    suspend fun bookFilterReplace(@Body request: BookFilterReplaceRequest): BaseResponse<Unit>

    /**
     * 6. 设置热力杀菌
     * URL: /home/water/setSterilization
     * Method: POST
     * 认证: 是
     */
    @POST("home/water/setSterilization")
    suspend fun setSterilization(@Body request: SetSterilizationRequest): BaseResponse<SterilizationApiResponse>

    // ==================== 八、通知模块 (4个接口) ====================

    @GET("home/notification/getList")
    suspend fun getNotificationList(): BaseResponse<List<NotificationApiItem>>

    @POST("home/notification/markRead")
    suspend fun markNotificationRead(@Body request: MarkNotificationReadRequest): BaseResponse<Unit>

    @POST("home/notification/markAllRead")
    suspend fun markAllNotificationsRead(): BaseResponse<Unit>

    @POST("home/notification/clearAll")
    suspend fun clearAllNotifications(): BaseResponse<Unit>

    // ==================== 九、服务预约模块 (1个接口) ====================

    @POST("home/service/book")
    suspend fun bookService(@Body request: BookServiceRequest): BaseResponse<Unit>

}
