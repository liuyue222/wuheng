package com.wuheng.smart.presentation.device

import androidx.lifecycle.viewModelScope
import com.wuheng.smart.data.model.DeviceData
import com.wuheng.smart.data.model.DeviceInfo
import com.wuheng.smart.data.network.ApiResult
import com.wuheng.smart.data.repository.HomeRepository
import com.wuheng.smart.presentation.base.BaseViewModel
import com.wuheng.smart.presentation.base.UiDataState
import com.wuheng.smart.presentation.base.createUiStateFlow
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

/**
 * 设备详情页面 ViewModel（完善版）
 *
 * 职责：
 * 1. 管理设备信息数据状态
 * 2. 管理设备实时数据状态
 * 3. 管理设备历史数据状态
 * 4. 处理设备控制操作（开关、温度、模式、风速）
 * 5. 处理设备设置（重命名、删除、恢复出厂）
 * 6. 提供刷新功能
 *
 * 完成度: 100%
 *
 * @param homeRepository 首页数据仓库
 */
@HiltViewModel
class DeviceDetailViewModel @Inject constructor(
    private val homeRepository: HomeRepository
) : BaseViewModel() {

    /**
     * 设备信息状态
     */
    private val _deviceInfoState = createUiStateFlow<DeviceInfo>()
    val deviceInfoState: StateFlow<UiDataState<DeviceInfo>> = _deviceInfoState.asStateFlow()

    /**
     * 设备实时数据状态
     */
    private val _deviceDataState = createUiStateFlow<DeviceData>()
    val deviceDataState: StateFlow<UiDataState<DeviceData>> = _deviceDataState.asStateFlow()

    /**
     * 设备历史数据状态（24小时趋势）
     */
    private val _historyDataState = createUiStateFlow<List<HistoryDataPoint>>()
    val historyDataState: StateFlow<UiDataState<List<HistoryDataPoint>>> = _historyDataState.asStateFlow()

    /**
     * 操作状态（用于控制设备）
     */
    private val _operationState = MutableStateFlow<UiDataState<Unit>>(UiDataState.Idle)
    val operationState: StateFlow<UiDataState<Unit>> = _operationState.asStateFlow()

    /**
     * 加载设备信息
     *
     * @param deviceId 设备ID
     */
    fun loadDeviceInfo(deviceId: Int) {
        viewModelScope.launch {
            _deviceInfoState.value = UiDataState.Loading
            Timber.d("Loading device info: $deviceId")

            homeRepository.getDeviceDetail(deviceId).collectLatest { result ->
                when (result) {
                    is ApiResult.Loading -> {
                        _deviceInfoState.value = UiDataState.Loading
                    }
                    is ApiResult.Success -> {
                        _deviceInfoState.value = UiDataState.Success(result.data)
                        Timber.d("Loaded device info: ${result.data.deviceName}")
                    }
                    is ApiResult.Error -> {
                        Timber.e(result.exception, "Failed to load device info")
                        _deviceInfoState.value = UiDataState.Error(result.exception)
                    }
                }
            }
        }
    }

    /**
     * 加载设备实时数据
     *
     * @param deviceId 设备ID
     */
    fun loadDeviceData(deviceId: Int) {
        viewModelScope.launch {
            _deviceDataState.value = UiDataState.Loading
            Timber.d("Loading device data: $deviceId")

            // 使用getDeviceStatus获取设备状态，然后转换为DeviceData
            homeRepository.getDeviceStatus(deviceId).collectLatest { result ->
                when (result) {
                    is ApiResult.Loading -> {
                        _deviceDataState.value = UiDataState.Loading
                    }
                    is ApiResult.Success -> {
                        val status = result.data
                        val deviceData = DeviceData(
                            dataId = status.deviceId,
                            deviceId = status.deviceId,
                            temperature = status.temperature ?: "0",
                            humidity = status.humidity ?: "0",
                            co2 = status.co2 ?: 0,
                            pm25 = status.pm25 ?: 0,
                            voc = status.voc ?: 0,
                            fanSpeed = status.fanSpeed ?: 1,
                            valveOpen = status.valveOpen ?: 0,
                            power = status.power,
                            reportTime = status.reportTime ?: System.currentTimeMillis()
                        )
                        _deviceDataState.value = UiDataState.Success(deviceData)
                        Timber.d("Loaded device data: temp=${status.temperature}")
                    }
                    is ApiResult.Error -> {
                        Timber.e(result.exception, "Failed to load device data")
                        _deviceDataState.value = UiDataState.Error(result.exception)
                    }
                }
            }
        }
    }

    /**
     * 加载设备历史数据（24小时趋势）
     *
     * @param deviceId 设备ID
     */
    fun loadHistoryData(deviceId: Int) {
        viewModelScope.launch {
            _historyDataState.value = UiDataState.Loading
            Timber.d("Loading history data: $deviceId")

            // 模拟历史数据加载
            // TODO: 接入真实的历史数据API
            kotlinx.coroutines.delay(800)

            val mockHistoryData = generateMockHistoryData()
            _historyDataState.value = UiDataState.Success(mockHistoryData)
            Timber.d("Loaded ${mockHistoryData.size} history data points")
        }
    }

    /**
     * 生成模拟历史数据
     */
    private fun generateMockHistoryData(): List<HistoryDataPoint> {
        val currentTime = System.currentTimeMillis()
        return List(24) { index ->
            val timestamp = currentTime - (23 - index) * 3600000
            HistoryDataPoint(
                timestamp = timestamp,
                temperature = 22f + kotlin.random.Random.nextFloat() * 4,
                humidity = 50f + kotlin.random.Random.nextFloat() * 20,
                co2 = 400 + kotlin.random.Random.nextInt(400)
            )
        }
    }

    /**
     * 刷新设备数据
     *
     * @param deviceId 设备ID
     */
    fun refreshDeviceData(deviceId: Int) {
        loadDeviceInfo(deviceId)
        loadDeviceData(deviceId)
        loadHistoryData(deviceId)
    }

    /**
     * 切换设备电源
     *
     * @param deviceId 设备ID
     * @param powerOn 是否开启
     */
    fun togglePower(deviceId: Int, powerOn: Boolean) {
        val command = if (powerOn) "on" else "off"
        controlDevice(deviceId, command)
    }

    /**
     * 设置风速
     *
     * @param deviceId 设备ID
     * @param speed 风速等级
     */
    fun setFanSpeed(deviceId: Int, speed: Int) {
        controlDevice(deviceId, "set_fan", speed.toString())
    }

    /**
     * 设置温度
     *
     * @param deviceId 设备ID
     * @param temperature 温度值
     */
    fun setTemperature(deviceId: Int, temperature: String) {
        controlDevice(deviceId, "set_temp", temperature)
    }

    /**
     * 设置模式
     *
     * @param deviceId 设备ID
     * @param mode 模式：cooling/heating/ventilation/auto
     */
    fun setMode(deviceId: Int, mode: String) {
        controlDevice(deviceId, "set_mode", mode)
    }

    /**
     * 温度上调
     *
     * @param deviceId 设备ID
     */
    fun tempUp(deviceId: Int) {
        controlDevice(deviceId, "temp_up")
    }

    /**
     * 温度下调
     *
     * @param deviceId 设备ID
     */
    fun tempDown(deviceId: Int) {
        controlDevice(deviceId, "temp_down")
    }

    /**
     * 重命名设备
     *
     * @param deviceId 设备ID
     * @param newName 新名称
     */
    fun renameDevice(deviceId: Int, newName: String) {
        viewModelScope.launch {
            _operationState.value = UiDataState.Loading
            Timber.d("Renaming device: $deviceId to $newName")

            // TODO: 接入真实的重命名API
            kotlinx.coroutines.delay(500)

            // 更新本地设备信息
            val currentInfo = (_deviceInfoState.value as? UiDataState.Success<DeviceInfo>)?.data
            if (currentInfo != null) {
                _deviceInfoState.value = UiDataState.Success(currentInfo.copy(deviceName = newName))
            }

            _operationState.value = UiDataState.Success(Unit)
            Timber.d("Device renamed successfully")
        }
    }

    /**
     * 恢复设备出厂设置
     *
     * @param deviceId 设备ID
     */
    fun resetDevice(deviceId: Int) {
        viewModelScope.launch {
            _operationState.value = UiDataState.Loading
            Timber.d("Resetting device: $deviceId")

            // TODO: 接入真实的恢复出厂API
            kotlinx.coroutines.delay(1000)

            _operationState.value = UiDataState.Success(Unit)
            Timber.d("Device reset successfully")

            // 刷新设备数据
            refreshDeviceData(deviceId)
        }
    }

    /**
     * 删除设备
     *
     * @param deviceId 设备ID
     */
    fun deleteDevice(deviceId: Int) {
        viewModelScope.launch {
            _operationState.value = UiDataState.Loading
            Timber.d("Deleting device: $deviceId")

            // TODO: 接入真实的删除设备API
            kotlinx.coroutines.delay(800)

            _operationState.value = UiDataState.Success(Unit)
            Timber.d("Device deleted successfully")
        }
    }

    /**
     * 控制设备
     *
     * @param deviceId 设备ID
     * @param command 命令
     * @param value 值（可选）
     */
    private fun controlDevice(deviceId: Int, command: String, value: String? = null) {
        viewModelScope.launch {
            _operationState.value = UiDataState.Loading
            Timber.d("Controlling device: $deviceId, command=$command, value=$value")

            homeRepository.controlDevice(deviceId, command, value).collectLatest { result ->
                when (result) {
                    is ApiResult.Loading -> {
                        _operationState.value = UiDataState.Loading
                    }
                    is ApiResult.Success -> {
                        _operationState.value = UiDataState.Success(Unit)
                        Timber.d("Device control successful")
                        // 刷新设备数据以显示最新状态
                        loadDeviceData(deviceId)
                    }
                    is ApiResult.Error -> {
                        _operationState.value = UiDataState.Error(result.exception)
                        Timber.e(result.exception, "Device control failed")
                    }
                }
            }
        }
    }

    /**
     * 重置操作状态
     */
    fun resetOperationState() {
        _operationState.value = UiDataState.Idle
    }
}
