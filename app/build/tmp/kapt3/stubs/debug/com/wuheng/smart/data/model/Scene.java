package com.wuheng.smart.data.model;

import com.google.gson.annotations.SerializedName;

/**
 * 智能场景
 *
 * UI组件映射：
 * - 2x2场景网格: name, iconResId, isRunning
 *
 * 四种核心场景定义（来自控制系统文档）:
 * - 会客模式: 温度24°C, 湿度50%, 风速自动
 * - 离家模式: 关闭所有设备, 开启安防
 * - 睡眠模式: 温度26°C, 湿度45%, 风速低
 * - 值守模式: 最低功耗运行
 */
@kotlin.Metadata(mv = {1, 7, 1}, k = 1, d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0007\n\u0002\b\"\b\u0087\b\u0018\u00002\u00020\u0001Bg\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0005\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u0007\u0012\b\b\u0002\u0010\b\u001a\u00020\t\u0012\n\b\u0002\u0010\n\u001a\u0004\u0018\u00010\u000b\u0012\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\u0007\u0012\n\b\u0002\u0010\r\u001a\u0004\u0018\u00010\u0003\u0012\b\b\u0002\u0010\u000e\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u000fJ\t\u0010\u001e\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u001f\u001a\u00020\u0003H\u00c6\u0003J\t\u0010 \u001a\u00020\u0003H\u00c6\u0003J\u0010\u0010!\u001a\u0004\u0018\u00010\u0007H\u00c6\u0003\u00a2\u0006\u0002\u0010\u0014J\t\u0010\"\u001a\u00020\tH\u00c6\u0003J\u0010\u0010#\u001a\u0004\u0018\u00010\u000bH\u00c6\u0003\u00a2\u0006\u0002\u0010\u001cJ\u0010\u0010$\u001a\u0004\u0018\u00010\u0007H\u00c6\u0003\u00a2\u0006\u0002\u0010\u0014J\u000b\u0010%\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J\t\u0010&\u001a\u00020\u0003H\u00c6\u0003Jp\u0010\'\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00032\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u00072\b\b\u0002\u0010\b\u001a\u00020\t2\n\b\u0002\u0010\n\u001a\u0004\u0018\u00010\u000b2\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\u00072\n\b\u0002\u0010\r\u001a\u0004\u0018\u00010\u00032\b\b\u0002\u0010\u000e\u001a\u00020\u0003H\u00c6\u0001\u00a2\u0006\u0002\u0010(J\u0013\u0010)\u001a\u00020\t2\b\u0010*\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010+\u001a\u00020\u0007H\u00d6\u0001J\t\u0010,\u001a\u00020\u0003H\u00d6\u0001R\u0016\u0010\u000e\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u0016\u0010\u0005\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0011R\u001a\u0010\u0006\u001a\u0004\u0018\u00010\u00078\u0006X\u0087\u0004\u00a2\u0006\n\n\u0002\u0010\u0015\u001a\u0004\b\u0013\u0010\u0014R\u0016\u0010\u0002\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0011R\u0016\u0010\b\u001a\u00020\t8\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\b\u0010\u0017R\u0016\u0010\u0004\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0011R\u0018\u0010\r\u001a\u0004\u0018\u00010\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u0011R\u001a\u0010\f\u001a\u0004\u0018\u00010\u00078\u0006X\u0087\u0004\u00a2\u0006\n\n\u0002\u0010\u0015\u001a\u0004\b\u001a\u0010\u0014R\u001a\u0010\n\u001a\u0004\u0018\u00010\u000b8\u0006X\u0087\u0004\u00a2\u0006\n\n\u0002\u0010\u001d\u001a\u0004\b\u001b\u0010\u001c\u00a8\u0006-"}, d2 = {"Lcom/wuheng/smart/data/model/Scene;", "", "id", "", "name", "icon", "iconResId", "", "isRunning", "", "presetTemperature", "", "presetHumidity", "presetFanSpeed", "description", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/Integer;ZLjava/lang/Float;Ljava/lang/Integer;Ljava/lang/String;Ljava/lang/String;)V", "getDescription", "()Ljava/lang/String;", "getIcon", "getIconResId", "()Ljava/lang/Integer;", "Ljava/lang/Integer;", "getId", "()Z", "getName", "getPresetFanSpeed", "getPresetHumidity", "getPresetTemperature", "()Ljava/lang/Float;", "Ljava/lang/Float;", "component1", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/Integer;ZLjava/lang/Float;Ljava/lang/Integer;Ljava/lang/String;Ljava/lang/String;)Lcom/wuheng/smart/data/model/Scene;", "equals", "other", "hashCode", "toString", "app_debug"})
public final class Scene {
    @org.jetbrains.annotations.NotNull()
    @com.google.gson.annotations.SerializedName(value = "id")
    private final java.lang.String id = null;
    @org.jetbrains.annotations.NotNull()
    @com.google.gson.annotations.SerializedName(value = "name")
    private final java.lang.String name = null;
    @org.jetbrains.annotations.NotNull()
    @com.google.gson.annotations.SerializedName(value = "icon")
    private final java.lang.String icon = null;
    @org.jetbrains.annotations.Nullable()
    @com.google.gson.annotations.SerializedName(value = "iconResId")
    private final java.lang.Integer iconResId = null;
    @com.google.gson.annotations.SerializedName(value = "isRunning")
    private final boolean isRunning = false;
    @org.jetbrains.annotations.Nullable()
    @com.google.gson.annotations.SerializedName(value = "presetTemperature")
    private final java.lang.Float presetTemperature = null;
    @org.jetbrains.annotations.Nullable()
    @com.google.gson.annotations.SerializedName(value = "presetHumidity")
    private final java.lang.Integer presetHumidity = null;
    @org.jetbrains.annotations.Nullable()
    @com.google.gson.annotations.SerializedName(value = "presetFanSpeed")
    private final java.lang.String presetFanSpeed = null;
    @org.jetbrains.annotations.NotNull()
    @com.google.gson.annotations.SerializedName(value = "description")
    private final java.lang.String description = null;
    
