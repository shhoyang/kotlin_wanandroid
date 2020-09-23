package com.hao.easy.base

import com.alibaba.android.arouter.exception.InitException
import com.alibaba.android.arouter.launcher.ARouter

object Router {

    /**
     * 防止调用改方法时ARouter还未初始化完成而crash
     */
    fun startLogin() {
        try {
            ARouter.getInstance().build("/user/LoginActivity").navigation()
        } catch (e: InitException) {
            e.printStackTrace()
        }
    }
}