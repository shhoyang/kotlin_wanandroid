package com.hao.library.ui

import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.hao.library.R
import com.hao.library.annotation.Base
import com.hao.library.viewmodel.PlaceholderViewModel

@Base
abstract class ContainerActivity<VB : ViewBinding> : BaseActivity<VB, PlaceholderViewModel>() {

    override fun initView() {
        supportFragmentManager.beginTransaction()
            .add(R.id.frameLayout, getFragment())
            .commit()
    }

    override fun initData() {

    }

    abstract fun getFragment(): Fragment
}
