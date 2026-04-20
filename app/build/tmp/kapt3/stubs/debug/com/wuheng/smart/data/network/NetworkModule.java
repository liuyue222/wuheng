package com.wuheng.smart.data.network;

import android.content.Context;
import com.wuheng.smart.BuildConfig;
import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import java.util.concurrent.TimeUnit;
import javax.inject.Singleton;

/**
 * 网络模块配置
 *
 * Base URL: http://116.62.51.112/wuheng_iot/index.php
 * 包含：
 * - Token管理器
 * - 日志拦截器
 * - 认证拦截器（自动添加Token）
 * - OkHttpClient配置
 * - Retrofit配置
 * - ApiService实例
 */
@dagger.hilt.InstallIn(value = {dagger.hilt.components.SingletonComponent.class})
@kotlin.Metadata(mv = {1, 7, 1}, k = 1, d1 = {"\u0000D\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\b\u00c7\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0007J\u0010\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH\u0007J\b\u0010\u000f\u001a\u00020\u0010H\u0007J\u0018\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u00102\u0006\u0010\u0014\u001a\u00020\fH\u0007J\u0010\u0010\u0015\u001a\u00020\n2\u0006\u0010\u0016\u001a\u00020\u0012H\u0007J\u0012\u0010\u0017\u001a\u00020\u000e2\b\b\u0001\u0010\u0018\u001a\u00020\u0019H\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u001a"}, d2 = {"Lcom/wuheng/smart/data/network/NetworkModule;", "", "()V", "BASE_URL", "", "TIMEOUT_SECONDS", "", "provideApiService", "Lcom/wuheng/smart/data/network/ApiService;", "retrofit", "Lretrofit2/Retrofit;", "provideAuthInterceptor", "Lcom/wuheng/smart/data/network/AuthInterceptor;", "tokenManager", "Lcom/wuheng/smart/data/network/TokenManager;", "provideHttpLoggingInterceptor", "Lokhttp3/logging/HttpLoggingInterceptor;", "provideOkHttpClient", "Lokhttp3/OkHttpClient;", "loggingInterceptor", "authInterceptor", "provideRetrofit", "okHttpClient", "provideTokenManager", "context", "Landroid/content/Context;", "app_debug"})
@dagger.Module()
public final class NetworkModule {
    @org.jetbrains.annotations.NotNull()
    public static final com.wuheng.smart.data.network.NetworkModule INSTANCE = null;
    
    /**
     * 请求超时时间（秒）
     */
    private static final long TIMEOUT_SECONDS = 30L;
    
    /**
     * 基础URL
     * 格式: http://116.62.51.112/wuheng_iot/index.php
     * 注意：必须以/结尾，Retrofit要求
     */
    private static final java.lang.String BASE_URL = "http://116.62.51.112/wuheng_iot/index.php/";
    
    private NetworkModule() {
        super();
    }
    
    /**
     * 提供Token管理器
     */
    @org.jetbrains.annotations.NotNull()
    @javax.inject.Singleton()
    @dagger.Provides()
    public final com.wuheng.smart.data.network.TokenManager provideTokenManager(@org.jetbrains.annotations.NotNull()
    @dagger.hilt.android.qualifiers.ApplicationContext()
    android.content.Context context) {
        return null;
    }
    
    /**
     * 提供HTTP日志拦截器
     * Debug模式下记录完整请求/响应，Release模式下关闭
     */
    @org.jetbrains.annotations.NotNull()
    @javax.inject.Singleton()
    @dagger.Provides()
    public final okhttp3.logging.HttpLoggingInterceptor provideHttpLoggingInterceptor() {
        return null;
    }
    
    /**
     * 提供认证拦截器
     * 自动为请求添加Authorization头
     */
    @org.jetbrains.annotations.NotNull()
    @javax.inject.Singleton()
    @dagger.Provides()
    public final com.wuheng.smart.data.network.AuthInterceptor provideAuthInterceptor(@org.jetbrains.annotations.NotNull()
    com.wuheng.smart.data.network.TokenManager tokenManager) {
        return null;
    }
    
    /**
     * 提供OkHttpClient
     * 配置：
     * - 连接/读取/写入超时30秒
     * - 添加认证拦截器（在日志拦截器之前，避免Token泄露到日志）
     * - 添加日志拦截器
     * - 启用连接失败重试
     */
    @org.jetbrains.annotations.NotNull()
    @javax.inject.Singleton()
    @dagger.Provides()
    public final okhttp3.OkHttpClient provideOkHttpClient(@org.jetbrains.annotations.NotNull()
    okhttp3.logging.HttpLoggingInterceptor loggingInterceptor, @org.jetbrains.annotations.NotNull()
    com.wuheng.smart.data.network.AuthInterceptor authInterceptor) {
        return null;
    }
    
    /**
     * 提供Retrofit实例
     * 使用Gson作为JSON转换器
     */
    @org.jetbrains.annotations.NotNull()
    @javax.inject.Singleton()
    @dagger.Provides()
    public final retrofit2.Retrofit provideRetrofit(@org.jetbrains.annotations.NotNull()
    okhttp3.OkHttpClient okHttpClient) {
        return null;
    }
    
    /**
     * 提供ApiService实例
     */
    @org.jetbrains.annotations.NotNull()
    @javax.inject.Singleton()
    @dagger.Provides()
    public final com.wuheng.smart.data.network.ApiService provideApiService(@org.jetbrains.annotations.NotNull()
    retrofit2.Retrofit retrofit) {
        return null;
    }
}