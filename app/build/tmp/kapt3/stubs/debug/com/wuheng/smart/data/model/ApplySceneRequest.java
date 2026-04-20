package com.wuheng.smart.data.model;

import com.google.gson.annotations.SerializedName;

/**
 * 应用场景请求
 */
@kotlin.Metadata(mv = {1, 7, 1}, k = 1, d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\t\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\b\u0087\b\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0005J\t\u0010\t\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\n\u001a\u00020\u0003H\u00c6\u0003J\u001d\u0010\u000b\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0003H\u00c6\u0001J\u0013\u0010\f\u001a\u00020\r2\b\u0010\u000e\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010\u000f\u001a\u00020\u0003H\u00d6\u0001J\t\u0010\u0010\u001a\u00020\u0011H\u00d6\u0001R\u0016\u0010\u0004\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u0016\u0010\u0002\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\b\u0010\u0007\u00a8\u0006\u0012"}, d2 = {"Lcom/wuheng/smart/data/model/ApplySceneRequest;", "", "sceneId", "", "houseId", "(II)V", "getHouseId", "()I", "getSceneId", "component1", "component2", "copy", "equals", "", "other", "hashCode", "toString", "", "app_debug"})
public final class ApplySceneRequest {
    @com.google.gson.annotations.SerializedName(value = "scene_id")
    private final int sceneId = 0;
    @com.google.gson.annotations.SerializedName(value = "house_id")
    private final int houseId = 0;
    
    /**
     * 应用场景请求
     */
    @org.jetbrains.annotations.NotNull()
    public final com.wuheng.smart.data.model.ApplySceneRequest copy(int sceneId, int houseId) {
        return null;
    }
    
    /**
     * 应用场景请求
     */
    @java.lang.Override()
    public boolean equals(@org.jetbrains.annotations.Nullable()
    java.lang.Object other) {
        return false;
    }
    
    /**
     * 应用场景请求
     */
    @java.lang.Override()
    public int hashCode() {
        return 0;
    }
    
    /**
     * 应用场景请求
     */
    @org.jetbrains.annotations.NotNull()
    @java.lang.Override()
    public java.lang.String toString() {
        return null;
    }
    
    public ApplySceneRequest(int sceneId, int houseId) {
        super();
    }
    
    public final int component1() {
        return 0;
    }
    
    public final int getSceneId() {
        return 0;
    }
    
    public final int component2() {
        return 0;
    }
    
    public final int getHouseId() {
        return 0;
    }
}