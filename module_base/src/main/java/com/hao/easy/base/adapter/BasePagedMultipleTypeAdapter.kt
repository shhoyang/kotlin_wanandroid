package com.hao.easy.base.adapter

import android.view.ViewGroup
import androidx.collection.SparseArrayCompat

/**
 * @author Yang Shihao
 */
abstract class BasePagedMultipleTypeAdapter<T : BaseItem>(data: ArrayList<T> = ArrayList()) :
    BasePagedAdapter<T>(0) {

    private val delegates: SparseArrayCompat<ItemViewDelegate<T>> = SparseArrayCompat()

    fun addDelegate(delegate: ItemViewDelegate<T>): BasePagedMultipleTypeAdapter<T> {
        val viewType = delegates.size()
        delegates.put(viewType, delegate)
        return this
    }

    override fun bindViewHolder(holder: ViewHolder, item: T, position: Int) {

    }

    override fun getItemViewType(position: Int): Int {
        val size = delegates.size()
        for (index in 0 until size) {
            val itemViewDelegate = delegates[index]
            if (itemViewDelegate?.isViewType(getItem(position)!!, position) == true) {
                return delegates.keyAt(index)
            }
        }
        throw IllegalArgumentException("No ItemViewDelegate added that matches position= $position in data source")
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(parent.context, parent, delegates.get(viewType)!!.getLayoutId())
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.setOnClickListener {
            itemClickListener?.itemClicked(holder, it, getItem(position)!!, position)
        }
        delegates.get(holder.itemViewType)?.bindViewHolder(holder, getItem(position)!!, position)
    }
}
