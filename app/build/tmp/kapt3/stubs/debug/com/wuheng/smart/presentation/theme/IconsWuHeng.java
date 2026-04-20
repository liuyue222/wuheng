package com.wuheng.smart.presentation.theme;

import androidx.compose.ui.graphics.SolidColor;
import androidx.compose.ui.graphics.StrokeCap;
import androidx.compose.ui.graphics.StrokeJoin;
import androidx.compose.ui.graphics.vector.ImageVector;

/**
 * 五恒系统专用图标集 - 自定义矢量图标库
 *
 * 包含所有UI界面使用的自定义图标：
 * - 底部导航栏图标 (Home/Climate/Water/Profile)
 * - 功能操作图标 (Menu/Close/Settings/Plus/Minus)
 * - 导航箭头 (ChevronRight/ChevronLeft)
 * - 设备状态图标 (Power/Fan/Flame/Shield/FreshAir/Droplet)
 * - 环境显示图标 (TemperatureHigh/TemperatureLow/Sun/Moon/Cloud)
 * - 状态指示图标 (CheckCircle)
 * - 新增：导航和选择器图标 (Home/List/ChevronDown)
 *
 * 使用示例：
 * ```kotlin
 * Icon(
 *    imageVector = IconsWuHeng.Climate,
 *    contentDescription = "冷暖舒适"
 * )
 * ```
 */
@kotlin.Metadata(mv = {1, 7, 1}, k = 1, d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b5\b\u00c7\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u0011\u0010\u0003\u001a\u00020\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u0011\u0010\u0007\u001a\u00020\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\b\u0010\u0006R\u0011\u0010\t\u001a\u00020\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u0006R\u0011\u0010\u000b\u001a\u00020\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\f\u0010\u0006R\u0011\u0010\r\u001a\u00020\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u0006R\u0011\u0010\u000f\u001a\u00020\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0006R\u0011\u0010\u0011\u001a\u00020\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0006R\u0011\u0010\u0013\u001a\u00020\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0006R\u0011\u0010\u0015\u001a\u00020\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0006R\u0011\u0010\u0017\u001a\u00020\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0006R\u0011\u0010\u0019\u001a\u00020\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u0006R\u0011\u0010\u001b\u001a\u00020\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u0006R\u0011\u0010\u001d\u001a\u00020\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\u0006R\u0011\u0010\u001f\u001a\u00020\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b \u0010\u0006R\u0011\u0010!\u001a\u00020\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\"\u0010\u0006R\u0011\u0010#\u001a\u00020\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b$\u0010\u0006R\u0011\u0010%\u001a\u00020\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b&\u0010\u0006R\u0011\u0010\'\u001a\u00020\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b(\u0010\u0006R\u0011\u0010)\u001a\u00020\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b*\u0010\u0006R\u0011\u0010+\u001a\u00020\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b,\u0010\u0006R\u0011\u0010-\u001a\u00020\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b.\u0010\u0006R\u0011\u0010/\u001a\u00020\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b0\u0010\u0006R\u0011\u00101\u001a\u00020\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b2\u0010\u0006R\u0011\u00103\u001a\u00020\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b4\u0010\u0006R\u0011\u00105\u001a\u00020\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b6\u0010\u0006R\u0011\u00107\u001a\u00020\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b8\u0010\u0006\u00a8\u00069"}, d2 = {"Lcom/wuheng/smart/presentation/theme/IconsWuHeng;", "", "()V", "CheckCircle", "Landroidx/compose/ui/graphics/vector/ImageVector;", "getCheckCircle", "()Landroidx/compose/ui/graphics/vector/ImageVector;", "ChevronDown", "getChevronDown", "ChevronLeft", "getChevronLeft", "ChevronRight", "getChevronRight", "Climate", "getClimate", "Close", "getClose", "Cloud", "getCloud", "Droplet", "getDroplet", "Fan", "getFan", "Flame", "getFlame", "FreshAir", "getFreshAir", "Home", "getHome", "List", "getList", "LocationPin", "getLocationPin", "Menu", "getMenu", "Minus", "getMinus", "Moon", "getMoon", "Plus", "getPlus", "Power", "getPower", "Profile", "getProfile", "Settings", "getSettings", "Shield", "getShield", "Sun", "getSun", "TemperatureHigh", "getTemperatureHigh", "TemperatureLow", "getTemperatureLow", "Water", "getWater", "app_debug"})
public final class IconsWuHeng {
    @org.jetbrains.annotations.NotNull()
    public static final com.wuheng.smart.presentation.theme.IconsWuHeng INSTANCE = null;
    
