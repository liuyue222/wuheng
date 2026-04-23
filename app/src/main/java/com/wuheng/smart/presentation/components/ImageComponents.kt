package com.wuheng.smart.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.DefaultAlpha
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.CachePolicy
import coil.request.ImageRequest
import coil.size.Scale
import coil.size.Size
import com.wuheng.smart.R

/**
 * 图片加载组件 - 内存优化版本
 * 提供统一的图片加载接口，内置缓存和内存优化策略
 */

/**
 * 优化的网络图片加载组件
 *
 * @param model 图片URL或资源
 * @param contentDescription 内容描述
 * @param modifier 修饰符
 * @param placeholder 占位图
 * @param error 错误图
 * @param contentScale 内容缩放模式
 * @param alignment 对齐方式
 * @param alpha 透明度
 * @param colorFilter 颜色滤镜
 * @param size 目标尺寸（用于内存优化，null表示使用原始尺寸）
 * @param allowHardware 是否允许硬件位图（Android O+）
 */
@Composable
fun OptimizedAsyncImage(
    model: Any?,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    placeholder: Painter? = null,
    error: Painter? = null,
    contentScale: ContentScale = ContentScale.Fit,
    alignment: Alignment = Alignment.Center,
    alpha: Float = DefaultAlpha,
    colorFilter: ColorFilter? = null,
    size: Size = Size.ORIGINAL,
    allowHardware: Boolean = true
) {
    val context = LocalContext.current

    val imageRequest = remember(model, size, allowHardware) {
        ImageRequest.Builder(context)
            .data(model)
            .size(size)
            .scale(Scale.FIT)
            .crossfade(true)
            .crossfade(200)
            // 内存缓存策略
            .memoryCachePolicy(CachePolicy.ENABLED)
            // 磁盘缓存策略
            .diskCachePolicy(CachePolicy.ENABLED)
            // 网络缓存策略
            .networkCachePolicy(CachePolicy.ENABLED)
            // 硬件位图（减少内存占用）
            .allowHardware(allowHardware)
            .build()
    }

    AsyncImage(
        model = imageRequest,
        contentDescription = contentDescription,
        modifier = modifier,
        placeholder = placeholder,
        error = error,
        contentScale = contentScale,
        alignment = alignment,
        alpha = alpha,
        colorFilter = colorFilter
    )
}

/**
 * 带加载状态的图片组件
 * 适用于需要监听加载状态的场景
 */
@Composable
fun OptimizedImageWithState(
    model: Any?,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    size: Size = Size.ORIGINAL,
    onLoading: @Composable () -> Unit = {},
    onError: @Composable () -> Unit = {},
    onSuccess: @Composable (Painter) -> Unit = { painter ->
        Image(
            painter = painter,
            contentDescription = contentDescription,
            modifier = modifier
        )
    }
) {
    val context = LocalContext.current

    val imageRequest = remember(model, size) {
        ImageRequest.Builder(context)
            .data(model)
            .size(size)
            .scale(Scale.FIT)
            .crossfade(true)
            .memoryCachePolicy(CachePolicy.ENABLED)
            .diskCachePolicy(CachePolicy.ENABLED)
            .build()
    }

    val painter = rememberAsyncImagePainter(
        model = imageRequest
    )

    val state = painter.state

    when (state) {
        is AsyncImagePainter.State.Loading -> {
            onLoading()
        }
        is AsyncImagePainter.State.Error -> {
            onError()
        }
        is AsyncImagePainter.State.Success -> {
            onSuccess(painter)
        }
        is AsyncImagePainter.State.Empty -> {
            onLoading()
        }
    }
}

/**
 * 列表图片组件 - 针对列表优化
 * 使用较小的内存缓存，支持快速滚动时暂停加载
 */
@Composable
fun ListItemImage(
    model: Any?,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    shouldLoad: Boolean = true,
    size: Size = Size(200, 200)
) {
    if (!shouldLoad) {
        // 快速滚动时不加载图片，显示占位图
        Image(
            painter = painterResource(id = R.drawable.ic_launcher_foreground),
            contentDescription = contentDescription,
            modifier = modifier
        )
        return
    }

    OptimizedAsyncImage(
        model = model,
        contentDescription = contentDescription,
        modifier = modifier,
        size = size,
        contentScale = ContentScale.Crop
    )
}

/**
 * 头像图片组件 - 固定小尺寸
 * 适用于用户头像等固定尺寸场景
 */
@Composable
fun AvatarImage(
    model: Any?,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    size: Int = 100
) {
    OptimizedAsyncImage(
        model = model,
        contentDescription = contentDescription,
        modifier = modifier,
        size = Size(size, size),
        contentScale = ContentScale.Crop,
        placeholder = painterResource(id = R.drawable.ic_launcher_foreground),
        error = painterResource(id = R.drawable.ic_launcher_foreground)
    )
}

/**
 * 背景图片组件 - 低质量预览
 * 先显示低质量预览，再加载高清图
 */
@Composable
fun BackgroundImage(
    model: Any?,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    previewModel: Any? = null
) {
    var showPreview by remember { mutableStateOf(previewModel != null) }

    if (showPreview && previewModel != null) {
        // 先显示低质量预览图
        OptimizedAsyncImage(
            model = previewModel,
            contentDescription = contentDescription,
            modifier = modifier,
            size = Size(100, 100), // 低分辨率
            contentScale = ContentScale.Crop
        )
    }

    // 加载高清图
    OptimizedAsyncImage(
        model = model,
        contentDescription = contentDescription,
        modifier = modifier,
        contentScale = ContentScale.Crop
    )
    
    // 成功加载后隐藏预览
    LaunchedEffect(model) {
        showPreview = false
    }
}
