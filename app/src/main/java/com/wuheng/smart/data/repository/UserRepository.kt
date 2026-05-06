package com.wuheng.smart.data.repository

import com.wuheng.smart.data.model.*
import com.wuheng.smart.data.network.ApiResult
import com.wuheng.smart.data.network.ApiService
import com.wuheng.smart.data.network.AppException
import com.wuheng.smart.data.network.RetryConfig
import com.wuheng.smart.data.network.TokenManager
import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

/**
 * 登录结果回调接口
 * 用于在登录成功后执行额外操作（如保存用户数据到数据库）
 */
fun interface LoginResultCallback {
    suspend fun onLoginSuccess(response: LoginResponse)
}

/**
 * 用户数据仓库接口
 *
 * 提供用户相关的所有数据操作方法，包括：
 * - 用户认证（登录、注册、登出）
 * - 用户信息管理（获取、更新）
 * - 密码管理（修改密码）
 * - 房屋绑定（获取房屋列表、绑定房屋）
 * - 记住密码功能（保存/清除登录凭证）
 */
interface UserRepository {

    // ==================== 新版API - 用户模块 (8个接口) ====================

    /**
     * 用户登录
     *
     * @param username 用户名或手机号
     * @param password 密码
     * @param callback 登录成功后的回调（可选），用于执行额外操作如保存用户数据到数据库
     * @return 登录响应，包含用户信息和Token
     */
    suspend fun login(
        username: String,
        password: String,
        callback: LoginResultCallback? = null
    ): Flow<ApiResult<LoginResponse>>

    /**
     * 用户注册
     *
     * @param request 注册请求参数
     * @return 注册响应，包含用户ID和Token
     */
    suspend fun register(request: RegisterRequest): Flow<ApiResult<RegisterResponse>>

    /**
     * 用户登出
     * 调用后应清除本地Token
     */
    suspend fun logout(): Flow<ApiResult<Unit>>

    /**
     * 获取当前用户信息
     *
     * @return 用户详细信息
     */
    suspend fun getUserInfo(): Flow<ApiResult<UserInfo>>

    /**
     * 更新用户信息
     *
     * @param request 更新请求参数
     */
    suspend fun updateUserInfo(request: UpdateUserInfoRequest): Flow<ApiResult<Unit>>

    /**
     * 修改密码
     *
     * @param oldPassword 旧密码
     * @param newPassword 新密码（至少6位）
     */
    suspend fun changePassword(oldPassword: String, newPassword: String): Flow<ApiResult<Unit>>

    /**
     * 绑定房屋
     *
     * @param houseId 房屋ID
     * @param bindCode 绑定码（可选）
     */
    suspend fun bindHouse(houseId: String, bindCode: String? = null): Flow<ApiResult<Unit>>

    /**
     * 获取当前用户的房屋列表
     *
     * @return 房屋列表
     */
    suspend fun getMyHouses(): Flow<ApiResult<List<MyHouse>>>

    /**
     * 忘记密码
     *
     * @param mobile 手机号
     * @param newPassword 新密码
     * @return 重置结果
     */
    suspend fun forgotPassword(mobile: String, newPassword: String): Flow<ApiResult<Unit>>

    // ==================== 记住密码功能 ====================

    /**
     * 保存登录凭证到本地
     *
     * @param phone 手机号
     * @param password 密码
     */
    suspend fun saveLoginCredentials(phone: String, password: String)

    /**
     * 清除保存的登录凭证
     */
    suspend fun clearLoginCredentials()

    /**
     * 获取保存的手机号
     * @return 手机号Flow
     */
    fun getSavedPhone(): Flow<String>

    /**
     * 获取保存的密码
     * @return 密码Flow
     */
    fun getSavedPassword(): Flow<String>

    /**
     * 检查是否启用了记住密码
     * @return 是否记住密码Flow
     */
    fun isRememberPassword(): Flow<Boolean>
}

/**
 * 用户数据仓库实现类
 *
 * @param apiService Retrofit API服务
 * @param tokenManager Token管理器，用于登录登出时的Token操作和保存登录凭证
 * @param useMock 是否使用Mock数据，默认false使用真实API
 */
