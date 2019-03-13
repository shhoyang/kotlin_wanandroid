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

    private var contentView: View? = null

    var state: Status by Delegates.observable(Status.DISMISS) { _, old, new ->
        if (old != new) {
            when (new) {
                Status.DISMISS -> {
                    contentView?.visible()
                    emptyViewLoading.gone()
                    emptyViewNoData.gone()
                    emptyViewLoadFailed.gone()
                    emptyViewNetworkUnavailable.gone()
                }
                Status.LOADING -> {
                    contentView?.gone()
                    emptyViewLoading.visible()
                    emptyViewNoData.gone()
                    emptyViewLoadFailed.gone()
                    emptyViewNetworkUnavailable.gone()
                }
                Status.NO_DATA -> {
                    contentView?.gone()
                    emptyViewLoading.gone()
                    emptyViewNoData.visible()
                    emptyViewLoadFailed.gone()
                    emptyViewNetworkUnavailable.gone()
                }
                Status.LOAD_FAILED -> {
                    contentView?.gone()
                    emptyViewLoading.gone()
                    emptyViewNoData.gone()
                    emptyViewLoadFailed.visible()
                    emptyViewNetworkUnavailable.gone()
                }
                Status.NETWORK_UNAVAILABLE -> {
                    contentView?.gone()
                    emptyViewLoading.gone()
                    emptyViewNoData.gone()
                    emptyViewLoadFailed.gone()
                    emptyViewNetworkUnavailable.visible()
                }
            }
        }
    }

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    private fun init() {
        View.inflate(context, R.layout.empty_view, this)

    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        if (childCount > 5) {
            throw IllegalStateException("EmptyView can only have one child view")
        }
        if (childCount == 5) {
            contentView = getChildAt(4)
        }
        state = Status.LOADING
    }

    enum class Status {
        DISMISS, LOADING, NO_DATA, LOAD_FAILED, NETWORK_UNAVAILABLE
    }
}