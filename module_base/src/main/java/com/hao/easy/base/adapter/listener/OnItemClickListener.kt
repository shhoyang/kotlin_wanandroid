package com.hao.easy.base.adapter.listener

import android.view.View
import com.hao.easy.base.adapter.ViewHolder

/**
 * @author Yang Shihao
 * @Date 2020/7/21
 */

interface OnItemClickListener<T> {
    fun itemClicked(holder: ViewHolder, view: View, item: T, position: Int)
}