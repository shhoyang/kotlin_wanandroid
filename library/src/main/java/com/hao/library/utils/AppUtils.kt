package com.hao.library.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.app.ActivityManager
import android.content.Context
import android.content.pm.PackageManager.NameNotFoundException
import android.telephony.TelephonyManager

/**
 * @author Yang Shihao
 */
object AppUtils {

    /**
     * 获取应用程序名称
     */
    fun getAppName(context: Context): String {
        return try {
            context.resources.getString(
                context.packageManager.getPackageInfo(
                    context.packageName,
                    0
                ).applicationInfo.labelRes
            )
        } catch (e: NameNotFoundException) {
            ""
        }
    }

    /**
     * 获取应用程序版本名称信息
     */
    fun getVersionName(context: Context): String {
        return try {
            context.packageManager.getPackageInfo(context.packageName, 0).versionName
        } catch (e: NameNotFoundException) {
            ""
        }
    }

    /**
     * 获取应用程序版本
     */
    fun getVersionCode(context: Context): Int {
        return try {
            val packageInfo = context.packageManager.getPackageInfo(context.packageName, 0)
            packageInfo.longVersionCode.toInt()
        } catch (e: NameNotFoundException) {
            0
        }
    }

    /**
     * 获取系统版本
     */
    fun getSystemVersion(): String {
        return android.os.Build.VERSION.RELEASE
    }

    /**
     * 获取手机型号
     */
    fun getSystemModel(): String {
        return android.os.Build.MODEL
    }

    /**
     * 获取厂商
     */
    fun getDeviceBrand(): String {
        return android.os.Build.BRAND
    }

    /**
     * 获取IMEI
     */
    @SuppressLint("MissingPermission")
    fun getIMEI(context: Context): String {
        return try {
            val tm = context.getSystemService(Activity.TELEPHONY_SERVICE) as TelephonyManager
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                tm.imei
            } else {
                tm.deviceId
            }
        } catch (e: Exception) {
            ""
        }
    }

    /**
     * 获取手机号
     */
    @SuppressLint("MissingPermission")
    fun getPhoneNum(context: Context): String {
        return try {
            val tm = context.getSystemService(Activity.TELEPHONY_SERVICE) as TelephonyManager
            tm.line1Number
        } catch (e: Exception) {
            ""
        }
    }

    /**
     * 进程名字
     */
    fun getProcessName(context: Context, pid: Int): String? {
        val am = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        am.runningAppProcesses.forEach {
            if (it.pid == pid) {
                return it.processName
            }
        }
        return null
    }

    /**
     * 是否是主进程
     */
    fun isMainProcess(context: Context, pid: Int): Boolean {
        context.packageName
        val am = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        am.runningAppProcesses.forEach {
            if (it.pid == pid) {
                return context.packageName == it.processName
            }
        }
        return false
    }

    /**
     * APP是否安装
     */
    fun appInstalled(context: Context, packageName: String): Boolean {
        val packages = context.packageManager.getInstalledPackages(0)
        packages.forEach {
            if (it.packageName.equals(packageName, true)) {
                return true
            }
        }
        return false
    }
}
