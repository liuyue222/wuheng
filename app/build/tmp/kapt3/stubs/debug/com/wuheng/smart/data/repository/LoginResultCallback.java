package com.wuheng.smart.data.repository;

import com.wuheng.smart.data.model.*;
import com.wuheng.smart.data.network.ApiResult;
import com.wuheng.smart.data.network.ApiService;
import com.wuheng.smart.data.network.AppException;
import com.wuheng.smart.data.network.RetryConfig;
import com.wuheng.smart.data.network.TokenManager;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import kotlinx.coroutines.flow.Flow;
import timber.log.Timber;
import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * 登录结果回调接口
 * 用于在登录成功后执行额外操作（如保存用户数据到数据库）
 */
@kotlin.Metadata(mv = {1, 7, 1}, k = 1, d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u00e6\u0080\u0001\u0018\u00002\u00020\u0001J\u0019\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u00a6@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0006\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006\u0007"}, d2 = {"Lcom/wuheng/smart/data/repository/LoginResultCallback;", "", "onLoginSuccess", "", "response", "Lcom/wuheng/smart/data/model/LoginResponse;", "(Lcom/wuheng/smart/data/model/LoginResponse;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_debug"})
public abstract interface LoginResultCallback {
    
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object onLoginSuccess(@org.jetbrains.annotations.NotNull()
    com.wuheng.smart.data.model.LoginResponse response, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> continuation);
}