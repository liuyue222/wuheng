package com.wuheng.smart.presentation.home;

import com.wuheng.smart.data.model.*;
import com.wuheng.smart.data.network.ApiResult;
import com.wuheng.smart.data.network.TokenManager;
import com.wuheng.smart.data.repository.HomeRepository;
import com.wuheng.smart.presentation.base.BaseViewModel;
import com.wuheng.smart.presentation.base.UiDataState;
import com.wuheng.smart.presentation.home.components.DeviceCardUiState;
import com.wuheng.smart.presentation.home.components.WeatherModeSelectorUiState;
import dagger.hilt.android.lifecycle.HiltViewModel;
import kotlinx.coroutines.flow.SharedFlow;
import kotlinx.coroutines.flow.StateFlow;
import javax.inject.Inject;

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
@dagger.hilt.android.lifecycle.HiltViewModel()
@kotlin.Metadata(mv = {1, 7, 1}, k = 1, d1 = {"\u0000\u00b6\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0012\n\u0002\u0010\b\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0006\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\b\b\u0007\u0018\u00002\u00020\u0001B\u0017\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\u0016\u00103\u001a\u00020\u00102\u0006\u00104\u001a\u0002052\u0006\u00106\u001a\u00020\tJ\"\u00107\u001a\u00020\u00102\u0006\u00108\u001a\u0002052\u0006\u00109\u001a\u00020\t2\n\b\u0002\u0010:\u001a\u0004\u0018\u00010\tJ\f\u0010;\u001a\b\u0012\u0004\u0012\u00020<0\rJ\u000e\u0010=\u001a\u00020\u00102\u0006\u00106\u001a\u00020\tJ\u000e\u0010>\u001a\u00020\u00102\u0006\u00106\u001a\u00020\tJ\b\u0010?\u001a\u00020\u0010H\u0002J\u000e\u0010@\u001a\u00020\u00102\u0006\u00106\u001a\u00020\tJ\u000e\u0010A\u001a\u00020\u00102\u0006\u00106\u001a\u00020\tJ\u000e\u0010B\u001a\u00020\u00102\u0006\u00108\u001a\u00020\tJ\u000e\u0010C\u001a\u00020\u00102\u0006\u0010D\u001a\u00020EJ\u000e\u0010F\u001a\u00020\u00102\u0006\u0010G\u001a\u00020HJ\u000e\u0010I\u001a\u00020\u00102\u0006\u0010J\u001a\u00020\u0016J\u000e\u0010K\u001a\u00020\u00102\u0006\u0010D\u001a\u00020LJ\u0006\u0010M\u001a\u00020\u0010J\u0006\u0010N\u001a\u00020\u0010J\u000e\u0010O\u001a\u00020\u00102\u0006\u0010P\u001a\u00020QJ\u0016\u0010R\u001a\u00020\u00102\u0006\u00108\u001a\u0002052\u0006\u0010S\u001a\u00020TJ\u0016\u0010U\u001a\u00020\u00102\u0006\u00106\u001a\u00020\t2\u0006\u0010V\u001a\u00020\tJ\u0016\u0010W\u001a\u00020\u00102\u0006\u00106\u001a\u00020\t2\u0006\u0010X\u001a\u00020\tJ\u0016\u0010Y\u001a\u00020\u00102\u0006\u00106\u001a\u00020\t2\u0006\u0010D\u001a\u00020ZJ\u0016\u0010[\u001a\u00020\u00102\u0006\u00108\u001a\u0002052\u0006\u0010\\\u001a\u00020]J\u000e\u0010^\u001a\u00020\u00102\u0006\u0010_\u001a\u00020\tJ\u000e\u0010`\u001a\u00020\u00102\u0006\u0010D\u001a\u00020EJ.\u0010a\u001a\u00020\u00102\u0006\u0010S\u001a\u0002052\u0006\u0010b\u001a\u00020\t2\u0006\u0010c\u001a\u0002052\u0006\u0010d\u001a\u0002052\u0006\u0010V\u001a\u000205R\u0014\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R \u0010\n\u001a\u0014\u0012\u0010\u0012\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000e0\r0\f0\u000bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001a\u0010\u000f\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00100\f0\u000bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001a\u0010\u0011\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00120\f0\u000bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R \u0010\u0013\u001a\u0014\u0012\u0010\u0012\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00140\r0\f0\u000bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00160\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001a\u0010\u0017\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00180\f0\u000bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u001a0\u000bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u001c0\u000bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\t0\u001e\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001f\u0010 R#\u0010!\u001a\u0014\u0012\u0010\u0012\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000e0\r0\f0\"\u00a2\u0006\b\n\u0000\u001a\u0004\b#\u0010$R\u001d\u0010%\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00100\f0\"\u00a2\u0006\b\n\u0000\u001a\u0004\b&\u0010$R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001d\u0010\'\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00120\f0\"\u00a2\u0006\b\n\u0000\u001a\u0004\b(\u0010$R#\u0010)\u001a\u0014\u0012\u0010\u0012\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00140\r0\f0\"\u00a2\u0006\b\n\u0000\u001a\u0004\b*\u0010$R\u0017\u0010+\u001a\b\u0012\u0004\u0012\u00020\u00160\u001e\u00a2\u0006\b\n\u0000\u001a\u0004\b,\u0010 R\u001d\u0010-\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00180\f0\"\u00a2\u0006\b\n\u0000\u001a\u0004\b.\u0010$R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010/\u001a\b\u0012\u0004\u0012\u00020\u001a0\"\u00a2\u0006\b\n\u0000\u001a\u0004\b0\u0010$R\u0017\u00101\u001a\b\u0012\u0004\u0012\u00020\u001c0\"\u00a2\u0006\b\n\u0000\u001a\u0004\b2\u0010$\u00a8\u0006e"}, d2 = {"Lcom/wuheng/smart/presentation/home/HomeViewModel;", "Lcom/wuheng/smart/presentation/base/BaseViewModel;", "homeRepository", "Lcom/wuheng/smart/data/repository/HomeRepository;", "tokenManager", "Lcom/wuheng/smart/data/network/TokenManager;", "(Lcom/wuheng/smart/data/repository/HomeRepository;Lcom/wuheng/smart/data/network/TokenManager;)V", "_deviceClickEvent", "Lkotlinx/coroutines/flow/MutableSharedFlow;", "", "_deviceListState", "Lkotlinx/coroutines/flow/MutableStateFlow;", "Lcom/wuheng/smart/presentation/base/UiDataState;", "", "Lcom/wuheng/smart/data/model/DeviceInfo;", "_deviceOperationState", "", "_houseInfoState", "Lcom/wuheng/smart/data/model/HouseInfo;", "_sceneListState", "Lcom/wuheng/smart/data/model/SceneInfo;", "_serviceClickEvent", "Lcom/wuheng/smart/data/model/ServiceType;", "_systemStatusState", "Lcom/wuheng/smart/data/model/SystemStatus;", "_uiState", "Lcom/wuheng/smart/presentation/home/HomeUiState;", "_weatherModeState", "Lcom/wuheng/smart/presentation/home/components/WeatherModeSelectorUiState;", "deviceClickEvent", "Lkotlinx/coroutines/flow/SharedFlow;", "getDeviceClickEvent", "()Lkotlinx/coroutines/flow/SharedFlow;", "deviceListState", "Lkotlinx/coroutines/flow/StateFlow;", "getDeviceListState", "()Lkotlinx/coroutines/flow/StateFlow;", "deviceOperationState", "getDeviceOperationState", "houseInfoState", "getHouseInfoState", "sceneListState", "getSceneListState", "serviceClickEvent", "getServiceClickEvent", "systemStatusState", "getSystemStatusState", "uiState", "getUiState", "weatherModeState", "getWeatherModeState", "applyScene", "sceneId", "", "houseId", "controlDevice", "deviceId", "command", "value", "getDeviceCardUiStates", "Lcom/wuheng/smart/presentation/home/components/DeviceCardUiState;", "loadDeviceList", "loadHouseInfo", "loadInitialData", "loadSceneList", "loadSystemStatus", "onDeviceCardClicked", "onModeSelected", "mode", "Lcom/wuheng/smart/presentation/home/ClimateMode;", "onSceneSelected", "sceneType", "Lcom/wuheng/smart/data/model/SceneType;", "onServiceClicked", "serviceType", "onWeatherModeSelected", "Lcom/wuheng/smart/data/model/WeatherMode;", "refresh", "refreshData", "saveScene", "request", "Lcom/wuheng/smart/data/model/SaveSceneRequest;", "setDeviceTemperature", "temperature", "", "setGlobalHumidity", "humidity", "setGlobalTemp", "temp", "setSystemMode", "Lcom/wuheng/smart/data/model/SystemMode;", "toggleDevicePower", "powerOn", "", "updateLocation", "location", "updateMode", "updateWeather", "weather", "aqi", "pm25", "app_debug"})
public final class HomeViewModel extends com.wuheng.smart.presentation.base.BaseViewModel {
    private final com.wuheng.smart.data.repository.HomeRepository homeRepository = null;
    private final com.wuheng.smart.data.network.TokenManager tokenManager = null;
    private final kotlinx.coroutines.flow.MutableStateFlow<com.wuheng.smart.presentation.home.HomeUiState> _uiState = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.wuheng.smart.presentation.home.HomeUiState> uiState = null;
    
