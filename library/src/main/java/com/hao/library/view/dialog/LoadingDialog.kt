package com.hao.library.view.dialog

import android.app.Activity
import android.view.Gravity
import android.view.View
import android.view.Window
import androidx.annotation.StyleRes
import com.hao.library.HaoLibrary
import com.hao.library.R
import com.hao.library.databinding.DialogLoadingBinding
import com.hao.library.utils.DisplayUtils

/**
 * @author Yang Shihao
 */
class LoadingDialog(
    activity: Activity,
    @StyleRes themeResId: Int = HaoLibrary.CONFIG.loadingDialogTheme()
) :
    BaseDialog<DialogLoadingBinding>(activity, themeResId) {

    private var loadingLayoutId = 0

    override fun getVB() = DialogLoadingBinding.inflate(layoutInflater)

    override fun parseTheme() {
        context.obtainStyledAttributes(R.styleable.LoadingDialog).apply {
            loadingLayoutId =
                getResourceId(R.styleable.LoadingDialog_loadingDialogLoadingLayoutId, 0)
            recycle()
        }
    }

    override fun setWindowParams(window: Window) {
        val attributes = window.attributes
        val width = DisplayUtils.getScreenWidth(activity) / 3
        attributes.width = width
        attributes.height = width
        attributes.gravity = Gravity.CENTER
        window.attributes = attributes
    }

    override fun initView() {
        viewBinding {
            if (0 != loadingLayoutId) {
                loading.removeAllViews()
                loading.addView(View.inflate(activity, loadingLayoutId, loading))
            }
        }
    }

    fun setMessage(message: String): LoadingDialog {
        viewBinding {
            if (tvMessage.text != message) {
                tvMessage.text = message
            }
        }
        return this
    }
}