package com.hao.easy.base.view

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.ViewConfiguration
import kotlin.math.abs

class RefreshLayout2 : RefreshLayout {

    private var startX = .0F
    private var startY = .0F
    private var isViewPager = false
    private var touchSlop: Int = 0

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
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
                val distanceX = abs(startX - endX)
                val distanceY = abs(startY - endY)
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