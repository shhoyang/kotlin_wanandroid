package com.hao.easy.base.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import com.hao.easy.base.utils.T

/**
 * @author Yang Shihao
 * @date 2018/11/18
 */
abstract class BaseFragment : Fragment() {


    /**
     * 懒加载标记
     */
    private var isLazy = false
    private var isLoad = false

    private lateinit var fragmentRootView: View

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
        prepare()
        initView()
        if (!isLazy) {
            initData()
        }
    }

    override fun onResume() {
        super.onResume()
        if (isLazy && !isLoad) {
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

    open fun prepare() {

    }

    @LayoutRes
    abstract fun getLayoutId(): Int

    open fun initView() {

    }

    open fun initData() {

    }

    fun lazyLoad(b: Boolean = true) {
        this.isLazy = b
    }

    fun toast(msg: String?) {
        T.short(context, msg)
    }

    fun toast(@StringRes resId: Int) {
        T.short(context, resId)
    }
}