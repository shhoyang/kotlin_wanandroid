package com.hao.easy.wan.viewmodel

import com.hao.easy.base.Config
import com.hao.easy.base.extensions.main
import com.hao.easy.base.extensions.subscribeBy
import com.hao.easy.wan.model.Article
import com.hao.easy.wan.repository.Api
import kotlin.properties.Delegates

class WechatArticleViewModel : BaseArticleViewModel() {

    var authorId: Int = 409

    private var refresh by Delegates.observable(Config.refresh) { _, old, new ->
        if (old != new) {
            invalidate()
        }
    }

    override fun onResume() {
        super.onResume()
        refresh = Config.refresh
    }

    override fun loadData(page: Int, onResponse: (ArrayList<Article>?) -> Unit) {
        Api.getWechatArticles(authorId, page).main().subscribeBy({
            onResponse(it?.datas)
        }, {
            onResponse(null)
        }).add()
    }
}