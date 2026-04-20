package com.wuheng.smart.data.repository;

import com.wuheng.smart.data.network.ApiService;
import com.wuheng.smart.data.network.TokenManager;
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
public final class UserRepositoryImpl_Factory implements Factory<UserRepositoryImpl> {
  private final Provider<ApiService> apiServiceProvider;

  private final Provider<TokenManager> tokenManagerProvider;

  private final Provider<Boolean> useMockProvider;

  public UserRepositoryImpl_Factory(Provider<ApiService> apiServiceProvider,
      Provider<TokenManager> tokenManagerProvider, Provider<Boolean> useMockProvider) {
    this.apiServiceProvider = apiServiceProvider;
    this.tokenManagerProvider = tokenManagerProvider;
    this.useMockProvider = useMockProvider;
  }

  @Override
  public UserRepositoryImpl get() {
    return newInstance(apiServiceProvider.get(), tokenManagerProvider.get(), useMockProvider.get());
  }

  public static UserRepositoryImpl_Factory create(Provider<ApiService> apiServiceProvider,
      Provider<TokenManager> tokenManagerProvider, Provider<Boolean> useMockProvider) {
    return new UserRepositoryImpl_Factory(apiServiceProvider, tokenManagerProvider, useMockProvider);
  }

  public static UserRepositoryImpl newInstance(ApiService apiService, TokenManager tokenManager,
      boolean useMock) {
    return new UserRepositoryImpl(apiService, tokenManager, useMock);
  }
}
