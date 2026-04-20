package com.wuheng.smart.presentation.device;

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
public final class DeviceDetailViewModel_Factory implements Factory<DeviceDetailViewModel> {
  private final Provider<HomeRepository> homeRepositoryProvider;

  public DeviceDetailViewModel_Factory(Provider<HomeRepository> homeRepositoryProvider) {
    this.homeRepositoryProvider = homeRepositoryProvider;
  }

  @Override
  public DeviceDetailViewModel get() {
    return newInstance(homeRepositoryProvider.get());
  }

  public static DeviceDetailViewModel_Factory create(
      Provider<HomeRepository> homeRepositoryProvider) {
    return new DeviceDetailViewModel_Factory(homeRepositoryProvider);
  }

  public static DeviceDetailViewModel newInstance(HomeRepository homeRepository) {
    return new DeviceDetailViewModel(homeRepository);
  }
}
