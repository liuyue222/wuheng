package com.wuheng.smart.data.model;

import com.google.gson.annotations.SerializedName;

/**
 * 滤芯状态信息 (新版API)
 */
@kotlin.Metadata(mv = {1, 7, 1}, k = 1, d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0012\n\u0002\u0010\u000b\n\u0002\b\u0004\b\u0087\b\u0018\u00002\u00020\u0001B-\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0005\u0012\u0006\u0010\u0007\u001a\u00020\u0003\u0012\u0006\u0010\b\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\tJ\t\u0010\u0011\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u0012\u001a\u00020\u0005H\u00c6\u0003J\t\u0010\u0013\u001a\u00020\u0005H\u00c6\u0003J\t\u0010\u0014\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u0015\u001a\u00020\u0003H\u00c6\u0003J;\u0010\u0016\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00052\b\b\u0002\u0010\u0007\u001a\u00020\u00032\b\b\u0002\u0010\b\u001a\u00020\u0003H\u00c6\u0001J\u0013\u0010\u0017\u001a\u00020\u00182\b\u0010\u0019\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010\u001a\u001a\u00020\u0003H\u00d6\u0001J\t\u0010\u001b\u001a\u00020\u0005H\u00d6\u0001R\u0016\u0010\u0002\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0016\u0010\u0004\u001a\u00020\u00058\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0016\u0010\u0006\u001a\u00020\u00058\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\rR\u0016\u0010\u0007\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u000bR\u0016\u0010\b\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u000b\u00a8\u0006\u001c"}, d2 = {"Lcom/wuheng/smart/data/model/FilterStatusInfo;", "", "filterId", "", "filterName", "", "filterType", "lifePercent", "status", "(ILjava/lang/String;Ljava/lang/String;II)V", "getFilterId", "()I", "getFilterName", "()Ljava/lang/String;", "getFilterType", "getLifePercent", "getStatus", "component1", "component2", "component3", "component4", "component5", "copy", "equals", "", "other", "hashCode", "toString", "app_debug"})
public final class FilterStatusInfo {
    @com.google.gson.annotations.SerializedName(value = "filter_id")
    private final int filterId = 0;
    @org.jetbrains.annotations.NotNull()
    @com.google.gson.annotations.SerializedName(value = "filter_name")
    private final java.lang.String filterName = null;
    @org.jetbrains.annotations.NotNull()
    @com.google.gson.annotations.SerializedName(value = "filter_type")
    private final java.lang.String filterType = null;
    @com.google.gson.annotations.SerializedName(value = "life_percent")
    private final int lifePercent = 0;
    @com.google.gson.annotations.SerializedName(value = "status")
    private final int status = 0;
    
    /**
     * 滤芯状态信息 (新版API)
     */
    @org.jetbrains.annotations.NotNull()
    public final com.wuheng.smart.data.model.FilterStatusInfo copy(int filterId, @org.jetbrains.annotations.NotNull()
    java.lang.String filterName, @org.jetbrains.annotations.NotNull()
    java.lang.String filterType, int lifePercent, int status) {
        return null;
    }
    
    /**
     * 滤芯状态信息 (新版API)
     */
    @java.lang.Override()
    public boolean equals(@org.jetbrains.annotations.Nullable()
    java.lang.Object other) {
        return false;
    }
    
    /**
     * 滤芯状态信息 (新版API)
     */
    @java.lang.Override()
    public int hashCode() {
        return 0;
    }
    
    /**
     * 滤芯状态信息 (新版API)
     */
    @org.jetbrains.annotations.NotNull()
    @java.lang.Override()
    public java.lang.String toString() {
        return null;
    }
    
    public FilterStatusInfo(int filterId, @org.jetbrains.annotations.NotNull()
    java.lang.String filterName, @org.jetbrains.annotations.NotNull()
    java.lang.String filterType, int lifePercent, int status) {
        super();
    }
    
    public final int component1() {
        return 0;
    }
    
    public final int getFilterId() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component2() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getFilterName() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component3() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getFilterType() {
        return null;
    }
    
    public final int component4() {
        return 0;
    }
    
    public final int getLifePercent() {
        return 0;
    }
    
    public final int component5() {
        return 0;
    }
    
    public final int getStatus() {
        return 0;
    }
}