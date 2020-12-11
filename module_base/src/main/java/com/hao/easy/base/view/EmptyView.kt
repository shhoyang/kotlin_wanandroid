package com.hao.easy.base.view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import com.hao.easy.base.R
import com.hao.easy.base.extensions.gone
import com.hao.easy.base.extensions.visible
import kotlinx.android.synthetic.main.empty_view.view.*
import kotlin.properties.Delegates

/**
 * @author Yang Shihao
 * @date 2018/11/24
 */
class EmptyView : FrameLayout {

    private var contentViews: ArrayList<View>? = null

    private var state: Status by Delegates.observable(Status.DISMISS) { _, old, new ->
        if (old != new) {
            when (new) {
                Status.DISMISS -> {
                    handleContent(VISIBLE)
                    emptyViewLoading.gone()
                    emptyViewNoData.gone()
                    emptyViewLoadFailed.gone()
                    emptyViewNetworkUnavailable.gone()
                }
                Status.LOADING -> {
                    handleContent(GONE)
                    emptyViewLoading.visible()
                    emptyViewNoData.gone()
                    emptyViewLoadFailed.gone()
                    emptyViewNetworkUnavailable.gone()
                }
                Status.NO_DATA -> {
                    handleContent(GONE)
                    emptyViewLoading.gone()
                    emptyViewNoData.visible()
                    emptyViewLoadFailed.gone()
                    emptyViewNetworkUnavailable.gone()
                }
                Status.LOAD_FAILED -> {
                    handleContent(GONE)
                    emptyViewLoading.gone()
                    emptyViewNoData.gone()
                    emptyViewLoadFailed.visible()
                    emptyViewNetworkUnavailable.gone()
                }
                Status.NETWORK_UNAVAILABLE -> {
                    handleContent(GONE)
                    emptyViewLoading.gone()
                    emptyViewNoData.gone()
                    emptyViewLoadFailed.gone()
                    emptyViewNetworkUnavailable.visible()
                }
            }
        }
    }

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init()
    }

    private fun init() {
        View.inflate(context, R.layout.empty_view, this)
    }

    private fun handleContent(visibility: Int) {
        contentViews?.forEach { it.visibility = visibility }
    }

    fun dismiss() {
        state = Status.DISMISS
    }

    fun loading() {
        state = Status.LOADING
    }

    fun noData() {
        state = Status.NO_DATA
    }

    fun loadFailed() {
        state = Status.LOAD_FAILED
    }

    fun networkUnavailable() {
        state = Status.NETWORK_UNAVAILABLE
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
//        if (childCount > 5) {
//            throw IllegalStateException("EmptyView can only have one child view")
//        }
        if (childCount > 4) {
            contentViews = ArrayList()
            for (i in 4 until childCount) {
                contentViews!!.add(getChildAt(i))
            }
        }

        loading()
    }

    enum class Status {
        DISMISS, LOADING, NO_DATA, LOAD_FAILED, NETWORK_UNAVAILABLE
    }
}