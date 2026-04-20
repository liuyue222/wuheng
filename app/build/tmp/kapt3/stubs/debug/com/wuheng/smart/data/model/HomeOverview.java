package com.wuheng.smart.data.model;

import com.google.gson.annotations.SerializedName;

/**
 * 首页概览数据
 * 包含住宅整体状态、环境参数、运行场景等信息
 *
 * UI组件映射：
 * - 环境数据卡片(5项): indoorTemperature, indoorHumidity, pm25, co2, voc
 * - 天气信息栏: weatherTemp, weatherDesc, aqiStatus
 * - 西湖一号院卡片: residenceName, address
 * - 场景网格: runningScenes (需要iconResId)
 */
@kotlin.Metadata(mv = {1, 7, 1}, k = 1, d1 = {"\u0000H\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u0006\n\u0002\b\u0005\n\u0002\u0010\u0007\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b-\n\u0002\u0010\u000b\n\u0002\b\u0004\b\u0087\b\u0018\u00002\u00020\u0001B\u00c1\u0001\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0005\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0007\u0012\b\b\u0002\u0010\b\u001a\u00020\u0003\u0012\b\b\u0002\u0010\t\u001a\u00020\u0003\u0012\b\b\u0002\u0010\n\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u000b\u001a\u00020\u0007\u0012\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\r\u0012\n\b\u0002\u0010\u000e\u001a\u0004\u0018\u00010\u000f\u0012\b\b\u0002\u0010\u0010\u001a\u00020\u000f\u0012\b\b\u0002\u0010\u0011\u001a\u00020\u000f\u0012\b\b\u0002\u0010\u0012\u001a\u00020\u000f\u0012\n\b\u0002\u0010\u0013\u001a\u0004\u0018\u00010\u000f\u0012\b\b\u0002\u0010\u0014\u001a\u00020\u0015\u0012\u000e\b\u0002\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00180\u0017\u0012\u000e\b\u0002\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u001a0\u0017\u00a2\u0006\u0002\u0010\u001bJ\t\u00104\u001a\u00020\u0003H\u00c6\u0003J\u000b\u00105\u001a\u0004\u0018\u00010\u000fH\u00c6\u0003J\t\u00106\u001a\u00020\u000fH\u00c6\u0003J\t\u00107\u001a\u00020\u000fH\u00c6\u0003J\t\u00108\u001a\u00020\u000fH\u00c6\u0003J\u000b\u00109\u001a\u0004\u0018\u00010\u000fH\u00c6\u0003J\t\u0010:\u001a\u00020\u0015H\u00c6\u0003J\u000f\u0010;\u001a\b\u0012\u0004\u0012\u00020\u00180\u0017H\u00c6\u0003J\u000f\u0010<\u001a\b\u0012\u0004\u0012\u00020\u001a0\u0017H\u00c6\u0003J\t\u0010=\u001a\u00020\u0003H\u00c6\u0003J\t\u0010>\u001a\u00020\u0003H\u00c6\u0003J\t\u0010?\u001a\u00020\u0007H\u00c6\u0003J\t\u0010@\u001a\u00020\u0003H\u00c6\u0003J\t\u0010A\u001a\u00020\u0003H\u00c6\u0003J\t\u0010B\u001a\u00020\u0003H\u00c6\u0003J\t\u0010C\u001a\u00020\u0007H\u00c6\u0003J\u0010\u0010D\u001a\u0004\u0018\u00010\rH\u00c6\u0003\u00a2\u0006\u0002\u00102J\u00ca\u0001\u0010E\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00032\b\b\u0002\u0010\u0006\u001a\u00020\u00072\b\b\u0002\u0010\b\u001a\u00020\u00032\b\b\u0002\u0010\t\u001a\u00020\u00032\b\b\u0002\u0010\n\u001a\u00020\u00032\b\b\u0002\u0010\u000b\u001a\u00020\u00072\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\r2\n\b\u0002\u0010\u000e\u001a\u0004\u0018\u00010\u000f2\b\b\u0002\u0010\u0010\u001a\u00020\u000f2\b\b\u0002\u0010\u0011\u001a\u00020\u000f2\b\b\u0002\u0010\u0012\u001a\u00020\u000f2\n\b\u0002\u0010\u0013\u001a\u0004\u0018\u00010\u000f2\b\b\u0002\u0010\u0014\u001a\u00020\u00152\u000e\b\u0002\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00180\u00172\u000e\b\u0002\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u001a0\u0017H\u00c6\u0001\u00a2\u0006\u0002\u0010FJ\u0013\u0010G\u001a\u00020H2\b\u0010I\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010J\u001a\u00020\u0003H\u00d6\u0001J\t\u0010K\u001a\u00020\u000fH\u00d6\u0001R\u0016\u0010\u0012\u001a\u00020\u000f8\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u001dR\u0016\u0010\u0010\u001a\u00020\u000f8\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\u001dR\u0016\u0010\n\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001f\u0010 R\u0016\u0010\u0014\u001a\u00020\u00158\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b!\u0010\"R\u0016\u0010\u0004\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b#\u0010 R\u0016\u0010\b\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b$\u0010 R\u0016\u0010\u0006\u001a\u00020\u00078\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b%\u0010&R\u0016\u0010\u0005\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\'\u0010 R\u0016\u0010\t\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b(\u0010 R\u001c\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u001a0\u00178\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b)\u0010*R\u0018\u0010\u0013\u001a\u0004\u0018\u00010\u000f8\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b+\u0010\u001dR\u0016\u0010\u0011\u001a\u00020\u000f8\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b,\u0010\u001dR\u0016\u0010\u0002\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b-\u0010 R\u001c\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00180\u00178\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b.\u0010*R\u0016\u0010\u000b\u001a\u00020\u00078\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b/\u0010&R\u0018\u0010\u000e\u001a\u0004\u0018\u00010\u000f8\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b0\u0010\u001dR\u001a\u0010\f\u001a\u0004\u0018\u00010\r8\u0006X\u0087\u0004\u00a2\u0006\n\n\u0002\u00103\u001a\u0004\b1\u00102\u00a8\u0006L"}, d2 = {"Lcom/wuheng/smart/data/model/HomeOverview;", "", "roomCount", "", "deviceCount", "onlineDeviceCount", "indoorTemperature", "", "indoorHumidity", "pm25", "co2", "voc", "weatherTemp", "", "weatherDesc", "", "aqiStatus", "residenceName", "address", "residenceImageResId", "currentWeatherMode", "Lcom/wuheng/smart/data/model/WeatherMode;", "runningScenes", "", "Lcom/wuheng/smart/data/model/Scene;", "recentDevices", "Lcom/wuheng/smart/data/model/Device;", "(IIIDIIIDLjava/lang/Float;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Lcom/wuheng/smart/data/model/WeatherMode;Ljava/util/List;Ljava/util/List;)V", "getAddress", "()Ljava/lang/String;", "getAqiStatus", "getCo2", "()I", "getCurrentWeatherMode", "()Lcom/wuheng/smart/data/model/WeatherMode;", "getDeviceCount", "getIndoorHumidity", "getIndoorTemperature", "()D", "getOnlineDeviceCount", "getPm25", "getRecentDevices", "()Ljava/util/List;", "getResidenceImageResId", "getResidenceName", "getRoomCount", "getRunningScenes", "getVoc", "getWeatherDesc", "getWeatherTemp", "()Ljava/lang/Float;", "Ljava/lang/Float;", "component1", "component10", "component11", "component12", "component13", "component14", "component15", "component16", "component17", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "(IIIDIIIDLjava/lang/Float;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Lcom/wuheng/smart/data/model/WeatherMode;Ljava/util/List;Ljava/util/List;)Lcom/wuheng/smart/data/model/HomeOverview;", "equals", "", "other", "hashCode", "toString", "app_debug"})
public final class HomeOverview {
    @com.google.gson.annotations.SerializedName(value = "roomCount")
    private final int roomCount = 0;
    @com.google.gson.annotations.SerializedName(value = "deviceCount")
    private final int deviceCount = 0;
    @com.google.gson.annotations.SerializedName(value = "onlineDeviceCount")
    private final int onlineDeviceCount = 0;
    @com.google.gson.annotations.SerializedName(value = "indoorTemperature")
    private final double indoorTemperature = 0.0;
    @com.google.gson.annotations.SerializedName(value = "indoorHumidity")
    private final int indoorHumidity = 0;
    @com.google.gson.annotations.SerializedName(value = "pm25")
    private final int pm25 = 0;
    @com.google.gson.annotations.SerializedName(value = "co2")
    private final int co2 = 0;
    @com.google.gson.annotations.SerializedName(value = "voc")
    private final double voc = 0.0;
    @org.jetbrains.annotations.Nullable()
    @com.google.gson.annotations.SerializedName(value = "weatherTemp")
    private final java.lang.Float weatherTemp = null;
    @org.jetbrains.annotations.Nullable()
    @com.google.gson.annotations.SerializedName(value = "weatherDesc")
    private final java.lang.String weatherDesc = null;
    @org.jetbrains.annotations.NotNull()
    @com.google.gson.annotations.SerializedName(value = "aqiStatus")
    private final java.lang.String aqiStatus = null;
    @org.jetbrains.annotations.NotNull()
    @com.google.gson.annotations.SerializedName(value = "residenceName")
    private final java.lang.String residenceName = null;
    @org.jetbrains.annotations.NotNull()
    @com.google.gson.annotations.SerializedName(value = "address")
    private final java.lang.String address = null;
    @org.jetbrains.annotations.Nullable()
    @com.google.gson.annotations.SerializedName(value = "residenceImageResId")
    private final java.lang.String residenceImageResId = null;
    @org.jetbrains.annotations.NotNull()
    @com.google.gson.annotations.SerializedName(value = "currentWeatherMode")
    private final com.wuheng.smart.data.model.WeatherMode currentWeatherMode = null;
    @org.jetbrains.annotations.NotNull()
    @com.google.gson.annotations.SerializedName(value = "runningScenes")
    private final java.util.List<com.wuheng.smart.data.model.Scene> runningScenes = null;
    @org.jetbrains.annotations.NotNull()
    @com.google.gson.annotations.SerializedName(value = "recentDevices")
    private final java.util.List<com.wuheng.smart.data.model.Device> recentDevices = null;
    
