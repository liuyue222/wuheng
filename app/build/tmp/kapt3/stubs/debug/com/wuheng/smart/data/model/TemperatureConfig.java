package com.wuheng.smart.data.model;

import com.google.gson.annotations.SerializedName;

/**
 * 温度控制范围配置常量
 */
@kotlin.Metadata(mv = {1, 7, 1}, k = 1, d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0006\n\u0002\b\u0005\b\u00c7\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\t"}, d2 = {"Lcom/wuheng/smart/data/model/TemperatureConfig;", "", "()V", "COOLING_MAX_TEMP", "", "COOLING_MIN_TEMP", "HEATING_MAX_TEMP", "HEATING_MIN_TEMP", "TEMP_STEP", "app_debug"})
public final class TemperatureConfig {
    @org.jetbrains.annotations.NotNull()
    public static final com.wuheng.smart.data.model.TemperatureConfig INSTANCE = null;
    public static final double COOLING_MIN_TEMP = 16.0;
    public static final double COOLING_MAX_TEMP = 30.0;
    public static final double HEATING_MIN_TEMP = 16.0;
    public static final double HEATING_MAX_TEMP = 32.0;
    public static final double TEMP_STEP = 0.5;
    
    private TemperatureConfig() {
        super();
    }
}