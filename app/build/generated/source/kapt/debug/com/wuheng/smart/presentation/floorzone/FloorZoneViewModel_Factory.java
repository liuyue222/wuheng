package com.wuheng.smart.presentation.floorzone;

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

  public FloorZoneViewModel_Factory(Provider<HomeRepository> homeRepositoryProvider) {
    this.homeRepositoryProvider = homeRepositoryProvider;
  }

  @Override
  public FloorZoneViewModel get() {
    return newInstance(homeRepositoryProvider.get());
  }

  public static FloorZoneViewModel_Factory create(Provider<HomeRepository> homeRepositoryProvider) {
    return new FloorZoneViewModel_Factory(homeRepositoryProvider);
  }

  public static FloorZoneViewModel newInstance(HomeRepository homeRepository) {
    return new FloorZoneViewModel(homeRepository);
  }
}
