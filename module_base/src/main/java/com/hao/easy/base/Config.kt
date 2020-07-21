package com.hao.easy.base

import com.hao.easy.base.user.User
import com.hao.easy.base.user.UserDb
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

object Config {

    private const val KEY_USERNAME = "loginUserName"
    private const val KEY_TOKEN = "token_pass"


    var user: User? = null
    val username: String?
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
        val cookies = BaseApplication.instance.httpManager.getCookies()
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
            val user = UserDb.instance().userDao().query(username!!)
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
        GlobalScope.launch(Dispatchers.IO) { UserDb.instance().userDao().insert(user) }
    }

    fun logout() {
        user?.apply {
            GlobalScope.launch(Dispatchers.IO) { UserDb.instance().userDao().delete(this@apply) }
        }
        user = null
        isLogin = false
        refresh()
        BaseApplication.instance.httpManager.cancelAllRequest()
    }

    inline fun refresh() {
        refresh++
    }
}

