package com.hao.easy.wan.ui.activity

import android.content.Context
import android.content.Intent
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.hao.easy.base.common.ExtraKey
import com.hao.easy.base.databinding.ActivityBaseListBinding
import com.hao.easy.base.ui.BaseListActivity
import com.hao.easy.base.ui.WebActivity
import com.hao.easy.wan.R
import com.hao.easy.wan.model.Article
import com.hao.easy.wan.ui.adapter.CommonArticleAdapter
import com.hao.easy.wan.viewmodel.SearchListViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

/**
 * @author Yang Shihao
 * @date 2018/12/3
 */
@AndroidEntryPoint
class SearchListActivity :
    BaseListActivity<ActivityBaseListBinding, Article, SearchListViewModel, CommonArticleAdapter>() {

    @Inject
    lateinit var adapter: CommonArticleAdapter

    override fun getVB() = ActivityBaseListBinding.inflate(layoutInflater)

    override fun getVM() = ViewModelProvider(this).get(SearchListViewModel::class.java)

    override fun adapter() = adapter

    override fun initData() {
        viewModel {
            lifecycle.addObserver(this)
            intent.getStringExtra(ExtraKey.STRING)?.let {
                title = it
                viewModel { hotWord = it }
            }
        }
        super.initData()
    }

    override fun itemClicked(view: View, item: Article, position: Int) {
        when (view.id) {
            R.id.tvLink -> WebActivity.start(this, item.title, item.projectLink)
            R.id.ivFav -> viewModel { collect(item, position) }
            else -> WebActivity.start(this, item.title, item.link)
        }
    }

    companion object {
        fun start(context: Context, content: String) {
            val intent = Intent(context, SearchListActivity::class.java)
            intent.putExtra(ExtraKey.STRING, content)
            context.startActivity(intent)
        }
    }
}