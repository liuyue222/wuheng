package com.wuheng.smart.presentation.climate

import androidx.lifecycle.viewModelScope
import com.wuheng.smart.data.model.*
import com.wuheng.smart.data.network.ApiResult
import com.wuheng.smart.data.network.TokenManager
import com.wuheng.smart.data.repository.ClimateRepository
import com.wuheng.smart.presentation.base.BaseViewModel
import com.wuheng.smart.presentation.base.UiDataState
import com.wuheng.smart.presentation.base.createUiStateFlow
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

/**
 * 冷暖系统 ViewModel（生产级实现）
 *
 * 职责：
 * 1. 管理冷暖系统所有UI状态（系统状态、温度、湿度等）
 * 2. 处理用户交互事件（温度调节、模式切换等）
 * 3. 协调Repository层数据获取与UI状态更新
 *
 * 使用新版API（系统模块）：
 * - getSystemStatus(houseId)
 * - setSystemMode(houseId, mode)
 * - setGlobalTemp(houseId, temp)
 * - setGlobalHumidity(houseId, humidity)
 */
@HiltViewModel
class ClimateViewModel @Inject constructor(
    private val climateRepository: ClimateRepository,
    private val tokenManager: TokenManager
) : BaseViewModel() {

    // ==================== 新版统一UI State ====================

    private val _uiState = MutableStateFlow(ClimateUiState())
    val uiState: StateFlow<ClimateUiState> = _uiState.asStateFlow()

    // ==================== UI State 定义 ====================

    // 新版API：系统状态
    private val _systemStatusState = createUiStateFlow<SystemStatus>()
    val systemStatusState: StateFlow<UiDataState<SystemStatus>> = _systemStatusState.asStateFlow()

    // 操作状态
    private val _operationState = MutableStateFlow<UiDataState<Unit>>(UiDataState.Idle)
    val operationState: StateFlow<UiDataState<Unit>> = _operationState.asStateFlow()

    // ==================== 防抖 Job ====================
    // 用于温度设置的防抖
    private var temperatureDebounceJob: Job? = null
    // 用于湿度设置的防抖
    private var humidityDebounceJob: Job? = null
    // 防抖延迟时间（毫秒）
    private val DEBOUNCE_DELAY_MS = 500L

    // ==================== 初始化 ====================

    init {
        loadInitialData()
    }

    private fun loadInitialData() {
        val houseId = tokenManager.getCurrentHouseId()
        Timber.d("ClimateViewModel loadInitialData: houseId=$houseId")
        if (houseId.isNotEmpty()) {
            // 同时加载系统状态和楼层数据
            loadSystemStatus(houseId)
            loadFloors(houseId)
        } else {
            Timber.w("ClimateViewModel: houseId is empty, cannot load data")
            _uiState.value = _uiState.value.copy(
                isLoading = false,
                errorMessage = "未选择房屋，请先绑定房屋"
            )
        }
    }

    // ==================== 数据加载方法（新版API）====================

    fun loadSystemStatus(houseId: String) {
        Timber.d("ClimateViewModel loadSystemStatus: houseId=$houseId")
        viewModelScope.launch {
            _systemStatusState.value = UiDataState.Loading
            _uiState.value = _uiState.value.copy(isLoading = true)
            climateRepository.getSystemStatus(houseId.toInt()).collectLatest { result ->
                when (result) {
                    is ApiResult.Loading -> {
                        _systemStatusState.value = UiDataState.Loading
                        _uiState.value = _uiState.value.copy(isLoading = true)
                    }
                    is ApiResult.Success -> {
                        Timber.d("ClimateViewModel loadSystemStatus success: systemMode=${result.data.systemStatus?.systemMode}, temp=${result.data.systemStatus?.globalTempSet}, humidity=${result.data.systemStatus?.globalHumiditySet}")
                        _systemStatusState.value = UiDataState.Success(result.data)
                        // 更新 UI State
                        _uiState.value = _uiState.value.copy(
                            isLoading = false,
                            errorMessage = null,
                            temperature = result.data.systemStatus?.globalTempSet?.toFloatOrNull() ?: 24.0f,
                            humidity = result.data.systemStatus?.globalHumiditySet?.toFloatOrNull() ?: 55.0f
                        )
                    }
                    is ApiResult.Error -> {
                        Timber.e("ClimateViewModel loadSystemStatus error: ${result.exception.message}")
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

    /**
     * 加载楼层列表
     */
    fun loadFloors(houseId: String) {
        Timber.d("ClimateViewModel loadFloors: houseId=$houseId")
        viewModelScope.launch {
            climateRepository.getFloorInfo(houseId.toInt()).collectLatest { result ->
                when (result) {
                    is ApiResult.Loading -> {
                        Timber.d("ClimateViewModel loadFloors: Loading")
                    }
                    is ApiResult.Success -> {
                        val floorList = result.data ?: emptyList()
                        Timber.d("ClimateViewModel loadFloors success: count=${floorList.size}")
                        // 将 FloorInfo 转换为 FloorItem
                        val floorItems = floorList.map { floorInfo ->
                            FloorItem(
                                id = floorInfo.floorId.toString(),
                                name = floorInfo.floorName,
                                isEnabled = true,
                                isMainControl = floorInfo.floorLevel == 1 // 一层作为主控
                            )
                        }
                        _uiState.value = _uiState.value.copy(
                            floors = floorItems
                        )
                        Timber.d("ClimateViewModel loadFloors: updated UI state with ${floorItems.size} floors")
                    }
                    is ApiResult.Error -> {
                        Timber.e("ClimateViewModel loadFloors error: ${result.exception.message}")
                    }
                }
            }
        }
    }

    // ==================== 系统控制方法（新版API）====================

    fun setSystemMode(houseId: String, mode: SystemMode) {
        viewModelScope.launch {
            _operationState.value = UiDataState.Loading
            _uiState.value = _uiState.value.copy(isLoading = true)
            climateRepository.setSystemMode(houseId.toInt(), mode).collectLatest { result ->
                when (result) {
                    is ApiResult.Loading -> {
                        _operationState.value = UiDataState.Loading
                        _uiState.value = _uiState.value.copy(isLoading = true)
                    }
                    is ApiResult.Success -> {
                        _operationState.value = UiDataState.Success(Unit)
                        _uiState.value = _uiState.value.copy(
                            isLoading = false,
                            errorMessage = null
                        )
                        loadSystemStatus(houseId)
                    }
                    is ApiResult.Error -> {
                        _operationState.value = UiDataState.Error(result.exception)
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
     * 设置全局温度
     * @param fromSlider 是否来自滑块操作，如果是则不触发loading状态避免闪烁
     */
    fun setGlobalTemp(houseId: String, temp: String, fromSlider: Boolean = true) {
        viewModelScope.launch {
            _operationState.value = UiDataState.Loading
            // 只有非滑块操作时才设置loading状态，避免界面闪烁
            if (!fromSlider) {
                _uiState.value = _uiState.value.copy(isLoading = true)
            }
            climateRepository.setGlobalTemp(houseId.toInt(), temp).collectLatest { result ->
                when (result) {
                    is ApiResult.Loading -> {
                        _operationState.value = UiDataState.Loading
                        if (!fromSlider) {
                            _uiState.value = _uiState.value.copy(isLoading = true)
                        }
                    }
                    is ApiResult.Success -> {
                        _operationState.value = UiDataState.Success(Unit)
                        _uiState.value = _uiState.value.copy(
                            isLoading = false,
                            errorMessage = null,
                            temperature = temp.toFloatOrNull() ?: _uiState.value.temperature
                        )
                        // 滑块操作时不立即刷新，避免界面闪烁
                        if (!fromSlider) {
                            loadSystemStatus(houseId)
                        }
                    }
                    is ApiResult.Error -> {
                        _operationState.value = UiDataState.Error(result.exception)
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
     * 设置全局湿度
     * @param fromSlider 是否来自滑块操作，如果是则不触发loading状态避免闪烁
     */
    fun setGlobalHumidity(houseId: String, humidity: String, fromSlider: Boolean = true) {
        viewModelScope.launch {
            _operationState.value = UiDataState.Loading
            // 只有非滑块操作时才设置loading状态，避免界面闪烁
            if (!fromSlider) {
                _uiState.value = _uiState.value.copy(isLoading = true)
            }
            climateRepository.setGlobalHumidity(houseId.toInt(), humidity).collectLatest { result ->
                when (result) {
                    is ApiResult.Loading -> {
                        _operationState.value = UiDataState.Loading
                        if (!fromSlider) {
                            _uiState.value = _uiState.value.copy(isLoading = true)
                        }
                    }
                    is ApiResult.Success -> {
                        _operationState.value = UiDataState.Success(Unit)
                        _uiState.value = _uiState.value.copy(
                            isLoading = false,
                            errorMessage = null,
                            humidity = humidity.toFloatOrNull() ?: _uiState.value.humidity
                        )
                        // 滑块操作时不立即刷新，避免界面闪烁
                        if (!fromSlider) {
                            loadSystemStatus(houseId)
                        }
                    }
                    is ApiResult.Error -> {
                        _operationState.value = UiDataState.Error(result.exception)
                        _uiState.value = _uiState.value.copy(
                            isLoading = false,
                            errorMessage = result.exception.message
                        )
                    }
                }
            }
        }
    }

    // ==================== 刷新方法 ====================

    fun refresh() {
        val houseId = tokenManager.getCurrentHouseId()
        Timber.d("ClimateViewModel refresh: houseId=$houseId")
        if (houseId.isNotEmpty()) {
            loadSystemStatus(houseId)
            loadFloors(houseId)
        } else {
            Timber.w("ClimateViewModel refresh: houseId is empty")
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
     * 选择Tab
     */
    fun onTabSelected(tab: ClimateTab) {
        _uiState.value = _uiState.value.copy(selectedTab = tab)
    }

    /**
     * 温度变化 - 带防抖处理
     *
     * 性能优化：
     * 1. 立即更新UI状态，确保界面响应流畅
     * 2. 使用防抖机制延迟API调用，避免频繁请求
     * 3. 用户停止操作500ms后才真正调用API
     */
    fun onTemperatureChange(temperature: Float) {
        Timber.d("ClimateViewModel onTemperatureChange: temperature=$temperature")
        // 立即更新UI状态，不触发loading，确保界面流畅
        _uiState.value = _uiState.value.copy(temperature = temperature)

        // 取消之前的防抖任务
        temperatureDebounceJob?.cancel()

        // 创建新的防抖任务
        val houseId = tokenManager.getCurrentHouseId()
        if (houseId.isNotEmpty()) {
            temperatureDebounceJob = viewModelScope.launch {
                delay(DEBOUNCE_DELAY_MS)
                // 防抖时间到，调用API
                Timber.d("ClimateViewModel: debounce complete, calling setGlobalTemp")
                setGlobalTemp(houseId, temperature.toString())
            }
        } else {
            Timber.w("ClimateViewModel onTemperatureChange: houseId is empty")
        }
    }

    /**
     * 湿度变化 - 带防抖处理
     *
     * 性能优化：
     * 1. 立即更新UI状态，确保界面响应流畅
     * 2. 使用防抖机制延迟API调用，避免频繁请求
     * 3. 用户停止操作500ms后才真正调用API
     */
    fun onHumidityChange(humidity: Float) {
        Timber.d("ClimateViewModel onHumidityChange: humidity=$humidity")
        // 立即更新UI状态，不触发loading，确保界面流畅
        _uiState.value = _uiState.value.copy(humidity = humidity)

        // 取消之前的防抖任务
        humidityDebounceJob?.cancel()

        // 创建新的防抖任务
        val houseId = tokenManager.getCurrentHouseId()
        if (houseId.isNotEmpty()) {
            humidityDebounceJob = viewModelScope.launch {
                delay(DEBOUNCE_DELAY_MS)
                // 防抖时间到，调用API
                Timber.d("ClimateViewModel: debounce complete, calling setGlobalHumidity")
                setGlobalHumidity(houseId, humidity.toString())
            }
        } else {
            Timber.w("ClimateViewModel onHumidityChange: houseId is empty")
        }
    }
    
    /**
     * 楼层开关切换
     */
    fun onFloorToggle(floorId: String, enabled: Boolean) {
        Timber.d("ClimateViewModel onFloorToggle: floorId=$floorId, enabled=$enabled")
        // 更新UI状态
        val updatedFloors = _uiState.value.floors.map {
            if (it.id == floorId) it.copy(isEnabled = enabled) else it
        }
        _uiState.value = _uiState.value.copy(floors = updatedFloors)
        Timber.d("ClimateViewModel onFloorToggle: updated ${updatedFloors.size} floors")

        // TODO: 调用API设置区域电源（需要新版API支持）
    }
}
