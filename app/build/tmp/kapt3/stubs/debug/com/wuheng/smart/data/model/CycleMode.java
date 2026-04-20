package com.wuheng.smart.data.model;

import com.google.gson.annotations.SerializedName;

/**
 * 热水循环模式枚举
 *
 * UI映射：生活热水循环卡片的2x2模式按钮
 * - 全天循环 (CycleMode.ALWAYS)
 * - 定时循环 (CycleMode.SCHEDULE)
 * - 临时循环 (CycleMode.TEMPORARY) - 高亮选中态
 * - 关闭循环 (CycleMode.OFF)
 */
@kotlin.Metadata(mv = {1, 7, 1}, k = 1, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0006\b\u0086\u0001\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004j\u0002\b\u0005j\u0002\b\u0006\u00a8\u0006\u0007"}, d2 = {"Lcom/wuheng/smart/data/model/CycleMode;", "", "(Ljava/lang/String;I)V", "ALWAYS", "SCHEDULE", "TEMPORARY", "OFF", "app_debug"})
public enum CycleMode {
    /*public static final*/ ALWAYS /* = new ALWAYS() */,
    /*public static final*/ SCHEDULE /* = new SCHEDULE() */,
    /*public static final*/ TEMPORARY /* = new TEMPORARY() */,
    /*public static final*/ OFF /* = new OFF() */;
    
    CycleMode() {
    }
}