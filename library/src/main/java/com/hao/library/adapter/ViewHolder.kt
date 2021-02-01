package com.hao.library.adapter

import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

class ViewHolder<VB : ViewBinding>(private val viewBinding: VB) :
    RecyclerView.ViewHolder(viewBinding.root) {

    fun viewBinding(block: VB.() -> Unit) {
        block(viewBinding)
    }
}
