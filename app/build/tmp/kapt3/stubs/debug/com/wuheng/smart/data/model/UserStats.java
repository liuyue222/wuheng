package com.wuheng.smart.data.model;

import com.google.gson.annotations.SerializedName;

/**
 * 用户统计数据
 */
@kotlin.Metadata(mv = {1, 7, 1}, k = 1, d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0006\n\u0002\b\u0011\n\u0002\u0010\u000b\n\u0002\b\u0004\b\u0087\b\u0018\u00002\u00020\u0001B7\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0007\u001a\u00020\b\u0012\b\b\u0002\u0010\t\u001a\u00020\b\u00a2\u0006\u0002\u0010\nJ\t\u0010\u0013\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u0014\u001a\u00020\u0005H\u00c6\u0003J\t\u0010\u0015\u001a\u00020\u0005H\u00c6\u0003J\t\u0010\u0016\u001a\u00020\bH\u00c6\u0003J\t\u0010\u0017\u001a\u00020\bH\u00c6\u0003J;\u0010\u0018\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00052\b\b\u0002\u0010\u0007\u001a\u00020\b2\b\b\u0002\u0010\t\u001a\u00020\bH\u00c6\u0001J\u0013\u0010\u0019\u001a\u00020\u001a2\b\u0010\u001b\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010\u001c\u001a\u00020\u0005H\u00d6\u0001J\t\u0010\u001d\u001a\u00020\u0003H\u00d6\u0001R\u0016\u0010\t\u001a\u00020\b8\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0016\u0010\u0007\u001a\u00020\b8\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\r\u0010\fR\u0016\u0010\u0006\u001a\u00020\u00058\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0016\u0010\u0004\u001a\u00020\u00058\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u000fR\u0016\u0010\u0002\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012\u00a8\u0006\u001e"}, d2 = {"Lcom/wuheng/smart/data/model/UserStats;", "", "userId", "", "totalDevices", "", "onlineDevices", "energyUsage", "", "energySaved", "(Ljava/lang/String;IIDD)V", "getEnergySaved", "()D", "getEnergyUsage", "getOnlineDevices", "()I", "getTotalDevices", "getUserId", "()Ljava/lang/String;", "component1", "component2", "component3", "component4", "component5", "copy", "equals", "", "other", "hashCode", "toString", "app_debug"})
public final class UserStats {
    @org.jetbrains.annotations.NotNull()
    @com.google.gson.annotations.SerializedName(value = "userId")
    private final java.lang.String userId = null;
    @com.google.gson.annotations.SerializedName(value = "totalDevices")
    private final int totalDevices = 0;
    @com.google.gson.annotations.SerializedName(value = "onlineDevices")
    private final int onlineDevices = 0;
    @com.google.gson.annotations.SerializedName(value = "energyUsage")
    private final double energyUsage = 0.0;
    @com.google.gson.annotations.SerializedName(value = "energySaved")
    private final double energySaved = 0.0;
    
    /**
     * 用户统计数据
     */
    @org.jetbrains.annotations.NotNull()
    public final com.wuheng.smart.data.model.UserStats copy(@org.jetbrains.annotations.NotNull()
    java.lang.String userId, int totalDevices, int onlineDevices, double energyUsage, double energySaved) {
        return null;
    }
    
    /**
     * 用户统计数据
     */
    @java.lang.Override()
    public boolean equals(@org.jetbrains.annotations.Nullable()
    java.lang.Object other) {
        return false;
    }
    
    /**
     * 用户统计数据
     */
    @java.lang.Override()
    public int hashCode() {
        return 0;
    }
    
    /**
     * 用户统计数据
     */
    @org.jetbrains.annotations.NotNull()
    @java.lang.Override()
    public java.lang.String toString() {
        return null;
    }
    
    public UserStats() {
        super();
    }
    
    public UserStats(@org.jetbrains.annotations.NotNull()
    java.lang.String userId, int totalDevices, int onlineDevices, double energyUsage, double energySaved) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component1() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getUserId() {
        return null;
    }
    
    public final int component2() {
        return 0;
    }
    
    public final int getTotalDevices() {
        return 0;
    }
    
    public final int component3() {
        return 0;
    }
    
    public final int getOnlineDevices() {
        return 0;
    }
    
    public final double component4() {
        return 0.0;
    }
    
    public final double getEnergyUsage() {
        return 0.0;
    }
    
    public final double component5() {
        return 0.0;
    }
    
    public final double getEnergySaved() {
        return 0.0;
    }
}