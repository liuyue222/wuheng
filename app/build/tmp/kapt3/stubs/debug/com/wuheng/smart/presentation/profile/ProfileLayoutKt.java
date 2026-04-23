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

@kotlin.Metadata(mv = {1, 7, 1}, k = 2, d1 = {"\u0000H\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0000\u001a\u001e\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00010\u0005H\u0003\u001a\u001e\u0010\u0006\u001a\u00020\u00012\u0006\u0010\u0007\u001a\u00020\u00032\f\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00010\u0005H\u0003\u001a\u0016\u0010\t\u001a\u00020\u00012\f\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00010\u0005H\u0003\u001a\u009f\u0001\u0010\u000b\u001a\u00020\u00012\u0006\u0010\f\u001a\u00020\r2\n\b\u0002\u0010\u000e\u001a\u0004\u0018\u00010\u000f2\f\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00010\u00052\f\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00010\u00052\f\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00010\u00052\f\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00010\u00052\f\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00010\u00052\f\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00010\u00052\f\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00010\u00052\b\b\u0002\u0010\u0015\u001a\u00020\u00162\b\b\u0002\u0010\u0017\u001a\u00020\u0018H\u0007\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b\u0019\u0010\u001a\u001a\u0010\u0010\u001b\u001a\u00020\u00012\u0006\u0010\u001c\u001a\u00020\u0003H\u0003\u001a6\u0010\u001d\u001a\u00020\u00012\u0006\u0010\u001e\u001a\u00020\u00032\b\u0010\u000e\u001a\u0004\u0018\u00010\u000f2\f\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00010\u00052\f\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00010\u0005H\u0003\u001a:\u0010\u001f\u001a\u00020\u00012\u0006\u0010 \u001a\u00020\u000f2\f\u0010!\u001a\b\u0012\u0004\u0012\u00020\u00010\"2\f\u0010#\u001a\b\u0012\u0004\u0012\u00020\u00010\u00052\f\u0010$\u001a\b\u0012\u0004\u0012\u00020\u00010\u0005H\u0007\u001a4\u0010%\u001a\u00020\u00012\b\u0010&\u001a\u0004\u0018\u00010\u000f2\u0012\u0010\'\u001a\u000e\u0012\u0004\u0012\u00020\u000f\u0012\u0004\u0012\u00020\u00010(2\f\u0010$\u001a\b\u0012\u0004\u0012\u00020\u00010\u0005H\u0007\u001a6\u0010)\u001a\u00020\u00012\u0006\u0010*\u001a\u00020\u00032\u0006\u0010+\u001a\u00020\u00032\u0006\u0010,\u001a\u00020\u00032\u0006\u0010-\u001a\u00020.2\f\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00010\u0005H\u0003\u0082\u0002\u000b\n\u0005\b\u00a1\u001e0\u0001\n\u0002\b\u0019\u00a8\u0006/"}, d2 = {"ClickableListItem", "", "title", "", "onClick", "Lkotlin/Function0;", "FooterSection", "version", "onPrivacyClick", "LogoutButton", "onLogout", "ProfileLayout", "uiState", "Lcom/wuheng/smart/presentation/profile/ProfileUiState;", "selectedServiceType", "Lcom/wuheng/smart/presentation/profile/ServiceType;", "onNotificationClick", "onServiceSelect", "onBookService", "onConsumablesClick", "onAboutClick", "modifier", "Landroidx/compose/ui/Modifier;", "maxWidth", "Landroidx/compose/ui/unit/Dp;", "ProfileLayout-7IhJQOk", "(Lcom/wuheng/smart/presentation/profile/ProfileUiState;Lcom/wuheng/smart/presentation/profile/ServiceType;Lkotlin/jvm/functions/Function0;Lkotlin/jvm/functions/Function0;Lkotlin/jvm/functions/Function0;Lkotlin/jvm/functions/Function0;Lkotlin/jvm/functions/Function0;Lkotlin/jvm/functions/Function0;Lkotlin/jvm/functions/Function0;Landroidx/compose/ui/Modifier;F)V", "ProjectOverviewCard", "description", "ServiceBookingCard", "lastServiceDate", "ServiceBookingConfirmDialog", "serviceType", "bookingState", "Lcom/wuheng/smart/presentation/base/UiDataState;", "onConfirm", "onDismiss", "ServiceTypeSelectorDialog", "selectedType", "onTypeSelected", "Lkotlin/Function1;", "UserHeader", "userName", "residenceName", "role", "hasNotification", "", "app_debug"})
public final class ProfileLayoutKt {
    
    /**
     * 用户头部信息 - 像素级还原设计图
     */
    @androidx.compose.runtime.Composable()
    private static final void UserHeader(java.lang.String userName, java.lang.String residenceName, java.lang.String role, boolean hasNotification, kotlin.jvm.functions.Function0<kotlin.Unit> onNotificationClick) {
    }
    
    /**
     * 项目概述卡片 - 像素级还原设计图
     */
    @androidx.compose.runtime.Composable()
    private static final void ProjectOverviewCard(java.lang.String description) {
    }
    
    /**
     * 预约服务卡片 - 像素级还原设计图
     */
    @androidx.compose.runtime.Composable()
    private static final void ServiceBookingCard(java.lang.String lastServiceDate, com.wuheng.smart.presentation.profile.ServiceType selectedServiceType, kotlin.jvm.functions.Function0<kotlin.Unit> onServiceSelect, kotlin.jvm.functions.Function0<kotlin.Unit> onBookService) {
    }
    
    /**
     * 可点击列表项 - 像素级还原设计图
     */
    @androidx.compose.runtime.Composable()
    private static final void ClickableListItem(java.lang.String title, kotlin.jvm.functions.Function0<kotlin.Unit> onClick) {
    }
    
    /**
     * 服务类型选择弹窗
     */
    @androidx.compose.runtime.Composable()
    public static final void ServiceTypeSelectorDialog(@org.jetbrains.annotations.Nullable()
    com.wuheng.smart.presentation.profile.ServiceType selectedType, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super com.wuheng.smart.presentation.profile.ServiceType, kotlin.Unit> onTypeSelected, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onDismiss) {
    }
    
    /**
     * 预约确认弹窗
     */
    @androidx.compose.runtime.Composable()
    public static final void ServiceBookingConfirmDialog(@org.jetbrains.annotations.NotNull()
    com.wuheng.smart.presentation.profile.ServiceType serviceType, @org.jetbrains.annotations.NotNull()
    com.wuheng.smart.presentation.base.UiDataState<kotlin.Unit> bookingState, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onConfirm, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onDismiss) {
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