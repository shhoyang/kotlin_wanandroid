package com.hao.easy.user.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.hao.easy.user.databinding.UserItemAboutBinding
import com.hao.easy.user.model.Menu
import com.hao.library.adapter.BaseNormalAdapter
import com.hao.library.adapter.ViewHolder
import com.hao.library.extensions.underline

class AboutAdapter : BaseNormalAdapter<UserItemAboutBinding, Menu>() {

    override fun getViewBinding(
        layoutInflater: LayoutInflater,
        parent: ViewGroup
    ) = UserItemAboutBinding.inflate(layoutInflater, parent, false)

    override fun bindViewHolder(
        viewHolder: ViewHolder<UserItemAboutBinding>,
        item: Menu,
        position: Int,
        payloads: MutableList<Any>
    ) {
        viewHolder.viewBinding {
            tvTitle.text = item.title
            tvDesc.text = item.desc
            tvDesc.underline()
            itemLongClickListener?.apply {
                root.setOnLongClickListener {
                    itemLongClicked(it, item, position)
                    true
                }
            }
        }
    }
}
