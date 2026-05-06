package com.wuheng.smart.presentation.profile;

import com.wuheng.smart.data.network.TokenManager;
import com.wuheng.smart.data.repository.HomeRepository;
import com.wuheng.smart.data.repository.UserRepository;
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
public final class ProfileViewModel_Factory implements Factory<ProfileViewModel> {
  private final Provider<TokenManager> tokenManagerProvider;

  private final Provider<UserRepository> userRepositoryProvider;

  private final Provider<WaterRepository> waterRepositoryProvider;

  private final Provider<HomeRepository> homeRepositoryProvider;

  public ProfileViewModel_Factory(Provider<TokenManager> tokenManagerProvider,
      Provider<UserRepository> userRepositoryProvider,
      Provider<WaterRepository> waterRepositoryProvider,
      Provider<HomeRepository> homeRepositoryProvider) {
    this.tokenManagerProvider = tokenManagerProvider;
    this.userRepositoryProvider = userRepositoryProvider;
    this.waterRepositoryProvider = waterRepositoryProvider;
    this.homeRepositoryProvider = homeRepositoryProvider;
  }

  @Override
  public ProfileViewModel get() {
    return newInstance(tokenManagerProvider.get(), userRepositoryProvider.get(), waterRepositoryProvider.get(), homeRepositoryProvider.get());
  }

  public static ProfileViewModel_Factory create(Provider<TokenManager> tokenManagerProvider,
      Provider<UserRepository> userRepositoryProvider,
      Provider<WaterRepository> waterRepositoryProvider,
      Provider<HomeRepository> homeRepositoryProvider) {
    return new ProfileViewModel_Factory(tokenManagerProvider, userRepositoryProvider, waterRepositoryProvider, homeRepositoryProvider);
  }

  public static ProfileViewModel newInstance(TokenManager tokenManager,
      UserRepository userRepository, WaterRepository waterRepository,
      HomeRepository homeRepository) {
    return new ProfileViewModel(tokenManager, userRepository, waterRepository, homeRepository);
  }
}
