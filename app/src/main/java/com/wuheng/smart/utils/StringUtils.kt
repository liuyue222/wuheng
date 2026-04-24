package com.wuheng.smart.utils

import android.text.TextUtils
import java.net.URLDecoder
import java.net.URLEncoder
import java.security.MessageDigest
import java.util.Base64
import java.util.regex.Pattern

/**
 * 字符串处理工具类
 * 提供字符串验证、转换、格式化等功能
 */
object StringUtils {

    // 常用正则表达式
    private val EMAIL_PATTERN = Pattern.compile(
        "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$"
    )
    private val PHONE_PATTERN = Pattern.compile(
        "^1[3-9]\\d{9}$"
    )
    private val PASSWORD_PATTERN = Pattern.compile(
        "^(?=.*[a-zA-Z])(?=.*\\d)[a-zA-Z\\d@$!%*?&]{6,20}$"
    )
    private val ID_CARD_PATTERN = Pattern.compile(
        "^[1-9]\\d{5}(18|19|20)\\d{2}(0[1-9]|1[0-2])(0[1-9]|[12]\\d|3[01])\\d{3}[\\dXx]$"
    )
    private val URL_PATTERN = Pattern.compile(
        "^(https?|ftp)://[^\\s/$.?#].[^\\s]*$", Pattern.CASE_INSENSITIVE
    )
    private val IPV4_PATTERN = Pattern.compile(
        "^((25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$"
    )
    private val CHINESE_PATTERN = Pattern.compile("[\\u4e00-\\u9fa5]")
    private val DIGIT_PATTERN = Pattern.compile("\\d+")
    private val LETTER_PATTERN = Pattern.compile("[a-zA-Z]+")

    /**
     * 判断字符串是否为空（null或长度为0）
     *
     * @param str 字符串
     * @return 是否为空
     */
    fun isEmpty(str: String?): Boolean {
        return str == null || str.isEmpty()
    }

    /**
     * 判断字符串是否为空白（null、长度为0或仅包含空白字符）
     *
     * @param str 字符串
     * @return 是否为空白
     */
    fun isBlank(str: String?): Boolean {
        return str == null || str.isBlank()
    }

    /**
     * 判断字符串是否不为空
     *
     * @param str 字符串
     * @return 是否不为空
     */
    fun isNotEmpty(str: String?): Boolean {
        return !isEmpty(str)
    }

    /**
     * 判断字符串是否不为空白
     *
     * @param str 字符串
     * @return 是否不为空白
     */
    fun isNotBlank(str: String?): Boolean {
        return !isBlank(str)
    }

    /**
     * 获取字符串长度（null安全）
     *
     * @param str 字符串
     * @return 长度
     */
    fun length(str: String?): Int {
        return str?.length ?: 0
    }

    /**
     * 如果字符串为空，返回默认值
     *
     * @param str 字符串
     * @param default 默认值
     * @return 原字符串或默认值
     */
    fun defaultIfEmpty(str: String?, default: String): String {
        return if (isEmpty(str)) default else str!!
    }

    /**
     * 如果字符串为空白，返回默认值
     *
     * @param str 字符串
     * @param default 默认值
     * @return 原字符串或默认值
     */
    fun defaultIfBlank(str: String?, default: String): String {
        return if (isBlank(str)) default else str!!
    }

    /**
     * 截断字符串
     *
     * @param str 字符串
     * @param maxLength 最大长度
     * @param suffix 后缀（如"..."）
     * @return 截断后的字符串
     */
    fun truncate(str: String?, maxLength: Int, suffix: String = "..."): String {
        if (str == null) return ""
        if (str.length <= maxLength) return str
        return str.substring(0, maxLength - suffix.length) + suffix
    }

    /**
     * 去除字符串两端的空白字符（null安全）
     *
     * @param str 字符串
     * @return 处理后的字符串
     */
    fun trim(str: String?): String {
        return str?.trim() ?: ""
    }

