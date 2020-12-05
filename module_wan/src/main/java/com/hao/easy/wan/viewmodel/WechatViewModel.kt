package com.hao.easy.wan.viewmodel

import androidx.lifecycle.MutableLiveData
import com.hao.easy.base.extensions.subscribeBy
import com.hao.easy.base.viewmodel.BaseViewModel
import com.hao.easy.wan.model.Ad
import com.hao.easy.wan.model.Author
import com.hao.easy.wan.repository.Api

class WechatViewModel : BaseViewModel() {

    val adLiveData = MutableLiveData<ArrayList<Ad>>()

    val authorsLiveData: MutableLiveData<List<Author>> = MutableLiveData()

    fun initData() {
        Api.getAd().subscribeBy({
            if (it != null) {
                adLiveData.value = it
            }
        }).add()

        Api.getAuthors().subscribeBy({
            if (it != null && it.isNotEmpty()) {
                authorsLiveData.value = it
            }
        }).add()
    }
}