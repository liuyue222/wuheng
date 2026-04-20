package com.wuheng.smart.presentation.floorzone

import androidx.lifecycle.viewModelScope
import com.wuheng.smart.data.model.FloorInfo
import com.wuheng.smart.data.model.RoomInfo
import com.wuheng.smart.data.network.ApiResult
import com.wuheng.smart.data.network.TokenManager
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
 * 楼层区域页面 ViewModel（生产级实现）
 *
 * 职责：
 * 1. 管理楼层列表数据状态
 * 2. 管理房间列表数据状态
 * 3. 处理楼层和房间选择
 * 4. 提供刷新功能
 *
 * @param homeRepository 首页数据仓库
 * @param tokenManager Token管理器，用于获取当前房屋ID
 */
@HiltViewModel
class FloorZoneViewModel @Inject constructor(
    private val homeRepository: HomeRepository,
    private val tokenManager: TokenManager
) : BaseViewModel() {

    /**
     * 楼层列表状态
     */
    private val _floorsState = createUiStateFlow<List<FloorInfo>>()
    val floorsState: StateFlow<UiDataState<List<FloorInfo>>> = _floorsState.asStateFlow()

    /**
     * 房间列表状态
     */
    private val _roomsState = createUiStateFlow<List<RoomInfo>>()
    val roomsState: StateFlow<UiDataState<List<RoomInfo>>> = _roomsState.asStateFlow()

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

    init {
        loadFloors()
    }

    /**
     * 加载楼层列表
     */
    fun loadFloors() {
        val houseId = tokenManager.getCurrentHouseId()
        if (houseId.isEmpty()) {
            Timber.w("No house selected, cannot load floors")
            _floorsState.value = UiDataState.Error(
                com.wuheng.smart.data.network.AppException.BusinessError(-1, "请先选择房屋")
            )
            return
        }

        viewModelScope.launch {
            _floorsState.value = UiDataState.Loading
            Timber.d("Loading floors for house: $houseId")

            homeRepository.getFloorList(houseId.toInt()).collectLatest { result ->
                when (result) {
                    is ApiResult.Loading -> {
                        _floorsState.value = UiDataState.Loading
                    }
                    is ApiResult.Success -> {
                        _floorsState.value = UiDataState.Success(result.data)
                        Timber.d("Loaded ${result.data.size} floors")

                        // 自动选中第一个楼层
                        if (result.data.isNotEmpty() && _selectedFloorId.value == null) {
                            selectFloor(result.data.first().floorId.toString())
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
     * 选择楼层
     *
     * @param floorId 楼层ID
     */
    fun selectFloor(floorId: String) {
        _selectedFloorId.value = floorId
        _selectedRoomId.value = null
        loadRooms(floorId)
        Timber.d("Selected floor: $floorId")
    }

    /**
     * 加载房间列表
     *
     * @param floorId 楼层ID
     */
    private fun loadRooms(floorId: String) {
        val houseId = tokenManager.getCurrentHouseId()
        if (houseId.isEmpty()) {
            Timber.w("No house selected, cannot load rooms")
            return
        }

        viewModelScope.launch {
            _roomsState.value = UiDataState.Loading
            Timber.d("Loading rooms for house: $houseId, floor: $floorId")

            homeRepository.getRoomList(houseId.toInt(), floorId.toInt()).collectLatest { result ->
                when (result) {
                    is ApiResult.Loading -> {
                        _roomsState.value = UiDataState.Loading
                    }
                    is ApiResult.Success -> {
                        _roomsState.value = UiDataState.Success(result.data)
                        Timber.d("Loaded ${result.data.size} rooms")

                        // 自动选中第一个房间
                        if (result.data.isNotEmpty()) {
                            _selectedRoomId.value = result.data.first().roomId.toString()
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
     * 选择房间
     *
     * @param roomId 房间ID
     */
    fun selectRoom(roomId: String) {
        _selectedRoomId.value = roomId
        Timber.d("Selected room: $roomId")
    }

    /**
     * 刷新数据
     */
    fun refresh() {
        loadFloors()
    }
}
