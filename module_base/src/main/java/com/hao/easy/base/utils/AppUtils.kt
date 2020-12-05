package com.hao.easy.base.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.app.ActivityManager
import android.content.Context
import android.content.pm.PackageManager.NameNotFoundException
import android.telephony.TelephonyManager
import android.text.TextUtils
import java.io.BufferedReader
import java.io.FileReader
import java.io.IOException


/**
 * @author Yang Shihao
 */
object AppUtils {

    /**
     * 获取应用程序名称
     */
    fun getAppName(context: Context): String? {
        return try {
            val packageManager = context.packageManager
            val packageInfo = packageManager.getPackageInfo(
                context.packageName, 0
            )
            val labelRes = packageInfo.applicationInfo.labelRes
            context.resources.getString(labelRes)
        } catch (e: NameNotFoundException) {
            ""
        }

        return null
    }

    /**
     * 获取应用程序版本名称信息
     */
    fun getVersionName(context: Context): String {
        return try {
            val packageManager = context.packageManager
            val packageInfo = packageManager.getPackageInfo(context.packageName, 0)
            packageInfo.versionName
        } catch (e: NameNotFoundException) {
            ""
        }
    }

    /**
     * 获取应用程序版本
     */
    fun getVersionCode(context: Context): Int {
        return try {
            val packageManager = context.packageManager
            val packageInfo = packageManager.getPackageInfo(context.packageName, 0)
            packageInfo.versionCode
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
        return return android.os.Build.MODEL
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
            val tm = context.getSystemService(Activity.TELEPHONY_SERVICE) as TelephonyManager?
            tm?.deviceId ?: ""
        } catch (e: Exception) {
            ""
        }
    }

    /**
     * 获取手机号
     */
    @SuppressLint("MissingPermission")
    fun getPhoneNum(context: Context): String? {

        var phone: String? = null

        try {
            val tm = context.getSystemService(Activity.TELEPHONY_SERVICE) as TelephonyManager?
            phone = tm?.line1Number
        } catch (e: Exception) {

        }
        return phone
    }

    /**
     * 获取进程名字
     */
    fun getProcessName(pid: Int): String? {
        var reader: BufferedReader? = null
        try {
            reader = BufferedReader(FileReader("/proc/$pid/cmdline"))
            var processName = reader.readLine()
            if (!TextUtils.isEmpty(processName)) {
                processName = processName.trim()
            }
            return processName
        } catch (throwable: Throwable) {

        } finally {
            try {
                reader?.close()
            } catch (e: IOException) {
            }

        }
        return null
    }

    /**
     * app是否正在运行
     */
    fun getAppState(context: Context, packageName: String): Boolean {
        val am = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val list = am.getRunningTasks(100)
        for (info in list) {
            if (info.topActivity?.packageName == packageName || info.baseActivity?.packageName == packageName) {
                return true
            }
        }

        return false
    }

    fun isForeground(context: Context?, className: String): Boolean {
        if (context == null || TextUtils.isEmpty(className)) {
            return false
        }

        val am = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val list = am.getRunningTasks(1)
        if (list != null && list.size > 0) {
            val cpn = list[0].topActivity
            if (className == cpn?.className) {
                return true
            }
        }

        return false

    }

    /**
     * 获取服务是否开启
     *
     * @param className 完整包名的服务类名
     */
    fun isRunningService(context: Context, className: String): Boolean {

        val activityManager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val runningServices = activityManager.getRunningServices(100)
        for (runningServiceInfo in runningServices) {
            val service = runningServiceInfo.service
            if (className == service.className) {
                return true
            }
        }
        return false
    }

    /**
     * 判断相对应的APP是否存在
     */
    fun appInstalled(context: Context, packageName: String): Boolean {
        val packageManager = context.packageManager
        //获取手机系统的所有APP包名，然后进行一一比较
        val packageInfos = packageManager.getInstalledPackages(0)
        for (i in packageInfos.indices) {
            if (packageInfos[i].packageName.equals(packageName, ignoreCase = true)) {
                return true
            }
        }
        return false
    }
}
