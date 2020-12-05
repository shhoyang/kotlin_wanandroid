package com.hao.easy.wan.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.hao.easy.base.Config
import com.hao.easy.base.extensions.subscribeBy
import com.hao.easy.wan.model.Article
import com.hao.easy.wan.model.ProjectType
import com.hao.easy.wan.repository.Api
import kotlin.properties.Delegates

class ProjectViewModel : BaseArticleViewModel() {

    val typeLiveData = MutableLiveData<ArrayList<ArrayList<ProjectType>>?>()

    private var refresh by Delegates.observable(Config.refresh) { _, old, new ->
        if (old != new) {
            refresh()
        }
    }

    override fun pageSize() = 6

    override fun onResume() {
        super.onResume()
        refresh = Config.refresh
    }

    override fun loadData(page: Int, onResponse: (ArrayList<Article>?) -> Unit) {
        Api.getNewProjectArticles(page - 1).subscribeBy({
            onResponse(it?.datas)
        }, {
            onResponse(null)
        }).add()
    }

    override fun refresh(callback: PageKeyedDataSource.LoadInitialCallback<Int, Article>) {
        super.refresh(callback)
        Api.getProjectType().subscribeBy({
            if (it != null && it.isNotEmpty()) {
                val list = ArrayList<ArrayList<ProjectType>>()
                val size = it.size
                if (size <= 8) {
                    list.add(it)
                    typeLiveData.value = list
                } else {
                    var start = 0
                    var end = 0
                    while (start < size) {
                        if (start + 8 < size) {
                            end += 8
                        } else {
                            end = size
                        }
                        val temp = ArrayList<ProjectType>()
                        temp.addAll(it.subList(start, end))
                        list.add(temp)
                        start += 8
                    }
                    typeLiveData.value = list
                }
            }
        }, {
            typeLiveData.value = null
        }).add()
    }
}