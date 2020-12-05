package com.hao.easy.base.view

import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import android.view.Gravity
import androidx.appcompat.widget.AppCompatTextView
import com.hao.easy.base.R

/**
 * @author Yang Shihao
 *
 * 边框、圆角文字
 */
class RoundTextView : AppCompatTextView {

    private var COLOR_NO = -1

    private var radius = 0F
    private var borderWidth = 0
    private var borderColor = COLOR_NO
    private var bgColor = COLOR_NO

    constructor(context: Context) : super(context) {
        initUI(context, null)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        initUI(context, attrs)
    }

    private fun initUI(context: Context, attrs: AttributeSet?) {
        gravity = Gravity.CENTER
        if (attrs != null) {
            val typedArray = context.obtainStyledAttributes(attrs, R.styleable.RoundTextView)
            typedArray.apply {
                radius = getDimension(R.styleable.RoundTextView_radius, radius)
                borderWidth =
                    getDimensionPixelSize(R.styleable.RoundTextView_borderWidth, borderWidth)
                borderColor = getColor(R.styleable.RoundTextView_borderColor, COLOR_NO)
                bgColor = getColor(R.styleable.RoundTextView_bgColor, COLOR_NO)
                recycle()
            }
        }
        val drawable = GradientDrawable()
        if (bgColor != COLOR_NO) {
            drawable.setColor(bgColor)
        }
        if (borderWidth != 0 && borderColor != COLOR_NO) {
            drawable.setStroke(borderWidth, borderColor)
        }
        if (radius != 0F) {
            drawable.cornerRadius = radius
        }
        background = drawable
    }
}