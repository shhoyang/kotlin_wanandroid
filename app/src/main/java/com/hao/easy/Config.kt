package com.hao.easy

import com.hao.easy.user.User
import com.hao.easy.user.UserDb
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

object Config {

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
        val cookies = App.instance.httpManager.cookies.loadAll()
        cookies.forEach {
            if (it.name == KEY_USERNAME) {
                val value = it.value
                if ("\"\"" != value) {
                    username = value
                }

            } else if (it.name == KEY_TOKEN) {
                val value = it.value
                if ("\"\"" != value) {
                    token = value
                }
            }
        }

        if (null != username && null != token) {
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
        GlobalScope.launch(Dispatchers.IO) {
            UserDb.instance().userDao().insert(user)
        }
    }

    fun logout() {
        user?.apply {
            GlobalScope.launch(Dispatchers.IO) {
                UserDb.instance().userDao().delete(this@apply)
            }
        }
        user = null
        isLogin = false
        refresh()
        App.instance.httpManager.apply {
            okHttpClient.dispatcher.cancelAll()
            persistentCookieJar.clear()
        }
    }

    fun refresh() {
        refresh++
    }
}

