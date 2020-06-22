package com.hao.easy.ui.activity

import android.content.Context
import android.content.Intent
import android.view.View
import com.hao.easy.R
import com.hao.easy.base.ui.BaseListActivity
import com.hao.easy.base.ui.WebActivity
import com.hao.easy.model.Article
import com.hao.easy.model.ProjectType
import com.hao.easy.ui.adapter.ProjectArticleAdapter
import com.hao.easy.viewmodel.ProjectArticleViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ProjectArticleActivity : BaseListActivity<Article, ProjectArticleViewModel>() {

    @Inject
    lateinit var adapter: ProjectArticleAdapter

    companion object {
        private const val TYPE = "TYPE"
        fun start(context: Context, projectType: ProjectType) {
            val intent = Intent(context, ProjectArticleActivity::class.java)
            intent.putExtra(TYPE, projectType)
            context.startActivity(intent)
        }
    }

    override fun initData() {
        val type = intent.getParcelableExtra<ProjectType>(TYPE)
        type?.apply {
            title = name.replace("&amp;", "")
            viewModel.typeId = id
        }

        super.initData()
    }

    override fun adapter() = adapter

    override fun itemClicked(view: View, item: Article, position: Int) {
        when (view.id) {
            R.id.tvLink -> {
                WebActivity.start(this, item.title, item.projectLink)
            }
            R.id.ivFav -> viewModel.collect(item, position)
            else -> WebActivity.start(this, item.title, item.link)
        }
    }
}
