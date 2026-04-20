package com.wuheng.smart.presentation.climate;

import java.lang.System;

/**
 * 冷暖舒适页面 UI State
 *
 * 包含全屋/楼层模式、温度湿度设定、楼层列表等所有UI需要的数据
 */
@kotlin.Metadata(mv = {1, 7, 1}, k = 1, d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0015\n\u0002\u0010\b\n\u0002\b\u0002\b\u0087\b\u0018\u00002\u00020\u0001BI\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0007\u0012\b\b\u0002\u0010\b\u001a\u00020\t\u0012\b\b\u0002\u0010\n\u001a\u00020\t\u0012\u000e\b\u0002\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\r0\f\u00a2\u0006\u0002\u0010\u000eJ\t\u0010\u0019\u001a\u00020\u0003H\u00c6\u0003J\u000b\u0010\u001a\u001a\u0004\u0018\u00010\u0005H\u00c6\u0003J\t\u0010\u001b\u001a\u00020\u0007H\u00c6\u0003J\t\u0010\u001c\u001a\u00020\tH\u00c6\u0003J\t\u0010\u001d\u001a\u00020\tH\u00c6\u0003J\u000f\u0010\u001e\u001a\b\u0012\u0004\u0012\u00020\r0\fH\u00c6\u0003JM\u0010\u001f\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00072\b\b\u0002\u0010\b\u001a\u00020\t2\b\b\u0002\u0010\n\u001a\u00020\t2\u000e\b\u0002\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\r0\fH\u00c6\u0001J\u0013\u0010 \u001a\u00020\u00032\b\u0010!\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010\"\u001a\u00020#H\u00d6\u0001J\t\u0010$\u001a\u00020\u0005H\u00d6\u0001R\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0017\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\r0\f\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R\u0011\u0010\n\u001a\u00020\t\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0002\u0010\u0015R\u0011\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0017R\u0011\u0010\b\u001a\u00020\t\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0014\u00a8\u0006%"}, d2 = {"Lcom/wuheng/smart/presentation/climate/ClimateUiState;", "", "isLoading", "", "errorMessage", "", "selectedTab", "Lcom/wuheng/smart/presentation/climate/ClimateTab;", "temperature", "", "humidity", "floors", "", "Lcom/wuheng/smart/presentation/climate/FloorItem;", "(ZLjava/lang/String;Lcom/wuheng/smart/presentation/climate/ClimateTab;FFLjava/util/List;)V", "getErrorMessage", "()Ljava/lang/String;", "getFloors", "()Ljava/util/List;", "getHumidity", "()F", "()Z", "getSelectedTab", "()Lcom/wuheng/smart/presentation/climate/ClimateTab;", "getTemperature", "component1", "component2", "component3", "component4", "component5", "component6", "copy", "equals", "other", "hashCode", "", "toString", "app_debug"})
public final class ClimateUiState {
    private final boolean isLoading = false;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String errorMessage = null;
    @org.jetbrains.annotations.NotNull()
    private final com.wuheng.smart.presentation.climate.ClimateTab selectedTab = null;
    private final float temperature = 0.0F;
    private final float humidity = 0.0F;
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<com.wuheng.smart.presentation.climate.FloorItem> floors = null;
    
    /**
     * 冷暖舒适页面 UI State
     *
     * 包含全屋/楼层模式、温度湿度设定、楼层列表等所有UI需要的数据
     */
    @org.jetbrains.annotations.NotNull()
    public final com.wuheng.smart.presentation.climate.ClimateUiState copy(boolean isLoading, @org.jetbrains.annotations.Nullable()
    java.lang.String errorMessage, @org.jetbrains.annotations.NotNull()
    com.wuheng.smart.presentation.climate.ClimateTab selectedTab, float temperature, float humidity, @org.jetbrains.annotations.NotNull()
    java.util.List<com.wuheng.smart.presentation.climate.FloorItem> floors) {
        return null;
    }
    
    /**
     * 冷暖舒适页面 UI State
     *
     * 包含全屋/楼层模式、温度湿度设定、楼层列表等所有UI需要的数据
     */
    @java.lang.Override()
    public boolean equals(@org.jetbrains.annotations.Nullable()
    java.lang.Object other) {
        return false;
    }
    
    /**
     * 冷暖舒适页面 UI State
     *
     * 包含全屋/楼层模式、温度湿度设定、楼层列表等所有UI需要的数据
     */
    @java.lang.Override()
    public int hashCode() {
        return 0;
    }
    
    /**
     * 冷暖舒适页面 UI State
     *
     * 包含全屋/楼层模式、温度湿度设定、楼层列表等所有UI需要的数据
     */
    @org.jetbrains.annotations.NotNull()
    @java.lang.Override()
    public java.lang.String toString() {
        return null;
    }
    
    public ClimateUiState() {
        super();
    }
    
    public ClimateUiState(boolean isLoading, @org.jetbrains.annotations.Nullable()
    java.lang.String errorMessage, @org.jetbrains.annotations.NotNull()
    com.wuheng.smart.presentation.climate.ClimateTab selectedTab, float temperature, float humidity, @org.jetbrains.annotations.NotNull()
    java.util.List<com.wuheng.smart.presentation.climate.FloorItem> floors) {
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
    public final com.wuheng.smart.presentation.climate.ClimateTab component3() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.wuheng.smart.presentation.climate.ClimateTab getSelectedTab() {
        return null;
    }
    
    public final float component4() {
        return 0.0F;
    }
    
    public final float getTemperature() {
        return 0.0F;
    }
    
    public final float component5() {
        return 0.0F;
    }
    
    public final float getHumidity() {
        return 0.0F;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.wuheng.smart.presentation.climate.FloorItem> component6() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.wuheng.smart.presentation.climate.FloorItem> getFloors() {
        return null;
    }
}