    /**
     * 新版API：房屋信息状态
     */
    private final kotlinx.coroutines.flow.MutableStateFlow<com.wuheng.smart.presentation.base.UiDataState<com.wuheng.smart.data.model.HouseInfo>> _houseInfoState = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.wuheng.smart.presentation.base.UiDataState<com.wuheng.smart.data.model.HouseInfo>> houseInfoState = null;
    
    /**
     * 新版API：设备列表状态
     */
    private final kotlinx.coroutines.flow.MutableStateFlow<com.wuheng.smart.presentation.base.UiDataState<java.util.List<com.wuheng.smart.data.model.DeviceInfo>>> _deviceListState = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.wuheng.smart.presentation.base.UiDataState<java.util.List<com.wuheng.smart.data.model.DeviceInfo>>> deviceListState = null;
    
    /**
     * 新版API：场景列表状态
     */
    private final kotlinx.coroutines.flow.MutableStateFlow<com.wuheng.smart.presentation.base.UiDataState<java.util.List<com.wuheng.smart.data.model.SceneInfo>>> _sceneListState = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.wuheng.smart.presentation.base.UiDataState<java.util.List<com.wuheng.smart.data.model.SceneInfo>>> sceneListState = null;
    
    /**
     * 新版API：系统状态
     */
    private final kotlinx.coroutines.flow.MutableStateFlow<com.wuheng.smart.presentation.base.UiDataState<com.wuheng.smart.data.model.SystemStatus>> _systemStatusState = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.wuheng.smart.presentation.base.UiDataState<com.wuheng.smart.data.model.SystemStatus>> systemStatusState = null;
    
