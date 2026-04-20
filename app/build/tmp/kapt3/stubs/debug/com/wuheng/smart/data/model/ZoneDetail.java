package com.wuheng.smart.data.model;

import com.google.gson.annotations.SerializedName;

/**
 * 区域详情（增强版）
 *
 * UI组件映射：
 * - 房间Chip选择器: name, floorName, isOnline
 * - 温度/湿度设定卡片: currentTemperature, targetTemperature, humidity, targetHumidity
 * - 风速选择器: fanSpeed
 * - 辐射模式开关: radiationConfig
 */
@kotlin.Metadata(mv = {1, 7, 1}, k = 1, d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0010\u0006\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b,\b\u0087\b\u0018\u00002\u00020\u0001B\u009f\u0001\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0005\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0007\u001a\u00020\b\u0012\b\b\u0002\u0010\t\u001a\u00020\b\u0012\b\b\u0002\u0010\n\u001a\u00020\u000b\u0012\b\b\u0002\u0010\f\u001a\u00020\u000b\u0012\b\b\u0002\u0010\r\u001a\u00020\u000e\u0012\b\b\u0002\u0010\u000f\u001a\u00020\u0010\u0012\b\b\u0002\u0010\u0011\u001a\u00020\u0010\u0012\b\b\u0002\u0010\u0012\u001a\u00020\u0013\u0012\n\b\u0002\u0010\u0014\u001a\u0004\u0018\u00010\u0015\u0012\b\b\u0002\u0010\u0016\u001a\u00020\u0010\u0012\n\b\u0002\u0010\u0017\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\u0002\u0010\u0018J\t\u0010-\u001a\u00020\u0003H\u00c6\u0003J\t\u0010.\u001a\u00020\u0010H\u00c6\u0003J\t\u0010/\u001a\u00020\u0010H\u00c6\u0003J\t\u00100\u001a\u00020\u0013H\u00c6\u0003J\u000b\u00101\u001a\u0004\u0018\u00010\u0015H\u00c6\u0003J\t\u00102\u001a\u00020\u0010H\u00c6\u0003J\u000b\u00103\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J\t\u00104\u001a\u00020\u0003H\u00c6\u0003J\t\u00105\u001a\u00020\u0003H\u00c6\u0003J\t\u00106\u001a\u00020\u0003H\u00c6\u0003J\t\u00107\u001a\u00020\bH\u00c6\u0003J\t\u00108\u001a\u00020\bH\u00c6\u0003J\t\u00109\u001a\u00020\u000bH\u00c6\u0003J\t\u0010:\u001a\u00020\u000bH\u00c6\u0003J\t\u0010;\u001a\u00020\u000eH\u00c6\u0003J\u00a3\u0001\u0010<\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00032\b\b\u0002\u0010\u0006\u001a\u00020\u00032\b\b\u0002\u0010\u0007\u001a\u00020\b2\b\b\u0002\u0010\t\u001a\u00020\b2\b\b\u0002\u0010\n\u001a\u00020\u000b2\b\b\u0002\u0010\f\u001a\u00020\u000b2\b\b\u0002\u0010\r\u001a\u00020\u000e2\b\b\u0002\u0010\u000f\u001a\u00020\u00102\b\b\u0002\u0010\u0011\u001a\u00020\u00102\b\b\u0002\u0010\u0012\u001a\u00020\u00132\n\b\u0002\u0010\u0014\u001a\u0004\u0018\u00010\u00152\b\b\u0002\u0010\u0016\u001a\u00020\u00102\n\b\u0002\u0010\u0017\u001a\u0004\u0018\u00010\u0003H\u00c6\u0001J\u0013\u0010=\u001a\u00020\u00102\b\u0010>\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010?\u001a\u00020\u000bH\u00d6\u0001J\t\u0010@\u001a\u00020\u0003H\u00d6\u0001R\u0016\u0010\u0007\u001a\u00020\b8\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u001aR\u0016\u0010\u0012\u001a\u00020\u00138\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u001cR\u0016\u0010\u0005\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\u001eR\u0016\u0010\u0006\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001f\u0010\u001eR\u0016\u0010\n\u001a\u00020\u000b8\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b \u0010!R\u0016\u0010\u0002\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\"\u0010\u001eR\u0016\u0010\u0011\u001a\u00020\u00108\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010#R\u0016\u0010\u000f\u001a\u00020\u00108\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010#R\u0016\u0010\r\u001a\u00020\u000e8\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b$\u0010%R\u0016\u0010\u0004\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b&\u0010\u001eR\u0018\u0010\u0014\u001a\u0004\u0018\u00010\u00158\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\'\u0010(R\u0016\u0010\u0016\u001a\u00020\u00108\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b)\u0010#R\u0018\u0010\u0017\u001a\u0004\u0018\u00010\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b*\u0010\u001eR\u0016\u0010\f\u001a\u00020\u000b8\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b+\u0010!R\u0016\u0010\t\u001a\u00020\b8\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b,\u0010\u001a\u00a8\u0006A"}, d2 = {"Lcom/wuheng/smart/data/model/ZoneDetail;", "", "id", "", "name", "floorId", "floorName", "currentTemperature", "", "targetTemperature", "humidity", "", "targetHumidity", "mode", "Lcom/wuheng/smart/data/model/ClimateMode;", "isRunning", "", "isOnline", "fanSpeed", "Lcom/wuheng/smart/data/model/FanSpeed;", "radiationConfig", "Lcom/wuheng/smart/data/model/RadiationConfig;", "scheduleEnabled", "scheduleInfo", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;DDIILcom/wuheng/smart/data/model/ClimateMode;ZZLcom/wuheng/smart/data/model/FanSpeed;Lcom/wuheng/smart/data/model/RadiationConfig;ZLjava/lang/String;)V", "getCurrentTemperature", "()D", "getFanSpeed", "()Lcom/wuheng/smart/data/model/FanSpeed;", "getFloorId", "()Ljava/lang/String;", "getFloorName", "getHumidity", "()I", "getId", "()Z", "getMode", "()Lcom/wuheng/smart/data/model/ClimateMode;", "getName", "getRadiationConfig", "()Lcom/wuheng/smart/data/model/RadiationConfig;", "getScheduleEnabled", "getScheduleInfo", "getTargetHumidity", "getTargetTemperature", "component1", "component10", "component11", "component12", "component13", "component14", "component15", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "equals", "other", "hashCode", "toString", "app_debug"})
public final class ZoneDetail {
    @org.jetbrains.annotations.NotNull()
    @com.google.gson.annotations.SerializedName(value = "id")
    private final java.lang.String id = null;
    @org.jetbrains.annotations.NotNull()
    @com.google.gson.annotations.SerializedName(value = "name")
    private final java.lang.String name = null;
    @org.jetbrains.annotations.NotNull()
    @com.google.gson.annotations.SerializedName(value = "floorId")
    private final java.lang.String floorId = null;
    @org.jetbrains.annotations.NotNull()
    @com.google.gson.annotations.SerializedName(value = "floorName")
    private final java.lang.String floorName = null;
    @com.google.gson.annotations.SerializedName(value = "currentTemperature")
    private final double currentTemperature = 0.0;
    @com.google.gson.annotations.SerializedName(value = "targetTemperature")
    private final double targetTemperature = 0.0;
    @com.google.gson.annotations.SerializedName(value = "humidity")
    private final int humidity = 0;
    @com.google.gson.annotations.SerializedName(value = "targetHumidity")
    private final int targetHumidity = 0;
    @org.jetbrains.annotations.NotNull()
    @com.google.gson.annotations.SerializedName(value = "mode")
    private final com.wuheng.smart.data.model.ClimateMode mode = null;
    @com.google.gson.annotations.SerializedName(value = "isRunning")
    private final boolean isRunning = false;
    @com.google.gson.annotations.SerializedName(value = "isOnline")
    private final boolean isOnline = false;
    @org.jetbrains.annotations.NotNull()
    @com.google.gson.annotations.SerializedName(value = "fanSpeed")
    private final com.wuheng.smart.data.model.FanSpeed fanSpeed = null;
    @org.jetbrains.annotations.Nullable()
    @com.google.gson.annotations.SerializedName(value = "radiationConfig")
    private final com.wuheng.smart.data.model.RadiationConfig radiationConfig = null;
    @com.google.gson.annotations.SerializedName(value = "scheduleEnabled")
    private final boolean scheduleEnabled = false;
    @org.jetbrains.annotations.Nullable()
    @com.google.gson.annotations.SerializedName(value = "scheduleInfo")
    private final java.lang.String scheduleInfo = null;
    
