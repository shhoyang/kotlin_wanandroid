package com.hao.easy.user.ui.adapter

import android.text.SpannableString
import android.text.style.UnderlineSpan
import android.widget.TextView
import com.hao.easy.base.adapter.BaseNormalAdapter
import com.hao.easy.base.adapter.ViewHolder
import com.hao.easy.user.R
import com.hao.easy.user.model.Menu
import javax.inject.Inject

class AboutAdapter @Inject constructor() : BaseNormalAdapter<Menu>(R.layout.user_item_about) {

    override fun bindViewHolder(holder: ViewHolder, item: Menu, position: Int) {
        holder.setText(R.id.tvTitle, item.title)
            .setText(R.id.tvDesc, item.desc)

        val textView = holder.getView<TextView>(R.id.tvDesc)
        val desc = SpannableString(item.desc)
        desc.setSpan(UnderlineSpan(), 0, item.desc.length, 0)
        textView.text = desc

        itemLongClickListener?.let {
            holder.itemView.setOnLongClickListener { view ->
                it(view, item, position)
            }
        }
    }
}
