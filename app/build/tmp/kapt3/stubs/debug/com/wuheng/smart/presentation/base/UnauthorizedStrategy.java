package com.wuheng.smart.presentation.base;

import androidx.lifecycle.ViewModel;
import com.wuheng.smart.data.network.ApiResult;
import com.wuheng.smart.data.network.AppException;
import com.wuheng.smart.data.network.AuthEvent;
import com.wuheng.smart.data.network.AuthEventManager;
import kotlinx.coroutines.flow.StateFlow;
import timber.log.Timber;

/**
 * 401 错误处理策略
 */
@kotlin.Metadata(mv = {1, 7, 1}, k = 1, d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b7\u0018\u00002\u00020\u0001:\u0002\u0003\u0004B\u0007\b\u0004\u00a2\u0006\u0002\u0010\u0002\u0082\u0001\u0002\u0005\u0006\u00a8\u0006\u0007"}, d2 = {"Lcom/wuheng/smart/presentation/base/UnauthorizedStrategy;", "", "()V", "AutoHandle", "ManualHandle", "Lcom/wuheng/smart/presentation/base/UnauthorizedStrategy$AutoHandle;", "Lcom/wuheng/smart/presentation/base/UnauthorizedStrategy$ManualHandle;", "app_debug"})
public abstract class UnauthorizedStrategy {
    
    private UnauthorizedStrategy() {
        super();
    }
    
    /**
     * 自动处理 - 发送 401 事件通知 UI 层跳转登录页
     */
    @kotlin.Metadata(mv = {1, 7, 1}, k = 1, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u00c7\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002\u00a8\u0006\u0003"}, d2 = {"Lcom/wuheng/smart/presentation/base/UnauthorizedStrategy$AutoHandle;", "Lcom/wuheng/smart/presentation/base/UnauthorizedStrategy;", "()V", "app_debug"})
    public static final class AutoHandle extends com.wuheng.smart.presentation.base.UnauthorizedStrategy {
        @org.jetbrains.annotations.NotNull()
        public static final com.wuheng.smart.presentation.base.UnauthorizedStrategy.AutoHandle INSTANCE = null;
        
        private AutoHandle() {
            super();
        }
    }
    
    /**
     * 手动处理 - 由调用方自行处理 401 错误
     */
    @kotlin.Metadata(mv = {1, 7, 1}, k = 1, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u00c7\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002\u00a8\u0006\u0003"}, d2 = {"Lcom/wuheng/smart/presentation/base/UnauthorizedStrategy$ManualHandle;", "Lcom/wuheng/smart/presentation/base/UnauthorizedStrategy;", "()V", "app_debug"})
    public static final class ManualHandle extends com.wuheng.smart.presentation.base.UnauthorizedStrategy {
        @org.jetbrains.annotations.NotNull()
        public static final com.wuheng.smart.presentation.base.UnauthorizedStrategy.ManualHandle INSTANCE = null;
        
        private ManualHandle() {
            super();
        }
    }
}