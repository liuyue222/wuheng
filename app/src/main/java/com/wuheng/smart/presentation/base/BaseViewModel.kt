package com.wuheng.smart.presentation.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wuheng.smart.data.network.ApiResult
import com.wuheng.smart.data.network.AppException
import com.wuheng.smart.data.network.AuthEvent
import com.wuheng.smart.data.network.AuthEventManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber

typealias UiState<T> = StateFlow<UiDataState<T>>

sealed class UiDataState<out T> {
    object Loading : UiDataState<Nothing>()
    data class LoadingWithData<T>(val data: T) : UiDataState<T>()
    data class Success<T>(val data: T) : UiDataState<T>()
    data class Error(val exception: AppException) : UiDataState<Nothing>()
    data class ErrorWithData<T>(val exception: AppException, val data: T) : UiDataState<T>()
    object Idle : UiDataState<Nothing>()

    /**
     * 获取当前状态中的数据（如果有）
     */
    fun getDataOrNull(): T? = when (this) {
        is Success -> data
        is LoadingWithData -> data
        is ErrorWithData -> data
        else -> null
    }

    /**
     * 检查是否为加载状态（包括带数据的加载）
     */
    fun isLoading(): Boolean = this is Loading || this is LoadingWithData

    /**
     * 检查是否为错误状态（包括带数据的错误）
     */
    fun isError(): Boolean = this is Error || this is ErrorWithData
}

/**
 * 401 错误处理策略
 */
sealed class UnauthorizedStrategy {
    /**
     * 自动处理 - 发送 401 事件通知 UI 层跳转登录页
     */
    object AutoHandle : UnauthorizedStrategy()

    /**
     * 手动处理 - 由调用方自行处理 401 错误
     */
    object ManualHandle : UnauthorizedStrategy()
}

abstract class BaseViewModel : ViewModel() {

    /**
     * 统一的请求处理方法
     * 自动处理 401 错误，发送事件通知 UI 层跳转到登录页
     *
     * @param request 请求函数
     * @param unauthorizedStrategy 401 处理策略，默认为自动处理
     * @param onSuccess 成功回调
     * @param onError 错误回调（不包括已自动处理的 401 错误）
     */
    protected fun <T> MutableStateFlow<UiDataState<T>>.launchRequest(
        request: suspend () -> ApiResult<T>,
        unauthorizedStrategy: UnauthorizedStrategy = UnauthorizedStrategy.AutoHandle,
        onSuccess: ((T) -> Unit)? = null,
        onError: ((AppException) -> Unit)? = null
    ) {
        viewModelScope.launch {
            this@launchRequest.value = UiDataState.Loading

            when (val result = request()) {
                is ApiResult.Success -> {
                    this@launchRequest.value = UiDataState.Success(result.data)
                    onSuccess?.invoke(result.data)
                }
                is ApiResult.Error -> {
                    val exception = result.exception

                    // 处理 401 Unauthorized 错误
                    if (exception is AppException.Unauthorized) {
                        handleUnauthorizedError(unauthorizedStrategy, exception)
                    }

                    // 如果不是自动处理 401，或者不是 401 错误，则更新 UI 状态
                    if (exception !is AppException.Unauthorized ||
                        unauthorizedStrategy is UnauthorizedStrategy.ManualHandle) {
                        this@launchRequest.value = UiDataState.Error(exception)
                        onError?.invoke(exception)
                    }
                }
                is ApiResult.Loading -> {
                    this@launchRequest.value = UiDataState.Loading
                }
            }
        }
    }

    /**
     * 处理 401 Unauthorized 错误
     */
    private fun handleUnauthorizedError(
        strategy: UnauthorizedStrategy,
        exception: AppException.Unauthorized
    ) {
        when (strategy) {
            is UnauthorizedStrategy.AutoHandle -> {
                Timber.w("BaseViewModel: 401 Unauthorized detected, posting auth event")
                // 发送 401 事件，通知 UI 层跳转到登录页
                AuthEventManager.postAuthEvent(AuthEvent.Unauthorized)
            }
            is UnauthorizedStrategy.ManualHandle -> {
                // 手动处理，不发送事件，由调用方自行处理
                Timber.d("BaseViewModel: 401 Unauthorized detected, manual handling")
            }
        }
    }

    /**
     * 兼容旧版本的方法签名
     */
    protected fun <T> MutableStateFlow<UiDataState<T>>.launchRequest(
        request: suspend () -> ApiResult<T>,
        onSuccess: ((T) -> Unit)? = null,
        onError: ((AppException) -> Unit)? = null
    ) {
        launchRequest(
            request = request,
            unauthorizedStrategy = UnauthorizedStrategy.AutoHandle,
            onSuccess = onSuccess,
            onError = onError
        )
    }

    protected fun <T> MutableStateFlow<UiDataState<T>>.setSuccess(data: T) {
        this.value = UiDataState.Success(data)
    }

    protected fun <T> MutableStateFlow<UiDataState<T>>.setError(exception: AppException) {
        this.value = UiDataState.Error(exception)
    }

    protected fun <T> MutableStateFlow<UiDataState<T>>.setLoading() {
        this.value = UiDataState.Loading
    }

    protected fun <T> MutableStateFlow<UiDataState<T>>.setIdle() {
        this.value = UiDataState.Idle
    }
}

fun <T> createUiStateFlow(): MutableStateFlow<UiDataState<T>> =
    MutableStateFlow(UiDataState.Idle)

/**
 * 处理UI状态 - 重命名以避免与Compose的collectAsState()冲突
 */
inline fun <T> UiState<T>.handleUiState(
    onLoading: () -> Unit = {},
    onSuccess: (T) -> Unit = {},
    onError: (AppException) -> Unit = {},
    onIdle: () -> Unit = {}
) {
    when (val state = this.value) {
        is UiDataState.Loading -> onLoading()
        is UiDataState.LoadingWithData -> onLoading()
        is UiDataState.Success -> onSuccess(state.data)
        is UiDataState.Error -> onError(state.exception)
        is UiDataState.ErrorWithData -> onError(state.exception)
        is UiDataState.Idle -> onIdle()
    }
}

/**
 * 扩展函数：在Compose中收集StateFlow并自动处理生命周期
 * 注意：这个函数名与Compose的collectAsState不同，避免冲突
 */
@Deprecated("使用 handleUiState 替代", ReplaceWith("handleUiState(onLoading, onSuccess, onError, onIdle)"))
inline fun <T> UiState<T>.collectAsState(
    onLoading: () -> Unit = {},
    onSuccess: (T) -> Unit = {},
    onError: (AppException) -> Unit = {},
    onIdle: () -> Unit = {}
) = handleUiState(onLoading, onSuccess, onError, onIdle)
