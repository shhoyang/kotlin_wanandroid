package com.hao.easy.wan.ui.fragment

import android.view.View
import com.hao.easy.base.ui.BaseListFragment
import com.hao.easy.base.ui.WebActivity
import com.hao.easy.wan.R
import com.hao.easy.wan.di.component
import com.hao.easy.wan.model.Article
import com.hao.easy.wan.ui.adapter.CommonArticleAdapter
import com.hao.easy.wan.viewmodel.FlutterViewModel
import javax.inject.Inject

/**
 * @author Yang Shihao
 * @date 2018/11/18
 */
class FlutterFragment : BaseListFragment<Article, FlutterViewModel>() {

    @Inject
    lateinit var adapter: CommonArticleAdapter

    companion object {
        private const val TAG = "KotlinFragment"
    }

    override fun getLayoutId() = R.layout.wechat_fragment_flutter

    override fun isLazy() = true

    override fun adapter() = adapter

    override fun initInject() {
        component().inject(this)
    }

    override fun initData() {
        super.initData()
        lifecycle.addObserver(viewModel)
    }

    override fun itemClicked(view: View, item: Article, position: Int) {
        when (view.id) {
            R.id.tvLink -> {
                context?.apply {
                    val title = item.title.replace(Regex("<[^>]+>"), "")
                    WebActivity.start(this, title, item.projectLink)
                }
            }
            R.id.ivFav -> {
                if (item.collect) {
                    viewModel.cancelCollect(item, position)
                } else {
                    viewModel.collect(item, position)
                }
            }
            else -> {
                context?.apply {
                    val title = item.title.replace(Regex("<[^>]+>"), "")
                    WebActivity.start(this, title, item.link)
                }
            }
        }
    }
}