package com.wuheng.smart;

import android.app.Activity;
import android.app.Service;
import android.view.View;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;
import com.wuheng.smart.data.network.ApiService;
import com.wuheng.smart.data.network.AuthInterceptor;
import com.wuheng.smart.data.network.NetworkModule;
import com.wuheng.smart.data.network.NetworkModule_ProvideApiServiceFactory;
import com.wuheng.smart.data.network.NetworkModule_ProvideAuthInterceptorFactory;
import com.wuheng.smart.data.network.NetworkModule_ProvideHttpLoggingInterceptorFactory;
import com.wuheng.smart.data.network.NetworkModule_ProvideOkHttpClientFactory;
import com.wuheng.smart.data.network.NetworkModule_ProvideRetrofitFactory;
import com.wuheng.smart.data.network.NetworkModule_ProvideTokenManagerFactory;
import com.wuheng.smart.data.network.TokenManager;
import com.wuheng.smart.data.repository.ClimateRepository;
import com.wuheng.smart.data.repository.HomeRepository;
import com.wuheng.smart.data.repository.UserRepository;
import com.wuheng.smart.data.repository.WaterRepository;
import com.wuheng.smart.di.ImageModule;
import com.wuheng.smart.di.RepositoryModule;
import com.wuheng.smart.di.RepositoryModule_ProvideClimateRepositoryFactory;
import com.wuheng.smart.di.RepositoryModule_ProvideHomeRepositoryFactory;
import com.wuheng.smart.di.RepositoryModule_ProvideUserRepositoryFactory;
import com.wuheng.smart.di.RepositoryModule_ProvideWaterRepositoryFactory;
import com.wuheng.smart.initializer.LazyHiltComponents;
import com.wuheng.smart.presentation.about.AboutViewModel;
import com.wuheng.smart.presentation.about.AboutViewModel_HiltModules_KeyModule_ProvideFactory;
import com.wuheng.smart.presentation.climate.ClimateViewModel;
import com.wuheng.smart.presentation.climate.ClimateViewModel_HiltModules_KeyModule_ProvideFactory;
import com.wuheng.smart.presentation.consumables.ConsumablesViewModel;
import com.wuheng.smart.presentation.consumables.ConsumablesViewModel_HiltModules_KeyModule_ProvideFactory;
import com.wuheng.smart.presentation.device.DeviceDetailViewModel;
import com.wuheng.smart.presentation.device.DeviceDetailViewModel_HiltModules_KeyModule_ProvideFactory;
import com.wuheng.smart.presentation.floorzone.FloorZoneViewModel;
import com.wuheng.smart.presentation.floorzone.FloorZoneViewModel_HiltModules_KeyModule_ProvideFactory;
import com.wuheng.smart.presentation.forgotpassword.ForgotPasswordViewModel;
import com.wuheng.smart.presentation.forgotpassword.ForgotPasswordViewModel_HiltModules_KeyModule_ProvideFactory;
import com.wuheng.smart.presentation.home.HomeViewModel;
import com.wuheng.smart.presentation.home.HomeViewModel_HiltModules_KeyModule_ProvideFactory;
import com.wuheng.smart.presentation.login.LoginViewModel;
import com.wuheng.smart.presentation.login.LoginViewModel_HiltModules_KeyModule_ProvideFactory;
import com.wuheng.smart.presentation.notification.NotificationViewModel;
import com.wuheng.smart.presentation.notification.NotificationViewModel_HiltModules_KeyModule_ProvideFactory;
import com.wuheng.smart.presentation.profile.ProfileViewModel;
import com.wuheng.smart.presentation.profile.ProfileViewModel_HiltModules_KeyModule_ProvideFactory;
import com.wuheng.smart.presentation.register.RegisterViewModel;
import com.wuheng.smart.presentation.register.RegisterViewModel_HiltModules_KeyModule_ProvideFactory;
import com.wuheng.smart.presentation.settings.SettingViewModel;
import com.wuheng.smart.presentation.settings.SettingViewModel_HiltModules_KeyModule_ProvideFactory;
import com.wuheng.smart.presentation.water.WaterViewModel;
import com.wuheng.smart.presentation.water.WaterViewModel_HiltModules_KeyModule_ProvideFactory;
import dagger.hilt.android.ActivityRetainedLifecycle;
import dagger.hilt.android.ViewModelLifecycle;
import dagger.hilt.android.flags.HiltWrapper_FragmentGetContextFix_FragmentGetContextFixModule;
import dagger.hilt.android.internal.builders.ActivityComponentBuilder;
import dagger.hilt.android.internal.builders.ActivityRetainedComponentBuilder;
import dagger.hilt.android.internal.builders.FragmentComponentBuilder;
import dagger.hilt.android.internal.builders.ServiceComponentBuilder;
import dagger.hilt.android.internal.builders.ViewComponentBuilder;
import dagger.hilt.android.internal.builders.ViewModelComponentBuilder;
import dagger.hilt.android.internal.builders.ViewWithFragmentComponentBuilder;
import dagger.hilt.android.internal.lifecycle.DefaultViewModelFactories;
import dagger.hilt.android.internal.lifecycle.DefaultViewModelFactories_InternalFactoryFactory_Factory;
import dagger.hilt.android.internal.managers.ActivityRetainedComponentManager_LifecycleModule_ProvideActivityRetainedLifecycleFactory;
import dagger.hilt.android.internal.modules.ApplicationContextModule;
import dagger.hilt.android.internal.modules.ApplicationContextModule_ProvideContextFactory;
import dagger.internal.DaggerGenerated;
import dagger.internal.DoubleCheck;
import dagger.internal.MapBuilder;
import dagger.internal.Preconditions;
import dagger.internal.SetBuilder;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import javax.annotation.processing.Generated;
import javax.inject.Provider;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;

