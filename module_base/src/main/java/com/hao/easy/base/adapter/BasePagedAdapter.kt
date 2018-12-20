package com.hao.easy.base.adapter

import android.arch.paging.PagedListAdapter
import android.content.Context
import android.support.v7.util.DiffUtil
import android.view.View
import android.view.ViewGroup

abstract class BasePagedAdapter<T : BaseItem>(private val layoutId: Int) : PagedListAdapter<T, ViewHolder>(Diff<T>()) {

    lateinit var context: Context

    var itemClickListener: ((View, T, Int) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        return ViewHolder(context, parent, layoutId)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        bindViewHolder(holder, getItem(position)!!, position)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int, payloads: MutableList<Any>) {
        itemClickListener?.apply { holder.itemView.setOnClickListener { this(holder.itemView, getItem(position)!!, position) } }
        bindViewHolder(holder, getItem(position)!!, position, payloads)
    }

    open fun bindViewHolder(holder: ViewHolder, item: T, position: Int, payloads: MutableList<Any>) {
        bindViewHolder(holder, item, position)
    }

    abstract fun bindViewHolder(holder: ViewHolder, item: T, position: Int)

    fun changeItem(position: Int) {
        if (position in 0..(itemCount - 1)) {
            notifyItemChanged(position)
        }
    }

    fun changeItem(position: Int, payload: Any?) {
        if (position in 0..(itemCount - 1)) {
            notifyItemChanged(position, payload)
        }
    }

    class Diff<T : BaseItem> : DiffUtil.ItemCallback<T>() {
        override fun areItemsTheSame(item: T, item1: T) = item.id == item1.id

        override fun areContentsTheSame(item: T, item1: T) = item == item1
    }
}