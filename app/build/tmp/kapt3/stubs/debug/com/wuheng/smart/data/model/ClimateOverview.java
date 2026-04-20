package com.wuheng.smart.data.model;

import com.google.gson.annotations.SerializedName;

@kotlin.Metadata(mv = {1, 7, 1}, k = 1, d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0006\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\b\n\u0002\b\"\n\u0002\u0010\u000e\n\u0000\b\u0087\b\u0018\u00002\u00020\u0001Ba\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0005\u001a\u00020\u0006\u0012\b\b\u0002\u0010\u0007\u001a\u00020\b\u0012\b\b\u0002\u0010\t\u001a\u00020\n\u0012\b\b\u0002\u0010\u000b\u001a\u00020\n\u0012\b\b\u0002\u0010\f\u001a\u00020\n\u0012\b\b\u0002\u0010\r\u001a\u00020\n\u0012\n\b\u0002\u0010\u000e\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\u0002\u0010\u000fJ\t\u0010\u001e\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u001f\u001a\u00020\u0003H\u00c6\u0003J\t\u0010 \u001a\u00020\u0006H\u00c6\u0003J\t\u0010!\u001a\u00020\bH\u00c6\u0003J\t\u0010\"\u001a\u00020\nH\u00c6\u0003J\t\u0010#\u001a\u00020\nH\u00c6\u0003J\t\u0010$\u001a\u00020\nH\u00c6\u0003J\t\u0010%\u001a\u00020\nH\u00c6\u0003J\u0010\u0010&\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003\u00a2\u0006\u0002\u0010\u0019Jj\u0010\'\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00062\b\b\u0002\u0010\u0007\u001a\u00020\b2\b\b\u0002\u0010\t\u001a\u00020\n2\b\b\u0002\u0010\u000b\u001a\u00020\n2\b\b\u0002\u0010\f\u001a\u00020\n2\b\b\u0002\u0010\r\u001a\u00020\n2\n\b\u0002\u0010\u000e\u001a\u0004\u0018\u00010\u0003H\u00c6\u0001\u00a2\u0006\u0002\u0010(J\u0013\u0010)\u001a\u00020\b2\b\u0010*\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010+\u001a\u00020\nH\u00d6\u0001J\t\u0010,\u001a\u00020-H\u00d6\u0001R\u0016\u0010\r\u001a\u00020\n8\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u0016\u0010\u0005\u001a\u00020\u00068\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013R\u0016\u0010\u0002\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0015R\u0016\u0010\t\u001a\u00020\n8\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0011R\u0016\u0010\u0007\u001a\u00020\b8\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\u0017R\u001a\u0010\u000e\u001a\u0004\u0018\u00010\u00038\u0006X\u0087\u0004\u00a2\u0006\n\n\u0002\u0010\u001a\u001a\u0004\b\u0018\u0010\u0019R\u0016\u0010\f\u001a\u00020\n8\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u0011R\u0016\u0010\u0004\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u0015R\u0016\u0010\u000b\u001a\u00020\n8\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\u0011\u00a8\u0006."}, d2 = {"Lcom/wuheng/smart/data/model/ClimateOverview;", "", "currentTemperature", "", "targetTemperature", "currentMode", "Lcom/wuheng/smart/data/model/ClimateMode;", "isRunning", "", "floorCount", "", "zoneCount", "runningZoneCount", "averageHumidity", "outdoorTemperature", "(DDLcom/wuheng/smart/data/model/ClimateMode;ZIIIILjava/lang/Double;)V", "getAverageHumidity", "()I", "getCurrentMode", "()Lcom/wuheng/smart/data/model/ClimateMode;", "getCurrentTemperature", "()D", "getFloorCount", "()Z", "getOutdoorTemperature", "()Ljava/lang/Double;", "Ljava/lang/Double;", "getRunningZoneCount", "getTargetTemperature", "getZoneCount", "component1", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "(DDLcom/wuheng/smart/data/model/ClimateMode;ZIIIILjava/lang/Double;)Lcom/wuheng/smart/data/model/ClimateOverview;", "equals", "other", "hashCode", "toString", "", "app_debug"})
public final class ClimateOverview {
    @com.google.gson.annotations.SerializedName(value = "currentTemperature")
    private final double currentTemperature = 0.0;
    @com.google.gson.annotations.SerializedName(value = "targetTemperature")
    private final double targetTemperature = 0.0;
    @org.jetbrains.annotations.NotNull()
    @com.google.gson.annotations.SerializedName(value = "currentMode")
    private final com.wuheng.smart.data.model.ClimateMode currentMode = null;
    @com.google.gson.annotations.SerializedName(value = "isRunning")
    private final boolean isRunning = false;
    @com.google.gson.annotations.SerializedName(value = "floorCount")
    private final int floorCount = 0;
    @com.google.gson.annotations.SerializedName(value = "zoneCount")
    private final int zoneCount = 0;
    @com.google.gson.annotations.SerializedName(value = "runningZoneCount")
    private final int runningZoneCount = 0;
    @com.google.gson.annotations.SerializedName(value = "averageHumidity")
    private final int averageHumidity = 0;
    @org.jetbrains.annotations.Nullable()
    @com.google.gson.annotations.SerializedName(value = "outdoorTemperature")
    private final java.lang.Double outdoorTemperature = null;
    
    @org.jetbrains.annotations.NotNull()
    public final com.wuheng.smart.data.model.ClimateOverview copy(double currentTemperature, double targetTemperature, @org.jetbrains.annotations.NotNull()
    com.wuheng.smart.data.model.ClimateMode currentMode, boolean isRunning, int floorCount, int zoneCount, int runningZoneCount, int averageHumidity, @org.jetbrains.annotations.Nullable()
    java.lang.Double outdoorTemperature) {
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
    
    public ClimateOverview() {
        super();
    }
    
    public ClimateOverview(double currentTemperature, double targetTemperature, @org.jetbrains.annotations.NotNull()
    com.wuheng.smart.data.model.ClimateMode currentMode, boolean isRunning, int floorCount, int zoneCount, int runningZoneCount, int averageHumidity, @org.jetbrains.annotations.Nullable()
    java.lang.Double outdoorTemperature) {
        super();
    }
    
    public final double component1() {
        return 0.0;
    }
    
    public final double getCurrentTemperature() {
        return 0.0;
    }
    
    public final double component2() {
        return 0.0;
    }
    
    public final double getTargetTemperature() {
        return 0.0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.wuheng.smart.data.model.ClimateMode component3() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.wuheng.smart.data.model.ClimateMode getCurrentMode() {
        return null;
    }
    
    public final boolean component4() {
        return false;
    }
    
    public final boolean isRunning() {
        return false;
    }
    
    public final int component5() {
        return 0;
    }
    
    public final int getFloorCount() {
        return 0;
    }
    
    public final int component6() {
        return 0;
    }
    
    public final int getZoneCount() {
        return 0;
    }
    
    public final int component7() {
        return 0;
    }
    
    public final int getRunningZoneCount() {
        return 0;
    }
    
    public final int component8() {
        return 0;
    }
    
    public final int getAverageHumidity() {
        return 0;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Double component9() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Double getOutdoorTemperature() {
        return null;
    }
}