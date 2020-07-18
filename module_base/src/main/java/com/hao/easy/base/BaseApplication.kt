package com.hao.easy.base

import android.app.Application
import com.hao.easy.base.di.component.AppComponent
import com.hao.easy.base.di.component.DaggerAppComponent
import com.hao.easy.base.di.module.AppModule
import com.hao.easy.base.extensions.notNullSingleValue

/**
 * @author Yang Shihao
@date 2018/11/18
 */

open class BaseApplication : Application() {

     val appComponent: AppComponent by lazy {
        DaggerAppComponent.builder()
                .appModule(AppModule(this))
                .build()
    }

    companion object {
        var instance by notNullSingleValue<BaseApplication>()
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}