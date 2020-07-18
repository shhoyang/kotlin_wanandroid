package com.hao.easy.wan.ui.fragment

import android.os.Bundle
import android.view.View
import com.hao.easy.base.common.RefreshResult
import com.hao.easy.base.ui.BaseListFragment
import com.hao.easy.base.ui.WebActivity
import com.hao.easy.wan.R
import com.hao.easy.wan.extensions.removeHtml
import com.hao.easy.wan.model.Article
import com.hao.easy.wan.ui.adapter.CommonArticleAdapter
import com.hao.easy.wan.viewmodel.WechatArticleViewModel

/**
 * @author Yang Shihao
 * @date 2018/11/18
 */
class WechatArticleFragment : BaseListFragment<Article, WechatArticleViewModel>() {

    companion object {
        private const val AUTHOR_ID = "AUTHOR_ID"

        fun instance(authorId: Int): WechatArticleFragment {
            val fragment = WechatArticleFragment()
            val bundle = Bundle()
            bundle.putInt(AUTHOR_ID, authorId)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun getLayoutId() = R.layout.activity_base_list

    override fun initData() {
        arguments?.apply {
            viewModel.authorId = getInt(AUTHOR_ID, 409)
        }
        super.initData()
        lifecycle.addObserver(viewModel)
    }

    override fun adapter(): CommonArticleAdapter {
        val adapter = CommonArticleAdapter()
        adapter.showAuthor = false
        return adapter
    }

    override fun itemClicked(view: View, item: Article, position: Int) {
        when (view.id) {
            R.id.tvLink -> {
                context?.apply {
                    val title = item.title.removeHtml()
                    WebActivity.start(this, title, item.projectLink)
                }
            }
            R.id.ivFav -> viewModel.collect(item, position)
            else -> {
                context?.apply {
                    val title = item.title.removeHtml()
                    WebActivity.start(this, title, item.link)
                }
            }
        }
    }

    override fun refreshFinished(result: RefreshResult) {
        super.refreshFinished(result)
        val weChatFragment = parentFragment as WechatFragment
        weChatFragment.refreshFinished()
    }

    fun refresh() {
        viewModel.invalidate()
    }
}