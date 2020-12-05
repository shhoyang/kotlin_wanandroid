package com.hao.easy.base.utils

import android.graphics.Color
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.RoundRectShape

/**
 * @author Yang Shihao
 */

object DrawableUtils {

    fun generateRoundRectDrawable(radius: Float, color: Int): Drawable {
        val outRect = floatArrayOf(radius, radius, radius, radius, radius, radius, radius, radius)
        val roundRectShape = RoundRectShape(outRect, null, null)
        val shapeDrawable = ShapeDrawable(roundRectShape)
        shapeDrawable.paint.color = color
        return shapeDrawable
    }

    fun generateRoundRectBorderDrawable(radius: Float, strokeWidth: Int, strokeColor: Int, bgColor: Int = Color.TRANSPARENT): Drawable {
        val gradientDrawable = GradientDrawable()
        gradientDrawable.setStroke(strokeWidth, strokeColor)
        gradientDrawable.setColor(bgColor)
        gradientDrawable.cornerRadius = radius
        return gradientDrawable
    }
}