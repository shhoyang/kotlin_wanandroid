package com.hao.easy.user

import com.alibaba.android.arouter.launcher.ARouter

object Router {
    fun startFavActivity() {
        ARouter.getInstance().build("/wechat/FavActivity").navigation()
    }
}