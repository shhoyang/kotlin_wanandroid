package com.hao.easy.base.view

import android.content.Context
import android.support.v4.widget.SwipeRefreshLayout
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.ViewConfiguration

class RefreshLayout : SwipeRefreshLayout {

    private var startX = .0F
    private var startY = .0F
    private var isViewPager = false
    private var touchSlop: Int = 0

    constructor(context: Context) : super(context) {
        touchSlop = ViewConfiguration.get(context).scaledTouchSlop
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        touchSlop = ViewConfiguration.get(context).scaledTouchSlop
    }

    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        when (ev.action) {
            MotionEvent.ACTION_DOWN -> {
                startX = ev.x
                startY = ev.y
                isViewPager = false
            }
            MotionEvent.ACTION_MOVE -> {
                if (isViewPager) {
                    return false
                }
                val endX = ev.x
                val endY = ev.y
                val distanceX = Math.abs(startX - endX)
                val distanceY = Math.abs(startY - endY)
                if (distanceX > touchSlop && distanceX > distanceY) {
                    isViewPager = true
                    return false
                }
            }
            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                isViewPager = false
            }
        }
        return super.onInterceptTouchEvent(ev)
    }
}