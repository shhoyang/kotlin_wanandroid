package com.hao.easy.wan.ui.activity

import android.content.Context
import android.content.Intent
import android.view.View
import com.hao.easy.base.ui.BaseListActivity
import com.hao.easy.base.ui.WebActivity
import com.hao.easy.wan.R
import com.hao.easy.wan.model.Article
import com.hao.easy.wan.model.Knowledge
import com.hao.easy.wan.ui.adapter.CommonArticleAdapter
import com.hao.easy.wan.viewmodel.KnowledgeArticleViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class KnowledgeArticleActivity : BaseListActivity<Article, KnowledgeArticleViewModel>() {

    @Inject
    lateinit var adapter: CommonArticleAdapter

    override fun adapter() = adapter

    override fun initData() {
        intent.getParcelableExtra<Knowledge>(TYPE)?.apply {
            title = name
            viewModel.typeId = id
        }
        super.initData()
        lifecycle.addObserver(viewModel)
    }

    override fun itemClicked(view: View, item: Article, position: Int) {
        when (view.id) {
            R.id.tvLink -> WebActivity.start(this, item.title, item.projectLink)
            R.id.ivFav -> viewModel.collect(item, position)
            else -> WebActivity.start(this, item.title, item.link)
        }
    }

    companion object {
        private const val TYPE = "TYPE"
        fun start(context: Context, knowledge: Knowledge) {
            val intent = Intent(context, KnowledgeArticleActivity::class.java)
            intent.putExtra(TYPE, knowledge)
            context.startActivity(intent)
        }
    }
}
