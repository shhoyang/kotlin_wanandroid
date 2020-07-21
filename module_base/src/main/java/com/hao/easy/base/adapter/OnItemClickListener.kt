package com.hao.easy.base.adapter

import android.view.View

/**
 * @author Yang Shihao
 * @Date 2020/7/21
 */

interface OnItemClickListener<T> {
    fun itemClicked(view: View, item: T, position: Int)
}