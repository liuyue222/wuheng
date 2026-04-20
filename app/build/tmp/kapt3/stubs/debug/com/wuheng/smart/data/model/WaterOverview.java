package com.wuheng.smart.data.model;

import com.google.gson.annotations.SerializedName;

/**
 * 水系统概览数据（增强版）
 *
 * UI组件映射：
 * - 生活热水循环卡片: currentTemp, cycleMode, temporaryDuration
 * - 热力杀菌卡片: sterilizationSchedule, sterilizationStatus
 * - 全屋净水滤芯: filters
 */
@kotlin.Metadata(mv = {1, 7, 1}, k = 1, d1 = {"\u0000N\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0006\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b0\n\u0002\u0010\u000b\n\u0002\b\u0004\b\u0087\b\u0018\u00002\u00020\u0001B\u00a9\u0001\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0007\u001a\u00020\u0005\u0012\b\b\u0002\u0010\b\u001a\u00020\u0005\u0012\b\b\u0002\u0010\t\u001a\u00020\n\u0012\b\b\u0002\u0010\u000b\u001a\u00020\n\u0012\b\b\u0002\u0010\f\u001a\u00020\r\u0012\b\b\u0002\u0010\u000e\u001a\u00020\u000f\u0012\b\b\u0002\u0010\u0010\u001a\u00020\n\u0012\n\b\u0002\u0010\u0011\u001a\u0004\u0018\u00010\u0012\u0012\n\b\u0002\u0010\u0013\u001a\u0004\u0018\u00010\u0014\u0012\u000e\b\u0002\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00170\u0016\u0012\n\b\u0002\u0010\u0018\u001a\u0004\u0018\u00010\n\u0012\n\b\u0002\u0010\u0019\u001a\u0004\u0018\u00010\r\u00a2\u0006\u0002\u0010\u001aJ\t\u00106\u001a\u00020\u0003H\u00c6\u0003J\t\u00107\u001a\u00020\nH\u00c6\u0003J\u000b\u00108\u001a\u0004\u0018\u00010\u0012H\u00c6\u0003J\u000b\u00109\u001a\u0004\u0018\u00010\u0014H\u00c6\u0003J\u000f\u0010:\u001a\b\u0012\u0004\u0012\u00020\u00170\u0016H\u00c6\u0003J\u0010\u0010;\u001a\u0004\u0018\u00010\nH\u00c6\u0003\u00a2\u0006\u0002\u00103J\u0010\u0010<\u001a\u0004\u0018\u00010\rH\u00c6\u0003\u00a2\u0006\u0002\u0010(J\t\u0010=\u001a\u00020\u0005H\u00c6\u0003J\t\u0010>\u001a\u00020\u0005H\u00c6\u0003J\t\u0010?\u001a\u00020\u0005H\u00c6\u0003J\t\u0010@\u001a\u00020\u0005H\u00c6\u0003J\t\u0010A\u001a\u00020\nH\u00c6\u0003J\t\u0010B\u001a\u00020\nH\u00c6\u0003J\t\u0010C\u001a\u00020\rH\u00c6\u0003J\t\u0010D\u001a\u00020\u000fH\u00c6\u0003J\u00b2\u0001\u0010E\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00052\b\b\u0002\u0010\u0007\u001a\u00020\u00052\b\b\u0002\u0010\b\u001a\u00020\u00052\b\b\u0002\u0010\t\u001a\u00020\n2\b\b\u0002\u0010\u000b\u001a\u00020\n2\b\b\u0002\u0010\f\u001a\u00020\r2\b\b\u0002\u0010\u000e\u001a\u00020\u000f2\b\b\u0002\u0010\u0010\u001a\u00020\n2\n\b\u0002\u0010\u0011\u001a\u0004\u0018\u00010\u00122\n\b\u0002\u0010\u0013\u001a\u0004\u0018\u00010\u00142\u000e\b\u0002\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00170\u00162\n\b\u0002\u0010\u0018\u001a\u0004\u0018\u00010\n2\n\b\u0002\u0010\u0019\u001a\u0004\u0018\u00010\rH\u00c6\u0001\u00a2\u0006\u0002\u0010FJ\u0013\u0010G\u001a\u00020H2\b\u0010I\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010J\u001a\u00020\nH\u00d6\u0001J\t\u0010K\u001a\u00020\u0012H\u00d6\u0001R\u0016\u0010\f\u001a\u00020\r8\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u001cR\u0016\u0010\u000e\u001a\u00020\u000f8\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\u001eR\u0016\u0010\t\u001a\u00020\n8\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001f\u0010 R\u001c\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00170\u00168\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b!\u0010\"R\u0016\u0010\b\u001a\u00020\u00058\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b#\u0010$R\u0016\u0010\u0004\u001a\u00020\u00058\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b%\u0010$R\u0016\u0010\u0006\u001a\u00020\u00058\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b&\u0010$R\u001a\u0010\u0019\u001a\u0004\u0018\u00010\r8\u0006X\u0087\u0004\u00a2\u0006\n\n\u0002\u0010)\u001a\u0004\b\'\u0010(R\u0016\u0010\u0007\u001a\u00020\u00058\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b*\u0010$R\u0016\u0010\u000b\u001a\u00020\n8\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b+\u0010 R\u0018\u0010\u0011\u001a\u0004\u0018\u00010\u00128\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b,\u0010-R\u0018\u0010\u0013\u001a\u0004\u0018\u00010\u00148\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b.\u0010/R\u0016\u0010\u0002\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b0\u00101R\u001a\u0010\u0018\u001a\u0004\u0018\u00010\n8\u0006X\u0087\u0004\u00a2\u0006\n\n\u0002\u00104\u001a\u0004\b2\u00103R\u0016\u0010\u0010\u001a\u00020\n8\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b5\u0010 \u00a8\u0006L"}, d2 = {"Lcom/wuheng/smart/data/model/WaterOverview;", "", "systemStatus", "Lcom/wuheng/smart/data/model/WaterSystemStatus;", "inletTemperature", "", "outletTemperature", "pressure", "flowRate", "deviceCount", "", "runningDeviceCount", "currentTemp", "", "cycleMode", "Lcom/wuheng/smart/data/model/CycleMode;", "temporaryDuration", "sterilizationSchedule", "", "sterilizationStatus", "Lcom/wuheng/smart/data/model/SterilizationStatus;", "filters", "", "Lcom/wuheng/smart/data/model/FilterInfo;", "tds", "ph", "(Lcom/wuheng/smart/data/model/WaterSystemStatus;DDDDIIFLcom/wuheng/smart/data/model/CycleMode;ILjava/lang/String;Lcom/wuheng/smart/data/model/SterilizationStatus;Ljava/util/List;Ljava/lang/Integer;Ljava/lang/Float;)V", "getCurrentTemp", "()F", "getCycleMode", "()Lcom/wuheng/smart/data/model/CycleMode;", "getDeviceCount", "()I", "getFilters", "()Ljava/util/List;", "getFlowRate", "()D", "getInletTemperature", "getOutletTemperature", "getPh", "()Ljava/lang/Float;", "Ljava/lang/Float;", "getPressure", "getRunningDeviceCount", "getSterilizationSchedule", "()Ljava/lang/String;", "getSterilizationStatus", "()Lcom/wuheng/smart/data/model/SterilizationStatus;", "getSystemStatus", "()Lcom/wuheng/smart/data/model/WaterSystemStatus;", "getTds", "()Ljava/lang/Integer;", "Ljava/lang/Integer;", "getTemporaryDuration", "component1", "component10", "component11", "component12", "component13", "component14", "component15", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "(Lcom/wuheng/smart/data/model/WaterSystemStatus;DDDDIIFLcom/wuheng/smart/data/model/CycleMode;ILjava/lang/String;Lcom/wuheng/smart/data/model/SterilizationStatus;Ljava/util/List;Ljava/lang/Integer;Ljava/lang/Float;)Lcom/wuheng/smart/data/model/WaterOverview;", "equals", "", "other", "hashCode", "toString", "app_debug"})
public final class WaterOverview {
    @org.jetbrains.annotations.NotNull()
    @com.google.gson.annotations.SerializedName(value = "systemStatus")
    private final com.wuheng.smart.data.model.WaterSystemStatus systemStatus = null;
    @com.google.gson.annotations.SerializedName(value = "inletTemperature")
    private final double inletTemperature = 0.0;
    @com.google.gson.annotations.SerializedName(value = "outletTemperature")
    private final double outletTemperature = 0.0;
    @com.google.gson.annotations.SerializedName(value = "pressure")
    private final double pressure = 0.0;
    @com.google.gson.annotations.SerializedName(value = "flowRate")
    private final double flowRate = 0.0;
    @com.google.gson.annotations.SerializedName(value = "deviceCount")
    private final int deviceCount = 0;
    @com.google.gson.annotations.SerializedName(value = "runningDeviceCount")
    private final int runningDeviceCount = 0;
    @com.google.gson.annotations.SerializedName(value = "currentTemp")
    private final float currentTemp = 0.0F;
    @org.jetbrains.annotations.NotNull()
    @com.google.gson.annotations.SerializedName(value = "cycleMode")
    private final com.wuheng.smart.data.model.CycleMode cycleMode = null;
    @com.google.gson.annotations.SerializedName(value = "temporaryDuration")
    private final int temporaryDuration = 0;
    @org.jetbrains.annotations.Nullable()
    @com.google.gson.annotations.SerializedName(value = "sterilizationSchedule")
    private final java.lang.String sterilizationSchedule = null;
    @org.jetbrains.annotations.Nullable()
    @com.google.gson.annotations.SerializedName(value = "sterilizationStatus")
    private final com.wuheng.smart.data.model.SterilizationStatus sterilizationStatus = null;
    @org.jetbrains.annotations.NotNull()
    @com.google.gson.annotations.SerializedName(value = "filters")
    private final java.util.List<com.wuheng.smart.data.model.FilterInfo> filters = null;
    @org.jetbrains.annotations.Nullable()
    @com.google.gson.annotations.SerializedName(value = "tds")
    private final java.lang.Integer tds = null;
    @org.jetbrains.annotations.Nullable()
    @com.google.gson.annotations.SerializedName(value = "ph")
    private final java.lang.Float ph = null;
    
