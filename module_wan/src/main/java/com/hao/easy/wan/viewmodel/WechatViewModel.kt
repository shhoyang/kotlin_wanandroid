package com.hao.easy.wan.viewmodel

import androidx.lifecycle.MutableLiveData
import com.hao.easy.base.extensions.io_main
import com.hao.easy.base.extensions.subscribeBy
import com.hao.easy.base.viewmodel.BaseViewModel
import com.hao.easy.wan.model.Author
import com.hao.easy.wan.repository.Api

class WechatViewModel : BaseViewModel() {

    var adLiveData = MutableLiveData<List<String>>()

    val authorsLiveData: MutableLiveData<List<Author>> = MutableLiveData()

    fun initData() {
        Api.getAd().io_main().subscribeBy {
            adLiveData.value = it?.map { ad -> ad.imagePath }
        }.add()

        Api.getAuthors().io_main().subscribeBy {

            if (it != null && it.isNotEmpty()) {
                authorsLiveData.value = it
            }
        }.add()
    }
}