package com.hao.easy.user

import com.hao.easy.base.ui.ContainerActivity
import com.hao.easy.user.ui.fragment.UserFragment

class MainActivity : ContainerActivity() {

    override fun showToolbar() = false

    override fun getFragment() = UserFragment()
}

