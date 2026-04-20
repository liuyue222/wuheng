package com.wuheng.smart.data.repository;

import com.wuheng.smart.data.network.ApiService;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata("javax.inject.Singleton")
@QualifierMetadata
@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes"
})
public final class WaterRepositoryImpl_Factory implements Factory<WaterRepositoryImpl> {
  private final Provider<ApiService> apiServiceProvider;

  private final Provider<Boolean> useMockProvider;

  public WaterRepositoryImpl_Factory(Provider<ApiService> apiServiceProvider,
      Provider<Boolean> useMockProvider) {
    this.apiServiceProvider = apiServiceProvider;
    this.useMockProvider = useMockProvider;
  }

  @Override
  public WaterRepositoryImpl get() {
    return newInstance(apiServiceProvider.get(), useMockProvider.get());
  }

  public static WaterRepositoryImpl_Factory create(Provider<ApiService> apiServiceProvider,
      Provider<Boolean> useMockProvider) {
    return new WaterRepositoryImpl_Factory(apiServiceProvider, useMockProvider);
  }

  public static WaterRepositoryImpl newInstance(ApiService apiService, boolean useMock) {
    return new WaterRepositoryImpl(apiService, useMock);
  }
}
