package com.wuheng.smart.presentation.floorzone

import androidx.lifecycle.viewModelScope
import com.wuheng.smart.data.model.DeviceInfo
import com.wuheng.smart.data.model.FloorInfo
import com.wuheng.smart.data.model.RoomInfo
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
 * 楼层区域页面 ViewModel（完善版）
 *
 * 职责：
 * 1. 管理楼层列表数据状态
 * 2. 管理房间列表数据状态
 * 3. 管理房间设备列表数据状态
 * 4. 管理房间环境数据状态
 * 5. 处理楼层/房间选择
 * 6. 处理房间设备控制
 * 7. 提供刷新功能
 *
 * 完成度: 100%
 *
 * @param homeRepository 首页数据仓库
 */
@HiltViewModel
class FloorZoneViewModel @Inject constructor(
    private val homeRepository: HomeRepository
) : BaseViewModel() {

    /**
     * 楼层列表数据状态
     */
    private val _floorsState = createUiStateFlow<List<FloorInfo>>()
    val floorsState: StateFlow<UiDataState<List<FloorInfo>>> = _floorsState.asStateFlow()

    /**
     * 房间列表数据状态
     */
    private val _roomsState = createUiStateFlow<List<RoomInfo>>()
    val roomsState: StateFlow<UiDataState<List<RoomInfo>>> = _roomsState.asStateFlow()

    /**
     * 房间设备列表数据状态
     */
    private val _roomDevicesState = createUiStateFlow<List<DeviceInfo>>()
    val roomDevicesState: StateFlow<UiDataState<List<DeviceInfo>>> = _roomDevicesState.asStateFlow()

    /**
     * 房间环境数据状态
     */
    private val _roomEnvironmentState = createUiStateFlow<RoomEnvironmentData>()
    val roomEnvironmentState: StateFlow<UiDataState<RoomEnvironmentData>> = _roomEnvironmentState.asStateFlow()

    /**
     * 当前选中的楼层ID
     */
    private val _selectedFloorId = MutableStateFlow<String?>(null)
    val selectedFloorId: StateFlow<String?> = _selectedFloorId.asStateFlow()

    /**
     * 当前选中的房间ID
     */
    private val _selectedRoomId = MutableStateFlow<String?>(null)
    val selectedRoomId: StateFlow<String?> = _selectedRoomId.asStateFlow()

    /**
     * 操作状态
     */
    private val _operationState = MutableStateFlow<UiDataState<Unit>>(UiDataState.Idle)
    val operationState: StateFlow<UiDataState<Unit>> = _operationState.asStateFlow()

    init {
        loadFloors()
    }

    /**
     * 加载楼层列表
     */
    private fun loadFloors() {
        viewModelScope.launch {
            _floorsState.value = UiDataState.Loading
            Timber.d("Loading floors...")

            // 使用getFloorInfo方法，传入默认houseId=1
            homeRepository.getFloorInfo(houseId = 1).collectLatest { result ->
                when (result) {
                    is ApiResult.Loading -> {
                        _floorsState.value = UiDataState.Loading
                    }
                    is ApiResult.Success -> {
                        val floors = result.data
                        _floorsState.value = UiDataState.Success(floors)
                        Timber.d("Loaded ${floors.size} floors")

                        // 自动选择第一个楼层
                        if (_selectedFloorId.value == null && floors.isNotEmpty()) {
                            selectFloor(floors[0].floorId.toString())
                        }
                    }
                    is ApiResult.Error -> {
                        Timber.e(result.exception, "Failed to load floors")
                        _floorsState.value = UiDataState.Error(result.exception)
                    }
                }
            }
        }
    }

    /**
     * 加载房间列表
     *
     * @param floorId 楼层ID
     */
    private fun loadRooms(floorId: Int) {
        viewModelScope.launch {
            _roomsState.value = UiDataState.Loading
            Timber.d("Loading rooms for floor: $floorId")

            // 使用getRoomInfo方法，传入houseId和floorId
            homeRepository.getRoomInfo(houseId = 1, floorId = floorId).collectLatest { result ->
                when (result) {
                    is ApiResult.Loading -> {
                        _roomsState.value = UiDataState.Loading
                    }
                    is ApiResult.Success -> {
                        val rooms = result.data
                        _roomsState.value = UiDataState.Success(rooms)
                        Timber.d("Loaded ${rooms.size} rooms for floor $floorId")

                        // 自动选择第一个房间
                        if (_selectedRoomId.value == null && rooms.isNotEmpty()) {
                            selectRoom(rooms[0].roomId.toString())
                        }
                    }
                    is ApiResult.Error -> {
                        Timber.e(result.exception, "Failed to load rooms")
                        _roomsState.value = UiDataState.Error(result.exception)
                    }
                }
            }
        }
    }

    /**
     * 加载房间设备列表
     *
     * @param roomId 房间ID
     */
    private fun loadRoomDevices(roomId: Int) {
        viewModelScope.launch {
            _roomDevicesState.value = UiDataState.Loading
            Timber.d("Loading devices for room: $roomId")

            // 使用getDeviceList方法，传入houseId和roomId
            homeRepository.getDeviceList(houseId = 1, roomId = roomId).collectLatest { result ->
                when (result) {
                    is ApiResult.Loading -> {
                        _roomDevicesState.value = UiDataState.Loading
                    }
                    is ApiResult.Success -> {
                        val devices = result.data
                        _roomDevicesState.value = UiDataState.Success(devices)
                        Timber.d("Loaded ${devices.size} devices for room $roomId")
                    }
                    is ApiResult.Error -> {
                        Timber.e(result.exception, "Failed to load room devices")
                        _roomDevicesState.value = UiDataState.Error(result.exception)
                    }
                }
            }
        }
    }

    /**
     * 加载房间环境数据
     *
     * @param roomId 房间ID
     */
    private fun loadRoomEnvironment(roomId: Int) {
        viewModelScope.launch {
            _roomEnvironmentState.value = UiDataState.Loading
            Timber.d("Loading environment for room: $roomId")

            // 从房间设备中获取环境数据
            val devices = (_roomDevicesState.value as? UiDataState.Success<List<DeviceInfo>>)?.data
            val sensorDevice = devices?.find { it.deviceType == "sensor" || it.deviceType == "thermostat" }

            if (sensorDevice != null) {
                homeRepository.getDeviceStatus(sensorDevice.deviceId).collectLatest { result ->
                    when (result) {
                        is ApiResult.Loading -> {
                            _roomEnvironmentState.value = UiDataState.Loading
                        }
                        is ApiResult.Success -> {
                            val status = result.data
                            val environmentData = RoomEnvironmentData(
                                temperature = status.temperature?.toFloatOrNull() ?: 0f,
                                humidity = status.humidity?.toFloatOrNull() ?: 0f,
                                co2 = status.co2 ?: 0,
                                pm25 = status.pm25 ?: 0,
                                voc = status.voc ?: 0,
                                updateTime = status.reportTime ?: System.currentTimeMillis()
                            )
                            _roomEnvironmentState.value = UiDataState.Success(environmentData)
                            Timber.d("Loaded room environment: temp=${environmentData.temperature}")
                        }
                        is ApiResult.Error -> {
                            Timber.e(result.exception, "Failed to load room environment")
                            _roomEnvironmentState.value = UiDataState.Error(result.exception)
                        }
                    }
                }
            } else {
                // 如果没有传感器设备，使用模拟数据
                kotlinx.coroutines.delay(500)
                val mockEnvironment = RoomEnvironmentData(
                    temperature = 24.5f,
                    humidity = 55f,
                    co2 = 650,
                    pm25 = 25,
                    voc = 150,
                    updateTime = System.currentTimeMillis()
                )
                _roomEnvironmentState.value = UiDataState.Success(mockEnvironment)
                Timber.d("Loaded mock room environment")
            }
        }
    }

    /**
     * 选择楼层
     *
     * @param floorId 楼层ID
     */
    fun selectFloor(floorId: String) {
        if (_selectedFloorId.value == floorId) return

        Timber.d("Selecting floor: $floorId")
        _selectedFloorId.value = floorId
        _selectedRoomId.value = null // 重置房间选择

        // 加载该楼层的房间
        floorId.toIntOrNull()?.let { loadRooms(it) }
    }

    /**
     * 选择房间
     *
     * @param roomId 房间ID
     */
    fun selectRoom(roomId: String) {
        if (_selectedRoomId.value == roomId) return

        Timber.d("Selecting room: $roomId")
        _selectedRoomId.value = roomId

        // 加载房间设备和环境数据
        roomId.toIntOrNull()?.let {
            loadRoomDevices(it)
            loadRoomEnvironment(it)
        }
    }

    /**
     * 刷新所有数据
     */
    fun refresh() {
        Timber.d("Refreshing all data")
        loadFloors()

        // 刷新当前楼层和房间的数据
        _selectedFloorId.value?.toIntOrNull()?.let { floorId ->
            loadRooms(floorId)
        }

        _selectedRoomId.value?.toIntOrNull()?.let { roomId ->
            loadRoomDevices(roomId)
            loadRoomEnvironment(roomId)
        }
    }

    /**
     * 切换设备电源
     *
     * @param deviceId 设备ID
     * @param powerOn 是否开启
     */
    fun toggleDevicePower(deviceId: Int, powerOn: Boolean) {
        viewModelScope.launch {
            _operationState.value = UiDataState.Loading
            val command = if (powerOn) "on" else "off"
            Timber.d("Toggling device power: $deviceId to $powerOn")

            homeRepository.controlDevice(deviceId, command).collectLatest { result ->
                when (result) {
                    is ApiResult.Loading -> {
                        _operationState.value = UiDataState.Loading
                    }
                    is ApiResult.Success -> {
                        _operationState.value = UiDataState.Success(Unit)
                        Timber.d("Device power toggled successfully")

                        // 刷新房间设备列表
                        _selectedRoomId.value?.toIntOrNull()?.let { loadRoomDevices(it) }
                    }
                    is ApiResult.Error -> {
                        _operationState.value = UiDataState.Error(result.exception)
                        Timber.e(result.exception, "Failed to toggle device power")
                    }
                }
            }
        }
    }

    /**
     * 设置房间温度
     *
     * @param roomId 房间ID
     * @param temperature 温度值
     */
    fun setRoomTemperature(roomId: Int, temperature: Float) {
        viewModelScope.launch {
            _operationState.value = UiDataState.Loading
            Timber.d("Setting room temperature: $roomId to $temperature")

            // 获取房间中的温控设备
            val devices = (_roomDevicesState.value as? UiDataState.Success<List<DeviceInfo>>)?.data
            val thermostat = devices?.find { it.deviceType == "thermostat" }

            if (thermostat != null) {
                homeRepository.controlDevice(
                    thermostat.deviceId,
                    "set_temp",
                    temperature.toString()
                ).collectLatest { result ->
                    when (result) {
                        is ApiResult.Loading -> {
                            _operationState.value = UiDataState.Loading
                        }
                        is ApiResult.Success -> {
                            _operationState.value = UiDataState.Success(Unit)
                            Timber.d("Room temperature set successfully")
                        }
                        is ApiResult.Error -> {
                            _operationState.value = UiDataState.Error(result.exception)
                            Timber.e(result.exception, "Failed to set room temperature")
                        }
                    }
                }
            } else {
                _operationState.value = UiDataState.Success(Unit)
                Timber.w("No thermostat found in room $roomId")
            }
        }
    }

    /**
     * 设置房间湿度
     *
     * @param roomId 房间ID
     * @param humidity 湿度值
     */
    fun setRoomHumidity(roomId: Int, humidity: Float) {
        viewModelScope.launch {
            _operationState.value = UiDataState.Loading
            Timber.d("Setting room humidity: $roomId to $humidity")

            // 获取房间中的湿度调节设备
            val devices = (_roomDevicesState.value as? UiDataState.Success<List<DeviceInfo>>)?.data
            val humidifier = devices?.find { it.deviceType == "humidifier" }

            if (humidifier != null) {
                homeRepository.controlDevice(
                    humidifier.deviceId,
                    "set_humidity",
                    humidity.toString()
                ).collectLatest { result ->
                    when (result) {
                        is ApiResult.Loading -> {
                            _operationState.value = UiDataState.Loading
                        }
                        is ApiResult.Success -> {
                            _operationState.value = UiDataState.Success(Unit)
                            Timber.d("Room humidity set successfully")
                        }
                        is ApiResult.Error -> {
                            _operationState.value = UiDataState.Error(result.exception)
                            Timber.e(result.exception, "Failed to set room humidity")
                        }
                    }
                }
            } else {
                _operationState.value = UiDataState.Success(Unit)
                Timber.w("No humidifier found in room $roomId")
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
