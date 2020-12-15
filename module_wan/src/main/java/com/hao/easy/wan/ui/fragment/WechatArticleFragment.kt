package com.hao.easy.wan.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.hao.easy.base.common.DataListResult
import com.hao.easy.base.common.ExtraKey
import com.hao.easy.base.ui.BaseListFragment
import com.hao.easy.base.ui.WebActivity
import com.hao.easy.wan.R
import com.hao.easy.wan.databinding.WanFragmentWechatArticleBinding
import com.hao.easy.wan.model.Article
import com.hao.easy.wan.model.Author
import com.hao.easy.wan.ui.adapter.CommonArticleAdapter
import com.hao.easy.wan.viewmodel.WechatArticleViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

/**
 * @author Yang Shihao
 * @date 2018/11/18
 */
@AndroidEntryPoint
class WechatArticleFragment :
    BaseListFragment<WanFragmentWechatArticleBinding, Article, WechatArticleViewModel, CommonArticleAdapter>() {

    @Inject
    lateinit var adapter: CommonArticleAdapter

    override fun getVB() = WanFragmentWechatArticleBinding.inflate(layoutInflater)

    override fun getVM() = ViewModelProvider(this).get(WechatArticleViewModel::class.java)

    override fun adapter() = adapter

    override fun initView() {
        adapter.showAuthor = false
        super.initView()
    }

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
            R.id.ivFav -> viewModel { collect(item, position) }
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
        viewModel { refresh() }
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