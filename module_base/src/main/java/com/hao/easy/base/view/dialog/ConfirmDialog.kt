package com.hao.easy.base.view.dialog

import android.app.Activity
import android.content.DialogInterface
import android.graphics.Color
import android.view.Gravity
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.TextView
import com.hao.easy.base.R
import com.hao.easy.base.extensions.gone
import com.hao.easy.base.utils.DisplayUtils
import com.hao.easy.base.utils.DrawableUtils

/**
 * @author Yang Shihao
 */

class ConfirmDialog(activity: Activity) : CommonDialog(activity = activity), View.OnClickListener,
    DialogInterface.OnCancelListener {

    private var tvMsg: TextView? = null
    private var tvCancel: TextView? = null
    private var tvConfirm: TextView? = null
    private var line: View? = null

    private var confirmDialogListener: ConfirmDialogListener? = null

    override fun setParams(window: Window) {
        initView()
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
    }

    fun initView() {
        val view = View.inflate(activity, R.layout.dialog_confirm, null)
        setContentView(view)
        tvMsg = view.findViewById(R.id.tvMsg)
        tvCancel = view.findViewById(R.id.tvCancel)
        tvConfirm = view.findViewById(R.id.tvConfirm)
        line = view.findViewById(R.id.line)

        tvCancel?.setOnClickListener(this)
        tvConfirm?.setOnClickListener(this)

        setOnCancelListener(this)
    }

    fun setMsg(msg: String): ConfirmDialog {
        tvMsg?.text = msg
        return this
    }

    fun hideCancel(): ConfirmDialog {
        tvCancel?.gone()
        line?.gone()
        return this
    }

    fun setListener(confirmDialogListener: ConfirmDialogListener?): ConfirmDialog {
        this.confirmDialogListener = confirmDialogListener
        return this
    }

    override fun onClick(v: View?) {
        if (v == tvCancel) {
            confirmDialogListener?.cancel()
        } else if (v == tvConfirm) {
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