    /**
     * 去除所有空白字符
     *
     * @param str 字符串
     * @return 处理后的字符串
     */
    fun removeAllWhitespace(str: String?): String {
        return str?.replace(Regex("\\s+"), "") ?: ""
    }

    /**
     * 反转字符串
     *
     * @param str 字符串
     * @return 反转后的字符串
     */
    fun reverse(str: String?): String {
        return str?.reversed() ?: ""
    }

    /**
     * 判断字符串是否包含子串（null安全）
     *
     * @param str 字符串
     * @param search 子串
     * @return 是否包含
     */
    fun contains(str: String?, search: String?): Boolean {
        if (str == null || search == null) return false
        return str.contains(search)
    }

    /**
     * 判断字符串是否以指定前缀开头（null安全）
     *
     * @param str 字符串
     * @param prefix 前缀
     * @return 是否以该前缀开头
     */
    fun startsWith(str: String?, prefix: String?): Boolean {
        if (str == null || prefix == null) return false
        return str.startsWith(prefix)
    }

    /**
     * 判断字符串是否以指定后缀结尾（null安全）
     *
     * @param str 字符串
     * @param suffix 后缀
     * @return 是否以该后缀结尾
     */
    fun endsWith(str: String?, suffix: String?): Boolean {
        if (str == null || suffix == null) return false
        return str.endsWith(suffix)
    }

    /**
     * 计算子串出现次数
     *
     * @param str 字符串
     * @param sub 子串
     * @return 出现次数
     */
    fun countOccurrences(str: String?, sub: String?): Int {
        if (str == null || sub.isNullOrEmpty()) return 0
        return str.split(sub).size - 1
    }

    /**
     * 验证邮箱格式
     *
     * @param email 邮箱地址
     * @return 是否有效
     */
    fun isValidEmail(email: String?): Boolean {
        if (email.isNullOrBlank()) return false
        return EMAIL_PATTERN.matcher(email).matches()
    }

    /**
     * 验证手机号格式（中国大陆）
     *
     * @param phone 手机号
     * @return 是否有效
     */
    fun isValidPhone(phone: String?): Boolean {
        if (phone.isNullOrBlank()) return false
        return PHONE_PATTERN.matcher(phone).matches()
    }

    /**
     * 验证密码强度
     * 要求：6-20位，包含字母和数字
     *
     * @param password 密码
     * @return 是否有效
     */
    fun isValidPassword(password: String?): Boolean {
        if (password.isNullOrBlank()) return false
        return PASSWORD_PATTERN.matcher(password).matches()
    }

    /**
     * 验证身份证号
     *
     * @param idCard 身份证号
     * @return 是否有效
     */
    fun isValidIdCard(idCard: String?): Boolean {
        if (idCard.isNullOrBlank()) return false
        if (!ID_CARD_PATTERN.matcher(idCard).matches()) return false
        return validateIdCardChecksum(idCard)
    }

    /**
     * 验证身份证号校验位
     */
    private fun validateIdCardChecksum(idCard: String): Boolean {
        if (idCard.length != 18) return false

        val weights = intArrayOf(7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2)
        val checkCodes = charArrayOf('1', '0', 'X', '9', '8', '7', '6', '5', '4', '3', '2')

        var sum = 0
        for (i in 0 until 17) {
            val digit = idCard[i].code - '0'.code
            if (digit < 0 || digit > 9) return false
            sum += digit * weights[i]
        }

        val checkCode = checkCodes[sum % 11]
        return idCard[17].uppercaseChar() == checkCode
    }

    /**
     * 验证URL格式
     *
     * @param url URL字符串
     * @return 是否有效
     */
    fun isValidUrl(url: String?): Boolean {
        if (url.isNullOrBlank()) return false
        return URL_PATTERN.matcher(url).matches()
    }

    /**
     * 验证IPv4地址
     *
     * @param ip IP地址
     * @return 是否有效
     */
    fun isValidIpv4(ip: String?): Boolean {
        if (ip.isNullOrBlank()) return false
        return IPV4_PATTERN.matcher(ip).matches()
    }

