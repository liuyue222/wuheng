package com.wuheng.smart.presentation.home.components;

import androidx.compose.animation.core.Spring;
import androidx.compose.foundation.layout.*;
import androidx.compose.material3.*;
import androidx.compose.runtime.*;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.graphics.ColorFilter;
import androidx.compose.ui.text.font.FontWeight;
import androidx.compose.ui.tooling.preview.Preview;
import com.wuheng.smart.R;
import com.wuheng.smart.data.model.WeatherMode;
import com.wuheng.smart.presentation.theme.*;

/**
 * 天气模式选择器组件 - 像素级还原第二版设计图
 *
 * 设计规范要点 (基于Dimension.kt和Color.kt):
 * ❌ 未选中时：背景色完全透明 (Color.Transparent)
 * ✅ 选中时：白色背景 + elevation_lg阴影 + 白色图标文字
 *
 * 尺寸规范:
 * - 按钮高度：mode_button_height = 44.dp
 * - 图标尺寸：mode_button_icon_size = 18.dp
 * - 按钮间距：mode_button_gap = 12.dp
 * - 选中态背景：ChipSelectedBg (#FFFFFF)
 * - 选中态阴影：elevation_lg = 8.dp
 * - 未选中态背景：Color.Transparent （完全透明！）
 * - 文字样式：mode_button_text_size = 14.sp, Medium
 *
 * 布局结构分析:
 * - 外层: 圆角矩形玻璃拟态卡片 (corner_md = 16dp圆角)
 * - 内部: 横向排列的3个按钮（除湿/制冷、通风、供暖/加湿）
 * - 布局方式: 水平排列，图标在左、文字在右 (Row布局)
 *
 * 切图资源引用:
 *  - 雪花(1).png -> ic_snowflake (制冷模式)
 *  - 太阳.png -> ic_sun (制热/供暖加湿模式)
 *  - 风.png -> ic_wind (通风模式)
 */
@kotlin.Metadata(mv = {1, 7, 1}, k = 1, d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0000\n\u0002\u0010\u000b\n\u0002\b\r\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0087\b\u0018\u00002\u00020\u0001B)\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\u000e\b\u0002\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00030\u0005\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\u0002\u0010\bJ\t\u0010\u000e\u001a\u00020\u0003H\u00c6\u0003J\u000f\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00030\u0005H\u00c6\u0003J\t\u0010\u0010\u001a\u00020\u0007H\u00c6\u0003J-\u0010\u0011\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\u000e\b\u0002\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00030\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u0007H\u00c6\u0001J\u0013\u0010\u0012\u001a\u00020\u00072\b\u0010\u0013\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010\u0014\u001a\u00020\u0015H\u00d6\u0001J\t\u0010\u0016\u001a\u00020\u0017H\u00d6\u0001R\u0017\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00030\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0011\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u000bR\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\f\u0010\r\u00a8\u0006\u0018"}, d2 = {"Lcom/wuheng/smart/presentation/home/components/WeatherModeSelectorUiState;", "", "selectedMode", "Lcom/wuheng/smart/data/model/WeatherMode;", "availableModes", "", "isEnabled", "", "(Lcom/wuheng/smart/data/model/WeatherMode;Ljava/util/List;Z)V", "getAvailableModes", "()Ljava/util/List;", "()Z", "getSelectedMode", "()Lcom/wuheng/smart/data/model/WeatherMode;", "component1", "component2", "component3", "copy", "equals", "other", "hashCode", "", "toString", "", "app_debug"})
public final class WeatherModeSelectorUiState {
    @org.jetbrains.annotations.NotNull()
    private final com.wuheng.smart.data.model.WeatherMode selectedMode = null;
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<com.wuheng.smart.data.model.WeatherMode> availableModes = null;
    private final boolean isEnabled = false;
    
    /**
     * 天气模式选择器组件 - 像素级还原第二版设计图
     *
     * 设计规范要点 (基于Dimension.kt和Color.kt):
     * ❌ 未选中时：背景色完全透明 (Color.Transparent)
     * ✅ 选中时：白色背景 + elevation_lg阴影 + 白色图标文字
     *
     * 尺寸规范:
     * - 按钮高度：mode_button_height = 44.dp
     * - 图标尺寸：mode_button_icon_size = 18.dp
     * - 按钮间距：mode_button_gap = 12.dp
     * - 选中态背景：ChipSelectedBg (#FFFFFF)
     * - 选中态阴影：elevation_lg = 8.dp
     * - 未选中态背景：Color.Transparent （完全透明！）
     * - 文字样式：mode_button_text_size = 14.sp, Medium
     *
     * 布局结构分析:
     * - 外层: 圆角矩形玻璃拟态卡片 (corner_md = 16dp圆角)
     * - 内部: 横向排列的3个按钮（除湿/制冷、通风、供暖/加湿）
     * - 布局方式: 水平排列，图标在左、文字在右 (Row布局)
     *
     * 切图资源引用:
     *  - 雪花(1).png -> ic_snowflake (制冷模式)
     *  - 太阳.png -> ic_sun (制热/供暖加湿模式)
     *  - 风.png -> ic_wind (通风模式)
     */
    @org.jetbrains.annotations.NotNull()
    public final com.wuheng.smart.presentation.home.components.WeatherModeSelectorUiState copy(@org.jetbrains.annotations.NotNull()
    com.wuheng.smart.data.model.WeatherMode selectedMode, @org.jetbrains.annotations.NotNull()
    java.util.List<? extends com.wuheng.smart.data.model.WeatherMode> availableModes, boolean isEnabled) {
        return null;
    }
    
