package com.wuheng.smart.data.model;

import com.google.gson.annotations.SerializedName;

/**
 * 设备状态 (getDeviceStatus接口返回)
 */
@kotlin.Metadata(mv = {1, 7, 1}, k = 1, d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\t\n\u0002\u0010\t\n\u0002\b\"\n\u0002\u0010\u000b\n\u0002\b\u0004\b\u0087\b\u0018\u00002\u00020\u0001B\u0085\u0001\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\u0003\u0012\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\u0006\u0012\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\u0006\u0012\n\b\u0002\u0010\n\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\r\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u000e\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u000f\u001a\u0004\u0018\u00010\u0010\u00a2\u0006\u0002\u0010\u0011J\t\u0010$\u001a\u00020\u0003H\u00c6\u0003J\u0010\u0010%\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003\u00a2\u0006\u0002\u0010\u0013J\u0010\u0010&\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003\u00a2\u0006\u0002\u0010\u0013J\u0010\u0010\'\u001a\u0004\u0018\u00010\u0010H\u00c6\u0003\u00a2\u0006\u0002\u0010\u001eJ\t\u0010(\u001a\u00020\u0003H\u00c6\u0003J\t\u0010)\u001a\u00020\u0006H\u00c6\u0003J\t\u0010*\u001a\u00020\u0003H\u00c6\u0003J\u000b\u0010+\u001a\u0004\u0018\u00010\u0006H\u00c6\u0003J\u000b\u0010,\u001a\u0004\u0018\u00010\u0006H\u00c6\u0003J\u0010\u0010-\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003\u00a2\u0006\u0002\u0010\u0013J\u0010\u0010.\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003\u00a2\u0006\u0002\u0010\u0013J\u0010\u0010/\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003\u00a2\u0006\u0002\u0010\u0013J\u0096\u0001\u00100\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00062\b\b\u0002\u0010\u0007\u001a\u00020\u00032\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\u00062\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\u00062\n\b\u0002\u0010\n\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\r\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u000e\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u000f\u001a\u0004\u0018\u00010\u0010H\u00c6\u0001\u00a2\u0006\u0002\u00101J\u0013\u00102\u001a\u0002032\b\u00104\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u00105\u001a\u00020\u0003H\u00d6\u0001J\t\u00106\u001a\u00020\u0006H\u00d6\u0001R\u001a\u0010\n\u001a\u0004\u0018\u00010\u00038\u0006X\u0087\u0004\u00a2\u0006\n\n\u0002\u0010\u0014\u001a\u0004\b\u0012\u0010\u0013R\u0016\u0010\u0002\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0016R\u001a\u0010\r\u001a\u0004\u0018\u00010\u00038\u0006X\u0087\u0004\u00a2\u0006\n\n\u0002\u0010\u0014\u001a\u0004\b\u0017\u0010\u0013R\u0018\u0010\t\u001a\u0004\u0018\u00010\u00068\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0019R\u0016\u0010\u0004\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u0016R\u001a\u0010\u000b\u001a\u0004\u0018\u00010\u00038\u0006X\u0087\u0004\u00a2\u0006\n\n\u0002\u0010\u0014\u001a\u0004\b\u001b\u0010\u0013R\u0016\u0010\u0007\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u0016R\u001a\u0010\u000f\u001a\u0004\u0018\u00010\u00108\u0006X\u0087\u0004\u00a2\u0006\n\n\u0002\u0010\u001f\u001a\u0004\b\u001d\u0010\u001eR\u0016\u0010\u0005\u001a\u00020\u00068\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b \u0010\u0019R\u0018\u0010\b\u001a\u0004\u0018\u00010\u00068\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b!\u0010\u0019R\u001a\u0010\u000e\u001a\u0004\u0018\u00010\u00038\u0006X\u0087\u0004\u00a2\u0006\n\n\u0002\u0010\u0014\u001a\u0004\b\"\u0010\u0013R\u001a\u0010\f\u001a\u0004\u0018\u00010\u00038\u0006X\u0087\u0004\u00a2\u0006\n\n\u0002\u0010\u0014\u001a\u0004\b#\u0010\u0013\u00a8\u00067"}, d2 = {"Lcom/wuheng/smart/data/model/DeviceStatus;", "", "deviceId", "", "onlineStatus", "runStatus", "", "power", "temperature", "humidity", "co2", "pm25", "voc", "fanSpeed", "valveOpen", "reportTime", "", "(IILjava/lang/String;ILjava/lang/String;Ljava/lang/String;Ljava/lang/Integer;Ljava/lang/Integer;Ljava/lang/Integer;Ljava/lang/Integer;Ljava/lang/Integer;Ljava/lang/Long;)V", "getCo2", "()Ljava/lang/Integer;", "Ljava/lang/Integer;", "getDeviceId", "()I", "getFanSpeed", "getHumidity", "()Ljava/lang/String;", "getOnlineStatus", "getPm25", "getPower", "getReportTime", "()Ljava/lang/Long;", "Ljava/lang/Long;", "getRunStatus", "getTemperature", "getValveOpen", "getVoc", "component1", "component10", "component11", "component12", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "(IILjava/lang/String;ILjava/lang/String;Ljava/lang/String;Ljava/lang/Integer;Ljava/lang/Integer;Ljava/lang/Integer;Ljava/lang/Integer;Ljava/lang/Integer;Ljava/lang/Long;)Lcom/wuheng/smart/data/model/DeviceStatus;", "equals", "", "other", "hashCode", "toString", "app_debug"})
public final class DeviceStatus {
    @com.google.gson.annotations.SerializedName(value = "device_id")
    private final int deviceId = 0;
    @com.google.gson.annotations.SerializedName(value = "online_status")
    private final int onlineStatus = 0;
    @org.jetbrains.annotations.NotNull()
    @com.google.gson.annotations.SerializedName(value = "run_status")
    private final java.lang.String runStatus = null;
    @com.google.gson.annotations.SerializedName(value = "power")
    private final int power = 0;
    @org.jetbrains.annotations.Nullable()
    @com.google.gson.annotations.SerializedName(value = "temperature")
    private final java.lang.String temperature = null;
    @org.jetbrains.annotations.Nullable()
    @com.google.gson.annotations.SerializedName(value = "humidity")
    private final java.lang.String humidity = null;
    @org.jetbrains.annotations.Nullable()
    @com.google.gson.annotations.SerializedName(value = "co2")
    private final java.lang.Integer co2 = null;
    @org.jetbrains.annotations.Nullable()
    @com.google.gson.annotations.SerializedName(value = "pm25")
    private final java.lang.Integer pm25 = null;
    @org.jetbrains.annotations.Nullable()
    @com.google.gson.annotations.SerializedName(value = "voc")
    private final java.lang.Integer voc = null;
    @org.jetbrains.annotations.Nullable()
    @com.google.gson.annotations.SerializedName(value = "fan_speed")
    private final java.lang.Integer fanSpeed = null;
    @org.jetbrains.annotations.Nullable()
    @com.google.gson.annotations.SerializedName(value = "valve_open")
    private final java.lang.Integer valveOpen = null;
    @org.jetbrains.annotations.Nullable()
    @com.google.gson.annotations.SerializedName(value = "report_time")
    private final java.lang.Long reportTime = null;
    
