package com.hao.easy.base.utils

import android.view.Window
import android.view.WindowManager
import java.lang.reflect.Method
import java.lang.reflect.AccessibleObject.setAccessible


object StatusBarUtil {

    fun MIUISetStatusBarLightMode(window: Window, dark: Boolean) {
        var clazz = window.javaClass
        var darkModeFlag = 0
        var layoutParams = Class.forName("android.view.MiuiWindowManager\$LayoutParams")
        var field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE")
        darkModeFlag = field.getInt(layoutParams)
        var extraFlagField = clazz.getMethod("setExtraFlags", Int::class.java, Int::class.java)
        if (dark) {
            extraFlagField.invoke(window, darkModeFlag, darkModeFlag)
        } else {
            extraFlagField.invoke(window, 0, darkModeFlag)
        }
    }

    fun FlymeSetStatusBarLightMode(window: Window, dark: Boolean) {
        try {
            var layoutParams = window.attributes
            var darkFlag = WindowManager.LayoutParams::class.java.getDeclaredField("MEIZU_FLAG_DARK_STATUS_BAR_ICON")
            var meizuFlags = WindowManager.LayoutParams::class.java.getDeclaredField("meizuFlags")
            darkFlag.isAccessible = true
            meizuFlags.isAccessible = true
            val bit = darkFlag.getInt(null)
            var value = meizuFlags.getInt(layoutParams)
            if (dark) {
                value = value or bit
            } else {
                value  = value and (bit.inv())
            }
            meizuFlags.setInt(layoutParams, value)
            window.attributes = layoutParams
        } catch (e: Exception) {

        }
    }
}