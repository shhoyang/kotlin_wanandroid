package com.hao.library.ui

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.viewbinding.ViewBinding
import com.hao.library.Dagger
import com.hao.library.annotation.Base
import com.hao.library.annotation.InjectViewBinding
import com.hao.library.annotation.InjectViewModel

/**
 * @author Yang Shihao
 */
@Base
abstract class BaseFragment<VB : ViewBinding, VM : ViewModel> : Fragment() {

    @JvmField
    @InjectViewBinding
    var vb: VB? = null

    @JvmField
    @InjectViewModel
    var vm: VM? = null

    protected val uiParams = UIParams()

    private var isLoad = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Dagger.inject(this)
        return vb?.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        prepare(uiParams, arguments)
        initView()
        isLoad = false
        if (!uiParams.isLazy) {
            initData()
        }
    }

    override fun onResume() {
        super.onResume()
        if (uiParams.isLazy && !isLoad) {
            initData()
            isLoad = true
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        vb = null
        isLoad = false
    }

    open fun prepare(uiParams: UIParams, bundle: Bundle?) {

    }

    fun viewBinding(block: VB.() -> Unit) {
        vb?.let(block)
    }

    fun viewModel(block: VM.() -> Unit) {
        vm?.let(block)
    }

    fun act(block: (BaseActivity<*, *>) -> Unit) {
        val activity = activity
        if (activity != null && activity is BaseActivity<*, *> && !activity.isFinishing) {
            block(activity)
        }
    }

    fun toast(msg: String?) {
        act { it.toast(msg) }
    }

    fun toast(@StringRes resId: Int) {
        act { it.toast(resId) }
    }

    fun toA(cls: Class<out Activity>, isFinish: Boolean = false) {
        act { it.toA(cls, isFinish) }
    }

    fun <T : View> f(id: Int): T? {
        return vb?.root?.findViewById(id)
    }

    abstract fun initView()

    abstract fun initData()
}