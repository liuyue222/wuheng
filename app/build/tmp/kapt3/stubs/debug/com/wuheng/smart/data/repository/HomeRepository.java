package com.wuheng.smart.data.repository;

import com.wuheng.smart.data.model.*;
import com.wuheng.smart.data.network.ApiResult;
import com.wuheng.smart.data.network.ApiService;
import com.wuheng.smart.data.network.RetryConfig;
import kotlinx.coroutines.flow.Flow;
import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * 首页数据仓库接口
 *
 * 提供首页所需的所有数据操作方法，包括：
 * - 房屋信息管理（获取房屋信息、楼层列表、房间列表）
 * - 设备管理（获取设备列表、设备详情、设备实时数据、控制设备）
 * - 场景管理（获取场景列表、应用场景、保存场景）
 * - 系统管理（获取系统状态、设置系统模式/温度/湿度）
 */
@kotlin.Metadata(mv = {1, 7, 1}, k = 1, d1 = {"\u0000\u008e\u0001\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bf\u0018\u00002\u00020\u0001J-\u0010\u0002\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\u00040\u00032\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\u0007H\u00a6@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\tJ9\u0010\n\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000b0\u00040\u00032\u0006\u0010\f\u001a\u00020\u00072\u0006\u0010\r\u001a\u00020\u000e2\n\b\u0002\u0010\u000f\u001a\u0004\u0018\u00010\u000eH\u00a6@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0010J%\u0010\u0011\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00120\u00040\u00032\u0006\u0010\f\u001a\u00020\u0007H\u00a6@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0013J7\u0010\u0014\u001a\u0014\u0012\u0010\u0012\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00120\u00150\u00040\u00032\u0006\u0010\b\u001a\u00020\u00072\n\b\u0002\u0010\u0016\u001a\u0004\u0018\u00010\u0007H\u00a6@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0017J%\u0010\u0018\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00190\u00040\u00032\u0006\u0010\f\u001a\u00020\u0007H\u00a6@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0013J+\u0010\u001a\u001a\u0014\u0012\u0010\u0012\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u001b0\u00150\u00040\u00032\u0006\u0010\b\u001a\u00020\u0007H\u00a6@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0013J%\u0010\u001c\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u001d0\u00040\u00032\u0006\u0010\b\u001a\u00020\u0007H\u00a6@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0013J7\u0010\u001e\u001a\u0014\u0012\u0010\u0012\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u001f0\u00150\u00040\u00032\u0006\u0010\b\u001a\u00020\u00072\n\b\u0002\u0010 \u001a\u0004\u0018\u00010\u0007H\u00a6@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0017J+\u0010!\u001a\u0014\u0012\u0010\u0012\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\"0\u00150\u00040\u00032\u0006\u0010\b\u001a\u00020\u0007H\u00a6@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0013J%\u0010#\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020$0\u00040\u00032\u0006\u0010\b\u001a\u00020\u0007H\u00a6@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0013J%\u0010%\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020&0\u00040\u00032\u0006\u0010\b\u001a\u00020\u0007H\u00a6@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0013J%\u0010\'\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020(0\u00040\u00032\u0006\u0010)\u001a\u00020*H\u00a6@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010+J-\u0010,\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020(0\u00040\u00032\u0006\u0010\b\u001a\u00020\u00072\u0006\u0010-\u001a\u00020\u000eH\u00a6@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010.J-\u0010/\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020(0\u00040\u00032\u0006\u0010\b\u001a\u00020\u00072\u0006\u00100\u001a\u00020\u000eH\u00a6@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010.J-\u00101\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002020\u00040\u00032\u0006\u0010\b\u001a\u00020\u00072\u0006\u00103\u001a\u00020\u000eH\u00a6@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010.J%\u00104\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002050\u00040\u00032\u0006\u0010)\u001a\u000206H\u00a6@\u00f8\u0001\u0000\u00a2\u0006\u0002\u00107\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u00068"}, d2 = {"Lcom/wuheng/smart/data/repository/HomeRepository;", "", "applyScene", "Lkotlinx/coroutines/flow/Flow;", "Lcom/wuheng/smart/data/network/ApiResult;", "Lcom/wuheng/smart/data/model/ApplySceneResponse;", "sceneId", "", "houseId", "(IILkotlin/coroutines/Continuation;)Ljava/lang/Object;", "controlDevice", "Lcom/wuheng/smart/data/model/ControlDeviceResponse;", "deviceId", "command", "", "value", "(ILjava/lang/String;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getDeviceDetail", "Lcom/wuheng/smart/data/model/DeviceInfo;", "(ILkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getDeviceList", "", "roomId", "(ILjava/lang/Integer;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getDeviceStatus", "Lcom/wuheng/smart/data/model/DeviceStatus;", "getFloorInfo", "Lcom/wuheng/smart/data/model/FloorInfo;", "getHouseInfo", "Lcom/wuheng/smart/data/model/HouseInfo;", "getRoomInfo", "Lcom/wuheng/smart/data/model/RoomInfo;", "floorId", "getSceneList", "Lcom/wuheng/smart/data/model/SceneInfo;", "getSystemParams", "Lcom/wuheng/smart/data/model/SystemParams;", "getSystemStatus", "Lcom/wuheng/smart/data/model/SystemStatus;", "saveScene", "", "request", "Lcom/wuheng/smart/data/model/SaveSceneRequest;", "(Lcom/wuheng/smart/data/model/SaveSceneRequest;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "setGlobalHumidity", "humidity", "(ILjava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "setGlobalTemp", "temp", "setSystemMode", "Lcom/wuheng/smart/data/model/SetSystemModeResponse;", "mode", "setSystemParams", "Lcom/wuheng/smart/data/model/SetSystemParamsResponse;", "Lcom/wuheng/smart/data/model/SetSystemParamsRequest;", "(Lcom/wuheng/smart/data/model/SetSystemParamsRequest;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_debug"})
public abstract interface HomeRepository {
    
    /**
     * 获取房屋详细信息
     *
     * @param houseId 房屋ID
     * @return 房屋详细信息
     */
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getHouseInfo(int houseId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlinx.coroutines.flow.Flow<? extends com.wuheng.smart.data.network.ApiResult<com.wuheng.smart.data.model.HouseInfo>>> continuation);
    
    /**
     * 获取楼层信息
     *
     * @param houseId 房屋ID
     * @return 楼层列表
     */
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getFloorInfo(int houseId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlinx.coroutines.flow.Flow<? extends com.wuheng.smart.data.network.ApiResult<? extends java.util.List<com.wuheng.smart.data.model.FloorInfo>>>> continuation);
    
    /**
     * 获取房间信息
     *
     * @param houseId 房屋ID
     * @param floorId 楼层ID（可选，不传则返回所有房间）
     * @return 房间列表
     */
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getRoomInfo(int houseId, @org.jetbrains.annotations.Nullable()
    java.lang.Integer floorId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlinx.coroutines.flow.Flow<? extends com.wuheng.smart.data.network.ApiResult<? extends java.util.List<com.wuheng.smart.data.model.RoomInfo>>>> continuation);
    
    /**
     * 获取设备列表
     *
     * @param houseId 房屋ID
     * @param roomId 房间ID（可选）
     * @return 设备列表
     */
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getDeviceList(int houseId, @org.jetbrains.annotations.Nullable()
    java.lang.Integer roomId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlinx.coroutines.flow.Flow<? extends com.wuheng.smart.data.network.ApiResult<? extends java.util.List<com.wuheng.smart.data.model.DeviceInfo>>>> continuation);
    
    /**
     * 获取设备详情
     *
     * @param deviceId 设备ID
     * @return 设备详细信息
     */
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getDeviceDetail(int deviceId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlinx.coroutines.flow.Flow<? extends com.wuheng.smart.data.network.ApiResult<com.wuheng.smart.data.model.DeviceInfo>>> continuation);
    
    /**
     * 获取设备状态
     *
     * @param deviceId 设备ID
     * @return 设备状态（温度、湿度、CO2等）
     */
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getDeviceStatus(int deviceId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlinx.coroutines.flow.Flow<? extends com.wuheng.smart.data.network.ApiResult<com.wuheng.smart.data.model.DeviceStatus>>> continuation);
    
    /**
     * 控制设备
     *
     * @param deviceId 设备ID
     * @param command 命令：on/off/temp_up/temp_down/set_temp
     * @param value 控制值（可选）
     * @return 控制响应
     */
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object controlDevice(int deviceId, @org.jetbrains.annotations.NotNull()
    java.lang.String command, @org.jetbrains.annotations.Nullable()
    java.lang.String value, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlinx.coroutines.flow.Flow<? extends com.wuheng.smart.data.network.ApiResult<com.wuheng.smart.data.model.ControlDeviceResponse>>> continuation);
    
    /**
     * 获取场景列表
     *
     * @param houseId 房屋ID
     * @return 场景列表
     */
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getSceneList(int houseId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlinx.coroutines.flow.Flow<? extends com.wuheng.smart.data.network.ApiResult<? extends java.util.List<com.wuheng.smart.data.model.SceneInfo>>>> continuation);
    
    /**
     * 应用场景
     *
     * @param sceneId 场景ID
     * @param houseId 房屋ID
     * @return 应用场景响应
     */
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object applyScene(int sceneId, int houseId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlinx.coroutines.flow.Flow<? extends com.wuheng.smart.data.network.ApiResult<com.wuheng.smart.data.model.ApplySceneResponse>>> continuation);
    
    /**
     * 保存自定义场景
     *
     * @param request 保存场景请求
     */
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object saveScene(@org.jetbrains.annotations.NotNull()
    com.wuheng.smart.data.model.SaveSceneRequest request, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlinx.coroutines.flow.Flow<? extends com.wuheng.smart.data.network.ApiResult<kotlin.Unit>>> continuation);
    
    /**
     * 获取系统状态
     *
     * @param houseId 房屋ID
     * @return 系统状态（模式、温度、湿度等）
     */
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getSystemStatus(int houseId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlinx.coroutines.flow.Flow<? extends com.wuheng.smart.data.network.ApiResult<com.wuheng.smart.data.model.SystemStatus>>> continuation);
    
    /**
     * 设置系统模式
     *
     * @param houseId 房屋ID
     * @param mode 模式：cooling/heating/ventilation/auto
     * @return 设置响应
     */
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object setSystemMode(int houseId, @org.jetbrains.annotations.NotNull()
    java.lang.String mode, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlinx.coroutines.flow.Flow<? extends com.wuheng.smart.data.network.ApiResult<com.wuheng.smart.data.model.SetSystemModeResponse>>> continuation);
    
    /**
     * 设置全局温度
     *
     * @param houseId 房屋ID
     * @param temp 温度值（16-30）
     */
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object setGlobalTemp(int houseId, @org.jetbrains.annotations.NotNull()
    java.lang.String temp, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlinx.coroutines.flow.Flow<? extends com.wuheng.smart.data.network.ApiResult<kotlin.Unit>>> continuation);
    
    /**
     * 设置全局湿度
     *
     * @param houseId 房屋ID
     * @param humidity 湿度值（30-70）
     */
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object setGlobalHumidity(int houseId, @org.jetbrains.annotations.NotNull()
    java.lang.String humidity, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlinx.coroutines.flow.Flow<? extends com.wuheng.smart.data.network.ApiResult<kotlin.Unit>>> continuation);
    
    /**
     * 获取系统参数
     *
     * @param houseId 房屋ID
     * @return 系统参数（温度、湿度、CO2阈值等设置）
     */
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getSystemParams(int houseId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlinx.coroutines.flow.Flow<? extends com.wuheng.smart.data.network.ApiResult<com.wuheng.smart.data.model.SystemParams>>> continuation);
    
    /**
     * 设置系统参数
     *
     * @param request 设置系统参数请求
     * @return 设置响应，包含更新的参数列表
     */
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object setSystemParams(@org.jetbrains.annotations.NotNull()
    com.wuheng.smart.data.model.SetSystemParamsRequest request, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlinx.coroutines.flow.Flow<? extends com.wuheng.smart.data.network.ApiResult<com.wuheng.smart.data.model.SetSystemParamsResponse>>> continuation);
    
    /**
     * 首页数据仓库接口
     *
     * 提供首页所需的所有数据操作方法，包括：
     * - 房屋信息管理（获取房屋信息、楼层列表、房间列表）
     * - 设备管理（获取设备列表、设备详情、设备实时数据、控制设备）
     * - 场景管理（获取场景列表、应用场景、保存场景）
     * - 系统管理（获取系统状态、设置系统模式/温度/湿度）
     */
    @kotlin.Metadata(mv = {1, 7, 1}, k = 3)
    public final class DefaultImpls {
    }
}