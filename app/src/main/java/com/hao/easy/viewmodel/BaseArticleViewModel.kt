package com.hao.easy.viewmodel

import com.hao.easy.aop.CheckLogin
import com.hao.easy.aop.LogMethod
import com.hao.easy.base.extensions.io_main
import com.hao.easy.base.extensions.subscribeBy
import com.hao.easy.base.viewmodel.BaseListViewModel
import com.hao.easy.model.Article
import com.hao.easy.repository.Api

abstract class BaseArticleViewModel : BaseListViewModel<Article>() {

    @LogMethod
    @CheckLogin
    fun collect(item: Article, position: Int) {
        if (item.collect) {
            doCancelCollect(item, position)
        } else {
            doCollect(item, position)
        }
    }

    private fun doCollect(item: Article, position: Int) {
        Api.collect(item.id).io_main().subscribeBy({
            item.collect = true
            notifyItem(position, "fav")
        }, {

        }).add()
    }

    open fun doCancelCollect(item: Article, position: Int) {
        Api.cancelCollect(item.id).io_main().subscribeBy({
            item.collect = false
            notifyItem(position, "fav")
        }, {

        }).add()
    }
}