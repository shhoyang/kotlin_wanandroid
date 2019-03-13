package com.hao.easy.base.extensions

import com.hao.easy.base.model.HttpResult
import com.socks.library.KLog
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

fun <D, T : HttpResult<D>> Observable<T>.main() =
        observeOn(AndroidSchedulers.mainThread())

fun <D, T : HttpResult<D>> Observable<T>.io_main() =
        subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())

fun <D, T : HttpResult<D>> Observable<T>.subscribeBy(onResponse: (D?) -> Unit) =
        this.subscribe({
            if (it.errorCode == 0) {
                onResponse(it.data)
            }
        }, {
            KLog.d("onFailure", it)
        })!!

fun <D, T : HttpResult<D>> Observable<T>.subscribeBy(onResponse: (D?) -> Unit, onFailure: (String) -> Unit) =
        subscribe({
            if (it.errorCode == 0) {
                onResponse(it.data)
            } else {
                onFailure(it.errorMsg)
            }
        }, {
            onFailure(it.message!!)
        })

