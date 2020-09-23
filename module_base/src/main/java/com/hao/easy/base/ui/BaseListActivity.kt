package com.hao.easy.base.ui

import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.hao.easy.base.R
import com.hao.easy.base.adapter.BaseItem
import com.hao.easy.base.adapter.BasePagedAdapter
import com.hao.easy.base.adapter.OnItemClickListener
import com.hao.easy.base.common.RefreshResult
import com.hao.easy.base.extensions.init
import com.hao.easy.base.view.EmptyView
import com.hao.easy.base.view.RefreshLayout
import com.hao.easy.base.viewmodel.BaseListViewModel
import java.lang.reflect.ParameterizedType

/**
 * @author Yang Shihao
 * @date 2018/11/18
 */
abstract class BaseListActivity<T : BaseItem, VM : BaseListViewModel<T>> : BaseActivity(),
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

    override fun initView() {
        refreshLayout = findViewById(R.id.baseRefreshLayout)
        recyclerView = findViewById(R.id.baseRecyclerView)!!
        emptyView = findViewById(R.id.baseEmptyView)
        val adapter = adapter()
        adapter.itemClickListener = this
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

    override fun itemClicked(view: View, item: T, position: Int) {

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
                    toast(R.string.base_t_no_more)
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
            RefreshResult.NO_MORE -> toast(R.string.base_t_no_more)
        }
    }

    abstract fun adapter(): BasePagedAdapter<T>

}