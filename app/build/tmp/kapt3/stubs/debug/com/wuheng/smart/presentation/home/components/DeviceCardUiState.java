package com.wuheng.smart.presentation.home.components;

import androidx.compose.animation.core.Spring;
import androidx.compose.foundation.layout.*;
import androidx.compose.material3.*;
import androidx.compose.runtime.*;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.graphics.ColorFilter;
import androidx.compose.ui.text.font.FontWeight;
import androidx.compose.ui.tooling.preview.Preview;
import com.wuheng.smart.R;
import com.wuheng.smart.data.model.DeviceType;
import com.wuheng.smart.presentation.theme.*;

/**
 * 设备控制卡片组件
 *
 * 布局结构分析（基于设计图）:
 * - 外层: 圆角矩形玻璃拟态卡片 (16dp圆角)
 * - 顶部区域: 设备图标 + 设备名称 + 开关状态
 * - 中间区域: 温度显示 + 模式图标(太阳/雪花)
 * - 底部区域: 自定义开关控件 (参考kaiguan-guan-3.png样式)
 *
 * 切图资源引用:
 *  - kongtiao.png -> ic_air_conditioner (空调图标)
 *  - kaiguan-guan-3.png -> ic_switch_on (开关开启态)
 *  - 太阳.png -> ic_sun (制热/日间模式)
 *  - 雪花(1).png -> ic_snowflake (制冷模式)
 */
@kotlin.Metadata(mv = {1, 7, 1}, k = 1, d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u001c\n\u0002\u0010\b\n\u0002\b\u0002\b\u0087\b\u0018\u00002\u00020\u0001B_\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0005\u001a\u00020\u0006\u0012\b\b\u0002\u0010\u0007\u001a\u00020\b\u0012\b\b\u0002\u0010\t\u001a\u00020\n\u0012\b\b\u0002\u0010\u000b\u001a\u00020\n\u0012\b\b\u0002\u0010\f\u001a\u00020\b\u0012\b\b\u0002\u0010\r\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u000e\u001a\u00020\b\u00a2\u0006\u0002\u0010\u000fJ\t\u0010\u001a\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u001b\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u001c\u001a\u00020\u0006H\u00c6\u0003J\t\u0010\u001d\u001a\u00020\bH\u00c6\u0003J\t\u0010\u001e\u001a\u00020\nH\u00c6\u0003J\t\u0010\u001f\u001a\u00020\nH\u00c6\u0003J\t\u0010 \u001a\u00020\bH\u00c6\u0003J\t\u0010!\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\"\u001a\u00020\bH\u00c6\u0003Jc\u0010#\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00062\b\b\u0002\u0010\u0007\u001a\u00020\b2\b\b\u0002\u0010\t\u001a\u00020\n2\b\b\u0002\u0010\u000b\u001a\u00020\n2\b\b\u0002\u0010\f\u001a\u00020\b2\b\b\u0002\u0010\r\u001a\u00020\u00032\b\b\u0002\u0010\u000e\u001a\u00020\bH\u00c6\u0001J\u0013\u0010$\u001a\u00020\b2\b\u0010%\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010&\u001a\u00020\'H\u00d6\u0001J\t\u0010(\u001a\u00020\u0003H\u00d6\u0001R\u0011\u0010\t\u001a\u00020\n\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013R\u0011\u0010\u0004\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0013R\u0011\u0010\u0005\u001a\u00020\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0016R\u0011\u0010\f\u001a\u00020\b\u00a2\u0006\b\n\u0000\u001a\u0004\b\f\u0010\u0017R\u0011\u0010\u000e\u001a\u00020\b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u0017R\u0011\u0010\u0007\u001a\u00020\b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\u0017R\u0011\u0010\r\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0013R\u0011\u0010\u000b\u001a\u00020\n\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u0011\u00a8\u0006)"}, d2 = {"Lcom/wuheng/smart/presentation/home/components/DeviceCardUiState;", "", "deviceId", "", "deviceName", "deviceType", "Lcom/wuheng/smart/data/model/DeviceType;", "isPoweredOn", "", "currentTemp", "", "targetTemp", "isCoolingMode", "roomName", "isOnline", "(Ljava/lang/String;Ljava/lang/String;Lcom/wuheng/smart/data/model/DeviceType;ZFFZLjava/lang/String;Z)V", "getCurrentTemp", "()F", "getDeviceId", "()Ljava/lang/String;", "getDeviceName", "getDeviceType", "()Lcom/wuheng/smart/data/model/DeviceType;", "()Z", "getRoomName", "getTargetTemp", "component1", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "equals", "other", "hashCode", "", "toString", "app_debug"})
public final class DeviceCardUiState {
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String deviceId = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String deviceName = null;
    @org.jetbrains.annotations.NotNull()
    private final com.wuheng.smart.data.model.DeviceType deviceType = null;
    private final boolean isPoweredOn = false;
    private final float currentTemp = 0.0F;
    private final float targetTemp = 0.0F;
    private final boolean isCoolingMode = false;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String roomName = null;
    private final boolean isOnline = false;
    
