package com.wuheng.smart.data.mock

import com.wuheng.smart.data.model.*

/**
 * Mock数据文件 - 智能家居App假数据
 * 包含首页、冷暖系统、水系统、用户等模块的模拟数据
 */
object MockData {

    // ==================== 天气与环境数据 ====================
    val mockWeatherInfo = WeatherInfo(
        temperature = 26,
        condition = "多云",
        icon = "cloudy",
        highTemp = 30,
        lowTemp = 22,
        humidity = 65
    )

    val mockAirQuality = AirQuality(
        aqi = 45,
        level = "优",
        pm25 = 22,
        pm10 = 35,
        description = "空气清新，适合户外活动"
    )

    val mockEnvironmentData = EnvironmentData(
        indoorTemperature = 24.5,
        indoorHumidity = 58,
        co2Level = 420,
        pm25 = 15,
        voc = 0.3
    )

    // ==================== 场景数据（增强版）====================

    /**
     * 智能场景列表（增强版）
     *
     * 四种核心场景定义（来自控制系统文档）:
     * - 会客模式: 温度24°C, 湿度50%, 风速自动
     * - 离家模式: 关闭所有设备, 开启安防
     * - 睡眠模式: 温度26°C, 湿度45%, 风速低
     * - 值守模式: 最低功耗运行
     */
    val mockScenes = listOf(
        Scene(
            id = "scene_001",
            name = "会客模式",
            icon = "users",
            isRunning = false,
            // 预设参数
            presetTemperature = 24f,
            presetHumidity = 50,
            presetFanSpeed = "auto",
            description = "自动调节至舒适温度，开启全屋照明"
        ),
        Scene(
            id = "scene_002",
            name = "离家模式",
            icon = "logout",
            isRunning = false,
            presetTemperature = null,  // 离家模式关闭设备
            presetHumidity = null,
            presetFanSpeed = null,
            description = "关闭所有电器，启动安防监控"
        ),
        Scene(
            id = "scene_003",
            name = "睡眠模式",
            icon = "moon",
            isRunning = true,
            presetTemperature = 26f,
            presetHumidity = 45,
            presetFanSpeed = "low",
            description = "调暗灯光，降低风速，静音运行"
        ),
        Scene(
            id = "scene_004",
            name = "值守模式",
            icon = "shield",
            isRunning = false,
            presetTemperature = 28f,
            presetHumidity = 55,
            presetFanSpeed = "auto",
            description = "最低功耗运行，保持基础环境监测"
        )
    )

    // ==================== 设备数据 ====================
    val mockDevices = listOf(
        Device(
            id = "dev_001",
            name = "客厅空调",
            type = DeviceType.CLIMATE,
            status = DeviceStatus.ON,
            roomName = "客厅",
            isOnline = true
        ),
        Device(
            id = "dev_002",
            name = "主卧空调",
            type = DeviceType.CLIMATE,
            status = DeviceStatus.ON,
            roomName = "主卧",
            isOnline = true
        ),
        Device(
            id = "dev_003",
            name = "热水器",
            type = DeviceType.WATER,
            status = DeviceStatus.ON,
            roomName = "设备间",
            isOnline = true
        ),
        Device(
            id = "dev_004",
            name = "客厅主灯",
            type = DeviceType.LIGHT,
            status = DeviceStatus.ON,
            roomName = "客厅",
            isOnline = true
        ),
        Device(
            id = "dev_005",
            name = "客厅窗帘",
            type = DeviceType.CURTAIN,
            status = DeviceStatus.STANDBY,
            roomName = "客厅",
            isOnline = true
        ),
        Device(
            id = "dev_006",
            name = "智能门锁",
            type = DeviceType.SECURITY,
            status = DeviceStatus.ON,
            roomName = "玄关",
            isOnline = true
        ),
        Device(
            id = "dev_007",
            name = "儿童房空调",
            type = DeviceType.CLIMATE,
            status = DeviceStatus.OFF,
            roomName = "儿童房",
            isOnline = true
        ),
        Device(
            id = "dev_008",
            name = "书房空调",
            type = DeviceType.CLIMATE,
            status = DeviceStatus.ON,
            roomName = "书房",
            isOnline = false
        )
    )

    // ==================== 首页数据（增强版）====================