@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes"
})
public final class DaggerWuHengApplication_HiltComponents_SingletonC {
  private DaggerWuHengApplication_HiltComponents_SingletonC() {
  }

  public static Builder builder() {
    return new Builder();
  }

  public static final class Builder {
    private ApplicationContextModule applicationContextModule;

    private Builder() {
    }

    public Builder applicationContextModule(ApplicationContextModule applicationContextModule) {
      this.applicationContextModule = Preconditions.checkNotNull(applicationContextModule);
      return this;
    }

    /**
     * @deprecated This module is declared, but an instance is not used in the component. This method is a no-op. For more, see https://dagger.dev/unused-modules.
     */
    @Deprecated
    public Builder hiltWrapper_FragmentGetContextFix_FragmentGetContextFixModule(
        HiltWrapper_FragmentGetContextFix_FragmentGetContextFixModule hiltWrapper_FragmentGetContextFix_FragmentGetContextFixModule) {
      Preconditions.checkNotNull(hiltWrapper_FragmentGetContextFix_FragmentGetContextFixModule);
      return this;
    }

    /**
     * @deprecated This module is declared, but an instance is not used in the component. This method is a no-op. For more, see https://dagger.dev/unused-modules.
     */
    @Deprecated
    public Builder imageModule(ImageModule imageModule) {
      Preconditions.checkNotNull(imageModule);
      return this;
    }

    /**
     * @deprecated This module is declared, but an instance is not used in the component. This method is a no-op. For more, see https://dagger.dev/unused-modules.
     */
    @Deprecated
    public Builder networkModule(NetworkModule networkModule) {
      Preconditions.checkNotNull(networkModule);
      return this;
    }

    /**
     * @deprecated This module is declared, but an instance is not used in the component. This method is a no-op. For more, see https://dagger.dev/unused-modules.
     */
    @Deprecated
    public Builder repositoryModule(RepositoryModule repositoryModule) {
      Preconditions.checkNotNull(repositoryModule);
      return this;
    }

    public WuHengApplication_HiltComponents.SingletonC build() {
      Preconditions.checkBuilderRequirement(applicationContextModule, ApplicationContextModule.class);
      return new SingletonCImpl(applicationContextModule);
    }
  }

  private static final class ActivityRetainedCBuilder implements WuHengApplication_HiltComponents.ActivityRetainedC.Builder {
    private final SingletonCImpl singletonCImpl;

    private ActivityRetainedCBuilder(SingletonCImpl singletonCImpl) {
      this.singletonCImpl = singletonCImpl;
    }

