package com.hao.easy.wan.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hao.easy.base.adapter.BasePagedAdapter
import com.hao.easy.base.adapter.ViewHolder
import com.hao.easy.base.extensions.load
import com.hao.easy.wan.R
import com.hao.easy.wan.databinding.WanItemProjectArticleBinding
import com.hao.easy.wan.model.Article
import javax.inject.Inject

class ProjectArticleAdapter @Inject constructor() :
    BasePagedAdapter<WanItemProjectArticleBinding, Article>() {

    override fun getViewBinding(
        layoutInflater: LayoutInflater,
        parent: ViewGroup
    ) = WanItemProjectArticleBinding.inflate(layoutInflater, parent, false)

    override fun bindViewHolder(
        viewHolder: ViewHolder<WanItemProjectArticleBinding>,
        item: Article,
        position: Int,
        payloads: MutableList<Any>
    ) {
        if (payloads == null || payloads.isEmpty() || "fav" != payloads[0]) {
            super.bindViewHolder(viewHolder, item, position, payloads)
        } else {
            viewHolder.viewBinding { ivFav.setImageResource(if (item.collect) R.drawable.ic_fav_1 else R.drawable.ic_fav_0) }
        }
    }

    override fun bindViewHolder(
        viewHolder: ViewHolder<WanItemProjectArticleBinding>,
        item: Article,
        position: Int
    ) {
        viewHolder.viewBinding {
            val click: (View) -> Unit = {
                itemClickListener?.apply {
                    itemClicked(it, item, position)
                }
            }


            tvTitle.text = item.title
            tvDesc.text = item.desc
            tvAuthor.text = item.author
            tvTime.text = item.niceDate
            ivFav.setImageResource(if (item.collect) R.drawable.ic_fav_1 else R.drawable.ic_fav_0)
            ivThumbnail.load(item.envelopePic)

            tvLink.setOnClickListener { click }
            ivFav.setOnClickListener { click }
        }
    }
}


