package com.hao.easy.wan.ui.fragment

import android.os.Bundle
import android.view.View
import com.google.android.material.appbar.AppBarLayout
import com.hao.easy.base.adapter.ViewHolder
import com.hao.easy.base.extensions.visibility
import com.hao.easy.base.extensions.visible
import com.hao.easy.base.ui.BaseListFragment
import com.hao.easy.base.ui.UIParams
import com.hao.easy.base.ui.WebActivity
import com.hao.easy.wan.R
import com.hao.easy.wan.model.Article
import com.hao.easy.wan.ui.adapter.ProjectArticleAdapter
import com.hao.easy.wan.ui.adapter.ProjectTypePageAdapter
import com.hao.easy.wan.viewmodel.ProjectViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.wan_fragment_project.*
import javax.inject.Inject

/**
 * @author Yang Shihao
 * @date 2018/11/18
 */
@AndroidEntryPoint
class ProjectFragment : BaseListFragment<Article, ProjectViewModel>() {

    @Inject
    lateinit var adapter: ProjectArticleAdapter

    @Inject
    lateinit var typeAdapter: ProjectTypePageAdapter

    override fun getLayoutId() = R.layout.wan_fragment_project

    override fun prepare(uiParams: UIParams, bundle: Bundle?) {
        uiParams.isLazy = true
    }

    override fun adapter() = adapter

    override fun initView() {
        super.initView()
        vpType.adapter = typeAdapter
        indicator.setViewPager(vpType)
        typeAdapter.registerAdapterDataObserver(indicator.adapterDataObserver)

        appBarLayout.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { _, verticalOffset ->
            baseRefreshLayout.isEnabled = verticalOffset == 0
        })
    }

    override fun initData() {
        viewModel.typeLiveData.observe(this) {
            typeAdapter.resetData(it)
            line.visible()
            indicator.visibility(it?.size ?: 0 > 1)
        }
        super.initData()
        lifecycle.addObserver(viewModel)
    }

    override fun itemClicked(holder: ViewHolder, view: View, item: Article, position: Int) {
        when (view.id) {
            R.id.tvLink -> act {
                WebActivity.start(it, item.title, item.projectLink)
            }
            R.id.ivFav -> viewModel.collect(item, position)
            else -> act {
                WebActivity.start(it, item.title, item.link)
            }
        }
    }
}