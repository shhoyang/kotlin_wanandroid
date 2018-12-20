package com.hao.easy.wechat.ui.adapter

import android.os.Build
import android.view.View
import android.widget.ImageView
import com.hao.easy.base.adapter.BasePagedAdapter
import com.hao.easy.base.adapter.ViewHolder
import com.hao.easy.wechat.R
import com.hao.easy.wechat.model.Article
import org.jetbrains.anko.find
import javax.inject.Inject

class ProjectArticleAdapter @Inject constructor() : BasePagedAdapter<Article>(R.layout.wechat_item_project_article) {

    override fun bindViewHolder(holder: ViewHolder, item: Article, position: Int) {

        var click: (View) -> Unit = {
            itemClickListener?.apply {
                this(it, item, position)
            }
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            holder.itemView.find<ImageView>(R.id.ivThumbnail).transitionName = item.envelopePic
        }
        holder.setText(R.id.tvTitle, item.title)
                .setText(R.id.tvDesc, item.desc)
                .setText(R.id.tvAuthor, "作者: ${item.author}")
                .setText(R.id.tvTime, "時間: ${item.niceDate}")
                .setImageResource(R.id.ivFav, if (item.collect) R.drawable.ic_fav_1 else R.drawable.ic_fav_0)
                .setImage(R.id.ivThumbnail, item.envelopePic)
                .setClickListener(arrayOf(R.id.ivLink, R.id.tvLink, R.id.ivFav), click)
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


