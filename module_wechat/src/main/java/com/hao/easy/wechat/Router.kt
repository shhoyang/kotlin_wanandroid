package com.hao.easy.wechat

import com.alibaba.android.arouter.launcher.ARouter

object Router {

    fun startLogin() {
        ARouter.getInstance().build("/user/LoginActivity").navigation()
    }
}