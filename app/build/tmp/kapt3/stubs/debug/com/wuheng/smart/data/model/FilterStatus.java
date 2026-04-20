package com.wuheng.smart.data.model;

import com.google.gson.annotations.SerializedName;

/**
 * 滤芯状态枚举
 */
@kotlin.Metadata(mv = {1, 7, 1}, k = 1, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0005\b\u0086\u0001\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004j\u0002\b\u0005\u00a8\u0006\u0006"}, d2 = {"Lcom/wuheng/smart/data/model/FilterStatus;", "", "(Ljava/lang/String;I)V", "NORMAL", "WARNING", "REPLACE_NOW", "app_debug"})
public enum FilterStatus {
    /*public static final*/ NORMAL /* = new NORMAL() */,
    /*public static final*/ WARNING /* = new WARNING() */,
    /*public static final*/ REPLACE_NOW /* = new REPLACE_NOW() */;
    
    FilterStatus() {
    }
}