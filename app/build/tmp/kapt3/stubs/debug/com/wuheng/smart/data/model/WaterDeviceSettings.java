package com.wuheng.smart.data.model;

import com.google.gson.annotations.SerializedName;

@kotlin.Metadata(mv = {1, 7, 1}, k = 1, d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0006\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0016\n\u0002\u0010\b\n\u0002\b\u0002\b\u0087\b\u0018\u00002\u00020\u0001B=\u0012\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0005\u0012\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u0007\u0012\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\u0007\u0012\b\b\u0002\u0010\t\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\nJ\u0010\u0010\u0014\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003\u00a2\u0006\u0002\u0010\u000eJ\t\u0010\u0015\u001a\u00020\u0005H\u00c6\u0003J\u000b\u0010\u0016\u001a\u0004\u0018\u00010\u0007H\u00c6\u0003J\u000b\u0010\u0017\u001a\u0004\u0018\u00010\u0007H\u00c6\u0003J\t\u0010\u0018\u001a\u00020\u0005H\u00c6\u0003JF\u0010\u0019\u001a\u00020\u00002\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u00072\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\u00072\b\b\u0002\u0010\t\u001a\u00020\u0005H\u00c6\u0001\u00a2\u0006\u0002\u0010\u001aJ\u0013\u0010\u001b\u001a\u00020\u00052\b\u0010\u001c\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010\u001d\u001a\u00020\u001eH\u00d6\u0001J\t\u0010\u001f\u001a\u00020\u0007H\u00d6\u0001R\u0016\u0010\t\u001a\u00020\u00058\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u001a\u0010\u0002\u001a\u0004\u0018\u00010\u00038\u0006X\u0087\u0004\u00a2\u0006\n\n\u0002\u0010\u000f\u001a\u0004\b\r\u0010\u000eR\u0016\u0010\u0004\u001a\u00020\u00058\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\fR\u0018\u0010\b\u001a\u0004\u0018\u00010\u00078\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R\u0018\u0010\u0006\u001a\u0004\u0018\u00010\u00078\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0012\u00a8\u0006 "}, d2 = {"Lcom/wuheng/smart/data/model/WaterDeviceSettings;", "", "targetTemperature", "", "timerEnabled", "", "timerStartTime", "", "timerEndTime", "ecoMode", "(Ljava/lang/Double;ZLjava/lang/String;Ljava/lang/String;Z)V", "getEcoMode", "()Z", "getTargetTemperature", "()Ljava/lang/Double;", "Ljava/lang/Double;", "getTimerEnabled", "getTimerEndTime", "()Ljava/lang/String;", "getTimerStartTime", "component1", "component2", "component3", "component4", "component5", "copy", "(Ljava/lang/Double;ZLjava/lang/String;Ljava/lang/String;Z)Lcom/wuheng/smart/data/model/WaterDeviceSettings;", "equals", "other", "hashCode", "", "toString", "app_debug"})
public final class WaterDeviceSettings {
    @org.jetbrains.annotations.Nullable()
    @com.google.gson.annotations.SerializedName(value = "targetTemperature")
    private final java.lang.Double targetTemperature = null;
    @com.google.gson.annotations.SerializedName(value = "timerEnabled")
    private final boolean timerEnabled = false;
    @org.jetbrains.annotations.Nullable()
    @com.google.gson.annotations.SerializedName(value = "timerStartTime")
    private final java.lang.String timerStartTime = null;
    @org.jetbrains.annotations.Nullable()
    @com.google.gson.annotations.SerializedName(value = "timerEndTime")
    private final java.lang.String timerEndTime = null;
    @com.google.gson.annotations.SerializedName(value = "ecoMode")
    private final boolean ecoMode = false;
    
    @org.jetbrains.annotations.NotNull()
    public final com.wuheng.smart.data.model.WaterDeviceSettings copy(@org.jetbrains.annotations.Nullable()
    java.lang.Double targetTemperature, boolean timerEnabled, @org.jetbrains.annotations.Nullable()
    java.lang.String timerStartTime, @org.jetbrains.annotations.Nullable()
    java.lang.String timerEndTime, boolean ecoMode) {
        return null;
    }
    
    @java.lang.Override()
    public boolean equals(@org.jetbrains.annotations.Nullable()
    java.lang.Object other) {
        return false;
    }
    
    @java.lang.Override()
    public int hashCode() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull()
    @java.lang.Override()
    public java.lang.String toString() {
        return null;
    }
    
    public WaterDeviceSettings() {
        super();
    }
    
    public WaterDeviceSettings(@org.jetbrains.annotations.Nullable()
    java.lang.Double targetTemperature, boolean timerEnabled, @org.jetbrains.annotations.Nullable()
    java.lang.String timerStartTime, @org.jetbrains.annotations.Nullable()
    java.lang.String timerEndTime, boolean ecoMode) {
        super();
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Double component1() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Double getTargetTemperature() {
        return null;
    }
    
    public final boolean component2() {
        return false;
    }
    
    public final boolean getTimerEnabled() {
        return false;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component3() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getTimerStartTime() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component4() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getTimerEndTime() {
        return null;
    }
    
    public final boolean component5() {
        return false;
    }
    
    public final boolean getEcoMode() {
        return false;
    }
}