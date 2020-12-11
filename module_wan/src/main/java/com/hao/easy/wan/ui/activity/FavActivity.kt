package com.hao.easy.wan.ui.activity

import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.hao.easy.base.adapter.ViewHolder
import com.hao.easy.base.ui.BaseListActivity
import com.hao.easy.base.ui.WebActivity
import com.hao.easy.base.view.dialog.ConfirmDialog
import com.hao.easy.base.view.dialog.ConfirmDialogListener
import com.hao.easy.wan.R
import com.hao.easy.wan.model.Article
import com.hao.easy.wan.ui.adapter.FavAdapter
import com.hao.easy.wan.viewmodel.FavViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

/**
 * @author Yang Shihao
 * @date 2018/12/3
 */
@AndroidEntryPoint
@Route(path = "/wechat/FavActivity")
class FavActivity : BaseListActivity<Article, FavViewModel>() {

    @Inject
    lateinit var adapter: FavAdapter

    override fun initView() {
        title = "我的收藏"
        super.initView()
    }

    override fun initData() {
        super.initData()
        viewModel.deleteResult.observe(this) {
            toast("刪除成功")
        }
    }

    override fun adapter() = adapter

    override fun itemClicked(holder: ViewHolder, view: View, item: Article, position: Int) {
        if (view.id == R.id.ivFav) {
            ConfirmDialog(this)
                .setMsg("是否刪除该条目吗？")
                .setListener(object : ConfirmDialogListener {
                    override fun confirm() {
                        viewModel.cancelCollect(item, position)
                    }

                    override fun cancel() {
                    }

                }).show()
        } else {
            WebActivity.start(this, item.title, item.link)
        }
    }
}