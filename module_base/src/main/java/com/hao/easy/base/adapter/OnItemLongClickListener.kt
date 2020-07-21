package com.hao.easy.base.adapter

import android.view.View

/**
 * @author Yang Shihao
 * @Date 2020/7/21
 */

interface OnItemLongClickListener<T> {
    fun itemLongClicked(view: View, item: T, position: Int)
}