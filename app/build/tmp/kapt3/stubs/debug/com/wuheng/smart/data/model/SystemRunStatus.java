package com.wuheng.smart.data.model;

import com.google.gson.annotations.SerializedName;

/**
 * 系统运行状态枚举
 */
@kotlin.Metadata(mv = {1, 7, 1}, k = 1, d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0000\n\u0002\u0010\u000e\n\u0002\b\t\b\u0086\u0001\u0018\u0000 \u000b2\b\u0012\u0004\u0012\u00020\u00000\u0001:\u0001\u000bB\u000f\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006j\u0002\b\u0007j\u0002\b\bj\u0002\b\tj\u0002\b\n\u00a8\u0006\f"}, d2 = {"Lcom/wuheng/smart/data/model/SystemRunStatus;", "", "value", "", "(Ljava/lang/String;ILjava/lang/String;)V", "getValue", "()Ljava/lang/String;", "RUNNING", "STOPPED", "STANDBY", "ERROR", "Companion", "app_debug"})
public enum SystemRunStatus {
    /*public static final*/ RUNNING /* = new RUNNING(null) */,
    /*public static final*/ STOPPED /* = new STOPPED(null) */,
    /*public static final*/ STANDBY /* = new STANDBY(null) */,
    /*public static final*/ ERROR /* = new ERROR(null) */;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String value = null;
    @org.jetbrains.annotations.NotNull()
    public static final com.wuheng.smart.data.model.SystemRunStatus.Companion Companion = null;
    
    SystemRunStatus(java.lang.String value) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getValue() {
        return null;
    }
    
    @kotlin.Metadata(mv = {1, 7, 1}, k = 1, d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u000e\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006\u00a8\u0006\u0007"}, d2 = {"Lcom/wuheng/smart/data/model/SystemRunStatus$Companion;", "", "()V", "fromValue", "Lcom/wuheng/smart/data/model/SystemRunStatus;", "value", "", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.wuheng.smart.data.model.SystemRunStatus fromValue(@org.jetbrains.annotations.NotNull()
        java.lang.String value) {
            return null;
        }
    }
}