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

/**
 * 应用主入口 Activity
 *
 * 启动优化要点：
 * 1. 使用 SplashScreen API 优化启动体验
 * 2. 延迟加载非首屏内容
 * 3. 优化 Compose 渲染性能
 * 4. 首帧渲染监控
 *
 * 职责：
 * 1. 设置应用主题（支持深色模式）
 * 2. 设置应用语言（支持多语言）
 * 3. 初始化导航宿主
 * 4. 配置底部导航栏
 * 5. 管理登录状态检查
 * 6. 监听认证事件（Unauthorized、LogoutSuccess）并跳转到登录页
 */
@kotlin.Metadata(mv = {1, 7, 1}, k = 1, d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0007\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0012\u0010\u0003\u001a\u00020\u00042\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006H\u0014J\b\u0010\u0007\u001a\u00020\u0004H\u0014J\b\u0010\b\u001a\u00020\u0004H\u0014\u00a8\u0006\t"}, d2 = {"Lcom/wuheng/smart/MainActivity;", "Landroidx/activity/ComponentActivity;", "()V", "onCreate", "", "savedInstanceState", "Landroid/os/Bundle;", "onResume", "onStart", "app_debug"})
@dagger.hilt.android.AndroidEntryPoint()
public final class MainActivity extends androidx.activity.ComponentActivity {
    
    public MainActivity() {
        super();
    }
    
    @java.lang.Override()
    protected void onCreate(@org.jetbrains.annotations.Nullable()
    android.os.Bundle savedInstanceState) {
    }
    
    @java.lang.Override()
    protected void onStart() {
    }
    
    @java.lang.Override()
    protected void onResume() {
    }
}