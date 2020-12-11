package com.hao.easy.wan.ui.adapter

import com.hao.easy.base.adapter.BaseNormalAdapter
import com.hao.easy.base.adapter.ViewHolder
import com.hao.easy.base.view.RoundButton
import com.hao.easy.wan.R
import com.hao.easy.wan.model.Knowledge
import javax.inject.Inject

/**
 * @author Yang Shihao
 * @date 2018/12/3
 */
class KnowledgeLeftAdapter @Inject constructor() :
    BaseNormalAdapter<Knowledge>(R.layout.wan_item_knowledge_left) {

    private var selectedPosition = 0
    private var preSelectedPosition = 0

    override fun bindViewHolder(holder: ViewHolder, item: Knowledge, position: Int) {
        (holder.itemView as RoundButton).apply {
            isSelected = position == selectedPosition
            text = item.name ?: ""
            setOnClickListener {
                preSelectedPosition = selectedPosition
                selectedPosition = position
                notifyItemChanged(preSelectedPosition)
                notifyItemChanged(selectedPosition)
                itemClickListener?.itemClicked(holder, it, item, position)
            }
        }
    }
}
