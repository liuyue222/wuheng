package com.wuheng.smart.data.model;

import com.google.gson.annotations.SerializedName;

/**
 * 辐射模式配置
 */
@kotlin.Metadata(mv = {1, 7, 1}, k = 1, d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0012\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0087\b\u0018\u00002\u00020\u0001B1\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0005\u0012\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u0007\u0012\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\u0007\u00a2\u0006\u0002\u0010\tJ\t\u0010\u0011\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u0012\u001a\u00020\u0005H\u00c6\u0003J\u0010\u0010\u0013\u001a\u0004\u0018\u00010\u0007H\u00c6\u0003\u00a2\u0006\u0002\u0010\u000bJ\u0010\u0010\u0014\u001a\u0004\u0018\u00010\u0007H\u00c6\u0003\u00a2\u0006\u0002\u0010\u000bJ:\u0010\u0015\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u00072\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\u0007H\u00c6\u0001\u00a2\u0006\u0002\u0010\u0016J\u0013\u0010\u0017\u001a\u00020\u00032\b\u0010\u0018\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010\u0019\u001a\u00020\u001aH\u00d6\u0001J\t\u0010\u001b\u001a\u00020\u001cH\u00d6\u0001R\u001a\u0010\u0006\u001a\u0004\u0018\u00010\u00078\u0006X\u0087\u0004\u00a2\u0006\n\n\u0002\u0010\f\u001a\u0004\b\n\u0010\u000bR\u001a\u0010\b\u001a\u0004\u0018\u00010\u00078\u0006X\u0087\u0004\u00a2\u0006\n\n\u0002\u0010\f\u001a\u0004\b\r\u0010\u000bR\u0016\u0010\u0002\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0002\u0010\u000eR\u0016\u0010\u0004\u001a\u00020\u00058\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010\u00a8\u0006\u001d"}, d2 = {"Lcom/wuheng/smart/data/model/RadiationConfig;", "", "isEnabled", "", "radiationType", "Lcom/wuheng/smart/data/model/RadiationType;", "ceilingTemp", "", "floorTemp", "(ZLcom/wuheng/smart/data/model/RadiationType;Ljava/lang/Float;Ljava/lang/Float;)V", "getCeilingTemp", "()Ljava/lang/Float;", "Ljava/lang/Float;", "getFloorTemp", "()Z", "getRadiationType", "()Lcom/wuheng/smart/data/model/RadiationType;", "component1", "component2", "component3", "component4", "copy", "(ZLcom/wuheng/smart/data/model/RadiationType;Ljava/lang/Float;Ljava/lang/Float;)Lcom/wuheng/smart/data/model/RadiationConfig;", "equals", "other", "hashCode", "", "toString", "", "app_debug"})
public final class RadiationConfig {
    @com.google.gson.annotations.SerializedName(value = "isEnabled")
    private final boolean isEnabled = false;
    @org.jetbrains.annotations.NotNull()
    @com.google.gson.annotations.SerializedName(value = "radiationType")
    private final com.wuheng.smart.data.model.RadiationType radiationType = null;
    @org.jetbrains.annotations.Nullable()
    @com.google.gson.annotations.SerializedName(value = "ceilingTemp")
    private final java.lang.Float ceilingTemp = null;
    @org.jetbrains.annotations.Nullable()
    @com.google.gson.annotations.SerializedName(value = "floorTemp")
    private final java.lang.Float floorTemp = null;
    
    /**
     * 辐射模式配置
     */
    @org.jetbrains.annotations.NotNull()
    public final com.wuheng.smart.data.model.RadiationConfig copy(boolean isEnabled, @org.jetbrains.annotations.NotNull()
    com.wuheng.smart.data.model.RadiationType radiationType, @org.jetbrains.annotations.Nullable()
    java.lang.Float ceilingTemp, @org.jetbrains.annotations.Nullable()
    java.lang.Float floorTemp) {
        return null;
    }
    
    /**
     * 辐射模式配置
     */
    @java.lang.Override()
    public boolean equals(@org.jetbrains.annotations.Nullable()
    java.lang.Object other) {
        return false;
    }
    
    /**
     * 辐射模式配置
     */
    @java.lang.Override()
    public int hashCode() {
        return 0;
    }
    
    /**
     * 辐射模式配置
     */
    @org.jetbrains.annotations.NotNull()
    @java.lang.Override()
    public java.lang.String toString() {
        return null;
    }
    
    public RadiationConfig() {
        super();
    }
    
    public RadiationConfig(boolean isEnabled, @org.jetbrains.annotations.NotNull()
    com.wuheng.smart.data.model.RadiationType radiationType, @org.jetbrains.annotations.Nullable()
    java.lang.Float ceilingTemp, @org.jetbrains.annotations.Nullable()
    java.lang.Float floorTemp) {
        super();
    }
    
    public final boolean component1() {
        return false;
    }
    
    public final boolean isEnabled() {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.wuheng.smart.data.model.RadiationType component2() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.wuheng.smart.data.model.RadiationType getRadiationType() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Float component3() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Float getCeilingTemp() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Float component4() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Float getFloorTemp() {
        return null;
    }
}