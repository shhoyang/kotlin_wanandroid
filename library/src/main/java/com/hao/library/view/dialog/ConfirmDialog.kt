package com.hao.library.view.dialog

import android.app.Activity
import android.content.DialogInterface
import android.text.TextUtils
import android.view.Gravity
import android.view.View
import android.view.Window
import android.view.WindowManager
import androidx.annotation.StyleRes
import com.hao.library.HaoLibrary
import com.hao.library.databinding.DialogConfirmBinding
import com.hao.library.extensions.gone
import com.hao.library.extensions.visible
import com.hao.library.utils.DisplayUtils

/**
 * @author Yang Shihao
 */

class ConfirmDialog(
    activity: Activity,
    private val params: Params,
    @StyleRes themeResId: Int = HaoLibrary.CONFIG.confirmDialogTheme()
) :
    BaseDialog<DialogConfirmBinding>(activity, themeResId),
    View.OnClickListener,
    DialogInterface.OnCancelListener {

    override fun getVB() = DialogConfirmBinding.inflate(layoutInflater)

    override fun setWindowParams(window: Window) {
        val attributes = window.attributes
        attributes.width = DisplayUtils.getScreenWidth(activity) / 5 * 4
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
                tvTitle.text = params.title
                lineTitle.visible()
            }

            tvMessage.gravity = params.messageGravity
            tvMessage.text = params.message

            if (params.showCancelBtn) {
                tvCancel.visible()
                lineCancel.visible()
                if (null != params.cancelBtnText) {
                    tvCancel.text = params.cancelBtnText
                }
                tvCancel.setOnClickListener(this@ConfirmDialog)
            } else {
                tvCancel.gone()
                lineCancel.gone()
            }

            if (null != params.confirmBtnText) {
                tvConfirm.text = params.confirmBtnText
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

        fun setCancelBtnText(text: String): Builder {
            params.cancelBtnText = text
            return this
        }

        fun setConfirmBtnText(text: String): Builder {
            params.confirmBtnText = text
            return this
        }

        fun setMessageGravity(gravity: Int): Builder {
            params.messageGravity = gravity
            return this
        }

        fun showCancelBtn(show: Boolean): Builder {
            params.showCancelBtn = show
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
        var messageGravity: Int = Gravity.CENTER
        var confirmBtnText: String? = null
        var cancelBtnText: String? = null
        var showCancelBtn: Boolean = true
        var confirmDialogListener: ConfirmDialogListener? = null
    }
}

interface ConfirmDialogListener {

    fun confirm()

    fun cancel()
}