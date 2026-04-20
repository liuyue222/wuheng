package com.wuheng.smart.data.model;

import com.google.gson.annotations.SerializedName;

/**
 * 湿度控制范围配置常量
 */
@kotlin.Metadata(mv = {1, 7, 1}, k = 1, d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0003\b\u00c7\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0007"}, d2 = {"Lcom/wuheng/smart/data/model/HumidityConfig;", "", "()V", "HUMIDITY_STEP", "", "MAX_HUMIDITY", "MIN_HUMIDITY", "app_debug"})
public final class HumidityConfig {
    @org.jetbrains.annotations.NotNull()
    public static final com.wuheng.smart.data.model.HumidityConfig INSTANCE = null;
    public static final int MIN_HUMIDITY = 30;
    public static final int MAX_HUMIDITY = 70;
    public static final int HUMIDITY_STEP = 5;
    
    private HumidityConfig() {
        super();
    }
}