    /**
     * 水系统概览数据（增强版）
     *
     * UI组件映射：
     * - 生活热水循环卡片: currentTemp, cycleMode, temporaryDuration
     * - 热力杀菌卡片: sterilizationSchedule, sterilizationStatus
     * - 全屋净水滤芯: filters
     */
    @org.jetbrains.annotations.NotNull()
    public final com.wuheng.smart.data.model.WaterOverview copy(@org.jetbrains.annotations.NotNull()
    com.wuheng.smart.data.model.WaterSystemStatus systemStatus, double inletTemperature, double outletTemperature, double pressure, double flowRate, int deviceCount, int runningDeviceCount, float currentTemp, @org.jetbrains.annotations.NotNull()
    com.wuheng.smart.data.model.CycleMode cycleMode, int temporaryDuration, @org.jetbrains.annotations.Nullable()
    java.lang.String sterilizationSchedule, @org.jetbrains.annotations.Nullable()
    com.wuheng.smart.data.model.SterilizationStatus sterilizationStatus, @org.jetbrains.annotations.NotNull()
    java.util.List<com.wuheng.smart.data.model.FilterInfo> filters, @org.jetbrains.annotations.Nullable()
    java.lang.Integer tds, @org.jetbrains.annotations.Nullable()
    java.lang.Float ph) {
        return null;
    }
    
