package com.hao.easy.ui.activity

import android.content.Context
import android.content.Intent
import android.view.View
import com.hao.easy.R
import com.hao.easy.base.ui.BaseListActivity
import com.hao.easy.base.ui.WebActivity
import com.hao.easy.extensions.removeHtml
import com.hao.easy.model.Article
import com.hao.easy.model.Knowledge
import com.hao.easy.ui.adapter.CommonArticleAdapter
import com.hao.easy.viewmodel.KnowledgeArticleViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class KnowledgeArticleActivity : BaseListActivity<Article, KnowledgeArticleViewModel>() {

    @Inject
    lateinit var adapter: CommonArticleAdapter

    companion object {
        private const val TYPE = "TYPE"
        fun start(context: Context, knowledge: Knowledge) {
            val intent = Intent(context, KnowledgeArticleActivity::class.java)
            intent.putExtra(TYPE, knowledge)
            context.startActivity(intent)
        }
    }

    override fun adapter() = adapter

    override fun initData() {
        val type = intent.getParcelableExtra<Knowledge>(TYPE)
        type?.apply {
            title = name
            viewModel.typeId = id
        }
        super.initData()
    }

    override fun itemClicked(view: View, item: Article, position: Int) {
        when (view.id) {
            R.id.tvLink -> {
                val title = item.title.removeHtml()
                WebActivity.start(this, title, item.projectLink)
            }
            R.id.ivFav -> viewModel.collect(item, position)
            else -> {
                val title = item.title.removeHtml()
                WebActivity.start(this, title, item.link)
            }
        }
    }
}
