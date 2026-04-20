package com.wuheng.smart;

import android.os.Bundle;
import androidx.activity.ComponentActivity;
import androidx.compose.material3.ExperimentalMaterial3Api;
import androidx.compose.runtime.Composable;
import androidx.compose.ui.Modifier;
import com.wuheng.smart.data.network.AuthEvent;
import com.wuheng.smart.data.network.AuthEventManager;
import com.wuheng.smart.navigation.BottomNavItem;
import com.wuheng.smart.navigation.NavigationRoutes;
import dagger.hilt.android.AndroidEntryPoint;
import timber.log.Timber;

/**
 * 应用主入口 Activity
 *
 * 职责：
 * 1. 设置应用主题
 * 2. 初始化导航宿主
 * 3. 配置底部导航栏
 * 4. 管理登录状态检查
 * 5. 监听认证事件（Unauthorized、LogoutSuccess）并跳转到登录页
 */
@kotlin.Metadata(mv = {1, 7, 1}, k = 1, d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0012\u0010\u0003\u001a\u00020\u00042\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006H\u0014\u00a8\u0006\u0007"}, d2 = {"Lcom/wuheng/smart/MainActivity;", "Landroidx/activity/ComponentActivity;", "()V", "onCreate", "", "savedInstanceState", "Landroid/os/Bundle;", "app_debug"})
@dagger.hilt.android.AndroidEntryPoint()
public final class MainActivity extends androidx.activity.ComponentActivity {
    
    public MainActivity() {
        super();
    }
    
    @java.lang.Override()
    protected void onCreate(@org.jetbrains.annotations.Nullable()
    android.os.Bundle savedInstanceState) {
    }
}