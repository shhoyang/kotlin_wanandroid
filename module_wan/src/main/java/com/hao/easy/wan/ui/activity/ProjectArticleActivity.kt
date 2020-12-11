package com.hao.easy.wan.ui.activity

import android.content.Context
import android.content.Intent
import android.view.View
import com.hao.easy.base.adapter.ViewHolder
import com.hao.easy.base.ui.BaseListActivity
import com.hao.easy.base.ui.WebActivity
import com.hao.easy.wan.R
import com.hao.easy.wan.model.Article
import com.hao.easy.wan.model.ProjectType
import com.hao.easy.wan.ui.adapter.ProjectArticleAdapter
import com.hao.easy.wan.viewmodel.ProjectArticleViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ProjectArticleActivity : BaseListActivity<Article, ProjectArticleViewModel>() {

    @Inject
    lateinit var adapter: ProjectArticleAdapter

    override fun adapter() = adapter

    override fun initData() {
        intent.getParcelableExtra<ProjectType>(TYPE)?.apply {
            title = name.replace("&amp;", "")
            viewModel.typeId = id
        }

        super.initData()
        lifecycle.addObserver(viewModel)
    }

    override fun itemClicked(holder: ViewHolder, view: View, item: Article, position: Int) {
        when (view.id) {
            R.id.tvLink -> WebActivity.start(this, item.title, item.projectLink)
            R.id.ivFav -> viewModel.collect(item, position)
            else -> WebActivity.start(this, item.title, item.link)
        }
    }

    companion object {
        private const val TYPE = "TYPE"
        fun start(context: Context, projectType: ProjectType) {
            val intent = Intent(context, ProjectArticleActivity::class.java)
            intent.putExtra(TYPE, projectType)
            context.startActivity(intent)
        }
    }
}
