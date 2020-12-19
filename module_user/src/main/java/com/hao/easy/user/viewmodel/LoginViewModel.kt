package com.hao.easy.user.viewmodel

import androidx.lifecycle.MutableLiveData
import com.hao.easy.base.Config
import com.hao.easy.base.extensions.subscribeBy
import com.hao.easy.base.model.ParamsBuilder
import com.hao.easy.base.viewmodel.BaseViewModel
import com.hao.easy.user.repository.Api

/**
 * @author Yang Shihao
 * @date 2018/11/25
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