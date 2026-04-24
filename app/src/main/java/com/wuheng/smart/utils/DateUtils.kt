package com.wuheng.smart.utils

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.TimeZone
import java.util.concurrent.TimeUnit

/**
 * 日期时间工具类
 * 提供日期格式化、解析、计算等功能
 */
object DateUtils {

    // 常用日期格式
    const val FORMAT_DATE_TIME = "yyyy-MM-dd HH:mm:ss"
    const val FORMAT_DATE = "yyyy-MM-dd"
    const val FORMAT_TIME = "HH:mm:ss"
    const val FORMAT_DATE_TIME_SHORT = "yyyyMMddHHmmss"
    const val FORMAT_DATE_SHORT = "yyyyMMdd"
    const val FORMAT_TIME_SHORT = "HHmmss"
    const val FORMAT_YEAR_MONTH = "yyyy-MM"
    const val FORMAT_MONTH_DAY = "MM-dd"
    const val FORMAT_HOUR_MINUTE = "HH:mm"
    const val FORMAT_DATE_TIME_MILLIS = "yyyy-MM-dd HH:mm:ss.SSS"
    const val FORMAT_ISO8601 = "yyyy-MM-dd'T'HH:mm:ss'Z'"

    // 默认时区
    private val DEFAULT_TIMEZONE = TimeZone.getTimeZone("Asia/Shanghai")

    /**
     * 获取当前时间戳（毫秒）
     */
    fun currentTimeMillis(): Long = System.currentTimeMillis()

    /**
     * 获取当前时间戳（秒）
     */
    fun currentTimeSeconds(): Long = System.currentTimeMillis() / 1000

    /**
     * 格式化日期时间为字符串
     *
     * @param date 日期对象
     * @param pattern 格式模式
     * @param locale 地区
     * @return 格式化后的字符串
     */
    fun format(
        date: Date?,
        pattern: String = FORMAT_DATE_TIME,
        locale: Locale = Locale.getDefault()
    ): String {
        if (date == null) return ""
        return SimpleDateFormat(pattern, locale).apply {
            timeZone = DEFAULT_TIMEZONE
        }.format(date)
    }

    /**
     * 格式化时间戳为字符串
     *
     * @param timestamp 时间戳（毫秒）
     * @param pattern 格式模式
     * @return 格式化后的字符串
     */
    fun format(
        timestamp: Long,
        pattern: String = FORMAT_DATE_TIME
    ): String {
        return format(Date(timestamp), pattern)
    }

    /**
     * 解析日期字符串为Date对象
     *
     * @param dateString 日期字符串
     * @param pattern 格式模式
     * @param locale 地区
     * @return Date对象，解析失败返回null
     */
    fun parse(
        dateString: String?,
        pattern: String = FORMAT_DATE_TIME,
        locale: Locale = Locale.getDefault()
    ): Date? {
        if (dateString.isNullOrBlank()) return null
        return try {
            SimpleDateFormat(pattern, locale).apply {
                timeZone = DEFAULT_TIMEZONE
            }.parse(dateString)
        } catch (e: Exception) {
            null
        }
    }

    /**
     * 解析日期字符串为时间戳
     *
     * @param dateString 日期字符串
     * @param pattern 格式模式
     * @return 时间戳（毫秒），解析失败返回0
     */
    fun parseToMillis(
        dateString: String?,
        pattern: String = FORMAT_DATE_TIME
    ): Long {
        return parse(dateString, pattern)?.time ?: 0L
    }

    /**
     * 获取当前日期字符串
     *
     * @param pattern 格式模式
     * @return 当前日期字符串
     */
    fun getCurrentDateString(pattern: String = FORMAT_DATE): String {
        return format(Date(), pattern)
    }

    /**
     * 获取当前时间字符串
     *
     * @param pattern 格式模式
     * @return 当前时间字符串
     */
    fun getCurrentTimeString(pattern: String = FORMAT_TIME): String {
        return format(Date(), pattern)
    }

