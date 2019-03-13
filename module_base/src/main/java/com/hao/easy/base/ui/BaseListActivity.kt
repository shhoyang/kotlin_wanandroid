package com.hao.easy.base.ui

import android.arch.lifecycle.ViewModelProviders
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.RecyclerView
import android.view.View
import com.hao.easy.base.R
import com.hao.easy.base.adapter.BaseItem
import com.hao.easy.base.adapter.BasePagedAdapter
import com.hao.easy.base.common.RefreshResult
import com.hao.easy.base.extensions.init
import com.hao.easy.base.extensions.snack
import com.hao.easy.base.view.EmptyView
import com.hao.easy.base.viewmodel.BaseListViewModel
import java.lang.reflect.ParameterizedType

/**
 * @author Yang Shihao
 * @date 2018/11/18
 */
abstract class BaseListActivity<T : BaseItem, VM : BaseListViewModel<T>> : BaseActivity() {

    companion object {
        private const val TAG = "BaseListFragment"
    }

    val viewModel: VM by lazy {
        val parameterizedType = javaClass.genericSuperclass as ParameterizedType
        val cla = parameterizedType.actualTypeArguments[1] as Class<VM>
        ViewModelProviders.of(this).get(cla)
    }

    private var refreshLayout: SwipeRefreshLayout? = null
    private var emptyView: EmptyView? = null

    private lateinit var recyclerView: RecyclerView

    override fun getLayoutId() = R.layout.activity_base_list

    override fun initView() {
        refreshLayout = f(R.id.baseRefreshLayout)
        recyclerView = f(R.id.baseRecyclerView)!!
        emptyView = f(R.id.baseEmptyView)
        val adapter = adapter()
        adapter.itemClickListener = { view, item, position ->
            itemClicked(view, item, position)
        }
        recyclerView.init(adapter)
        refreshLayout?.setOnRefreshListener {
            viewModel.invalidate()
        }
    }

    override fun initData() {
        viewModel.observeDataObserver(this,
                { adapter().submitList(it) },
                { refreshFinished(it) },
                { loadMoreFinished(it) })

        viewModel.observeAdapterObserver(this,
                { position, payload ->
                    adapter().notifyItemChanged(position, payload)
                },
                {
                })
    }

    open fun itemClicked(view: View, item: T, position: Int) {

    }

    open fun refreshFinished(result: RefreshResult) {
        refreshLayout?.isRefreshing = false
        emptyView?.apply {
            when (result) {
                RefreshResult.SUCCEED -> state = EmptyView.Status.DISMISS
                RefreshResult.FAILED -> state = EmptyView.Status.LOAD_FAILED
                RefreshResult.NO_DATA -> state = EmptyView.Status.NO_DATA
                RefreshResult.NO_MORE -> {
                    state = EmptyView.Status.DISMISS
                    refreshLayout?.snack("全部加載完成")
                }
            }
        }
    }

    private fun loadMoreFinished(result: RefreshResult) {
        when (result) {
            RefreshResult.SUCCEED -> {
            }
            RefreshResult.FAILED -> {
            }
            RefreshResult.NO_MORE -> refreshLayout?.snack("全部加載完成")
        }
    }

    abstract fun adapter(): BasePagedAdapter<T>
}