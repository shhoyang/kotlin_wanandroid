package com.hao.easy.wan.adapter

import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.hao.easy.wan.R
import com.hao.easy.wan.databinding.WanItemArticleBottomBinding
import com.hao.easy.wan.databinding.WanItemArticleNoImageBinding
import com.hao.easy.wan.databinding.WanItemArticleWithImageBinding
import com.hao.easy.wan.model.Article
import com.hao.library.adapter.BaseMultiTypePagedAdapter
import com.hao.library.adapter.ItemViewDelegate
import com.hao.library.adapter.ViewHolder
import com.hao.library.extensions.gone
import com.hao.library.extensions.load
import com.hao.library.extensions.visible

class ArticleAdapter : BaseMultiTypePagedAdapter<Article>(ArticleDiff()) {

    init {
        addDelegate(ItemWithImage())
        addDelegate(ItemNoImage())
    }

    inner class ItemNoImage : ItemViewDelegate<WanItemArticleNoImageBinding, Article> {

        override fun isViewType(item: Article, position: Int) = TextUtils.isEmpty(item.envelopePic)

        override fun getViewBinding(
            layoutInflater: LayoutInflater,
            parent: ViewGroup
        ) = WanItemArticleNoImageBinding.inflate(layoutInflater, parent, false)

        override fun bindViewHolder(
            viewHolder: ViewHolder<WanItemArticleNoImageBinding>,
            item: Article,
            position: Int,
            payloads: MutableList<Any>
        ) {
            if (payloads.isEmpty() || "fav" != payloads[0]) {
                viewHolder.viewBinding {
                    tvTitle.text = item.title
                    if (TextUtils.isEmpty(item.desc)) {
                        tvDesc.gone()
                    } else {
                        tvDesc.visible()
                        tvDesc.text = item.desc
                    }

                    bindBottom(bottom, item, position)
                }
            } else {
                viewHolder.viewBinding { bindFav(bottom, item) }
            }
        }
    }

    inner class ItemWithImage : ItemViewDelegate<WanItemArticleWithImageBinding, Article> {

        override fun isViewType(item: Article, position: Int) = !TextUtils.isEmpty(item.envelopePic)

        override fun getViewBinding(
            layoutInflater: LayoutInflater,
            parent: ViewGroup
        ) = WanItemArticleWithImageBinding.inflate(layoutInflater, parent, false)

        override fun bindViewHolder(
            viewHolder: ViewHolder<WanItemArticleWithImageBinding>,
            item: Article,
            position: Int,
            payloads: MutableList<Any>
        ) {
            if (payloads.isEmpty() || "fav" != payloads[0]) {
                viewHolder.viewBinding {
                    ivThumbnail.load(item.envelopePic)
                    tvTitle.text = item.title
                    tvDesc.text = item.desc
                    bindBottom(bottom, item, position)
                }
            } else {
                viewHolder.viewBinding {
                    bindFav(bottom, item)
                }
            }
        }
    }

    private fun bindBottom(bottom: WanItemArticleBottomBinding, item: Article, position: Int) {
        val click: (View) -> Unit = {
            itemClickListener?.apply {
                itemClicked(it, item, position)
            }
        }
        bottom.apply {
            if (TextUtils.isEmpty(item.author)) {
                tvAuthor.gone()
            } else {
                tvAuthor.visible()
                tvAuthor.text = item.author
            }

            tvTime.text = item.niceDate

            if (TextUtils.isEmpty(item.projectLink)) {
                tvLink.gone()
                tvLink.setOnClickListener(null)
            } else {
                tvLink.visible()
                tvLink.setOnClickListener(click)
            }
            bindFav(bottom, item)
            ivFavorite.setOnClickListener(click)
        }
    }

    private fun bindFav(bottom: WanItemArticleBottomBinding, item: Article) {
        bottom.ivFavorite.setImageResource(if (item.collect) R.drawable.wan_ic_favorite_1 else R.drawable.wan_ic_favorite_0)
    }

    class ArticleDiff : DiffUtil.ItemCallback<Article>() {
        override fun areItemsTheSame(oldItem: Article, newItem: Article) = oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Article, newItem: Article) =
            oldItem.collect == newItem.collect
    }
}