package com.wuheng.smart.data.model;

import com.google.gson.annotations.SerializedName;

@kotlin.Metadata(mv = {1, 7, 1}, k = 1, d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0006\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u001c\n\u0002\u0010\b\n\u0002\b\u0002\b\u0087\b\u0018\u00002\u00020\u0001B[\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0005\u001a\u00020\u0006\u0012\b\b\u0002\u0010\u0007\u001a\u00020\b\u0012\b\b\u0002\u0010\t\u001a\u00020\n\u0012\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\f\u0012\n\b\u0002\u0010\r\u001a\u0004\u0018\u00010\f\u0012\n\b\u0002\u0010\u000e\u001a\u0004\u0018\u00010\u000f\u00a2\u0006\u0002\u0010\u0010J\t\u0010\u001f\u001a\u00020\u0003H\u00c6\u0003J\t\u0010 \u001a\u00020\u0003H\u00c6\u0003J\t\u0010!\u001a\u00020\u0006H\u00c6\u0003J\t\u0010\"\u001a\u00020\bH\u00c6\u0003J\t\u0010#\u001a\u00020\nH\u00c6\u0003J\u0010\u0010$\u001a\u0004\u0018\u00010\fH\u00c6\u0003\u00a2\u0006\u0002\u0010\u0012J\u0010\u0010%\u001a\u0004\u0018\u00010\fH\u00c6\u0003\u00a2\u0006\u0002\u0010\u0012J\u000b\u0010&\u001a\u0004\u0018\u00010\u000fH\u00c6\u0003Jd\u0010\'\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00062\b\b\u0002\u0010\u0007\u001a\u00020\b2\b\b\u0002\u0010\t\u001a\u00020\n2\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\f2\n\b\u0002\u0010\r\u001a\u0004\u0018\u00010\f2\n\b\u0002\u0010\u000e\u001a\u0004\u0018\u00010\u000fH\u00c6\u0001\u00a2\u0006\u0002\u0010(J\u0013\u0010)\u001a\u00020\n2\b\u0010*\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010+\u001a\u00020,H\u00d6\u0001J\t\u0010-\u001a\u00020\u0003H\u00d6\u0001R\u001a\u0010\u000b\u001a\u0004\u0018\u00010\f8\u0006X\u0087\u0004\u00a2\u0006\n\n\u0002\u0010\u0013\u001a\u0004\b\u0011\u0010\u0012R\u0016\u0010\u0002\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0015R\u0016\u0010\t\u001a\u00020\n8\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\t\u0010\u0016R\u0016\u0010\u0004\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0015R\u0018\u0010\u000e\u001a\u0004\u0018\u00010\u000f8\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0019R\u0016\u0010\u0007\u001a\u00020\b8\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u001bR\u001a\u0010\r\u001a\u0004\u0018\u00010\f8\u0006X\u0087\u0004\u00a2\u0006\n\n\u0002\u0010\u0013\u001a\u0004\b\u001c\u0010\u0012R\u0016\u0010\u0005\u001a\u00020\u00068\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\u001e\u00a8\u0006."}, d2 = {"Lcom/wuheng/smart/data/model/WaterDevice;", "", "id", "", "name", "type", "Lcom/wuheng/smart/data/model/WaterDeviceType;", "status", "Lcom/wuheng/smart/data/model/WaterDeviceStatus;", "isRunning", "", "currentTemperature", "", "targetTemperature", "settings", "Lcom/wuheng/smart/data/model/WaterDeviceSettings;", "(Ljava/lang/String;Ljava/lang/String;Lcom/wuheng/smart/data/model/WaterDeviceType;Lcom/wuheng/smart/data/model/WaterDeviceStatus;ZLjava/lang/Double;Ljava/lang/Double;Lcom/wuheng/smart/data/model/WaterDeviceSettings;)V", "getCurrentTemperature", "()Ljava/lang/Double;", "Ljava/lang/Double;", "getId", "()Ljava/lang/String;", "()Z", "getName", "getSettings", "()Lcom/wuheng/smart/data/model/WaterDeviceSettings;", "getStatus", "()Lcom/wuheng/smart/data/model/WaterDeviceStatus;", "getTargetTemperature", "getType", "()Lcom/wuheng/smart/data/model/WaterDeviceType;", "component1", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "copy", "(Ljava/lang/String;Ljava/lang/String;Lcom/wuheng/smart/data/model/WaterDeviceType;Lcom/wuheng/smart/data/model/WaterDeviceStatus;ZLjava/lang/Double;Ljava/lang/Double;Lcom/wuheng/smart/data/model/WaterDeviceSettings;)Lcom/wuheng/smart/data/model/WaterDevice;", "equals", "other", "hashCode", "", "toString", "app_debug"})
public final class WaterDevice {
    @org.jetbrains.annotations.NotNull()
    @com.google.gson.annotations.SerializedName(value = "id")
    private final java.lang.String id = null;
    @org.jetbrains.annotations.NotNull()
    @com.google.gson.annotations.SerializedName(value = "name")
    private final java.lang.String name = null;
    @org.jetbrains.annotations.NotNull()
    @com.google.gson.annotations.SerializedName(value = "type")
    private final com.wuheng.smart.data.model.WaterDeviceType type = null;
    @org.jetbrains.annotations.NotNull()
    @com.google.gson.annotations.SerializedName(value = "status")
    private final com.wuheng.smart.data.model.WaterDeviceStatus status = null;
    @com.google.gson.annotations.SerializedName(value = "isRunning")
    private final boolean isRunning = false;
    @org.jetbrains.annotations.Nullable()
    @com.google.gson.annotations.SerializedName(value = "currentTemperature")
    private final java.lang.Double currentTemperature = null;
    @org.jetbrains.annotations.Nullable()
    @com.google.gson.annotations.SerializedName(value = "targetTemperature")
    private final java.lang.Double targetTemperature = null;
    @org.jetbrains.annotations.Nullable()
    @com.google.gson.annotations.SerializedName(value = "settings")
    private final com.wuheng.smart.data.model.WaterDeviceSettings settings = null;
    
