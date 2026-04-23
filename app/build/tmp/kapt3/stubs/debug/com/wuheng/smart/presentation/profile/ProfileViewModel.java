package com.wuheng.smart.presentation.profile;

import com.wuheng.smart.data.network.ApiResult;
import com.wuheng.smart.data.network.TokenManager;
import com.wuheng.smart.data.repository.UserRepository;
import com.wuheng.smart.presentation.base.BaseViewModel;
import dagger.hilt.android.lifecycle.HiltViewModel;
import kotlinx.coroutines.flow.StateFlow;
import timber.log.Timber;
import javax.inject.Inject;

/**
 * 个人中心 ViewModel
 *
 * 职责：
 * 1. 管理个人中心所有UI状态
 * 2. 处理用户交互事件
 * 3. 调用API获取用户数据
 */
@dagger.hilt.android.lifecycle.HiltViewModel()
@kotlin.Metadata(mv = {1, 7, 1}, k = 1, d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u000e\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u0017\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\u0006\u0010\u0013\u001a\u00020\nJ\u0006\u0010\u0014\u001a\u00020\nJ\b\u0010\u0015\u001a\u00020\nH\u0002J\u0006\u0010\u0016\u001a\u00020\nJ\u0006\u0010\u0017\u001a\u00020\nJ\u0006\u0010\u0018\u001a\u00020\nJ\u0006\u0010\u0019\u001a\u00020\nJ\u0006\u0010\u001a\u001a\u00020\nJ\u000e\u0010\u001b\u001a\u00020\n2\u0006\u0010\u001c\u001a\u00020\u001dR\u001a\u0010\u0007\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\n0\t0\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\f0\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001d\u0010\r\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\n0\t0\u000e\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\f0\u000e\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0010R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u001e"}, d2 = {"Lcom/wuheng/smart/presentation/profile/ProfileViewModel;", "Lcom/wuheng/smart/presentation/base/BaseViewModel;", "tokenManager", "Lcom/wuheng/smart/data/network/TokenManager;", "userRepository", "Lcom/wuheng/smart/data/repository/UserRepository;", "(Lcom/wuheng/smart/data/network/TokenManager;Lcom/wuheng/smart/data/repository/UserRepository;)V", "_bookingState", "Lkotlinx/coroutines/flow/MutableStateFlow;", "Lcom/wuheng/smart/presentation/base/UiDataState;", "", "_uiState", "Lcom/wuheng/smart/presentation/profile/ProfileUiState;", "bookingState", "Lkotlinx/coroutines/flow/StateFlow;", "getBookingState", "()Lkotlinx/coroutines/flow/StateFlow;", "uiState", "getUiState", "clearError", "confirmBooking", "loadUserInfo", "logout", "refresh", "refreshData", "resetBookingState", "retry", "selectServiceType", "type", "Lcom/wuheng/smart/presentation/profile/ServiceType;", "app_debug"})
public final class ProfileViewModel extends com.wuheng.smart.presentation.base.BaseViewModel {
    private final com.wuheng.smart.data.network.TokenManager tokenManager = null;
    private final com.wuheng.smart.data.repository.UserRepository userRepository = null;
    private final kotlinx.coroutines.flow.MutableStateFlow<com.wuheng.smart.presentation.profile.ProfileUiState> _uiState = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.wuheng.smart.presentation.profile.ProfileUiState> uiState = null;
    private final kotlinx.coroutines.flow.MutableStateFlow<com.wuheng.smart.presentation.base.UiDataState<kotlin.Unit>> _bookingState = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.wuheng.smart.presentation.base.UiDataState<kotlin.Unit>> bookingState = null;
    
    @javax.inject.Inject()
    public ProfileViewModel(@org.jetbrains.annotations.NotNull()
    com.wuheng.smart.data.network.TokenManager tokenManager, @org.jetbrains.annotations.NotNull()
    com.wuheng.smart.data.repository.UserRepository userRepository) {
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
    
    /**
     * 加载用户信息
     * 从API获取用户详细信息并更新UI
     */
    private final void loadUserInfo() {
    }
    
    /**
     * 刷新所有数据
     * 重新加载用户信息和相关数据
     */
    public final void refresh() {
    }
    
    /**
     * 刷新数据（供Layout调用）
     */
    public final void refreshData() {
    }
    
    /**
     * 重试加载数据
     * 在加载失败时调用
     */
    public final void retry() {
    }
    
    /**
     * 清除错误信息
     */
    public final void clearError() {
    }
    
    /**
     * 选择服务类型
     */
    public final void selectServiceType(@org.jetbrains.annotations.NotNull()
    com.wuheng.smart.presentation.profile.ServiceType type) {
    }
    
    /**
     * 确认预约
     */
    public final void confirmBooking() {
    }
    
    /**
     * 重置预约状态
     */
    public final void resetBookingState() {
    }
    
    /**
     * 用户登出
     */
    public final void logout() {
    }
}