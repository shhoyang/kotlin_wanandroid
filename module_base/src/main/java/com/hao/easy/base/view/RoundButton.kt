package com.hao.easy.base.view

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.StateListDrawable
import android.util.AttributeSet
import android.view.Gravity
import androidx.appcompat.widget.AppCompatTextView
import com.hao.easy.base.R

/**
 * @author Yang Shihao
 *
 * 圆角按钮
 */
class RoundButton : AppCompatTextView {

    private var radius = 0F
    private var borderWidth = 0
    private var borderColor = COLOR_NO
    private var normalColor = COLOR_NO
    private var pressedColor = COLOR_NO
    private var disableColor = COLOR_NO
    private var selectedColor = COLOR_NO
    private var normalTextColor = COLOR_NO
    private var pressedTextColor = COLOR_NO
    private var disableTextColor = COLOR_NO
    private var selectedTextColor = COLOR_NO

    constructor(context: Context) : super(context) {
        initUI(context, null)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        initUI(context, attrs)
    }

    private fun initUI(context: Context, attrs: AttributeSet?) {
        isClickable = true
        paint.isFakeBoldText = true
        gravity = Gravity.CENTER
        context.obtainStyledAttributes(attrs, R.styleable.RoundButton).apply {
            radius = getDimension(R.styleable.RoundButton_radius, 0F)
            borderWidth = getDimensionPixelSize(R.styleable.RoundButton_borderWidth, 0)
            borderColor = getColor(R.styleable.RoundButton_borderColor, COLOR_NO)
            normalColor = getColor(R.styleable.RoundButton_normalColor, COLOR_NO)
            pressedColor = getColor(R.styleable.RoundButton_pressedColor, COLOR_NO)
            disableColor = getColor(R.styleable.RoundButton_disableColor, COLOR_NO)
            selectedColor = getColor(R.styleable.RoundButton_selectedColor, COLOR_NO)
            normalTextColor = getColor(R.styleable.RoundButton_normalTextColor, COLOR_NO)
            pressedTextColor = getColor(R.styleable.RoundButton_pressedTextColor, normalTextColor)
            disableTextColor = getColor(R.styleable.RoundButton_disableTextColor, normalTextColor)
            selectedTextColor = getColor(R.styleable.RoundButton_selectedTextColor, normalTextColor)
            recycle()
        }
        buildColorState()
        buildDrawableState()
    }

    private fun buildColorState() {
        val colorStateList = ColorStateList(
            arrayOf(NORMAL_STATE, PRESSED_STATE, DISABLE_STATE, SELECTED_STATE),
            intArrayOf(normalTextColor, pressedTextColor, disableTextColor, selectedTextColor)
        )
        setTextColor(colorStateList)
    }


    private fun buildDrawableState() {
        val stateListDrawable = StateListDrawable()
        stateListDrawable.addState(NORMAL_STATE, createDrawable(normalColor, borderColor, borderWidth, radius))
        stateListDrawable.addState(PRESSED_STATE, createDrawable(pressedColor, COLOR_NO, 0, radius))
        stateListDrawable.addState(DISABLE_STATE, createDrawable(disableColor, COLOR_NO, 0, radius))
        stateListDrawable.addState(SELECTED_STATE, createDrawable(selectedColor, COLOR_NO, 0, radius))
        background = stateListDrawable
    }

    private fun createDrawable(
        color: Int,
        borderColor: Int,
        borderWidth: Int,
        radius: Float
    ): Drawable? {

        if (color == COLOR_NO && borderColor == COLOR_NO) {
            return null
        }

        val drawable = GradientDrawable()
        if (color != COLOR_NO) {
            drawable.setColor(color)
        }

        if (borderColor != COLOR_NO && borderWidth != 0) {
            drawable.setStroke(borderWidth, borderColor)
        }
        if (radius != 0F) {
            drawable.cornerRadius = radius
        }
        return drawable
    }

    companion object {
        private val NORMAL_STATE = intArrayOf(
            android.R.attr.state_enabled,
            -android.R.attr.state_pressed,
            -android.R.attr.state_selected
        )
        private val PRESSED_STATE = intArrayOf(android.R.attr.state_pressed)
        private val DISABLE_STATE = intArrayOf(-android.R.attr.state_enabled)
        private val SELECTED_STATE = intArrayOf(android.R.attr.state_selected)

        private const val COLOR_NO = -1
    }
}