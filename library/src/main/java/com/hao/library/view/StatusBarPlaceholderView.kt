package com.hao.library.view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import com.hao.library.utils.DisplayUtils

/**
 * @author Yang Shihao
 *
 * 状态栏等高
 */
class StatusBarPlaceholderView : View {

    private var statusBarHeight = 0

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init()
    }

    private fun init() {
        statusBarHeight = DisplayUtils.getStatusBarHeight(context)
    }


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(
            widthMeasureSpec,
            MeasureSpec.makeMeasureSpec(statusBarHeight, MeasureSpec.EXACTLY)
        )
    }
}