package com.hao.easy.wan.ui.activity

import com.alibaba.android.arouter.facade.annotation.Route
import com.hao.easy.base.ui.BaseListActivity
import com.hao.easy.wan.di.component
import com.hao.easy.wan.model.Knowledge
import com.hao.easy.wan.ui.adapter.KnowledgeAdapter
import com.hao.easy.wan.viewmodel.KnowledgeViewModel
import javax.inject.Inject

@Route(path = "/wechat/KnowledgeActivity")
class KnowledgeActivity : BaseListActivity<Knowledge,KnowledgeViewModel>() {

    @Inject
    lateinit var adapter:KnowledgeAdapter

    override fun adapter() = adapter

    override fun initView() {
        title = "知识体系"
        super.initView()
    }

    override fun initInject() {
        component().inject(this)
    }
}
