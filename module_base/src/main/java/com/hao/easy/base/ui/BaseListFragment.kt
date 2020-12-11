package com.hao.easy.base.ui

import android.view.View
import androidx.annotation.CallSuper
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hao.easy.base.view.RefreshLayout
import com.hao.easy.base.R
import com.hao.easy.base.adapter.BaseItem
import com.hao.easy.base.adapter.BasePagedAdapter
import com.hao.easy.base.adapter.ViewHolder
import com.hao.easy.base.adapter.listener.OnItemClickListener
import com.hao.easy.base.common.DataListResult
import com.hao.easy.base.view.EmptyView
import com.hao.easy.base.viewmodel.BaseListViewModel
import java.lang.reflect.ParameterizedType

/**
 * @author Yang Shihao
 * @date 2018/11/18
 */
abstract class BaseListFragment<T : BaseItem, VM : BaseListViewModel<T>> : BaseFragment(),
    OnItemClickListener<T> {

    val viewModel: VM by lazy {
        val parameterizedType = javaClass.genericSuperclass as ParameterizedType
        val cla = parameterizedType.actualTypeArguments[1] as Class<VM>
        ViewModelProvider(this).get(cla)
    }

    private var refreshLayout: RefreshLayout? = null
    private var emptyView: EmptyView? = null

    private lateinit var recyclerView: RecyclerView

    override fun getLayoutId() = R.layout.activity_base_list

    @CallSuper
    override fun initView() {
        refreshLayout = f(R.id.baseRefreshLayout)
        recyclerView = f(R.id.baseRecyclerView)!!
        emptyView = f(R.id.baseEmptyView)
        val adapter = adapter()
        adapter.setOnItemClickListener(this)
        processRecyclerView(recyclerView, emptyView)
        recyclerView.adapter = adapter
        refreshLayout?.setOnRefreshListener {
            viewModel.refresh()
        }
    }

    @CallSuper
    override fun initData() {
        viewModel.loadLiveData.observe(this) {
            adapter().submitList(it)
        }
        viewModel.refreshLiveData.observe(this) {
            refreshFinished(it)
        }
        viewModel.loadMoreLiveData.observe(this) {
            loadMoreFinished(it)
        }
        if (uiParams.hasItemChange) {
            viewModel.notifyItemLiveData.observe(this) {
                adapter().notifyItemChanged(it.first, it.second)
            }
        }
        if (uiParams.hasItemRemove) {
            viewModel.removeItemLiveData.observe(this) {
                adapter().notifyItemRemoved(it)
            }
        }
    }

    open fun processRecyclerView(recyclerView: RecyclerView, emptyView: EmptyView?) {
        recyclerView.layoutManager = LinearLayoutManager(recyclerView.context)
    }

    override fun itemClicked(holder: ViewHolder, view: View, item: T, position: Int) {

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

    abstract fun adapter(): BasePagedAdapter<T>
}