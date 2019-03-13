package com.hao.easy.wan.ui.activity

import android.content.Context
import android.view.View
import com.hao.easy.base.ui.BaseListActivity
import com.hao.easy.base.ui.WebActivity
import com.hao.easy.wan.R
import com.hao.easy.wan.di.component
import com.hao.easy.wan.model.Article
import com.hao.easy.wan.model.Knowledge
import com.hao.easy.wan.ui.adapter.CommonArticleAdapter
import com.hao.easy.wan.viewmodel.KnowledgeArticleViewModel
import org.jetbrains.anko.startActivity
import javax.inject.Inject

class KnowledgeArticleActivity : BaseListActivity<Article, KnowledgeArticleViewModel>() {

    @Inject
    lateinit var adapter: CommonArticleAdapter

    companion object {
        private const val TYPE = "TYPE"
        fun start(context: Context, knowledge: Knowledge) {
            context.startActivity<KnowledgeArticleActivity>(Pair(TYPE, knowledge))
        }
    }

    override fun adapter() = adapter

    override fun initInject() {
        component().inject(this)
    }

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
                val title = item.title.replace(Regex("<[^>]+>"), "")
                WebActivity.start(this, title, item.projectLink)
            }
            R.id.ivFav -> {
                if (item.collect) {
                    viewModel.cancelCollect(item, position)
                } else {
                    viewModel.collect(item, position)
                }
            }
            else -> {
                val title = item.title.replace(Regex("<[^>]+>"), "")
                WebActivity.start(this, title, item.link)
            }
        }
    }
}
