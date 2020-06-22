package com.hao.easy.ui.fragment

import android.view.View
import com.hao.easy.base.ui.BaseListFragment
import com.hao.easy.base.ui.WebActivity
import com.hao.easy.R
import com.hao.easy.extensions.removeHtml
import com.hao.easy.model.Article
import com.hao.easy.ui.adapter.CommonArticleAdapter
import com.hao.easy.viewmodel.FlutterViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

/**
 * @author Yang Shihao
 * @date 2018/11/18
 */
@AndroidEntryPoint
class FlutterFragment : BaseListFragment<Article, FlutterViewModel>() {

    @Inject
    lateinit var adapter: CommonArticleAdapter

    override fun getLayoutId() = R.layout.wechat_fragment_flutter

    override fun adapter() = adapter

    override fun initData() {
        super.initData()
        lifecycle.addObserver(viewModel)
    }

    override fun itemClicked(view: View, item: Article, position: Int) {
        when (view.id) {
            R.id.tvLink -> {
                context?.apply {
                    val title = item.title.removeHtml()
                    WebActivity.start(this, title, item.projectLink)
                }
            }
            R.id.ivFav -> viewModel.collect(item, position)
            else -> {
                context?.apply {
                    val title = item.title.removeHtml()
                    WebActivity.start(this, title, item.link)
                }
            }
        }
    }
}