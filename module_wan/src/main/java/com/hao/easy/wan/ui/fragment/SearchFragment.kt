package com.hao.easy.wan.ui.fragment

import android.graphics.Color
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.hao.easy.base.BaseApplication
import com.hao.easy.base.adapter.listener.OnItemClickListener
import com.hao.easy.base.extensions.init
import com.hao.easy.base.extensions.visibility
import com.hao.easy.base.ui.BaseFragment
import com.hao.easy.base.ui.UIParams
import com.hao.easy.base.utils.DisplayUtils
import com.hao.easy.base.utils.DrawableUtils
import com.hao.easy.base.view.dialog.ConfirmDialog
import com.hao.easy.base.view.dialog.LoadingDialog
import com.hao.easy.wan.databinding.WanFragmentSearchBinding
import com.hao.easy.wan.db.Db
import com.hao.easy.wan.model.HotWord
import com.hao.easy.wan.ui.activity.SearchListActivity
import com.hao.easy.wan.ui.adapter.HotWordAdapter
import com.hao.easy.wan.viewmodel.SearchViewModel
import com.library.flowlayout.FlowLayoutManager
import com.library.flowlayout.SpaceItemDecoration
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

/**
 * @author Yang Shihao
 * @date 2018/11/18
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

    override fun getVB() = WanFragmentSearchBinding.inflate(layoutInflater)

    override fun getVM() = ViewModelProvider(this).get(SearchViewModel::class.java)

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
            SearchListActivity.start(it, content)
        }
    }

    override fun initData() {
        Db.instance().historyDao().queryAll().observe(this@SearchFragment, Observer {
            historyAdapter.resetData(it)
            viewBinding {
                val visible = historyAdapter.data.isNotEmpty()
                rlHistoryTitle.visibility(visible)
                rvHistory.visibility(visible)
            }
        })
        viewModel {
            hotWordLiveData.observe(this@SearchFragment, Observer {
                hotWordAdapter.resetData(it)
                viewBinding {
                    val visible = hotWordAdapter.data.isNotEmpty()
                    tvHotTitle.visibility(visible)
                    rvHotWord.visibility(visible)
                }
            })
            getHotWords()
        }
    }

    override fun itemClicked(view: View, item: HotWord, position: Int) {
        search(item.name)
    }
}