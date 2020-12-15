package com.hao.easy.wan.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.hao.easy.base.adapter.listener.OnItemClickListener
import com.hao.easy.base.extensions.init
import com.hao.easy.base.ui.BaseFragment
import com.hao.easy.base.ui.UIParams
import com.hao.easy.wan.databinding.WanFragmentKnowledgeBinding
import com.hao.easy.wan.model.Knowledge
import com.hao.easy.wan.ui.activity.KnowledgeArticleActivity
import com.hao.easy.wan.ui.adapter.KnowledgeLeftAdapter
import com.hao.easy.wan.ui.adapter.KnowledgeRightAdapter
import com.hao.easy.wan.viewmodel.KnowledgeViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

/**
 * @author Yang Shihao
 * @Date 2020/7/20
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

    override fun getVB() = WanFragmentKnowledgeBinding.inflate(layoutInflater)

    override fun getVM() = ViewModelProvider(this).get(KnowledgeViewModel::class.java)

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
            liveData.observe(this@KnowledgeFragment, Observer {
                if (it == null || it.isEmpty()) {
                    viewBinding { baseEmptyView.noData() }
                } else {
                    viewBinding { baseEmptyView.dismiss() }
                    leftAdapter.resetData(it)
                    rightAdapter.resetData(it[0].children)
                }
            })
            loadData()
        }
    }
}