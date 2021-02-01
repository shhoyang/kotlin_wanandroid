package com.hao.library.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.collection.SparseArrayCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.viewbinding.ViewBinding
import com.hao.library.databinding.EmptyBinding

open class BaseMultiTypePagedAdapter<D : PagedAdapterItem>(
    diff: DiffUtil.ItemCallback<D> = Diff()
) :
    BasePagedAdapter<ViewBinding, D>(diff) {

    private val delegates: SparseArrayCompat<ItemViewDelegate<out ViewBinding, D>> =
        SparseArrayCompat()

    fun addDelegate(delegate: ItemViewDelegate<out ViewBinding, D>) {
        val viewType = delegates.size()
        delegates.put(viewType, delegate)
    }

    override fun getItemViewType(position: Int): Int {
        val size = delegates.size()
        for (index in 0 until size) {
            val itemViewDelegate = delegates[index]
            if (itemViewDelegate!!.isViewType(getItem(position)!!, position)) {
                return delegates.keyAt(index)
            }
        }
        throw IllegalArgumentException("No ItemViewDelegate added that matches position= $position in data source")
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder<ViewBinding> {
        return ViewHolder(
            delegates.get(viewType)!!.getViewBinding(LayoutInflater.from(parent.context), parent)
        )
    }

    override fun bindViewHolder(
        viewHolder: ViewHolder<ViewBinding>,
        item: D,
        position: Int,
        payloads: MutableList<Any>
    ) {
        delegates.get(viewHolder.itemViewType)?.bind(
            viewHolder,
            getItem(position)!!,
            position,
            payloads
        )
    }

    override fun getViewBinding(layoutInflater: LayoutInflater, parent: ViewGroup): ViewBinding {
        return EmptyBinding.inflate(layoutInflater)
    }
}