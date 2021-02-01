package com.hao.library.utils

import android.text.TextUtils
import java.text.SimpleDateFormat
import java.util.*

/**
 * @author Yang Shihao
 */
object DateUtils {

    const val FORMAT_Y = "yyyy"

    const val FORMAT_HM = "HH:mm"

    const val FORMAT_MDHM = "MM-dd HH:mm"

    const val FORMAT_YMD = "yyyy-MM-dd"

    const val FORMAT_YMDHM = "yyyy-MM-dd HH:mm"

    private const val FORMAT = "yyyy-MM-dd HH:mm:ss"

    private const val MILLIS_SECOND = 1000

    private const val MILLIS_MINUTE = 60000

    private const val MILLIS_HOUR = 3600000

    private const val MILLIS_DAY = 86400000

    /**
     * 字符串转换成日期，默认格式
     */
    fun str2Date(str: String): Date? {
        return str2Date(str, null)
    }

    /**
     * 字符串转换成日期，指定格式
     */
    fun str2Date(str: String?, format: String?): Date? {
        var f = format
        if (TextUtils.isEmpty(str)) {
            return null
        }
        if (TextUtils.isEmpty(f)) {
            f = FORMAT
        }
        var date: Date? = null
        try {
            date = SimpleDateFormat(f, Locale.getDefault()).parse(str!!)
        } catch (e: Exception) {
        }

        return date
    }

    /**
     * 日期转换成字符串，默认格式
     */
    fun date2Str(d: Date): String? {
        return date2Str(d, null)
    }

    /**
     * 日期转换成字符串，指定格式
     */
    fun date2Str(d: Date?, format: String?): String? {
        var f = format
        if (d == null) {
            return null
        }
        if (TextUtils.isEmpty(f)) {
            f = FORMAT
        }
        return SimpleDateFormat(f, Locale.getDefault()).format(d)
    }

    /**
     * 获取当前时间，默认格式
     */
    fun getTimeStr(): String {
        return getTimeStr(null)
    }

    /**
     * 获取当前时间，指定格式
     */
    fun getTimeStr(format: String?): String {
        var f = format
        if (TextUtils.isEmpty(f)) {
            f = FORMAT
        }
        return SimpleDateFormat(f, Locale.getDefault()).format(Date())
    }

    /**
     * 时间格式化，默认格式
     */
    fun stamp2Str(millis: Long): String {
        return SimpleDateFormat(FORMAT, Locale.getDefault()).format(Date(millis))
    }

    /**
     * 时间格式化，指定格式
     */
    fun stamp2Str(millis: Long, format: String): String {
        return try {
            SimpleDateFormat(format, Locale.getDefault()).format(Date(millis))
        } catch (e: Exception) {
            ""
        }
    }
}