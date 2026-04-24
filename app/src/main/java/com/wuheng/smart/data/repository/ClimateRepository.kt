package com.wuheng.smart.data.repository

import com.wuheng.smart.data.model.*
import com.wuheng.smart.data.network.ApiResult
import com.wuheng.smart.data.network.ApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

/**
 * 冷暖系统数据仓库接口
 *
 * 提供冷暖系统相关的所有数据操作方法，包括：
 * - 系统状态管理（获取、设置系统模式/温度/湿度）
 * - 楼层数据管理（获取楼层列表、房间列表）
 */
interface ClimateRepository {

    // ==================== 新版API - 系统模块 (4个接口) ====================

    /**
     * 获取系统状态
     *
     * @param houseId 房屋ID
     * @return 系统状态
     */
    suspend fun getSystemStatus(houseId: Int): Flow<ApiResult<SystemStatus>>

    /**
     * 设置系统模式
     *
     * @param houseId 房屋ID
     * @param mode 系统模式
     * @return 设置响应
     */
    suspend fun setSystemMode(houseId: Int, mode: SystemMode): Flow<ApiResult<SetSystemModeResponse>>

    /**
     * 设置全局温度
     *
     * @param houseId 房屋ID
     * @param temp 温度值
     */
    suspend fun setGlobalTemp(houseId: Int, temp: String): Flow<ApiResult<Unit>>

    /**
     * 设置全局湿度
     *
     * @param houseId 房屋ID
     * @param humidity 湿度值
     */
    suspend fun setGlobalHumidity(houseId: Int, humidity: String): Flow<ApiResult<Unit>>

    // ==================== 房屋模块 - 楼层/房间数据 ====================

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
}

/**
 * 冷暖系统数据仓库实现类
 *
 * @param apiService Retrofit API服务
 * @param useMock 是否使用Mock数据，默认false使用真实API
 */
@Singleton
class ClimateRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val useMock: Boolean = false
) : BaseRepository(), ClimateRepository {

    // ==================== 新版API实现 - 系统模块 ====================

    override suspend fun getSystemStatus(houseId: Int): Flow<ApiResult<SystemStatus>> = flow {
        logOperation("getSystemStatus", "houseId=$houseId")
        emit(ApiResult.Loading)

        val result = if (useMock) {
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
        emit(result)
    }

    override suspend fun setSystemMode(houseId: Int, mode: SystemMode): Flow<ApiResult<SetSystemModeResponse>> = flow {
        logOperation("setSystemMode", "houseId=$houseId, mode=${mode.value}")
        emit(ApiResult.Loading)

        val result = if (useMock) {
            kotlinx.coroutines.delay(500)
            ApiResult.Success(SetSystemModeResponse(mode.value))
        } else {
            apiCall { apiService.setSystemMode(SetSystemModeRequest(houseId, mode.value)) }
        }
        emit(result)
    }

    override suspend fun setGlobalTemp(houseId: Int, temp: String): Flow<ApiResult<Unit>> = flow {
        logOperation("setGlobalTemp", "houseId=$houseId, temp=$temp")
        emit(ApiResult.Loading)

        val result = if (useMock) {
            kotlinx.coroutines.delay(500)
            ApiResult.Success(Unit)
        } else {
            apiCall { apiService.setGlobalTemp(SetGlobalTempRequest(houseId, temp)) }
        }
        emit(result)
    }

    override suspend fun setGlobalHumidity(houseId: Int, humidity: String): Flow<ApiResult<Unit>> = flow {
        logOperation("setGlobalHumidity", "houseId=$houseId, humidity=$humidity")
        emit(ApiResult.Loading)

        val result = if (useMock) {
            kotlinx.coroutines.delay(500)
            ApiResult.Success(Unit)
        } else {
            apiCall { apiService.setGlobalHumidity(SetGlobalHumidityRequest(houseId, humidity)) }
        }
        emit(result)
    }

    // ==================== 房屋模块实现 - 楼层/房间数据 ====================

    override suspend fun getFloorInfo(houseId: Int): Flow<ApiResult<List<FloorInfo>>> = flow {
        logOperation("getFloorInfo", "houseId=$houseId")
        emit(ApiResult.Loading)

        val result = if (useMock) {
            kotlinx.coroutines.delay(300)
            val mockFloors = listOf(
                FloorInfo(1, "FLOOR202604190001", "地下一层", -1, "80.00", 0),
                FloorInfo(2, "FLOOR202604190002", "一层", 1, "100.00", 3),
                FloorInfo(3, "FLOOR202604190003", "二层", 2, "100.00", 2)
            )
            ApiResult.Success(mockFloors)
        } else {
            apiCall { apiService.getFloorInfo(houseId) }
        }
        emit(result)
    }

    override suspend fun getRoomInfo(houseId: Int, floorId: Int?): Flow<ApiResult<List<RoomInfo>>> = flow {
        logOperation("getRoomInfo", "houseId=$houseId, floorId=$floorId")
        emit(ApiResult.Loading)

        val result = if (useMock) {
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
            apiCall { apiService.getRoomInfo(houseId, floorId) }
        }
        emit(result)
    }
}