    /**
     * 判断是否包含中文字符
     *
     * @param str 字符串
     * @return 是否包含中文
     */
    fun containsChinese(str: String?): Boolean {
        if (str.isNullOrBlank()) return false
        return CHINESE_PATTERN.matcher(str).find()
    }

    /**
     * 判断是否全为数字
     *
     * @param str 字符串
     * @return 是否全为数字
     */
    fun isNumeric(str: String?): Boolean {
        if (str.isNullOrBlank()) return false
        return str.all { it.isDigit() }
    }

    /**
     * 判断是否全为字母
     *
     * @param str 字符串
     * @return 是否全为字母
     */
    fun isAlpha(str: String?): Boolean {
        if (str.isNullOrBlank()) return false
        return str.all { it.isLetter() }
    }

    /**
     * 判断是否全为字母或数字
     *
     * @param str 字符串
     * @return 是否全为字母或数字
     */
    fun isAlphanumeric(str: String?): Boolean {
        if (str.isNullOrBlank()) return false
        return str.all { it.isLetterOrDigit() }
    }

    /**
     * 隐藏手机号中间部分
     *
     * @param phone 手机号
     * @return 隐藏后的手机号（如：138****8888）
     */
    fun maskPhone(phone: String?): String {
        if (phone.isNullOrBlank() || phone.length != 11) return phone ?: ""
        return phone.substring(0, 3) + "****" + phone.substring(7)
    }

    /**
     * 隐藏邮箱用户名部分
     *
     * @param email 邮箱地址
     * @return 隐藏后的邮箱（如：a***@example.com）
     */
    fun maskEmail(email: String?): String {
        if (email.isNullOrBlank() || !email.contains("@")) return email ?: ""
        val parts = email.split("@")
        val name = parts[0]
        val domain = parts[1]

        val maskedName = when {
            name.length <= 2 -> "*".repeat(name.length)
            name.length <= 4 -> name.first() + "*".repeat(name.length - 2) + name.last()
            else -> name.take(2) + "***" + name.takeLast(2)
        }
        return "$maskedName@$domain"
    }

    /**
     * 隐藏身份证号
     *
     * @param idCard 身份证号
     * @return 隐藏后的身份证号（如：110101********1234）
     */
    fun maskIdCard(idCard: String?): String {
        if (idCard.isNullOrBlank() || idCard.length != 18) return idCard ?: ""
        return idCard.substring(0, 6) + "********" + idCard.substring(14)
    }

    /**
     * 隐藏姓名（保留姓氏）
     *
     * @param name 姓名
     * @return 隐藏后的姓名（如：张**）
     */
    fun maskName(name: String?): String {
        if (name.isNullOrBlank()) return ""
        if (name.length <= 1) return name
        return name.first() + "*".repeat(name.length - 1)
    }

    /**
     * 计算MD5值
     *
     * @param str 字符串
     * @return MD5值（32位小写）
     */
    fun md5(str: String?): String {
        if (str.isNullOrEmpty()) return ""
        return try {
            val md = MessageDigest.getInstance("MD5")
            val digest = md.digest(str.toByteArray(Charsets.UTF_8))
            digest.joinToString("") { "%02x".format(it) }
        } catch (e: Exception) {
            ""
        }
    }

    /**
     * 计算SHA-256值
     *
     * @param str 字符串
     * @return SHA-256值（64位小写）
     */
    fun sha256(str: String?): String {
        if (str.isNullOrEmpty()) return ""
        return try {
            val md = MessageDigest.getInstance("SHA-256")
            val digest = md.digest(str.toByteArray(Charsets.UTF_8))
            digest.joinToString("") { "%02x".format(it) }
        } catch (e: Exception) {
            ""
        }
    }

    /**
     * Base64编码
     *
     * @param str 字符串
     * @return Base64编码后的字符串
     */
    fun base64Encode(str: String?): String {
        if (str.isNullOrEmpty()) return ""
        return Base64.getEncoder().encodeToString(str.toByteArray(Charsets.UTF_8))
    }

