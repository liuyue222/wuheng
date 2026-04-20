package com.wuheng.smart.di;

import com.wuheng.smart.data.network.ApiService;
import com.wuheng.smart.data.repository.ClimateRepository;
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
public final class RepositoryModule_ProvideClimateRepositoryFactory implements Factory<ClimateRepository> {
  private final Provider<ApiService> apiServiceProvider;

  private final Provider<Boolean> useMockProvider;

  public RepositoryModule_ProvideClimateRepositoryFactory(Provider<ApiService> apiServiceProvider,
      Provider<Boolean> useMockProvider) {
    this.apiServiceProvider = apiServiceProvider;
    this.useMockProvider = useMockProvider;
  }

  @Override
  public ClimateRepository get() {
    return provideClimateRepository(apiServiceProvider.get(), useMockProvider.get());
  }

  public static RepositoryModule_ProvideClimateRepositoryFactory create(
      Provider<ApiService> apiServiceProvider, Provider<Boolean> useMockProvider) {
    return new RepositoryModule_ProvideClimateRepositoryFactory(apiServiceProvider, useMockProvider);
  }

  public static ClimateRepository provideClimateRepository(ApiService apiService, boolean useMock) {
    return Preconditions.checkNotNullFromProvides(RepositoryModule.INSTANCE.provideClimateRepository(apiService, useMock));
  }
}
