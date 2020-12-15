package com.hao.easy.base.adapter

/**
 * @author Yang Shihao
 */
interface ItemViewDelegate<T> {

    fun getLayoutId(): Int

    fun isViewType(item: T, position: Int): Boolean

//    fun bindViewHolder(holder: ViewHolder, item: T, position: Int)
}