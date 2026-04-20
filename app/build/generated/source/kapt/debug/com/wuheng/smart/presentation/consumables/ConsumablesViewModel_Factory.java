package com.wuheng.smart.presentation.consumables;

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
public final class ConsumablesViewModel_Factory implements Factory<ConsumablesViewModel> {
  private final Provider<WaterRepository> waterRepositoryProvider;

  private final Provider<TokenManager> tokenManagerProvider;

  public ConsumablesViewModel_Factory(Provider<WaterRepository> waterRepositoryProvider,
      Provider<TokenManager> tokenManagerProvider) {
    this.waterRepositoryProvider = waterRepositoryProvider;
    this.tokenManagerProvider = tokenManagerProvider;
  }

  @Override
  public ConsumablesViewModel get() {
    return newInstance(waterRepositoryProvider.get(), tokenManagerProvider.get());
  }

  public static ConsumablesViewModel_Factory create(
      Provider<WaterRepository> waterRepositoryProvider,
      Provider<TokenManager> tokenManagerProvider) {
    return new ConsumablesViewModel_Factory(waterRepositoryProvider, tokenManagerProvider);
  }

  public static ConsumablesViewModel newInstance(WaterRepository waterRepository,
      TokenManager tokenManager) {
    return new ConsumablesViewModel(waterRepository, tokenManager);
  }
}
