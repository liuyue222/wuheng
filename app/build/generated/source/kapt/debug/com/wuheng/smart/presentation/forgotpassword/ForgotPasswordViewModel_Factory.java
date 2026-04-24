package com.wuheng.smart.presentation.forgotpassword;

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
public final class ForgotPasswordViewModel_Factory implements Factory<ForgotPasswordViewModel> {
  private final Provider<UserRepository> userRepositoryProvider;

  public ForgotPasswordViewModel_Factory(Provider<UserRepository> userRepositoryProvider) {
    this.userRepositoryProvider = userRepositoryProvider;
  }

  @Override
  public ForgotPasswordViewModel get() {
    return newInstance(userRepositoryProvider.get());
  }

  public static ForgotPasswordViewModel_Factory create(
      Provider<UserRepository> userRepositoryProvider) {
    return new ForgotPasswordViewModel_Factory(userRepositoryProvider);
  }

  public static ForgotPasswordViewModel newInstance(UserRepository userRepository) {
    return new ForgotPasswordViewModel(userRepository);
  }
}
