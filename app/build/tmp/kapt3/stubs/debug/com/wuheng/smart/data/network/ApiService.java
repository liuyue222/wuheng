package com.wuheng.smart.data.network;

import com.wuheng.smart.data.model.*;
import retrofit2.http.*;

/**
 * 五恒智能控制系统 API 接口服务
 * Base URL: http://116.62.51.112/wuheng_iot/index.php
 */
@kotlin.Metadata(mv = {1, 7, 1}, k = 1, d1 = {"\u0000\u00e0\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\t\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bf\u0018\u00002\u00020\u0001J!\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\b\b\u0001\u0010\u0005\u001a\u00020\u0006H\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0007J!\u0010\b\u001a\b\u0012\u0004\u0012\u00020\t0\u00032\b\b\u0001\u0010\u0005\u001a\u00020\nH\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u000bJ!\u0010\f\u001a\b\u0012\u0004\u0012\u00020\t0\u00032\b\b\u0001\u0010\u0005\u001a\u00020\rH\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u000eJ!\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\t0\u00032\b\b\u0001\u0010\u0005\u001a\u00020\u0010H\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0011J!\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\t0\u00032\b\b\u0001\u0010\u0005\u001a\u00020\u0013H\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0014J!\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\t0\u00032\b\b\u0001\u0010\u0005\u001a\u00020\u0016H\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0017J\u0017\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\t0\u0003H\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0019J!\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u001b0\u00032\b\b\u0001\u0010\u0005\u001a\u00020\u001cH\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u001dJ!\u0010\u001e\u001a\b\u0012\u0004\u0012\u00020\t0\u00032\b\b\u0001\u0010\u0005\u001a\u00020\u001fH\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010 J!\u0010!\u001a\b\u0012\u0004\u0012\u00020\t0\u00032\b\b\u0001\u0010\u0005\u001a\u00020\"H\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010#J!\u0010$\u001a\b\u0012\u0004\u0012\u00020%0\u00032\b\b\u0001\u0010&\u001a\u00020\'H\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010(JK\u0010)\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020+0*0\u00032\b\b\u0001\u0010&\u001a\u00020\'2\n\b\u0003\u0010,\u001a\u0004\u0018\u00010-2\n\b\u0003\u0010.\u001a\u0004\u0018\u00010/2\n\b\u0003\u00100\u001a\u0004\u0018\u00010/H\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u00101J3\u00102\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020%0*0\u00032\b\b\u0001\u00103\u001a\u00020\'2\n\b\u0003\u00104\u001a\u0004\u0018\u00010\'H\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u00105J!\u00106\u001a\b\u0012\u0004\u0012\u0002070\u00032\b\b\u0001\u0010&\u001a\u00020\'H\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010(J\'\u00108\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002090*0\u00032\b\b\u0001\u00103\u001a\u00020\'H\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010(J\'\u0010:\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020;0*0\u00032\b\b\u0001\u00103\u001a\u00020\'H\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010(J!\u0010<\u001a\b\u0012\u0004\u0012\u00020=0\u00032\b\b\u0001\u00103\u001a\u00020\'H\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010(J!\u0010>\u001a\b\u0012\u0004\u0012\u00020?0\u00032\b\b\u0001\u00103\u001a\u00020\'H\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010(J\'\u0010@\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020A0*0\u00032\b\b\u0001\u00103\u001a\u00020\'H\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010(J\u001d\u0010B\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020C0*0\u0003H\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0019J\u001d\u0010D\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020E0*0\u0003H\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0019J!\u0010F\u001a\b\u0012\u0004\u0012\u00020G0\u00032\b\b\u0001\u00103\u001a\u00020\'H\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010(J3\u0010H\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020I0*0\u00032\b\b\u0001\u00103\u001a\u00020\'2\n\b\u0003\u0010J\u001a\u0004\u0018\u00010\'H\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u00105J\'\u0010K\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020L0*0\u00032\b\b\u0001\u00103\u001a\u00020\'H\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010(J!\u0010M\u001a\b\u0012\u0004\u0012\u00020N0\u00032\b\b\u0001\u00103\u001a\u00020\'H\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010(J\u0017\u0010O\u001a\b\u0012\u0004\u0012\u00020P0\u0003H\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0019J!\u0010Q\u001a\b\u0012\u0004\u0012\u00020R0\u00032\b\b\u0001\u00103\u001a\u00020\'H\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010(J+\u0010S\u001a\b\u0012\u0004\u0012\u00020T0\u00032\b\b\u0001\u0010U\u001a\u00020-2\b\b\u0001\u0010V\u001a\u00020-H\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010WJ!\u0010X\u001a\b\u0012\u0004\u0012\u00020Y0\u00032\b\b\u0001\u0010\u0005\u001a\u00020ZH\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010[J\u0017\u0010\\\u001a\b\u0012\u0004\u0012\u00020\t0\u0003H\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0019J\u0017\u0010]\u001a\b\u0012\u0004\u0012\u00020\t0\u0003H\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0019J!\u0010^\u001a\b\u0012\u0004\u0012\u00020\t0\u00032\b\b\u0001\u0010\u0005\u001a\u00020_H\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010`J!\u0010a\u001a\b\u0012\u0004\u0012\u00020b0\u00032\b\b\u0001\u0010\u0005\u001a\u00020cH\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010dJ!\u0010e\u001a\b\u0012\u0004\u0012\u00020\t0\u00032\b\b\u0001\u0010\u0005\u001a\u00020fH\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010gJ!\u0010h\u001a\b\u0012\u0004\u0012\u00020\t0\u00032\b\b\u0001\u0010\u0005\u001a\u00020iH\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010jJ!\u0010k\u001a\b\u0012\u0004\u0012\u00020l0\u00032\b\b\u0001\u0010\u0005\u001a\u00020mH\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010nJ!\u0010o\u001a\b\u0012\u0004\u0012\u00020\t0\u00032\b\b\u0001\u0010\u0005\u001a\u00020pH\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010qJ!\u0010r\u001a\b\u0012\u0004\u0012\u00020\t0\u00032\b\b\u0001\u0010\u0005\u001a\u00020sH\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010tJ!\u0010u\u001a\b\u0012\u0004\u0012\u00020v0\u00032\b\b\u0001\u0010\u0005\u001a\u00020wH\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010xJ!\u0010y\u001a\b\u0012\u0004\u0012\u00020z0\u00032\b\b\u0001\u0010\u0005\u001a\u00020{H\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010|J\"\u0010}\u001a\b\u0012\u0004\u0012\u00020~0\u00032\b\b\u0001\u0010\u0005\u001a\u00020\u007fH\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0003\u0010\u0080\u0001J$\u0010\u0081\u0001\u001a\b\u0012\u0004\u0012\u00020\t0\u00032\t\b\u0001\u0010\u0005\u001a\u00030\u0082\u0001H\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0003\u0010\u0083\u0001\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006\u0084\u0001"}, d2 = {"Lcom/wuheng/smart/data/network/ApiService;", "", "applyScene", "Lcom/wuheng/smart/data/network/BaseResponse;", "Lcom/wuheng/smart/data/model/ApplySceneResponse;", "request", "Lcom/wuheng/smart/data/model/ApplySceneRequest;", "(Lcom/wuheng/smart/data/model/ApplySceneRequest;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "bindHouse", "", "Lcom/wuheng/smart/data/model/BindHouseRequest;", "(Lcom/wuheng/smart/data/model/BindHouseRequest;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "bookFilterReplace", "Lcom/wuheng/smart/data/model/BookFilterReplaceRequest;", "(Lcom/wuheng/smart/data/model/BookFilterReplaceRequest;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "bookService", "Lcom/wuheng/smart/data/model/BookServiceRequest;", "(Lcom/wuheng/smart/data/model/BookServiceRequest;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "cancelVacationMode", "Lcom/wuheng/smart/data/model/CancelVacationRequest;", "(Lcom/wuheng/smart/data/model/CancelVacationRequest;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "changePassword", "Lcom/wuheng/smart/data/model/ChangePasswordRequest;", "(Lcom/wuheng/smart/data/model/ChangePasswordRequest;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "clearAllNotifications", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "controlDevice", "Lcom/wuheng/smart/data/model/ControlDeviceResponse;", "Lcom/wuheng/smart/data/model/ControlDeviceRequest;", "(Lcom/wuheng/smart/data/model/ControlDeviceRequest;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "deleteDevice", "Lcom/wuheng/smart/data/model/DeleteDeviceRequest;", "(Lcom/wuheng/smart/data/model/DeleteDeviceRequest;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "forgotPassword", "Lcom/wuheng/smart/data/model/ForgotPasswordRequest;", "(Lcom/wuheng/smart/data/model/ForgotPasswordRequest;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getDeviceDetail", "Lcom/wuheng/smart/data/model/DeviceInfo;", "deviceId", "", "(ILkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getDeviceHistoryData", "", "Lcom/wuheng/smart/data/model/HistoryDataPoint;", "dataType", "", "startTime", "", "endTime", "(ILjava/lang/String;Ljava/lang/Long;Ljava/lang/Long;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getDeviceList", "houseId", "roomId", "(ILjava/lang/Integer;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getDeviceStatus", "Lcom/wuheng/smart/data/model/DeviceStatus;", "getFilterStatus", "Lcom/wuheng/smart/data/model/FilterStatusInfo;", "getFloorInfo", "Lcom/wuheng/smart/data/model/FloorInfo;", "getHotWaterStatus", "Lcom/wuheng/smart/data/model/HotWaterStatusResponse;", "getHouseInfo", "Lcom/wuheng/smart/data/model/HouseInfo;", "getMaintenanceLog", "Lcom/wuheng/smart/data/model/MaintenanceLogItem;", "getMyHouses", "Lcom/wuheng/smart/data/model/MyHouse;", "getNotificationList", "Lcom/wuheng/smart/data/model/NotificationApiItem;", "getOutdoorEnv", "Lcom/wuheng/smart/data/model/OutdoorEnv;", "getRoomInfo", "Lcom/wuheng/smart/data/model/RoomInfo;", "floorId", "getSceneList", "Lcom/wuheng/smart/data/model/SceneInfo;", "getSystemStatus", "Lcom/wuheng/smart/data/model/SystemStatus;", "getUserInfo", "Lcom/google/gson/JsonElement;", "getVacationStatus", "Lcom/wuheng/smart/data/model/VacationStatusResponse;", "getWeather", "Lcom/wuheng/smart/data/model/WeatherData;", "lat", "lng", "(Ljava/lang/String;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "login", "Lcom/wuheng/smart/data/model/LoginResponse;", "Lcom/wuheng/smart/data/model/LoginRequest;", "(Lcom/wuheng/smart/data/model/LoginRequest;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "logout", "markAllNotificationsRead", "markNotificationRead", "Lcom/wuheng/smart/data/model/MarkNotificationReadRequest;", "(Lcom/wuheng/smart/data/model/MarkNotificationReadRequest;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "register", "Lcom/wuheng/smart/data/model/RegisterResponse;", "Lcom/wuheng/smart/data/model/RegisterRequest;", "(Lcom/wuheng/smart/data/model/RegisterRequest;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "renameDevice", "Lcom/wuheng/smart/data/model/RenameDeviceRequest;", "(Lcom/wuheng/smart/data/model/RenameDeviceRequest;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "saveScene", "Lcom/wuheng/smart/data/model/SaveSceneRequest;", "(Lcom/wuheng/smart/data/model/SaveSceneRequest;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "setCirculationMode", "Lcom/wuheng/smart/data/model/SetCirculationModeResponse;", "Lcom/wuheng/smart/data/model/SetCirculationModeRequest;", "(Lcom/wuheng/smart/data/model/SetCirculationModeRequest;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "setGlobalHumidity", "Lcom/wuheng/smart/data/model/SetGlobalHumidityRequest;", "(Lcom/wuheng/smart/data/model/SetGlobalHumidityRequest;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "setGlobalTemp", "Lcom/wuheng/smart/data/model/SetGlobalTempRequest;", "(Lcom/wuheng/smart/data/model/SetGlobalTempRequest;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "setSterilization", "Lcom/wuheng/smart/data/model/SterilizationApiResponse;", "Lcom/wuheng/smart/data/model/SetSterilizationRequest;", "(Lcom/wuheng/smart/data/model/SetSterilizationRequest;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "setSystemMode", "Lcom/wuheng/smart/data/model/SetSystemModeResponse;", "Lcom/wuheng/smart/data/model/SetSystemModeRequest;", "(Lcom/wuheng/smart/data/model/SetSystemModeRequest;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "setVacationMode", "Lcom/wuheng/smart/data/model/SetVacationModeResponse;", "Lcom/wuheng/smart/data/model/SetVacationModeRequest;", "(Lcom/wuheng/smart/data/model/SetVacationModeRequest;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "updateUserInfo", "Lcom/wuheng/smart/data/model/UpdateUserInfoRequest;", "(Lcom/wuheng/smart/data/model/UpdateUserInfoRequest;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_debug"})
public abstract interface ApiService {
    
    /**
     * 1. 用户登录
     * URL: /home/user/login
     * Method: POST
     * 认证: 否
     */
    @org.jetbrains.annotations.Nullable()
    @retrofit2.http.POST(value = "home/user/login")
    public abstract java.lang.Object login(@org.jetbrains.annotations.NotNull()
    @retrofit2.http.Body()
    com.wuheng.smart.data.model.LoginRequest request, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.wuheng.smart.data.network.BaseResponse<com.wuheng.smart.data.model.LoginResponse>> continuation);
    
    /**
     * 2. 用户注册
     * URL: /home/user/register
     * Method: POST
     * 认证: 否
     */
    @org.jetbrains.annotations.Nullable()
    @retrofit2.http.POST(value = "home/user/register")
    public abstract java.lang.Object register(@org.jetbrains.annotations.NotNull()
    @retrofit2.http.Body()
    com.wuheng.smart.data.model.RegisterRequest request, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.wuheng.smart.data.network.BaseResponse<com.wuheng.smart.data.model.RegisterResponse>> continuation);
    
    /**
     * 3. 用户登出
     * URL: /home/user/logout
     * Method: POST
     * 认证: 是
     */
    @org.jetbrains.annotations.Nullable()
    @retrofit2.http.POST(value = "home/user/logout")
    public abstract java.lang.Object logout(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.wuheng.smart.data.network.BaseResponse<kotlin.Unit>> continuation);
    
    /**
     * 4. 获取用户信息
     * URL: /home/user/getUserInfo
     * Method: GET
     * 认证: 是
     * data字段可能返回对象{}或空数组[]，用JsonElement兼容
     */
    @org.jetbrains.annotations.Nullable()
    @retrofit2.http.GET(value = "home/user/getUserInfo")
    public abstract java.lang.Object getUserInfo(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.wuheng.smart.data.network.BaseResponse<com.google.gson.JsonElement>> continuation);
    
    /**
     * 5. 更新用户信息
     * URL: /home/user/updateUserInfo
     * Method: POST
     * 认证: 是
     */
    @org.jetbrains.annotations.Nullable()
    @retrofit2.http.POST(value = "home/user/updateUserInfo")
    public abstract java.lang.Object updateUserInfo(@org.jetbrains.annotations.NotNull()
    @retrofit2.http.Body()
    com.wuheng.smart.data.model.UpdateUserInfoRequest request, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.wuheng.smart.data.network.BaseResponse<kotlin.Unit>> continuation);
    
    /**
     * 6. 修改密码
     * URL: /home/user/changePassword
     * Method: POST
     * 认证: 是
     */
    @org.jetbrains.annotations.Nullable()
    @retrofit2.http.POST(value = "home/user/changePassword")
    public abstract java.lang.Object changePassword(@org.jetbrains.annotations.NotNull()
    @retrofit2.http.Body()
    com.wuheng.smart.data.model.ChangePasswordRequest request, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.wuheng.smart.data.network.BaseResponse<kotlin.Unit>> continuation);
    
    /**
     * 7. 绑定房屋
     * URL: /home/user/bindHouse
     * Method: POST
     * 认证: 是
     */
    @org.jetbrains.annotations.Nullable()
    @retrofit2.http.POST(value = "home/user/bindHouse")
    public abstract java.lang.Object bindHouse(@org.jetbrains.annotations.NotNull()
    @retrofit2.http.Body()
    com.wuheng.smart.data.model.BindHouseRequest request, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.wuheng.smart.data.network.BaseResponse<kotlin.Unit>> continuation);
    
    /**
     * 8. 获取我的房屋列表
     * URL: /home/user/getMyHouses
     * Method: GET
     * 认证: 是
     */
    @org.jetbrains.annotations.Nullable()
    @retrofit2.http.GET(value = "home/user/getMyHouses")
    public abstract java.lang.Object getMyHouses(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.wuheng.smart.data.network.BaseResponse<java.util.List<com.wuheng.smart.data.model.MyHouse>>> continuation);
    
    /**
     * 9. 忘记密码
     * URL: /home/user/forgotPassword
     * Method: POST
     * 认证: 否
     */
    @org.jetbrains.annotations.Nullable()
    @retrofit2.http.POST(value = "home/user/forgotPassword")
    public abstract java.lang.Object forgotPassword(@org.jetbrains.annotations.NotNull()
    @retrofit2.http.Body()
    com.wuheng.smart.data.model.ForgotPasswordRequest request, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.wuheng.smart.data.network.BaseResponse<kotlin.Unit>> continuation);
    
    /**
     * 1. 获取房屋信息
     * URL: /home/house/getHouseInfo
     * Method: GET
     * 认证: 是
     */
    @org.jetbrains.annotations.Nullable()
    @retrofit2.http.GET(value = "home/house/getHouseInfo")
    public abstract java.lang.Object getHouseInfo(@retrofit2.http.Query(value = "house_id")
    int houseId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.wuheng.smart.data.network.BaseResponse<com.wuheng.smart.data.model.HouseInfo>> continuation);
    
    /**
     * 2. 获取楼层信息
     * URL: /home/house/getFloorList
     * Method: GET
     * 认证: 是
     * 注意: 后端接口名为getFloorList，前端统一使用getFloorInfo
     */
    @org.jetbrains.annotations.Nullable()
    @retrofit2.http.GET(value = "home/house/getFloorList")
    public abstract java.lang.Object getFloorInfo(@retrofit2.http.Query(value = "house_id")
    int houseId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.wuheng.smart.data.network.BaseResponse<java.util.List<com.wuheng.smart.data.model.FloorInfo>>> continuation);
    
    /**
     * 3. 获取房间信息
     * URL: /home/house/getRoomList
     * Method: GET
     * 认证: 是
     * 注意: 后端接口名为getRoomList，前端统一使用getRoomInfo
     */
    @org.jetbrains.annotations.Nullable()
    @retrofit2.http.GET(value = "home/house/getRoomList")
    public abstract java.lang.Object getRoomInfo(@retrofit2.http.Query(value = "house_id")
    int houseId, @org.jetbrains.annotations.Nullable()
    @retrofit2.http.Query(value = "floor_id")
    java.lang.Integer floorId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.wuheng.smart.data.network.BaseResponse<java.util.List<com.wuheng.smart.data.model.RoomInfo>>> continuation);
    
    @org.jetbrains.annotations.Nullable()
    @retrofit2.http.GET(value = "home/house/getMaintenanceLog")
    public abstract java.lang.Object getMaintenanceLog(@retrofit2.http.Query(value = "house_id")
    int houseId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.wuheng.smart.data.network.BaseResponse<java.util.List<com.wuheng.smart.data.model.MaintenanceLogItem>>> continuation);
    
    /**
     * 1. 获取设备列表
     * URL: /home/device/getDeviceList
     * Method: GET
     * 认证: 是
     */
    @org.jetbrains.annotations.Nullable()
    @retrofit2.http.GET(value = "home/device/getDeviceList")
    public abstract java.lang.Object getDeviceList(@retrofit2.http.Query(value = "house_id")
    int houseId, @org.jetbrains.annotations.Nullable()
    @retrofit2.http.Query(value = "room_id")
    java.lang.Integer roomId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.wuheng.smart.data.network.BaseResponse<java.util.List<com.wuheng.smart.data.model.DeviceInfo>>> continuation);
    
    /**
     * 2. 获取设备详情
     * URL: /home/device/getDeviceInfo
     * Method: GET
     * 认证: 是
     */
    @org.jetbrains.annotations.Nullable()
    @retrofit2.http.GET(value = "home/device/getDeviceInfo")
    public abstract java.lang.Object getDeviceDetail(@retrofit2.http.Query(value = "device_id")
    int deviceId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.wuheng.smart.data.network.BaseResponse<com.wuheng.smart.data.model.DeviceInfo>> continuation);
    
    /**
     * 3. 获取设备实时数据
     * URL: /home/device/getDeviceData
     * Method: GET
     * 认证: 是
     */
    @org.jetbrains.annotations.Nullable()
    @retrofit2.http.GET(value = "home/device/getDeviceData")
    public abstract java.lang.Object getDeviceStatus(@retrofit2.http.Query(value = "device_id")
    int deviceId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.wuheng.smart.data.network.BaseResponse<com.wuheng.smart.data.model.DeviceStatus>> continuation);
    
    /**
     * 4. 控制设备
     * URL: /home/device/controlDevice
     * Method: POST
     * 认证: 是
     */
    @org.jetbrains.annotations.Nullable()
    @retrofit2.http.POST(value = "home/device/controlDevice")
    public abstract java.lang.Object controlDevice(@org.jetbrains.annotations.NotNull()
    @retrofit2.http.Body()
    com.wuheng.smart.data.model.ControlDeviceRequest request, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.wuheng.smart.data.network.BaseResponse<com.wuheng.smart.data.model.ControlDeviceResponse>> continuation);
    
    @org.jetbrains.annotations.Nullable()
    @retrofit2.http.GET(value = "home/device/getHistoryData")
    public abstract java.lang.Object getDeviceHistoryData(@retrofit2.http.Query(value = "device_id")
    int deviceId, @org.jetbrains.annotations.Nullable()
    @retrofit2.http.Query(value = "data_type")
    java.lang.String dataType, @org.jetbrains.annotations.Nullable()
    @retrofit2.http.Query(value = "start_time")
    java.lang.Long startTime, @org.jetbrains.annotations.Nullable()
    @retrofit2.http.Query(value = "end_time")
    java.lang.Long endTime, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.wuheng.smart.data.network.BaseResponse<java.util.List<com.wuheng.smart.data.model.HistoryDataPoint>>> continuation);
    
    @org.jetbrains.annotations.Nullable()
    @retrofit2.http.POST(value = "home/device/renameDevice")
    public abstract java.lang.Object renameDevice(@org.jetbrains.annotations.NotNull()
    @retrofit2.http.Body()
    com.wuheng.smart.data.model.RenameDeviceRequest request, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.wuheng.smart.data.network.BaseResponse<kotlin.Unit>> continuation);
    
    @org.jetbrains.annotations.Nullable()
    @retrofit2.http.POST(value = "home/device/deleteDevice")
    public abstract java.lang.Object deleteDevice(@org.jetbrains.annotations.NotNull()
    @retrofit2.http.Body()
    com.wuheng.smart.data.model.DeleteDeviceRequest request, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.wuheng.smart.data.network.BaseResponse<kotlin.Unit>> continuation);
    
    /**
     * 1. 获取场景列表
     * URL: /home/scene/getSceneList
     * Method: GET
     * 认证: 是
     */
    @org.jetbrains.annotations.Nullable()
    @retrofit2.http.GET(value = "home/scene/getSceneList")
    public abstract java.lang.Object getSceneList(@retrofit2.http.Query(value = "house_id")
    int houseId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.wuheng.smart.data.network.BaseResponse<java.util.List<com.wuheng.smart.data.model.SceneInfo>>> continuation);
    
    /**
     * 2. 应用场景
     * URL: /home/scene/applyScene
     * Method: POST
     * 认证: 是
     */
    @org.jetbrains.annotations.Nullable()
    @retrofit2.http.POST(value = "home/scene/applyScene")
    public abstract java.lang.Object applyScene(@org.jetbrains.annotations.NotNull()
    @retrofit2.http.Body()
    com.wuheng.smart.data.model.ApplySceneRequest request, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.wuheng.smart.data.network.BaseResponse<com.wuheng.smart.data.model.ApplySceneResponse>> continuation);
    
    /**
     * 3. 保存自定义场景
     * URL: /home/scene/saveScene
     * Method: POST
     * 认证: 是
     */
    @org.jetbrains.annotations.Nullable()
    @retrofit2.http.POST(value = "home/scene/saveScene")
    public abstract java.lang.Object saveScene(@org.jetbrains.annotations.NotNull()
    @retrofit2.http.Body()
    com.wuheng.smart.data.model.SaveSceneRequest request, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.wuheng.smart.data.network.BaseResponse<kotlin.Unit>> continuation);
    
    /**
     * 4. 设置度假模式
     * URL: /home/scene/setVacationMode
     * Method: POST
     * 认证: 是
     */
    @org.jetbrains.annotations.Nullable()
    @retrofit2.http.POST(value = "home/scene/setVacationMode")
    public abstract java.lang.Object setVacationMode(@org.jetbrains.annotations.NotNull()
    @retrofit2.http.Body()
    com.wuheng.smart.data.model.SetVacationModeRequest request, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.wuheng.smart.data.network.BaseResponse<com.wuheng.smart.data.model.SetVacationModeResponse>> continuation);
    
    /**
     * 5. 获取度假模式状态
     * URL: /home/scene/getVacationStatus
     * Method: GET
     * 认证: 是
     */
    @org.jetbrains.annotations.Nullable()
    @retrofit2.http.GET(value = "home/scene/getVacationStatus")
    public abstract java.lang.Object getVacationStatus(@retrofit2.http.Query(value = "house_id")
    int houseId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.wuheng.smart.data.network.BaseResponse<com.wuheng.smart.data.model.VacationStatusResponse>> continuation);
    
    /**
     * 6. 取消度假模式
     * URL: /home/scene/cancelVacationMode
     * Method: POST
     * 认证: 是
     */
    @org.jetbrains.annotations.Nullable()
    @retrofit2.http.POST(value = "home/scene/cancelVacationMode")
    public abstract java.lang.Object cancelVacationMode(@org.jetbrains.annotations.NotNull()
    @retrofit2.http.Body()
    com.wuheng.smart.data.model.CancelVacationRequest request, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.wuheng.smart.data.network.BaseResponse<kotlin.Unit>> continuation);
    
    /**
     * 1. 获取系统状态
     * URL: /home/system/getSystemStatus
     * Method: GET
     * 认证: 是
     */
    @org.jetbrains.annotations.Nullable()
    @retrofit2.http.GET(value = "home/system/getSystemStatus")
    public abstract java.lang.Object getSystemStatus(@retrofit2.http.Query(value = "house_id")
    int houseId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.wuheng.smart.data.network.BaseResponse<com.wuheng.smart.data.model.SystemStatus>> continuation);
    
    /**
     * 2. 设置系统模式
     * URL: /home/system/setSystemMode
     * Method: POST
     * 认证: 是
     */
    @org.jetbrains.annotations.Nullable()
    @retrofit2.http.POST(value = "home/system/setSystemMode")
    public abstract java.lang.Object setSystemMode(@org.jetbrains.annotations.NotNull()
    @retrofit2.http.Body()
    com.wuheng.smart.data.model.SetSystemModeRequest request, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.wuheng.smart.data.network.BaseResponse<com.wuheng.smart.data.model.SetSystemModeResponse>> continuation);
    
    /**
     * 3. 设置全局温度
     * URL: /home/system/setGlobalTemp
     * Method: POST
     * 认证: 是
     */
    @org.jetbrains.annotations.Nullable()
    @retrofit2.http.POST(value = "home/system/setGlobalTemp")
    public abstract java.lang.Object setGlobalTemp(@org.jetbrains.annotations.NotNull()
    @retrofit2.http.Body()
    com.wuheng.smart.data.model.SetGlobalTempRequest request, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.wuheng.smart.data.network.BaseResponse<kotlin.Unit>> continuation);
    
    /**
     * 4. 设置全局湿度
     * URL: /home/system/setGlobalHumidity
     * Method: POST
     * 认证: 是
     */
    @org.jetbrains.annotations.Nullable()
    @retrofit2.http.POST(value = "home/system/setGlobalHumidity")
    public abstract java.lang.Object setGlobalHumidity(@org.jetbrains.annotations.NotNull()
    @retrofit2.http.Body()
    com.wuheng.smart.data.model.SetGlobalHumidityRequest request, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.wuheng.smart.data.network.BaseResponse<kotlin.Unit>> continuation);
    
    /**
     * 1. 获取天气数据
     * URL: /home/weather/getWeather
     * Method: GET
     * 认证: 否
     */
    @org.jetbrains.annotations.Nullable()
    @retrofit2.http.GET(value = "home/weather/getWeather")
    public abstract java.lang.Object getWeather(@org.jetbrains.annotations.NotNull()
    @retrofit2.http.Query(value = "lat")
    java.lang.String lat, @org.jetbrains.annotations.NotNull()
    @retrofit2.http.Query(value = "lng")
    java.lang.String lng, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.wuheng.smart.data.network.BaseResponse<com.wuheng.smart.data.model.WeatherData>> continuation);
    
    /**
     * 2. 获取室外环境
     * URL: /home/weather/getOutdoorEnv
     * Method: GET
     * 认证: 是
     */
    @org.jetbrains.annotations.Nullable()
    @retrofit2.http.GET(value = "home/weather/getOutdoorEnv")
    public abstract java.lang.Object getOutdoorEnv(@retrofit2.http.Query(value = "house_id")
    int houseId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.wuheng.smart.data.network.BaseResponse<com.wuheng.smart.data.model.OutdoorEnv>> continuation);
    
    /**
     * 1. 获取热水循环状态
     * URL: /home/water/getHeaterStatus
     * Method: GET
     * 认证: 是
     */
    @org.jetbrains.annotations.Nullable()
    @retrofit2.http.GET(value = "home/water/getHeaterStatus")
    public abstract java.lang.Object getHotWaterStatus(@retrofit2.http.Query(value = "house_id")
    int houseId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.wuheng.smart.data.network.BaseResponse<com.wuheng.smart.data.model.HotWaterStatusResponse>> continuation);
    
    /**
     * 2. 设置循环模式
     * URL: /home/water/setCirculationMode
     * Method: POST
     * 认证: 是
     */
    @org.jetbrains.annotations.Nullable()
    @retrofit2.http.POST(value = "home/water/setCirculationMode")
    public abstract java.lang.Object setCirculationMode(@org.jetbrains.annotations.NotNull()
    @retrofit2.http.Body()
    com.wuheng.smart.data.model.SetCirculationModeRequest request, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.wuheng.smart.data.network.BaseResponse<com.wuheng.smart.data.model.SetCirculationModeResponse>> continuation);
    
    /**
     * 4. 获取滤芯状态
     * URL: /home/water/getFilterStatus
     * Method: GET
     * 认证: 是
     */
    @org.jetbrains.annotations.Nullable()
    @retrofit2.http.GET(value = "home/water/getFilterStatus")
    public abstract java.lang.Object getFilterStatus(@retrofit2.http.Query(value = "house_id")
    int houseId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.wuheng.smart.data.network.BaseResponse<java.util.List<com.wuheng.smart.data.model.FilterStatusInfo>>> continuation);
    
    /**
     * 5. 预约滤芯更换
     * URL: /home/water/bookFilterReplace
     * Method: POST
     * 认证: 是
     */
    @org.jetbrains.annotations.Nullable()
    @retrofit2.http.POST(value = "home/water/bookFilterReplace")
    public abstract java.lang.Object bookFilterReplace(@org.jetbrains.annotations.NotNull()
    @retrofit2.http.Body()
    com.wuheng.smart.data.model.BookFilterReplaceRequest request, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.wuheng.smart.data.network.BaseResponse<kotlin.Unit>> continuation);
    
    /**
     * 6. 设置热力杀菌
     * URL: /home/water/setSterilization
     * Method: POST
     * 认证: 是
     */
    @org.jetbrains.annotations.Nullable()
    @retrofit2.http.POST(value = "home/water/setSterilization")
    public abstract java.lang.Object setSterilization(@org.jetbrains.annotations.NotNull()
    @retrofit2.http.Body()
    com.wuheng.smart.data.model.SetSterilizationRequest request, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.wuheng.smart.data.network.BaseResponse<com.wuheng.smart.data.model.SterilizationApiResponse>> continuation);
    
    @org.jetbrains.annotations.Nullable()
    @retrofit2.http.GET(value = "home/notification/getList")
    public abstract java.lang.Object getNotificationList(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.wuheng.smart.data.network.BaseResponse<java.util.List<com.wuheng.smart.data.model.NotificationApiItem>>> continuation);
    
    @org.jetbrains.annotations.Nullable()
    @retrofit2.http.POST(value = "home/notification/markRead")
    public abstract java.lang.Object markNotificationRead(@org.jetbrains.annotations.NotNull()
    @retrofit2.http.Body()
    com.wuheng.smart.data.model.MarkNotificationReadRequest request, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.wuheng.smart.data.network.BaseResponse<kotlin.Unit>> continuation);
    
    @org.jetbrains.annotations.Nullable()
    @retrofit2.http.POST(value = "home/notification/markAllRead")
    public abstract java.lang.Object markAllNotificationsRead(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.wuheng.smart.data.network.BaseResponse<kotlin.Unit>> continuation);
    
    @org.jetbrains.annotations.Nullable()
    @retrofit2.http.POST(value = "home/notification/clearAll")
    public abstract java.lang.Object clearAllNotifications(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.wuheng.smart.data.network.BaseResponse<kotlin.Unit>> continuation);
    
    @org.jetbrains.annotations.Nullable()
    @retrofit2.http.POST(value = "home/service/book")
    public abstract java.lang.Object bookService(@org.jetbrains.annotations.NotNull()
    @retrofit2.http.Body()
    com.wuheng.smart.data.model.BookServiceRequest request, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.wuheng.smart.data.network.BaseResponse<kotlin.Unit>> continuation);
    
    /**
     * 五恒智能控制系统 API 接口服务
     * Base URL: http://116.62.51.112/wuheng_iot/index.php
     */
    @kotlin.Metadata(mv = {1, 7, 1}, k = 3)
    public final class DefaultImpls {
    }
}