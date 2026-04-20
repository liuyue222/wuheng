package com.wuheng.smart.data.model;

import com.google.gson.annotations.SerializedName;

/**
 * 设备运行状态枚举
 */
@kotlin.Metadata(mv = {1, 7, 1}, k = 1, d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0000\n\u0002\u0010\u000e\n\u0002\b\n\b\u0086\u0001\u0018\u0000 \f2\b\u0012\u0004\u0012\u00020\u00000\u0001:\u0001\fB\u000f\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006j\u0002\b\u0007j\u0002\b\bj\u0002\b\tj\u0002\b\nj\u0002\b\u000b\u00a8\u0006\r"}, d2 = {"Lcom/wuheng/smart/data/model/DeviceRunStatus;", "", "value", "", "(Ljava/lang/String;ILjava/lang/String;)V", "getValue", "()Ljava/lang/String;", "RUNNING", "STOPPED", "STANDBY", "ERROR", "OFFLINE", "Companion", "app_debug"})
public enum DeviceRunStatus {
    /*public static final*/ RUNNING /* = new RUNNING(null) */,
    /*public static final*/ STOPPED /* = new STOPPED(null) */,
    /*public static final*/ STANDBY /* = new STANDBY(null) */,
    /*public static final*/ ERROR /* = new ERROR(null) */,
    /*public static final*/ OFFLINE /* = new OFFLINE(null) */;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String value = null;
    @org.jetbrains.annotations.NotNull()
    public static final com.wuheng.smart.data.model.DeviceRunStatus.Companion Companion = null;
    
    DeviceRunStatus(java.lang.String value) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getValue() {
        return null;
    }
    
    @kotlin.Metadata(mv = {1, 7, 1}, k = 1, d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u000e\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006\u00a8\u0006\u0007"}, d2 = {"Lcom/wuheng/smart/data/model/DeviceRunStatus$Companion;", "", "()V", "fromValue", "Lcom/wuheng/smart/data/model/DeviceRunStatus;", "value", "", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.wuheng.smart.data.model.DeviceRunStatus fromValue(@org.jetbrains.annotations.NotNull()
        java.lang.String value) {
            return null;
        }
    }
}