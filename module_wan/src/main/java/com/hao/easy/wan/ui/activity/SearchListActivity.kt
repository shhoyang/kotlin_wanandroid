package com.hao.easy.wan.ui.activity

import android.content.Context
import android.content.Intent
import android.view.View
import com.hao.easy.base.adapter.ViewHolder
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
class SearchListActivity : BaseListActivity<Article, SearchListViewModel>() {

    @Inject
    lateinit var adapter: CommonArticleAdapter

    override fun adapter() = adapter

    override fun initData() {
        intent.getStringExtra(CONTENT)?.let {
            title = it
            viewModel.hotWord = it
        }
        super.initData()
        lifecycle.addObserver(viewModel)
    }

    override fun itemClicked(holder: ViewHolder, view: View, item: Article, position: Int) {
        when (view.id) {
            R.id.tvLink -> WebActivity.start(this, item.title, item.projectLink)
            R.id.ivFav -> viewModel.collect(item, position)
            else -> WebActivity.start(this, item.title, item.link)
        }
    }

    companion object {
        private const val CONTENT = "CONTENT"
        fun start(context: Context, content: String) {
            val intent = Intent(context, SearchListActivity::class.java)
            intent.putExtra(CONTENT, content)
            context.startActivity(intent)
        }
    }
}