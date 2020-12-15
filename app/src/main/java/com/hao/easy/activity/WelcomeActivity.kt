package com.hao.easy.activity

import com.hao.easy.base.ui.BaseActivity
import com.hao.easy.databinding.AppActivityWelcomeBinding

class WelcomeActivity : BaseActivity<AppActivityWelcomeBinding, Nothing>() {

    override fun getVB() = AppActivityWelcomeBinding.inflate(layoutInflater)

    override fun getVM(): Nothing? = null

    override fun initView() {
        toA(MainActivity::class.java, true)
    }

    override fun initData() {
    }
}
