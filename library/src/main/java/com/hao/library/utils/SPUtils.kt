package com.hao.library.utils

import android.content.Context

/**
 * @author Yang Shihao
 */
object SPUtils {

    private const val FILE_NAME = "sharedPre"

    private fun pref(context: Context) =
        context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE)

    /**
     * 保存数据的方法
     */
    fun put(context: Context, key: String, value: Any) {
        with(pref(context).edit()) {
            when (value) {
                is String -> putString(key, value)
                is Int -> putInt(key, value)
                is Long -> putLong(key, value)
                is Boolean -> putBoolean(key, value)
                is Float -> putFloat(key, value)
                else -> throw IllegalAccessException("this type can not saved into preference")
            }.apply()
        }
    }

    /**
     * 获取数据的方法
     */
    fun <T> get(context: Context, key: String, defaultValue: T): T = with(pref(context)) {
        val value: Any? = when (defaultValue) {
            is String -> getString(key, defaultValue)
            is Int -> getInt(key, defaultValue)
            is Long -> getLong(key, defaultValue)
            is Boolean -> getBoolean(key, defaultValue)
            is Float -> getFloat(key, defaultValue)
            else -> throw IllegalAccessException("this type can not saved into preference")
        }
        return value as T
    }

    /**
     * 移除某个key值已经对应的值
     */
    fun remove(context: Context, key: String) {
        pref(context).edit().remove(key).apply()
    }

    /**
     * 清除所有数据
     */
    fun clear(context: Context) {
        pref(context).edit().clear().apply()
    }

    /**
     * 查询某个key是否已经存在
     */
    fun contains(context: Context, key: String): Boolean {
        return pref(context).contains(key)
    }

    /**
     * 返回所有的键值对
     */
    fun getAll(context: Context): Map<String, *> {
        return pref(context).all
    }
}