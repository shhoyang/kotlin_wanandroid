package com.hao.easy.wan.ui.adapter

import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.hao.easy.base.adapter.BasePagedAdapter
import com.hao.easy.base.adapter.ViewHolder
import com.hao.easy.base.extensions.gone
import com.hao.easy.base.extensions.visible
import com.hao.easy.wan.R
import com.hao.easy.wan.databinding.WanItemCommonArticleBinding
import com.hao.easy.wan.model.Article
import javax.inject.Inject

class CommonArticleAdapter @Inject constructor() :
    BasePagedAdapter<WanItemCommonArticleBinding, Article>(
        ArticleDiff()
    ) {

    var showAuthor = true

    override fun getViewBinding(
        layoutInflater: LayoutInflater,
        parent: ViewGroup
    ) = WanItemCommonArticleBinding.inflate(layoutInflater, parent, false)

    override fun bindViewHolder(
        viewHolder: ViewHolder<WanItemCommonArticleBinding>,
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
        viewHolder: ViewHolder<WanItemCommonArticleBinding>,
        item: Article,
        position: Int
    ) {
        val click: (View) -> Unit = {
            itemClickListener?.apply {
                itemClicked(it, item, position)
            }
        }

        viewHolder.viewBinding {
            tvTitle.text = item.title
            tvTime.text = item.niceDate
            ivFav.setImageResource(if (item.collect) R.drawable.ic_fav_1 else R.drawable.ic_fav_0)
            ivFav.setOnClickListener(click)
            if (showAuthor && !TextUtils.isEmpty(item.author)) {
                tvAuthor.visible()
                tvAuthor.text = item.author

            } else {
                tvAuthor.gone()
            }

            if (TextUtils.isEmpty(item.projectLink)) {
                tvLink.gone()
                tvLink.setOnClickListener(null)

            } else {
                tvLink.visible()
                tvLink.setOnClickListener(click)
            }
        }
    }

    class ArticleDiff : DiffUtil.ItemCallback<Article>() {
        override fun areItemsTheSame(oldItem: Article, newItem: Article) = oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Article, newItem: Article) =
            oldItem.collect == newItem.collect
    }
}