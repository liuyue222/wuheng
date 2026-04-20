package com.wuheng.smart.data.mock;

import com.wuheng.smart.data.model.*;
import com.wuheng.smart.data.network.BaseResponse;
import kotlinx.coroutines.flow.Flow;

/**
 * Mock数据源 - 模拟网络请求
 * 提供模拟的API调用方法，支持模拟网络延迟
 */
@kotlin.Metadata(mv = {1, 7, 1}, k = 1, d1 = {"\u0000t\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u0006\n\u0002\b\u0006\b\u00c7\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u000e\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007H\u0002J!\u0010\t\u001a\b\u0012\u0004\u0012\u0002H\n0\u0007\"\u0004\b\u0000\u0010\n2\u0006\u0010\u000b\u001a\u0002H\nH\u0002\u00a2\u0006\u0002\u0010\fJ\u0018\u0010\r\u001a\u0014\u0012\u0010\u0012\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00100\u000f0\u00070\u000eJ\u0012\u0010\u0011\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00120\u00070\u000eJ\u0018\u0010\u0013\u001a\u0014\u0012\u0010\u0012\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00140\u000f0\u00070\u000eJ\u0012\u0010\u0015\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00160\u00070\u000eJ\u001a\u0010\u0017\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00180\u00070\u000e2\u0006\u0010\u0019\u001a\u00020\u001aJ \u0010\u001b\u001a\u0014\u0012\u0010\u0012\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u001c0\u000f0\u00070\u000e2\u0006\u0010\u001d\u001a\u00020\u001aJ.\u0010\u001e\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u001f0\u00070\u000e2\u0006\u0010 \u001a\u00020\u001a2\b\u0010!\u001a\u0004\u0018\u00010\u001a2\b\u0010\"\u001a\u0004\u0018\u00010\u001aJ\u0012\u0010#\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00070\u000eJ\"\u0010$\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00070\u000e2\u0006\u0010\u0019\u001a\u00020\u001a2\u0006\u0010%\u001a\u00020&J\"\u0010\'\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00070\u000e2\u0006\u0010\u0019\u001a\u00020\u001a2\u0006\u0010(\u001a\u00020)J\"\u0010*\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00070\u000e2\u0006\u0010\u0019\u001a\u00020\u001a2\u0006\u0010+\u001a\u00020,J\u0011\u0010-\u001a\u00020\bH\u0082@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010.J\u0018\u0010/\u001a\u00020\b2\u0006\u0010\u0019\u001a\u00020\u001a2\u0006\u0010%\u001a\u00020&H\u0002J\u0018\u00100\u001a\u00020\b2\u0006\u0010\u0019\u001a\u00020\u001a2\u0006\u0010(\u001a\u00020)H\u0002J\u0018\u00101\u001a\u00020\b2\u0006\u0010\u0019\u001a\u00020\u001a2\u0006\u0010+\u001a\u00020,H\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u00062"}, d2 = {"Lcom/wuheng/smart/data/mock/MockDataSource;", "", "()V", "MAX_DELAY", "", "MIN_DELAY", "createEmptySuccessResponse", "Lcom/wuheng/smart/data/network/BaseResponse;", "", "createSuccessResponse", "T", "data", "(Ljava/lang/Object;)Lcom/wuheng/smart/data/network/BaseResponse;", "getAllDevices", "Lkotlinx/coroutines/flow/Flow;", "", "Lcom/wuheng/smart/data/model/Device;", "getClimateOverview", "Lcom/wuheng/smart/data/model/ClimateOverview;", "getFloors", "Lcom/wuheng/smart/data/model/Floor;", "getHomeOverview", "Lcom/wuheng/smart/data/model/HomeOverview;", "getZoneDetail", "Lcom/wuheng/smart/data/model/ZoneDetail;", "zoneId", "", "getZonesByFloor", "Lcom/wuheng/smart/data/model/Zone;", "floorId", "login", "Lcom/wuheng/smart/data/model/UserInfo;", "phone", "code", "password", "logout", "setZoneMode", "mode", "Lcom/wuheng/smart/data/model/ClimateMode;", "setZonePower", "powerOn", "", "setZoneTemperature", "temperature", "", "simulateNetworkDelay", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "updateZoneMode", "updateZonePower", "updateZoneTemperature", "app_debug"})
public final class MockDataSource {
    @org.jetbrains.annotations.NotNull()
    public static final com.wuheng.smart.data.mock.MockDataSource INSTANCE = null;
    private static final long MIN_DELAY = 500L;
    private static final long MAX_DELAY = 1500L;
    
