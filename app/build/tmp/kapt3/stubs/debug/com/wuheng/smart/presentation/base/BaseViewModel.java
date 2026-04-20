package com.wuheng.smart.presentation.base;

import androidx.lifecycle.ViewModel;
import com.wuheng.smart.data.network.ApiResult;
import com.wuheng.smart.data.network.AppException;
import com.wuheng.smart.data.network.AuthEvent;
import com.wuheng.smart.data.network.AuthEventManager;
import kotlinx.coroutines.flow.StateFlow;
import timber.log.Timber;

@kotlin.Metadata(mv = {1, 7, 1}, k = 1, d1 = {"\u0000F\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\n\b\'\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0018\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0002Jz\u0010\t\u001a\u00020\u0004\"\u0004\b\u0000\u0010\n*\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\n0\f0\u000b2\"\u0010\r\u001a\u001e\b\u0001\u0012\u0010\u0012\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\n0\u00100\u000f\u0012\u0006\u0012\u0004\u0018\u00010\u00110\u000e2\u0016\b\u0002\u0010\u0012\u001a\u0010\u0012\u0004\u0012\u0002H\n\u0012\u0004\u0012\u00020\u0004\u0018\u00010\u000e2\u0016\b\u0002\u0010\u0013\u001a\u0010\u0012\u0004\u0012\u00020\u0014\u0012\u0004\u0012\u00020\u0004\u0018\u00010\u000eH\u0004\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0015J\u0084\u0001\u0010\t\u001a\u00020\u0004\"\u0004\b\u0000\u0010\n*\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\n0\f0\u000b2\"\u0010\r\u001a\u001e\b\u0001\u0012\u0010\u0012\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\n0\u00100\u000f\u0012\u0006\u0012\u0004\u0018\u00010\u00110\u000e2\b\b\u0002\u0010\u0016\u001a\u00020\u00062\u0016\b\u0002\u0010\u0012\u001a\u0010\u0012\u0004\u0012\u0002H\n\u0012\u0004\u0012\u00020\u0004\u0018\u00010\u000e2\u0016\b\u0002\u0010\u0013\u001a\u0010\u0012\u0004\u0012\u00020\u0014\u0012\u0004\u0012\u00020\u0004\u0018\u00010\u000eH\u0004\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0017J&\u0010\u0018\u001a\u00020\u0004\"\u0004\b\u0000\u0010\n*\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\n0\f0\u000b2\u0006\u0010\u0007\u001a\u00020\u0014H\u0004J\u001e\u0010\u0019\u001a\u00020\u0004\"\u0004\b\u0000\u0010\n*\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\n0\f0\u000bH\u0004J\u001e\u0010\u001a\u001a\u00020\u0004\"\u0004\b\u0000\u0010\n*\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\n0\f0\u000bH\u0004J+\u0010\u001b\u001a\u00020\u0004\"\u0004\b\u0000\u0010\n*\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\n0\f0\u000b2\u0006\u0010\u001c\u001a\u0002H\nH\u0004\u00a2\u0006\u0002\u0010\u001d\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006\u001e"}, d2 = {"Lcom/wuheng/smart/presentation/base/BaseViewModel;", "Landroidx/lifecycle/ViewModel;", "()V", "handleUnauthorizedError", "", "strategy", "Lcom/wuheng/smart/presentation/base/UnauthorizedStrategy;", "exception", "Lcom/wuheng/smart/data/network/AppException$Unauthorized;", "launchRequest", "T", "Lkotlinx/coroutines/flow/MutableStateFlow;", "Lcom/wuheng/smart/presentation/base/UiDataState;", "request", "Lkotlin/Function1;", "Lkotlin/coroutines/Continuation;", "Lcom/wuheng/smart/data/network/ApiResult;", "", "onSuccess", "onError", "Lcom/wuheng/smart/data/network/AppException;", "(Lkotlinx/coroutines/flow/MutableStateFlow;Lkotlin/jvm/functions/Function1;Lkotlin/jvm/functions/Function1;Lkotlin/jvm/functions/Function1;)V", "unauthorizedStrategy", "(Lkotlinx/coroutines/flow/MutableStateFlow;Lkotlin/jvm/functions/Function1;Lcom/wuheng/smart/presentation/base/UnauthorizedStrategy;Lkotlin/jvm/functions/Function1;Lkotlin/jvm/functions/Function1;)V", "setError", "setIdle", "setLoading", "setSuccess", "data", "(Lkotlinx/coroutines/flow/MutableStateFlow;Ljava/lang/Object;)V", "app_debug"})
public abstract class BaseViewModel extends androidx.lifecycle.ViewModel {
    
