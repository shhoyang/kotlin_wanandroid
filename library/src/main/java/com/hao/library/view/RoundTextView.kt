package com.hao.library.view

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.StateListDrawable
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import com.hao.library.R

/**
 * @author Yang Shihao
 *
 * 圆角TextView，Button
 */
class RoundTextView : AppCompatTextView {

    private var radius = 0F
    private var borderWidth = 0
    private var borderColor = COLOR_NO
    private var normalColor = COLOR_NO
    private var normalTextColor = COLOR_NO
    private var pressedColor = COLOR_NO
    private var pressedTextColor = COLOR_NO
    private var selectedColor = COLOR_NO
    private var selectedTextColor = COLOR_NO
    private var disableColor = COLOR_NO
    private var disableTextColor = COLOR_NO

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        initUI(context, attrs)
    }

    private fun initUI(context: Context, attrs: AttributeSet?) {
        context.obtainStyledAttributes(attrs, R.styleable.RoundTextView).apply {
            radius = getDimension(R.styleable.RoundTextView_roundTextViewRadius, radius)
            borderWidth = getDimensionPixelSize(R.styleable.RoundTextView_roundTextViewBorderWidth, borderWidth)
            borderColor = getColor(R.styleable.RoundTextView_roundTextViewBorderColor, COLOR_NO)
            normalColor = getColor(R.styleable.RoundTextView_roundTextViewNormalColor, COLOR_NO)
            normalTextColor = getColor(R.styleable.RoundTextView_roundTextViewNormalTextColor, COLOR_NO)
            pressedColor = getColor(R.styleable.RoundTextView_roundTextViewPressedColor, COLOR_NO)
            pressedTextColor = getColor(R.styleable.RoundTextView_roundTextViewPressedTextColor, normalTextColor)
            selectedColor = getColor(R.styleable.RoundTextView_roundTextViewSelectedColor, COLOR_NO)
            selectedTextColor = getColor(R.styleable.RoundTextView_roundTextViewSelectedTextColor, normalTextColor)
            disableColor = getColor(R.styleable.RoundTextView_roundTextViewDisableColor, COLOR_NO)
            disableTextColor = getColor(R.styleable.RoundTextView_roundTextViewDisableTextColor, normalTextColor)
            recycle()
        }
        buildState()
    }

    private fun buildState() {
        val stateListDrawable = StateListDrawable()
        val textStates = ArrayList<IntArray>()
        val textColors = ArrayList<Int>()

        createDrawable(disableColor, COLOR_NO, 0, radius) {
            stateListDrawable.addState(DISABLE_STATE, it)
            textStates.add(DISABLE_STATE)
            textColors.add(disableTextColor)
        }

        createDrawable(selectedColor, COLOR_NO, 0, radius) {
            stateListDrawable.addState(SELECTED_STATE, it)
            textStates.add(SELECTED_STATE)
            textColors.add(selectedTextColor)
        }

        createDrawable(pressedColor, COLOR_NO, 0, radius) {
            stateListDrawable.addState(PRESSED_STATE, it)
            textStates.add(PRESSED_STATE)
            textColors.add(pressedTextColor)
        }

        createDrawable(normalColor, borderColor, borderWidth, radius) {
            stateListDrawable.addState(NORMAL_STATE, it)
            textStates.add(NORMAL_STATE)
            textColors.add(normalTextColor)
        }

        background = stateListDrawable
        setTextColor(ColorStateList(textStates.toTypedArray(), textColors.toIntArray()))
    }

    private fun createDrawable(
        color: Int,
        borderColor: Int,
        borderWidth: Int,
        radius: Float, block: (Drawable) -> Unit
    ) {
        if (color == COLOR_NO && borderColor == COLOR_NO) {
            return
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
        block(drawable)
    }

    companion object {
        private val DISABLE_STATE = intArrayOf(-android.R.attr.state_enabled)
        private val SELECTED_STATE = intArrayOf(android.R.attr.state_selected)
        private val PRESSED_STATE = intArrayOf(android.R.attr.state_pressed)
        private val NORMAL_STATE = intArrayOf()

        // 这个颜色很少有人用吧
        private const val COLOR_NO = -2
    }
}