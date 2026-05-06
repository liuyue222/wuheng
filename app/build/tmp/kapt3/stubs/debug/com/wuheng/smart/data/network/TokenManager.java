package com.wuheng.smart.data.network;

import android.content.Context;
import androidx.datastore.core.DataStore;
import androidx.datastore.preferences.core.Preferences;
import dagger.hilt.android.qualifiers.ApplicationContext;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.StateFlow;
import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Token管理器 - 使用StateFlow作为单一数据源，DataStore进行持久化存储
 *
 * 功能：
 * 1. 内存级Token存储（同步读写，避免拦截器中使用runBlocking导致的线程阻塞）
 * 2. Token状态监听（通过StateFlow观察Token变化）
 * 3. 用户信息缓存（登录后保存用户基本信息）
 * 4. 当前选中房屋ID管理
 * 5. 持久化存储（应用重启后Token不丢失）
 * 6. 记住密码功能（安全存储登录凭证）
 */
@kotlin.Metadata(mv = {1, 7, 1}, k = 1, d1 = {"\u0000N\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\r\n\u0002\u0010\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u001c\b\u0017\u0018\u0000 I2\u00020\u0001:\u0001IB\u0011\b\u0007\u0012\b\b\u0001\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\b\u0010\'\u001a\u00020(H\u0002J\u0011\u0010)\u001a\u00020(H\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010*J\u0006\u0010+\u001a\u00020(J\u0006\u0010,\u001a\u00020(J\b\u0010\u0014\u001a\u00020\u0007H\u0016J\f\u0010-\u001a\b\u0012\u0004\u0012\u00020\t0.J\f\u0010/\u001a\b\u0012\u0004\u0012\u00020\u00070.J\f\u00100\u001a\b\u0012\u0004\u0012\u00020\u00070.J\f\u00101\u001a\b\u0012\u0004\u0012\u00020\u00070.J\f\u00102\u001a\b\u0012\u0004\u0012\u00020\t0.J\u0006\u00103\u001a\u00020\u0007J\u0011\u00104\u001a\u00020\tH\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010*J\u0006\u00105\u001a\u00020\tJ\u0006\u00106\u001a\u00020\tJ\f\u00107\u001a\b\u0012\u0004\u0012\u00020\t0.J.\u00108\u001a\u00020(2\u0006\u00109\u001a\u00020\u00072\u0006\u0010!\u001a\u00020\u00072\u0006\u0010#\u001a\u00020\u00072\u0006\u0010%\u001a\u00020\u00072\u0006\u0010:\u001a\u00020\u0007J\u0006\u0010;\u001a\u00020(J!\u0010<\u001a\u00020(2\u0006\u0010=\u001a\u00020\u00072\u0006\u0010>\u001a\u00020\u0007H\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010?J\u000e\u0010@\u001a\u00020(2\u0006\u0010:\u001a\u00020\u0007J\u000e\u0010A\u001a\u00020(2\u0006\u0010B\u001a\u00020\tJ\u000e\u0010C\u001a\u00020(2\u0006\u0010D\u001a\u00020\u0007J\u000e\u0010E\u001a\u00020(2\u0006\u0010B\u001a\u00020\tJ\u000e\u0010F\u001a\u00020(2\u0006\u0010G\u001a\u00020\u0007J\u001e\u0010H\u001a\u00020(2\u0006\u0010!\u001a\u00020\u00072\u0006\u0010#\u001a\u00020\u00072\u0006\u0010%\u001a\u00020\u0007R\u0014\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\b\u001a\b\u0012\u0004\u0012\u00020\t0\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\t0\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0011X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00070\u0013\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0015R\u0017\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\t0\u0013\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0015R\u0014\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u001a0\u0019X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u00070\u0013\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u0015R\u0017\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\t0\u0013\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\u0015R\u0017\u0010\u001f\u001a\b\u0012\u0004\u0012\u00020\u00070\u0013\u00a2\u0006\b\n\u0000\u001a\u0004\b \u0010\u0015R\u0017\u0010!\u001a\b\u0012\u0004\u0012\u00020\u00070\u0013\u00a2\u0006\b\n\u0000\u001a\u0004\b\"\u0010\u0015R\u0017\u0010#\u001a\b\u0012\u0004\u0012\u00020\u00070\u0013\u00a2\u0006\b\n\u0000\u001a\u0004\b$\u0010\u0015R\u0017\u0010%\u001a\b\u0012\u0004\u0012\u00020\u00070\u0013\u00a2\u0006\b\n\u0000\u001a\u0004\b&\u0010\u0015\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006J"}, d2 = {"Lcom/wuheng/smart/data/network/TokenManager;", "", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "_currentHouseId", "Lkotlinx/coroutines/flow/MutableStateFlow;", "", "_darkMode", "", "_language", "_systemTheme", "_tokenFlow", "_userId", "_userName", "_userType", "coroutineScope", "Lkotlinx/coroutines/CoroutineScope;", "currentHouseId", "Lkotlinx/coroutines/flow/StateFlow;", "getCurrentHouseId", "()Lkotlinx/coroutines/flow/StateFlow;", "darkMode", "getDarkMode", "dataStore", "Landroidx/datastore/core/DataStore;", "Landroidx/datastore/preferences/core/Preferences;", "language", "getLanguage", "systemTheme", "getSystemTheme", "tokenFlow", "getTokenFlow", "userId", "getUserId", "userName", "getUserName", "userType", "getUserType", "clearAllData", "", "clearLoginCredentials", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "clearToken", "clearUserInfo", "getDarkModeFlow", "Lkotlinx/coroutines/flow/Flow;", "getLanguageFlow", "getSavedPassword", "getSavedPhone", "getSystemThemeFlow", "getToken", "hasSavedCredentials", "hasSelectedHouse", "hasToken", "isRememberPassword", "onLoginSuccess", "token", "houseId", "onLogout", "saveLoginCredentials", "phone", "password", "(Ljava/lang/String;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "setCurrentHouseId", "setDarkMode", "enabled", "setLanguage", "languageCode", "setSystemTheme", "setToken", "newToken", "setUserInfo", "Companion", "app_debug"})
@javax.inject.Singleton()
public class TokenManager {
    private final android.content.Context context = null;
    @org.jetbrains.annotations.NotNull()
    public static final com.wuheng.smart.data.network.TokenManager.Companion Companion = null;
    private static final kotlin.properties.ReadOnlyProperty dataStore$delegate = null;
    private static final androidx.datastore.preferences.core.Preferences.Key<java.lang.String> KEY_TOKEN = null;
    private static final androidx.datastore.preferences.core.Preferences.Key<java.lang.String> KEY_USER_ID = null;
    private static final androidx.datastore.preferences.core.Preferences.Key<java.lang.String> KEY_USER_NAME = null;
    private static final androidx.datastore.preferences.core.Preferences.Key<java.lang.String> KEY_USER_TYPE = null;
    private static final androidx.datastore.preferences.core.Preferences.Key<java.lang.String> KEY_CURRENT_HOUSE_ID = null;
    private static final androidx.datastore.preferences.core.Preferences.Key<java.lang.String> KEY_SAVED_PHONE = null;
    private static final androidx.datastore.preferences.core.Preferences.Key<java.lang.String> KEY_SAVED_PASSWORD = null;
    private static final androidx.datastore.preferences.core.Preferences.Key<java.lang.Boolean> KEY_REMEMBER_PASSWORD = null;
    private static final androidx.datastore.preferences.core.Preferences.Key<java.lang.Boolean> KEY_DARK_MODE = null;
    private static final androidx.datastore.preferences.core.Preferences.Key<java.lang.Boolean> KEY_SYSTEM_THEME = null;
    private static final androidx.datastore.preferences.core.Preferences.Key<java.lang.String> KEY_LANGUAGE = null;
    private final androidx.datastore.core.DataStore<androidx.datastore.preferences.core.Preferences> dataStore = null;
    private final kotlinx.coroutines.CoroutineScope coroutineScope = null;
    private final kotlinx.coroutines.flow.MutableStateFlow<java.lang.String> _tokenFlow = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.lang.String> tokenFlow = null;
    private final kotlinx.coroutines.flow.MutableStateFlow<java.lang.String> _userId = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.lang.String> userId = null;
    private final kotlinx.coroutines.flow.MutableStateFlow<java.lang.String> _userName = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.lang.String> userName = null;
    private final kotlinx.coroutines.flow.MutableStateFlow<java.lang.String> _userType = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.lang.String> userType = null;
    private final kotlinx.coroutines.flow.MutableStateFlow<java.lang.String> _currentHouseId = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.lang.String> currentHouseId = null;
    private final kotlinx.coroutines.flow.MutableStateFlow<java.lang.Boolean> _darkMode = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.lang.Boolean> darkMode = null;
    private final kotlinx.coroutines.flow.MutableStateFlow<java.lang.Boolean> _systemTheme = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.lang.Boolean> systemTheme = null;
    private final kotlinx.coroutines.flow.MutableStateFlow<java.lang.String> _language = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.lang.String> language = null;
    
    @javax.inject.Inject()
    public TokenManager(@org.jetbrains.annotations.NotNull()
    @dagger.hilt.android.qualifiers.ApplicationContext()
    android.content.Context context) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.lang.String> getTokenFlow() {
        return null;
    }
    
    /**
     * 清除所有数据（包括内存和持久化存储）
     */
    private final void clearAllData() {
    }
    
    /**
     * 同步获取当前Token（用于拦截器）
     * 从内存直接读取，避免线程阻塞
     */
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getToken() {
        return null;
    }
    
    /**
     * 同步设置Token，同时持久化到DataStore
     */
    public final void setToken(@org.jetbrains.annotations.NotNull()
    java.lang.String newToken) {
    }
    
    /**
     * 清除Token，同时清除持久化存储
     */
    public final void clearToken() {
    }
    
    /**
     * 检查是否有有效的Token
     */
    public final boolean hasToken() {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.lang.String> getUserId() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.lang.String> getUserName() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.lang.String> getUserType() {
        return null;
    }
    
    /**
     * 设置用户信息，同时持久化到DataStore
     */
    public final void setUserInfo(@org.jetbrains.annotations.NotNull()
    java.lang.String userId, @org.jetbrains.annotations.NotNull()
    java.lang.String userName, @org.jetbrains.annotations.NotNull()
    java.lang.String userType) {
    }
    
    /**
     * 清除用户信息，同时清除持久化存储
     */
    public final void clearUserInfo() {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.lang.String> getCurrentHouseId() {
        return null;
    }
    
    /**
     * 设置当前选中的房屋ID，同时持久化到DataStore
     */
    public final void setCurrentHouseId(@org.jetbrains.annotations.NotNull()
    java.lang.String houseId) {
    }
    
    /**
     * 获取当前房屋ID（同步）
     */
    @org.jetbrains.annotations.NotNull()
    public java.lang.String getCurrentHouseId() {
        return null;
    }
    
    /**
     * 检查是否已选择房屋
     */
    public final boolean hasSelectedHouse() {
        return false;
    }
    
    /**
     * 保存登录凭证（记住密码）
     * 注意：实际项目中应该对密码进行加密存储
     *
     * @param phone 手机号
     * @param password 密码
     */
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object saveLoginCredentials(@org.jetbrains.annotations.NotNull()
    java.lang.String phone, @org.jetbrains.annotations.NotNull()
    java.lang.String password, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> continuation) {
        return null;
    }
    
    /**
     * 清除保存的登录凭证
     */
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object clearLoginCredentials(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> continuation) {
        return null;
    }
    
    /**
     * 获取保存的手机号
     */
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<java.lang.String> getSavedPhone() {
        return null;
    }
    
    /**
     * 获取保存的密码
     */
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<java.lang.String> getSavedPassword() {
        return null;
    }
    
    /**
     * 检查是否启用了记住密码
     */
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<java.lang.Boolean> isRememberPassword() {
        return null;
    }
    
    /**
     * 检查是否有保存的登录凭证
     */
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object hasSavedCredentials(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Boolean> continuation) {
        return null;
    }
    
    /**
     * 登录成功后保存所有用户信息
     */
    public final void onLoginSuccess(@org.jetbrains.annotations.NotNull()
    java.lang.String token, @org.jetbrains.annotations.NotNull()
    java.lang.String userId, @org.jetbrains.annotations.NotNull()
    java.lang.String userName, @org.jetbrains.annotations.NotNull()
    java.lang.String userType, @org.jetbrains.annotations.NotNull()
    java.lang.String houseId) {
    }
    
    /**
     * 登出时清除所有信息
     */
    public final void onLogout() {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.lang.Boolean> getDarkMode() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.lang.Boolean> getSystemTheme() {
        return null;
    }
    
    /**
     * 设置深色模式
     */
    public final void setDarkMode(boolean enabled) {
    }
    
    /**
     * 设置是否跟随系统主题
     */
    public final void setSystemTheme(boolean enabled) {
    }
    
    /**
     * 获取深色模式设置Flow
     */
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<java.lang.Boolean> getDarkModeFlow() {
        return null;
    }
    
    /**
     * 获取系统主题设置Flow
     */
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<java.lang.Boolean> getSystemThemeFlow() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.lang.String> getLanguage() {
        return null;
    }
    
    /**
     * 设置语言
     * @param languageCode 语言代码: "zh" - 中文, "en" - 英文
     */
    public final void setLanguage(@org.jetbrains.annotations.NotNull()
    java.lang.String languageCode) {
    }
    
    /**
     * 获取语言设置Flow
     */
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<java.lang.String> getLanguageFlow() {
        return null;
    }
    
    @kotlin.Metadata(mv = {1, 7, 1}, k = 1, d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u0014\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00070\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00070\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u00070\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R%\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00130\u0012*\u00020\u00148BX\u0082\u0084\u0002\u00a2\u0006\f\n\u0004\b\u0017\u0010\u0018\u001a\u0004\b\u0015\u0010\u0016\u00a8\u0006\u0019"}, d2 = {"Lcom/wuheng/smart/data/network/TokenManager$Companion;", "", "()V", "KEY_CURRENT_HOUSE_ID", "Landroidx/datastore/preferences/core/Preferences$Key;", "", "KEY_DARK_MODE", "", "KEY_LANGUAGE", "KEY_REMEMBER_PASSWORD", "KEY_SAVED_PASSWORD", "KEY_SAVED_PHONE", "KEY_SYSTEM_THEME", "KEY_TOKEN", "KEY_USER_ID", "KEY_USER_NAME", "KEY_USER_TYPE", "dataStore", "Landroidx/datastore/core/DataStore;", "Landroidx/datastore/preferences/core/Preferences;", "Landroid/content/Context;", "getDataStore", "(Landroid/content/Context;)Landroidx/datastore/core/DataStore;", "dataStore$delegate", "Lkotlin/properties/ReadOnlyProperty;", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
        
        private final androidx.datastore.core.DataStore<androidx.datastore.preferences.core.Preferences> getDataStore(android.content.Context $this$dataStore) {
            return null;
        }
    }
}