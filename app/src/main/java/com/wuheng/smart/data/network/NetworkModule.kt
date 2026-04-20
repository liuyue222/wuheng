package com.wuheng.smart.data.network

import android.content.Context
import com.wuheng.smart.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

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
@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    /**
     * 请求超时时间（秒）
     */
    private const val TIMEOUT_SECONDS = 30L

    /**
     * 基础URL
     * 格式: http://116.62.51.112/wuheng_iot/index.php
     * 注意：必须以/结尾，Retrofit要求
     */
    private const val BASE_URL = "http://116.62.51.112/wuheng_iot/index.php/"

    /**
     * 提供Token管理器
     */
    @Provides
    @Singleton
    fun provideTokenManager(@ApplicationContext context: Context): TokenManager {
        return TokenManager(context)
    }

    /**
     * 提供HTTP日志拦截器
     * Debug模式下记录完整请求/响应，Release模式下关闭
     */
    @Provides
    @Singleton
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor.Level.BODY
            } else {
                HttpLoggingInterceptor.Level.NONE
            }
        }
    }

    /**
     * 提供认证拦截器
     * 自动为请求添加Authorization头
     */
    @Provides
    @Singleton
    fun provideAuthInterceptor(tokenManager: TokenManager): AuthInterceptor {
        return AuthInterceptor(tokenManager)
    }

    /**
     * 提供OkHttpClient
     * 配置：
     * - 连接/读取/写入超时30秒
     * - 添加认证拦截器（在日志拦截器之前，避免Token泄露到日志）
     * - 添加日志拦截器
     * - 启用连接失败重试
     */
    @Provides
    @Singleton
    fun provideOkHttpClient(
        loggingInterceptor: HttpLoggingInterceptor,
        authInterceptor: AuthInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            // 认证拦截器在日志拦截器之前添加
            // 避免日志中泄露Token
            .addInterceptor(authInterceptor)
            .addInterceptor(loggingInterceptor)
            .connectTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .readTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .writeTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)
            .build()
    }

    /**
     * 提供Retrofit实例
     * 使用Gson作为JSON转换器
     */
    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    /**
     * 提供ApiService实例
     */
    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }
}
