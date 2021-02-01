package com.hao.library.ui

import android.view.View
import androidx.annotation.CallSuper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.hao.library.R
import com.hao.library.adapter.BasePagedAdapter
import com.hao.library.adapter.PagedAdapterItem
import com.hao.library.adapter.listener.OnItemClickListener
import com.hao.library.annotation.Base
import com.hao.library.annotation.Inject
import com.hao.library.constant.DataListResult
import com.hao.library.view.EmptyView
import com.hao.library.view.RefreshLayout
import com.hao.library.viewmodel.BaseListViewModel

/**
 * @author Yang Shihao
 */
@Base
abstract class BaseListFragment<VB : ViewBinding, D : PagedAdapterItem, VM : BaseListViewModel<D>, A : BasePagedAdapter<*, D>> :
    BaseFragment<VB, VM>(),
    OnItemClickListener<D> {

    private var refreshLayout: RefreshLayout? = null
    private var emptyView: EmptyView? = null

    @Inject
    lateinit var adapter: A

    @CallSuper
    override fun initView() {
        val recyclerView: RecyclerView = f(R.id.baseRecyclerView)!!
        adapter.setOnItemClickListener(this)
        recyclerView.layoutManager = getLayoutManager()
        recyclerView.adapter = adapter
        this.refreshLayout = f(R.id.baseRefreshLayout)
        this.emptyView = f(R.id.baseEmptyView)
        this.refreshLayout?.setOnRefreshListener {
            viewModel { refresh() }
        }
    }

    @CallSuper
    override fun initData() {
        viewModel {
            loadLiveData.observe(this@BaseListFragment) {
                adapter.submitList(it)
            }
            refreshLiveData.observe(this@BaseListFragment) {
                refreshFinished(it)
            }
            loadMoreLiveData.observe(this@BaseListFragment) {
                loadMoreFinished(it)
            }
            if (uiParams.hasItemChange) {
                notifyItemLiveData.observe(this@BaseListFragment) {
                    adapter.notifyItemChanged(it.first, it.second)
                }
            }
            if (uiParams.hasItemRemove) {
                removeItemLiveData.observe(this@BaseListFragment) {
                    adapter.notifyItemRemoved(it)
                }
            }
        }
    }

    open fun getLayoutManager(): RecyclerView.LayoutManager {
        return LinearLayoutManager(requireContext())
    }

    override fun itemClicked(view: View, item: D, position: Int) {

    }

    @CallSuper
    open fun refreshFinished(result: DataListResult) {
        refreshLayout?.isRefreshing = false
        emptyView?.apply {
            when (result) {
                DataListResult.SUCCEED -> dismiss()
                DataListResult.FAILED -> loadFailed()
                DataListResult.NO_DATA -> noData()
                DataListResult.NO_MORE -> dismiss()
            }
        }
    }

    open fun loadMoreFinished(result: DataListResult) {
    }
}