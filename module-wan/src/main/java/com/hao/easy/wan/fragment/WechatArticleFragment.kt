package com.hao.easy.wan.fragment

import android.os.Bundle
import android.view.View
import com.hao.easy.base.constant.ExtraKey
import com.hao.easy.base.ui.WebActivity
import com.hao.easy.wan.R
import com.hao.easy.wan.adapter.ArticleAdapter
import com.hao.easy.wan.databinding.WanFragmentWechatArticleBinding
import com.hao.easy.wan.model.Article
import com.hao.easy.wan.model.Author
import com.hao.easy.wan.viewmodel.WechatArticleViewModel
import com.hao.library.annotation.AndroidEntryPoint
import com.hao.library.constant.DataListResult
import com.hao.library.ui.BaseListFragment

/**
 * @author Yang Shihao
 */
@AndroidEntryPoint
class WechatArticleFragment :
    BaseListFragment<WanFragmentWechatArticleBinding, Article, WechatArticleViewModel, ArticleAdapter>() {

    override fun initData() {
        viewModel {
            lifecycle.addObserver(this)
            arguments?.apply {
                authorId = getInt(ExtraKey.INT, Author.ID_HONGYANG)
            }
        }
        super.initData()
    }

    override fun itemClicked(view: View, item: Article, position: Int) {
        when (view.id) {
            R.id.tvLink -> act {
                WebActivity.start(it, item.title, item.projectLink)
            }
            R.id.ivFavorite -> viewModel { collect(item, position) }
            else -> act {
                WebActivity.start(it, item.title, item.link)
            }
        }
    }

    override fun refreshFinished(result: DataListResult) {
        super.refreshFinished(result)
        val weChatFragment = parentFragment as WechatFragment
        weChatFragment.refreshFinished()
    }

    fun refresh() {
        vm?.refresh()
    }

    companion object {
        fun instance(authorId: Int): WechatArticleFragment {
            val fragment = WechatArticleFragment()
            val bundle = Bundle()
            bundle.putInt(ExtraKey.INT, authorId)
            fragment.arguments = bundle
            return fragment
        }
    }
}