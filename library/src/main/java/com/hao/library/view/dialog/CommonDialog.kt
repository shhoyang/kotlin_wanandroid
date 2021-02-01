package com.hao.library.view.dialog

import android.app.Activity
import android.app.Dialog
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import androidx.annotation.StyleRes
import com.hao.library.R
import com.hao.library.utils.DisplayUtils


/**
 * @author Yang Shihao
 */
class CommonDialog(
    activity: Activity,
    private val params: Params,
    @StyleRes themeResId: Int = R.style.Dialog
) : Dialog(
    activity,
    themeResId
) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window?.apply {
            decorView.setPadding(0, 0, 0, 0)
            params.apply {
                val attr = attributes
                attr.width = width
                attr.height = height
                attr.gravity = gravity
                attr.windowAnimations = anim
                attributes = attr
            }
        }
        if (params.view == null) {
            throw NullPointerException("view cannot be null")
        }
        setContentView(params.view!!)
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