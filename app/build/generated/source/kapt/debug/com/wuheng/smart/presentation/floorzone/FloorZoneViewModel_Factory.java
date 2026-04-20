package com.wuheng.smart.presentation.floorzone;

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
public final class FloorZoneViewModel_Factory implements Factory<FloorZoneViewModel> {
  private final Provider<HomeRepository> homeRepositoryProvider;

  private final Provider<TokenManager> tokenManagerProvider;

  public FloorZoneViewModel_Factory(Provider<HomeRepository> homeRepositoryProvider,
      Provider<TokenManager> tokenManagerProvider) {
    this.homeRepositoryProvider = homeRepositoryProvider;
    this.tokenManagerProvider = tokenManagerProvider;
  }

  @Override
  public FloorZoneViewModel get() {
    return newInstance(homeRepositoryProvider.get(), tokenManagerProvider.get());
  }

  public static FloorZoneViewModel_Factory create(Provider<HomeRepository> homeRepositoryProvider,
      Provider<TokenManager> tokenManagerProvider) {
    return new FloorZoneViewModel_Factory(homeRepositoryProvider, tokenManagerProvider);
  }

  public static FloorZoneViewModel newInstance(HomeRepository homeRepository,
      TokenManager tokenManager) {
    return new FloorZoneViewModel(homeRepository, tokenManager);
  }
}
