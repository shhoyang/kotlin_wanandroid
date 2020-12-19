package com.hao.easy.wan.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.hao.easy.base.adapter.BaseNormalAdapter
import com.hao.easy.base.adapter.ViewHolder
import com.hao.easy.wan.databinding.WanItemKnowledgeLeftBinding
import com.hao.easy.wan.model.Knowledge
import javax.inject.Inject

/**
 * @author Yang Shihao
 * @date 2018/12/3
 */
class KnowledgeLeftAdapter @Inject constructor() :
    BaseNormalAdapter<WanItemKnowledgeLeftBinding, Knowledge>() {

    private var selectedPosition = 0
    private var preSelectedPosition = 0

    override fun getViewBinding(
        layoutInflater: LayoutInflater,
        parent: ViewGroup
    ) = WanItemKnowledgeLeftBinding.inflate(layoutInflater, parent, false)

    override fun bindViewHolder(
        viewHolder: ViewHolder<WanItemKnowledgeLeftBinding>,
        item: Knowledge,
        position: Int
    ) {
        viewHolder.viewBinding {
            root.isSelected = position == selectedPosition
            root.text = item.name ?: ""
            root.setOnClickListener {
                if(position != selectedPosition){
                    preSelectedPosition = selectedPosition
                    selectedPosition = position
                    notifyItemChanged(preSelectedPosition)
                    notifyItemChanged(selectedPosition)
                    itemClickListener?.itemClicked(it, item, position)

                }            }
        }
    }
}
