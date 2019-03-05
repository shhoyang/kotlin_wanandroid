package com.hao.easy.wan.ui.activity

import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.hao.easy.base.ui.BaseListActivity
import com.hao.easy.base.ui.WebActivity
import com.hao.easy.wan.R
import com.hao.easy.wan.di.component
import com.hao.easy.wan.model.Article
import com.hao.easy.wan.ui.adapter.FavAdapter
import com.hao.easy.wan.viewmodel.FavViewModel
import javax.inject.Inject

/**
 * @author Yang Shihao
 * @date 2018/12/3
 */

@Route(path = "/wechat/FavActivity")
class FavActivity : BaseListActivity<Article, FavViewModel>() {

    @Inject
    lateinit var adapter: FavAdapter

    override fun initInject() {
        component().inject(this)
    }

    override fun initView() {
        title = "我的收藏"
        super.initView()
    }

    override fun adapter() = adapter

    override fun itemClicked(view: View, item: Article, position: Int) {
        if (view.id == R.id.buttonDelete) {
            viewModel.cancelCollect(item, position)
        } else {
            WebActivity.start(this, item.title, item.link)
        }
    }
}