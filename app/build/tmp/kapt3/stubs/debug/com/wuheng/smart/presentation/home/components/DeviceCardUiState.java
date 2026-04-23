package com.wuheng.smart.presentation.home.components;

import androidx.compose.animation.*;
import androidx.compose.animation.core.*;
import androidx.compose.foundation.layout.*;
import androidx.compose.material.icons.Icons;
import androidx.compose.material.icons.filled.*;
import androidx.compose.material3.*;
import androidx.compose.runtime.*;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.graphics.vector.ImageVector;
import androidx.compose.ui.text.font.FontWeight;
import androidx.compose.ui.tooling.preview.Preview;
import com.wuheng.smart.data.model.DeviceInfo;
import com.wuheng.smart.presentation.base.UiDataState;
import com.wuheng.smart.presentation.theme.*;

/**
 * 设备卡片 UI State
 * 用于首页设备列表展示
 */
@kotlin.Metadata(mv = {1, 7, 1}, k = 1, d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0007\n\u0002\b!\n\u0002\u0010\b\n\u0002\b\u0002\b\u0087\b\u0018\u00002\u00020\u0001Bg\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\b\b\u0002\u0010\u0007\u001a\u00020\b\u0012\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\n\u0012\b\b\u0002\u0010\u000b\u001a\u00020\b\u0012\b\b\u0002\u0010\f\u001a\u00020\u0003\u0012\b\b\u0002\u0010\r\u001a\u00020\b\u0012\b\b\u0002\u0010\u000e\u001a\u00020\b\u0012\n\b\u0002\u0010\u000f\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\u0002\u0010\u0010J\t\u0010\u001d\u001a\u00020\u0003H\u00c6\u0003J\u000b\u0010\u001e\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J\t\u0010\u001f\u001a\u00020\u0003H\u00c6\u0003J\t\u0010 \u001a\u00020\u0006H\u00c6\u0003J\t\u0010!\u001a\u00020\bH\u00c6\u0003J\u0010\u0010\"\u001a\u0004\u0018\u00010\nH\u00c6\u0003\u00a2\u0006\u0002\u0010\u0012J\t\u0010#\u001a\u00020\bH\u00c6\u0003J\t\u0010$\u001a\u00020\u0003H\u00c6\u0003J\t\u0010%\u001a\u00020\bH\u00c6\u0003J\t\u0010&\u001a\u00020\bH\u00c6\u0003Jv\u0010\'\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00062\b\b\u0002\u0010\u0007\u001a\u00020\b2\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\n2\b\b\u0002\u0010\u000b\u001a\u00020\b2\b\b\u0002\u0010\f\u001a\u00020\u00032\b\b\u0002\u0010\r\u001a\u00020\b2\b\b\u0002\u0010\u000e\u001a\u00020\b2\n\b\u0002\u0010\u000f\u001a\u0004\u0018\u00010\u0003H\u00c6\u0001\u00a2\u0006\u0002\u0010(J\u0013\u0010)\u001a\u00020\b2\b\u0010*\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010+\u001a\u00020,H\u00d6\u0001J\t\u0010-\u001a\u00020\u0003H\u00d6\u0001R\u0015\u0010\t\u001a\u0004\u0018\u00010\n\u00a2\u0006\n\n\u0002\u0010\u0013\u001a\u0004\b\u0011\u0010\u0012R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0015R\u0011\u0010\u0004\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0015R\u0011\u0010\u0005\u001a\u00020\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0018R\u0013\u0010\u000f\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u0015R\u0011\u0010\u000e\u001a\u00020\b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u001bR\u0011\u0010\u000b\u001a\u00020\b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\u001bR\u0011\u0010\r\u001a\u00020\b\u00a2\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u001bR\u0011\u0010\u0007\u001a\u00020\b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\u001bR\u0011\u0010\f\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u0015\u00a8\u0006."}, d2 = {"Lcom/wuheng/smart/presentation/home/components/DeviceCardUiState;", "", "deviceId", "", "deviceName", "deviceType", "Lcom/wuheng/smart/presentation/home/components/DeviceType;", "isPoweredOn", "", "currentTemp", "", "isCoolingMode", "roomName", "isOnline", "hasError", "errorMessage", "(Ljava/lang/String;Ljava/lang/String;Lcom/wuheng/smart/presentation/home/components/DeviceType;ZLjava/lang/Float;ZLjava/lang/String;ZZLjava/lang/String;)V", "getCurrentTemp", "()Ljava/lang/Float;", "Ljava/lang/Float;", "getDeviceId", "()Ljava/lang/String;", "getDeviceName", "getDeviceType", "()Lcom/wuheng/smart/presentation/home/components/DeviceType;", "getErrorMessage", "getHasError", "()Z", "getRoomName", "component1", "component10", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "(Ljava/lang/String;Ljava/lang/String;Lcom/wuheng/smart/presentation/home/components/DeviceType;ZLjava/lang/Float;ZLjava/lang/String;ZZLjava/lang/String;)Lcom/wuheng/smart/presentation/home/components/DeviceCardUiState;", "equals", "other", "hashCode", "", "toString", "app_debug"})
public final class DeviceCardUiState {
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String deviceId = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String deviceName = null;
    @org.jetbrains.annotations.NotNull()
    private final com.wuheng.smart.presentation.home.components.DeviceType deviceType = null;
    private final boolean isPoweredOn = false;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Float currentTemp = null;
    private final boolean isCoolingMode = false;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String roomName = null;
    private final boolean isOnline = false;
    private final boolean hasError = false;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String errorMessage = null;
    