    /**
     * 当前选中的天气模式状态
     */
    private final kotlinx.coroutines.flow.MutableStateFlow<com.wuheng.smart.presentation.home.components.WeatherModeSelectorUiState> _weatherModeState = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.wuheng.smart.presentation.home.components.WeatherModeSelectorUiState> weatherModeState = null;
    
    /**
     * 设备控制操作状态（用于显示加载/成功/错误反馈）
     */
    private final kotlinx.coroutines.flow.MutableStateFlow<com.wuheng.smart.presentation.base.UiDataState<kotlin.Unit>> _deviceOperationState = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.wuheng.smart.presentation.base.UiDataState<kotlin.Unit>> deviceOperationState = null;
    
    /**
     * 服务点击事件（一次性事件，使用SharedFlow避免配置变更重复消费）
     */
    private final kotlinx.coroutines.flow.MutableSharedFlow<com.wuheng.smart.data.model.ServiceType> _serviceClickEvent = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.SharedFlow<com.wuheng.smart.data.model.ServiceType> serviceClickEvent = null;
    
    /**
     * 设备卡片点击事件（导航到设备详情页，一次性事件）
     */
    private final kotlinx.coroutines.flow.MutableSharedFlow<java.lang.String> _deviceClickEvent = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.SharedFlow<java.lang.String> deviceClickEvent = null;
    
    @javax.inject.Inject()
    public HomeViewModel(@org.jetbrains.annotations.NotNull()
    com.wuheng.smart.data.repository.HomeRepository homeRepository, @org.jetbrains.annotations.NotNull()
    com.wuheng.smart.data.network.TokenManager tokenManager) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.wuheng.smart.presentation.home.HomeUiState> getUiState() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.wuheng.smart.presentation.base.UiDataState<com.wuheng.smart.data.model.HouseInfo>> getHouseInfoState() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.wuheng.smart.presentation.base.UiDataState<java.util.List<com.wuheng.smart.data.model.DeviceInfo>>> getDeviceListState() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.wuheng.smart.presentation.base.UiDataState<java.util.List<com.wuheng.smart.data.model.SceneInfo>>> getSceneListState() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.wuheng.smart.presentation.base.UiDataState<com.wuheng.smart.data.model.SystemStatus>> getSystemStatusState() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.wuheng.smart.presentation.home.components.WeatherModeSelectorUiState> getWeatherModeState() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.wuheng.smart.presentation.base.UiDataState<kotlin.Unit>> getDeviceOperationState() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.SharedFlow<com.wuheng.smart.data.model.ServiceType> getServiceClickEvent() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.SharedFlow<java.lang.String> getDeviceClickEvent() {
        return null;
    }
    
    /**
     * 加载初始数据
     */
    private final void loadInitialData() {
    }
    
    /**
     * 加载房屋信息（新版API）
     */
    public final void loadHouseInfo(@org.jetbrains.annotations.NotNull()
    java.lang.String houseId) {
    }
    
