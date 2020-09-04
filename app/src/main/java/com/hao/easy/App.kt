package com.hao.easy

import androidx.multidex.MultiDexApplication
import com.alibaba.android.arouter.launcher.ARouter
import com.hao.easy.base.extensions.notNullSingleValue
import com.hao.easy.repository.HttpManager
import com.socks.library.KLog
import com.tencent.smtt.sdk.QbSdk
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

/**
 * @author Yang Shihao
 * @date 2018/11/18
 */

@HiltAndroidApp
class App : MultiDexApplication() {

    @Inject
    lateinit var httpManager: HttpManager

    override fun onCreate() {
        super.onCreate()
        instance = this
        KLog.init(true)
        QbSdk.initX5Environment(this, null)
        ARouter.openLog()
        ARouter.openDebug()
        ARouter.init(this)
    }

    companion object {
        var instance by notNullSingleValue<App>()
    }
}

