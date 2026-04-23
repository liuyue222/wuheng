package com.wuheng.smart.presentation.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import timber.log.Timber
import java.lang.ref.WeakReference
import java.util.concurrent.ConcurrentHashMap

/**
 * 优化的BaseViewModel - 内存优化版本
 * 提供协程自动管理、大对象弱引用、资源清理等功能
 */
abstract class OptimizedViewModel : ViewModel() {

    /**
     * 协程任务管理 - 用于跟踪和取消协程
     */
    private val activeJobs = ConcurrentHashMap<String, Job>()

    /**
     * 大对象弱引用缓存 - 避免ViewModel持有大量数据
     */
    private val weakReferenceCache = ConcurrentHashMap<String, WeakReference<Any>>()

    /**
     * 资源清理回调列表
     */
    private val cleanupCallbacks = mutableListOf<() -> Unit>()

    /**
     * ViewModel是否已被清理
     */
    private val _isCleared = MutableStateFlow(false)
    val isCleared: StateFlow<Boolean> = _isCleared.asStateFlow()

    /**
     * 启动一个命名协程，便于管理和取消
     *
     * @param name 协程名称，用于后续管理
     * @param block 协程体
     * @return Job实例
     */
    protected fun launchNamed(
        name: String,
        block: suspend CoroutineScope.() -> Unit
    ): Job {
        // 取消同名协程
        activeJobs[name]?.cancel()

        val job = viewModelScope.launch {
            try {
                block()
            } catch (e: CancellationException) {
                Timber.d("协程 $name 被取消")
                throw e
            } catch (e: Exception) {
                Timber.e(e, "协程 $name 执行出错")
                handleError(e)
            }
        }

        activeJobs[name] = job

        // 协程完成后移除
        job.invokeOnCompletion {
            activeJobs.remove(name)
        }

        return job
    }

    /**
     * 取消指定名称的协程
     */
    protected fun cancelJob(name: String) {
        activeJobs[name]?.cancel()
        activeJobs.remove(name)
    }

    /**
     * 取消所有活跃协程
     */
    protected fun cancelAllJobs() {
        activeJobs.values.forEach { it.cancel() }
        activeJobs.clear()
    }

    /**
     * 延迟执行操作 - 自动处理ViewModel生命周期
     *
     * @param delayMillis 延迟时间（毫秒）
     * @param action 执行的操作
     * @return Job实例
     */
    protected fun postDelayed(
        delayMillis: Long,
        action: () -> Unit
    ): Job {
        return viewModelScope.launch {
            delay(delayMillis)
            if (!isCleared.value) {
                action()
            }
        }
    }

    /**
     * 存储大对象到弱引用缓存
     * 适用于不经常访问的大对象数据
     *
     * @param key 缓存键
     * @param value 缓存值
     */
    protected fun <T : Any> putWeakReference(key: String, value: T) {
        weakReferenceCache[key] = WeakReference(value)
    }

    /**
     * 从弱引用缓存获取对象
     *
     * @param key 缓存键
     * @return 缓存值，如果已被GC回收则返回null
     */
    @Suppress("UNCHECKED_CAST")
    protected fun <T : Any> getWeakReference(key: String): T? {
        return weakReferenceCache[key]?.get() as? T
    }

    /**
     * 清除弱引用缓存
     */
    protected fun clearWeakReferences() {
        weakReferenceCache.clear()
    }

    /**
     * 注册资源清理回调
     * 在ViewModel被清理时自动调用
     *
     * @param callback 清理回调
     */
    protected fun registerCleanup(callback: () -> Unit) {
        cleanupCallbacks.add(callback)
    }

    /**
     * 错误处理 - 子类可重写
     */
    protected open fun handleError(error: Throwable) {
        Timber.e(error, "ViewModel错误: ${error.message}")
    }

    /**
     * 清理资源 - 在ViewModel被销毁时调用
     */
    protected open fun onCleanup() {
        // 子类可重写此方法进行资源清理
    }

    override fun onCleared() {
        super.onCleared()

        _isCleared.value = true

        // 取消所有协程
        cancelAllJobs()

        // 执行注册的清理回调
        cleanupCallbacks.forEach { callback ->
            try {
                callback()
            } catch (e: Exception) {
                Timber.e(e, "清理回调执行失败")
            }
        }
        cleanupCallbacks.clear()

        // 清除弱引用缓存
        clearWeakReferences()

        // 执行子类清理逻辑
        onCleanup()

        Timber.d("${this::class.simpleName} 已清理")
    }
}

