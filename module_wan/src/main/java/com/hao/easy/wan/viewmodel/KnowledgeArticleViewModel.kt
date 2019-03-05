package com.hao.easy.wan.viewmodel

import com.hao.easy.base.extensions.io_main
import com.hao.easy.base.extensions.subscribeBy
import com.hao.easy.wan.model.Article
import com.hao.easy.wan.repository.Api

class KnowledgeArticleViewModel : BaseArticleViewModel() {

    override fun pageSize() = 6

    var typeId: Int = 408

    override fun loadData(page: Int, onResponse: (ArrayList<Article>?) -> Unit) {
        Api.getKnowledgeArticle(page - 1, typeId).io_main().subscribeBy({
            onResponse(it?.datas)
        }, {
            onResponse(null)
        }).add()
    }
}

