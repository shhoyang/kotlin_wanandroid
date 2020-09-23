package com.hao.easy.activity

import android.content.Intent
import com.hao.easy.R
import com.hao.easy.base.ui.BaseActivity

class WelcomeActivity : BaseActivity() {

    override fun showToolbar() = false

    override fun getLayoutId() = R.layout.app_activity_welcome

    override fun initData() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}
