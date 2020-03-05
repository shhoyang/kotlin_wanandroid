package com.hao.easy.wan.ui.adapter

import com.hao.easy.base.adapter.BasePagedAdapter
import com.hao.easy.base.adapter.ViewHolder
import com.hao.easy.wan.R
import com.hao.easy.wan.model.Article
import javax.inject.Inject

/**
 * @author Yang Shihao
 * @date 2018/12/3
 */
class FavAdapter @Inject constructor() : BasePagedAdapter<Article>(R.layout.wechat_item_fav) {

    override fun bindViewHolder(holder: ViewHolder, item: Article, position: Int) {
        holder.setText(R.id.tvTitle, item.title)
            .setClickListener(R.id.ivFav) {
                itemClickListener?.apply {
                    this(it, item, position)
                }
            }
    }
}