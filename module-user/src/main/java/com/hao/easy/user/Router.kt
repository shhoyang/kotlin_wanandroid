package com.hao.easy.user

import com.alibaba.android.arouter.launcher.ARouter

object Router {
    fun startFavoritesActivity() {
        ARouter.getInstance().build("/wan/FavoritesActivity").navigation()
    }
}