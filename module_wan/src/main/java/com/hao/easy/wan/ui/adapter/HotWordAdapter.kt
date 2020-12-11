package com.hao.easy.wan.ui.adapter

import android.widget.TextView
import com.hao.easy.base.adapter.BaseNormalAdapter
import com.hao.easy.base.adapter.ViewHolder
import com.hao.easy.wan.R
import com.hao.easy.wan.model.HotWord
import javax.inject.Inject

/**
 * @author Yang Shihao
 * @Date 2020/7/20
 */
class HotWordAdapter @Inject constructor() : BaseNormalAdapter<HotWord>(R.layout.wan_item_hot_word) {

    override fun bindViewHolder(holder: ViewHolder, item: HotWord, position: Int) {
        (holder.itemView as TextView).text = item.name
    }
}