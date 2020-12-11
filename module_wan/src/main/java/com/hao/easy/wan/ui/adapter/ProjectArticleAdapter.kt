package com.hao.easy.wan.ui.adapter

import android.view.View
import com.hao.easy.base.adapter.BasePagedAdapter
import com.hao.easy.base.adapter.ViewHolder
import com.hao.easy.wan.R
import com.hao.easy.wan.model.Article
import javax.inject.Inject

class ProjectArticleAdapter @Inject constructor() :
    BasePagedAdapter<Article>(R.layout.wan_item_project_article) {

    override fun bindViewHolder(holder: ViewHolder, item: Article, position: Int) {

        val click: (View) -> Unit = {
            itemClickListener?.apply {
                itemClicked(holder, it, item, position)
            }
        }
        holder.setText(R.id.tvTitle, item.title)
            .setText(R.id.tvDesc, item.desc)
            .setText(R.id.tvAuthor, item.author)
            .setText(R.id.tvTime, item.niceDate)
            .setImageResource(
                R.id.ivFav,
                if (item.collect) R.drawable.ic_fav_1 else R.drawable.ic_fav_0
            )
            .setImage(R.id.ivThumbnail, item.envelopePic)
            .setClickListener(arrayOf(R.id.tvLink, R.id.ivFav), click)
    }

    override fun bindViewHolder(
        holder: ViewHolder,
        item: Article,
        position: Int,
        payloads: MutableList<Any>
    ) {
        if (payloads == null || payloads.isEmpty() || "fav" != payloads[0]) {
            super.bindViewHolder(holder, item, position, payloads)
        } else {
            holder.setImageResource(
                R.id.ivFav,
                if (item.collect) R.drawable.ic_fav_1 else R.drawable.ic_fav_0
            )
        }
    }
}


