package com.wuheng.smart.data.model;

import com.google.gson.annotations.SerializedName;

/**
 * 系统状态
 */
@kotlin.Metadata(mv = {1, 7, 1}, k = 1, d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u000f\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\b\u0087\b\u0018\u00002\u00020\u0001B)\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\u0007\u00a2\u0006\u0002\u0010\tJ\t\u0010\u0011\u001a\u00020\u0003H\u00c6\u0003J\u000b\u0010\u0012\u001a\u0004\u0018\u00010\u0005H\u00c6\u0003J\t\u0010\u0013\u001a\u00020\u0007H\u00c6\u0003J\t\u0010\u0014\u001a\u00020\u0007H\u00c6\u0003J3\u0010\u0015\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00072\b\b\u0002\u0010\b\u001a\u00020\u0007H\u00c6\u0001J\u0013\u0010\u0016\u001a\u00020\u00172\b\u0010\u0018\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010\u0019\u001a\u00020\u0007H\u00d6\u0001J\t\u0010\u001a\u001a\u00020\u001bH\u00d6\u0001R\u0016\u0010\u0006\u001a\u00020\u00078\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0018\u0010\u0004\u001a\u0004\u0018\u00010\u00058\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0016\u0010\b\u001a\u00020\u00078\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000bR\u0016\u0010\u0002\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010\u00a8\u0006\u001c"}, d2 = {"Lcom/wuheng/smart/data/model/SystemStatus;", "", "systemStatus", "Lcom/wuheng/smart/data/model/SystemStatusInfo;", "houseInfo", "Lcom/wuheng/smart/data/model/HouseInfo;", "deviceCount", "", "onlineCount", "(Lcom/wuheng/smart/data/model/SystemStatusInfo;Lcom/wuheng/smart/data/model/HouseInfo;II)V", "getDeviceCount", "()I", "getHouseInfo", "()Lcom/wuheng/smart/data/model/HouseInfo;", "getOnlineCount", "getSystemStatus", "()Lcom/wuheng/smart/data/model/SystemStatusInfo;", "component1", "component2", "component3", "component4", "copy", "equals", "", "other", "hashCode", "toString", "", "app_debug"})
public final class SystemStatus {
    @org.jetbrains.annotations.NotNull()
    @com.google.gson.annotations.SerializedName(value = "system_status")
    private final com.wuheng.smart.data.model.SystemStatusInfo systemStatus = null;
    @org.jetbrains.annotations.Nullable()
    @com.google.gson.annotations.SerializedName(value = "house_info")
    private final com.wuheng.smart.data.model.HouseInfo houseInfo = null;
    @com.google.gson.annotations.SerializedName(value = "device_count")
    private final int deviceCount = 0;
    @com.google.gson.annotations.SerializedName(value = "online_count")
    private final int onlineCount = 0;
    
    /**
     * 系统状态
     */
    @org.jetbrains.annotations.NotNull()
    public final com.wuheng.smart.data.model.SystemStatus copy(@org.jetbrains.annotations.NotNull()
    com.wuheng.smart.data.model.SystemStatusInfo systemStatus, @org.jetbrains.annotations.Nullable()
    com.wuheng.smart.data.model.HouseInfo houseInfo, int deviceCount, int onlineCount) {
        return null;
    }
    
    /**
     * 系统状态
     */
    @java.lang.Override()
    public boolean equals(@org.jetbrains.annotations.Nullable()
    java.lang.Object other) {
        return false;
    }
    
    /**
     * 系统状态
     */
    @java.lang.Override()
    public int hashCode() {
        return 0;
    }
    
    /**
     * 系统状态
     */
    @org.jetbrains.annotations.NotNull()
    @java.lang.Override()
    public java.lang.String toString() {
        return null;
    }
    
    public SystemStatus(@org.jetbrains.annotations.NotNull()
    com.wuheng.smart.data.model.SystemStatusInfo systemStatus, @org.jetbrains.annotations.Nullable()
    com.wuheng.smart.data.model.HouseInfo houseInfo, int deviceCount, int onlineCount) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.wuheng.smart.data.model.SystemStatusInfo component1() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.wuheng.smart.data.model.SystemStatusInfo getSystemStatus() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.wuheng.smart.data.model.HouseInfo component2() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.wuheng.smart.data.model.HouseInfo getHouseInfo() {
        return null;
    }
    
    public final int component3() {
        return 0;
    }
    
    public final int getDeviceCount() {
        return 0;
    }
    
    public final int component4() {
        return 0;
    }
    
    public final int getOnlineCount() {
        return 0;
    }
}