package com.hao.easy.base.view

import android.content.Context
import android.util.AttributeSet
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.hao.easy.base.R

open class RefreshLayout : SwipeRefreshLayout {

    constructor(context: Context) : this(context,null)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        setColorSchemeResources(R.color.colorPrimary)
    }
}