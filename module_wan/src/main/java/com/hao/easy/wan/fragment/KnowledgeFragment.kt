package com.hao.easy.wan.fragment

import android.os.Bundle
import android.view.View
import com.hao.easy.wan.activity.KnowledgeArticleActivity
import com.hao.easy.wan.adapter.KnowledgeLeftAdapter
import com.hao.easy.wan.adapter.KnowledgeRightAdapter
import com.hao.easy.wan.databinding.WanFragmentKnowledgeBinding
import com.hao.easy.wan.model.Knowledge
import com.hao.easy.wan.viewmodel.KnowledgeViewModel
import com.hao.library.adapter.listener.OnItemClickListener
import com.hao.library.annotation.AndroidEntryPoint
import com.hao.library.annotation.Inject
import com.hao.library.extensions.init
import com.hao.library.ui.BaseFragment
import com.hao.library.ui.UIParams

/**
 * @author Yang Shihao
 */
@AndroidEntryPoint
class KnowledgeFragment : BaseFragment<WanFragmentKnowledgeBinding, KnowledgeViewModel>() {

    @Inject
    lateinit var leftAdapter: KnowledgeLeftAdapter

    @Inject
    lateinit var rightAdapter: KnowledgeRightAdapter

    override fun prepare(uiParams: UIParams, bundle: Bundle?) {
        uiParams.isLazy = true
    }

    override fun initView() {
        rightAdapter.setOnItemClickListener(object : OnItemClickListener<Knowledge> {
            override fun itemClicked(
                view: View,
                item: Knowledge,
                position: Int
            ) {
                act { KnowledgeArticleActivity.start(it, item) }
            }
        })

        viewBinding {
            leftAdapter.setOnItemClickListener(object : OnItemClickListener<Knowledge> {
                override fun itemClicked(
                    view: View,
                    item: Knowledge,
                    position: Int
                ) {
                    rightAdapter.resetData(item.children)
                    rvRight.smoothScrollToPosition(0)
                }
            })
            rvLeft.init(leftAdapter)
            rvRight.init(rightAdapter)
        }
    }

    override fun initData() {
        viewModel {
            liveData.observe(this@KnowledgeFragment){
                if (it == null || it.isEmpty()) {
                    viewBinding { baseEmptyView.noData() }
                } else {
                    viewBinding { baseEmptyView.dismiss() }
                    leftAdapter.resetData(it)
                    rightAdapter.resetData(it[0].children)
                }
            }
            loadData()
        }
    }
}