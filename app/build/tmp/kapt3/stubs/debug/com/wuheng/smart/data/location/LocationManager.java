package com.wuheng.smart.data.location;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Geocoder;
import android.location.Location;
import android.os.Looper;
import androidx.core.content.ContextCompat;
import com.google.android.gms.location.*;
import timber.log.Timber;
import java.util.Locale;

/**
 * 定位管理器
 * 用于获取用户当前位置和地址信息
 */
@kotlin.Metadata(mv = {1, 7, 1}, k = 1, d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0019\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u000bJ\u0013\u0010\f\u001a\u0004\u0018\u00010\nH\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\rJ\u001f\u0010\u000e\u001a\u0010\u0012\u0004\u0012\u00020\b\u0012\u0006\u0012\u0004\u0018\u00010\n0\u000fH\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\rJ\u0006\u0010\u0010\u001a\u00020\u0011J\u0018\u0010\u0012\u001a\u00020\u00132\u000e\u0010\u0014\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\n0\u0015H\u0002R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006\u0016"}, d2 = {"Lcom/wuheng/smart/data/location/LocationManager;", "", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "fusedLocationClient", "Lcom/google/android/gms/location/FusedLocationProviderClient;", "getAddressFromLocation", "", "location", "Landroid/location/Location;", "(Landroid/location/Location;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getCurrentLocation", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getFormattedLocation", "Lkotlin/Pair;", "hasLocationPermission", "", "requestNewLocation", "", "continuation", "Lkotlin/coroutines/Continuation;", "app_debug"})
public final class LocationManager {
    private final android.content.Context context = null;
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
     * 获取当前位置（如果lastLocation为空，则请求新位置）
     */
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object getCurrentLocation(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super android.location.Location> continuation) {
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
     */
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object getFormattedLocation(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Pair<java.lang.String, ? extends android.location.Location>> continuation) {
        return null;
    }
}