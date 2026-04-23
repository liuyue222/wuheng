package com.wuheng.smart;

import android.app.Application;
import coil.ImageLoaderFactory;
import com.wuheng.smart.data.monitoring.MemoryMonitor;
import dagger.hilt.android.HiltAndroidApp;
import timber.log.Timber;

@kotlin.Metadata(mv = {1, 7, 1}, k = 1, d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\b\u0007\u0018\u0000 \b2\u00020\u00012\u00020\u0002:\u0001\bB\u0005\u00a2\u0006\u0002\u0010\u0003J\b\u0010\u0004\u001a\u00020\u0005H\u0016J\b\u0010\u0006\u001a\u00020\u0007H\u0016\u00a8\u0006\t"}, d2 = {"Lcom/wuheng/smart/WuHengApplication;", "Landroid/app/Application;", "Lcoil/ImageLoaderFactory;", "()V", "newImageLoader", "Lcoil/ImageLoader;", "onCreate", "", "Companion", "app_debug"})
@dagger.hilt.android.HiltAndroidApp()
public final class WuHengApplication extends android.app.Application implements coil.ImageLoaderFactory {
    @org.jetbrains.annotations.NotNull()
    public static final com.wuheng.smart.WuHengApplication.Companion Companion = null;
    private static com.wuheng.smart.WuHengApplication instance;
    
    public WuHengApplication() {
        super();
    }
    
    @java.lang.Override()
    public void onCreate() {
    }
    
    /**
     * 提供全局ImageLoader实例
     * 配置内存缓存和磁盘缓存策略
     */
    @org.jetbrains.annotations.NotNull()
    @java.lang.Override()
    public coil.ImageLoader newImageLoader() {
        return null;
    }
    
    @kotlin.Metadata(mv = {1, 7, 1}, k = 1, d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R$\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0004@BX\u0086.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0006\u0010\u0007\"\u0004\b\b\u0010\t\u00a8\u0006\n"}, d2 = {"Lcom/wuheng/smart/WuHengApplication$Companion;", "", "()V", "<set-?>", "Lcom/wuheng/smart/WuHengApplication;", "instance", "getInstance", "()Lcom/wuheng/smart/WuHengApplication;", "setInstance", "(Lcom/wuheng/smart/WuHengApplication;)V", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.wuheng.smart.WuHengApplication getInstance() {
            return null;
        }
        
        private final void setInstance(com.wuheng.smart.WuHengApplication p0) {
        }
    }
}