package com.hao.easy.base.adapter

import androidx.recyclerview.widget.DiffUtil

/**
 * @author Yang Shihao
 * @Date 2020-01-14
 */
class Diff<T : BaseItem> : DiffUtil.ItemCallback<T>() {

    override fun areItemsTheSame(item: T, item1: T) = item.getKey() == item1.getKey()

    override fun areContentsTheSame(item: T, item1: T) = item.equals(item1)
}

interface BaseItem {
    fun getKey(): Any
}
