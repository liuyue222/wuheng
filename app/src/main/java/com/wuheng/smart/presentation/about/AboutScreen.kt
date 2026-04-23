package com.wuheng.smart.presentation.about

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.wuheng.smart.presentation.base.UiDataState
import com.wuheng.smart.presentation.theme.*

/**
 * 关于新宜能页面 - 像素级还原设计图
 *
 * 布局结构：
 * 1. 顶部导航栏：返回按钮 + "关于新宜能"标题
 * 2. 公司Logo区域：应用图标 + 应用名称
 * 3. 版本信息：当前版本号
 * 4. 功能菜单列表：
 *    - 功能介绍
 *    - 用户协议
 *    - 隐私政策
 *    - 联系我们
 * 5. 底部版权信息
 *
 * 设计规范：
 * - 页面背景：BackgroundLight (#F1F5F9)
 * - 卡片背景：SurfaceLight (白色)
 * - 卡片圆角：corner_md (16.dp)
 * - 列表项高度：menu_item_height (56.dp)
 */
@Composable
fun AboutScreen(
    viewModel: AboutViewModel = hiltViewModel(),
    onNavigateBack: () -> Unit = {},
    onFunctionIntroClick: () -> Unit = {},
    onUserAgreementClick: () -> Unit = {},
    onPrivacyPolicyClick: () -> Unit = {},
    onContactUsClick: () -> Unit = {}
) {
    val aboutInfoState by viewModel.aboutInfoState.collectAsStateWithLifecycle()

    AboutContent(
        aboutInfoState = aboutInfoState,
        onNavigateBack = onNavigateBack,
        onFunctionIntroClick = onFunctionIntroClick,
        onUserAgreementClick = onUserAgreementClick,
        onPrivacyPolicyClick = onPrivacyPolicyClick,
        onContactUsClick = onContactUsClick
    )
}

/**
 * 关于页面内容
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AboutContent(
    aboutInfoState: UiDataState<AboutInfo>,
    onNavigateBack: () -> Unit = {},
    onFunctionIntroClick: () -> Unit = {},
    onUserAgreementClick: () -> Unit = {},
    onPrivacyPolicyClick: () -> Unit = {},
    onContactUsClick: () -> Unit = {}
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "关于新宜能",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.SemiBold,
                        color = TextPrimaryLight
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "返回",
                            tint = BackArrowColor
                        )
                    }
                },
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = BackgroundLight,
                    titleContentColor = TextPrimaryLight
                )
            )
        },
        containerColor = BackgroundLight
    ) { paddingValues ->
        when (aboutInfoState) {
            is UiDataState.Idle, is UiDataState.Loading, is UiDataState.LoadingWithData -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(color = PrimaryBlue)
                }
            }
            is UiDataState.Error, is UiDataState.ErrorWithData -> {
                // 显示默认内容
                AboutContentBody(
                    paddingValues = paddingValues,
                    aboutInfo = AboutInfo(
                        appName = "新宜能五恒系统",
                        version = "V1.2.3",
                        copyright = "© 2024 新宜能科技 版权所有"
                    ),
                    onFunctionIntroClick = onFunctionIntroClick,
                    onUserAgreementClick = onUserAgreementClick,
                    onPrivacyPolicyClick = onPrivacyPolicyClick,
                    onContactUsClick = onContactUsClick
                )
            }
            is UiDataState.Success -> {
                AboutContentBody(
                    paddingValues = paddingValues,
                    aboutInfo = aboutInfoState.data,
                    onFunctionIntroClick = onFunctionIntroClick,
                    onUserAgreementClick = onUserAgreementClick,
                    onPrivacyPolicyClick = onPrivacyPolicyClick,
                    onContactUsClick = onContactUsClick
                )
            }
        }
    }
}

/**
 * 关于页面主体内容
 */
