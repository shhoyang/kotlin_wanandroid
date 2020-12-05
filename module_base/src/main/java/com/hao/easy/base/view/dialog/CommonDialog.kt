package com.hao.easy.base.view.dialog

import android.app.Activity
import android.app.Dialog
import android.view.Gravity
import android.view.View
import android.view.Window
import android.view.WindowManager
import androidx.annotation.LayoutRes
import androidx.annotation.StyleRes
import com.hao.easy.base.R
import com.hao.easy.base.utils.DisplayUtils


/**
 * @author Yang Shihao
 */
open class CommonDialog(
    var activity: Activity,
    private var params: Params? = null,
    @LayoutRes val layoutId: Int = 0,
    @StyleRes themeResId: Int = R.style.Dialog
) : Dialog(activity, themeResId) {

    init {
        createDialog()
    }

    private fun createDialog() {
        val window = window
        window?.decorView?.setPadding(0, 0, 0, 0)
        if (window != null) {
            setParams(window)
        }
    }

    protected open fun setParams(window: Window) {
        params?.apply {
            setContentView(view!!)
            val attributes = window.attributes
            attributes.width = width
            attributes.height = height
            attributes.gravity = gravity
            attributes.windowAnimations = anim
            window.attributes = attributes
        }
    }

    override fun show() {
        if (!isShowing) {
            super.show()
        }
    }

    class Builder(private val activity: Activity) {

        private val params = Params()

        fun setWidth(width: Int): Builder {
            params.width = width
            return this
        }

        fun setHeight(height: Int): Builder {
            params.height = height
            return this
        }

        fun setHorizontalMargin(margin: Int): Builder {
            params.width =
                DisplayUtils.getScreenWidth(activity) - DisplayUtils.dp2px(activity, margin) * 2
            return this
        }

        fun setGravity(gravity: Int): Builder {
            params.gravity = gravity
            return this
        }

        fun setAnim(@StyleRes anim: Int): Builder {
            params.anim = anim
            return this
        }

        fun setContentView(view: View): Builder {
            params.view = view
            return this
        }

        fun build(): CommonDialog {
            return CommonDialog(activity, params)
        }
    }

    class Params {
        var width: Int = WindowManager.LayoutParams.MATCH_PARENT
        var height: Int = WindowManager.LayoutParams.WRAP_CONTENT
        var gravity: Int = Gravity.CENTER
        var anim: Int = 0
        var view: View? = null
    }
}