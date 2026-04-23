package com.wuheng.smart.data.repository;

import com.wuheng.smart.data.model.*;
import com.wuheng.smart.data.network.ApiResult;
import com.wuheng.smart.data.network.ApiService;
import com.wuheng.smart.data.network.RetryConfig;
import com.wuheng.smart.data.network.TokenManager;
import kotlinx.coroutines.flow.Flow;
import timber.log.Timber;
import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * 用户数据仓库实现类
 *
 * @param apiService Retrofit API服务
 * @param tokenManager Token管理器，用于登录登出时的Token操作和保存登录凭证
 * @param useMock 是否使用Mock数据，默认false使用真实API
 */
@kotlin.Metadata(mv = {1, 7, 1}, k = 1, d1 = {"\u0000p\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\b\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0007\u0018\u00002\u00020\u00012\u00020\u0002B!\b\u0007\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\b\b\u0002\u0010\u0007\u001a\u00020\b\u00a2\u0006\u0002\u0010\tJ/\u0010\n\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\r0\f0\u000b2\u0006\u0010\u000e\u001a\u00020\u000f2\b\u0010\u0010\u001a\u0004\u0018\u00010\u000fH\u0096@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0011J-\u0010\u0012\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\r0\f0\u000b2\u0006\u0010\u0013\u001a\u00020\u000f2\u0006\u0010\u0014\u001a\u00020\u000fH\u0096@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0011J\u0011\u0010\u0015\u001a\u00020\rH\u0096@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0016J#\u0010\u0017\u001a\u0014\u0012\u0010\u0012\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00190\u00180\f0\u000bH\u0096@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0016J\u000e\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u000f0\u000bH\u0016J\u000e\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u000f0\u000bH\u0016J\u001d\u0010\u001c\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u001d0\f0\u000bH\u0096@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0016J#\u0010\u001e\u001a\u00020\r2\u0006\u0010\u001f\u001a\u00020 2\b\u0010!\u001a\u0004\u0018\u00010\"H\u0082@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010#J\u000e\u0010$\u001a\b\u0012\u0004\u0012\u00020\b0\u000bH\u0016J7\u0010%\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020 0\f0\u000b2\u0006\u0010&\u001a\u00020\u000f2\u0006\u0010\'\u001a\u00020\u000f2\b\u0010!\u001a\u0004\u0018\u00010\"H\u0096@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010(J\u001d\u0010)\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\r0\f0\u000bH\u0096@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0016J%\u0010*\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020+0\f0\u000b2\u0006\u0010,\u001a\u00020-H\u0096@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010.J!\u0010/\u001a\u00020\r2\u0006\u00100\u001a\u00020\u000f2\u0006\u0010\'\u001a\u00020\u000fH\u0096@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0011J%\u00101\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\r0\f0\u000b2\u0006\u0010,\u001a\u000202H\u0096@\u00f8\u0001\u0000\u00a2\u0006\u0002\u00103R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u00064"}, d2 = {"Lcom/wuheng/smart/data/repository/UserRepositoryImpl;", "Lcom/wuheng/smart/data/repository/BaseRepository;", "Lcom/wuheng/smart/data/repository/UserRepository;", "apiService", "Lcom/wuheng/smart/data/network/ApiService;", "tokenManager", "Lcom/wuheng/smart/data/network/TokenManager;", "useMock", "", "(Lcom/wuheng/smart/data/network/ApiService;Lcom/wuheng/smart/data/network/TokenManager;Z)V", "bindHouse", "Lkotlinx/coroutines/flow/Flow;", "Lcom/wuheng/smart/data/network/ApiResult;", "", "houseId", "", "bindCode", "(Ljava/lang/String;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "changePassword", "oldPassword", "newPassword", "clearLoginCredentials", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getMyHouses", "", "Lcom/wuheng/smart/data/model/MyHouse;", "getSavedPassword", "getSavedPhone", "getUserInfo", "Lcom/wuheng/smart/data/model/UserInfo;", "handleLoginSuccess", "response", "Lcom/wuheng/smart/data/model/LoginResponse;", "callback", "Lcom/wuheng/smart/data/repository/LoginResultCallback;", "(Lcom/wuheng/smart/data/model/LoginResponse;Lcom/wuheng/smart/data/repository/LoginResultCallback;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "isRememberPassword", "login", "username", "password", "(Ljava/lang/String;Ljava/lang/String;Lcom/wuheng/smart/data/repository/LoginResultCallback;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "logout", "register", "Lcom/wuheng/smart/data/model/RegisterResponse;", "request", "Lcom/wuheng/smart/data/model/RegisterRequest;", "(Lcom/wuheng/smart/data/model/RegisterRequest;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "saveLoginCredentials", "phone", "updateUserInfo", "Lcom/wuheng/smart/data/model/UpdateUserInfoRequest;", "(Lcom/wuheng/smart/data/model/UpdateUserInfoRequest;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_debug"})
@javax.inject.Singleton()
public final class UserRepositoryImpl extends com.wuheng.smart.data.repository.BaseRepository implements com.wuheng.smart.data.repository.UserRepository {
    private final com.wuheng.smart.data.network.ApiService apiService = null;
    private final com.wuheng.smart.data.network.TokenManager tokenManager = null;
    private final boolean useMock = false;
    
    @javax.inject.Inject()
    public UserRepositoryImpl(@org.jetbrains.annotations.NotNull()
    com.wuheng.smart.data.network.ApiService apiService, @org.jetbrains.annotations.NotNull()
    com.wuheng.smart.data.network.TokenManager tokenManager, boolean useMock) {
        super();
    }
    
    @org.jetbrains.annotations.Nullable()
    @java.lang.Override()
    public java.lang.Object login(@org.jetbrains.annotations.NotNull()
    java.lang.String username, @org.jetbrains.annotations.NotNull()
    java.lang.String password, @org.jetbrains.annotations.Nullable()
    com.wuheng.smart.data.repository.LoginResultCallback callback, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlinx.coroutines.flow.Flow<? extends com.wuheng.smart.data.network.ApiResult<com.wuheng.smart.data.model.LoginResponse>>> continuation) {
        return null;
    }
    
    /**
     * 处理登录成功后的通用逻辑
     */
    private final java.lang.Object handleLoginSuccess(com.wuheng.smart.data.model.LoginResponse response, com.wuheng.smart.data.repository.LoginResultCallback callback, kotlin.coroutines.Continuation<? super kotlin.Unit> continuation) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    @java.lang.Override()
    public java.lang.Object register(@org.jetbrains.annotations.NotNull()
    com.wuheng.smart.data.model.RegisterRequest request, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlinx.coroutines.flow.Flow<? extends com.wuheng.smart.data.network.ApiResult<com.wuheng.smart.data.model.RegisterResponse>>> continuation) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    @java.lang.Override()
    public java.lang.Object logout(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlinx.coroutines.flow.Flow<? extends com.wuheng.smart.data.network.ApiResult<kotlin.Unit>>> continuation) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    @java.lang.Override()
    public java.lang.Object getUserInfo(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlinx.coroutines.flow.Flow<? extends com.wuheng.smart.data.network.ApiResult<com.wuheng.smart.data.model.UserInfo>>> continuation) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    @java.lang.Override()
    public java.lang.Object updateUserInfo(@org.jetbrains.annotations.NotNull()
    com.wuheng.smart.data.model.UpdateUserInfoRequest request, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlinx.coroutines.flow.Flow<? extends com.wuheng.smart.data.network.ApiResult<kotlin.Unit>>> continuation) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    @java.lang.Override()
    public java.lang.Object changePassword(@org.jetbrains.annotations.NotNull()
    java.lang.String oldPassword, @org.jetbrains.annotations.NotNull()
    java.lang.String newPassword, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlinx.coroutines.flow.Flow<? extends com.wuheng.smart.data.network.ApiResult<kotlin.Unit>>> continuation) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    @java.lang.Override()
    public java.lang.Object bindHouse(@org.jetbrains.annotations.NotNull()
    java.lang.String houseId, @org.jetbrains.annotations.Nullable()
    java.lang.String bindCode, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlinx.coroutines.flow.Flow<? extends com.wuheng.smart.data.network.ApiResult<kotlin.Unit>>> continuation) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    @java.lang.Override()
    public java.lang.Object getMyHouses(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlinx.coroutines.flow.Flow<? extends com.wuheng.smart.data.network.ApiResult<? extends java.util.List<com.wuheng.smart.data.model.MyHouse>>>> continuation) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    @java.lang.Override()
    public java.lang.Object saveLoginCredentials(@org.jetbrains.annotations.NotNull()
    java.lang.String phone, @org.jetbrains.annotations.NotNull()
    java.lang.String password, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> continuation) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    @java.lang.Override()
    public java.lang.Object clearLoginCredentials(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> continuation) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    @java.lang.Override()
    public kotlinx.coroutines.flow.Flow<java.lang.String> getSavedPhone() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    @java.lang.Override()
    public kotlinx.coroutines.flow.Flow<java.lang.String> getSavedPassword() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    @java.lang.Override()
    public kotlinx.coroutines.flow.Flow<java.lang.Boolean> isRememberPassword() {
        return null;
    }
}