package com.wuheng.smart.data.repository

import com.wuheng.smart.data.model.*
import com.wuheng.smart.data.network.ApiResult
import com.wuheng.smart.data.network.ApiService
import com.wuheng.smart.data.network.RetryConfig
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

/**
 * 首页数据仓库接口
 *
 * 提供首页所需的所有数据操作方法，包括：
 * - 房屋信息管理（获取房屋信息、楼层列表、房间列表）
 * - 设备管理（获取设备列表、设备详情、设备实时数据、控制设备）
 * - 场景管理（获取场景列表、应用场景、保存场景）
 * - 系统管理（获取系统状态、设置系统模式/温度/湿度）
 */
interface HomeRepository {

    // ==================== 新版API - 房屋模块 (3个接口) ====================

    /**
     * 获取房屋详细信息
     *
     * @param houseId 房屋ID
     * @return 房屋详细信息
     */
    suspend fun getHouseInfo(houseId: Int): Flow<ApiResult<HouseInfo>>

    /**
     * 获取楼层信息
     *
     * @param houseId 房屋ID
     * @return 楼层列表
     */
    suspend fun getFloorInfo(houseId: Int): Flow<ApiResult<List<FloorInfo>>>

    /**
     * 获取房间信息
     *
     * @param houseId 房屋ID
     * @param floorId 楼层ID（可选，不传则返回所有房间）
     * @return 房间列表
     */
    suspend fun getRoomInfo(houseId: Int, floorId: Int? = null): Flow<ApiResult<List<RoomInfo>>>

    // ==================== 新版API - 设备模块 (4个接口) ====================

    /**
     * 获取设备列表
     *
     * @param houseId 房屋ID
     * @param roomId 房间ID（可选）
     * @return 设备列表
     */
    suspend fun getDeviceList(houseId: Int, roomId: Int? = null): Flow<ApiResult<List<DeviceInfo>>>

    /**
     * 获取设备详情
     *
     * @param deviceId 设备ID
     * @return 设备详细信息
     */
    suspend fun getDeviceDetail(deviceId: Int): Flow<ApiResult<DeviceInfo>>

    /**
     * 获取设备状态
     *
     * @param deviceId 设备ID
     * @return 设备状态（温度、湿度、CO2等）
     */
    suspend fun getDeviceStatus(deviceId: Int): Flow<ApiResult<DeviceStatus>>

    /**
     * 控制设备
     *
     * @param deviceId 设备ID
     * @param command 命令：on/off/temp_up/temp_down/set_temp
     * @param value 控制值（可选）
     * @return 控制响应
     */
    suspend fun controlDevice(deviceId: Int, command: String, value: String? = null): Flow<ApiResult<ControlDeviceResponse>>

    // ==================== 新版API - 场景模块 (3个接口) ====================

    /**
     * 获取场景列表
     *
     * @param houseId 房屋ID
     * @return 场景列表
     */
    suspend fun getSceneList(houseId: Int): Flow<ApiResult<List<SceneInfo>>>

    /**
     * 应用场景
     *
     * @param sceneId 场景ID
     * @param houseId 房屋ID
     * @return 应用场景响应
     */
    suspend fun applyScene(sceneId: Int, houseId: Int): Flow<ApiResult<ApplySceneResponse>>

    /**
     * 保存自定义场景
     *
     * @param request 保存场景请求
     */
    suspend fun saveScene(request: SaveSceneRequest): Flow<ApiResult<Unit>>

    // ==================== 场景模块 - 度假模式接口 ====================

    /**
     * 设置度假模式
     *
     * @param houseId 房屋ID
     * @param returnTime 归期时间戳（秒）
     * @param tempSet 度假温度设置（可选）
     * @param humiditySet 度假湿度设置（可选）
     * @return 设置度假模式响应
     */
    suspend fun setVacationMode(
        houseId: Int,
        returnTime: Long,
        tempSet: String? = null,
        humiditySet: String? = null
    ): Flow<ApiResult<SetVacationModeResponse>>

    /**
     * 获取度假模式状态
     *
     * @param houseId 房屋ID
     * @return 度假模式状态
     */
    suspend fun getVacationStatus(houseId: Int): Flow<ApiResult<VacationStatusResponse>>

