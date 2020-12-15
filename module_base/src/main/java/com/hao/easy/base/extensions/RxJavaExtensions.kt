package com.hao.easy.base.extensions

import android.text.TextUtils
import com.hao.easy.base.BaseApplication
import com.hao.easy.base.Router
import com.hao.easy.base.model.HttpResult
import com.hao.easy.base.utils.T
import com.socks.library.KLog
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

fun <D, T : HttpResult<D>> Observable<T>.subscribeBy(
    onResponse: (D?) -> Unit,
    onFailure: ((Pair<Int, String>) -> Unit)? = null,
    toastWhenSucceed: Boolean = false,
    toastWhenFailed: Boolean = true
): Disposable {
    return subscribeOn(Schedulers.io())
        .subscribeOn(Schedulers.newThread())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe({
            when (it.errorCode) {
                HttpCode.SUCCESS -> {
                    if (toastWhenSucceed) {
                        toast(it.errorMsg)
                    }
                    onResponse(it.data)
                }
                HttpCode.NO_LOGIN -> {
                    toast(it.errorMsg)
                    Router.startLogin()
                }
                else -> {
                    errorHandle(it.errorCode, it.errorMsg, toastWhenFailed, onFailure)
                }
            }
        }, {
            KLog.d("subscribeBy--", "subscribeBy :${it.message}")
            errorHandle(HttpCode.HTTP_EXCEPTION, "网络不给力", toastWhenFailed, onFailure)
            it.printStackTrace()
        })
}

private fun toast(msg: String?) {
    if (!TextUtils.isEmpty(msg)) {
        T.short(BaseApplication.instance, msg)
    }
}

private fun errorHandle(
    code: Int,
    msg: String,
    enableToast: Boolean,
    onFailure: ((Pair<Int, String>) -> Unit)?
) {
    if (enableToast) {
        toast(msg)
    }
    onFailure?.invoke(Pair(code, msg))
}

object HttpCode {

    /**
     * 网络异常，自定义，不是后台返回
     */
    const val HTTP_EXCEPTION = -200

    /**
     * 成功
     */
    const val SUCCESS = 0

    /**
     * 未登录
     */
    const val NO_LOGIN = -1001
}