@Singleton
class UserRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val tokenManager: TokenManager,
    private val useMock: Boolean = false
) : BaseRepository(), UserRepository {

    // ==================== 新版API实现 ====================

    override suspend fun login(
        username: String,
        password: String,
        callback: LoginResultCallback?
    ): Flow<ApiResult<LoginResponse>> = apiFlowWithRetry(
        config = RetryConfig.FAST, // 登录使用快速重试
        operation = "login",
        params = "username=$username"
    ) {
        if (useMock) {
            // Mock登录数据
            kotlinx.coroutines.delay(500) // 模拟网络延迟
            val mockResponse = LoginResponse(
                userId = 1,
                userIdNo = "USER202604190001",
                userName = username,
                userTel = "13800138001",
                userToken = "token001",
                userType = 1,
                houseId = 1,
                status = 1
            )
            handleLoginSuccess(mockResponse, callback)
            Timber.d("Mock login success: userId=${mockResponse.userId}")
            ApiResult.Success(mockResponse)
        } else {
            apiCall {
                apiService.login(LoginRequest(username, password))
            }.also { result ->
                if (result is ApiResult.Success) {
                    handleLoginSuccess(result.data, callback)
                    Timber.d("Login success: userId=${result.data.userId}")
                }
            }
        }
    }

    /**
     * 处理登录成功后的通用逻辑
     */
    private suspend fun handleLoginSuccess(
        response: LoginResponse,
        callback: LoginResultCallback?
    ) {
        // 保存Token到本地（将Int类型转换为String存储）
        tokenManager.onLoginSuccess(
            token = response.userToken,
            userId = response.userId.toString(),
            userName = response.userName,
            userType = response.userType.toString(),
            houseId = response.houseId.toString()
        )
        // 执行额外的回调操作（如保存用户数据到数据库）
        callback?.onLoginSuccess(response)
    }

    override suspend fun register(request: RegisterRequest): Flow<ApiResult<RegisterResponse>> = apiFlowWithRetry(
        config = RetryConfig.FAST, // 注册使用快速重试
        operation = "register",
        params = "username=${request.username}, mobile=${request.mobile}"
    ) {
        if (useMock) {
            kotlinx.coroutines.delay(500)
            val mockResponse = RegisterResponse(
                userId = 6,
                userToken = "token_new_user_001"
            )
            ApiResult.Success(mockResponse)
        } else {
            apiCall { apiService.register(request) }
        }
    }

    override suspend fun logout(): Flow<ApiResult<Unit>> = apiFlow(
        operation = "logout"
    ) {
        if (useMock) {
            kotlinx.coroutines.delay(300)
            tokenManager.onLogout()
            ApiResult.Success(Unit)
        } else {
            apiCall { apiService.logout() }.also {
                // 无论API调用成功与否，都清除本地Token
                tokenManager.onLogout()
                Timber.d("Logout completed, token cleared")
            }
        }
    }

    override suspend fun getUserInfo(): Flow<ApiResult<UserInfo>> = apiFlowWithRetry(
        config = RetryConfig.DEFAULT, // 获取用户信息使用默认重试
        operation = "getUserInfo"
    ) {
        if (useMock) {
            kotlinx.coroutines.delay(300)
            val mockUserInfo = UserInfo(
                userId = 1,
                userIdNo = "USER202604190001",
                userName = "张三",
                userTel = "13800138001",
                userType = 1,
                houseId = 1,
                status = 1
            )
            ApiResult.Success(mockUserInfo)
        } else {
            val rawResult = apiCall { apiService.getUserInfo() }
            @Suppress("UNCHECKED_CAST")
            when {
                rawResult is ApiResult.Success && rawResult.data != null -> {
                    val userInfo = when (rawResult.data) {
                        is JsonObject -> {
                            Gson().fromJson(rawResult.data, UserInfo::class.java)
                        }
                        is JsonArray -> {
                            val array = rawResult.data.asJsonArray
                            if (array.size() > 0) {
                                Gson().fromJson(array.get(0), UserInfo::class.java)
                            } else null
                        }
                        else -> null
                    }
                    if (userInfo != null) {
                        ApiResult.Success(userInfo)
                    } else {
                        ApiResult.Error(AppException.BusinessError(400, "用户信息为空"))
                    }
                }
                rawResult is ApiResult.Error -> rawResult
                else -> ApiResult.Error(AppException.UnknownError("未知错误"))
            }
        }
    }

    override suspend fun updateUserInfo(request: UpdateUserInfoRequest): Flow<ApiResult<Unit>> = apiFlowWithRetry(
        config = RetryConfig.DEFAULT, // 更新用户信息使用默认重试
        operation = "updateUserInfo",
        params = "realname=${request.realname}, email=${request.email}"
    ) {
        if (useMock) {
            kotlinx.coroutines.delay(300)
            ApiResult.Success(Unit)
        } else {
            apiCall { apiService.updateUserInfo(request) }
        }
    }

    override suspend fun changePassword(oldPassword: String, newPassword: String): Flow<ApiResult<Unit>> = apiFlow(
        operation = "changePassword"
    ) {
        if (useMock) {
            kotlinx.coroutines.delay(300)
            ApiResult.Success(Unit)
        } else {
            apiCall { apiService.changePassword(ChangePasswordRequest(oldPassword, newPassword)) }
        }
    }

    override suspend fun bindHouse(houseId: String, bindCode: String?): Flow<ApiResult<Unit>> = apiFlow(
        operation = "bindHouse",
        params = "houseId=$houseId, bindCode=$bindCode"
    ) {
        if (useMock) {
            kotlinx.coroutines.delay(300)
            tokenManager.setCurrentHouseId(houseId)
            ApiResult.Success(Unit)
        } else {
            apiCall { apiService.bindHouse(BindHouseRequest(houseId.toInt(), bindCode)) }.also {
                if (it is ApiResult.Success) {
                    tokenManager.setCurrentHouseId(houseId)
                }
            }
        }
    }

    override suspend fun getMyHouses(): Flow<ApiResult<List<MyHouse>>> = apiFlow(
        operation = "getMyHouses"
    ) {
        if (useMock) {
            kotlinx.coroutines.delay(300)
            val mockHouses = listOf(
                MyHouse(
                    houseId = 1,
                    houseIdNo = "HOUSE202604190001",
                    houseName = "阳光花园别墅",
                    address = "浙江省杭州市西湖区文三路123号",
                    ownerName = "张三",
                    areaTotal = "280.00",
                    systemType = "辐射空调系统",
                    bindType = "owner",
                    bindTime = System.currentTimeMillis() / 1000,
                    systemMode = "cooling",
                    systemRunStatus = "running",
                    indoorTemp = "24.50",
                    indoorHumidity = "45.20",
                    outdoorTemp = "26.00",
                    deviceCount = 6,
                    onlineCount = 5,
                    alarmCount = 0
                )
            )
            ApiResult.Success(mockHouses)
        } else {
            apiCall { apiService.getMyHouses() }
        }
    }

    override suspend fun forgotPassword(
        mobile: String,
        newPassword: String
    ): Flow<ApiResult<Unit>> = apiFlow(
        operation = "forgotPassword",
        params = "mobile=$mobile"
    ) {
        if (useMock) {
            kotlinx.coroutines.delay(1000)
            Timber.d("Mock forgot password success: mobile=$mobile")
            ApiResult.Success(Unit)
        } else {
            apiCall {
                apiService.forgotPassword(ForgotPasswordRequest(mobile, newPassword))
            }
        }
    }

    // ==================== 记住密码功能实现 ====================

    override suspend fun saveLoginCredentials(phone: String, password: String) {
        tokenManager.saveLoginCredentials(phone, password)
        Timber.d("Login credentials saved for phone: $phone")
    }

    override suspend fun clearLoginCredentials() {
        tokenManager.clearLoginCredentials()
        Timber.d("Login credentials cleared")
    }

    override fun getSavedPhone(): Flow<String> {
        return tokenManager.getSavedPhone()
    }

    override fun getSavedPassword(): Flow<String> {
        return tokenManager.getSavedPassword()
    }

    override fun isRememberPassword(): Flow<Boolean> {
        return tokenManager.isRememberPassword()
    }
}
