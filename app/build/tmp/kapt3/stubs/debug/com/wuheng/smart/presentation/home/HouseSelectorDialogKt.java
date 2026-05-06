package com.wuheng.smart.presentation.home;

import androidx.compose.foundation.layout.*;
import androidx.compose.material.icons.Icons;
import androidx.compose.material3.*;
import androidx.compose.runtime.Composable;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.text.font.FontWeight;
import com.wuheng.smart.data.model.MyHouse;
import com.wuheng.smart.presentation.theme.*;

@kotlin.Metadata(mv = {1, 7, 1}, k = 2, d1 = {"\u00000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a&\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00010\u0007H\u0003\u001a@\u0010\b\u001a\u00020\u00012\f\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00030\n2\u0006\u0010\u000b\u001a\u00020\f2\u0012\u0010\r\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00010\u000e2\f\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00010\u0007H\u0007\u00a8\u0006\u0010"}, d2 = {"HouseItem", "", "house", "Lcom/wuheng/smart/data/model/MyHouse;", "isSelected", "", "onClick", "Lkotlin/Function0;", "HouseSelectorDialog", "houses", "", "currentHouseId", "", "onHouseSelected", "Lkotlin/Function1;", "onDismiss", "app_debug"})
public final class HouseSelectorDialogKt {
    
    /**
     * 房产选择对话框
     */
    @androidx.compose.runtime.Composable()
    public static final void HouseSelectorDialog(@org.jetbrains.annotations.NotNull()
    java.util.List<com.wuheng.smart.data.model.MyHouse> houses, @org.jetbrains.annotations.NotNull()
    java.lang.String currentHouseId, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super com.wuheng.smart.data.model.MyHouse, kotlin.Unit> onHouseSelected, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onDismiss) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void HouseItem(com.wuheng.smart.data.model.MyHouse house, boolean isSelected, kotlin.jvm.functions.Function0<kotlin.Unit> onClick) {
    }
}