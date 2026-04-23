package com.wuheng.smart.initializer

import android.content.Context
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.components.SingletonComponent
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Hilt 组件延迟初始化管理器
 *
 * 用于延迟初始化非关键的 Hilt 注入组件，减少 Application.onCreate 耗时
 */
@Singleton
class LazyHiltComponents @Inject constructor() {

    companion object {
        @Volatile
        private var instance: LazyHiltComponents? = null

        fun getInstance(context: Context): LazyHiltComponents {
            return instance ?: synchronized(this) {
                instance ?: EntryPointAccessors.fromApplication(
                    context.applicationContext,
                    LazyHiltEntryPoint::class.java
                ).getLazyHiltComponents().also {
                    instance = it
                }
            }
        }
    }

    // 延迟初始化的组件标记
    private val initializedComponents = mutableSetOf<String>()

    /**
     * 初始化指定组件（线程安全）
     */
    fun initializeComponent(componentName: String, initializer: () -> Unit) {
        if (initializedComponents.contains(componentName)) return

        synchronized(initializedComponents) {
            if (initializedComponents.contains(componentName)) return
            initializer()
            initializedComponents.add(componentName)
        }
    }

    /**
     * 检查组件是否已初始化
     */
    fun isComponentInitialized(componentName: String): Boolean {
        return initializedComponents.contains(componentName)
    }
}

/**
 * Hilt Entry Point for LazyHiltComponents
 */
@EntryPoint
@InstallIn(SingletonComponent::class)
interface LazyHiltEntryPoint {
    fun getLazyHiltComponents(): LazyHiltComponents
}

/**
 * 延迟初始化委托类
 */
class LazyComponent<T>(
    private val componentName: String,
    private val initializer: () -> T
) {
    private var value: T? = null

    fun get(lazyComponents: LazyHiltComponents): T {
        if (value == null) {
            lazyComponents.initializeComponent(componentName) {
                value = initializer()
            }
        }
        return value!!
    }

    fun isInitialized(): Boolean = value != null
}

/**
 * 组件初始化优先级
 */
enum class InitPriority(val delayMillis: Long) {
    IMMEDIATE(0),       // 立即初始化
    HIGH(100),          // 高优先级（100ms后）
    NORMAL(500),        // 普通优先级（500ms后）
    LOW(2000),          // 低优先级（2s后）
    BACKGROUND(5000)    // 后台初始化（5s后）
}