    /**
     * 设备控制卡片组件
     *
     * 布局结构分析（基于设计图）:
     * - 外层: 圆角矩形玻璃拟态卡片 (16dp圆角)
     * - 顶部区域: 设备图标 + 设备名称 + 开关状态
     * - 中间区域: 温度显示 + 模式图标(太阳/雪花)
     * - 底部区域: 自定义开关控件 (参考kaiguan-guan-3.png样式)
     *
     * 切图资源引用:
     *  - kongtiao.png -> ic_air_conditioner (空调图标)
     *  - kaiguan-guan-3.png -> ic_switch_on (开关开启态)
     *  - 太阳.png -> ic_sun (制热/日间模式)
     *  - 雪花(1).png -> ic_snowflake (制冷模式)
     */
    @org.jetbrains.annotations.NotNull()
    public final com.wuheng.smart.presentation.home.components.DeviceCardUiState copy(@org.jetbrains.annotations.NotNull()
    java.lang.String deviceId, @org.jetbrains.annotations.NotNull()
    java.lang.String deviceName, @org.jetbrains.annotations.NotNull()
    com.wuheng.smart.data.model.DeviceType deviceType, boolean isPoweredOn, float currentTemp, float targetTemp, boolean isCoolingMode, @org.jetbrains.annotations.NotNull()
    java.lang.String roomName, boolean isOnline) {
        return null;
    }
    
    /**
     * 设备控制卡片组件
     *
     * 布局结构分析（基于设计图）:
     * - 外层: 圆角矩形玻璃拟态卡片 (16dp圆角)
     * - 顶部区域: 设备图标 + 设备名称 + 开关状态
     * - 中间区域: 温度显示 + 模式图标(太阳/雪花)
     * - 底部区域: 自定义开关控件 (参考kaiguan-guan-3.png样式)
     *
     * 切图资源引用:
     *  - kongtiao.png -> ic_air_conditioner (空调图标)
     *  - kaiguan-guan-3.png -> ic_switch_on (开关开启态)
     *  - 太阳.png -> ic_sun (制热/日间模式)
     *  - 雪花(1).png -> ic_snowflake (制冷模式)
     */
    @java.lang.Override()
    public boolean equals(@org.jetbrains.annotations.Nullable()
    java.lang.Object other) {
        return false;
    }
    
    /**
     * 设备控制卡片组件
     *
     * 布局结构分析（基于设计图）:
     * - 外层: 圆角矩形玻璃拟态卡片 (16dp圆角)
     * - 顶部区域: 设备图标 + 设备名称 + 开关状态
     * - 中间区域: 温度显示 + 模式图标(太阳/雪花)
     * - 底部区域: 自定义开关控件 (参考kaiguan-guan-3.png样式)
     *
     * 切图资源引用:
     *  - kongtiao.png -> ic_air_conditioner (空调图标)
     *  - kaiguan-guan-3.png -> ic_switch_on (开关开启态)
     *  - 太阳.png -> ic_sun (制热/日间模式)
     *  - 雪花(1).png -> ic_snowflake (制冷模式)
     */
    @java.lang.Override()
    public int hashCode() {
        return 0;
    }
    
    /**
     * 设备控制卡片组件
     *
     * 布局结构分析（基于设计图）:
     * - 外层: 圆角矩形玻璃拟态卡片 (16dp圆角)
     * - 顶部区域: 设备图标 + 设备名称 + 开关状态
     * - 中间区域: 温度显示 + 模式图标(太阳/雪花)
     * - 底部区域: 自定义开关控件 (参考kaiguan-guan-3.png样式)
     *
     * 切图资源引用:
     *  - kongtiao.png -> ic_air_conditioner (空调图标)
     *  - kaiguan-guan-3.png -> ic_switch_on (开关开启态)
     *  - 太阳.png -> ic_sun (制热/日间模式)
     *  - 雪花(1).png -> ic_snowflake (制冷模式)
     */
    @org.jetbrains.annotations.NotNull()
    @java.lang.Override()
    public java.lang.String toString() {
        return null;
    }
    
    public DeviceCardUiState() {
        super();
    }
    
    public DeviceCardUiState(@org.jetbrains.annotations.NotNull()
    java.lang.String deviceId, @org.jetbrains.annotations.NotNull()
    java.lang.String deviceName, @org.jetbrains.annotations.NotNull()
    com.wuheng.smart.data.model.DeviceType deviceType, boolean isPoweredOn, float currentTemp, float targetTemp, boolean isCoolingMode, @org.jetbrains.annotations.NotNull()
    java.lang.String roomName, boolean isOnline) {
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
    public final com.wuheng.smart.data.model.DeviceType component3() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.wuheng.smart.data.model.DeviceType getDeviceType() {
        return null;
    }
    
    public final boolean component4() {
        return false;
    }
    
    public final boolean isPoweredOn() {
        return false;
    }
    
    public final float component5() {
        return 0.0F;
    }
    
    public final float getCurrentTemp() {
        return 0.0F;
    }
    
    public final float component6() {
        return 0.0F;
    }
    
    public final float getTargetTemp() {
        return 0.0F;
    }
    
    public final boolean component7() {
        return false;
    }
    
    public final boolean isCoolingMode() {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component8() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getRoomName() {
        return null;
    }
    
    public final boolean component9() {
        return false;
    }
    
    public final boolean isOnline() {
        return false;
    }
}