package com.hao.easy.base

import androidx.multidex.MultiDexApplication
import com.hao.easy.base.constant.Constant
import com.hao.library.HaoLibrary
import com.hao.library.HaoLibraryConfig
import com.hao.library.extensions.notNullSingleValue
import com.hao.library.http.HttpResponseModel

/**
 * @author Yang Shihao
 */

open class BaseApplication : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()
        instance = this
        HaoLibrary.init(this, haoLibraryConfig)
    }

    private val haoLibraryConfig by lazy {
        object : HaoLibraryConfig() {
            override fun isLogin(): Boolean {
                return Config.isLogin
            }

            override fun login() {
                Router.startLogin()
            }

            override fun getBaseUrl(): String {
                return Constant.BASE_URL
            }

            override fun <T : HttpResponseModel<*>> handleResponse(t: T): Boolean {
                return when (t.getCode()) {
                    // 未登录
                    "-1001" -> {
                        Router.startLogin()
                        true
                    }
                    else -> false
                }
            }
        }
    }

    companion object {
        var instance by notNullSingleValue<BaseApplication>()
    }
}