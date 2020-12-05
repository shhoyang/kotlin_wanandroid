package com.hao.easy.base

import androidx.multidex.MultiDexApplication
import com.hao.easy.base.extensions.notNullSingleValue
import com.hao.easy.base.hilt.HttpManager
import javax.inject.Inject

/**
 * @author Yang Shihao
@date 2018/11/18
 */

open class BaseApplication : MultiDexApplication() {

    @Inject
    lateinit var httpManager: HttpManager

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    companion object {
        var instance by notNullSingleValue<BaseApplication>()
    }
}