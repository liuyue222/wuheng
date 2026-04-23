package com.wuheng.smart.security

import android.content.Context
import android.os.Build
import android.os.Debug
import android.provider.Settings
import android.util.Base64
import com.wuheng.smart.BuildConfig
import java.io.File
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

/**
 * 安全工具类
 *
 * 提供以下安全功能：
 * 1. 字符串加密/解密（AES-256）
 * 2. 防调试检测
 * 3. Root检测
 * 4. 模拟器检测
 * 5. Xposed/Frida检测
 * 6. 应用完整性校验
 *
 * 注意：这些安全措施可以增加逆向难度，但不能完全阻止专业攻击者
 *
 * @author 五恒智能控制系统
 * @since 1.0
 */
object SecurityUtils {

    private const val AES_ALGORITHM = "AES"
    private const val AES_TRANSFORMATION = "AES/CBC/PKCS7Padding"
    private const val IV_LENGTH = 16
    private const val KEY_LENGTH = 32

    // 加密密钥（实际应从安全渠道获取，如服务器下发或Native层）
    // 这里使用BuildConfig中的加密密钥
    private val SECRET_KEY: String by lazy {
        BuildConfig.ENCRYPTION_KEY.takeIf { it.isNotEmpty() }
            ?: generateFallbackKey()
    }

    /**
     * 生成备用密钥（当BuildConfig中未配置时）
     * 注意：实际生产环境应确保密钥从安全渠道获取
     */
    private fun generateFallbackKey(): String {
        // 使用应用签名哈希作为备用密钥的一部分
        return "WuHengSmart2024SecureKeyForAES256Encryption"
    }

    // ==================== 字符串加密/解密 ====================