    /**
     * 设备状态 (getDeviceStatus接口返回)
     */
    @org.jetbrains.annotations.NotNull()
    public final com.wuheng.smart.data.model.DeviceStatus copy(int deviceId, int onlineStatus, @org.jetbrains.annotations.NotNull()
    java.lang.String runStatus, int power, @org.jetbrains.annotations.Nullable()
    java.lang.String temperature, @org.jetbrains.annotations.Nullable()
    java.lang.String humidity, @org.jetbrains.annotations.Nullable()
    java.lang.Integer co2, @org.jetbrains.annotations.Nullable()
    java.lang.Integer pm25, @org.jetbrains.annotations.Nullable()
    java.lang.Integer voc, @org.jetbrains.annotations.Nullable()
    java.lang.Integer fanSpeed, @org.jetbrains.annotations.Nullable()
    java.lang.Integer valveOpen, @org.jetbrains.annotations.Nullable()
    java.lang.Long reportTime) {
        return null;
    }
    
    /**
     * 设备状态 (getDeviceStatus接口返回)
     */
    @java.lang.Override()
    public boolean equals(@org.jetbrains.annotations.Nullable()
    java.lang.Object other) {
        return false;
    }
    
    /**
     * 设备状态 (getDeviceStatus接口返回)
     */
    @java.lang.Override()
    public int hashCode() {
        return 0;
    }
    
    /**
     * 设备状态 (getDeviceStatus接口返回)
     */
    @org.jetbrains.annotations.NotNull()
    @java.lang.Override()
    public java.lang.String toString() {
        return null;
    }
    
    public DeviceStatus(int deviceId, int onlineStatus, @org.jetbrains.annotations.NotNull()
    java.lang.String runStatus, int power, @org.jetbrains.annotations.Nullable()
    java.lang.String temperature, @org.jetbrains.annotations.Nullable()
    java.lang.String humidity, @org.jetbrains.annotations.Nullable()
    java.lang.Integer co2, @org.jetbrains.annotations.Nullable()
    java.lang.Integer pm25, @org.jetbrains.annotations.Nullable()
    java.lang.Integer voc, @org.jetbrains.annotations.Nullable()
    java.lang.Integer fanSpeed, @org.jetbrains.annotations.Nullable()
    java.lang.Integer valveOpen, @org.jetbrains.annotations.Nullable()
    java.lang.Long reportTime) {
        super();
    }
    
    public final int component1() {
        return 0;
    }
    
    public final int getDeviceId() {
        return 0;
    }
    
    public final int component2() {
        return 0;
    }
    
    public final int getOnlineStatus() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component3() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getRunStatus() {
        return null;
    }
    
    public final int component4() {
        return 0;
    }
    
    public final int getPower() {
        return 0;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component5() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getTemperature() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component6() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getHumidity() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Integer component7() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Integer getCo2() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Integer component8() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Integer getPm25() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Integer component9() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Integer getVoc() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Integer component10() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Integer getFanSpeed() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Integer component11() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Integer getValveOpen() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Long component12() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Long getReportTime() {
        return null;
    }
}