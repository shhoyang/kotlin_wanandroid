package com.hao.easy.base.extensions

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.view.ViewGroup
import android.widget.ImageView
import androidx.annotation.DrawableRes
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.target.Target
import com.bumptech.glide.request.transition.Transition

/**
 * @author Yang Shihao
 * @date 2018/11/22
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

/**
 * @param w 显示的宽，根据w重设图片的高度
 */
fun ImageView.loadWithHolder(url: Any?, @DrawableRes placeholder: Int, w: Int) {
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
    Glide.with(this).asBitmap().load(url).apply(options).into(object : SimpleTarget<Bitmap>() {

        override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
            val params = layoutParams
            val ratio = w * 1.0F / resource.width
            val h = (resource.height * ratio).toInt()
            if (params != null) {
                params.width = ViewGroup.LayoutParams.MATCH_PARENT
                params.height = h
                layoutParams = params
            }
            setImageBitmap(resource)
        }

        override fun onLoadFailed(errorDrawable: Drawable?) {
            super.onLoadFailed(errorDrawable)
            setImageDrawable(errorDrawable)
        }
    })
}