package com.hao.easy.wan.ui.adapter

import com.hao.easy.base.adapter.BasePagedAdapter
import com.hao.easy.base.adapter.ViewHolder
import com.hao.easy.wan.R
import com.hao.easy.wan.model.Article
import com.mcxtzhang.swipemenulib.SwipeMenuLayout
import javax.inject.Inject

/**
 * @author Yang Shihao
 * @date 2018/12/3
 */
class FavAdapter @Inject constructor() : BasePagedAdapter<Article>(R.layout.wechat_item_fav) {

    override fun bindViewHolder(holder: ViewHolder, item: Article, position: Int) {
        (holder.itemView as SwipeMenuLayout).isIos = false
        holder.setText(R.id.tvTitle, item.title)
                .setClickListener(R.id.buttonDelete) {
                    itemClickListener?.apply {
                        (holder.itemView as SwipeMenuLayout).quickClose()
                        this(it, item, position)
                    }
                }
    }
}