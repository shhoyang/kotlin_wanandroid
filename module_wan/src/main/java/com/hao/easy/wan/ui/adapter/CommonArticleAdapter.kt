package com.hao.easy.wan.ui.adapter

import android.text.TextUtils
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import com.hao.easy.base.adapter.BasePagedAdapter
import com.hao.easy.base.adapter.ViewHolder
import com.hao.easy.wan.R
import com.hao.easy.wan.model.Article
import javax.inject.Inject

class CommonArticleAdapter @Inject constructor() :
    BasePagedAdapter<Article>(R.layout.wan_item_common_article, ArticleDiff()) {

    var showAuthor = true

    override fun bindViewHolder(holder: ViewHolder, item: Article, position: Int) {

        val click: (View) -> Unit = {
            itemClickListener?.apply {
                itemClicked(it, item, position)
            }
        }

        holder.setText(R.id.tvTitle, item.title)
            .setText(R.id.tvTime, item.niceDate)
            .setImageResource(
                R.id.ivFav,
                if (item.collect) R.drawable.ic_fav_1 else R.drawable.ic_fav_0
            )
            .setClickListener(R.id.ivFav, click)

        val tvAuthor = holder.getView<TextView>(R.id.tvAuthor)
        if (showAuthor && !TextUtils.isEmpty(item.author)) {
            holder.visible(tvAuthor)
            tvAuthor.text = item.author

        } else {
            holder.gone(tvAuthor)
        }

        val tvLink = holder.getView<TextView>(R.id.tvLink)
        if (TextUtils.isEmpty(item.projectLink)) {
            holder.gone(tvLink)
            tvLink.setOnClickListener(null)

        } else {
            holder.visible(tvLink)
                .setClickListener(R.id.tvLink, click)
        }
    }

    override fun bindViewHolder(
        holder: ViewHolder,
        item: Article,
        position: Int,
        payloads: MutableList<Any>
    ) {
        if (payloads == null || payloads.isEmpty()) {
            super.bindViewHolder(holder, item, position, payloads)
        } else if ("fav" != payloads[0]) {
            super.bindViewHolder(holder, item, position, payloads)
        } else {
            holder.setImageResource(
                R.id.ivFav,
                if (item.collect) R.drawable.ic_fav_1 else R.drawable.ic_fav_0
            )
        }
    }

    class ArticleDiff : DiffUtil.ItemCallback<Article>() {
        override fun areItemsTheSame(oldItem: Article, newItem: Article) = oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Article, newItem: Article) =
            oldItem.collect == newItem.collect
    }
}