package com.wuheng.smart.presentation.base;

import androidx.lifecycle.ViewModel;
import com.wuheng.smart.data.network.ApiResult;
import com.wuheng.smart.data.network.AppException;
import com.wuheng.smart.data.network.AuthEvent;
import com.wuheng.smart.data.network.AuthEventManager;
import kotlinx.coroutines.flow.StateFlow;
import timber.log.Timber;

@kotlin.Metadata(mv = {1, 7, 1}, k = 1, d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b7\u0018\u0000*\u0006\b\u0000\u0010\u0001 \u00012\u00020\u0002:\u0004\u0004\u0005\u0006\u0007B\u0007\b\u0004\u00a2\u0006\u0002\u0010\u0003\u0082\u0001\u0004\b\t\n\u000b\u00a8\u0006\f"}, d2 = {"Lcom/wuheng/smart/presentation/base/UiDataState;", "T", "", "()V", "Error", "Idle", "Loading", "Success", "Lcom/wuheng/smart/presentation/base/UiDataState$Error;", "Lcom/wuheng/smart/presentation/base/UiDataState$Idle;", "Lcom/wuheng/smart/presentation/base/UiDataState$Loading;", "Lcom/wuheng/smart/presentation/base/UiDataState$Success;", "app_debug"})
public abstract class UiDataState<T extends java.lang.Object> {
    
    private UiDataState() {
        super();
    }
    
    @kotlin.Metadata(mv = {1, 7, 1}, k = 1, d1 = {"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0001\n\u0002\b\u0002\b\u00c7\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0003\u00a8\u0006\u0004"}, d2 = {"Lcom/wuheng/smart/presentation/base/UiDataState$Loading;", "Lcom/wuheng/smart/presentation/base/UiDataState;", "", "()V", "app_debug"})
    public static final class Loading extends com.wuheng.smart.presentation.base.UiDataState {
        @org.jetbrains.annotations.NotNull()
        public static final com.wuheng.smart.presentation.base.UiDataState.Loading INSTANCE = null;
        
        private Loading() {
            super();
        }
    }
    
    @kotlin.Metadata(mv = {1, 7, 1}, k = 1, d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0087\b\u0018\u0000*\u0004\b\u0001\u0010\u00012\b\u0012\u0004\u0012\u0002H\u00010\u0002B\r\u0012\u0006\u0010\u0003\u001a\u00028\u0001\u00a2\u0006\u0002\u0010\u0004J\u000e\u0010\b\u001a\u00028\u0001H\u00c6\u0003\u00a2\u0006\u0002\u0010\u0006J\u001e\u0010\t\u001a\b\u0012\u0004\u0012\u00028\u00010\u00002\b\b\u0002\u0010\u0003\u001a\u00028\u0001H\u00c6\u0001\u00a2\u0006\u0002\u0010\nJ\u0013\u0010\u000b\u001a\u00020\f2\b\u0010\r\u001a\u0004\u0018\u00010\u000eH\u00d6\u0003J\t\u0010\u000f\u001a\u00020\u0010H\u00d6\u0001J\t\u0010\u0011\u001a\u00020\u0012H\u00d6\u0001R\u0013\u0010\u0003\u001a\u00028\u0001\u00a2\u0006\n\n\u0002\u0010\u0007\u001a\u0004\b\u0005\u0010\u0006\u00a8\u0006\u0013"}, d2 = {"Lcom/wuheng/smart/presentation/base/UiDataState$Success;", "T", "Lcom/wuheng/smart/presentation/base/UiDataState;", "data", "(Ljava/lang/Object;)V", "getData", "()Ljava/lang/Object;", "Ljava/lang/Object;", "component1", "copy", "(Ljava/lang/Object;)Lcom/wuheng/smart/presentation/base/UiDataState$Success;", "equals", "", "other", "", "hashCode", "", "toString", "", "app_debug"})
    public static final class Success<T extends java.lang.Object> extends com.wuheng.smart.presentation.base.UiDataState<T> {
        private final T data = null;
        
        @org.jetbrains.annotations.NotNull()
        public final com.wuheng.smart.presentation.base.UiDataState.Success<T> copy(T data) {
            return null;
        }
        
        @java.lang.Override()
        public boolean equals(@org.jetbrains.annotations.Nullable()
        java.lang.Object other) {
            return false;
        }
        
        @java.lang.Override()
        public int hashCode() {
            return 0;
        }
        
        @org.jetbrains.annotations.NotNull()
        @java.lang.Override()
        public java.lang.String toString() {
            return null;
        }
        
        public Success(T data) {
            super();
        }
        
        public final T component1() {
            return null;
        }
        
        public final T getData() {
            return null;
        }
    }
    
    @kotlin.Metadata(mv = {1, 7, 1}, k = 1, d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0001\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0087\b\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\r\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u00a2\u0006\u0002\u0010\u0005J\t\u0010\b\u001a\u00020\u0004H\u00c6\u0003J\u0013\u0010\t\u001a\u00020\u00002\b\b\u0002\u0010\u0003\u001a\u00020\u0004H\u00c6\u0001J\u0013\u0010\n\u001a\u00020\u000b2\b\u0010\f\u001a\u0004\u0018\u00010\rH\u00d6\u0003J\t\u0010\u000e\u001a\u00020\u000fH\u00d6\u0001J\t\u0010\u0010\u001a\u00020\u0011H\u00d6\u0001R\u0011\u0010\u0003\u001a\u00020\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007\u00a8\u0006\u0012"}, d2 = {"Lcom/wuheng/smart/presentation/base/UiDataState$Error;", "Lcom/wuheng/smart/presentation/base/UiDataState;", "", "exception", "Lcom/wuheng/smart/data/network/AppException;", "(Lcom/wuheng/smart/data/network/AppException;)V", "getException", "()Lcom/wuheng/smart/data/network/AppException;", "component1", "copy", "equals", "", "other", "", "hashCode", "", "toString", "", "app_debug"})
    public static final class Error extends com.wuheng.smart.presentation.base.UiDataState {
        @org.jetbrains.annotations.NotNull()
        private final com.wuheng.smart.data.network.AppException exception = null;
        
        @org.jetbrains.annotations.NotNull()
        public final com.wuheng.smart.presentation.base.UiDataState.Error copy(@org.jetbrains.annotations.NotNull()
        com.wuheng.smart.data.network.AppException exception) {
            return null;
        }
        
        @java.lang.Override()
        public boolean equals(@org.jetbrains.annotations.Nullable()
        java.lang.Object other) {
            return false;
        }
        
        @java.lang.Override()
        public int hashCode() {
            return 0;
        }
        
        @org.jetbrains.annotations.NotNull()
        @java.lang.Override()
        public java.lang.String toString() {
            return null;
        }
        
        public Error(@org.jetbrains.annotations.NotNull()
        com.wuheng.smart.data.network.AppException exception) {
            super();
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.wuheng.smart.data.network.AppException component1() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.wuheng.smart.data.network.AppException getException() {
            return null;
        }
    }
    
    @kotlin.Metadata(mv = {1, 7, 1}, k = 1, d1 = {"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0001\n\u0002\b\u0002\b\u00c7\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0003\u00a8\u0006\u0004"}, d2 = {"Lcom/wuheng/smart/presentation/base/UiDataState$Idle;", "Lcom/wuheng/smart/presentation/base/UiDataState;", "", "()V", "app_debug"})
    public static final class Idle extends com.wuheng.smart.presentation.base.UiDataState {
        @org.jetbrains.annotations.NotNull()
        public static final com.wuheng.smart.presentation.base.UiDataState.Idle INSTANCE = null;
        
        private Idle() {
            super();
        }
    }
}