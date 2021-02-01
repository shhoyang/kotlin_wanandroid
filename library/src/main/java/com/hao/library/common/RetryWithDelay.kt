package com.hao.library.common

import io.reactivex.Observable
import io.reactivex.functions.Function
import java.util.concurrent.TimeUnit

/**
 * @author Yang Shihao
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
