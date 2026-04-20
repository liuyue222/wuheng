package com.wuheng.smart.data.repository;

import com.wuheng.smart.data.mock.MockDataSource;
import com.wuheng.smart.data.network.ApiResult;
import com.wuheng.smart.data.network.AppException;
import com.wuheng.smart.data.network.BaseResponse;
import kotlinx.coroutines.flow.Flow;
import timber.log.Timber;

/**
 * Repository基类
 *
 * 提供通用的API调用封装和Mock数据支持
 * 所有Repository实现类都应继承此类以获得统一的错误处理和日志记录能力
 */
@kotlin.Metadata(mv = {1, 7, 1}, k = 1, d1 = {"\u0000N\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0010\u0003\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\b\'\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002JA\u0010\u0003\u001a\b\u0012\u0004\u0012\u0002H\u00050\u0004\"\u0004\b\u0000\u0010\u00052\"\u0010\u0003\u001a\u001e\b\u0001\u0012\u0010\u0012\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00050\b0\u0007\u0012\u0006\u0012\u0004\u0018\u00010\u00010\u0006H\u0084@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\tJs\u0010\n\u001a\b\u0012\u0004\u0012\u0002H\u00050\u0004\"\u0004\b\u0000\u0010\u00052\u0006\u0010\u000b\u001a\u00020\f2(\u0010\r\u001a$\b\u0001\u0012\u0016\u0012\u0014\u0012\u0010\u0012\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00050\b0\u000e0\u0007\u0012\u0006\u0012\u0004\u0018\u00010\u00010\u00062\"\u0010\u0003\u001a\u001e\b\u0001\u0012\u0010\u0012\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00050\b0\u0007\u0012\u0006\u0012\u0004\u0018\u00010\u00010\u0006H\u0084@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u000fJm\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00110\u00042\u0006\u0010\u000b\u001a\u00020\f2(\u0010\r\u001a$\b\u0001\u0012\u0016\u0012\u0014\u0012\u0010\u0012\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00110\b0\u000e0\u0007\u0012\u0006\u0012\u0004\u0018\u00010\u00010\u00062\"\u0010\u0003\u001a\u001e\b\u0001\u0012\u0010\u0012\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00110\b0\u0007\u0012\u0006\u0012\u0004\u0018\u00010\u00010\u0006H\u0084@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u000fJ]\u0010\u0012\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00050\u00040\u000e\"\u0004\b\u0000\u0010\u00052\u0006\u0010\u0013\u001a\u00020\u00142\n\b\u0002\u0010\u0015\u001a\u0004\u0018\u00010\u00142$\b\u0004\u0010\u0016\u001a\u001e\b\u0001\u0012\u0010\u0012\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00050\u00040\u0007\u0012\u0006\u0012\u0004\u0018\u00010\u00010\u0006H\u0084\b\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0017J\u0018\u0010\u0018\u001a\u00020\u00112\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0019\u001a\u00020\u001aH\u0004J\u001c\u0010\u001b\u001a\u00020\u00112\u0006\u0010\u0013\u001a\u00020\u00142\n\b\u0002\u0010\u0015\u001a\u0004\u0018\u00010\u0014H\u0004J\"\u0010\u001c\u001a\b\u0012\u0004\u0012\u0002H\u00050\u0004\"\u0004\b\u0000\u0010\u00052\f\u0010\u001d\u001a\b\u0012\u0004\u0012\u0002H\u00050\u001eH\u0004\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006\u001f"}, d2 = {"Lcom/wuheng/smart/data/repository/BaseRepository;", "", "()V", "apiCall", "Lcom/wuheng/smart/data/network/ApiResult;", "T", "Lkotlin/Function1;", "Lkotlin/coroutines/Continuation;", "Lcom/wuheng/smart/data/network/BaseResponse;", "(Lkotlin/jvm/functions/Function1;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "apiCallWithMock", "useMock", "", "mockCall", "Lkotlinx/coroutines/flow/Flow;", "(ZLkotlin/jvm/functions/Function1;Lkotlin/jvm/functions/Function1;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "apiCallWithMockUnit", "", "apiFlow", "operation", "", "params", "block", "(Ljava/lang/String;Ljava/lang/String;Lkotlin/jvm/functions/Function1;)Lkotlinx/coroutines/flow/Flow;", "logError", "error", "", "logOperation", "mockResult", "mockDataProvider", "Lkotlin/Function0;", "app_debug"})
public abstract class BaseRepository {
    
    public BaseRepository() {
        super();
    }
    
    /**
     * 执行API调用，自动处理异常
     *
     * @param apiCall 实际的API调用suspend函数
     * @return 包装后的ApiResult
     */
    @org.jetbrains.annotations.Nullable()
    protected final <T extends java.lang.Object>java.lang.Object apiCall(@org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super kotlin.coroutines.Continuation<? super com.wuheng.smart.data.network.BaseResponse<T>>, ? extends java.lang.Object> apiCall, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.wuheng.smart.data.network.ApiResult<? extends T>> continuation) {
        return null;
    }
    
    /**
     * 通用的API Flow构建器
     * 自动处理Loading状态和结果发射
     *
     * @param operation 操作名称，用于日志记录
     * @param params 操作参数，用于日志记录（可选）
     * @param block 实际的API调用逻辑，返回ApiResult
     * @return Flow<ApiResult<T>> 自动发射Loading和结果的Flow
     */
    @org.jetbrains.annotations.NotNull()
    protected final <T extends java.lang.Object>kotlinx.coroutines.flow.Flow<com.wuheng.smart.data.network.ApiResult<T>> apiFlow(@org.jetbrains.annotations.NotNull()
    java.lang.String operation, @org.jetbrains.annotations.Nullable()
    java.lang.String params, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super kotlin.coroutines.Continuation<? super com.wuheng.smart.data.network.ApiResult<? extends T>>, ? extends java.lang.Object> block) {
        return null;
    }
    
    /**
     * 带Mock支持的API调用
     * 根据useMock参数决定使用Mock数据还是真实API
     *
     * @param useMock 是否使用Mock数据
     * @param mockCall Mock数据源调用
     * @param apiCall 真实API调用
     * @return 包装后的ApiResult
     */
    @org.jetbrains.annotations.Nullable()
    protected final <T extends java.lang.Object>java.lang.Object apiCallWithMock(boolean useMock, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super kotlin.coroutines.Continuation<? super kotlinx.coroutines.flow.Flow<com.wuheng.smart.data.network.BaseResponse<T>>>, ? extends java.lang.Object> mockCall, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super kotlin.coroutines.Continuation<? super com.wuheng.smart.data.network.BaseResponse<T>>, ? extends java.lang.Object> apiCall, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.wuheng.smart.data.network.ApiResult<? extends T>> continuation) {
        return null;
    }
    
    /**
     * 带Mock支持的Unit类型API调用
     *
     * @param useMock 是否使用Mock数据
     * @param mockCall Mock数据源调用
     * @param apiCall 真实API调用
     * @return 包装后的ApiResult<Unit>
     */
    @org.jetbrains.annotations.Nullable()
    protected final java.lang.Object apiCallWithMockUnit(boolean useMock, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super kotlin.coroutines.Continuation<? super kotlinx.coroutines.flow.Flow<com.wuheng.smart.data.network.BaseResponse<kotlin.Unit>>>, ? extends java.lang.Object> mockCall, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super kotlin.coroutines.Continuation<? super com.wuheng.smart.data.network.BaseResponse<kotlin.Unit>>, ? extends java.lang.Object> apiCall, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.wuheng.smart.data.network.ApiResult<kotlin.Unit>> continuation) {
        return null;
    }
    
    /**
     * 直接返回Mock数据（用于简单的Mock场景）
     *
     * @param mockDataProvider 提供Mock数据的lambda
     * @return 包装后的ApiResult.Success
     */
    @org.jetbrains.annotations.NotNull()
    protected final <T extends java.lang.Object>com.wuheng.smart.data.network.ApiResult<T> mockResult(@org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<? extends T> mockDataProvider) {
        return null;
    }
    
    /**
     * 记录Repository操作日志
     *
     * @param operation 操作名称
     * @param params 操作参数（可选）
     */
    protected final void logOperation(@org.jetbrains.annotations.NotNull()
    java.lang.String operation, @org.jetbrains.annotations.Nullable()
    java.lang.String params) {
    }
    
    /**
     * 记录错误日志
     *
     * @param operation 操作名称
     * @param error 错误信息
     */
    protected final void logError(@org.jetbrains.annotations.NotNull()
    java.lang.String operation, @org.jetbrains.annotations.NotNull()
    java.lang.Throwable error) {
    }
}