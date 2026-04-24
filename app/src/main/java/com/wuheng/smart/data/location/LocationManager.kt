package com.wuheng.smart.data.location

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.os.Looper
import androidx.core.content.ContextCompat
import com.google.android.gms.location.*
import kotlinx.coroutines.*
import kotlinx.coroutines.suspendCancellableCoroutine
import timber.log.Timber
import java.util.Locale
import kotlin.coroutines.resume

/**
 * 定位管理器
 * 用于获取用户当前位置和地址信息
 */
class LocationManager(private val context: Context) {

    companion object {
        private const val LOCATION_TIMEOUT_MS = 10000L // 10秒超时
        private const val DEFAULT_CITY = "杭州市"
        private const val DEFAULT_DISTRICT = "余杭区"
        private const val DEFAULT_ADDRESS = "$DEFAULT_CITY · $DEFAULT_DISTRICT"
        // 杭州市中心坐标（西湖附近）
        private const val DEFAULT_LATITUDE = 30.2741
        private const val DEFAULT_LONGITUDE = 120.1551
    }

    private val fusedLocationClient: FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(context)

    /**
     * 检查是否有定位权限
     */
    fun hasLocationPermission(): Boolean {
        return ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED
    }

    /**
     * 获取当前位置（如果lastLocation为空，则请求新位置）
     * 带超时机制，超时后返回默认位置
     */
    suspend fun getCurrentLocation(): Location? {
        if (!hasLocationPermission()) {
            Timber.w("没有定位权限，返回默认位置")
            return getDefaultLocation()
        }

        return try {
            withTimeout(LOCATION_TIMEOUT_MS) {
                suspendCancellableCoroutine { continuation ->
                    try {
                        // 先尝试获取lastLocation
                        fusedLocationClient.lastLocation
                            .addOnSuccessListener { location ->
                                if (location != null) {
                                    Timber.d("获取到lastLocation: ${location.latitude}, ${location.longitude}")
                                    continuation.resume(location)
                                } else {
                                    // lastLocation为空，请求新位置
                                    requestNewLocation(continuation)
                                }
                            }
                            .addOnFailureListener { e ->
                                Timber.e(e, "获取lastLocation失败")
                                requestNewLocation(continuation)
                            }
                    } catch (e: SecurityException) {
                        Timber.e(e, "定位权限异常")
                        continuation.resume(null)
                    }

                    // 当协程被取消时，清理资源
                    continuation.invokeOnCancellation {
                        Timber.d("定位协程被取消")
                    }
                }
            }
        } catch (e: TimeoutCancellationException) {
            Timber.w("定位超时，使用默认位置")
            getDefaultLocation()
        } catch (e: Exception) {
            Timber.e(e, "获取位置失败，使用默认位置")
            getDefaultLocation()
        }
    }

    /**
     * 获取默认位置（杭州市中心）
     */
    private fun getDefaultLocation(): Location {
        return Location("default").apply {
            latitude = DEFAULT_LATITUDE
            longitude = DEFAULT_LONGITUDE
        }
    }

    /**
     * 请求新位置
     */
    private fun requestNewLocation(continuation: kotlin.coroutines.Continuation<Location?>) {
        try {
            val locationRequest = LocationRequest.create().apply {
                priority = LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY
                interval = 0
                numUpdates = 1
                // 设置最快等待时间，避免长时间等待
                fastestInterval = 0
            }

            val locationCallback = object : LocationCallback() {
                override fun onLocationResult(result: LocationResult) {
                    result.lastLocation?.let { location ->
                        Timber.d("获取到新位置: ${location.latitude}, ${location.longitude}")
                        continuation.resume(location)
                    } ?: run {
                        Timber.w("LocationResult返回空位置")
                        continuation.resume(null)
                    }
                    fusedLocationClient.removeLocationUpdates(this)
                }

                override fun onLocationAvailability(availability: LocationAvailability) {
                    if (!availability.isLocationAvailable) {
                        Timber.w("位置不可用")
                    }
                }
            }

            fusedLocationClient.requestLocationUpdates(
                locationRequest,
                locationCallback,
                Looper.getMainLooper()
            )
        } catch (e: SecurityException) {
            Timber.e(e, "请求新位置权限异常")
            continuation.resume(null)
        } catch (e: Exception) {
            Timber.e(e, "请求新位置失败")
            continuation.resume(null)
        }
    }

