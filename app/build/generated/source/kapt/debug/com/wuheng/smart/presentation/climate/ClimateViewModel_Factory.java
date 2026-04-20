package com.wuheng.smart.presentation.climate;

import com.wuheng.smart.data.network.TokenManager;
import com.wuheng.smart.data.repository.ClimateRepository;
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
public final class ClimateViewModel_Factory implements Factory<ClimateViewModel> {
  private final Provider<ClimateRepository> climateRepositoryProvider;

  private final Provider<TokenManager> tokenManagerProvider;

  public ClimateViewModel_Factory(Provider<ClimateRepository> climateRepositoryProvider,
      Provider<TokenManager> tokenManagerProvider) {
    this.climateRepositoryProvider = climateRepositoryProvider;
    this.tokenManagerProvider = tokenManagerProvider;
  }

  @Override
  public ClimateViewModel get() {
    return newInstance(climateRepositoryProvider.get(), tokenManagerProvider.get());
  }

  public static ClimateViewModel_Factory create(
      Provider<ClimateRepository> climateRepositoryProvider,
      Provider<TokenManager> tokenManagerProvider) {
    return new ClimateViewModel_Factory(climateRepositoryProvider, tokenManagerProvider);
  }

  public static ClimateViewModel newInstance(ClimateRepository climateRepository,
      TokenManager tokenManager) {
    return new ClimateViewModel(climateRepository, tokenManager);
  }
}
