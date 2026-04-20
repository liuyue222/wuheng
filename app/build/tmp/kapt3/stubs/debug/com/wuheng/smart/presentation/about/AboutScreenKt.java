package com.wuheng.smart.presentation.about;

import androidx.compose.foundation.layout.*;
import androidx.compose.material.icons.Icons;
import androidx.compose.material3.*;
import androidx.compose.runtime.*;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.text.font.FontWeight;
import androidx.compose.ui.text.style.TextAlign;
import androidx.compose.ui.tooling.preview.Preview;
import com.wuheng.smart.presentation.base.UiDataState;
import com.wuheng.smart.presentation.theme.*;

@kotlin.Metadata(mv = {1, 7, 1}, k = 2, d1 = {"\u0000@\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\u001af\u0010\u0000\u001a\u00020\u00012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\u000e\b\u0002\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00010\u00062\u000e\b\u0002\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00010\u00062\u000e\b\u0002\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00010\u00062\u000e\b\u0002\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00010\u00062\u000e\b\u0002\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00010\u0006H\u0007\u001aP\u0010\u000b\u001a\u00020\u00012\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u00042\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00010\u00062\f\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00010\u00062\f\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00010\u00062\f\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00010\u0006H\u0003\u001a\u0010\u0010\u000f\u001a\u00020\u00012\u0006\u0010\u0010\u001a\u00020\u0011H\u0003\u001a\u0010\u0010\u0012\u001a\u00020\u00012\u0006\u0010\u000e\u001a\u00020\u0004H\u0003\u001a\u0018\u0010\u0013\u001a\u00020\u00012\u0006\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u0017H\u0003\u001a@\u0010\u0018\u001a\u00020\u00012\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00010\u00062\f\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00010\u00062\f\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00010\u00062\f\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00010\u0006H\u0003\u001ab\u0010\u0019\u001a\u00020\u00012\b\b\u0002\u0010\u001a\u001a\u00020\u001b2\u000e\b\u0002\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00010\u00062\u000e\b\u0002\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00010\u00062\u000e\b\u0002\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00010\u00062\u000e\b\u0002\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00010\u00062\u000e\b\u0002\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00010\u0006H\u0007\u001a\b\u0010\u001c\u001a\u00020\u0001H\u0007\u001a\b\u0010\u001d\u001a\u00020\u0001H\u0007\u001a\b\u0010\u001e\u001a\u00020\u0001H\u0007\u00a8\u0006\u001f"}, d2 = {"AboutContent", "", "aboutInfoState", "Lcom/wuheng/smart/presentation/base/UiDataState;", "Lcom/wuheng/smart/presentation/about/AboutInfo;", "onNavigateBack", "Lkotlin/Function0;", "onFunctionIntroClick", "onUserAgreementClick", "onPrivacyPolicyClick", "onContactUsClick", "AboutContentBody", "paddingValues", "Landroidx/compose/foundation/layout/PaddingValues;", "aboutInfo", "AboutFooterSection", "copyright", "", "AboutHeaderSection", "AboutMenuItemRow", "item", "Lcom/wuheng/smart/presentation/about/AboutMenuItem;", "showDivider", "", "AboutMenuList", "AboutScreen", "viewModel", "Lcom/wuheng/smart/presentation/about/AboutViewModel;", "AboutScreenDarkPreview", "AboutScreenLoadingPreview", "AboutScreenPreview", "app_debug"})
public final class AboutScreenKt {
    
    /**
     * 关于新宜能页面 - 像素级还原设计图
     *
     * 布局结构：
     * 1. 顶部导航栏：返回按钮 + "关于新宜能"标题
     * 2. 公司Logo区域：应用图标 + 应用名称
     * 3. 版本信息：当前版本号
     * 4. 功能菜单列表：
     *   - 功能介绍
     *   - 用户协议
     *   - 隐私政策
     *   - 联系我们
     * 5. 底部版权信息
     *
     * 设计规范：
     * - 页面背景：BackgroundLight (#F1F5F9)
     * - 卡片背景：SurfaceLight (白色)
     * - 卡片圆角：corner_md (16.dp)
     * - 列表项高度：menu_item_height (56.dp)
     */
    @androidx.compose.runtime.Composable()
    public static final void AboutScreen(@org.jetbrains.annotations.NotNull()
    com.wuheng.smart.presentation.about.AboutViewModel viewModel, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onNavigateBack, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onFunctionIntroClick, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onUserAgreementClick, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onPrivacyPolicyClick, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onContactUsClick) {
    }
    
