package com.hao.easy.wan.viewmodel

import androidx.lifecycle.MutableLiveData
import com.hao.easy.base.Config
import com.hao.easy.wan.model.Article
import com.hao.easy.wan.repository.Api
import com.hao.library.http.subscribeBy
import com.hao.library.viewmodel.BaseListViewModel

/**
 * @author Yang Shihao
 */
class FavoritesViewModel : BaseListViewModel<Article>() {

    val deleteResult = MutableLiveData<Boolean>()

    override fun loadData(page: Int, onResponse: (ArrayList<Article>?) -> Unit) {
        Api.getMyFav(page - 1).subscribeBy({
            onResponse(it?.datas)
        }, {
            onResponse(null)
        }).add()
    }

    fun cancelCollect(item: Article, position: Int) {
        Api.cancelCollectFromFav(item.id, item.originId).subscribeBy({
            deleteResult.value = true
            refresh()
            Config.refresh()
        }).add()
    }
}