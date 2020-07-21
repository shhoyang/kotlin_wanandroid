package com.hao.easy.base.extensions

import io.reactivex.Observable
import io.reactivex.functions.Function

import java.util.concurrent.TimeUnit

/**
 * @author Yang Shihao
 * @date 2019-06-18
 */
class RetryWithDelay(private val maxRetryCount: Int, private val retryDelayMillis: Int) :
    Function<Observable<out Throwable>, Observable<*>> {
    private var retryCount: Int = 0

    @Throws(Exception::class)
    override fun apply(observable: Observable<out Throwable>): Observable<*> {
        return observable
            .flatMap { throwable ->
                if (++retryCount <= maxRetryCount) {
                    Observable.timer(retryDelayMillis.toLong(), TimeUnit.MILLISECONDS)
                } else Observable.error<Any>(throwable)
            }
    }
}
