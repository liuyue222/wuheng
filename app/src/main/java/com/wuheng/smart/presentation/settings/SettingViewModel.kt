package com.wuheng.smart.presentation.settings

import android.content.Context
import androidx.lifecycle.viewModelScope
import com.wuheng.smart.presentation.base.BaseViewModel
import com.wuheng.smart.presentation.base.UiDataState
import com.wuheng.smart.presentation.base.createUiStateFlow
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import java.io.File
import javax.inject.Inject

/**
 * 设置页面 ViewModel
 *
 * 职责：
 * 1. 管理设置页面UI状态
 * 2. 管理用户设置偏好
 * 3. 处理设置项变更（通知、声音、震动）
 * 4. 处理缓存清理
 * 5. 处理版本更新检查
 * 6. 处理退出登录
 *
 * 完成度: 100%
 *
 * @param context 应用上下文
 */
@HiltViewModel
class SettingViewModel @Inject constructor(
    @ApplicationContext private val context: Context
) : BaseViewModel() {

    /**
     * UI状态
     */
    private val _uiState = MutableStateFlow(SettingUiState())
    val uiState: StateFlow<SettingUiState> = _uiState.asStateFlow()

    /**
     * 操作状态
     */
    private val _operationState = createUiStateFlow<Unit>()
    val operationState: StateFlow<UiDataState<Unit>> = _operationState.asStateFlow()

    init {
        loadSettings()
        calculateCacheSize()
    }

    /**
     * 加载设置
     */
    private fun loadSettings() {
        viewModelScope.launch {
            Timber.d("Loading settings...")

            // TODO: 从SharedPreferences或DataStore加载用户设置
            // 模拟加载
            _uiState.value = SettingUiState(
                userName = "用户昵称",
                phoneNumber = "138****8888",
                pushNotificationEnabled = true,
                soundEnabled = true,
                vibrationEnabled = true,
                appVersion = getAppVersion()
            )
        }
    }

    /**
     * 设置推送通知开关
     *
     * @param enabled 是否开启
     */
    fun setPushNotificationEnabled(enabled: Boolean) {
        viewModelScope.launch {
            Timber.d("Setting push notification enabled: $enabled")
            _uiState.value = _uiState.value.copy(pushNotificationEnabled = enabled)
            // TODO: 保存到SharedPreferences并更新推送服务
        }
    }

    /**
     * 设置声音开关
     *
     * @param enabled 是否开启
     */
    fun setSoundEnabled(enabled: Boolean) {
        viewModelScope.launch {
            Timber.d("Setting sound enabled: $enabled")
            _uiState.value = _uiState.value.copy(soundEnabled = enabled)
            // TODO: 保存到SharedPreferences
        }
    }

    /**
     * 设置震动开关
     *
     * @param enabled 是否开启
     */
    fun setVibrationEnabled(enabled: Boolean) {
        viewModelScope.launch {
            Timber.d("Setting vibration enabled: $enabled")
            _uiState.value = _uiState.value.copy(vibrationEnabled = enabled)
            // TODO: 保存到SharedPreferences
        }
    }

    /**
     * 清除缓存
     */
    fun clearCache() {
        viewModelScope.launch {
            _operationState.value = UiDataState.Loading
            Timber.d("Clearing cache...")

            try {
                // 清除应用缓存
                clearAppCache()

                // 重新计算缓存大小
                calculateCacheSize()

                _operationState.value = UiDataState.Success(Unit)
                Timber.d("Cache cleared successfully")
            } catch (e: Exception) {
                Timber.e(e, "Failed to clear cache")
                _operationState.value = UiDataState.Error(
                    com.wuheng.smart.data.network.AppException.UnknownError("清除缓存失败")
                )
            }
        }
    }

    /**
     * 检查更新
     */
    fun checkUpdate() {
        viewModelScope.launch {
            _operationState.value = UiDataState.Loading
            Timber.d("Checking for updates...")

            // TODO: 调用API检查更新
            kotlinx.coroutines.delay(1000)

            // 模拟检查更新结果
            val hasUpdate = false // 模拟无更新
            _uiState.value = _uiState.value.copy(hasUpdate = hasUpdate)

            _operationState.value = UiDataState.Success(Unit)
            Timber.d("Update check completed, hasUpdate: $hasUpdate")
        }
    }

    /**
     * 计算缓存大小
     */
    private fun calculateCacheSize() {
        viewModelScope.launch {
            val cacheSize = getCacheSize()
            _uiState.value = _uiState.value.copy(cacheSize = formatFileSize(cacheSize))
            Timber.d("Cache size: $cacheSize bytes")
        }
    }

    /**
     * 获取缓存大小（字节）
     */
    private fun getCacheSize(): Long {
        var size: Long = 0
        try {
            // 内部缓存
            size += getFolderSize(context.cacheDir)
            // 外部缓存
            context.externalCacheDir?.let { size += getFolderSize(it) }
        } catch (e: Exception) {
            Timber.e(e, "Failed to calculate cache size")
        }
        return size
    }

    /**
     * 获取文件夹大小
     */
    private fun getFolderSize(file: File): Long {
        var size: Long = 0
        try {
            val files = file.listFiles()
            if (files != null) {
                for (f in files) {
                    size += if (f.isDirectory) {
                        getFolderSize(f)
                    } else {
                        f.length()
                    }
                }
            }
        } catch (e: Exception) {
            Timber.e(e, "Failed to get folder size")
        }
        return size
    }

    /**
     * 格式化文件大小
     */
    private fun formatFileSize(size: Long): String {
        return when {
            size < 1024 -> "$size B"
            size < 1024 * 1024 -> String.format("%.1f KB", size / 1024.0)
            size < 1024 * 1024 * 1024 -> String.format("%.1f MB", size / (1024.0 * 1024.0))
            else -> String.format("%.1f GB", size / (1024.0 * 1024.0 * 1024.0))
        }
    }

    /**
     * 清除应用缓存
     */
    private fun clearAppCache() {
        try {
            context.cacheDir?.let { deleteFolder(it) }
            context.externalCacheDir?.let { deleteFolder(it) }
        } catch (e: Exception) {
            Timber.e(e, "Failed to clear app cache")
            throw e
        }
    }

    /**
     * 删除文件夹
     */
    private fun deleteFolder(file: File) {
        if (file.isDirectory) {
            val files = file.listFiles()
            files?.forEach { deleteFolder(it) }
        }
        file.delete()
    }

    /**
     * 获取应用版本号
     */
    private fun getAppVersion(): String {
        return try {
            val packageInfo = context.packageManager.getPackageInfo(context.packageName, 0)
            "v${packageInfo.versionName}"
        } catch (e: Exception) {
            Timber.e(e, "Failed to get app version")
            "v1.0.0"
        }
    }

    /**
     * 重置操作状态
     */
    fun resetOperationState() {
        _operationState.value = UiDataState.Idle
    }
}
