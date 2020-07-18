package com.hao.easy.wan.ui.fragment

import android.view.View
import com.hao.easy.base.ui.BaseListFragment
import com.hao.easy.base.ui.WebActivity
import com.hao.easy.wan.R
import com.hao.easy.wan.extensions.removeHtml
import com.hao.easy.wan.model.Article
import com.hao.easy.wan.ui.adapter.CommonArticleAdapter
import com.hao.easy.wan.viewmodel.FlutterViewModel

/**
 * @author Yang Shihao
 * @date 2018/11/18
 */
class FlutterFragment : BaseListFragment<Article, FlutterViewModel>() {

    override fun getLayoutId() = R.layout.wechat_fragment_flutter

    override fun adapter() = CommonArticleAdapter()

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