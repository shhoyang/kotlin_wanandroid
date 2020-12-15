package com.hao.easy.base.ui

import androidx.fragment.app.Fragment
import com.hao.easy.base.R
import com.hao.easy.base.databinding.ActivityContainerBinding

abstract class ContainerActivity : BaseActivity<ActivityContainerBinding, Nothing>() {

    override fun getVB() = ActivityContainerBinding.inflate(layoutInflater)

    override fun getVM(): Nothing? = null

    override fun initView() {
        supportFragmentManager.beginTransaction()
            .add(R.id.frameLayout, getFragment())
            .commit()
    }

    override fun initData() {

    }

    abstract fun getFragment(): Fragment
}