/**
 * 分页数据管理器 - 优化大数据集的内存使用
 */
class PagingDataManager<T>(
    private val pageSize: Int = 20,
    private val maxCachedPages: Int = 3
) {
    private val dataCache = mutableMapOf<Int, List<T>>()
    private val accessedPages = mutableListOf<Int>()

    /**
     * 获取指定页的数据
     */
    fun getPage(page: Int): List<T>? {
        return dataCache[page]?.also {
            // 更新访问顺序
            accessedPages.remove(page)
            accessedPages.add(page)
        }
    }

    /**
     * 存储页数据
     */
    fun putPage(page: Int, data: List<T>) {
        // 如果缓存已满，移除最久未访问的页
        if (dataCache.size >= maxCachedPages && !dataCache.containsKey(page)) {
            val oldestPage = accessedPages.firstOrNull()
            if (oldestPage != null) {
                dataCache.remove(oldestPage)
                accessedPages.remove(oldestPage)
            }
        }

        dataCache[page] = data
        if (!accessedPages.contains(page)) {
            accessedPages.add(page)
        }
    }

    /**
     * 清除所有缓存
     */
    fun clear() {
        dataCache.clear()
        accessedPages.clear()
    }

    /**
     * 获取已缓存的所有数据
     */
    fun getAllCachedData(): List<T> {
        return dataCache.values.flatten()
    }
}

/**
 * 内存敏感的数据加载器
 * 在低内存时自动释放非必要数据
 */
abstract class MemorySensitiveLoader<T> {

    private var cachedData: T? = null
    private var lastLoadTime: Long = 0
    private val cacheValidityDuration = 5 * 60 * 1000L // 5分钟缓存有效期

    /**
     * 加载数据 - 优先使用缓存
     */
    suspend fun load(forceRefresh: Boolean = false): T? {
        // 检查缓存是否有效
        if (!forceRefresh && isCacheValid()) {
            return cachedData
        }

        // 加载新数据
        return try {
            val data = performLoad()
            cachedData = data
            lastLoadTime = System.currentTimeMillis()
            data
        } catch (e: Exception) {
            Timber.e(e, "数据加载失败")
            cachedData // 返回缓存数据（如果有）
        }
    }

    /**
     * 执行实际的数据加载
     */
    protected abstract suspend fun performLoad(): T

    /**
     * 检查缓存是否有效
     */
    private fun isCacheValid(): Boolean {
        return cachedData != null &&
                (System.currentTimeMillis() - lastLoadTime) < cacheValidityDuration
    }

    /**
     * 清除缓存 - 在内存警告时调用
     */
    fun clearCache() {
        cachedData = null
        lastLoadTime = 0
    }

    /**
     * 获取缓存的数据大小（估算）
     * 子类可重写以提供更准确的值
     */
    open fun getCacheSize(): Int {
        return 0
    }
}

/**
 * ViewModel内存监控工具
 */
object ViewModelMemoryMonitor {

    /**
     * 获取当前ViewModel的内存使用情况
     */
    fun getMemoryInfo(): MemoryInfo {
        val runtime = Runtime.getRuntime()
        val maxMemory = runtime.maxMemory()
        val totalMemory = runtime.totalMemory()
        val freeMemory = runtime.freeMemory()
        val usedMemory = totalMemory - freeMemory

        return MemoryInfo(
            maxMemory = maxMemory,
            totalMemory = totalMemory,
            freeMemory = freeMemory,
            usedMemory = usedMemory,
            usagePercent = (usedMemory * 100 / maxMemory).toInt()
        )
    }

    /**
     * 检查是否处于低内存状态
     */
    fun isLowMemory(): Boolean {
        val info = getMemoryInfo()
        return info.usagePercent > 80 // 使用超过80%视为低内存
    }

    data class MemoryInfo(
        val maxMemory: Long,
        val totalMemory: Long,
        val freeMemory: Long,
        val usedMemory: Long,
        val usagePercent: Int
    ) {
        override fun toString(): String {
            return "MemoryInfo(used=${usedMemory / 1024 / 1024}MB, " +
                    "max=${maxMemory / 1024 / 1024}MB, " +
                    "usage=${usagePercent}%)"
        }
    }
}