    public BaseViewModel() {
        super();
    }
    
    /**
     * 统一的请求处理方法
     * 自动处理 401 错误，发送事件通知 UI 层跳转到登录页
     *
     * @param request 请求函数
     * @param unauthorizedStrategy 401 处理策略，默认为自动处理
     * @param onSuccess 成功回调
     * @param onError 错误回调（不包括已自动处理的 401 错误）
     */
    protected final <T extends java.lang.Object>void launchRequest(@org.jetbrains.annotations.NotNull()
    kotlinx.coroutines.flow.MutableStateFlow<com.wuheng.smart.presentation.base.UiDataState<T>> $this$launchRequest, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super kotlin.coroutines.Continuation<? super com.wuheng.smart.data.network.ApiResult<? extends T>>, ? extends java.lang.Object> request, @org.jetbrains.annotations.NotNull()
    com.wuheng.smart.presentation.base.UnauthorizedStrategy unauthorizedStrategy, @org.jetbrains.annotations.Nullable()
    kotlin.jvm.functions.Function1<? super T, kotlin.Unit> onSuccess, @org.jetbrains.annotations.Nullable()
    kotlin.jvm.functions.Function1<? super com.wuheng.smart.data.network.AppException, kotlin.Unit> onError) {
    }
    
    /**
     * 处理 401 Unauthorized 错误
     */
    private final void handleUnauthorizedError(com.wuheng.smart.presentation.base.UnauthorizedStrategy strategy, com.wuheng.smart.data.network.AppException.Unauthorized exception) {
    }
    
    /**
     * 兼容旧版本的方法签名
     */
    protected final <T extends java.lang.Object>void launchRequest(@org.jetbrains.annotations.NotNull()
    kotlinx.coroutines.flow.MutableStateFlow<com.wuheng.smart.presentation.base.UiDataState<T>> $this$launchRequest, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super kotlin.coroutines.Continuation<? super com.wuheng.smart.data.network.ApiResult<? extends T>>, ? extends java.lang.Object> request, @org.jetbrains.annotations.Nullable()
    kotlin.jvm.functions.Function1<? super T, kotlin.Unit> onSuccess, @org.jetbrains.annotations.Nullable()
    kotlin.jvm.functions.Function1<? super com.wuheng.smart.data.network.AppException, kotlin.Unit> onError) {
    }
    
    protected final <T extends java.lang.Object>void setSuccess(@org.jetbrains.annotations.NotNull()
    kotlinx.coroutines.flow.MutableStateFlow<com.wuheng.smart.presentation.base.UiDataState<T>> $this$setSuccess, T data) {
    }
    
    protected final <T extends java.lang.Object>void setError(@org.jetbrains.annotations.NotNull()
    kotlinx.coroutines.flow.MutableStateFlow<com.wuheng.smart.presentation.base.UiDataState<T>> $this$setError, @org.jetbrains.annotations.NotNull()
    com.wuheng.smart.data.network.AppException exception) {
    }
    
    protected final <T extends java.lang.Object>void setLoading(@org.jetbrains.annotations.NotNull()
    kotlinx.coroutines.flow.MutableStateFlow<com.wuheng.smart.presentation.base.UiDataState<T>> $this$setLoading) {
    }
    
    protected final <T extends java.lang.Object>void setIdle(@org.jetbrains.annotations.NotNull()
    kotlinx.coroutines.flow.MutableStateFlow<com.wuheng.smart.presentation.base.UiDataState<T>> $this$setIdle) {
    }
}