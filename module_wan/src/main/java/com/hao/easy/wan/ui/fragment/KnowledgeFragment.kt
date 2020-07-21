package com.hao.easy.wan.ui.fragment

import android.view.View
import com.hao.easy.base.ui.BaseListFragment
import com.hao.easy.base.utils.DisplayUtils
import com.hao.easy.wan.R
import com.hao.easy.wan.model.Knowledge
import com.hao.easy.wan.ui.activity.KnowledgeArticleActivity
import com.hao.easy.wan.ui.adapter.KnowledgeAdapter
import com.hao.easy.wan.viewmodel.KnowledgeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.status_bar_holder.*
import javax.inject.Inject

/**
 * @author Yang Shihao
 * @Date 2020/7/20
 */
@AndroidEntryPoint
class KnowledgeFragment : BaseListFragment<Knowledge, KnowledgeViewModel>() {

    @Inject
    lateinit var adapter: KnowledgeAdapter

    override fun getLayoutId() = R.layout.wan_fragment_knowledge

    override fun adapter() = adapter

    override fun initView() {
        val layoutParams = statusBarHolder.layoutParams
        layoutParams.height = DisplayUtils.getStatusBarHeight(context!!)
        statusBarHolder.layoutParams = layoutParams
        super.initView()
    }

    override fun itemClicked(view: View, item: Knowledge, position: Int) {
        super.itemClicked(view, item, position)
        context?.apply { KnowledgeArticleActivity.start(this, item) }
    }
}