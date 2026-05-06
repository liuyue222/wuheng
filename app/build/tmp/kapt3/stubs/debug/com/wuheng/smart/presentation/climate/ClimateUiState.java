package com.wuheng.smart.presentation.climate;

import java.lang.System;

/**
 * 冷暖舒适页面 UI State
 *
 * 包含全屋/楼层模式、温度湿度设定、楼层列表等所有UI需要的数据
 */
@kotlin.Metadata(mv = {1, 7, 1}, k = 1, d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u001c\n\u0002\u0010\b\n\u0002\b\u0002\b\u0087\b\u0018\u00002\u00020\u0001Bo\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0007\u0012\b\b\u0002\u0010\b\u001a\u00020\t\u0012\b\b\u0002\u0010\n\u001a\u00020\t\u0012\u000e\b\u0002\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\r0\f\u0012\n\b\u0002\u0010\u000e\u001a\u0004\u0018\u00010\u0005\u0012\u000e\b\u0002\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00100\f\u0012\b\b\u0002\u0010\u0011\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0012J\t\u0010 \u001a\u00020\u0003H\u00c6\u0003J\u000b\u0010!\u001a\u0004\u0018\u00010\u0005H\u00c6\u0003J\t\u0010\"\u001a\u00020\u0007H\u00c6\u0003J\t\u0010#\u001a\u00020\tH\u00c6\u0003J\t\u0010$\u001a\u00020\tH\u00c6\u0003J\u000f\u0010%\u001a\b\u0012\u0004\u0012\u00020\r0\fH\u00c6\u0003J\u000b\u0010&\u001a\u0004\u0018\u00010\u0005H\u00c6\u0003J\u000f\u0010\'\u001a\b\u0012\u0004\u0012\u00020\u00100\fH\u00c6\u0003J\t\u0010(\u001a\u00020\u0003H\u00c6\u0003Js\u0010)\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00072\b\b\u0002\u0010\b\u001a\u00020\t2\b\b\u0002\u0010\n\u001a\u00020\t2\u000e\b\u0002\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\r0\f2\n\b\u0002\u0010\u000e\u001a\u0004\u0018\u00010\u00052\u000e\b\u0002\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00100\f2\b\b\u0002\u0010\u0011\u001a\u00020\u0003H\u00c6\u0001J\u0013\u0010*\u001a\u00020\u00032\b\u0010+\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010,\u001a\u00020-H\u00d6\u0001J\t\u0010.\u001a\u00020\u0005H\u00d6\u0001R\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014R\u0017\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\r0\f\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0016R\u0011\u0010\n\u001a\u00020\t\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0018R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0002\u0010\u0019R\u0017\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00100\f\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u0016R\u0011\u0010\u0011\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u0019R\u0013\u0010\u000e\u001a\u0004\u0018\u00010\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u0014R\u0011\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\u001eR\u0011\u0010\b\u001a\u00020\t\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001f\u0010\u0018\u00a8\u0006/"}, d2 = {"Lcom/wuheng/smart/presentation/climate/ClimateUiState;", "", "isLoading", "", "errorMessage", "", "selectedTab", "Lcom/wuheng/smart/presentation/climate/ClimateTab;", "temperature", "", "humidity", "floors", "", "Lcom/wuheng/smart/presentation/climate/FloorItem;", "selectedFloorId", "rooms", "Lcom/wuheng/smart/presentation/climate/RoomUiItem;", "roomsLoading", "(ZLjava/lang/String;Lcom/wuheng/smart/presentation/climate/ClimateTab;FFLjava/util/List;Ljava/lang/String;Ljava/util/List;Z)V", "getErrorMessage", "()Ljava/lang/String;", "getFloors", "()Ljava/util/List;", "getHumidity", "()F", "()Z", "getRooms", "getRoomsLoading", "getSelectedFloorId", "getSelectedTab", "()Lcom/wuheng/smart/presentation/climate/ClimateTab;", "getTemperature", "component1", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "equals", "other", "hashCode", "", "toString", "app_debug"})
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
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String selectedFloorId = null;
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<com.wuheng.smart.presentation.climate.RoomUiItem> rooms = null;
    private final boolean roomsLoading = false;
    
    /**
     * 冷暖舒适页面 UI State
     *
     * 包含全屋/楼层模式、温度湿度设定、楼层列表等所有UI需要的数据
     */
    @org.jetbrains.annotations.NotNull()
    public final com.wuheng.smart.presentation.climate.ClimateUiState copy(boolean isLoading, @org.jetbrains.annotations.Nullable()
    java.lang.String errorMessage, @org.jetbrains.annotations.NotNull()
    com.wuheng.smart.presentation.climate.ClimateTab selectedTab, float temperature, float humidity, @org.jetbrains.annotations.NotNull()
    java.util.List<com.wuheng.smart.presentation.climate.FloorItem> floors, @org.jetbrains.annotations.Nullable()
    java.lang.String selectedFloorId, @org.jetbrains.annotations.NotNull()
    java.util.List<com.wuheng.smart.presentation.climate.RoomUiItem> rooms, boolean roomsLoading) {
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
    java.util.List<com.wuheng.smart.presentation.climate.FloorItem> floors, @org.jetbrains.annotations.Nullable()
    java.lang.String selectedFloorId, @org.jetbrains.annotations.NotNull()
    java.util.List<com.wuheng.smart.presentation.climate.RoomUiItem> rooms, boolean roomsLoading) {
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
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component7() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getSelectedFloorId() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.wuheng.smart.presentation.climate.RoomUiItem> component8() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.wuheng.smart.presentation.climate.RoomUiItem> getRooms() {
        return null;
    }
    
    public final boolean component9() {
        return false;
    }
    
    public final boolean getRoomsLoading() {
        return false;
    }
}