    /**
     * Base64解码
     *
     * @param str Base64字符串
     * @return 解码后的字符串
     */
    fun base64Decode(str: String?): String {
        if (str.isNullOrEmpty()) return ""
        return try {
            String(Base64.getDecoder().decode(str), Charsets.UTF_8)
        } catch (e: Exception) {
            ""
        }
    }

    /**
     * URL编码
     *
     * @param str 字符串
     * @return URL编码后的字符串
     */
    fun urlEncode(str: String?): String {
        if (str.isNullOrEmpty()) return ""
        return try {
            URLEncoder.encode(str, Charsets.UTF_8.name())
        } catch (e: Exception) {
            str
        }
    }

    /**
     * URL解码
     *
     * @param str URL编码字符串
     * @return 解码后的字符串
     */
    fun urlDecode(str: String?): String {
        if (str.isNullOrEmpty()) return ""
        return try {
            URLDecoder.decode(str, Charsets.UTF_8.name())
        } catch (e: Exception) {
            str
        }
    }

    /**
     * 驼峰命名转下划线命名
     *
     * @param str 驼峰字符串
     * @return 下划线字符串
     */
    fun camelToSnake(str: String?): String {
        if (str.isNullOrBlank()) return ""
        return str.replace(Regex("([a-z])([A-Z]+)"), "$1_$2").lowercase()
    }

    /**
     * 下划线命名转驼峰命名
     *
     * @param str 下划线字符串
     * @return 驼峰字符串
     */
    fun snakeToCamel(str: String?): String {
        if (str.isNullOrBlank()) return ""
        return str.split("_").mapIndexed { index, word ->
            if (index == 0) word.lowercase()
            else word.replaceFirstChar { it.uppercase() }
        }.joinToString("")
    }

    /**
     * 首字母大写
     *
     * @param str 字符串
     * @return 首字母大写的字符串
     */
    fun capitalize(str: String?): String {
        if (str.isNullOrBlank()) return ""
        return str.replaceFirstChar { it.uppercase() }
    }

    /**
     * 首字母小写
     *
     * @param str 字符串
     * @return 首字母小写的字符串
     */
    fun uncapitalize(str: String?): String {
        if (str.isNullOrBlank()) return ""
        return str.replaceFirstChar { it.lowercase() }
    }

    /**
     * 将字符串转换为整数（null安全）
     *
     * @param str 字符串
     * @param default 默认值
     * @return 整数
     */
    fun toInt(str: String?, default: Int = 0): Int {
        return try {
            str?.toInt() ?: default
        } catch (e: NumberFormatException) {
            default
        }
    }

    /**
     * 将字符串转换为长整数（null安全）
     *
     * @param str 字符串
     * @param default 默认值
     * @return 长整数
     */
    fun toLong(str: String?, default: Long = 0L): Long {
        return try {
            str?.toLong() ?: default
        } catch (e: NumberFormatException) {
            default
        }
    }

    /**
     * 将字符串转换为浮点数（null安全）
     *
     * @param str 字符串
     * @param default 默认值
     * @return 浮点数
     */
    fun toFloat(str: String?, default: Float = 0f): Float {
        return try {
            str?.toFloat() ?: default
        } catch (e: NumberFormatException) {
            default
        }
    }

    /**
     * 将字符串转换为双精度浮点数（null安全）
     *
     * @param str 字符串
     * @param default 默认值
     * @return 双精度浮点数
     */
    fun toDouble(str: String?, default: Double = 0.0): Double {
        return try {
            str?.toDouble() ?: default
        } catch (e: NumberFormatException) {
            default
        }
    }

    /**
     * 将字符串转换为布尔值（null安全）
     *
     * @param str 字符串
     * @param default 默认值
     * @return 布尔值
     */
    fun toBoolean(str: String?, default: Boolean = false): Boolean {
        return when (str?.lowercase()) {
            "true", "1", "yes", "y", "on" -> true
            "false", "0", "no", "n", "off" -> false
            else -> default
        }
    }

