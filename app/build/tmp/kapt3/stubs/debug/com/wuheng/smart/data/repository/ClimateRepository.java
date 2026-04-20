package com.wuheng.smart.data.repository;

import com.wuheng.smart.data.model.*;
import com.wuheng.smart.data.network.ApiResult;
import com.wuheng.smart.data.network.ApiService;
import kotlinx.coroutines.flow.Flow;
import timber.log.Timber;
import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * 冷暖系统数据仓库接口
 *
 * 提供冷暖系统相关的所有数据操作方法，包括：
 * - 系统状态管理（获取、设置系统模式/温度/湿度）
 */
@kotlin.Metadata(mv = {1, 7, 1}, k = 1, d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\bf\u0018\u00002\u00020\u0001J%\u0010\u0002\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\u00040\u00032\u0006\u0010\u0006\u001a\u00020\u0007H\u00a6@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\bJ-\u0010\t\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\n0\u00040\u00032\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\u000b\u001a\u00020\fH\u00a6@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\rJ-\u0010\u000e\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\n0\u00040\u00032\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\u000f\u001a\u00020\fH\u00a6@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\rJ-\u0010\u0010\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00110\u00040\u00032\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\u0012\u001a\u00020\u0013H\u00a6@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0014\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006\u0015"}, d2 = {"Lcom/wuheng/smart/data/repository/ClimateRepository;", "", "getSystemStatus", "Lkotlinx/coroutines/flow/Flow;", "Lcom/wuheng/smart/data/network/ApiResult;", "Lcom/wuheng/smart/data/model/SystemStatus;", "houseId", "", "(ILkotlin/coroutines/Continuation;)Ljava/lang/Object;", "setGlobalHumidity", "", "humidity", "", "(ILjava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "setGlobalTemp", "temp", "setSystemMode", "Lcom/wuheng/smart/data/model/SetSystemModeResponse;", "mode", "Lcom/wuheng/smart/data/model/SystemMode;", "(ILcom/wuheng/smart/data/model/SystemMode;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_debug"})
public abstract interface ClimateRepository {
    
    /**
     * 获取系统状态
     *
     * @param houseId 房屋ID
     * @return 系统状态
     */
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getSystemStatus(int houseId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlinx.coroutines.flow.Flow<? extends com.wuheng.smart.data.network.ApiResult<com.wuheng.smart.data.model.SystemStatus>>> continuation);
    
    /**
     * 设置系统模式
     *
     * @param houseId 房屋ID
     * @param mode 系统模式
     * @return 设置响应
     */
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object setSystemMode(int houseId, @org.jetbrains.annotations.NotNull()
    com.wuheng.smart.data.model.SystemMode mode, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlinx.coroutines.flow.Flow<? extends com.wuheng.smart.data.network.ApiResult<com.wuheng.smart.data.model.SetSystemModeResponse>>> continuation);
    
    /**
     * 设置全局温度
     *
     * @param houseId 房屋ID
     * @param temp 温度值
     */
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object setGlobalTemp(int houseId, @org.jetbrains.annotations.NotNull()
    java.lang.String temp, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlinx.coroutines.flow.Flow<? extends com.wuheng.smart.data.network.ApiResult<kotlin.Unit>>> continuation);
    
    /**
     * 设置全局湿度
     *
     * @param houseId 房屋ID
     * @param humidity 湿度值
     */
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object setGlobalHumidity(int houseId, @org.jetbrains.annotations.NotNull()
    java.lang.String humidity, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlinx.coroutines.flow.Flow<? extends com.wuheng.smart.data.network.ApiResult<kotlin.Unit>>> continuation);
}