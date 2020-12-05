package com.hao.easy.base.ui

import android.view.View
import androidx.annotation.CallSuper
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hao.easy.base.view.RefreshLayout
import com.hao.easy.base.R
import com.hao.easy.base.adapter.BaseItem
import com.hao.easy.base.adapter.BasePagedAdapter
import com.hao.easy.base.adapter.OnItemClickListener
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
        viewModel.loadLiveData.observe(this, Observer {
            adapter().submitList(it)
        })
        viewModel.refreshLiveData.observe(this, Observer {
            refreshFinished(it)
        })
        viewModel.loadMoreLiveData.observe(this, Observer {
            loadMoreFinished(it)
        })
        if (uiParams.hasItemChange) {
            viewModel.notifyItemLiveData.observe(this, Observer {
                adapter().notifyItemChanged(it.first, it.second)
            })
        }
        if (uiParams.hasItemRemove) {
            viewModel.removeItemLiveData.observe(this, Observer {
                adapter().notifyItemRemoved(it)
            })
        }
    }

    open fun processRecyclerView(recyclerView: RecyclerView, emptyView: EmptyView?) {
        recyclerView.layoutManager = LinearLayoutManager(recyclerView.context)
    }

    override fun itemClicked(view: View, item: T, position: Int) {

    }

    @CallSuper
    open fun refreshFinished(result: DataListResult) {
        refreshLayout?.isRefreshing = false
        emptyView?.apply {
            state = when (result) {
                DataListResult.SUCCEED -> EmptyView.Status.DISMISS
                DataListResult.FAILED -> EmptyView.Status.LOAD_FAILED
                DataListResult.NO_DATA -> EmptyView.Status.NO_DATA
                DataListResult.NO_MORE -> EmptyView.Status.DISMISS
            }
        }
    }

    open fun loadMoreFinished(result: DataListResult) {
        when (result) {
            DataListResult.SUCCEED -> {
            }
            DataListResult.FAILED -> {
            }
            DataListResult.NO_MORE -> {
            }
        }
    }

    abstract fun adapter(): BasePagedAdapter<T>
}