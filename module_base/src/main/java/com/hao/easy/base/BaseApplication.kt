package com.hao.easy.base

import android.app.Application
import com.hao.easy.base.extensions.notNullSingleValue
import com.hao.easy.base.hilt.HttpManager
import javax.inject.Inject

/**
 * @author Yang Shihao
@date 2018/11/18
 */

open class BaseApplication : Application() {

    @Inject
    lateinit var httpManager: HttpManager

    companion object {
        var instance by notNullSingleValue<BaseApplication>()
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}