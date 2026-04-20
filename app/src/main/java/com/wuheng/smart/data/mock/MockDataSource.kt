package com.wuheng.smart.data.mock

import com.wuheng.smart.data.model.*
import com.wuheng.smart.data.network.BaseResponse
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlin.random.Random

/**
 * Mock数据源 - 模拟网络请求
 * 提供模拟的API调用方法，支持模拟网络延迟
 */
object MockDataSource {

    // 模拟网络延迟范围 (500-1500ms)
    private const val MIN_DELAY = 500L
    private const val MAX_DELAY = 1500L

    /**
     * 获取模拟延迟时间
     */
    private suspend fun simulateNetworkDelay() {
        val delayTime = Random.nextLong(MIN_DELAY, MAX_DELAY)
        delay(delayTime)
    }

    /**
     * 创建成功响应
     */
    private fun <T> createSuccessResponse(data: T): BaseResponse<T> {
        return BaseResponse(
            code = 200,
            message = "success",
            data = data
        )
    }

    /**
     * 创建成功响应（无数据）
     */
    private fun createEmptySuccessResponse(): BaseResponse<Unit> {
        return BaseResponse(
            code = 200,
            message = "success",
            data = Unit
        )
    }

    // ==================== 首页数据 ====================

    /**
     * 获取首页概览数据
     */
    fun getHomeOverview(): Flow<BaseResponse<HomeOverview>> = flow {
        simulateNetworkDelay()
        emit(createSuccessResponse(MockData.mockHomeOverview))
    }

    /**
     * 获取所有设备列表
     */
    fun getAllDevices(): Flow<BaseResponse<List<Device>>> = flow {
        simulateNetworkDelay()
        emit(createSuccessResponse(MockData.mockDevices))
    }

    // ==================== 冷暖系统 ====================

    /**
     * 获取冷暖系统概览
     */
    fun getClimateOverview(): Flow<BaseResponse<ClimateOverview>> = flow {
        simulateNetworkDelay()
        emit(createSuccessResponse(MockData.mockClimateOverview))
    }

    /**
     * 获取楼层列表
     */
    fun getFloors(): Flow<BaseResponse<List<Floor>>> = flow {
        simulateNetworkDelay()
        emit(createSuccessResponse(MockData.mockFloors))
    }

    /**
     * 根据楼层ID获取区域列表
     */
    fun getZonesByFloor(floorId: String): Flow<BaseResponse<List<Zone>>> = flow {
        simulateNetworkDelay()
        val zones = MockData.mockZones.filter { it.floorId == floorId }
        emit(createSuccessResponse(zones))
    }

    /**
     * 获取区域详情
     */
    fun getZoneDetail(zoneId: String): Flow<BaseResponse<ZoneDetail>> = flow {
        simulateNetworkDelay()
        val zoneDetail = MockData.mockZoneDetails.find { it.id == zoneId }
            ?: throw NoSuchElementException("Zone not found: $zoneId")
        emit(createSuccessResponse(zoneDetail))
    }

    /**
     * 设置区域温度
     */
    fun setZoneTemperature(zoneId: String, temperature: Double): Flow<BaseResponse<Unit>> = flow {
        simulateNetworkDelay()
        // 模拟更新本地数据
        updateZoneTemperature(zoneId, temperature)
        emit(createEmptySuccessResponse())
    }

    /**
     * 设置区域模式
     */
    fun setZoneMode(zoneId: String, mode: ClimateMode): Flow<BaseResponse<Unit>> = flow {
        simulateNetworkDelay()
        updateZoneMode(zoneId, mode)
        emit(createEmptySuccessResponse())
    }

    /**
     * 设置区域电源开关
     */
    fun setZonePower(zoneId: String, powerOn: Boolean): Flow<BaseResponse<Unit>> = flow {
        simulateNetworkDelay()
        updateZonePower(zoneId, powerOn)
        emit(createEmptySuccessResponse())
    }

    // ==================== 用户数据（增强版）====================

    /**
     * 用户登录
     */
    fun login(phone: String, code: String?, password: String?): Flow<BaseResponse<UserInfo>> = flow {
        simulateNetworkDelay()
        emit(createSuccessResponse(MockData.mockUserInfo))
    }

    /**
     * 用户登出
     */
    fun logout(): Flow<BaseResponse<Unit>> = flow {
        simulateNetworkDelay()
        emit(createEmptySuccessResponse())
    }

    // ==================== 内部更新方法 ====================

    /**
     * 更新区域温度（内部方法，模拟数据更新）
     */
    private fun updateZoneTemperature(zoneId: String, temperature: Double) {
        val zoneIndex = MockData.mockZones.indexOfFirst { it.id == zoneId }
        if (zoneIndex != -1) {
            val oldZone = MockData.mockZones[zoneIndex]
            MockData.mockZones.toMutableList()[zoneIndex] = oldZone.copy(
                targetTemperature = temperature
            )
        }
    }

    /**
     * 更新区域模式（内部方法，模拟数据更新）
     */
    private fun updateZoneMode(zoneId: String, mode: ClimateMode) {
        val zoneIndex = MockData.mockZones.indexOfFirst { it.id == zoneId }
        if (zoneIndex != -1) {
            val oldZone = MockData.mockZones[zoneIndex]
            MockData.mockZones.toMutableList()[zoneIndex] = oldZone.copy(
                mode = mode,
                isRunning = mode != ClimateMode.OFF
            )
        }
    }

    /**
     * 更新区域电源（内部方法，模拟数据更新）
     */
    private fun updateZonePower(zoneId: String, powerOn: Boolean) {
        val zoneIndex = MockData.mockZones.indexOfFirst { it.id == zoneId }
        if (zoneIndex != -1) {
            val oldZone = MockData.mockZones[zoneIndex]
            MockData.mockZones.toMutableList()[zoneIndex] = oldZone.copy(
                isRunning = powerOn,
                mode = if (powerOn) ClimateMode.COOLING else ClimateMode.OFF
            )
        }
    }

}
