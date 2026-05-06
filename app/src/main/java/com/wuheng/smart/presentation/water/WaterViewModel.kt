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
import timber.log.Timber
import javax.inject.Inject

/**
 * 水系统 ViewModel（生产级实现）
 *
 * 职责：
 * 1. 管理水系统所有UI状态（热水循环、净水状态、滤芯列表等）
 * 2. 处理用户交互事件（模式切换、时长设置、滤芯更换等）
 * 3. 协调Repository层数据获取与UI状态更新
 *
 * 使用新版API（水系统模块4个接口）：
 * - getHotWaterStatus(houseId)      -> GET /home/water/getHeaterStatus
 * - setCirculationMode(houseId, mode, duration) -> POST /home/water/setCirculationMode
 * - getFilterStatus(houseId)        -> GET /home/water/getFilterStatus
 * - bookFilterReplace(...)           -> POST /home/water/bookFilterReplace
 *
 * UI组件映射：
 * - HotWaterCirculationCard: cycleModeState, currentTemp, temporaryDuration
 * - WaterPurifierCard: waterPurifierStatusState (TDS, 水质等)
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
     * 热水循环状态（新版API: getHotWaterStatus）
     */
    private val _hotWaterStatusState = createUiStateFlow<HotWaterStatusResponse>()
    val hotWaterStatusState: StateFlow<UiDataState<HotWaterStatusResponse>> = _hotWaterStatusState.asStateFlow()

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
     * 滤芯状态列表（新版API: getFilterStatus）
     */
    private val _filterStatusState = createUiStateFlow<List<FilterStatusInfo>>()
    val filterStatusState: StateFlow<UiDataState<List<FilterStatusInfo>>> = _filterStatusState.asStateFlow()

    /**
     * 通用操作状态（用于显示加载/成功/错误反馈）
     */
    private val _operationState = MutableStateFlow<UiDataState<Unit>>(UiDataState.Idle)
    val operationState: StateFlow<UiDataState<Unit>> = _operationState.asStateFlow()

    /**
     * 热力杀菌预约状态
     */
    private val _sterilizationState = MutableStateFlow<UiDataState<Unit>>(UiDataState.Idle)
    val sterilizationState: StateFlow<UiDataState<Unit>> = _sterilizationState.asStateFlow()

    /**
     * 滤芯预约更换状态
     */
    private val _filterReplaceState = MutableStateFlow<UiDataState<Unit>>(UiDataState.Idle)
    val filterReplaceState: StateFlow<UiDataState<Unit>> = _filterReplaceState.asStateFlow()

    // ==================== 初始化 ====================

    init {
        loadInitialData()
    }

    private fun loadInitialData() {
        val houseId = tokenManager.getCurrentHouseId()
        if (houseId.isNotEmpty()) {
            loadHotWaterStatus(houseId)
            loadFilterStatus(houseId)
        } else {
            Timber.w("No house ID available, skipping water data load")
        }
    }

    // ==================== 数据加载方法（新版API - 4个核心接口）====================

    /**
     * 1. 加载热水循环状态（新版API: getHotWaterStatus）
     */
    fun loadHotWaterStatus(houseId: String, isRefresh: Boolean = false) {
        viewModelScope.launch {
            _hotWaterStatusState.value = UiDataState.Loading
            if (!isRefresh) {
                _uiState.value = _uiState.value.copy(isLoading = true)
            }

            try {
                val houseIdInt = houseId.toIntOrNull()
                if (houseIdInt == null) {
                    _hotWaterStatusState.value = UiDataState.Error(
                        com.wuheng.smart.data.network.AppException.BusinessError(-1, "无效的房屋ID")
                    )
                    return@launch
                }

                waterRepository.getHotWaterStatus(houseIdInt).collectLatest { result ->
                    when (result) {
                        is ApiResult.Loading -> {
                            _hotWaterStatusState.value = UiDataState.Loading
                            if (!isRefresh) {
                                _uiState.value = _uiState.value.copy(isLoading = true)
                            }
                        }
                        is ApiResult.Success -> {
                            _hotWaterStatusState.value = UiDataState.Success(result.data)
                            _currentTempState.value = result.data.currentTemp.toFloatOrNull() ?: 55f

                            val scheduleText = formatSterilizationSchedule(
                                result.data.sterilizationDay,
                                result.data.sterilizationTime
                            )

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
                                currentTemp = result.data.currentTemp.toFloatOrNull()?.toInt() ?: 55,
                                sterilizationSchedule = scheduleText
                            )
                        }
                        is ApiResult.Error -> {
                            _hotWaterStatusState.value = UiDataState.Error(result.exception)
                            _uiState.value = _uiState.value.copy(
                                isLoading = false,
                                errorMessage = result.exception.message
                            )
                        }
                    }
                }
            } catch (e: Exception) {
                Timber.e(e, "Error loading hot water status")
                _hotWaterStatusState.value = UiDataState.Error(
                    com.wuheng.smart.data.network.AppException.UnknownError(e.message ?: "加载失败")
                )
            }
        }
    }

    private fun formatSterilizationSchedule(dayStr: String, timeStr: String): String {
        if (dayStr.isBlank()) return ""
        val dayNames = mapOf(
            "1" to "一", "2" to "二", "3" to "三", "4" to "四",
            "5" to "五", "6" to "六", "7" to "日"
        )
        val days = dayStr.split(",").mapNotNull { dayNames[it.trim()] }
        if (days.isEmpty()) return ""
        val dayPart = days.joinToString("、")
        val timePart = if (timeStr.length >= 5) timeStr.substring(0, 5) else timeStr
        return "每周$dayPart $timePart"
    }

    /**
     * 2. 加载滤芯状态（新版API: getFilterStatus）
     */
    fun loadFilterStatus(houseId: String, isRefresh: Boolean = false) {
        viewModelScope.launch {
            _filterStatusState.value = UiDataState.Loading
            if (!isRefresh) {
                _uiState.value = _uiState.value.copy(isLoading = true)
            }

            try {
                val houseIdInt = houseId.toIntOrNull()
                if (houseIdInt == null) {
                    _filterStatusState.value = UiDataState.Error(
                        com.wuheng.smart.data.network.AppException.BusinessError(-1, "无效的房屋ID")
                    )
                    return@launch
                }

                waterRepository.getFilterStatus(houseIdInt).collectLatest { result ->
                    when (result) {
                        is ApiResult.Loading -> {
                            _filterStatusState.value = UiDataState.Loading
                            if (!isRefresh) {
                                _uiState.value = _uiState.value.copy(isLoading = true)
                            }
                        }
                        is ApiResult.Success -> {
                            _filterStatusState.value = UiDataState.Success(result.data)
                            val filterItems = result.data.map { filterStatus ->
                                FilterItem(
                                    filterId = filterStatus.filterId.toString(),
                                    name = filterStatus.filterName,
                                    progress = filterStatus.lifePercent / 100f,
                                    status = when {
                                        filterStatus.lifePercent > 30 -> FilterUiStatus.NORMAL
                                        filterStatus.lifePercent > 10 -> FilterUiStatus.WARNING
                                        else -> FilterUiStatus.EXPIRED
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
            } catch (e: Exception) {
                Timber.e(e, "Error loading filter status")
                _filterStatusState.value = UiDataState.Error(
                    com.wuheng.smart.data.network.AppException.UnknownError(e.message ?: "加载失败")
                )
            }
        }
    }

    // ==================== 生活热水循环控制方法（新版API: setCirculationMode）====================

    /**
     * 4. 切换热水循环模式（新版API: setCirculationMode）
     *
     * @param houseId 房屋ID
     * @param mode 目标模式 (ALL_DAY/TIMER/TEMP/OFF)
     * @param duration 临时模式的运行时长(分钟)，仅TEMP模式需要
     */
    fun setCirculationMode(houseId: String, mode: CirculationMode, duration: Int? = null) {
        // 先更新UI状态（乐观更新）- 只更新模式状态，不触发isLoading避免页面闪烁
        _cycleModeState.value = when (mode) {
            CirculationMode.ALL_DAY -> CycleMode.ALWAYS
            CirculationMode.TIMER -> CycleMode.SCHEDULE
            CirculationMode.TEMP -> CycleMode.TEMPORARY
            CirculationMode.OFF -> CycleMode.OFF
        }
        if (duration != null) {
            _temporaryDurationState.value = duration
        }
        // 乐观更新统一UI State - 不设置isLoading，避免触发整个页面的重组和闪烁
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
            // 注意：不在_uiState中设置isLoading，避免触发整个页面的重组

            try {
                val houseIdInt = houseId.toIntOrNull()
                if (houseIdInt == null) {
                    _operationState.value = UiDataState.Error(
                        com.wuheng.smart.data.network.AppException.BusinessError(-1, "无效的房屋ID")
                    )
                    return@launch
                }

                waterRepository.setCirculationMode(houseIdInt, mode, duration).collectLatest { result ->
                    when (result) {
                        is ApiResult.Loading -> {
                            _operationState.value = UiDataState.Loading
                            // 不在_uiState中设置isLoading，避免页面闪烁
                        }
                        is ApiResult.Success -> {
                            _operationState.value = UiDataState.Success(Unit)
                            loadHotWaterStatus(houseId, isRefresh = true)
                        }
                        is ApiResult.Error -> {
                            _operationState.value = UiDataState.Error(result.exception)
                            _uiState.value = _uiState.value.copy(
                                errorMessage = result.exception.message
                            )
                            // 失败时回滚UI状态
                            _cycleModeState.value = CycleMode.OFF
                        }
                    }
                }
            } catch (e: Exception) {
                Timber.e(e, "Error setting circulation mode")
                _operationState.value = UiDataState.Error(
                    com.wuheng.smart.data.network.AppException.UnknownError(e.message ?: "设置失败")
                )
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

    // ==================== 滤芯管理方法 ====================

    /**
     * 预约滤芯更换服务
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

            try {
                val houseIdInt = houseId.toIntOrNull()
                val filterIdInt = filterId.toIntOrNull()

                if (houseIdInt == null || filterIdInt == null) {
                    _operationState.value = UiDataState.Error(
                        com.wuheng.smart.data.network.AppException.BusinessError(-1, "无效的ID参数")
                    )
                    return@launch
                }

                waterRepository.bookFilterReplace(houseIdInt, filterIdInt, contactName, contactPhone, appointmentDate)
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
                                loadFilterStatus(houseId, isRefresh = true)
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
            } catch (e: Exception) {
                Timber.e(e, "Error booking filter replacement")
                _operationState.value = UiDataState.Error(
                    com.wuheng.smart.data.network.AppException.UnknownError(e.message ?: "预约失败")
                )
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
            loadHotWaterStatus(houseId)
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

    /**
     * 更新热力杀菌预约时间
     */
    fun updateSterilizationSchedule(dayOfWeek: Int, hour: Int, minute: Int) {
        val houseIdStr = tokenManager.getCurrentHouseId()
        if (houseIdStr.isEmpty()) {
            _sterilizationState.value = UiDataState.Error(
                com.wuheng.smart.data.network.AppException.BusinessError(-1, "未选择房屋")
            )
            return
        }
        val houseIdInt = houseIdStr.toIntOrNull() ?: return

        viewModelScope.launch {
            _sterilizationState.value = UiDataState.Loading

            val dayOfWeekStr = dayOfWeek.toString()
            val timeStr = String.format("%02d:%02d:00", hour, minute)

            waterRepository.setSterilization(
                houseId = houseIdInt,
                enable = 1,
                dayOfWeek = dayOfWeekStr,
                time = timeStr,
                temp = null
            ).collectLatest { result ->
                when (result) {
                    is ApiResult.Loading -> {
                        _sterilizationState.value = UiDataState.Loading
                    }
                    is ApiResult.Success -> {
                        val daysOfWeek = listOf("周一", "周二", "周三", "周四", "周五", "周六", "周日")
                        val scheduleText = "每周${daysOfWeek[dayOfWeek - 1]} ${String.format("%02d:%02d", hour, minute)}"
                        _uiState.value = _uiState.value.copy(sterilizationSchedule = scheduleText)

                        val currentStatus = _hotWaterStatusState.value
                        if (currentStatus is UiDataState.Success) {
                            val updated = currentStatus.data.copy(
                                sterilizationDay = dayOfWeekStr,
                                sterilizationTime = timeStr,
                                sterilizationEnable = 1
                            )
                            _hotWaterStatusState.value = UiDataState.Success(updated)
                        }

                        _sterilizationState.value = UiDataState.Success(Unit)
                        Timber.d("Sterilization schedule set: dayOfWeek=$dayOfWeekStr, time=$timeStr")
                    }
                    is ApiResult.Error -> {
                        _sterilizationState.value = UiDataState.Error(result.exception)
                        Timber.e(result.exception, "Failed to set sterilization schedule")
                    }
                }
            }
        }
    }

    /**
     * 重置热力杀菌状态
     */
    fun resetSterilizationState() {
        _sterilizationState.value = UiDataState.Idle
    }

    /**
     * 重置滤芯预约状态
     */
    fun resetFilterReplaceState() {
        _filterReplaceState.value = UiDataState.Idle
    }

    /**
     * 重置操作状态
     */
    fun resetOperationState() {
        _operationState.value = UiDataState.Idle
    }

    /**
     * 预约滤芯更换（带状态管理）
     *
     * @param filterId 滤芯ID
     * @param contactName 联系人姓名
     * @param contactPhone 联系人电话
     * @param appointmentDate 预约日期 (格式: yyyy-MM-dd)
     */
    fun bookFilterReplaceWithState(
        filterId: String,
        contactName: String,
        contactPhone: String,
        appointmentDate: String
    ) {
        val houseId = tokenManager.getCurrentHouseId()
        if (houseId.isEmpty()) {
            _filterReplaceState.value = UiDataState.Error(
                com.wuheng.smart.data.network.AppException.BusinessError(-1, "未选择房屋")
            )
            return
        }

        viewModelScope.launch {
            _filterReplaceState.value = UiDataState.Loading

            try {
                val houseIdInt = houseId.toIntOrNull()
                val filterIdInt = filterId.toIntOrNull()

                if (houseIdInt == null || filterIdInt == null) {
                    _filterReplaceState.value = UiDataState.Error(
                        com.wuheng.smart.data.network.AppException.BusinessError(-1, "无效的ID参数")
                    )
                    return@launch
                }

                waterRepository.bookFilterReplace(
                    houseIdInt,
                    filterIdInt,
                    contactName.takeIf { it.isNotBlank() },
                    contactPhone.takeIf { it.isNotBlank() },
                    appointmentDate.takeIf { it.isNotBlank() }
                ).collectLatest { result ->
                    when (result) {
                        is ApiResult.Loading -> {
                            _filterReplaceState.value = UiDataState.Loading
                        }
                        is ApiResult.Success -> {
                            _filterReplaceState.value = UiDataState.Success(Unit)
                            loadFilterStatus(houseId, isRefresh = true)
                        }
                        is ApiResult.Error -> {
                            _filterReplaceState.value = UiDataState.Error(result.exception)
                        }
                    }
                }
            } catch (e: Exception) {
                Timber.e(e, "Error booking filter replacement")
                _filterReplaceState.value = UiDataState.Error(
                    com.wuheng.smart.data.network.AppException.UnknownError(e.message ?: "预约失败")
                )
            }
        }
    }
}
