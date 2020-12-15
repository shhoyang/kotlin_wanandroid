package com.hao.easy.wan.ui.activity

import android.content.Context
import android.content.Intent
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.hao.easy.base.common.ExtraKey
import com.hao.easy.base.databinding.ActivityBaseListBinding
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
class ProjectArticleActivity :
    BaseListActivity<ActivityBaseListBinding, Article, ProjectArticleViewModel, ProjectArticleAdapter>() {

    @Inject
    lateinit var adapter: ProjectArticleAdapter

    override fun getVB() = ActivityBaseListBinding.inflate(layoutInflater)

    override fun getVM() = ViewModelProvider(this).get(ProjectArticleViewModel::class.java)

    override fun adapter() = adapter

    override fun initData() {
        viewModel {
            lifecycle.addObserver(this)
            intent.getParcelableExtra<ProjectType>(ExtraKey.PARCELABLE)?.apply {
                title = name
                viewModel { typeId = id }
            }
        }
        super.initData()
    }

    override fun itemClicked(view: View, item: Article, position: Int) {
        when (view.id) {
            R.id.tvLink -> WebActivity.start(this, item.title, item.projectLink)
            R.id.ivFav -> viewModel { collect(item, position) }
            else -> WebActivity.start(this, item.title, item.link)
        }
    }

    companion object {
        fun start(context: Context, projectType: ProjectType) {
            val intent = Intent(context, ProjectArticleActivity::class.java)
            intent.putExtra(ExtraKey.PARCELABLE, projectType)
            context.startActivity(intent)
        }
    }
}
