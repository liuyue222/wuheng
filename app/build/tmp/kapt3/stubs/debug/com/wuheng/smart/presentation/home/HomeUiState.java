package com.wuheng.smart.presentation.home;

import com.wuheng.smart.data.model.*;
import com.wuheng.smart.data.network.ApiResult;
import com.wuheng.smart.data.network.TokenManager;
import com.wuheng.smart.data.repository.HomeRepository;
import com.wuheng.smart.presentation.base.BaseViewModel;
import com.wuheng.smart.presentation.base.UiDataState;
import com.wuheng.smart.presentation.home.components.DeviceCardUiState;
import com.wuheng.smart.presentation.home.components.DeviceType;
import com.wuheng.smart.presentation.home.components.WeatherModeSelectorUiState;
import dagger.hilt.android.lifecycle.HiltViewModel;
import kotlinx.coroutines.flow.SharedFlow;
import kotlinx.coroutines.flow.StateFlow;
import javax.inject.Inject;

/**
 * 首页统一的UI State
 * 用于新版Layout架构
 */
@kotlin.Metadata(mv = {1, 7, 1}, k = 1, d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b,\b\u0087\b\u0018\u00002\u00020\u0001B\u00ad\u0001\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0007\u001a\u00020\b\u0012\b\b\u0002\u0010\t\u001a\u00020\u0005\u0012\b\b\u0002\u0010\n\u001a\u00020\b\u0012\b\b\u0002\u0010\u000b\u001a\u00020\b\u0012\b\b\u0002\u0010\f\u001a\u00020\b\u0012\b\b\u0002\u0010\r\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u000e\u001a\u00020\u000f\u0012\b\b\u0002\u0010\u0010\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0011\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0012\u001a\u00020\b\u0012\b\b\u0002\u0010\u0013\u001a\u00020\u0005\u0012\u000e\b\u0002\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00160\u0015\u0012\b\b\u0002\u0010\u0017\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0018J\t\u0010-\u001a\u00020\u0003H\u00c6\u0003J\t\u0010.\u001a\u00020\u000fH\u00c6\u0003J\t\u0010/\u001a\u00020\u0005H\u00c6\u0003J\t\u00100\u001a\u00020\u0005H\u00c6\u0003J\t\u00101\u001a\u00020\bH\u00c6\u0003J\t\u00102\u001a\u00020\u0005H\u00c6\u0003J\u000f\u00103\u001a\b\u0012\u0004\u0012\u00020\u00160\u0015H\u00c6\u0003J\t\u00104\u001a\u00020\u0003H\u00c6\u0003J\u000b\u00105\u001a\u0004\u0018\u00010\u0005H\u00c6\u0003J\t\u00106\u001a\u00020\u0005H\u00c6\u0003J\t\u00107\u001a\u00020\bH\u00c6\u0003J\t\u00108\u001a\u00020\u0005H\u00c6\u0003J\t\u00109\u001a\u00020\bH\u00c6\u0003J\t\u0010:\u001a\u00020\bH\u00c6\u0003J\t\u0010;\u001a\u00020\bH\u00c6\u0003J\t\u0010<\u001a\u00020\u0005H\u00c6\u0003J\u00b1\u0001\u0010=\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00052\b\b\u0002\u0010\u0007\u001a\u00020\b2\b\b\u0002\u0010\t\u001a\u00020\u00052\b\b\u0002\u0010\n\u001a\u00020\b2\b\b\u0002\u0010\u000b\u001a\u00020\b2\b\b\u0002\u0010\f\u001a\u00020\b2\b\b\u0002\u0010\r\u001a\u00020\u00052\b\b\u0002\u0010\u000e\u001a\u00020\u000f2\b\b\u0002\u0010\u0010\u001a\u00020\u00052\b\b\u0002\u0010\u0011\u001a\u00020\u00052\b\b\u0002\u0010\u0012\u001a\u00020\b2\b\b\u0002\u0010\u0013\u001a\u00020\u00052\u000e\b\u0002\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00160\u00152\b\b\u0002\u0010\u0017\u001a\u00020\u0003H\u00c6\u0001J\u0013\u0010>\u001a\u00020\u00032\b\u0010?\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010@\u001a\u00020\bH\u00d6\u0001J\t\u0010A\u001a\u00020\u0005H\u00d6\u0001R\u0011\u0010\n\u001a\u00020\b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u001aR\u0011\u0010\u0012\u001a\u00020\b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u001aR\u0011\u0010\u000e\u001a\u00020\u000f\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u001dR\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\u001fR\u0011\u0010\u0011\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b \u0010\u001fR\u0011\u0010\u0010\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b!\u0010\u001fR\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0002\u0010\"R\u0011\u0010\u0006\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b#\u0010\u001fR\u0011\u0010\f\u001a\u00020\b\u00a2\u0006\b\n\u0000\u001a\u0004\b$\u0010\u001aR\u0011\u0010\u0007\u001a\u00020\b\u00a2\u0006\b\n\u0000\u001a\u0004\b%\u0010\u001aR\u0011\u0010\u000b\u001a\u00020\b\u00a2\u0006\b\n\u0000\u001a\u0004\b&\u0010\u001aR\u0011\u0010\u0017\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\'\u0010\"R\u0011\u0010\r\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b(\u0010\u001fR\u0017\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00160\u0015\u00a2\u0006\b\n\u0000\u001a\u0004\b)\u0010*R\u0011\u0010\u0013\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b+\u0010\u001fR\u0011\u0010\t\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b,\u0010\u001f\u00a8\u0006B"}, d2 = {"Lcom/wuheng/smart/presentation/home/HomeUiState;", "", "isLoading", "", "errorMessage", "", "location", "outdoorTemp", "", "weather", "aqi", "pm25", "outdoorHumidity", "residenceName", "currentMode", "Lcom/wuheng/smart/presentation/home/ClimateMode;", "indoorTemp", "indoorHumidity", "co2", "tovc", "scenes", "", "Lcom/wuheng/smart/presentation/home/SceneItem;", "preheatPreheatEnabled", "(ZLjava/lang/String;Ljava/lang/String;ILjava/lang/String;IIILjava/lang/String;Lcom/wuheng/smart/presentation/home/ClimateMode;Ljava/lang/String;Ljava/lang/String;ILjava/lang/String;Ljava/util/List;Z)V", "getAqi", "()I", "getCo2", "getCurrentMode", "()Lcom/wuheng/smart/presentation/home/ClimateMode;", "getErrorMessage", "()Ljava/lang/String;", "getIndoorHumidity", "getIndoorTemp", "()Z", "getLocation", "getOutdoorHumidity", "getOutdoorTemp", "getPm25", "getPreheatPreheatEnabled", "getResidenceName", "getScenes", "()Ljava/util/List;", "getTovc", "getWeather", "component1", "component10", "component11", "component12", "component13", "component14", "component15", "component16", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "equals", "other", "hashCode", "toString", "app_debug"})
public final class HomeUiState {
    private final boolean isLoading = false;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String errorMessage = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String location = null;
    private final int outdoorTemp = 0;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String weather = null;
    private final int aqi = 0;
    private final int pm25 = 0;
    private final int outdoorHumidity = 0;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String residenceName = null;
    @org.jetbrains.annotations.NotNull()
    private final com.wuheng.smart.presentation.home.ClimateMode currentMode = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String indoorTemp = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String indoorHumidity = null;
    private final int co2 = 0;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String tovc = null;
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<com.wuheng.smart.presentation.home.SceneItem> scenes = null;
    private final boolean preheatPreheatEnabled = false;
    
