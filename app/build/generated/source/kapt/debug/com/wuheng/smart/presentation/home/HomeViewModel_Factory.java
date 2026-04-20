package com.wuheng.smart.presentation.home;

import com.wuheng.smart.data.network.TokenManager;
import com.wuheng.smart.data.repository.HomeRepository;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata
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
public final class HomeViewModel_Factory implements Factory<HomeViewModel> {
  private final Provider<HomeRepository> homeRepositoryProvider;

  private final Provider<TokenManager> tokenManagerProvider;

  public HomeViewModel_Factory(Provider<HomeRepository> homeRepositoryProvider,
      Provider<TokenManager> tokenManagerProvider) {
    this.homeRepositoryProvider = homeRepositoryProvider;
    this.tokenManagerProvider = tokenManagerProvider;
  }

  @Override
  public HomeViewModel get() {
    return newInstance(homeRepositoryProvider.get(), tokenManagerProvider.get());
  }

  public static HomeViewModel_Factory create(Provider<HomeRepository> homeRepositoryProvider,
      Provider<TokenManager> tokenManagerProvider) {
    return new HomeViewModel_Factory(homeRepositoryProvider, tokenManagerProvider);
  }

  public static HomeViewModel newInstance(HomeRepository homeRepository,
      TokenManager tokenManager) {
    return new HomeViewModel(homeRepository, tokenManager);
  }
}
