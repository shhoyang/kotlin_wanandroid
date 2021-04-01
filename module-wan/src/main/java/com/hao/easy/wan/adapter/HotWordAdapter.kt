package com.hao.easy.wan.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.hao.easy.wan.databinding.WanItemHotWordBinding
import com.hao.easy.wan.model.HotWord
import com.hao.library.adapter.BaseNormalAdapter
import com.hao.library.adapter.ViewHolder

/**
 * @author Yang Shihao
 */
class HotWordAdapter : BaseNormalAdapter<WanItemHotWordBinding, HotWord>() {

    override fun getViewBinding(
        layoutInflater: LayoutInflater,
        parent: ViewGroup
    ) = WanItemHotWordBinding.inflate(layoutInflater, parent, false)

    override fun bindViewHolder(
        viewHolder: ViewHolder<WanItemHotWordBinding>,
        item: HotWord,
        position: Int,
        payloads: MutableList<Any>
    ) {
        viewHolder.viewBinding { root.text = item.name }
    }
}