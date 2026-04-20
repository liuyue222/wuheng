package com.wuheng.smart.presentation.profile;

import androidx.compose.foundation.layout.*;
import androidx.compose.material3.*;
import androidx.compose.runtime.Composable;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.tooling.preview.Preview;
import com.wuheng.smart.data.network.AuthEventManager;
import com.wuheng.smart.data.network.TokenManager;
import timber.log.Timber;

@kotlin.Metadata(mv = {1, 7, 1}, k = 2, d1 = {"\u0000\u001e\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\f\u001ab\u0010\u0000\u001a\u00020\u00012\b\b\u0002\u0010\u0002\u001a\u00020\u00032\u000e\b\u0002\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00010\u00052\u000e\b\u0002\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00010\u00052\u000e\b\u0002\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00010\u00052\u000e\b\u0002\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00010\u00052\u000e\b\u0002\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00010\u0005H\u0007\u001a\u0080\u0001\u0010\n\u001a\u00020\u00012\u0006\u0010\u000b\u001a\u00020\f2\f\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u00010\u00052\f\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00010\u00052\f\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00010\u00052\f\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00010\u00052\f\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00010\u00052\f\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00010\u00052\f\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00010\u00052\f\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00010\u0005H\u0003\u001a\b\u0010\u0015\u001a\u00020\u0001H\u0007\u001a\b\u0010\u0016\u001a\u00020\u0001H\u0007\u001a\b\u0010\u0017\u001a\u00020\u0001H\u0002\u00a8\u0006\u0018"}, d2 = {"ProfileScreen", "", "viewModel", "Lcom/wuheng/smart/presentation/profile/ProfileViewModel;", "onNavigateToNotifications", "Lkotlin/Function0;", "onNavigateToServiceSelect", "onNavigateToConsumables", "onNavigateToAbout", "onNavigateToPrivacy", "ProfileScreenContent", "uiState", "Lcom/wuheng/smart/presentation/profile/ProfileUiState;", "onNotificationClick", "onServiceSelect", "onBookService", "onConsumablesClick", "onAboutClick", "onPrivacyClick", "onLogout", "onRefresh", "ProfileScreenPreview", "ProfileScreenWidePreview", "performLogout", "app_debug"})
public final class ProfileScreenKt {
    
    /**
     * 个人中心页面 Screen - 处理ViewModel和状态管理
     * 逻辑和UI分离：Screen负责状态管理，Layout负责纯UI渲染
     */
    @androidx.compose.runtime.Composable()
    public static final void ProfileScreen(@org.jetbrains.annotations.NotNull()
    com.wuheng.smart.presentation.profile.ProfileViewModel viewModel, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onNavigateToNotifications, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onNavigateToServiceSelect, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onNavigateToConsumables, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onNavigateToAbout, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onNavigateToPrivacy) {
    }
    
    /**
     * 执行登出操作
     * 1. 清除 TokenManager 中的用户信息
     * 2. 发送 AuthEvent.LogoutSuccess 事件
     */
    private static final void performLogout() {
    }
    
    /**
     * 个人中心页面内容 - 纯UI，接收状态和回调
     */
    @androidx.compose.runtime.Composable()
    private static final void ProfileScreenContent(com.wuheng.smart.presentation.profile.ProfileUiState uiState, kotlin.jvm.functions.Function0<kotlin.Unit> onNotificationClick, kotlin.jvm.functions.Function0<kotlin.Unit> onServiceSelect, kotlin.jvm.functions.Function0<kotlin.Unit> onBookService, kotlin.jvm.functions.Function0<kotlin.Unit> onConsumablesClick, kotlin.jvm.functions.Function0<kotlin.Unit> onAboutClick, kotlin.jvm.functions.Function0<kotlin.Unit> onPrivacyClick, kotlin.jvm.functions.Function0<kotlin.Unit> onLogout, kotlin.jvm.functions.Function0<kotlin.Unit> onRefresh) {
    }
    
    @androidx.compose.runtime.Composable()
    @androidx.compose.ui.tooling.preview.Preview(showBackground = true, name = "\u4e2a\u4eba\u4e2d\u5fc3-\u6b63\u5e38\u72b6\u6001", backgroundColor = 4293981432L)
    public static final void ProfileScreenPreview() {
    }
    
    @androidx.compose.runtime.Composable()
    @androidx.compose.ui.tooling.preview.Preview(showBackground = true, name = "\u4e2a\u4eba\u4e2d\u5fc3-\u5bbd\u5c4f720dp", widthDp = 720, backgroundColor = 4293981432L)
    public static final void ProfileScreenWidePreview() {
    }
}