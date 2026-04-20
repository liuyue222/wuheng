package com.wuheng.smart.data.model;

import com.google.gson.annotations.SerializedName;

/**
 * 滤芯信息
 *
 * UI映射：全屋净水滤芯卡片 - 进度条动画
 * - 显示滤芯名称、型号、剩余百分比、剩余天数、状态
 *
 * 寿命计算算法说明：
 * - 主要按使用天数计算（totalLifeDays - usedDays）
 * - 可选结合流量计数据（如果设备支持）
 * - 剩余百分比 = (1 - usedDays/totalLifeDays) * 100
 */
@kotlin.Metadata(mv = {1, 7, 1}, k = 1, d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0010\u0007\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u001c\n\u0002\u0010\u000b\n\u0002\b\u0004\b\u0087\b\u0018\u00002\u00020\u0001Bk\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0005\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0007\u001a\u00020\b\u0012\b\b\u0002\u0010\t\u001a\u00020\n\u0012\b\b\u0002\u0010\u000b\u001a\u00020\n\u0012\b\b\u0002\u0010\f\u001a\u00020\n\u0012\b\b\u0002\u0010\r\u001a\u00020\u000e\u0012\n\b\u0002\u0010\u000f\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\u0002\u0010\u0010J\t\u0010\u001f\u001a\u00020\u0003H\u00c6\u0003J\u000b\u0010 \u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J\t\u0010!\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\"\u001a\u00020\u0003H\u00c6\u0003J\t\u0010#\u001a\u00020\u0003H\u00c6\u0003J\t\u0010$\u001a\u00020\bH\u00c6\u0003J\t\u0010%\u001a\u00020\nH\u00c6\u0003J\t\u0010&\u001a\u00020\nH\u00c6\u0003J\t\u0010\'\u001a\u00020\nH\u00c6\u0003J\t\u0010(\u001a\u00020\u000eH\u00c6\u0003Jo\u0010)\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00032\b\b\u0002\u0010\u0006\u001a\u00020\u00032\b\b\u0002\u0010\u0007\u001a\u00020\b2\b\b\u0002\u0010\t\u001a\u00020\n2\b\b\u0002\u0010\u000b\u001a\u00020\n2\b\b\u0002\u0010\f\u001a\u00020\n2\b\b\u0002\u0010\r\u001a\u00020\u000e2\n\b\u0002\u0010\u000f\u001a\u0004\u0018\u00010\u0003H\u00c6\u0001J\u0013\u0010*\u001a\u00020+2\b\u0010,\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010-\u001a\u00020\nH\u00d6\u0001J\t\u0010.\u001a\u00020\u0003H\u00d6\u0001R\u0016\u0010\u0005\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R\u0016\u0010\u0002\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0012R\u0018\u0010\u000f\u001a\u0004\u0018\u00010\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0012R\u0016\u0010\u0004\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0012R\u0016\u0010\t\u001a\u00020\n8\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0017R\u0016\u0010\u0007\u001a\u00020\b8\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0019R\u0016\u0010\r\u001a\u00020\u000e8\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u001bR\u0016\u0010\u000b\u001a\u00020\n8\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u0017R\u0016\u0010\u0006\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\u0012R\u0016\u0010\f\u001a\u00020\n8\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\u0017\u00a8\u0006/"}, d2 = {"Lcom/wuheng/smart/data/model/FilterInfo;", "", "id", "", "name", "filterModel", "type", "remainingPercentage", "", "remainingDays", "", "totalLifeDays", "usedDays", "status", "Lcom/wuheng/smart/data/model/FilterStatus;", "lastReplacedDate", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;FIIILcom/wuheng/smart/data/model/FilterStatus;Ljava/lang/String;)V", "getFilterModel", "()Ljava/lang/String;", "getId", "getLastReplacedDate", "getName", "getRemainingDays", "()I", "getRemainingPercentage", "()F", "getStatus", "()Lcom/wuheng/smart/data/model/FilterStatus;", "getTotalLifeDays", "getType", "getUsedDays", "component1", "component10", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "equals", "", "other", "hashCode", "toString", "app_debug"})
public final class FilterInfo {
    @org.jetbrains.annotations.NotNull()
    @com.google.gson.annotations.SerializedName(value = "id")
    private final java.lang.String id = null;
    @org.jetbrains.annotations.NotNull()
    @com.google.gson.annotations.SerializedName(value = "name")
    private final java.lang.String name = null;
    @org.jetbrains.annotations.NotNull()
    @com.google.gson.annotations.SerializedName(value = "filterModel")
    private final java.lang.String filterModel = null;
    @org.jetbrains.annotations.NotNull()
    @com.google.gson.annotations.SerializedName(value = "type")
    private final java.lang.String type = null;
    @com.google.gson.annotations.SerializedName(value = "remainingPercentage")
    private final float remainingPercentage = 0.0F;
    @com.google.gson.annotations.SerializedName(value = "remainingDays")
    private final int remainingDays = 0;
    @com.google.gson.annotations.SerializedName(value = "totalLifeDays")
    private final int totalLifeDays = 0;
    @com.google.gson.annotations.SerializedName(value = "usedDays")
    private final int usedDays = 0;
    @org.jetbrains.annotations.NotNull()
    @com.google.gson.annotations.SerializedName(value = "status")
    private final com.wuheng.smart.data.model.FilterStatus status = null;
    @org.jetbrains.annotations.Nullable()
    @com.google.gson.annotations.SerializedName(value = "lastReplacedDate")
    private final java.lang.String lastReplacedDate = null;
    
