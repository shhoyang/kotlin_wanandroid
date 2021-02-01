package com.hao.easy.activity

import com.hao.easy.databinding.AppActivityWelcomeBinding
import com.hao.library.annotation.AndroidEntryPoint
import com.hao.library.ui.BaseActivity
import com.hao.library.viewmodel.PlaceholderViewModel

@AndroidEntryPoint(injectViewModel = false)
class WelcomeActivity : BaseActivity<AppActivityWelcomeBinding, PlaceholderViewModel>() {

    override fun initView() {
        toA(MainActivity::class.java, true)
    }

    override fun initData() {
    }
}
