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
 * 天气数据类
 */
@kotlin.Metadata(mv = {1, 7, 1}, k = 1, d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0012\n\u0002\u0010\u000b\n\u0002\b\u0004\b\u0087\b\u0018\u00002\u00020\u0001B-\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0003\u0012\u0006\u0010\u0007\u001a\u00020\u0003\u0012\u0006\u0010\b\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\tJ\t\u0010\u0011\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u0012\u001a\u00020\u0005H\u00c6\u0003J\t\u0010\u0013\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u0014\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u0015\u001a\u00020\u0003H\u00c6\u0003J;\u0010\u0016\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00032\b\b\u0002\u0010\u0007\u001a\u00020\u00032\b\b\u0002\u0010\b\u001a\u00020\u0003H\u00c6\u0001J\u0013\u0010\u0017\u001a\u00020\u00182\b\u0010\u0019\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010\u001a\u001a\u00020\u0003H\u00d6\u0001J\t\u0010\u001b\u001a\u00020\u0005H\u00d6\u0001R\u0011\u0010\u0006\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0011\u0010\b\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\f\u0010\u000bR\u0011\u0010\u0007\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000bR\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000bR\u0011\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010\u00a8\u0006\u001c"}, d2 = {"Lcom/wuheng/smart/data/location/WeatherInfo;", "", "temperature", "", "weather", "", "aqi", "pm25", "humidity", "(ILjava/lang/String;III)V", "getAqi", "()I", "getHumidity", "getPm25", "getTemperature", "getWeather", "()Ljava/lang/String;", "component1", "component2", "component3", "component4", "component5", "copy", "equals", "", "other", "hashCode", "toString", "app_debug"})
public final class WeatherInfo {
    private final int temperature = 0;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String weather = null;
    private final int aqi = 0;
    private final int pm25 = 0;
    private final int humidity = 0;
    
    /**
     * 天气数据类
     */
    @org.jetbrains.annotations.NotNull()
    public final com.wuheng.smart.data.location.WeatherInfo copy(int temperature, @org.jetbrains.annotations.NotNull()
    java.lang.String weather, int aqi, int pm25, int humidity) {
        return null;
    }
    
    /**
     * 天气数据类
     */
    @java.lang.Override()
    public boolean equals(@org.jetbrains.annotations.Nullable()
    java.lang.Object other) {
        return false;
    }
    
    /**
     * 天气数据类
     */
    @java.lang.Override()
    public int hashCode() {
        return 0;
    }
    
    /**
     * 天气数据类
     */
    @org.jetbrains.annotations.NotNull()
    @java.lang.Override()
    public java.lang.String toString() {
        return null;
    }
    
    public WeatherInfo(int temperature, @org.jetbrains.annotations.NotNull()
    java.lang.String weather, int aqi, int pm25, int humidity) {
        super();
    }
    
    public final int component1() {
        return 0;
    }
    
    public final int getTemperature() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component2() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getWeather() {
        return null;
    }
    
    public final int component3() {
        return 0;
    }
    
    public final int getAqi() {
        return 0;
    }
    
    public final int component4() {
        return 0;
    }
    
    public final int getPm25() {
        return 0;
    }
    
    public final int component5() {
        return 0;
    }
    
    public final int getHumidity() {
        return 0;
    }
}