package com.hao.library.utils

import android.content.Context
import android.util.TypedValue
import com.hao.library.R


/**
 * @author Yang Shihao
 */
object AttrsUtils {

    fun getColorPrimary(context: Context): Int {
        val typedValue = TypedValue()
        context.theme.resolveAttribute(R.attr.colorPrimary, typedValue, true)
        return typedValue.data
    }

    fun getColor(context: Context, attr: Int, defValue: Int): Int {
        val typedValue = TypedValue()
        val attrs = intArrayOf(attr)
        val typedArray = context.obtainStyledAttributes(typedValue.resourceId, attrs)
        val value = typedArray.getColor(0, defValue)
        typedArray.recycle()
        return value
    }

    fun getString(context: Context, attr: Int, defValue: String = ""): String {
        val typedValue = TypedValue()
        val attrs = intArrayOf(attr)
        val typedArray = context.obtainStyledAttributes(typedValue.resourceId, attrs)
        val value = typedArray.getString(0) ?: defValue
        typedArray.recycle()
        return value
    }

    fun getBoolean(context: Context, attr: Int, defValue: Boolean = false): Boolean {
        val typedValue = TypedValue()
        val attrs = intArrayOf(attr)
        val typedArray = context.obtainStyledAttributes(typedValue.resourceId, attrs)
        val value = typedArray.getBoolean(0, defValue)
        typedArray.recycle()
        return value
    }
}