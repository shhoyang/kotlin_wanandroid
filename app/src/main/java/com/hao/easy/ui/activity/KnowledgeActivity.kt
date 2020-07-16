package com.hao.easy.ui.activity

import com.hao.easy.base.ui.BaseListActivity
import com.hao.easy.model.Knowledge
import com.hao.easy.ui.adapter.KnowledgeAdapter
import com.hao.easy.viewmodel.KnowledgeViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class KnowledgeActivity : BaseListActivity<Knowledge,KnowledgeViewModel>() {

    @Inject
    lateinit var adapter:KnowledgeAdapter

    override fun adapter() = adapter

    override fun initView() {
        title = "知识体系"
        super.initView()
    }
}
