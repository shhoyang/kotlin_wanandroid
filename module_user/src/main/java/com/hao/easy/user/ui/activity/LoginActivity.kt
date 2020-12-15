package com.hao.easy.user.ui.activity

import com.alibaba.android.arouter.facade.annotation.Route
import com.hao.easy.base.ui.BaseActivity
import com.hao.easy.user.databinding.UserActivityLoginBinding

@Route(path = "/user/LoginActivity")
class LoginActivity : BaseActivity<UserActivityLoginBinding, Nothing>() {

    override fun getVB() = UserActivityLoginBinding.inflate(layoutInflater)

    override fun getVM(): Nothing? = null

    override fun initView() {
    }

    override fun initData() {
    }
}
