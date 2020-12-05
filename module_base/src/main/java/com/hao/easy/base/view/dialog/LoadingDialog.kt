package com.hao.easy.base.view.dialog

import android.app.Activity
import android.graphics.Color
import android.view.Gravity
import android.view.View
import android.view.Window
import android.widget.TextView
import com.hao.easy.base.R
import com.hao.easy.base.utils.DisplayUtils
import com.hao.easy.base.utils.DrawableUtils

/**
 * @author Yang Shihao
 */
class LoadingDialog(activity: Activity) :
    CommonDialog(activity = activity) {

    private var tvText: TextView? = null

    override fun setParams(window: Window) {
        initView()
        val attributes = window.attributes
        val width = DisplayUtils.getScreenWidth(activity) / 3
        attributes.width = width
        attributes.height = width
        attributes.gravity = Gravity.CENTER
        window.attributes = attributes
        window.setBackgroundDrawable(
            DrawableUtils.generateRoundRectDrawable(
                DisplayUtils.dp2px(
                    activity,
                    8
                ).toFloat(), Color.WHITE
            )
        )
//        setCancelable(false)
    }

    fun initView() {
        val view = View.inflate(activity, R.layout.dialog_loading, null)
        tvText = view.findViewById(R.id.tvText)
        setContentView(view)
    }

    fun setMsg(msg: String): LoadingDialog {
        if (tvText?.text != msg) {
            tvText?.text = msg
        }
        return this
    }
}