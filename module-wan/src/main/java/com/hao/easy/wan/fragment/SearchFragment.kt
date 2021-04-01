package com.hao.easy.wan.fragment

import android.graphics.Color
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import com.hao.easy.base.BaseApplication
import com.hao.easy.wan.activity.SearchResultActivity
import com.hao.easy.wan.adapter.HotWordAdapter
import com.hao.easy.wan.databinding.WanFragmentSearchBinding
import com.hao.easy.wan.db.Db
import com.hao.easy.wan.model.HotWord
import com.hao.easy.wan.viewmodel.SearchViewModel
import com.hao.library.adapter.listener.OnItemClickListener
import com.hao.library.annotation.AndroidEntryPoint
import com.hao.library.annotation.Inject
import com.hao.library.extensions.init
import com.hao.library.extensions.visibility
import com.hao.library.ui.BaseFragment
import com.hao.library.ui.UIParams
import com.hao.library.utils.DisplayUtils
import com.hao.library.utils.DrawableUtils
import com.library.flowlayout.FlowLayoutManager
import com.library.flowlayout.SpaceItemDecoration

/**
 * @author Yang Shihao
 */
@AndroidEntryPoint
class SearchFragment : BaseFragment<WanFragmentSearchBinding, SearchViewModel>(),
    OnItemClickListener<HotWord> {

    @Inject
    lateinit var hotWordAdapter: HotWordAdapter

    @Inject
    lateinit var historyAdapter: HotWordAdapter

    override fun prepare(uiParams: UIParams, bundle: Bundle?) {
        uiParams.isLazy = true
    }

    override fun initView() {

        hotWordAdapter.setOnItemClickListener(this@SearchFragment)
        historyAdapter.setOnItemClickListener(this@SearchFragment)
        val spaceItemDecoration =
            SpaceItemDecoration(DisplayUtils.dp2px(context ?: BaseApplication.instance, 4))

        viewBinding {
            etContent.background = DrawableUtils.generateRoundRectDrawable(1000F, Color.WHITE)
            rvHotWord.addItemDecoration(spaceItemDecoration)
            rvHistory.addItemDecoration(spaceItemDecoration)
            rvHotWord.init(hotWordAdapter, FlowLayoutManager())
            rvHistory.init(historyAdapter, FlowLayoutManager())

            tvSearch.setOnClickListener {
                search(etContent.text.toString().trim())
            }
            tvClearHistory.setOnClickListener {
                viewModel { deleteAll() }
            }
        }
    }

    private fun search(content: String) {
        if (TextUtils.isEmpty(content)) {
            return
        }

        viewModel { insert(content) }
        act {
            SearchResultActivity.start(it, content)
        }
    }

    override fun initData() {
        Db.instance().historyDao().queryAll().observe(this@SearchFragment) {
            historyAdapter.resetData(it)
            viewBinding {
                val visible = historyAdapter.data.isNotEmpty()
                rlHistoryTitle.visibility(visible)
                rvHistory.visibility(visible)
            }
        }
        viewModel {
            hotWordLiveData.observe(this@SearchFragment) {
                hotWordAdapter.resetData(it)
                viewBinding {
                    val visible = hotWordAdapter.data.isNotEmpty()
                    tvHotTitle.visibility(visible)
                    rvHotWord.visibility(visible)
                }
            }
            getHotWords()
        }
    }

    override fun itemClicked(view: View, item: HotWord, position: Int) {
        search(item.name)
    }
}