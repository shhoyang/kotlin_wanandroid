package com.hao.library.view

import android.content.Context
import android.text.TextUtils
import android.util.AttributeSet
import android.util.TypedValue
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.core.content.ContextCompat
import com.hao.library.HaoLibrary
import com.hao.library.R
import com.hao.library.databinding.ToolbarBinding
import com.hao.library.extensions.gone
import com.hao.library.extensions.visibility
import com.hao.library.extensions.visible
import com.hao.library.utils.DrawableUtils

/**
 * @author Yang Shihao
 */
class ToolbarLayout : FrameLayout {

    private var viewBinding: ToolbarBinding
    private var toolbarHeight = 0
    private var toolbarBackgroundColor = 0
    private var showBack = true
    private var iconColor = 0
    private var titleText: String? = null
    private var titleTextSize = 0F
    private var titleTextColor = 0
    private var showLine = true
    private var lineColor = 0

    constructor(context: Context) : this(context, null, 0, 0)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : this(
        context,
        attrs,
        defStyleAttr, 0
    )

    constructor(
        context: Context,
        attrs: AttributeSet?,
        defStyleAttr: Int,
        defStyleRes: Int
    ) : super(context, attrs, defStyleAttr, defStyleRes) {
        viewBinding = ToolbarBinding.inflate(LayoutInflater.from(context), this)
        context.obtainStyledAttributes(
            attrs,
            R.styleable.ToolbarLayout,
            defStyleAttr,
            HaoLibrary.CONFIG.toolbarLayoutTheme()
        )
            .apply {
                toolbarHeight = getDimensionPixelSize(
                    R.styleable.ToolbarLayout_toolbarHeight,
                    resources.getDimensionPixelSize(R.dimen.toolbarHeight)
                )
                toolbarBackgroundColor =
                    getColor(R.styleable.ToolbarLayout_toolbarBackgroundColor, 0)
                showBack = getBoolean(R.styleable.ToolbarLayout_toolbarShowBack, showBack)
                iconColor = getColor(
                    R.styleable.ToolbarLayout_toolbarIconColor,
                    ContextCompat.getColor(context, R.color.toolbarIconColor)
                )
                titleText = getString(R.styleable.ToolbarLayout_toolbarTitleText)
                titleTextSize = getDimension(
                    R.styleable.ToolbarLayout_toolbarTitleTextSize,
                    resources.getDimension(R.dimen.toolbarTitleTextSize)
                )
                titleTextColor = getColor(
                    R.styleable.ToolbarLayout_toolbarTitleTextColor,
                    ContextCompat.getColor(context, R.color.toolbarTitleTextColor)
                )
                showLine = getBoolean(R.styleable.ToolbarLayout_toolbarShowLine, showLine)
                lineColor = getColor(
                    R.styleable.ToolbarLayout_toolbarLineColor,
                    ContextCompat.getColor(context, R.color.toolbarLineColor)
                )
                recycle()
            }
    }

    fun showBack(show: Boolean) {
        viewBinding.toolbarBack.visibility(show)
    }

    fun setTitleText(text: String) {
        viewBinding.toolbarTitle.text = text
    }

    fun setTitleTextColor(color: Int) {
        viewBinding.toolbarTitle.setTextColor(color)
    }

    fun setIconColor(color: Int) {
        viewBinding.toolbarBack.apply { DrawableUtils.tint(this, color) }
    }

    fun showLine(show: Boolean) {
        viewBinding.toolbarLine.visibility(show)
    }

    fun setLineColor(color: Int) {
        viewBinding.toolbarLine.setBackgroundColor(color)
    }


    fun setBackClickListener(f: () -> Unit) {
        viewBinding.toolbarBack.setOnClickListener { f() }
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        viewBinding.apply {
            if (0 != toolbarBackgroundColor) {
                root.setBackgroundColor(toolbarBackgroundColor)
            }
            if (showBack) {
                toolbarBack.visible()
                DrawableUtils.tint(toolbarBack, iconColor)
            } else {
                toolbarBack.gone()
            }
            if (!TextUtils.isEmpty(titleText)) {
                toolbarTitle.text = titleText
                toolbarTitle.setTextColor(titleTextColor)
                toolbarTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, titleTextSize)
            }
            if (showLine) {
                toolbarLine.visible()
                toolbarLine.setBackgroundColor(lineColor)
            } else {
                toolbarLine.gone()
            }
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        if (resources.getDimensionPixelSize(R.dimen.toolbarHeight) == toolbarHeight) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        } else {
            super.onMeasure(
                widthMeasureSpec,
                MeasureSpec.makeMeasureSpec(toolbarHeight, MeasureSpec.EXACTLY)
            )
        }
    }
}