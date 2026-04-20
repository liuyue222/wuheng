package com.wuheng.smart.data.model;

import com.google.gson.annotations.SerializedName;

/**
 * 滤芯状态枚举
 */
@kotlin.Metadata(mv = {1, 7, 1}, k = 1, d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0000\n\u0002\u0010\b\n\u0002\b\b\b\u0086\u0001\u0018\u0000 \n2\b\u0012\u0004\u0012\u00020\u00000\u0001:\u0001\nB\u000f\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006j\u0002\b\u0007j\u0002\b\bj\u0002\b\t\u00a8\u0006\u000b"}, d2 = {"Lcom/wuheng/smart/data/model/FilterLifeStatus;", "", "code", "", "(Ljava/lang/String;II)V", "getCode", "()I", "NORMAL", "WARNING", "CRITICAL", "Companion", "app_debug"})
public enum FilterLifeStatus {
    /*public static final*/ NORMAL /* = new NORMAL(0) */,
    /*public static final*/ WARNING /* = new WARNING(0) */,
    /*public static final*/ CRITICAL /* = new CRITICAL(0) */;
    private final int code = 0;
    @org.jetbrains.annotations.NotNull()
    public static final com.wuheng.smart.data.model.FilterLifeStatus.Companion Companion = null;
    
    FilterLifeStatus(int code) {
    }
    
    public final int getCode() {
        return 0;
    }
    
    @kotlin.Metadata(mv = {1, 7, 1}, k = 1, d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u000e\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006\u00a8\u0006\u0007"}, d2 = {"Lcom/wuheng/smart/data/model/FilterLifeStatus$Companion;", "", "()V", "fromCode", "Lcom/wuheng/smart/data/model/FilterLifeStatus;", "code", "", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.wuheng.smart.data.model.FilterLifeStatus fromCode(int code) {
            return null;
        }
    }
}