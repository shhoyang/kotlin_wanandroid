package com.hao.easy.wan.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.hao.easy.base.adapter.BaseNormalAdapter
import com.hao.easy.base.adapter.ViewHolder
import com.hao.easy.wan.databinding.WanItemKnowledgeRightBinding
import com.hao.easy.wan.model.Knowledge
import javax.inject.Inject

/**
 * @author Yang Shihao
 * @date 2018/12/3
 */
class KnowledgeRightAdapter @Inject constructor() :
    BaseNormalAdapter<WanItemKnowledgeRightBinding, Knowledge>() {

    override fun getViewBinding(
        layoutInflater: LayoutInflater,
        parent: ViewGroup
    ) = WanItemKnowledgeRightBinding.inflate(layoutInflater, parent, false)

    override fun bindViewHolder(
        viewHolder: ViewHolder<WanItemKnowledgeRightBinding>,
        item: Knowledge,
        position: Int
    ) {
        viewHolder.viewBinding {
            root.text = item.name
            root.setOnClickListener {
                itemClickListener?.itemClicked(it, item, position)
            }
        }
    }
}