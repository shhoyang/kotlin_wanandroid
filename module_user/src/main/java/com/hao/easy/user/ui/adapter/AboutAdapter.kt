package com.hao.easy.user.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.hao.easy.base.adapter.BaseNormalAdapter
import com.hao.easy.base.adapter.ViewHolder
import com.hao.easy.base.extensions.underline
import com.hao.easy.user.databinding.UserItemAboutBinding
import com.hao.easy.user.model.Menu
import javax.inject.Inject

class AboutAdapter @Inject constructor() : BaseNormalAdapter<UserItemAboutBinding, Menu>() {

    override fun getViewBinding(
        layoutInflater: LayoutInflater,
        parent: ViewGroup
    ) = UserItemAboutBinding.inflate(layoutInflater, parent, false)

    override fun bindViewHolder(
        viewHolder: ViewHolder<UserItemAboutBinding>,
        item: Menu,
        position: Int
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
