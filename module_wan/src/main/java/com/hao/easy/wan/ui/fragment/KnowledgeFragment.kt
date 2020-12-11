package com.hao.easy.wan.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.hao.easy.base.adapter.ViewHolder
import com.hao.easy.base.adapter.listener.OnItemClickListener
import com.hao.easy.base.extensions.init
import com.hao.easy.base.ui.BaseFragment
import com.hao.easy.base.ui.UIParams
import com.hao.easy.wan.R
import com.hao.easy.wan.model.Knowledge
import com.hao.easy.wan.ui.activity.KnowledgeArticleActivity
import com.hao.easy.wan.ui.adapter.KnowledgeLeftAdapter
import com.hao.easy.wan.ui.adapter.KnowledgeRightAdapter
import com.hao.easy.wan.viewmodel.KnowledgeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.wan_fragment_knowledge.*
import javax.inject.Inject

/**
 * @author Yang Shihao
 * @Date 2020/7/20
 */
@AndroidEntryPoint
class KnowledgeFragment : BaseFragment() {

    @Inject
    lateinit var leftAdapter: KnowledgeLeftAdapter

    @Inject
    lateinit var rightAdapter: KnowledgeRightAdapter

    private val viewModel by lazy {
        ViewModelProvider(this).get(KnowledgeViewModel::class.java)
    }

    override fun prepare(uiParams: UIParams, bundle: Bundle?) {
        uiParams.isLazy = true
    }

    override fun getLayoutId() = R.layout.wan_fragment_knowledge

    override fun initView() {
        leftAdapter.setOnItemClickListener(object : OnItemClickListener<Knowledge> {
            override fun itemClicked(
                holder: ViewHolder,
                view: View,
                item: Knowledge,
                position: Int
            ) {
                rightAdapter.resetData(item.children)
                rvRight.smoothScrollToPosition(0)
            }
        })
        rvLeft.init(leftAdapter)

        rightAdapter.setOnItemClickListener(object : OnItemClickListener<Knowledge> {
            override fun itemClicked(
                holder: ViewHolder,
                view: View,
                item: Knowledge,
                position: Int
            ) {
                act { KnowledgeArticleActivity.start(it, item) }
            }
        })
        rvRight.init(rightAdapter)
    }

    override fun initData() {
        viewModel.liveData.observe(this) {
            if (it == null || it.isEmpty()) {
                baseEmptyView.noData()
            } else {
                baseEmptyView.dismiss()
                leftAdapter.resetData(it)
                rightAdapter.resetData(it[0].children)
            }
        }
        viewModel.loadData()
    }
}