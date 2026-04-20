package com.wuheng.smart.presentation.profile;

import androidx.compose.foundation.layout.*;
import androidx.compose.material.icons.Icons;
import androidx.compose.material3.*;
import androidx.compose.runtime.Composable;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.text.font.FontWeight;
import androidx.compose.ui.unit.Dp;
import com.wuheng.smart.presentation.components.*;
import com.wuheng.smart.presentation.theme.*;

@kotlin.Metadata(mv = {1, 7, 1}, k = 2, d1 = {"\u00002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0010\u000b\n\u0000\u001a\u001e\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00010\u0005H\u0003\u001a\u0016\u0010\u0006\u001a\u00020\u00012\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00010\u0005H\u0003\u001a\u0093\u0001\u0010\b\u001a\u00020\u00012\u0006\u0010\t\u001a\u00020\n2\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u00010\u00052\f\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u00010\u00052\f\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u00010\u00052\f\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00010\u00052\f\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00010\u00052\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00010\u00052\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00010\u00052\b\b\u0002\u0010\u0010\u001a\u00020\u00112\b\b\u0002\u0010\u0012\u001a\u00020\u0013H\u0007\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b\u0014\u0010\u0015\u001a\u0010\u0010\u0016\u001a\u00020\u00012\u0006\u0010\u0017\u001a\u00020\u0003H\u0003\u001a,\u0010\u0018\u001a\u00020\u00012\u0006\u0010\u0019\u001a\u00020\u00032\f\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u00010\u00052\f\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u00010\u0005H\u0003\u001a6\u0010\u001a\u001a\u00020\u00012\u0006\u0010\u001b\u001a\u00020\u00032\u0006\u0010\u001c\u001a\u00020\u00032\u0006\u0010\u001d\u001a\u00020\u00032\u0006\u0010\u001e\u001a\u00020\u001f2\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u00010\u0005H\u0003\u0082\u0002\u000b\n\u0005\b\u00a1\u001e0\u0001\n\u0002\b\u0019\u00a8\u0006 "}, d2 = {"FooterSection", "", "version", "", "onPrivacyClick", "Lkotlin/Function0;", "LogoutButton", "onLogout", "ProfileLayout", "uiState", "Lcom/wuheng/smart/presentation/profile/ProfileUiState;", "onNotificationClick", "onServiceSelect", "onBookService", "onConsumablesClick", "onAboutClick", "modifier", "Landroidx/compose/ui/Modifier;", "maxWidth", "Landroidx/compose/ui/unit/Dp;", "ProfileLayout-pPrIpRY", "(Lcom/wuheng/smart/presentation/profile/ProfileUiState;Lkotlin/jvm/functions/Function0;Lkotlin/jvm/functions/Function0;Lkotlin/jvm/functions/Function0;Lkotlin/jvm/functions/Function0;Lkotlin/jvm/functions/Function0;Lkotlin/jvm/functions/Function0;Lkotlin/jvm/functions/Function0;Landroidx/compose/ui/Modifier;F)V", "ProjectOverviewCard", "description", "ServiceBookingCard", "lastServiceDate", "UserHeader", "userName", "residenceName", "role", "hasNotification", "", "app_debug"})
public final class ProfileLayoutKt {
    
    /**
     * 用户头部信息
     */
    @androidx.compose.runtime.Composable()
    private static final void UserHeader(java.lang.String userName, java.lang.String residenceName, java.lang.String role, boolean hasNotification, kotlin.jvm.functions.Function0<kotlin.Unit> onNotificationClick) {
    }
    
    /**
     * 项目概述卡片
     */
    @androidx.compose.runtime.Composable()
    private static final void ProjectOverviewCard(java.lang.String description) {
    }
    
    /**
     * 预约服务卡片
     */
    @androidx.compose.runtime.Composable()
    private static final void ServiceBookingCard(java.lang.String lastServiceDate, kotlin.jvm.functions.Function0<kotlin.Unit> onServiceSelect, kotlin.jvm.functions.Function0<kotlin.Unit> onBookService) {
    }
    
    /**
     * 退出登录按钮
     */
    @androidx.compose.runtime.Composable()
    private static final void LogoutButton(kotlin.jvm.functions.Function0<kotlin.Unit> onLogout) {
    }
    
    /**
     * 页脚区域
     */
    @androidx.compose.runtime.Composable()
    private static final void FooterSection(java.lang.String version, kotlin.jvm.functions.Function0<kotlin.Unit> onPrivacyClick) {
    }
}