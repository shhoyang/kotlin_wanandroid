package com.hao.easy.base.view.dialog

import android.app.Activity
import android.content.DialogInterface
import android.graphics.Color
import android.view.Gravity
import android.view.View
import android.view.Window
import android.view.WindowManager
import com.hao.easy.base.databinding.DialogConfirmBinding
import com.hao.easy.base.extensions.gone
import com.hao.easy.base.utils.DisplayUtils
import com.hao.easy.base.utils.DrawableUtils

/**
 * @author Yang Shihao
 */

class ConfirmDialog(activity: Activity) : BaseDialog<DialogConfirmBinding>(activity),
    View.OnClickListener,
    DialogInterface.OnCancelListener {

    private var confirmDialogListener: ConfirmDialogListener? = null

    override fun getVB() = DialogConfirmBinding.inflate(layoutInflater)

    override fun setWindowParams(window: Window) {
        val attributes = window.attributes
        val w = DisplayUtils.getScreenWidth(activity)
        attributes.width = w / 10 * 8
        attributes.height = WindowManager.LayoutParams.WRAP_CONTENT
        attributes.gravity = Gravity.CENTER
        window.attributes = attributes
        window.setBackgroundDrawable(
            DrawableUtils.generateRoundRectDrawable(
                DisplayUtils.dp2px(
                    activity,
                    4
                ).toFloat(), Color.WHITE
            )
        )
        setCancelable(false)
        setOnCancelListener(this)
    }

    override fun initView() {
        viewBinding.tvCancel.setOnClickListener(this)
        viewBinding.tvConfirm.setOnClickListener(this)
    }

    fun setMsg(msg: String): ConfirmDialog {
        viewBinding.tvMsg.text = msg
        return this
    }

    fun hideCancel(): ConfirmDialog {
        viewBinding.tvCancel.gone()
        viewBinding.line.gone()
        return this
    }

    fun setListener(confirmDialogListener: ConfirmDialogListener?): ConfirmDialog {
        this.confirmDialogListener = confirmDialogListener
        return this
    }

    override fun onClick(v: View?) {
        if (v == viewBinding.tvCancel) {
            confirmDialogListener?.cancel()
        } else if (v == viewBinding.tvConfirm) {
            confirmDialogListener?.confirm()
        }
        dismiss()
    }

    override fun onCancel(dialog: DialogInterface?) {
        confirmDialogListener?.cancel()
    }
}

interface ConfirmDialogListener {

    fun confirm()

    fun cancel()
}