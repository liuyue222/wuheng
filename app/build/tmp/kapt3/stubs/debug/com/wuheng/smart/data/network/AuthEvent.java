package com.wuheng.smart.data.network;

import kotlinx.coroutines.flow.SharedFlow;

/**
 * 认证事件类型
 */
@kotlin.Metadata(mv = {1, 7, 1}, k = 1, d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b7\u0018\u00002\u00020\u0001:\u0003\u0003\u0004\u0005B\u0007\b\u0004\u00a2\u0006\u0002\u0010\u0002\u0082\u0001\u0003\u0006\u0007\b\u00a8\u0006\t"}, d2 = {"Lcom/wuheng/smart/data/network/AuthEvent;", "", "()V", "LoginSuccess", "LogoutSuccess", "Unauthorized", "Lcom/wuheng/smart/data/network/AuthEvent$LoginSuccess;", "Lcom/wuheng/smart/data/network/AuthEvent$LogoutSuccess;", "Lcom/wuheng/smart/data/network/AuthEvent$Unauthorized;", "app_debug"})
public abstract class AuthEvent {
    
    private AuthEvent() {
        super();
    }
    
    /**
     * 401 Unauthorized - Token 过期或无效
     * 需要清除 Token 并跳转到登录页
     */
    @kotlin.Metadata(mv = {1, 7, 1}, k = 1, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u00c7\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002\u00a8\u0006\u0003"}, d2 = {"Lcom/wuheng/smart/data/network/AuthEvent$Unauthorized;", "Lcom/wuheng/smart/data/network/AuthEvent;", "()V", "app_debug"})
    public static final class Unauthorized extends com.wuheng.smart.data.network.AuthEvent {
        @org.jetbrains.annotations.NotNull()
        public static final com.wuheng.smart.data.network.AuthEvent.Unauthorized INSTANCE = null;
        
        private Unauthorized() {
            super();
        }
    }
    
    /**
     * 登出成功
     */
    @kotlin.Metadata(mv = {1, 7, 1}, k = 1, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u00c7\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002\u00a8\u0006\u0003"}, d2 = {"Lcom/wuheng/smart/data/network/AuthEvent$LogoutSuccess;", "Lcom/wuheng/smart/data/network/AuthEvent;", "()V", "app_debug"})
    public static final class LogoutSuccess extends com.wuheng.smart.data.network.AuthEvent {
        @org.jetbrains.annotations.NotNull()
        public static final com.wuheng.smart.data.network.AuthEvent.LogoutSuccess INSTANCE = null;
        
        private LogoutSuccess() {
            super();
        }
    }
    
    /**
     * 登录成功
     */
    @kotlin.Metadata(mv = {1, 7, 1}, k = 1, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u00c7\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002\u00a8\u0006\u0003"}, d2 = {"Lcom/wuheng/smart/data/network/AuthEvent$LoginSuccess;", "Lcom/wuheng/smart/data/network/AuthEvent;", "()V", "app_debug"})
    public static final class LoginSuccess extends com.wuheng.smart.data.network.AuthEvent {
        @org.jetbrains.annotations.NotNull()
        public static final com.wuheng.smart.data.network.AuthEvent.LoginSuccess INSTANCE = null;
        
        private LoginSuccess() {
            super();
        }
    }
}