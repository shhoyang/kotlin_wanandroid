package com.hao.library.adapter.listener

import android.view.View

/**
 * @author Yang Shihao
 */

interface OnItemClickListener<T> {
    fun itemClicked(view: View, item: T, position: Int)
}