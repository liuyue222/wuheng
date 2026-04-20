package com.wuheng.smart.data.network;

import kotlinx.coroutines.Dispatchers;
import retrofit2.HttpException;
import timber.log.Timber;
import java.io.IOException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

@kotlin.Metadata(mv = {1, 7, 1}, k = 2, d1 = {"\u00002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a\u001c\u0010\u0000\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u00022\u0006\u0010\u0003\u001a\u00020\u0004H\u0002\u001a\u0014\u0010\u0005\u001a\u0004\u0018\u00010\u00062\b\u0010\u0007\u001a\u0004\u0018\u00010\u0006H\u0002\u001aa\u0010\b\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u00022\u001e\b\u0002\u0010\t\u001a\u0018\b\u0001\u0012\n\u0012\b\u0012\u0004\u0012\u00020\f0\u000b\u0012\u0006\u0012\u0004\u0018\u00010\r0\n2\"\u0010\u000e\u001a\u001e\b\u0001\u0012\u0010\u0012\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00020\u000f0\u000b\u0012\u0006\u0012\u0004\u0018\u00010\r0\nH\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0010\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006\u0011"}, d2 = {"handleHttpException", "Lcom/wuheng/smart/data/network/ApiResult;", "T", "e", "Lretrofit2/HttpException;", "parseServerErrorMessage", "", "errorBody", "safeApiCall", "emitLoading", "Lkotlin/Function1;", "Lkotlin/coroutines/Continuation;", "", "", "apiCall", "Lcom/wuheng/smart/data/network/BaseResponse;", "(Lkotlin/jvm/functions/Function1;Lkotlin/jvm/functions/Function1;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_debug"})
public final class SafeApiCallKt {
    
    /**
     * 安全的 API 调用包装函数
     * 自动处理异常并转换为 ApiResult
     *
     * 特性：
     * 1. 自动处理 401 Unauthorized 错误，清除 Token 并触发重新登录
     * 2. 网络异常、超时异常统一处理
     * 3. 业务错误码转换
     */
    @org.jetbrains.annotations.Nullable()
    public static final <T extends java.lang.Object>java.lang.Object safeApiCall(@org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super kotlin.coroutines.Continuation<? super kotlin.Unit>, ? extends java.lang.Object> emitLoading, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super kotlin.coroutines.Continuation<? super com.wuheng.smart.data.network.BaseResponse<T>>, ? extends java.lang.Object> apiCall, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.wuheng.smart.data.network.ApiResult<? extends T>> continuation) {
        return null;
    }
    
    /**
     * 处理 HTTP 异常
     * 特别处理 401 Unauthorized 错误
     */
    private static final <T extends java.lang.Object>com.wuheng.smart.data.network.ApiResult<T> handleHttpException(retrofit2.HttpException e) {
        return null;
    }
    
    /**
     * 解析服务器错误信息
     */
    private static final java.lang.String parseServerErrorMessage(java.lang.String errorBody) {
        return null;
    }
}