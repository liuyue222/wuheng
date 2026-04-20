package com.wuheng.smart.data.network;

import kotlinx.coroutines.flow.SharedFlow;

/**
 * 认证事件管理器
 *
 * 用于在应用层和数据中心层之间传递认证相关事件
 * 采用单例模式，使用 SharedFlow 实现事件订阅
 *
 * 使用场景：
 * 1. 当收到 401 错误时，SafeApiCall 会发送 Unauthorized 事件
 * 2. MainActivity 或其他 UI 层组件订阅此事件，收到后跳转到登录页
 * 3. 登录/登出操作完成后发送相应事件
 */
@kotlin.Metadata(mv = {1, 7, 1}, k = 1, d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0005\b\u00c7\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u000e\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u0005J\u0006\u0010\r\u001a\u00020\u000bJ\u0006\u0010\u000e\u001a\u00020\u000bJ\u0006\u0010\u000f\u001a\u00020\u000bR\u0014\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00050\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\b\u0010\t\u00a8\u0006\u0010"}, d2 = {"Lcom/wuheng/smart/data/network/AuthEventManager;", "", "()V", "_authEvents", "Lkotlinx/coroutines/flow/MutableSharedFlow;", "Lcom/wuheng/smart/data/network/AuthEvent;", "authEvents", "Lkotlinx/coroutines/flow/SharedFlow;", "getAuthEvents", "()Lkotlinx/coroutines/flow/SharedFlow;", "postAuthEvent", "", "event", "postLoginSuccessEvent", "postLogoutSuccessEvent", "postUnauthorizedEvent", "app_debug"})
public final class AuthEventManager {
    @org.jetbrains.annotations.NotNull()
    public static final com.wuheng.smart.data.network.AuthEventManager INSTANCE = null;
    private static final kotlinx.coroutines.flow.MutableSharedFlow<com.wuheng.smart.data.network.AuthEvent> _authEvents = null;
    
    /**
     * 认证事件流
     * UI 层订阅此流以接收认证事件
     */
    @org.jetbrains.annotations.NotNull()
    private static final kotlinx.coroutines.flow.SharedFlow<com.wuheng.smart.data.network.AuthEvent> authEvents = null;
    
    private AuthEventManager() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.SharedFlow<com.wuheng.smart.data.network.AuthEvent> getAuthEvents() {
        return null;
    }
    
    /**
     * 发送认证事件
     *
     * @param event 认证事件
     */
    public final void postAuthEvent(@org.jetbrains.annotations.NotNull()
    com.wuheng.smart.data.network.AuthEvent event) {
    }
    
    /**
     * 发送 401 Unauthorized 事件
     * 快捷方法
     */
    public final void postUnauthorizedEvent() {
    }
    
    /**
     * 发送登录成功事件
     */
    public final void postLoginSuccessEvent() {
    }
    
    /**
     * 发送登出成功事件
     */
    public final void postLogoutSuccessEvent() {
    }
}