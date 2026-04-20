package com.wuheng.smart.data.model;

import com.google.gson.annotations.SerializedName;

/**
 * 功能菜单项
 *
 * UI映射：功能菜单列表 (图标+箭头+红点)
 * - 耗材使用进度 [>]
 * - 关于新宜能 [>]
 * - 意见反馈 [>]
 * - 清除缓存 [>]
 */
@kotlin.Metadata(mv = {1, 7, 1}, k = 1, d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\b\n\u0002\b\u0018\b\u0087\b\u0018\u00002\u00020\u0001BC\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0005\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0007\u0012\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\t\u0012\b\b\u0002\u0010\n\u001a\u00020\u0007\u00a2\u0006\u0002\u0010\u000bJ\t\u0010\u0015\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u0016\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u0017\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u0018\u001a\u00020\u0007H\u00c6\u0003J\u0010\u0010\u0019\u001a\u0004\u0018\u00010\tH\u00c6\u0003\u00a2\u0006\u0002\u0010\rJ\t\u0010\u001a\u001a\u00020\u0007H\u00c6\u0003JL\u0010\u001b\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00032\b\b\u0002\u0010\u0006\u001a\u00020\u00072\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\t2\b\b\u0002\u0010\n\u001a\u00020\u0007H\u00c6\u0001\u00a2\u0006\u0002\u0010\u001cJ\u0013\u0010\u001d\u001a\u00020\u00072\b\u0010\u001e\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010\u001f\u001a\u00020\tH\u00d6\u0001J\t\u0010 \u001a\u00020\u0003H\u00d6\u0001R\u001a\u0010\b\u001a\u0004\u0018\u00010\t8\u0006X\u0087\u0004\u00a2\u0006\n\n\u0002\u0010\u000e\u001a\u0004\b\f\u0010\rR\u0016\u0010\u0006\u001a\u00020\u00078\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0016\u0010\u0005\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R\u0016\u0010\u0002\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0012R\u0016\u0010\n\u001a\u00020\u00078\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u0010R\u0016\u0010\u0004\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0012\u00a8\u0006!"}, d2 = {"Lcom/wuheng/smart/data/model/MenuItem;", "", "id", "", "title", "iconType", "hasNotification", "", "badgeCount", "", "isEnabled", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;ZLjava/lang/Integer;Z)V", "getBadgeCount", "()Ljava/lang/Integer;", "Ljava/lang/Integer;", "getHasNotification", "()Z", "getIconType", "()Ljava/lang/String;", "getId", "getTitle", "component1", "component2", "component3", "component4", "component5", "component6", "copy", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;ZLjava/lang/Integer;Z)Lcom/wuheng/smart/data/model/MenuItem;", "equals", "other", "hashCode", "toString", "app_debug"})
public final class MenuItem {
    @org.jetbrains.annotations.NotNull()
    @com.google.gson.annotations.SerializedName(value = "id")
    private final java.lang.String id = null;
    @org.jetbrains.annotations.NotNull()
    @com.google.gson.annotations.SerializedName(value = "title")
    private final java.lang.String title = null;
    @org.jetbrains.annotations.NotNull()
    @com.google.gson.annotations.SerializedName(value = "iconType")
    private final java.lang.String iconType = null;
    @com.google.gson.annotations.SerializedName(value = "hasNotification")
    private final boolean hasNotification = false;
    @org.jetbrains.annotations.Nullable()
    @com.google.gson.annotations.SerializedName(value = "badgeCount")
    private final java.lang.Integer badgeCount = null;
    @com.google.gson.annotations.SerializedName(value = "isEnabled")
    private final boolean isEnabled = false;
    
    /**
     * 功能菜单项
     *
     * UI映射：功能菜单列表 (图标+箭头+红点)
     * - 耗材使用进度 [>]
     * - 关于新宜能 [>]
     * - 意见反馈 [>]
     * - 清除缓存 [>]
     */
    @org.jetbrains.annotations.NotNull()
    public final com.wuheng.smart.data.model.MenuItem copy(@org.jetbrains.annotations.NotNull()
    java.lang.String id, @org.jetbrains.annotations.NotNull()
    java.lang.String title, @org.jetbrains.annotations.NotNull()
    java.lang.String iconType, boolean hasNotification, @org.jetbrains.annotations.Nullable()
    java.lang.Integer badgeCount, boolean isEnabled) {
        return null;
    }
    
    /**
     * 功能菜单项
     *
     * UI映射：功能菜单列表 (图标+箭头+红点)
     * - 耗材使用进度 [>]
     * - 关于新宜能 [>]
     * - 意见反馈 [>]
     * - 清除缓存 [>]
     */
    @java.lang.Override()
    public boolean equals(@org.jetbrains.annotations.Nullable()
    java.lang.Object other) {
        return false;
    }
    
    /**
     * 功能菜单项
     *
     * UI映射：功能菜单列表 (图标+箭头+红点)
     * - 耗材使用进度 [>]
     * - 关于新宜能 [>]
     * - 意见反馈 [>]
     * - 清除缓存 [>]
     */
    @java.lang.Override()
    public int hashCode() {
        return 0;
    }
    
    /**
     * 功能菜单项
     *
     * UI映射：功能菜单列表 (图标+箭头+红点)
     * - 耗材使用进度 [>]
     * - 关于新宜能 [>]
     * - 意见反馈 [>]
     * - 清除缓存 [>]
     */
    @org.jetbrains.annotations.NotNull()
    @java.lang.Override()
    public java.lang.String toString() {
        return null;
    }
    
    public MenuItem() {
        super();
    }
    
    public MenuItem(@org.jetbrains.annotations.NotNull()
    java.lang.String id, @org.jetbrains.annotations.NotNull()
    java.lang.String title, @org.jetbrains.annotations.NotNull()
    java.lang.String iconType, boolean hasNotification, @org.jetbrains.annotations.Nullable()
    java.lang.Integer badgeCount, boolean isEnabled) {
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
    public final java.lang.String getTitle() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component3() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getIconType() {
        return null;
    }
    
    public final boolean component4() {
        return false;
    }
    
    public final boolean getHasNotification() {
        return false;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Integer component5() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Integer getBadgeCount() {
        return null;
    }
    
    public final boolean component6() {
        return false;
    }
    
    public final boolean isEnabled() {
        return false;
    }
}