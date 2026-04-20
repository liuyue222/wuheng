package com.wuheng.smart.di;

import com.wuheng.smart.BuildConfig;
import com.wuheng.smart.data.network.ApiService;
import com.wuheng.smart.data.network.TokenManager;
import com.wuheng.smart.data.repository.*;
import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;
import javax.inject.Named;
import javax.inject.Singleton;

/**
 * Repository依赖注入模块
 *
 * 提供所有Repository的实例，支持Mock模式和真实API模式的切换
 * 通过BuildConfig.DEBUG自动决定默认模式
 */
@dagger.hilt.InstallIn(value = {dagger.hilt.components.SingletonComponent.class})
@kotlin.Metadata(mv = {1, 7, 1}, k = 1, d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u00c7\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u001a\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\b\b\u0001\u0010\u0007\u001a\u00020\bH\u0007J\u001a\u0010\t\u001a\u00020\n2\u0006\u0010\u0005\u001a\u00020\u00062\b\b\u0001\u0010\u0007\u001a\u00020\bH\u0007J\b\u0010\u000b\u001a\u00020\bH\u0007J\"\u0010\f\u001a\u00020\r2\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u000e\u001a\u00020\u000f2\b\b\u0001\u0010\u0007\u001a\u00020\bH\u0007J\u001a\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0005\u001a\u00020\u00062\b\b\u0001\u0010\u0007\u001a\u00020\bH\u0007\u00a8\u0006\u0012"}, d2 = {"Lcom/wuheng/smart/di/RepositoryModule;", "", "()V", "provideClimateRepository", "Lcom/wuheng/smart/data/repository/ClimateRepository;", "apiService", "Lcom/wuheng/smart/data/network/ApiService;", "useMock", "", "provideHomeRepository", "Lcom/wuheng/smart/data/repository/HomeRepository;", "provideUseMock", "provideUserRepository", "Lcom/wuheng/smart/data/repository/UserRepository;", "tokenManager", "Lcom/wuheng/smart/data/network/TokenManager;", "provideWaterRepository", "Lcom/wuheng/smart/data/repository/WaterRepository;", "app_debug"})
@dagger.Module()
public final class RepositoryModule {
    @org.jetbrains.annotations.NotNull()
    public static final com.wuheng.smart.di.RepositoryModule INSTANCE = null;
    
    private RepositoryModule() {
        super();
    }
    
    /**
     * 控制是否使用Mock数据
     * 默认使用真实API，设置为true时使用Mock数据
     */
    @javax.inject.Named(value = "useMock")
    @javax.inject.Singleton()
    @dagger.Provides()
    public final boolean provideUseMock() {
        return false;
    }
    
    /**
     * 提供HomeRepository实例
     */
    @org.jetbrains.annotations.NotNull()
    @javax.inject.Singleton()
    @dagger.Provides()
    public final com.wuheng.smart.data.repository.HomeRepository provideHomeRepository(@org.jetbrains.annotations.NotNull()
    com.wuheng.smart.data.network.ApiService apiService, @javax.inject.Named(value = "useMock")
    boolean useMock) {
        return null;
    }
    
    /**
     * 提供ClimateRepository实例
     */
    @org.jetbrains.annotations.NotNull()
    @javax.inject.Singleton()
    @dagger.Provides()
    public final com.wuheng.smart.data.repository.ClimateRepository provideClimateRepository(@org.jetbrains.annotations.NotNull()
    com.wuheng.smart.data.network.ApiService apiService, @javax.inject.Named(value = "useMock")
    boolean useMock) {
        return null;
    }
    
    /**
     * 提供WaterRepository实例
     */
    @org.jetbrains.annotations.NotNull()
    @javax.inject.Singleton()
    @dagger.Provides()
    public final com.wuheng.smart.data.repository.WaterRepository provideWaterRepository(@org.jetbrains.annotations.NotNull()
    com.wuheng.smart.data.network.ApiService apiService, @javax.inject.Named(value = "useMock")
    boolean useMock) {
        return null;
    }
    
    /**
     * 提供UserRepository实例
     */
    @org.jetbrains.annotations.NotNull()
    @javax.inject.Singleton()
    @dagger.Provides()
    public final com.wuheng.smart.data.repository.UserRepository provideUserRepository(@org.jetbrains.annotations.NotNull()
    com.wuheng.smart.data.network.ApiService apiService, @org.jetbrains.annotations.NotNull()
    com.wuheng.smart.data.network.TokenManager tokenManager, @javax.inject.Named(value = "useMock")
    boolean useMock) {
        return null;
    }
}