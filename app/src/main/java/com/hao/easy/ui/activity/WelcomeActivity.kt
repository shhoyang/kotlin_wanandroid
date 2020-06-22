package com.hao.easy.ui.activity

import android.os.Handler
import android.os.Looper
import com.hao.easy.Config
import com.hao.easy.base.ui.BaseActivity
import com.hao.easy.R
import kotlin.concurrent.thread

class WelcomeActivity : BaseActivity() {

    companion object {
        private const val DURATION = 1500L
    }

    override fun showToolbar() = false

    override fun getLayoutId(): Int {
        return R.layout.user_activity_welcome
    }

    override fun initData() {
        thread {
            val l = System.currentTimeMillis()
            Config.init()
            val delayTime = DURATION + l - System.currentTimeMillis()
            if (delayTime <= 0) {
                start()
            } else {
                Handler(Looper.getMainLooper()).postDelayed({
                    start()
                }, delayTime)
            }
        }
    }

    private fun start() {
        startActivity(MainActivity::class.java)
        finish()
    }
}