    /**
     * 取消度假模式
     *
     * @param houseId 房屋ID
     * @return 操作结果
     */
    suspend fun cancelVacationMode(houseId: Int): Flow<ApiResult<Unit>>

    // ==================== 新版API - 系统模块 (4个接口) ====================

    /**
     * 获取系统状态
     *
     * @param houseId 房屋ID
     * @return 系统状态（模式、温度、湿度等）
     */
    suspend fun getSystemStatus(houseId: Int): Flow<ApiResult<SystemStatus>>

    /**
     * 设置系统模式
     *
     * @param houseId 房屋ID
     * @param mode 模式：cooling/heating/ventilation/auto
     * @return 设置响应
     */
    suspend fun setSystemMode(houseId: Int, mode: String): Flow<ApiResult<SetSystemModeResponse>>

    /**
     * 设置全局温度
     *
     * @param houseId 房屋ID
     * @param temp 温度值（16-30）
     */
    suspend fun setGlobalTemp(houseId: Int, temp: String): Flow<ApiResult<Unit>>

    /**
     * 设置全局湿度
     *
     * @param houseId 房屋ID
     * @param humidity 湿度值（30-70）
     */
    suspend fun setGlobalHumidity(houseId: Int, humidity: String): Flow<ApiResult<Unit>>

    // getSystemParams / setSystemParams -- 接口文档中不存在，暂不可用

    // ==================== 天气模块 ====================
    suspend fun getWeather(lat: String, lng: String): Flow<ApiResult<WeatherData>>
    suspend fun getOutdoorEnv(houseId: Int): Flow<ApiResult<OutdoorEnv>>

    // ==================== 通知模块 ====================

    suspend fun getNotificationList(): Flow<ApiResult<List<NotificationApiItem>>>

    suspend fun markNotificationRead(notificationId: Int): Flow<ApiResult<Unit>>

    suspend fun markAllNotificationsRead(): Flow<ApiResult<Unit>>

    suspend fun clearAllNotifications(): Flow<ApiResult<Unit>>

    // ==================== 服务预约模块 ====================

    suspend fun bookService(
        houseId: Int,
        serviceType: String,
        contactName: String,
        contactPhone: String,
        appointmentDate: String,
        remark: String? = null
    ): Flow<ApiResult<Unit>>

    suspend fun getMaintenanceLog(houseId: Int): Flow<ApiResult<List<MaintenanceLogItem>>>

    // ==================== 设备扩展模块 ====================

    suspend fun getDeviceHistoryData(
        deviceId: Int,
        dataType: String? = null,
        startTime: Long? = null,
        endTime: Long? = null
    ): Flow<ApiResult<List<HistoryDataPoint>>>

    suspend fun renameDevice(deviceId: Int, deviceName: String): Flow<ApiResult<Unit>>

    suspend fun deleteDevice(deviceId: Int): Flow<ApiResult<Unit>>
}

/**
 * 首页数据仓库实现类
 *
 * @param apiService Retrofit API服务
 * @param useMock 是否使用Mock数据，默认false使用真实API
 */
