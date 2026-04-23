package com.wuheng.smart;

import androidx.lifecycle.ViewModel;
import com.wuheng.smart.data.network.TokenManager;
import com.wuheng.smart.navigation.NavigationRoutes;
import dagger.hilt.android.lifecycle.HiltViewModel;
import kotlinx.coroutines.flow.StateFlow;
import timber.log.Timber;
import javax.inject.Inject;

/**
 * MainActivity 的 ViewModel
 *
 * 职责：
 * 1. 管理应用登录状态
 * 2. 根据登录状态决定起始页面
 * 3. 监听 Token 变化并更新登录状态
 * 4. 管理主题设置（深色模式/系统主题）
 *
 * @param tokenManager Token 管理器，用于检查登录状态和主题设置
 */
@dagger.hilt.android.lifecycle.HiltViewModel()
@kotlin.Metadata(mv = {1, 7, 1}, k = 1, d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0010\u0002\n\u0002\b\u0006\b\u0007\u0018\u00002\u00020\u0001B\u000f\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\b\u0010\u0015\u001a\u00020\u0016H\u0002J\u000e\u0010\u0017\u001a\u00020\u00162\u0006\u0010\u0018\u001a\u00020\u0007J\u000e\u0010\u0019\u001a\u00020\u00162\u0006\u0010\u001a\u001a\u00020\tJ\u000e\u0010\u001b\u001a\u00020\u00162\u0006\u0010\u0018\u001a\u00020\u0007R\u0014\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\b\u001a\b\u0012\u0004\u0012\u00020\t0\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00070\u000b\u00a2\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0017\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00070\u000b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\rR\u0017\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\t0\u000b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\rR\u0017\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\t0\u000b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\rR\u0017\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00070\u000b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\rR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u001c"}, d2 = {"Lcom/wuheng/smart/MainViewModel;", "Landroidx/lifecycle/ViewModel;", "tokenManager", "Lcom/wuheng/smart/data/network/TokenManager;", "(Lcom/wuheng/smart/data/network/TokenManager;)V", "_isLoggedIn", "Lkotlinx/coroutines/flow/MutableStateFlow;", "", "_startDestination", "", "darkMode", "Lkotlinx/coroutines/flow/StateFlow;", "getDarkMode", "()Lkotlinx/coroutines/flow/StateFlow;", "isLoggedIn", "language", "getLanguage", "startDestination", "getStartDestination", "systemTheme", "getSystemTheme", "checkLoginStatus", "", "setDarkMode", "enabled", "setLanguage", "languageCode", "setSystemTheme", "app_debug"})
public final class MainViewModel extends androidx.lifecycle.ViewModel {
    private final com.wuheng.smart.data.network.TokenManager tokenManager = null;
    
    /**
     * 登录状态
     */
    private final kotlinx.coroutines.flow.MutableStateFlow<java.lang.Boolean> _isLoggedIn = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.lang.Boolean> isLoggedIn = null;
    
    /**
     * 起始页面路由
     */
    private final kotlinx.coroutines.flow.MutableStateFlow<java.lang.String> _startDestination = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.lang.String> startDestination = null;
    
    /**
     * 深色模式设置
     */
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.lang.Boolean> darkMode = null;
    
    /**
     * 系统主题设置
     */
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.lang.Boolean> systemTheme = null;
    
    /**
     * 语言设置
     */
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.lang.String> language = null;
    
    @javax.inject.Inject()
    public MainViewModel(@org.jetbrains.annotations.NotNull()
    com.wuheng.smart.data.network.TokenManager tokenManager) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.lang.Boolean> isLoggedIn() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.lang.String> getStartDestination() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.lang.Boolean> getDarkMode() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.lang.Boolean> getSystemTheme() {
        return null;
    }
    
    /**
     * 检查当前登录状态
     */
    private final void checkLoginStatus() {
    }
    
    /**
     * 设置深色模式
     */
    public final void setDarkMode(boolean enabled) {
    }
    
    /**
     * 设置是否跟随系统主题
     */
    public final void setSystemTheme(boolean enabled) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.lang.String> getLanguage() {
        return null;
    }
    
    /**
     * 设置语言
     * @param languageCode 语言代码: "zh" - 中文, "en" - 英文
     */
    public final void setLanguage(@org.jetbrains.annotations.NotNull()
    java.lang.String languageCode) {
    }
}