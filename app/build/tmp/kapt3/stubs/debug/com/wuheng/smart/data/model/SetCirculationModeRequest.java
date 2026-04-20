package com.wuheng.smart.data.model;

import com.google.gson.annotations.SerializedName;

/**
 * 设置循环模式请求
 */
@kotlin.Metadata(mv = {1, 7, 1}, k = 1, d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u000f\n\u0002\u0010\u000b\n\u0002\b\u0004\b\u0087\b\u0018\u00002\u00020\u0001B!\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\u0002\u0010\u0007J\t\u0010\u000f\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u0010\u001a\u00020\u0005H\u00c6\u0003J\u0010\u0010\u0011\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003\u00a2\u0006\u0002\u0010\tJ.\u0010\u0012\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u0003H\u00c6\u0001\u00a2\u0006\u0002\u0010\u0013J\u0013\u0010\u0014\u001a\u00020\u00152\b\u0010\u0016\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010\u0017\u001a\u00020\u0003H\u00d6\u0001J\t\u0010\u0018\u001a\u00020\u0005H\u00d6\u0001R\u001a\u0010\u0006\u001a\u0004\u0018\u00010\u00038\u0006X\u0087\u0004\u00a2\u0006\n\n\u0002\u0010\n\u001a\u0004\b\b\u0010\tR\u0016\u0010\u0002\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0016\u0010\u0004\u001a\u00020\u00058\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000e\u00a8\u0006\u0019"}, d2 = {"Lcom/wuheng/smart/data/model/SetCirculationModeRequest;", "", "houseId", "", "mode", "", "duration", "(ILjava/lang/String;Ljava/lang/Integer;)V", "getDuration", "()Ljava/lang/Integer;", "Ljava/lang/Integer;", "getHouseId", "()I", "getMode", "()Ljava/lang/String;", "component1", "component2", "component3", "copy", "(ILjava/lang/String;Ljava/lang/Integer;)Lcom/wuheng/smart/data/model/SetCirculationModeRequest;", "equals", "", "other", "hashCode", "toString", "app_debug"})
public final class SetCirculationModeRequest {
    @com.google.gson.annotations.SerializedName(value = "house_id")
    private final int houseId = 0;
    @org.jetbrains.annotations.NotNull()
    @com.google.gson.annotations.SerializedName(value = "mode")
    private final java.lang.String mode = null;
    @org.jetbrains.annotations.Nullable()
    @com.google.gson.annotations.SerializedName(value = "duration")
    private final java.lang.Integer duration = null;
    
    /**
     * 设置循环模式请求
     */
    @org.jetbrains.annotations.NotNull()
    public final com.wuheng.smart.data.model.SetCirculationModeRequest copy(int houseId, @org.jetbrains.annotations.NotNull()
    java.lang.String mode, @org.jetbrains.annotations.Nullable()
    java.lang.Integer duration) {
        return null;
    }
    
    /**
     * 设置循环模式请求
     */
    @java.lang.Override()
    public boolean equals(@org.jetbrains.annotations.Nullable()
    java.lang.Object other) {
        return false;
    }
    
    /**
     * 设置循环模式请求
     */
    @java.lang.Override()
    public int hashCode() {
        return 0;
    }
    
    /**
     * 设置循环模式请求
     */
    @org.jetbrains.annotations.NotNull()
    @java.lang.Override()
    public java.lang.String toString() {
        return null;
    }
    
    public SetCirculationModeRequest(int houseId, @org.jetbrains.annotations.NotNull()
    java.lang.String mode, @org.jetbrains.annotations.Nullable()
    java.lang.Integer duration) {
        super();
    }
    
    public final int component1() {
        return 0;
    }
    
    public final int getHouseId() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component2() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getMode() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Integer component3() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Integer getDuration() {
        return null;
    }
}