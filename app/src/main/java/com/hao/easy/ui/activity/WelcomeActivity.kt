package com.hao.easy.ui.activity

import com.hao.easy.Config
import com.hao.easy.base.ui.BaseActivity
import com.hao.easy.R
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
        startActivity(MainActivity::class.java)
        finish()
    }
}
