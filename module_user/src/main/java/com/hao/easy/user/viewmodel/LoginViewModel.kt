package com.hao.easy.user.viewmodel

import androidx.lifecycle.MutableLiveData
import com.hao.easy.base.Config
import com.hao.easy.user.repository.Api
import com.hao.library.http.subscribeBy
import com.hao.library.model.ParamsBuilder
import com.hao.library.viewmodel.BaseViewModel

/**
 * @author Yang Shihao
 */
class LoginViewModel : BaseViewModel() {

    val loginLiveData = MutableLiveData<String?>()

    fun login(username: String, password: String) {
        val params = ParamsBuilder()
            .put("username", username)
            .put("password", password)
            .build()
        Api.login(params).subscribeBy({
            Config.logged(it!!)
            loginLiveData.value = null
        }).add()
    }
}