    /**
     * 连接字符串数组
     *
     * @param array 字符串数组
     * @param separator 分隔符
     * @return 连接后的字符串
     */
    fun join(array: Array<String?>?, separator: String = ","): String {
        if (array == null) return ""
        return array.filterNotNull().joinToString(separator)
    }

    /**
     * 连接字符串列表
     *
     * @param list 字符串列表
     * @param separator 分隔符
     * @return 连接后的字符串
     */
    fun join(list: List<String?>?, separator: String = ","): String {
        if (list == null) return ""
        return list.filterNotNull().joinToString(separator)
    }

    /**
     * 分割字符串
     *
     * @param str 字符串
     * @param delimiter 分隔符
     * @return 分割后的列表
     */
    fun split(str: String?, delimiter: String = ","): List<String> {
        if (str.isNullOrBlank()) return emptyList()
        return str.split(delimiter).map { it.trim() }.filter { it.isNotEmpty() }
    }

    /**
     * 去除HTML标签
     *
     * @param html HTML字符串
     * @return 纯文本
     */
    fun stripHtml(html: String?): String {
        if (html.isNullOrBlank()) return ""
        return html.replace(Regex("<[^>]*>"), "")
    }

    /**
     * 转义HTML特殊字符
     *
     * @param str 字符串
     * @return 转义后的字符串
     */
    fun escapeHtml(str: String?): String {
        if (str.isNullOrEmpty()) return ""
        return str
            .replace("&", "&amp;")
            .replace("<", "&lt;")
            .replace(">", "&gt;")
            .replace("\"", "&quot;")
            .replace("'", "&#x27;")
    }

    /**
     * 生成指定长度的随机字符串
     *
     * @param length 长度
     * @param useLetters 是否使用字母
     * @param useNumbers 是否使用数字
     * @return 随机字符串
     */
    fun randomString(
        length: Int,
        useLetters: Boolean = true,
        useNumbers: Boolean = true
    ): String {
        val letters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"
        val numbers = "0123456789"
        val chars = buildString {
            if (useLetters) append(letters)
            if (useNumbers) append(numbers)
        }

        if (chars.isEmpty()) return ""

        return (1..length).map { chars.random() }.joinToString("")
    }

    /**
     * 比较两个字符串（null安全）
     *
     * @param str1 字符串1
     * @param str2 字符串2
     * @return 比较结果
     */
    fun compare(str1: String?, str2: String?): Int {
        return when {
            str1 == null && str2 == null -> 0
            str1 == null -> -1
            str2 == null -> 1
            else -> str1.compareTo(str2)
        }
    }

    /**
     * 判断两个字符串是否相等（null安全）
     *
     * @param str1 字符串1
     * @param str2 字符串2
     * @param ignoreCase 是否忽略大小写
     * @return 是否相等
     */
    fun equals(str1: String?, str2: String?, ignoreCase: Boolean = false): Boolean {
        return if (ignoreCase) {
            str1?.equals(str2, ignoreCase = true) ?: (str2 == null)
        } else {
            str1 == str2
        }
    }

    /**
     * 获取字符串字节长度（UTF-8编码）
     *
     * @param str 字符串
     * @return 字节长度
     */
    fun byteLength(str: String?): Int {
        return str?.toByteArray(Charsets.UTF_8)?.size ?: 0
    }

    /**
     * 格式化文件大小
     *
     * @param sizeBytes 大小（字节）
     * @return 格式化后的字符串（如：1.5 MB）
     */
    fun formatFileSize(sizeBytes: Long): String {
        if (sizeBytes < 0) return "0 B"
        val units = arrayOf("B", "KB", "MB", "GB", "TB")
        var size = sizeBytes.toDouble()
        var unitIndex = 0

        while (size >= 1024 && unitIndex < units.size - 1) {
            size /= 1024
            unitIndex++
        }

        return String.format("%.2f %s", size, units[unitIndex])
    }
}
