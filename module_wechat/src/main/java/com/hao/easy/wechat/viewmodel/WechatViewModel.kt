package com.hao.easy.wechat.viewmodel

import android.arch.lifecycle.MutableLiveData
import com.hao.easy.base.common.FragmentCreator
import com.hao.easy.base.extensions.io_main
import com.hao.easy.base.extensions.subscribeBy
import com.hao.easy.base.viewmodel.BaseViewModel
import com.hao.easy.wechat.repository.Api
import com.hao.easy.wechat.ui.fragment.WechatArticleFragment

class WechatViewModel : BaseViewModel() {

    var adLiveData = MutableLiveData<List<String>>()

    val authorsLiveData: MutableLiveData<List<Pair<String, FragmentCreator>>> = MutableLiveData()

    fun initData() {
        Api.getAd().io_main().subscribeBy {
            adLiveData.value = it?.map { ad -> ad.imagePath }
        }.add()

        Api.getAuthors().io_main().subscribeBy {
            var fragments = it?.map { author ->
                Pair<String, FragmentCreator>(author.name, object : FragmentCreator {
                    override fun create() = WechatArticleFragment.instance(author.id)
                })
            }
            authorsLiveData.value = fragments
        }.add()
    }
}