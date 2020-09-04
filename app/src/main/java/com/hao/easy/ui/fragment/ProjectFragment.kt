package com.hao.easy.ui.fragment

import android.view.View
import androidx.lifecycle.Observer
import com.google.android.material.appbar.AppBarLayout
import com.hao.easy.R
import com.hao.easy.base.extensions.visibility
import com.hao.easy.base.extensions.visible
import com.hao.easy.base.ui.BaseListFragment
import com.hao.easy.base.ui.WebActivity
import com.hao.easy.model.Article
import com.hao.easy.ui.adapter.ProjectArticleAdapter
import com.hao.easy.ui.adapter.ProjectTypePageAdapter
import com.hao.easy.viewmodel.ProjectViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.wechat_fragment_project.*
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

    private var enableRefresh = true
    private var appBarOffset = 0

    override fun getLayoutId() = R.layout.wechat_fragment_project

    override fun initView() {
        super.initView()

        vpType.adapter = typeAdapter
        indicator.setViewPager(vpType)
        typeAdapter.registerAdapterDataObserver(indicator.adapterDataObserver)

        appBarLayout.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { _, verticalOffset ->
            appBarOffset = verticalOffset
            baseRefreshLayout.isEnabled = appBarOffset == 0 && enableRefresh
        })
    }

    override fun initData() {
        super.initData()
        viewModel.typeLiveData.observe(this, Observer {
            typeAdapter.setData(it!!)
            line.visible()
            indicator.visibility(it.size > 1)
        })
        lifecycle.addObserver(viewModel)
    }

    override fun adapter() = adapter

    override fun itemClicked(view: View, item: Article, position: Int) {
        when (view.id) {
            R.id.tvLink -> {
                context?.apply {
                    WebActivity.start(this, item.title, item.projectLink)
                }
            }
            R.id.ivFav -> viewModel.collect(item, position)
            else -> context?.apply {
                WebActivity.start(this, item.title, item.link)
            }
        }
    }
}