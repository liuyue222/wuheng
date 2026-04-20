package com.wuheng.smart.presentation.water;

import com.wuheng.smart.data.model.*;
import com.wuheng.smart.data.network.ApiResult;
import com.wuheng.smart.data.network.TokenManager;
import com.wuheng.smart.data.repository.WaterRepository;
import com.wuheng.smart.presentation.base.BaseViewModel;
import com.wuheng.smart.presentation.base.UiDataState;
import dagger.hilt.android.lifecycle.HiltViewModel;
import kotlinx.coroutines.flow.StateFlow;
import javax.inject.Inject;

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
@dagger.hilt.android.lifecycle.HiltViewModel()
@kotlin.Metadata(mv = {1, 7, 1}, k = 1, d1 = {"\u0000n\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0007\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0010\n\u0002\u0010\u000e\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u0007\u0018\u00002\u00020\u0001B\u0017\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J:\u0010(\u001a\u00020\u00132\u0006\u0010)\u001a\u00020*2\u0006\u0010+\u001a\u00020*2\n\b\u0002\u0010,\u001a\u0004\u0018\u00010*2\n\b\u0002\u0010-\u001a\u0004\u0018\u00010*2\n\b\u0002\u0010.\u001a\u0004\u0018\u00010*J\u000e\u0010/\u001a\u00020\u00132\u0006\u0010)\u001a\u00020*J\u000e\u00100\u001a\u00020\u00132\u0006\u0010)\u001a\u00020*J\b\u00101\u001a\u00020\u0013H\u0002J\u000e\u00102\u001a\u00020\u00132\u0006\u00103\u001a\u000204J\u0006\u00105\u001a\u00020\u0013J\u0006\u00106\u001a\u00020\u0013J\'\u00107\u001a\u00020\u00132\u0006\u0010)\u001a\u00020*2\u0006\u00103\u001a\u0002082\n\b\u0002\u00109\u001a\u0004\u0018\u00010\u0015\u00a2\u0006\u0002\u0010:J\u000e\u0010;\u001a\u00020\u00132\u0006\u00109\u001a\u00020\u0015R\u0014\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u000b0\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R \u0010\f\u001a\u0014\u0012\u0010\u0012\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000f0\u000e0\r0\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001a\u0010\u0010\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00110\r0\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001a\u0010\u0012\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00130\r0\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00150\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00170\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\t0\u0019\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u001bR\u0017\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u000b0\u0019\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\u001bR#\u0010\u001e\u001a\u0014\u0012\u0010\u0012\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000f0\u000e0\r0\u0019\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001f\u0010\u001bR\u001d\u0010 \u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00110\r0\u0019\u00a2\u0006\b\n\u0000\u001a\u0004\b!\u0010\u001bR\u001d\u0010\"\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00130\r0\u0019\u00a2\u0006\b\n\u0000\u001a\u0004\b#\u0010\u001bR\u0017\u0010$\u001a\b\u0012\u0004\u0012\u00020\u00150\u0019\u00a2\u0006\b\n\u0000\u001a\u0004\b%\u0010\u001bR\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010&\u001a\b\u0012\u0004\u0012\u00020\u00170\u0019\u00a2\u0006\b\n\u0000\u001a\u0004\b\'\u0010\u001bR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006<"}, d2 = {"Lcom/wuheng/smart/presentation/water/WaterViewModel;", "Lcom/wuheng/smart/presentation/base/BaseViewModel;", "waterRepository", "Lcom/wuheng/smart/data/repository/WaterRepository;", "tokenManager", "Lcom/wuheng/smart/data/network/TokenManager;", "(Lcom/wuheng/smart/data/repository/WaterRepository;Lcom/wuheng/smart/data/network/TokenManager;)V", "_currentTempState", "Lkotlinx/coroutines/flow/MutableStateFlow;", "", "_cycleModeState", "Lcom/wuheng/smart/data/model/CycleMode;", "_filterStatusState", "Lcom/wuheng/smart/presentation/base/UiDataState;", "", "Lcom/wuheng/smart/data/model/FilterStatusInfo;", "_heaterStatusState", "Lcom/wuheng/smart/data/model/HeaterStatus;", "_operationState", "", "_temporaryDurationState", "", "_uiState", "Lcom/wuheng/smart/presentation/water/WaterUiState;", "currentTempState", "Lkotlinx/coroutines/flow/StateFlow;", "getCurrentTempState", "()Lkotlinx/coroutines/flow/StateFlow;", "cycleModeState", "getCycleModeState", "filterStatusState", "getFilterStatusState", "heaterStatusState", "getHeaterStatusState", "operationState", "getOperationState", "temporaryDurationState", "getTemporaryDurationState", "uiState", "getUiState", "bookFilterReplace", "houseId", "", "filterId", "contactName", "contactPhone", "appointmentDate", "loadFilterStatus", "loadHeaterStatus", "loadInitialData", "onHotWaterModeSelected", "mode", "Lcom/wuheng/smart/presentation/water/HotWaterMode;", "refresh", "refreshData", "setCirculationMode", "Lcom/wuheng/smart/data/model/CirculationMode;", "duration", "(Ljava/lang/String;Lcom/wuheng/smart/data/model/CirculationMode;Ljava/lang/Integer;)V", "setTemporaryDuration", "app_debug"})
public final class WaterViewModel extends com.wuheng.smart.presentation.base.BaseViewModel {
    private final com.wuheng.smart.data.repository.WaterRepository waterRepository = null;
    private final com.wuheng.smart.data.network.TokenManager tokenManager = null;
    private final kotlinx.coroutines.flow.MutableStateFlow<com.wuheng.smart.presentation.water.WaterUiState> _uiState = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.wuheng.smart.presentation.water.WaterUiState> uiState = null;
    
