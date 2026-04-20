package com.wuheng.smart.data.model;

import com.google.gson.annotations.SerializedName;

/**
 * 设备实时数据
 */
@kotlin.Metadata(mv = {1, 7, 1}, k = 1, d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\b\n\u0002\u0010\t\n\u0002\b\u001c\n\u0002\u0010\u000b\n\u0002\b\u0004\b\u0087\b\u0018\u00002\u00020\u0001B]\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\u0006\u0012\u0006\u0010\b\u001a\u00020\u0003\u0012\u0006\u0010\t\u001a\u00020\u0003\u0012\u0006\u0010\n\u001a\u00020\u0003\u0012\u0006\u0010\u000b\u001a\u00020\u0003\u0012\u0006\u0010\f\u001a\u00020\u0003\u0012\u0006\u0010\r\u001a\u00020\u0003\u0012\u0006\u0010\u000e\u001a\u00020\u000f\u00a2\u0006\u0002\u0010\u0010J\t\u0010\u001f\u001a\u00020\u0003H\u00c6\u0003J\t\u0010 \u001a\u00020\u0003H\u00c6\u0003J\t\u0010!\u001a\u00020\u000fH\u00c6\u0003J\t\u0010\"\u001a\u00020\u0003H\u00c6\u0003J\t\u0010#\u001a\u00020\u0006H\u00c6\u0003J\t\u0010$\u001a\u00020\u0006H\u00c6\u0003J\t\u0010%\u001a\u00020\u0003H\u00c6\u0003J\t\u0010&\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\'\u001a\u00020\u0003H\u00c6\u0003J\t\u0010(\u001a\u00020\u0003H\u00c6\u0003J\t\u0010)\u001a\u00020\u0003H\u00c6\u0003Jw\u0010*\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00062\b\b\u0002\u0010\u0007\u001a\u00020\u00062\b\b\u0002\u0010\b\u001a\u00020\u00032\b\b\u0002\u0010\t\u001a\u00020\u00032\b\b\u0002\u0010\n\u001a\u00020\u00032\b\b\u0002\u0010\u000b\u001a\u00020\u00032\b\b\u0002\u0010\f\u001a\u00020\u00032\b\b\u0002\u0010\r\u001a\u00020\u00032\b\b\u0002\u0010\u000e\u001a\u00020\u000fH\u00c6\u0001J\u0013\u0010+\u001a\u00020,2\b\u0010-\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010.\u001a\u00020\u0003H\u00d6\u0001J\t\u0010/\u001a\u00020\u0006H\u00d6\u0001R\u0016\u0010\b\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R\u0016\u0010\u0002\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0012R\u0016\u0010\u0004\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0012R\u0016\u0010\u000b\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0012R\u0016\u0010\u0007\u001a\u00020\u00068\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0017R\u0016\u0010\t\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0012R\u0016\u0010\r\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u0012R\u0016\u0010\u000e\u001a\u00020\u000f8\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u001bR\u0016\u0010\u0005\u001a\u00020\u00068\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u0017R\u0016\u0010\f\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\u0012R\u0016\u0010\n\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\u0012\u00a8\u00060"}, d2 = {"Lcom/wuheng/smart/data/model/DeviceData;", "", "dataId", "", "deviceId", "temperature", "", "humidity", "co2", "pm25", "voc", "fanSpeed", "valveOpen", "power", "reportTime", "", "(IILjava/lang/String;Ljava/lang/String;IIIIIIJ)V", "getCo2", "()I", "getDataId", "getDeviceId", "getFanSpeed", "getHumidity", "()Ljava/lang/String;", "getPm25", "getPower", "getReportTime", "()J", "getTemperature", "getValveOpen", "getVoc", "component1", "component10", "component11", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "equals", "", "other", "hashCode", "toString", "app_debug"})
public final class DeviceData {
    @com.google.gson.annotations.SerializedName(value = "data_id")
    private final int dataId = 0;
    @com.google.gson.annotations.SerializedName(value = "device_id")
    private final int deviceId = 0;
    @org.jetbrains.annotations.NotNull()
    @com.google.gson.annotations.SerializedName(value = "temperature")
    private final java.lang.String temperature = null;
    @org.jetbrains.annotations.NotNull()
    @com.google.gson.annotations.SerializedName(value = "humidity")
    private final java.lang.String humidity = null;
    @com.google.gson.annotations.SerializedName(value = "co2")
    private final int co2 = 0;
    @com.google.gson.annotations.SerializedName(value = "pm25")
    private final int pm25 = 0;
    @com.google.gson.annotations.SerializedName(value = "voc")
    private final int voc = 0;
    @com.google.gson.annotations.SerializedName(value = "fan_speed")
    private final int fanSpeed = 0;
    @com.google.gson.annotations.SerializedName(value = "valve_open")
    private final int valveOpen = 0;
    @com.google.gson.annotations.SerializedName(value = "power")
    private final int power = 0;
    @com.google.gson.annotations.SerializedName(value = "report_time")
    private final long reportTime = 0L;
    
    /**
     * 设备实时数据
     */
    @org.jetbrains.annotations.NotNull()
    public final com.wuheng.smart.data.model.DeviceData copy(int dataId, int deviceId, @org.jetbrains.annotations.NotNull()
    java.lang.String temperature, @org.jetbrains.annotations.NotNull()
    java.lang.String humidity, int co2, int pm25, int voc, int fanSpeed, int valveOpen, int power, long reportTime) {
        return null;
    }
    
    /**
     * 设备实时数据
     */
    @java.lang.Override()
    public boolean equals(@org.jetbrains.annotations.Nullable()
    java.lang.Object other) {
        return false;
    }
    
    /**
     * 设备实时数据
     */
    @java.lang.Override()
    public int hashCode() {
        return 0;
    }
    
    /**
     * 设备实时数据
     */
    @org.jetbrains.annotations.NotNull()
    @java.lang.Override()
    public java.lang.String toString() {
        return null;
    }
    
    public DeviceData(int dataId, int deviceId, @org.jetbrains.annotations.NotNull()
    java.lang.String temperature, @org.jetbrains.annotations.NotNull()
    java.lang.String humidity, int co2, int pm25, int voc, int fanSpeed, int valveOpen, int power, long reportTime) {
        super();
    }
    
    public final int component1() {
        return 0;
    }
    
    public final int getDataId() {
        return 0;
    }
    
    public final int component2() {
        return 0;
    }
    
    public final int getDeviceId() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component3() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getTemperature() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component4() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getHumidity() {
        return null;
    }
    
    public final int component5() {
        return 0;
    }
    
    public final int getCo2() {
        return 0;
    }
    
    public final int component6() {
        return 0;
    }
    
    public final int getPm25() {
        return 0;
    }
    
    public final int component7() {
        return 0;
    }
    
    public final int getVoc() {
        return 0;
    }
    
    public final int component8() {
        return 0;
    }
    
    public final int getFanSpeed() {
        return 0;
    }
    
    public final int component9() {
        return 0;
    }
    
    public final int getValveOpen() {
        return 0;
    }
    
    public final int component10() {
        return 0;
    }
    
    public final int getPower() {
        return 0;
    }
    
    public final long component11() {
        return 0L;
    }
    
    public final long getReportTime() {
        return 0L;
    }
}