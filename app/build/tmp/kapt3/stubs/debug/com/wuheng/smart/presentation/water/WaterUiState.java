package com.wuheng.smart.presentation.water;

import java.lang.System;

/**
 * 水系统页面 UI State
 *
 * 包含生活热水循环、热力杀菌、滤芯状态等所有UI需要的数据
 */
@kotlin.Metadata(mv = {1, 7, 1}, k = 1, d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0019\b\u0087\b\u0018\u00002\u00020\u0001BS\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0007\u0012\b\b\u0002\u0010\b\u001a\u00020\t\u0012\b\b\u0002\u0010\n\u001a\u00020\t\u0012\b\b\u0002\u0010\u000b\u001a\u00020\u0005\u0012\u000e\b\u0002\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u000e0\r\u00a2\u0006\u0002\u0010\u000fJ\t\u0010\u001b\u001a\u00020\u0003H\u00c6\u0003J\u000b\u0010\u001c\u001a\u0004\u0018\u00010\u0005H\u00c6\u0003J\t\u0010\u001d\u001a\u00020\u0007H\u00c6\u0003J\t\u0010\u001e\u001a\u00020\tH\u00c6\u0003J\t\u0010\u001f\u001a\u00020\tH\u00c6\u0003J\t\u0010 \u001a\u00020\u0005H\u00c6\u0003J\u000f\u0010!\u001a\b\u0012\u0004\u0012\u00020\u000e0\rH\u00c6\u0003JW\u0010\"\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00072\b\b\u0002\u0010\b\u001a\u00020\t2\b\b\u0002\u0010\n\u001a\u00020\t2\b\b\u0002\u0010\u000b\u001a\u00020\u00052\u000e\b\u0002\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u000e0\rH\u00c6\u0001J\u0013\u0010#\u001a\u00020\u00032\b\u0010$\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010%\u001a\u00020\tH\u00d6\u0001J\t\u0010&\u001a\u00020\u0005H\u00d6\u0001R\u0011\u0010\b\u001a\u00020\t\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013R\u0017\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u000e0\r\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0015R\u0011\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0017R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0002\u0010\u0018R\u0011\u0010\u000b\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u0013R\u0011\u0010\n\u001a\u00020\t\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u0011\u00a8\u0006\'"}, d2 = {"Lcom/wuheng/smart/presentation/water/WaterUiState;", "", "isLoading", "", "errorMessage", "", "hotWaterMode", "Lcom/wuheng/smart/presentation/water/HotWaterMode;", "currentTemp", "", "temporaryDuration", "sterilizationSchedule", "filters", "", "Lcom/wuheng/smart/presentation/water/FilterItem;", "(ZLjava/lang/String;Lcom/wuheng/smart/presentation/water/HotWaterMode;IILjava/lang/String;Ljava/util/List;)V", "getCurrentTemp", "()I", "getErrorMessage", "()Ljava/lang/String;", "getFilters", "()Ljava/util/List;", "getHotWaterMode", "()Lcom/wuheng/smart/presentation/water/HotWaterMode;", "()Z", "getSterilizationSchedule", "getTemporaryDuration", "component1", "component2", "component3", "component4", "component5", "component6", "component7", "copy", "equals", "other", "hashCode", "toString", "app_debug"})
public final class WaterUiState {
    private final boolean isLoading = false;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String errorMessage = null;
    @org.jetbrains.annotations.NotNull()
    private final com.wuheng.smart.presentation.water.HotWaterMode hotWaterMode = null;
    private final int currentTemp = 0;
    private final int temporaryDuration = 0;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String sterilizationSchedule = null;
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<com.wuheng.smart.presentation.water.FilterItem> filters = null;
    
    /**
     * 水系统页面 UI State
     *
     * 包含生活热水循环、热力杀菌、滤芯状态等所有UI需要的数据
     */
    @org.jetbrains.annotations.NotNull()
    public final com.wuheng.smart.presentation.water.WaterUiState copy(boolean isLoading, @org.jetbrains.annotations.Nullable()
    java.lang.String errorMessage, @org.jetbrains.annotations.NotNull()
    com.wuheng.smart.presentation.water.HotWaterMode hotWaterMode, int currentTemp, int temporaryDuration, @org.jetbrains.annotations.NotNull()
    java.lang.String sterilizationSchedule, @org.jetbrains.annotations.NotNull()
    java.util.List<com.wuheng.smart.presentation.water.FilterItem> filters) {
        return null;
    }
    
    /**
     * 水系统页面 UI State
     *
     * 包含生活热水循环、热力杀菌、滤芯状态等所有UI需要的数据
     */
    @java.lang.Override()
    public boolean equals(@org.jetbrains.annotations.Nullable()
    java.lang.Object other) {
        return false;
    }
    
    /**
     * 水系统页面 UI State
     *
     * 包含生活热水循环、热力杀菌、滤芯状态等所有UI需要的数据
     */
    @java.lang.Override()
    public int hashCode() {
        return 0;
    }
    
    /**
     * 水系统页面 UI State
     *
     * 包含生活热水循环、热力杀菌、滤芯状态等所有UI需要的数据
     */
    @org.jetbrains.annotations.NotNull()
    @java.lang.Override()
    public java.lang.String toString() {
        return null;
    }
    
    public WaterUiState() {
        super();
    }
    
    public WaterUiState(boolean isLoading, @org.jetbrains.annotations.Nullable()
    java.lang.String errorMessage, @org.jetbrains.annotations.NotNull()
    com.wuheng.smart.presentation.water.HotWaterMode hotWaterMode, int currentTemp, int temporaryDuration, @org.jetbrains.annotations.NotNull()
    java.lang.String sterilizationSchedule, @org.jetbrains.annotations.NotNull()
    java.util.List<com.wuheng.smart.presentation.water.FilterItem> filters) {
        super();
    }
    
    public final boolean component1() {
        return false;
    }
    
    public final boolean isLoading() {
        return false;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component2() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getErrorMessage() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.wuheng.smart.presentation.water.HotWaterMode component3() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.wuheng.smart.presentation.water.HotWaterMode getHotWaterMode() {
        return null;
    }
    
    public final int component4() {
        return 0;
    }
    
    public final int getCurrentTemp() {
        return 0;
    }
    
    public final int component5() {
        return 0;
    }
    
    public final int getTemporaryDuration() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component6() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getSterilizationSchedule() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.wuheng.smart.presentation.water.FilterItem> component7() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.wuheng.smart.presentation.water.FilterItem> getFilters() {
        return null;
    }
}