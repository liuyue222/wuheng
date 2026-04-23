package com.wuheng.smart.di

import android.content.Context
import coil.ImageLoader
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import coil.decode.SvgDecoder
import coil.disk.DiskCache
import coil.memory.MemoryCache
import coil.request.CachePolicy
import coil.util.DebugLogger
import com.wuheng.smart.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * 图片加载模块 - Coil配置
 * 提供内存优化和缓存策略
 */
@Module
@InstallIn(SingletonComponent::class)
object ImageModule {

    /**
     * 提供全局ImageLoader实例
     * 配置内存缓存、磁盘缓存和图片解码器
     */
    @Provides
    @Singleton
    fun provideImageLoader(@ApplicationContext context: Context): ImageLoader {
        return ImageLoader.Builder(context)
            // 内存缓存配置 - 限制内存使用
            .memoryCache {
                MemoryCache.Builder(context)
                    // 最大内存缓存：应用可用内存的25%
                    .maxSizePercent(0.25)
                    // 强引用缓存大小
                    .strongReferencesEnabled(true)
                    .build()
            }
            // 磁盘缓存配置
            .diskCache {
                DiskCache.Builder()
                    .directory(context.cacheDir.resolve("image_cache"))
                    // 最大磁盘缓存：100MB
                    .maxSizeBytes(100 * 1024 * 1024)
                    .build()
            }
            // 图片解码器配置
            .components {
                // SVG支持
                add(SvgDecoder.Factory())
                // GIF支持 - 根据API级别选择解码器
                if (android.os.Build.VERSION.SDK_INT >= 28) {
                    add(ImageDecoderDecoder.Factory())
                } else {
                    add(GifDecoder.Factory())
                }
            }
            // 网络图片缓存策略
            .respectCacheHeaders(false)
            // 调试日志
            .apply {
                if (BuildConfig.DEBUG) {
                    logger(DebugLogger())
                }
            }
            // 跨平台图片格式支持
            .allowHardware(android.os.Build.VERSION.SDK_INT >= 26)
            .build()
    }
}
