package com.wuheng.smart.data.repository;

import com.wuheng.smart.data.model.*;
import com.wuheng.smart.data.network.ApiResult;
import com.wuheng.smart.data.network.ApiService;
import kotlinx.coroutines.flow.Flow;
import timber.log.Timber;
import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * 水系统数据仓库实现类
 *
 * @param apiService Retrofit API服务
 * @param useMock 是否使用Mock数据，默认false使用真实API
 */
@kotlin.Metadata(mv = {1, 7, 1}, k = 1, d1 = {"\u0000`\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u0007\u0018\u0000 \"2\u00020\u00012\u00020\u0002:\u0001\"B\u0019\b\u0007\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\b\b\u0002\u0010\u0005\u001a\u00020\u0006\u00a2\u0006\u0002\u0010\u0007JK\u0010\b\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000b0\n0\t2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\r2\b\u0010\u000f\u001a\u0004\u0018\u00010\u00102\b\u0010\u0011\u001a\u0004\u0018\u00010\u00102\b\u0010\u0012\u001a\u0004\u0018\u00010\u0010H\u0096@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0013J+\u0010\u0014\u001a\u0014\u0012\u0010\u0012\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00160\u00150\n0\t2\u0006\u0010\f\u001a\u00020\rH\u0096@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0017J%\u0010\u0018\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00190\n0\t2\u0006\u0010\f\u001a\u00020\rH\u0096@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0017J%\u0010\u001a\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u001b0\n0\t2\u0006\u0010\f\u001a\u00020\rH\u0096@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0017J7\u0010\u001c\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u001d0\n0\t2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u001e\u001a\u00020\u001f2\b\u0010 \u001a\u0004\u0018\u00010\rH\u0096@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010!R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006#"}, d2 = {"Lcom/wuheng/smart/data/repository/WaterRepositoryImpl;", "Lcom/wuheng/smart/data/repository/BaseRepository;", "Lcom/wuheng/smart/data/repository/WaterRepository;", "apiService", "Lcom/wuheng/smart/data/network/ApiService;", "useMock", "", "(Lcom/wuheng/smart/data/network/ApiService;Z)V", "bookFilterReplace", "Lkotlinx/coroutines/flow/Flow;", "Lcom/wuheng/smart/data/network/ApiResult;", "", "houseId", "", "filterId", "contactName", "", "contactPhone", "appointmentDate", "(IILjava/lang/String;Ljava/lang/String;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getFilterStatus", "", "Lcom/wuheng/smart/data/model/FilterStatusInfo;", "(ILkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getHotWaterStatus", "Lcom/wuheng/smart/data/model/HotWaterStatusResponse;", "getWaterPurifierStatus", "Lcom/wuheng/smart/data/model/WaterPurifierStatusResponse;", "setCirculationMode", "Lcom/wuheng/smart/data/model/SetCirculationModeResponse;", "mode", "Lcom/wuheng/smart/data/model/CirculationMode;", "duration", "(ILcom/wuheng/smart/data/model/CirculationMode;Ljava/lang/Integer;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "Companion", "app_debug"})
@javax.inject.Singleton()
public final class WaterRepositoryImpl extends com.wuheng.smart.data.repository.BaseRepository implements com.wuheng.smart.data.repository.WaterRepository {
    private final com.wuheng.smart.data.network.ApiService apiService = null;
    private final boolean useMock = false;
    @org.jetbrains.annotations.NotNull()
    public static final com.wuheng.smart.data.repository.WaterRepositoryImpl.Companion Companion = null;
    private static final int MAX_RETRY_COUNT = 3;
    private static final long RETRY_DELAY_MS = 1000L;
    
    @javax.inject.Inject()
    public WaterRepositoryImpl(@org.jetbrains.annotations.NotNull()
    com.wuheng.smart.data.network.ApiService apiService, boolean useMock) {
        super();
    }
    
    @org.jetbrains.annotations.Nullable()
    @java.lang.Override()
    public java.lang.Object getHotWaterStatus(int houseId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlinx.coroutines.flow.Flow<? extends com.wuheng.smart.data.network.ApiResult<com.wuheng.smart.data.model.HotWaterStatusResponse>>> continuation) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    @java.lang.Override()
    public java.lang.Object setCirculationMode(int houseId, @org.jetbrains.annotations.NotNull()
    com.wuheng.smart.data.model.CirculationMode mode, @org.jetbrains.annotations.Nullable()
    java.lang.Integer duration, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlinx.coroutines.flow.Flow<? extends com.wuheng.smart.data.network.ApiResult<com.wuheng.smart.data.model.SetCirculationModeResponse>>> continuation) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    @java.lang.Override()
    public java.lang.Object getWaterPurifierStatus(int houseId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlinx.coroutines.flow.Flow<? extends com.wuheng.smart.data.network.ApiResult<com.wuheng.smart.data.model.WaterPurifierStatusResponse>>> continuation) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    @java.lang.Override()
    public java.lang.Object getFilterStatus(int houseId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlinx.coroutines.flow.Flow<? extends com.wuheng.smart.data.network.ApiResult<? extends java.util.List<com.wuheng.smart.data.model.FilterStatusInfo>>>> continuation) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    @java.lang.Override()
    public java.lang.Object bookFilterReplace(int houseId, int filterId, @org.jetbrains.annotations.Nullable()
    java.lang.String contactName, @org.jetbrains.annotations.Nullable()
    java.lang.String contactPhone, @org.jetbrains.annotations.Nullable()
    java.lang.String appointmentDate, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlinx.coroutines.flow.Flow<? extends com.wuheng.smart.data.network.ApiResult<kotlin.Unit>>> continuation) {
        return null;
    }
    
    @kotlin.Metadata(mv = {1, 7, 1}, k = 1, d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\t\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0007"}, d2 = {"Lcom/wuheng/smart/data/repository/WaterRepositoryImpl$Companion;", "", "()V", "MAX_RETRY_COUNT", "", "RETRY_DELAY_MS", "", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
    }
}