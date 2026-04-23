package com.wuheng.smart.data.model;

import com.google.gson.annotations.SerializedName;

/**
 * 扩展设备信息（用于设备卡片展示）
 * 包含温度、模式等详细运行参数
 */
@kotlin.Metadata(mv = {1, 7, 1}, k = 1, d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0006\n\u0002\b\u001f\n\u0002\u0010\b\n\u0002\b\u0002\b\u0087\b\u0018\u00002\u00020\u0001Ba\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0005\u001a\u00020\u0006\u0012\b\b\u0002\u0010\u0007\u001a\u00020\b\u0012\b\b\u0002\u0010\t\u001a\u00020\u0003\u0012\b\b\u0002\u0010\n\u001a\u00020\u000b\u0012\b\b\u0002\u0010\f\u001a\u00020\r\u0012\n\b\u0002\u0010\u000e\u001a\u0004\u0018\u00010\r\u0012\b\b\u0002\u0010\u000f\u001a\u00020\u000b\u00a2\u0006\u0002\u0010\u0010J\t\u0010\u001f\u001a\u00020\u0003H\u00c6\u0003J\t\u0010 \u001a\u00020\u0003H\u00c6\u0003J\t\u0010!\u001a\u00020\u0006H\u00c6\u0003J\t\u0010\"\u001a\u00020\bH\u00c6\u0003J\t\u0010#\u001a\u00020\u0003H\u00c6\u0003J\t\u0010$\u001a\u00020\u000bH\u00c6\u0003J\t\u0010%\u001a\u00020\rH\u00c6\u0003J\u0010\u0010&\u001a\u0004\u0018\u00010\rH\u00c6\u0003\u00a2\u0006\u0002\u0010\u001bJ\t\u0010\'\u001a\u00020\u000bH\u00c6\u0003Jj\u0010(\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00062\b\b\u0002\u0010\u0007\u001a\u00020\b2\b\b\u0002\u0010\t\u001a\u00020\u00032\b\b\u0002\u0010\n\u001a\u00020\u000b2\b\b\u0002\u0010\f\u001a\u00020\r2\n\b\u0002\u0010\u000e\u001a\u0004\u0018\u00010\r2\b\b\u0002\u0010\u000f\u001a\u00020\u000bH\u00c6\u0001\u00a2\u0006\u0002\u0010)J\u0013\u0010*\u001a\u00020\u000b2\b\u0010+\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010,\u001a\u00020-H\u00d6\u0001J\t\u0010.\u001a\u00020\u0003H\u00d6\u0001R\u0016\u0010\f\u001a\u00020\r8\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R\u0016\u0010\u0002\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014R\u0016\u0010\u000f\u001a\u00020\u000b8\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0015R\u0016\u0010\n\u001a\u00020\u000b8\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u0015R\u0016\u0010\u0004\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0014R\u0016\u0010\t\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0014R\u0016\u0010\u0007\u001a\u00020\b8\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0019R\u001a\u0010\u000e\u001a\u0004\u0018\u00010\r8\u0006X\u0087\u0004\u00a2\u0006\n\n\u0002\u0010\u001c\u001a\u0004\b\u001a\u0010\u001bR\u0016\u0010\u0005\u001a\u00020\u00068\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\u001e\u00a8\u0006/"}, d2 = {"Lcom/wuheng/smart/data/model/DeviceDetail;", "", "id", "", "name", "type", "Lcom/wuheng/smart/data/model/DeviceType;", "status", "Lcom/wuheng/smart/data/model/DeviceRunningStatus;", "roomName", "isOnline", "", "currentTemp", "", "targetTemp", "isCoolingMode", "(Ljava/lang/String;Ljava/lang/String;Lcom/wuheng/smart/data/model/DeviceType;Lcom/wuheng/smart/data/model/DeviceRunningStatus;Ljava/lang/String;ZDLjava/lang/Double;Z)V", "getCurrentTemp", "()D", "getId", "()Ljava/lang/String;", "()Z", "getName", "getRoomName", "getStatus", "()Lcom/wuheng/smart/data/model/DeviceRunningStatus;", "getTargetTemp", "()Ljava/lang/Double;", "Ljava/lang/Double;", "getType", "()Lcom/wuheng/smart/data/model/DeviceType;", "component1", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "(Ljava/lang/String;Ljava/lang/String;Lcom/wuheng/smart/data/model/DeviceType;Lcom/wuheng/smart/data/model/DeviceRunningStatus;Ljava/lang/String;ZDLjava/lang/Double;Z)Lcom/wuheng/smart/data/model/DeviceDetail;", "equals", "other", "hashCode", "", "toString", "app_debug"})
public final class DeviceDetail {
    @org.jetbrains.annotations.NotNull()
    @com.google.gson.annotations.SerializedName(value = "id")
    private final java.lang.String id = null;
    @org.jetbrains.annotations.NotNull()
    @com.google.gson.annotations.SerializedName(value = "name")
    private final java.lang.String name = null;
    @org.jetbrains.annotations.NotNull()
    @com.google.gson.annotations.SerializedName(value = "type")
    private final com.wuheng.smart.data.model.DeviceType type = null;
    @org.jetbrains.annotations.NotNull()
    @com.google.gson.annotations.SerializedName(value = "status")
    private final com.wuheng.smart.data.model.DeviceRunningStatus status = null;
    @org.jetbrains.annotations.NotNull()
    @com.google.gson.annotations.SerializedName(value = "roomName")
    private final java.lang.String roomName = null;
    @com.google.gson.annotations.SerializedName(value = "isOnline")
    private final boolean isOnline = false;
    @com.google.gson.annotations.SerializedName(value = "currentTemp")
    private final double currentTemp = 0.0;
    @org.jetbrains.annotations.Nullable()
    @com.google.gson.annotations.SerializedName(value = "targetTemp")
    private final java.lang.Double targetTemp = null;
    @com.google.gson.annotations.SerializedName(value = "isCoolingMode")
    private final boolean isCoolingMode = false;
    
