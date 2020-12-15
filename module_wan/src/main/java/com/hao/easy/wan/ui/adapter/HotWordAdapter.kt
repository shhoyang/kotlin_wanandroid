package com.hao.easy.wan.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.hao.easy.base.adapter.BaseNormalAdapter
import com.hao.easy.base.adapter.ViewHolder
import com.hao.easy.wan.databinding.WanItemHotWordBinding
import com.hao.easy.wan.model.HotWord
import javax.inject.Inject

/**
 * @author Yang Shihao
 * @Date 2020/7/20
 */
class HotWordAdapter @Inject constructor() :
    BaseNormalAdapter<WanItemHotWordBinding, HotWord>() {

    override fun getViewBinding(
        layoutInflater: LayoutInflater,
        parent: ViewGroup
    ) = WanItemHotWordBinding.inflate(layoutInflater, parent, false)

    override fun bindViewHolder(
        viewHolder: ViewHolder<WanItemHotWordBinding>,
        item: HotWord,
        position: Int
    ) {
        viewHolder.viewBinding { root.text = item.name }
    }
}