    /**
     * 冷暖舒适图标 - 用于底部导航栏"冷暖舒适"Tab
     */
    @org.jetbrains.annotations.NotNull()
    private static final androidx.compose.ui.graphics.vector.ImageVector Climate = null;
    
    /**
     * 水系统图标 - 用于底部导航栏"水系统"Tab
     */
    @org.jetbrains.annotations.NotNull()
    private static final androidx.compose.ui.graphics.vector.ImageVector Water = null;
    
    /**
     * 我的页面图标 - 用于底部导航栏"我的"Tab
     */
    @org.jetbrains.annotations.NotNull()
    private static final androidx.compose.ui.graphics.vector.ImageVector Profile = null;
    
    /**
     * 菜单图标 - 用于侧边菜单或更多选项
     */
    @org.jetbrains.annotations.NotNull()
    private static final androidx.compose.ui.graphics.vector.ImageVector Menu = null;
    
    /**
     * 关闭图标 - 用于关闭对话框或弹窗
     */
    @org.jetbrains.annotations.NotNull()
    private static final androidx.compose.ui.graphics.vector.ImageVector Close = null;
    
    /**
     * 设置图标 - 用于进入设置页面
     */
    @org.jetbrains.annotations.NotNull()
    private static final androidx.compose.ui.graphics.vector.ImageVector Settings = null;
    
    /**
     * 加号图标 - 用于添加新项目或增加数值
     */
    @org.jetbrains.annotations.NotNull()
    private static final androidx.compose.ui.graphics.vector.ImageVector Plus = null;
    
    /**
     * 减号图标 - 用于减少数值
     */
    @org.jetbrains.annotations.NotNull()
    private static final androidx.compose.ui.graphics.vector.ImageVector Minus = null;
    
    /**
     * 右箭头图标 - 用于导航到下一级页面
     */
    @org.jetbrains.annotations.NotNull()
    private static final androidx.compose.ui.graphics.vector.ImageVector ChevronRight = null;
    
    /**
     * 左箭头图标 - 用于返回上一级页面
     */
    @org.jetbrains.annotations.NotNull()
    private static final androidx.compose.ui.graphics.vector.ImageVector ChevronLeft = null;
    
    /**
     * 电源图标 - 用于设备开关控制
     */
    @org.jetbrains.annotations.NotNull()
    private static final androidx.compose.ui.graphics.vector.ImageVector Power = null;
    
    /**
     * 风扇图标 - 用于风速控制
     */
    @org.jetbrains.annotations.NotNull()
    private static final androidx.compose.ui.graphics.vector.ImageVector Fan = null;
    
    /**
     * 火焰图标 - 用于供暖模式或温度加热
     */
    @org.jetbrains.annotations.NotNull()
    private static final androidx.compose.ui.graphics.vector.ImageVector Flame = null;
    
    /**
     * 盾牌图标 - 用于安全保护功能（如热力杀菌）
     */
    @org.jetbrains.annotations.NotNull()
    private static final androidx.compose.ui.graphics.vector.ImageVector Shield = null;
    
    /**
     * 新风图标 - 用于新风系统控制
     */
    @org.jetbrains.annotations.NotNull()
    private static final androidx.compose.ui.graphics.vector.ImageVector FreshAir = null;
    
    /**
     * 水滴图标 - 用于湿度显示或水系统相关功能
     */
    @org.jetbrains.annotations.NotNull()
    private static final androidx.compose.ui.graphics.vector.ImageVector Droplet = null;
    
