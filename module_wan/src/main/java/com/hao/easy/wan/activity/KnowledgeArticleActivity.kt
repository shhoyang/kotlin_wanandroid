package com.hao.easy.wan.activity

import android.content.Context
import android.content.Intent
import android.view.View
import com.hao.easy.base.constant.ExtraKey
import com.hao.easy.wan.R
import com.hao.easy.wan.adapter.ArticleAdapter
import com.hao.easy.wan.model.Article
import com.hao.easy.wan.model.Knowledge
import com.hao.easy.wan.viewmodel.KnowledgeArticleViewModel
import com.hao.library.annotation.AndroidEntryPoint
import com.hao.library.databinding.ActivityBaseListBinding
import com.hao.library.ui.BaseListActivity
import com.hao.library.ui.WebActivity

@AndroidEntryPoint
class KnowledgeArticleActivity :
    BaseListActivity<ActivityBaseListBinding, Article, KnowledgeArticleViewModel, ArticleAdapter>() {

    override fun initData() {
        viewModel {
            lifecycle.addObserver(this)
            intent.getParcelableExtra<Knowledge>(ExtraKey.PARCELABLE)?.apply {
                title = name
                viewModel { typeId = id }
            }
        }
        super.initData()
    }

    override fun itemClicked(view: View, item: Article, position: Int) {
        when (view.id) {
            R.id.tvLink -> WebActivity.start(this, item.title, item.projectLink)
            R.id.ivFavorite -> viewModel { collect(item, position) }
            else -> WebActivity.start(this, item.title, item.link)
        }
    }

    companion object {
        fun start(context: Context, knowledge: Knowledge) {
            val intent = Intent(context, KnowledgeArticleActivity::class.java)
            intent.putExtra(ExtraKey.PARCELABLE, knowledge)
            context.startActivity(intent)
        }
    }
}
