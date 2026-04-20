package com.wuheng.smart.data.model;

import com.google.gson.annotations.SerializedName;

/**
 * 场景信息
 */
@kotlin.Metadata(mv = {1, 7, 1}, k = 1, d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0002\b!\n\u0002\u0010\u000b\n\u0002\b\u0004\b\u0087\b\u0018\u00002\u00020\u0001BU\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0005\u0012\u0006\u0010\u0007\u001a\u00020\u0005\u0012\u0006\u0010\b\u001a\u00020\u0005\u0012\u0006\u0010\t\u001a\u00020\u0005\u0012\u0006\u0010\n\u001a\u00020\u0003\u0012\u0006\u0010\u000b\u001a\u00020\u0003\u0012\u0006\u0010\f\u001a\u00020\u0003\u0012\u0006\u0010\r\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u000eJ\t\u0010\u001b\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u001c\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u001d\u001a\u00020\u0005H\u00c6\u0003J\t\u0010\u001e\u001a\u00020\u0005H\u00c6\u0003J\t\u0010\u001f\u001a\u00020\u0005H\u00c6\u0003J\t\u0010 \u001a\u00020\u0005H\u00c6\u0003J\t\u0010!\u001a\u00020\u0005H\u00c6\u0003J\t\u0010\"\u001a\u00020\u0003H\u00c6\u0003J\t\u0010#\u001a\u00020\u0003H\u00c6\u0003J\t\u0010$\u001a\u00020\u0003H\u00c6\u0003Jm\u0010%\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00052\b\b\u0002\u0010\u0007\u001a\u00020\u00052\b\b\u0002\u0010\b\u001a\u00020\u00052\b\b\u0002\u0010\t\u001a\u00020\u00052\b\b\u0002\u0010\n\u001a\u00020\u00032\b\b\u0002\u0010\u000b\u001a\u00020\u00032\b\b\u0002\u0010\f\u001a\u00020\u00032\b\b\u0002\u0010\r\u001a\u00020\u0003H\u00c6\u0001J\u0013\u0010&\u001a\u00020\'2\b\u0010(\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010)\u001a\u00020\u0003H\u00d6\u0001J\t\u0010*\u001a\u00020\u0005H\u00d6\u0001R\u0016\u0010\u000b\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0016\u0010\n\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0010R\u0016\u0010\f\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0010R\u0016\u0010\r\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0010R\u0016\u0010\t\u001a\u00020\u00058\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0015R\u0016\u0010\u0002\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0010R\u0016\u0010\u0004\u001a\u00020\u00058\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0015R\u0016\u0010\u0006\u001a\u00020\u00058\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0015R\u0016\u0010\u0007\u001a\u00020\u00058\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u0015R\u0016\u0010\b\u001a\u00020\u00058\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u0015\u00a8\u0006+"}, d2 = {"Lcom/wuheng/smart/data/model/SceneInfo;", "", "sceneId", "", "sceneIdNo", "", "sceneName", "sceneType", "tempSet", "humiditySet", "fanSpeed", "ceilingRadiation", "floorRadiation", "freshAir", "(ILjava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;IIII)V", "getCeilingRadiation", "()I", "getFanSpeed", "getFloorRadiation", "getFreshAir", "getHumiditySet", "()Ljava/lang/String;", "getSceneId", "getSceneIdNo", "getSceneName", "getSceneType", "getTempSet", "component1", "component10", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "equals", "", "other", "hashCode", "toString", "app_debug"})
public final class SceneInfo {
    @com.google.gson.annotations.SerializedName(value = "scene_id")
    private final int sceneId = 0;
    @org.jetbrains.annotations.NotNull()
    @com.google.gson.annotations.SerializedName(value = "scene_id_no")
    private final java.lang.String sceneIdNo = null;
    @org.jetbrains.annotations.NotNull()
    @com.google.gson.annotations.SerializedName(value = "scene_name")
    private final java.lang.String sceneName = null;
    @org.jetbrains.annotations.NotNull()
    @com.google.gson.annotations.SerializedName(value = "scene_type")
    private final java.lang.String sceneType = null;
    @org.jetbrains.annotations.NotNull()
    @com.google.gson.annotations.SerializedName(value = "temp_set")
    private final java.lang.String tempSet = null;
    @org.jetbrains.annotations.NotNull()
    @com.google.gson.annotations.SerializedName(value = "humidity_set")
    private final java.lang.String humiditySet = null;
    @com.google.gson.annotations.SerializedName(value = "fan_speed")
    private final int fanSpeed = 0;
    @com.google.gson.annotations.SerializedName(value = "ceiling_radiation")
    private final int ceilingRadiation = 0;
    @com.google.gson.annotations.SerializedName(value = "floor_radiation")
    private final int floorRadiation = 0;
    @com.google.gson.annotations.SerializedName(value = "fresh_air")
    private final int freshAir = 0;
    
    /**
     * 场景信息
     */
    @org.jetbrains.annotations.NotNull()
    public final com.wuheng.smart.data.model.SceneInfo copy(int sceneId, @org.jetbrains.annotations.NotNull()
    java.lang.String sceneIdNo, @org.jetbrains.annotations.NotNull()
    java.lang.String sceneName, @org.jetbrains.annotations.NotNull()
    java.lang.String sceneType, @org.jetbrains.annotations.NotNull()
    java.lang.String tempSet, @org.jetbrains.annotations.NotNull()
    java.lang.String humiditySet, int fanSpeed, int ceilingRadiation, int floorRadiation, int freshAir) {
        return null;
    }
    
    /**
     * 场景信息
     */
    @java.lang.Override()
    public boolean equals(@org.jetbrains.annotations.Nullable()
    java.lang.Object other) {
        return false;
    }
    
    /**
     * 场景信息
     */
    @java.lang.Override()
    public int hashCode() {
        return 0;
    }
    
    /**
     * 场景信息
     */
    @org.jetbrains.annotations.NotNull()
    @java.lang.Override()
    public java.lang.String toString() {
        return null;
    }
    
    public SceneInfo(int sceneId, @org.jetbrains.annotations.NotNull()
    java.lang.String sceneIdNo, @org.jetbrains.annotations.NotNull()
    java.lang.String sceneName, @org.jetbrains.annotations.NotNull()
    java.lang.String sceneType, @org.jetbrains.annotations.NotNull()
    java.lang.String tempSet, @org.jetbrains.annotations.NotNull()
    java.lang.String humiditySet, int fanSpeed, int ceilingRadiation, int floorRadiation, int freshAir) {
        super();
    }
    
    public final int component1() {
        return 0;
    }
    
    public final int getSceneId() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component2() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getSceneIdNo() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component3() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getSceneName() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component4() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getSceneType() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component5() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getTempSet() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component6() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getHumiditySet() {
        return null;
    }
    
    public final int component7() {
        return 0;
    }
    
    public final int getFanSpeed() {
        return 0;
    }
    
    public final int component8() {
        return 0;
    }
    
    public final int getCeilingRadiation() {
        return 0;
    }
    
    public final int component9() {
        return 0;
    }
    
    public final int getFloorRadiation() {
        return 0;
    }
    
    public final int component10() {
        return 0;
    }
    
    public final int getFreshAir() {
        return 0;
    }
}