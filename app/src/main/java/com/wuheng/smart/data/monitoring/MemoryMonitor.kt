package com.wuheng.smart.data.monitoring

import android.app.Application
import android.content.ComponentCallbacks2
import android.content.res.Configuration
import coil.imageLoader
import com.wuheng.smart.WuHengApplication
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import timber.log.Timber
import java.util.concurrent.ConcurrentHashMap

/**
 * 内存监控管理器
 * 提供内存使用监控、内存警告处理、缓存自动清理等功能
 */
class MemoryMonitor private constructor(private val application: Application) {

    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.Default)

    // 内存状态Flow
    private val _memoryState = MutableStateFlow(MemoryState())
    val memoryState: StateFlow<MemoryState> = _memoryState.asStateFlow()

    // 内存警告回调列表
    private val memoryWarningCallbacks = mutableListOf<(MemoryLevel) -> Unit>()

    // 内存上报回调
    private var memoryReportCallback: ((MemoryReport) -> Unit)? = null

    // 监控任务
    private var monitoringJob: Job? = null

    // 是否正在监控
    private val _isMonitoring = MutableStateFlow(false)
    val isMonitoring: StateFlow<Boolean> = _isMonitoring.asStateFlow()

    companion object {
        @Volatile
        private var instance: MemoryMonitor? = null

        fun getInstance(application: Application): MemoryMonitor {
            return instance ?: synchronized(this) {
                instance ?: MemoryMonitor(application).also { instance = it }
            }
        }
    }

    /**
     * 开始内存监控
     *
     * @param intervalMs 监控间隔（毫秒），默认30秒
     */
    fun startMonitoring(intervalMs: Long = 30000) {
        if (monitoringJob != null) return

        _isMonitoring.value = true
        monitoringJob = scope.launch {
            while (isActive) {
                updateMemoryState()
                delay(intervalMs)
            }
        }

        // 注册系统内存回调
        application.registerComponentCallbacks(memoryCallbacks)

        Timber.d("内存监控已启动，间隔: ${intervalMs}ms")
    }

    /**
     * 停止内存监控
     */
    fun stopMonitoring() {
        monitoringJob?.cancel()
        monitoringJob = null
        _isMonitoring.value = false
        application.unregisterComponentCallbacks(memoryCallbacks)
        Timber.d("内存监控已停止")
    }

    /**
     * 注册内存警告回调
     */
    fun registerMemoryWarningCallback(callback: (MemoryLevel) -> Unit) {
        memoryWarningCallbacks.add(callback)
    }

    /**
     * 注销内存警告回调
     */
    fun unregisterMemoryWarningCallback(callback: (MemoryLevel) -> Unit) {
        memoryWarningCallbacks.remove(callback)
    }

    /**
     * 设置内存上报回调
     */
    fun setMemoryReportCallback(callback: (MemoryReport) -> Unit) {
        memoryReportCallback = callback
    }

    /**
     * 更新内存状态
     */
    private fun updateMemoryState() {
        val runtime = Runtime.getRuntime()
        val maxMemory = runtime.maxMemory()
        val totalMemory = runtime.totalMemory()
        val freeMemory = runtime.freeMemory()
        val usedMemory = totalMemory - freeMemory
        val usagePercent = (usedMemory * 100 / maxMemory).toInt()

        val state = MemoryState(
            maxMemory = maxMemory,
            totalMemory = totalMemory,
            freeMemory = freeMemory,
            usedMemory = usedMemory,
            usagePercent = usagePercent,
            timestamp = System.currentTimeMillis()
        )

        _memoryState.value = state

        // 检查内存级别
        val level = when {
            usagePercent >= 90 -> MemoryLevel.CRITICAL
            usagePercent >= 75 -> MemoryLevel.HIGH
            usagePercent >= 60 -> MemoryLevel.MODERATE
            else -> MemoryLevel.NORMAL
        }

        // 触发内存警告回调
        if (level >= MemoryLevel.HIGH) {
            memoryWarningCallbacks.forEach { it.invoke(level) }
            handleMemoryWarning(level)
        }

        // 上报内存数据
        reportMemory(state, level)

        Timber.d("内存状态: ${state.format()}")
    }

    /**
     * 处理内存警告
     */
    private fun handleMemoryWarning(level: MemoryLevel) {
        Timber.w("内存警告: $level")

        when (level) {
            MemoryLevel.CRITICAL -> {
                // 严重内存不足：清理所有缓存
                clearAllCaches()
            }
            MemoryLevel.HIGH -> {
                // 内存较高：清理非必要缓存
                clearNonEssentialCaches()
            }
            else -> {
                // 其他级别：只清理图片缓存
                clearImageCache()
            }
        }
    }

    /**
     * 清理图片缓存
     */
    private fun clearImageCache() {
        try {
            application.imageLoader.memoryCache?.clear()
            Timber.d("图片内存缓存已清理")
        } catch (e: Exception) {
            Timber.e(e, "清理图片缓存失败")
        }
    }

    /**
     * 清理非必要缓存
     */
    private fun clearNonEssentialCaches() {
        clearImageCache()

        // 清理磁盘缓存
        scope.launch(Dispatchers.IO) {
            try {
                application.imageLoader.diskCache?.clear()
                Timber.d("图片磁盘缓存已清理")
            } catch (e: Exception) {
                Timber.e(e, "清理磁盘缓存失败")
            }
        }

        // 触发GC建议
        System.gc()

        Timber.d("非必要缓存已清理")
    }

    /**
     * 清理所有缓存
     */
    private fun clearAllCaches() {
        clearNonEssentialCaches()

        // 清理所有ViewModel缓存
        MemoryCacheManager.clearAllCaches()

        // 强制GC
        System.runFinalization()
        System.gc()

        Timber.d("所有缓存已清理")
    }

    /**
     * 上报内存数据
     */
    private fun reportMemory(state: MemoryState, level: MemoryLevel) {
        val report = MemoryReport(
            state = state,
            level = level,
            appVersion = getAppVersion(),
            deviceInfo = getDeviceInfo()
        )

        memoryReportCallback?.invoke(report)
    }

    /**
     * 获取应用版本
     */
    private fun getAppVersion(): String {
        return try {
            application.packageManager.getPackageInfo(application.packageName, 0).versionName ?: "unknown"
        } catch (e: Exception) {
            "unknown"
        }
    }

    /**
     * 获取设备信息
     */
    private fun getDeviceInfo(): String {
        return "${android.os.Build.MANUFACTURER} ${android.os.Build.MODEL}, Android ${android.os.Build.VERSION.RELEASE}"
    }

    /**
     * 系统内存回调
     */
    private val memoryCallbacks = object : ComponentCallbacks2 {
        override fun onConfigurationChanged(newConfig: Configuration) {}

        override fun onLowMemory() {
            Timber.w("系统内存不足 (onLowMemory)")
            handleMemoryWarning(MemoryLevel.CRITICAL)
        }

        override fun onTrimMemory(level: Int) {
            when (level) {
                ComponentCallbacks2.TRIM_MEMORY_RUNNING_CRITICAL,
                ComponentCallbacks2.TRIM_MEMORY_COMPLETE -> {
                    Timber.w("系统内存严重不足: $level")
                    handleMemoryWarning(MemoryLevel.CRITICAL)
                }
                ComponentCallbacks2.TRIM_MEMORY_RUNNING_LOW,
                ComponentCallbacks2.TRIM_MEMORY_MODERATE -> {
                    Timber.w("系统内存较低: $level")
                    handleMemoryWarning(MemoryLevel.HIGH)
                }
                ComponentCallbacks2.TRIM_MEMORY_RUNNING_MODERATE -> {
                    Timber.d("系统内存中等: $level")
                    handleMemoryWarning(MemoryLevel.MODERATE)
                }
                ComponentCallbacks2.TRIM_MEMORY_UI_HIDDEN -> {
                    Timber.d("UI隐藏，清理缓存: $level")
                    clearNonEssentialCaches()
                }
                ComponentCallbacks2.TRIM_MEMORY_BACKGROUND -> {
                    Timber.d("应用在后台，清理缓存: $level")
                    clearImageCache()
                }
            }
        }
    }

    /**
     * 内存状态数据类
     */
    data class MemoryState(
        val maxMemory: Long = 0,
        val totalMemory: Long = 0,
        val freeMemory: Long = 0,
        val usedMemory: Long = 0,
        val usagePercent: Int = 0,
        val timestamp: Long = 0
    ) {
        fun format(): String {
            return "used=${usedMemory / 1024 / 1024}MB, max=${maxMemory / 1024 / 1024}MB, usage=${usagePercent}%"
        }
    }

    /**
     * 内存级别枚举
     */
    enum class MemoryLevel {
        NORMAL,     // 正常
        MODERATE,   // 中等
        HIGH,       // 较高
        CRITICAL    // 严重
    }

    /**
     * 内存上报数据类
     */
    data class MemoryReport(
        val state: MemoryState,
        val level: MemoryLevel,
        val appVersion: String,
        val deviceInfo: String,
        val reportTime: Long = System.currentTimeMillis()
    )
}

/**
 * 内存缓存管理器
 * 统一管理应用内的各种缓存
 */
object MemoryCacheManager {

    private val caches = ConcurrentHashMap<String, MutableMap<*, *>>()

    /**
     * 注册缓存
     */
    fun registerCache(name: String, cache: MutableMap<*, *>) {
        caches[name] = cache
    }

    /**
     * 注销缓存
     */
    fun unregisterCache(name: String) {
        caches.remove(name)
    }

    /**
     * 清理指定缓存
     */
    fun clearCache(name: String) {
        caches[name]?.clear()
    }

    /**
     * 清理所有缓存
     */
    fun clearAllCaches() {
        caches.values.forEach { it.clear() }
    }

    /**
     * 获取缓存统计
     */
    fun getCacheStats(): Map<String, Int> {
        return caches.mapValues { it.value.size }
    }
}

/**
 * 内存监控扩展函数
 */
fun Application.initMemoryMonitor(): MemoryMonitor {
    return MemoryMonitor.getInstance(this).also {
        it.startMonitoring()
    }
}