    /**
     * 获取年份
     *
     * @param date 日期对象，默认为当前日期
     * @return 年份
     */
    fun getYear(date: Date = Date()): Int {
        return Calendar.getInstance().apply {
            time = date
            timeZone = DEFAULT_TIMEZONE
        }.get(Calendar.YEAR)
    }

    /**
     * 获取月份（1-12）
     *
     * @param date 日期对象，默认为当前日期
     * @return 月份
     */
    fun getMonth(date: Date = Date()): Int {
        return Calendar.getInstance().apply {
            time = date
            timeZone = DEFAULT_TIMEZONE
        }.get(Calendar.MONTH) + 1
    }

    /**
     * 获取日期（1-31）
     *
     * @param date 日期对象，默认为当前日期
     * @return 日期
     */
    fun getDay(date: Date = Date()): Int {
        return Calendar.getInstance().apply {
            time = date
            timeZone = DEFAULT_TIMEZONE
        }.get(Calendar.DAY_OF_MONTH)
    }

    /**
     * 获取小时（0-23）
     *
     * @param date 日期对象，默认为当前日期
     * @return 小时
     */
    fun getHour(date: Date = Date()): Int {
        return Calendar.getInstance().apply {
            time = date
            timeZone = DEFAULT_TIMEZONE
        }.get(Calendar.HOUR_OF_DAY)
    }

    /**
     * 获取分钟（0-59）
     *
     * @param date 日期对象，默认为当前日期
     * @return 分钟
     */
    fun getMinute(date: Date = Date()): Int {
        return Calendar.getInstance().apply {
            time = date
            timeZone = DEFAULT_TIMEZONE
        }.get(Calendar.MINUTE)
    }

    /**
     * 获取星期几（1-7，1为周日）
     *
     * @param date 日期对象，默认为当前日期
     * @return 星期几
     */
    fun getDayOfWeek(date: Date = Date()): Int {
        return Calendar.getInstance().apply {
            time = date
            timeZone = DEFAULT_TIMEZONE
        }.get(Calendar.DAY_OF_WEEK)
    }

    /**
     * 获取星期几的中文名称
     *
     * @param date 日期对象，默认为当前日期
     * @return 星期几的中文名称
     */
    fun getDayOfWeekChinese(date: Date = Date()): String {
        val weekDays = arrayOf("周日", "周一", "周二", "周三", "周四", "周五", "周六")
        return weekDays[getDayOfWeek(date) - 1]
    }

    /**
     * 添加天数
     *
     * @param date 日期对象
     * @param days 天数（可为负）
     * @return 新的日期对象
     */
    fun addDays(date: Date, days: Int): Date {
        return Calendar.getInstance().apply {
            time = date
            timeZone = DEFAULT_TIMEZONE
            add(Calendar.DAY_OF_MONTH, days)
        }.time
    }

    /**
     * 添加小时
     *
     * @param date 日期对象
     * @param hours 小时数（可为负）
     * @return 新的日期对象
     */
    fun addHours(date: Date, hours: Int): Date {
        return Calendar.getInstance().apply {
            time = date
            timeZone = DEFAULT_TIMEZONE
            add(Calendar.HOUR_OF_DAY, hours)
        }.time
    }

    /**
     * 添加分钟
     *
     * @param date 日期对象
     * @param minutes 分钟数（可为负）
     * @return 新的日期对象
     */
    fun addMinutes(date: Date, minutes: Int): Date {
        return Calendar.getInstance().apply {
            time = date
            timeZone = DEFAULT_TIMEZONE
            add(Calendar.MINUTE, minutes)
        }.time
    }

    /**
     * 添加月份
     *
     * @param date 日期对象
     * @param months 月数（可为负）
     * @return 新的日期对象
     */
    fun addMonths(date: Date, months: Int): Date {
        return Calendar.getInstance().apply {
            time = date
            timeZone = DEFAULT_TIMEZONE
            add(Calendar.MONTH, months)
        }.time
    }

    /**
     * 计算两个日期之间的天数差
     *
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @return 天数差
     */
    fun daysBetween(startDate: Date, endDate: Date): Long {
        val diffMillis = endDate.time - startDate.time
        return TimeUnit.DAYS.convert(diffMillis, TimeUnit.MILLISECONDS)
    }