    @Override
    public WuHengApplication_HiltComponents.ActivityRetainedC build() {
      return new ActivityRetainedCImpl(singletonCImpl);
    }
  }

  private static final class ActivityCBuilder implements WuHengApplication_HiltComponents.ActivityC.Builder {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private Activity activity;

    private ActivityCBuilder(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
    }

    @Override
    public ActivityCBuilder activity(Activity activity) {
      this.activity = Preconditions.checkNotNull(activity);
      return this;
    }

    @Override
    public WuHengApplication_HiltComponents.ActivityC build() {
      Preconditions.checkBuilderRequirement(activity, Activity.class);
      return new ActivityCImpl(singletonCImpl, activityRetainedCImpl, activity);
    }
  }

  private static final class FragmentCBuilder implements WuHengApplication_HiltComponents.FragmentC.Builder {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private Fragment fragment;

    private FragmentCBuilder(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, ActivityCImpl activityCImpl) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;
    }

    @Override
    public FragmentCBuilder fragment(Fragment fragment) {
      this.fragment = Preconditions.checkNotNull(fragment);
      return this;
    }

    @Override
    public WuHengApplication_HiltComponents.FragmentC build() {
      Preconditions.checkBuilderRequirement(fragment, Fragment.class);
      return new FragmentCImpl(singletonCImpl, activityRetainedCImpl, activityCImpl, fragment);
    }
  }

  private static final class ViewWithFragmentCBuilder implements WuHengApplication_HiltComponents.ViewWithFragmentC.Builder {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private final FragmentCImpl fragmentCImpl;

    private View view;

    private ViewWithFragmentCBuilder(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, ActivityCImpl activityCImpl,
        FragmentCImpl fragmentCImpl) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;
      this.fragmentCImpl = fragmentCImpl;
    }

    @Override
    public ViewWithFragmentCBuilder view(View view) {
      this.view = Preconditions.checkNotNull(view);
      return this;
    }

    @Override
    public WuHengApplication_HiltComponents.ViewWithFragmentC build() {
      Preconditions.checkBuilderRequirement(view, View.class);
      return new ViewWithFragmentCImpl(singletonCImpl, activityRetainedCImpl, activityCImpl, fragmentCImpl, view);
    }
  }

  private static final class ViewCBuilder implements WuHengApplication_HiltComponents.ViewC.Builder {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private View view;

    private ViewCBuilder(SingletonCImpl singletonCImpl, ActivityRetainedCImpl activityRetainedCImpl,
        ActivityCImpl activityCImpl) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;
    }

    @Override
    public ViewCBuilder view(View view) {
      this.view = Preconditions.checkNotNull(view);
      return this;
    }

    @Override
    public WuHengApplication_HiltComponents.ViewC build() {
      Preconditions.checkBuilderRequirement(view, View.class);
      return new ViewCImpl(singletonCImpl, activityRetainedCImpl, activityCImpl, view);
    }
  }

  private static final class ViewModelCBuilder implements WuHengApplication_HiltComponents.ViewModelC.Builder {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private SavedStateHandle savedStateHandle;

    private ViewModelLifecycle viewModelLifecycle;

    private ViewModelCBuilder(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
    }

    @Override
    public ViewModelCBuilder savedStateHandle(SavedStateHandle handle) {
      this.savedStateHandle = Preconditions.checkNotNull(handle);
      return this;
    }

    @Override
    public ViewModelCBuilder viewModelLifecycle(ViewModelLifecycle viewModelLifecycle) {
      this.viewModelLifecycle = Preconditions.checkNotNull(viewModelLifecycle);
      return this;
    }

    @Override
    public WuHengApplication_HiltComponents.ViewModelC build() {
      Preconditions.checkBuilderRequirement(savedStateHandle, SavedStateHandle.class);
      Preconditions.checkBuilderRequirement(viewModelLifecycle, ViewModelLifecycle.class);
      return new ViewModelCImpl(singletonCImpl, activityRetainedCImpl, savedStateHandle, viewModelLifecycle);
    }
  }

  private static final class ServiceCBuilder implements WuHengApplication_HiltComponents.ServiceC.Builder {
    private final SingletonCImpl singletonCImpl;

    private Service service;

    private ServiceCBuilder(SingletonCImpl singletonCImpl) {
      this.singletonCImpl = singletonCImpl;
    }

    @Override
    public ServiceCBuilder service(Service service) {
      this.service = Preconditions.checkNotNull(service);
      return this;
    }

    @Override
    public WuHengApplication_HiltComponents.ServiceC build() {
      Preconditions.checkBuilderRequirement(service, Service.class);
      return new ServiceCImpl(singletonCImpl, service);
    }
  }

  private static final class ViewWithFragmentCImpl extends WuHengApplication_HiltComponents.ViewWithFragmentC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private final FragmentCImpl fragmentCImpl;

    private final ViewWithFragmentCImpl viewWithFragmentCImpl = this;

    private ViewWithFragmentCImpl(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, ActivityCImpl activityCImpl,
        FragmentCImpl fragmentCImpl, View viewParam) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;
      this.fragmentCImpl = fragmentCImpl;


    }
  }

  private static final class FragmentCImpl extends WuHengApplication_HiltComponents.FragmentC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private final FragmentCImpl fragmentCImpl = this;

    private FragmentCImpl(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, ActivityCImpl activityCImpl,
        Fragment fragmentParam) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;


    }

    @Override
    public DefaultViewModelFactories.InternalFactoryFactory getHiltInternalFactoryFactory() {
      return activityCImpl.getHiltInternalFactoryFactory();
    }

    @Override
    public ViewWithFragmentComponentBuilder viewWithFragmentComponentBuilder() {
      return new ViewWithFragmentCBuilder(singletonCImpl, activityRetainedCImpl, activityCImpl, fragmentCImpl);
    }
  }

  private static final class ViewCImpl extends WuHengApplication_HiltComponents.ViewC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private final ViewCImpl viewCImpl = this;

    private ViewCImpl(SingletonCImpl singletonCImpl, ActivityRetainedCImpl activityRetainedCImpl,
        ActivityCImpl activityCImpl, View viewParam) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;


    }
  }

  private static final class ActivityCImpl extends WuHengApplication_HiltComponents.ActivityC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl = this;

    private ActivityCImpl(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, Activity activityParam) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;


    }

    @Override
    public void injectMainActivity(MainActivity arg0) {
    }

    @Override
    public DefaultViewModelFactories.InternalFactoryFactory getHiltInternalFactoryFactory() {
      return DefaultViewModelFactories_InternalFactoryFactory_Factory.newInstance(getViewModelKeys(), new ViewModelCBuilder(singletonCImpl, activityRetainedCImpl));
    }

    @Override
    public Set<String> getViewModelKeys() {
      return SetBuilder.<String>newSetBuilder(14).add(AboutViewModel_HiltModules_KeyModule_ProvideFactory.provide()).add(ClimateViewModel_HiltModules_KeyModule_ProvideFactory.provide()).add(ConsumablesViewModel_HiltModules_KeyModule_ProvideFactory.provide()).add(DeviceDetailViewModel_HiltModules_KeyModule_ProvideFactory.provide()).add(FloorZoneViewModel_HiltModules_KeyModule_ProvideFactory.provide()).add(ForgotPasswordViewModel_HiltModules_KeyModule_ProvideFactory.provide()).add(HomeViewModel_HiltModules_KeyModule_ProvideFactory.provide()).add(LoginViewModel_HiltModules_KeyModule_ProvideFactory.provide()).add(MainViewModel_HiltModules_KeyModule_ProvideFactory.provide()).add(NotificationViewModel_HiltModules_KeyModule_ProvideFactory.provide()).add(ProfileViewModel_HiltModules_KeyModule_ProvideFactory.provide()).add(RegisterViewModel_HiltModules_KeyModule_ProvideFactory.provide()).add(SettingViewModel_HiltModules_KeyModule_ProvideFactory.provide()).add(WaterViewModel_HiltModules_KeyModule_ProvideFactory.provide()).build();
    }

    @Override
    public ViewModelComponentBuilder getViewModelComponentBuilder() {
      return new ViewModelCBuilder(singletonCImpl, activityRetainedCImpl);
    }

    @Override
    public FragmentComponentBuilder fragmentComponentBuilder() {
      return new FragmentCBuilder(singletonCImpl, activityRetainedCImpl, activityCImpl);
    }

    @Override
    public ViewComponentBuilder viewComponentBuilder() {
      return new ViewCBuilder(singletonCImpl, activityRetainedCImpl, activityCImpl);
    }
  }

  private static final class ViewModelCImpl extends WuHengApplication_HiltComponents.ViewModelC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ViewModelCImpl viewModelCImpl = this;

    private Provider<AboutViewModel> aboutViewModelProvider;

    private Provider<ClimateViewModel> climateViewModelProvider;

    private Provider<ConsumablesViewModel> consumablesViewModelProvider;

    private Provider<DeviceDetailViewModel> deviceDetailViewModelProvider;

    private Provider<FloorZoneViewModel> floorZoneViewModelProvider;

    private Provider<ForgotPasswordViewModel> forgotPasswordViewModelProvider;

    private Provider<HomeViewModel> homeViewModelProvider;

    private Provider<LoginViewModel> loginViewModelProvider;

    private Provider<MainViewModel> mainViewModelProvider;

    private Provider<NotificationViewModel> notificationViewModelProvider;

    private Provider<ProfileViewModel> profileViewModelProvider;

    private Provider<RegisterViewModel> registerViewModelProvider;

    private Provider<SettingViewModel> settingViewModelProvider;

    private Provider<WaterViewModel> waterViewModelProvider;

    private ViewModelCImpl(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, SavedStateHandle savedStateHandleParam,
        ViewModelLifecycle viewModelLifecycleParam) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;

      initialize(savedStateHandleParam, viewModelLifecycleParam);

    }

    @SuppressWarnings("unchecked")
    private void initialize(final SavedStateHandle savedStateHandleParam,
        final ViewModelLifecycle viewModelLifecycleParam) {
      this.aboutViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 0);
      this.climateViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 1);
      this.consumablesViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 2);
      this.deviceDetailViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 3);
      this.floorZoneViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 4);
      this.forgotPasswordViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 5);
      this.homeViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 6);
      this.loginViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 7);
      this.mainViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 8);
      this.notificationViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 9);
      this.profileViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 10);
      this.registerViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 11);
      this.settingViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 12);
      this.waterViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 13);
    }

    @Override
    public Map<String, Provider<ViewModel>> getHiltViewModelMap() {
      return MapBuilder.<String, Provider<ViewModel>>newMapBuilder(14).put("com.wuheng.smart.presentation.about.AboutViewModel", ((Provider) aboutViewModelProvider)).put("com.wuheng.smart.presentation.climate.ClimateViewModel", ((Provider) climateViewModelProvider)).put("com.wuheng.smart.presentation.consumables.ConsumablesViewModel", ((Provider) consumablesViewModelProvider)).put("com.wuheng.smart.presentation.device.DeviceDetailViewModel", ((Provider) deviceDetailViewModelProvider)).put("com.wuheng.smart.presentation.floorzone.FloorZoneViewModel", ((Provider) floorZoneViewModelProvider)).put("com.wuheng.smart.presentation.forgotpassword.ForgotPasswordViewModel", ((Provider) forgotPasswordViewModelProvider)).put("com.wuheng.smart.presentation.home.HomeViewModel", ((Provider) homeViewModelProvider)).put("com.wuheng.smart.presentation.login.LoginViewModel", ((Provider) loginViewModelProvider)).put("com.wuheng.smart.MainViewModel", ((Provider) mainViewModelProvider)).put("com.wuheng.smart.presentation.notification.NotificationViewModel", ((Provider) notificationViewModelProvider)).put("com.wuheng.smart.presentation.profile.ProfileViewModel", ((Provider) profileViewModelProvider)).put("com.wuheng.smart.presentation.register.RegisterViewModel", ((Provider) registerViewModelProvider)).put("com.wuheng.smart.presentation.settings.SettingViewModel", ((Provider) settingViewModelProvider)).put("com.wuheng.smart.presentation.water.WaterViewModel", ((Provider) waterViewModelProvider)).build();
    }

    private static final class SwitchingProvider<T> implements Provider<T> {
      private final SingletonCImpl singletonCImpl;

      private final ActivityRetainedCImpl activityRetainedCImpl;

      private final ViewModelCImpl viewModelCImpl;

      private final int id;

      SwitchingProvider(SingletonCImpl singletonCImpl, ActivityRetainedCImpl activityRetainedCImpl,
          ViewModelCImpl viewModelCImpl, int id) {
        this.singletonCImpl = singletonCImpl;
        this.activityRetainedCImpl = activityRetainedCImpl;
        this.viewModelCImpl = viewModelCImpl;
        this.id = id;
      }

      @SuppressWarnings("unchecked")
      @Override
      public T get() {
        switch (id) {
          case 0: // com.wuheng.smart.presentation.about.AboutViewModel 
          return (T) new AboutViewModel();

          case 1: // com.wuheng.smart.presentation.climate.ClimateViewModel 
          return (T) new ClimateViewModel(singletonCImpl.provideClimateRepositoryProvider.get(), singletonCImpl.provideTokenManagerProvider.get());

          case 2: // com.wuheng.smart.presentation.consumables.ConsumablesViewModel 
          return (T) new ConsumablesViewModel(singletonCImpl.provideWaterRepositoryProvider.get(), singletonCImpl.provideTokenManagerProvider.get());

          case 3: // com.wuheng.smart.presentation.device.DeviceDetailViewModel 
          return (T) new DeviceDetailViewModel(singletonCImpl.provideHomeRepositoryProvider.get());

          case 4: // com.wuheng.smart.presentation.floorzone.FloorZoneViewModel 
          return (T) new FloorZoneViewModel(singletonCImpl.provideHomeRepositoryProvider.get());

          case 5: // com.wuheng.smart.presentation.forgotpassword.ForgotPasswordViewModel 
          return (T) new ForgotPasswordViewModel();

          case 6: // com.wuheng.smart.presentation.home.HomeViewModel 
          return (T) new HomeViewModel(singletonCImpl.provideHomeRepositoryProvider.get(), singletonCImpl.provideTokenManagerProvider.get());

          case 7: // com.wuheng.smart.presentation.login.LoginViewModel 
          return (T) new LoginViewModel(singletonCImpl.provideUserRepositoryProvider.get());

          case 8: // com.wuheng.smart.MainViewModel 
          return (T) new MainViewModel(singletonCImpl.provideTokenManagerProvider.get());

          case 9: // com.wuheng.smart.presentation.notification.NotificationViewModel 
          return (T) new NotificationViewModel();

          case 10: // com.wuheng.smart.presentation.profile.ProfileViewModel 
          return (T) new ProfileViewModel(singletonCImpl.provideTokenManagerProvider.get(), singletonCImpl.provideUserRepositoryProvider.get());

          case 11: // com.wuheng.smart.presentation.register.RegisterViewModel 
          return (T) new RegisterViewModel(singletonCImpl.provideUserRepositoryProvider.get());

          case 12: // com.wuheng.smart.presentation.settings.SettingViewModel 
          return (T) new SettingViewModel(ApplicationContextModule_ProvideContextFactory.provideContext(singletonCImpl.applicationContextModule));

          case 13: // com.wuheng.smart.presentation.water.WaterViewModel 
          return (T) new WaterViewModel(singletonCImpl.provideWaterRepositoryProvider.get(), singletonCImpl.provideTokenManagerProvider.get());

          default: throw new AssertionError(id);
        }
      }
    }
  }

  private static final class ActivityRetainedCImpl extends WuHengApplication_HiltComponents.ActivityRetainedC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl = this;

    private Provider<ActivityRetainedLifecycle> provideActivityRetainedLifecycleProvider;

    private ActivityRetainedCImpl(SingletonCImpl singletonCImpl) {
      this.singletonCImpl = singletonCImpl;

      initialize();

    }

    @SuppressWarnings("unchecked")
    private void initialize() {
      this.provideActivityRetainedLifecycleProvider = DoubleCheck.provider(new SwitchingProvider<ActivityRetainedLifecycle>(singletonCImpl, activityRetainedCImpl, 0));
    }

    @Override
    public ActivityComponentBuilder activityComponentBuilder() {
      return new ActivityCBuilder(singletonCImpl, activityRetainedCImpl);
    }

    @Override
    public ActivityRetainedLifecycle getActivityRetainedLifecycle() {
      return provideActivityRetainedLifecycleProvider.get();
    }

    private static final class SwitchingProvider<T> implements Provider<T> {
      private final SingletonCImpl singletonCImpl;

      private final ActivityRetainedCImpl activityRetainedCImpl;

      private final int id;

      SwitchingProvider(SingletonCImpl singletonCImpl, ActivityRetainedCImpl activityRetainedCImpl,
          int id) {
        this.singletonCImpl = singletonCImpl;
        this.activityRetainedCImpl = activityRetainedCImpl;
        this.id = id;
      }

      @SuppressWarnings("unchecked")
      @Override
      public T get() {
        switch (id) {
          case 0: // dagger.hilt.android.ActivityRetainedLifecycle 
          return (T) ActivityRetainedComponentManager_LifecycleModule_ProvideActivityRetainedLifecycleFactory.provideActivityRetainedLifecycle();

          default: throw new AssertionError(id);
        }
      }
    }
  }

  private static final class ServiceCImpl extends WuHengApplication_HiltComponents.ServiceC {
    private final SingletonCImpl singletonCImpl;

    private final ServiceCImpl serviceCImpl = this;

    private ServiceCImpl(SingletonCImpl singletonCImpl, Service serviceParam) {
      this.singletonCImpl = singletonCImpl;


    }
  }

  private static final class SingletonCImpl extends WuHengApplication_HiltComponents.SingletonC {
    private final ApplicationContextModule applicationContextModule;

    private final SingletonCImpl singletonCImpl = this;

    private Provider<LazyHiltComponents> lazyHiltComponentsProvider;

    private Provider<HttpLoggingInterceptor> provideHttpLoggingInterceptorProvider;

    private Provider<TokenManager> provideTokenManagerProvider;

    private Provider<AuthInterceptor> provideAuthInterceptorProvider;

    private Provider<OkHttpClient> provideOkHttpClientProvider;

    private Provider<Retrofit> provideRetrofitProvider;

    private Provider<ApiService> provideApiServiceProvider;

    private Provider<Boolean> provideUseMockProvider;

    private Provider<ClimateRepository> provideClimateRepositoryProvider;

    private Provider<WaterRepository> provideWaterRepositoryProvider;

    private Provider<HomeRepository> provideHomeRepositoryProvider;

    private Provider<UserRepository> provideUserRepositoryProvider;

    private SingletonCImpl(ApplicationContextModule applicationContextModuleParam) {
      this.applicationContextModule = applicationContextModuleParam;
      initialize(applicationContextModuleParam);

    }

    @SuppressWarnings("unchecked")
    private void initialize(final ApplicationContextModule applicationContextModuleParam) {
      this.lazyHiltComponentsProvider = DoubleCheck.provider(new SwitchingProvider<LazyHiltComponents>(singletonCImpl, 0));
      this.provideHttpLoggingInterceptorProvider = DoubleCheck.provider(new SwitchingProvider<HttpLoggingInterceptor>(singletonCImpl, 5));
      this.provideTokenManagerProvider = DoubleCheck.provider(new SwitchingProvider<TokenManager>(singletonCImpl, 7));
      this.provideAuthInterceptorProvider = DoubleCheck.provider(new SwitchingProvider<AuthInterceptor>(singletonCImpl, 6));
      this.provideOkHttpClientProvider = DoubleCheck.provider(new SwitchingProvider<OkHttpClient>(singletonCImpl, 4));
      this.provideRetrofitProvider = DoubleCheck.provider(new SwitchingProvider<Retrofit>(singletonCImpl, 3));
      this.provideApiServiceProvider = DoubleCheck.provider(new SwitchingProvider<ApiService>(singletonCImpl, 2));
      this.provideUseMockProvider = DoubleCheck.provider(new SwitchingProvider<Boolean>(singletonCImpl, 8));
      this.provideClimateRepositoryProvider = DoubleCheck.provider(new SwitchingProvider<ClimateRepository>(singletonCImpl, 1));
      this.provideWaterRepositoryProvider = DoubleCheck.provider(new SwitchingProvider<WaterRepository>(singletonCImpl, 9));
      this.provideHomeRepositoryProvider = DoubleCheck.provider(new SwitchingProvider<HomeRepository>(singletonCImpl, 10));
      this.provideUserRepositoryProvider = DoubleCheck.provider(new SwitchingProvider<UserRepository>(singletonCImpl, 11));
    }

    @Override
    public void injectWuHengApplication(WuHengApplication arg0) {
    }

    @Override
    public LazyHiltComponents getLazyHiltComponents() {
      return lazyHiltComponentsProvider.get();
    }

    @Override
    public Set<Boolean> getDisableFragmentGetContextFix() {
      return Collections.<Boolean>emptySet();
    }

    @Override
    public ActivityRetainedComponentBuilder retainedComponentBuilder() {
      return new ActivityRetainedCBuilder(singletonCImpl);
    }

    @Override
    public ServiceComponentBuilder serviceComponentBuilder() {
      return new ServiceCBuilder(singletonCImpl);
    }

    private static final class SwitchingProvider<T> implements Provider<T> {
      private final SingletonCImpl singletonCImpl;

      private final int id;

      SwitchingProvider(SingletonCImpl singletonCImpl, int id) {
        this.singletonCImpl = singletonCImpl;
        this.id = id;
      }

      @SuppressWarnings("unchecked")
      @Override
      public T get() {
        switch (id) {
          case 0: // com.wuheng.smart.initializer.LazyHiltComponents 
          return (T) new LazyHiltComponents();

          case 1: // com.wuheng.smart.data.repository.ClimateRepository 
          return (T) RepositoryModule_ProvideClimateRepositoryFactory.provideClimateRepository(singletonCImpl.provideApiServiceProvider.get(), singletonCImpl.provideUseMockProvider.get());

          case 2: // com.wuheng.smart.data.network.ApiService 
          return (T) NetworkModule_ProvideApiServiceFactory.provideApiService(singletonCImpl.provideRetrofitProvider.get());

          case 3: // retrofit2.Retrofit 
          return (T) NetworkModule_ProvideRetrofitFactory.provideRetrofit(singletonCImpl.provideOkHttpClientProvider.get());

          case 4: // okhttp3.OkHttpClient 
          return (T) NetworkModule_ProvideOkHttpClientFactory.provideOkHttpClient(singletonCImpl.provideHttpLoggingInterceptorProvider.get(), singletonCImpl.provideAuthInterceptorProvider.get());

          case 5: // okhttp3.logging.HttpLoggingInterceptor 
          return (T) NetworkModule_ProvideHttpLoggingInterceptorFactory.provideHttpLoggingInterceptor();

          case 6: // com.wuheng.smart.data.network.AuthInterceptor 
          return (T) NetworkModule_ProvideAuthInterceptorFactory.provideAuthInterceptor(singletonCImpl.provideTokenManagerProvider.get());

          case 7: // com.wuheng.smart.data.network.TokenManager 
          return (T) NetworkModule_ProvideTokenManagerFactory.provideTokenManager(ApplicationContextModule_ProvideContextFactory.provideContext(singletonCImpl.applicationContextModule));

          case 8: // @javax.inject.Named("useMock") java.lang.Boolean 
          return (T) (Boolean) RepositoryModule.INSTANCE.provideUseMock();

          case 9: // com.wuheng.smart.data.repository.WaterRepository 
          return (T) RepositoryModule_ProvideWaterRepositoryFactory.provideWaterRepository(singletonCImpl.provideApiServiceProvider.get(), singletonCImpl.provideUseMockProvider.get());

          case 10: // com.wuheng.smart.data.repository.HomeRepository 
          return (T) RepositoryModule_ProvideHomeRepositoryFactory.provideHomeRepository(singletonCImpl.provideApiServiceProvider.get(), singletonCImpl.provideUseMockProvider.get());

          case 11: // com.wuheng.smart.data.repository.UserRepository 
          return (T) RepositoryModule_ProvideUserRepositoryFactory.provideUserRepository(singletonCImpl.provideApiServiceProvider.get(), singletonCImpl.provideTokenManagerProvider.get(), singletonCImpl.provideUseMockProvider.get());

          default: throw new AssertionError(id);
        }
      }
    }
  }
}
