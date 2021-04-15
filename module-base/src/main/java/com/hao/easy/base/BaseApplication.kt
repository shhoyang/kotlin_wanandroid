package com.hao.easy.base

import android.app.Application
import com.hao.easy.base.constant.Constant
import com.hao.library.HaoLibraryConfig
import com.hao.library.HttpConfig
import com.hao.library.extensions.notNullSingleValue
import com.hao.library.http.HttpResponseModel

/**
 * @author Yang Shihao
 */

open class BaseApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        instance = this
        HaoLibraryConfig.Builder(this)
            .setHttpConfig(MyHttpConfig())
            .apply()
    }

    class MyHttpConfig : HttpConfig {
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

    companion object {
        var instance by notNullSingleValue<BaseApplication>()
    }
}