    /**
     * 智能场景
     *
     * UI组件映射：
     * - 2x2场景网格: name, iconResId, isRunning
     *
     * 四种核心场景定义（来自控制系统文档）:
     * - 会客模式: 温度24°C, 湿度50%, 风速自动
     * - 离家模式: 关闭所有设备, 开启安防
     * - 睡眠模式: 温度26°C, 湿度45%, 风速低
     * - 值守模式: 最低功耗运行
     */
    @org.jetbrains.annotations.NotNull()
    public final com.wuheng.smart.data.model.Scene copy(@org.jetbrains.annotations.NotNull()
    java.lang.String id, @org.jetbrains.annotations.NotNull()
    java.lang.String name, @org.jetbrains.annotations.NotNull()
    java.lang.String icon, @org.jetbrains.annotations.Nullable()
    java.lang.Integer iconResId, boolean isRunning, @org.jetbrains.annotations.Nullable()
    java.lang.Float presetTemperature, @org.jetbrains.annotations.Nullable()
    java.lang.Integer presetHumidity, @org.jetbrains.annotations.Nullable()
    java.lang.String presetFanSpeed, @org.jetbrains.annotations.NotNull()
    java.lang.String description) {
        return null;
    }
    
    /**
     * 智能场景
     *
     * UI组件映射：
     * - 2x2场景网格: name, iconResId, isRunning
     *
     * 四种核心场景定义（来自控制系统文档）:
     * - 会客模式: 温度24°C, 湿度50%, 风速自动
     * - 离家模式: 关闭所有设备, 开启安防
     * - 睡眠模式: 温度26°C, 湿度45%, 风速低
     * - 值守模式: 最低功耗运行
     */
    @java.lang.Override()
    public boolean equals(@org.jetbrains.annotations.Nullable()
    java.lang.Object other) {
        return false;
    }
    
    /**
     * 智能场景
     *
     * UI组件映射：
     * - 2x2场景网格: name, iconResId, isRunning
     *
     * 四种核心场景定义（来自控制系统文档）:
     * - 会客模式: 温度24°C, 湿度50%, 风速自动
     * - 离家模式: 关闭所有设备, 开启安防
     * - 睡眠模式: 温度26°C, 湿度45%, 风速低
     * - 值守模式: 最低功耗运行
     */
    @java.lang.Override()
    public int hashCode() {
        return 0;
    }
    
    /**
     * 智能场景
     *
     * UI组件映射：
     * - 2x2场景网格: name, iconResId, isRunning
     *
     * 四种核心场景定义（来自控制系统文档）:
     * - 会客模式: 温度24°C, 湿度50%, 风速自动
     * - 离家模式: 关闭所有设备, 开启安防
     * - 睡眠模式: 温度26°C, 湿度45%, 风速低
     * - 值守模式: 最低功耗运行
     */
    @org.jetbrains.annotations.NotNull()
    @java.lang.Override()
    public java.lang.String toString() {
        return null;
    }
    
    public Scene() {
        super();
    }
    
    public Scene(@org.jetbrains.annotations.NotNull()
    java.lang.String id, @org.jetbrains.annotations.NotNull()
    java.lang.String name, @org.jetbrains.annotations.NotNull()
    java.lang.String icon, @org.jetbrains.annotations.Nullable()
    java.lang.Integer iconResId, boolean isRunning, @org.jetbrains.annotations.Nullable()
    java.lang.Float presetTemperature, @org.jetbrains.annotations.Nullable()
    java.lang.Integer presetHumidity, @org.jetbrains.annotations.Nullable()
    java.lang.String presetFanSpeed, @org.jetbrains.annotations.NotNull()
    java.lang.String description) {
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
    public final java.lang.String component3() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getIcon() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Integer component4() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Integer getIconResId() {
        return null;
    }
    
    public final boolean component5() {
        return false;
    }
    
    public final boolean isRunning() {
        return false;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Float component6() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Float getPresetTemperature() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Integer component7() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Integer getPresetHumidity() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component8() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getPresetFanSpeed() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component9() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getDescription() {
        return null;
    }
}