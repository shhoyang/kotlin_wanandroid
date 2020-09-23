package com.hao.easy.wan.view

import android.content.Context
import android.util.AttributeSet
import androidx.constraintlayout.widget.ConstraintLayout
import com.hao.easy.base.utils.DisplayUtils

/**
 * @author Yang Shihao
 * @Date 2020/9/9
 *
 * 最小高为状态栏高度的ConstraintLayout
 */
class MinimumHeightConstraintLayout : ConstraintLayout {

    private var statusBarHeight: Int = 0

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init()
    }

    private fun init() {
        statusBarHeight = DisplayUtils.getStatusBarHeight(context)
    }

    override fun getMinimumHeight(): Int {
        return statusBarHeight
    }
}