@file:OptIn(ExperimentalMaterial3Api::class)

package com.wuheng.smart.presentation.privacypolicy

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.wuheng.smart.presentation.theme.*

/**
 * 隐私协议页面
 *
 * 布局结构：
 * 1. 顶部导航栏：返回按钮 + "隐私协议"标题
 * 2. 隐私协议内容：滚动文本区域
 *
 * 设计规范：
 * - 页面背景：BackgroundLight (#F1F5F9)
 * - 文字颜色：TextPrimaryLight (#1E293B)
 * - 标题字号：text_h2_size (20.sp)
 * - 正文字号：text_body_size (14.sp)
 *
 * @param onNavigateBack 返回上一页回调
 */
@Composable
fun PrivacyPolicyScreen(
    onNavigateBack: () -> Unit = {}
) {
    Scaffold(
        topBar = {
            PrivacyPolicyTopBar(onNavigateBack = onNavigateBack)
        },
        containerColor = BackgroundLight
    ) { paddingValues ->
        PrivacyPolicyContent(
            modifier = Modifier.padding(paddingValues)
        )
    }
}

/**
 * 隐私协议页面顶部导航栏
 */
@Composable
private fun PrivacyPolicyTopBar(
    onNavigateBack: () -> Unit
) {
    TopAppBar(
        title = {
            Text(
                text = "隐私协议",
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
}

/**
 * 隐私协议页面内容
 */
@Composable
private fun PrivacyPolicyContent(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = page_margin_horizontal)
            .padding(vertical = spacing_lg)
    ) {
        // 隐私协议标题
        Text(
            text = "新宜能五恒系统隐私协议",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            color = TextPrimaryLight,
            fontSize = text_h2_size,
            modifier = Modifier.padding(bottom = spacing_lg)
        )

        // 最后更新日期
        Text(
            text = "最后更新日期：2024年1月1日",
            style = MaterialTheme.typography.bodyMedium,
            color = TextSecondaryLight,
            fontSize = text_caption_size,
            modifier = Modifier.padding(bottom = spacing_xl)
        )

        // 隐私协议章节
        PrivacyPolicySection(
            title = "一、引言",
            content = """
                新宜能科技（以下简称"我们"或"本公司"）非常重视用户的隐私保护。本隐私协议旨在向您说明我们如何收集、使用、存储和保护您的个人信息。请您在使用我们的服务前，仔细阅读并理解本隐私协议的全部内容。
                
                一旦您开始使用新宜能五恒系统APP（以下简称"本应用"），即表示您已充分理解并同意本隐私协议的所有条款。如您不同意本隐私协议的任何内容，请立即停止使用本应用。
            """.trimIndent()
        )

        PrivacyPolicySection(
            title = "二、信息收集",
            content = """
                我们可能会收集以下类型的信息：
                
                1. 账户信息：当您注册账户时，我们会收集您的用户名、手机号码、电子邮箱等信息。
                
                2. 设备信息：我们会收集您使用的设备型号、操作系统版本、设备标识符等技术信息。
                
                3. 使用数据：我们会收集您使用本应用的相关数据，包括操作日志、功能使用情况等。
                
                4. 位置信息：经您授权后，我们可能会收集您的地理位置信息，用于提供天气服务和定位功能。
                
                5. 房屋信息：您绑定的房屋信息、设备配置信息等，用于提供智能家居控制服务。
            """.trimIndent()
        )

        PrivacyPolicySection(
            title = "三、信息使用",
            content = """
                我们收集的信息将用于以下目的：
                
                1. 提供、维护和改进我们的服务；
                2. 处理您的账户注册和登录；
                3. 响应您的客户服务请求；
                4. 发送服务通知和更新信息；
                5. 进行数据分析和研究，以改善用户体验；
                6. 防止欺诈和滥用行为，保障账户安全。
            """.trimIndent()
        )

        PrivacyPolicySection(
            title = "四、信息共享",
            content = """
                我们不会向第三方出售、交易或转让您的个人信息。但在以下情况下，我们可能会共享您的信息：
                
                1. 经您明确同意；
                2. 与我们的服务提供商共享，以帮助我们运营业务（这些提供商有义务保护您的信息）；
                3. 遵守法律法规要求，响应法律程序；
                4. 保护我们或他人的权利、财产或安全。
            """.trimIndent()
        )

        PrivacyPolicySection(
            title = "五、信息安全",
            content = """
                我们采取多种安全措施来保护您的个人信息：
                
                1. 使用加密技术传输敏感数据；
                2. 实施访问控制，限制对员工访问个人信息的权限；
                3. 定期进行安全审计和风险评估；
                4. 建立数据备份和灾难恢复机制。
                
                尽管我们采取了上述措施，但请注意，互联网传输无法保证100%安全。您使用我们的服务即表示您理解并接受这一风险。
            """.trimIndent()
        )

        PrivacyPolicySection(
            title = "六、您的权利",
            content = """
                根据适用的数据保护法律，您享有以下权利：
                
                1. 访问权：您有权访问我们持有的关于您的个人信息；
                2. 更正权：您有权要求更正不准确的个人信息；
                3. 删除权：在特定情况下，您有权要求删除您的个人信息；
                4. 限制处理权：在特定情况下，您有权要求限制对您个人信息的处理；
                5. 数据可携带权：您有权以结构化、通用的格式获取您的个人信息；
                6. 反对权：您有权反对我们处理您的个人信息。
                
                如需行使上述权利，请通过本协议末尾的联系方式与我们联系。
            """.trimIndent()
        )

        PrivacyPolicySection(
            title = "七、Cookie和类似技术",
            content = """
                我们可能会使用Cookie和类似技术来收集和存储信息。这些技术帮助我们：
                
                1. 记住您的登录状态和偏好设置；
                2. 了解您如何使用我们的服务；
                3. 改善用户体验和提供个性化服务。
                
                您可以通过设备或浏览器设置管理Cookie偏好，但请注意，禁用Cookie可能会影响某些功能的正常使用。
            """.trimIndent()
        )

        PrivacyPolicySection(
            title = "八、儿童隐私",
            content = """
                我们的服务不面向14岁以下的儿童。我们不会故意收集14岁以下儿童的个人信息。如果您发现我们无意中收集了儿童的个人信息，请立即联系我们，我们将尽快删除相关信息。
            """.trimIndent()
        )

        PrivacyPolicySection(
            title = "九、隐私协议的变更",
            content = """
                我们可能会不时更新本隐私协议。当我们对协议进行重大变更时，我们会在应用内发布通知，并更新协议顶部的"最后更新日期"。
                
                我们建议您定期查看本隐私协议，以了解我们如何保护您的信息。继续使用我们的服务即表示您接受修订后的隐私协议。
            """.trimIndent()
        )

        PrivacyPolicySection(
            title = "十、联系我们",
            content = """
                如果您对本隐私协议有任何疑问、意见或投诉，请通过以下方式与我们联系：
                
                公司名称：新宜能科技有限公司
                联系地址：上海市浦东新区张江高科技园区
                客服电话：400-888-8888
                电子邮箱：privacy@wuheng.com
                
                我们将在收到您的反馈后15个工作日内予以回复。
            """.trimIndent()
        )

        // 底部间距
        Spacer(modifier = Modifier.height(spacing_xxl))

        // 版权信息
        Text(
            text = "© 2024 新宜能科技 版权所有",
            style = MaterialTheme.typography.bodySmall,
            color = TextTertiaryLight,
            fontSize = version_text_size,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(spacing_lg))
    }
}

/**
 * 隐私协议章节组件
 */
@Composable
private fun PrivacyPolicySection(
    title: String,
    content: String
) {
    Column(
        modifier = Modifier.padding(bottom = spacing_xl)
    ) {
        // 章节标题
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.SemiBold,
            color = TextPrimaryLight,
            fontSize = text_h3_size,
            modifier = Modifier.padding(bottom = spacing_md)
        )

        // 章节内容
        Text(
            text = content,
            style = MaterialTheme.typography.bodyMedium,
            color = TextPrimaryLight,
            fontSize = text_body_size,
            lineHeight = text_line_height_body,
            modifier = Modifier.padding(bottom = spacing_sm)
        )
    }
}

// ==================== Preview 函数 ====================

@Preview(showBackground = true, name = "隐私协议页面-亮色主题", backgroundColor = 0xFFF1F5F9)
@Composable
fun PrivacyPolicyScreenPreview() {
    WuHengTheme {
        PrivacyPolicyScreen(
            onNavigateBack = {}
        )
    }
}

@Preview(showBackground = true, name = "隐私协议页面-暗色主题", backgroundColor = 0xFF0F172A)
@Composable
fun PrivacyPolicyScreenDarkPreview() {
    WuHengTheme(darkTheme = true) {
        PrivacyPolicyScreen(
            onNavigateBack = {}
        )
    }
}