    /**
     * 区域详情（增强版）
     *
     * UI组件映射：
     * - 房间Chip选择器: name, floorName, isOnline
     * - 温度/湿度设定卡片: currentTemperature, targetTemperature, humidity, targetHumidity
     * - 风速选择器: fanSpeed
     * - 辐射模式开关: radiationConfig
     */
    @org.jetbrains.annotations.NotNull()
    public final com.wuheng.smart.data.model.ZoneDetail copy(@org.jetbrains.annotations.NotNull()
    java.lang.String id, @org.jetbrains.annotations.NotNull()
    java.lang.String name, @org.jetbrains.annotations.NotNull()
    java.lang.String floorId, @org.jetbrains.annotations.NotNull()
    java.lang.String floorName, double currentTemperature, double targetTemperature, int humidity, int targetHumidity, @org.jetbrains.annotations.NotNull()
    com.wuheng.smart.data.model.ClimateMode mode, boolean isRunning, boolean isOnline, @org.jetbrains.annotations.NotNull()
    com.wuheng.smart.data.model.FanSpeed fanSpeed, @org.jetbrains.annotations.Nullable()
    com.wuheng.smart.data.model.RadiationConfig radiationConfig, boolean scheduleEnabled, @org.jetbrains.annotations.Nullable()
    java.lang.String scheduleInfo) {
        return null;
    }
    
