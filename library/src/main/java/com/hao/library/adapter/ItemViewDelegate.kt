package com.hao.library.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding

/**
 * @author Yang Shihao
 */
interface ItemViewDelegate<VB : ViewBinding, D : PagedAdapterItem> {

    fun isViewType(item: D, position: Int): Boolean

    fun getViewBinding(layoutInflater: LayoutInflater, parent: ViewGroup): VB

    fun bindViewHolder(
        viewHolder: ViewHolder<VB>,
        item: D,
        position: Int,
        payloads: MutableList<Any>
    )

    fun bind(
        viewHolder: ViewHolder<out ViewBinding>,
        item: D,
        position: Int,
        payloads: MutableList<Any>
    ) {
        bindViewHolder(viewHolder as ViewHolder<VB>, item, position, payloads)
    }
}