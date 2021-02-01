package com.hao.library.utils

import android.content.Context
import android.text.TextUtils
import android.widget.Toast
import androidx.annotation.StringRes

/**
 * @author Yang Shihao
 */
object T {

    fun short(context: Context?, msg: String?) {
        if (null == context || TextUtils.isEmpty(msg)) {
            return
        }
        val toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT)
//        toast.setText(msg)
        toast.show()
    }

    fun short(context: Context?, @StringRes resId: Int) {
        if (null == context) {
            return
        }
        val toast = Toast.makeText(context, resId, Toast.LENGTH_SHORT)
//        toast.setText(resId)
        toast.show()
    }
}