    /**
     * 首页统一的UI State
     * 用于新版Layout架构
     */
    @org.jetbrains.annotations.NotNull()
    public final com.wuheng.smart.presentation.home.HomeUiState copy(boolean isLoading, @org.jetbrains.annotations.Nullable()
    java.lang.String errorMessage, @org.jetbrains.annotations.NotNull()
    java.lang.String location, int outdoorTemp, @org.jetbrains.annotations.NotNull()
    java.lang.String weather, int aqi, int pm25, int outdoorHumidity, @org.jetbrains.annotations.NotNull()
    java.lang.String residenceName, @org.jetbrains.annotations.NotNull()
    com.wuheng.smart.presentation.home.ClimateMode currentMode, @org.jetbrains.annotations.NotNull()
    java.lang.String indoorTemp, @org.jetbrains.annotations.NotNull()
    java.lang.String indoorHumidity, int co2, @org.jetbrains.annotations.NotNull()
    java.lang.String tovc, @org.jetbrains.annotations.NotNull()
    java.util.List<com.wuheng.smart.presentation.home.SceneItem> scenes, boolean preheatPreheatEnabled) {
        return null;
    }
    
    /**
     * 首页统一的UI State
     * 用于新版Layout架构
     */
    @java.lang.Override()
    public boolean equals(@org.jetbrains.annotations.Nullable()
    java.lang.Object other) {
        return false;
    }
    
