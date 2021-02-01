package com.hao.easy.user.viewmodel

import androidx.lifecycle.MutableLiveData
import com.hao.library.viewmodel.BaseViewModel
import com.hao.easy.user.repository.Api
import com.hao.library.http.subscribeBy

/**
 * @author Yang Shihao
 */
class UserViewModel : BaseViewModel() {

    val logoutLiveData = MutableLiveData<String?>()

    fun logout() {
        Api.logout().subscribeBy({
            logoutLiveData.value = null
        }).add()
    }
}