    /**
     * 首页概览数据
     * 包含住宅整体状态、环境参数、运行场景等信息
     *
     * UI组件映射：
     * - 环境数据卡片(5项): indoorTemperature, indoorHumidity, pm25, co2, voc
     * - 天气信息栏: weatherTemp, weatherDesc, aqiStatus
     * - 西湖一号院卡片: residenceName, address
     * - 场景网格: runningScenes (需要iconResId)
     */
    @org.jetbrains.annotations.NotNull()
    public final com.wuheng.smart.data.model.HomeOverview copy(int roomCount, int deviceCount, int onlineDeviceCount, double indoorTemperature, int indoorHumidity, int pm25, int co2, double voc, @org.jetbrains.annotations.Nullable()
    java.lang.Float weatherTemp, @org.jetbrains.annotations.Nullable()
    java.lang.String weatherDesc, @org.jetbrains.annotations.NotNull()
    java.lang.String aqiStatus, @org.jetbrains.annotations.NotNull()
    java.lang.String residenceName, @org.jetbrains.annotations.NotNull()
    java.lang.String address, @org.jetbrains.annotations.Nullable()
    java.lang.String residenceImageResId, @org.jetbrains.annotations.NotNull()
    com.wuheng.smart.data.model.WeatherMode currentWeatherMode, @org.jetbrains.annotations.NotNull()
    java.util.List<com.wuheng.smart.data.model.Scene> runningScenes, @org.jetbrains.annotations.NotNull()
    java.util.List<com.wuheng.smart.data.model.Device> recentDevices) {
        return null;
    }
    