    @org.jetbrains.annotations.NotNull()
    public final com.wuheng.smart.data.model.WaterDevice copy(@org.jetbrains.annotations.NotNull()
    java.lang.String id, @org.jetbrains.annotations.NotNull()
    java.lang.String name, @org.jetbrains.annotations.NotNull()
    com.wuheng.smart.data.model.WaterDeviceType type, @org.jetbrains.annotations.NotNull()
    com.wuheng.smart.data.model.WaterDeviceStatus status, boolean isRunning, @org.jetbrains.annotations.Nullable()
    java.lang.Double currentTemperature, @org.jetbrains.annotations.Nullable()
    java.lang.Double targetTemperature, @org.jetbrains.annotations.Nullable()
    com.wuheng.smart.data.model.WaterDeviceSettings settings) {
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
    
    public WaterDevice() {
        super();
    }
    
    public WaterDevice(@org.jetbrains.annotations.NotNull()
    java.lang.String id, @org.jetbrains.annotations.NotNull()
    java.lang.String name, @org.jetbrains.annotations.NotNull()
    com.wuheng.smart.data.model.WaterDeviceType type, @org.jetbrains.annotations.NotNull()
    com.wuheng.smart.data.model.WaterDeviceStatus status, boolean isRunning, @org.jetbrains.annotations.Nullable()
    java.lang.Double currentTemperature, @org.jetbrains.annotations.Nullable()
    java.lang.Double targetTemperature, @org.jetbrains.annotations.Nullable()
    com.wuheng.smart.data.model.WaterDeviceSettings settings) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component1() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getId() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component2() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getName() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.wuheng.smart.data.model.WaterDeviceType component3() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.wuheng.smart.data.model.WaterDeviceType getType() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.wuheng.smart.data.model.WaterDeviceStatus component4() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.wuheng.smart.data.model.WaterDeviceStatus getStatus() {
        return null;
    }
    
    public final boolean component5() {
        return false;
    }
    
    public final boolean isRunning() {
        return false;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Double component6() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Double getCurrentTemperature() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Double component7() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Double getTargetTemperature() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.wuheng.smart.data.model.WaterDeviceSettings component8() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.wuheng.smart.data.model.WaterDeviceSettings getSettings() {
        return null;
    }
}