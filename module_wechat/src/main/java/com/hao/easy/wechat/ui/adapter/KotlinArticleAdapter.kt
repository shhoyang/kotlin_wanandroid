package com.hao.easy.wechat.ui.adapter

import com.hao.easy.base.adapter.BasePagedAdapter
import com.hao.easy.base.adapter.ViewHolder
import com.hao.easy.wechat.R
import com.hao.easy.wechat.model.Article
import javax.inject.Inject

class KotlinArticleAdapter @Inject constructor() : BasePagedAdapter<Article>(R.layout.wechat_item_kotlin_article) {

    override fun bindViewHolder(holder: ViewHolder, item: Article, position: Int) {
        var title = item.title.replace(Regex("<[^>]+>"), "")
        holder.setText(R.id.tvTitle, title)
                .setText(R.id.tvAuthor, "作者: ${item.author}")
                .setText(R.id.tvTime, "時間: ${item.niceDate}")
                .setImageResource(R.id.ivFav, if (item.collect) R.drawable.ic_fav_1 else R.drawable.ic_fav_0)
                .setClickListener(R.id.ivFav) {
                    itemClickListener?.apply {
                        this(it, item, position)
                    }
                }
    }

    override fun bindViewHolder(holder: ViewHolder, item: Article, position: Int, payloads: MutableList<Any>) {
        if (payloads == null || payloads.isEmpty()) {
            super.bindViewHolder(holder, item, position, payloads)
        } else if ("fav" != payloads[0]) {
            super.bindViewHolder(holder, item, position, payloads)
        } else {
            holder.setImageResource(R.id.ivFav, if (item.collect) R.drawable.ic_fav_1 else R.drawable.ic_fav_0)
        }
    }
}