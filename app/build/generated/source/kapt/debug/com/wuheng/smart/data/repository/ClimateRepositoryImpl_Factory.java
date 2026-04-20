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
public final class ClimateRepositoryImpl_Factory implements Factory<ClimateRepositoryImpl> {
  private final Provider<ApiService> apiServiceProvider;

  private final Provider<Boolean> useMockProvider;

  public ClimateRepositoryImpl_Factory(Provider<ApiService> apiServiceProvider,
      Provider<Boolean> useMockProvider) {
    this.apiServiceProvider = apiServiceProvider;
    this.useMockProvider = useMockProvider;
  }

  @Override
  public ClimateRepositoryImpl get() {
    return newInstance(apiServiceProvider.get(), useMockProvider.get());
  }

  public static ClimateRepositoryImpl_Factory create(Provider<ApiService> apiServiceProvider,
      Provider<Boolean> useMockProvider) {
    return new ClimateRepositoryImpl_Factory(apiServiceProvider, useMockProvider);
  }

  public static ClimateRepositoryImpl newInstance(ApiService apiService, boolean useMock) {
    return new ClimateRepositoryImpl(apiService, useMock);
  }
}