    /**
     * 高温图标 - 用于显示高温状态或升温趋势
     */
    @org.jetbrains.annotations.NotNull()
    private static final androidx.compose.ui.graphics.vector.ImageVector TemperatureHigh = null;
    
    /**
     * 低温图标 - 用于显示低温状态或降温趋势
     */
    @org.jetbrains.annotations.NotNull()
    private static final androidx.compose.ui.graphics.vector.ImageVector TemperatureLow = null;
    
    /**
     * 太阳图标 - 用于晴天天气显示
     */
    @org.jetbrains.annotations.NotNull()
    private static final androidx.compose.ui.graphics.vector.ImageVector Sun = null;
    
    /**
     * 月亮图标 - 用于夜间模式或阴天显示
     */
    @org.jetbrains.annotations.NotNull()
    private static final androidx.compose.ui.graphics.vector.ImageVector Moon = null;
    
    /**
     * 云朵图标 - 用于多云天气显示
     */
    @org.jetbrains.annotations.NotNull()
    private static final androidx.compose.ui.graphics.vector.ImageVector Cloud = null;
    
    /**
     * 勾选圆圈图标 - 用于表示已完成或正常状态
     */
    @org.jetbrains.annotations.NotNull()
    private static final androidx.compose.ui.graphics.vector.ImageVector CheckCircle = null;
    
    /**
     * 首页/房子图标 - 用于冷暖舒适页面Tab栏"全屋"
     */
    @org.jetbrains.annotations.NotNull()
    private static final androidx.compose.ui.graphics.vector.ImageVector Home = null;
    
    /**
     * 列表图标 - 用于冷暖舒适页面Tab栏"楼层"
     */
    @org.jetbrains.annotations.NotNull()
    private static final androidx.compose.ui.graphics.vector.ImageVector List = null;
    
    /**
     * 下拉箭头图标 - 用于楼层选择器、时长选择器等
     */
    @org.jetbrains.annotations.NotNull()
    private static final androidx.compose.ui.graphics.vector.ImageVector ChevronDown = null;
    
    /**
     * 定位/位置图标 - 用于显示当前位置
     */
    @org.jetbrains.annotations.NotNull()
    private static final androidx.compose.ui.graphics.vector.ImageVector LocationPin = null;
    
    private IconsWuHeng() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.compose.ui.graphics.vector.ImageVector getClimate() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.compose.ui.graphics.vector.ImageVector getWater() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.compose.ui.graphics.vector.ImageVector getProfile() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.compose.ui.graphics.vector.ImageVector getMenu() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.compose.ui.graphics.vector.ImageVector getClose() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.compose.ui.graphics.vector.ImageVector getSettings() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.compose.ui.graphics.vector.ImageVector getPlus() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.compose.ui.graphics.vector.ImageVector getMinus() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.compose.ui.graphics.vector.ImageVector getChevronRight() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.compose.ui.graphics.vector.ImageVector getChevronLeft() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.compose.ui.graphics.vector.ImageVector getPower() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.compose.ui.graphics.vector.ImageVector getFan() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.compose.ui.graphics.vector.ImageVector getFlame() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.compose.ui.graphics.vector.ImageVector getShield() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.compose.ui.graphics.vector.ImageVector getFreshAir() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.compose.ui.graphics.vector.ImageVector getDroplet() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.compose.ui.graphics.vector.ImageVector getTemperatureHigh() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.compose.ui.graphics.vector.ImageVector getTemperatureLow() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.compose.ui.graphics.vector.ImageVector getSun() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.compose.ui.graphics.vector.ImageVector getMoon() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.compose.ui.graphics.vector.ImageVector getCloud() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.compose.ui.graphics.vector.ImageVector getCheckCircle() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.compose.ui.graphics.vector.ImageVector getHome() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.compose.ui.graphics.vector.ImageVector getList() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.compose.ui.graphics.vector.ImageVector getChevronDown() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.compose.ui.graphics.vector.ImageVector getLocationPin() {
        return null;
    }
}