package com.hao.easy.wechat.ui.activity

import com.alibaba.android.arouter.facade.annotation.Route
import com.hao.easy.base.ui.BaseListActivity
import com.hao.easy.wechat.di.component
import com.hao.easy.wechat.model.Knowledge
import com.hao.easy.wechat.ui.adapter.KnowledgeAdapter
import com.hao.easy.wechat.viewmodel.KnowledgeViewModel
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