    /**
     * 热水循环状态（新版API）
     */
    private final kotlinx.coroutines.flow.MutableStateFlow<com.wuheng.smart.presentation.base.UiDataState<com.wuheng.smart.data.model.HeaterStatus>> _heaterStatusState = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.wuheng.smart.presentation.base.UiDataState<com.wuheng.smart.data.model.HeaterStatus>> heaterStatusState = null;
    
    /**
     * 当前选中的循环模式
     */
    private final kotlinx.coroutines.flow.MutableStateFlow<com.wuheng.smart.data.model.CycleMode> _cycleModeState = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.wuheng.smart.data.model.CycleMode> cycleModeState = null;
    
    /**
     * 临时循环运行时长（分钟）
     */
    private final kotlinx.coroutines.flow.MutableStateFlow<java.lang.Integer> _temporaryDurationState = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.lang.Integer> temporaryDurationState = null;
    
    /**
     * 当前水温显示
     */
    private final kotlinx.coroutines.flow.MutableStateFlow<java.lang.Float> _currentTempState = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.lang.Float> currentTempState = null;
    
    /**
     * 滤芯状态列表（新版API）
     */
    private final kotlinx.coroutines.flow.MutableStateFlow<com.wuheng.smart.presentation.base.UiDataState<java.util.List<com.wuheng.smart.data.model.FilterStatusInfo>>> _filterStatusState = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.wuheng.smart.presentation.base.UiDataState<java.util.List<com.wuheng.smart.data.model.FilterStatusInfo>>> filterStatusState = null;
    
    /**
     * 通用操作状态（用于显示加载/成功/错误反馈）
     */
    private final kotlinx.coroutines.flow.MutableStateFlow<com.wuheng.smart.presentation.base.UiDataState<kotlin.Unit>> _operationState = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.wuheng.smart.presentation.base.UiDataState<kotlin.Unit>> operationState = null;
    
    @javax.inject.Inject()
    public WaterViewModel(@org.jetbrains.annotations.NotNull()
    com.wuheng.smart.data.repository.WaterRepository waterRepository, @org.jetbrains.annotations.NotNull()
    com.wuheng.smart.data.network.TokenManager tokenManager) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.wuheng.smart.presentation.water.WaterUiState> getUiState() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.wuheng.smart.presentation.base.UiDataState<com.wuheng.smart.data.model.HeaterStatus>> getHeaterStatusState() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.wuheng.smart.data.model.CycleMode> getCycleModeState() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.lang.Integer> getTemporaryDurationState() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.lang.Float> getCurrentTempState() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.wuheng.smart.presentation.base.UiDataState<java.util.List<com.wuheng.smart.data.model.FilterStatusInfo>>> getFilterStatusState() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.wuheng.smart.presentation.base.UiDataState<kotlin.Unit>> getOperationState() {
        return null;
    }
    
    private final void loadInitialData() {
    }
    
    /**
     * 加载热水循环状态（新版API）
     */
    public final void loadHeaterStatus(@org.jetbrains.annotations.NotNull()
    java.lang.String houseId) {
    }
    
    /**
     * 加载滤芯状态（新版API）
     */
    public final void loadFilterStatus(@org.jetbrains.annotations.NotNull()
    java.lang.String houseId) {
    }
    
    /**
     * 切换热水循环模式（新版API）
     *
     * @param houseId 房屋ID
     * @param mode 目标模式 (ALL_DAY/TIMER/TEMP/OFF)
     * @param duration 临时模式的运行时长(分钟)，仅TEMP模式需要
     */
    public final void setCirculationMode(@org.jetbrains.annotations.NotNull()
    java.lang.String houseId, @org.jetbrains.annotations.NotNull()
    com.wuheng.smart.data.model.CirculationMode mode, @org.jetbrains.annotations.Nullable()
    java.lang.Integer duration) {
    }
    
    /**
     * 设置临时循环运行时长
     *
     * @param duration 时长(分钟): 30/60/90/120
     */
    public final void setTemporaryDuration(int duration) {
    }
    
    /**
     * 预约滤芯更换服务（新版API）
     *
     * @param houseId 房屋ID
     * @param filterId 滤芯ID
     * @param contactName 联系人姓名
     * @param contactPhone 联系人电话
     * @param appointmentDate 预约日期
     */
    public final void bookFilterReplace(@org.jetbrains.annotations.NotNull()
    java.lang.String houseId, @org.jetbrains.annotations.NotNull()
    java.lang.String filterId, @org.jetbrains.annotations.Nullable()
    java.lang.String contactName, @org.jetbrains.annotations.Nullable()
    java.lang.String contactPhone, @org.jetbrains.annotations.Nullable()
    java.lang.String appointmentDate) {
    }
    
    /**
     * 刷新所有数据
     */
    public final void refresh() {
    }
    
    /**
     * 刷新数据（供Layout调用）
     */
    public final void refreshData() {
    }
    
    /**
     * 选择热水模式
     */
    public final void onHotWaterModeSelected(@org.jetbrains.annotations.NotNull()
    com.wuheng.smart.presentation.water.HotWaterMode mode) {
    }
}