package com.hao.easy.user.ui.activity

import android.content.Intent
import com.alibaba.android.arouter.facade.annotation.Route
import com.hao.easy.base.ui.BaseActivity
import com.hao.easy.base.ui.UIParams
import com.hao.easy.user.R

@Route(path = "/user/LoginActivity")
class LoginActivity : BaseActivity() {

    override fun prepare(uiParams: UIParams, intent: Intent?) {
        uiParams.showToolbar = false
    }

    override fun getLayoutId() = R.layout.user_activity_login

    override fun initView() {
    }
}
