package com.wuheng.smart.presentation.splash

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.wuheng.smart.presentation.theme.PrimaryBlue
import kotlinx.coroutines.delay

/**
 * 启动页
 *
 * 显示应用Logo和加载状态，然后自动跳转到登录页或首页
 */
@Composable
fun SplashScreen(
    onNavigateToLogin: () -> Unit,
    onNavigateToHome: () -> Unit
) {
    LaunchedEffect(Unit) {
        // 模拟加载时间
        delay(1500)
        // 检查登录状态，这里简单跳转到登录页
        onNavigateToLogin()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Logo区域
            Box(
                modifier = Modifier
                    .size(120.dp)
                    .background(PrimaryBlue.copy(alpha = 0.1f), shape = MaterialTheme.shapes.large),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "五恒",
                    fontSize = 48.sp,
                    fontWeight = FontWeight.Bold,
                    color = PrimaryBlue
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // 应用名称
            Text(
                text = "五恒智能控制系统",
                fontSize = 24.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(8.dp))

            // 副标题
            Text(
                text = "智能 · 舒适 · 健康",
                fontSize = 14.sp,
                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(48.dp))

            // 加载指示器
            CircularProgressIndicator(
                color = PrimaryBlue,
                modifier = Modifier.size(32.dp)
            )
        }
    }
}
