package com.hao.easy.activity

import android.content.Intent
import com.hao.easy.R
import com.hao.easy.base.ui.BaseActivity
import com.hao.easy.base.ui.UIParams

class WelcomeActivity : BaseActivity() {

    override fun prepare(uiParams: UIParams, intent: Intent?) {
        uiParams.showToolbar = false
    }

    override fun getLayoutId() = R.layout.app_activity_welcome

    override fun initData() {
        to(MainActivity::class.java, true)
    }
}