    /**
     * 首页概览数据（增强版）
     *
     * 新增字段说明：
     * - weatherTemp, weatherDesc, aqiStatus: 天气信息栏数据
     * - residenceName, address: 西湖壹号院卡片数据
     * - voc: 环境数据卡片第5项
     */
    val mockHomeOverview = HomeOverview(
        roomCount = 8,
        deviceCount = 24,
        onlineDeviceCount = 22,
        // ========== 环境数据（5项）==========
        indoorTemperature = 24.5,
        indoorHumidity = 58,
        pm25 = 15,
        co2 = 420,
        voc = 0.3,                          // TOVC/VOC (环境数据卡片的第5项)
        // ========== 天气信息栏 ==========
        weatherTemp = 26f,                   // 室外天气温度
        weatherDesc = "多云",                 // 天气描述
        aqiStatus = "优",                    // AQI等级文本
        // ========== 住宅信息 ==========
        residenceName = "西湖壹号院",         // 住宅名称
        address = "杭州市西湖区文一西路969号", // 详细地址
        residenceImageResId = null,          // 背景图片（可选）
        // ========== 运行状态 ==========
        currentWeatherMode = WeatherMode.COOLING,
        runningScenes = mockScenes.filter { it.isRunning },
        recentDevices = mockDevices.take(4)
    )

    // ==================== 住宅信息 ====================
    val mockResidence = Residence(
        id = "res_001",
        name = "绿城桃花源别墅",
        address = "杭州市余杭区桃花源别墅区18栋",
        area = 380.0,
        floors = 4,
        rooms = 8
    )

    // ==================== 冷暖系统数据 ====================
    val mockClimateOverview = ClimateOverview(
        currentTemperature = 24.5,
        targetTemperature = 26.0,
        currentMode = ClimateMode.COOLING,
        isRunning = true,
        floorCount = 4,
        zoneCount = 8,
        runningZoneCount = 5
    )

    val mockFloors = listOf(
        Floor(
            id = "floor_b1",
            name = "B1 地下室",
            order = 0,
            zoneCount = 2,
            runningZoneCount = 1,
            averageTemperature = 22.0
        ),
        Floor(
            id = "floor_1f",
            name = "1F 一层",
            order = 1,
            zoneCount = 3,
            runningZoneCount = 2,
            averageTemperature = 24.5
        ),
        Floor(
            id = "floor_2f",
            name = "2F 二层",
            order = 2,
            zoneCount = 2,
            runningZoneCount = 1,
            averageTemperature = 25.0
        ),
        Floor(
            id = "floor_3f",
            name = "3F 三层",
            order = 3,
            zoneCount = 1,
            runningZoneCount = 1,
            averageTemperature = 26.0
        )
    )

    val mockZones = listOf(
        // B1 地下室
        Zone(
            id = "zone_b1_01",
            name = "影音室",
            floorId = "floor_b1",
            currentTemperature = 22.0,
            targetTemperature = 24.0,
            mode = ClimateMode.COOLING,
            isRunning = true,
            isOnline = true
        ),
        Zone(
            id = "zone_b1_02",
            name = "健身房",
            floorId = "floor_b1",
            currentTemperature = 21.5,
            targetTemperature = 23.0,
            mode = ClimateMode.OFF,
            isRunning = false,
            isOnline = true
        ),
        // 1F 一层
        Zone(
            id = "zone_1f_01",
            name = "客厅",
            floorId = "floor_1f",
            currentTemperature = 24.5,
            targetTemperature = 26.0,
            mode = ClimateMode.COOLING,
            isRunning = true,
            isOnline = true
        ),
        Zone(
            id = "zone_1f_02",
            name = "餐厅",
            floorId = "floor_1f",
            currentTemperature = 24.0,
            targetTemperature = 26.0,
            mode = ClimateMode.COOLING,
            isRunning = true,
            isOnline = true
        ),
        Zone(
            id = "zone_1f_03",
            name = "厨房",
            floorId = "floor_1f",
            currentTemperature = 25.0,
            targetTemperature = 26.0,
            mode = ClimateMode.VENTILATION,
            isRunning = false,
            isOnline = true
        ),
        // 2F 二层
        Zone(
            id = "zone_2f_01",
            name = "主卧",
            floorId = "floor_2f",
            currentTemperature = 25.0,
            targetTemperature = 26.0,
            mode = ClimateMode.COOLING,
            isRunning = true,
            isOnline = true
        ),
        Zone(
            id = "zone_2f_02",
            name = "儿童房",
            floorId = "floor_2f",
            currentTemperature = 24.5,
            targetTemperature = 25.0,
            mode = ClimateMode.OFF,
            isRunning = false,
            isOnline = true
        ),
        // 3F 三层
        Zone(
            id = "zone_3f_01",
            name = "书房",
            floorId = "floor_3f",
            currentTemperature = 26.0,
            targetTemperature = 25.0,
            mode = ClimateMode.COOLING,
            isRunning = true,
            isOnline = false
        )
    )

