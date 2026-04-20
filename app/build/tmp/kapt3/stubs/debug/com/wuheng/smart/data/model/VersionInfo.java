package com.wuheng.smart.data.model;

import com.google.gson.annotations.SerializedName;

/**
 * 应用版本信息
 *
 * UI映射：版本信息区域 (底部居中)
 * - "V1.2.3"
 * - 《隐私服务条款》链接
 */
@kotlin.Metadata(mv = {1, 7, 1}, k = 1, d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0018\b\u0087\b\u0018\u00002\u00020\u0001BG\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0005\u0012\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u0003\u0012\b\b\u0002\u0010\u0007\u001a\u00020\b\u0012\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\n\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\u0002\u0010\u000bJ\t\u0010\u0015\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u0016\u001a\u00020\u0005H\u00c6\u0003J\u000b\u0010\u0017\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J\t\u0010\u0018\u001a\u00020\bH\u00c6\u0003J\u000b\u0010\u0019\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J\u000b\u0010\u001a\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003JK\u0010\u001b\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u00032\b\b\u0002\u0010\u0007\u001a\u00020\b2\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\n\u001a\u0004\u0018\u00010\u0003H\u00c6\u0001J\u0013\u0010\u001c\u001a\u00020\b2\b\u0010\u001d\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010\u001e\u001a\u00020\u0005H\u00d6\u0001J\t\u0010\u001f\u001a\u00020\u0003H\u00d6\u0001R\u0018\u0010\u0006\u001a\u0004\u0018\u00010\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0016\u0010\u0007\u001a\u00020\b8\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0018\u0010\n\u001a\u0004\u0018\u00010\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\rR\u0018\u0010\t\u001a\u0004\u0018\u00010\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\rR\u0016\u0010\u0004\u001a\u00020\u00058\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013R\u0016\u0010\u0002\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\r\u00a8\u0006 "}, d2 = {"Lcom/wuheng/smart/data/model/VersionInfo;", "", "versionName", "", "versionCode", "", "buildDate", "hasUpdate", "", "updateUrl", "updateLog", "(Ljava/lang/String;ILjava/lang/String;ZLjava/lang/String;Ljava/lang/String;)V", "getBuildDate", "()Ljava/lang/String;", "getHasUpdate", "()Z", "getUpdateLog", "getUpdateUrl", "getVersionCode", "()I", "getVersionName", "component1", "component2", "component3", "component4", "component5", "component6", "copy", "equals", "other", "hashCode", "toString", "app_debug"})
public final class VersionInfo {
    @org.jetbrains.annotations.NotNull()
    @com.google.gson.annotations.SerializedName(value = "versionName")
    private final java.lang.String versionName = null;
    @com.google.gson.annotations.SerializedName(value = "versionCode")
    private final int versionCode = 0;
    @org.jetbrains.annotations.Nullable()
    @com.google.gson.annotations.SerializedName(value = "buildDate")
    private final java.lang.String buildDate = null;
    @com.google.gson.annotations.SerializedName(value = "hasUpdate")
    private final boolean hasUpdate = false;
    @org.jetbrains.annotations.Nullable()
    @com.google.gson.annotations.SerializedName(value = "updateUrl")
    private final java.lang.String updateUrl = null;
    @org.jetbrains.annotations.Nullable()
    @com.google.gson.annotations.SerializedName(value = "updateLog")
    private final java.lang.String updateLog = null;
    
    /**
     * 应用版本信息
     *
     * UI映射：版本信息区域 (底部居中)
     * - "V1.2.3"
     * - 《隐私服务条款》链接
     */
    @org.jetbrains.annotations.NotNull()
    public final com.wuheng.smart.data.model.VersionInfo copy(@org.jetbrains.annotations.NotNull()
    java.lang.String versionName, int versionCode, @org.jetbrains.annotations.Nullable()
    java.lang.String buildDate, boolean hasUpdate, @org.jetbrains.annotations.Nullable()
    java.lang.String updateUrl, @org.jetbrains.annotations.Nullable()
    java.lang.String updateLog) {
        return null;
    }
    
    /**
     * 应用版本信息
     *
     * UI映射：版本信息区域 (底部居中)
     * - "V1.2.3"
     * - 《隐私服务条款》链接
     */
    @java.lang.Override()
    public boolean equals(@org.jetbrains.annotations.Nullable()
    java.lang.Object other) {
        return false;
    }
    
    /**
     * 应用版本信息
     *
     * UI映射：版本信息区域 (底部居中)
     * - "V1.2.3"
     * - 《隐私服务条款》链接
     */
    @java.lang.Override()
    public int hashCode() {
        return 0;
    }
    
    /**
     * 应用版本信息
     *
     * UI映射：版本信息区域 (底部居中)
     * - "V1.2.3"
     * - 《隐私服务条款》链接
     */
    @org.jetbrains.annotations.NotNull()
    @java.lang.Override()
    public java.lang.String toString() {
        return null;
    }
    
    public VersionInfo() {
        super();
    }
    
    public VersionInfo(@org.jetbrains.annotations.NotNull()
    java.lang.String versionName, int versionCode, @org.jetbrains.annotations.Nullable()
    java.lang.String buildDate, boolean hasUpdate, @org.jetbrains.annotations.Nullable()
    java.lang.String updateUrl, @org.jetbrains.annotations.Nullable()
    java.lang.String updateLog) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component1() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getVersionName() {
        return null;
    }
    
    public final int component2() {
        return 0;
    }
    
    public final int getVersionCode() {
        return 0;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component3() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getBuildDate() {
        return null;
    }
    
    public final boolean component4() {
        return false;
    }
    
    public final boolean getHasUpdate() {
        return false;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component5() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getUpdateUrl() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component6() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getUpdateLog() {
        return null;
    }
}