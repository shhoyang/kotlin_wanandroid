package com.hao.easy.wan.viewmodel

import com.hao.easy.base.Config
import com.hao.easy.base.extensions.io_main
import com.hao.easy.base.extensions.main
import com.hao.easy.base.extensions.subscribeBy
import com.hao.easy.base.viewmodel.BaseListViewModel
import com.hao.easy.wan.Router
import com.hao.easy.wan.model.Article
import com.hao.easy.wan.repository.Api

/**
 * @author Yang Shihao
 * @date 2018/12/3
 */
class FavViewModel : BaseListViewModel<Article>() {

    override fun loadData(page: Int, onResponse: (ArrayList<Article>?) -> Unit) {
        Api.getMyFav(page - 1).main().subscribeBy({
            onResponse(it?.datas)
        }, {
            onResponse(null)
        }).add()
    }

    fun cancelCollect(item: Article, position: Int) {
        if (!Config.isLogin) {
            Router.startLogin()
            return
        }
        Api.cancelCollectFromFav(item.id, item.originId).io_main().subscribeBy({
            invalidate()
            Config.refresh()
        }, {

        }).add()
    }
}