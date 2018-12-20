package com.hao.easy.wechat.ui.activity

import android.content.Context
import android.view.View
import com.hao.easy.base.ui.BaseListActivity
import com.hao.easy.base.ui.WebActivity
import com.hao.easy.wechat.R
import com.hao.easy.wechat.di.component
import com.hao.easy.wechat.model.Article
import com.hao.easy.wechat.model.Knowledge
import com.hao.easy.wechat.ui.adapter.KnowledgeArticleAdapter
import com.hao.easy.wechat.viewmodel.KnowledgeArticleViewModel
import org.jetbrains.anko.startActivity
import javax.inject.Inject

class KnowledgeArticleActivity : BaseListActivity<Article, KnowledgeArticleViewModel>() {

    @Inject
    lateinit var adapter: KnowledgeArticleAdapter

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
        var type = intent.getParcelableExtra<Knowledge>(TYPE)
        type?.apply {
            title = name
            viewModel.typeId = id
        }
        super.initData()
    }

    override fun itemClicked(view: View, item: Article, position: Int) {
        if (view.id == R.id.ivFav) {
            if (item.collect) {
                viewModel.cancelCollect(item, position)
            } else {
                viewModel.collect(item, position)
            }
        } else {
            WebActivity.start(this, item.title, item.link)
        }
    }
}
