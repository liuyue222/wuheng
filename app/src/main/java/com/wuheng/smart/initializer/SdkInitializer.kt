package com.wuheng.smart.initializer

import android.content.Context
import androidx.startup.Initializer
import com.wuheng.smart.performance.StartupTimer
import com.wuheng.smart.performance.traceStartup
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import timber.log.Timber

/**
 * SDK 初始化器
 *
 * 使用 App Startup 库管理第三方 SDK 的初始化
 * 支持异步初始化非关键 SDK
 */

/**
 * 初始化管理器
 * 集中管理所有 SDK 的初始化
 */
object SdkInitManager {

    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.Default)

    // 初始化状态标记
    @Volatile
    var isTimberInitialized = false
        private set

    @Volatile
    var isLocationSdkInitialized = false
        private set

    @Volatile
    var isAnalyticsInitialized = false
        private set

    @Volatile
    var isPushInitialized = false
        private set

    /**
     * 初始化 Timber 日志（同步）
     */
    fun initTimber(debug: Boolean) {
        if (isTimberInitialized) return

        traceStartup(StartupTimer.TASK_TIMBER_INIT) {
            if (debug) {
                Timber.plant(Timber.DebugTree())
            }
            isTimberInitialized = true
        }
    }

    /**
     * 异步初始化定位 SDK
     */
    fun initLocationSdkAsync(context: Context) {
        scope.launch {
            traceStartup(StartupTimer.TASK_LOCATION_INIT) {
                // 模拟定位 SDK 初始化
                // 实际项目中替换为: LocationServices.getFusedLocationProviderClient(context)
                kotlinx.coroutines.delay(50) // 模拟耗时
                isLocationSdkInitialized = true
                Timber.d("Location SDK initialized")
            }
        }
    }

    /**
     * 异步初始化统计 SDK（友盟等）
     */
    fun initAnalyticsAsync(context: Context) {
        scope.launch {
            // 延迟初始化，不阻塞启动
            kotlinx.coroutines.delay(2000) // 启动2秒后再初始化

            traceStartup(StartupTimer.TASK_SDK_INIT) {
                // 模拟统计 SDK 初始化
                // 实际项目中替换为: UMConfigure.init(...)
                kotlinx.coroutines.delay(100)
                isAnalyticsInitialized = true
                Timber.d("Analytics SDK initialized")
            }
        }
    }

    /**
     * 异步初始化推送 SDK
     */
    fun initPushAsync(context: Context) {
        scope.launch {
            // 延迟初始化，不阻塞启动
            kotlinx.coroutines.delay(3000) // 启动3秒后再初始化

            traceStartup(StartupTimer.TASK_SDK_INIT) {
                // 模拟推送 SDK 初始化
                // 实际项目中替换为: PushManager.getInstance().initialize(...)
                kotlinx.coroutines.delay(150)
                isPushInitialized = true
                Timber.d("Push SDK initialized")
            }
        }
    }

    /**
     * 等待所有异步初始化完成（用于需要确保 SDK 就绪的场景）
     */
    suspend fun awaitAllInitialized() {
        // 实际项目中可以使用 CompletableDeferred 等机制
        while (!isLocationSdkInitialized || !isAnalyticsInitialized || !isPushInitialized) {
            kotlinx.coroutines.delay(100)
        }
    }
}

/**
 * Timber 初始化器（同步）
 */
class TimberInitializer : Initializer<Unit> {
    override fun create(context: Context) {
        SdkInitManager.initTimber(true) // DEBUG 模式
    }

    override fun dependencies(): List<Class<out Initializer<*>>> = emptyList()
}

/**
 * 定位 SDK 初始化器（异步）
 */
class LocationSdkInitializer : Initializer<Unit> {
    override fun create(context: Context) {
        SdkInitManager.initLocationSdkAsync(context)
    }

    override fun dependencies(): List<Class<out Initializer<*>>> {
        return listOf(TimberInitializer::class.java)
    }
}

/**
 * 统计 SDK 初始化器（异步延迟）
 */
class AnalyticsInitializer : Initializer<Unit> {
    override fun create(context: Context) {
        SdkInitManager.initAnalyticsAsync(context)
    }

    override fun dependencies(): List<Class<out Initializer<*>>> {
        return listOf(TimberInitializer::class.java)
    }
}

/**
 * 推送 SDK 初始化器（异步延迟）
 */
class PushInitializer : Initializer<Unit> {
    override fun create(context: Context) {
        SdkInitManager.initPushAsync(context)
    }

    override fun dependencies(): List<Class<out Initializer<*>>> {
        return listOf(TimberInitializer::class.java)
    }
}
