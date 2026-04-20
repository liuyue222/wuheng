package com.wuheng.smart.presentation.home

import androidx.lifecycle.viewModelScope
import com.wuheng.smart.data.model.*
import com.wuheng.smart.data.network.ApiResult
import com.wuheng.smart.data.network.TokenManager
import com.wuheng.smart.data.repository.HomeRepository
import com.wuheng.smart.presentation.base.BaseViewModel
import com.wuheng.smart.presentation.base.UiDataState
import com.wuheng.smart.presentation.base.createUiStateFlow
import com.wuheng.smart.presentation.home.components.DeviceCardUiState
import com.wuheng.smart.presentation.home.components.WeatherModeSelectorUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * 首页统一的UI State
 * 用于新版Layout架构
 */
data class HomeUiState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val location: String = "",
    val outdoorTemp: Int = 0,
    val weather: String = "",
    val aqi: Int = 0,
    val pm25: Int = 0,
    val outdoorHumidity: Int = 0,
    val residenceName: String = "",
    val currentMode: ClimateMode = ClimateMode.COOLING,
    val indoorTemp: String = "--",
    val indoorHumidity: String = "--",
    val co2: Int = 0,
    val tovc: String = "--",
    val scenes: List<SceneItem> = emptyList(),
    val preheatPreheatEnabled: Boolean = false
)

data class SceneItem(
    val type: SceneType,
    val name: String,
    val isSelected: Boolean = false
)

enum class ClimateMode {
    COOLING, VENTILATION, HEATING
}

