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
     * 获取当前位置（GMS优先，系统LocationManager兜底）
     * GMS不可用时（常见于国产手机），自动fallback到系统LocationManager
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
                        fusedLocationClient.lastLocation
                            .addOnSuccessListener { location ->
                                if (location != null) {
                                    Timber.d("GMS获取到lastLocation: ${location.latitude}, ${location.longitude}")
                                    continuation.resume(location)
                                } else {
                                    Timber.d("GMS lastLocation为空，尝试系统LocationManager")
                                    val systemLocation = getLocationViaSystemManagerAsync()
                                    if (systemLocation != null) {
                                        Timber.d("系统LocationManager获取到位置: ${systemLocation.latitude}, ${systemLocation.longitude}")
                                        continuation.resume(systemLocation)
                                    } else {
                                        requestNewLocation(continuation)
                                    }
                                }
                            }
                            .addOnFailureListener { e ->
                                Timber.e(e, "GMS获取lastLocation失败，尝试系统LocationManager")
                                val systemLocation = getLocationViaSystemManagerAsync()
                                if (systemLocation != null) {
                                    Timber.d("系统LocationManager获取到位置: ${systemLocation.latitude}, ${systemLocation.longitude}")
                                    continuation.resume(systemLocation)
                                } else {
                                    requestNewLocation(continuation)
                                }
                            }
                    } catch (e: SecurityException) {
                        Timber.e(e, "定位权限异常")
                        continuation.resume(null)
                    }

                    continuation.invokeOnCancellation {
                        Timber.d("定位协程被取消")
                    }
                }
            }
        } catch (e: TimeoutCancellationException) {
            Timber.w("定位超时，尝试系统LocationManager兜底")
            val systemLocation = getLocationViaSystemManagerAsync()
            if (systemLocation != null) {
                Timber.d("超时后系统LocationManager成功获取位置")
                systemLocation
            } else {
                Timber.w("系统LocationManager也失败，使用默认位置")
                getDefaultLocation()
            }
        } catch (e: Exception) {
            Timber.e(e, "获取位置失败，使用默认位置")
            getDefaultLocation()
        }
    }

    /**
     * 通过系统LocationManager同步获取位置（非挂起）
     * 优先GPS，其次Network，最后lastKnown
     */
    private fun getLocationViaSystemManagerAsync(): Location? {
        try {
            val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as? android.location.LocationManager
                ?: return null

            val providers = listOf(
                android.location.LocationManager.GPS_PROVIDER,
                android.location.LocationManager.NETWORK_PROVIDER
            )

            for (provider in providers) {
                if (!locationManager.isProviderEnabled(provider)) continue
                try {
                    val lastKnown = locationManager.getLastKnownLocation(provider)
                    if (lastKnown != null && isLocationRecent(lastKnown)) {
                        return lastKnown
                    }
                } catch (e: SecurityException) {
                    Timber.w("系统LocationManager权限异常: $provider")
                }
            }

            for (provider in providers) {
                if (!locationManager.isProviderEnabled(provider)) continue
                try {
                    val lastKnown = locationManager.getLastKnownLocation(provider)
                    if (lastKnown != null) {
                        return lastKnown
                    }
                } catch (e: SecurityException) {
                    // ignore
                }
            }

            return null
        } catch (e: Exception) {
            Timber.e(e, "系统LocationManager获取位置异常")
            return null
        }
    }

    /**
     * 判断位置是否在最近2分钟内获取的
     */
    private fun isLocationRecent(location: Location): Boolean {
        val now = System.currentTimeMillis()
        val locationTime = location.time
        return (now - locationTime) < 120000L
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


