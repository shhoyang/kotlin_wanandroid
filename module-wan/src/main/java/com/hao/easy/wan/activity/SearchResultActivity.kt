package com.hao.easy.wan.activity

import android.content.Context
import android.content.Intent
import android.view.View
import com.hao.easy.base.constant.ExtraKey
import com.hao.easy.base.ui.WebActivity
import com.hao.easy.wan.R
import com.hao.easy.wan.adapter.ArticleAdapter
import com.hao.easy.wan.model.Article
import com.hao.easy.wan.viewmodel.SearchResultViewModel
import com.hao.library.annotation.AndroidEntryPoint
import com.hao.library.databinding.ActivityBaseListBinding
import com.hao.library.ui.BaseListActivity

/**
 * @author Yang Shihao
 */
@AndroidEntryPoint
class SearchResultActivity :
    BaseListActivity<ActivityBaseListBinding, Article, SearchResultViewModel, ArticleAdapter>() {

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
            R.id.ivFavorite -> viewModel { collect(item, position) }
            else -> WebActivity.start(this, item.title, item.link)
        }
    }

    companion object {
        fun start(context: Context, content: String) {
            val intent = Intent(context, SearchResultActivity::class.java)
            intent.putExtra(ExtraKey.STRING, content)
            context.startActivity(intent)
        }
    }
}