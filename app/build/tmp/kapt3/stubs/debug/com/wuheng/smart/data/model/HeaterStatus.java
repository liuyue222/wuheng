package com.wuheng.smart.data.model;

import com.google.gson.annotations.SerializedName;

/**
 * 热水循环状态
 */
@kotlin.Metadata(mv = {1, 7, 1}, k = 1, d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0013\n\u0002\u0010\u000b\n\u0002\b\u0004\b\u0087\b\u0018\u00002\u00020\u0001B5\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0003\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\u0007\u0012\u0006\u0010\t\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\nJ\t\u0010\u0013\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u0014\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u0015\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u0016\u001a\u00020\u0007H\u00c6\u0003J\t\u0010\u0017\u001a\u00020\u0007H\u00c6\u0003J\t\u0010\u0018\u001a\u00020\u0003H\u00c6\u0003JE\u0010\u0019\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00032\b\b\u0002\u0010\u0006\u001a\u00020\u00072\b\b\u0002\u0010\b\u001a\u00020\u00072\b\b\u0002\u0010\t\u001a\u00020\u0003H\u00c6\u0001J\u0013\u0010\u001a\u001a\u00020\u001b2\b\u0010\u001c\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010\u001d\u001a\u00020\u0007H\u00d6\u0001J\t\u0010\u001e\u001a\u00020\u0003H\u00d6\u0001R\u0016\u0010\u0005\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0016\u0010\u0006\u001a\u00020\u00078\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u0016\u0010\u0002\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\fR\u0016\u0010\b\u001a\u00020\u00078\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u000eR\u0016\u0010\t\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\fR\u0016\u0010\u0004\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\f\u00a8\u0006\u001f"}, d2 = {"Lcom/wuheng/smart/data/model/HeaterStatus;", "", "currentTemp", "", "targetTemp", "circulationMode", "circulationStatus", "", "sterilizationEnable", "sterilizationTime", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;IILjava/lang/String;)V", "getCirculationMode", "()Ljava/lang/String;", "getCirculationStatus", "()I", "getCurrentTemp", "getSterilizationEnable", "getSterilizationTime", "getTargetTemp", "component1", "component2", "component3", "component4", "component5", "component6", "copy", "equals", "", "other", "hashCode", "toString", "app_debug"})
public final class HeaterStatus {
    @org.jetbrains.annotations.NotNull()
    @com.google.gson.annotations.SerializedName(value = "current_temp")
    private final java.lang.String currentTemp = null;
    @org.jetbrains.annotations.NotNull()
    @com.google.gson.annotations.SerializedName(value = "target_temp")
    private final java.lang.String targetTemp = null;
    @org.jetbrains.annotations.NotNull()
    @com.google.gson.annotations.SerializedName(value = "circulation_mode")
    private final java.lang.String circulationMode = null;
    @com.google.gson.annotations.SerializedName(value = "circulation_status")
    private final int circulationStatus = 0;
    @com.google.gson.annotations.SerializedName(value = "sterilization_enable")
    private final int sterilizationEnable = 0;
    @org.jetbrains.annotations.NotNull()
    @com.google.gson.annotations.SerializedName(value = "sterilization_time")
    private final java.lang.String sterilizationTime = null;
    
    /**
     * 热水循环状态
     */
    @org.jetbrains.annotations.NotNull()
    public final com.wuheng.smart.data.model.HeaterStatus copy(@org.jetbrains.annotations.NotNull()
    java.lang.String currentTemp, @org.jetbrains.annotations.NotNull()
    java.lang.String targetTemp, @org.jetbrains.annotations.NotNull()
    java.lang.String circulationMode, int circulationStatus, int sterilizationEnable, @org.jetbrains.annotations.NotNull()
    java.lang.String sterilizationTime) {
        return null;
    }
    
    /**
     * 热水循环状态
     */
    @java.lang.Override()
    public boolean equals(@org.jetbrains.annotations.Nullable()
    java.lang.Object other) {
        return false;
    }
    
    /**
     * 热水循环状态
     */
    @java.lang.Override()
    public int hashCode() {
        return 0;
    }
    
    /**
     * 热水循环状态
     */
    @org.jetbrains.annotations.NotNull()
    @java.lang.Override()
    public java.lang.String toString() {
        return null;
    }
    
    public HeaterStatus(@org.jetbrains.annotations.NotNull()
    java.lang.String currentTemp, @org.jetbrains.annotations.NotNull()
    java.lang.String targetTemp, @org.jetbrains.annotations.NotNull()
    java.lang.String circulationMode, int circulationStatus, int sterilizationEnable, @org.jetbrains.annotations.NotNull()
    java.lang.String sterilizationTime) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component1() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getCurrentTemp() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component2() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getTargetTemp() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component3() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getCirculationMode() {
        return null;
    }
    
    public final int component4() {
        return 0;
    }
    
    public final int getCirculationStatus() {
        return 0;
    }
    
    public final int component5() {
        return 0;
    }
    
    public final int getSterilizationEnable() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component6() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getSterilizationTime() {
        return null;
    }
}