    /**
     * 关于页面内容
     */
    @androidx.compose.runtime.Composable()
    @kotlin.OptIn(markerClass = {androidx.compose.material3.ExperimentalMaterial3Api.class})
    public static final void AboutContent(@org.jetbrains.annotations.NotNull()
    com.wuheng.smart.presentation.base.UiDataState<com.wuheng.smart.presentation.about.AboutInfo> aboutInfoState, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onNavigateBack, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onFunctionIntroClick, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onUserAgreementClick, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onPrivacyPolicyClick, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onContactUsClick) {
    }
    
    /**
     * 关于页面主体内容
     */
    @androidx.compose.runtime.Composable()
    private static final void AboutContentBody(androidx.compose.foundation.layout.PaddingValues paddingValues, com.wuheng.smart.presentation.about.AboutInfo aboutInfo, kotlin.jvm.functions.Function0<kotlin.Unit> onFunctionIntroClick, kotlin.jvm.functions.Function0<kotlin.Unit> onUserAgreementClick, kotlin.jvm.functions.Function0<kotlin.Unit> onPrivacyPolicyClick, kotlin.jvm.functions.Function0<kotlin.Unit> onContactUsClick) {
    }
    
    /**
     * 关于页面头部区域 - Logo和应用信息
     *
     * 设计规范：
     * - Logo背景：SurfaceLight (白色)
     * - Logo尺寸：80.dp x 80.dp
     * - 圆角：corner_lg (20.dp)
     * - 应用名称：text_h2_size (20.sp)
     * - 版本号：version_text_size (12.sp)
     */
    @androidx.compose.runtime.Composable()
    private static final void AboutHeaderSection(com.wuheng.smart.presentation.about.AboutInfo aboutInfo) {
    }
    
    /**
     * 关于页面菜单列表
     *
     * 菜单项：
     * 1. 功能介绍
     * 2. 用户协议
     * 3. 隐私政策
     * 4. 联系我们
     */
    @androidx.compose.runtime.Composable()
    private static final void AboutMenuList(kotlin.jvm.functions.Function0<kotlin.Unit> onFunctionIntroClick, kotlin.jvm.functions.Function0<kotlin.Unit> onUserAgreementClick, kotlin.jvm.functions.Function0<kotlin.Unit> onPrivacyPolicyClick, kotlin.jvm.functions.Function0<kotlin.Unit> onContactUsClick) {
    }
    
    /**
     * 关于页面菜单项行组件
     *
     * 设计规范：
     * - 高度：menu_item_height (56.dp)
     * - 水平内边距：menu_item_padding_h (20.dp)
     * - 标题字号：menu_title_size (16.sp)
     * - 箭头颜色：ChevronRightColor (#CBD5E1)
     */
    @androidx.compose.runtime.Composable()
    private static final void AboutMenuItemRow(com.wuheng.smart.presentation.about.AboutMenuItem item, boolean showDivider) {
    }
    
    /**
     * 关于页面底部版权信息
     *
     * 设计规范：
     * - 文字颜色：TextTertiaryLight (#94A3B8)
     * - 字号：version_text_size (12.sp)
     * - 居中对齐
     */
    @androidx.compose.runtime.Composable()
    private static final void AboutFooterSection(java.lang.String copyright) {
    }
    
    @androidx.compose.runtime.Composable()
    @androidx.compose.ui.tooling.preview.Preview(showBackground = true, name = "\u5173\u4e8e\u9875\u9762-\u4eae\u8272\u4e3b\u9898", backgroundColor = 4294047225L)
    public static final void AboutScreenPreview() {
    }
    
    @androidx.compose.runtime.Composable()
    @androidx.compose.ui.tooling.preview.Preview(showBackground = true, name = "\u5173\u4e8e\u9875\u9762-\u52a0\u8f7d\u72b6\u6001", backgroundColor = 4294047225L)
    public static final void AboutScreenLoadingPreview() {
    }
    
    @androidx.compose.runtime.Composable()
    @androidx.compose.ui.tooling.preview.Preview(showBackground = true, name = "\u5173\u4e8e\u9875\u9762-\u6697\u8272\u4e3b\u9898", backgroundColor = 4279179050L)
    public static final void AboutScreenDarkPreview() {
    }
}