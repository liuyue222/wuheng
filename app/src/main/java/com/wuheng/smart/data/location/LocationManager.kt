package com.wuheng.smart.data.location

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.os.Looper
import androidx.core.content.ContextCompat
import com.google.android.gms.location.*
import kotlinx.coroutines.suspendCancellableCoroutine
import timber.log.Timber
import java.util.Locale
import kotlin.coroutines.resume

/**
 * 定位管理器
 * 用于获取用户当前位置和地址信息
 */
class LocationManager(private val context: Context) {

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
     */
    suspend fun getCurrentLocation(): Location? = suspendCancellableCoroutine { continuation ->
        try {
            if (!hasLocationPermission()) {
                continuation.resume(null)
                return@suspendCancellableCoroutine
            }

            // 先尝试获取lastLocation
            fusedLocationClient.lastLocation
                .addOnSuccessListener { location ->
                    if (location != null) {
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
    }

    /**
     * 请求新位置
     */
    private fun requestNewLocation(continuation: kotlin.coroutines.Continuation<Location?>) {
        try {
            val locationRequest = LocationRequest.create().apply {
                priority = LocationRequest.PRIORITY_HIGH_ACCURACY
                interval = 0
                numUpdates = 1
            }

            val locationCallback = object : LocationCallback() {
                override fun onLocationResult(result: LocationResult) {
                    result.lastLocation?.let { location ->
                        continuation.resume(location)
                    } ?: run {
                        continuation.resume(null)
                    }
                    fusedLocationClient.removeLocationUpdates(this)
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
                    address.getAddressLine(0) ?: "未知位置"
                }
            } else {
                "未知位置"
            }
        } catch (e: Exception) {
            Timber.e(e, "获取地址失败")
            "未知位置"
        }
    }

    /**
     * 获取格式化的位置信息
     */
    suspend fun getFormattedLocation(): Pair<String, Location?> {
        val location = getCurrentLocation()
        return if (location != null) {
            val address = getAddressFromLocation(location)
            address to location
        } else {
            "定位失败" to null
        }
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
 */
class WeatherManager {
    
    /**
     * 获取天气信息（模拟数据，实际应该调用天气API）
     */
    suspend fun getWeather(latitude: Double, longitude: Double): WeatherInfo {
        // TODO: 调用真实的天气API，如高德天气、和风天气等
        // 目前返回模拟数据
        return WeatherInfo(
            temperature = 26,
            weather = "多云",
            aqi = 35,
            pm25 = 12,
            humidity = 65
        )
    }
}
