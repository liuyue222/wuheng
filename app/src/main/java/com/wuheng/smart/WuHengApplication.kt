package com.wuheng.smart

import android.app.Application
import coil.ImageLoader
import coil.ImageLoaderFactory
import com.wuheng.smart.data.monitoring.MemoryMonitor
import com.wuheng.smart.data.monitoring.initMemoryMonitor
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class WuHengApplication : Application(), ImageLoaderFactory {

    companion object {
        lateinit var instance: WuHengApplication
            private set
    }

    override fun onCreate() {
        super.onCreate()
        instance = this

        // 初始化日志
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        // 初始化内存监控
        initMemoryMonitor()

        Timber.d("WuHengApplication 初始化完成")
    }

    /**
     * 提供全局ImageLoader实例
     * 配置内存缓存和磁盘缓存策略
     */
    override fun newImageLoader(): ImageLoader {
        return ImageLoader.Builder(this)
            // 内存缓存配置 - 限制内存使用
            .memoryCache {
                coil.memory.MemoryCache.Builder(this)
                    // 最大内存缓存：应用可用内存的25%
                    .maxSizePercent(0.25)
                    // 强引用缓存大小
                    .strongReferencesEnabled(true)
                    .build()
            }
            // 磁盘缓存配置
            .diskCache {
                coil.disk.DiskCache.Builder()
                    .directory(cacheDir.resolve("image_cache"))
                    // 最大磁盘缓存：100MB
                    .maxSizeBytes(100 * 1024 * 1024)
                    .build()
            }
            // 图片解码器配置
            .components {
                // SVG支持
                add(coil.decode.SvgDecoder.Factory())
                // GIF支持 - 根据API级别选择解码器
                if (android.os.Build.VERSION.SDK_INT >= 28) {
                    add(coil.decode.ImageDecoderDecoder.Factory())
                } else {
                    add(coil.decode.GifDecoder.Factory())
                }
            }
            // 网络图片缓存策略
            .respectCacheHeaders(false)
            // 调试日志
            .apply {
                if (BuildConfig.DEBUG) {
                    logger(coil.util.DebugLogger())
                }
            }
            // 跨平台图片格式支持
            .allowHardware(android.os.Build.VERSION.SDK_INT >= 26)
            .build()
    }
}
