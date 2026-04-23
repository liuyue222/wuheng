package com.wuheng.smart.performance

import android.app.Activity
import android.app.Application
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.ViewTreeObserver
import androidx.core.os.TraceCompat
import timber.log.Timber

/**
 * 启动追踪器
 *
 * 自动追踪应用启动全过程，包括：
 * - Application 初始化
 * - Activity 生命周期
 * - 首帧渲染时间
 *
 * 使用方法：
 * 在 Application.onCreate() 中调用 StartupTracer.initialize(this)
 */
object StartupTracer {

    private var isInitialized = false
    private var isFirstActivity = true
    private val mainHandler = Handler(Looper.getMainLooper())

    /**
     * 初始化启动追踪器
     */
    fun initialize(application: Application) {
        if (isInitialized) return
        isInitialized = true

        application.registerActivityLifecycleCallbacks(
            object : Application.ActivityLifecycleCallbacks {
                override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
                    if (isFirstActivity) {
                        StartupTimer.recordFromColdStart(StartupTimer.MAIN_ACTIVITY_CREATE)
                        Timber.d("[StartupTracer] First activity created: ${activity.javaClass.simpleName}")
                    }
                }

                override fun onActivityStarted(activity: Activity) {
                    if (isFirstActivity) {
                        StartupTimer.recordFromColdStart(StartupTimer.MAIN_ACTIVITY_START)
                    }
                }

                override fun onActivityResumed(activity: Activity) {
                    if (isFirstActivity) {
                        isFirstActivity = false
                        StartupTimer.recordFromColdStart(StartupTimer.MAIN_ACTIVITY_RESUME)

                        // 监听首帧渲染
                        activity.window?.decorView?.let { decorView ->
                            waitForFirstFrame(decorView)
                        }
                    }
                }

                override fun onActivityPaused(activity: Activity) {}
                override fun onActivityStopped(activity: Activity) {}
                override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {}
                override fun onActivityDestroyed(activity: Activity) {}
            }
        )
    }

    /**
     * 等待首帧渲染完成
     */
    private fun waitForFirstFrame(decorView: View) {
        decorView.viewTreeObserver.addOnPreDrawListener(
            object : ViewTreeObserver.OnPreDrawListener {
                override fun onPreDraw(): Boolean {
                    StartupTimer.recordFromColdStart(StartupTimer.FIRST_FRAME_DRAWN)
                    decorView.viewTreeObserver.removeOnPreDrawListener(this)

                    // 延迟一帧确保真正渲染完成
                    mainHandler.post {
                        StartupTimer.recordFromColdStart(StartupTimer.FIRST_FRAME_RENDERED)
                        StartupTimer.printReport()
                    }
                    return true
                }
            }
        )
    }

    /**
     * 开始追踪某个方法（用于Android Studio Profiler）
     */
    fun beginSection(sectionName: String) {
        TraceCompat.beginSection(sectionName)
    }

    /**
     * 结束追踪
     */
    fun endSection() {
        TraceCompat.endSection()
    }

    /**
     * 追踪代码块执行
     */
    inline fun <T> traceSection(sectionName: String, block: () -> T): T {
        beginSection(sectionName)
        return try {
            block()
        } finally {
            endSection()
        }
    }
}

/**
 * 启动性能埋点上报接口
 */
interface StartupMetricsReporter {
    fun reportStartupMetrics(metrics: StartupMetrics)
}

/**
 * 启动性能数据类
 */
data class StartupMetrics(
    val coldStartTime: Long,
    val appOnCreateTime: Long,
    val mainActivityCreateTime: Long,
    val firstFrameDrawnTime: Long,
    val firstFrameRenderedTime: Long,
    val taskMetrics: Map<String, Long> = emptyMap()
) {
    /**
     * 是否达到优化目标（< 1.5秒）
     */
    fun isTargetMet(): Boolean = firstFrameRenderedTime < 1500

    /**
     * 获取性能等级
     */
    fun getPerformanceLevel(): PerformanceLevel {
        return when (firstFrameRenderedTime) {
            in 0..1000 -> PerformanceLevel.EXCELLENT
            in 1001..1500 -> PerformanceLevel.GOOD
            in 1501..2000 -> PerformanceLevel.FAIR
            else -> PerformanceLevel.POOR
        }
    }
}

enum class PerformanceLevel {
    EXCELLENT,  // < 1s
    GOOD,       // 1s - 1.5s
    FAIR,       // 1.5s - 2s
    POOR        // > 2s
}

/**
 * 默认的启动性能埋点上报实现（使用Timber）
 */
class TimberStartupMetricsReporter : StartupMetricsReporter {
    override fun reportStartupMetrics(metrics: StartupMetrics) {
        val level = metrics.getPerformanceLevel()
        val message = buildString {
            append("Startup Metrics Report: ")
            append("level=$level, ")
            append("total=${metrics.firstFrameRenderedTime}ms, ")
            append("appOnCreate=${metrics.appOnCreateTime}ms, ")
            append("activityCreate=${metrics.mainActivityCreateTime}ms")
        }

        when (level) {
            PerformanceLevel.EXCELLENT -> Timber.i(message)
            PerformanceLevel.GOOD -> Timber.i(message)
            PerformanceLevel.FAIR -> Timber.w(message)
            PerformanceLevel.POOR -> Timber.e(message)
        }
    }
}