    /**
     * 滤芯信息
     *
     * UI映射：全屋净水滤芯卡片 - 进度条动画
     * - 显示滤芯名称、型号、剩余百分比、剩余天数、状态
     *
     * 寿命计算算法说明：
     * - 主要按使用天数计算（totalLifeDays - usedDays）
     * - 可选结合流量计数据（如果设备支持）
     * - 剩余百分比 = (1 - usedDays/totalLifeDays) * 100
     */
    @org.jetbrains.annotations.NotNull()
    public final com.wuheng.smart.data.model.FilterInfo copy(@org.jetbrains.annotations.NotNull()
    java.lang.String id, @org.jetbrains.annotations.NotNull()
    java.lang.String name, @org.jetbrains.annotations.NotNull()
    java.lang.String filterModel, @org.jetbrains.annotations.NotNull()
    java.lang.String type, float remainingPercentage, int remainingDays, int totalLifeDays, int usedDays, @org.jetbrains.annotations.NotNull()
    com.wuheng.smart.data.model.FilterStatus status, @org.jetbrains.annotations.Nullable()
    java.lang.String lastReplacedDate) {
        return null;
    }
    
    /**
     * 滤芯信息
     *
     * UI映射：全屋净水滤芯卡片 - 进度条动画
     * - 显示滤芯名称、型号、剩余百分比、剩余天数、状态
     *
     * 寿命计算算法说明：
     * - 主要按使用天数计算（totalLifeDays - usedDays）
     * - 可选结合流量计数据（如果设备支持）
     * - 剩余百分比 = (1 - usedDays/totalLifeDays) * 100
     */
    @java.lang.Override()
    public boolean equals(@org.jetbrains.annotations.Nullable()
    java.lang.Object other) {
        return false;
    }
    
    /**
     * 滤芯信息
     *
     * UI映射：全屋净水滤芯卡片 - 进度条动画
     * - 显示滤芯名称、型号、剩余百分比、剩余天数、状态
     *
     * 寿命计算算法说明：
     * - 主要按使用天数计算（totalLifeDays - usedDays）
     * - 可选结合流量计数据（如果设备支持）
     * - 剩余百分比 = (1 - usedDays/totalLifeDays) * 100
     */
    @java.lang.Override()
    public int hashCode() {
        return 0;
    }
    
    /**
     * 滤芯信息
     *
     * UI映射：全屋净水滤芯卡片 - 进度条动画
     * - 显示滤芯名称、型号、剩余百分比、剩余天数、状态
     *
     * 寿命计算算法说明：
     * - 主要按使用天数计算（totalLifeDays - usedDays）
     * - 可选结合流量计数据（如果设备支持）
     * - 剩余百分比 = (1 - usedDays/totalLifeDays) * 100
     */
    @org.jetbrains.annotations.NotNull()
    @java.lang.Override()
    public java.lang.String toString() {
        return null;
    }
    
    public FilterInfo() {
        super();
    }
    
    public FilterInfo(@org.jetbrains.annotations.NotNull()
    java.lang.String id, @org.jetbrains.annotations.NotNull()
    java.lang.String name, @org.jetbrains.annotations.NotNull()
    java.lang.String filterModel, @org.jetbrains.annotations.NotNull()
    java.lang.String type, float remainingPercentage, int remainingDays, int totalLifeDays, int usedDays, @org.jetbrains.annotations.NotNull()
    com.wuheng.smart.data.model.FilterStatus status, @org.jetbrains.annotations.Nullable()
    java.lang.String lastReplacedDate) {
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
    public final java.lang.String getFilterModel() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component4() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getType() {
        return null;
    }
    
    public final float component5() {
        return 0.0F;
    }
    
    public final float getRemainingPercentage() {
        return 0.0F;
    }
    
    public final int component6() {
        return 0;
    }
    
    public final int getRemainingDays() {
        return 0;
    }
    
    public final int component7() {
        return 0;
    }
    
    public final int getTotalLifeDays() {
        return 0;
    }
    
    public final int component8() {
        return 0;
    }
    
    public final int getUsedDays() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.wuheng.smart.data.model.FilterStatus component9() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.wuheng.smart.data.model.FilterStatus getStatus() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component10() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getLastReplacedDate() {
        return null;
    }
}