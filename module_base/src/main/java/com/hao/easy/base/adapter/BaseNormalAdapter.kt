package com.hao.easy.base.adapter

import android.content.Context
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

abstract class BaseNormalAdapter<T>(
    private val layoutId: Int,
    var data: ArrayList<T> = ArrayList()
) : RecyclerView.Adapter<ViewHolder>() {

    lateinit var context: Context

    var itemClickListener: OnItemClickListener<T>? = null

    override fun getItemCount() = data.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        return ViewHolder(context, parent, layoutId)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        itemClickListener?.apply {
            holder.itemView.setOnClickListener {
                itemClicked(it, data[position], position)
            }
        }
        bindViewHolder(holder, data[position], position)
    }

    fun resetData(data: ArrayList<T>?) {
        this.data.clear()
        if (data != null && data.isNotEmpty()) {
            this.data.addAll(data)
        }
        notifyDataSetChanged()
    }

    fun addData(data: ArrayList<T>?) {
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