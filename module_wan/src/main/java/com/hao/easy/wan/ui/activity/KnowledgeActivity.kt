package com.hao.easy.wan.ui.activity

import com.alibaba.android.arouter.facade.annotation.Route
import com.hao.easy.base.ui.BaseListActivity
import com.hao.easy.wan.model.Knowledge
import com.hao.easy.wan.ui.adapter.KnowledgeAdapter
import com.hao.easy.wan.viewmodel.KnowledgeViewModel

@Route(path = "/wechat/KnowledgeActivity")
class KnowledgeActivity : BaseListActivity<Knowledge, KnowledgeViewModel>() {

    override fun adapter() = KnowledgeAdapter()

    override fun initView() {
        title = "知识体系"
        super.initView()
    }
}