    private MockDataSource() {
        super();
    }
    
    /**
     * 获取模拟延迟时间
     */
    private final java.lang.Object simulateNetworkDelay(kotlin.coroutines.Continuation<? super kotlin.Unit> continuation) {
        return null;
    }
    
    /**
     * 创建成功响应
     */
    private final <T extends java.lang.Object>com.wuheng.smart.data.network.BaseResponse<T> createSuccessResponse(T data) {
        return null;
    }
    
    /**
     * 创建成功响应（无数据）
     */
    private final com.wuheng.smart.data.network.BaseResponse<kotlin.Unit> createEmptySuccessResponse() {
        return null;
    }
    
    /**
     * 获取首页概览数据
     */
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<com.wuheng.smart.data.network.BaseResponse<com.wuheng.smart.data.model.HomeOverview>> getHomeOverview() {
        return null;
    }
    
    /**
     * 获取所有设备列表
     */
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<com.wuheng.smart.data.network.BaseResponse<java.util.List<com.wuheng.smart.data.model.Device>>> getAllDevices() {
        return null;
    }
    
    /**
     * 获取冷暖系统概览
     */
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<com.wuheng.smart.data.network.BaseResponse<com.wuheng.smart.data.model.ClimateOverview>> getClimateOverview() {
        return null;
    }
    
    /**
     * 获取楼层列表
     */
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<com.wuheng.smart.data.network.BaseResponse<java.util.List<com.wuheng.smart.data.model.Floor>>> getFloors() {
        return null;
    }
    
    /**
     * 根据楼层ID获取区域列表
     */
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<com.wuheng.smart.data.network.BaseResponse<java.util.List<com.wuheng.smart.data.model.Zone>>> getZonesByFloor(@org.jetbrains.annotations.NotNull()
    java.lang.String floorId) {
        return null;
    }
    
    /**
     * 获取区域详情
     */
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<com.wuheng.smart.data.network.BaseResponse<com.wuheng.smart.data.model.ZoneDetail>> getZoneDetail(@org.jetbrains.annotations.NotNull()
    java.lang.String zoneId) {
        return null;
    }
    
    /**
     * 设置区域温度
     */
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<com.wuheng.smart.data.network.BaseResponse<kotlin.Unit>> setZoneTemperature(@org.jetbrains.annotations.NotNull()
    java.lang.String zoneId, double temperature) {
        return null;
    }
    
    /**
     * 设置区域模式
     */
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<com.wuheng.smart.data.network.BaseResponse<kotlin.Unit>> setZoneMode(@org.jetbrains.annotations.NotNull()
    java.lang.String zoneId, @org.jetbrains.annotations.NotNull()
    com.wuheng.smart.data.model.ClimateMode mode) {
        return null;
    }
    
    /**
     * 设置区域电源开关
     */
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<com.wuheng.smart.data.network.BaseResponse<kotlin.Unit>> setZonePower(@org.jetbrains.annotations.NotNull()
    java.lang.String zoneId, boolean powerOn) {
        return null;
    }
    
    /**
     * 用户登录
     */
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<com.wuheng.smart.data.network.BaseResponse<com.wuheng.smart.data.model.UserInfo>> login(@org.jetbrains.annotations.NotNull()
    java.lang.String phone, @org.jetbrains.annotations.Nullable()
    java.lang.String code, @org.jetbrains.annotations.Nullable()
    java.lang.String password) {
        return null;
    }
    
    /**
     * 用户登出
     */
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<com.wuheng.smart.data.network.BaseResponse<kotlin.Unit>> logout() {
        return null;
    }
    
    /**
     * 更新区域温度（内部方法，模拟数据更新）
     */
    private final void updateZoneTemperature(java.lang.String zoneId, double temperature) {
    }
    
    /**
     * 更新区域模式（内部方法，模拟数据更新）
     */
    private final void updateZoneMode(java.lang.String zoneId, com.wuheng.smart.data.model.ClimateMode mode) {
    }
    
    /**
     * 更新区域电源（内部方法，模拟数据更新）
     */
    private final void updateZonePower(java.lang.String zoneId, boolean powerOn) {
    }
}