package com.wuheng.smart.di;

import com.wuheng.smart.data.network.ApiService;
import com.wuheng.smart.data.network.TokenManager;
import com.wuheng.smart.data.repository.UserRepository;
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
public final class RepositoryModule_ProvideUserRepositoryFactory implements Factory<UserRepository> {
  private final Provider<ApiService> apiServiceProvider;

  private final Provider<TokenManager> tokenManagerProvider;

  private final Provider<Boolean> useMockProvider;

  public RepositoryModule_ProvideUserRepositoryFactory(Provider<ApiService> apiServiceProvider,
      Provider<TokenManager> tokenManagerProvider, Provider<Boolean> useMockProvider) {
    this.apiServiceProvider = apiServiceProvider;
    this.tokenManagerProvider = tokenManagerProvider;
    this.useMockProvider = useMockProvider;
  }

  @Override
  public UserRepository get() {
    return provideUserRepository(apiServiceProvider.get(), tokenManagerProvider.get(), useMockProvider.get());
  }

  public static RepositoryModule_ProvideUserRepositoryFactory create(
      Provider<ApiService> apiServiceProvider, Provider<TokenManager> tokenManagerProvider,
      Provider<Boolean> useMockProvider) {
    return new RepositoryModule_ProvideUserRepositoryFactory(apiServiceProvider, tokenManagerProvider, useMockProvider);
  }

  public static UserRepository provideUserRepository(ApiService apiService,
      TokenManager tokenManager, boolean useMock) {
    return Preconditions.checkNotNullFromProvides(RepositoryModule.INSTANCE.provideUserRepository(apiService, tokenManager, useMock));
  }
}
