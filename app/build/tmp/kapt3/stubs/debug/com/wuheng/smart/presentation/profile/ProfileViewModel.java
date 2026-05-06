package com.wuheng.smart.presentation.profile;

import com.wuheng.smart.BuildConfig;
import com.wuheng.smart.data.network.ApiResult;
import com.wuheng.smart.data.network.TokenManager;
import com.wuheng.smart.data.repository.HomeRepository;
import com.wuheng.smart.data.repository.UserRepository;
import com.wuheng.smart.data.repository.WaterRepository;
import com.wuheng.smart.presentation.base.BaseViewModel;
import com.wuheng.smart.presentation.base.UiDataState;
import dagger.hilt.android.lifecycle.HiltViewModel;
import kotlinx.coroutines.flow.StateFlow;
import timber.log.Timber;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import javax.inject.Inject;

@dagger.hilt.android.lifecycle.HiltViewModel()
@kotlin.Metadata(mv = {1, 7, 1}, k = 1, d1 = {"\u0000F\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0010\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\'\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u00a2\u0006\u0002\u0010\nJ\b\u0010\u0017\u001a\u00020\u000eH\u0002J\u0006\u0010\u0018\u001a\u00020\u000eJ\u0006\u0010\u0019\u001a\u00020\u000eJ\b\u0010\u001a\u001a\u00020\u000eH\u0002J\b\u0010\u001b\u001a\u00020\u000eH\u0002J\u0006\u0010\u001c\u001a\u00020\u000eJ\u0006\u0010\u001d\u001a\u00020\u000eJ\u0006\u0010\u001e\u001a\u00020\u000eJ\u0006\u0010\u001f\u001a\u00020\u000eJ\u0006\u0010 \u001a\u00020\u000eJ\u000e\u0010!\u001a\u00020\u000e2\u0006\u0010\"\u001a\u00020#R\u001a\u0010\u000b\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000e0\r0\fX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00100\fX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001d\u0010\u0011\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000e0\r0\u0012\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00100\u0012\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0014R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006$"}, d2 = {"Lcom/wuheng/smart/presentation/profile/ProfileViewModel;", "Lcom/wuheng/smart/presentation/base/BaseViewModel;", "tokenManager", "Lcom/wuheng/smart/data/network/TokenManager;", "userRepository", "Lcom/wuheng/smart/data/repository/UserRepository;", "waterRepository", "Lcom/wuheng/smart/data/repository/WaterRepository;", "homeRepository", "Lcom/wuheng/smart/data/repository/HomeRepository;", "(Lcom/wuheng/smart/data/network/TokenManager;Lcom/wuheng/smart/data/repository/UserRepository;Lcom/wuheng/smart/data/repository/WaterRepository;Lcom/wuheng/smart/data/repository/HomeRepository;)V", "_bookingState", "Lkotlinx/coroutines/flow/MutableStateFlow;", "Lcom/wuheng/smart/presentation/base/UiDataState;", "", "_uiState", "Lcom/wuheng/smart/presentation/profile/ProfileUiState;", "bookingState", "Lkotlinx/coroutines/flow/StateFlow;", "getBookingState", "()Lkotlinx/coroutines/flow/StateFlow;", "uiState", "getUiState", "bookFilterReplaceFromProfile", "clearError", "confirmBooking", "loadMaintenanceLog", "loadUserInfo", "logout", "refresh", "refreshData", "resetBookingState", "retry", "selectServiceType", "type", "Lcom/wuheng/smart/presentation/profile/ServiceType;", "app_debug"})
public final class ProfileViewModel extends com.wuheng.smart.presentation.base.BaseViewModel {
    private final com.wuheng.smart.data.network.TokenManager tokenManager = null;
    private final com.wuheng.smart.data.repository.UserRepository userRepository = null;
    private final com.wuheng.smart.data.repository.WaterRepository waterRepository = null;
    private final com.wuheng.smart.data.repository.HomeRepository homeRepository = null;
    private final kotlinx.coroutines.flow.MutableStateFlow<com.wuheng.smart.presentation.profile.ProfileUiState> _uiState = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.wuheng.smart.presentation.profile.ProfileUiState> uiState = null;
    private final kotlinx.coroutines.flow.MutableStateFlow<com.wuheng.smart.presentation.base.UiDataState<kotlin.Unit>> _bookingState = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.wuheng.smart.presentation.base.UiDataState<kotlin.Unit>> bookingState = null;
    
    @javax.inject.Inject()
    public ProfileViewModel(@org.jetbrains.annotations.NotNull()
    com.wuheng.smart.data.network.TokenManager tokenManager, @org.jetbrains.annotations.NotNull()
    com.wuheng.smart.data.repository.UserRepository userRepository, @org.jetbrains.annotations.NotNull()
    com.wuheng.smart.data.repository.WaterRepository waterRepository, @org.jetbrains.annotations.NotNull()
    com.wuheng.smart.data.repository.HomeRepository homeRepository) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.wuheng.smart.presentation.profile.ProfileUiState> getUiState() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.wuheng.smart.presentation.base.UiDataState<kotlin.Unit>> getBookingState() {
        return null;
    }
    
    private final void loadUserInfo() {
    }
    
    private final void loadMaintenanceLog() {
    }
    
    public final void refresh() {
    }
    
    public final void refreshData() {
    }
    
    public final void retry() {
    }
    
    public final void clearError() {
    }
    
    public final void selectServiceType(@org.jetbrains.annotations.NotNull()
    com.wuheng.smart.presentation.profile.ServiceType type) {
    }
    
    public final void confirmBooking() {
    }
    
    private final void bookFilterReplaceFromProfile() {
    }
    
    public final void resetBookingState() {
    }
    
    public final void logout() {
    }
}