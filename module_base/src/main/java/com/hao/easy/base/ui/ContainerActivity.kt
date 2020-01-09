package com.hao.easy.base.ui

import androidx.fragment.app.Fragment
import com.hao.easy.base.R

abstract class ContainerActivity : BaseActivity() {

    override fun getLayoutId() = R.layout.activity_container

    override fun initView() {
        supportFragmentManager.beginTransaction()
                .add(R.id.frameLayout, getFragment())
                .commit()
    }

    abstract fun getFragment(): Fragment
}
