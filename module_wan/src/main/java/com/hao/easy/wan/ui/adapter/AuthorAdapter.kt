package com.hao.easy.wan.ui.adapter

import android.graphics.Color
import android.widget.ImageView
import android.widget.TextView
import com.hao.easy.base.adapter.BaseNormalAdapter
import com.hao.easy.base.adapter.ViewHolder
import com.hao.easy.base.extensions.gone
import com.hao.easy.base.extensions.visible
import com.hao.easy.wan.R
import com.hao.easy.wan.model.Author
import javax.inject.Inject

/**
 * @author Yang Shihao
 * @Date 12/8/20
 */
class AuthorAdapter @Inject constructor() : BaseNormalAdapter<Author>(R.layout.wan_item_author) {

    var isEdit = false

    override fun bindViewHolder(holder: ViewHolder, item: Author, position: Int) {

        val important = item.isImportant()
        val visible = item.isVisible()

        val tvName: TextView = holder.getView(R.id.tvName)
        val ivAction: ImageView = holder.getView(R.id.ivAction)
        tvName.isEnabled = !important
        tvName.text = item.name

        if (!isEdit || important) {
            ivAction.gone()
            holder.itemView.setOnClickListener(null)
        } else {
            ivAction.visible()
            ivAction.setImageResource(if (visible) R.drawable.wan_ic_delete_black else R.drawable.wan_ic_add_black)
            holder.itemView.setOnClickListener {
                itemClickListener?.itemClicked(holder, it, item, position)
            }
        }

        if (visible) {
            holder.itemView.setOnLongClickListener {
                itemLongClickListener?.itemLongClicked(holder, it, item, position)
                true
            }
        } else {
            holder.itemView.setOnLongClickListener(null)
        }
    }
}