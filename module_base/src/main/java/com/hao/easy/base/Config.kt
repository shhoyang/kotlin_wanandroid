package com.hao.easy.base

import com.hao.easy.base.user.User
import com.hao.easy.base.user.UserDb
import kotlin.concurrent.thread

object Config {

    private const val TAG = "Config"

    private const val KEY_USERNAME = "loginUserName"
    private const val KEY_TOKEN = "token_pass"


    var user: User? = null
    var username: String? = null
        get() {
            return user?.username
        }

    var isLogin: Boolean = false
        get() {
            return field && null != user
        }

    //刷新列表
    var refresh = 0

    fun init() {
        var username: String? = null
        var token: String? = null
        val cookies = App.instance.appComponent.cookies()
        cookies.forEach {
            if (it.name() == KEY_USERNAME) {
                val value = it.value()
                if ("\"\"" != value) {
                    username = value
                }

            } else if (it.name() == KEY_TOKEN) {
                val value = it.value()
                if ("\"\"" != value) {
                    token = value
                }
            }
        }

        if ((null != username) and (null != token)) {
            val user = UserDb.instance().userDao().queryUser(username!!)
            if (null != user) {
                this.user = user
                isLogin = true
            }
        }
    }

    fun logged(user: User) {
        this.user = user
        isLogin = true
        refresh()
        thread { UserDb.instance().userDao().insert(user) }
    }

    fun logout() {
        user?.apply {
            thread { UserDb.instance().userDao().delete(this) }
        }
        user = null
        isLogin = false
        refresh()
        App.instance.appComponent.apply {
            okHttpClient().dispatcher().cancelAll()
            persistentCookieJar().clear()
        }
    }

    fun refresh() {
        refresh++
    }
}

