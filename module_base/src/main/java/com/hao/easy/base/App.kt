package com.hao.easy.base

import android.app.Application
import com.alibaba.android.arouter.launcher.ARouter
import com.hao.easy.base.di.component.AppComponent
import com.hao.easy.base.di.component.DaggerAppComponent
import com.hao.easy.base.di.module.AppModule
import com.hao.easy.base.extensions.notNullSingleValue
import com.socks.library.KLog
import com.tencent.smtt.sdk.QbSdk

/**
 * @author Yang Shihao
@date 2018/11/18
 */

open class App : Application() {

     val appComponent: AppComponent by lazy {
        DaggerAppComponent.builder()
                .appModule(AppModule(this))
                .build()
    }

    companion object {
        var instance by notNullSingleValue<App>()
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        KLog.init(true)
        QbSdk.initX5Environment(this, null)
        ARouter.openLog()
        ARouter.openDebug()
        ARouter.init(this)
    }
}