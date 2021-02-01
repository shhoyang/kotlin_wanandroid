package com.hao.library

import android.app.Activity
import androidx.fragment.app.Fragment
import com.hao.library.annotation.DaggerConstant

/**
 * @author Yang Shihao
 */
object Dagger {

    fun inject(activity: Activity) {
        val clazz = Class.forName(activity.javaClass.name + DaggerConstant.SUFFIX)
        val method = clazz.getMethod(DaggerConstant.STATIC_METHOD_NAME, activity.javaClass)
        method.invoke(null, activity)
    }

    fun inject(fragment: Fragment) {
        val clazz = Class.forName(fragment.javaClass.name + DaggerConstant.SUFFIX)
        val method = clazz.getMethod(DaggerConstant.STATIC_METHOD_NAME, fragment.javaClass)
        method.invoke(null, fragment)
    }
}