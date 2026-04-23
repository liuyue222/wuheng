package com.wuheng.smart.data.repository;

import com.wuheng.smart.data.model.*;
import com.wuheng.smart.data.network.ApiResult;
import com.wuheng.smart.data.network.ApiService;
import com.wuheng.smart.data.network.RetryConfig;
import kotlinx.coroutines.flow.Flow;
import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * 首页数据仓库实现类
 *
 * @param apiService Retrofit API服务
 * @param useMock 是否使用Mock数据，默认false使用真实API
 */
@kotlin.Metadata(mv = {1, 7, 1}, k = 1, d1 = {"\u0000\u00a6\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0007\u0018\u00002\u00020\u00012\u00020\u0002B\u0019\b\u0007\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\b\b\u0002\u0010\u0005\u001a\u00020\u0006\u00a2\u0006\u0002\u0010\u0007J-\u0010\n\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\r0\f0\u000b2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u000fH\u0096@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0011J7\u0010\u0012\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00130\f0\u000b2\u0006\u0010\u0014\u001a\u00020\u000f2\u0006\u0010\u0015\u001a\u00020\u00162\b\u0010\u0017\u001a\u0004\u0018\u00010\u0016H\u0096@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0018J%\u0010\u0019\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u001a0\f0\u000b2\u0006\u0010\u0014\u001a\u00020\u000fH\u0096@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u001bJ5\u0010\u001c\u001a\u0014\u0012\u0010\u0012\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u001a0\u001d0\f0\u000b2\u0006\u0010\u0010\u001a\u00020\u000f2\b\u0010\u001e\u001a\u0004\u0018\u00010\u000fH\u0096@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u001fJ%\u0010 \u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020!0\f0\u000b2\u0006\u0010\u0014\u001a\u00020\u000fH\u0096@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u001bJ+\u0010\"\u001a\u0014\u0012\u0010\u0012\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020#0\u001d0\f0\u000b2\u0006\u0010\u0010\u001a\u00020\u000fH\u0096@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u001bJ%\u0010$\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020%0\f0\u000b2\u0006\u0010\u0010\u001a\u00020\u000fH\u0096@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u001bJ5\u0010&\u001a\u0014\u0012\u0010\u0012\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\'0\u001d0\f0\u000b2\u0006\u0010\u0010\u001a\u00020\u000f2\b\u0010(\u001a\u0004\u0018\u00010\u000fH\u0096@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u001fJ+\u0010)\u001a\u0014\u0012\u0010\u0012\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020*0\u001d0\f0\u000b2\u0006\u0010\u0010\u001a\u00020\u000fH\u0096@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u001bJ%\u0010+\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020,0\f0\u000b2\u0006\u0010\u0010\u001a\u00020\u000fH\u0096@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u001bJ%\u0010-\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020.0\f0\u000b2\u0006\u0010\u0010\u001a\u00020\u000fH\u0096@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u001bJ%\u0010/\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002000\f0\u000b2\u0006\u00101\u001a\u000202H\u0096@\u00f8\u0001\u0000\u00a2\u0006\u0002\u00103J-\u00104\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002000\f0\u000b2\u0006\u0010\u0010\u001a\u00020\u000f2\u0006\u00105\u001a\u00020\u0016H\u0096@\u00f8\u0001\u0000\u00a2\u0006\u0002\u00106J-\u00107\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002000\f0\u000b2\u0006\u0010\u0010\u001a\u00020\u000f2\u0006\u00108\u001a\u00020\u0016H\u0096@\u00f8\u0001\u0000\u00a2\u0006\u0002\u00106J-\u00109\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020:0\f0\u000b2\u0006\u0010\u0010\u001a\u00020\u000f2\u0006\u0010;\u001a\u00020\u0016H\u0096@\u00f8\u0001\u0000\u00a2\u0006\u0002\u00106J%\u0010<\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020=0\f0\u000b2\u0006\u00101\u001a\u00020>H\u0096@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010?R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006@"}, d2 = {"Lcom/wuheng/smart/data/repository/HomeRepositoryImpl;", "Lcom/wuheng/smart/data/repository/BaseRepository;", "Lcom/wuheng/smart/data/repository/HomeRepository;", "apiService", "Lcom/wuheng/smart/data/network/ApiService;", "useMock", "", "(Lcom/wuheng/smart/data/network/ApiService;Z)V", "houseRetryConfig", "Lcom/wuheng/smart/data/network/RetryConfig;", "applyScene", "Lkotlinx/coroutines/flow/Flow;", "Lcom/wuheng/smart/data/network/ApiResult;", "Lcom/wuheng/smart/data/model/ApplySceneResponse;", "sceneId", "", "houseId", "(IILkotlin/coroutines/Continuation;)Ljava/lang/Object;", "controlDevice", "Lcom/wuheng/smart/data/model/ControlDeviceResponse;", "deviceId", "command", "", "value", "(ILjava/lang/String;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getDeviceDetail", "Lcom/wuheng/smart/data/model/DeviceInfo;", "(ILkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getDeviceList", "", "roomId", "(ILjava/lang/Integer;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getDeviceStatus", "Lcom/wuheng/smart/data/model/DeviceStatus;", "getFloorInfo", "Lcom/wuheng/smart/data/model/FloorInfo;", "getHouseInfo", "Lcom/wuheng/smart/data/model/HouseInfo;", "getRoomInfo", "Lcom/wuheng/smart/data/model/RoomInfo;", "floorId", "getSceneList", "Lcom/wuheng/smart/data/model/SceneInfo;", "getSystemParams", "Lcom/wuheng/smart/data/model/SystemParams;", "getSystemStatus", "Lcom/wuheng/smart/data/model/SystemStatus;", "saveScene", "", "request", "Lcom/wuheng/smart/data/model/SaveSceneRequest;", "(Lcom/wuheng/smart/data/model/SaveSceneRequest;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "setGlobalHumidity", "humidity", "(ILjava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "setGlobalTemp", "temp", "setSystemMode", "Lcom/wuheng/smart/data/model/SetSystemModeResponse;", "mode", "setSystemParams", "Lcom/wuheng/smart/data/model/SetSystemParamsResponse;", "Lcom/wuheng/smart/data/model/SetSystemParamsRequest;", "(Lcom/wuheng/smart/data/model/SetSystemParamsRequest;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_debug"})
@javax.inject.Singleton()
public final class HomeRepositoryImpl extends com.wuheng.smart.data.repository.BaseRepository implements com.wuheng.smart.data.repository.HomeRepository {
    private final com.wuheng.smart.data.network.ApiService apiService = null;
    private final boolean useMock = false;
    
    /**
     * 房屋模块使用默认重试配置
     * - 最大重试3次
     * - 初始延迟1秒，指数退避
     * - 网络错误、超时、服务器错误都会重试
     */
    private final com.wuheng.smart.data.network.RetryConfig houseRetryConfig = null;
    
    @javax.inject.Inject()
    public HomeRepositoryImpl(@org.jetbrains.annotations.NotNull()
    com.wuheng.smart.data.network.ApiService apiService, boolean useMock) {
        super();
    }
    
    @org.jetbrains.annotations.Nullable()
    @java.lang.Override()
    public java.lang.Object getHouseInfo(int houseId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlinx.coroutines.flow.Flow<? extends com.wuheng.smart.data.network.ApiResult<com.wuheng.smart.data.model.HouseInfo>>> continuation) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    @java.lang.Override()
    public java.lang.Object getFloorInfo(int houseId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlinx.coroutines.flow.Flow<? extends com.wuheng.smart.data.network.ApiResult<? extends java.util.List<com.wuheng.smart.data.model.FloorInfo>>>> continuation) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    @java.lang.Override()
    public java.lang.Object getRoomInfo(int houseId, @org.jetbrains.annotations.Nullable()
    java.lang.Integer floorId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlinx.coroutines.flow.Flow<? extends com.wuheng.smart.data.network.ApiResult<? extends java.util.List<com.wuheng.smart.data.model.RoomInfo>>>> continuation) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    @java.lang.Override()
    public java.lang.Object getDeviceList(int houseId, @org.jetbrains.annotations.Nullable()
    java.lang.Integer roomId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlinx.coroutines.flow.Flow<? extends com.wuheng.smart.data.network.ApiResult<? extends java.util.List<com.wuheng.smart.data.model.DeviceInfo>>>> continuation) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    @java.lang.Override()
    public java.lang.Object getDeviceDetail(int deviceId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlinx.coroutines.flow.Flow<? extends com.wuheng.smart.data.network.ApiResult<com.wuheng.smart.data.model.DeviceInfo>>> continuation) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    @java.lang.Override()
    public java.lang.Object getDeviceStatus(int deviceId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlinx.coroutines.flow.Flow<? extends com.wuheng.smart.data.network.ApiResult<com.wuheng.smart.data.model.DeviceStatus>>> continuation) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    @java.lang.Override()
    public java.lang.Object controlDevice(int deviceId, @org.jetbrains.annotations.NotNull()
    java.lang.String command, @org.jetbrains.annotations.Nullable()
    java.lang.String value, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlinx.coroutines.flow.Flow<? extends com.wuheng.smart.data.network.ApiResult<com.wuheng.smart.data.model.ControlDeviceResponse>>> continuation) {
        return null;
    }
    
    /**
     * 获取场景列表
     * 使用带重试机制的API调用，在网络错误时自动重试3次
     */
    @org.jetbrains.annotations.Nullable()
    @java.lang.Override()
    public java.lang.Object getSceneList(int houseId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlinx.coroutines.flow.Flow<? extends com.wuheng.smart.data.network.ApiResult<? extends java.util.List<com.wuheng.smart.data.model.SceneInfo>>>> continuation) {
        return null;
    }
    
    /**
     * 应用场景
     * 使用带重试机制的API调用，确保场景切换命令可靠送达
     */
    @org.jetbrains.annotations.Nullable()
    @java.lang.Override()
    public java.lang.Object applyScene(int sceneId, int houseId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlinx.coroutines.flow.Flow<? extends com.wuheng.smart.data.network.ApiResult<com.wuheng.smart.data.model.ApplySceneResponse>>> continuation) {
        return null;
    }
    
    /**
     * 保存自定义场景
     * 使用带重试机制的API调用，确保场景配置保存成功
     */
    @org.jetbrains.annotations.Nullable()
    @java.lang.Override()
    public java.lang.Object saveScene(@org.jetbrains.annotations.NotNull()
    com.wuheng.smart.data.model.SaveSceneRequest request, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlinx.coroutines.flow.Flow<? extends com.wuheng.smart.data.network.ApiResult<kotlin.Unit>>> continuation) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    @java.lang.Override()
    public java.lang.Object getSystemStatus(int houseId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlinx.coroutines.flow.Flow<? extends com.wuheng.smart.data.network.ApiResult<com.wuheng.smart.data.model.SystemStatus>>> continuation) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    @java.lang.Override()
    public java.lang.Object setSystemMode(int houseId, @org.jetbrains.annotations.NotNull()
    java.lang.String mode, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlinx.coroutines.flow.Flow<? extends com.wuheng.smart.data.network.ApiResult<com.wuheng.smart.data.model.SetSystemModeResponse>>> continuation) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    @java.lang.Override()
    public java.lang.Object setGlobalTemp(int houseId, @org.jetbrains.annotations.NotNull()
    java.lang.String temp, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlinx.coroutines.flow.Flow<? extends com.wuheng.smart.data.network.ApiResult<kotlin.Unit>>> continuation) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    @java.lang.Override()
    public java.lang.Object setGlobalHumidity(int houseId, @org.jetbrains.annotations.NotNull()
    java.lang.String humidity, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlinx.coroutines.flow.Flow<? extends com.wuheng.smart.data.network.ApiResult<kotlin.Unit>>> continuation) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    @java.lang.Override()
    public java.lang.Object getSystemParams(int houseId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlinx.coroutines.flow.Flow<? extends com.wuheng.smart.data.network.ApiResult<com.wuheng.smart.data.model.SystemParams>>> continuation) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    @java.lang.Override()
    public java.lang.Object setSystemParams(@org.jetbrains.annotations.NotNull()
    com.wuheng.smart.data.model.SetSystemParamsRequest request, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlinx.coroutines.flow.Flow<? extends com.wuheng.smart.data.network.ApiResult<com.wuheng.smart.data.model.SetSystemParamsResponse>>> continuation) {
        return null;
    }
}