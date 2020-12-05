package com.hao.easy.user.viewmodel

import androidx.lifecycle.MutableLiveData
import com.hao.easy.base.extensions.subscribeBy
import com.hao.easy.base.viewmodel.BaseViewModel
import com.hao.easy.user.repository.Api

/**
 * @author Yang Shihao
 * @date 2018/11/25
 */
class UserViewModel : BaseViewModel() {

    val logoutLiveData = MutableLiveData<String?>()

    fun logout() {
        Api.logout().subscribeBy({
            logoutLiveData.value = null
        }).add()
    }
}