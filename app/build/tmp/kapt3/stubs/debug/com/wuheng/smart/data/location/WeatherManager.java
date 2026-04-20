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
 * 天气管理器（模拟，实际应该调用天气API）
 */
@kotlin.Metadata(mv = {1, 7, 1}, k = 1, d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0006\n\u0002\b\u0003\b\u0007\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J!\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u0006H\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\b\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006\t"}, d2 = {"Lcom/wuheng/smart/data/location/WeatherManager;", "", "()V", "getWeather", "Lcom/wuheng/smart/data/location/WeatherInfo;", "latitude", "", "longitude", "(DDLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_debug"})
public final class WeatherManager {
    
    public WeatherManager() {
        super();
    }
    
    /**
     * 获取天气信息（模拟数据，实际应该调用天气API）
     */
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object getWeather(double latitude, double longitude, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.wuheng.smart.data.location.WeatherInfo> continuation) {
        return null;
    }
}