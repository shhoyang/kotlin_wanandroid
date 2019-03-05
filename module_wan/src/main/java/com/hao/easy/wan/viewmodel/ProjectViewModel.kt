package com.hao.easy.wan.viewmodel

import android.arch.lifecycle.MutableLiveData
import com.hao.easy.base.Config
import com.hao.easy.base.extensions.io_main
import com.hao.easy.base.extensions.main
import com.hao.easy.base.extensions.subscribeBy
import com.hao.easy.wan.model.Article
import com.hao.easy.wan.model.ProjectType
import com.hao.easy.wan.repository.Api
import kotlin.properties.Delegates

class ProjectViewModel : BaseArticleViewModel() {

    override fun pageSize() = 6

    val typeLiveData = MutableLiveData<ArrayList<ProjectType>>()

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
        Api.getNewProjectArticles(page - 1).main().subscribeBy({
            onResponse(it?.datas)
        }, {
            onResponse(null)
        }).add()
    }

    override fun refresh() {
        Api.getProjectType().io_main().subscribeBy({
            if (it != null && !it.isEmpty()) {
                typeLiveData.value = it
            }
        }, {

        }).add()
    }
}