    val mockZoneDetails = mockZones.map { zone ->
        ZoneDetail(
            id = zone.id,
            name = zone.name,
            floorId = zone.floorId,
            floorName = mockFloors.find { it.id == zone.floorId }?.name ?: "",
            currentTemperature = zone.currentTemperature,
            targetTemperature = zone.targetTemperature,
            mode = zone.mode,
            isRunning = zone.isRunning,
            isOnline = zone.isOnline,
            humidity = 55 + (0..10).random(),
            fanSpeed = FanSpeed.AUTO,
            scheduleEnabled = false,
            scheduleInfo = null
        )
    }

    // ==================== 水系统数据（增强版）====================

    // 热力杀菌状态 - 使用 model 包中的 SterilizationStatus
    val mockSterilizationStatus = com.wuheng.smart.data.model.SterilizationStatus(
        isActive = false,
        lastExecutedTime = "2026-04-01 03:00",
        nextScheduledTime = "2026-04-08 03:00",
        sterilizationTemperature = 70,
        duration = 30
    )

    // 滤芯状态 - 使用 model 包中的 FilterInfo
    val mockFilterStatus = listOf(
        com.wuheng.smart.data.model.FilterInfo(
            id = "filter_001",
            name = "前置过滤器",
            filterModel = "PP-001",
            type = "前置过滤",
            remainingPercentage = 85f,
            remainingDays = 310,
            totalLifeDays = 365,
            usedDays = 55,
            status = com.wuheng.smart.data.model.FilterStatus.NORMAL
        ),
        com.wuheng.smart.data.model.FilterInfo(
            id = "filter_002",
            name = "中央净水机滤芯",
            filterModel = "CF-002",
            type = "中央净水",
            remainingPercentage = 45f,
            remainingDays = 329,
            totalLifeDays = 730,
            usedDays = 401,
            status = com.wuheng.smart.data.model.FilterStatus.WARNING
        ),
        com.wuheng.smart.data.model.FilterInfo(
            id = "filter_003",
            name = "末端直饮滤芯",
            filterModel = "RO-003",
            type = "末端直饮",
            remainingPercentage = 20f,
            remainingDays = 73,
            totalLifeDays = 365,
            usedDays = 292,
            status = com.wuheng.smart.data.model.FilterStatus.REPLACE_NOW
        )
    )

    /**
     * 水系统概览数据（增强版）
     *
     * 新增字段说明：
     * - currentTemp, cycleMode, temporaryDuration: 生活热水循环卡片
     * - sterilizationSchedule, sterilizationStatus: 热力杀菌卡片
     * - filters: 全屋净水滤芯列表
     */
    val mockWaterOverview = WaterOverview(
        systemStatus = WaterSystemStatus.NORMAL,
        inletTemperature = 15.5,
        outletTemperature = 45.0,
        pressure = 0.35,
        flowRate = 2.8,
        deviceCount = 6,
        runningDeviceCount = 4,
        // ========== 新增：生活热水 ==========
        currentTemp = 55f,                     // 当前水温 55°C
        cycleMode = CycleMode.TEMPORARY,       // 当前选中临时循环模式
        temporaryDuration = 30,                // 临时运行30分钟
        // ========== 新增：热力杀菌 ==========
        sterilizationSchedule = "每周五 02:00", // 杀菌预约时间
        sterilizationStatus = mockSterilizationStatus,
        // ========== 新增：滤芯列表 ==========
        filters = mockFilterStatus,
        // ========== 新增：水质参数 ==========
        tds = 25,                             // TDS值 (正常<50)
        ph = 7.2f                             // PH值 (正常6.5-8.5)
    )

