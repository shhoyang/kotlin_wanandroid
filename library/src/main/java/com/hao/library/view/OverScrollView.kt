package com.hao.library.view

import android.content.Context
import android.graphics.Rect
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.animation.TranslateAnimation
import android.widget.ScrollView

class OverScrollView : ScrollView {

    private lateinit var contentView: View

    //布局初始位置
    private var contentViewPosition = Rect()

    private var startY = 0F

    private var canPullUP = false

    private var canPullDown = false

    private var isMove = false

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init()
    }

    private fun init() {
        isVerticalFadingEdgeEnabled = false
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        if (childCount == 1) {
            contentView = getChildAt(0)
        }
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        super.onLayout(changed, l, t, r, b)
        contentView.apply {
            contentViewPosition.set(left, top, right, bottom)
        }
    }

    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        when (ev.action) {
            MotionEvent.ACTION_DOWN -> {
                canPullUP = isCanPullUp()
                canPullDown = isCanPullDown()
                startY = ev.y
            }
            MotionEvent.ACTION_UP -> {
                if (isMove) {
                    val anim = TranslateAnimation(
                        0F,
                        0F,
                        contentView.top.toFloat(),
                        contentViewPosition.top.toFloat()
                    )
                    anim.duration = ANIM_DURATION
                    contentView.startAnimation(anim)

                    contentViewPosition.apply {
                        contentView.layout(left, top, right, bottom)
                    }

                    canPullUP = false
                    canPullDown = false
                    isMove = false
                }
            }

            MotionEvent.ACTION_MOVE -> {
                if (!canPullUP && !canPullDown) {
                    startY = ev.y
                    canPullUP = isCanPullUp()
                    canPullDown = isCanPullDown()
                    return super.dispatchTouchEvent(ev)
                }

                val lastY = ev.y
                val distance = lastY - startY

                val b =
                    (canPullUP && canPullDown) || (canPullUP && distance < 0) || (canPullDown && distance > 0)

                if (b) {
                    val offset = (distance * MOVE_FACTOR).toInt()
                    contentViewPosition.apply {
                        contentView.layout(left, top + offset, right, bottom + offset)
                    }
                    isMove = true
                }
            }
        }

        return super.dispatchTouchEvent(ev)
    }

    private fun isCanPullUp() = contentView.height <= height + scrollY

    private fun isCanPullDown() = scrollY == 0 || contentView.height < height + scrollY

    companion object {
        private const val MOVE_FACTOR = 0.5
        private const val ANIM_DURATION = 300L
    }
}