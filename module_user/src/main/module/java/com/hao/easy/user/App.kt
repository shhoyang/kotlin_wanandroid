package com.hao.easy.user

import com.alibaba.android.arouter.launcher.ARouter
import com.hao.easy.base.BaseApplication
import com.hao.library.utils.L
import com.tencent.bugly.Bugly
import com.tencent.bugly.beta.Beta
import com.tencent.smtt.sdk.QbSdk

/**
 * @author Yang Shihao
 */

class App : BaseApplication() {

    override fun onCreate() {
        super.onCreate()
        L.init(true)
        QbSdk.initX5Environment(this, null)
        ARouter.openLog()
        ARouter.openDebug()
        ARouter.init(this)
    }
}