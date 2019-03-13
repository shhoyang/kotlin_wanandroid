package com.hao.easy.base.utils

import android.view.Window
import android.view.WindowManager


object StatusBarUtil {

    fun MIUISetStatusBarLightMode(window: Window, dark: Boolean) {
        val clazz = window.javaClass
        var darkModeFlag = 0
        val layoutParams = Class.forName("android.view.MiuiWindowManager\$LayoutParams")
        val field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE")
        darkModeFlag = field.getInt(layoutParams)
        val extraFlagField = clazz.getMethod("setExtraFlags", Int::class.java, Int::class.java)
        if (dark) {
            extraFlagField.invoke(window, darkModeFlag, darkModeFlag)
        } else {
            extraFlagField.invoke(window, 0, darkModeFlag)
        }
    }

    fun FlymeSetStatusBarLightMode(window: Window, dark: Boolean) {
        try {
            val layoutParams = window.attributes
            val darkFlag = WindowManager.LayoutParams::class.java.getDeclaredField("MEIZU_FLAG_DARK_STATUS_BAR_ICON")
            val meizuFlags = WindowManager.LayoutParams::class.java.getDeclaredField("meizuFlags")
            darkFlag.isAccessible = true
            meizuFlags.isAccessible = true
            val bit = darkFlag.getInt(null)
            var value = meizuFlags.getInt(layoutParams)
            value = if (dark) {
                value or bit
            } else {
                value and (bit.inv())
            }
            meizuFlags.setInt(layoutParams, value)
            window.attributes = layoutParams
        } catch (e: Exception) {

        }
    }
}