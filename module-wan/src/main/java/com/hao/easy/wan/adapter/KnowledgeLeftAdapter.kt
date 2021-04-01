package com.hao.easy.wan.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.hao.easy.wan.databinding.WanItemKnowledgeLeftBinding
import com.hao.easy.wan.model.Knowledge
import com.hao.library.adapter.BaseNormalAdapter
import com.hao.library.adapter.ViewHolder

/**
 * @author Yang Shihao
 */
class KnowledgeLeftAdapter : BaseNormalAdapter<WanItemKnowledgeLeftBinding, Knowledge>() {

    private var selectedPosition = 0
    private var preSelectedPosition = 0

    override fun getViewBinding(
        layoutInflater: LayoutInflater,
        parent: ViewGroup
    ) = WanItemKnowledgeLeftBinding.inflate(layoutInflater, parent, false)

    override fun bindViewHolder(
        viewHolder: ViewHolder<WanItemKnowledgeLeftBinding>,
        item: Knowledge,
        position: Int,
        payloads: MutableList<Any>
    ) {
        viewHolder.viewBinding {
            root.isSelected = position == selectedPosition
            root.text = item.name ?: ""
            root.setOnClickListener {
                if (position != selectedPosition) {
                    preSelectedPosition = selectedPosition
                    selectedPosition = position
                    notifyItemChanged(preSelectedPosition)
                    notifyItemChanged(selectedPosition)
                    itemClickListener?.itemClicked(it, item, position)

                }
            }
        }
    }
}
