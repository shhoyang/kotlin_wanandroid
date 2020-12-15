package com.hao.easy.wan.ui.activity

import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.alibaba.android.arouter.facade.annotation.Route
import com.hao.easy.base.databinding.ActivityBaseListBinding
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
class FavActivity : BaseListActivity<ActivityBaseListBinding, Article, FavViewModel, FavAdapter>() {

    @Inject
    lateinit var adapter: FavAdapter

    override fun getVB() = ActivityBaseListBinding.inflate(layoutInflater)

    override fun getVM() = ViewModelProvider(this).get(FavViewModel::class.java)

    override fun adapter() = adapter

    override fun initView() {
        title = "我的收藏"
        super.initView()
    }

    override fun initData() {
        super.initData()
        viewModel {
            deleteResult.observe(this@FavActivity, Observer {
                toast("刪除成功")
            })
        }
    }

    override fun itemClicked(view: View, item: Article, position: Int) {
        if (view.id == R.id.ivFav) {
            ConfirmDialog(this)
                .setMsg("确定刪除该收藏吗？")
                .setListener(object : ConfirmDialogListener {
                    override fun confirm() {
                        viewModel { cancelCollect(item, position) }
                    }

                    override fun cancel() {
                    }

                }).show()
        } else {
            WebActivity.start(this, item.title, item.link)
        }
    }
}