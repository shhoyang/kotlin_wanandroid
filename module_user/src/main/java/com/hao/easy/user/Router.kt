package com.hao.easy.user

import com.alibaba.android.arouter.launcher.ARouter

object Router {
    fun startMainActivity() {
        ARouter.getInstance().build("/app/MainActivity").navigation()
    }

    fun startFavActivity() {
        ARouter.getInstance().build("/wechat/FavActivity").navigation()
    }

    fun startKnowledgeActivity() {
        ARouter.getInstance().build("/wechat/KnowledgeActivity").navigation()
    }
}