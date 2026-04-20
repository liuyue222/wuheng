package com.wuheng.smart.data.model;

import com.google.gson.annotations.SerializedName;

/**
 * 预约滤芯更换请求
 */
@kotlin.Metadata(mv = {1, 7, 1}, k = 1, d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0011\n\u0002\u0010\u000b\n\u0002\b\u0004\b\u0087\b\u0018\u00002\u00020\u0001B9\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u0006\u0012\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\u0006\u0012\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\u0006\u00a2\u0006\u0002\u0010\tJ\t\u0010\u0011\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u0012\u001a\u00020\u0003H\u00c6\u0003J\u000b\u0010\u0013\u001a\u0004\u0018\u00010\u0006H\u00c6\u0003J\u000b\u0010\u0014\u001a\u0004\u0018\u00010\u0006H\u00c6\u0003J\u000b\u0010\u0015\u001a\u0004\u0018\u00010\u0006H\u00c6\u0003JA\u0010\u0016\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u00062\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\u00062\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\u0006H\u00c6\u0001J\u0013\u0010\u0017\u001a\u00020\u00182\b\u0010\u0019\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010\u001a\u001a\u00020\u0003H\u00d6\u0001J\t\u0010\u001b\u001a\u00020\u0006H\u00d6\u0001R\u0018\u0010\b\u001a\u0004\u0018\u00010\u00068\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0018\u0010\u0005\u001a\u0004\u0018\u00010\u00068\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\f\u0010\u000bR\u0018\u0010\u0007\u001a\u0004\u0018\u00010\u00068\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000bR\u0016\u0010\u0004\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0016\u0010\u0002\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u000f\u00a8\u0006\u001c"}, d2 = {"Lcom/wuheng/smart/data/model/BookFilterReplaceRequest;", "", "houseId", "", "filterId", "contactName", "", "contactPhone", "appointmentDate", "(IILjava/lang/String;Ljava/lang/String;Ljava/lang/String;)V", "getAppointmentDate", "()Ljava/lang/String;", "getContactName", "getContactPhone", "getFilterId", "()I", "getHouseId", "component1", "component2", "component3", "component4", "component5", "copy", "equals", "", "other", "hashCode", "toString", "app_debug"})
public final class BookFilterReplaceRequest {
    @com.google.gson.annotations.SerializedName(value = "house_id")
    private final int houseId = 0;
    @com.google.gson.annotations.SerializedName(value = "filter_id")
    private final int filterId = 0;
    @org.jetbrains.annotations.Nullable()
    @com.google.gson.annotations.SerializedName(value = "contact_name")
    private final java.lang.String contactName = null;
    @org.jetbrains.annotations.Nullable()
    @com.google.gson.annotations.SerializedName(value = "contact_phone")
    private final java.lang.String contactPhone = null;
    @org.jetbrains.annotations.Nullable()
    @com.google.gson.annotations.SerializedName(value = "appointment_date")
    private final java.lang.String appointmentDate = null;
    
    /**
     * 预约滤芯更换请求
     */
    @org.jetbrains.annotations.NotNull()
    public final com.wuheng.smart.data.model.BookFilterReplaceRequest copy(int houseId, int filterId, @org.jetbrains.annotations.Nullable()
    java.lang.String contactName, @org.jetbrains.annotations.Nullable()
    java.lang.String contactPhone, @org.jetbrains.annotations.Nullable()
    java.lang.String appointmentDate) {
        return null;
    }
    
    /**
     * 预约滤芯更换请求
     */
    @java.lang.Override()
    public boolean equals(@org.jetbrains.annotations.Nullable()
    java.lang.Object other) {
        return false;
    }
    
    /**
     * 预约滤芯更换请求
     */
    @java.lang.Override()
    public int hashCode() {
        return 0;
    }
    
    /**
     * 预约滤芯更换请求
     */
    @org.jetbrains.annotations.NotNull()
    @java.lang.Override()
    public java.lang.String toString() {
        return null;
    }
    
    public BookFilterReplaceRequest(int houseId, int filterId, @org.jetbrains.annotations.Nullable()
    java.lang.String contactName, @org.jetbrains.annotations.Nullable()
    java.lang.String contactPhone, @org.jetbrains.annotations.Nullable()
    java.lang.String appointmentDate) {
        super();
    }
    
    public final int component1() {
        return 0;
    }
    
    public final int getHouseId() {
        return 0;
    }
    
    public final int component2() {
        return 0;
    }
    
    public final int getFilterId() {
        return 0;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component3() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getContactName() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component4() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getContactPhone() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component5() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getAppointmentDate() {
        return null;
    }
}