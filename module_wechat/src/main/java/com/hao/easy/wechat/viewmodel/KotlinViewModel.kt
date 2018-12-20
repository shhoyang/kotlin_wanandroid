package com.hao.easy.wechat.viewmodel

import com.hao.easy.base.extensions.main
import com.hao.easy.base.extensions.subscribeBy
import com.hao.easy.wechat.model.Article
import com.hao.easy.wechat.repository.Api

class KotlinViewModel : BaseArticleViewModel() {

    private var key = "kotlin"

    override fun pageSize() = 10

    override fun loadData(page: Int, onResponse: (ArrayList<Article>?) -> Unit) {
        Api.search(page - 1, key).main().subscribeBy({
            onResponse(it?.datas)
        }, {
            onResponse(null)
        }).add()
    }
}