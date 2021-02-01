package com.hao.library.view

import android.content.Context
import android.util.AttributeSet
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout

open class RefreshLayout : SwipeRefreshLayout {

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    fun startRefresh() {
        isRefreshing = true
    }

    fun stopRefresh() {
        isRefreshing = false
    }
}