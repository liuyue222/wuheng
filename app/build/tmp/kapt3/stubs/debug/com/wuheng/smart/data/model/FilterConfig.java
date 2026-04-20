package com.wuheng.smart.data.model;

import com.google.gson.annotations.SerializedName;

/**
 * 滤芯状态阈值配置
 */
@kotlin.Metadata(mv = {1, 7, 1}, k = 1, d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0003\b\u00c7\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0007X\u0086T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\n"}, d2 = {"Lcom/wuheng/smart/data/model/FilterConfig;", "", "()V", "CRITICAL_COLOR", "", "NORMAL_COLOR", "REPLACE_THRESHOLD", "", "WARNING_COLOR", "WARNING_THRESHOLD", "app_debug"})
public final class FilterConfig {
    @org.jetbrains.annotations.NotNull()
    public static final com.wuheng.smart.data.model.FilterConfig INSTANCE = null;
    public static final int WARNING_THRESHOLD = 30;
    public static final int REPLACE_THRESHOLD = 10;
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String NORMAL_COLOR = "#4ADE80";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String WARNING_COLOR = "#FBBF24";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String CRITICAL_COLOR = "#EF4444";
    
    private FilterConfig() {
        super();
    }
}