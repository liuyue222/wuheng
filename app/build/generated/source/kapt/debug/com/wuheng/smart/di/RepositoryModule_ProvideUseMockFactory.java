package com.wuheng.smart.di;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;

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
public final class RepositoryModule_ProvideUseMockFactory implements Factory<Boolean> {
  @Override
  public Boolean get() {
    return provideUseMock();
  }

  public static RepositoryModule_ProvideUseMockFactory create() {
    return InstanceHolder.INSTANCE;
  }

  public static boolean provideUseMock() {
    return RepositoryModule.INSTANCE.provideUseMock();
  }

  private static final class InstanceHolder {
    private static final RepositoryModule_ProvideUseMockFactory INSTANCE = new RepositoryModule_ProvideUseMockFactory();
  }
}
