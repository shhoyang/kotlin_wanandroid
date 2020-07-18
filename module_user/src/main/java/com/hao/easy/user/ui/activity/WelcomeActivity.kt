package com.hao.easy.user.ui.activity

import com.hao.easy.base.Config
import com.hao.easy.base.ui.BaseActivity
import com.hao.easy.user.R
import com.hao.easy.user.Router
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class WelcomeActivity : BaseActivity() {

    override fun showToolbar() = false

    override fun getLayoutId(): Int {
        return R.layout.user_activity_welcome
    }

    override fun initData() {
        GlobalScope.launch {
            Config.init()
            withContext(Dispatchers.Main) {
                start()
            }
        }
    }

    private fun start() {
        Router.startMainActivity()
        finish()
    }
}
