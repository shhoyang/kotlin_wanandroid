package com.hao.easy.base.utils

import android.content.Context
import android.text.TextUtils
import android.widget.Toast
import androidx.annotation.StringRes

/**
 * @author Yang Shihao
 * @Date 2020/7/20
 */
object T {

    fun short(context: Context?, msg: String?) {
        if (null == context || TextUtils.isEmpty(msg)) {
            return
        }
        val toast = Toast.makeText(context, null, Toast.LENGTH_SHORT)
        toast.setText(msg)
        toast.show()
    }

    fun short(context: Context?, @StringRes resId: Int) {
        if (null == context) {
            return
        }
        val toast = Toast.makeText(context, null, Toast.LENGTH_SHORT)
        toast.setText(resId)
        toast.show()
    }
}