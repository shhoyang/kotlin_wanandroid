package com.hao.easy.wan.ui.fragment

import android.view.View
import androidx.lifecycle.Observer
import com.google.android.material.appbar.AppBarLayout
import com.hao.easy.base.extensions.visibility
import com.hao.easy.base.extensions.visible
import com.hao.easy.base.ui.BaseListFragment
import com.hao.easy.base.ui.WebActivity
import com.hao.easy.base.utils.DisplayUtils
import com.hao.easy.wan.R
import com.hao.easy.wan.model.Article
import com.hao.easy.wan.ui.adapter.ProjectArticleAdapter
import com.hao.easy.wan.ui.adapter.ProjectTypePageAdapter
import com.hao.easy.wan.viewmodel.ProjectViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.status_bar_holder.*
import kotlinx.android.synthetic.main.wan_fragment_project.*
import kotlinx.android.synthetic.main.wan_fragment_project.appBarLayout
import kotlinx.android.synthetic.main.wan_fragment_project.baseRefreshLayout
import kotlinx.android.synthetic.main.wan_fragment_project.indicator
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

    override fun initView() {
        super.initView()
        val layoutParams = statusBarHolder.layoutParams
        layoutParams.height = DisplayUtils.getStatusBarHeight(context!!)
        statusBarHolder.layoutParams = layoutParams
        vpType.adapter = typeAdapter
        indicator.setViewPager(vpType)
        typeAdapter.registerAdapterDataObserver(indicator.adapterDataObserver)

        appBarLayout.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { _, verticalOffset ->
            baseRefreshLayout.isEnabled = verticalOffset == 0
        })
    }

    override fun initData() {
        super.initData()
        viewModel.typeLiveData.observe(this, Observer {
            typeAdapter.resetData(it)
            line.visible()
            indicator.visibility(it?.size ?: 0 > 1)
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
//                WebWithImageActivity.start(this, item.title, item.link, item.envelopePic)
                WebActivity.start(this, item.title, item.link)
            }
        }
    }
}