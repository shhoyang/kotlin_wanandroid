package com.hao.easy.base.view.dialog

import android.app.Activity
import android.content.DialogInterface
import android.text.TextUtils
import android.view.Gravity
import android.view.View
import android.view.Window
import android.view.WindowManager
import com.hao.easy.base.databinding.DialogConfirmBinding
import com.hao.easy.base.extensions.gone
import com.hao.easy.base.extensions.visible
import com.hao.easy.base.utils.DisplayUtils

/**
 * @author Yang Shihao
 */

class ConfirmDialog(activity: Activity, private val params: Params) :
    BaseDialog<DialogConfirmBinding>(activity),
    View.OnClickListener,
    DialogInterface.OnCancelListener {

    override fun getVB() = DialogConfirmBinding.inflate(layoutInflater)

    override fun setWindowParams(window: Window) {
        val attributes = window.attributes
        val w = DisplayUtils.getScreenWidth(activity)
        attributes.width = w / 10 * 8
        attributes.height = WindowManager.LayoutParams.WRAP_CONTENT
        attributes.gravity = Gravity.CENTER
        window.attributes = attributes
        setCancelable(false)
        setOnCancelListener(this)
    }

    override fun initView() {
        viewBinding {
            if (TextUtils.isEmpty(params.title)) {
                tvTitle.gone()
                lineTitle.gone()
            } else {
                tvTitle.visible()
                lineTitle.visible()
                tvTitle.text = params.title
            }

            tvMessage.gravity = params.messageGravity
            tvMessage.text = params.message

            if (params.showCancel) {
                tvCancel.visible()
                lineCancel.visible()
                if (!TextUtils.isEmpty(params.cancelText)) {
                    tvCancel.text = params.cancelText
                }
                tvCancel.setOnClickListener(this@ConfirmDialog)
            } else {
                tvCancel.gone()
                lineCancel.gone()
            }

            if (!TextUtils.isEmpty(params.confirmText)) {
                tvConfirm.text = params.confirmText
            }
            tvConfirm.setOnClickListener(this@ConfirmDialog)
        }
    }

    override fun onClick(v: View?) {
        viewBinding {
            if (v == tvCancel) {
                params.confirmDialogListener?.cancel()
            } else if (v == tvConfirm) {
                params.confirmDialogListener?.confirm()
            }
        }
        dismiss()
    }

    override fun onCancel(dialog: DialogInterface?) {
        params.confirmDialogListener?.cancel()
    }

    class Builder(private val activity: Activity) {

        private val params = Params()

        fun setTitle(title: String): Builder {
            params.title = title
            return this
        }

        fun setMessage(message: String): Builder {
            params.message = message
            return this
        }

        fun setCancelText(cancelText: String): Builder {
            params.cancelText = cancelText
            return this
        }

        fun setConfirmText(confirmText: String): Builder {
            params.confirmText = confirmText
            return this
        }

        fun setMessageGravity(gravity: Int): Builder {
            params.messageGravity = gravity
            return this
        }

        fun showCancel(showCancel: Boolean): Builder {
            params.showCancel = showCancel
            return this
        }

        fun setListener(confirmDialogListener: ConfirmDialogListener?): Builder {
            params.confirmDialogListener = confirmDialogListener
            return this
        }

        fun build(): ConfirmDialog {
            return ConfirmDialog(activity, params)
        }
    }

    class Params {
        var title: String? = null
        var message: String? = null
        var cancelText: String? = null
        var confirmText: String? = null
        var messageGravity = Gravity.CENTER
        var showCancel = true
        var confirmDialogListener: ConfirmDialogListener? = null
    }
}

interface ConfirmDialogListener {

    fun confirm()

    fun cancel()
}