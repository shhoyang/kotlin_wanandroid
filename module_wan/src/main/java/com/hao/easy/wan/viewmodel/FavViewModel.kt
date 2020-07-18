package com.hao.easy.wan.viewmodel

import androidx.lifecycle.MutableLiveData
import com.hao.easy.base.Config
import com.hao.easy.base.extensions.io_main
import com.hao.easy.base.extensions.main
import com.hao.easy.base.extensions.subscribeBy
import com.hao.easy.base.viewmodel.BaseListViewModel
import com.hao.easy.wan.model.Article
import com.hao.easy.wan.repository.Api

/**
 * @author Yang Shihao
 * @date 2018/12/3
 */
class FavViewModel : BaseListViewModel<Article>() {

    var deleteResult = MutableLiveData<Boolean>()

    override fun loadData(page: Int, onResponse: (ArrayList<Article>?) -> Unit) {
        Api.getMyFav(page - 1).main().subscribeBy({
            onResponse(it?.datas)
        }, {
            onResponse(null)
        }).add()
    }

    fun cancelCollect(item: Article, position: Int) {
        Api.cancelCollectFromFav(item.id, item.originId).io_main().subscribeBy({
            deleteResult.value = true
            invalidate()
            Config.refresh()
        }, {

        }).add()
    }
}