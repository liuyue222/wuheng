package com.wuheng.smart.data.model;

import com.google.gson.annotations.SerializedName;

/**
 * 绑定房屋请求
 */
@kotlin.Metadata(mv = {1, 7, 1}, k = 1, d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\t\n\u0002\u0010\u000b\n\u0002\b\u0004\b\u0087\b\u0018\u00002\u00020\u0001B\u0019\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u00a2\u0006\u0002\u0010\u0006J\t\u0010\u000b\u001a\u00020\u0003H\u00c6\u0003J\u000b\u0010\f\u001a\u0004\u0018\u00010\u0005H\u00c6\u0003J\u001f\u0010\r\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0005H\u00c6\u0001J\u0013\u0010\u000e\u001a\u00020\u000f2\b\u0010\u0010\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010\u0011\u001a\u00020\u0003H\u00d6\u0001J\t\u0010\u0012\u001a\u00020\u0005H\u00d6\u0001R\u0018\u0010\u0004\u001a\u0004\u0018\u00010\u00058\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0016\u0010\u0002\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n\u00a8\u0006\u0013"}, d2 = {"Lcom/wuheng/smart/data/model/BindHouseRequest;", "", "houseId", "", "bindCode", "", "(ILjava/lang/String;)V", "getBindCode", "()Ljava/lang/String;", "getHouseId", "()I", "component1", "component2", "copy", "equals", "", "other", "hashCode", "toString", "app_debug"})
public final class BindHouseRequest {
    @com.google.gson.annotations.SerializedName(value = "house_id")
    private final int houseId = 0;
    @org.jetbrains.annotations.Nullable()
    @com.google.gson.annotations.SerializedName(value = "bind_code")
    private final java.lang.String bindCode = null;
    
    /**
     * 绑定房屋请求
     */
    @org.jetbrains.annotations.NotNull()
    public final com.wuheng.smart.data.model.BindHouseRequest copy(int houseId, @org.jetbrains.annotations.Nullable()
    java.lang.String bindCode) {
        return null;
    }
    
    /**
     * 绑定房屋请求
     */
    @java.lang.Override()
    public boolean equals(@org.jetbrains.annotations.Nullable()
    java.lang.Object other) {
        return false;
    }
    
    /**
     * 绑定房屋请求
     */
    @java.lang.Override()
    public int hashCode() {
        return 0;
    }
    
    /**
     * 绑定房屋请求
     */
    @org.jetbrains.annotations.NotNull()
    @java.lang.Override()
    public java.lang.String toString() {
        return null;
    }
    
    public BindHouseRequest(int houseId, @org.jetbrains.annotations.Nullable()
    java.lang.String bindCode) {
        super();
    }
    
    public final int component1() {
        return 0;
    }
    
    public final int getHouseId() {
        return 0;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component2() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getBindCode() {
        return null;
    }
}