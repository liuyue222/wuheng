package com.wuheng.smart.di;

import com.wuheng.smart.data.network.ApiService;
import com.wuheng.smart.data.repository.WaterRepository;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata("javax.inject.Singleton")
@QualifierMetadata("javax.inject.Named")
@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes"
})
public final class RepositoryModule_ProvideWaterRepositoryFactory implements Factory<WaterRepository> {
  private final Provider<ApiService> apiServiceProvider;

  private final Provider<Boolean> useMockProvider;

  public RepositoryModule_ProvideWaterRepositoryFactory(Provider<ApiService> apiServiceProvider,
      Provider<Boolean> useMockProvider) {
    this.apiServiceProvider = apiServiceProvider;
    this.useMockProvider = useMockProvider;
  }

  @Override
  public WaterRepository get() {
    return provideWaterRepository(apiServiceProvider.get(), useMockProvider.get());
  }

  public static RepositoryModule_ProvideWaterRepositoryFactory create(
      Provider<ApiService> apiServiceProvider, Provider<Boolean> useMockProvider) {
    return new RepositoryModule_ProvideWaterRepositoryFactory(apiServiceProvider, useMockProvider);
  }

  public static WaterRepository provideWaterRepository(ApiService apiService, boolean useMock) {
    return Preconditions.checkNotNullFromProvides(RepositoryModule.INSTANCE.provideWaterRepository(apiService, useMock));
  }
}
