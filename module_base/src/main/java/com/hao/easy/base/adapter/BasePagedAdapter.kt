package com.hao.easy.base.adapter

import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil

abstract class BasePagedAdapter<T : BaseItem>(
    private val layoutId: Int,
    diff: DiffUtil.ItemCallback<T> = Diff()
) :
    PagedListAdapter<T, ViewHolder>(diff) {

    protected var itemClickListener: OnItemClickListener<T>? = null
    protected var itemLongClickListener: OnItemLongClickListener<T>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(parent.context, parent, layoutId)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        bindViewHolder(holder, getItem(position)!!, position)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int, payloads: MutableList<Any>) {
        itemClickListener?.apply {
            holder.itemView.setOnClickListener {
                itemClicked(it, getItem(position)!!, position)
            }
        }
        bindViewHolder(holder, getItem(position)!!, position, payloads)
    }

    fun setOnItemClickListener(itemClickListener: OnItemClickListener<T>?) {
        this.itemClickListener = itemClickListener
    }

    fun setOnItemLongClickListener(itemLongClickListener: OnItemLongClickListener<T>?) {
        this.itemLongClickListener = itemLongClickListener
    }

    open fun bindViewHolder(
        holder: ViewHolder,
        item: T,
        position: Int,
        payloads: MutableList<Any>
    ) {
        bindViewHolder(holder, item, position)
    }

    abstract fun bindViewHolder(holder: ViewHolder, item: T, position: Int)

    fun changeItem(position: Int) {
        if (position in 0 until itemCount) {
            notifyItemChanged(position)
        }
    }

    fun changeItem(position: Int, payload: Any?) {
        if (position in 0 until itemCount) {
            notifyItemChanged(position, payload)
        }
    }
}