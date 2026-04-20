package com.wuheng.smart.presentation.base;

import androidx.lifecycle.ViewModel;
import com.wuheng.smart.data.network.ApiResult;
import com.wuheng.smart.data.network.AppException;
import com.wuheng.smart.data.network.AuthEvent;
import com.wuheng.smart.data.network.AuthEventManager;
import kotlinx.coroutines.flow.StateFlow;
import timber.log.Timber;

@kotlin.Metadata(mv = {1, 7, 1}, k = 2, d1 = {"\u00000\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\u001a\u0018\u0010\u0000\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00030\u00020\u0001\"\u0004\b\u0000\u0010\u0003\u001ax\u0010\u0004\u001a\u00020\u0005\"\u0004\b\u0000\u0010\u0003*\u0018\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00030\u00020\u0006j\b\u0012\u0004\u0012\u0002H\u0003`\u00072\u000e\b\u0002\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00050\t2\u0014\b\u0002\u0010\n\u001a\u000e\u0012\u0004\u0012\u0002H\u0003\u0012\u0004\u0012\u00020\u00050\u000b2\u0014\b\u0002\u0010\f\u001a\u000e\u0012\u0004\u0012\u00020\r\u0012\u0004\u0012\u00020\u00050\u000b2\u000e\b\u0002\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00050\tH\u0087\b\u00f8\u0001\u0000\u001ax\u0010\u000f\u001a\u00020\u0005\"\u0004\b\u0000\u0010\u0003*\u0018\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00030\u00020\u0006j\b\u0012\u0004\u0012\u0002H\u0003`\u00072\u000e\b\u0002\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00050\t2\u0014\b\u0002\u0010\n\u001a\u000e\u0012\u0004\u0012\u0002H\u0003\u0012\u0004\u0012\u00020\u00050\u000b2\u0014\b\u0002\u0010\f\u001a\u000e\u0012\u0004\u0012\u00020\r\u0012\u0004\u0012\u00020\u00050\u000b2\u000e\b\u0002\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00050\tH\u0086\b\u00f8\u0001\u0000*(\u0010\u0010\u001a\u0004\b\u0000\u0010\u0003\"\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00030\u00020\u00062\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00030\u00020\u0006\u0082\u0002\u0007\n\u0005\b\u009920\u0001\u00a8\u0006\u0011"}, d2 = {"createUiStateFlow", "Lkotlinx/coroutines/flow/MutableStateFlow;", "Lcom/wuheng/smart/presentation/base/UiDataState;", "T", "collectAsState", "", "Lkotlinx/coroutines/flow/StateFlow;", "Lcom/wuheng/smart/presentation/base/UiState;", "onLoading", "Lkotlin/Function0;", "onSuccess", "Lkotlin/Function1;", "onError", "Lcom/wuheng/smart/data/network/AppException;", "onIdle", "handleUiState", "UiState", "app_debug"})
public final class BaseViewModelKt {
    
    @org.jetbrains.annotations.NotNull()
    public static final <T extends java.lang.Object>kotlinx.coroutines.flow.MutableStateFlow<com.wuheng.smart.presentation.base.UiDataState<T>> createUiStateFlow() {
        return null;
    }
    
    /**
     * 处理UI状态 - 重命名以避免与Compose的collectAsState()冲突
     */
    public static final <T extends java.lang.Object>void handleUiState(@org.jetbrains.annotations.NotNull()
    kotlinx.coroutines.flow.StateFlow<? extends com.wuheng.smart.presentation.base.UiDataState<? extends T>> $this$handleUiState, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onLoading, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super T, kotlin.Unit> onSuccess, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super com.wuheng.smart.data.network.AppException, kotlin.Unit> onError, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onIdle) {
    }
    
    /**
     * 扩展函数：在Compose中收集StateFlow并自动处理生命周期
     * 注意：这个函数名与Compose的collectAsState不同，避免冲突
     */
    @java.lang.Deprecated()
    public static final <T extends java.lang.Object>void collectAsState(@org.jetbrains.annotations.NotNull()
    kotlinx.coroutines.flow.StateFlow<? extends com.wuheng.smart.presentation.base.UiDataState<? extends T>> $this$collectAsState, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onLoading, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super T, kotlin.Unit> onSuccess, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super com.wuheng.smart.data.network.AppException, kotlin.Unit> onError, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onIdle) {
    }
}