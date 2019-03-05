package com.hao.easy.wan

import com.alibaba.android.arouter.launcher.ARouter

object Router {

    fun startLogin() {
        ARouter.getInstance().build("/user/LoginActivity").navigation()
    }
}