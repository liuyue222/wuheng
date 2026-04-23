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
 * 水系统数据仓库接口
 *
 * 提供水系统相关的所有数据操作方法，包括：
 * - 热水循环管理（获取状态、设置模式）
 * - 净水状态管理（获取TDS、水质等）
 * - 滤芯管理（获取状态、预约更换）
 */
interface WaterRepository {

    // ==================== 水系统模块 - 4个核心接口 ====================

    /**
     * 1. 获取热水循环状态
     * 对应API: GET /home/water/getHotWaterStatus
     *
     * @param houseId 房屋ID
     * @return 热水循环状态
     */
    suspend fun getHotWaterStatus(houseId: Int): Flow<ApiResult<HotWaterStatusResponse>>

    /**
     * 2. 设置循环模式
     * 对应API: POST /home/water/setCirculationMode
     *
     * @param houseId 房屋ID
     * @param mode 循环模式
     * @param duration 临时循环时长（分钟，仅TEMP模式需要）
     * @return 设置响应
     */
    suspend fun setCirculationMode(
        houseId: Int,
        mode: CirculationMode,
        duration: Int? = null
    ): Flow<ApiResult<SetCirculationModeResponse>>

    /**
     * 3. 获取净水状态
     * 对应API: GET /home/water/getWaterPurifierStatus
     *
     * @param houseId 房屋ID
     * @return 净水状态（TDS、水质等）
     */
    suspend fun getWaterPurifierStatus(houseId: Int): Flow<ApiResult<WaterPurifierStatusResponse>>

    /**
     * 4. 获取滤芯状态列表
     * 对应API: GET /home/water/getFilterStatus
     *
     * @param houseId 房屋ID
     * @return 滤芯状态列表
     */
    suspend fun getFilterStatus(houseId: Int): Flow<ApiResult<List<FilterStatusInfo>>>

    // ==================== 扩展功能接口 ====================

    /**
     * 预约滤芯更换
     *
     * @param houseId 房屋ID
     * @param filterId 滤芯ID
     * @param contactName 联系人姓名（可选）
     * @param contactPhone 联系人电话（可选）
     * @param appointmentDate 预约日期（可选）
     */
    suspend fun bookFilterReplace(
        houseId: Int,
        filterId: Int,
        contactName: String? = null,
        contactPhone: String? = null,
        appointmentDate: String? = null
    ): Flow<ApiResult<Unit>>
}

/**
 * 水系统数据仓库实现类
 *
 * @param apiService Retrofit API服务
 * @param useMock 是否使用Mock数据，默认false使用真实API
 */
@Singleton
class WaterRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val useMock: Boolean = false
) : BaseRepository(), WaterRepository {

    companion object {
        private const val MAX_RETRY_COUNT = 3
        private const val RETRY_DELAY_MS = 1000L
    }

    // ==================== 核心API实现 - 带重试机制 ====================

    override suspend fun getHotWaterStatus(houseId: Int): Flow<ApiResult<HotWaterStatusResponse>> = flow {
        logOperation("getHotWaterStatus", "houseId=$houseId")
        emit(ApiResult.Loading)

        val result = if (useMock) {
            delay(300)
            val mockStatus = HotWaterStatusResponse(
                currentTemp = "55.00",
                targetTemp = "55.00",
                circulationMode = "all_day",
                circulationStatus = 1,
                sterilizationEnable = 1,
                sterilizationTime = "02:00:00"
            )
            ApiResult.Success(mockStatus)
        } else {
            apiCallWithRetry(maxRetries = MAX_RETRY_COUNT) { apiService.getHotWaterStatus(houseId) }
        }
        emit(result)
    }

    override suspend fun setCirculationMode(
        houseId: Int,
        mode: CirculationMode,
        duration: Int?
    ): Flow<ApiResult<SetCirculationModeResponse>> = flow {
        logOperation("setCirculationMode", "houseId=$houseId, mode=${mode.value}, duration=$duration")
        emit(ApiResult.Loading)

        val result = if (useMock) {
            delay(500)
            ApiResult.Success(SetCirculationModeResponse(mode.value))
        } else {
            apiCallWithRetry(maxRetries = MAX_RETRY_COUNT) {
                apiService.setCirculationMode(
                    SetCirculationModeRequest(houseId, mode.value, duration)
                )
            }
        }
        emit(result)
    }

    override suspend fun getWaterPurifierStatus(houseId: Int): Flow<ApiResult<WaterPurifierStatusResponse>> = flow {
        logOperation("getWaterPurifierStatus", "houseId=$houseId")
        emit(ApiResult.Loading)

        val result = if (useMock) {
            delay(300)
            val mockStatus = WaterPurifierStatusResponse(
                tdsIn = 150,
                tdsOut = 15,
                waterQuality = "excellent",
                totalFlow = "1250.5",
                dailyFlow = "45.2",
                deviceStatus = 1,
                lastUpdate = "2026-04-23 14:30:00"
            )
            ApiResult.Success(mockStatus)
        } else {
            apiCallWithRetry(maxRetries = MAX_RETRY_COUNT) { apiService.getWaterPurifierStatus(houseId) }
        }
        emit(result)
    }

    override suspend fun getFilterStatus(houseId: Int): Flow<ApiResult<List<FilterStatusInfo>>> = flow {
        logOperation("getFilterStatus", "houseId=$houseId")
        emit(ApiResult.Loading)

        val result = if (useMock) {
            delay(300)
            val mockFilters = listOf(
                FilterStatusInfo(1, "前置过滤器", "pre", 85, 0),
                FilterStatusInfo(2, "中央净水器", "central", 60, 0),
                FilterStatusInfo(3, "末端直饮机", "end", 45, 1)
            )
            ApiResult.Success(mockFilters)
        } else {
            apiCallWithRetry(maxRetries = MAX_RETRY_COUNT) { apiService.getFilterStatus(houseId) }
        }
        emit(result)
    }

    override suspend fun bookFilterReplace(
        houseId: Int,
        filterId: Int,
        contactName: String?,
        contactPhone: String?,
        appointmentDate: String?
    ): Flow<ApiResult<Unit>> = flow {
        logOperation("bookFilterReplace", "houseId=$houseId, filterId=$filterId")
        emit(ApiResult.Loading)

        val result = if (useMock) {
            delay(500)
            ApiResult.Success(Unit)
        } else {
            // 注意：bookFilterReplace不在4个核心接口中，需要后端确认
            // 这里使用模拟实现
            Timber.w("bookFilterReplace API not in core 4 endpoints, using mock")
            ApiResult.Success(Unit)
        }
        emit(result)
    }
}
