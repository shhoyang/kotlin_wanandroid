package com.hao.easy

import com.alibaba.android.arouter.launcher.ARouter
import com.hao.easy.base.BaseApplication
import com.socks.library.KLog
import com.tencent.smtt.sdk.QbSdk
import dagger.hilt.android.HiltAndroidApp

/**
 * @author Yang Shihao
 * @Date 2020/7/18
 */

@HiltAndroidApp
class App : BaseApplication() {

    override fun onCreate() {
        super.onCreate()
        KLog.init(true)
        QbSdk.initX5Environment(this, null)
        ARouter.openLog()
        ARouter.openDebug()
        ARouter.init(this)
    }
}