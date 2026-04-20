package com.wuheng.smart.data.model;

import com.google.gson.annotations.SerializedName;

/**
 * 循环模式配置请求
 */
@kotlin.Metadata(mv = {1, 7, 1}, k = 1, d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u000b\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\b\u0087\b\u0018\u00002\u00020\u0001B\u0019\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u00a2\u0006\u0002\u0010\u0006J\t\u0010\f\u001a\u00020\u0003H\u00c6\u0003J\u0010\u0010\r\u001a\u0004\u0018\u00010\u0005H\u00c6\u0003\u00a2\u0006\u0002\u0010\bJ$\u0010\u000e\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0005H\u00c6\u0001\u00a2\u0006\u0002\u0010\u000fJ\u0013\u0010\u0010\u001a\u00020\u00112\b\u0010\u0012\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010\u0013\u001a\u00020\u0005H\u00d6\u0001J\t\u0010\u0014\u001a\u00020\u0015H\u00d6\u0001R\u001a\u0010\u0004\u001a\u0004\u0018\u00010\u00058\u0006X\u0087\u0004\u00a2\u0006\n\n\u0002\u0010\t\u001a\u0004\b\u0007\u0010\bR\u0016\u0010\u0002\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000b\u00a8\u0006\u0016"}, d2 = {"Lcom/wuheng/smart/data/model/CycleModeRequest;", "", "mode", "Lcom/wuheng/smart/data/model/CycleMode;", "duration", "", "(Lcom/wuheng/smart/data/model/CycleMode;Ljava/lang/Integer;)V", "getDuration", "()Ljava/lang/Integer;", "Ljava/lang/Integer;", "getMode", "()Lcom/wuheng/smart/data/model/CycleMode;", "component1", "component2", "copy", "(Lcom/wuheng/smart/data/model/CycleMode;Ljava/lang/Integer;)Lcom/wuheng/smart/data/model/CycleModeRequest;", "equals", "", "other", "hashCode", "toString", "", "app_debug"})
public final class CycleModeRequest {
    @org.jetbrains.annotations.NotNull()
    @com.google.gson.annotations.SerializedName(value = "mode")
    private final com.wuheng.smart.data.model.CycleMode mode = null;
    @org.jetbrains.annotations.Nullable()
    @com.google.gson.annotations.SerializedName(value = "duration")
    private final java.lang.Integer duration = null;
    
    /**
     * 循环模式配置请求
     */
    @org.jetbrains.annotations.NotNull()
    public final com.wuheng.smart.data.model.CycleModeRequest copy(@org.jetbrains.annotations.NotNull()
    com.wuheng.smart.data.model.CycleMode mode, @org.jetbrains.annotations.Nullable()
    java.lang.Integer duration) {
        return null;
    }
    
    /**
     * 循环模式配置请求
     */
    @java.lang.Override()
    public boolean equals(@org.jetbrains.annotations.Nullable()
    java.lang.Object other) {
        return false;
    }
    
    /**
     * 循环模式配置请求
     */
    @java.lang.Override()
    public int hashCode() {
        return 0;
    }
    
    /**
     * 循环模式配置请求
     */
    @org.jetbrains.annotations.NotNull()
    @java.lang.Override()
    public java.lang.String toString() {
        return null;
    }
    
    public CycleModeRequest(@org.jetbrains.annotations.NotNull()
    com.wuheng.smart.data.model.CycleMode mode, @org.jetbrains.annotations.Nullable()
    java.lang.Integer duration) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.wuheng.smart.data.model.CycleMode component1() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.wuheng.smart.data.model.CycleMode getMode() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Integer component2() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Integer getDuration() {
        return null;
    }
}