    /**
     * 水系统概览数据（增强版）
     *
     * UI组件映射：
     * - 生活热水循环卡片: currentTemp, cycleMode, temporaryDuration
     * - 热力杀菌卡片: sterilizationSchedule, sterilizationStatus
     * - 全屋净水滤芯: filters
     */
    @java.lang.Override()
    public boolean equals(@org.jetbrains.annotations.Nullable()
    java.lang.Object other) {
        return false;
    }
    
    /**
     * 水系统概览数据（增强版）
     *
     * UI组件映射：
     * - 生活热水循环卡片: currentTemp, cycleMode, temporaryDuration
     * - 热力杀菌卡片: sterilizationSchedule, sterilizationStatus
     * - 全屋净水滤芯: filters
     */
    @java.lang.Override()
    public int hashCode() {
        return 0;
    }
    
    /**
     * 水系统概览数据（增强版）
     *
     * UI组件映射：
     * - 生活热水循环卡片: currentTemp, cycleMode, temporaryDuration
     * - 热力杀菌卡片: sterilizationSchedule, sterilizationStatus
     * - 全屋净水滤芯: filters
     */
    @org.jetbrains.annotations.NotNull()
    @java.lang.Override()
    public java.lang.String toString() {
        return null;
    }
    
    public WaterOverview() {
        super();
    }
    
