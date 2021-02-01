package com.hao.library.adapter

import androidx.recyclerview.widget.DiffUtil

/**
 * @author Yang Shihao
 */
class Diff<T : PagedAdapterItem> : DiffUtil.ItemCallback<T>() {

    override fun areItemsTheSame(item: T, item1: T) = item.getKey() == item1.getKey()

    override fun areContentsTheSame(item: T, item1: T) = item.toString() == item1.toString()
}

interface PagedAdapterItem {
    fun getKey(): Any
}
