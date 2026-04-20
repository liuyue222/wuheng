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
 * 设备详情页面 ViewModel（生产级实现）
 *
 * 职责：
 * 1. 管理设备信息数据状态
 * 2. 管理设备实时数据状态
 * 3. 处理设备控制操作
 * 4. 提供刷新功能
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

            homeRepository.getDeviceInfo(deviceId).collectLatest { result ->
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

            homeRepository.getDeviceData(deviceId).collectLatest { result ->
                when (result) {
                    is ApiResult.Loading -> {
                        _deviceDataState.value = UiDataState.Loading
                    }
                    is ApiResult.Success -> {
                        _deviceDataState.value = UiDataState.Success(result.data)
                        Timber.d("Loaded device data: temp=${result.data.temperature}")
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
     * 刷新设备数据
     *
     * @param deviceId 设备ID
     */
    fun refreshDeviceData(deviceId: Int) {
        loadDeviceInfo(deviceId)
        loadDeviceData(deviceId)
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