    /**
     * 首页概览数据
     * 包含住宅整体状态、环境参数、运行场景等信息
     *
     * UI组件映射：
     * - 环境数据卡片(5项): indoorTemperature, indoorHumidity, pm25, co2, voc
     * - 天气信息栏: weatherTemp, weatherDesc, aqiStatus
     * - 西湖一号院卡片: residenceName, address
     * - 场景网格: runningScenes (需要iconResId)
     */
    @java.lang.Override()
    public boolean equals(@org.jetbrains.annotations.Nullable()
    java.lang.Object other) {
        return false;
    }
    
    /**
     * 首页概览数据
     * 包含住宅整体状态、环境参数、运行场景等信息
     *
     * UI组件映射：
     * - 环境数据卡片(5项): indoorTemperature, indoorHumidity, pm25, co2, voc
     * - 天气信息栏: weatherTemp, weatherDesc, aqiStatus
     * - 西湖一号院卡片: residenceName, address
     * - 场景网格: runningScenes (需要iconResId)
     */
    @java.lang.Override()
    public int hashCode() {
        return 0;
    }
    
    /**
     * 首页概览数据
     * 包含住宅整体状态、环境参数、运行场景等信息
     *
     * UI组件映射：
     * - 环境数据卡片(5项): indoorTemperature, indoorHumidity, pm25, co2, voc
     * - 天气信息栏: weatherTemp, weatherDesc, aqiStatus
     * - 西湖一号院卡片: residenceName, address
     * - 场景网格: runningScenes (需要iconResId)
     */
    @org.jetbrains.annotations.NotNull()
    @java.lang.Override()
    public java.lang.String toString() {
        return null;
    }
    
