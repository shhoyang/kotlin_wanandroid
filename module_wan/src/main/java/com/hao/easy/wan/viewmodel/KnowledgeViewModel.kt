package com.hao.easy.wan.viewmodel

import com.hao.easy.base.extensions.io_main
import com.hao.easy.base.extensions.subscribeBy
import com.hao.easy.base.viewmodel.BaseListViewModel
import com.hao.easy.wan.model.Knowledge
import com.hao.easy.wan.repository.Api

class KnowledgeViewModel : BaseListViewModel<Knowledge>() {

    override fun pageSize() = Int.MAX_VALUE

    override fun loadData(page: Int, onResponse: (ArrayList<Knowledge>?) -> Unit) {
        Api.getKnowledge().io_main().subscribeBy({
            onResponse(it)
        }, {
            onResponse(null)
        }).add()
    }
}

