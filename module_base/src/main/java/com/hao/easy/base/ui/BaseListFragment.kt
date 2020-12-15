package com.hao.easy.base.ui

import android.view.View
import androidx.annotation.CallSuper
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.hao.easy.base.R
import com.hao.easy.base.adapter.BaseItem
import com.hao.easy.base.adapter.BasePagedAdapter
import com.hao.easy.base.adapter.listener.OnItemClickListener
import com.hao.easy.base.common.DataListResult
import com.hao.easy.base.view.EmptyView
import com.hao.easy.base.view.RefreshLayout
import com.hao.easy.base.viewmodel.BaseListViewModel

/**
 * @author Yang Shihao
 * @date 2018/11/18
 */
abstract class BaseListFragment<VB : ViewBinding, D : BaseItem, VM : BaseListViewModel<D>, A : BasePagedAdapter<*, D>> :
    BaseFragment<VB, VM>(),
    OnItemClickListener<D> {

    private var refreshLayout: RefreshLayout? = null
    private var emptyView: EmptyView? = null

    @CallSuper
    override fun initView() {
        val recyclerView: RecyclerView = f(R.id.baseRecyclerView)!!
        val adapter = adapter()
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
            loadLiveData.observe(this@BaseListFragment, Observer {
                adapter().submitList(it)
            })
            refreshLiveData.observe(this@BaseListFragment, Observer {
                refreshFinished(it)
            })
            loadMoreLiveData.observe(this@BaseListFragment, Observer {
                loadMoreFinished(it)
            })
            if (uiParams.hasItemChange) {
                notifyItemLiveData.observe(this@BaseListFragment, Observer {
                    adapter().notifyItemChanged(it.first, it.second)
                })
            }
            if (uiParams.hasItemRemove) {
                removeItemLiveData.observe(this@BaseListFragment, Observer {
                    adapter().notifyItemRemoved(it)
                })
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

    abstract fun adapter(): A
}