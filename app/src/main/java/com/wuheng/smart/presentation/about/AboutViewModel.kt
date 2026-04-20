package com.wuheng.smart.presentation.about

import androidx.lifecycle.viewModelScope
import com.wuheng.smart.BuildConfig
import com.wuheng.smart.presentation.base.BaseViewModel
import com.wuheng.smart.presentation.base.UiDataState
import com.wuheng.smart.presentation.base.createUiStateFlow
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

/**
 * 关于页面 ViewModel（生产级实现）
 *
 * 职责：
 * 1. 管理应用信息数据状态
 * 2. 使用本地版本信息（接口文档中没有版本信息接口）
 * 3. 提供刷新功能
 */
@HiltViewModel
class AboutViewModel @Inject constructor() : BaseViewModel() {

    /**
     * 关于页面信息状态
     */
    private val _aboutInfoState = createUiStateFlow<AboutInfo>()
    val aboutInfoState: StateFlow<UiDataState<AboutInfo>> = _aboutInfoState.asStateFlow()

    init {
        loadAboutInfo()
    }

    /**
     * 加载应用信息
     * 使用本地版本信息（接口文档中没有版本信息接口）
     */
    private fun loadAboutInfo() {
        viewModelScope.launch {
            _aboutInfoState.value = UiDataState.Loading
            Timber.d("Loading about info")

            // 模拟网络延迟
            delay(300)

            // 使用本地版本信息
            val aboutInfo = AboutInfo(
                appName = "新宜能五恒系统",
                version = BuildConfig.VERSION_NAME,
                versionCode = BuildConfig.VERSION_CODE,
                copyright = "© 2024 新宜能科技 版权所有",
                hasUpdate = false,
                updateUrl = null,
                updateLog = null
            )

            _aboutInfoState.value = UiDataState.Success(aboutInfo)
            Timber.d("Loaded about info: version=${aboutInfo.version}")
        }
    }

    /**
     * 刷新应用信息
     */
    fun refresh() {
        loadAboutInfo()
    }
}

/**
 * 关于页面信息数据类
 */
data class AboutInfo(
    val appName: String,
    val version: String,
    val versionCode: Int = 1,
    val copyright: String,
    val hasUpdate: Boolean = false,
    val updateUrl: String? = null,
    val updateLog: String? = null
)
