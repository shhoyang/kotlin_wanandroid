package com.hao.easy.wechat.viewmodel

import com.hao.easy.base.Config
import com.hao.easy.base.extensions.io_main
import com.hao.easy.base.extensions.subscribeBy
import com.hao.easy.base.viewmodel.BaseListViewModel
import com.hao.easy.wechat.Router
import com.hao.easy.wechat.model.Article
import com.hao.easy.wechat.repository.Api

abstract class BaseArticleViewModel : BaseListViewModel<Article>() {

    fun collect(item: Article, position: Int) {
        if (!Config.isLogin) {
            Router.startLogin()
            return
        }
        Api.collect(item.id).io_main().subscribeBy({
            item.collect = true
            notifyItem(position, "fav")
        }, {

        }).add()
    }

    open fun cancelCollect(item: Article, position: Int) {
        if (!Config.isLogin) {
            Router.startLogin()
            return
        }
        Api.cancelCollect(item.id).io_main().subscribeBy({
            item.collect = false
            notifyItem(position, "fav")
        }, {

        }).add()
    }
}