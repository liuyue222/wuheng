package com.wuheng.smart.data.network

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow

/**
 * 认证事件类型
 */
sealed class AuthEvent {
    /**
     * 401 Unauthorized - Token 过期或无效
     * 需要清除 Token 并跳转到登录页
     */
    object Unauthorized : AuthEvent()

    /**
     * 登出成功
     */
    object LogoutSuccess : AuthEvent()

    /**
     * 登录成功
     */
    object LoginSuccess : AuthEvent()
}

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
object AuthEventManager {

    private val _authEvents = MutableSharedFlow<AuthEvent>(
        extraBufferCapacity = 1,  // 缓冲一个事件，确保新订阅者能收到最新事件
        replay = 0                // 不回放历史事件
    )

    /**
     * 认证事件流
     * UI 层订阅此流以接收认证事件
     */
    val authEvents: SharedFlow<AuthEvent> = _authEvents.asSharedFlow()

    /**
     * 发送认证事件
     *
     * @param event 认证事件
     */
    fun postAuthEvent(event: AuthEvent) {
        _authEvents.tryEmit(event)
    }

    /**
     * 发送 401 Unauthorized 事件
     * 快捷方法
     */
    fun postUnauthorizedEvent() {
        postAuthEvent(AuthEvent.Unauthorized)
    }

    /**
     * 发送登录成功事件
     */
    fun postLoginSuccessEvent() {
        postAuthEvent(AuthEvent.LoginSuccess)
    }

    /**
     * 发送登出成功事件
     */
    fun postLogoutSuccessEvent() {
        postAuthEvent(AuthEvent.LogoutSuccess)
    }
}
