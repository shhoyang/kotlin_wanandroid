package com.hao.library.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.viewbinding.ViewBinding
import com.hao.library.adapter.listener.OnItemClickListener
import com.hao.library.adapter.listener.OnItemLongClickListener

abstract class BasePagedAdapter<VB : ViewBinding, D : PagedAdapterItem>(
    diff: DiffUtil.ItemCallback<D> = Diff()
) :
    PagedListAdapter<D, ViewHolder<VB>>(diff) {

    protected var itemClickListener: OnItemClickListener<D>? = null
    protected var itemLongClickListener: OnItemLongClickListener<D>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder<VB> {
        return ViewHolder(getViewBinding(LayoutInflater.from(parent.context), parent))
    }

    override fun onBindViewHolder(
        holderHolder: ViewHolder<VB>,
        position: Int,
        payloads: MutableList<Any>
    ) {
        itemClickListener?.apply {
            holderHolder.itemView.setOnClickListener {
                itemClicked(it, getItem(position)!!, position)
            }
        }
        bindViewHolder(holderHolder, getItem(position)!!, position, payloads)
    }

    override fun onBindViewHolder(holder: ViewHolder<VB>, position: Int) {

    }

    fun setOnItemClickListener(itemClickListener: OnItemClickListener<D>?) {
        this.itemClickListener = itemClickListener
    }

    fun setOnItemLongClickListener(itemLongClickListener: OnItemLongClickListener<D>?) {
        this.itemLongClickListener = itemLongClickListener
    }

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

    abstract fun getViewBinding(layoutInflater: LayoutInflater, parent: ViewGroup): VB

    abstract fun bindViewHolder(
        viewHolder: ViewHolder<VB>,
        item: D,
        position: Int,
        payloads: MutableList<Any>
    )
}