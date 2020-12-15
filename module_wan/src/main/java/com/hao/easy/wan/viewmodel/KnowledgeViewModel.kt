package com.hao.easy.wan.viewmodel

import androidx.lifecycle.MutableLiveData
import com.hao.easy.base.extensions.subscribeBy
import com.hao.easy.base.viewmodel.BaseViewModel
import com.hao.easy.wan.model.Knowledge
import com.hao.easy.wan.repository.Api

class KnowledgeViewModel : BaseViewModel() {

    val liveData = MutableLiveData<List<Knowledge>>()

    fun loadData() {
        Api.getKnowledge().subscribeBy({
            liveData.value =
                it?.filter { c -> c.children != null && c.children?.isNotEmpty() == true }
        }, {
            liveData.value = null
        }).add()
    }
}

