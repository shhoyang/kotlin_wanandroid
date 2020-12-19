package com.hao.easy.base.view.dialog

import android.app.Activity
import android.view.Gravity
import android.view.Window
import com.hao.easy.base.databinding.DialogLoadingBinding
import com.hao.easy.base.utils.DisplayUtils

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
    }

    fun setMessage(message: String): LoadingDialog {
        viewBinding {
            if (tvText.text != message) {
                tvText.text = message
            }
        }
        return this
    }
}