    /**
     * 加载设备列表（新版API）
     */
    public final void loadDeviceList(@org.jetbrains.annotations.NotNull()
    java.lang.String houseId) {
    }
    
    /**
     * 加载场景列表（新版API）
     */
    public final void loadSceneList(@org.jetbrains.annotations.NotNull()
    java.lang.String houseId) {
    }
    
    /**
     * 加载系统状态（新版API）
     */
    public final void loadSystemStatus(@org.jetbrains.annotations.NotNull()
    java.lang.String houseId) {
    }
    
    /**
     * 控制设备（新版API）
     *
     * @param deviceId 设备ID
     * @param command 命令：on/off/temp_up/temp_down/set_temp
     * @param value 控制值（可选）
     */
    public final void controlDevice(int deviceId, @org.jetbrains.annotations.NotNull()
    java.lang.String command, @org.jetbrains.annotations.Nullable()
    java.lang.String value) {
    }
    
    /**
     * 切换设备电源开关（新版API）
     *
     * @param deviceId 设备ID
     * @param powerOn 是否开启
     */
    public final void toggleDevicePower(int deviceId, boolean powerOn) {
    }
    
    /**
     * 设置设备目标温度（新版API）
     *
     * @param deviceId 设备ID
     * @param temperature 目标温度
     */
    public final void setDeviceTemperature(int deviceId, double temperature) {
    }
    
    /**
     * 处理设备卡片点击事件
     * 触发导航到设备详情页
     *
     * @param deviceId 设备ID
     */
    public final void onDeviceCardClicked(@org.jetbrains.annotations.NotNull()
    java.lang.String deviceId) {
    }
    
    /**
     * 切换系统天气模式（新版API）
     *
     * @param mode 目标模式（制冷/制热/通风/自动）
     */
    public final void onWeatherModeSelected(@org.jetbrains.annotations.NotNull()
    com.wuheng.smart.data.model.WeatherMode mode) {
    }
    
    /**
     * 处理服务入口点击事件
     *
     * @param serviceType 服务类型
     */
    public final void onServiceClicked(@org.jetbrains.annotations.NotNull()
    com.wuheng.smart.data.model.ServiceType serviceType) {
    }
    
    /**
     * 应用场景（新版API）
     *
     * @param sceneId 场景ID
     * @param houseId 房屋ID
     */
    public final void applyScene(int sceneId, @org.jetbrains.annotations.NotNull()
    java.lang.String houseId) {
    }
    
    /**
     * 保存自定义场景（新版API）
     *
     * @param request 保存场景请求
     */
    public final void saveScene(@org.jetbrains.annotations.NotNull()
    com.wuheng.smart.data.model.SaveSceneRequest request) {
    }
    
    /**
     * 设置系统模式
     */
    public final void setSystemMode(@org.jetbrains.annotations.NotNull()
    java.lang.String houseId, @org.jetbrains.annotations.NotNull()
    com.wuheng.smart.data.model.SystemMode mode) {
    }
    
    /**
     * 设置全局温度
     */
    public final void setGlobalTemp(@org.jetbrains.annotations.NotNull()
    java.lang.String houseId, @org.jetbrains.annotations.NotNull()
    java.lang.String temp) {
    }
    
    /**
     * 设置全局湿度
     */
    public final void setGlobalHumidity(@org.jetbrains.annotations.NotNull()
    java.lang.String houseId, @org.jetbrains.annotations.NotNull()
    java.lang.String humidity) {
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
     * 选择气候模式
     */
    public final void onModeSelected(@org.jetbrains.annotations.NotNull()
    com.wuheng.smart.presentation.home.ClimateMode mode) {
    }
    
    /**
     * 选择场景
     */
    public final void onSceneSelected(@org.jetbrains.annotations.NotNull()
    com.wuheng.smart.data.model.SceneType sceneType) {
    }
    
    /**
     * 更新位置信息
     */
    public final void updateLocation(@org.jetbrains.annotations.NotNull()
    java.lang.String location) {
    }
    
    /**
     * 更新天气信息
     */
    public final void updateWeather(int temperature, @org.jetbrains.annotations.NotNull()
    java.lang.String weather, int aqi, int pm25, int humidity) {
    }
    
    /**
     * 更新模式（仅更新UI，不刷新整个页面）
     */
    public final void updateMode(@org.jetbrains.annotations.NotNull()
    com.wuheng.smart.presentation.home.ClimateMode mode) {
    }
    
    /**
     * 将 DeviceInfo 列表转换为 DeviceCardUiState 列表（供UI层使用）
     *
     * @return 设备卡片UI状态列表
     */
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.wuheng.smart.presentation.home.components.DeviceCardUiState> getDeviceCardUiStates() {
        return null;
    }
}