    /**
     * 设备卡片 UI State
     * 用于首页设备列表展示
     */
    @org.jetbrains.annotations.NotNull()
    public final com.wuheng.smart.presentation.home.components.DeviceCardUiState copy(@org.jetbrains.annotations.NotNull()
    java.lang.String deviceId, @org.jetbrains.annotations.NotNull()
    java.lang.String deviceName, @org.jetbrains.annotations.NotNull()
    com.wuheng.smart.presentation.home.components.DeviceType deviceType, boolean isPoweredOn, @org.jetbrains.annotations.Nullable()
    java.lang.Float currentTemp, boolean isCoolingMode, @org.jetbrains.annotations.NotNull()
    java.lang.String roomName, boolean isOnline, boolean hasError, @org.jetbrains.annotations.Nullable()
    java.lang.String errorMessage) {
        return null;
    }
    
    /**
     * 设备卡片 UI State
     * 用于首页设备列表展示
     */
    @java.lang.Override()
    public boolean equals(@org.jetbrains.annotations.Nullable()
    java.lang.Object other) {
        return false;
    }
    
    /**
     * 设备卡片 UI State
     * 用于首页设备列表展示
     */
    @java.lang.Override()
    public int hashCode() {
        return 0;
    }
    
    /**
     * 设备卡片 UI State
     * 用于首页设备列表展示
     */
    @org.jetbrains.annotations.NotNull()
    @java.lang.Override()
    public java.lang.String toString() {
        return null;
    }
    
    public DeviceCardUiState(@org.jetbrains.annotations.NotNull()
    java.lang.String deviceId, @org.jetbrains.annotations.NotNull()
    java.lang.String deviceName, @org.jetbrains.annotations.NotNull()
    com.wuheng.smart.presentation.home.components.DeviceType deviceType, boolean isPoweredOn, @org.jetbrains.annotations.Nullable()
    java.lang.Float currentTemp, boolean isCoolingMode, @org.jetbrains.annotations.NotNull()
    java.lang.String roomName, boolean isOnline, boolean hasError, @org.jetbrains.annotations.Nullable()
    java.lang.String errorMessage) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component1() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getDeviceId() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component2() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getDeviceName() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.wuheng.smart.presentation.home.components.DeviceType component3() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.wuheng.smart.presentation.home.components.DeviceType getDeviceType() {
        return null;
    }
    
    public final boolean component4() {
        return false;
    }
    
    public final boolean isPoweredOn() {
        return false;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Float component5() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Float getCurrentTemp() {
        return null;
    }
    
    public final boolean component6() {
        return false;
    }
    
    public final boolean isCoolingMode() {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component7() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getRoomName() {
        return null;
    }
    
    public final boolean component8() {
        return false;
    }
    
    public final boolean isOnline() {
        return false;
    }
    
    public final boolean component9() {
        return false;
    }
    
    public final boolean getHasError() {
        return false;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component10() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getErrorMessage() {
        return null;
    }
}