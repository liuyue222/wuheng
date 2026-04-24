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
 * 用户数据仓库接口
 *
 * 提供用户相关的所有数据操作方法，包括：
 * - 用户认证（登录、注册、登出）
 * - 用户信息管理（获取、更新）
 * - 密码管理（修改密码）
 * - 房屋绑定（获取房屋列表、绑定房屋）
 * - 记住密码功能（保存/清除登录凭证）
 */
@kotlin.Metadata(mv = {1, 7, 1}, k = 1, d1 = {"\u0000^\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\n\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\bf\u0018\u00002\u00020\u0001J1\u0010\u0002\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\u00040\u00032\u0006\u0010\u0006\u001a\u00020\u00072\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\u0007H\u00a6@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\tJ-\u0010\n\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\u00040\u00032\u0006\u0010\u000b\u001a\u00020\u00072\u0006\u0010\f\u001a\u00020\u0007H\u00a6@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\tJ\u0011\u0010\r\u001a\u00020\u0005H\u00a6@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u000eJ-\u0010\u000f\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\u00040\u00032\u0006\u0010\u0010\u001a\u00020\u00072\u0006\u0010\f\u001a\u00020\u0007H\u00a6@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\tJ#\u0010\u0011\u001a\u0014\u0012\u0010\u0012\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00130\u00120\u00040\u0003H\u00a6@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u000eJ\u000e\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00070\u0003H&J\u000e\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00070\u0003H&J\u001d\u0010\u0016\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00170\u00040\u0003H\u00a6@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u000eJ\u000e\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u00190\u0003H&J9\u0010\u001a\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u001b0\u00040\u00032\u0006\u0010\u001c\u001a\u00020\u00072\u0006\u0010\u001d\u001a\u00020\u00072\n\b\u0002\u0010\u001e\u001a\u0004\u0018\u00010\u001fH\u00a6@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010 J\u001d\u0010!\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\u00040\u0003H\u00a6@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u000eJ%\u0010\"\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020#0\u00040\u00032\u0006\u0010$\u001a\u00020%H\u00a6@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010&J!\u0010\'\u001a\u00020\u00052\u0006\u0010(\u001a\u00020\u00072\u0006\u0010\u001d\u001a\u00020\u0007H\u00a6@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\tJ%\u0010)\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\u00040\u00032\u0006\u0010$\u001a\u00020*H\u00a6@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010+\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006,"}, d2 = {"Lcom/wuheng/smart/data/repository/UserRepository;", "", "bindHouse", "Lkotlinx/coroutines/flow/Flow;", "Lcom/wuheng/smart/data/network/ApiResult;", "", "houseId", "", "bindCode", "(Ljava/lang/String;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "changePassword", "oldPassword", "newPassword", "clearLoginCredentials", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "forgotPassword", "mobile", "getMyHouses", "", "Lcom/wuheng/smart/data/model/MyHouse;", "getSavedPassword", "getSavedPhone", "getUserInfo", "Lcom/wuheng/smart/data/model/UserInfo;", "isRememberPassword", "", "login", "Lcom/wuheng/smart/data/model/LoginResponse;", "username", "password", "callback", "Lcom/wuheng/smart/data/repository/LoginResultCallback;", "(Ljava/lang/String;Ljava/lang/String;Lcom/wuheng/smart/data/repository/LoginResultCallback;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "logout", "register", "Lcom/wuheng/smart/data/model/RegisterResponse;", "request", "Lcom/wuheng/smart/data/model/RegisterRequest;", "(Lcom/wuheng/smart/data/model/RegisterRequest;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "saveLoginCredentials", "phone", "updateUserInfo", "Lcom/wuheng/smart/data/model/UpdateUserInfoRequest;", "(Lcom/wuheng/smart/data/model/UpdateUserInfoRequest;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_debug"})
public abstract interface UserRepository {
    
    /**
     * 用户登录
     *
     * @param username 用户名或手机号
     * @param password 密码
     * @param callback 登录成功后的回调（可选），用于执行额外操作如保存用户数据到数据库
     * @return 登录响应，包含用户信息和Token
     */
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object login(@org.jetbrains.annotations.NotNull()
    java.lang.String username, @org.jetbrains.annotations.NotNull()
    java.lang.String password, @org.jetbrains.annotations.Nullable()
    com.wuheng.smart.data.repository.LoginResultCallback callback, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlinx.coroutines.flow.Flow<? extends com.wuheng.smart.data.network.ApiResult<com.wuheng.smart.data.model.LoginResponse>>> continuation);
    
    /**
     * 用户注册
     *
     * @param request 注册请求参数
     * @return 注册响应，包含用户ID和Token
     */
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object register(@org.jetbrains.annotations.NotNull()
    com.wuheng.smart.data.model.RegisterRequest request, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlinx.coroutines.flow.Flow<? extends com.wuheng.smart.data.network.ApiResult<com.wuheng.smart.data.model.RegisterResponse>>> continuation);
    
    /**
     * 用户登出
     * 调用后应清除本地Token
     */
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object logout(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlinx.coroutines.flow.Flow<? extends com.wuheng.smart.data.network.ApiResult<kotlin.Unit>>> continuation);
    
    /**
     * 获取当前用户信息
     *
     * @return 用户详细信息
     */
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getUserInfo(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlinx.coroutines.flow.Flow<? extends com.wuheng.smart.data.network.ApiResult<com.wuheng.smart.data.model.UserInfo>>> continuation);
    
    /**
     * 更新用户信息
     *
     * @param request 更新请求参数
     */
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object updateUserInfo(@org.jetbrains.annotations.NotNull()
    com.wuheng.smart.data.model.UpdateUserInfoRequest request, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlinx.coroutines.flow.Flow<? extends com.wuheng.smart.data.network.ApiResult<kotlin.Unit>>> continuation);
    
    /**
     * 修改密码
     *
     * @param oldPassword 旧密码
     * @param newPassword 新密码（至少6位）
     */
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object changePassword(@org.jetbrains.annotations.NotNull()
    java.lang.String oldPassword, @org.jetbrains.annotations.NotNull()
    java.lang.String newPassword, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlinx.coroutines.flow.Flow<? extends com.wuheng.smart.data.network.ApiResult<kotlin.Unit>>> continuation);
    
    /**
     * 绑定房屋
     *
     * @param houseId 房屋ID
     * @param bindCode 绑定码（可选）
     */
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object bindHouse(@org.jetbrains.annotations.NotNull()
    java.lang.String houseId, @org.jetbrains.annotations.Nullable()
    java.lang.String bindCode, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlinx.coroutines.flow.Flow<? extends com.wuheng.smart.data.network.ApiResult<kotlin.Unit>>> continuation);
    
    /**
     * 获取当前用户的房屋列表
     *
     * @return 房屋列表
     */
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getMyHouses(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlinx.coroutines.flow.Flow<? extends com.wuheng.smart.data.network.ApiResult<? extends java.util.List<com.wuheng.smart.data.model.MyHouse>>>> continuation);
    
    /**
     * 忘记密码
     *
     * @param mobile 手机号
     * @param newPassword 新密码
     * @return 重置结果
     */
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object forgotPassword(@org.jetbrains.annotations.NotNull()
    java.lang.String mobile, @org.jetbrains.annotations.NotNull()
    java.lang.String newPassword, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlinx.coroutines.flow.Flow<? extends com.wuheng.smart.data.network.ApiResult<kotlin.Unit>>> continuation);
    
    /**
     * 保存登录凭证到本地
     *
     * @param phone 手机号
     * @param password 密码
     */
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object saveLoginCredentials(@org.jetbrains.annotations.NotNull()
    java.lang.String phone, @org.jetbrains.annotations.NotNull()
    java.lang.String password, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> continuation);
    
    /**
     * 清除保存的登录凭证
     */
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object clearLoginCredentials(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> continuation);
    
    /**
     * 获取保存的手机号
     * @return 手机号Flow
     */
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.lang.String> getSavedPhone();
    
    /**
     * 获取保存的密码
     * @return 密码Flow
     */
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.lang.String> getSavedPassword();
    
    /**
     * 检查是否启用了记住密码
     * @return 是否记住密码Flow
     */
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.lang.Boolean> isRememberPassword();
    
    /**
     * 用户数据仓库接口
     *
     * 提供用户相关的所有数据操作方法，包括：
     * - 用户认证（登录、注册、登出）
     * - 用户信息管理（获取、更新）
     * - 密码管理（修改密码）
     * - 房屋绑定（获取房屋列表、绑定房屋）
     * - 记住密码功能（保存/清除登录凭证）
     */
    @kotlin.Metadata(mv = {1, 7, 1}, k = 3)
    public final class DefaultImpls {
    }
}