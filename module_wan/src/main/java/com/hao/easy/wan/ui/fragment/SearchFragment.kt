package com.hao.easy.wan.ui.fragment

import android.os.Bundle
import android.text.TextUtils
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.hao.easy.base.BaseApplication
import com.hao.easy.base.adapter.OnItemClickListener
import com.hao.easy.base.extensions.init
import com.hao.easy.base.extensions.visibility
import com.hao.easy.base.ui.BaseFragment
import com.hao.easy.base.ui.UIParams
import com.hao.easy.base.utils.DisplayUtils
import com.hao.easy.wan.R
import com.hao.easy.wan.db.HistoryDb
import com.hao.easy.wan.model.HotWord
import com.hao.easy.wan.ui.activity.SearchListActivity
import com.hao.easy.wan.ui.adapter.HotWordAdapter
import com.hao.easy.wan.viewmodel.SearchViewModel
import com.library.flowlayout.FlowLayoutManager
import com.library.flowlayout.SpaceItemDecoration
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.wan_fragment_search.*
import javax.inject.Inject

/**
 * @author Yang Shihao
 * @date 2018/11/18
 */
@AndroidEntryPoint
class SearchFragment : BaseFragment(), OnItemClickListener<HotWord> {

    @Inject
    lateinit var hotWordAdapter: HotWordAdapter

    @Inject
    lateinit var historyAdapter: HotWordAdapter

    private val viewModel by lazy {
        ViewModelProvider(this).get(SearchViewModel::class.java)
    }

    override fun getLayoutId() = R.layout.wan_fragment_search

    override fun prepare(uiParams: UIParams, bundle: Bundle?) {
        uiParams.isLazy = true
    }

    override fun initView() {
        DisplayUtils.setStatusBarHolder(baseToolbar)
        hotWordAdapter.setOnItemClickListener(this)
        historyAdapter.setOnItemClickListener(this)

        val spaceItemDecoration =
            SpaceItemDecoration(DisplayUtils.dp2px(context ?: BaseApplication.instance, 4))
        rvHotWord.addItemDecoration(spaceItemDecoration)
        rvHistory.addItemDecoration(spaceItemDecoration)
        rvHotWord.init(hotWordAdapter, FlowLayoutManager())
        rvHistory.init(historyAdapter, FlowLayoutManager())

        tvSearch.setOnClickListener {
            search(etContent.text.toString().trim())
        }

        tvClearHistory.setOnClickListener {
            viewModel.deleteAll()
        }
    }

    private fun search(content: String) {
        if (!TextUtils.isEmpty(content) && context != null) {
            SearchListActivity.start(context ?: BaseApplication.instance, content)
            viewModel.search(content)
        }
    }

    override fun initData() {
        viewModel.hotWordLiveData.observe(this, Observer {
            hotWordAdapter.resetData(it)
            val visible = hotWordAdapter.data.isNotEmpty()
            tvHotTitle.visibility(visible)
            rvHotWord.visibility(visible)
        })
        HistoryDb.instance().historyDao().queryAll().observe(this, Observer {
            historyAdapter.resetData(it)
            val visible = historyAdapter.data.isNotEmpty()
            rlHistoryTitle.visibility(visible)
            rvHistory.visibility(visible)
        })
        viewModel.getHotWords()
    }

    override fun itemClicked(view: View, item: HotWord, position: Int) {
        search(item.name)
    }
}