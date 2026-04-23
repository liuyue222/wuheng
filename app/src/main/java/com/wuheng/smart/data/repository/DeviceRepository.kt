package com.wuheng.smart.data.repository

import com.wuheng.smart.data.model.*
import com.wuheng.smart.data.network.ApiResult
import com.wuheng.smart.data.network.ApiService
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

/**
 * 设备模块数据仓库接口
 *
 * 提供设备相关的所有数据操作方法，包括：
 * - 设备列表获取
 * - 设备详情获取
 * - 设备状态获取
 * - 设备控制
 *
 * 特性：
 * - 内置重试机制（网络错误时自动重试）
 * - 统一的错误处理
 * - Mock数据支持
 */
interface DeviceRepository {

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
    suspend fun controlDevice(
        deviceId: Int,
        command: String,
        value: String? = null
    ): Flow<ApiResult<ControlDeviceResponse>>
}

/**
 * 设备模块数据仓库实现类
 *
 * @param apiService Retrofit API服务
 * @param useMock 是否使用Mock数据，默认false使用真实API
 */
@Singleton
class DeviceRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val useMock: Boolean = false
) : BaseRepository(), DeviceRepository {

    companion object {
        // 重试配置
        const val MAX_RETRY_COUNT = 3
        const val RETRY_DELAY_MS = 1000L
    }

    override suspend fun getDeviceList(
        houseId: Int,
        roomId: Int?
    ): Flow<ApiResult<List<DeviceInfo>>> = apiFlowWithRetry(
        operation = "getDeviceList",
        params = "houseId=$houseId, roomId=$roomId",
        maxRetries = MAX_RETRY_COUNT
    ) {
        if (useMock) {
            delay(300)
            val mockDevices = listOf(
                DeviceInfo(
                    1,
                    "DEV202604190001",
                    "客厅温控器",
                    "thermostat",
                    "TH-2025A",
                    1,
                    "running",
                    "客厅"
                ),
                DeviceInfo(
                    2,
                    "DEV202604190002",
                    "主卧温控器",
                    "thermostat",
                    "TH-2025A",
                    1,
                    "standby",
                    "主卧"
                ),
                DeviceInfo(
                    3,
                    "DEV202604190003",
                    "环境传感器",
                    "sensor",
                    "SE-001",
                    1,
                    "running",
                    "客厅"
                ),
                DeviceInfo(
                    4,
                    "DEV202604190004",
                    "新风系统",
                    "fresh_air",
                    "FA-001",
                    1,
                    "running",
                    "全屋"
                ),
                DeviceInfo(
                    5,
                    "DEV202604190005",
                    "地暖控制器",
                    "floor_heating",
                    "FH-001",
                    1,
                    "running",
                    "客厅"
                ),
                DeviceInfo(
                    6,
                    "DEV202604190006",
                    "湿度调节器",
                    "humidifier",
                    "HM-001",
                    0,
                    "offline",
                    "主卧"
                )
            )
            ApiResult.Success(mockDevices)
        } else {
            apiCall { apiService.getDeviceList(houseId, roomId) }
        }
    }

    override suspend fun getDeviceDetail(deviceId: Int): Flow<ApiResult<DeviceInfo>> =
        apiFlowWithRetry(
            operation = "getDeviceDetail",
            params = "deviceId=$deviceId",
            maxRetries = MAX_RETRY_COUNT
        ) {
            if (useMock) {
                delay(300)
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

    override suspend fun getDeviceStatus(deviceId: Int): Flow<ApiResult<DeviceStatus>> =
        apiFlowWithRetry(
            operation = "getDeviceStatus",
            params = "deviceId=$deviceId",
            maxRetries = MAX_RETRY_COUNT
        ) {
            if (useMock) {
                delay(300)
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
    ): Flow<ApiResult<ControlDeviceResponse>> = apiFlowWithRetry(
        operation = "controlDevice",
        params = "deviceId=$deviceId, command=$command, value=$value",
        maxRetries = MAX_RETRY_COUNT
    ) {
        if (useMock) {
            delay(500)
            val mockResponse = ControlDeviceResponse(
                command = command,
                value = value ?: "",
                deviceId = deviceId
            )
            ApiResult.Success(mockResponse)
        } else {
            apiCall {
                apiService.controlDevice(
                    ControlDeviceRequest(
                        deviceId,
                        command,
                        value
                    )
                )
            }
        }
    }
}
