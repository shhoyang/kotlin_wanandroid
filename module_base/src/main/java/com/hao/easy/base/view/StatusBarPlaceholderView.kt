package com.hao.easy.base.view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import com.hao.easy.base.utils.DisplayUtils

/**
 * @author Yang Shihao
 */
class StatusBarPlaceholderView : View {

    private var statusBarHeight: Int = 0

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs){
        init()
    }

    private fun init() {
        statusBarHeight = DisplayUtils.getStatusBarHeight(context)
    }


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(statusBarHeight, MeasureSpec.EXACTLY))
    }
}