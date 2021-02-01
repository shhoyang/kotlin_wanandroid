package com.hao.library.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.viewbinding.ViewBinding
import com.hao.library.AppManager
import com.hao.library.Dagger
import com.hao.library.R
import com.hao.library.annotation.Base
import com.hao.library.annotation.InjectViewBinding
import com.hao.library.annotation.InjectViewModel
import com.hao.library.utils.DisplayUtils
import com.hao.library.utils.L
import com.hao.library.utils.T
import com.hao.library.view.ToolbarLayout

/**
 * @author Yang Shihao
 */
@Base
abstract class BaseActivity<VB : ViewBinding, VM : ViewModel> : AppCompatActivity() {

    @JvmField
    @InjectViewBinding
    var vb: VB? = null

    @JvmField
    @InjectViewModel
    var vm: VM? = null

    private var toolbarLayout: ToolbarLayout? = null

    protected val uiParams = UIParams()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppManager.instance().pushActivity(this)
        L.d("ActivityName", javaClass.simpleName)
        init()
        initView()
        initData()
    }

    private fun init() {
        prepare(uiParams, intent)
        if(uiParams.isTransparentStatusBar){
            DisplayUtils.setTransparentStatusBar(this)
        }
        Dagger.inject(this)
        setContentView(vb?.root)
        toolbarLayout = f(R.id.baseToolbar)
        toolbarLayout?.setBackClickListener {
            onBackPressed()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        vb = null
        AppManager.instance().popActivity(this)
    }

    override fun setTitle(title: CharSequence) {
        toolbarLayout?.setTitleText(title.toString())
    }

    open fun prepare(uiParams: UIParams, intent: Intent?) {
    }

    fun viewBinding(block: VB.() -> Unit) {
        vb?.let(block)
    }

    fun viewModel(block: VM.() -> Unit) {
        vm?.let(block)
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
        return vb?.root?.findViewById(id)
    }

    fun contentView(): ViewGroup = window.decorView.findViewById(android.R.id.content)

    abstract fun initView()

    abstract fun initData()
}