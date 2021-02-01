package com.hao.library.http

import com.hao.library.HaoLibrary
import com.hao.library.utils.T
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import retrofit2.HttpException
import java.net.ConnectException
import java.net.SocketException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

fun <D, T : HttpResponseModel<D>> Observable<T>.subscribeBy(
    onResponse: (D?) -> Unit,
    onFailure: (Pair<String, String>) -> Unit = {},
    toastWhenSucceed: Boolean = false,
    toastWhenFailed: Boolean = false
): Disposable = subscribeOn(Schedulers.io())
    .observeOn(AndroidSchedulers.mainThread())
    .subscribe({
        // 没有消费
        if (!HaoLibrary.CONFIG.handleResponse(it)) {
            if (it.isSucceed()) {
                if (toastWhenSucceed) {
                    toast(it.getMessage())
                }
                onResponse(it.getData())
            } else {
                if (toastWhenFailed) {
                    toast(it.getMessage())
                }
                onFailure(Pair(it.getCode(), it.getMessage()))
            }
        }
    }, {
        OkHttpClient
        val error: Pair<String, String> = when (it) {
            is UnknownHostException, is ConnectException, is SocketException, is SocketTimeoutException, is HttpException -> Pair(
                "-200",
                "网络异常"
            )
            else -> Pair("-300", "未知异常")
        }
        if (toastWhenFailed) {
            toast(error.second)
        }
        onFailure(error)

    })

fun toast(msg: String) {
    T.short(HaoLibrary.context, msg)
}