@Singleton
class HomeRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val useMock: Boolean = false
) : BaseRepository(), HomeRepository {

    // ==================== 新版API实现 - 房屋模块 (带重试机制) ====================

    /**
     * 房屋模块使用默认重试配置
     * - 最大重试3次
     * - 初始延迟1秒，指数退避
     * - 网络错误、超时、服务器错误都会重试
     */
    private val houseRetryConfig = RetryConfig.DEFAULT

    override suspend fun getHouseInfo(houseId: Int): Flow<ApiResult<HouseInfo>> = apiFlow(
        operation = "getHouseInfo",
        params = "houseId=$houseId"
    ) {
        if (useMock) {
            kotlinx.coroutines.delay(300)
            val mockHouse = HouseInfo(
                houseId = houseId,
                houseIdNo = "HOUSE202604190001",
                houseName = "阳光花园别墅",
                ownerName = "张三",
                ownerPhone = "13800138001",
                address = "浙江省杭州市西湖区文三路123号",
                floorCount = 3,
                areaTotal = "280.00",
                systemType = "辐射空调系统",
                roomCount = 5,
                deviceCount = 6,
                onlineCount = 5
            )
            ApiResult.Success(mockHouse)
        } else {
            apiCallWithRetry(
                config = houseRetryConfig,
                operation = "getHouseInfo"
            ) { apiService.getHouseInfo(houseId) }
        }
    }

    override suspend fun getFloorInfo(houseId: Int): Flow<ApiResult<List<FloorInfo>>> = apiFlow(
        operation = "getFloorInfo",
        params = "houseId=$houseId"
    ) {
        if (useMock) {
            kotlinx.coroutines.delay(300)
            val mockFloors = listOf(
                FloorInfo(1, "FLOOR202604190001", "地下一层", -1, "80.00", 0),
                FloorInfo(2, "FLOOR202604190002", "一层", 1, "100.00", 3),
                FloorInfo(3, "FLOOR202604190003", "二层", 2, "100.00", 2)
            )
            ApiResult.Success(mockFloors)
        } else {
            apiCallWithRetry(
                config = houseRetryConfig,
                operation = "getFloorInfo"
            ) { apiService.getFloorInfo(houseId) }
        }
    }

    override suspend fun getRoomInfo(houseId: Int, floorId: Int?): Flow<ApiResult<List<RoomInfo>>> = apiFlow(
        operation = "getRoomInfo",
        params = "houseId=$houseId, floorId=$floorId"
    ) {
        if (useMock) {
            kotlinx.coroutines.delay(300)
            val mockRooms = listOf(
                RoomInfo(1, "ROOM202604190001", "客厅", "living", "45.00", 2),
                RoomInfo(2, "ROOM202604190002", "主卧", "bedroom", "25.00", 1),
                RoomInfo(3, "ROOM202604190003", "厨房", "kitchen", "15.00", 1),
                RoomInfo(4, "ROOM202604190004", "次卧", "bedroom", "20.00", 1),
                RoomInfo(5, "ROOM202604190005", "书房", "study", "18.00", 1)
            )
            ApiResult.Success(mockRooms)
        } else {
            apiCallWithRetry(
                config = houseRetryConfig,
                operation = "getRoomInfo"
            ) { apiService.getRoomInfo(houseId, floorId) }
        }
    }

    // ==================== 新版API实现 - 设备模块 ====================

    override suspend fun getDeviceList(houseId: Int, roomId: Int?): Flow<ApiResult<List<DeviceInfo>>> = apiFlow(
        operation = "getDeviceList",
        params = "houseId=$houseId, roomId=$roomId"
    ) {
        if (useMock) {
            kotlinx.coroutines.delay(300)
            val mockDevices = listOf(
                DeviceInfo(1, "DEV202604190001", "客厅温控器", "thermostat", "TH-2025A", 1, "running", "客厅"),
                DeviceInfo(2, "DEV202604190002", "主卧温控器", "thermostat", "TH-2025A", 1, "standby", "主卧"),
                DeviceInfo(3, "DEV202604190003", "环境传感器", "sensor", "SE-001", 1, "running", "客厅"),
                DeviceInfo(4, "DEV202604190004", "新风系统", "fresh_air", "FA-001", 1, "running", "全屋"),
                DeviceInfo(5, "DEV202604190005", "地暖控制器", "floor_heating", "FH-001", 1, "running", "客厅"),
                DeviceInfo(6, "DEV202604190006", "湿度调节器", "humidifier", "HM-001", 0, "offline", "主卧")
            )
            ApiResult.Success(mockDevices)
        } else {
            apiCall { apiService.getDeviceList(houseId, roomId) }
        }
    }

    override suspend fun getDeviceDetail(deviceId: Int): Flow<ApiResult<DeviceInfo>> = apiFlow(
        operation = "getDeviceDetail",
        params = "deviceId=$deviceId"
    ) {
        if (useMock) {
            kotlinx.coroutines.delay(300)
            val mockDevice = DeviceInfo(
                deviceId = deviceId,
                deviceIdNo = "DEV20260419000$deviceId",
                deviceName = "设备$deviceId",
                deviceType = "thermostat",
                deviceModel = "TH-2025A",
                onlineStatus = 1,
                runStatus = "running",
                roomName = "客厅"
            )
            ApiResult.Success(mockDevice)
        } else {
            apiCall { apiService.getDeviceDetail(deviceId) }
        }
    }

    override suspend fun getDeviceStatus(deviceId: Int): Flow<ApiResult<DeviceStatus>> = apiFlow(
        operation = "getDeviceStatus",
        params = "deviceId=$deviceId"
    ) {
        if (useMock) {
            kotlinx.coroutines.delay(300)
            val mockStatus = DeviceStatus(
                deviceId = deviceId,
                onlineStatus = 1,
                runStatus = "running",
                power = 1,
                temperature = "24.50",
                humidity = "45.00",
                co2 = 420,
                pm25 = 35,
                voc = 150,
                fanSpeed = 1,
                valveOpen = 1,
                reportTime = System.currentTimeMillis() / 1000
            )
            ApiResult.Success(mockStatus)
        } else {
            apiCall { apiService.getDeviceStatus(deviceId) }
        }
    }

    override suspend fun controlDevice(
        deviceId: Int,
        command: String,
        value: String?
    ): Flow<ApiResult<ControlDeviceResponse>> = apiFlow(
        operation = "controlDevice",
        params = "deviceId=$deviceId, command=$command, value=$value"
    ) {
        if (useMock) {
            kotlinx.coroutines.delay(500)
            val mockResponse = ControlDeviceResponse(
                command = command,
                value = value ?: "",
                deviceId = deviceId
            )
            ApiResult.Success(mockResponse)
        } else {
            apiCall { apiService.controlDevice(ControlDeviceRequest(deviceId, command, value)) }
        }
    }

    // ==================== 新版API实现 - 场景模块 (带重试机制) ====================

    /**
     * 获取场景列表
     * 使用带重试机制的API调用，在网络错误时自动重试3次
     */
    override suspend fun getSceneList(houseId: Int): Flow<ApiResult<List<SceneInfo>>> = apiFlowWithRetry(
        operation = "getSceneList",
        params = "houseId=$houseId",
        maxRetries = 3,
        initialDelay = 1000L
    ) {
        if (useMock) {
            kotlinx.coroutines.delay(300)
            val mockScenes = listOf(
                SceneInfo(1, "SCENE202604190001", "回家模式", "guest", "24.00", "45.00", 1, 1, 1, 1),
                SceneInfo(2, "SCENE202604190002", "离家模式", "away", "18.00", "40.00", 0, 0, 0, 0),
                SceneInfo(3, "SCENE202604190003", "睡眠模式", "sleep", "26.00", "45.00", 1, 0, 0, 1),
                SceneInfo(4, "SCENE202604190004", "值守模式", "home", "22.00", "50.00", 0, 1, 1, 1)
            )
            ApiResult.Success(mockScenes)
        } else {
            apiCallWithRetry(maxRetries = 3) { apiService.getSceneList(houseId) }
        }
    }

    /**
     * 应用场景
     * 使用带重试机制的API调用，确保场景切换命令可靠送达
     */
    override suspend fun applyScene(sceneId: Int, houseId: Int): Flow<ApiResult<ApplySceneResponse>> = apiFlowWithRetry(
        operation = "applyScene",
        params = "sceneId=$sceneId, houseId=$houseId",
        maxRetries = 3,
        initialDelay = 800L
    ) {
        if (useMock) {
            kotlinx.coroutines.delay(500)
            val mockResponse = ApplySceneResponse(
                sceneId = sceneId,
                sceneName = "场景$sceneId",
                tempSet = "24.00",
                humiditySet = "45.00"
            )
            ApiResult.Success(mockResponse)
        } else {
            apiCallWithRetry(maxRetries = 3) { apiService.applyScene(ApplySceneRequest(sceneId, houseId)) }
        }
    }

    /**
     * 保存自定义场景
     * 使用带重试机制的API调用，确保场景配置保存成功
     */
    override suspend fun saveScene(request: SaveSceneRequest): Flow<ApiResult<Unit>> = apiFlowWithRetry(
        operation = "saveScene",
        params = "houseId=${request.houseId}, name=${request.sceneName}",
        maxRetries = 3,
        initialDelay = 1000L
    ) {
        if (useMock) {
            kotlinx.coroutines.delay(500)
            ApiResult.Success(Unit)
        } else {
            apiCallWithRetry(maxRetries = 3) { apiService.saveScene(request) }
        }
    }

    // ==================== 场景模块实现 - 度假模式 ====================

    /**
     * 设置度假模式
     */
    override suspend fun setVacationMode(
        houseId: Int,
        returnTime: Long,
        tempSet: String?,
        humiditySet: String?
    ): Flow<ApiResult<SetVacationModeResponse>> = apiFlow(
        operation = "setVacationMode",
        params = "houseId=$houseId, returnTime=$returnTime"
    ) {
        if (useMock) {
            kotlinx.coroutines.delay(500)
            val mockResponse = SetVacationModeResponse(
                houseId = houseId,
                returnTime = returnTime,
                returnTimeStr = "2026-04-23 12:00:00",
                preStartTime = returnTime - 7200,
                preStartTimeStr = "2026-04-23 10:00:00",
                tempSet = tempSet ?: "18.00",
                humiditySet = humiditySet ?: "55.00"
            )
            ApiResult.Success(mockResponse)
        } else {
            apiCall { apiService.setVacationMode(
                SetVacationModeRequest(houseId, returnTime, tempSet, humiditySet)
            )}
        }
    }

    /**
     * 获取度假模式状态
     */
    override suspend fun getVacationStatus(houseId: Int): Flow<ApiResult<VacationStatusResponse>> = apiFlow(
        operation = "getVacationStatus",
        params = "houseId=$houseId"
    ) {
        if (useMock) {
            kotlinx.coroutines.delay(300)
            val mockStatus = VacationStatusResponse(
                active = true,
                status = "waiting",
                returnTime = 1745316000,
                returnTimeStr = "2026-04-23 12:00:00",
                preStartTime = 1745308800,
                preStartTimeStr = "2026-04-23 10:00:00",
                tempSet = "18.00",
                humiditySet = "55.00",
                countdownSeconds = 172800,
                countdownText = "2天0小时"
            )
            ApiResult.Success(mockStatus)
        } else {
            apiCall { apiService.getVacationStatus(houseId) }
        }
    }

    /**
     * 取消度假模式
     */
    override suspend fun cancelVacationMode(houseId: Int): Flow<ApiResult<Unit>> = apiFlow(
        operation = "cancelVacationMode",
        params = "houseId=$houseId"
    ) {
        if (useMock) {
            kotlinx.coroutines.delay(300)
            ApiResult.Success(Unit)
        } else {
            apiCall { apiService.cancelVacationMode(CancelVacationRequest(houseId)) }
        }
    }

    // ==================== 新版API实现 - 系统模块 ====================

    override suspend fun getSystemStatus(houseId: Int): Flow<ApiResult<SystemStatus>> = apiFlow(
        operation = "getSystemStatus",
        params = "houseId=$houseId"
    ) {
        if (useMock) {
            kotlinx.coroutines.delay(300)
            val mockStatus = SystemStatus(
                systemStatus = SystemStatusInfo(
                    systemMode = "cooling",
                    globalTempSet = "24.00",
                    globalHumiditySet = "45.00",
                    avgIndoorTemp = "23.50",
                    avgIndoorHumidity = "45.20",
                    systemRunStatus = "running"
                ),
                houseInfo = null,
                deviceCount = 6,
                onlineCount = 5
            )
            ApiResult.Success(mockStatus)
        } else {
            // 使用带重试机制的API调用
            apiCallWithRetry(maxRetries = 3) { apiService.getSystemStatus(houseId) }
        }
    }

    override suspend fun setSystemMode(houseId: Int, mode: String): Flow<ApiResult<SetSystemModeResponse>> = apiFlow(
        operation = "setSystemMode",
        params = "houseId=$houseId, mode=$mode"
    ) {
        if (useMock) {
            kotlinx.coroutines.delay(500)
            ApiResult.Success(SetSystemModeResponse(mode))
        } else {
            // 设置操作使用普通调用（不重试，避免重复设置）
            apiCall { apiService.setSystemMode(SetSystemModeRequest(houseId, mode)) }
        }
    }

    override suspend fun setGlobalTemp(houseId: Int, temp: String): Flow<ApiResult<Unit>> = apiFlow(
        operation = "setGlobalTemp",
        params = "houseId=$houseId, temp=$temp"
    ) {
        if (useMock) {
            kotlinx.coroutines.delay(500)
            ApiResult.Success(Unit)
        } else {
            apiCall { apiService.setGlobalTemp(SetGlobalTempRequest(houseId, temp)) }
        }
    }

    override suspend fun setGlobalHumidity(houseId: Int, humidity: String): Flow<ApiResult<Unit>> = apiFlow(
        operation = "setGlobalHumidity",
        params = "houseId=$houseId, humidity=$humidity"
    ) {
        if (useMock) {
            kotlinx.coroutines.delay(500)
            ApiResult.Success(Unit)
        } else {
            apiCall { apiService.setGlobalHumidity(SetGlobalHumidityRequest(houseId, humidity)) }
        }
    }

    // getSystemParams / setSystemParams -- 接口文档中不存在，暂不可用

    // ==================== 天气模块实现 ====================

    override suspend fun getWeather(lat: String, lng: String): Flow<ApiResult<WeatherData>> = apiFlow(
        operation = "getWeather",
        params = "lat=$lat, lng=$lng"
    ) {
        if (useMock) {
            kotlinx.coroutines.delay(300)
            val mockForecast = listOf(
                WeatherForecast("2026-04-21", "28", "18", "晴间多云"),
                WeatherForecast("2026-04-22", "25", "17", "小雨"),
                WeatherForecast("2026-04-23", "27", "19", "多云")
            )
            val mockWeather = WeatherData(
                location = "Hangzhou, Zhejiang",
                latitude = lat,
                longitude = lng,
                temperature = "26",
                weatherCode = "0",
                weatherDesc = "多云",
                humidity = "65",
                windSpeed = "15",
                windDirection = "NE",
                visibility = "10",
                uvIndex = "5",
                aqi = 35,
                aqiLevel = "优",
                pm25 = 12,
                pm10 = 28,
                forecast = mockForecast
            )
            ApiResult.Success(mockWeather)
        } else {
            apiCall { apiService.getWeather(lat, lng) }
        }
    }

    override suspend fun getOutdoorEnv(houseId: Int): Flow<ApiResult<OutdoorEnv>> = apiFlow(
        operation = "getOutdoorEnv",
        params = "houseId=$houseId"
    ) {
        if (useMock) {
            kotlinx.coroutines.delay(300)
            val mockEnv = OutdoorEnv(
                outdoorTemp = "26.00",
                outdoorHumidity = "65.00",
                outdoorAqi = 35,
                outdoorPm25 = 12
            )
            ApiResult.Success(mockEnv)
        } else {
            apiCall { apiService.getOutdoorEnv(houseId) }
        }
    }

    // ==================== 通知模块实现 ====================

    override suspend fun getNotificationList(): Flow<ApiResult<List<NotificationApiItem>>> = apiFlow(
        operation = "getNotificationList",
        params = ""
    ) {
        if (useMock) {
            kotlinx.coroutines.delay(300)
            val now = System.currentTimeMillis() / 1000
            val mockNotifications = listOf(
                NotificationApiItem(1, "system", "系统通知", "您的房屋系统已完成定期巡检", 0, now - 3600),
                NotificationApiItem(2, "alert", "滤芯到期提醒", "末端直饮机滤芯将于7天后到期，请及时更换", 0, now - 7200),
                NotificationApiItem(3, "device", "设备离线提醒", "客厅温控器已离线，请检查设备连接状态", 1, now - 86400)
            )
            ApiResult.Success(mockNotifications)
        } else {
            apiCall { apiService.getNotificationList() }
        }
    }

    override suspend fun markNotificationRead(notificationId: Int): Flow<ApiResult<Unit>> = apiFlow(
        operation = "markNotificationRead",
        params = "notificationId=$notificationId"
    ) {
        if (useMock) {
            kotlinx.coroutines.delay(200)
            ApiResult.Success(Unit)
        } else {
            apiCall { apiService.markNotificationRead(MarkNotificationReadRequest(notificationId)) }
        }
    }

    override suspend fun markAllNotificationsRead(): Flow<ApiResult<Unit>> = apiFlow(
        operation = "markAllNotificationsRead",
        params = ""
    ) {
        if (useMock) {
            kotlinx.coroutines.delay(200)
            ApiResult.Success(Unit)
        } else {
            apiCall { apiService.markAllNotificationsRead() }
        }
    }

    override suspend fun clearAllNotifications(): Flow<ApiResult<Unit>> = apiFlow(
        operation = "clearAllNotifications",
        params = ""
    ) {
        if (useMock) {
            kotlinx.coroutines.delay(200)
            ApiResult.Success(Unit)
        } else {
            apiCall { apiService.clearAllNotifications() }
        }
    }

    // ==================== 服务预约模块实现 ====================

    override suspend fun bookService(
        houseId: Int,
        serviceType: String,
        contactName: String,
        contactPhone: String,
        appointmentDate: String,
        remark: String?
    ): Flow<ApiResult<Unit>> = apiFlow(
        operation = "bookService",
        params = "houseId=$houseId, serviceType=$serviceType"
    ) {
        if (useMock) {
            kotlinx.coroutines.delay(500)
            ApiResult.Success(Unit)
        } else {
            apiCall {
                apiService.bookService(
                    BookServiceRequest(houseId, serviceType, contactName, contactPhone, appointmentDate, remark)
                )
            }
        }
    }

    override suspend fun getMaintenanceLog(houseId: Int): Flow<ApiResult<List<MaintenanceLogItem>>> = apiFlow(
        operation = "getMaintenanceLog",
        params = "houseId=$houseId"
    ) {
        if (useMock) {
            kotlinx.coroutines.delay(300)
            val mockLogs = listOf(
                MaintenanceLogItem(1, "滤芯更换", "2026-04-15", "前置过滤器滤芯更换", "张三"),
                MaintenanceLogItem(2, "系统检修", "2026-03-20", "新风系统年度检修", "李四"),
                MaintenanceLogItem(3, "常规保养", "2026-05-10", "全屋水管道清洗维护", "王五")
            )
            ApiResult.Success(mockLogs)
        } else {
            apiCall { apiService.getMaintenanceLog(houseId) }
        }
    }

    // ==================== 设备扩展模块实现 ====================

    override suspend fun getDeviceHistoryData(
        deviceId: Int,
        dataType: String?,
        startTime: Long?,
        endTime: Long?
    ): Flow<ApiResult<List<HistoryDataPoint>>> = apiFlow(
        operation = "getDeviceHistoryData",
        params = "deviceId=$deviceId, dataType=$dataType"
    ) {
        if (useMock) {
            kotlinx.coroutines.delay(300)
            val now = System.currentTimeMillis() / 1000
            val mockData = (0..23).map { i ->
                HistoryDataPoint(
                    timestamp = now - (23 - i) * 3600,
                    temperature = "%.1f".format(22.0 + kotlin.math.sin(i * 0.3) * 3.0),
                    humidity = "%.1f".format(45.0 + kotlin.math.cos(i * 0.2) * 5.0),
                    co2 = 400 + (i % 3) * 50,
                    pm25 = 20 + i % 4 * 5
                )
            }
            ApiResult.Success(mockData)
        } else {
            apiCall { apiService.getDeviceHistoryData(deviceId, dataType, startTime, endTime) }
        }
    }

    override suspend fun renameDevice(deviceId: Int, deviceName: String): Flow<ApiResult<Unit>> = apiFlow(
        operation = "renameDevice",
        params = "deviceId=$deviceId, deviceName=$deviceName"
    ) {
        if (useMock) {
            kotlinx.coroutines.delay(300)
            ApiResult.Success(Unit)
        } else {
            apiCall { apiService.renameDevice(RenameDeviceRequest(deviceId, deviceName)) }
        }
    }

    override suspend fun deleteDevice(deviceId: Int): Flow<ApiResult<Unit>> = apiFlow(
        operation = "deleteDevice",
        params = "deviceId=$deviceId"
    ) {
        if (useMock) {
            kotlinx.coroutines.delay(300)
            ApiResult.Success(Unit)
        } else {
            apiCall { apiService.deleteDevice(DeleteDeviceRequest(deviceId)) }
        }
    }
}
