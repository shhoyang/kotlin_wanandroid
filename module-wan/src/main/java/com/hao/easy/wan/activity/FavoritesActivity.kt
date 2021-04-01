package com.hao.easy.wan.activity

import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.hao.easy.base.ui.WebActivity
import com.hao.easy.wan.R
import com.hao.easy.wan.adapter.FavoritesAdapter
import com.hao.easy.wan.model.Article
import com.hao.easy.wan.viewmodel.FavoritesViewModel
import com.hao.library.annotation.AndroidEntryPoint
import com.hao.library.databinding.ActivityBaseListBinding
import com.hao.library.ui.BaseListActivity
import com.hao.library.view.dialog.ConfirmDialog
import com.hao.library.view.dialog.ConfirmDialogListener

/**
 * @author Yang Shihao
 */
@AndroidEntryPoint
@Route(path = "/wan/FavoritesActivity")
class FavoritesActivity :
    BaseListActivity<ActivityBaseListBinding, Article, FavoritesViewModel, FavoritesAdapter>(){

    override fun initView() {
        title = getString(R.string.wan_title_fav)
        super.initView()
    }

    override fun initData() {
        super.initData()
        viewModel {
            deleteResult.observe(this@FavoritesActivity) {
                toast(R.string.wan_cancel_fav)
            }
        }
    }

    override fun itemClicked(view: View, item: Article, position: Int) {
        if (view.id == R.id.ivFavorite) {
            ConfirmDialog.Builder(this)
                .setMessage(getString(R.string.wan_confirm_cancel_fav))
                .setListener(object : ConfirmDialogListener {
                    override fun confirm() {
                        viewModel { cancelCollect(item, position) }
                    }

                    override fun cancel() {
                    }

                }).build().show()
        } else {
            WebActivity.start(this, item.title, item.link)
        }
    }
}