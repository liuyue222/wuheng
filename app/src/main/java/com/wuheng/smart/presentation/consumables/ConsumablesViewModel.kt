package com.wuheng.smart.presentation.consumables

import androidx.lifecycle.viewModelScope
import com.wuheng.smart.data.model.FilterStatusInfo
import com.wuheng.smart.data.network.ApiResult
import com.wuheng.smart.data.network.AppException
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
 * 耗材进度页面 ViewModel（生产级实现）
 *
 * 职责：
 * 1. 管理耗材（滤芯）列表数据状态
 * 2. 提供刷新功能
 * 3. 处理滤芯更换预约
 *
 * 使用新版API（水系统模块）：
 * - getFilterStatus(houseId)
 * - bookFilterReplace(houseId, filterId, ...)
 *
 * @param waterRepository 水系统数据仓库
 * @param tokenManager Token管理器，用于获取当前房屋ID
 */
@HiltViewModel
class ConsumablesViewModel @Inject constructor(
    private val waterRepository: WaterRepository,
    private val tokenManager: TokenManager
) : BaseViewModel() {

    /**
     * 耗材（滤芯）列表状态
     */
    private val _consumablesState = createUiStateFlow<List<ConsumableItem>>()
    val consumablesState: StateFlow<UiDataState<List<ConsumableItem>>> = _consumablesState.asStateFlow()

    /**
     * 预约操作状态
     */
    private val _bookingState = MutableStateFlow<UiDataState<Unit>>(UiDataState.Idle)
    val bookingState: StateFlow<UiDataState<Unit>> = _bookingState.asStateFlow()

    init {
        loadConsumables()
    }

    /**
     * 加载耗材数据（新版API）
     */
    fun loadConsumables() {
        val houseId = tokenManager.getCurrentHouseId()
        if (houseId.isEmpty()) {
            Timber.w("No house selected, cannot load consumables")
            _consumablesState.value = UiDataState.Error(AppException.BusinessError(-1, "未选择房屋"))
            return
        }

        viewModelScope.launch {
            _consumablesState.value = UiDataState.Loading
            Timber.d("Loading consumables for house: $houseId")

            // 使用新版API
            waterRepository.getFilterStatus(houseId.toInt()).collectLatest { result ->
                when (result) {
                    is ApiResult.Loading -> {
                        _consumablesState.value = UiDataState.Loading
                    }
                    is ApiResult.Success -> {
                        val items = result.data.map { it.toUiModel() }
                        _consumablesState.value = UiDataState.Success(items)
                        Timber.d("Loaded ${items.size} consumables")
                    }
                    is ApiResult.Error -> {
                        Timber.e(result.exception, "Failed to load consumables")
                        _consumablesState.value = UiDataState.Error(result.exception)
                    }
                }
            }
        }
    }

    /**
     * 预约滤芯更换（新版API）
     *
     * @param filterId 滤芯ID
     */
    fun bookFilterReplacement(filterId: String) {
        val houseId = tokenManager.getCurrentHouseId()
        if (houseId.isEmpty()) {
            Timber.w("Cannot book replacement: no house selected")
            return
        }

        viewModelScope.launch {
            _bookingState.value = UiDataState.Loading
            Timber.d("Booking filter replacement: houseId=$houseId, filterId=$filterId")

            waterRepository.bookFilterReplace(houseId.toInt(), filterId.toInt()).collectLatest { result ->
                when (result) {
                    is ApiResult.Loading -> {
                        _bookingState.value = UiDataState.Loading
                    }
                    is ApiResult.Success -> {
                        _bookingState.value = UiDataState.Success(Unit)
                        Timber.d("Filter replacement booked successfully")
                        // 刷新列表
                        loadConsumables()
                    }
                    is ApiResult.Error -> {
                        _bookingState.value = UiDataState.Error(result.exception)
                        Timber.e(result.exception, "Failed to book filter replacement")
                    }
                }
            }
        }
    }

    /**
     * 刷新数据
     */
    fun refresh() {
        loadConsumables()
    }

    /**
     * 重置预约状态
     */
    fun resetBookingState() {
        _bookingState.value = UiDataState.Idle
    }

    // ==================== 数据转换扩展函数 ====================

    private fun FilterStatusInfo.toUiModel(): ConsumableItem {
        return ConsumableItem(
            id = filterId,
            name = filterName,
            percentage = lifePercent,
            status = when (status) {
                0 -> ConsumableStatus.NORMAL
                1 -> ConsumableStatus.WARNING
                2 -> ConsumableStatus.CRITICAL
                else -> ConsumableStatus.NORMAL
            }
        )
    }
}
