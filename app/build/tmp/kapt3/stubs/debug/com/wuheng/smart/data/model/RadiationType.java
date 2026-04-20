package com.wuheng.smart.data.model;

import com.google.gson.annotations.SerializedName;

/**
 * 辐射类型枚举
 * 用于冷暖系统的辐射末端控制（地暖/顶面辐射）
 */
@kotlin.Metadata(mv = {1, 7, 1}, k = 1, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0006\b\u0086\u0001\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004j\u0002\b\u0005j\u0002\b\u0006\u00a8\u0006\u0007"}, d2 = {"Lcom/wuheng/smart/data/model/RadiationType;", "", "(Ljava/lang/String;I)V", "CEILING", "FLOOR", "BOTH", "NONE", "app_debug"})
public enum RadiationType {
    /*public static final*/ CEILING /* = new CEILING() */,
    /*public static final*/ FLOOR /* = new FLOOR() */,
    /*public static final*/ BOTH /* = new BOTH() */,
    /*public static final*/ NONE /* = new NONE() */;
    
    RadiationType() {
    }
}