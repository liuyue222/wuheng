package com.wuheng.smart.presentation.profile

import androidx.lifecycle.viewModelScope
import com.wuheng.smart.BuildConfig
import com.wuheng.smart.data.network.ApiResult
import com.wuheng.smart.data.network.TokenManager
import com.wuheng.smart.data.repository.HomeRepository
import com.wuheng.smart.data.repository.UserRepository
import com.wuheng.smart.data.repository.WaterRepository
import com.wuheng.smart.presentation.base.BaseViewModel
import com.wuheng.smart.presentation.base.UiDataState
import com.wuheng.smart.presentation.base.createUiStateFlow
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val tokenManager: TokenManager,
    private val userRepository: UserRepository,
    private val waterRepository: WaterRepository,
    private val homeRepository: HomeRepository
) : BaseViewModel() {

    private val _uiState = MutableStateFlow(ProfileUiState())
    val uiState: StateFlow<ProfileUiState> = _uiState.asStateFlow()

    private val _bookingState = MutableStateFlow<UiDataState<Unit>>(UiDataState.Idle)
    val bookingState: StateFlow<UiDataState<Unit>> = _bookingState.asStateFlow()

    init {
        loadUserInfo()
        loadMaintenanceLog()
    }

    private fun loadUserInfo() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, errorMessage = null)

            userRepository.getUserInfo().collect { result ->
                when (result) {
                    is ApiResult.Success -> {
                        val userInfo = result.data
                        _uiState.value = _uiState.value.copy(
                            isLoading = false,
                            errorMessage = null,
                            userName = userInfo.userName,
                            role = when (userInfo.userType) {
                                1 -> "业主"
                                2 -> "租户"
                                3 -> "管理员"
                                else -> "用户"
                            }
                        )
                        Timber.d("User info loaded: userId=${userInfo.userId}, userName=${userInfo.userName}")
                    }
                    is ApiResult.Error -> {
                        val errorMsg = result.exception.message ?: "获取用户信息失败"
                        _uiState.value = _uiState.value.copy(
                            isLoading = false,
                            errorMessage = if (_uiState.value.userName.isEmpty()) errorMsg else null,
                            userName = _uiState.value.userName.ifEmpty { "用户" }
                        )
                        Timber.e("Failed to load user info: $errorMsg")
                    }
                    is ApiResult.Loading -> {
                        _uiState.value = _uiState.value.copy(isLoading = true)
                    }
                }
            }
        }
    }

    private fun loadMaintenanceLog() {
        val houseIdStr = tokenManager.getCurrentHouseId()
        if (houseIdStr.isEmpty()) return
        val houseId = houseIdStr.toIntOrNull() ?: return

        viewModelScope.launch {
            homeRepository.getMaintenanceLog(houseId).collectLatest { result ->
                when (result) {
                    is ApiResult.Success -> {
                        val logs = result.data
                        if (logs.isNotEmpty()) {
                            val latestLog = logs.maxByOrNull { it.serviceDate }
                            if (latestLog != null) {
                                _uiState.value = _uiState.value.copy(
                                    lastServiceDate = latestLog.serviceDate
                                )
                            }
                        }
                    }
                    is ApiResult.Error -> {
                        Timber.e(result.exception, "Failed to load maintenance log")
                    }
                    is ApiResult.Loading -> {}
                }
            }
        }
    }

    fun refresh() {
        Timber.d("Refreshing profile data")
        loadUserInfo()
        loadMaintenanceLog()
    }

    fun refreshData() {
        refresh()
    }

    fun retry() {
        Timber.d("Retry loading profile data")
        loadUserInfo()
    }

    fun clearError() {
        _uiState.value = _uiState.value.copy(errorMessage = null)
    }

    fun selectServiceType(type: ServiceType) {
        _uiState.value = _uiState.value.copy(selectedServiceType = type)
        Timber.d("Selected service type: ${type.displayName}")
    }

    fun confirmBooking() {
        val serviceType = _uiState.value.selectedServiceType
        if (serviceType == null) {
            Timber.w("No service type selected")
            return
        }

        if (serviceType == ServiceType.FILTER_REPLACEMENT) {
            bookFilterReplaceFromProfile()
            return
        }

        val houseIdStr = tokenManager.getCurrentHouseId()
        if (houseIdStr.isEmpty()) {
            _bookingState.value = UiDataState.Error(
                com.wuheng.smart.data.network.AppException.BusinessError(-1, "未选择房屋")
            )
            return
        }
        val houseId = houseIdStr.toIntOrNull() ?: return

        val contactName = "刘大大"
        val contactPhone = "15900474254"
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val appointmentDate = dateFormat.format(Date())

        viewModelScope.launch {
            _bookingState.value = UiDataState.Loading

            homeRepository.bookService(
                houseId = houseId,
                serviceType = serviceType.toApiValue(),
                contactName = contactName,
                contactPhone = contactPhone,
                appointmentDate = appointmentDate,
                remark = null
            ).collectLatest { result ->
                when (result) {
                    is ApiResult.Loading -> {
                        _bookingState.value = UiDataState.Loading
                    }
                    is ApiResult.Success -> {
                        _bookingState.value = UiDataState.Success(Unit)
                        Timber.d("Service booking successful: ${serviceType.displayName}")
                    }
                    is ApiResult.Error -> {
                        _bookingState.value = UiDataState.Error(result.exception)
                        Timber.e(result.exception, "Service booking failed")
                    }
                }
            }
        }
    }

    private fun bookFilterReplaceFromProfile() {
        val houseId = tokenManager.getCurrentHouseId()
        if (houseId.isEmpty()) {
            _bookingState.value = UiDataState.Error(
                com.wuheng.smart.data.network.AppException.BusinessError(-1, "未选择房屋")
            )
            return
        }

        viewModelScope.launch {
            _bookingState.value = UiDataState.Loading

            try {
                val houseIdInt = houseId.toIntOrNull()
                if (houseIdInt == null) {
                    _bookingState.value = UiDataState.Error(
                        com.wuheng.smart.data.network.AppException.BusinessError(-1, "无效的房屋ID")
                    )
                    return@launch
                }

                waterRepository.bookFilterReplace(
                    houseIdInt,
                    filterId = 0,
                    contactName = null,
                    contactPhone = null,
                    appointmentDate = null
                ).collectLatest { result ->
                    when (result) {
                        is ApiResult.Loading -> {
                            _bookingState.value = UiDataState.Loading
                        }
                        is ApiResult.Success -> {
                            _bookingState.value = UiDataState.Success(Unit)
                            Timber.d("Filter replacement booking successful (profile)")
                        }
                        is ApiResult.Error -> {
                            _bookingState.value = UiDataState.Error(result.exception)
                        }
                    }
                }
            } catch (e: Exception) {
                Timber.e(e, "Error booking filter replacement from profile")
                _bookingState.value = UiDataState.Error(
                    com.wuheng.smart.data.network.AppException.UnknownError(e.message ?: "预约失败")
                )
            }
        }
    }

    fun resetBookingState() {
        _bookingState.value = UiDataState.Idle
    }

    fun logout() {
        tokenManager.onLogout()
        Timber.d("User logged out")
    }
}

data class ProfileUiState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val userName: String = "",
    val residenceName: String = "",
    val role: String = "",
    val hasNotification: Boolean = false,
    val projectDescription: String = "五恒智能控制系统采用先进的辐射空调技术，为您提供恒温、恒湿、恒氧、恒洁、恒静的舒适居住环境。",
    val lastServiceDate: String = "2024-03-15",
    val version: String = BuildConfig.VERSION_NAME,
    val selectedServiceType: ServiceType? = null
)