/**
 * 首页 ViewModel（生产级实现）
 *
 * 职责：
 * 1. 管理首页所有UI状态（设备列表、天气模式、环境数据等）
 * 2. 处理用户交互事件（设备开关、模式切换、服务点击等）
 * 3. 协调Repository层数据获取与UI状态更新
 *
 * 使用新版API：
 * - 房屋模块: getHouseInfo, getFloorList, getRoomList
 * - 设备模块: getDeviceList, getDeviceInfo, getDeviceData, controlDevice
 * - 场景模块: getSceneList, applyScene, saveScene
 * - 系统模块: getSystemStatus, setSystemMode, setGlobalTemp, setGlobalHumidity
 *
 * 状态管理策略：
 * - 使用 StateFlow<UiDataState<T>> 暴露UI状态
 * - 使用 SharedFlow 处理一次性事件（导航、Toast等）
 * - 遵循单向数据流（UDF）原则
 */
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val homeRepository: HomeRepository,
    private val tokenManager: TokenManager
) : BaseViewModel() {

    // ==================== 新版统一UI State ====================
    
    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()
    
    // ==================== UI State 定义 ====================

    /**
     * 新版API：房屋信息状态
     */
    private val _houseInfoState = createUiStateFlow<HouseInfo>()
    val houseInfoState: StateFlow<UiDataState<HouseInfo>> = _houseInfoState.asStateFlow()

    /**
     * 新版API：设备列表状态
     */
    private val _deviceListState = createUiStateFlow<List<DeviceInfo>>()
    val deviceListState: StateFlow<UiDataState<List<DeviceInfo>>> = _deviceListState.asStateFlow()

    /**
     * 新版API：场景列表状态
     */
    private val _sceneListState = createUiStateFlow<List<SceneInfo>>()
    val sceneListState: StateFlow<UiDataState<List<SceneInfo>>> = _sceneListState.asStateFlow()

    /**
     * 新版API：系统状态
     */
    private val _systemStatusState = createUiStateFlow<SystemStatus>()
    val systemStatusState: StateFlow<UiDataState<SystemStatus>> = _systemStatusState.asStateFlow()

    /**
     * 当前选中的天气模式状态
     */
    private val _weatherModeState = MutableStateFlow(WeatherModeSelectorUiState())
    val weatherModeState: StateFlow<WeatherModeSelectorUiState> = _weatherModeState.asStateFlow()

    /**
     * 设备控制操作状态（用于显示加载/成功/错误反馈）
     */
    private val _deviceOperationState = createUiStateFlow<Unit>()
    val deviceOperationState: StateFlow<UiDataState<Unit>> = _deviceOperationState.asStateFlow()

    /**
     * 服务点击事件（一次性事件，使用SharedFlow避免配置变更重复消费）
     */
    private val _serviceClickEvent = MutableSharedFlow<ServiceType>()
    val serviceClickEvent: SharedFlow<ServiceType> = _serviceClickEvent.asSharedFlow()

    /**
     * 设备卡片点击事件（导航到设备详情页，一次性事件）
     */
    private val _deviceClickEvent = MutableSharedFlow<String>()
    val deviceClickEvent: SharedFlow<String> = _deviceClickEvent.asSharedFlow()

    // ==================== 初始化 ====================

    init {
        loadInitialData()
    }

    /**
     * 加载初始数据
     */
    private fun loadInitialData() {
        // 使用新版API加载数据
        val houseId = tokenManager.getCurrentHouseId()
        if (houseId.isNotEmpty()) {
            loadHouseInfo(houseId)
            loadDeviceList(houseId)
            loadSceneList(houseId)
            loadSystemStatus(houseId)
        }
    }

    // ==================== 数据加载方法（新版API）====================

    /**
     * 加载房屋信息（新版API）
     */
    fun loadHouseInfo(houseId: String) {
        viewModelScope.launch {
            _houseInfoState.value = UiDataState.Loading
            _uiState.value = _uiState.value.copy(isLoading = true)
            homeRepository.getHouseInfo(houseId.toInt()).collectLatest { result ->
                when (result) {
                    is ApiResult.Loading -> {
                        _houseInfoState.value = UiDataState.Loading
                        _uiState.value = _uiState.value.copy(isLoading = true)
                    }
                    is ApiResult.Success -> {
                        _houseInfoState.value = UiDataState.Success(result.data)
                        _uiState.value = _uiState.value.copy(
                            isLoading = false,
                            errorMessage = null,
                            residenceName = result.data.houseName
                        )
                    }
                    is ApiResult.Error -> {
                        _houseInfoState.value = UiDataState.Error(result.exception)
                        _uiState.value = _uiState.value.copy(
                            isLoading = false,
                            errorMessage = result.exception.message
                        )
                    }
                }
            }
        }
    }

    /**
     * 加载设备列表（新版API）
     */
    fun loadDeviceList(houseId: String) {
        viewModelScope.launch {
            _deviceListState.value = UiDataState.Loading
            homeRepository.getDeviceList(houseId.toInt()).collectLatest { result ->
                when (result) {
                    is ApiResult.Loading -> _deviceListState.value = UiDataState.Loading
                    is ApiResult.Success -> _deviceListState.value = UiDataState.Success(result.data)
                    is ApiResult.Error -> _deviceListState.value = UiDataState.Error(result.exception)
                }
            }
        }
    }

    /**
     * 加载场景列表（新版API）
     */
    fun loadSceneList(houseId: String) {
        viewModelScope.launch {
            _sceneListState.value = UiDataState.Loading
            homeRepository.getSceneList(houseId.toInt()).collectLatest { result ->
                when (result) {
                    is ApiResult.Loading -> _sceneListState.value = UiDataState.Loading
                    is ApiResult.Success -> {
                        _sceneListState.value = UiDataState.Success(result.data)
                        // 将API场景数据映射到UI State，保持当前选中状态
                        val currentSelectedType = _uiState.value.scenes.find { it.isSelected }?.type
                        val sceneItems = result.data.map { sceneInfo ->
                            val sceneType = when (sceneInfo.sceneType) {
                                "guest" -> SceneType.MEETING
                                "away" -> SceneType.AWAY
                                "sleep" -> SceneType.SLEEP
                                "guard", "eco", "vacation" -> SceneType.GUARD
                                else -> SceneType.MEETING
                            }
                            SceneItem(
                                type = sceneType,
                                name = sceneInfo.sceneName,
                                isSelected = sceneType == currentSelectedType
                            )
                        }
                        _uiState.value = _uiState.value.copy(scenes = sceneItems)
                    }
                    is ApiResult.Error -> _sceneListState.value = UiDataState.Error(result.exception)
                }
            }
        }
    }

    /**
     * 加载系统状态（新版API）
     */
    fun loadSystemStatus(houseId: String) {
        viewModelScope.launch {
            _systemStatusState.value = UiDataState.Loading
            _uiState.value = _uiState.value.copy(isLoading = true)
            homeRepository.getSystemStatus(houseId.toInt()).collectLatest { result ->
                when (result) {
                    is ApiResult.Loading -> {
                        _systemStatusState.value = UiDataState.Loading
                        _uiState.value = _uiState.value.copy(isLoading = true)
                    }
                    is ApiResult.Success -> {
                        _systemStatusState.value = UiDataState.Success(result.data)
                        // 更新 UI State
                        val systemStatus = result.data.systemStatus
                        _uiState.value = _uiState.value.copy(
                            isLoading = false,
                            errorMessage = null,
                            indoorTemp = systemStatus?.avgIndoorTemp ?: "--",
                            indoorHumidity = systemStatus?.avgIndoorHumidity ?: "--",
                            co2 = systemStatus?.avgCo2?.toIntOrNull() ?: 0,
                            currentMode = when(systemStatus?.systemMode) {
                                "cooling" -> ClimateMode.COOLING
                                "heating" -> ClimateMode.HEATING
                                "ventilation" -> ClimateMode.VENTILATION
                                else -> ClimateMode.COOLING
                            },
                            // 更新室外环境数据
                            outdoorTemp = systemStatus?.outdoorTemp?.toIntOrNull() ?: 0,
                            outdoorHumidity = systemStatus?.outdoorHumidity?.toIntOrNull() ?: 0,
                            aqi = systemStatus?.outdoorAqi?.toIntOrNull() ?: 0,
                            pm25 = systemStatus?.outdoorPm25?.toIntOrNull() ?: 0
                        )
                    }
                    is ApiResult.Error -> {
                        _systemStatusState.value = UiDataState.Error(result.exception)
                        _uiState.value = _uiState.value.copy(
                            isLoading = false,
                            errorMessage = result.exception.message
                        )
                    }
                }
            }
        }
    }

    // ==================== 设备控制方法（新版API）====================

    /**
     * 控制设备（新版API）
     *
     * @param deviceId 设备ID
     * @param command 命令：on/off/temp_up/temp_down/set_temp
     * @param value 控制值（可选）
     */
    fun controlDevice(deviceId: Int, command: String, value: String? = null) {
        viewModelScope.launch {
            _deviceOperationState.value = UiDataState.Loading
            homeRepository.controlDevice(deviceId, command, value).collectLatest { result ->
                when (result) {
                    is ApiResult.Loading -> _deviceOperationState.value = UiDataState.Loading
                    is ApiResult.Success -> {
                        _deviceOperationState.value = UiDataState.Success(Unit)
                        // 操作成功后刷新设备列表
                        val houseId = tokenManager.getCurrentHouseId()
                        if (houseId.isNotEmpty()) {
                            loadDeviceList(houseId)
                        }
                    }
                    is ApiResult.Error -> _deviceOperationState.value = UiDataState.Error(result.exception)
                }
            }
        }
    }

    /**
     * 切换设备电源开关（新版API）
     *
     * @param deviceId 设备ID
     * @param powerOn 是否开启
     */
    fun toggleDevicePower(deviceId: Int, powerOn: Boolean) {
        controlDevice(deviceId, if (powerOn) "on" else "off")
    }

    /**
     * 设置设备目标温度（新版API）
     *
     * @param deviceId 设备ID
     * @param temperature 目标温度
     */
    fun setDeviceTemperature(deviceId: Int, temperature: Double) {
        controlDevice(deviceId, "set_temp", temperature.toString())
    }

    /**
     * 处理设备卡片点击事件
     * 触发导航到设备详情页
     *
     * @param deviceId 设备ID
     */
    fun onDeviceCardClicked(deviceId: String) {
        viewModelScope.launch {
            _deviceClickEvent.emit(deviceId)
        }
    }

    // ==================== 天气模式控制方法（新版API）====================

    /**
     * 切换系统天气模式（新版API）
     *
     * @param mode 目标模式（制冷/制热/通风/自动）
     */
    fun onWeatherModeSelected(mode: WeatherMode) {
        // 先更新UI状态（乐观更新）
        _weatherModeState.value = _weatherModeState.value.copy(
            selectedMode = mode
        )

        val houseId = tokenManager.getCurrentHouseId()
        if (houseId.isEmpty()) return

        viewModelScope.launch {
            _deviceOperationState.value = UiDataState.Loading
            val systemMode = when (mode) {
                WeatherMode.COOLING -> "cooling"
                WeatherMode.HEATING -> "heating"
                WeatherMode.VENTILATION -> "ventilation"
                WeatherMode.AUTO -> "auto"
            }
            homeRepository.setSystemMode(houseId.toInt(), systemMode).collectLatest { result ->
                when (result) {
                    is ApiResult.Loading -> _deviceOperationState.value = UiDataState.Loading
                    is ApiResult.Success -> {
                        _deviceOperationState.value = UiDataState.Success(Unit)
                        loadSystemStatus(houseId)
                    }
                    is ApiResult.Error -> {
                        _deviceOperationState.value = UiDataState.Error(result.exception)
                        // 如果失败，回滚UI状态
                        _weatherModeState.value = _weatherModeState.value.copy(
                            selectedMode = WeatherMode.COOLING // 默认回滚到制冷
                        )
                    }
                }
            }
        }
    }

    // ==================== 服务入口方法 ====================

    /**
     * 处理服务入口点击事件
     *
     * @param serviceType 服务类型
     */
    fun onServiceClicked(serviceType: ServiceType) {
        viewModelScope.launch {
            _serviceClickEvent.emit(serviceType)
        }
    }

    // ==================== 场景控制方法（新版API）====================

    /**
     * 应用场景（新版API）
     *
     * @param sceneId 场景ID
     * @param houseId 房屋ID
     */
    fun applyScene(sceneId: Int, houseId: String) {
        viewModelScope.launch {
            _deviceOperationState.value = UiDataState.Loading
            homeRepository.applyScene(sceneId, houseId.toInt()).collectLatest { result ->
                when (result) {
                    is ApiResult.Loading -> _deviceOperationState.value = UiDataState.Loading
                    is ApiResult.Success -> {
                        _deviceOperationState.value = UiDataState.Success(Unit)
                        // 刷新场景列表和系统状态
                        loadSceneList(houseId)
                        loadSystemStatus(houseId)
                    }
                    is ApiResult.Error -> _deviceOperationState.value = UiDataState.Error(result.exception)
                }
            }
        }
    }

    /**
     * 保存自定义场景（新版API）
     *
     * @param request 保存场景请求
     */
    fun saveScene(request: SaveSceneRequest) {
        viewModelScope.launch {
            _deviceOperationState.value = UiDataState.Loading
            homeRepository.saveScene(request).collectLatest { result ->
                when (result) {
                    is ApiResult.Loading -> _deviceOperationState.value = UiDataState.Loading
                    is ApiResult.Success -> {
                        _deviceOperationState.value = UiDataState.Success(Unit)
                        // 刷新场景列表
                        val houseId = tokenManager.getCurrentHouseId()
                        if (houseId.isNotEmpty()) {
                            loadSceneList(houseId)
                        }
                    }
                    is ApiResult.Error -> _deviceOperationState.value = UiDataState.Error(result.exception)
                }
            }
        }
    }

    // ==================== 系统模式控制（新版API）====================

    /**
     * 设置系统模式
     */
    fun setSystemMode(houseId: String, mode: SystemMode) {
        viewModelScope.launch {
            _deviceOperationState.value = UiDataState.Loading
            homeRepository.setSystemMode(houseId.toInt(), mode.value).collectLatest { result ->
                when (result) {
                    is ApiResult.Loading -> _deviceOperationState.value = UiDataState.Loading
                    is ApiResult.Success -> {
                        _deviceOperationState.value = UiDataState.Success(Unit)
                        loadSystemStatus(houseId)
                    }
                    is ApiResult.Error -> _deviceOperationState.value = UiDataState.Error(result.exception)
                }
            }
        }
    }

    /**
     * 设置全局温度
     */
    fun setGlobalTemp(houseId: String, temp: String) {
        viewModelScope.launch {
            _deviceOperationState.value = UiDataState.Loading
            homeRepository.setGlobalTemp(houseId.toInt(), temp).collectLatest { result ->
                when (result) {
                    is ApiResult.Loading -> _deviceOperationState.value = UiDataState.Loading
                    is ApiResult.Success -> {
                        _deviceOperationState.value = UiDataState.Success(Unit)
                        loadSystemStatus(houseId)
                    }
                    is ApiResult.Error -> _deviceOperationState.value = UiDataState.Error(result.exception)
                }
            }
        }
    }

    /**
     * 设置全局湿度
     */
    fun setGlobalHumidity(houseId: String, humidity: String) {
        viewModelScope.launch {
            _deviceOperationState.value = UiDataState.Loading
            homeRepository.setGlobalHumidity(houseId.toInt(), humidity).collectLatest { result ->
                when (result) {
                    is ApiResult.Loading -> _deviceOperationState.value = UiDataState.Loading
                    is ApiResult.Success -> {
                        _deviceOperationState.value = UiDataState.Success(Unit)
                        loadSystemStatus(houseId)
                    }
                    is ApiResult.Error -> _deviceOperationState.value = UiDataState.Error(result.exception)
                }
            }
        }
    }

    // ==================== 刷新方法 ====================

    /**
     * 刷新所有数据
     */
    fun refresh() {
        val houseId = tokenManager.getCurrentHouseId()
        if (houseId.isNotEmpty()) {
            loadHouseInfo(houseId)
            loadDeviceList(houseId)
            loadSceneList(houseId)
            loadSystemStatus(houseId)
        }
    }
    
    /**
     * 刷新数据（供Layout调用）
     */
    fun refreshData() {
        refresh()
    }
    
    // ==================== 新版UI交互方法 ====================
    
    /**
     * 选择气候模式
     */
    fun onModeSelected(mode: ClimateMode) {
        _uiState.value = _uiState.value.copy(currentMode = mode)
        // 转换为系统模式并调用API
        val houseId = tokenManager.getCurrentHouseId()
        if (houseId.isNotEmpty()) {
            val systemMode = when (mode) {
                ClimateMode.COOLING -> SystemMode.COOLING
                ClimateMode.VENTILATION -> SystemMode.VENTILATION
                ClimateMode.HEATING -> SystemMode.HEATING
            }
            setSystemMode(houseId, systemMode)
        }
    }
    
    /**
     * 选择场景
     */
    fun onSceneSelected(sceneType: SceneType) {
        // 更新UI状态
        val updatedScenes = _uiState.value.scenes.map {
            it.copy(isSelected = it.type == sceneType)
        }
        _uiState.value = _uiState.value.copy(scenes = updatedScenes)

        // 从场景列表状态中获取对应的sceneId
        val houseId = tokenManager.getCurrentHouseId()
        if (houseId.isNotEmpty()) {
            val sceneList = (_sceneListState.value as? UiDataState.Success)?.data
            val sceneInfo = sceneList?.find { scene ->
                when (sceneType) {
                    SceneType.MEETING -> scene.sceneName.contains("会客") || scene.sceneName.contains(" Meeting")
                    SceneType.AWAY -> scene.sceneName.contains("离家") || scene.sceneName.contains("Away")
                    SceneType.SLEEP -> scene.sceneName.contains("睡眠") || scene.sceneName.contains("Sleep")
                    SceneType.GUARD -> scene.sceneName.contains("值守") || scene.sceneName.contains("Guard")
                    else -> false
                }
            }
            val sceneId = sceneInfo?.sceneId ?: return
            applyScene(sceneId, houseId)
        }
    }

    // ==================== UI State 更新方法（不触发全屏刷新）====================

    /**
     * 更新位置信息
     */
    fun updateLocation(location: String) {
        _uiState.value = _uiState.value.copy(location = location)
    }

    /**
     * 更新天气信息
     */
    fun updateWeather(
        temperature: Int,
        weather: String,
        aqi: Int,
        pm25: Int,
        humidity: Int
    ) {
        _uiState.value = _uiState.value.copy(
            outdoorTemp = temperature,
            weather = weather,
            aqi = aqi,
            pm25 = pm25,
            outdoorHumidity = humidity
        )
    }

    /**
     * 更新模式（仅更新UI，不刷新整个页面）
     */
    fun updateMode(mode: ClimateMode) {
        _uiState.value = _uiState.value.copy(currentMode = mode)
        // 可选：调用API设置系统模式
        val houseId = tokenManager.getCurrentHouseId()
        if (houseId.isNotEmpty()) {
            viewModelScope.launch {
                val systemMode = when (mode) {
                    ClimateMode.COOLING -> SystemMode.COOLING
                    ClimateMode.VENTILATION -> SystemMode.VENTILATION
                    ClimateMode.HEATING -> SystemMode.HEATING
                }
                setSystemMode(houseId, systemMode)
            }
        }
    }

    // ==================== UI State 转换辅助方法 ====================

    /**
     * 将 DeviceInfo 列表转换为 DeviceCardUiState 列表（供UI层使用）
     *
     * @return 设备卡片UI状态列表
     */
    fun getDeviceCardUiStates(): List<DeviceCardUiState> {
        return when (val state = _deviceListState.value) {
            is UiDataState.Success -> state.data.map { device ->
                DeviceCardUiState(
                    deviceId = device.deviceId.toString(),
                    deviceName = device.deviceName,
                    deviceType = when(device.deviceType.lowercase()) {
                        "climate", "kongtiao", "空调" -> DeviceType.CLIMATE
                        "water", "shui", "水系统" -> DeviceType.WATER
                        "light", "dengguang", "灯光" -> DeviceType.LIGHT
                        "curtain", "chuanglian", "窗帘" -> DeviceType.CURTAIN
                        "security", "anfang", "安防" -> DeviceType.SECURITY
                        else -> DeviceType.OTHER
                    },
                    isPoweredOn = device.runStatus == "running",
                    currentTemp = 0f, // 温度需要从getDeviceData API获取
                    isCoolingMode = _weatherModeState.value.selectedMode == WeatherMode.COOLING,
                    roomName = device.roomName,
                    isOnline = device.onlineStatus == 1
                )
            }
            else -> emptyList()
        }
    }
}