    /**
     * 计算两个日期之间的小时差
     *
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @return 小时差
     */
    fun hoursBetween(startDate: Date, endDate: Date): Long {
        val diffMillis = endDate.time - startDate.time
        return TimeUnit.HOURS.convert(diffMillis, TimeUnit.MILLISECONDS)
    }

    /**
     * 计算两个日期之间的分钟差
     *
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @return 分钟差
     */
    fun minutesBetween(startDate: Date, endDate: Date): Long {
        val diffMillis = endDate.time - startDate.time
        return TimeUnit.MINUTES.convert(diffMillis, TimeUnit.MILLISECONDS)
    }

    /**
     * 判断是否为同一天
     *
     * @param date1 日期1
     * @param date2 日期2
     * @return 是否为同一天
     */
    fun isSameDay(date1: Date, date2: Date): Boolean {
        val cal1 = Calendar.getInstance().apply {
            time = date1
            timeZone = DEFAULT_TIMEZONE
        }
        val cal2 = Calendar.getInstance().apply {
            time = date2
            timeZone = DEFAULT_TIMEZONE
        }
        return cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
                cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR)
    }

    /**
     * 判断是否为今天
     *
     * @param date 日期对象
     * @return 是否为今天
     */
    fun isToday(date: Date): Boolean {
        return isSameDay(date, Date())
    }

    /**
     * 判断是否为昨天
     *
     * @param date 日期对象
     * @return 是否为昨天
     */
    fun isYesterday(date: Date): Boolean {
        val yesterday = addDays(Date(), -1)
        return isSameDay(date, yesterday)
    }

    /**
     * 判断是否为明天
     *
     * @param date 日期对象
     * @return 是否为明天
     */
    fun isTomorrow(date: Date): Boolean {
        val tomorrow = addDays(Date(), 1)
        return isSameDay(date, tomorrow)
    }

    /**
     * 获取一天的开始时间
     *
     * @param date 日期对象
     * @return 当天开始时间
     */
    fun getStartOfDay(date: Date = Date()): Date {
        return Calendar.getInstance().apply {
            time = date
            timeZone = DEFAULT_TIMEZONE
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }.time
    }

    /**
     * 获取一天的结束时间
     *
     * @param date 日期对象
     * @return 当天结束时间
     */
    fun getEndOfDay(date: Date = Date()): Date {
        return Calendar.getInstance().apply {
            time = date
            timeZone = DEFAULT_TIMEZONE
            set(Calendar.HOUR_OF_DAY, 23)
            set(Calendar.MINUTE, 59)
            set(Calendar.SECOND, 59)
            set(Calendar.MILLISECOND, 999)
        }.time
    }

    /**
     * 获取月份的第一天
     *
     * @param date 日期对象
     * @return 月份第一天
     */
    fun getFirstDayOfMonth(date: Date = Date()): Date {
        return Calendar.getInstance().apply {
            time = date
            timeZone = DEFAULT_TIMEZONE
            set(Calendar.DAY_OF_MONTH, 1)
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }.time
    }

    /**
     * 获取月份的最后一天
     *
     * @param date 日期对象
     * @return 月份最后一天
     */
    fun getLastDayOfMonth(date: Date = Date()): Date {
        return Calendar.getInstance().apply {
            time = date
            timeZone = DEFAULT_TIMEZONE
            set(Calendar.DAY_OF_MONTH, getActualMaximum(Calendar.DAY_OF_MONTH))
            set(Calendar.HOUR_OF_DAY, 23)
            set(Calendar.MINUTE, 59)
            set(Calendar.SECOND, 59)
            set(Calendar.MILLISECOND, 999)
        }.time
    }

    /**
     * 获取友好时间显示
     *
     * @param timestamp 时间戳（毫秒）
     * @return 友好时间字符串（如：刚刚、5分钟前、今天 10:30、昨天 10:30、2024-01-01）
     */
    fun getFriendlyTime(timestamp: Long): String {
        val now = System.currentTimeMillis()
        val diffMillis = now - timestamp

        return when {
            diffMillis < TimeUnit.MINUTES.toMillis(1) -> "刚刚"
            diffMillis < TimeUnit.HOURS.toMillis(1) -> "${TimeUnit.MINUTES.convert(diffMillis, TimeUnit.MILLISECONDS)}分钟前"
            diffMillis < TimeUnit.HOURS.toMillis(2) -> "1小时前"
            isToday(Date(timestamp)) -> "今天 ${format(timestamp, FORMAT_HOUR_MINUTE)}"
            isYesterday(Date(timestamp)) -> "昨天 ${format(timestamp, FORMAT_HOUR_MINUTE)}"
            diffMillis < TimeUnit.DAYS.toMillis(7) -> getDayOfWeekChinese(Date(timestamp))
            else -> format(timestamp, FORMAT_DATE)
        }
    }

    /**
     * 获取年龄
     *
     * @param birthDate 出生日期
     * @return 年龄
     */
    fun getAge(birthDate: Date): Int {
        val birthCal = Calendar.getInstance().apply {
            time = birthDate
            timeZone = DEFAULT_TIMEZONE
        }
        val nowCal = Calendar.getInstance().apply {
            timeZone = DEFAULT_TIMEZONE
        }

        var age = nowCal.get(Calendar.YEAR) - birthCal.get(Calendar.YEAR)

        if (nowCal.get(Calendar.DAY_OF_YEAR) < birthCal.get(Calendar.DAY_OF_YEAR)) {
            age--
        }

        return age.coerceAtLeast(0)
    }

    /**
     * 判断是否为闰年
     *
     * @param year 年份
     * @return 是否为闰年
     */
    fun isLeapYear(year: Int): Boolean {
        return (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0)
    }

    /**
     * 获取某月的天数
     *
     * @param year 年份
     * @param month 月份（1-12）
     * @return 天数
     */
    fun getDaysInMonth(year: Int, month: Int): Int {
        return when (month) {
            1, 3, 5, 7, 8, 10, 12 -> 31
            4, 6, 9, 11 -> 30
            2 -> if (isLeapYear(year)) 29 else 28
            else -> 0
        }
    }

    /**
     * 判断日期是否在范围内
     *
     * @param date 要判断的日期
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @return 是否在范围内
     */
    fun isInRange(date: Date, startDate: Date, endDate: Date): Boolean {
        return date.time in startDate.time..endDate.time
    }

    /**
     * 获取时间戳的毫秒部分
     *
     * @param timestamp 时间戳
     * @return 毫秒部分
     */
    fun getMillisPart(timestamp: Long): Int {
        return (timestamp % 1000).toInt()
    }

    /**
     * 将秒转换为时分秒格式
     *
     * @param seconds 秒数
     * @return 时分秒字符串（如：02:30:45）
     */
    fun secondsToHMS(seconds: Long): String {
        val hours = seconds / 3600
        val minutes = (seconds % 3600) / 60
        val secs = seconds % 60
        return String.format("%02d:%02d:%02d", hours, minutes, secs)
    }

    /**
     * 将秒转换为分秒格式
     *
     * @param seconds 秒数
     * @return 分秒字符串（如：30:45）
     */
    fun secondsToMS(seconds: Long): String {
        val minutes = seconds / 60
        val secs = seconds % 60
        return String.format("%02d:%02d", minutes, secs)
    }

    /**
     * 将毫秒转换为可读时间
     *
     * @param millis 毫秒数
     * @return 可读时间字符串（如：2小时30分钟）
     */
    fun millisToReadable(millis: Long): String {
        val hours = TimeUnit.MILLISECONDS.toHours(millis)
        val minutes = TimeUnit.MILLISECONDS.toMinutes(millis) % 60
        val seconds = TimeUnit.MILLISECONDS.toSeconds(millis) % 60

        return buildString {
            if (hours > 0) append("${hours}小时")
            if (minutes > 0) append("${minutes}分钟")
            if (seconds > 0 && hours == 0L) append("${seconds}秒")
        }.ifEmpty { "0秒" }
    }
}