    /**
     * 区域详情（增强版）
     *
     * UI组件映射：
     * - 房间Chip选择器: name, floorName, isOnline
     * - 温度/湿度设定卡片: currentTemperature, targetTemperature, humidity, targetHumidity
     * - 风速选择器: fanSpeed
     * - 辐射模式开关: radiationConfig
     */
    @java.lang.Override()
    public boolean equals(@org.jetbrains.annotations.Nullable()
    java.lang.Object other) {
        return false;
    }
    
    /**
     * 区域详情（增强版）
     *
     * UI组件映射：
     * - 房间Chip选择器: name, floorName, isOnline
     * - 温度/湿度设定卡片: currentTemperature, targetTemperature, humidity, targetHumidity
     * - 风速选择器: fanSpeed
     * - 辐射模式开关: radiationConfig
     */
    @java.lang.Override()
    public int hashCode() {
        return 0;
    }
    
    /**
     * 区域详情（增强版）
     *
     * UI组件映射：
     * - 房间Chip选择器: name, floorName, isOnline
     * - 温度/湿度设定卡片: currentTemperature, targetTemperature, humidity, targetHumidity
     * - 风速选择器: fanSpeed
     * - 辐射模式开关: radiationConfig
     */
    @org.jetbrains.annotations.NotNull()
    @java.lang.Override()
    public java.lang.String toString() {
        return null;
    }
    
    public ZoneDetail() {
        super();
    }
    
    public ZoneDetail(@org.jetbrains.annotations.NotNull()
    java.lang.String id, @org.jetbrains.annotations.NotNull()
    java.lang.String name, @org.jetbrains.annotations.NotNull()
    java.lang.String floorId, @org.jetbrains.annotations.NotNull()
    java.lang.String floorName, double currentTemperature, double targetTemperature, int humidity, int targetHumidity, @org.jetbrains.annotations.NotNull()
    com.wuheng.smart.data.model.ClimateMode mode, boolean isRunning, boolean isOnline, @org.jetbrains.annotations.NotNull()
    com.wuheng.smart.data.model.FanSpeed fanSpeed, @org.jetbrains.annotations.Nullable()
    com.wuheng.smart.data.model.RadiationConfig radiationConfig, boolean scheduleEnabled, @org.jetbrains.annotations.Nullable()
    java.lang.String scheduleInfo) {
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
    public final java.lang.String component3() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getFloorId() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component4() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getFloorName() {
        return null;
    }
    
    public final double component5() {
        return 0.0;
    }
    
    public final double getCurrentTemperature() {
        return 0.0;
    }
    
    public final double component6() {
        return 0.0;
    }
    
    public final double getTargetTemperature() {
        return 0.0;
    }
    
    public final int component7() {
        return 0;
    }
    
    public final int getHumidity() {
        return 0;
    }
    
    public final int component8() {
        return 0;
    }
    
    public final int getTargetHumidity() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.wuheng.smart.data.model.ClimateMode component9() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.wuheng.smart.data.model.ClimateMode getMode() {
        return null;
    }
    
    public final boolean component10() {
        return false;
    }
    
    public final boolean isRunning() {
        return false;
    }
    
    public final boolean component11() {
        return false;
    }
    
    public final boolean isOnline() {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.wuheng.smart.data.model.FanSpeed component12() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.wuheng.smart.data.model.FanSpeed getFanSpeed() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.wuheng.smart.data.model.RadiationConfig component13() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.wuheng.smart.data.model.RadiationConfig getRadiationConfig() {
        return null;
    }
    
    public final boolean component14() {
        return false;
    }
    
    public final boolean getScheduleEnabled() {
        return false;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component15() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getScheduleInfo() {
        return null;
    }
}