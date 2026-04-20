package com.wuheng.smart.presentation.home.components;

import androidx.compose.animation.core.Spring;
import androidx.compose.foundation.layout.*;
import androidx.compose.material.icons.Icons;
import androidx.compose.material3.*;
import androidx.compose.runtime.*;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.graphics.ColorFilter;
import androidx.compose.ui.text.font.FontWeight;
import androidx.compose.ui.text.style.TextAlign;
import androidx.compose.ui.tooling.preview.Preview;
import com.wuheng.smart.R;
import com.wuheng.smart.data.model.ServiceType;
import com.wuheng.smart.presentation.theme.*;

/**
 * 服务入口网格组件
 *
 * 布局结构分析（基于设计图）:
 * - 外层: 垂直布局，包含标题 + 2x2网格
 * - 网格项: 圆角矩形卡片 (14dp圆角)
 *  - 上半部分: 图标居中显示（带圆形背景）
 *  - 下半部分: 服务名称文字
 * - 交互: 点击反馈（缩放+阴影变化）
 *
 * 切图资源引用:
 *  - 上门服务-面.png -> ic_service (上门服务)
 *  - 沙发，空位.png -> ic_couch (家居/空间服务)
 *  - _叶子.png -> ic_leaf (环保/绿植服务)
 *  - liebiao.png -> 列表/更多服务
 */
@kotlin.Metadata(mv = {1, 7, 1}, k = 1, d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0002\b\u000f\n\u0002\u0010\u000b\n\u0002\b\u0004\b\u0087\b\u0018\u00002\u00020\u0001B\'\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\b\b\u0002\u0010\b\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\tJ\t\u0010\u0011\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u0012\u001a\u00020\u0005H\u00c6\u0003J\t\u0010\u0013\u001a\u00020\u0007H\u00c6\u0003J\t\u0010\u0014\u001a\u00020\u0005H\u00c6\u0003J1\u0010\u0015\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00072\b\b\u0002\u0010\b\u001a\u00020\u0005H\u00c6\u0001J\u0013\u0010\u0016\u001a\u00020\u00172\b\u0010\u0018\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010\u0019\u001a\u00020\u0007H\u00d6\u0001J\t\u0010\u001a\u001a\u00020\u0005H\u00d6\u0001R\u0011\u0010\b\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0011\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0011\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000bR\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010\u00a8\u0006\u001b"}, d2 = {"Lcom/wuheng/smart/presentation/home/components/ServiceItem;", "", "type", "Lcom/wuheng/smart/data/model/ServiceType;", "name", "", "iconRes", "", "description", "(Lcom/wuheng/smart/data/model/ServiceType;Ljava/lang/String;ILjava/lang/String;)V", "getDescription", "()Ljava/lang/String;", "getIconRes", "()I", "getName", "getType", "()Lcom/wuheng/smart/data/model/ServiceType;", "component1", "component2", "component3", "component4", "copy", "equals", "", "other", "hashCode", "toString", "app_debug"})
public final class ServiceItem {
    @org.jetbrains.annotations.NotNull()
    private final com.wuheng.smart.data.model.ServiceType type = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String name = null;
    private final int iconRes = 0;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String description = null;
    
    /**
     * 服务入口网格组件
     *
     * 布局结构分析（基于设计图）:
     * - 外层: 垂直布局，包含标题 + 2x2网格
     * - 网格项: 圆角矩形卡片 (14dp圆角)
     *  - 上半部分: 图标居中显示（带圆形背景）
     *  - 下半部分: 服务名称文字
     * - 交互: 点击反馈（缩放+阴影变化）
     *
     * 切图资源引用:
     *  - 上门服务-面.png -> ic_service (上门服务)
     *  - 沙发，空位.png -> ic_couch (家居/空间服务)
     *  - _叶子.png -> ic_leaf (环保/绿植服务)
     *  - liebiao.png -> 列表/更多服务
     */
    @org.jetbrains.annotations.NotNull()
    public final com.wuheng.smart.presentation.home.components.ServiceItem copy(@org.jetbrains.annotations.NotNull()
    com.wuheng.smart.data.model.ServiceType type, @org.jetbrains.annotations.NotNull()
    java.lang.String name, int iconRes, @org.jetbrains.annotations.NotNull()
    java.lang.String description) {
        return null;
    }
    
    /**
     * 服务入口网格组件
     *
     * 布局结构分析（基于设计图）:
     * - 外层: 垂直布局，包含标题 + 2x2网格
     * - 网格项: 圆角矩形卡片 (14dp圆角)
     *  - 上半部分: 图标居中显示（带圆形背景）
     *  - 下半部分: 服务名称文字
     * - 交互: 点击反馈（缩放+阴影变化）
     *
     * 切图资源引用:
     *  - 上门服务-面.png -> ic_service (上门服务)
     *  - 沙发，空位.png -> ic_couch (家居/空间服务)
     *  - _叶子.png -> ic_leaf (环保/绿植服务)
     *  - liebiao.png -> 列表/更多服务
     */
    @java.lang.Override()
    public boolean equals(@org.jetbrains.annotations.Nullable()
    java.lang.Object other) {
        return false;
    }
    
    /**
     * 服务入口网格组件
     *
     * 布局结构分析（基于设计图）:
     * - 外层: 垂直布局，包含标题 + 2x2网格
     * - 网格项: 圆角矩形卡片 (14dp圆角)
     *  - 上半部分: 图标居中显示（带圆形背景）
     *  - 下半部分: 服务名称文字
     * - 交互: 点击反馈（缩放+阴影变化）
     *
     * 切图资源引用:
     *  - 上门服务-面.png -> ic_service (上门服务)
     *  - 沙发，空位.png -> ic_couch (家居/空间服务)
     *  - _叶子.png -> ic_leaf (环保/绿植服务)
     *  - liebiao.png -> 列表/更多服务
     */
    @java.lang.Override()
    public int hashCode() {
        return 0;
    }
    
    /**
     * 服务入口网格组件
     *
     * 布局结构分析（基于设计图）:
     * - 外层: 垂直布局，包含标题 + 2x2网格
     * - 网格项: 圆角矩形卡片 (14dp圆角)
     *  - 上半部分: 图标居中显示（带圆形背景）
     *  - 下半部分: 服务名称文字
     * - 交互: 点击反馈（缩放+阴影变化）
     *
     * 切图资源引用:
     *  - 上门服务-面.png -> ic_service (上门服务)
     *  - 沙发，空位.png -> ic_couch (家居/空间服务)
     *  - _叶子.png -> ic_leaf (环保/绿植服务)
     *  - liebiao.png -> 列表/更多服务
     */
    @org.jetbrains.annotations.NotNull()
    @java.lang.Override()
    public java.lang.String toString() {
        return null;
    }
    
    public ServiceItem(@org.jetbrains.annotations.NotNull()
    com.wuheng.smart.data.model.ServiceType type, @org.jetbrains.annotations.NotNull()
    java.lang.String name, int iconRes, @org.jetbrains.annotations.NotNull()
    java.lang.String description) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.wuheng.smart.data.model.ServiceType component1() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.wuheng.smart.data.model.ServiceType getType() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component2() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getName() {
        return null;
    }
    
    public final int component3() {
        return 0;
    }
    
    public final int getIconRes() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component4() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getDescription() {
        return null;
    }
}