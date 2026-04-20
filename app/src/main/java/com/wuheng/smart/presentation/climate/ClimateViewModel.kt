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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
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

    // ==================== 初始化 ====================

    init {
        loadInitialData()
    }

    private fun loadInitialData() {
        // 使用新版API加载系统状态
        val houseId = tokenManager.getCurrentHouseId()
        if (houseId.isNotEmpty()) {
            loadSystemStatus(houseId)
        }
    }

    // ==================== 数据加载方法（新版API）====================

    fun loadSystemStatus(houseId: String) {
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

    fun setGlobalTemp(houseId: String, temp: String) {
        viewModelScope.launch {
            _operationState.value = UiDataState.Loading
            _uiState.value = _uiState.value.copy(isLoading = true)
            climateRepository.setGlobalTemp(houseId.toInt(), temp).collectLatest { result ->
                when (result) {
                    is ApiResult.Loading -> {
                        _operationState.value = UiDataState.Loading
                        _uiState.value = _uiState.value.copy(isLoading = true)
                    }
                    is ApiResult.Success -> {
                        _operationState.value = UiDataState.Success(Unit)
                        _uiState.value = _uiState.value.copy(
                            isLoading = false,
                            errorMessage = null,
                            temperature = temp.toFloatOrNull() ?: _uiState.value.temperature
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

    fun setGlobalHumidity(houseId: String, humidity: String) {
        viewModelScope.launch {
            _operationState.value = UiDataState.Loading
            _uiState.value = _uiState.value.copy(isLoading = true)
            climateRepository.setGlobalHumidity(houseId.toInt(), humidity).collectLatest { result ->
                when (result) {
                    is ApiResult.Loading -> {
                        _operationState.value = UiDataState.Loading
                        _uiState.value = _uiState.value.copy(isLoading = true)
                    }
                    is ApiResult.Success -> {
                        _operationState.value = UiDataState.Success(Unit)
                        _uiState.value = _uiState.value.copy(
                            isLoading = false,
                            errorMessage = null,
                            humidity = humidity.toFloatOrNull() ?: _uiState.value.humidity
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

    // ==================== 刷新方法 ====================

    fun refresh() {
        val houseId = tokenManager.getCurrentHouseId()
        if (houseId.isNotEmpty()) {
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
     * 选择Tab
     */
    fun onTabSelected(tab: ClimateTab) {
        _uiState.value = _uiState.value.copy(selectedTab = tab)
    }
    
    /**
     * 温度变化
     */
    fun onTemperatureChange(temperature: Float) {
        _uiState.value = _uiState.value.copy(temperature = temperature)
        // 调用API设置全局温度
        val houseId = tokenManager.getCurrentHouseId()
        if (houseId.isNotEmpty()) {
            setGlobalTemp(houseId, temperature.toString())
        }
    }
    
    /**
     * 湿度变化
     */
    fun onHumidityChange(humidity: Float) {
        _uiState.value = _uiState.value.copy(humidity = humidity)
        // 调用API设置全局湿度
        val houseId = tokenManager.getCurrentHouseId()
        if (houseId.isNotEmpty()) {
            setGlobalHumidity(houseId, humidity.toString())
        }
    }
    
    /**
     * 楼层开关切换
     */
    fun onFloorToggle(floorId: String, enabled: Boolean) {
        // 更新UI状态
        val updatedFloors = _uiState.value.floors.map { 
            if (it.id == floorId) it.copy(isEnabled = enabled) else it 
        }
        _uiState.value = _uiState.value.copy(floors = updatedFloors)
        
        // TODO: 调用API设置区域电源（需要新版API支持）
    }
}
