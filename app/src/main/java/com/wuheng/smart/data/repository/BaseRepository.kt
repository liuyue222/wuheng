package com.wuheng.smart.data.repository

import com.wuheng.smart.data.mock.MockDataSource
import com.wuheng.smart.data.network.ApiResult
import com.wuheng.smart.data.network.AppException
import com.wuheng.smart.data.network.BaseResponse
import com.wuheng.smart.data.network.safeApiCall
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import timber.log.Timber

/**
 * Repository基类
 *
 * 提供通用的API调用封装和Mock数据支持
 * 所有Repository实现类都应继承此类以获得统一的错误处理和日志记录能力
 */
abstract class BaseRepository {

    /**
     * 执行API调用，自动处理异常
     *
     * @param apiCall 实际的API调用suspend函数
     * @return 包装后的ApiResult
     */
    protected suspend fun <T> apiCall(
        apiCall: suspend () -> BaseResponse<T>
    ): ApiResult<T> {
        return safeApiCall(apiCall = apiCall)
    }

    /**
     * 通用的API Flow构建器
     * 自动处理Loading状态和结果发射
     *
     * @param operation 操作名称，用于日志记录
     * @param params 操作参数，用于日志记录（可选）
     * @param block 实际的API调用逻辑，返回ApiResult
     * @return Flow<ApiResult<T>> 自动发射Loading和结果的Flow
     */
    protected inline fun <T> apiFlow(
        operation: String,
        params: String? = null,
        crossinline block: suspend () -> ApiResult<T>
    ): Flow<ApiResult<T>> = flow {
        logOperation(operation, params)
        emit(ApiResult.Loading)
        emit(block())
    }

    /**
     * 带Mock支持的API调用
     * 根据useMock参数决定使用Mock数据还是真实API
     *
     * @param useMock 是否使用Mock数据
     * @param mockCall Mock数据源调用
     * @param apiCall 真实API调用
     * @return 包装后的ApiResult
     */
    protected suspend fun <T> apiCallWithMock(
        useMock: Boolean,
        mockCall: suspend () -> Flow<BaseResponse<T>>,
        apiCall: suspend () -> BaseResponse<T>
    ): ApiResult<T> {
        return if (useMock) {
            try {
                Timber.d("Using mock data")
                val response = mockCall().first()
                if (response.data != null) {
                    ApiResult.Success(response.data)
                } else {
                    ApiResult.Error(AppException.UnknownError("Mock data is null"))
                }
            } catch (e: Exception) {
                Timber.e(e, "Mock data error")
                ApiResult.Error(AppException.UnknownError(e.message ?: "Mock data error"))
            }
        } else {
            safeApiCall(apiCall = apiCall)
        }
    }

    /**
     * 带Mock支持的Unit类型API调用
     *
     * @param useMock 是否使用Mock数据
     * @param mockCall Mock数据源调用
     * @param apiCall 真实API调用
     * @return 包装后的ApiResult<Unit>
     */
    protected suspend fun apiCallWithMockUnit(
        useMock: Boolean,
        mockCall: suspend () -> Flow<BaseResponse<Unit>>,
        apiCall: suspend () -> BaseResponse<Unit>
    ): ApiResult<Unit> {
        return if (useMock) {
            try {
                Timber.d("Using mock data (Unit)")
                mockCall().first()
                ApiResult.Success(Unit)
            } catch (e: Exception) {
                Timber.e(e, "Mock data error")
                ApiResult.Error(AppException.UnknownError(e.message ?: "Mock data error"))
            }
        } else {
            safeApiCall(apiCall = apiCall)
        }
    }

    /**
     * 直接返回Mock数据（用于简单的Mock场景）
     *
     * @param mockDataProvider 提供Mock数据的lambda
     * @return 包装后的ApiResult.Success
     */
    protected fun <T> mockResult(mockDataProvider: () -> T): ApiResult<T> {
        return try {
            ApiResult.Success(mockDataProvider())
        } catch (e: Exception) {
            Timber.e(e, "Mock data provider error")
            ApiResult.Error(AppException.UnknownError(e.message ?: "Mock data error"))
        }
    }

    /**
     * 记录Repository操作日志
     *
     * @param operation 操作名称
     * @param params 操作参数（可选）
     */
    protected fun logOperation(operation: String, params: String? = null) {
        if (params != null) {
            Timber.d("Repository Operation: $operation, Params: $params")
        } else {
            Timber.d("Repository Operation: $operation")
        }
    }

    /**
     * 记录错误日志
     *
     * @param operation 操作名称
     * @param error 错误信息
     */
    protected fun logError(operation: String, error: Throwable) {
        Timber.e(error, "Repository Error in $operation: ${error.message}")
    }
}
