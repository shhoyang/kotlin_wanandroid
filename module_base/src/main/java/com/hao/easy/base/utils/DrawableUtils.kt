package com.hao.easy.base.utils

import android.graphics.Color
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable

/**
 * @author Yang Shihao
 */

object DrawableUtils {

    fun generateRoundRectDrawable(radius: Float, color: Int): Drawable {
        val drawable = GradientDrawable()
        drawable.setColor(color)
        drawable.cornerRadius = radius
        return drawable
    }

    fun generateRoundRectBorderDrawable(radius: Float, strokeWidth: Int, strokeColor: Int, bgColor: Int = Color.TRANSPARENT): Drawable {
        val drawable = GradientDrawable()
        drawable.setStroke(strokeWidth, strokeColor)
        drawable.setColor(bgColor)
        drawable.cornerRadius = radius
        return drawable
    }
}