package com.hao.library.view

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.ViewConfiguration
import kotlin.math.abs

class RefreshLayout2 : RefreshLayout {

    private var startX = .0F
    private var startY = .0F
    private var isViewPager = false
    private var touchSlop = 0

    constructor(context: Context) : this(context, null)

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

//    @Override
//    public boolean dispatchTouchEvent(MotionEvent ev) {
//        switch (ev.getAction()) {
//            case MotionEvent.ACTION_DOWN:
//            startX = (int) ev.getX();
//            startY = (int) ev.getY();
//            getParent().requestDisallowInterceptTouchEvent(true);//告诉viewgroup不要去拦截我
//            break;
//            case MotionEvent.ACTION_MOVE:
//            int endX = (int) ev.getX();
//            int endY = (int) ev.getY();
//            int disX = Math.abs(endX - startX);
//            int disY = Math.abs(endY - startY);
//            if (disX > disY) {
//                getParent().requestDisallowInterceptTouchEvent(false);
//            } else {
//                getParent().requestDisallowInterceptTouchEvent(true);//下拉的时候是false
//            }
//            break;
//            case MotionEvent.ACTION_UP:
//            case MotionEvent.ACTION_CANCEL:
//            getParent().requestDisallowInterceptTouchEvent(true);
//            break;
//        }
//        return super.dispatchTouchEvent(ev);
//    }
}