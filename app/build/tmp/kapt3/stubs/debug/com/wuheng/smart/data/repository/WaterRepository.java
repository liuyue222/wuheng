package com.wuheng.smart.data.repository;

import com.wuheng.smart.data.model.*;
import com.wuheng.smart.data.network.ApiResult;
import com.wuheng.smart.data.network.ApiService;
import kotlinx.coroutines.flow.Flow;
import timber.log.Timber;
import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * 水系统数据仓库接口
 *
 * 提供水系统相关的所有数据操作方法，包括：
 * - 热水循环管理（获取状态、设置模式）
 * - 滤芯管理（获取状态、预约更换）
 */
@kotlin.Metadata(mv = {1, 7, 1}, k = 1, d1 = {"\u0000H\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\bf\u0018\u00002\u00020\u0001JQ\u0010\u0002\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\u00040\u00032\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\u00072\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\n2\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\n2\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\nH\u00a6@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\rJ+\u0010\u000e\u001a\u0014\u0012\u0010\u0012\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00100\u000f0\u00040\u00032\u0006\u0010\u0006\u001a\u00020\u0007H\u00a6@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0011J%\u0010\u0012\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00130\u00040\u00032\u0006\u0010\u0006\u001a\u00020\u0007H\u00a6@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0011J9\u0010\u0014\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00150\u00040\u00032\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\u0016\u001a\u00020\u00172\n\b\u0002\u0010\u0018\u001a\u0004\u0018\u00010\u0007H\u00a6@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0019\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006\u001a"}, d2 = {"Lcom/wuheng/smart/data/repository/WaterRepository;", "", "bookFilterReplace", "Lkotlinx/coroutines/flow/Flow;", "Lcom/wuheng/smart/data/network/ApiResult;", "", "houseId", "", "filterId", "contactName", "", "contactPhone", "appointmentDate", "(IILjava/lang/String;Ljava/lang/String;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getFilterStatus", "", "Lcom/wuheng/smart/data/model/FilterStatusInfo;", "(ILkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getHeaterStatus", "Lcom/wuheng/smart/data/model/HeaterStatus;", "setCirculationMode", "Lcom/wuheng/smart/data/model/SetCirculationModeResponse;", "mode", "Lcom/wuheng/smart/data/model/CirculationMode;", "duration", "(ILcom/wuheng/smart/data/model/CirculationMode;Ljava/lang/Integer;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_debug"})
public abstract interface WaterRepository {
    
    /**
     * 获取热水循环状态
     *
     * @param houseId 房屋ID
     * @return 热水循环状态
     */
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getHeaterStatus(int houseId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlinx.coroutines.flow.Flow<? extends com.wuheng.smart.data.network.ApiResult<com.wuheng.smart.data.model.HeaterStatus>>> continuation);
    
    /**
     * 设置循环模式
     *
     * @param houseId 房屋ID
     * @param mode 循环模式
     * @param duration 临时循环时长（分钟，仅TEMP模式需要）
     * @return 设置响应
     */
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object setCirculationMode(int houseId, @org.jetbrains.annotations.NotNull()
    com.wuheng.smart.data.model.CirculationMode mode, @org.jetbrains.annotations.Nullable()
    java.lang.Integer duration, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlinx.coroutines.flow.Flow<? extends com.wuheng.smart.data.network.ApiResult<com.wuheng.smart.data.model.SetCirculationModeResponse>>> continuation);
    
    /**
     * 获取滤芯状态列表
     *
     * @param houseId 房屋ID
     * @return 滤芯状态列表
     */
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getFilterStatus(int houseId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlinx.coroutines.flow.Flow<? extends com.wuheng.smart.data.network.ApiResult<? extends java.util.List<com.wuheng.smart.data.model.FilterStatusInfo>>>> continuation);
    
    /**
     * 预约滤芯更换
     *
     * @param houseId 房屋ID
     * @param filterId 滤芯ID
     * @param contactName 联系人姓名（可选）
     * @param contactPhone 联系人电话（可选）
     * @param appointmentDate 预约日期（可选）
     */
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object bookFilterReplace(int houseId, int filterId, @org.jetbrains.annotations.Nullable()
    java.lang.String contactName, @org.jetbrains.annotations.Nullable()
    java.lang.String contactPhone, @org.jetbrains.annotations.Nullable()
    java.lang.String appointmentDate, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlinx.coroutines.flow.Flow<? extends com.wuheng.smart.data.network.ApiResult<kotlin.Unit>>> continuation);
    
    /**
     * 水系统数据仓库接口
     *
     * 提供水系统相关的所有数据操作方法，包括：
     * - 热水循环管理（获取状态、设置模式）
     * - 滤芯管理（获取状态、预约更换）
     */
    @kotlin.Metadata(mv = {1, 7, 1}, k = 3)
    public final class DefaultImpls {
    }
}