    /**
     * 加密字符串（AES-256-CBC）
     *
     * @param plainText 明文
     * @return Base64编码的密文
     */
    fun encrypt(plainText: String): String {
        return try {
            val cipher = Cipher.getInstance(AES_TRANSFORMATION)
            val keySpec = generateKeySpec()
            val iv = ByteArray(IV_LENGTH).apply {
                java.security.SecureRandom().nextBytes(this)
            }
            val ivSpec = IvParameterSpec(iv)

            cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivSpec)
            val encrypted = cipher.doFinal(plainText.toByteArray(Charsets.UTF_8))

            // 将IV附加到密文前面
            val combined = iv + encrypted
            Base64.encodeToString(combined, Base64.NO_WRAP)
        } catch (e: Exception) {
            if (BuildConfig.ENABLE_LOGGING) {
                e.printStackTrace()
            }
            ""
        }
    }

    /**
     * 解密字符串（AES-256-CBC）
     *
     * @param encryptedText Base64编码的密文
     * @return 明文
     */
    fun decrypt(encryptedText: String): String {
        return try {
            val combined = Base64.decode(encryptedText, Base64.NO_WRAP)

            // 分离IV和密文
            val iv = combined.copyOfRange(0, IV_LENGTH)
            val encrypted = combined.copyOfRange(IV_LENGTH, combined.size)

            val cipher = Cipher.getInstance(AES_TRANSFORMATION)
            val keySpec = generateKeySpec()
            val ivSpec = IvParameterSpec(iv)

            cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec)
            val decrypted = cipher.doFinal(encrypted)

            String(decrypted, Charsets.UTF_8)
        } catch (e: Exception) {
            if (BuildConfig.ENABLE_LOGGING) {
                e.printStackTrace()
            }
            ""
        }
    }

    /**
     * 生成AES密钥
     */
    private fun generateKeySpec(): SecretKeySpec {
        val keyBytes = SECRET_KEY.toByteArray(Charsets.UTF_8)
        // 确保密钥长度为32字节（256位）
        val key = if (keyBytes.size >= KEY_LENGTH) {
            keyBytes.copyOf(KEY_LENGTH)
        } else {
            keyBytes.copyOf(KEY_LENGTH).apply {
                // 使用PKCS7填充
                for (i in keyBytes.size until KEY_LENGTH) {
                    this[i] = (KEY_LENGTH - keyBytes.size).toByte()
                }
            }
        }
        return SecretKeySpec(key, AES_ALGORITHM)
    }

    /**
     * 简单字符串混淆（用于轻度敏感数据）
     * 比AES更快，但安全性较低
     *
     * @param input 输入字符串
     * @return 混淆后的字符串
     */
    fun obfuscate(input: String): String {
        val key = SECRET_KEY.hashCode()
        return input.mapIndexed { index, char ->
            (char.code xor (key + index)).toChar()
        }.joinToString("")
    }

    /**
     * 字符串解混淆
     *
     * @param input 混淆后的字符串
     * @return 原始字符串
     */
    fun deobfuscate(input: String): String {
        // XOR操作是对称的
        return obfuscate(input)
    }

    // ==================== 防调试检测 ====================

    /**
     * 检测是否处于调试模式
     *
     * @return true 如果检测到调试器
     */
    fun isDebugged(): Boolean {
        return Debug.isDebuggerConnected() || Debug.waitingForDebugger()
    }

    /**
     * 检测是否可被调试
     * 检查应用是否以debuggable模式运行
     *
     * @param context 应用上下文
     * @return true 如果应用可被调试
     */
    fun isDebuggable(context: Context): Boolean {
        return context.applicationInfo.flags and android.content.pm.ApplicationInfo.FLAG_DEBUGGABLE != 0
    }

    /**
     * 执行防调试检测
     * 如果检测到调试器，可以执行相应的安全措施
     *
     * @param onDebugDetected 检测到调试时的回调
     */
    fun checkDebugMode(onDebugDetected: () -> Unit = {}) {
        if (isDebugged()) {
            onDebugDetected()
        }
    }

    // ==================== Root检测 ====================

    /**
     * 检测设备是否已Root
     *
     * 检测方法：
     * 1. 检查常见的Root文件
     * 2. 检查可执行的su命令
     * 3. 检查系统属性
     *
     * @return true 如果检测到Root
     */
    fun isDeviceRooted(): Boolean {
        return checkRootFiles() || checkSuCommand() || checkSystemProperties()
    }

    /**
     * 检查常见的Root文件
     */
    private fun checkRootFiles(): Boolean {
        val rootFiles = arrayOf(
            "/system/bin/su",
            "/system/xbin/su",
            "/sbin/su",
            "/su/bin/su",
            "/data/local/xbin/su",
            "/data/local/bin/su",
            "/system/sd/xbin/su",
            "/system/bin/failsafe/su",
            "/data/local/su",
            "/su/bin",
            "/system/app/Superuser.apk",
            "/system/app/SuperSU.apk",
            "/system/app/Magisk.apk"
        )

        return rootFiles.any { File(it).exists() }
    }

    /**
     * 检查是否可以执行su命令
     */
    private fun checkSuCommand(): Boolean {
        return try {
            val process = Runtime.getRuntime().exec(arrayOf("/system/xbin/which", "su"))
            process.waitFor()
            process.exitValue() == 0
        } catch (e: Exception) {
            false
        }
    }

    /**
     * 检查系统属性
     */
    private fun checkSystemProperties(): Boolean {
        val dangerousProps = arrayOf(
            "ro.debuggable",
            "ro.secure"
        )

        return try {
            dangerousProps.any { prop ->
                val process = Runtime.getRuntime().exec("getprop $prop")
                process.inputStream.bufferedReader().use { reader ->
                    val value = reader.readLine()
                    when (prop) {
                        "ro.debuggable" -> value == "1"
                        "ro.secure" -> value == "0"
                        else -> false
                    }
                }
            }
        } catch (e: Exception) {
            false
        }
    }

    // ==================== 模拟器检测 ====================

    /**
     * 检测是否在模拟器中运行
     *
     * @param context 应用上下文
     * @return true 如果检测到模拟器
     */
    fun isEmulator(context: Context): Boolean {
        return checkBasicEmulatorIndicators() ||
                checkAdvancedEmulatorIndicators(context) ||
                checkHardwareIndicators()
    }

    /**
     * 检查基本的模拟器指标
     */
    private fun checkBasicEmulatorIndicators(): Boolean {
        val buildIndicators = arrayOf(
            Build.FINGERPRINT.startsWith("generic"),
            Build.FINGERPRINT.lowercase().contains("emulator"),
            Build.MODEL.contains("google_sdk"),
            Build.MODEL.lowercase().contains("emulator"),
            Build.MANUFACTURER.contains("Genymotion"),
            Build.BRAND.startsWith("generic") && Build.DEVICE.startsWith("generic"),
            "google_sdk" == Build.PRODUCT,
            Build.HARDWARE.contains("goldfish"),
            Build.HARDWARE.contains("ranchu"),
            Build.BOARD.lowercase().contains("unknown"),
            Build.ID.contains("FRF91") && Build.TAGS.contains("test-keys")
        )

        return buildIndicators.any { it }
    }

    /**
     * 检查高级的模拟器指标
     */
    private fun checkAdvancedEmulatorIndicators(context: Context): Boolean {
        // 检查设备ID
        val androidId = Settings.Secure.getString(
            context.contentResolver,
            Settings.Secure.ANDROID_ID
        )

        // 模拟器的Android ID通常有特定模式
        return androidId == null ||
                androidId == "9774d56d682e549c" ||
                androidId.isEmpty()
    }

    /**
     * 检查硬件指标
     */
    private fun checkHardwareIndicators(): Boolean {
        // 检查CPU信息
        return try {
            val cpuInfo = File("/proc/cpuinfo").readText()
            cpuInfo.contains("hypervisor") ||
                    cpuInfo.contains("vmware") ||
                    cpuInfo.contains("qemu") ||
                    cpuInfo.contains("kvm")
        } catch (e: Exception) {
            false
        }
    }

    // ==================== Xposed/Frida检测 ====================

    /**
     * 检测是否安装了Xposed框架
     *
     * @return true 如果检测到Xposed
     */
    fun isXposedInstalled(): Boolean {
        return checkXposedFiles() || checkXposedClasses() || checkXposedStackTrace()
    }

    /**
     * 检查Xposed相关文件
     */
    private fun checkXposedFiles(): Boolean {
        val xposedFiles = arrayOf(
            "/system/framework/XposedBridge.jar",
            "/system/bin/app_process_xposed",
            "/system/xbin/xposed",
            "/data/data/de.robv.android.xposed.installer",
            "/data/data/io.github.lsposed.manager",
            "/data/data/org.lsposed.manager"
        )

        return xposedFiles.any { File(it).exists() }
    }

    /**
     * 检查Xposed类
     */
    private fun checkXposedClasses(): Boolean {
        val xposedClasses = arrayOf(
            "de.robv.android.xposed.XposedBridge",
            "de.robv.android.xposed.XposedHelpers",
            "io.github.lsposed.lspd.core.Main"
        )

        return xposedClasses.any { className ->
            try {
                Class.forName(className)
                true
            } catch (e: ClassNotFoundException) {
                false
            }
        }
    }

    /**
     * 检查堆栈跟踪中的Xposed痕迹
     */
    private fun checkXposedStackTrace(): Boolean {
        return try {
            throw Exception("Check Xposed")
        } catch (e: Exception) {
            e.stackTrace.any { element ->
                element.className.contains("xposed") ||
                        element.className.contains("Xposed") ||
                        element.methodName.contains("xposed") ||
                        element.methodName.contains("Xposed")
            }
        }
    }

    /**
     * 检测Frida
     *
     * @return true 如果检测到Frida
     */
    fun isFridaDetected(): Boolean {
        return checkFridaPort() || checkFridaProcesses() || checkFridaModules()
    }

    /**
     * 检查Frida默认端口
     */
    private fun checkFridaPort(): Boolean {
        return try {
            val process = Runtime.getRuntime().exec("netstat -an")
            process.inputStream.bufferedReader().use { reader ->
                reader.readText().contains(":27042")
            }
        } catch (e: Exception) {
            false
        }
    }

    /**
     * 检查Frida进程
     */
    private fun checkFridaProcesses(): Boolean {
        val fridaProcesses = arrayOf(
            "frida-server",
            "frida-helper",
            "frida-agent"
        )

        return try {
            val process = Runtime.getRuntime().exec("ps")
            val output = process.inputStream.bufferedReader().use { it.readText() }
            fridaProcesses.any { output.contains(it) }
        } catch (e: Exception) {
            false
        }
    }

    /**
     * 检查Frida模块
     */
    private fun checkFridaModules(): Boolean {
        return try {
            val mapsFile = File("/proc/self/maps")
            val content = mapsFile.readText()
            content.contains("frida") ||
                    content.contains("frida-agent") ||
                    content.contains("linjector")
        } catch (e: Exception) {
            false
        }
    }

    // ==================== 应用完整性校验 ====================

    /**
     * 计算字符串的SHA-256哈希
     *
     * @param input 输入字符串
     * @return SHA-256哈希值
     */
    fun sha256(input: String): String {
        return try {
            val digest = MessageDigest.getInstance("SHA-256")
            val hash = digest.digest(input.toByteArray(Charsets.UTF_8))
            hash.joinToString("") { "%02x".format(it) }
        } catch (e: NoSuchAlgorithmException) {
            ""
        }
    }

    /**
     * 计算字节数组的SHA-256哈希
     *
     * @param input 输入字节数组
     * @return SHA-256哈希值
     */
    fun sha256(input: ByteArray): String {
        return try {
            val digest = MessageDigest.getInstance("SHA-256")
            val hash = digest.digest(input)
            hash.joinToString("") { "%02x".format(it) }
        } catch (e: NoSuchAlgorithmException) {
            ""
        }
    }

    /**
     * 验证应用签名
     * 用于校验应用是否被重新签名
     *
     * @param context 应用上下文
     * @param expectedSignature 预期的签名哈希
     * @return true 如果签名匹配
     */
    fun verifyAppSignature(context: Context, expectedSignature: String): Boolean {
        return try {
            val packageManager = context.packageManager
            val packageName = context.packageName

            val signatures = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                packageManager.getPackageInfo(
                    packageName,
                    android.content.pm.PackageManager.GET_SIGNING_CERTIFICATES
                ).signingInfo.apkContentsSigners
            } else {
                @Suppress("DEPRECATION")
                packageManager.getPackageInfo(
                    packageName,
                    android.content.pm.PackageManager.GET_SIGNATURES
                ).signatures
            }

            signatures?.any { signature ->
                sha256(signature.toByteArray()) == expectedSignature
            } ?: false
        } catch (e: Exception) {
            false
        }
    }

    // ==================== 综合安全检测 ====================

    /**
     * 执行全面的安全检测
     *
     * @param context 应用上下文
     * @return 安全检测结果
     */
    fun performSecurityCheck(context: Context): SecurityCheckResult {
        return SecurityCheckResult(
            isDebugged = isDebugged(),
            isDebuggable = isDebuggable(context),
            isRooted = isDeviceRooted(),
            isEmulator = isEmulator(context),
            isXposedInstalled = isXposedInstalled(),
            isFridaDetected = isFridaDetected()
        )
    }

    /**
     * 安全检测结果数据类
     */
    data class SecurityCheckResult(
        val isDebugged: Boolean,
        val isDebuggable: Boolean,
        val isRooted: Boolean,
        val isEmulator: Boolean,
        val isXposedInstalled: Boolean,
        val isFridaDetected: Boolean
    ) {
        /**
         * 是否存在安全风险
         */
        fun hasSecurityRisk(): Boolean {
            return isDebugged || isRooted || isXposedInstalled || isFridaDetected
        }

        /**
         * 获取风险描述列表
         */
        fun getRiskDescriptions(): List<String> {
            val risks = mutableListOf<String>()
            if (isDebugged) risks.add("检测到调试器")
            if (isDebuggable) risks.add("应用处于可调试模式")
            if (isRooted) risks.add("设备已Root")
            if (isEmulator) risks.add("运行在模拟器中")
            if (isXposedInstalled) risks.add("检测到Xposed框架")
            if (isFridaDetected) risks.add("检测到Frida")
            return risks
        }
    }
}
