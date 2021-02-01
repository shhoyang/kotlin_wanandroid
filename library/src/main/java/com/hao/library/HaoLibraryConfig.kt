package com.hao.library

import com.hao.library.http.HttpResponseModel

/**
 * @author Yang Shihao
 */
abstract class HaoLibraryConfig {

    /**
     * UI相关
     * 自定义主题可以继承默认主题
     */
    open fun toolbarLayoutTheme() = R.style.ToolbarLayout
    open fun emptyViewTheme() = R.style.EmptyView
    open fun confirmDialogTheme() = R.style.ConfirmDialog
    open fun loadingDialogTheme() = R.style.LoadingDialog

    /**
     * 登录相关
     */
    abstract fun isLogin(): Boolean

    abstract fun login()

    /**
     * 网络相关
     */
    abstract fun getBaseUrl(): String

    abstract fun <T : HttpResponseModel<*>> handleResponse(t: T): Boolean
}