    public WaterOverview(@org.jetbrains.annotations.NotNull()
    com.wuheng.smart.data.model.WaterSystemStatus systemStatus, double inletTemperature, double outletTemperature, double pressure, double flowRate, int deviceCount, int runningDeviceCount, float currentTemp, @org.jetbrains.annotations.NotNull()
    com.wuheng.smart.data.model.CycleMode cycleMode, int temporaryDuration, @org.jetbrains.annotations.Nullable()
    java.lang.String sterilizationSchedule, @org.jetbrains.annotations.Nullable()
    com.wuheng.smart.data.model.SterilizationStatus sterilizationStatus, @org.jetbrains.annotations.NotNull()
    java.util.List<com.wuheng.smart.data.model.FilterInfo> filters, @org.jetbrains.annotations.Nullable()
    java.lang.Integer tds, @org.jetbrains.annotations.Nullable()
    java.lang.Float ph) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.wuheng.smart.data.model.WaterSystemStatus component1() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.wuheng.smart.data.model.WaterSystemStatus getSystemStatus() {
        return null;
    }
    
    public final double component2() {
        return 0.0;
    }
    
    public final double getInletTemperature() {
        return 0.0;
    }
    
    public final double component3() {
        return 0.0;
    }
    
    public final double getOutletTemperature() {
        return 0.0;
    }
    
    public final double component4() {
        return 0.0;
    }
    
    public final double getPressure() {
        return 0.0;
    }
    
    public final double component5() {
        return 0.0;
    }
    
    public final double getFlowRate() {
        return 0.0;
    }
    
    public final int component6() {
        return 0;
    }
    
    public final int getDeviceCount() {
        return 0;
    }
    
    public final int component7() {
        return 0;
    }
    
    public final int getRunningDeviceCount() {
        return 0;
    }
    
    public final float component8() {
        return 0.0F;
    }
    
    public final float getCurrentTemp() {
        return 0.0F;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.wuheng.smart.data.model.CycleMode component9() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.wuheng.smart.data.model.CycleMode getCycleMode() {
        return null;
    }
    
    public final int component10() {
        return 0;
    }
    
    public final int getTemporaryDuration() {
        return 0;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component11() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getSterilizationSchedule() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.wuheng.smart.data.model.SterilizationStatus component12() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.wuheng.smart.data.model.SterilizationStatus getSterilizationStatus() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.wuheng.smart.data.model.FilterInfo> component13() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.wuheng.smart.data.model.FilterInfo> getFilters() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Integer component14() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Integer getTds() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Float component15() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Float getPh() {
        return null;
    }
}