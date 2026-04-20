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

@kotlin.Metadata(mv = {1, 7, 1}, k = 2, d1 = {"\u00006\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010 \n\u0002\b\u0002\u001a*\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u000e\b\u0002\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00010\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u0007H\u0007\u001a\b\u0010\b\u001a\u00020\u0001H\u0007\u001a2\u0010\t\u001a\u00020\u00012\b\b\u0002\u0010\n\u001a\u00020\u000b2\u0014\b\u0002\u0010\f\u001a\u000e\u0012\u0004\u0012\u00020\u000e\u0012\u0004\u0012\u00020\u00010\r2\b\b\u0002\u0010\u0006\u001a\u00020\u0007H\u0007\u001a(\u0010\u000f\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00010\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u0007H\u0003\u001a\b\u0010\u0010\u001a\u00020\u0001H\u0007\u001a8\u0010\u0011\u001a\u00020\u00012\u000e\b\u0002\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00030\u00132\u0014\b\u0002\u0010\f\u001a\u000e\u0012\u0004\u0012\u00020\u000e\u0012\u0004\u0012\u00020\u00010\r2\b\b\u0002\u0010\u0006\u001a\u00020\u0007H\u0007\u001a\u000e\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00030\u0013H\u0002\u00a8\u0006\u0015"}, d2 = {"ServiceFeaturedCard", "", "item", "Lcom/wuheng/smart/presentation/home/components/ServiceItem;", "onClick", "Lkotlin/Function0;", "modifier", "Landroidx/compose/ui/Modifier;", "ServiceFeaturedCardPreview", "ServiceGrid", "uiState", "Lcom/wuheng/smart/presentation/home/components/ServiceGridUiState;", "onServiceClick", "Lkotlin/Function1;", "Lcom/wuheng/smart/data/model/ServiceType;", "ServiceGridItem", "ServiceGridPreview", "ServiceHorizontalList", "services", "", "defaultServices", "app_debug"})
public final class ServiceGridKt {
    
    private static final java.util.List<com.wuheng.smart.presentation.home.components.ServiceItem> defaultServices() {
        return null;
    }
    
    @androidx.compose.runtime.Composable()
    public static final void ServiceGrid(@org.jetbrains.annotations.NotNull()
    com.wuheng.smart.presentation.home.components.ServiceGridUiState uiState, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super com.wuheng.smart.data.model.ServiceType, kotlin.Unit> onServiceClick, @org.jetbrains.annotations.NotNull()
    androidx.compose.ui.Modifier modifier) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void ServiceGridItem(com.wuheng.smart.presentation.home.components.ServiceItem item, kotlin.jvm.functions.Function0<kotlin.Unit> onClick, androidx.compose.ui.Modifier modifier) {
    }
    
    /**
     * 横向滚动服务列表（用于更多场景）
     */
    @androidx.compose.runtime.Composable()
    public static final void ServiceHorizontalList(@org.jetbrains.annotations.NotNull()
    java.util.List<com.wuheng.smart.presentation.home.components.ServiceItem> services, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super com.wuheng.smart.data.model.ServiceType, kotlin.Unit> onServiceClick, @org.jetbrains.annotations.NotNull()
    androidx.compose.ui.Modifier modifier) {
    }
    
    /**
     * 单个服务入口卡片（大尺寸版本，用于首页推荐）
     */
    @androidx.compose.runtime.Composable()
    public static final void ServiceFeaturedCard(@org.jetbrains.annotations.NotNull()
    com.wuheng.smart.presentation.home.components.ServiceItem item, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onClick, @org.jetbrains.annotations.NotNull()
    androidx.compose.ui.Modifier modifier) {
    }
    
    @androidx.compose.runtime.Composable()
    @androidx.compose.ui.tooling.preview.Preview(showBackground = true, name = "\u670d\u52a1\u7f51\u683c", backgroundColor = 4294047225L)
    public static final void ServiceGridPreview() {
    }
    
    @androidx.compose.runtime.Composable()
    @androidx.compose.ui.tooling.preview.Preview(showBackground = true, name = "\u7279\u8272\u670d\u52a1\u5361\u7247", backgroundColor = 4294047225L)
    public static final void ServiceFeaturedCardPreview() {
    }
}