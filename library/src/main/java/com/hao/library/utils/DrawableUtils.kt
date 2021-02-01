package com.hao.library.utils

import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.core.graphics.drawable.DrawableCompat

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

    fun generateRoundRectBorderDrawable(
        radius: Float,
        strokeWidth: Int,
        strokeColor: Int,
        bgColor: Int = Color.TRANSPARENT
    ): Drawable {
        val drawable = GradientDrawable()
        drawable.setStroke(strokeWidth, strokeColor)
        drawable.setColor(bgColor)
        drawable.cornerRadius = radius
        return drawable
    }

    fun tint(imageView: ImageView, color: Int) {
        val wrappedDrawable = DrawableCompat.wrap(imageView.drawable)
        DrawableCompat.setTintList(wrappedDrawable, ColorStateList.valueOf(color))
    }

    fun tint(progressBar: ProgressBar, color: Int) {
        val wrappedDrawable = DrawableCompat.wrap(progressBar.indeterminateDrawable)
        DrawableCompat.setTintList(wrappedDrawable, ColorStateList.valueOf(color))
    }
}