    /**
     * 首页统一的UI State
     * 用于新版Layout架构
     */
    @java.lang.Override()
    public int hashCode() {
        return 0;
    }
    
    /**
     * 首页统一的UI State
     * 用于新版Layout架构
     */
    @org.jetbrains.annotations.NotNull()
    @java.lang.Override()
    public java.lang.String toString() {
        return null;
    }
    
    public HomeUiState() {
        super();
    }
    
    public HomeUiState(boolean isLoading, @org.jetbrains.annotations.Nullable()
    java.lang.String errorMessage, @org.jetbrains.annotations.NotNull()
    java.lang.String location, int outdoorTemp, @org.jetbrains.annotations.NotNull()
    java.lang.String weather, int aqi, int pm25, int outdoorHumidity, @org.jetbrains.annotations.NotNull()
    java.lang.String residenceName, @org.jetbrains.annotations.NotNull()
    com.wuheng.smart.presentation.home.ClimateMode currentMode, @org.jetbrains.annotations.NotNull()
    java.lang.String indoorTemp, @org.jetbrains.annotations.NotNull()
    java.lang.String indoorHumidity, int co2, @org.jetbrains.annotations.NotNull()
    java.lang.String tovc, @org.jetbrains.annotations.NotNull()
    java.util.List<com.wuheng.smart.presentation.home.SceneItem> scenes, boolean preheatPreheatEnabled) {
        super();
    }
    
    public final boolean component1() {
        return false;
    }
    
    public final boolean isLoading() {
        return false;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component2() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getErrorMessage() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component3() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getLocation() {
        return null;
    }
    
    public final int component4() {
        return 0;
    }
    
    public final int getOutdoorTemp() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component5() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getWeather() {
        return null;
    }
    
    public final int component6() {
        return 0;
    }
    
    public final int getAqi() {
        return 0;
    }
    
    public final int component7() {
        return 0;
    }
    
    public final int getPm25() {
        return 0;
    }
    
    public final int component8() {
        return 0;
    }
    
    public final int getOutdoorHumidity() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component9() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getResidenceName() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.wuheng.smart.presentation.home.ClimateMode component10() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.wuheng.smart.presentation.home.ClimateMode getCurrentMode() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component11() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getIndoorTemp() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component12() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getIndoorHumidity() {
        return null;
    }
    
    public final int component13() {
        return 0;
    }
    
    public final int getCo2() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component14() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getTovc() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.wuheng.smart.presentation.home.SceneItem> component15() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.wuheng.smart.presentation.home.SceneItem> getScenes() {
        return null;
    }
    
    public final boolean component16() {
        return false;
    }
    
    public final boolean getPreheatPreheatEnabled() {
        return false;
    }
}