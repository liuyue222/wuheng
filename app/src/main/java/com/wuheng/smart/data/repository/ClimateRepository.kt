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
}
