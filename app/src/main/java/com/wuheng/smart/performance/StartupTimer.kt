package com.wuheng.smart.performance

import android.os.SystemClock
import timber.log.Timber
import java.util.concurrent.ConcurrentHashMap

/**
 * 启动时间追踪器
 *
 * 用于记录应用启动各阶段的耗时，帮助分析启动性能瓶颈
 *
 * 使用示例：
 * ```
 * // 在Application.onCreate开始处
 * StartupTimer.start(StartupTimer.APP_ONCREATE)
 *
 * // 在Application.onCreate结束处
 * StartupTimer.end(StartupTimer.APP_ONCREATE)
 *
 * // 打印所有耗时
 * StartupTimer.printReport()
 * ```
 */
object StartupTimer {

    // 启动阶段标记
    const val APP_ATTACH_BASE_CONTEXT = "app_attach_base_context"
    const val APP_ONCREATE = "app_oncreate"
    const val APP_ONCREATE_END = "app_oncreate_end"
    const val MAIN_ACTIVITY_CREATE = "main_activity_create"
    const val MAIN_ACTIVITY_START = "main_activity_start"
    const val MAIN_ACTIVITY_RESUME = "main_activity_resume"
    const val FIRST_FRAME_DRAWN = "first_frame_drawn"
    const val FIRST_FRAME_RENDERED = "first_frame_rendered"

    // 子任务标记
    const val TASK_TIMBER_INIT = "task_timber_init"
    const val TASK_HILT_INIT = "task_hilt_init"
    const val TASK_SDK_INIT = "task_sdk_init"
    const val TASK_DATASTORE_INIT = "task_datastore_init"
    const val TASK_LOCATION_INIT = "task_location_init"

    // 冷启动开始时间（进程创建时间）
    private var coldStartTime: Long = 0

    // 时间记录表
    private val timeRecords = ConcurrentHashMap<String, Long>()

    // 耗时记录表
    private val durationRecords = ConcurrentHashMap<String, Long>()

    /**
     * 记录冷启动开始时间
     * 应在Application.attachBaseContext()中尽早调用
     */
    fun recordColdStart() {
        coldStartTime = SystemClock.elapsedRealtime()
        timeRecords["cold_start"] = coldStartTime
        Timber.d("[StartupTimer] Cold start recorded at: $coldStartTime")
    }

    /**
     * 开始记录某个阶段
     */
    fun start(tag: String) {
        val startTime = SystemClock.elapsedRealtime()
        timeRecords[tag] = startTime
        Timber.d("[StartupTimer] Started: $tag at $startTime")
    }

    /**
     * 结束记录某个阶段
     */
    fun end(tag: String) {
        val endTime = SystemClock.elapsedRealtime()
        val startTime = timeRecords[tag] ?: return
        val duration = endTime - startTime
        durationRecords[tag] = duration
        Timber.d("[StartupTimer] Ended: $tag, duration: ${duration}ms")
    }

    /**
     * 记录从冷启动开始的耗时
     */
    fun recordFromColdStart(tag: String) {
        if (coldStartTime == 0L) {
            recordColdStart()
        }
        val currentTime = SystemClock.elapsedRealtime()
        val duration = currentTime - coldStartTime
        durationRecords[tag] = duration
        timeRecords[tag] = currentTime
        Timber.d("[StartupTimer] $tag from cold start: ${duration}ms")
    }

    /**
     * 获取某个阶段的耗时
     */
    fun getDuration(tag: String): Long {
        return durationRecords[tag] ?: -1
    }

    /**
     * 获取从冷启动到指定标记的耗时
     */
    fun getDurationFromColdStart(tag: String): Long {
        val tagTime = timeRecords[tag] ?: return -1
        return tagTime - coldStartTime
    }

    /**
     * 打印完整的启动耗时报告
     */
    fun printReport() {
        val report = buildString {
            appendLine("\n╔══════════════════════════════════════════════════════════════╗")
            appendLine("║                    启动性能报告                                ║")
            appendLine("╠══════════════════════════════════════════════════════════════╣")

            // 总启动时间
            val totalTime = durationRecords[FIRST_FRAME_RENDERED] ?: durationRecords[FIRST_FRAME_DRAWN]
            ?: durationRecords[MAIN_ACTIVITY_RESUME] ?: -1
            if (totalTime > 0) {
                val status = if (totalTime < 1500) "✓ 优秀" else if (totalTime < 2000) "△ 良好" else "✗ 需优化"
                appendLine("║ 总启动时间: ${totalTime}ms $status")
                appendLine("╠══════════════════════════════════════════════════════════════╣")
            }

            // 各阶段耗时
            appendLine("║ 启动阶段耗时:")
            durationRecords.toSortedMap().forEach { (tag, duration) ->
                val indent = if (tag.startsWith("task_")) "  " else ""
                appendLine("║ $indent$tag: ${duration}ms")
            }

            appendLine("╚══════════════════════════════════════════════════════════════╝")
        }

        Timber.i(report)
    }

    /**
     * 获取启动性能数据（用于埋点上报）
     */
    fun getPerformanceData(): Map<String, Long> {
        return durationRecords.toMap()
    }

    /**
     * 重置所有记录
     */
    fun reset() {
        timeRecords.clear()
        durationRecords.clear()
        coldStartTime = 0
    }
}

/**
 * 启动追踪器扩展函数
 * 用于方便地追踪代码块执行时间
 */
inline fun <T> traceStartup(tag: String, block: () -> T): T {
    StartupTimer.start(tag)
    return try {
        block()
    } finally {
        StartupTimer.end(tag)
    }
}

/**
 * 异步任务启动追踪
 */
suspend inline fun <T> traceStartupAsync(tag: String, crossinline block: suspend () -> T): T {
    StartupTimer.start(tag)
    return try {
        block()
    } finally {
        StartupTimer.end(tag)
    }
}
