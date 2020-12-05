package com.hao.easy.base.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

abstract class BaseNormalAdapter<T>(
    private val layoutId: Int = 0,
    var data: ArrayList<T> = ArrayList()
) : RecyclerView.Adapter<ViewHolder>() {

    protected var itemClickListener: OnItemClickListener<T>? = null
    protected var itemLongClickListener: OnItemLongClickListener<T>? = null

    override fun getItemCount() = data.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(parent.context, parent, layoutId)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        itemClickListener?.apply {
            holder.itemView.setOnClickListener {
                itemClicked(it, data[position], position)
            }
        }
        bindViewHolder(holder, data[position], position)
    }

    protected fun getItem(position: Int) = data[position]

    fun setOnItemClickListener(itemClickListener: OnItemClickListener<T>?) {
        this.itemClickListener = itemClickListener
    }

    fun setOnItemLongClickListener(itemLongClickListener: OnItemLongClickListener<T>?) {
        this.itemLongClickListener = itemLongClickListener
    }

    fun resetData(data: List<T>?) {
        this.data.clear()
        if (data != null && data.isNotEmpty()) {
            this.data.addAll(data)
        }
        notifyDataSetChanged()
    }

    fun addData(data: List<T>?) {
        if (data == null || data.isEmpty()) {
            return
        }

        val size = this.data.size
        this.data.addAll(data)
        notifyItemRangeInserted(size, data.size)
    }

    fun addItem(data: T) {
        val size = this.data.size
        this.data.add(data)
        notifyItemInserted(size)
    }

    fun clear() {
        this.data.clear()
    }

    abstract fun bindViewHolder(holder: ViewHolder, item: T, position: Int)
}