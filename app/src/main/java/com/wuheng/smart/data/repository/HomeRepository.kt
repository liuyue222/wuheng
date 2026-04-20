package com.wuheng.smart.data.repository

import com.wuheng.smart.data.model.*
import com.wuheng.smart.data.network.ApiResult
import com.wuheng.smart.data.network.ApiService
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
     * 获取楼层列表
     *
     * @param houseId 房屋ID
     * @return 楼层列表
     */
    suspend fun getFloorList(houseId: Int): Flow<ApiResult<List<FloorInfo>>>

    /**
     * 获取房间列表
     *
     * @param houseId 房屋ID
     * @param floorId 楼层ID（可选，不传则返回所有房间）
     * @return 房间列表
     */
    suspend fun getRoomList(houseId: Int, floorId: Int? = null): Flow<ApiResult<List<RoomInfo>>>

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
    suspend fun getDeviceInfo(deviceId: Int): Flow<ApiResult<DeviceInfo>>

    /**
     * 获取设备实时数据
     *
     * @param deviceId 设备ID
     * @return 设备实时数据（温度、湿度、CO2等）
     */
    suspend fun getDeviceData(deviceId: Int): Flow<ApiResult<DeviceData>>

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

    // ==================== 新版API实现 - 房屋模块 ====================

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
            apiCall { apiService.getHouseInfo(houseId) }
        }
    }

    override suspend fun getFloorList(houseId: Int): Flow<ApiResult<List<FloorInfo>>> = apiFlow(
        operation = "getFloorList",
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
            apiCall { apiService.getFloorList(houseId) }
        }
    }

    override suspend fun getRoomList(houseId: Int, floorId: Int?): Flow<ApiResult<List<RoomInfo>>> = apiFlow(
        operation = "getRoomList",
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
            apiCall { apiService.getRoomList(houseId, floorId) }
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

    override suspend fun getDeviceInfo(deviceId: Int): Flow<ApiResult<DeviceInfo>> = apiFlow(
        operation = "getDeviceInfo",
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
            apiCall { apiService.getDeviceInfo(deviceId) }
        }
    }

    override suspend fun getDeviceData(deviceId: Int): Flow<ApiResult<DeviceData>> = apiFlow(
        operation = "getDeviceData",
        params = "deviceId=$deviceId"
    ) {
        if (useMock) {
            kotlinx.coroutines.delay(300)
            val mockData = DeviceData(
                dataId = 1,
                deviceId = deviceId,
                temperature = "24.50",
                humidity = "45.00",
                co2 = 420,
                pm25 = 35,
                voc = 150,
                fanSpeed = 1,
                valveOpen = 1,
                power = 1,
                reportTime = System.currentTimeMillis() / 1000
            )
            ApiResult.Success(mockData)
        } else {
            apiCall { apiService.getDeviceData(deviceId) }
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

    // ==================== 新版API实现 - 场景模块 ====================

    override suspend fun getSceneList(houseId: Int): Flow<ApiResult<List<SceneInfo>>> = apiFlow(
        operation = "getSceneList",
        params = "houseId=$houseId"
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
            apiCall { apiService.getSceneList(houseId) }
        }
    }

    override suspend fun applyScene(sceneId: Int, houseId: Int): Flow<ApiResult<ApplySceneResponse>> = apiFlow(
        operation = "applyScene",
        params = "sceneId=$sceneId, houseId=$houseId"
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
            apiCall { apiService.applyScene(ApplySceneRequest(sceneId, houseId)) }
        }
    }

    override suspend fun saveScene(request: SaveSceneRequest): Flow<ApiResult<Unit>> = apiFlow(
        operation = "saveScene",
        params = "houseId=${request.houseId}, name=${request.sceneName}"
    ) {
        if (useMock) {
            kotlinx.coroutines.delay(500)
            ApiResult.Success(Unit)
        } else {
            apiCall { apiService.saveScene(request) }
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
            apiCall { apiService.getSystemStatus(houseId) }
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
}