@Composable
private fun AboutContentBody(
    paddingValues: PaddingValues,
    aboutInfo: AboutInfo,
    onFunctionIntroClick: () -> Unit,
    onUserAgreementClick: () -> Unit,
    onPrivacyPolicyClick: () -> Unit,
    onContactUsClick: () -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .padding(horizontal = page_margin_horizontal),
        verticalArrangement = Arrangement.spacedBy(spacing_lg),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item { Spacer(modifier = Modifier.height(spacing_xl)) }

        // ==================== Logo和版本信息区域 ====================
        item {
            AboutHeaderSection(aboutInfo = aboutInfo)
        }

        item { Spacer(modifier = Modifier.height(spacing_md)) }

        // ==================== 功能菜单列表 ====================
        item {
            AboutMenuList(
                onFunctionIntroClick = onFunctionIntroClick,
                onUserAgreementClick = onUserAgreementClick,
                onPrivacyPolicyClick = onPrivacyPolicyClick,
                onContactUsClick = onContactUsClick
            )
        }

        // ==================== 底部版权信息 ====================
        item {
            Spacer(modifier = Modifier.height(spacing_xxl))
            AboutFooterSection(copyright = aboutInfo.copyright)
        }

        item { Spacer(modifier = Modifier.height(spacing_lg)) }
    }
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
@Composable
private fun AboutHeaderSection(aboutInfo: AboutInfo) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(spacing_md)
    ) {
        // Logo容器
        Box(
            modifier = Modifier
                .size(80.dp)
                .shadow(
                    elevation = elevation_md,
                    shape = RoundedCornerShape(corner_lg),
                    ambientColor = ShadowLight.copy(alpha = 0.2f),
                    spotColor = ShadowLight.copy(alpha = 0.3f)
                )
                .clip(RoundedCornerShape(corner_lg))
                .background(SurfaceLight),
            contentAlignment = Alignment.Center
        ) {
            // Logo占位符 - 使用应用首字母
            Text(
                text = "新",
                style = MaterialTheme.typography.displaySmall,
                fontWeight = FontWeight.Bold,
                color = PrimaryBlue
            )
        }

        // 应用名称
        Text(
            text = aboutInfo.appName,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.SemiBold,
            color = TextPrimaryLight,
            fontSize = text_h2_size
        )

        // 版本号
        Text(
            text = aboutInfo.version,
            style = MaterialTheme.typography.bodyMedium,
            color = TextTertiaryLight,
            fontSize = version_text_size
        )
    }
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
@Composable
private fun AboutMenuList(
    onFunctionIntroClick: () -> Unit,
    onUserAgreementClick: () -> Unit,
    onPrivacyPolicyClick: () -> Unit,
    onContactUsClick: () -> Unit
) {
    val menuItems = listOf(
        AboutMenuItem(
            id = "function_intro",
            title = "功能介绍",
            onClick = onFunctionIntroClick
        ),
        AboutMenuItem(
            id = "user_agreement",
            title = "用户协议",
            onClick = onUserAgreementClick
        ),
        AboutMenuItem(
            id = "privacy_policy",
            title = "隐私政策",
            onClick = onPrivacyPolicyClick
        ),
        AboutMenuItem(
            id = "contact_us",
            title = "联系我们",
            onClick = onContactUsClick
        )
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(
                elevation = elevation_md,
                shape = RoundedCornerShape(corner_md),
                ambientColor = ShadowLight.copy(alpha = 0.15f),
                spotColor = ShadowLight.copy(alpha = 0.25f)
            )
            .clip(RoundedCornerShape(corner_md))
            .background(SurfaceLight)
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            menuItems.forEachIndexed { index, item ->
                AboutMenuItemRow(
                    item = item,
                    showDivider = index < menuItems.size - 1
                )
            }
        }
    }
}

/**
 * 关于页面菜单项数据类
 */
private data class AboutMenuItem(
    val id: String,
    val title: String,
    val onClick: () -> Unit
)

/**
 * 关于页面菜单项行组件
 *
 * 设计规范：
 * - 高度：menu_item_height (56.dp)
 * - 水平内边距：menu_item_padding_h (20.dp)
 * - 标题字号：menu_title_size (16.sp)
 * - 箭头颜色：ChevronRightColor (#CBD5E1)
 */
@Composable
private fun AboutMenuItemRow(
    item: AboutMenuItem,
    showDivider: Boolean
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(menu_item_height)
                .clickable(onClick = item.onClick)
                .padding(horizontal = menu_item_padding_h),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // 标题
            Text(
                text = item.title,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Medium,
                color = MenuItemTitleColor,
                fontSize = menu_title_size
            )

            // 右箭头
            Icon(
                imageVector = Icons.Default.KeyboardArrowRight,
                contentDescription = "进入${item.title}",
                tint = ChevronRightColor,
                modifier = Modifier.size(menu_arrow_size)
            )
        }

        // 分割线
        if (showDivider) {
            Divider(
                modifier = Modifier
                    .padding(start = menu_divider_indent)
                    .height(0.5.dp),
                color = DividerLight
            )
        }
    }
}

/**
 * 关于页面底部版权信息
 *
 * 设计规范：
 * - 文字颜色：TextTertiaryLight (#94A3B8)
 * - 字号：version_text_size (12.sp)
 * - 居中对齐
 */
@Composable
private fun AboutFooterSection(copyright: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(spacing_xs)
    ) {
        Text(
            text = copyright,
            style = MaterialTheme.typography.bodySmall,
            color = TextTertiaryLight,
            fontSize = version_text_size,
            textAlign = TextAlign.Center
        )
    }
}

// ==================== Preview 函数 ====================

@Preview(showBackground = true, name = "关于页面-亮色主题", backgroundColor = 0xFFF1F5F9)
@Composable
fun AboutScreenPreview() {
    WuHengTheme {
        AboutContent(
            aboutInfoState = UiDataState.Success(
                AboutInfo(
                    appName = "新宜能五恒系统",
                    version = "V1.2.3",
                    copyright = "© 2024 新宜能科技 版权所有"
                )
            ),
            onNavigateBack = {},
            onFunctionIntroClick = {},
            onUserAgreementClick = {},
            onPrivacyPolicyClick = {},
            onContactUsClick = {}
        )
    }
}

@Preview(showBackground = true, name = "关于页面-加载状态", backgroundColor = 0xFFF1F5F9)
@Composable
fun AboutScreenLoadingPreview() {
    WuHengTheme {
        AboutContent(
            aboutInfoState = UiDataState.Loading,
            onNavigateBack = {},
            onFunctionIntroClick = {},
            onUserAgreementClick = {},
            onPrivacyPolicyClick = {},
            onContactUsClick = {}
        )
    }
}

@Preview(showBackground = true, name = "关于页面-暗色主题", backgroundColor = 0xFF0F172A)
@Composable
fun AboutScreenDarkPreview() {
    WuHengTheme(darkTheme = true) {
        AboutContent(
            aboutInfoState = UiDataState.Success(
                AboutInfo(
                    appName = "新宜能五恒系统",
                    version = "V1.2.3",
                    copyright = "© 2024 新宜能科技 版权所有"
                )
            ),
            onNavigateBack = {},
            onFunctionIntroClick = {},
            onUserAgreementClick = {},
            onPrivacyPolicyClick = {},
            onContactUsClick = {}
        )
    }
}
