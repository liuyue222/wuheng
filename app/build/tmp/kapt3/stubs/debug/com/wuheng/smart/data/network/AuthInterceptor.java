package com.wuheng.smart.data.network;

import okhttp3.Interceptor;
import okhttp3.Response;
import timber.log.Timber;
import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * 认证拦截器
 *
 * 功能：
 * 1. 为每个请求添加 Authorization Header
 * 2. 拦截 401 响应，清除 Token 并发送认证事件
 * 3. 支持自定义 Content-Type 和 Accept Header
 */
@kotlin.Metadata(mv = {1, 7, 1}, k = 1, d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u000f\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\b\u0010\u0005\u001a\u00020\u0006H\u0002J\u0010\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u000b"}, d2 = {"Lcom/wuheng/smart/data/network/AuthInterceptor;", "Lokhttp3/Interceptor;", "tokenManager", "Lcom/wuheng/smart/data/network/TokenManager;", "(Lcom/wuheng/smart/data/network/TokenManager;)V", "handleUnauthorizedResponse", "", "intercept", "Lokhttp3/Response;", "chain", "Lokhttp3/Interceptor$Chain;", "app_debug"})
@javax.inject.Singleton()
public final class AuthInterceptor implements okhttp3.Interceptor {
    private final com.wuheng.smart.data.network.TokenManager tokenManager = null;
    
    @javax.inject.Inject()
    public AuthInterceptor(@org.jetbrains.annotations.NotNull()
    com.wuheng.smart.data.network.TokenManager tokenManager) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    @java.lang.Override()
    public okhttp3.Response intercept(@org.jetbrains.annotations.NotNull()
    okhttp3.Interceptor.Chain chain) {
        return null;
    }
    
    /**
     * 处理 401 响应
     * 清除 Token 并发送认证事件
     */
    private final void handleUnauthorizedResponse() {
    }
}