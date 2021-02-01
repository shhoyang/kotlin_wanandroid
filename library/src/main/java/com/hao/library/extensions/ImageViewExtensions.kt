package com.hao.library.extensions

import android.view.ViewGroup
import android.widget.ImageView
import androidx.annotation.DrawableRes
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target

/**
 * @author Yang Shihao
 */

fun ImageView.load(url: Any) {
    Glide.with(this).load(url).into(this)
}

fun ImageView.load(url: Any, placeholder: Int) {
    val options = RequestOptions()
        .placeholder(placeholder)
        .error(placeholder)
    Glide.with(this).load(url).apply(options).into(this)
}

fun ImageView.loadCircle(url: Any) {
    Glide.with(this).load(url).apply(RequestOptions.bitmapTransform(CircleCrop())).into(this)
}

fun ImageView.loadRounded(url: Any, radius: Int) {
    Glide.with(this).load(url).apply(RequestOptions.bitmapTransform(RoundedCorners(radius)))
        .into(this)
}

/**
 * @param w 显示的宽
 * @param relW 实际的宽
 * @param relH 实际的高
 */
fun ImageView.loadWithHolder(url: Any?, @DrawableRes placeholder: Int, w: Int, relW: Int, relH: Int) {
    layoutParams?.let {
        layoutParams = with(it) {
            val h = if (relW == 0 || relH == 0) {
                ViewGroup.LayoutParams.WRAP_CONTENT
            } else {
                val ratio = w * 1.0F / relW
                (relH * ratio).toInt()
            }
            it.width = ViewGroup.LayoutParams.MATCH_PARENT
            it.height = h
            this
        }
    }
    if (url == null) {
        Glide.with(this).load(placeholder).into(this)
        return
    }
    val options = RequestOptions()
        .placeholder(placeholder)
        .error(placeholder)
        .encodeQuality(100)
        .override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
        .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
    Glide.with(this).load(url).apply(options).into(this)
}