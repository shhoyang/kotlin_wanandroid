package com.hao.easy.base.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.viewbinding.ViewBinding
import com.hao.easy.base.R
import com.hao.easy.base.common.AppManager
import com.hao.easy.base.utils.DisplayUtils
import com.hao.easy.base.utils.T
import com.hao.easy.base.view.ToolbarLayout
import com.socks.library.KLog

/**
 * @author Yang Shihao
 * @date 2018/11/18
 */
abstract class BaseActivity<VB : ViewBinding, VM : ViewModel> : AppCompatActivity() {

    private var viewBinding: VB? = null

    private val viewModel: VM? by lazy {
        getVM()
    }

    private var toolbarLayout: ToolbarLayout? = null

    protected val uiParams = UIParams()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppManager.instance().pushActivity(this)
        KLog.d("ActivityName", javaClass.simpleName)
        init()
        initView()
        initData()
    }

    private fun init() {
        prepare(uiParams, intent)
        if (uiParams.isTransparentStatusBar) {
            DisplayUtils.setTransparentStatusBar(this)
        }
        viewBinding = getVB()
        setContentView(viewBinding?.root)
        toolbarLayout = f(R.id.baseToolbar)
        toolbarLayout?.setBackClickListener {
            onBackPressed()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        viewBinding = null
        AppManager.instance().popActivity(this)
    }

    override fun setTitle(title: CharSequence?) {
        toolbarLayout?.title = title
    }

    open fun prepare(uiParams: UIParams, intent: Intent?) {
    }

    fun viewBinding(block: VB.() -> Unit) {
        viewBinding?.let(block)
    }

    fun viewModel(block: VM.() -> Unit) {
        viewModel?.let(block)
    }

    fun toast(text: String?) {
        T.short(this, text)
    }

    fun toast(@StringRes resId: Int) {
        T.short(this, resId)
    }

    fun toA(cls: Class<out Activity>, isFinish: Boolean = false) {
        startActivity(Intent(this, cls))
        if (isFinish) {
            finish()
        }
    }

    fun <T : View> f(id: Int): T? {
        return viewBinding?.root?.findViewById(id)
    }

    fun contentView(): ViewGroup = window.decorView.findViewById(android.R.id.content)

    abstract fun getVB(): VB

    abstract fun getVM(): VM?

    abstract fun initView()

    abstract fun initData()
}