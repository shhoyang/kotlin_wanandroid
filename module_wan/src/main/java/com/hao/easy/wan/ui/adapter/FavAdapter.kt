package com.hao.easy.wan.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.hao.easy.base.adapter.BasePagedAdapter
import com.hao.easy.base.adapter.ViewHolder
import com.hao.easy.wan.databinding.WanItemFavBinding
import com.hao.easy.wan.model.Article
import javax.inject.Inject

/**
 * @author Yang Shihao
 * @date 2018/12/3
 */
class FavAdapter @Inject constructor() : BasePagedAdapter<WanItemFavBinding, Article>() {

    override fun getViewBinding(
        layoutInflater: LayoutInflater,
        parent: ViewGroup
    ) = WanItemFavBinding.inflate(layoutInflater, parent, false)

    override fun bindViewHolder(
        viewHolder: ViewHolder<WanItemFavBinding>,
        item: Article,
        position: Int
    ) {
        viewHolder.viewBinding {
            tvTitle.text = item.title
            ivFav.setOnClickListener {
                itemClickListener?.itemClicked(it, item, position)
            }
        }
    }
}