package com.hao.easy.base.view

import android.content.Context
import android.content.res.ColorStateList
import android.support.v4.content.ContextCompat
import android.support.v4.graphics.drawable.DrawableCompat
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import com.hao.easy.base.R
import com.hao.easy.base.extensions.visibility
import kotlinx.android.synthetic.main.toolbar.view.*
import org.jetbrains.anko.textColor


/**
 * @author Yang Shihao
 * @date 2018/11/21
 */
class ToolbarLayout : FrameLayout {

    var title: CharSequence? = ""
        set(value) {
            field = value
            toolbarTitle?.text = title
        }

    var textColor = 0x333333
        set(value) {
            field = value
            toolbarTitle?.textColor = value
        }

    var iconTintColor: Int = 0x333333
        set(value) {
            field = value
            toolbarBack?.apply { tintIcon(this, value) }
        }

    var showBack: Boolean = true
        set(value) {
            field = value
            toolbarBack?.visibility(value)
        }


    var showLine: Boolean = true
        set(value) {
            field = value
            toolbarLine?.visibility(value)
        }

    constructor(context: Context) : super(context) {
        init(null)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(attrs)
    }

    private fun init(attrs: AttributeSet?) {
        View.inflate(context, R.layout.toolbar, this)
        attrs?.let {
            val typedArray = context.obtainStyledAttributes(it, R.styleable.ToolbarLayout)
            typedArray.apply {
                title = getString(R.styleable.ToolbarLayout_title)
                showBack = getBoolean(R.styleable.ToolbarLayout_showBack, true)
                showLine = getBoolean(R.styleable.ToolbarLayout_showLine, true)
                textColor =
                    getColor(R.styleable.ToolbarLayout_textColor, ContextCompat.getColor(context, R.color.text_black))
                iconTintColor =
                    getColor(R.styleable.ToolbarLayout_iconTint, ContextCompat.getColor(context, R.color.text_black))
                recycle()
            }
        }
    }

    fun setBackClickListener(f: () -> Unit) {
        toolbarBack.setOnClickListener { f() }
    }

    private fun tintIcon(imageView: ImageView, colors: Int) {
        imageView.apply {
            val wrappedDrawable = DrawableCompat.wrap(drawable)
            DrawableCompat.setTintList(wrappedDrawable, ColorStateList.valueOf(colors))
            setImageDrawable(wrappedDrawable)
        }
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        toolbarBack.apply {
            visibility(showBack)
            if (showBack) {
                tintIcon(this, iconTintColor)
            }
        }
        toolbarTitle.apply {
            text = title
            textColor = this@ToolbarLayout.textColor
        }

        toolbarLine.visibility(showLine)
    }
}