package com.hao.easy.ui.activity

import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import com.hao.easy.base.extensions.snack
import com.hao.easy.base.ui.BaseListActivity
import com.hao.easy.base.ui.WebActivity
import com.hao.easy.R
import com.hao.easy.model.Article
import com.hao.easy.ui.adapter.FavAdapter
import com.hao.easy.viewmodel.FavViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

/**
 * @author Yang Shihao
 * @date 2018/12/3
 */

@AndroidEntryPoint
class FavActivity : BaseListActivity<Article, FavViewModel>() {

    @Inject
    lateinit var adapter: FavAdapter

    override fun initView() {
        title = "我的收藏"
        super.initView()
    }

    override fun initData() {
        super.initData()
        viewModel.deleteResult.observe(this, Observer {
            if (true == it) {
                refreshLayout?.snack("刪除成功")
            }
        })
    }

    override fun adapter() = adapter

    override fun itemClicked(view: View, item: Article, position: Int) {
        if (view.id == R.id.ivFav) {
            val alertDialog = AlertDialog.Builder(this)
            alertDialog.setTitle("是否刪除該條目？")
                .setMessage(item.title)
                .setPositiveButton(
                    "确定"
                ) { dialog, _ ->
                    viewModel.cancelCollect(item, position)
                    dialog.dismiss()
                }
                .setNegativeButton(
                    "取消"
                ) { dialog, _ ->
                    dialog.dismiss()
                }

            alertDialog.show()
        } else {
            WebActivity.start(this, item.title, item.link)
        }
    }
}