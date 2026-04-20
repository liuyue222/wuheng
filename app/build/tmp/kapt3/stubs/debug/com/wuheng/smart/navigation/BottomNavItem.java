package com.wuheng.smart.navigation;

import androidx.annotation.StringRes;
import androidx.compose.material.icons.Icons;
import androidx.compose.ui.graphics.vector.ImageVector;
import com.wuheng.smart.R;

@kotlin.Metadata(mv = {1, 7, 1}, k = 1, d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b7\u0018\u0000 \u00102\u00020\u0001:\u0005\u000f\u0010\u0011\u0012\u0013B!\b\u0004\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0001\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\u0002\u0010\bR\u0011\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0011\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000e\u0082\u0001\u0004\u0014\u0015\u0016\u0017\u00a8\u0006\u0018"}, d2 = {"Lcom/wuheng/smart/navigation/BottomNavItem;", "", "route", "", "labelResId", "", "icon", "Landroidx/compose/ui/graphics/vector/ImageVector;", "(Ljava/lang/String;ILandroidx/compose/ui/graphics/vector/ImageVector;)V", "getIcon", "()Landroidx/compose/ui/graphics/vector/ImageVector;", "getLabelResId", "()I", "getRoute", "()Ljava/lang/String;", "Climate", "Companion", "Home", "Profile", "Water", "Lcom/wuheng/smart/navigation/BottomNavItem$Climate;", "Lcom/wuheng/smart/navigation/BottomNavItem$Home;", "Lcom/wuheng/smart/navigation/BottomNavItem$Profile;", "Lcom/wuheng/smart/navigation/BottomNavItem$Water;", "app_debug"})
public abstract class BottomNavItem {
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String route = null;
    private final int labelResId = 0;
    @org.jetbrains.annotations.NotNull()
    private final androidx.compose.ui.graphics.vector.ImageVector icon = null;
    @org.jetbrains.annotations.NotNull()
    public static final com.wuheng.smart.navigation.BottomNavItem.Companion Companion = null;
    @org.jetbrains.annotations.NotNull()
    private static final java.util.List<com.wuheng.smart.navigation.BottomNavItem> items = null;
    
    private BottomNavItem(java.lang.String route, @androidx.annotation.StringRes()
    int labelResId, androidx.compose.ui.graphics.vector.ImageVector icon) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getRoute() {
        return null;
    }
    
    public final int getLabelResId() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.compose.ui.graphics.vector.ImageVector getIcon() {
        return null;
    }
    
    @kotlin.Metadata(mv = {1, 7, 1}, k = 1, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u00c7\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002\u00a8\u0006\u0003"}, d2 = {"Lcom/wuheng/smart/navigation/BottomNavItem$Home;", "Lcom/wuheng/smart/navigation/BottomNavItem;", "()V", "app_debug"})
    public static final class Home extends com.wuheng.smart.navigation.BottomNavItem {
        @org.jetbrains.annotations.NotNull()
        public static final com.wuheng.smart.navigation.BottomNavItem.Home INSTANCE = null;
        
        private Home() {
            super(null, 0, null);
        }
    }
    
    @kotlin.Metadata(mv = {1, 7, 1}, k = 1, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u00c7\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002\u00a8\u0006\u0003"}, d2 = {"Lcom/wuheng/smart/navigation/BottomNavItem$Climate;", "Lcom/wuheng/smart/navigation/BottomNavItem;", "()V", "app_debug"})
    public static final class Climate extends com.wuheng.smart.navigation.BottomNavItem {
        @org.jetbrains.annotations.NotNull()
        public static final com.wuheng.smart.navigation.BottomNavItem.Climate INSTANCE = null;
        
        private Climate() {
            super(null, 0, null);
        }
    }
    
    @kotlin.Metadata(mv = {1, 7, 1}, k = 1, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u00c7\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002\u00a8\u0006\u0003"}, d2 = {"Lcom/wuheng/smart/navigation/BottomNavItem$Water;", "Lcom/wuheng/smart/navigation/BottomNavItem;", "()V", "app_debug"})
    public static final class Water extends com.wuheng.smart.navigation.BottomNavItem {
        @org.jetbrains.annotations.NotNull()
        public static final com.wuheng.smart.navigation.BottomNavItem.Water INSTANCE = null;
        
        private Water() {
            super(null, 0, null);
        }
    }
    
    @kotlin.Metadata(mv = {1, 7, 1}, k = 1, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u00c7\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002\u00a8\u0006\u0003"}, d2 = {"Lcom/wuheng/smart/navigation/BottomNavItem$Profile;", "Lcom/wuheng/smart/navigation/BottomNavItem;", "()V", "app_debug"})
    public static final class Profile extends com.wuheng.smart.navigation.BottomNavItem {
        @org.jetbrains.annotations.NotNull()
        public static final com.wuheng.smart.navigation.BottomNavItem.Profile INSTANCE = null;
        
        private Profile() {
            super(null, 0, null);
        }
    }
    
    @kotlin.Metadata(mv = {1, 7, 1}, k = 1, d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u0017\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007\u00a8\u0006\b"}, d2 = {"Lcom/wuheng/smart/navigation/BottomNavItem$Companion;", "", "()V", "items", "", "Lcom/wuheng/smart/navigation/BottomNavItem;", "getItems", "()Ljava/util/List;", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.util.List<com.wuheng.smart.navigation.BottomNavItem> getItems() {
            return null;
        }
    }
}