package com.hao.easy.user.viewmodel

import android.arch.lifecycle.MutableLiveData
import com.hao.easy.base.Config
import com.hao.easy.base.extensions.io_main
import com.hao.easy.base.extensions.subscribeBy
import com.hao.easy.base.user.User
import com.hao.easy.base.viewmodel.BaseViewModel
import com.hao.easy.user.repository.Api

/**
 * @author Yang Shihao
 * @date 2018/11/25
 */
class UserViewModel : BaseViewModel() {

    var loginLiveData = MutableLiveData<User>()

    var logoutLiveData = MutableLiveData<String>()

    override fun onResume() {
        loginLiveData.value = Config.user
    }

    fun logout() {
        Api.logout().io_main().subscribeBy({
            logoutLiveData.value = null
        }, {
            logoutLiveData.value = null
        }).add()
    }
}