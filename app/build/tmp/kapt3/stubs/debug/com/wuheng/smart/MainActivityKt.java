package com.wuheng.smart;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import androidx.activity.ComponentActivity;
import androidx.compose.material3.ExperimentalMaterial3Api;
import androidx.compose.runtime.Composable;
import androidx.compose.ui.Modifier;
import com.wuheng.smart.data.network.AuthEvent;
import com.wuheng.smart.data.network.AuthEventManager;
import com.wuheng.smart.navigation.BottomNavItem;
import com.wuheng.smart.navigation.NavigationActions;
import com.wuheng.smart.navigation.NavigationRoutes;
import com.wuheng.smart.performance.StartupTimer;
import dagger.hilt.android.AndroidEntryPoint;
import timber.log.Timber;
import java.util.Locale;

@kotlin.Metadata(mv = {1, 7, 1}, k = 2, d1 = {"\u0000\u001c\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u000e\n\u0000\u001a\u0012\u0010\u0000\u001a\u00020\u00012\b\b\u0002\u0010\u0002\u001a\u00020\u0003H\u0007\u001a\u0012\u0010\u0004\u001a\u00020\u00012\b\b\u0002\u0010\u0002\u001a\u00020\u0003H\u0003\u001a\u0012\u0010\u0005\u001a\u00020\u00062\b\u0010\u0007\u001a\u0004\u0018\u00010\bH\u0002\u00a8\u0006\t"}, d2 = {"WuHengApp", "", "viewModel", "Lcom/wuheng/smart/MainViewModel;", "WuHengAppWithThemeAndLanguage", "shouldShowBottomBar", "", "route", "", "app_debug"})
public final class MainActivityKt {
    
    /**
     * 带主题和语言的应用主 Composable
     */
    @androidx.compose.runtime.Composable()
    private static final void WuHengAppWithThemeAndLanguage(com.wuheng.smart.MainViewModel viewModel) {
    }
    
    /**
     * 应用主 Composable
     *
     * 包含：
     * - 导航宿主 (NavHost)
     * - 底部导航栏 (仅在主Tab页面显示)
     * - 登录状态管理
     * - 认证事件监听
     */
    @androidx.compose.runtime.Composable()
    @kotlin.OptIn(markerClass = {androidx.compose.material3.ExperimentalMaterial3Api.class})
    public static final void WuHengApp(@org.jetbrains.annotations.NotNull()
    com.wuheng.smart.MainViewModel viewModel) {
    }
    
    /**
     * 检查路由是否需要显示底部导航栏
     */
    private static final boolean shouldShowBottomBar(java.lang.String route) {
        return false;
    }
}