    val mockWaterDevices = listOf(
        WaterDevice(
            id = "water_001",
            name = "燃气锅炉",
            type = WaterDeviceType.BOILER,
            status = WaterDeviceStatus.NORMAL,
            isRunning = true,
            currentTemperature = 65.0,
            targetTemperature = 65.0,
            settings = WaterDeviceSettings(
                targetTemperature = 65.0,
                timerEnabled = false,
                timerStartTime = null,
                timerEndTime = null,
                ecoMode = false
            )
        ),
        WaterDevice(
            id = "water_002",
            name = "储水式热水器",
            type = WaterDeviceType.WATER_HEATER,
            status = WaterDeviceStatus.NORMAL,
            isRunning = true,
            currentTemperature = 55.0,
            targetTemperature = 55.0,
            settings = WaterDeviceSettings(
                targetTemperature = 55.0,
                timerEnabled = true,
                timerStartTime = "06:00",
                timerEndTime = "23:00",
                ecoMode = true
            )
        ),
        WaterDevice(
            id = "water_003",
            name = "热水循环泵",
            type = WaterDeviceType.CIRCULATION_PUMP,
            status = WaterDeviceStatus.NORMAL,
            isRunning = true,
            currentTemperature = null,
            targetTemperature = null,
            settings = WaterDeviceSettings(
                targetTemperature = null,
                timerEnabled = true,
                timerStartTime = "06:00",
                timerEndTime = "23:00",
                ecoMode = false
            )
        ),
        WaterDevice(
            id = "water_004",
            name = "保温水箱",
            type = WaterDeviceType.WATER_TANK,
            status = WaterDeviceStatus.NORMAL,
            isRunning = true,
            currentTemperature = 52.0,
            targetTemperature = null,
            settings = WaterDeviceSettings(
                targetTemperature = null,
                timerEnabled = false,
                timerStartTime = null,
                timerEndTime = null,
                ecoMode = false
            )
        ),
        WaterDevice(
            id = "water_005",
            name = "中央净水器",
            type = WaterDeviceType.PURIFIER,
            status = WaterDeviceStatus.WARNING,
            isRunning = true,
            currentTemperature = null,
            targetTemperature = null,
            settings = WaterDeviceSettings(
                targetTemperature = null,
                timerEnabled = false,
                timerStartTime = null,
                timerEndTime = null,
                ecoMode = false
            )
        ),
        WaterDevice(
            id = "water_006",
            name = "软水机",
            type = WaterDeviceType.SOFTENER,
            status = WaterDeviceStatus.NORMAL,
            isRunning = false,
            currentTemperature = null,
            targetTemperature = null,
            settings = WaterDeviceSettings(
                targetTemperature = null,
                timerEnabled = false,
                timerStartTime = null,
                timerEndTime = null,
                ecoMode = true
            )
        )
    )

    // 热水循环模式
    val mockHotWaterModes = listOf(
        HotWaterMode(
            id = "mode_point",
            name = "点动模式",
            description = "按需启动，节能省气",
            isActive = false
        ),
        HotWaterMode(
            id = "mode_schedule",
            name = "预约模式",
            description = "定时启动，到点即热",
            isActive = true
        ),
        HotWaterMode(
            id = "mode_always",
            name = "全天候",
            description = "24小时热水待命",
            isActive = false
        ),
        HotWaterMode(
            id = "mode_eco",
            name = "经济模式",
            description = "智能调节，平衡舒适与节能",
            isActive = false
        )
    )

    // ==================== 用户数据（增强版）====================

    val mockUserInfo = UserInfo(
        userId = 1,
        userIdNo = "user_001",
        userName = "张先生",
        userTel = "13800138000",
        userType = 1,
        houseId = 1,
        status = 1
    )

    /**
     * 用户资料（增强版）
     *
     * 新增字段：
     * - role: 用户角色 ("业主")
     * - hasUnreadNotification: 未读通知状态
     * - unreadCount: 未读消息数量
     */
    val mockUserProfile = UserProfile(
        userId = "user_001",
        nickname = "张先生",
        avatar = "https://example.com/avatar/default.png",
        phone = "138****8888",
        email = "zhang@example.com",
        homeName = "西湖壹号院",                 // 住宅名称
        address = "杭州市西湖区文一西路969号",   // 详细地址
        role = "业主",                          // 用户角色
        hasUnreadNotification = true,           // 有未读通知
        unreadCount = 3                         // 3条未读消息
    )