    public HomeOverview() {
        super();
    }
    
    public HomeOverview(int roomCount, int deviceCount, int onlineDeviceCount, double indoorTemperature, int indoorHumidity, int pm25, int co2, double voc, @org.jetbrains.annotations.Nullable()
    java.lang.Float weatherTemp, @org.jetbrains.annotations.Nullable()
    java.lang.String weatherDesc, @org.jetbrains.annotations.NotNull()
    java.lang.String aqiStatus, @org.jetbrains.annotations.NotNull()
    java.lang.String residenceName, @org.jetbrains.annotations.NotNull()
    java.lang.String address, @org.jetbrains.annotations.Nullable()
    java.lang.String residenceImageResId, @org.jetbrains.annotations.NotNull()
    com.wuheng.smart.data.model.WeatherMode currentWeatherMode, @org.jetbrains.annotations.NotNull()
    java.util.List<com.wuheng.smart.data.model.Scene> runningScenes, @org.jetbrains.annotations.NotNull()
    java.util.List<com.wuheng.smart.data.model.Device> recentDevices) {
        super();
    }
    
    public final int component1() {
        return 0;
    }
    
    public final int getRoomCount() {
        return 0;
    }
    
    public final int component2() {
        return 0;
    }
    
    public final int getDeviceCount() {
        return 0;
    }
    
    public final int component3() {
        return 0;
    }
    
    public final int getOnlineDeviceCount() {
        return 0;
    }
    
    public final double component4() {
        return 0.0;
    }
    
    public final double getIndoorTemperature() {
        return 0.0;
    }
    
    public final int component5() {
        return 0;
    }
    
    public final int getIndoorHumidity() {
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
    
    public final int getCo2() {
        return 0;
    }
    
    public final double component8() {
        return 0.0;
    }
    
    public final double getVoc() {
        return 0.0;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Float component9() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Float getWeatherTemp() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component10() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getWeatherDesc() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component11() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getAqiStatus() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component12() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getResidenceName() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component13() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getAddress() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component14() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getResidenceImageResId() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.wuheng.smart.data.model.WeatherMode component15() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.wuheng.smart.data.model.WeatherMode getCurrentWeatherMode() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.wuheng.smart.data.model.Scene> component16() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.wuheng.smart.data.model.Scene> getRunningScenes() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.wuheng.smart.data.model.Device> component17() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.wuheng.smart.data.model.Device> getRecentDevices() {
        return null;
    }
}