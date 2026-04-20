package com.wuheng.smart.data.model;

import com.google.gson.annotations.SerializedName;

/**
 * 热力杀菌状态
 *
 * UI映射：热力杀菌卡片
 * - 显示预约时间、上次执行时间、温度设置
 */
@kotlin.Metadata(mv = {1, 7, 1}, k = 1, d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u001a\b\u0087\b\u0018\u00002\u00020\u0001BO\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u0005\u0012\b\b\u0002\u0010\u0007\u001a\u00020\b\u0012\b\b\u0002\u0010\t\u001a\u00020\b\u0012\b\b\u0002\u0010\n\u001a\u00020\b\u0012\b\b\u0002\u0010\u000b\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\fJ\t\u0010\u0016\u001a\u00020\u0003H\u00c6\u0003J\u000b\u0010\u0017\u001a\u0004\u0018\u00010\u0005H\u00c6\u0003J\u000b\u0010\u0018\u001a\u0004\u0018\u00010\u0005H\u00c6\u0003J\t\u0010\u0019\u001a\u00020\bH\u00c6\u0003J\t\u0010\u001a\u001a\u00020\bH\u00c6\u0003J\t\u0010\u001b\u001a\u00020\bH\u00c6\u0003J\t\u0010\u001c\u001a\u00020\u0005H\u00c6\u0003JS\u0010\u001d\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u00052\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u00052\b\b\u0002\u0010\u0007\u001a\u00020\b2\b\b\u0002\u0010\t\u001a\u00020\b2\b\b\u0002\u0010\n\u001a\u00020\b2\b\b\u0002\u0010\u000b\u001a\u00020\u0005H\u00c6\u0001J\u0013\u0010\u001e\u001a\u00020\u00032\b\u0010\u001f\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010 \u001a\u00020\bH\u00d6\u0001J\t\u0010!\u001a\u00020\u0005H\u00d6\u0001R\u0016\u0010\t\u001a\u00020\b8\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u0016\u0010\u0002\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0002\u0010\u000fR\u0018\u0010\u0004\u001a\u0004\u0018\u00010\u00058\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u0018\u0010\u0006\u001a\u0004\u0018\u00010\u00058\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0011R\u0016\u0010\n\u001a\u00020\b8\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u000eR\u0016\u0010\u000b\u001a\u00020\u00058\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0011R\u0016\u0010\u0007\u001a\u00020\b8\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u000e\u00a8\u0006\""}, d2 = {"Lcom/wuheng/smart/data/model/SterilizationStatus;", "", "isActive", "", "lastExecutedTime", "", "nextScheduledTime", "sterilizationTemperature", "", "duration", "scheduleDayOfWeek", "scheduleTime", "(ZLjava/lang/String;Ljava/lang/String;IIILjava/lang/String;)V", "getDuration", "()I", "()Z", "getLastExecutedTime", "()Ljava/lang/String;", "getNextScheduledTime", "getScheduleDayOfWeek", "getScheduleTime", "getSterilizationTemperature", "component1", "component2", "component3", "component4", "component5", "component6", "component7", "copy", "equals", "other", "hashCode", "toString", "app_debug"})
public final class SterilizationStatus {
    @com.google.gson.annotations.SerializedName(value = "isActive")
    private final boolean isActive = false;
    @org.jetbrains.annotations.Nullable()
    @com.google.gson.annotations.SerializedName(value = "lastExecutedTime")
    private final java.lang.String lastExecutedTime = null;
    @org.jetbrains.annotations.Nullable()
    @com.google.gson.annotations.SerializedName(value = "nextScheduledTime")
    private final java.lang.String nextScheduledTime = null;
    @com.google.gson.annotations.SerializedName(value = "sterilizationTemperature")
    private final int sterilizationTemperature = 0;
    @com.google.gson.annotations.SerializedName(value = "duration")
    private final int duration = 0;
    @com.google.gson.annotations.SerializedName(value = "scheduleDayOfWeek")
    private final int scheduleDayOfWeek = 0;
    @org.jetbrains.annotations.NotNull()
    @com.google.gson.annotations.SerializedName(value = "scheduleTime")
    private final java.lang.String scheduleTime = null;
    
    /**
     * 热力杀菌状态
     *
     * UI映射：热力杀菌卡片
     * - 显示预约时间、上次执行时间、温度设置
     */
    @org.jetbrains.annotations.NotNull()
    public final com.wuheng.smart.data.model.SterilizationStatus copy(boolean isActive, @org.jetbrains.annotations.Nullable()
    java.lang.String lastExecutedTime, @org.jetbrains.annotations.Nullable()
    java.lang.String nextScheduledTime, int sterilizationTemperature, int duration, int scheduleDayOfWeek, @org.jetbrains.annotations.NotNull()
    java.lang.String scheduleTime) {
        return null;
    }
    
    /**
     * 热力杀菌状态
     *
     * UI映射：热力杀菌卡片
     * - 显示预约时间、上次执行时间、温度设置
     */
    @java.lang.Override()
    public boolean equals(@org.jetbrains.annotations.Nullable()
    java.lang.Object other) {
        return false;
    }
    
    /**
     * 热力杀菌状态
     *
     * UI映射：热力杀菌卡片
     * - 显示预约时间、上次执行时间、温度设置
     */
    @java.lang.Override()
    public int hashCode() {
        return 0;
    }
    
    /**
     * 热力杀菌状态
     *
     * UI映射：热力杀菌卡片
     * - 显示预约时间、上次执行时间、温度设置
     */
    @org.jetbrains.annotations.NotNull()
    @java.lang.Override()
    public java.lang.String toString() {
        return null;
    }
    
    public SterilizationStatus() {
        super();
    }
    
    public SterilizationStatus(boolean isActive, @org.jetbrains.annotations.Nullable()
    java.lang.String lastExecutedTime, @org.jetbrains.annotations.Nullable()
    java.lang.String nextScheduledTime, int sterilizationTemperature, int duration, int scheduleDayOfWeek, @org.jetbrains.annotations.NotNull()
    java.lang.String scheduleTime) {
        super();
    }
    
    public final boolean component1() {
        return false;
    }
    
    public final boolean isActive() {
        return false;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component2() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getLastExecutedTime() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component3() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getNextScheduledTime() {
        return null;
    }
    
    public final int component4() {
        return 0;
    }
    
    public final int getSterilizationTemperature() {
        return 0;
    }
    
    public final int component5() {
        return 0;
    }
    
    public final int getDuration() {
        return 0;
    }
    
    public final int component6() {
        return 0;
    }
    
    public final int getScheduleDayOfWeek() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component7() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getScheduleTime() {
        return null;
    }
}