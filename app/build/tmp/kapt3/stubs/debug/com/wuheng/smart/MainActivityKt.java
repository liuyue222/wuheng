package com.wuheng.smart;

import android.content.res.Configuration;
import android.os.Bundle;
import androidx.activity.ComponentActivity;
import androidx.compose.material3.ExperimentalMaterial3Api;
import androidx.compose.runtime.Composable;
import androidx.compose.ui.Modifier;
import com.wuheng.smart.data.network.AuthEvent;
import com.wuheng.smart.data.network.AuthEventManager;
import com.wuheng.smart.navigation.NavigationRoutes;
import com.wuheng.smart.performance.StartupTimer;
import dagger.hilt.android.AndroidEntryPoint;
import timber.log.Timber;
import java.util.Locale;

@kotlin.Metadata(mv = {1, 7, 1}, k = 2, d1 = {"\u0000\u001e\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\u001a\u0018\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u0007\u001a\b\u0010\u0006\u001a\u00020\u0001H\u0003\u001a\u0012\u0010\u0007\u001a\u00020\b2\b\u0010\t\u001a\u0004\u0018\u00010\u0005H\u0002\u00a8\u0006\n"}, d2 = {"WuHengApp", "", "viewModel", "Lcom/wuheng/smart/MainViewModel;", "language", "", "WuHengAppWithThemeAndLanguage", "shouldShowBottomBar", "", "route", "app_debug"})
public final class MainActivityKt {
    
    /**
     * 带主题和语言的应用主 Composable
     *
     * 注意：ViewModel必须在原始Activity Context中获取，不能在CompositionLocalProvider修改后的Context中获取
     */
    @androidx.compose.runtime.Composable()
    private static final void WuHengAppWithThemeAndLanguage() {
    }
    
    /**
     * 应用主 Composable
     *
     * 包含：
     * - 导航宿主 (NavHost)
     * - 底部导航栏 (仅在主Tab页面显示)
     * - 登录状态管理
     * - 认证事件监听
     *
     * @param viewModel MainViewModel实例，由父组件传入以确保在正确的Context中创建
     * @param language 当前语言设置，用于应用本地化
     */
    @androidx.compose.runtime.Composable()
    @kotlin.OptIn(markerClass = {androidx.compose.material3.ExperimentalMaterial3Api.class})
    public static final void WuHengApp(@org.jetbrains.annotations.NotNull()
    com.wuheng.smart.MainViewModel viewModel, @org.jetbrains.annotations.NotNull()
    java.lang.String language) {
    }
    
    /**
     * 检查路由是否需要显示底部导航栏
     */
    private static final boolean shouldShowBottomBar(java.lang.String route) {
        return false;
    }
}