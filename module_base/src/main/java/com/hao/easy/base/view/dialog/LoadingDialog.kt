package com.hao.easy.base.view.dialog

import android.app.Activity
import android.graphics.Color
import android.view.Gravity
import android.view.Window
import com.hao.easy.base.databinding.DialogLoadingBinding
import com.hao.easy.base.utils.DisplayUtils
import com.hao.easy.base.utils.DrawableUtils

/**
 * @author Yang Shihao
 */
class LoadingDialog(activity: Activity) : BaseDialog<DialogLoadingBinding>(activity) {

    override fun getVB() = DialogLoadingBinding.inflate(layoutInflater)

    override fun setWindowParams(window: Window) {
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
    }

    fun setMsg(msg: String): LoadingDialog {
        if (viewBinding.tvText.text != msg) {
            viewBinding.tvText.text = msg
        }
        return this
    }
}