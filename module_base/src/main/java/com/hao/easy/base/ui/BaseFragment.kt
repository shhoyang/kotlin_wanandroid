package com.hao.easy.base.ui

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment

/**
 * @author Yang Shihao
 * @date 2018/11/18
 */
abstract class BaseFragment : Fragment() {

    protected val uiParams = UIParams()

    /**
     * 懒加载标记
     */
    private var isLoad = false

    protected lateinit var fragmentRootView: View

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentRootView = inflater.inflate(getLayoutId(), container, false)
        return fragmentRootView
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
        isLoad = false
    }

    fun <T : View> f(id: Int): T? {
        return fragmentRootView.findViewById(id)
    }

    open fun prepare(uiParams: UIParams, bundle: Bundle?) {

    }

    open fun initView() {

    }

    open fun initData() {

    }

    fun act(block: (BaseActivity) -> Unit) {
        val activity = activity
        if (activity != null && activity is BaseActivity && !activity.isFinishing) {
            block(activity)
        }
    }

    fun toast(msg: String?) {
        act { it.toast(msg) }
    }

    fun toast(@StringRes resId: Int) {
        act { it.toast(resId) }
    }

    fun to(cls: Class<out Activity>, isFinish: Boolean = false) {
        act { it.to(cls, isFinish) }
    }

    @LayoutRes
    abstract fun getLayoutId(): Int
}