package com.hao.easy.base.viewmodel

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.ViewModel
import com.socks.library.KLog
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

open class BaseViewModel : ViewModel(), LifecycleObserver {

    private var tag = "BaseViewModel_${javaClass.simpleName}"

    private var compositeDisposable: CompositeDisposable? = null

    open fun Disposable.add() {
        if (compositeDisposable == null || compositeDisposable!!.isDisposed) {
            compositeDisposable = CompositeDisposable()
        }
        compositeDisposable!!.add(this)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    protected open fun onCreate() {
        KLog.d(tag, "onCreate")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    protected open fun onStart() {
        KLog.d(tag, "onStart")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    protected open fun onResume() {
        KLog.d(tag, "onResume")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    protected open fun onPause() {
        KLog.d(tag, "onPause")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    protected open fun onStop() {
        KLog.d(tag, "onStop")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    protected open fun onDestroy() {
        KLog.d(tag, "onDestroy")
    }

    override fun onCleared() {
        super.onCleared()
        KLog.d(tag, "onCleared")
        if (compositeDisposable != null) {
            if (!compositeDisposable!!.isDisposed) {
                compositeDisposable!!.dispose()

            }
            compositeDisposable = null
        }
    }
}
