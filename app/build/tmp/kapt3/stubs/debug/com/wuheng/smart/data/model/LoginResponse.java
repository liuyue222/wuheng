package com.wuheng.smart.data.model;

import com.google.gson.annotations.SerializedName;

/**
 * 用户登录响应
 * 注意：后端返回的user_id、user_type、house_id、status是Int类型
 */
@kotlin.Metadata(mv = {1, 7, 1}, k = 1, d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u001b\n\u0002\u0010\u000b\n\u0002\b\u0004\b\u0087\b\u0018\u00002\u00020\u0001BE\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0005\u0012\u0006\u0010\u0007\u001a\u00020\u0005\u0012\u0006\u0010\b\u001a\u00020\u0005\u0012\u0006\u0010\t\u001a\u00020\u0003\u0012\u0006\u0010\n\u001a\u00020\u0003\u0012\u0006\u0010\u000b\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\fJ\t\u0010\u0017\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u0018\u001a\u00020\u0005H\u00c6\u0003J\t\u0010\u0019\u001a\u00020\u0005H\u00c6\u0003J\t\u0010\u001a\u001a\u00020\u0005H\u00c6\u0003J\t\u0010\u001b\u001a\u00020\u0005H\u00c6\u0003J\t\u0010\u001c\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u001d\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u001e\u001a\u00020\u0003H\u00c6\u0003JY\u0010\u001f\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00052\b\b\u0002\u0010\u0007\u001a\u00020\u00052\b\b\u0002\u0010\b\u001a\u00020\u00052\b\b\u0002\u0010\t\u001a\u00020\u00032\b\b\u0002\u0010\n\u001a\u00020\u00032\b\b\u0002\u0010\u000b\u001a\u00020\u0003H\u00c6\u0001J\u0013\u0010 \u001a\u00020!2\b\u0010\"\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010#\u001a\u00020\u0003H\u00d6\u0001J\t\u0010$\u001a\u00020\u0005H\u00d6\u0001R\u0016\u0010\n\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u0016\u0010\u000b\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u000eR\u0016\u0010\u0002\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u000eR\u0016\u0010\u0004\u001a\u00020\u00058\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R\u0016\u0010\u0006\u001a\u00020\u00058\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0012R\u0016\u0010\u0007\u001a\u00020\u00058\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0012R\u0016\u0010\b\u001a\u00020\u00058\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0012R\u0016\u0010\t\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u000e\u00a8\u0006%"}, d2 = {"Lcom/wuheng/smart/data/model/LoginResponse;", "", "userId", "", "userIdNo", "", "userName", "userTel", "userToken", "userType", "houseId", "status", "(ILjava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;III)V", "getHouseId", "()I", "getStatus", "getUserId", "getUserIdNo", "()Ljava/lang/String;", "getUserName", "getUserTel", "getUserToken", "getUserType", "component1", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "copy", "equals", "", "other", "hashCode", "toString", "app_debug"})
public final class LoginResponse {
    @com.google.gson.annotations.SerializedName(value = "user_id")
    private final int userId = 0;
    @org.jetbrains.annotations.NotNull()
    @com.google.gson.annotations.SerializedName(value = "user_id_no")
    private final java.lang.String userIdNo = null;
    @org.jetbrains.annotations.NotNull()
    @com.google.gson.annotations.SerializedName(value = "user_name")
    private final java.lang.String userName = null;
    @org.jetbrains.annotations.NotNull()
    @com.google.gson.annotations.SerializedName(value = "user_tel")
    private final java.lang.String userTel = null;
    @org.jetbrains.annotations.NotNull()
    @com.google.gson.annotations.SerializedName(value = "user_token")
    private final java.lang.String userToken = null;
    @com.google.gson.annotations.SerializedName(value = "user_type")
    private final int userType = 0;
    @com.google.gson.annotations.SerializedName(value = "house_id")
    private final int houseId = 0;
    @com.google.gson.annotations.SerializedName(value = "status")
    private final int status = 0;
    
    /**
     * 用户登录响应
     * 注意：后端返回的user_id、user_type、house_id、status是Int类型
     */
    @org.jetbrains.annotations.NotNull()
    public final com.wuheng.smart.data.model.LoginResponse copy(int userId, @org.jetbrains.annotations.NotNull()
    java.lang.String userIdNo, @org.jetbrains.annotations.NotNull()
    java.lang.String userName, @org.jetbrains.annotations.NotNull()
    java.lang.String userTel, @org.jetbrains.annotations.NotNull()
    java.lang.String userToken, int userType, int houseId, int status) {
        return null;
    }
    
    /**
     * 用户登录响应
     * 注意：后端返回的user_id、user_type、house_id、status是Int类型
     */
    @java.lang.Override()
    public boolean equals(@org.jetbrains.annotations.Nullable()
    java.lang.Object other) {
        return false;
    }
    
    /**
     * 用户登录响应
     * 注意：后端返回的user_id、user_type、house_id、status是Int类型
     */
    @java.lang.Override()
    public int hashCode() {
        return 0;
    }
    
    /**
     * 用户登录响应
     * 注意：后端返回的user_id、user_type、house_id、status是Int类型
     */
    @org.jetbrains.annotations.NotNull()
    @java.lang.Override()
    public java.lang.String toString() {
        return null;
    }
    
    public LoginResponse(int userId, @org.jetbrains.annotations.NotNull()
    java.lang.String userIdNo, @org.jetbrains.annotations.NotNull()
    java.lang.String userName, @org.jetbrains.annotations.NotNull()
    java.lang.String userTel, @org.jetbrains.annotations.NotNull()
    java.lang.String userToken, int userType, int houseId, int status) {
        super();
    }
    
    public final int component1() {
        return 0;
    }
    
    public final int getUserId() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component2() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getUserIdNo() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component3() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getUserName() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component4() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getUserTel() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component5() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getUserToken() {
        return null;
    }
    
    public final int component6() {
        return 0;
    }
    
    public final int getUserType() {
        return 0;
    }
    
    public final int component7() {
        return 0;
    }
    
    public final int getHouseId() {
        return 0;
    }
    
    public final int component8() {
        return 0;
    }
    
    public final int getStatus() {
        return 0;
    }
}