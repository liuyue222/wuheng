package com.wuheng.smart.data.location;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Geocoder;
import android.location.Location;
import android.os.Looper;
import androidx.core.content.ContextCompat;
import com.google.android.gms.location.*;
import kotlinx.coroutines.*;
import timber.log.Timber;
import java.util.Locale;

/**
 * 定位管理器
 * 用于获取用户当前位置和地址信息
 */
@kotlin.Metadata(mv = {1, 7, 1}, k = 1, d1 = {"\u0000D\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0007\u0018\u0000 \u00192\u00020\u0001:\u0001\u0019B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0019\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u000bJ\u0013\u0010\f\u001a\u0004\u0018\u00010\nH\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\rJ\b\u0010\u000e\u001a\u00020\nH\u0002J\u001d\u0010\u000f\u001a\u000e\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\n0\u0010H\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\rJ\n\u0010\u0011\u001a\u0004\u0018\u00010\nH\u0002J\u0006\u0010\u0012\u001a\u00020\u0013J\u0010\u0010\u0014\u001a\u00020\u00132\u0006\u0010\t\u001a\u00020\nH\u0002J\u0018\u0010\u0015\u001a\u00020\u00162\u000e\u0010\u0017\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\n0\u0018H\u0002R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006\u001a"}, d2 = {"Lcom/wuheng/smart/data/location/LocationManager;", "", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "fusedLocationClient", "Lcom/google/android/gms/location/FusedLocationProviderClient;", "getAddressFromLocation", "", "location", "Landroid/location/Location;", "(Landroid/location/Location;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getCurrentLocation", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getDefaultLocation", "getFormattedLocation", "Lkotlin/Pair;", "getLocationViaSystemManagerAsync", "hasLocationPermission", "", "isLocationRecent", "requestNewLocation", "", "continuation", "Lkotlin/coroutines/Continuation;", "Companion", "app_debug"})
public final class LocationManager {
    private final android.content.Context context = null;
    @org.jetbrains.annotations.NotNull()
    public static final com.wuheng.smart.data.location.LocationManager.Companion Companion = null;
    private static final long LOCATION_TIMEOUT_MS = 10000L;
    private static final java.lang.String DEFAULT_CITY = "\u676d\u5dde\u5e02";
    private static final java.lang.String DEFAULT_DISTRICT = "\u4f59\u676d\u533a";
    private static final java.lang.String DEFAULT_ADDRESS = "\u676d\u5dde\u5e02 \u00b7 \u4f59\u676d\u533a";
    private static final double DEFAULT_LATITUDE = 30.2741;
    private static final double DEFAULT_LONGITUDE = 120.1551;
    private final com.google.android.gms.location.FusedLocationProviderClient fusedLocationClient = null;
    
    public LocationManager(@org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        super();
    }
    
    /**
     * 检查是否有定位权限
     */
    public final boolean hasLocationPermission() {
        return false;
    }
    
    /**
     * 获取当前位置（GMS优先，系统LocationManager兜底）
     * GMS不可用时（常见于国产手机），自动fallback到系统LocationManager
     * 带超时机制，超时后返回默认位置
     */
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object getCurrentLocation(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super android.location.Location> continuation) {
        return null;
    }
    
    /**
     * 通过系统LocationManager同步获取位置（非挂起）
     * 优先GPS，其次Network，最后lastKnown
     */
    private final android.location.Location getLocationViaSystemManagerAsync() {
        return null;
    }
    
    /**
     * 判断位置是否在最近2分钟内获取的
     */
    private final boolean isLocationRecent(android.location.Location location) {
        return false;
    }
    
    /**
     * 获取默认位置（杭州市中心）
     */
    private final android.location.Location getDefaultLocation() {
        return null;
    }
    
    /**
     * 请求新位置
     */
    private final void requestNewLocation(kotlin.coroutines.Continuation<? super android.location.Location> continuation) {
    }
    
    /**
     * 根据位置获取地址信息
     */
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object getAddressFromLocation(@org.jetbrains.annotations.NotNull()
    android.location.Location location, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.String> continuation) {
        return null;
    }
    
    /**
     * 获取格式化的位置信息
     * 始终返回有效地址，不会返回null或"定位失败"
     */
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object getFormattedLocation(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Pair<java.lang.String, ? extends android.location.Location>> continuation) {
        return null;
    }
    
    @kotlin.Metadata(mv = {1, 7, 1}, k = 1, d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u0006\n\u0002\b\u0002\n\u0002\u0010\t\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\bX\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\f"}, d2 = {"Lcom/wuheng/smart/data/location/LocationManager$Companion;", "", "()V", "DEFAULT_ADDRESS", "", "DEFAULT_CITY", "DEFAULT_DISTRICT", "DEFAULT_LATITUDE", "", "DEFAULT_LONGITUDE", "LOCATION_TIMEOUT_MS", "", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
    }
}