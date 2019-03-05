package com.hao.easy.wan.viewmodel

import com.hao.easy.base.extensions.main
import com.hao.easy.base.extensions.subscribeBy
import com.hao.easy.wan.model.Article
import com.hao.easy.wan.repository.Api

class ProjectArticleViewModel : BaseArticleViewModel() {

    override fun pageSize() = 6

    var typeId: Int = 0

    override fun loadData(page: Int, onResponse: (ArrayList<Article>?) -> Unit) {

        Api.getProjectArticles(page - 1, typeId).main().subscribeBy({
            onResponse(it?.datas)
        }, {
            onResponse(null)
        }).add()
    }
}