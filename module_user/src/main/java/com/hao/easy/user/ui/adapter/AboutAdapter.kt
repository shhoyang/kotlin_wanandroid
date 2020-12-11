package com.hao.easy.user.ui.adapter

import android.widget.TextView
import com.hao.easy.base.adapter.BaseNormalAdapter
import com.hao.easy.base.adapter.ViewHolder
import com.hao.easy.base.extensions.underline
import com.hao.easy.user.R
import com.hao.easy.user.model.Menu
import javax.inject.Inject

class AboutAdapter @Inject constructor() : BaseNormalAdapter<Menu>(R.layout.user_item_about) {

    override fun bindViewHolder(holder: ViewHolder, item: Menu, position: Int) {
        holder.setText(R.id.tvTitle, item.title)
            .setText(R.id.tvDesc, item.desc)

        val textView = holder.getView<TextView>(R.id.tvDesc)
        textView.underline()
        textView.text = item.desc

        itemLongClickListener?.apply {
            holder.itemView.setOnLongClickListener {
                itemLongClicked(holder, it, item, position)
                true
            }
        }
    }
}