    /**
     * 根据位置获取地址信息
     */
    suspend fun getAddressFromLocation(location: Location): String {
        return try {
            // 如果是默认位置，直接返回默认地址
            if (location.provider == "default") {
                return DEFAULT_ADDRESS
            }

            val geocoder = Geocoder(context, Locale.getDefault())
            val addresses = geocoder.getFromLocation(location.latitude, location.longitude, 1)

            if (!addresses.isNullOrEmpty()) {
                val address = addresses[0]
                // 返回省市区信息
                val city = address.locality ?: address.subAdminArea ?: ""
                val district = address.subLocality ?: ""

                if (city.isNotEmpty() && district.isNotEmpty()) {
                    "$city · $district"
                } else if (city.isNotEmpty()) {
                    city
                } else {
                    address.getAddressLine(0) ?: DEFAULT_ADDRESS
                }
            } else {
                Timber.w("Geocoder返回空地址，使用默认地址")
                DEFAULT_ADDRESS
            }
        } catch (e: Exception) {
            Timber.e(e, "获取地址失败，使用默认地址")
            DEFAULT_ADDRESS
        }
    }

    /**
     * 获取格式化的位置信息
     * 始终返回有效地址，不会返回null或"定位失败"
     */
    suspend fun getFormattedLocation(): Pair<String, Location> {
        val location = getCurrentLocation() ?: getDefaultLocation()
        val address = getAddressFromLocation(location)
        return address to location
    }
}

/**
 * 天气数据类
 */
data class WeatherInfo(
    val temperature: Int,
    val weather: String,
    val aqi: Int,
    val pm25: Int,
    val humidity: Int
)

/**
 * 天气管理器（模拟，实际应该调用天气API）
 * 支持超时重试机制
 */
class WeatherManager {

    companion object {
        private const val MAX_RETRY_COUNT = 3
        private const val RETRY_DELAY_MS = 1000L
        private const val TIMEOUT_MS = 5000L
    }

    /**
     * 获取天气信息（带超时重试机制）
     * @param latitude 纬度
     * @param longitude 经度
     * @param retryCount 当前重试次数
     * @return WeatherInfo 天气信息，失败时返回默认数据
     */
    suspend fun getWeather(
        latitude: Double,
        longitude: Double,
        retryCount: Int = 0
    ): WeatherInfo {
        return try {
            // 使用withTimeout添加超时控制
            kotlinx.coroutines.withTimeout(TIMEOUT_MS) {
                fetchWeatherFromApi(latitude, longitude)
            }
        } catch (e: kotlinx.coroutines.TimeoutCancellationException) {
            Timber.w("天气API超时，重试次数: $retryCount")
            handleRetry(latitude, longitude, retryCount, "超时")
        } catch (e: Exception) {
            Timber.e(e, "获取天气失败，重试次数: $retryCount")
            handleRetry(latitude, longitude, retryCount, e.message ?: "未知错误")
        }
    }

    /**
     * 处理重试逻辑
     */
    private suspend fun handleRetry(
        latitude: Double,
        longitude: Double,
        retryCount: Int,
        errorMessage: String
    ): WeatherInfo {
        return if (retryCount < MAX_RETRY_COUNT) {
            // 延迟后重试
            kotlinx.coroutines.delay(RETRY_DELAY_MS * (retryCount + 1))
            getWeather(latitude, longitude, retryCount + 1)
        } else {
            // 超过最大重试次数，返回默认数据
            Timber.w("天气API重试${MAX_RETRY_COUNT}次后仍失败，使用默认数据: $errorMessage")
            getDefaultWeather()
        }
    }

    /**
     * 从API获取天气（模拟实现）
     * TODO: 替换为真实的天气API调用
     */
    private suspend fun fetchWeatherFromApi(latitude: Double, longitude: Double): WeatherInfo {
        // 模拟网络延迟
        kotlinx.coroutines.delay(100)

        // TODO: 调用真实的天气API，如高德天气、和风天气等
        // 示例：
        // val response = weatherApiService.getWeather(latitude, longitude)
        // return response.toWeatherInfo()

        // 目前返回模拟数据
        return WeatherInfo(
            temperature = 26,
            weather = "多云",
            aqi = 35,
            pm25 = 12,
            humidity = 65
        )
    }

    /**
     * 获取默认天气数据（API失败时使用）
     */
    fun getDefaultWeather(): WeatherInfo {
        return WeatherInfo(
            temperature = 25,
            weather = "晴",
            aqi = 50,
            pm25 = 20,
            humidity = 60
        )
    }
}
