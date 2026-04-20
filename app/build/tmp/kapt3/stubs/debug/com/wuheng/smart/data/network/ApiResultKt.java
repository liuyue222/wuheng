package com.wuheng.smart.data.network;

import java.lang.System;

@kotlin.Metadata(mv = {1, 7, 1}, k = 2, d1 = {"\u00008\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a\u0012\u0010\u0000\u001a\u00020\u00012\n\u0010\u0002\u001a\u00060\u0003j\u0002`\u0004\u001a;\u0010\u0005\u001a\b\u0012\u0004\u0012\u0002H\u00070\u0006\"\u0004\b\u0000\u0010\u00072\u001c\u0010\b\u001a\u0018\b\u0001\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00070\n\u0012\u0006\u0012\u0004\u0018\u00010\u000b0\tH\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\f\u001a0\u0010\r\u001a\b\u0012\u0004\u0012\u0002H\u00070\u0006\"\u0004\b\u0000\u0010\u0007*\b\u0012\u0004\u0012\u0002H\u00070\u00062\u0012\u0010\u000e\u001a\u000e\u0012\u0004\u0012\u00020\u0001\u0012\u0004\u0012\u00020\u000f0\t\u001a*\u0010\u0010\u001a\b\u0012\u0004\u0012\u0002H\u00070\u0006\"\u0004\b\u0000\u0010\u0007*\b\u0012\u0004\u0012\u0002H\u00070\u00062\f\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u000f0\u0011\u001a0\u0010\u0012\u001a\b\u0012\u0004\u0012\u0002H\u00070\u0006\"\u0004\b\u0000\u0010\u0007*\b\u0012\u0004\u0012\u0002H\u00070\u00062\u0012\u0010\u000e\u001a\u000e\u0012\u0004\u0012\u0002H\u0007\u0012\u0004\u0012\u00020\u000f0\t\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006\u0013"}, d2 = {"handleApiException", "Lcom/wuheng/smart/data/network/AppException;", "e", "Ljava/lang/Exception;", "Lkotlin/Exception;", "safeApiCall", "Lcom/wuheng/smart/data/network/ApiResult;", "T", "call", "Lkotlin/Function1;", "Lkotlin/coroutines/Continuation;", "", "(Lkotlin/jvm/functions/Function1;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "onError", "action", "", "onLoading", "Lkotlin/Function0;", "onSuccess", "app_debug"})
public final class ApiResultKt {
    
    @org.jetbrains.annotations.NotNull()
    public static final <T extends java.lang.Object>com.wuheng.smart.data.network.ApiResult<T> onSuccess(@org.jetbrains.annotations.NotNull()
    com.wuheng.smart.data.network.ApiResult<? extends T> $this$onSuccess, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super T, kotlin.Unit> action) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public static final <T extends java.lang.Object>com.wuheng.smart.data.network.ApiResult<T> onError(@org.jetbrains.annotations.NotNull()
    com.wuheng.smart.data.network.ApiResult<? extends T> $this$onError, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super com.wuheng.smart.data.network.AppException, kotlin.Unit> action) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public static final <T extends java.lang.Object>com.wuheng.smart.data.network.ApiResult<T> onLoading(@org.jetbrains.annotations.NotNull()
    com.wuheng.smart.data.network.ApiResult<? extends T> $this$onLoading, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> action) {
        return null;
    }
    
    /**
     * 安全的API调用包装函数
     * 自动处理异常并转换为ApiResult
     */
    @org.jetbrains.annotations.Nullable()
    public static final <T extends java.lang.Object>java.lang.Object safeApiCall(@org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super kotlin.coroutines.Continuation<? super T>, ? extends java.lang.Object> call, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.wuheng.smart.data.network.ApiResult<? extends T>> continuation) {
        return null;
    }
    
    /**
     * 处理API异常，转换为AppException
     */
    @org.jetbrains.annotations.NotNull()
    public static final com.wuheng.smart.data.network.AppException handleApiException(@org.jetbrains.annotations.NotNull()
    java.lang.Exception e) {
        return null;
    }
}