    // ========== 新增：功能菜单列表 ==========

    /**
     * 功能菜单项列表
     *
     * UI映射：功能菜单列表 (图标+箭头+红点)
     */
    val mockMenuItems = listOf(
        MenuItem(
            id = MenuItemId.CONSUMABLES,
            title = "耗材使用进度",
            iconType = "chart",
            hasNotification = true,             // 显示红点（有耗材需更换）
            badgeCount = null,
            isEnabled = true
        ),
        MenuItem(
            id = MenuItemId.ABOUT,
            title = "关于新宜能",
            iconType = "info",
            hasNotification = false,
            badgeCount = null,
            isEnabled = true
        ),
        MenuItem(
            id = MenuItemId.FEEDBACK,
            title = "意见反馈",
            iconType = "feedback",
            hasNotification = false,
            badgeCount = null,
            isEnabled = true
        ),
        MenuItem(
            id = MenuItemId.CLEAR_CACHE,
            title = "清除缓存",
            iconType = "trash",
            hasNotification = false,
            badgeCount = null,
            isEnabled = true
        )
    )

    // ========== 新增：版本信息 ==========

    /**
     * 应用版本信息
     */
    val mockVersionInfo = VersionInfo(
        versionName = "V1.2.3",                 // 版本号
        versionCode = 123,
        buildDate = "2026-04-10",
        hasUpdate = false,                       // 无新版本
        updateUrl = null,
        updateLog = null
    )

    // 项目概述
    val mockProjectOverview = ProjectOverview(
        projectName = "绿城桃花源智能家居系统",
        installationDate = "2025-08-15",
        warrantyExpireDate = "2028-08-15",
        serviceProvider = "浙江智能家居科技有限公司",
        serviceHotline = "400-888-8888",
        systemVersion = "v2.5.1",
        lastMaintenanceDate = "2026-03-15"
    )

    // 预约记录
    val mockAppointments = listOf(
        AppointmentRecord(
            id = "apt_001",
            type = "定期保养",
            status = AppointmentStatus.COMPLETED,
            appointmentDate = "2026-03-15",
            serviceItem = "冷暖系统全面检测与保养",
            technician = "李师傅",
            rating = 5,
            comment = "服务专业，态度很好"
        ),
        AppointmentRecord(
            id = "apt_002",
            type = "故障维修",
            status = AppointmentStatus.COMPLETED,
            appointmentDate = "2026-01-20",
            serviceItem = "水系统循环泵异响处理",
            technician = "王师傅",
            rating = 5,
            comment = "快速解决问题"
        ),
        AppointmentRecord(
            id = "apt_003",
            type = "定期保养",
            status = AppointmentStatus.SCHEDULED,
            appointmentDate = "2026-07-15",
            serviceItem = "水系统滤芯更换与检测",
            technician = null,
            rating = null,
            comment = null
        )
    )

    // ==================== 辅助数据类 ====================
    data class WeatherInfo(
        val temperature: Int,
        val condition: String,
        val icon: String,
        val highTemp: Int,
        val lowTemp: Int,
        val humidity: Int
    )

    data class AirQuality(
        val aqi: Int,
        val level: String,
        val pm25: Int,
        val pm10: Int,
        val description: String
    )

    data class EnvironmentData(
        val indoorTemperature: Double,
        val indoorHumidity: Int,
        val co2Level: Int,
        val pm25: Int,
        val voc: Double
    )

    data class Residence(
        val id: String,
        val name: String,
        val address: String,
        val area: Double,
        val floors: Int,
        val rooms: Int
    )

    data class HotWaterMode(
        val id: String,
        val name: String,
        val description: String,
        val isActive: Boolean
    )

    data class ProjectOverview(
        val projectName: String,
        val installationDate: String,
        val warrantyExpireDate: String,
        val serviceProvider: String,
        val serviceHotline: String,
        val systemVersion: String,
        val lastMaintenanceDate: String
    )

    data class AppointmentRecord(
        val id: String,
        val type: String,
        val status: AppointmentStatus,
        val appointmentDate: String,
        val serviceItem: String,
        val technician: String?,
        val rating: Int?,
        val comment: String?
    )

    enum class AppointmentStatus {
        SCHEDULED,      // 已预约
        IN_PROGRESS,    // 服务中
        COMPLETED,      // 已完成
        CANCELLED       // 已取消
    }
}