    /**
     * 天气模式选择器组件 - 像素级还原第二版设计图
     *
     * 设计规范要点 (基于Dimension.kt和Color.kt):
     * ❌ 未选中时：背景色完全透明 (Color.Transparent)
     * ✅ 选中时：白色背景 + elevation_lg阴影 + 白色图标文字
     *
     * 尺寸规范:
     * - 按钮高度：mode_button_height = 44.dp
     * - 图标尺寸：mode_button_icon_size = 18.dp
     * - 按钮间距：mode_button_gap = 12.dp
     * - 选中态背景：ChipSelectedBg (#FFFFFF)
     * - 选中态阴影：elevation_lg = 8.dp
     * - 未选中态背景：Color.Transparent （完全透明！）
     * - 文字样式：mode_button_text_size = 14.sp, Medium
     *
     * 布局结构分析:
     * - 外层: 圆角矩形玻璃拟态卡片 (corner_md = 16dp圆角)
     * - 内部: 横向排列的3个按钮（除湿/制冷、通风、供暖/加湿）
     * - 布局方式: 水平排列，图标在左、文字在右 (Row布局)
     *
     * 切图资源引用:
     *  - 雪花(1).png -> ic_snowflake (制冷模式)
     *  - 太阳.png -> ic_sun (制热/供暖加湿模式)
     *  - 风.png -> ic_wind (通风模式)
     */
    @java.lang.Override()
    public boolean equals(@org.jetbrains.annotations.Nullable()
    java.lang.Object other) {
        return false;
    }
    
    /**
     * 天气模式选择器组件 - 像素级还原第二版设计图
     *
     * 设计规范要点 (基于Dimension.kt和Color.kt):
     * ❌ 未选中时：背景色完全透明 (Color.Transparent)
     * ✅ 选中时：白色背景 + elevation_lg阴影 + 白色图标文字
     *
     * 尺寸规范:
     * - 按钮高度：mode_button_height = 44.dp
     * - 图标尺寸：mode_button_icon_size = 18.dp
     * - 按钮间距：mode_button_gap = 12.dp
     * - 选中态背景：ChipSelectedBg (#FFFFFF)
     * - 选中态阴影：elevation_lg = 8.dp
     * - 未选中态背景：Color.Transparent （完全透明！）
     * - 文字样式：mode_button_text_size = 14.sp, Medium
     *
     * 布局结构分析:
     * - 外层: 圆角矩形玻璃拟态卡片 (corner_md = 16dp圆角)
     * - 内部: 横向排列的3个按钮（除湿/制冷、通风、供暖/加湿）
     * - 布局方式: 水平排列，图标在左、文字在右 (Row布局)
     *
     * 切图资源引用:
     *  - 雪花(1).png -> ic_snowflake (制冷模式)
     *  - 太阳.png -> ic_sun (制热/供暖加湿模式)
     *  - 风.png -> ic_wind (通风模式)
     */
    @java.lang.Override()
    public int hashCode() {
        return 0;
    }
    
    /**
     * 天气模式选择器组件 - 像素级还原第二版设计图
     *
     * 设计规范要点 (基于Dimension.kt和Color.kt):
     * ❌ 未选中时：背景色完全透明 (Color.Transparent)
     * ✅ 选中时：白色背景 + elevation_lg阴影 + 白色图标文字
     *
     * 尺寸规范:
     * - 按钮高度：mode_button_height = 44.dp
     * - 图标尺寸：mode_button_icon_size = 18.dp
     * - 按钮间距：mode_button_gap = 12.dp
     * - 选中态背景：ChipSelectedBg (#FFFFFF)
     * - 选中态阴影：elevation_lg = 8.dp
     * - 未选中态背景：Color.Transparent （完全透明！）
     * - 文字样式：mode_button_text_size = 14.sp, Medium
     *
     * 布局结构分析:
     * - 外层: 圆角矩形玻璃拟态卡片 (corner_md = 16dp圆角)
     * - 内部: 横向排列的3个按钮（除湿/制冷、通风、供暖/加湿）
     * - 布局方式: 水平排列，图标在左、文字在右 (Row布局)
     *
     * 切图资源引用:
     *  - 雪花(1).png -> ic_snowflake (制冷模式)
     *  - 太阳.png -> ic_sun (制热/供暖加湿模式)
     *  - 风.png -> ic_wind (通风模式)
     */
    @org.jetbrains.annotations.NotNull()
    @java.lang.Override()
    public java.lang.String toString() {
        return null;
    }
    
    public WeatherModeSelectorUiState() {
        super();
    }
    
    public WeatherModeSelectorUiState(@org.jetbrains.annotations.NotNull()
    com.wuheng.smart.data.model.WeatherMode selectedMode, @org.jetbrains.annotations.NotNull()
    java.util.List<? extends com.wuheng.smart.data.model.WeatherMode> availableModes, boolean isEnabled) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.wuheng.smart.data.model.WeatherMode component1() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.wuheng.smart.data.model.WeatherMode getSelectedMode() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.wuheng.smart.data.model.WeatherMode> component2() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.wuheng.smart.data.model.WeatherMode> getAvailableModes() {
        return null;
    }
    
    public final boolean component3() {
        return false;
    }
    
    public final boolean isEnabled() {
        return false;
    }
}