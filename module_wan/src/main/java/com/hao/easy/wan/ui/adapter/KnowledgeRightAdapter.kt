package com.hao.easy.wan.ui.adapter

import android.widget.TextView
import com.hao.easy.base.adapter.BaseNormalAdapter
import com.hao.easy.base.adapter.BasePagedAdapter
import com.hao.easy.base.adapter.ViewHolder
import com.hao.easy.wan.R
import com.hao.easy.wan.model.Knowledge
import javax.inject.Inject

/**
 * @author Yang Shihao
 * @date 2018/12/3
 */
class KnowledgeRightAdapter @Inject constructor() :
    BaseNormalAdapter<Knowledge>(R.layout.wan_item_knowledge_right) {

    override fun bindViewHolder(holder: ViewHolder, item: Knowledge, position: Int) {
        (holder.itemView as TextView).text = item.name
        holder.itemView.setOnClickListener {
            itemClickListener?.itemClicked(holder, it, item, position)
        }
    }
}