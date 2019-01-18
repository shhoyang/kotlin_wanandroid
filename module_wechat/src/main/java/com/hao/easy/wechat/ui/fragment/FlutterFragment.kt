package com.hao.easy.wechat.ui.fragment

import android.view.View
import com.hao.easy.base.ui.BaseListFragment
import com.hao.easy.base.ui.WebActivity
import com.hao.easy.wechat.R
import com.hao.easy.wechat.di.component
import com.hao.easy.wechat.model.Article
import com.hao.easy.wechat.ui.adapter.CommonArticleAdapter
import com.hao.easy.wechat.viewmodel.FlutterViewModel
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
                    var title = item.title.replace(Regex("<[^>]+>"), "")
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
                    var title = item.title.replace(Regex("<[^>]+>"), "")
                    WebActivity.start(this, title, item.link)
                }
            }
        }
    }
}