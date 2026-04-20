package com.wuheng.smart.presentation.floorzone;

import com.wuheng.smart.data.model.FloorInfo;
import com.wuheng.smart.data.model.RoomInfo;
import com.wuheng.smart.data.network.ApiResult;
import com.wuheng.smart.data.network.TokenManager;
import com.wuheng.smart.data.repository.HomeRepository;
import com.wuheng.smart.presentation.base.BaseViewModel;
import com.wuheng.smart.presentation.base.UiDataState;
import dagger.hilt.android.lifecycle.HiltViewModel;
import kotlinx.coroutines.flow.StateFlow;
import timber.log.Timber;
import javax.inject.Inject;

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
@dagger.hilt.android.lifecycle.HiltViewModel()
@kotlin.Metadata(mv = {1, 7, 1}, k = 1, d1 = {"\u0000H\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u0002\n\u0002\b\u0007\b\u0007\u0018\u00002\u00020\u0001B\u0017\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\u0006\u0010\u001b\u001a\u00020\u001cJ\u0010\u0010\u001d\u001a\u00020\u001c2\u0006\u0010\u001e\u001a\u00020\u000fH\u0002J\u0006\u0010\u001f\u001a\u00020\u001cJ\u000e\u0010 \u001a\u00020\u001c2\u0006\u0010\u001e\u001a\u00020\u000fJ\u000e\u0010!\u001a\u00020\u001c2\u0006\u0010\"\u001a\u00020\u000fR \u0010\u0007\u001a\u0014\u0012\u0010\u0012\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000b0\n0\t0\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R \u0010\f\u001a\u0014\u0012\u0010\u0012\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\r0\n0\t0\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0016\u0010\u000e\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u000f0\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0016\u0010\u0010\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u000f0\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R#\u0010\u0011\u001a\u0014\u0012\u0010\u0012\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000b0\n0\t0\u0012\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R#\u0010\u0015\u001a\u0014\u0012\u0010\u0012\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\r0\n0\t0\u0012\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0014R\u0019\u0010\u0017\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u000f0\u0012\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0014R\u0019\u0010\u0019\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u000f0\u0012\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u0014R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006#"}, d2 = {"Lcom/wuheng/smart/presentation/floorzone/FloorZoneViewModel;", "Lcom/wuheng/smart/presentation/base/BaseViewModel;", "homeRepository", "Lcom/wuheng/smart/data/repository/HomeRepository;", "tokenManager", "Lcom/wuheng/smart/data/network/TokenManager;", "(Lcom/wuheng/smart/data/repository/HomeRepository;Lcom/wuheng/smart/data/network/TokenManager;)V", "_floorsState", "Lkotlinx/coroutines/flow/MutableStateFlow;", "Lcom/wuheng/smart/presentation/base/UiDataState;", "", "Lcom/wuheng/smart/data/model/FloorInfo;", "_roomsState", "Lcom/wuheng/smart/data/model/RoomInfo;", "_selectedFloorId", "", "_selectedRoomId", "floorsState", "Lkotlinx/coroutines/flow/StateFlow;", "getFloorsState", "()Lkotlinx/coroutines/flow/StateFlow;", "roomsState", "getRoomsState", "selectedFloorId", "getSelectedFloorId", "selectedRoomId", "getSelectedRoomId", "loadFloors", "", "loadRooms", "floorId", "refresh", "selectFloor", "selectRoom", "roomId", "app_debug"})
public final class FloorZoneViewModel extends com.wuheng.smart.presentation.base.BaseViewModel {
    private final com.wuheng.smart.data.repository.HomeRepository homeRepository = null;
    private final com.wuheng.smart.data.network.TokenManager tokenManager = null;
    
    /**
     * 楼层列表状态
     */
    private final kotlinx.coroutines.flow.MutableStateFlow<com.wuheng.smart.presentation.base.UiDataState<java.util.List<com.wuheng.smart.data.model.FloorInfo>>> _floorsState = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.wuheng.smart.presentation.base.UiDataState<java.util.List<com.wuheng.smart.data.model.FloorInfo>>> floorsState = null;
    
    /**
     * 房间列表状态
     */
    private final kotlinx.coroutines.flow.MutableStateFlow<com.wuheng.smart.presentation.base.UiDataState<java.util.List<com.wuheng.smart.data.model.RoomInfo>>> _roomsState = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.wuheng.smart.presentation.base.UiDataState<java.util.List<com.wuheng.smart.data.model.RoomInfo>>> roomsState = null;
    
    /**
     * 当前选中的楼层ID
     */
    private final kotlinx.coroutines.flow.MutableStateFlow<java.lang.String> _selectedFloorId = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.lang.String> selectedFloorId = null;
    
    /**
     * 当前选中的房间ID
     */
    private final kotlinx.coroutines.flow.MutableStateFlow<java.lang.String> _selectedRoomId = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.lang.String> selectedRoomId = null;
    
    @javax.inject.Inject()
    public FloorZoneViewModel(@org.jetbrains.annotations.NotNull()
    com.wuheng.smart.data.repository.HomeRepository homeRepository, @org.jetbrains.annotations.NotNull()
    com.wuheng.smart.data.network.TokenManager tokenManager) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.wuheng.smart.presentation.base.UiDataState<java.util.List<com.wuheng.smart.data.model.FloorInfo>>> getFloorsState() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.wuheng.smart.presentation.base.UiDataState<java.util.List<com.wuheng.smart.data.model.RoomInfo>>> getRoomsState() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.lang.String> getSelectedFloorId() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.lang.String> getSelectedRoomId() {
        return null;
    }
    
    /**
     * 加载楼层列表
     */
    public final void loadFloors() {
    }
    
    /**
     * 选择楼层
     *
     * @param floorId 楼层ID
     */
    public final void selectFloor(@org.jetbrains.annotations.NotNull()
    java.lang.String floorId) {
    }
    
    /**
     * 加载房间列表
     *
     * @param floorId 楼层ID
     */
    private final void loadRooms(java.lang.String floorId) {
    }
    
    /**
     * 选择房间
     *
     * @param roomId 房间ID
     */
    public final void selectRoom(@org.jetbrains.annotations.NotNull()
    java.lang.String roomId) {
    }
    
    /**
     * 刷新数据
     */
    public final void refresh() {
    }
}