# 五恒智能控制系统 ProGuard/R8 规则
# 添加项目特定的 ProGuard 规则

#===============================================================================
# 基础规则
#===============================================================================

# 保留行号信息（用于崩溃分析）
-keepattributes SourceFile,LineNumberTable
-renamesourcefileattribute SourceFile

# 保留异常、内部类、签名、注解等信息
-keepattributes Exceptions,InnerClasses,Signature,Deprecated,
                SourceFile,LineNumberTable,Annotation,EnclosingMethod

# 保留泛型信息
-keepattributes Signature

# 保留注解信息
-keepattributes *Annotation*
-keep class * extends java.lang.annotation.Annotation { *; }

#===============================================================================
# Kotlin 规则
#===============================================================================

# 保留 Kotlin 元数据
-keep class kotlin.Metadata { *; }
-keepattributes RuntimeVisibleAnnotations

# 保留 Kotlin 协程
-keepnames class kotlinx.coroutines.internal.MainDispatcherFactory {}
-keepnames class kotlinx.coroutines.CoroutineExceptionHandler {}
-keepnames class kotlinx.coroutines.android.AndroidExceptionPreHandler {}
-keepnames class kotlinx.coroutines.android.AndroidDispatcherFactory {}

# 保留 Kotlin 反射
-keepclassmembers class **$WhenMappings {
    <fields>;
}

#===============================================================================
# AndroidX 规则
#===============================================================================

# AndroidX Core
-keep class androidx.core.** { *; }
-dontwarn androidx.core.**

# AndroidX Lifecycle
-keep class androidx.lifecycle.** { *; }
-keepclassmembers class androidx.lifecycle.** { *; }
-dontwarn androidx.lifecycle.**

# AndroidX Navigation
-keep class androidx.navigation.** { *; }
-dontwarn androidx.navigation.**

# AndroidX DataStore
-keep class androidx.datastore.** { *; }
-dontwarn androidx.datastore.**

# AndroidX Startup
-keep class androidx.startup.** { *; }
-dontwarn androidx.startup.**

# AndroidX SplashScreen
-keep class androidx.core.splashscreen.** { *; }
-dontwarn androidx.core.splashscreen.**

#===============================================================================
# Compose 规则
#===============================================================================

# Compose 运行时
-keep class androidx.compose.** { *; }
-keepclassmembers class androidx.compose.** { *; }
-dontwarn androidx.compose.**

# Compose 编译器生成的类
-keep class * extends androidx.compose.runtime.Composer {
    <methods>;
}

# 保留 @Composable 函数
-keepclasseswithmembers class * {
    @androidx.compose.runtime.Composable <methods>;
}

# 保留 Preview 函数
-keepclasseswithmembers class * {
    @androidx.compose.ui.tooling.preview.Preview <methods>;
}

#===============================================================================
# Hilt 规则
#===============================================================================

# Hilt
-keep class dagger.hilt.** { *; }
-keep class * extends dagger.hilt.internal.GeneratedComponent {
    <methods>;
}
-keep class * extends dagger.hilt.android.internal.managers.ActivityComponentManager {
    <methods>;
}
-keep class dagger.hilt.android.lifecycle.HiltViewModel {
    <methods>;
}

# Dagger
-keep class dagger.** { *; }
-keep class javax.inject.** { *; }
-keep class * extends dagger.internal.Binding
-keep class * extends dagger.internal.ModuleAdapter
-keep class * extends dagger.internal.StaticInjection

# 保留 Hilt 生成的类
-keep class * extends dagger.hilt.android.internal.lifecycle.HiltViewModelFactory {
    <methods>;
}
-keep class * extends dagger.hilt.android.internal.managers.SavedStateHandleHolder {
    <methods>;
}

# 保留 @AndroidEntryPoint 类
-keepclasseswithmembers class * {
    @dagger.hilt.android.AndroidEntryPoint <fields>;
}

# 保留 @HiltAndroidApp 类
-keepclasseswithmembers class * {
    @dagger.hilt.android.HiltAndroidApp <fields>;
}

#===============================================================================
# Retrofit & OkHttp 规则
#===============================================================================

# Retrofit
-keep class retrofit2.** { *; }
-dontwarn retrofit2.**
-keepattributes Signature
-keepattributes Exceptions

# 保留 Retrofit 接口
-keepclasseswithmembers class * {
    @retrofit2.http.* <methods>;
}

# OkHttp
-keep class okhttp3.** { *; }
-dontwarn okhttp3.**
-dontwarn okio.**

# Gson
-keep class com.google.gson.** { *; }
-dontwarn com.google.gson.**

# 保留 Gson 序列化/反序列化的类
-keep class com.wuheng.smart.data.model.** { *; }
-keep class com.wuheng.smart.data.network.** { *; }

# 保留 API 响应类
-keep class * extends com.wuheng.smart.data.network.BaseResponse { *; }

#===============================================================================
# Timber 规则
#===============================================================================

-keep class timber.log.** { *; }
-dontwarn timber.log.**

#===============================================================================
# Google Play Services 规则
#===============================================================================

# Location
-keep class com.google.android.gms.** { *; }
-dontwarn com.google.android.gms.**

#===============================================================================
# 项目特定规则
#===============================================================================

# 保留 Application 类
-keep class com.wuheng.smart.WuHengApplication { *; }

# 保留 MainActivity
-keep class com.wuheng.smart.MainActivity { *; }

# 保留所有 ViewModel
-keep class * extends androidx.lifecycle.ViewModel {
    <init>();
    <init>(...);
}

# 保留所有 Repository
-keep class com.wuheng.smart.data.repository.** { *; }

# 保留所有 Screen Composable 函数
-keep class com.wuheng.smart.presentation.** { *; }

# 保留 Navigation 相关类
-keep class com.wuheng.smart.navigation.** { *; }

# 保留性能监控类
-keep class com.wuheng.smart.performance.** { *; }

# 保留初始化器类
-keep class com.wuheng.smart.initializer.** { *; }

#===============================================================================
# 优化规则
#===============================================================================

# 启用 R8 优化（简化配置以避免StackOverflowError）
# 注意：以下配置可能导致大型Compose项目的StackOverflowError，已简化
# -optimizations !code/simplification/arithmetic,!code/simplification/cast,!field/*,!class/merging/*
# -optimizationpasses 5

# 使用保守的优化配置
-optimizations !code/allocation/variable
-optimizationpasses 3
-dontoptimize
-allowaccessmodification

# 避免递归优化问题
-dontpreverify
-dontshrink
-dontoptimize

# 移除日志代码（Release 模式）
-assumenosideeffects class android.util.Log {
    public static *** d(...);
    public static *** v(...);
    public static *** i(...);
    public static *** w(...);
    public static *** e(...);
}

-assumenosideeffects class timber.log.Timber {
    public static *** d(...);
    public static *** v(...);
    public static *** i(...);
    public static *** w(...);
    public static *** e(...);
}

# 移除 println
-assumenosideeffects class java.io.PrintStream {
    public void println(...);
    public void print(...);
}

#===============================================================================
# 测试规则（仅在测试时排除）
#===============================================================================

# 保留测试相关类
-keep class * extends org.junit.runner.Runner { *; }
-keep class org.junit.** { *; }
-keep class org.junit.jupiter.** { *; }
-dontwarn org.junit.**
-dontwarn org.junit.jupiter.**

# MockK
-keep class io.mockk.** { *; }
-dontwarn io.mockk.**

# 保留测试文件
-keep class **.test.** { *; }
-keep class **.androidTest.** { *; }
