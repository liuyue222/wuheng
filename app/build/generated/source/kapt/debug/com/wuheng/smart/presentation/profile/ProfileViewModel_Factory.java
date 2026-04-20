package com.wuheng.smart.presentation.profile;

import com.wuheng.smart.data.network.TokenManager;
import com.wuheng.smart.data.repository.UserRepository;
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

  public ProfileViewModel_Factory(Provider<TokenManager> tokenManagerProvider,
      Provider<UserRepository> userRepositoryProvider) {
    this.tokenManagerProvider = tokenManagerProvider;
    this.userRepositoryProvider = userRepositoryProvider;
  }

  @Override
  public ProfileViewModel get() {
    return newInstance(tokenManagerProvider.get(), userRepositoryProvider.get());
  }

  public static ProfileViewModel_Factory create(Provider<TokenManager> tokenManagerProvider,
      Provider<UserRepository> userRepositoryProvider) {
    return new ProfileViewModel_Factory(tokenManagerProvider, userRepositoryProvider);
  }

  public static ProfileViewModel newInstance(TokenManager tokenManager,
      UserRepository userRepository) {
    return new ProfileViewModel(tokenManager, userRepository);
  }
}
