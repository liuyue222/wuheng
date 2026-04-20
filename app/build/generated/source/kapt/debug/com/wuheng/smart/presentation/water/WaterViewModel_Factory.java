package com.wuheng.smart.presentation.water;

import com.wuheng.smart.data.network.TokenManager;
import com.wuheng.smart.data.repository.WaterRepository;
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
public final class WaterViewModel_Factory implements Factory<WaterViewModel> {
  private final Provider<WaterRepository> waterRepositoryProvider;

  private final Provider<TokenManager> tokenManagerProvider;

  public WaterViewModel_Factory(Provider<WaterRepository> waterRepositoryProvider,
      Provider<TokenManager> tokenManagerProvider) {
    this.waterRepositoryProvider = waterRepositoryProvider;
    this.tokenManagerProvider = tokenManagerProvider;
  }

  @Override
  public WaterViewModel get() {
    return newInstance(waterRepositoryProvider.get(), tokenManagerProvider.get());
  }

  public static WaterViewModel_Factory create(Provider<WaterRepository> waterRepositoryProvider,
      Provider<TokenManager> tokenManagerProvider) {
    return new WaterViewModel_Factory(waterRepositoryProvider, tokenManagerProvider);
  }

  public static WaterViewModel newInstance(WaterRepository waterRepository,
      TokenManager tokenManager) {
    return new WaterViewModel(waterRepository, tokenManager);
  }
}
