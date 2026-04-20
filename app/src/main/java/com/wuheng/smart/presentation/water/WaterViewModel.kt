package com.wuheng.smart.presentation.water

import androidx.lifecycle.viewModelScope
import com.wuheng.smart.data.model.*
import com.wuheng.smart.data.network.ApiResult
import com.wuheng.smart.data.network.TokenManager
import com.wuheng.smart.data.repository.WaterRepository
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
 * 水系统 ViewModel（生产级实现）
 *
 * 职责：
 * 1. 管理水系统所有UI状态（热水循环、滤芯列表等）
 * 2. 处理用户交互事件（模式切换、时长设置、滤芯更换等）
 * 3. 协调Repository层数据获取与UI状态更新
 *
 * 使用新版API（水系统模块）：
 * - getHeaterStatus(houseId)
 * - setCirculationMode(houseId, mode, duration)
 * - getFilterStatus(houseId)
 * - bookFilterReplace(houseId, filterId, ...)
 *
 * UI组件映射：
 * - HotWaterCirculationCard: cycleModeState, currentTemp, temporaryDuration
 * - FilterSystemCard: filterStatusState
 */
@HiltViewModel
class WaterViewModel @Inject constructor(
    private val waterRepository: WaterRepository,
    private val tokenManager: TokenManager
) : BaseViewModel() {

    // ==================== 新版统一UI State ====================
    
    private val _uiState = MutableStateFlow(WaterUiState())
    val uiState: StateFlow<WaterUiState> = _uiState.asStateFlow()
    
    // ==================== UI State 定义 ====================

    /**
     * 热水循环状态（新版API）
     */
    private val _heaterStatusState = createUiStateFlow<HeaterStatus>()
    val heaterStatusState: StateFlow<UiDataState<HeaterStatus>> = _heaterStatusState.asStateFlow()

    /**
     * 当前选中的循环模式
     */
    private val _cycleModeState = MutableStateFlow<CycleMode>(CycleMode.OFF)
    val cycleModeState: StateFlow<CycleMode> = _cycleModeState.asStateFlow()

    /**
     * 临时循环运行时长（分钟）
     */
    private val _temporaryDurationState = MutableStateFlow<Int>(30)
    val temporaryDurationState: StateFlow<Int> = _temporaryDurationState.asStateFlow()

    /**
     * 当前水温显示
     */
    private val _currentTempState = MutableStateFlow<Float>(55f)
    val currentTempState: StateFlow<Float> = _currentTempState.asStateFlow()

    /**
     * 滤芯状态列表（新版API）
     */
    private val _filterStatusState = createUiStateFlow<List<FilterStatusInfo>>()
    val filterStatusState: StateFlow<UiDataState<List<FilterStatusInfo>>> = _filterStatusState.asStateFlow()

    /**
     * 通用操作状态（用于显示加载/成功/错误反馈）
     */
    private val _operationState = MutableStateFlow<UiDataState<Unit>>(UiDataState.Idle)
    val operationState: StateFlow<UiDataState<Unit>> = _operationState.asStateFlow()

    // ==================== 初始化 ====================

    init {
        loadInitialData()
    }

    private fun loadInitialData() {
        // 使用新版API加载数据
        val houseId = tokenManager.getCurrentHouseId()
        if (houseId.isNotEmpty()) {
            loadHeaterStatus(houseId)
            loadFilterStatus(houseId)
        }
    }

    // ==================== 数据加载方法（新版API）====================

    /**
     * 加载热水循环状态（新版API）
     */
    fun loadHeaterStatus(houseId: String) {
        viewModelScope.launch {
            _heaterStatusState.value = UiDataState.Loading
            _uiState.value = _uiState.value.copy(isLoading = true)
            waterRepository.getHeaterStatus(houseId.toInt()).collectLatest { result ->
                when (result) {
                    is ApiResult.Loading -> {
                        _heaterStatusState.value = UiDataState.Loading
                        _uiState.value = _uiState.value.copy(isLoading = true)
                    }
                    is ApiResult.Success -> {
                        _heaterStatusState.value = UiDataState.Success(result.data)
                        // 同步更新UI状态
                        _currentTempState.value = result.data.currentTemp.toFloatOrNull() ?: 55f
                        // 更新统一UI State
                        _uiState.value = _uiState.value.copy(
                            isLoading = false,
                            errorMessage = null,
                            hotWaterMode = when (result.data.circulationMode) {
                                "all_day" -> HotWaterMode.ALL_DAY
                                "timer" -> HotWaterMode.TIMED
                                "temp" -> HotWaterMode.TEMPORARY
                                "off" -> HotWaterMode.OFF
                                else -> HotWaterMode.OFF
                            },
                            currentTemp = result.data.currentTemp.toFloatOrNull()?.toInt() ?: 55
                        )
                    }
                    is ApiResult.Error -> {
                        _heaterStatusState.value = UiDataState.Error(result.exception)
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
     * 加载滤芯状态（新版API）
     */
    fun loadFilterStatus(houseId: String) {
        viewModelScope.launch {
            _filterStatusState.value = UiDataState.Loading
            _uiState.value = _uiState.value.copy(isLoading = true)
            waterRepository.getFilterStatus(houseId.toInt()).collectLatest { result ->
                when (result) {
                    is ApiResult.Loading -> {
                        _filterStatusState.value = UiDataState.Loading
                        _uiState.value = _uiState.value.copy(isLoading = true)
                    }
                    is ApiResult.Success -> {
                        _filterStatusState.value = UiDataState.Success(result.data)
                        // 更新统一UI State - 将FilterStatusInfo映射为FilterItem
                        val filterItems = result.data.map { filterStatus ->
                            FilterItem(
                                name = filterStatus.filterName,
                                progress = filterStatus.lifePercent / 100f,
                                status = when {
                                    filterStatus.lifePercent > 30 -> FilterStatus.NORMAL
                                    filterStatus.lifePercent > 10 -> FilterStatus.WARNING
                                    else -> FilterStatus.EXPIRED
                                }
                            )
                        }
                        _uiState.value = _uiState.value.copy(
                            isLoading = false,
                            errorMessage = null,
                            filters = filterItems
                        )
                    }
                    is ApiResult.Error -> {
                        _filterStatusState.value = UiDataState.Error(result.exception)
                        _uiState.value = _uiState.value.copy(
                            isLoading = false,
                            errorMessage = result.exception.message
                        )
                    }
                }
            }
        }
    }

    // ==================== 生活热水循环控制方法（新版API）====================

    /**
     * 切换热水循环模式（新版API）
     *
     * @param houseId 房屋ID
     * @param mode 目标模式 (ALL_DAY/TIMER/TEMP/OFF)
     * @param duration 临时模式的运行时长(分钟)，仅TEMP模式需要
     */
    fun setCirculationMode(houseId: String, mode: CirculationMode, duration: Int? = null) {
        // 先更新UI状态（乐观更新）
        _cycleModeState.value = when (mode) {
            CirculationMode.ALL_DAY -> CycleMode.ALWAYS
            CirculationMode.TIMER -> CycleMode.SCHEDULE
            CirculationMode.TEMP -> CycleMode.TEMPORARY
            CirculationMode.OFF -> CycleMode.OFF
        }
        if (duration != null) {
            _temporaryDurationState.value = duration
        }
        // 乐观更新统一UI State
        _uiState.value = _uiState.value.copy(
            hotWaterMode = when (mode) {
                CirculationMode.ALL_DAY -> HotWaterMode.ALL_DAY
                CirculationMode.TIMER -> HotWaterMode.TIMED
                CirculationMode.TEMP -> HotWaterMode.TEMPORARY
                CirculationMode.OFF -> HotWaterMode.OFF
            },
            temporaryDuration = duration ?: _uiState.value.temporaryDuration
        )

        viewModelScope.launch {
            _operationState.value = UiDataState.Loading
            _uiState.value = _uiState.value.copy(isLoading = true)
            waterRepository.setCirculationMode(houseId.toInt(), mode, duration).collectLatest { result ->
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
                        loadHeaterStatus(houseId)
                    }
                    is ApiResult.Error -> {
                        _operationState.value = UiDataState.Error(result.exception)
                        _uiState.value = _uiState.value.copy(
                            isLoading = false,
                            errorMessage = result.exception.message
                        )
                        // 失败时回滚UI状态
                        _cycleModeState.value = CycleMode.OFF
                    }
                }
            }
        }
    }

    /**
     * 设置临时循环运行时长
     *
     * @param duration 时长(分钟): 30/60/90/120
     */
    fun setTemporaryDuration(duration: Int) {
        _temporaryDurationState.value = duration
        // 如果当前是临时模式，同步更新到后端
        if (_cycleModeState.value == CycleMode.TEMPORARY) {
            val houseId = tokenManager.getCurrentHouseId()
            if (houseId.isNotEmpty()) {
                setCirculationMode(houseId, CirculationMode.TEMP, duration)
            }
        }
    }

    // ==================== 滤芯管理方法（新版API）====================

    /**
     * 预约滤芯更换服务（新版API）
     *
     * @param houseId 房屋ID
     * @param filterId 滤芯ID
     * @param contactName 联系人姓名
     * @param contactPhone 联系人电话
     * @param appointmentDate 预约日期
     */
    fun bookFilterReplace(
        houseId: String,
        filterId: String,
        contactName: String? = null,
        contactPhone: String? = null,
        appointmentDate: String? = null
    ) {
        viewModelScope.launch {
            _operationState.value = UiDataState.Loading
            _uiState.value = _uiState.value.copy(isLoading = true)
            waterRepository.bookFilterReplace(houseId.toInt(), filterId.toInt(), contactName, contactPhone, appointmentDate)
                .collectLatest { result ->
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
                            // 刷新滤芯状态
                            loadFilterStatus(houseId)
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

    /**
     * 刷新所有数据
     */
    fun refresh() {
        val houseId = tokenManager.getCurrentHouseId()
        if (houseId.isNotEmpty()) {
            loadHeaterStatus(houseId)
            loadFilterStatus(houseId)
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
     * 选择热水模式
     */
    fun onHotWaterModeSelected(mode: HotWaterMode) {
        _uiState.value = _uiState.value.copy(hotWaterMode = mode)
        // 调用API设置循环模式
        val houseId = tokenManager.getCurrentHouseId()
        if (houseId.isNotEmpty()) {
            val circulationMode = when (mode) {
                HotWaterMode.ALL_DAY -> CirculationMode.ALL_DAY
                HotWaterMode.TIMED -> CirculationMode.TIMER
                HotWaterMode.TEMPORARY -> CirculationMode.TEMP
                HotWaterMode.OFF -> CirculationMode.OFF
            }
            setCirculationMode(houseId, circulationMode, _temporaryDurationState.value)
        }
    }
}
