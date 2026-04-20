package com.wuheng.smart.di

import com.wuheng.smart.BuildConfig
import com.wuheng.smart.data.network.ApiService
import com.wuheng.smart.data.network.TokenManager
import com.wuheng.smart.data.repository.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

/**
 * Repository依赖注入模块
 *
 * 提供所有Repository的实例，支持Mock模式和真实API模式的切换
 * 通过BuildConfig.DEBUG自动决定默认模式
 */
@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    /**
     * 控制是否使用Mock数据
     * 默认使用真实API，设置为true时使用Mock数据
     */
    @Provides
    @Singleton
    @Named("useMock")
    fun provideUseMock(): Boolean = false

    /**
     * 提供HomeRepository实例
     */
    @Provides
    @Singleton
    fun provideHomeRepository(
        apiService: ApiService,
        @Named("useMock") useMock: Boolean
    ): HomeRepository {
        return HomeRepositoryImpl(apiService, useMock)
    }

    /**
     * 提供ClimateRepository实例
     */
    @Provides
    @Singleton
    fun provideClimateRepository(
        apiService: ApiService,
        @Named("useMock") useMock: Boolean
    ): ClimateRepository {
        return ClimateRepositoryImpl(apiService, useMock)
    }

    /**
     * 提供WaterRepository实例
     */
    @Provides
    @Singleton
    fun provideWaterRepository(
        apiService: ApiService,
        @Named("useMock") useMock: Boolean
    ): WaterRepository {
        return WaterRepositoryImpl(apiService, useMock)
    }

    /**
     * 提供UserRepository实例
     */
    @Provides
    @Singleton
    fun provideUserRepository(
        apiService: ApiService,
        tokenManager: TokenManager,
        @Named("useMock") useMock: Boolean
    ): UserRepository {
        return UserRepositoryImpl(apiService, tokenManager, useMock)
    }
}
