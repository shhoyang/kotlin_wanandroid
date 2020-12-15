package com.hao.easy.base.view.dialog

import android.app.Activity
import android.app.Dialog
import android.os.Bundle
import android.view.Window
import androidx.annotation.StyleRes
import androidx.viewbinding.ViewBinding
import com.hao.easy.base.R

/**
 * @author Yang Shihao
 */
abstract class BaseDialog<VB : ViewBinding>(
    protected val activity: Activity,
    @StyleRes themeResId: Int = R.style.Dialog
) : Dialog(
    activity,
    themeResId
) {

    var viewBinding: VB

    init {
        viewBinding = getVB()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window?.apply {
            decorView.setPadding(0, 0, 0, 0)
            setWindowParams(this)
        }
        setContentView(viewBinding.root)
        initView()
    }

    override fun show() {
        if (!isShowing) {
            super.show()
        }
    }

    open fun setWindowParams(window: Window) {

    }

    open fun initView() {

    }

    abstract fun getVB(): VB
}