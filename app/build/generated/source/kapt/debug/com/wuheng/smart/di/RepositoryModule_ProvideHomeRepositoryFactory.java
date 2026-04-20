package com.wuheng.smart.di;

import com.wuheng.smart.data.network.ApiService;
import com.wuheng.smart.data.repository.HomeRepository;
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
public final class RepositoryModule_ProvideHomeRepositoryFactory implements Factory<HomeRepository> {
  private final Provider<ApiService> apiServiceProvider;

  private final Provider<Boolean> useMockProvider;

  public RepositoryModule_ProvideHomeRepositoryFactory(Provider<ApiService> apiServiceProvider,
      Provider<Boolean> useMockProvider) {
    this.apiServiceProvider = apiServiceProvider;
    this.useMockProvider = useMockProvider;
  }

  @Override
  public HomeRepository get() {
    return provideHomeRepository(apiServiceProvider.get(), useMockProvider.get());
  }

  public static RepositoryModule_ProvideHomeRepositoryFactory create(
      Provider<ApiService> apiServiceProvider, Provider<Boolean> useMockProvider) {
    return new RepositoryModule_ProvideHomeRepositoryFactory(apiServiceProvider, useMockProvider);
  }

  public static HomeRepository provideHomeRepository(ApiService apiService, boolean useMock) {
    return Preconditions.checkNotNullFromProvides(RepositoryModule.INSTANCE.provideHomeRepository(apiService, useMock));
  }
}
