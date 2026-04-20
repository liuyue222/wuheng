package com.wuheng.smart.data.network

import com.google.gson.annotations.SerializedName

data class BaseResponse<T>(
    @SerializedName("code")
    val code: Int,
    @SerializedName("msg")
    val message: String,
    @SerializedName("data")
    val data: T?
) {
    fun isSuccess(): Boolean = code == SUCCESS_CODE

    companion object {
        const val SUCCESS_CODE = 200
        const val TOKEN_EXPIRED_CODE = 401
        const val SERVER_ERROR_CODE = 500
    }
}
