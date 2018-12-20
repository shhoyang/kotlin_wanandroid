package com.hao.easy.wechat.viewmodel

import com.hao.easy.base.extensions.main
import com.hao.easy.base.extensions.subscribeBy
import com.hao.easy.wechat.model.Article
import com.hao.easy.wechat.repository.Api

class FlutterViewModel : BaseArticleViewModel() {

    private var key = "flutter"

    override fun pageSize() = 10

    override fun loadData(page: Int, onResponse: (ArrayList<Article>?) -> Unit) {
        Api.search(page - 1, key).main().subscribeBy({
            onResponse(it?.datas)
        }, {
            onResponse(null)
        }).add()
    }
}