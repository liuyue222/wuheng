package com.wuheng.smart.data.network;

import com.wuheng.smart.data.model.*;
import retrofit2.http.*;

/**
 * 五恒智能控制系统 API 接口服务
 * Base URL: http://116.62.51.112/wuheng_iot/index.php
 */
@kotlin.Metadata(mv = {1, 7, 1}, k = 1, d1 = {"\u0000\u00ea\u0001\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bf\u0018\u00002\u00020\u0001J!\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\b\b\u0001\u0010\u0005\u001a\u00020\u0006H\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0007J!\u0010\b\u001a\b\u0012\u0004\u0012\u00020\t0\u00032\b\b\u0001\u0010\u0005\u001a\u00020\nH\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u000bJ!\u0010\f\u001a\b\u0012\u0004\u0012\u00020\t0\u00032\b\b\u0001\u0010\u0005\u001a\u00020\rH\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u000eJ!\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\t0\u00032\b\b\u0001\u0010\u0005\u001a\u00020\u0010H\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0011J!\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00130\u00032\b\b\u0001\u0010\u0005\u001a\u00020\u0014H\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0015J!\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00170\u00032\b\b\u0001\u0010\u0018\u001a\u00020\u0019H\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u001aJ!\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u001c0\u00032\b\b\u0001\u0010\u0018\u001a\u00020\u0019H\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u001aJ3\u0010\u001d\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u001c0\u001e0\u00032\b\b\u0001\u0010\u001f\u001a\u00020\u00192\n\b\u0003\u0010 \u001a\u0004\u0018\u00010\u0019H\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010!J\'\u0010\"\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020#0\u001e0\u00032\b\b\u0001\u0010\u001f\u001a\u00020\u0019H\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u001aJ\'\u0010$\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020%0\u001e0\u00032\b\b\u0001\u0010\u001f\u001a\u00020\u0019H\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u001aJ!\u0010&\u001a\b\u0012\u0004\u0012\u00020\'0\u00032\b\b\u0001\u0010\u001f\u001a\u00020\u0019H\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u001aJ!\u0010(\u001a\b\u0012\u0004\u0012\u00020)0\u00032\b\b\u0001\u0010\u001f\u001a\u00020\u0019H\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u001aJ\u001d\u0010*\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020+0\u001e0\u0003H\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010,J3\u0010-\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020.0\u001e0\u00032\b\b\u0001\u0010\u001f\u001a\u00020\u00192\n\b\u0003\u0010/\u001a\u0004\u0018\u00010\u0019H\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010!J\'\u00100\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002010\u001e0\u00032\b\b\u0001\u0010\u001f\u001a\u00020\u0019H\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u001aJ!\u00102\u001a\b\u0012\u0004\u0012\u0002030\u00032\b\b\u0001\u0010\u001f\u001a\u00020\u0019H\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u001aJ\u0017\u00104\u001a\b\u0012\u0004\u0012\u0002050\u0003H\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010,J!\u00106\u001a\b\u0012\u0004\u0012\u0002070\u00032\b\b\u0001\u0010\u0005\u001a\u000208H\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u00109J\u0017\u0010:\u001a\b\u0012\u0004\u0012\u00020\t0\u0003H\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010,J!\u0010;\u001a\b\u0012\u0004\u0012\u00020<0\u00032\b\b\u0001\u0010\u0005\u001a\u00020=H\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010>J!\u0010?\u001a\b\u0012\u0004\u0012\u00020\t0\u00032\b\b\u0001\u0010\u0005\u001a\u00020@H\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010AJ!\u0010B\u001a\b\u0012\u0004\u0012\u00020C0\u00032\b\b\u0001\u0010\u0005\u001a\u00020DH\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010EJ!\u0010F\u001a\b\u0012\u0004\u0012\u00020\t0\u00032\b\b\u0001\u0010\u0005\u001a\u00020GH\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010HJ!\u0010I\u001a\b\u0012\u0004\u0012\u00020\t0\u00032\b\b\u0001\u0010\u0005\u001a\u00020JH\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010KJ!\u0010L\u001a\b\u0012\u0004\u0012\u00020M0\u00032\b\b\u0001\u0010\u0005\u001a\u00020NH\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010OJ!\u0010P\u001a\b\u0012\u0004\u0012\u00020\t0\u00032\b\b\u0001\u0010\u0005\u001a\u00020QH\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010R\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006S"}, d2 = {"Lcom/wuheng/smart/data/network/ApiService;", "", "applyScene", "Lcom/wuheng/smart/data/network/BaseResponse;", "Lcom/wuheng/smart/data/model/ApplySceneResponse;", "request", "Lcom/wuheng/smart/data/model/ApplySceneRequest;", "(Lcom/wuheng/smart/data/model/ApplySceneRequest;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "bindHouse", "", "Lcom/wuheng/smart/data/model/BindHouseRequest;", "(Lcom/wuheng/smart/data/model/BindHouseRequest;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "bookFilterReplace", "Lcom/wuheng/smart/data/model/BookFilterReplaceRequest;", "(Lcom/wuheng/smart/data/model/BookFilterReplaceRequest;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "changePassword", "Lcom/wuheng/smart/data/model/ChangePasswordRequest;", "(Lcom/wuheng/smart/data/model/ChangePasswordRequest;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "controlDevice", "Lcom/wuheng/smart/data/model/ControlDeviceResponse;", "Lcom/wuheng/smart/data/model/ControlDeviceRequest;", "(Lcom/wuheng/smart/data/model/ControlDeviceRequest;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getDeviceData", "Lcom/wuheng/smart/data/model/DeviceData;", "deviceId", "", "(ILkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getDeviceInfo", "Lcom/wuheng/smart/data/model/DeviceInfo;", "getDeviceList", "", "houseId", "roomId", "(ILjava/lang/Integer;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getFilterStatus", "Lcom/wuheng/smart/data/model/FilterStatusInfo;", "getFloorList", "Lcom/wuheng/smart/data/model/FloorInfo;", "getHeaterStatus", "Lcom/wuheng/smart/data/model/HeaterStatus;", "getHouseInfo", "Lcom/wuheng/smart/data/model/HouseInfo;", "getMyHouses", "Lcom/wuheng/smart/data/model/MyHouse;", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getRoomList", "Lcom/wuheng/smart/data/model/RoomInfo;", "floorId", "getSceneList", "Lcom/wuheng/smart/data/model/SceneInfo;", "getSystemStatus", "Lcom/wuheng/smart/data/model/SystemStatus;", "getUserInfo", "Lcom/wuheng/smart/data/model/UserInfo;", "login", "Lcom/wuheng/smart/data/model/LoginResponse;", "Lcom/wuheng/smart/data/model/LoginRequest;", "(Lcom/wuheng/smart/data/model/LoginRequest;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "logout", "register", "Lcom/wuheng/smart/data/model/RegisterResponse;", "Lcom/wuheng/smart/data/model/RegisterRequest;", "(Lcom/wuheng/smart/data/model/RegisterRequest;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "saveScene", "Lcom/wuheng/smart/data/model/SaveSceneRequest;", "(Lcom/wuheng/smart/data/model/SaveSceneRequest;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "setCirculationMode", "Lcom/wuheng/smart/data/model/SetCirculationModeResponse;", "Lcom/wuheng/smart/data/model/SetCirculationModeRequest;", "(Lcom/wuheng/smart/data/model/SetCirculationModeRequest;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "setGlobalHumidity", "Lcom/wuheng/smart/data/model/SetGlobalHumidityRequest;", "(Lcom/wuheng/smart/data/model/SetGlobalHumidityRequest;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "setGlobalTemp", "Lcom/wuheng/smart/data/model/SetGlobalTempRequest;", "(Lcom/wuheng/smart/data/model/SetGlobalTempRequest;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "setSystemMode", "Lcom/wuheng/smart/data/model/SetSystemModeResponse;", "Lcom/wuheng/smart/data/model/SetSystemModeRequest;", "(Lcom/wuheng/smart/data/model/SetSystemModeRequest;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "updateUserInfo", "Lcom/wuheng/smart/data/model/UpdateUserInfoRequest;", "(Lcom/wuheng/smart/data/model/UpdateUserInfoRequest;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_debug"})
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
     */
    @org.jetbrains.annotations.Nullable()
    @retrofit2.http.GET(value = "home/user/getUserInfo")
    public abstract java.lang.Object getUserInfo(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.wuheng.smart.data.network.BaseResponse<com.wuheng.smart.data.model.UserInfo>> continuation);
    
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
     * 2. 获取楼层列表
     * URL: /home/house/getFloorList
     * Method: GET
     * 认证: 是
     */
    @org.jetbrains.annotations.Nullable()
    @retrofit2.http.GET(value = "home/house/getFloorList")
    public abstract java.lang.Object getFloorList(@retrofit2.http.Query(value = "house_id")
    int houseId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.wuheng.smart.data.network.BaseResponse<java.util.List<com.wuheng.smart.data.model.FloorInfo>>> continuation);
    
    /**
     * 3. 获取房间列表
     * URL: /home/house/getRoomList
     * Method: GET
     * 认证: 是
     */
    @org.jetbrains.annotations.Nullable()
    @retrofit2.http.GET(value = "home/house/getRoomList")
    public abstract java.lang.Object getRoomList(@retrofit2.http.Query(value = "house_id")
    int houseId, @org.jetbrains.annotations.Nullable()
    @retrofit2.http.Query(value = "floor_id")
    java.lang.Integer floorId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.wuheng.smart.data.network.BaseResponse<java.util.List<com.wuheng.smart.data.model.RoomInfo>>> continuation);
    
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
    public abstract java.lang.Object getDeviceInfo(@retrofit2.http.Query(value = "device_id")
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
    public abstract java.lang.Object getDeviceData(@retrofit2.http.Query(value = "device_id")
    int deviceId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.wuheng.smart.data.network.BaseResponse<com.wuheng.smart.data.model.DeviceData>> continuation);
    
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
     * 1. 获取热水循环状态
     * URL: /home/water/getHeaterStatus
     * Method: GET
     * 认证: 是
     */
    @org.jetbrains.annotations.Nullable()
    @retrofit2.http.GET(value = "home/water/getHeaterStatus")
    public abstract java.lang.Object getHeaterStatus(@retrofit2.http.Query(value = "house_id")
    int houseId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.wuheng.smart.data.network.BaseResponse<com.wuheng.smart.data.model.HeaterStatus>> continuation);
    
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
     * 3. 获取滤芯状态
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
     * 4. 预约滤芯更换
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
     * 五恒智能控制系统 API 接口服务
     * Base URL: http://116.62.51.112/wuheng_iot/index.php
     */
    @kotlin.Metadata(mv = {1, 7, 1}, k = 3)
    public final class DefaultImpls {
    }
}