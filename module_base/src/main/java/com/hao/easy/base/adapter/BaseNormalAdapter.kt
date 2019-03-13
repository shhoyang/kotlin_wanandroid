package com.hao.easy.base.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup

abstract class BaseNormalAdapter<T>(private val layoutId: Int, var list: ArrayList<T> = ArrayList()) : RecyclerView.Adapter<ViewHolder>() {

    lateinit var context: Context

    var itemClickListener: ((View, T, Int) -> Unit)? = null
    var itemLongClickListener: ((View, T, Int) -> Boolean)? = null

    override fun getItemCount() = list.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        return ViewHolder(context, parent, layoutId)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        itemClickListener?.let { holder.itemView.setOnClickListener { it(holder.itemView, list[position], position) } }
        bindViewHolder(holder, list[position], position)
    }

    fun setData(data: ArrayList<T>) {
        if (data.isEmpty()) {
            return
        }

        list = data
        notifyDataSetChanged()
    }

    fun addData(data: ArrayList<T>) {
        if (data.isEmpty()) {
            return
        }

        val size = list.size
        list.addAll(data)
        notifyItemRangeInserted(size, data.size)
    }

    fun addItem(data: T) {
        val size = list.size
        list.add(data)
        notifyItemInserted(size)
    }

    fun clear() {
        list.clear()
    }

    abstract fun bindViewHolder(holder: ViewHolder, item: T, position: Int)
}