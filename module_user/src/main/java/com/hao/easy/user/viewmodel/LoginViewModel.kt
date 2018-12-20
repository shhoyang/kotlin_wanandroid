package com.hao.easy.user.viewmodel

import android.arch.lifecycle.MutableLiveData
import com.hao.easy.base.Config
import com.hao.easy.base.extensions.io_main
import com.hao.easy.base.extensions.subscribeBy
import com.hao.easy.base.viewmodel.BaseViewModel
import com.hao.easy.user.repository.Api

/**
 * @author Yang Shihao
 * @date 2018/11/25
 */
class LoginViewModel : BaseViewModel() {

    var loginLiveData = MutableLiveData<String>()

    fun login(username: String, password: String) {
        Api.login(username, password).io_main().subscribeBy({
            Config.logged(it!!)
            loginLiveData.value = null
        }, {
            loginLiveData.value = it
        }).add()
    }
}