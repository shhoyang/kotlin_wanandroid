package com.hao.easy.base.extensions

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

/**
 * @author Yang Shihao
 * @date 2018/11/22
 */

fun ImageView.load(url: Any) {
    Glide.with(this).load(url).into(this)
}

fun ImageView.load(url: Any, holder: Int) {
    val options = RequestOptions()
        .placeholder(holder)
        .error(holder)
    Glide.with(this).load(url).apply(options).into(this)
}

fun ImageView.loadCircle(url: Any) {
    Glide.with(this).load(url).apply(RequestOptions.circleCropTransform()).into(this)
}