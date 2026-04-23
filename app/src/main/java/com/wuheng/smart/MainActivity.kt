package com.wuheng.smart

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.wuheng.smart.data.network.AuthEvent
import com.wuheng.smart.data.network.AuthEventManager
import com.wuheng.smart.navigation.BottomNavItem
import com.wuheng.smart.navigation.NavigationActions
import com.wuheng.smart.navigation.NavigationRoutes
import com.wuheng.smart.navigation.NavGraph
import com.wuheng.smart.navigation.bottomNavRoutes
import com.wuheng.smart.navigation.navigateToLogin
import com.wuheng.smart.performance.StartupTimer
import com.wuheng.smart.presentation.theme.WuHengBottomNavigation
import com.wuheng.smart.presentation.theme.WuHengTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import timber.log.Timber
import java.util.Locale

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
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        // 安装 SplashScreen（必须在 super.onCreate 之前）
        val splashScreen = installSplashScreen()

        StartupTimer.start(StartupTimer.MAIN_ACTIVITY_CREATE)

        super.onCreate(savedInstanceState)

        // 保持 SplashScreen 直到首帧渲染完成
        splashScreen.setKeepOnScreenCondition {
            // 条件返回 true 时保持显示启动页
            // 这里可以根据实际需要调整，如等待必要数据加载完成
            false
        }

        setContent {
            WuHengAppWithThemeAndLanguage()
        }

        StartupTimer.end(StartupTimer.MAIN_ACTIVITY_CREATE)
    }

    override fun onStart() {
        super.onStart()
        StartupTimer.recordFromColdStart(StartupTimer.MAIN_ACTIVITY_START)
    }

    override fun onResume() {
        super.onResume()
        StartupTimer.recordFromColdStart(StartupTimer.MAIN_ACTIVITY_RESUME)
    }
}

/**
 * 带主题和语言的应用主 Composable
 */
@Composable
private fun WuHengAppWithThemeAndLanguage(
    viewModel: MainViewModel = hiltViewModel()
) {
    // 获取主题设置
    val darkMode by viewModel.darkMode.collectAsStateWithLifecycle()
    val systemTheme by viewModel.systemTheme.collectAsStateWithLifecycle()

    // 获取语言设置
    val language by viewModel.language.collectAsStateWithLifecycle()

    // 应用语言设置
    val context = LocalContext.current
    val configuration = Configuration(context.resources.configuration)
    configuration.setLocale(Locale(language))
    val localizedContext = context.createConfigurationContext(configuration)

    CompositionLocalProvider(LocalContext provides localizedContext) {
        WuHengTheme(
            darkTheme = darkMode,
            useSystemTheme = systemTheme
        ) {
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.background
            ) {
                WuHengApp()
            }
        }
    }
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
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WuHengApp(
    viewModel: MainViewModel = hiltViewModel()
) {
    val navController = rememberNavController()

    // 获取当前路由
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val currentRoute = currentDestination?.route

    // 判断当前是否在主Tab页面（显示底部导航栏）
    val isMainTabRoute = currentRoute in NavigationRoutes.bottomNavRoutes

    // 获取登录状态
    val isLoggedIn by viewModel.isLoggedIn.collectAsStateWithLifecycle()
    val startDestination by viewModel.startDestination.collectAsStateWithLifecycle()

    // 监听认证事件（Unauthorized、LogoutSuccess）
    LaunchedEffect(Unit) {
        AuthEventManager.authEvents.collectLatest { event ->
            when (event) {
                is AuthEvent.Unauthorized -> {
                    Timber.w("Received Unauthorized event, navigating to login")
                    navController.navigateToLogin()
                }
                is AuthEvent.LogoutSuccess -> {
                    Timber.d("Received LogoutSuccess event, navigating to login")
                    navController.navigateToLogin()
                }
                is AuthEvent.LoginSuccess -> {
                    Timber.d("Received LoginSuccess event, navigating to home")
                    navController.navigate(NavigationRoutes.HOME) {
                        popUpTo(NavigationRoutes.LOGIN) { inclusive = true }
                    }
                }
            }
        }
    }

    // 根据登录状态决定起始页面
    LaunchedEffect(isLoggedIn, startDestination) {
        if (!isLoggedIn && currentRoute != NavigationRoutes.LOGIN) {
            Timber.d("User not logged in, navigating to login page")
            navController.navigateToLogin()
        }
    }

    Scaffold(
        bottomBar = {
            // 仅在主Tab页面显示底部导航栏
            if (isMainTabRoute) {
                // 根据当前路由确定选中的导航项索引
                val selectedIndex = when (currentRoute) {
                    NavigationRoutes.HOME -> 0
                    NavigationRoutes.CLIMATE -> 1
                    NavigationRoutes.WATER -> 2
                    NavigationRoutes.PROFILE -> 3
                    else -> 0
                }

                WuHengBottomNavigation(
                    selectedItem = selectedIndex,
                    onItemSelected = { index ->
                        val route = when (index) {
                            0 -> NavigationRoutes.HOME
                            1 -> NavigationRoutes.CLIMATE
                            2 -> NavigationRoutes.WATER
                            3 -> NavigationRoutes.PROFILE
                            else -> NavigationRoutes.HOME
                        }
                        navController.navigate(route) {
                            // 弹出到起始目的地，避免堆栈累积
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            // 避免重复导航到同一页面
                            launchSingleTop = true
                            // 恢复之前保存的状态
                            restoreState = true
                        }
                    }
                )
            }
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { innerPadding ->
        NavGraph(
            navController = navController,
            modifier = Modifier.padding(innerPadding),
            startDestination = startDestination
        )
    }
}

/**
 * 检查路由是否需要显示底部导航栏
 */
private fun shouldShowBottomBar(route: String?): Boolean {
    return route in listOf(
        NavigationRoutes.HOME,
        NavigationRoutes.CLIMATE,
        NavigationRoutes.WATER,
        NavigationRoutes.PROFILE
    )
}
