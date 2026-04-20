package com.wuheng.smart.presentation.about;

import com.wuheng.smart.BuildConfig;
import com.wuheng.smart.presentation.base.BaseViewModel;
import com.wuheng.smart.presentation.base.UiDataState;
import dagger.hilt.android.lifecycle.HiltViewModel;
import kotlinx.coroutines.flow.StateFlow;
import timber.log.Timber;
import javax.inject.Inject;

/**
 * 关于页面 ViewModel（生产级实现）
 *
 * 职责：
 * 1. 管理应用信息数据状态
 * 2. 使用本地版本信息（接口文档中没有版本信息接口）
 * 3. 提供刷新功能
 */
@dagger.hilt.android.lifecycle.HiltViewModel()
@kotlin.Metadata(mv = {1, 7, 1}, k = 1, d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0002\b\u0007\u0018\u00002\u00020\u0001B\u0007\b\u0007\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u000b\u001a\u00020\fH\u0002J\u0006\u0010\r\u001a\u00020\fR\u001a\u0010\u0003\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00060\u00050\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001d\u0010\u0007\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00060\u00050\b\u00a2\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n\u00a8\u0006\u000e"}, d2 = {"Lcom/wuheng/smart/presentation/about/AboutViewModel;", "Lcom/wuheng/smart/presentation/base/BaseViewModel;", "()V", "_aboutInfoState", "Lkotlinx/coroutines/flow/MutableStateFlow;", "Lcom/wuheng/smart/presentation/base/UiDataState;", "Lcom/wuheng/smart/presentation/about/AboutInfo;", "aboutInfoState", "Lkotlinx/coroutines/flow/StateFlow;", "getAboutInfoState", "()Lkotlinx/coroutines/flow/StateFlow;", "loadAboutInfo", "", "refresh", "app_debug"})
public final class AboutViewModel extends com.wuheng.smart.presentation.base.BaseViewModel {
    
    /**
     * 关于页面信息状态
     */
    private final kotlinx.coroutines.flow.MutableStateFlow<com.wuheng.smart.presentation.base.UiDataState<com.wuheng.smart.presentation.about.AboutInfo>> _aboutInfoState = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.wuheng.smart.presentation.base.UiDataState<com.wuheng.smart.presentation.about.AboutInfo>> aboutInfoState = null;
    
    @javax.inject.Inject()
    public AboutViewModel() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.wuheng.smart.presentation.base.UiDataState<com.wuheng.smart.presentation.about.AboutInfo>> getAboutInfoState() {
        return null;
    }
    
    /**
     * 加载应用信息
     * 使用本地版本信息（接口文档中没有版本信息接口）
     */
    private final void loadAboutInfo() {
    }
    
    /**
     * 刷新应用信息
     */
    public final void refresh() {
    }
}