    /**
     * 扩展设备信息（用于设备卡片展示）
     * 包含温度、模式等详细运行参数
     */
    @org.jetbrains.annotations.NotNull()
    public final com.wuheng.smart.data.model.DeviceDetail copy(@org.jetbrains.annotations.NotNull()
    java.lang.String id, @org.jetbrains.annotations.NotNull()
    java.lang.String name, @org.jetbrains.annotations.NotNull()
    com.wuheng.smart.data.model.DeviceType type, @org.jetbrains.annotations.NotNull()
    com.wuheng.smart.data.model.DeviceRunningStatus status, @org.jetbrains.annotations.NotNull()
    java.lang.String roomName, boolean isOnline, double currentTemp, @org.jetbrains.annotations.Nullable()
    java.lang.Double targetTemp, boolean isCoolingMode) {
        return null;
    }
    
    /**
     * 扩展设备信息（用于设备卡片展示）
     * 包含温度、模式等详细运行参数
     */
    @java.lang.Override()
    public boolean equals(@org.jetbrains.annotations.Nullable()
    java.lang.Object other) {
        return false;
    }
    
    /**
     * 扩展设备信息（用于设备卡片展示）
     * 包含温度、模式等详细运行参数
     */
    @java.lang.Override()
    public int hashCode() {
        return 0;
    }
    
    /**
     * 扩展设备信息（用于设备卡片展示）
     * 包含温度、模式等详细运行参数
     */
    @org.jetbrains.annotations.NotNull()
    @java.lang.Override()
    public java.lang.String toString() {
        return null;
    }
    
    public DeviceDetail() {
        super();
    }
    
    public DeviceDetail(@org.jetbrains.annotations.NotNull()
    java.lang.String id, @org.jetbrains.annotations.NotNull()
    java.lang.String name, @org.jetbrains.annotations.NotNull()
    com.wuheng.smart.data.model.DeviceType type, @org.jetbrains.annotations.NotNull()
    com.wuheng.smart.data.model.DeviceRunningStatus status, @org.jetbrains.annotations.NotNull()
    java.lang.String roomName, boolean isOnline, double currentTemp, @org.jetbrains.annotations.Nullable()
    java.lang.Double targetTemp, boolean isCoolingMode) {
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
    public final com.wuheng.smart.data.model.DeviceType component3() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.wuheng.smart.data.model.DeviceType getType() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.wuheng.smart.data.model.DeviceRunningStatus component4() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.wuheng.smart.data.model.DeviceRunningStatus getStatus() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component5() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getRoomName() {
        return null;
    }
    
    public final boolean component6() {
        return false;
    }
    
    public final boolean isOnline() {
        return false;
    }
    
    public final double component7() {
        return 0.0;
    }
    
    public final double getCurrentTemp() {
        return 0.0;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Double component8() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Double getTargetTemp() {
        return null;
    }
    
    public final boolean component9() {
        return false;
    }
    
    public final boolean isCoolingMode() {
        return false;
    }
}