package com.hao.library.view

import android.content.Context
import android.util.AttributeSet
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.hao.library.HaoLibrary
import com.hao.library.R
import com.hao.library.databinding.EmptyViewBinding
import com.hao.library.extensions.visibility
import com.hao.library.utils.DrawableUtils
import kotlin.properties.Delegates

/**
 * @author Yang Shihao
 */
class EmptyView : FrameLayout {

    private lateinit var viewBinding: EmptyViewBinding

    private var iconColor = 0
    private var textSize = 0F
    private var textColor = 0

    private var loadingText: String? = null
    private var noDataText: String? = null
    private var loadFailedText: String? = null
    private var networkUnavailableText: String? = null

    private var loadingLayoutId = 0
    private var noDataLayoutId = 0
    private var loadFailedLayoutId = 0
    private var networkUnavailableLayoutId = 0

    private var contentViews: ArrayList<View>? = null

    private var state: Int by Delegates.observable(DISMISS) { _, old, new ->
        if (old != new) {
            contentViews?.forEach { it.visibility(state.and(DISMISS) != 0) }
            viewBinding.apply {
                emptyViewLoading.visibility(state.and(LOADING) != 0)
                emptyViewNoData.visibility(state.and(NO_DATA) != 0)
                emptyViewLoadFailed.visibility(state.and(LOAD_FAILED) != 0)
                emptyViewNetworkUnavailable.visibility(state.and(NETWORK_UNAVAILABLE) != 0)

            }
        }
    }

    constructor(context: Context) : this(context, null, 0, 0)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : this(
        context,
        attrs,
        defStyleAttr, 0
    )

    constructor(
        context: Context,
        attrs: AttributeSet?,
        defStyleAttr: Int,
        defStyleRes: Int
    ) : super(context, attrs, defStyleAttr, defStyleRes) {
        viewBinding = EmptyViewBinding.inflate(LayoutInflater.from(context), this)
        context.obtainStyledAttributes(
            attrs,
            R.styleable.EmptyView,
            defStyleAttr,
            HaoLibrary.CONFIG.emptyViewTheme()
        ).apply {
            iconColor = getColor(
                R.styleable.EmptyView_emptyViewIconColor,
                ContextCompat.getColor(context, R.color.emptyViewIconColor)
            )
            textSize = getDimension(
                R.styleable.EmptyView_emptyViewTextSize,
                resources.getDimension(R.dimen.emptyViewTextSize)
            )
            textColor = getColor(
                R.styleable.EmptyView_emptyViewTextColor,
                ContextCompat.getColor(context, R.color.emptyViewTextColor)
            )
            loadingText = getString(R.styleable.EmptyView_emptyViewLoadingText)
            noDataText = getString(R.styleable.EmptyView_emptyViewNoDataText)
            loadFailedText = getString(R.styleable.EmptyView_emptyViewLoadFailedText)
            networkUnavailableText =
                getString(R.styleable.EmptyView_emptyViewNetworkUnavailableText)
            loadingLayoutId = getResourceId(R.styleable.EmptyView_emptyViewLoadingLayoutId, 0)
            noDataLayoutId = getResourceId(R.styleable.EmptyView_emptyViewNoDataLayoutId, 0)
            loadFailedLayoutId = getResourceId(R.styleable.EmptyView_emptyViewLoadFailedLayoutId, 0)
            networkUnavailableLayoutId =
                getResourceId(R.styleable.EmptyView_emptyViewNetworkUnavailableLayoutId, 0)
            recycle()
        }
    }

    fun dismiss() {
        state = DISMISS
    }

    fun loading() {
        state = LOADING
    }

    fun noData() {
        state = NO_DATA
    }

    fun loadFailed() {
        state = LOAD_FAILED
    }

    fun networkUnavailable() {
        state = NETWORK_UNAVAILABLE
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

        viewBinding.apply {
            setTextStyle(emptyViewLoadingText, loadingText)
            setTextStyle(emptyViewNoDataText, noDataText)
            setTextStyle(emptyViewLoadFailedText, loadFailedText)
            setTextStyle(emptyViewNetworkUnavailableText, networkUnavailableText)
            DrawableUtils.tint(emptyViewProgress, iconColor)
            DrawableUtils.tint(emptyViewNoDataIcon, iconColor)
            DrawableUtils.tint(emptyViewLoadFailedIcon, iconColor)
            DrawableUtils.tint(emptyViewNetworkUnavailableIcon, iconColor)
            setLayout(emptyViewLoading, loadingLayoutId)
            setLayout(emptyViewNoData, noDataLayoutId)
            setLayout(emptyViewLoading, loadFailedLayoutId)
            setLayout(emptyViewNetworkUnavailable, networkUnavailableLayoutId)
        }

        loading()
    }

    private fun setTextStyle(textView: TextView, text: String?) {
        textView.text = text
        textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize)
        textView.setTextColor(textColor)
    }

    private fun setLayout(parent: ViewGroup, layoutId: Int) {
        if (0 != layoutId) {
            parent.removeAllViews()
            parent.addView(inflate(context, layoutId, parent))
        }
    }

    companion object {
        private const val DISMISS = 0x000F0000
        private const val LOADING = 0x0000F000
        private const val NO_DATA = 0x00000F00
        private const val LOAD_FAILED = 0x000000F0
        private const val NETWORK_UNAVAILABLE = 0x0000000F
    }
}