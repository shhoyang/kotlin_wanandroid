package com.hao.easy.wan.viewmodel

import com.hao.easy.base.Config
import com.hao.easy.wan.model.Article
import com.hao.easy.wan.repository.Api
import com.hao.library.http.subscribeBy
import kotlin.properties.Delegates

class SearchResultViewModel : BaseArticleViewModel() {

    var hotWord = "面试"

    private var refresh by Delegates.observable(Config.refresh) { _, old, new ->
        if (old != new) {
            refresh()
        }
    }

    override fun onResume() {
        super.onResume()
        refresh = Config.refresh
    }

    override fun pageSize() = 10

    override fun loadData(page: Int, onResponse: (ArrayList<Article>?) -> Unit) {
        Api.search(page - 1, hotWord).subscribeBy({
            onResponse(it?.datas)
        }, {
            onResponse(null)
        }).add()
    }
}